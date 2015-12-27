package com.weather.core.bean;

public enum AQIEnum {
	LV1("weather.aqi.lv1", 1), LV2("weather.aqi.lv2", 2), LV3(
			"weather.aqi.lv3", 3), LV4("weather.aqi.lv4", 4), LV5(
			"weather.aqi.lv5", 5), LV6("weather.aqi.lv6", 6);
	private String name;
	private int lv;

	private AQIEnum(String name, int lv) {
		this.name = name;
		this.lv = lv;
	}

	public static AQIEnum getAQIEnum(String levelStr) {
		// 0-50 优50-100良 100-150 轻度污染 150-200中毒污染 200-300重度污染 300>严重污染
		int pm25 = 0;
		try {
			int level = Integer.parseInt(levelStr);
			if (level >= 50 && level < 100) {
				pm25 = 2;
			} else if (level >= 100 && level < 150) {
				pm25 = 3;
			} else if (level >= 150 && level < 200) {
				pm25 = 4;
			} else if (level >= 200 && level < 300) {
				pm25 = 5;
			} else if (level >= 300) {
				pm25 = 6;
			} else {
				pm25 = 1;
			}
		} catch (Exception e) {
		}
		for (AQIEnum aqi : values()) {
			if (aqi.getLv() == pm25) {
				return aqi;
			}
		}
		return null;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getLv() {
		return lv;
	}

	public void setLv(int lv) {
		this.lv = lv;
	}

}
