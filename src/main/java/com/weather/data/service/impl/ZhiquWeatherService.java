package com.weather.data.service.impl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.TimeoutException;

import net.rubyeye.xmemcached.MemcachedClient;
import net.rubyeye.xmemcached.exception.MemcachedException;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.weather.core.InitData;
import com.weather.core.bean.AQIEnum;
import com.weather.core.bean.UvidxEnum;
import com.weather.core.cache.MemcachedKeyGenerator;
import com.weather.core.model.Air;
import com.weather.core.model.BaseWeather;
import com.weather.core.model.ForecastHour;
import com.weather.core.model.FutureWeather;
import com.weather.core.model.WarningWeather;
import com.weather.core.model.WeatherInfo;
import com.weather.core.util.HttpUtils;
import com.weather.core.xml.bean.AirTemplate;
import com.weather.core.xml.bean.Cc;
import com.weather.core.xml.bean.Ct;
import com.weather.core.xml.bean.Cts;
import com.weather.core.xml.bean.Day;
import com.weather.core.xml.bean.Dt;
import com.weather.core.xml.bean.Dts;
import com.weather.core.xml.bean.Dw;
import com.weather.core.xml.bean.Dws;
import com.weather.core.xml.bean.Fhour;
import com.weather.core.xml.bean.ForecasthourTemplate;
import com.weather.core.xml.bean.History;
import com.weather.core.xml.bean.Hour;
import com.weather.core.xml.bean.Idx;
import com.weather.core.xml.bean.Idxs;
import com.weather.core.xml.bean.Index;
import com.weather.core.xml.bean.Main;
import com.weather.core.xml.bean.Pages;
import com.weather.core.xml.bean.Trend;
import com.weather.core.xml.bean.Trends;
import com.weather.core.xml.bean.WeatherTemplate;
import com.weather.data.service.IZhiquWeatherService;

@Service
public class ZhiquWeatherService implements IZhiquWeatherService {
	private final Log log = LogFactory.getLog(ZhiquWeatherService.class);
	private SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
	private SimpleDateFormat sdf_hour = new SimpleDateFormat("yyyy/MM/dd HH:mm");
	private SimpleDateFormat sdf_hour1 = new SimpleDateFormat(
			"yyyy-MM-dd HH:mm:ss");
	private SimpleDateFormat zh_sdf_hour = new SimpleDateFormat(
			"yyyy年MM月dd日HH时");
	private SimpleDateFormat sdf_only_hour = new SimpleDateFormat("HH:mm");
	@Autowired
	private MemcachedClient memcachedClient;
	@Value("${zhiqu.weather.cache.expire.time}")
	private int zhiquWeatherCacheExpireTime;
	@Autowired
	private MessageSource messageSource;

