package com.weather.alipay.service.impl;

import java.net.URLEncoder;
import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;

import com.weather.alipay.service.IAlipayService;
import com.weather.alipay.util.Keys;
import com.weather.alipay.util.Rsa;

@Service
public class AlipayService implements IAlipayService {
	@Autowired
	private MessageSource messageSource;

	@Override
	public String getOrderInfo(String tradeNo, String price) {
		StringBuilder sb = new StringBuilder();
		sb.append("partner=\"");
		sb.append(Keys.DEFAULT_PARTNER);
		sb.append("\"&out_trade_no=\"");
		sb.append(tradeNo);
		sb.append("\"&subject=\"");
		sb.append(messageSource.getMessage("donate.pay.title", null,
				Locale.SIMPLIFIED_CHINESE));
		sb.append("\"&body=\"");
		sb.append(messageSource.getMessage("donate.pay.title", null,
				Locale.SIMPLIFIED_CHINESE));
		sb.append("\"&total_fee=\"");
		sb.append(price);
		sb.append("\"&notify_url=\"");

		// 网址需要做URL编码
		sb.append(URLEncoder.encode("http://www.51juzhai.com"));
		sb.append("\"&service=\"mobile.securitypay.pay");
		sb.append("\"&_input_charset=\"UTF-8");
		sb.append("\"&return_url=\"");
		sb.append(URLEncoder.encode("http://m.alipay.com"));
		sb.append("\"&payment_type=\"1");
		sb.append("\"&seller_id=\"");
		sb.append(Keys.DEFAULT_SELLER);

		// 如果show_url值为空，可不传
		// sb.append("\"&show_url=\"");
		sb.append("\"&it_b_pay=\"30m");
		sb.append("\"");
		String info = new String(sb);
		String sign = Rsa.sign(info, Keys.PRIVATE);
		sign = URLEncoder.encode(sign);
		info += "&sign=\"" + sign + "\"&" + getSignType();
		return info;
	}

	private String getSignType() {
		return "sign_type=\"RSA\"";
	}

}
