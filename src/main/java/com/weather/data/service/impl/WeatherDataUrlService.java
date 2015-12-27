package com.weather.data.service.impl;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.weather.core.Constants;
import com.weather.core.bean.DataSource;
import com.weather.data.exception.WeatherDataException;
import com.weather.data.service.IWeatherDataUrlService;

@Service
public class WeatherDataUrlService implements IWeatherDataUrlService,
		BeanFactoryAware {
	@Autowired
	private BeanFactory beanFactory;
	@Autowired
	private static final Log log = LogFactory
			.getLog(WeatherDataUrlService.class);

	@Override
	public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
		this.beanFactory = beanFactory;
	}

	private IWeatherDataUrlService getServiceBean(String tpName) {
		DataSource source = DataSource.getDataSource(tpName);
		if (source == null) {
			tpName = Constants.DATA_SOURCE;
		}
		String beanName = tpName + this.getClass().getSimpleName();
		IWeatherDataUrlService mapService = (IWeatherDataUrlService) beanFactory
				.getBean(beanName);
		if (log.isDebugEnabled()) {
			log.debug("get bean" + beanName + ".");
		}
		return mapService;
	}

	@Override
	public String getAutoLocUrl(String from, double lat, double lng)
			throws WeatherDataException {
		if (StringUtils.isEmpty(from)) {
			from = Constants.DATA_SOURCE;
		}
		return getServiceBean(from).getAutoLocUrl(from, lat, lng);
	}

	@Override
	public String getWeatherDataUrl(String from, String cityName)
			throws WeatherDataException {
		if (StringUtils.isEmpty(from)) {
			from = Constants.DATA_SOURCE;
		}
		return getServiceBean(from).getWeatherDataUrl(from, cityName);
	}

	@Override
	public String getWeatherDataUrl(int cityId) throws WeatherDataException {
		return getServiceBean(Constants.DATA_SOURCE).getWeatherDataUrl(cityId);
	}
}
