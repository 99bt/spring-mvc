package com.yaowang.util.ws;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.lang.StringUtils;

import com.yaowang.service.impl.SysOptionServiceImpl;
import com.yaowang.util.MD5;
import com.yaowang.util.http.HttpUtil;

/**
 * 流的工具
 * @author shenl
 *
 */
public class WsLiveUtil {
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
		return liveUtil("resume", roomUrl);
	}
	/**
	 * 禁播流
	 * @return
	 * @throws Exception
	 */
	public static String liveCloseUtil(String roomUrl) throws Exception{
		return liveUtil("forbid", roomUrl);
	}
	
	private static String liveUtil(String type, String roomUrl) throws Exception{
		String url = "http://cm.chinanetcenter.com/CM/cm-command.do?username=#username#&password=#password#&cmd=channel_manager&action=#action#&type=publish&channel=#url#";
		//用户名
		String user = "lansha";
		url = url.replaceFirst("#username#", user);
		//密码
		String password = user + "Lansha2015" + roomUrl;
		password = MD5.encrypt(password);
		url = url.replaceFirst("#password#", password);
		
		url = url.replaceFirst("#url#", roomUrl);
		url = url.replaceFirst("#action#", type);
//		System.out.println(url);
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
	
	public static void main(String[] args) throws Exception {
//		System.out.println(liveOpenUtil("rtmp://live.98u.com/live/live-10004"));;
//		liveToPic("rtmp://live.98u.com/live", "live-10004");
		liveCloseUtil("rtmp://live.9d6d.com/live/test6311");
	}
}
