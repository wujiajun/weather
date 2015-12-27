package com.weather.core.xml.bean;

import java.io.Serializable;
import java.util.List;

import javax.xml.bind.annotation.XmlElement;

public class History implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -3592848565039090117L;
	private List<Day> day;

	@XmlElement
	public List<Day> getDay() {
		return day;
	}

	public void setDay(List<Day> day) {
		this.day = day;
	}
}
