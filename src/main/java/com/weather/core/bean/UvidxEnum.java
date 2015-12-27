package com.weather.core.bean;

public enum UvidxEnum {
	LV1("weather.uvidx.name.lv1", "weather.uvidx.desc.lv1", 1), LV2(
			"weather.uvidx.name.lv2", "weather.uvidx.desc.lv2", 2), LV3(
			"weather.uvidx.name.lv3", "weather.uvidx.desc.lv3", 3), LV4(
			"weather.uvidx.name.lv4", "weather.uvidx.desc.lv4", 4);
	private String name;
	private String desc;
	private int lv;

	private UvidxEnum(String name, String desc, int lv) {
		this.desc = desc;
		this.name = name;
		this.lv = lv;
	}

	public String getName() {
		return name;
	}

	public String getDesc() {
		return desc;
	}

	public int getLv() {
		return lv;
	}

}
