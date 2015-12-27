package com.weather.core.xml.bean;

import java.io.Serializable;
import java.util.List;

import javax.xml.bind.annotation.XmlElement;

public class Dts implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -3858487172335003453L;
	private List<Dt> dts;

	@XmlElement(name = "dt")
	public List<Dt> getDts() {
		return dts;
	}

	public void setDts(List<Dt> dts) {
		this.dts = dts;
	}

}
