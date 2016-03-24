package com.yaowang.util;

import java.util.Map;


public class LanshaResource {
	public static Map<String, String> dataMap = ResourcesLoad.load("classpath*:/lansha.properties");
	public static Map<String, String> wxDataMap = ResourcesLoad.load("classpath*:/conf/wxconnectconfig.properties");
}
