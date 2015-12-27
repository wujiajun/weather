package com.weather.core.bean;

public enum RemindTextTemplate {
	RAIN("remind.rain.text"), WIND("remind.wind.text"), BIG_WIND(
			"remind.wind.big.text"), TMP("remind.tmp.text"), HOT_TMP(
			"remind.hot.tmp.text"), TMP_DROP("remind.tmp.drop.text");

	private String name;

	private RemindTextTemplate(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

}
