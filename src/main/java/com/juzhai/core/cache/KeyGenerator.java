package com.juzhai.core.cache;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.lang.StringUtils;

public abstract class KeyGenerator {

	public static final String CACHE_KEY_SEPARATOR = ".";
	protected final static SimpleDateFormat sdf = new SimpleDateFormat("yyMMdd");

	protected static String genKey(long primaryKey, String... funcs) {
		StringBuilder sb = new StringBuilder();
		sb.append(primaryKey);
		for (String func : funcs) {
			if (StringUtils.isNotEmpty(func)) {
				sb.append(CACHE_KEY_SEPARATOR).append(func);
			}
		}
		return sb.toString();
	}

	protected static String formatKey(Date date) {
		return sdf.format(date);
	}

}