package com.weather.core.bean;

public enum RemindTimeTemplate {
	DAY("remind.time.day"), NIGHT("remind.time.night"), MORNING(
			"remind.time.morning");

	private String name;

	private RemindTimeTemplate(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

}
