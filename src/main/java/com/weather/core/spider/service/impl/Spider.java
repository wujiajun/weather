package com.weather.core.spider.service.impl;

import org.springframework.stereotype.Service;

import com.weather.core.spider.service.ISpider;
import com.weather.core.util.HttpUtils;

@Service
public class Spider implements ISpider {

	@Override
	public String spide(String link) {
		return HttpUtils.getContent(link);
	}

}
