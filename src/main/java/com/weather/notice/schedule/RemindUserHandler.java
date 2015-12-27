package com.weather.notice.schedule;

import java.util.Calendar;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import com.juzhai.core.sechdule.AbstractScheduleHandler;
import com.weather.core.bean.DeviceName;
import com.weather.core.cache.RedisKeyGenerator;
import com.weather.data.service.IWeatherDataService;
import com.weather.notice.exception.NoticeException;
import com.weather.notice.service.INoticeService;
import com.weather.passport.model.UserConfig;
import com.weather.passport.service.IPassportService;

@Component
public class RemindUserHandler extends AbstractScheduleHandler {
	@Autowired
	private RedisTemplate<String, String> strRedisTemplate;
	@Autowired
	private IWeatherDataService weatherDataService;
	@Autowired
	private INoticeService noticeService;
	@Autowired
	private IPassportService passportService;

	@Override
	protected void doHandle() {
		long start = System.currentTimeMillis();
		Calendar cal = Calendar.getInstance();
		int hour = cal.get(Calendar.HOUR_OF_DAY);
		int minute = cal.get(Calendar.MINUTE) >= 30 ? 30 : 0;
		int firstResult = 0;
		int maxResults = 200;
		// 找到所有此时需要提醒的人
		while (true) {
			Set<String> tokens = strRedisTemplate.opsForZSet().reverseRange(
					RedisKeyGenerator.genRemindHourMinuteKey(hour, minute),
					firstResult, firstResult + maxResults - 1);
			if (CollectionUtils.isEmpty(tokens)) {
				break;
			}
			for (String token : tokens) {
				UserConfig userConfig = passportService.getUserConfig(token);
				if (null != userConfig) {
					if (StringUtils.equals(userConfig.getDeviceName(),
							DeviceName.ANDROID.getName())) {
						passportService.removeUser(token);
						continue;
					}
				}
				try {
					String content = weatherDataService.getNoticeContent(token);
					if (StringUtils.isNotEmpty(content)) {
						try {
							noticeService.notice(token, content);
						} catch (NoticeException e) {
							log.error("weather notice is error token=" + token
									+ ".", e);
						}
					}
				} catch (Exception e) {
					log.error(e.getMessage(), e);
					continue;
				}

			}
			firstResult = firstResult + maxResults;
		}
		long end = System.currentTimeMillis();
		if (log.isDebugEnabled()) {
			log.debug(hour + ":" + minute + " use " + (end - start) + "ms.");
		}
	}
}