	@Override
	public WeatherTemplate getSixWeather(String city, String province) {
		WeatherTemplate template = new WeatherTemplate();
		if (!StringUtils.hasText(city) || !StringUtils.hasText(province)) {
			return template;
		}
		try {
			template = memcachedClient.get(MemcachedKeyGenerator
					.genZhiquWeatherCacheKey(city, province));
		} catch (TimeoutException | InterruptedException | MemcachedException e) {
			log.error(e.getMessage(), e);
		}
		if (template == null || template.getCts() == null) {
			WeatherInfo info = null;
			String url = "http://sixweather.3gpk.net/SixWeather.aspx?city="
					+ city
					+ "&prov="
					+ province
					+ "&channelid=com.metek.zqWeather&modelid=&deviceid=354254050781223";
			String content = HttpUtils.getContent(url);
			if (!StringUtils.hasText(content)) {
				return template;
			}
			try {
				Document document = (Document) DocumentHelper
						.parseText(content);
				Element root = document.getRootElement();
				info = new WeatherInfo();
				Element table1 = root.element("Table1");
				Element table2 = root.element("Table2");
				String zhuan = messageSource.getMessage("zhiqu.parse.zhuan",
						null, Locale.SIMPLIFIED_CHINESE);
				String feng = messageSource.getMessage("zhiqu.parse.feng",
						null, Locale.SIMPLIFIED_CHINESE);
				if (table2 != null) {
					List<ForecastHour> forecastHours = new ArrayList<ForecastHour>();
					for (int i = 1; i <= 4; i++) {
						String timespan = table2.elementText("timespan" + i);
						String sixweather = table2
								.elementText("sixweather" + i);
						String highTemp = table2.elementText("highTemp" + i);
						String lowTemp = table2.elementText("lowTemp" + i);
						ForecastHour forecastHour = new ForecastHour();
						forecastHour.setId(i);
						BaseWeather baseWeather = new BaseWeather();
						baseWeather.setMaxTmpStr(highTemp.replace("℃", ""));
						baseWeather.setMinTmpStr(lowTemp.replace("℃", ""));
						String[] skys = sixweather.split(zhuan);
						if (skys.length == 1) {
							baseWeather.setHwd(skys[0]);
							baseWeather.setLwd(skys[0]);
						} else {
							baseWeather.setHwd(skys[0]);
							baseWeather.setLwd(skys[1]);
						}
						forecastHour.setBaseWeather(baseWeather);
						String timeInterval = timespan;
						String ri = messageSource.getMessage("zhiqu.parse.ri",
								null, Locale.SIMPLIFIED_CHINESE);
						String mingtianshangwu = messageSource.getMessage(
								"zhiqu.parse.mingtianshangwu", null,
								Locale.SIMPLIFIED_CHINESE);
						String mingtianxiawu = messageSource.getMessage(
								"zhiqu.parse.mingtianxiawu", null,
								Locale.SIMPLIFIED_CHINESE);
						String shangwu = messageSource.getMessage(
								"zhiqu.parse.shangwu", null,
								Locale.SIMPLIFIED_CHINESE);
						String xiawu = messageSource.getMessage(
								"zhiqu.parse.xiawu", null,
								Locale.SIMPLIFIED_CHINESE);
						String qianbanye = messageSource.getMessage(
								"zhiqu.parse.qianbanye", null,
								Locale.SIMPLIFIED_CHINESE);
						String houbanye = messageSource.getMessage(
								"zhiqu.parse.houbanye", null,
								Locale.SIMPLIFIED_CHINESE);
						timeInterval = timeInterval.replace("(", "");
						timeInterval = timeInterval.replace(")", "");
						Calendar cal = Calendar.getInstance();
						if (timeInterval.indexOf(mingtianshangwu) != -1) {
							cal.add(Calendar.DAY_OF_YEAR, 1);
							timeInterval = timeInterval.replace(
									mingtianshangwu,
									cal.get(Calendar.DAY_OF_MONTH) + ri);
						}
						if (timeInterval.indexOf(mingtianxiawu) != -1) {
							cal.add(Calendar.DAY_OF_YEAR, 1);
							timeInterval = timeInterval.replace(mingtianxiawu,
									cal.get(Calendar.DAY_OF_MONTH) + ri);
						}
						timeInterval = timeInterval.replaceAll(shangwu,
								cal.get(Calendar.DAY_OF_MONTH) + ri);
						timeInterval = timeInterval.replace(xiawu,
								cal.get(Calendar.DAY_OF_MONTH) + ri);
						if (timeInterval.indexOf(qianbanye) != -1) {
							timeInterval = timeInterval.replace(qianbanye,
									cal.get(Calendar.DAY_OF_MONTH) + ri);
							String str[] = timeInterval.split("-");
							cal.add(Calendar.DAY_OF_YEAR, 1);
							timeInterval = str[0] + "-"
									+ cal.get(Calendar.DAY_OF_MONTH) + ri
									+ str[1];
						}
						if (timeInterval.indexOf(houbanye) != -1) {
							cal.add(Calendar.DAY_OF_YEAR, 1);
							timeInterval = timeInterval.replace(houbanye,
									cal.get(Calendar.DAY_OF_MONTH) + ri);
						}

						forecastHour.setTimeInterval(timeInterval);
						forecastHours.add(forecastHour);
					}
					info.setForecastHours(forecastHours);
				}
				if (table1 != null) {
					List<FutureWeather> futureWeathers = new ArrayList<FutureWeather>();
					info.setFutureWeathers(futureWeathers);
					String todaySun = table1.element("todaySun")
							.getStringValue();
					String tomorrowSun = table1.element("tomorrowSun")
							.getStringValue();
					for (int i = 0; i <= 6; i++) {
						Calendar calendar = Calendar.getInstance();
						FutureWeather futureWeather = new FutureWeather();
						Element temp = table1.element("temp" + i);
						Element weather = table1.element("weather" + i);
						Element wind = table1.element("wind" + i);
						Element windlevel = table1.element("windlevel" + i);
						if (i == 0) {
							calendar.add(Calendar.DAY_OF_YEAR, -1);
						} else if (i > 1) {
							calendar.add(Calendar.DAY_OF_YEAR, i - 1);
						}
						int week = calendar.get(Calendar.DAY_OF_WEEK);
						futureWeather.setDate(sdf.format(calendar.getTime()));
						futureWeather.setWeekday(week == 1 ? 7 : week - 1);
						if (i == 1) {
							String[] suns = todaySun.split("\\|");
							futureWeather.setSunrise(suns[0].trim());
							futureWeather.setSunset(suns[1].trim());
						} else if (i == 2) {
							String[] suns = tomorrowSun.split("\\|");
							futureWeather.setSunrise(suns[0].trim());
							futureWeather.setSunset(suns[1].trim());
						}
						BaseWeather baseWeather = new BaseWeather();
						String rangeTemp = temp.getStringValue().replaceAll(
								"℃", "");
						String[] rangeTemps = rangeTemp.split("~");
						baseWeather.setMaxTmpStr(rangeTemps[0]);
						baseWeather.setMinTmpStr(rangeTemps[1]);
						baseWeather.setWdir(wind.getStringValue().substring(0,
								wind.getStringValue().indexOf(feng) + 1));
						String[] skys = weather.getStringValue().split(zhuan);
						if (skys.length == 1) {
							baseWeather.setHwd(skys[0]);
							baseWeather.setLwd(skys[0]);
						} else {
							baseWeather.setHwd(skys[0]);
							baseWeather.setLwd(skys[1]);
						}
						baseWeather.setWl(windlevel.getStringValue());
						futureWeather.setBaseWeather(baseWeather);
						if (i == 1) {
							info.setBaseWeather(baseWeather);
						}
						futureWeathers.add(futureWeather);
					}
					String alarmtext = table1.elementText("alarmtext");
					if (StringUtils.hasText(alarmtext)) {
						String shi = messageSource.getMessage(
								"zhiqu.parse.shi", null,
								Locale.SIMPLIFIED_CHINESE);
						String fabu = messageSource.getMessage(
								"zhiqu.parse.fabu", null,
								Locale.SIMPLIFIED_CHINESE);
						String yujing = table1.elementText("yujing");
						List<WarningWeather> warningWeathers = new ArrayList<WarningWeather>();
						WarningWeather warningWeather = new WarningWeather();
						warningWeathers.add(warningWeather);
						info.setWarningWeathers(warningWeathers);
						warningWeather.setDesc(yujing.substring(
								yujing.indexOf(fabu) + 2, yujing.length()));
						warningWeather.setEndTime(null);
						warningWeather.setId(yujing.hashCode());
						warningWeather.setInfo(alarmtext);
						String time = yujing.substring(0,
								yujing.indexOf(shi) + 1);
						warningWeather.setPublishTime(zh_sdf_hour.parse(time));
					}
					info.setHum(Integer.parseInt(table1.elementText("shidu")
							.replace("%", "")));
					info.setNowTmp(Integer.parseInt(table1
							.elementText("tempNow")));
					info.setSunrise(todaySun.split("\\|")[0].trim());
					info.setSunset(todaySun.split("\\|")[1].trim());
					info.setUpdateTime(new Date());
					// info.setWindDirection(windDirection); 风力无法获取级别
					// info.setWindPower(windPower);
					Air air = new Air();
					air.setLevel(table1.elementText("AQIData"));
					air.setPm25(table1.elementText("PM2Dot5Data"));
					air.setPm10(table1.elementText("PM10Data"));
					info.setAir(air);
					// 紫外线
					String warning = table1.elementText("warning");
					UvidxEnum myUvidxEnum = null;
					for (UvidxEnum uvidxEnum : UvidxEnum.values()) {
						String uvidxDesc = messageSource.getMessage(
								uvidxEnum.getDesc(), null,
								Locale.SIMPLIFIED_CHINESE);
						if (warning.indexOf(uvidxDesc) != -1) {
							myUvidxEnum = uvidxEnum;
						}
					}

					if (myUvidxEnum != null) {
						info.setUvidx(messageSource.getMessage(
								myUvidxEnum.getName(), null,
								Locale.SIMPLIFIED_CHINESE));
						info.setUvidxLv(myUvidxEnum.getLv());
					}

				}
				template = getWeatherTemplate(info, city);
				memcachedClient.set(MemcachedKeyGenerator
						.genZhiquWeatherCacheKey(city, province),
						zhiquWeatherCacheExpireTime, template);
			} catch (Exception e) {
				e.printStackTrace();
				log.error("parse zhiqu weather is error error="
						+ e.getMessage());

			}
		}
		return template;
	}

