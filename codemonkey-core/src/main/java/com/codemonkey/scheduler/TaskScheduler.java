package com.codemonkey.scheduler;

import org.apache.log4j.Logger;
import org.springframework.context.annotation.Lazy;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.codemonkey.utils.SysUtils;

@Component
@Lazy(false)
public class TaskScheduler {

	private Logger log = SysUtils.getLog(TaskScheduler.class);
	
	@Scheduled(cron = "0 0/1 * * * *")
	public void run() {
		log.info("5 min run once");
	}
}
