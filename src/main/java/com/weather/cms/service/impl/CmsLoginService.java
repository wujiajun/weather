package com.weather.cms.service.impl;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.juzhai.core.web.session.LoginSessionManager;
import com.weather.cms.service.ICmsLoginService;

@Service
public class CmsLoginService implements ICmsLoginService {

	@Autowired
	private LoginSessionManager loginSessionManager;

	@Override
	public void login(HttpServletRequest request, HttpServletResponse response,
			long uid) {
		loginSessionManager.login(request, response, uid, 0, true, false);
	}

}
