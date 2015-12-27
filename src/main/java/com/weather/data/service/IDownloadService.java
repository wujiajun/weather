package com.weather.data.service;

import com.weather.core.bean.DeviceName;

public interface IDownloadService {

	/**
	 * 下载地址
	 * 
	 * @param deviceName
	 *            设备名字
	 * @return 下载地址
	 */
	String downloadUrl(DeviceName deviceName);
}
