package com.weather.core.spider.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.stereotype.Service;

import com.weather.core.spider.service.IParser;

@Service
public abstract class AbstractParser<T> implements IParser<T> {

	protected List<String> findList(String content, String regEx) {
		List<String> list = new ArrayList<String>();
		Pattern pat = Pattern.compile(regEx, Pattern.DOTALL);
		Matcher mat = pat.matcher(content);
		while (mat.find()) {
			list.add(mat.group(1));
		}
		return list;
	}

	protected String find(String content, String regEx) {
		List<String> list = findList(content, regEx);
		return list.size() > 0 ? list.get(0) : null;
	}

}
