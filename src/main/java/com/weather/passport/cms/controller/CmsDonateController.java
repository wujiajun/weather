package com.weather.passport.cms.controller;

import java.util.List;
import java.util.Locale;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.juzhai.core.pager.PagerManager;
import com.weather.core.bean.DeviceName;
import com.weather.passport.controller.form.DonateForm;
import com.weather.passport.exception.DonateException;
import com.weather.passport.model.Donate;
import com.weather.passport.service.IDonateService;

@Controller
@RequestMapping(value = "cms")
public class CmsDonateController {

	@Autowired
	private IDonateService donateService;
	@Autowired
	private MessageSource messageSource;

	@RequestMapping(value = "/listDonate", method = RequestMethod.GET)
	public String listDonate(HttpServletRequest request, Model model,
			@RequestParam(defaultValue = "1") int page) {
		PagerManager pager = new PagerManager(page, 20,
				donateService.countDonate());
		List<Donate> list = donateService.listDonateOrderByTime(
				pager.getFirstResult(), pager.getMaxResult());
		model.addAttribute("donateList", list);
		model.addAttribute("pager", pager);
		return "cms/donate/list";
	}

	@RequestMapping(value = "/prepareCreateDonate", method = RequestMethod.GET)
	public String prepareCreateDonate(HttpServletRequest request, Model model,
			boolean success) {
		model.addAttribute("orderId", UUID.randomUUID().toString());
		model.addAttribute("success", success);
		return "cms/donate/prepare_create_donate";
	}

	@RequestMapping(value = "/createDonate", method = RequestMethod.POST)
	public String createDonate(HttpServletRequest request, Model model,
			DonateForm donateForm) {
		try {
			DeviceName deviceName = DeviceName.getDeviceName(donateForm
					.getOrigin());
			if (deviceName != null) {
				donateService.addUpDonate(donateForm, deviceName, false);
			}
			return "redirect:prepareCreateDonate?success=true";
		} catch (DonateException e) {
			model.addAttribute("errorCode", e.getErrorCode());
			model.addAttribute("error", messageSource.getMessage(
					e.getErrorCode(), null, Locale.SIMPLIFIED_CHINESE));
			model.addAttribute("donateForm", donateForm);
			return prepareCreateDonate(request, model, false);
		}
	}
}
