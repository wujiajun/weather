package com.weather.core.xml.bean;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlAttribute;

public class AirTemplate implements Serializable {
	private static final long serialVersionUID = 5268155085003222893L;
	private String cityName;
	private String lv;
	private String pmtwoaqi;
	private String pmtenaqi;
	private String ptime;
	private String aqigrade;
	private String desc;

	@XmlAttribute
	public String getCityName() {
		return cityName;
	}

	public void setCityName(String cityName) {
		this.cityName = cityName;
	}

	@XmlAttribute
	public String getLv() {
		return lv;
	}

	public void setLv(String lv) {
		this.lv = lv;
	}

	@XmlAttribute
	public String getPmtwoaqi() {
		return pmtwoaqi;
	}

	public void setPmtwoaqi(String pmtwoaqi) {
		this.pmtwoaqi = pmtwoaqi;
	}

	@XmlAttribute
	public String getPmtenaqi() {
		return pmtenaqi;
	}

	public void setPmtenaqi(String pmtenaqi) {
		this.pmtenaqi = pmtenaqi;
	}

	@XmlAttribute
	public String getPtime() {
		return ptime;
	}

	public void setPtime(String ptime) {
		this.ptime = ptime;
	}

	@XmlAttribute
	public String getAqigrade() {
		return aqigrade;
	}

	public void setAqigrade(String aqigrade) {
		this.aqigrade = aqigrade;
	}

	@XmlAttribute
	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

}
