package com.weather.core.xml.bean;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlAttribute;

public class Hour implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3373822194404374115L;

	private String wd;

	private int ltmp;

	private int htmp;

	private int wid;

	private String timeinterval;

	private int id;

	@XmlAttribute
	public String getWd() {
		return wd;
	}

	public void setWd(String wd) {
		this.wd = wd;
	}

	@XmlAttribute
	public int getLtmp() {
		return ltmp;
	}

	public void setLtmp(int ltmp) {
		this.ltmp = ltmp;
	}

	@XmlAttribute
	public int getHtmp() {
		return htmp;
	}

	public void setHtmp(int htmp) {
		this.htmp = htmp;
	}

	@XmlAttribute
	public int getWid() {
		return wid;
	}

	public void setWid(int wid) {
		this.wid = wid;
	}

	@XmlAttribute
	public String getTimeinterval() {
		return timeinterval;
	}

	public void setTimeinterval(String timeinterval) {
		this.timeinterval = timeinterval;
	}

	@XmlAttribute
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

}
