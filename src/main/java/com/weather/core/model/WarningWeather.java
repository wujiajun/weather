package com.weather.core.model;

import java.util.Date;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class WarningWeather {
	private String desc;
	private String info;
	private Integer id;
	private Date publishTime;
	private Date endTime;

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	public String getInfo() {
		return info;
	}

	public void setInfo(String info) {
		this.info = info;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public void reset() {
		this.desc = null;
		this.info = null;
		this.id = null;
		this.publishTime = null;
		this.endTime = null;
	}

	public Date getPublishTime() {
		return publishTime;
	}

	public void setPublishTime(Date publishTime) {
		this.publishTime = publishTime;
	}

	public Date getEndTime() {
		return endTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}

	@Override
	public boolean equals(Object o) {
		WarningWeather tmp = (WarningWeather) o;
		if (this.id == null || tmp.id == null) {
			return false;
		}

		return this.id.intValue() == tmp.id ? true : false;
	}
}
