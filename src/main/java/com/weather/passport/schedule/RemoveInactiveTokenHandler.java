package com.weather.passport.schedule;

import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.juzhai.core.sechdule.AbstractScheduleHandler;
import com.weather.core.bean.DeviceName;
import com.weather.notice.service.INoticeService;
import com.weather.passport.service.IPassportService;

@Component
public class RemoveInactiveTokenHandler extends AbstractScheduleHandler {
	@Autowired
	private INoticeService noticeService;
	@Autowired
	private IPassportService passportService;

	@Override
	protected void doHandle() {
		List<String> inactiveTokens = noticeService
				.getInactiveTokens(DeviceName.IPHONE);
		if (CollectionUtils.isNotEmpty(inactiveTokens)) {
			for (String token : inactiveTokens) {
				if (StringUtils.isNotEmpty(token)) {
					passportService.removeUser(token);
				}
			}
			if (log.isDebugEnabled()) {
				log.debug("remove " + inactiveTokens.size()
						+ "inactive tokens.");
			}
		}
	}

}
