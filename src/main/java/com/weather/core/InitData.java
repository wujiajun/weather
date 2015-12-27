package com.weather.core;

import java.io.IOException;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Properties;

import javax.annotation.PostConstruct;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

@Component("weatherInitData")
@Lazy(false)
public class InitData {
	private static final Log log = LogFactory.getLog(InitData.class);

	public static final Map<String, Integer> CITY_MAP = new HashMap<String, Integer>();
	private static final String FILE_CITY_PATH = "properties/citys.properties";

	public static final Map<Integer, String> CITY_ID_MAP = new HashMap<Integer, String>();
	private static final String FILE_CITY_ID_PATH = "properties/cityids.properties";

	public static final Map<String, String> ZHIQU_CITY_MAP = new HashMap<String, String>();
	private static final String FILE_ZHIQU_CITY_PATH = "properties/zhiqucity.properties";

	public static final Map<String, String> MOJI_MATCH_ZHIQU_CITY_MAP = new HashMap<String, String>();
	private static final String FILE_MOJI_MATCH_ZHIQU_CITY_PATH = "properties/mojiMatchZhiqu.properties";
	// 出行建议图片链接女
	public static final Map<Integer, String> OUT_SUGGEST_URL_MAP = new HashMap<Integer, String>();
	private static final String FILE_OUT_SUGGEST_PATH = "properties/outsuggest.properties";
	// 出行建议图片链接男
	public static final Map<Integer, String> OUT_SUGGEST_BOY_URL_MAP = new HashMap<Integer, String>();
	private static final String FILE_OUT_SUGGEST_BOY_PATH = "properties/outsuggestboy.properties";
	// 淘宝女装
	public static final Map<Integer, String> TAOBAO_OUT_SUGGEST_URL_MAP = new HashMap<Integer, String>();
	private static final String FILE_TAOBAO_OUT_SUGGEST_PATH = "properties/taobaooutsuggest.properties";
	// 淘宝男装
	public static final Map<Integer, String> TAOBAO_BOY_OUT_SUGGEST_URL_MAP = new HashMap<Integer, String>();
	private static final String FILE_TAOBAO_BOY_OUT_SUGGEST_PATH = "properties/taobaooutsuggestboy.properties";

	public static final Map<String, Integer> ZHIQU_SKY_MAP = new HashMap<String, Integer>();
	private static final String ZHIQU_SKY_FILE_CITY_PATH = "properties/zhiquskyid.properties";

	public static final Map<String, Integer> ZHIQU_NIGHT_SKY_MAP = new HashMap<String, Integer>();
	private static final String ZHIQU_NIGHT_SKY_FILE_CITY_PATH = "properties/zhiqunightskyid.properties";

	public static final Map<String, String> HTQ_CITY_PY_MAP = new HashMap<String, String>();
	private static final String HTQ_CITY_PY_MAP_FILE_PATH = "properties/htqcityspy.properties";

	public static final Map<String, String> MOJI_MATCH_HTQ_CITY_MAP = new HashMap<String, String>();
	private static final String FILE_MOJI_MATCH_HTQ_CITY_PATH = "properties/mojimatchhtq.properties";

	@PostConstruct
	public void init() {
		initCity();
		initOutSuggest();
		initTaobaoSuggest();
		initSky();
		initPy();
	}

	private void initPy() {

		Properties config = new Properties();
		try {
			config.load(InitData.class.getClassLoader().getResourceAsStream(
					HTQ_CITY_PY_MAP_FILE_PATH));
		} catch (IOException e) {
			log.error(e.getMessage(), e);
		}
		for (Entry<Object, Object> entry : config.entrySet()) {
			String key = (String) entry.getKey();
			String value = (String) entry.getValue();
			HTQ_CITY_PY_MAP.put(key, value);
		}
	}