	private WeatherTemplate getWeatherTemplate(WeatherInfo info, String city) {
		WeatherTemplate template = new WeatherTemplate();
		Calendar calendar = Calendar.getInstance();
		Cts cts = new Cts();
		Ct ct = new Ct();
		Pages pages = new Pages();
		Fhour fhour = new Fhour();
		ForecasthourTemplate forecasthour = new ForecasthourTemplate();
		template.setCts(cts);
		cts.setCt(ct);
		ct.setCity(city);
		ct.setPages(pages);
		pages.setFhour(fhour);
		fhour.setForecasthour(forecasthour);
		List<Hour> hours = new ArrayList<Hour>();
		forecasthour.setHour(hours);
		int i = 1;
		if (info.getForecastHours() != null) {
			for (ForecastHour forecastHour : info.getForecastHours()) {
				Hour hour = new Hour();
				hour.setHtmp(forecastHour.getBaseWeather().getMaxTmp());
				hour.setId(i);
				hour.setLtmp(forecastHour.getBaseWeather().getMinTmp());
				hour.setTimeinterval(forecastHour.getTimeInterval());
				hour.setWd(forecastHour.getBaseWeather().getHwd());
				i++;
				hours.add(hour);
			}
		}
		Main main = new Main();
		Dts dts = new Dts();
		pages.setMain(main);
		main.setDts(dts);
		List<Dt> dtsList = new ArrayList<Dt>();
		dts.setDts(dtsList);
		for (int j = 1; j <= 5; j++) {
			FutureWeather futureWeather = info.getFutureWeathers().get(j);
			Dt dt = new Dt();
			dt.setDow(futureWeather.getWeekday());
			dt.setDate(futureWeather.getDate());
			dt.setHtmp(futureWeather.getBaseWeather().getMaxTmp());
			dt.setHwd(futureWeather.getBaseWeather().getHwd());
			dt.setId(j);
			dt.setLtmp(futureWeather.getBaseWeather().getMinTmp());
			dt.setLwd(futureWeather.getBaseWeather().getLwd());
			dt.setSr(futureWeather.getSunrise());
			dt.setSs(futureWeather.getSunset());
			dt.setWdir(futureWeather.getBaseWeather().getWdir());
			dt.setWl(futureWeather.getBaseWeather().getWl());
			dt.setHwid(InitData.getZhiquSky(dt.getHwd()));
			dt.setLwid(InitData.getZhiquSky(dt.getLwd()));
			dtsList.add(dt);
		}
		// cc
		Cc cc = new Cc();
		main.setCc(cc);
		cc.setWdir(info.getBaseWeather().getWdir());
		cc.setGdt(sdf.format(info.getUpdateTime()));
		cc.setHtmp(info.getBaseWeather().getMaxTmp() + "");
		int myHour = calendar.get(Calendar.HOUR_OF_DAY);
		if (myHour >= 18 || myHour < 6) {
			cc.setWd(info.getBaseWeather().getLwd());
			cc.setWid(InitData.getZhiquSky(info.getBaseWeather().getLwd()) + "");
		} else {
			cc.setWd(info.getBaseWeather().getHwd());
			cc.setWid(InitData.getZhiquSky(info.getBaseWeather().getHwd()) + "");
		}
		cc.setHum(info.getHum() + "");
		cc.setLtmp(info.getBaseWeather().getMinTmp() + "");
		cc.setSr(info.getSunrise());
		cc.setSs(info.getSunset());
		cc.setTmp(info.getNowTmp() + "");
		cc.setUpt(sdf_only_hour.format(info.getUpdateTime()));

		cc.setWl(info.getBaseWeather().getWl());

		// dws
		List<WarningWeather> warningWeathers = info.getWarningWeathers();
		if (org.apache.commons.collections.CollectionUtils
				.isNotEmpty(warningWeathers)) {
			// 知趣天气只有一个预警提醒
			Dws dws = new Dws();
			main.setDws(dws);

			List<Dw> dwList = new ArrayList<Dw>();
			dws.setDw(dwList);
			for (WarningWeather warningWeather : warningWeathers) {
				Dw dw = new Dw();
				dw.setDesc(warningWeather.getDesc());
				dw.setId(warningWeather.getId());
				dw.setInfo(warningWeather.getInfo());
				dw.setPt(sdf_hour.format(warningWeather.getPublishTime()));
				calendar.setTime(warningWeather.getPublishTime());
				calendar.add(Calendar.HOUR_OF_DAY, 6);
				dw.setEt(sdf_hour.format(calendar.getTime()));
				dwList.add(dw);
			}

		}
		if (CollectionUtils.isNotEmpty(info.getFutureWeathers())) {
			// trend
			Trend trend = new Trend();
			pages.setTrend(trend);
			History history = new History();
			trend.setHistory(history);
			Trends trends = new Trends();
			trend.setTrends(trends);
			List<Day> fdays = new ArrayList<Day>();
			List<Day> hdays = new ArrayList<Day>();
			trends.setDay(fdays);
			history.setDay(hdays);
			for (int j = 0; j <= 5; j++) {
				FutureWeather futureWeather = info.getFutureWeathers().get(j);
				if (futureWeather != null) {
					Day day = new Day();
					day.setDow(futureWeather.getWeekday());
					day.setDate(futureWeather.getDate());
					day.setHtmp(futureWeather.getBaseWeather().getMaxTmp());
					day.setHwd(futureWeather.getBaseWeather().getHwd());
					day.setId(j);
					day.setLtmp(futureWeather.getBaseWeather().getMinTmp());
					day.setLwd(futureWeather.getBaseWeather().getLwd());
					day.setHwdir(futureWeather.getBaseWeather().getWdir());
					day.setHwl(futureWeather.getBaseWeather().getWl());
					day.setLwid(InitData.getZhiquSky(day.getLwd()));
					day.setHwid(InitData.getZhiquSky(day.getHwd()));
					day.setLwl(futureWeather.getBaseWeather().getWl());
					day.setLwdir(futureWeather.getBaseWeather().getWdir());
					if (j <= 1) {
						hdays.add(day);
					}
					if (j >= 1) {
						fdays.add(day);
					}
				}
			}
		}
		// air
		Index index = new Index();
		pages.setIndex(index);
		if (info.getAir() != null
				&& StringUtils.hasText(info.getAir().getLevel())) {
			AirTemplate airTemplate = new AirTemplate();
			index.setAir(airTemplate);
			airTemplate.setCityName(city);
			airTemplate.setLv(info.getAir().getLevel());
			airTemplate.setPmtenaqi(info.getAir().getPm10());
			airTemplate.setPmtwoaqi(info.getAir().getPm25());
			airTemplate.setPtime(sdf_hour1.format(info.getUpdateTime()) + ".0");
			String aqigrade = messageSource.getMessage(
					AQIEnum.getAQIEnum(info.getAir().getLevel()).getName(),
					null, Locale.SIMPLIFIED_CHINESE);
			airTemplate.setAqigrade(aqigrade);
			airTemplate.setDesc(aqigrade);
		}
		//
		if (info.getUvidxLv() != null && StringUtils.hasText(info.getUvidx())) {
			Idxs idxs = new Idxs();
			index.setIdxs(idxs);
			List<Idx> idxList = new ArrayList<Idx>();
			idxs.setIdx(idxList);
			Idx idx = new Idx();
			idxList.add(idx);
			idx.setDesc(info.getUvidx());
			idx.setType(12);
			idx.setLv(info.getUvidxLv());
		}
		return template;
	}
}
