package com.weather.notice.service.impl;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import cn.jpush.api.DeviceEnum;
import cn.jpush.api.ErrorCodeEnum;
import cn.jpush.api.JPushClient;
import cn.jpush.api.MessageResult;

import com.weather.core.bean.DeviceName;
import com.weather.notice.exception.NoticeException;
import com.weather.notice.service.INoticeService;

@Service
public class AndroidNoticeService implements INoticeService {
	private final Log log = LogFactory.getLog(getClass());
	@Value("${android.jpush.appkey}")
	private String androidJpushAppkey;
	@Value("${android.jpush.master.secret}")
	private String androidJpushMasterSecret;

	@Override
	public void notice(String token, String text) throws NoticeException {
		JPushClient jpush = new JPushClient(androidJpushMasterSecret,
				androidJpushAppkey, DeviceEnum.Android);
		// 消息id固定。表示覆盖上一条消息
		MessageResult msgResult = jpush.sendCustomMessageWithAlias(1, token,
				null, text);
		if (null != msgResult) {
			if (msgResult.getErrcode() != ErrorCodeEnum.NOERROR.value()) {
				log.error("jpush send msg is error errorcode="
						+ msgResult.getErrcode() + " errorMsg="
						+ msgResult.getErrmsg());
			}
		} else {
			log.error("jpush send msg is error");
		}
	}

	@Override
	public List<String> getInactiveTokens(DeviceName deviceName) {
		return null;
	}

}
