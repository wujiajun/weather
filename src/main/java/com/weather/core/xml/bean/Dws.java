package com.weather.core.xml.bean;

import java.io.Serializable;
import java.util.List;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;

public class Dws implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -7233074426757342821L;
	private long lupd;
	private List<Dw> dw;

	public Dws() {
		this.lupd = System.currentTimeMillis();
	}

	@XmlAttribute
	public long getLupd() {
		return lupd;
	}

	public void setLupd(long lupd) {
		this.lupd = lupd;
	}

	@XmlElement
	public List<Dw> getDw() {
		return dw;
	}

	public void setDw(List<Dw> dw) {
		this.dw = dw;
	}

}
