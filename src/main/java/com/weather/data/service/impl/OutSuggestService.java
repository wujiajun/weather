package com.weather.data.service.impl;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import com.weather.core.InitData;
import com.weather.core.cache.RedisKeyGenerator;
import com.weather.core.util.HttpUtils;
import com.weather.core.util.RegExUtils;
import com.weather.data.bean.Gender;
import com.weather.data.service.IOutSuggestService;

@Service
public class OutSuggestService implements IOutSuggestService {
	private final String IMAGEURL_REGEX = "pic_url\":\"(.*?)\"";
	private static final String IMAGEURL_BOY_REGEX = "srp_img_error.*?src=\"(.*?)\"";
	@Autowired
	private RedisTemplate<String, String> redisTemplate;
	@Value("${weather.image.masks}")
	private String masks;
	@Value("${weather.image.sweater}")
	private String sweater;
	@Value("${weather.image.umbrella}")
	private String umbrella;
	@Value("${weather.image.boy.masks}")
	private String masksBoy;
	@Value("${weather.image.boy.umbrella}")
	private String umbrellaBoy;

	@Override
	public String suggestDetailUrl(int suggestId, Gender gender) {
		String url = "";
		switch (gender) {
		case BOY:
			url = InitData.TAOBAO_BOY_OUT_SUGGEST_URL_MAP.get(suggestId);
			break;
		case GIRL:
			url = InitData.TAOBAO_OUT_SUGGEST_URL_MAP.get(suggestId);
			break;
		}
		return url;

	}

	@Override
	public void saveSuggestImageUrl(int suggestId, String imageUrl,
			Gender gender) {
		if (suggestId <= 0 || StringUtils.isEmpty(imageUrl)) {
			return;
		}
		// TODO 用redis存
		redisTemplate.opsForValue().set(
				RedisKeyGenerator.genOutSuggestImageUrlKey(suggestId,
						gender.getType()), imageUrl);

	}

	@Override
	public String suggestImageUrl(int suggestId, Gender gender) {
		if (suggestId <= 0) {
			return null;
		}
		String url;
		switch (suggestId) {
		case 1:
			if (gender.getType() == Gender.GIRL.getType()) {
				url = umbrella;
			} else {
				url = umbrellaBoy;
			}
			break;
		case 2:
			if (gender.getType() == Gender.GIRL.getType()) {
				url = masks;
			} else {
				url = masksBoy;
			}
			break;
		case 6:
			if (gender.getType() == Gender.GIRL.getType()) {
				url = sweater;
			} else {
				url = redisTemplate.opsForValue().get(
						RedisKeyGenerator.genOutSuggestImageUrlKey(suggestId,
								gender.getType()));
			}
			break;
		default:
			url = redisTemplate.opsForValue().get(
					RedisKeyGenerator.genOutSuggestImageUrlKey(suggestId,
							gender.getType()));
			break;
		}
		return url;
	}

	@Override
	public void initSuggestImageUrl() {
		for (int suggestId : InitData.OUT_SUGGEST_URL_MAP.keySet()) {
			String content = HttpUtils.getContent(InitData.OUT_SUGGEST_URL_MAP
					.get(suggestId));
			String imageUrl = RegExUtils.find(content, IMAGEURL_REGEX);
			if (StringUtils.isNotEmpty(imageUrl)) {
				saveSuggestImageUrl(suggestId, imageUrl, Gender.GIRL);
			}
		}

		for (int suggestId : InitData.OUT_SUGGEST_BOY_URL_MAP.keySet()) {
			String content = HttpUtils
					.getContent(InitData.OUT_SUGGEST_BOY_URL_MAP.get(suggestId));
			String imageUrl = RegExUtils.find(content, IMAGEURL_BOY_REGEX);
			if (StringUtils.isNotEmpty(imageUrl)) {
				saveSuggestImageUrl(suggestId, imageUrl, Gender.BOY);
			}
		}

	}

}
