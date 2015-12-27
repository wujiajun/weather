package com.weather.alipay.service;

public interface IAlipayService {
	/**
	 * 获取订单信息
	 * 
	 * @param userName
	 * @param price
	 * @param color
	 * @return
	 */
	String getOrderInfo(String tradeNo, String price);
}
