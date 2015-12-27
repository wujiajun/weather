package com.weather.data.service;

import java.util.List;
import java.util.Set;

import com.weather.data.controller.form.WeatherDataForm;
import com.weather.data.exception.WeatherDataException;
import com.weather.data.model.TrendDay;
import com.weather.data.model.TrendHour;
import com.weather.passport.model.UserConfig;

public interface IWeatherDataService {

	/**
	 * 需要爬取天气数据的城市列表
	 * 
	 * @return
	 */
	Set<Integer> needSpiderCity();

	/**
	 * 获取爬虫
	 * 
	 * @param form
	 * @throws WeatherDataException
	 */
	void receiverSpiderData(WeatherDataForm form) throws WeatherDataException;

	/**
	 * 获取通知内容
	 * 
	 * @param token
	 * @return
	 */
	String getNoticeContent(String token);

	/**
	 * 获取通知内容
	 * 
	 * @param token
	 * @return
	 */
	String getNoticeContent(UserConfig userConfig);

	List<TrendDay> getTrend(String city) throws WeatherDataException;

	List<TrendHour> getTrendHour(String city) throws WeatherDataException;
}
