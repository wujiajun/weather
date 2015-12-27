package com.weather.core.util;

import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URI;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.http.HttpHost;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;

public class HttpUtils {
	private static final Log log = LogFactory.getLog(HttpUtils.class);
	private static int CONNECT_TIMEOUT = 10000;
	private static int READ_TIMEOUT = 10000;
	private static BasicHttpParams httpParams = null;

	public static String getContent(String url) {
		if (httpParams == null) {
			httpParams = new BasicHttpParams();
			HttpConnectionParams.setConnectionTimeout(httpParams,
					CONNECT_TIMEOUT);
			HttpConnectionParams.setSoTimeout(httpParams, READ_TIMEOUT);
		}
		DefaultHttpClient httpclient = new DefaultHttpClient(httpParams);
		ResponseHandler<String> responseHandler = new BasicResponseHandler();
		HttpGet httpget = new HttpGet(url);
		httpget.setHeader("User-Agent",
				" C100G SEC-SGHC100G/1.0 UP.Browser/5.0.5.1 (GUI)");
		httpget.setHeader("Accept",
				"text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8");
		httpget.setHeader("Accept-Language",
				"zh-cn,zh;q=0.8,en-us;q=0.5,en;q=0.3");
		String content = "";
		try {
			content = httpclient.execute(httpget, responseHandler);
		} catch (Exception e) {
			log.error(e.getMessage());
		} finally {
			httpclient.getConnectionManager().shutdown();
		}
		return content;
	}

	public static String toUtf8String(String s) {
		try {
			return new String(s.getBytes("iso-8859-1"), "UTF-8");
		} catch (UnsupportedEncodingException e) {
			return null;
		}
	}

	public static InputStream getContent(String host, String uri) {
		DefaultHttpClient httpclient = new DefaultHttpClient();
		HttpGet httpget = new HttpGet();
		// 设置user agent Apache-HttpClient/UNAVAILABLE (java 1.4)
		try {
			httpget.setHeader("User-Agent",
					" C100G SEC-SGHC100G/1.0 UP.Browser/5.0.5.1 (GUI)");
			httpget.setHeader("Accept",
					"text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8");
			httpget.setHeader("Accept-Language",
					"zh-cn,zh;q=0.8,en-us;q=0.5,en;q=0.3");
			httpget.setURI(new URI(uri));
			HttpHost httphost = new HttpHost(host, 80, "http");
			return httpclient.execute(httphost, httpget).getEntity()
					.getContent();
		} catch (Exception e) {
		} finally {
			httpclient.getConnectionManager().shutdown();
		}
		return null;
	}
}
