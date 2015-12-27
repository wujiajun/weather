package com.juzhai.core.web.session;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface LoginSessionManager {

	/**
	 * 从request里获取UserContext
	 * 
	 * @param request
	 * @return
	 */
	UserContext getUserContextAndUpdateExpire(HttpServletRequest request);

	/**
	 * 登录
	 * 
	 * @param request
	 * @param response
	 * @param uid
	 * @param tpId
	 * @param isAdmin
	 * @param persistent
	 */
	void login(HttpServletRequest request, HttpServletResponse response,
			long uid, long tpId, boolean isAdmin, boolean persistent);

	/**
	 * 登出
	 * 
	 * @param request
	 * @param response
	 */
	void logout(HttpServletRequest request, HttpServletResponse response);

}
