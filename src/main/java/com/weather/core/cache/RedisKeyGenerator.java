package com.weather.core.cache;

import java.util.Date;

import com.juzhai.core.cache.KeyGenerator;

public class RedisKeyGenerator extends KeyGenerator {
	/**
	 * 用户注册过的城市
	 * 
	 * @return
	 */
	public static String genRegisteredCityKey() {
		return "registeredCity";
	}

	/**
	 * 通过城市获取设备号
	 * 
	 * @param city
	 * @return
	 */
	// TODO (done) 命名习惯
	public static String genTokenKey(int city) {
		return genKey(city, "token_city");
	}

	/**
	 * 通过token获取用户信息
	 * 
	 * @param token
	 * @return
	 */
	// TODO (done) 命名习惯
	public static String genUserConfigKey(String token) {
		return token + CACHE_KEY_SEPARATOR + "user_info";
	}

	/**
	 * 某天某个城市的天气数据
	 * 
	 * @param date
	 * @return
	 */
	public static String genWeatherDateKey(Date date, int city) {
		return formatKey(date) + CACHE_KEY_SEPARATOR + city
				+ CACHE_KEY_SEPARATOR + "weatherDateKey";
	}

	/**
	 * 此时需要提醒的人
	 * 
	 * @param hour
	 * @return
	 */
	private static String genRemindHourKey(int hour) {
		return genKey(hour, "remindHourKey");
	}

	public static String genRemindHourMinuteKey(int hour, Integer minute) {
		if (minute == null || minute <= 0) {
			return genRemindHourKey(hour);
		} else {
			return genKey(hour, String.valueOf(minute), "remindHourKey");
		}
	}

	/**
	 * 需要提醒的城市列表
	 * 
	 * @return
	 */
	public static String genRemindCitiesKey() {
		return "remindCitiesKey";
	}

	/**
	 * 出行建议的图片地址
	 * 
	 * @param suggestId
	 * @return
	 */
	public static String genOutSuggestImageUrlKey(int suggestId, int gender) {
		return genKey(suggestId, "" + gender, "outSuggestImageUrl");
	}
}
