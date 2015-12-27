package com.weather.core.xml.bean;

import java.io.Serializable;
import java.util.List;

import javax.xml.bind.annotation.XmlElement;

public class Trends implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -3853208062749052250L;
	private List<Day> day;

	@XmlElement
	public List<Day> getDay() {
		return day;
	}

	public void setDay(List<Day> day) {
		this.day = day;
	}

}
