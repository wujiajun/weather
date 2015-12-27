package com.weather.data.model;

import java.io.Serializable;

public class RemindCity implements Serializable {
	private static final long serialVersionUID = -7351513819907481989L;
	// TODO (done) 命名
	private boolean remindRain;
	private boolean remindWind;
	private boolean remindCooling;
	private boolean remindHot;
	private boolean remindTempDrop;
	private int city;

	public boolean isRemindRain() {
		return remindRain;
	}

	public void setRemindRain(boolean remindRain) {
		this.remindRain = remindRain;
	}

	public boolean isRemindWind() {
		return remindWind;
	}

	public void setRemindWind(boolean remindWind) {
		this.remindWind = remindWind;
	}

	public boolean isRemindCooling() {
		return remindCooling;
	}

	public void setRemindCooling(boolean remindCooling) {
		this.remindCooling = remindCooling;
	}

	public boolean isRemindHot() {
		return remindHot;
	}

	public void setRemindHot(boolean remindHot) {
		this.remindHot = remindHot;
	}

	public int getCity() {
		return city;
	}

	public void setCity(int city) {
		this.city = city;
	}

	public boolean isRemindTempDrop() {
		return remindTempDrop;
	}

	public void setRemindTempDrop(boolean remindTempDrop) {
		this.remindTempDrop = remindTempDrop;
	}
}
