package com.weather.data.service.impl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Service;

import com.weather.core.spider.service.impl.AbstractParser;
import com.weather.core.util.HttpUtils;
import com.weather.data.model.TrendHour;

@Service
public class HtqTrendHourParser extends AbstractParser<List<TrendHour>> {
	private SimpleDateFormat zh_sdf_hour = new SimpleDateFormat("MM月dd日HH点");

	@Override
	public List<TrendHour> parser(String content) {
		try {
			List<String> contentList = findList(content,
					"<tr align=\"center\">(.*?)</tr>");
			List<TrendHour> list = new ArrayList<TrendHour>(contentList.size());
			for (int i = 0; i < contentList.size(); i++) {
				TrendHour trendHour = new TrendHour();
				String str = contentList.get(i);
				Date date = zh_sdf_hour.parse(find(str, "<b>(.*?)</b>"));
				trendHour.setRun(Integer.parseInt(find(str,
						"color:#0000aa;\">(.*?)</font>")));
				String wind = find(str, "<td width=\"152\".*?>(.*?)</td>")
						.replaceAll("&nbsp;", " ");
				trendHour.setWind(wind.substring(0, wind.indexOf("级") + 1));
				trendHour.setHour(date.getHours());
				trendHour.setSky(find(str, "<td width=\"93\".*?>(.*?)</td>"));
				trendHour.setTmp(find(str, "color:#ff0000;\">(.*?)</font>"));
				list.add(trendHour);
			}
			return list;
		} catch (Exception e) {
			return null;
		}

	}

	public static void main(String[] args) {
		HtqTrendHourParser s = new HtqTrendHourParser();
		List<TrendHour> list = s.parser(HttpUtils
				.getContent("http://www.haotq.com/h_shanghai.html"));
		int i = 0;
		for (TrendHour hour : list) {
			System.out.println(hour + "=" + (i++));
		}
	}
}
