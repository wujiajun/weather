package com.weather.core.xml.bean;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "wr")
public class WeatherTemplate implements Serializable {

	private static final long serialVersionUID = -8780541131043017377L;
	private Cts cts;

	@XmlElement
	public Cts getCts() {
		return cts;
	}

	public void setCts(Cts cts) {
		this.cts = cts;
	}

}
