package com.yaowang.lansha.util.wx;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import com.alibaba.fastjson.JSONObject;
import com.yaowang.lansha.entity.WechatAccessToken;
import com.yaowang.lansha.entity.WechatJsapiticket;
import com.yaowang.lansha.service.WechatAccessTokenService;
import com.yaowang.lansha.service.WechatJsapiticketService;

public class WechatTokenUtil{
	/**
	 * 同步获得远程token
	 * @author ZHY
	 * @creationDate. 2016-1-25 上午09:33:16
	 * @param appId 应用ID
	 * @return appSecret 应用秘钥
	 * @throws Exception
	 */
	public synchronized static String getRemoteToken(String appId,
			String appSecret,WechatAccessTokenService wechatAccessTokenService) throws Exception {
		String accessToken = "";
		String enc = WechatConstantsUtil.API_REQUEST_ENCODING; // 请求编码
		String serverURL = WechatConstantsUtil.API_ACCESSTOKEN_URL; // API服务地址
		Map<String, String> inParams = new HashMap<String, String>();//组织请求参数
		inParams.put("appid", appId);
		inParams.put("secret", appSecret);
		String requestURL = HttpRequestUtil.getRequestURL(serverURL, inParams,enc); // 生成请求token的地址
		String resJson = HttpRequestUtil.doGet(requestURL, enc); // 调用服务响应消息
		Map<String, Object> jsonObj = (Map<String, Object>) JSONObject.parse(resJson);//解析响应结果
		if (jsonObj != null && jsonObj.size() > 0) {
			Object objAccessToken = jsonObj.get("access_token"); // 获取令牌			
			if (objAccessToken != null) { // 正常调用响应可以获取到令牌
				accessToken = objAccessToken.toString();
				WechatAccessToken localToken = new WechatAccessToken();// 将最新的令牌存入数据库
				localToken.setAppId(appId); // 公众账号ID
				localToken.setPubDate(new Date()); // 当前时间
				localToken.setTokenName(accessToken); // 令牌
				localToken.setCreateTime(new Date());
				localToken.setExpiresIn(WechatConstantsUtil.API_ACCESSTOKEN_TIMEOUT);//
				Integer count = wechatAccessTokenService.update(localToken);
				if(count<1){
					wechatAccessTokenService.save(localToken); // 保存或者更新令牌信息
				}
				return accessToken;
			}
		}
		return accessToken;
	}

	/**
	 * 同步获得远程ticket
	 * @author ZHY
	 * @creationDate. 2016-1-25 上午09:33:16
	 * @param appId 应用ID
	 * @return token
	 * @throws Exception
	 */
	public synchronized static String getRemoteTicket(String appId, String token,WechatJsapiticketService wechatJsapiticketService)
			throws Exception {

		// 调用接口获取TICKET
		String serverURL = WechatConstantsUtil.API_JSAPITICKET_URL;// API服务地址
		serverURL = serverURL.replace("ACCESS_TOKEN", token);
		String enc = WechatConstantsUtil.API_REQUEST_ENCODING; // 请求编码
		String resJson = HttpRequestUtil.doGet(serverURL, enc); // 调用服务响应消息
		String accessTicket = "";
		Map<String, Object> jsonObj = (Map<String, Object>) JSONObject.parse(resJson);//解析响应结果
		if (jsonObj != null && jsonObj.size() > 0) {
			Object objAccessTicket = jsonObj.get("ticket"); // 获取TICKET
			if (objAccessTicket != null) { // 正常调用响应可以获取到令牌
				accessTicket = objAccessTicket.toString();
				WechatJsapiticket localTicket = new WechatJsapiticket();// 将最新的令牌存入数据库
				localTicket.setAppId(appId); // 公众账号ID
				localTicket.setPubDate(new Date()); // 当前时间
				localTicket.setTicketName(accessTicket); // 令牌
				localTicket.setCreateTime(new Date());
				localTicket.setExpiresIn(WechatConstantsUtil.API_ACCESSTOKEN_TIMEOUT);
				Integer count = wechatJsapiticketService.update(localTicket);//更新令牌信息
				if(count<1){
					wechatJsapiticketService.save(localTicket); // 保存令牌信息
				}				
				return accessTicket;
			}
		}
		return accessTicket;
	}

}
