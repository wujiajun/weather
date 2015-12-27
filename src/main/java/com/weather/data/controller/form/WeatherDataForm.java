package com.weather.data.controller.form;

import java.io.Serializable;

public class WeatherDataForm implements Serializable {

	private static final long serialVersionUID = -2458646977467986234L;
	private String hWeather;
	private String lWeather;
	private String hWindPower;
	private String lWindPower;
	private String hTmp;
	private String lTmp;
	private int city;

	public String gethTmp() {
		return hTmp;
	}

	public void sethTmp(String hTmp) {
		this.hTmp = hTmp;
	}

	public String getlTmp() {
		return lTmp;
	}

	public void setlTmp(String lTmp) {
		this.lTmp = lTmp;
	}

	public String gethWindPower() {
		return hWindPower;
	}

	public void sethWindPower(String hWindPower) {
		this.hWindPower = hWindPower;
	}

	public String getlWindPower() {
		return lWindPower;
	}

	public void setlWindPower(String lWindPower) {
		this.lWindPower = lWindPower;
	}

	public String gethWeather() {
		return hWeather;
	}

	public void sethWeather(String hWeather) {
		this.hWeather = hWeather;
	}

	public String getlWeather() {
		return lWeather;
	}

	public void setlWeather(String lWeather) {
		this.lWeather = lWeather;
	}

	public int getCity() {
		return city;
	}

	public void setCity(int city) {
		this.city = city;
	}

	@Override
	public String toString() {
		return "WeatherDataForm [hWeather=" + hWeather + ", lWeather="
				+ lWeather + ", hWindPower=" + hWindPower + ", lWindPower="
				+ lWindPower + ", hTmp=" + hTmp + ", lTmp=" + lTmp + ", city="
				+ city + "]";
	}

}
