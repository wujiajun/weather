package com.weather.data.model;

import java.io.Serializable;

public class TrendDay implements Serializable {
	private static final long serialVersionUID = -4178124059349651422L;
	private String sky;
	private String date;
	private String maxTmp;
	private String minTmp;
	private int run;
	private String wind;

	public String getSky() {
		return sky;
	}

	public void setSky(String sky) {
		this.sky = sky;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getMaxTmp() {
		return maxTmp;
	}

	public void setMaxTmp(String maxTmp) {
		this.maxTmp = maxTmp;
	}

	public String getMinTmp() {
		return minTmp;
	}

	public void setMinTmp(String minTmp) {
		this.minTmp = minTmp;
	}

	public int getRun() {
		return run;
	}

	public void setRun(int run) {
		this.run = run;
	}

	public String getWind() {
		return wind;
	}

	public void setWind(String wind) {
		this.wind = wind;
	}

	@Override
	public String toString() {
		return "TrendDay [sky=" + sky + ", date=" + date + ", maxTmp=" + maxTmp
				+ ", minTmp=" + minTmp + ", run=" + run + ", wind=" + wind
				+ "]";
	}

}
