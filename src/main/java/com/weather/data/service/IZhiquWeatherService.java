package com.weather.data.service;

import com.weather.core.xml.bean.WeatherTemplate;
import com.weather.data.exception.WeatherDataException;

public interface IZhiquWeatherService {
	/**
	 * 获取知趣天气数据
	 * 
	 * @param city
	 * @param province
	 * @return
	 */
	WeatherTemplate getSixWeather(String city, String province)
			throws WeatherDataException;
}
