package com.weather.passport.service;

import com.weather.core.bean.DeviceName;
import com.weather.passport.controller.form.UserForm;
import com.weather.passport.exception.PassportException;
import com.weather.passport.model.UserConfig;

public interface IPassportService {

	/**
	 * 保存用户配置信息
	 * 
	 * @param remind
	 */
	UserConfig saveUserConfig(UserForm form, DeviceName divice)
			throws PassportException;

	/**
	 * 获取userConfig
	 * 
	 * @param token
	 * @return
	 */
	UserConfig getUserConfig(String token);

	/**
	 * 删除用户
	 * 
	 * @param token
	 */
	void removeUser(String token);

}