	private void initCity() {
		Properties config = new Properties();
		try {
			config.load(InitData.class.getClassLoader().getResourceAsStream(
					FILE_CITY_PATH));
		} catch (IOException e) {
			log.error(e.getMessage(), e);
		}
		for (Entry<Object, Object> entry : config.entrySet()) {
			String key = (String) entry.getKey();
			String value = (String) entry.getValue();
			CITY_MAP.put(key, Integer.parseInt(value));
		}
		// 知趣天气城市
		Properties config1 = new Properties();
		try {
			config1.load(InitData.class.getClassLoader().getResourceAsStream(
					FILE_ZHIQU_CITY_PATH));
		} catch (IOException e) {
			log.error(e.getMessage(), e);
		}
		for (Entry<Object, Object> entry : config1.entrySet()) {
			String key = (String) entry.getKey();
			String value = (String) entry.getValue();
			ZHIQU_CITY_MAP.put(key, value);
		}
		Properties config2 = new Properties();
		try {
			config2.load(InitData.class.getClassLoader().getResourceAsStream(
					FILE_MOJI_MATCH_ZHIQU_CITY_PATH));
		} catch (IOException e) {
			log.error(e.getMessage(), e);
		}
		for (Entry<Object, Object> entry : config2.entrySet()) {
			String key = (String) entry.getKey();
			String value = (String) entry.getValue();
			MOJI_MATCH_ZHIQU_CITY_MAP.put(key, value);
		}

		Properties config3 = new Properties();
		try {
			config3.load(InitData.class.getClassLoader().getResourceAsStream(
					FILE_CITY_ID_PATH));
		} catch (IOException e) {
			log.error(e.getMessage(), e);
		}
		for (Entry<Object, Object> entry : config3.entrySet()) {
			String key = (String) entry.getKey();
			String value = (String) entry.getValue();
			CITY_ID_MAP.put(Integer.parseInt(key), value);
		}

		Properties config4 = new Properties();
		try {
			config4.load(InitData.class.getClassLoader().getResourceAsStream(
					FILE_MOJI_MATCH_HTQ_CITY_PATH));
		} catch (IOException e) {
			log.error(e.getMessage(), e);
		}
		for (Entry<Object, Object> entry : config4.entrySet()) {
			String key = (String) entry.getKey();
			String value = (String) entry.getValue();
			MOJI_MATCH_HTQ_CITY_MAP.put(key, value);
		}
	}

	private void initOutSuggest() {
		Properties config = new Properties();
		try {
			config.load(InitData.class.getClassLoader().getResourceAsStream(
					FILE_OUT_SUGGEST_PATH));
		} catch (IOException e) {
			log.error(e.getMessage(), e);
		}
		for (Entry<Object, Object> entry : config.entrySet()) {
			Integer key = Integer.valueOf((String) entry.getKey());
			String value = (String) entry.getValue();
			OUT_SUGGEST_URL_MAP.put(key, value);
		}

		Properties config1 = new Properties();
		try {
			config1.load(InitData.class.getClassLoader().getResourceAsStream(
					FILE_OUT_SUGGEST_BOY_PATH));
		} catch (IOException e) {
			log.error(e.getMessage(), e);
		}
		for (Entry<Object, Object> entry : config1.entrySet()) {
			Integer key = Integer.valueOf((String) entry.getKey());
			String value = (String) entry.getValue();
			OUT_SUGGEST_BOY_URL_MAP.put(key, value);
		}
	}

	private void initTaobaoSuggest() {
		Properties config = new Properties();
		try {
			config.load(InitData.class.getClassLoader().getResourceAsStream(
					FILE_TAOBAO_OUT_SUGGEST_PATH));
		} catch (IOException e) {
			log.error(e.getMessage(), e);
		}
		for (Entry<Object, Object> entry : config.entrySet()) {
			Integer key = Integer.valueOf((String) entry.getKey());
			String value = (String) entry.getValue();
			TAOBAO_OUT_SUGGEST_URL_MAP.put(key, value);
		}

		Properties config1 = new Properties();
		try {
			config1.load(InitData.class.getClassLoader().getResourceAsStream(
					FILE_TAOBAO_BOY_OUT_SUGGEST_PATH));
		} catch (IOException e) {
			log.error(e.getMessage(), e);
		}
		for (Entry<Object, Object> entry : config1.entrySet()) {
			Integer key = Integer.valueOf((String) entry.getKey());
			String value = (String) entry.getValue();
			TAOBAO_BOY_OUT_SUGGEST_URL_MAP.put(key, value);
		}
	}

	private void initSky() {
		Properties config = new Properties();
		try {
			config.load(InitData.class.getClassLoader().getResourceAsStream(
					ZHIQU_SKY_FILE_CITY_PATH));
		} catch (IOException e) {
			log.error(e.getMessage(), e);
		}
		for (Entry<Object, Object> entry : config.entrySet()) {
			String key = (String) entry.getKey();
			String value = (String) entry.getValue();
			ZHIQU_SKY_MAP.put(key, Integer.parseInt(value));
		}

		Properties config1 = new Properties();
		try {
			config1.load(InitData.class.getClassLoader().getResourceAsStream(
					ZHIQU_NIGHT_SKY_FILE_CITY_PATH));
		} catch (IOException e) {
			log.error(e.getMessage(), e);
		}
		for (Entry<Object, Object> entry : config1.entrySet()) {
			String key = (String) entry.getKey();
			String value = (String) entry.getValue();
			ZHIQU_NIGHT_SKY_MAP.put(key, Integer.parseInt(value));
		}
	}

	public static Integer getZhiquSky(String wd) {
		Calendar calendar = Calendar.getInstance();
		int myHour = calendar.get(Calendar.HOUR_OF_DAY);
		if (myHour >= 18 || myHour < 6) {
			Integer wid = ZHIQU_NIGHT_SKY_MAP.get(wd);
			if (wid != null) {
				return wid;
			}
		}
		Integer wid = ZHIQU_SKY_MAP.get(wd);
		return wid == null ? 2 : ZHIQU_SKY_MAP.get(wd);

	}
}
