package com.yaowang.common.util;

import java.util.Date;

import org.apache.commons.lang.ArrayUtils;

import com.yaowang.entity.LogSystem;
import com.yaowang.service.LogSystemService;
import com.yaowang.util.asynchronous.AsynchronousService;
import com.yaowang.util.asynchronous.ObjectCallable;
import com.yaowang.util.spring.ContainerManager;

public class LogUtil {
	/**
	 * 记录日志
	 */
	public static final void log(String type, Object...values){
		if (ArrayUtils.isEmpty(values)) {
			return;
		}
		StringBuffer buffer = new StringBuffer();
		for (Object string : values) {
			buffer.append(string);
		}
		final LogSystem logSystem = new LogSystem();
		logSystem.setType(type);
		logSystem.setValue(buffer.toString());
		logSystem.setCreateTime(new Date());
		
		//异步日志记录
        AsynchronousService.submit(new ObjectCallable(){
			@Override
			public Object run() throws Exception {
				LogSystemService service = (LogSystemService)ContainerManager.getComponent("logSystemService");
				service.save(logSystem);
				return null;
			}
        });
	}
}
