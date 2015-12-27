package com.weather.core.xml.bean;

import java.io.Serializable;
import java.util.List;

import javax.xml.bind.annotation.XmlElement;

public class ForecasthourTemplate implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5546322999438991024L;
	private List<Hour> hour;

	@XmlElement
	public List<Hour> getHour() {
		return hour;
	}

	public void setHour(List<Hour> hour) {
		this.hour = hour;
	}

}
