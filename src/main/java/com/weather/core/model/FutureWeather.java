package com.weather.core.model;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class FutureWeather {
	private BaseWeather baseWeather;
	private String date;
	private Integer weekday;
	private String sunrise;// 日出时间
	private String sunset;// 日落时间

	public BaseWeather getBaseWeather() {
		return baseWeather;
	}

	public void setBaseWeather(BaseWeather baseWeather) {
		this.baseWeather = baseWeather;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public Integer getWeekday() {
		return weekday;
	}

	public void setWeekday(Integer weekday) {
		this.weekday = weekday;
	}

	public String getSunrise() {
		return sunrise;
	}

	public void setSunrise(String sunrise) {
		this.sunrise = sunrise;
	}

	public String getSunset() {
		return sunset;
	}

	public void setSunset(String sunset) {
		this.sunset = sunset;
	}

}
