package com.weather.passport.controller;

import javapns.Push;
import javapns.communication.exceptions.CommunicationException;
import javapns.communication.exceptions.KeystoreException;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.juzhai.core.web.response.AjaxResult;
import com.weather.core.bean.DeviceName;
import com.weather.core.util.WeatherUtils;
import com.weather.data.service.IWeatherDataService;
import com.weather.data.service.IWeatherDataUrlService;
import com.weather.data.task.SpiderWeatherDateTask;
import com.weather.notice.exception.NoticeException;
import com.weather.notice.service.INoticeService;
import com.weather.notice.service.IosPushInfo;
import com.weather.passport.controller.form.UserForm;
import com.weather.passport.exception.PassportException;
import com.weather.passport.service.IPassportService;

@Controller
@RequestMapping(value = "passport")
public class PassportController {
	private final Log log = LogFactory.getLog(getClass());
	@Autowired
	private IPassportService passportService;
	@Autowired
	private MessageSource messageSource;
	@Autowired
	private INoticeService noticeService;
	@Autowired
	private IWeatherDataService weatherDataService;
	@Autowired
	private IWeatherDataUrlService weatherDataUrlService;
	@Autowired
	private ThreadPoolTaskExecutor taskExecutor;

	// @Autowired
	// private RedisTemplate<String, String> stringRedisTemplate;

	/**
	 * 保存用户设置
	 * 
	 * @param request
	 * @param form
	 * @return
	 */
	@RequestMapping(value = "/saveUserConfig", method = RequestMethod.POST)
	@ResponseBody
	public AjaxResult saveUserConfig(HttpServletRequest request, UserForm form) {
		AjaxResult result = new AjaxResult();
		try {
			DeviceName device = WeatherUtils.getClientName(request);
			result.setResult(passportService.saveUserConfig(form, device));
		} catch (PassportException e) {
			result.setError(e.getMessage(), messageSource);
		}

		// test
		// UserConfig userConfig =
		// passportService.getUserConfig(form.getToken());
		// if (userConfig != null) {
		// System.out.println(userConfig.getHour());
		// System.out.println(userConfig.getMinute());
		// }
		// if (null != userConfig.getHour()) {
		// System.out.println(stringRedisTemplate.opsForZSet().score(
		// RedisKeyGenerator.genRemindHourMinuteKey(
		// userConfig.getHour(), userConfig.getMinute()),
		// userConfig.getToken()));
		// }
		return result;
	}

	@RequestMapping(value = "/testNotice", method = RequestMethod.GET)
	@ResponseBody
	public AjaxResult testNotice(HttpServletRequest request) {
		// dev
		// 5D368B320DE90907E08CC25307BC3407EF2ED676CA185B149C16EDB4CD87CF0F
		// product
		// 0D44069E2D8B529DBCC488744BCB4DBCFCE0C6B60B3FC54086BF647EA610DE5D

		try {
			Push.test(
					IosPushInfo.getKeystore(),
					IosPushInfo.getPassword(),
					IosPushInfo.isProduction(),
					IosPushInfo.isProduction() ? "0D44069E2D8B529DBCC488744BCB4DBCFCE0C6B60B3FC54086BF647EA610DE5D"
							: "5D368B320DE90907E08CC25307BC3407EF2ED676CA185B149C16EDB4CD87CF0F");
		} catch (CommunicationException | KeystoreException e) {
			log.error(e.getMessage(), e);
		}
		return new AjaxResult();
	}

	@RequestMapping(value = "/testQueueNotice", method = RequestMethod.GET)
	@ResponseBody
	public AjaxResult testQueueNotice(HttpServletRequest request) {
		// dev
		// 5D368B320DE90907E08CC25307BC3407EF2ED676CA185B149C16EDB4CD87CF0F
		// product
		// 0D44069E2D8B529DBCC488744BCB4DBCFCE0C6B60B3FC54086BF647EA610DE5D

		try {
			noticeService
					.notice("0D44069E2D8B529DBCC488744BCB4DBCFCE0C6B60B3FC54086BF647EA610DE5D",
							"product test");
		} catch (NoticeException e) {
			log.error(e.getMessage(), e);
		}
		return new AjaxResult();
	}

	@RequestMapping(value = "/testNoticeContent", method = RequestMethod.GET)
	@ResponseBody
	public AjaxResult testSetRemindHot(HttpServletRequest request) {

		AjaxResult result = new AjaxResult();
		// try {
		// UserForm form = new UserForm();
		// form.setCityName("上海");
		// form.setHour("7:00");
		// form.setRemindCooling(true);
		// form.setRemindHot(true);
		// form.setRemindRain(true);
		// form.setRemindWind(true);
		// form.setToken("10019944");
		// passportService.saveUserConfig(form, DeviceName.IPHONE);
		String content = weatherDataService
				.getNoticeContent("4F4EB9A2C331A372B7FBD7D9A19801221505538DB15C42453DA1CFF2A828583C");
		result.setResult(content);
		System.out.println(content);
		// } catch (PassportException e) {
		// result.setError(e.getMessage(), messageSource);
		// }
		return result;
	}

	@RequestMapping(value = "/spideCityWeather", method = RequestMethod.GET)
	@ResponseBody
	public AjaxResult spideCityWeather(HttpServletRequest request, int cityId) {

		AjaxResult result = new AjaxResult();
		if (cityId > 0) {
			taskExecutor.execute(new SpiderWeatherDateTask(cityId,
					weatherDataService, weatherDataUrlService, 0));
		}
		return result;
	}
}
