package com.yaowang.util;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;

/**
 * 判断是否手机用户
 * @author shenl
 *
 */
public class JudgeIsMoblie {
	//类型
	private static final String[] mobileAgents = { "iphone", "ipad", "android", "phone", "mobile", "wap", "Googlebot-Mobile" };

	/**
	 * 判断是否是手机访问
	 * @param request
	 * @return
	 */
	public static boolean judgeIsMoblie(HttpServletRequest request) {
		boolean isMoblie = false;
		String head = request.getHeader("User-Agent");
		if (StringUtils.isNotBlank(head)) {
			head = head.toLowerCase();
			for (String mobileAgent : mobileAgents) {
				if (head.indexOf(mobileAgent) >= 0) {
					isMoblie = true;
					break;
				}
			}
		}
		return isMoblie;
	}
}
