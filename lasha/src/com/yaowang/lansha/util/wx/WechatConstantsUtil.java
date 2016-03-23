package com.yaowang.lansha.util.wx;

import com.yaowang.util.LanshaResource;

public class WechatConstantsUtil {
	/**
	 * API请求编码
	 */
	public static final String API_REQUEST_ENCODING = "UTF-8";
	/**
	 * 微信公众号应用ID
	 */
	public static final String ACCOUNT_APPID = LanshaResource.wxDataMap.get("wx.account.appId");
	/**
	 * 微信公众号应用密钥
	 */
	public static final String ACCOUNT_APPSECRET = LanshaResource.wxDataMap.get("wx.account.appSecret");
	/**
	 * 访问token地址
	 */
	public static final String API_ACCESSTOKEN_URL = LanshaResource.wxDataMap.get("wx.api.accessToken.url");
	/**
	 * 访问JSAPITikect地址
	 */
	public static final String API_JSAPITICKET_URL = LanshaResource.wxDataMap.get("wx.api.jsApiTicket.url");
	/**
	 * 访问token超时时间（单位：秒）
	 */
	public static final int API_ACCESSTOKEN_TIMEOUT = Integer.valueOf(LanshaResource.wxDataMap.get("wx.api.accessToken.timeout"));
}
