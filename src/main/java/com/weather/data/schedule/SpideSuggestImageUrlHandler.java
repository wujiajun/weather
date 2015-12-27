package com.weather.data.schedule;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.juzhai.core.sechdule.AbstractScheduleHandler;
import com.weather.data.service.IOutSuggestService;
import com.weather.data.service.impl.WeatherDataService;

@Component
public class SpideSuggestImageUrlHandler extends AbstractScheduleHandler {
	private final Log log = LogFactory.getLog(WeatherDataService.class);
	@Autowired
	private IOutSuggestService outSuggestService;

	@Override
	protected void doHandle() {
		try {
			outSuggestService.initSuggestImageUrl();
		} catch (Exception e) {
			log.error("SpideSuggestImageUrlHandler is error" + e.getMessage());
		}

	}

}
