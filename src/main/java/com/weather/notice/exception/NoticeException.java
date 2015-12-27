package com.weather.notice.exception;

import com.weather.core.exception.WeatherException;

public class NoticeException extends WeatherException {

	private static final long serialVersionUID = -6247602021192857391L;

	public NoticeException(String errorCode, Throwable cause) {
		super(errorCode, cause);
	}

	public NoticeException(String errorCode) {
		super(errorCode);
	}
}
