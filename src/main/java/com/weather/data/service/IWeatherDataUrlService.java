package com.weather.data.service;

import com.weather.data.exception.WeatherDataException;

public interface IWeatherDataUrlService {

	/**
	 * 自动获取城市
	 * 
	 * @param lastCity
	 * @param lat
	 * @param lng
	 * @return
	 */
	String getAutoLocUrl(String from, double lat, double lng)
			throws WeatherDataException;

	/**
	 * 根据城市名称获取天气数据url
	 * 
	 * @param cityName
	 * @return
	 * @throws WeatherDataException
	 */
	String getWeatherDataUrl(String from, String cityName)
			throws WeatherDataException;

	/**
	 * 根据城市的id获取天气数据url
	 * 
	 * @param cityId
	 * @return
	 * @throws WeatherDataException
	 */
	String getWeatherDataUrl(int cityId) throws WeatherDataException;

}
