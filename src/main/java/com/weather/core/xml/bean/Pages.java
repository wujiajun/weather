package com.weather.core.xml.bean;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

@XmlType(propOrder = { "fhour", "main", "trend", "index" })
public class Pages implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5787951746097828011L;

	private Fhour fhour;

	private Main main;

	private Trend trend;
	private Index index;

	@XmlElement(name = "page")
	public Fhour getFhour() {
		return fhour;
	}

	public void setFhour(Fhour fhour) {
		this.fhour = fhour;
	}

	@XmlElement(name = "page")
	public Main getMain() {
		return main;
	}

	public void setMain(Main main) {
		this.main = main;
	}

	@XmlElement(name = "page")
	public Trend getTrend() {
		return trend;
	}

	public void setTrend(Trend trend) {
		this.trend = trend;
	}

	@XmlElement(name = "page")
	public Index getIndex() {
		return index;
	}

	public void setIndex(Index index) {
		this.index = index;
	}

}
