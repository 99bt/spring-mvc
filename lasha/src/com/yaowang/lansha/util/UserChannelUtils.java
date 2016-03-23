package com.yaowang.lansha.util;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;

import com.yaowang.common.action.BaseAction;

public class UserChannelUtils {
	public static String CHANNEL_URL = "/index.html";

	public static String getChannelURL(Integer id) {
		return String.format("%s?pn=%s", CHANNEL_URL, id);
	}

	/**
	 * 保存推广号到cookie中
	 * 
	 * @param response
	 * @param name
	 * @param value
	 */
	public static void addChannel2Cookie(HttpServletResponse response,
			String name, String value) {
		Cookie cookie = new Cookie(name, value);
		// 有效期一个月
		cookie.setMaxAge(60 * 60 * 24 * 30);
		response.addCookie(cookie);
		// 加入session
		BaseAction.getSession().put(name, value);
	}

	/**
	 * 从cookie中读取推广号
	 * 
	 * @param request
	 * @param name
	 * @return
	 */
	public static String getChannelFormCookie(HttpServletRequest request,
			String name) {
		//默认推广员
		String defaultString = "";
		// session获取
		defaultString = (String)BaseAction.getSession().get(name);
		if(StringUtils.isNotBlank(defaultString)){
			return defaultString;
		}
		// cookie获取
		Cookie[] cookies = request.getCookies();
		if (null == cookies) {
			return defaultString;
		}
		for (Cookie cookie : cookies) {
			if (cookie.getName().equals(name)) {
				return cookie.getValue();
			}
		}
		return defaultString;
	}
}
