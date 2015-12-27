package com.weather.core.cache;

import com.juzhai.core.cache.KeyGenerator;

public class MemcachedKeyGenerator extends KeyGenerator {
	public static String genMojiSecretKey() {
		return "mojisecret";
	}

	public static String genZhiquWeatherCacheKey(String city, String prov) {
		return "zhiquWeatherCacheKey_" + city + "_" + prov;
	}

	public static String genTrendWeatherCacheKey(String city) {
		return "trendWeatherCacheKey_" + city;
	}

	public static String genTrendHourWeatherCacheKey(String city) {
		return "trendHourWeatherCacheKey_" + city;
	}
	// /**
	// * 需要提醒的城市列表
	// *
	// * @return
	// */
	// public static String genRemindCityListKey() {
	// return "remindCityListKey";
	// }

}
