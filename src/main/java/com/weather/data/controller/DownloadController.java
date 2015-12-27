package com.weather.data.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.weather.core.bean.DeviceName;
import com.weather.core.util.WeatherUtils;
import com.weather.data.service.IDownloadService;

@Controller
@RequestMapping("download")
public class DownloadController {

	@Autowired
	private IDownloadService downloadService;

	@RequestMapping(value = "", method = RequestMethod.GET)
	public String mobile(HttpServletRequest request,
			HttpServletResponse response, Model model) {
		DeviceName device = WeatherUtils.getClientName(request);
		return "redirect:" + downloadService.downloadUrl(device);
	}
}
