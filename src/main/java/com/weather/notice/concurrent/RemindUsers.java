package com.weather.notice.concurrent;

import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.util.CollectionUtils;

import com.juzhai.core.util.SpringUtil;
import com.weather.core.bean.DeviceName;
import com.weather.core.cache.RedisKeyGenerator;
import com.weather.data.service.IWeatherDataService;
import com.weather.notice.exception.NoticeException;
import com.weather.notice.service.INoticeService;
import com.weather.passport.model.UserConfig;
import com.weather.passport.service.IPassportService;

public class RemindUsers {

	private final Log log = LogFactory.getLog(getClass());

	private int hour;
	private int minute;

	private long rowStart;
	private long rowEnd;

	// 用util来获取
	private IPassportService passportService;
	private RedisTemplate<String, String> redisTemplate;
	private IWeatherDataService weatherDataService;
	private INoticeService noticeService;

	@SuppressWarnings("unchecked")
	public RemindUsers(int hour, int minute, long start, long end) {
		this.rowStart = start;
		this.rowEnd = end;
		this.hour = hour;
		this.minute = minute;

		this.passportService = (IPassportService) SpringUtil
				.getBean("passportService");
		this.weatherDataService = (IWeatherDataService) SpringUtil
				.getBean("weatherDataService");
		this.noticeService = (INoticeService) SpringUtil
				.getBean("noticeService");
		this.redisTemplate = (RedisTemplate<String, String>) SpringUtil
				.getBean("redisTemplate");
	}

	public long getN() {
		return rowEnd - rowStart + 1;
	}

	public RemindUsers newSub(long subStart, long subEnd) {
		return new RemindUsers(this.hour, this.minute,
				this.rowStart + subStart, this.rowStart + subEnd);
	}

	public void remind() {
		if (log.isDebugEnabled()) {
			log.debug(hour + ":" + minute + " start thread[ "
					+ Thread.currentThread().getId() + "]. rowStart:"
					+ rowStart + "; rowEnd:" + rowEnd);
		}
		long start = rowStart;
		long maxResults = 200;
		while (true) {
			boolean quit = false;
			long end = start + maxResults - 1;
			if (end >= rowEnd) {
				end = rowEnd;
				quit = true;
			}
			Set<String> tokens = redisTemplate.opsForZSet().reverseRange(
					RedisKeyGenerator.genRemindHourMinuteKey(hour, minute),
					start, end);
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
			if (quit) {
				if (log.isDebugEnabled()) {
					log.debug(hour + ":" + minute + " quit thread[ "
							+ Thread.currentThread().getId() + "]. end to "
							+ end);
				}
				break;
			} else {
				start = start + maxResults;
			}
		}
	}
}
