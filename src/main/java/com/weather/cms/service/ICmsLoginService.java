package com.weather.cms.service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface ICmsLoginService {

	/**
	 * cms登录
	 * 
	 * @param request
	 * @param response
	 * @param uid
	 */
	void login(HttpServletRequest request, HttpServletResponse response,
			long uid);
}
