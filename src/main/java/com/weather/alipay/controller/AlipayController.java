package com.weather.alipay.controller;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.juzhai.core.web.response.AjaxResult;
import com.weather.alipay.service.IAlipayService;

@Controller
@RequestMapping(value = "alipay")
public class AlipayController {
	private final Log log = LogFactory.getLog(getClass());

	@Autowired
	private IAlipayService alipayService;

	@RequestMapping(value = "/getOrderInfo", method = RequestMethod.GET)
	@ResponseBody
	public AjaxResult getOrderInfo(HttpServletRequest request, String tradeNo,
			String price) {
		AjaxResult result = new AjaxResult();
		try {
			result.setResult(alipayService.getOrderInfo(tradeNo, price));
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			result.setResult(false);
		}
		return result;
	}
}
