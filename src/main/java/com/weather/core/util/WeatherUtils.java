package com.weather.core.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Calendar;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;

import com.weather.core.bean.DeviceName;

public class WeatherUtils {

	public static String getMD5URL(String s, String s1, String secret) {
		if (StringUtils.isEmpty(secret)) {
			return null;
		}
		String s3 = (new StringBuilder()).append(secret).append(s).toString();
		String s4 = Long.toHexString(System.currentTimeMillis() / 1000L)
				.toUpperCase();
		String s5 = computeMD5((new StringBuilder()).append(s3).append(s4)
				.toString());
		String s6 = (new StringBuilder()).append(s5).append("/").append(s4)
				.append(s).append(s1).toString();
		return s6;

	}

	private static String computeMD5(String s) {
		MessageDigest messagedigest = null;
		try {
			messagedigest = MessageDigest.getInstance("MD5");
		} catch (NoSuchAlgorithmException e) {
			return null;
		}
		char ac[] = s.toCharArray();
		byte abyte0[] = new byte[ac.length];
		for (int i = 0; i < ac.length; i++)
			abyte0[i] = (byte) ac[i];

		byte abyte1[] = messagedigest.digest(abyte0);
		StringBuffer stringbuffer = new StringBuffer();
		for (int j = 0; j < abyte1.length; j++) {
			int k = 0xff & abyte1[j];
			if (k < 16)
				stringbuffer.append("0");
			stringbuffer.append(Integer.toHexString(k));
		}
		return stringbuffer.toString();

	}

	public static String getCDNSecret() {
		InputStream inputstream;
		String s = null;
		inputstream = null;
		try {
			inputstream = HttpUtils.getContent("weather.moji001.com",
					"/weather/GetCDNSecret");
		} catch (Exception exception) {
		}
		if (inputstream == null) {
			return s;
		}

		BufferedReader bufferedreader = new BufferedReader(
				new InputStreamReader(inputstream));
		do {

			try {
				String s2 = bufferedreader.readLine();
				if (s2 == null)
					break;
				String s3 = s2.trim();
				if (s3 == null || s3.equals(""))
					continue;
				s = s3;
				break;
			} catch (IOException ioexception) {
			}

		} while (true);

		return s;
	}

	public static int getTmp(String tmp) {
		try {
			return Integer.parseInt(tmp);
		} catch (Exception e) {
			return 0;
		}
	}

	/**
	 * 获取风力级别
	 * 
	 * @param wind
	 * @return
	 */
	public static int getWindLevel(String wind) {
		int bigWind = 0;
		try {
			if (wind.indexOf("-") != -1) {
				// xx-xx
				String[] str = wind.split("-");
				if (str.length > 1) {
					bigWind = Integer.parseInt(str[1].trim());
				}
			} else if (wind.indexOf("<") != -1) {
				// 小于N最大值为n-1
				bigWind = Integer.parseInt(wind.replace("<", "").trim()) - 1;
			} else if (wind.indexOf(">") != -1) {
				// 大于N最大值为n+1
				bigWind = Integer.parseInt(wind.replace(">", "").trim()) + 1;
			} else {
				bigWind = Integer.parseInt(wind);
			}
		} catch (Exception e) {
		}
		return bigWind;
	}

	public static DeviceName getClientName(HttpServletRequest request) {
		String userAgent = request.getHeader("User-Agent");
		String deviceName = null;
		if (userAgent.contains("iPhone") || userAgent.contains("iPad")) {
			deviceName = DeviceName.IPHONE.getName();
		} else if (userAgent.contains("Android")) {
			deviceName = DeviceName.ANDROID.getName();
		}
		return DeviceName.getDeviceName(deviceName);
	}

	public static Date getYesterdayDate() {
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.DAY_OF_WEEK, -1);
		return cal.getTime();
	}

	public static Date getTodayDate() {
		Calendar cal = Calendar.getInstance();
		return cal.getTime();
	}
}
