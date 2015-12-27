package com.weather.core.xml.bean;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlAttribute;

public class Idx implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 5766697460086721938L;
	private int type;
	private int lv;
	private String desc;

	@XmlAttribute
	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	@XmlAttribute
	public int getLv() {
		return lv;
	}

	public void setLv(int lv) {
		this.lv = lv;
	}

	@XmlAttribute
	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

}
