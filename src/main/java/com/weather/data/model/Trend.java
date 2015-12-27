package com.weather.data.model;

import java.io.Serializable;
import java.util.List;

public class Trend implements Serializable {
	private static final long serialVersionUID = 4298984381388194279L;
	private List<TrendDay> days;
	private List<TrendHour> hours;

	public List<TrendDay> getDays() {
		return days;
	}

	public void setDays(List<TrendDay> days) {
		this.days = days;
	}

	public List<TrendHour> getHours() {
		return hours;
	}

	public void setHours(List<TrendHour> hours) {
		this.hours = hours;
	}

}
