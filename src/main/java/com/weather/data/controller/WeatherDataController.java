package com.weather.data.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.juzhai.core.web.response.AjaxResult;
import com.weather.core.xml.bean.WeatherTemplate;
import com.weather.data.controller.form.WeatherDataForm;
import com.weather.data.exception.WeatherDataException;
import com.weather.data.service.IMapService;
import com.weather.data.service.IWeatherDataService;
import com.weather.data.service.IWeatherDataUrlService;
import com.weather.data.service.IZhiquWeatherService;

@Controller
@RequestMapping(value = "data")
public class WeatherDataController {
	@Autowired
	private IWeatherDataService weatherDataService;
	@Autowired
	private IWeatherDataUrlService weatherDataUrlService;
	@Autowired
	private MessageSource messageSource;
	@Autowired
	private IMapService baiduMapService;
	@Autowired
	private IZhiquWeatherService zhiquWeatherService;
	@Value("${weather.hl.link}")
	private String hl = null;

	/**
	 * 获取请求定位的地址
	 * 
	 * @param request
	 * @param lat
	 * @param lng
	 * @return
	 */
	@RequestMapping(value = "/autoLocation", method = RequestMethod.GET)
	@ResponseBody
	public AjaxResult autoLocation(HttpServletRequest request, double lat,
			double lng, String source) {
		AjaxResult result = new AjaxResult();
		try {
			result.setResult(weatherDataUrlService.getAutoLocUrl(source, lat,
					lng));
		} catch (WeatherDataException e) {
		}
		return result;
	}

	/**
	 * 获取请求天气的地址
	 * 
	 * @param request
	 * @param cityName
	 * @return
	 */
	@RequestMapping(value = "/getHttpUrl", method = RequestMethod.GET)
	@ResponseBody
	public AjaxResult getUrl(HttpServletRequest request, String cityName,
			String source) {
		AjaxResult result = new AjaxResult();
		try {
			result.setResult(weatherDataUrlService.getWeatherDataUrl(source,
					cityName));
		} catch (WeatherDataException e) {
			result.setError(e.getMessage(), messageSource);
		}
		return result;
	}

	/**
	 * 接收远程爬取的数据
	 * 
	 * @param form
	 * @param count
	 * @return
	 */
	@RequestMapping(value = "/receiverSpider", method = RequestMethod.GET)
	@ResponseBody
	public AjaxResult getUrl(WeatherDataForm form) {
		AjaxResult result = new AjaxResult();
		try {
			weatherDataService.receiverSpiderData(form);
		} catch (WeatherDataException e) {
			result.setError(e.getMessage(), messageSource);
		}
		return result;
	}

	/**
	 * 请求需要爬取的城市
	 * 
	 * @return
	 */
	@RequestMapping(value = "/needSpiderCity", method = RequestMethod.GET)
	@ResponseBody
	public AjaxResult needSpiderCity() {
		AjaxResult result = new AjaxResult();
		try {
			result.setResult(weatherDataService.needSpiderCity());
		} catch (Exception e) {
			result.setSuccess(false);
		}
		return result;
	}

	@RequestMapping(value = "/getSixWeather", method = RequestMethod.GET)
	@ResponseBody
	public WeatherTemplate getSixWeather(String city, String province,
			HttpServletResponse response, HttpServletRequest request) {
		try {
			return zhiquWeatherService.getSixWeather(city, province);
		} catch (WeatherDataException e) {
			return new WeatherTemplate();
		}
	}

	@RequestMapping(value = "/hl", method = RequestMethod.GET)
	public String otherLink(HttpServletRequest request) {
		if (StringUtils.isNotEmpty(hl)) {
			return "redirect:" + hl;
		}
		return null;
	}

	@RequestMapping(value = "/trend", method = RequestMethod.GET)
	@ResponseBody
	public AjaxResult trend(HttpServletRequest request, String cityName) {
		AjaxResult result = new AjaxResult();
		try {
			result.setResult(weatherDataService.getTrend(cityName));
		} catch (WeatherDataException e) {
			result.setError(e.getMessage(), messageSource);
		}
		return result;
	}

	@RequestMapping(value = "/trendHour", method = RequestMethod.GET)
	@ResponseBody
	public AjaxResult trendHour(HttpServletRequest request, String cityName) {
		AjaxResult result = new AjaxResult();
		try {
			result.setResult(weatherDataService.getTrendHour(cityName));
		} catch (WeatherDataException e) {
			result.setError(e.getMessage(), messageSource);
		}
		return result;
	}

}
