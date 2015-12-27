package com.weather.data.service.impl;

import java.util.concurrent.TimeoutException;

import net.rubyeye.xmemcached.MemcachedClient;
import net.rubyeye.xmemcached.exception.MemcachedException;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.weather.core.InitData;
import com.weather.core.cache.MemcachedKeyGenerator;
import com.weather.core.util.WeatherUtils;
import com.weather.data.exception.WeatherDataException;
import com.weather.data.service.IWeatherDataUrlService;

@Service
public class MojiWeatherDataUrlService implements IWeatherDataUrlService {
	private final Log log = LogFactory.getLog(MojiWeatherDataUrlService.class);
	@Value("${spider.website.domain}")
	private String domain;
	@Value("${spider.website.lbs.domain}")
	private String lbsDomain;
	@Value("${spider.website.domain}")
	private String defaultSecret;
	@Value("${spider.website.android.sdk}")
	private String androidSDK;
	@Value("${spider.website.partnerid}")
	private String partnerid;
	@Value("${spider.website.imei}")
	private String imei;
	@Value("${spider.website.version}")
	private String version;
	@Value("${spider.website.baseosver}")
	private String baseosver;
	@Value("${spider.website.userid}")
	private String userid;
	@Value("${secret.cache.expire.time}")
	private int secretCacheExpireTime;
	@Autowired
	private MemcachedClient memcachedClient;

	@Override
	public String getWeatherDataUrl(String from, String cityName)
			throws WeatherDataException {
		Integer cityId = InitData.CITY_MAP.get(cityName);
		if (cityId == null) {
			throw new WeatherDataException(
					WeatherDataException.ILLEGAL_OPERATION);
		}

		return getWeatherDataUrl(cityId);
	}

	@Override
	public String getAutoLocUrl(String from, double lat, double lng) {
		return (new StringBuilder()).append(lbsDomain).append("weather/")
				.append("GetCDNWeatherByLocation").append(getCDNParameter(0))
				.append("&cdma_lat=").append(lat).append("&cdma_lng=")
				.append(lng).toString();
	}

	@Override
	public String getWeatherDataUrl(int city) throws WeatherDataException {
		if (city <= 0) {
			return null;
		}
		String secret = null;
		try {
			secret = memcachedClient.get(MemcachedKeyGenerator
					.genMojiSecretKey());
		} catch (TimeoutException | InterruptedException | MemcachedException e) {
			log.error(e.getMessage(), e);
		}
		if (StringUtils.isEmpty(secret)) {
			secret = WeatherUtils.getCDNSecret();
			if (StringUtils.isEmpty(secret)) {
				secret = defaultSecret;
			}
			try {
				memcachedClient.set(MemcachedKeyGenerator.genMojiSecretKey(),
						secretCacheExpireTime, secret);
			} catch (TimeoutException | InterruptedException
					| MemcachedException e) {
				log.error(e.getMessage(), e);
			}
		}
		if (StringUtils.isEmpty(secret)) {
			log.error("get moji secret is error");
		}
		return domain
				+ WeatherUtils.getMD5URL(getCDNURL(city),
						getCDNParameter(city), secret);
	}

	private String getCDNURL(int city) {
		return (new StringBuilder()).append("/data/xml/weather/").append("200")
				.append("/").append(city).append(".xml").toString();
	}

	private String getCDNParameter(int city) {
		return (new StringBuilder()).append("?&Platform=Android&BaseOSVer=")
				.append(baseosver).append("&UserID=").append(userid)
				.append("&CityID=").append(city).append("&Version=")
				.append(version).append("&IMEI=").append(imei)
				.append("&SdkVer=").append(androidSDK).append("&Device=phone")
				.append("&Model=").append("sdk").append("&PartnerKey=")
				.append(partnerid).append("&DV=").append("200")
				.append("&VersionType=").append("1").toString();
	}
}
