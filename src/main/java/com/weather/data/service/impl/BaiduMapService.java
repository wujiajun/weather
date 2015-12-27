package com.weather.data.service.impl;

import net.sf.json.JSONObject;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.weather.core.util.HttpUtils;
import com.weather.data.bean.AddressComponent;
import com.weather.data.service.IMapService;

@Service
public class BaiduMapService implements IMapService {
	private final Log log = LogFactory.getLog(getClass());
	@Value("${api.baidu.key}")
	private String apiBaiduKey;
	@Value("${api.baidu.geocod.uri}")
	private String apiBaiduGeocodUri;

	private AddressComponent conversionPlace(String url) {
		AddressComponent address = null;
		try {
			JSONObject json = JSONObject.fromObject(HttpUtils.getContent(url));
			String status = json.getString("status");
			if (status != null && status.equalsIgnoreCase("0")) {
				JSONObject results = json.getJSONObject("result");
				if (results != null) {
					JSONObject addressObject = results
							.getJSONObject("addressComponent");
					if (addressObject != null) {
						address = new AddressComponent();
						address.setCity(addressObject.getString("city"));
						address.setDistrict(addressObject.getString("district"));
						address.setProvince(addressObject.getString("province"));
						address.setStreet(addressObject.getString("street"));
						address.setStreetNumber(addressObject
								.getString("street_number"));
						return address;
					}
				}
			}
		} catch (Exception e) {
		}
		return address;
	}

	@Override
	public AddressComponent geocode(double lat, double lng) {
		String url = apiBaiduGeocodUri + "?ak=" + apiBaiduKey
				+ "&pois=1&output=json&location=" + lat + "," + lng
				+ "&coordtype=gcj02ll";
		return conversionPlace(url);
	}

}
