package com.weather.data.controller;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.weather.data.bean.Gender;
import com.weather.data.service.IOutSuggestService;

@Controller
@RequestMapping(value = "suggest")
public class OutSuggestController {

	@Value("${outsuggest.other.link}")
	private String otherLink = null;

	@Autowired
	private IOutSuggestService outSuggestService;

	@RequestMapping(value = "/detail", method = RequestMethod.GET)
	public String suggestDetail(HttpServletRequest request, int suggestId,
			Integer gender) {
		Gender genderEnum = Gender.getGender(gender);
		if (genderEnum == null) {
			genderEnum = Gender.GIRL;
		}
		String url = outSuggestService.suggestDetailUrl(suggestId, genderEnum);
		if (StringUtils.isNotEmpty(url)) {
			return "redirect:" + url;
		}
		return null;
	}

	@RequestMapping(value = "/image", method = RequestMethod.GET)
	public String suggestImage(HttpServletRequest request, int suggestId,
			Integer gender) {
		Gender genderEnum = Gender.getGender(gender);
		if (genderEnum == null) {
			genderEnum = Gender.GIRL;
		}
		String url = outSuggestService.suggestImageUrl(suggestId, genderEnum);
		if (StringUtils.isNotEmpty(url)) {
			return "redirect:" + url;
		}
		return null;
	}

	@RequestMapping(value = "/otherLink", method = RequestMethod.GET)
	public String otherLink(HttpServletRequest request) {
		if (StringUtils.isNotEmpty(otherLink)) {
			return "redirect:" + otherLink;
		}
		return null;
	}

	@ResponseBody
	@RequestMapping(value = "/init", method = RequestMethod.GET)
	public String initSuggest(HttpServletRequest request) {
		outSuggestService.initSuggestImageUrl();
		return "success";
	}
}
