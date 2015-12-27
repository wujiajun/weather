package com.weather.passport.model;

import java.io.Serializable;

public class UserConfig implements Serializable {
	private static final long serialVersionUID = 5183958287444232096L;
	private String token;
	private String cityName;
	private int city;
	// 设备类型
	private String deviceName;
	private boolean remindRain;
	private boolean remindWind;
	private boolean remindCooling;
	private boolean remindHot;
	private boolean remindTempDrop;
	private Integer hour;
	private Integer minute;

	public int getCity() {
		return city;
	}

	public void setCity(int city) {
		this.city = city;
	}

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

	public Integer getHour() {
		return hour;
	}

	public void setHour(Integer hour) {
		this.hour = hour;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public String getCityName() {
		return cityName;
	}

	public void setCityName(String cityName) {
		this.cityName = cityName;
	}

	public String getDeviceName() {
		return deviceName;
	}

	public void setDeviceName(String deviceName) {
		this.deviceName = deviceName;
	}

	public boolean isRemindHot() {
		return remindHot;
	}

	public void setRemindHot(boolean remindHot) {
		this.remindHot = remindHot;
	}

	public Integer getMinute() {
		return minute;
	}

	public void setMinute(Integer minute) {
		this.minute = minute;
	}

	public boolean isRemindTempDrop() {
		return remindTempDrop;
	}

	public void setRemindTempDrop(boolean remindTempDrop) {
		this.remindTempDrop = remindTempDrop;
	}
}
