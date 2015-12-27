package com.weather.data.service.impl;

import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import net.rubyeye.xmemcached.MemcachedClient;
import net.rubyeye.xmemcached.exception.MemcachedException;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.MessageSource;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.weather.core.InitData;
import com.weather.core.bean.RemindTextTemplate;
import com.weather.core.cache.MemcachedKeyGenerator;
import com.weather.core.cache.RedisKeyGenerator;
import com.weather.core.spider.service.IParser;
import com.weather.core.spider.service.ISpider;
import com.weather.core.util.WeatherUtils;
import com.weather.data.controller.form.WeatherDataForm;
import com.weather.data.exception.WeatherDataException;
import com.weather.data.model.RemindCity;
import com.weather.data.model.TrendDay;
import com.weather.data.model.TrendHour;
import com.weather.data.service.IWeatherDataService;
import com.weather.passport.model.UserConfig;
import com.weather.passport.service.IPassportService;

@Service
public class WeatherDataService implements IWeatherDataService {
	private final Log log = LogFactory.getLog(WeatherDataService.class);
	@Value("${remind.city.list.expire.time}")
	private int remindCityListExpireTime;

	@Value("${weahter.reduce.tmp}")
	private int weahterReduceTmp;
	@Value("${weahter.less.than.tmp}")
	private int weahterLessThanTmp;
	@Value("${weather.hot.tmp}")
	private int weatherHotTmp;
	@Value("${weahter.big.wind.level}")
	private int weahterBigWindLevel;
	@Value("${weather.tmp.drop}")
	private int weatherTmpDrop;
	@Value("${weather.tmp.drop.ltmp.less.than}")
	private int weatherTmpDropLtmpLessThan;
	@Autowired
	private MessageSource messageSource;
	@Autowired
	private RedisTemplate<String, Object> redisTemplate;
	@Autowired
	private RedisTemplate<String, Integer> intRedisTemplate;
	@Autowired
	private RedisTemplate<String, RemindCity> remindCityRedisTemplate;
	@Autowired
	private IPassportService passportService;
	@Autowired
	private IParser<List<TrendHour>> htqTrendHourParser;
	@Autowired
	private IParser<List<TrendDay>> htqTrendDayParser;
	@Autowired
	private ISpider spider;
	@Autowired
	private MemcachedClient memcachedClient;
	@Value("${trend.weather.cache.expire.time}")
	private int trendWeatherCacheExpireTime;
	@Value("${trend.hour.weather.cache.expire.time}")
	private int trendHourWeatherCacheExpireTime;

