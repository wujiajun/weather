package com.weather.data.service.impl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import com.weather.core.spider.service.impl.AbstractParser;
import com.weather.core.util.HttpUtils;
import com.weather.data.model.TrendDay;

@Service
public class HtqTrendDayParser extends AbstractParser<List<TrendDay>> {
	private SimpleDateFormat sdf = new SimpleDateFormat("MM/dd");
	private SimpleDateFormat zh_sdf_hour = new SimpleDateFormat("MM月dd日");

	@Override
	public List<TrendDay> parser(String content) {
		try {
			List<String> contentList = findList(content,
					"<table class=\"fore-semi.*?>(.*?)</table>");
			List<TrendDay> list = new ArrayList<TrendDay>(contentList.size());
			for (int i = 0; i < contentList.size(); i++) {
				TrendDay trendDay = new TrendDay();
				String str = contentList.get(i);
				String time = find(str,
						"<td class=\"fore-semi-date\".*?>(.*?)<br>");
				trendDay.setDate(sdf.format(zh_sdf_hour.parse(time)));
				trendDay.setMaxTmp(find(str, "color:#ff0000;\">(.*?)</font>"));
				trendDay.setMinTmp(find(str, "color:#008800;\">(.*?)</font>"));
				trendDay.setSky(find(str, "白天&nbsp;(.*?)</font>"));
				String wind = find(str, "℃&nbsp;&nbsp;(.*?)<br>");
				if (StringUtils.isEmpty(wind)) {
					wind = find(str, "湿度.*?&nbsp;&nbsp;(.*?)<br>");
				}
				trendDay.setWind(wind.replaceAll("&nbsp;", " "));
				int run = 0;
				try {
					run = Integer.parseInt(find(str,
							"color:#0000aa;\">(.*?)</font>"));
				} catch (NumberFormatException e) {
				}
				trendDay.setRun(run);
				list.add(trendDay);
			}
			return list;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}

	}

	public static void main(String[] args) {
		HtqTrendDayParser s = new HtqTrendDayParser();
		List<TrendDay> list = s.parser(HttpUtils
				.getContent("http://www.haotq.com/d_shanghai.html"));
	}
}
