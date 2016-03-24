package com.yaowang.util.openfire.http;

import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.http.NameValuePair;
import org.apache.http.conn.ConnectTimeoutException;

import com.alibaba.fastjson.JSON;
import com.yaowang.common.constant.BaseConstant;
import com.yaowang.util.http.HttpUtil;
import com.yaowang.util.openfire.OpenFireConstant;

public class UserTool {
	
	/**
	 * 注册用户
	 * @param username
	 * @param pwd
	 * @param name
	 * @return
	 */
	public static String register(String username, String pwd, String name) {
		return null;
	}

	/**
	 * 直接db修改密码
	 * @param username
	 * @param pwd
	 * @param name
	 * @return
	 */
	public static Boolean modifyPwd(String username, String pwd, String npwd, String name){
		return false;
	}
	

	/**
	 * 删除当前用户
	 * 
	 * @param connection
	 * @return
	 */
	public static boolean deleteAccount(String username, String pwd) {
		return false;
	}
	/**
	 * 加入黑名单
	 * @param uid
	 * @return
	 */
	public static Boolean addBlacklist(String uid){
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		// 令牌
		HttpUtil.addParams(params, "token", OpenFireConstant.PLUGINS_TOKEN);
		// 服务方法名称
		HttpUtil.addParams(params, "method", "addBlacklist");
		HttpUtil.addParams(params, "name", uid);
		
		try {
			String url = String.format(OpenFireConstant.PLUGINS_URL, OpenFireConstant.IP);
			String result = HttpUtil.handlePost(url, params);
			result = URLDecoder.decode(result, "utf-8");
			Map<String, Object> map = JSON.parseObject(result, Map.class);
			
			if (BaseConstant.NO == Integer.valueOf(map.get("status").toString())) {
				return false;
			}
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}
	/**
	 * 解除黑名单
	 * @param uid
	 * @return
	 */
	public static Boolean removeBlacklist(String uid){
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		// 令牌
		HttpUtil.addParams(params, "token", OpenFireConstant.PLUGINS_TOKEN);
		// 服务方法名称
		HttpUtil.addParams(params, "method", "removeBlacklist");
		HttpUtil.addParams(params, "name", uid);
		
		try {
			String url = String.format(OpenFireConstant.PLUGINS_URL, OpenFireConstant.IP);
			String result = HttpUtil.handlePost(url, params);
			result = URLDecoder.decode(result, "utf-8");
			Map<String, Object> map = JSON.parseObject(result, Map.class);
			
			if (BaseConstant.NO == Integer.valueOf(map.get("status").toString())) {
				return false;
			}
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}
}