	@Override
	public void receiverSpiderData(WeatherDataForm form)
			throws WeatherDataException {
		validateWeatherData(form);
		if (form.getCity() == 33 || form.getCity() == 340) {
			if (log.isDebugEnabled()) {
				log.debug("[----------" + "now weather cityid="
						+ form.getCity() + " hweather=" + form.gethWeather()
						+ "lweather=" + form.getlWeather() + "htmp="
						+ form.gethTmp() + "ltmp=" + form.getlTmp() + "hpower="
						+ form.gethWindPower() + "lpower="
						+ form.getlWindPower() + "----------]");
			}
		}
		// 保存当天天气数据
		redisTemplate.opsForValue().set(
				RedisKeyGenerator.genWeatherDateKey(
						WeatherUtils.getTodayDate(), form.getCity()), form);
		String rain = messageSource.getMessage("weahter.rain", null,
				Locale.SIMPLIFIED_CHINESE);
		RemindCity remind = new RemindCity();
		remind.setCity(form.getCity());
		if (form.gethWeather().indexOf(rain) != -1
				|| form.getlWeather().indexOf(rain) != -1) {
			// 今天有雨
			remind.setRemindRain(true);
		}
		WeatherDataForm oldData = (WeatherDataForm) redisTemplate.opsForValue()
				.get(RedisKeyGenerator.genWeatherDateKey(
						WeatherUtils.getYesterdayDate(), form.getCity()));
		if (oldData != null && StringUtils.isNotEmpty(oldData.getlTmp())) {
			try {
				int oldTmp = Integer.parseInt(oldData.getlTmp());
				int tmp = Integer.parseInt(form.getlTmp());
				if (oldTmp - tmp >= weahterReduceTmp
						&& tmp < weahterLessThanTmp) {
					// 今天气温骤降
					remind.setRemindCooling(true);
				}
			} catch (Exception e) {
			}
		}
		if (WeatherUtils.getWindLevel(form.gethWindPower()) >= weahterBigWindLevel
				|| WeatherUtils.getWindLevel(form.getlWindPower()) >= weahterBigWindLevel) {
			// 今天有大风
			remind.setRemindWind(true);
		}
		if (WeatherUtils.getTmp(form.gethTmp()) >= weatherHotTmp) {
			remind.setRemindHot(true);
		}
		if ((WeatherUtils.getTmp(form.gethTmp()) - WeatherUtils.getTmp(form
				.getlTmp())) >= weatherTmpDrop
				&& WeatherUtils.getTmp(form.getlTmp()) <= weatherTmpDropLtmpLessThan) {
			remind.setRemindTempDrop(true);
		}
		if (remind.isRemindRain() || remind.isRemindWind()
				|| remind.isRemindCooling() || remind.isRemindHot()
				|| remind.isRemindTempDrop()) {
			// 存入redis
			remindCityRedisTemplate.opsForSet().add(
					RedisKeyGenerator.genRemindCitiesKey(), remind);
			remindCityRedisTemplate.expire(
					RedisKeyGenerator.genRemindCitiesKey(),
					remindCityListExpireTime, TimeUnit.SECONDS);
			if (log.isDebugEnabled()) {
				log.debug("[----------remindCity: " + remind.getCity()
						+ "----------]");
			}
		}
	}

	private void validateWeatherData(WeatherDataForm form)
			throws WeatherDataException {
		if (form == null) {
			throw new WeatherDataException(
					WeatherDataException.ILLEGAL_OPERATION);
		}
		if (form.getCity() <= 0) {
			throw new WeatherDataException(
					WeatherDataException.ILLEGAL_OPERATION);
		}
		if (StringUtils.isEmpty(form.gethTmp())
				|| StringUtils.isEmpty(form.getlTmp())) {
			throw new WeatherDataException(
					WeatherDataException.ILLEGAL_OPERATION);
		}
		if (StringUtils.isEmpty(form.gethWeather())
				|| StringUtils.isEmpty(form.getlWeather())) {
			throw new WeatherDataException(
					WeatherDataException.ILLEGAL_OPERATION);
		}
		if (StringUtils.isEmpty(form.gethWindPower())
				|| StringUtils.isEmpty(form.getlWindPower())) {
			throw new WeatherDataException(
					WeatherDataException.ILLEGAL_OPERATION);
		}
	}

	@Override
	public Set<Integer> needSpiderCity() {
		Set<Integer> citys = new HashSet<Integer>();
		Set<Integer> allCitys = intRedisTemplate.opsForSet().members(
				RedisKeyGenerator.genRegisteredCityKey());
		for (int city : allCitys) {
			if (!redisTemplate.hasKey(RedisKeyGenerator.genWeatherDateKey(
					WeatherUtils.getTodayDate(), city))) {
				citys.add(city);
			}
		}
		return citys;

	}

