package com.weather.notice.schedule;

import java.util.Calendar;
import java.util.concurrent.ForkJoinPool;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;

import com.juzhai.core.sechdule.AbstractScheduleHandler;
import com.weather.core.cache.RedisKeyGenerator;
import com.weather.notice.concurrent.RemindUsers;
import com.weather.notice.concurrent.RemindUsersForkJoinTask;

//@Component
public class ForkJoinRemindUserHandler extends AbstractScheduleHandler {
	private final Log log = LogFactory.getLog(getClass());

	@Autowired
	private RedisTemplate<String, String> redisTemplate;
	@Autowired
	private ForkJoinPool forkJoinPool;

	@Override
	protected void doHandle() {
		Calendar cal = Calendar.getInstance();
		int hour = cal.get(Calendar.HOUR_OF_DAY);
		int minute = cal.get(Calendar.MINUTE) >= 30 ? 30 : 0;

		long nCount = redisTemplate.opsForZSet().count(
				RedisKeyGenerator.genRemindHourMinuteKey(hour, minute),
				Double.MIN_VALUE, Double.MAX_VALUE);
		// long nCount = 30000L;

		RemindUsers problem = new RemindUsers(hour, minute, 0L, nCount - 1);
		RemindUsersForkJoinTask task = new RemindUsersForkJoinTask(problem);

		long time1 = System.currentTimeMillis();
		forkJoinPool.invoke(task);
		if (log.isDebugEnabled()) {
			log.debug("paralled Time:" + (System.currentTimeMillis() - time1)
					+ "ms");
		}
	}
}
