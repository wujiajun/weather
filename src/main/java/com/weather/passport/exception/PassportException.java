package com.weather.passport.exception;

import com.weather.core.exception.WeatherException;

public class PassportException extends WeatherException {
	private static final long serialVersionUID = -6924516126905798608L;

	public PassportException(String errorCode, Throwable cause) {
		super(errorCode, cause);
	}

	public PassportException(String errorCode) {
		super(errorCode);
	}
}
