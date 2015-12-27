package com.weather.passport.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.juzhai.core.pager.PagerManager;
import com.juzhai.core.web.response.AjaxResult;
import com.juzhai.core.web.response.ListJsonResult;
import com.weather.core.bean.DeviceName;
import com.weather.core.util.WeatherUtils;
import com.weather.passport.controller.form.DonateForm;
import com.weather.passport.exception.DonateException;
import com.weather.passport.model.Donate;
import com.weather.passport.service.IDonateService;

@Controller
@RequestMapping(value = "donate")
public class DonateController {

	private final Log log = LogFactory.getLog(getClass());

	@Autowired
	private IDonateService donateService;
	@Autowired
	private MessageSource messageSource;

	@RequestMapping(value = "/addDonate", method = RequestMethod.POST)
	@ResponseBody
	public AjaxResult addDonate(HttpServletRequest request, DonateForm form) {
		AjaxResult result = new AjaxResult();
		try {
			DeviceName device = WeatherUtils.getClientName(request);
			donateService.addUpDonate(form, device, true);
			result.setSuccess(true);
		} catch (DonateException e) {
			log.error(e.getMessage(), e);
			result.setError(e.getErrorCode(), messageSource);
		}
		return result;
	}

	@RequestMapping(value = "/donateList", method = RequestMethod.GET)
	@ResponseBody
	public ListJsonResult donateList(HttpServletRequest request,
			@RequestParam(defaultValue = "1") int page,
			@RequestParam(defaultValue = "time") String sort) {
		PagerManager pager = new PagerManager(page, 20,
				donateService.countDonate());
		List<Donate> list = null;
		if ("time".equals(sort)) {
			list = donateService.listDonateOrderByTime(pager.getFirstResult(),
					pager.getMaxResult());
		} else {
			list = donateService.listDonateOrderBySum(pager.getFirstResult(),
					pager.getMaxResult());
		}

		ListJsonResult result = new ListJsonResult();
		result.setResult(pager, list);
		return result;
	}
}
