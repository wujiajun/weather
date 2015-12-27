package com.weather.data.model;

import java.io.Serializable;

public class TrendHour implements Serializable {
	private static final long serialVersionUID = 7860963741091312779L;
	private String sky;
	private int hour;
	private String tmp;
	private String wind;
	private int run;

	public String getSky() {
		return sky;
	}

	public void setSky(String sky) {
		this.sky = sky;
	}

	public int getHour() {
		return hour;
	}

	public void setHour(int hour) {
		this.hour = hour;
	}

	public String getTmp() {
		return tmp;
	}

	public void setTmp(String tmp) {
		this.tmp = tmp;
	}

	public String getWind() {
		return wind;
	}

	public void setWind(String wind) {
		this.wind = wind;
	}

	public int getRun() {
		return run;
	}

	public void setRun(int run) {
		this.run = run;
	}

	@Override
	public String toString() {
		return "TrendHour [sky=" + sky + ", hour=" + hour + ", tmp=" + tmp
				+ ", wind=" + wind + ", run=" + run + "]";
	}

}
