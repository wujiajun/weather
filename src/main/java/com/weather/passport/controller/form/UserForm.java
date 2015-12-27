package com.weather.passport.controller.form;

public class UserForm {
	private String token;
	private String cityName;

	private boolean remindRain;
	private boolean remindWind;
	private boolean remindCooling;
	private boolean remindHot;
	private boolean remindTempDrop;
	private String hour;

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
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

	public String getHour() {
		return hour;
	}

	public void setHour(String hour) {
		this.hour = hour;
	}

	public String getCityName() {
		return cityName;
	}

	public void setCityName(String cityName) {
		this.cityName = cityName;
	}

	public boolean isRemindHot() {
		return remindHot;
	}

	public void setRemindHot(boolean remindHot) {
		this.remindHot = remindHot;
	}

	public boolean isRemindTempDrop() {
		return remindTempDrop;
	}

	public void setRemindTempDrop(boolean remindTempDrop) {
		this.remindTempDrop = remindTempDrop;
	}

}
