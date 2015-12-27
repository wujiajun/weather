package com.weather.core.util;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegExUtils {
	public static String find(String content, String regEx) {
		List<String> list = findList(content, regEx);
		return list.size() > 0 ? list.get(0) : null;
	}

	public static List<String> findList(String content, String regEx) {
		List<String> list = new ArrayList<String>();
		Pattern pat = Pattern.compile(regEx);
		Matcher mat = pat.matcher(content);
		while (mat.find()) {
			list.add(mat.group(1));
		}
		return list;
	}
}
