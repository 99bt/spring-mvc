package com.yaowang.util.openfire.http;

import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.http.NameValuePair;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.yaowang.common.constant.BaseConstant;
import com.yaowang.common.util.LogUtil;
import com.yaowang.util.http.HttpUtil;
import com.yaowang.util.openfire.OpenFireConstant;

public class MessageTool {
	/**
	 * 发送消息
	 * 
	 * @param username用户名
	 * @param msg消息内容
	 * @param type消息类型(点赞，评论，回复，关注)详细查看OpenFireConstant
	 * @param data消息类型内容(资讯id，用户id等信息)
	 * @return
	 */
	public static Boolean sendUserMessage(String imIp, String username, String msg, Integer type, String data) {
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		// 令牌
		HttpUtil.addParams(params, "token", OpenFireConstant.PLUGINS_TOKEN);
		// 服务方法名称
		HttpUtil.addParams(params, "method", "chatMessage");
		// 参数
		HttpUtil.addParams(params, "from", OpenFireConstant.ADMIN_NAME);
		HttpUtil.addParams(params, "to", username);
		HttpUtil.addParams(params, "body", msg);
		
		HttpUtil.addParams(params, OpenFireConstant.MSG_TYPE, type.toString());
		HttpUtil.addParams(params, OpenFireConstant.MSG_DATA, data);
		HttpUtil.addParams(params, OpenFireConstant.MSG_FORMNAME, "系统消息");
		
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
	 * 发送房间指令
	 * 
	 * @param room
	 * @param type
	 * @return
	 */
	public static Boolean sendRoomMessage(String imIp, String roomId, String conference, String type) {
		String message = type + "|" + roomId + "|" + System.currentTimeMillis();
		LogUtil.log("OPENFIRE.MESSAGE", message);
		
		JSONObject object = new JSONObject();
		object.put("type", type);
		
		return sendMessage(imIp, roomId, conference, object.toJSONString());
	}

	/**
	 * 发送房间消息
	 * 
	 * @param roomName
	 * @param msg
	 * @return
	 */
	public static Boolean sendMessage(String imIp, String roomName, String conference, String msg) {
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		// 令牌
		HttpUtil.addParams(params, "token", OpenFireConstant.PLUGINS_TOKEN);
		// 服务方法名称
		HttpUtil.addParams(params, "method", "groupchatMessage");
		// 参数
		HttpUtil.addParams(params, "from", OpenFireConstant.ADMIN_NAME + "|0|0");
		HttpUtil.addParams(params, "to", roomName + "@" + conference);
		HttpUtil.addParams(params, "body", msg);
		
//		HttpUtil.addParams(params, OpenFireConstant.MSG_TYPE, type.toString());
//		HttpUtil.addParams(params, OpenFireConstant.MSG_DATA, data);
//		HttpUtil.addParams(params, OpenFireConstant.MSG_FORMNAME, "系统消息");
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
	 * 获取房间最近消息(用户类型，昵称，消息，时间)
	 * @param imIp
	 * @param name
	 * @param length
	 * @return
	 */
	public static List<Object[]> getMessage(String imIp, String name, String length){
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		// 令牌
		HttpUtil.addParams(params, "token", OpenFireConstant.PLUGINS_TOKEN);
		// 服务方法名称
		HttpUtil.addParams(params, "method", "getMessage");
		// 参数
		HttpUtil.addParams(params, "name", name);
		HttpUtil.addParams(params, "length", length);

		List<Object[]> data = new ArrayList<Object[]>();
		try {
			String url = String.format(OpenFireConstant.PLUGINS_URL, imIp);
			String result = HttpUtil.handlePost(url, params);
//			result = URLDecoder.decode(result, "utf-8");
			Map<String, Object> map = JSON.parseObject(result, Map.class);
			
			if (map.get("data") instanceof JSONArray) {
				JSONArray array = (JSONArray)map.get("data");
				for (Object o : array) {
					Object[] msg = ((JSONArray)o).toArray();
					msg[1] = URLDecoder.decode(msg[1].toString(), "utf-8");
					msg[2] = URLDecoder.decode(msg[2].toString(), "utf-8");
					data.add(msg);
				}
			}
			return data;
		} catch (Exception e) {
			return data;
		}
	}
}
