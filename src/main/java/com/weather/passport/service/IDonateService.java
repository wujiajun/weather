package com.weather.passport.service;

import java.util.List;

import com.weather.core.bean.DeviceName;
import com.weather.passport.controller.form.DonateForm;
import com.weather.passport.exception.DonateException;
import com.weather.passport.model.Donate;

public interface IDonateService {

	/**
	 * 累加一次捐赠
	 * 
	 * @param donateForm
	 * @param deviceName
	 * @param needValidate
	 * @return
	 * @throws DonateException
	 */
	void addUpDonate(DonateForm donateForm, DeviceName deviceName,
			boolean needValidate) throws DonateException;

	/**
	 * 根据时间倒序捐赠人记录
	 * 
	 * @param firstResult
	 * @param maxResults
	 * @return
	 */
	List<Donate> listDonateOrderByTime(int firstResult, int maxResults);

	/**
	 * 根据总额倒序捐赠人记录
	 * 
	 * @param firstResult
	 * @param maxResults
	 * @return
	 */
	List<Donate> listDonateOrderBySum(int firstResult, int maxResults);

	/**
	 * 捐赠数量
	 * 
	 * @return
	 */
	int countDonate();
}
