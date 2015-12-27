package com.weather.data.task;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.dom4j.Attribute;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

import com.weather.core.util.HttpUtils;
import com.weather.data.controller.form.WeatherDataForm;
import com.weather.data.exception.WeatherDataException;
import com.weather.data.service.IWeatherDataService;
import com.weather.data.service.IWeatherDataUrlService;

public class SpiderWeatherDateTask implements Runnable {
	private SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
	private final Log log = LogFactory.getLog(SpiderWeatherDateTask.class);
	private int cityId;
	private IWeatherDataService weatherDataService;
	private IWeatherDataUrlService weatherDataUrlService;

	public SpiderWeatherDateTask(int cityId,
			IWeatherDataService weatherDataService,
			IWeatherDataUrlService weatherDataUrlService, int citySize) {
		this.cityId = cityId;
		this.weatherDataService = weatherDataService;
		this.weatherDataUrlService = weatherDataUrlService;
	}

	@Override
	public void run() {
		try {
			WeatherDataForm form = parse(cityId);
			if (form != null) {
				weatherDataService.receiverSpiderData(form);
			}
		} catch (WeatherDataException e) {
			log.error("SpideWeatherDataHandler spider is error errprmsg="
					+ e.getMessage());
		}
	}

	private WeatherDataForm parse(int cityId) throws WeatherDataException {
		WeatherDataForm form = null;
		String url = weatherDataUrlService.getWeatherDataUrl(cityId);
		String content = HttpUtils.toUtf8String(HttpUtils.getContent(url));
		if (StringUtils.isEmpty(content)) {
			return null;
		}
		try {
			Document document = (Document) DocumentHelper.parseText(content);
			Element root = document.getRootElement();
			form = new WeatherDataForm();
			Element cts = root.element("cts");
			Element ct = cts.element("ct");
			Element pages = ct.element("pages");
			for (Iterator iter = pages.elementIterator("page"); iter.hasNext();) {
				Element page = (Element) iter.next();
				if ("trend".equals(page.attributeValue("id"))) {
					Element trends = page.element("trends");
					for (Iterator trendsIter = trends.elementIterator(); trendsIter
							.hasNext();) {
						Element day = (Element) trendsIter.next();
						if (sdf.format(new Date()).equals(
								day.attributeValue("date"))) {
							for (int j = 0; j < day.attributeCount(); j++) {
								Attribute tempAttribute = day.attribute(j);
								String attrName = tempAttribute.getName();
								switch (attrName) {
								case "hwd":
									form.sethWeather(day
											.attributeValue(attrName));
									break;
								case "lwd":
									form.setlWeather(day
											.attributeValue(attrName));
									break;
								case "htmp":
									form.sethTmp(day.attributeValue(attrName));
									break;
								case "ltmp":
									form.setlTmp(day.attributeValue(attrName));
									break;
								case "hwl":
									form.sethWindPower(day
											.attributeValue(attrName));
									break;
								case "lwl":
									form.setlWindPower(day
											.attributeValue(attrName));
									break;
								}
							}
						}
					}
				}
			}
			form.setCity(cityId);
		} catch (DocumentException e) {
			log.error("weather data parse xml is error;" + e.getMessage());
		}
		return form;
	}
}
