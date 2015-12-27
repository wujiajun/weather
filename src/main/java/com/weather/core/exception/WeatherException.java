package com.weather.core.exception;

public class WeatherException extends Exception {

	private static final long serialVersionUID = -9154020128236378600L;

	public static final String SYSTEM_ERROR = "00001";

	public static final String ILLEGAL_OPERATION = "00002";

	public WeatherException(String errorCode, Throwable cause) {
		super(errorCode, cause);
	}

	public WeatherException(String errorCode) {
		super(errorCode);
	}

	public WeatherException() {
		super();
	}

	public String getErrorCode() {
		return super.getMessage();
	}
}
