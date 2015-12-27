package com.weather.passport.exception;

import com.weather.core.exception.WeatherException;

public class DonateException extends WeatherException {
	private static final long serialVersionUID = -6924516126905798608L;

	public static final String DONATE_NAME_ERROR = "10001";

	public static final String DONATE_CITY_ERROR = "10002";

	public DonateException(String errorCode, Throwable cause) {
		super(errorCode, cause);
	}

	public DonateException(String errorCode) {
		super(errorCode);
	}
}
