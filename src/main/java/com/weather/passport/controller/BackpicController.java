package com.weather.passport.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

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
import com.juzhai.core.web.response.ListJsonResult;
import com.weather.core.bean.AppImage;
import com.weather.core.bean.DeviceName;
import com.weather.core.util.WeatherUtils;
import com.weather.passport.model.Backpic;

@Controller
@RequestMapping(value = "backpic")
public class BackpicController {
	private final Log log = LogFactory.getLog(getClass());
	@Autowired
	private MessageSource messageSource;

	/**
	 * 背景图列表
	 * 
	 * @param request
	 * @param page
	 * @param sort
	 * @return
	 */
	@RequestMapping(value = "/backpicList", method = RequestMethod.GET)
	@ResponseBody
	public ListJsonResult donateList(HttpServletRequest request,
			@RequestParam(defaultValue = "1") int page) {
		DeviceName device = WeatherUtils.getClientName(request);
		if (device == null) {
			device = DeviceName.IPHONE;
		}
		int results = 10;
		PagerManager pager = new PagerManager(page, results,
				AppImage.values().length);
		List<Backpic> list = new ArrayList<Backpic>();
		int i = 0;
		for (AppImage image : AppImage.values()) {
			if (i >= ((page - 1) * results) && i < (page * results)) {
				Backpic backpic = new Backpic();
				backpic.setName(messageSource.getMessage(image.getName(), null,
						Locale.SIMPLIFIED_CHINESE));
				switch (device) {
				case ANDROID:
					backpic.setPreview(image.getPreview());
					backpic.setDownLoadLink(image.getDownLoadLink());
					break;
				case IPHONE:
					// backpic/ios/
					String preview = image.getPreview();
					int endIndex = preview.lastIndexOf("/");
					preview = preview.substring(0, endIndex) + "/backpic/ios"
							+ preview.substring(endIndex);
					backpic.setPreview(preview);
					backpic.setDownLoadLink(image.getDownLoadLink().replaceAll(
							"appimage", "backpic/ios"));
					break;
				default:
					backpic.setPreview(image.getPreview());
					backpic.setDownLoadLink(image.getDownLoadLink());
					break;

				}
				backpic.setSize(image.getSize());
				backpic.setFilename(image.getFilename());
				list.add(backpic);
			}
			i++;
		}
		ListJsonResult result = new ListJsonResult();
		result.setResult(pager, list);
		return result;
	}
}
