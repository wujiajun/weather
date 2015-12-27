package com.weather.data.schedule;

import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Component;

import com.juzhai.core.sechdule.AbstractScheduleHandler;
import com.weather.data.service.IWeatherDataService;
import com.weather.data.service.IWeatherDataUrlService;
import com.weather.data.task.SpiderWeatherDateTask;

@Component
public class SpideWeatherDataHandler extends AbstractScheduleHandler {
	@Autowired
	private IWeatherDataService weatherDataService;
	@Autowired
	private IWeatherDataUrlService weatherDataUrlService;
	@Autowired
	private ThreadPoolTaskExecutor taskExecutor;

	@Override
	protected void doHandle() {
		Set<Integer> cityIds = weatherDataService.needSpiderCity();
		for (Integer cityId : cityIds) {
			// TODO (review) 请求解析天气
			taskExecutor.execute(new SpiderWeatherDateTask(cityId,
					weatherDataService, weatherDataUrlService, cityIds.size()));
		}
	}

}
