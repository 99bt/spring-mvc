package com.yaowang.util.spring;

import org.quartz.Trigger;
/**
 * 定时器启动器
 * @author shenl
 *
 */
public class SchedulerFactoryBean extends org.springframework.scheduling.quartz.SchedulerFactoryBean{

	@Override
	public void setTriggers(Trigger[] triggers) {
		//是否启动定时器
		String t = System.getProperty("triggers");
		if ("1".equals(t)) {
			System.out.println("=========================>spring定时器已启动");
			super.setTriggers(triggers);
		}
	}
}
