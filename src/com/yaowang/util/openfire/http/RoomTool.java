package com.yaowang.util.openfire.http;

import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.MapUtils;
import org.apache.http.NameValuePair;

import com.alibaba.fastjson.JSON;
import com.yaowang.common.constant.BaseConstant;
import com.yaowang.util.http.HttpUtil;
import com.yaowang.util.openfire.OpenFireConstant;

@SuppressWarnings("unchecked")
public class RoomTool {

	public static void main(String[] args) throws Exception {
		// Object type = createRoom("snle", "tgy002",
		// OpenFireConstant.CONFERENCE);
		// Object type = contains("tgy002");
		// Object type = destroyRoom("tg002");
		// System.out.println(type);

		// jion("ww", OpenFireConstant.CHATGROUP_CONFERENCE,
		// Arrays.asList("10000@0.0.0.0"));
		// System.out.println(getUserCount("ww",
		// OpenFireConstant.CHATGROUP_CONFERENCE));
	}

	/**
	 * 加入房间
	 * 
	 * @param users
	 * @return
	 */
	public static Boolean jion(String imIp, String roomName, String conference, final List<String> users) {
		// TODO 将用户信息从OpenFire服务器中删除
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		// 令牌
		HttpUtil.addParams(params, "token", OpenFireConstant.PLUGINS_TOKEN);
		// 服务方法名称
		HttpUtil.addParams(params, "method", "save");
		// 讨论组ID
		HttpUtil.addParams(params, "id", roomName + "@" + conference);
		// 讨论组内用户id数组
		for (String userId : users) {
			HttpUtil.addParams(params, "users", userId);
		}
		try {
			String url = String.format(OpenFireConstant.PLUGINS_URL, imIp);
			String result = HttpUtil.handlePost(url, params);
			result = URLDecoder.decode(result, "utf-8");
			Map<String, Object> map = JSON.parseObject(result, Map.class);
			if (BaseConstant.NO == Integer.valueOf(map.get("status").toString())) {
//				System.out.println(map.get("failed").toString());
				return false;
			}else {
				return true;
			}
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	/**
	 * 删除房间用户
	 * 
	 * @param users
	 * @return
	 */
	public static Boolean remove(String imIp, String roomName, String conference, final List<String> users) {
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		// 令牌
		HttpUtil.addParams(params, "token", OpenFireConstant.PLUGINS_TOKEN);
		// 服务方法名称
		HttpUtil.addParams(params, "method", "remove");
		// 讨论组ID
		HttpUtil.addParams(params, "id", roomName + "@" + conference);
		// 讨论组内用户id数组
		for (String userId : users) {
			HttpUtil.addParams(params, "users", userId);
		}
		try {
			String url = String.format(OpenFireConstant.PLUGINS_URL, imIp);
			String result = HttpUtil.handlePost(url, params);
			result = URLDecoder.decode(result, "utf-8");
			Map<String, Object> map = JSON.parseObject(result, Map.class);
			if (BaseConstant.NO == Integer.valueOf(map.get("status").toString())) {
//				System.out.println(map.get("failed").toString());
				return false;
			}else {
				return true;
			}
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	/**
	 * 获取在线人数
	 * 
	 * @param roomName
	 * @param conference
	 * @return
	 */
	public static Integer getUserCount(String imIp, String roomName, String conference) {
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		// 令牌
		HttpUtil.addParams(params, "token", OpenFireConstant.PLUGINS_TOKEN);
		// 服务方法名称
		HttpUtil.addParams(params, "method", "online");
		// 讨论组ID
		HttpUtil.addParams(params, "id", roomName + "@" + conference);
		Integer number = 0;
		try {
			String url = String.format(OpenFireConstant.PLUGINS_URL, imIp);
			String result = HttpUtil.handlePost(url, params);
			result = URLDecoder.decode(result, "utf-8");
			Map<String, Object> map = JSON.parseObject(result, Map.class);
			if (BaseConstant.NO == Integer.valueOf(map.get("status").toString())) {
//				System.out.println(map.get("failed").toString());
				return number;
			} else {
				Map<String, Object> dataMap = (Map<String, Object>) map.get("data");
				if(MapUtils.isNotEmpty(dataMap) && dataMap.containsKey("number")){
					Object n = dataMap.get("number");
					if (n == null) {
						number = null;
					}else {
						number = Integer.valueOf(n.toString());
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
		return number;
	}
	/**
	 * 创建房间
	 * 
	 * @return
	 */
	public static Boolean createRoom(String imIp, String roomName, String conference) {
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		// 令牌
		HttpUtil.addParams(params, "token", OpenFireConstant.PLUGINS_TOKEN);
		// 服务方法名称
		HttpUtil.addParams(params, "method", "createRoom");
		// 房间
		HttpUtil.addParams(params, "roomName", roomName);
		HttpUtil.addParams(params, "mucName", conference);
		try {
			String url = String.format(OpenFireConstant.PLUGINS_URL, imIp);
			String result = HttpUtil.handlePost(url, params);
			result = URLDecoder.decode(result, "utf-8");
			Map<String, Object> map = JSON.parseObject(result, Map.class);
			if (BaseConstant.NO == Integer.valueOf(map.get("status").toString())) {
				return false;
			}else {
				return true;
			}
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
	
	/**
	 * @Description: 销毁房间
	 * @param roomName
	 * @param conference
	 * @return
	 */
	public static Boolean destroyRoom(String imIp, String roomName, String conference) {
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		// 令牌
		HttpUtil.addParams(params, "token", OpenFireConstant.PLUGINS_TOKEN);
		// 服务方法名称
		HttpUtil.addParams(params, "method", "destroyRoom");
		// 房间ID
		HttpUtil.addParams(params, "id", roomName + "@" + conference);
		try {
			String url = String.format(OpenFireConstant.PLUGINS_URL, imIp);
			String result = HttpUtil.handlePost(url, params);
			result = URLDecoder.decode(result, "utf-8");
			Map<String, Object> map = JSON.parseObject(result, Map.class);
			if (BaseConstant.NO == Integer.valueOf(map.get("status").toString())) {
				return false;
			}else {
				return true;
			}
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
}
