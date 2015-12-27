package com.weather.data.service;

import com.weather.data.bean.Gender;

public interface IOutSuggestService {

	/**
	 * 获取建议的详情地址
	 * 
	 * @param suggestId
	 * @return
	 */
	String suggestDetailUrl(int suggestId, Gender gender);

	/**
	 * 保存出行建议的最新图片地址
	 * 
	 * @param suggestId
	 * @param imageUrl
	 */
	void saveSuggestImageUrl(int suggestId, String imageUrl, Gender gender);

	/**
	 * 获取出行建议的图片地址
	 * 
	 * @param suggestId
	 * @return
	 */
	String suggestImageUrl(int suggestId, Gender gender);

	/**
	 * 初始化建议出行图片地址
	 */
	void initSuggestImageUrl();
}
