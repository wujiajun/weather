package com.weather.data.exception;

import com.weather.core.exception.WeatherException;

public class WeatherDataException extends WeatherException {
	private static final long serialVersionUID = -241917621923037712L;
	public static final String TREND_NOT_SUPPORT_CITY = "20001";
	public static final String TREND_REND_TIME_OUT = "20002";

	public WeatherDataException(String errorCode, Throwable cause) {
		super(errorCode, cause);
	}

	public WeatherDataException(String errorCode) {
		super(errorCode);
	}
}
