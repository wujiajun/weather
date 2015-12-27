package com.weather.notice.schedule;

import java.util.Calendar;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import com.juzhai.core.sechdule.AbstractScheduleHandler;
import com.weather.core.cache.RedisKeyGenerator;
import com.weather.notice.task.RemindUserTask;

//@Component
public class ParallelRemindUserHandler extends AbstractScheduleHandler {
	private final Log log = LogFactory.getLog(getClass());

	@Autowired
	private RedisTemplate<String, String> redisTemplate;
	@Autowired
	private ThreadPoolTaskExecutor taskExecutor;

	@Autowired
	// private ForkJoinPool forkJoinPool;
	@Override
	protected void doHandle() {
		Calendar cal = Calendar.getInstance();
		int hour = cal.get(Calendar.HOUR_OF_DAY);
		int minute = cal.get(Calendar.MINUTE) >= 30 ? 30 : 0;

		long nCount = redisTemplate.opsForZSet().count(
				RedisKeyGenerator.genRemindHourMinuteKey(hour, minute),
				Double.MIN_VALUE, Double.MAX_VALUE);

		// RemindUsers problem = new RemindUsers(hour, minute, 0L, nCount - 1);
		// RemindUsersForkJoinTask task = new RemindUsersForkJoinTask(problem);
		//
		// long time1 = System.currentTimeMillis();
		// poolInstance.invoke(task);
		// if (log.isDebugEnabled()) {
		// log.debug("paralled Time:" + (System.currentTimeMillis() - time1)
		// + "ms");
		// }

		int perThreadNCount = 5 * 60 * 10;

		long threadNum = nCount / perThreadNCount + 1;
		if (log.isDebugEnabled()) {
			log.debug(hour + ":" + minute + " is using " + threadNum
					+ " threads.");
		}
		for (int i = 0; i < threadNum; i++) {
			int rowStart = i * perThreadNCount;
			if (rowStart >= nCount) {
				break;
			}
			int rowEnd = rowStart + perThreadNCount - 1;
			if (rowEnd >= nCount) {
				rowEnd = Long.valueOf(nCount).intValue() - 1;
			}
			taskExecutor.execute(new RemindUserTask(hour, minute, rowStart,
					rowEnd));
		}
	}
}
