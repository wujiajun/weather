package com.weather.core.xml.bean;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlElement;

public class Cts implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1115408508862348785L;
	private Ct ct;

	@XmlElement
	public Ct getCt() {
		return ct;
	}

	public void setCt(Ct ct) {
		this.ct = ct;
	}

}
