package com.weather.notice.service.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javapns.Push;
import javapns.communication.exceptions.CommunicationException;
import javapns.communication.exceptions.KeystoreException;
import javapns.devices.Device;
import javapns.devices.exceptions.InvalidDeviceTokenFormatException;
import javapns.notification.PushNotificationPayload;
import javapns.notification.transmission.PushQueue;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.json.JSONException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.weather.core.bean.DeviceName;
import com.weather.notice.exception.NoticeException;
import com.weather.notice.service.INoticeService;
import com.weather.notice.service.IosPushInfo;
import com.weather.passport.service.IPassportService;

@Service
public class IphoneNoticeService implements INoticeService {

	private final Log log = LogFactory.getLog(getClass());

	private PushQueue pushQueue;
	@Value("${ios.push.queue.thread.num}")
	private int iosPushQueueThreadNum = 10;

	@Autowired
	private IPassportService passportService;

	@Override
	public void notice(String token, String text) throws NoticeException {
		try {
			if (null == pushQueue) {
				pushQueue = Push.queue(IosPushInfo.getKeystore(),
						IosPushInfo.getPassword(), IosPushInfo.isProduction(),
						iosPushQueueThreadNum);
				pushQueue.start();
			}

			PushNotificationPayload payload = PushNotificationPayload.complex();
			payload.addAlert(text);
			try {
				pushQueue.add(payload, token);
			} catch (InvalidDeviceTokenFormatException e) {
				if (StringUtils.length(token) == 15) {
					passportService.removeUser(token);
				}
			}

			// Push.combined(text, 0, IosPushInfo.getSound(),
			// IosPushInfo.getKeystore(), IosPushInfo.getPassword(),
			// IosPushInfo.isProduction(), user.getToken());

			if (log.isDebugEnabled()) {
				log.debug("[" + Thread.currentThread().getId()
						+ "] notice user[" + token + "].");
			}
		} catch (KeystoreException | JSONException e) {
			log.error(e.getMessage(), e);
		}
	}

	@Override
	public List<String> getInactiveTokens(DeviceName deviceName) {
		try {
			List<Device> deviceList = Push.feedback(IosPushInfo.getKeystore(),
					IosPushInfo.getPassword(), IosPushInfo.isProduction());
			List<String> tokenList = new ArrayList<>(deviceList.size());
			for (Device device : deviceList) {
				tokenList.add(device.getToken());
			}
			return tokenList;
		} catch (CommunicationException | KeystoreException e) {
			log.error(e.getMessage(), e);
			return Collections.emptyList();
		}
	}
}
