package com.weather.core.model;

public class ForecastHour {
	private BaseWeather baseWeather;
	private int id;// 可以用于时间段排序的 id从小到大
	private String timeInterval;// 时间段 12:00-18:00

	// private String windDirection;// 风向
	// private String windPower;// 风力

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getTimeInterval() {
		return timeInterval;
	}

	public void setTimeInterval(String timeInterval) {
		this.timeInterval = timeInterval;
	}

	public BaseWeather getBaseWeather() {
		return baseWeather;
	}

	public void setBaseWeather(BaseWeather baseWeather) {
		this.baseWeather = baseWeather;
	}

}
