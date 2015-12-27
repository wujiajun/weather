package com.weather.data.service.impl;

import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.weather.core.InitData;
import com.weather.data.bean.AddressComponent;
import com.weather.data.exception.WeatherDataException;
import com.weather.data.service.IMapService;
import com.weather.data.service.IWeatherDataUrlService;

@Service
public class ZhiquWeatherDataUrlService implements IWeatherDataUrlService {
	private final String ZHIQU_URI = "weather/data/getSixWeather";
	@Value("${server.base.url}")
	private String baseUrl;
	@Autowired
	private IMapService mapService;
	@Autowired
	private MessageSource messageSource;

	@Override
	public String getAutoLocUrl(String from, double lat, double lng)
			throws WeatherDataException {
		AddressComponent address = mapService.geocode(lat, lng);
		String district = address.getDistrict();
		String city = address.getCity();
		String myCity = "";
		String myProv = "";
		if (district.indexOf(messageSource.getMessage("weather.district", null,
				Locale.SIMPLIFIED_CHINESE)) != -1) {
			myCity = district.substring(0, district.length() - 1);
			myProv = InitData.ZHIQU_CITY_MAP.get(myCity);
			if (!StringUtils.hasText(myProv)) {
				myCity = district;
				myProv = InitData.ZHIQU_CITY_MAP.get(district);
				if (!StringUtils.hasText(myProv)) {
					myCity = "";
					myProv = "";
				}
			}
		} else {
			myCity = city.substring(0, city.length() - 1);
			myProv = InitData.ZHIQU_CITY_MAP.get(myCity);
			if (!StringUtils.hasText(myProv)) {
				myCity = "";
				myProv = "";
			}
		}
		return baseUrl + ZHIQU_URI + "?city=" + myCity + "&province=" + myProv;

	}

	@Override
	public String getWeatherDataUrl(String from, String cityName)
			throws WeatherDataException {
		String province = InitData.ZHIQU_CITY_MAP.get(cityName);
		String myCity = "";
		String myProv = "";
		if (!StringUtils.hasText(province)) {
			String cityAndProv = InitData.MOJI_MATCH_ZHIQU_CITY_MAP
					.get(cityName);
			if (StringUtils.hasText(cityAndProv)) {
				String str[] = cityAndProv.split("=");
				myCity = str[0];
				myProv = str[1];
			}
		} else {
			myCity = cityName;
			myProv = province;
		}

		return baseUrl + ZHIQU_URI + "?city=" + myCity + "&province=" + myProv;

	}

	@Override
	public String getWeatherDataUrl(int cityId) throws WeatherDataException {
		return getWeatherDataUrl(null, InitData.CITY_ID_MAP.get(cityId));
	}

}
