package com.weather.data.service.impl;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.weather.core.bean.DeviceName;
import com.weather.data.service.IDownloadService;

@Service
public class DownloadService implements IDownloadService {

	@Value("${download.android.url}")
	private String downloadAndroidUrl;
	@Value("${download.ios.url}")
	private String downloadIosUrl;

	@Override
	public String downloadUrl(DeviceName deviceName) {
		if (null == deviceName) {
			return downloadIosUrl;
		}
		switch (deviceName) {
		case ANDROID:
			return downloadAndroidUrl;
		case IPHONE:
			return downloadIosUrl;
		default:
			return downloadIosUrl;
		}
	}

}
