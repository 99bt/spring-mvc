package com.yaowang.util.openfire.http;

import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.http.NameValuePair;

import com.alibaba.fastjson.JSON;
import com.yaowang.common.constant.BaseConstant;
import com.yaowang.util.http.HttpUtil;
import com.yaowang.util.openfire.OpenFireConstant;

public class OfficiaUserTool {
	/**
	 * 清除房管缓存
	 * @param imIp
	 * @param name
	 * @return
	 */
	public static Boolean clearOfficialUser(String imIp){
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		// 令牌
		HttpUtil.addParams(params, "token", OpenFireConstant.PLUGINS_TOKEN);
		// 服务方法名称
		HttpUtil.addParams(params, "method", "clearOfficialUser");
		
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