	@Override
	public String getNoticeContent(UserConfig userConfig) {
		if (null == userConfig) {
			return null;
		}
		Set<RemindCity> citys = remindCityRedisTemplate.opsForSet().members(
				RedisKeyGenerator.genRemindCitiesKey());
		if (CollectionUtils.isEmpty(citys)) {
			return null;
		}

		RemindCity remindCity = getRemindCity(citys, userConfig.getCity());
		if (remindCity == null) {
			return null;
		}

		// 需要发送提醒
		WeatherDataForm todayData = (WeatherDataForm) redisTemplate
				.opsForValue().get(
						RedisKeyGenerator.genWeatherDateKey(
								WeatherUtils.getTodayDate(),
								userConfig.getCity()));
		if (null == todayData) {
			return null;
		}
		StringBuilder text = new StringBuilder();
		// 城市需要通知且用户打开通知
		if (remindCity.isRemindRain() && userConfig.isRemindRain()) {
			String rainText = messageSource.getMessage("weahter.rain", null,
					Locale.SIMPLIFIED_CHINESE);
			if (todayData.gethWeather().indexOf(rainText) != -1
					|| todayData.getlWeather().indexOf(rainText) != -1) {
				if (StringUtils.isEmpty(todayData.gethWeather())) {
					text.append(messageSource.getMessage(
							RemindTextTemplate.RAIN.getName(),
							new Object[] { todayData.getlWeather() },
							Locale.SIMPLIFIED_CHINESE));
				} else if (StringUtils.isEmpty(todayData.getlWeather())) {
					text.append(messageSource.getMessage(
							RemindTextTemplate.RAIN.getName(),
							new Object[] { todayData.gethWeather() },
							Locale.SIMPLIFIED_CHINESE));
				} else if (StringUtils.equals(todayData.getlWeather(),
						todayData.gethWeather())) {
					text.append(messageSource.getMessage(
							RemindTextTemplate.RAIN.getName(),
							new Object[] { todayData.gethWeather() },
							Locale.SIMPLIFIED_CHINESE));
				} else {
					String rainWeather = messageSource.getMessage(
							"remind.rain.weather",
							new Object[] { todayData.gethWeather(),
									todayData.getlWeather() },
							Locale.SIMPLIFIED_CHINESE);
					text.append(messageSource.getMessage(
							RemindTextTemplate.RAIN.getName(),
							new Object[] { rainWeather },
							Locale.SIMPLIFIED_CHINESE));
				}
			}
		}
		if (remindCity.isRemindCooling() && userConfig.isRemindCooling()) {
			text.append(messageSource.getMessage(
					RemindTextTemplate.TMP.getName(), null,
					Locale.SIMPLIFIED_CHINESE));
		}
		if (remindCity.isRemindHot() && userConfig.isRemindHot()) {
			if (StringUtils.isNotEmpty(todayData.gethTmp())) {
				text.append(messageSource.getMessage(
						RemindTextTemplate.HOT_TMP.getName(), null,
						Locale.SIMPLIFIED_CHINESE));
			}
		}
		if (remindCity.isRemindWind() && userConfig.isRemindWind()) {
			int hWind = WeatherUtils.getWindLevel(todayData.gethWindPower());
			int lWind = WeatherUtils.getWindLevel(todayData.getlWindPower());
			if (hWind > 8 || lWind > 8) {
				text.append(messageSource.getMessage(
						RemindTextTemplate.BIG_WIND.getName(), null,
						Locale.SIMPLIFIED_CHINESE));
			} else if ((hWind > 6 && hWind <= 8) || (lWind > 6 && lWind <= 8)) {
				text.append(messageSource.getMessage(
						RemindTextTemplate.WIND.getName(), null,
						Locale.SIMPLIFIED_CHINESE));
			}
		}
		if (remindCity.isRemindTempDrop() && userConfig.isRemindTempDrop()) {
			if (StringUtils.isNotEmpty(todayData.gethTmp())
					&& StringUtils.isNotEmpty(todayData.getlTmp())) {
				int tmpDrop = WeatherUtils.getTmp(todayData.gethTmp())
						- WeatherUtils.getTmp(todayData.getlTmp());
				text.append(messageSource.getMessage(
						RemindTextTemplate.TMP_DROP.getName(),
						new Object[] { tmpDrop }, Locale.SIMPLIFIED_CHINESE));
			}
		}

		if (StringUtils.isEmpty(text.toString())) {
			return null;
		}
		return messageSource.getMessage("remind.text", new Object[] {
				userConfig.getCityName(), text.toString() },
				Locale.SIMPLIFIED_CHINESE);
	}

