package com.weather.notice.service;

import java.util.List;

import com.weather.core.bean.DeviceName;
import com.weather.notice.exception.NoticeException;

public interface INoticeService {
	/**
	 * 发送消息
	 * 
	 * @param token
	 * @param text
	 * @throws NoticeException
	 */
	void notice(String token, String text) throws NoticeException;

	/**
	 * 获取需要删除的token
	 * 
	 * @return
	 */
	List<String> getInactiveTokens(DeviceName deviceName);
}
