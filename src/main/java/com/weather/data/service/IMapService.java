package com.weather.data.service;

import com.weather.data.bean.AddressComponent;

public interface IMapService {

	/**
	 * 通过经纬度获取地址
	 * 
	 * @param point
	 * @return
	 */
	AddressComponent geocode(double lat, double lng);
}
