package com.yaowang.util.gs;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import com.yaowang.service.impl.SysOptionServiceImpl;
import com.yaowang.util.MD5;
import com.yaowang.util.http.HttpUtil;

public class WsliveUtil {
	/**
	 * 时间格式化
	 */
	private final static SimpleDateFormat FORMAT = new SimpleDateFormat("yyyyMMddHHmmss");
	
	/**
	 * 获取流的状态
	 * @return
	 * @throws Exception 
	 */
	public static String[] getMasterState() throws Exception{
		//地址
		String url = SysOptionServiceImpl.getValue("FMS.CALL.URL");
		if (StringUtils.isEmpty(url)) {
			//地址为空，则不请求
			return null;
		}
		//key
		String key = SysOptionServiceImpl.getValue("FMS.CALL.KEY");
		
		//获取r
		Date now = new Date();
		//查询十分钟前数据
		Date bef = new Date(now.getTime() - 10 * 60 * 1000);
		StringBuffer r = new StringBuffer(bef.getTime()+"");
		r.setLength(10);
		//md5加密
		String k = r.toString() + key;
		k = MD5.encrypt(k);
		//时间
		String t = FORMAT.format(bef);
		//组装数据
		url = url.replaceFirst("#t#", t);
		url = url.replaceFirst("#r#", r.toString());
		url = url.replaceFirst("#k#", k);
		String data = HttpUtil.handleGet(url);
		
		return new String[]{data, url };
	}
	
	/**
	 * 取消禁播流
	 * @return
	 * @throws Exception 
	 */
	public static String liveOpenUtil(String roomUrl) throws Exception{
		return liveUtil("-1", roomUrl);
	}
	/**
	 * 禁播流
	 * @return
	 * @throws Exception
	 */
	public static String liveCloseUtil(String roomUrl) throws Exception{
		return liveUtil("1", roomUrl);
	}
	
	private static String liveUtil(String type, String roomUrl) throws Exception{
		String url = "http://portal.gosun.com/api/closestream?user_id=%s&vhost=%s&app=%s&stream=%s&action=%s&sign=%s";
		//用户名
		url = String.format(url, "lanstv");
		//密码
		url = String.format(url, "123.com");
		
		String[] strs = getRtmpUrl(roomUrl);
		//vhost
		url = String.format(url, strs[0]);
		//app
		url = String.format(url, strs[1]);
		//stream
		url = String.format(url, strs[2]);
		//action
		url = String.format(url, type);
		//sign
		
		
		String data = HttpUtil.handleGet(url);
		return data;
	}

	/**
	 * 直播流快照
	 * @param rtmp
	 * @param live
	 * @throws Exception 
	 */
	public static String liveToPic(String rtmp, String live) throws Exception{
		if (StringUtils.isEmpty(rtmp) || StringUtils.isEmpty(live)) {
			return null;
		}
		rtmp = rtmp.substring("rtmp://".length());
		rtmp = rtmp.substring(0, rtmp.indexOf("/"));
		
		String url = "http://" + rtmp + "/live/" + live;
		return HttpUtil.handleGet(url);
	}
	
	/**
	 * 拆分流地址
	 * @param roomUrl
	 * @return
	 */
	private static String[] getRtmpUrl(String roomUrl) {
		List<String> gs = new ArrayList<String>();
		String[] s = roomUrl.split("/");
		gs.add(s[2]);
		gs.add(s[3]);
		gs.add(s[4]);
		return gs.toArray(new String[]{});
	}
	
	public static void main(String[] args) throws Exception {
//		System.out.println(liveOpenUtil("rtmp://live.98u.com/live/live-10004"));;
//		liveToPic("rtmp://live.98u.com/live", "live-10004");
//		liveCloseUtil("rtmp://live.9d6d.com/live/test6311");
		System.out.println(Arrays.toString(getRtmpUrl("rtmp://live.9d6d.com/live/test6311")));
	}
}
