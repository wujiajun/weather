package com.weather.notice.service.impl;

import java.util.List;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.weather.core.bean.DeviceName;
import com.weather.notice.exception.NoticeException;
import com.weather.notice.service.INoticeService;
import com.weather.passport.model.UserConfig;
import com.weather.passport.service.IPassportService;

@Service
public class NoticeService implements INoticeService, BeanFactoryAware {
	@Autowired
	private BeanFactory beanFactory;
	@Autowired
	private IPassportService passportService;

	@Override
	public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
		this.beanFactory = beanFactory;
	}

	@Override
	public void notice(String token, String text) throws NoticeException {
		UserConfig userConfig = passportService.getUserConfig(token);
		if (null == userConfig) {
			return;
		}
		getNoticeServiceBean(userConfig.getDeviceName()).notice(token, text);
	}

	private INoticeService getNoticeServiceBean(String deviceName) {
		String beanName = deviceName + this.getClass().getSimpleName();
		INoticeService service = (INoticeService) beanFactory.getBean(beanName);
		return service;
	}

	@Override
	public List<String> getInactiveTokens(DeviceName deviceName) {
		return getNoticeServiceBean(deviceName.getName()).getInactiveTokens(
				deviceName);
	}

}
