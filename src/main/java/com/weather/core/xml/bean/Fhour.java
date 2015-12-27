package com.weather.core.xml.bean;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;

public class Fhour implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2989041554171132816L;

	private String id;

	private ForecasthourTemplate forecasthour;

	public Fhour() {
		this.id = "fhour";
	}

	@XmlAttribute
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	@XmlElement(name = "forecasthour")
	public ForecasthourTemplate getForecasthour() {
		return forecasthour;
	}

	public void setForecasthour(ForecasthourTemplate forecasthour) {
		this.forecasthour = forecasthour;
	}

}
