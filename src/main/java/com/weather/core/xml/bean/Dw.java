package com.weather.core.xml.bean;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlAttribute;

public class Dw implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 9024970275710088474L;
	private int id;
	private String pt;
	private String desc;
	private String info;
	private String et;

	@XmlAttribute
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	@XmlAttribute
	public String getPt() {
		return pt;
	}

	public void setPt(String pt) {
		this.pt = pt;
	}

	@XmlAttribute
	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	@XmlAttribute
	public String getInfo() {
		return info;
	}

	public void setInfo(String info) {
		this.info = info;
	}

	@XmlAttribute
	public String getEt() {
		return et;
	}

	public void setEt(String et) {
		this.et = et;
	}

}
