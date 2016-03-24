package com.yaowang.util;

import org.apache.commons.lang.StringUtils;

/**
 * @ClassName: RegularUtil
 * @Description: TODO(常用正则验证)
 * @author tandingbo
 * @date 2015年5月22日 下午4:21:43
 *
 */
public class RegularUtil {

	public RegularUtil() {
	}

	public static boolean telphoneReg(String telphone) {
		String telphoneReg = "^(13[0-9]|14[57]|15[0-9]|16[0-9]|17[6-8]|18[0-9])[0-9]{8}$";
		if (StringUtils.isBlank(telphone)) {
			return false;
		} else {
			return telphone.matches(telphoneReg);
		}
	}
	
	public static boolean emailReg(String email) {
		String emailReg = "\\w+([-+.]\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*";
		if (StringUtils.isBlank(email)) {
			return false;
		} else {
			return email.matches(emailReg);
		}
	}

	public static boolean checkMoney(Float value) {
		String str = String.valueOf(value);
		String regex = "^(-)?(([1-9]{1}\\d*)|([0]{1}))(\\.(\\d){1,2})?$";
		return str.matches(regex);
	}

	public static boolean checkNumber(double value) {
		String str = String.valueOf(value);
		String regex = "^(-?[1-9]\\d*\\.?\\d*)|(-?0\\.\\d*[1-9])|(-?[0])|(-?[0]\\.\\d*)$";
		return str.matches(regex);
	}

	public static boolean checkNumber(int value) {
		String str = String.valueOf(value);
		String regex = "^(-?[1-9]\\d*\\.?\\d*)|(-?0\\.\\d*[1-9])|(-?[0])|(-?[0]\\.\\d*)$";
		return str.matches(regex);
	}

	public static boolean checkNumber(String value) {
		String regex = "^(-?[1-9]\\d*\\.?\\d*)|(-?0\\.\\d*[1-9])|(-?[0])|(-?[0]\\.\\d*)$";
		return value.matches(regex);
	}
}
