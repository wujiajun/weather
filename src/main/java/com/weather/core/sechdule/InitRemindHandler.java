package com.weather.core.sechdule;

import org.springframework.stereotype.Component;

import com.juzhai.core.sechdule.AbstractScheduleHandler;

@Component
public class InitRemindHandler extends AbstractScheduleHandler {
	// @Autowired
	// private IWeatherDataService weatherDataService;
	// @Autowired
	// private RedisTemplate<String, Object> redisTemplate;
	// @Autowired
	// private RedisTemplate<String, Integer> intRedisTemplate;
	// @Value("${default.remind.end.time}")
	// private int defaultRemindEndTime;

	@Override
	protected void doHandle() {
		// Calendar cal = Calendar.getInstance();
		// int hour = cal.get(Calendar.HOUR_OF_DAY);
		// Set<Integer> citys = intRedisTemplate.opsForSet().members(
		// RedisKeyGenerator.genRegisteredCityKey());
		// // 提示只提示5-14点之间超过14点则不需要计算（凌晨的时候会计算）
		// if (hour < defaultRemindEndTime) {
		// for (int city : citys) {
		// WeatherDataForm data = null;
		// data = (WeatherDataForm) redisTemplate.opsForValue().get(
		// RedisKeyGenerator.genWeatherDateKey(
		// WeatherUtils.getTodayDate(), city));
		// if (data != null) {
		// try {
		// weatherDataService.receiverSpiderData(data);
		// } catch (WeatherDataException e) {
		// log.error("InitRemindHandler is error", e);
		// }
		// }
		// }
		// }
	}
}