	@Override
	public String getNoticeContent(String token) {
		UserConfig userConfig = passportService.getUserConfig(token);
		return getNoticeContent(userConfig);
	}

	private RemindCity getRemindCity(Set<RemindCity> citys, int city) {
		for (RemindCity remindCity : citys) {
			if (remindCity.getCity() == city) {
				return remindCity;
			}
		}
		return null;
	}

	@Override
	public List<TrendDay> getTrend(String city) throws WeatherDataException {
		List<TrendDay> list = null;
		if (InitData.HTQ_CITY_PY_MAP.get(city) == null
				&& InitData.MOJI_MATCH_HTQ_CITY_MAP.get(city) == null) {
			throw new WeatherDataException(
					WeatherDataException.TREND_NOT_SUPPORT_CITY);
		}
		try {
			list = memcachedClient.get(MemcachedKeyGenerator
					.genTrendWeatherCacheKey(city));
		} catch (TimeoutException | InterruptedException | MemcachedException e) {
			log.error(e.getMessage(), e);
		}
		if (list != null) {
			return list;
		}
		String py = null;
		py = InitData.HTQ_CITY_PY_MAP.get(city);
		if (StringUtils.isEmpty(py)) {
			String str = InitData.MOJI_MATCH_HTQ_CITY_MAP.get(city);
			py = (str.split("="))[1];
		}
		String url = "http://www.haotq.com/";
		String content = spider.spide(url + "d_" + py + ".html");
		if (StringUtils.isEmpty(content)) {
			throw new WeatherDataException(
					WeatherDataException.TREND_REND_TIME_OUT);
		}
		list = htqTrendDayParser.parser(content);
		content = spider.spide(url + "d15_" + py + ".html");
		if (StringUtils.isEmpty(content)) {
			throw new WeatherDataException(
					WeatherDataException.TREND_REND_TIME_OUT);
		}
		List<TrendDay> dayList2 = htqTrendDayParser.parser(content);
		list.addAll(dayList2);
		if (!CollectionUtils.isEmpty(list)) {
			try {
				memcachedClient.set(
						MemcachedKeyGenerator.genTrendWeatherCacheKey(city),
						trendWeatherCacheExpireTime, list);
			} catch (TimeoutException | InterruptedException
					| MemcachedException e) {
			}
		}
		return list;
	}

	@Override
	public List<TrendHour> getTrendHour(String city)
			throws WeatherDataException {
		List<TrendHour> list = null;
		if (InitData.HTQ_CITY_PY_MAP.get(city) == null
				&& InitData.MOJI_MATCH_HTQ_CITY_MAP.get(city) == null) {
			throw new WeatherDataException(
					WeatherDataException.TREND_NOT_SUPPORT_CITY);
		}
		try {
			list = memcachedClient.get(MemcachedKeyGenerator
					.genTrendHourWeatherCacheKey(city));
		} catch (TimeoutException | InterruptedException | MemcachedException e) {
			log.error(e.getMessage(), e);
		}
		if (list != null) {
			return list;
		}
		String py = null;
		py = InitData.HTQ_CITY_PY_MAP.get(city);
		if (StringUtils.isEmpty(py)) {
			String str = InitData.MOJI_MATCH_HTQ_CITY_MAP.get(city);
			py = (str.split("="))[1];
		}
		String url = "http://www.haotq.com/";
		String content = spider.spide(url + "h_" + py + ".html");
		if (StringUtils.isEmpty(content)) {
			throw new WeatherDataException(
					WeatherDataException.TREND_REND_TIME_OUT);
		}
		list = htqTrendHourParser.parser(content);

		if (!CollectionUtils.isEmpty(list)) {
			try {
				memcachedClient
						.set(MemcachedKeyGenerator
								.genTrendHourWeatherCacheKey(city),
								trendHourWeatherCacheExpireTime, list);
			} catch (TimeoutException | InterruptedException
					| MemcachedException e) {
			}
		}
		return list;
	}

}
