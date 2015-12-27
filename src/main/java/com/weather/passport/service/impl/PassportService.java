package com.weather.passport.service.impl;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import com.weather.core.InitData;
import com.weather.core.bean.DeviceName;
import com.weather.core.cache.RedisKeyGenerator;
import com.weather.passport.controller.form.UserForm;
import com.weather.passport.exception.PassportException;
import com.weather.passport.model.UserConfig;
import com.weather.passport.service.IPassportService;

@Service
public class PassportService implements IPassportService {
	@Autowired
	private RedisTemplate<String, Object> redisTemplate;
	@Autowired
	private RedisTemplate<String, String> stringRedisTemplate;
	@Autowired
	private RedisTemplate<String, Integer> intRedisTemplate;

	@Override
	public UserConfig saveUserConfig(UserForm form, DeviceName device)
			throws PassportException {
		UserConfig userConfig = validate(form, device);
		if (device == null || device.equals(DeviceName.ANDROID)) {
			return userConfig;
		}
		UserConfig oldUserConfig = getUserConfig(userConfig.getToken());
		saveRemind(oldUserConfig, userConfig);
		saveUser(oldUserConfig, userConfig);
		return userConfig;
	}

	private UserConfig validate(UserForm form, DeviceName divice)
			throws PassportException {
		int city = 0;
		if (form == null) {
			throw new PassportException(PassportException.ILLEGAL_OPERATION);
		}
		if (StringUtils.isEmpty(form.getToken())) {
			throw new PassportException(PassportException.ILLEGAL_OPERATION);
		}
		if (InitData.CITY_MAP.get(form.getCityName()) != null) {
			city = InitData.CITY_MAP.get(form.getCityName());
		}
		Integer hour = null;
		Integer minute = null;
		if (StringUtils.isNotEmpty(form.getHour())) {
			try {
				SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
				Calendar cal = Calendar.getInstance();
				cal.setTime(sdf.parse(form.getHour()));
				hour = cal.get(Calendar.HOUR_OF_DAY);
				minute = cal.get(Calendar.MINUTE);
			} catch (ParseException e) {
				throw new PassportException(PassportException.ILLEGAL_OPERATION);
			}
		}
		UserConfig user = new UserConfig();
		user.setCity(city);
		user.setCityName(form.getCityName());
		user.setDeviceName(divice.getName());
		user.setHour(hour);
		user.setMinute(minute);
		user.setRemindCooling(form.isRemindCooling());
		user.setRemindRain(form.isRemindRain());
		user.setRemindWind(form.isRemindWind());
		user.setRemindHot(form.isRemindHot());
		user.setRemindTempDrop(form.isRemindTempDrop());
		user.setToken(form.getToken());
		return user;
	}

	private void saveUser(UserConfig oldUserConfig, UserConfig userConfig) {
		if (userConfig.getCity() != 0) {
			if (oldUserConfig != null
					&& oldUserConfig.getCity() != userConfig.getCity()) {
				// 删除旧的token所在城市
				stringRedisTemplate.opsForSet().remove(
						RedisKeyGenerator.genTokenKey(oldUserConfig.getCity()),
						userConfig.getToken());
				if (stringRedisTemplate.opsForSet().size(
						RedisKeyGenerator.genTokenKey(oldUserConfig.getCity())) == 0) {
					intRedisTemplate.opsForSet().remove(
							RedisKeyGenerator.genRegisteredCityKey(),
							oldUserConfig.getCity());
				}
			}
			if (oldUserConfig == null
					|| oldUserConfig.getCity() != userConfig.getCity()) {
				// 城市和设备号的关系
				stringRedisTemplate.opsForSet().add(
						RedisKeyGenerator.genTokenKey(userConfig.getCity()),
						userConfig.getToken());
				intRedisTemplate.opsForSet().add(
						RedisKeyGenerator.genRegisteredCityKey(),
						userConfig.getCity());
			}
		}
		redisTemplate.opsForValue().set(
				RedisKeyGenerator.genUserConfigKey(userConfig.getToken()),
				userConfig);

	}

	private void saveRemind(UserConfig oldUserConfig, UserConfig userConfig) {
		if (userConfig.getHour() == null) {
			return;
		}
		if (oldUserConfig != null && oldUserConfig.getHour() != null) {
			if (oldUserConfig.getHour().intValue() != userConfig.getHour()
					.intValue()
					|| oldUserConfig.getMinute() == null
					|| oldUserConfig.getMinute().intValue() != userConfig
							.getMinute().intValue()) {
				stringRedisTemplate.opsForZSet().remove(
						RedisKeyGenerator.genRemindHourMinuteKey(
								oldUserConfig.getHour(),
								oldUserConfig.getMinute()),
						userConfig.getToken());
			}
		}
		if (oldUserConfig == null
				|| oldUserConfig.getHour() == null
				|| oldUserConfig.getHour().intValue() != userConfig.getHour()
						.intValue()
				|| oldUserConfig.getMinute() == null
				|| oldUserConfig.getMinute().intValue() != userConfig
						.getMinute().intValue()) {
			stringRedisTemplate.opsForZSet().add(
					RedisKeyGenerator.genRemindHourMinuteKey(
							userConfig.getHour(), userConfig.getMinute()),
					userConfig.getToken(), System.currentTimeMillis());
		}
	}

	@Override
	public void removeUser(String token) {
		UserConfig oldUserConfig = getUserConfig(token);
		if (null != oldUserConfig) {
			// 删除userConfig
			redisTemplate.delete(RedisKeyGenerator.genUserConfigKey(token));
			// 删除所在城市的token
			if (oldUserConfig.getCity() > 0) {
				stringRedisTemplate.opsForSet().remove(
						RedisKeyGenerator.genTokenKey(oldUserConfig.getCity()),
						token);
				if (stringRedisTemplate.opsForSet().size(
						RedisKeyGenerator.genTokenKey(oldUserConfig.getCity())) == 0) {
					intRedisTemplate.opsForSet().remove(
							RedisKeyGenerator.genRegisteredCityKey(),
							oldUserConfig.getCity());
				}
			}
			// 删除remindHour
			if (oldUserConfig.getHour() != null) {
				stringRedisTemplate.opsForZSet().remove(
						RedisKeyGenerator.genRemindHourMinuteKey(
								oldUserConfig.getHour(),
								oldUserConfig.getMinute()), token);
			}
		}
	}

	@Override
	public UserConfig getUserConfig(String token) {
		return (UserConfig) redisTemplate.opsForValue().get(
				RedisKeyGenerator.genUserConfigKey(token));
	}
}
