package com.yaowang.util.mt;

import java.util.HashMap;
import java.util.Map;

import com.sun.star.uno.RuntimeException;
import com.yaowang.service.impl.SysOptionServiceImpl;
import com.yaowang.util.http.HttpUtil;

/**
 * 创蓝短信接口
 * @author shenlie
 *
 */
public class MTLanchuangUtil {
	/**
	 * 短信发送服务器
	 */
	private static String MT_SERVER = "";
	/**
	 * 短信服务账户
	 */
	private static String MT_OPEN_ID = "";
	/**
	 * 短信服务密码
	 */
	private static String MT_OPEN_PASS = "";
	/**
	 * 错误码
	 */
	private static Map<String, String> errorMap = new HashMap<String, String>();
	static{
		refresh();
//		errorMap.put("0", "提交成功");
		errorMap.put("101", "无此用户");
		errorMap.put("102", "密码错误");
		errorMap.put("103", "提交过快（提交速度超过流速限制）");
		errorMap.put("104", "系统忙（因平台侧原因，暂时无法处理提交的短信）");
		errorMap.put("105", "敏感短信（短信内容包含敏感词）");
		errorMap.put("106", "消息长度错（>536或<=0）");
		errorMap.put("107", "包含错误的手机号码");
		errorMap.put("108", "手机号码个数错（群发>50000或<=0;单发>200或<=0）");
		errorMap.put("109", "无发送额度（该用户可用短信数已使用完）");
		errorMap.put("110", "不在发送时间内");
		errorMap.put("111", "超出该账户当月发送额度限制");
		errorMap.put("112", "无此产品，用户没有订购该产品");
		errorMap.put("113", "extno格式错（非数字或者长度不对）");
		errorMap.put("115", "自动审核驳回");
		errorMap.put("116", "签名不合法，未带签名（用户必须带签名的前提下）");
		errorMap.put("117", "IP地址认证错,请求调用的IP地址不是系统登记的IP地址");
		errorMap.put("118", "用户没有相应的发送权限");
		errorMap.put("119", "用户已过期");
		errorMap.put("120", "短信内容不在白名单中");
	}
	
	/**
	 * 刷新配置
	 */
	public static void refresh(){
		MT_SERVER = SysOptionServiceImpl.getValue("SYS.MT.SERVER.LC");
		MT_OPEN_ID = SysOptionServiceImpl.getValue("SYS.MT.OPEN_ID.LC");
		MT_OPEN_PASS = SysOptionServiceImpl.getValue("SYS.MT.OPEN_PASS.LC");
	}
	/**
	 * 发送消息
	 * @param content
	 * @param mobile
	 * @return
	 * @throws Exception 
	 */
	public static String sendMt(String content, String mobile) throws Exception{
		String str = String.format(MT_SERVER + "/msg/HttpBatchSendSM?account=%s&pswd=%s&mobile=%s&msg=%s&needstatus=true", MT_OPEN_ID, MT_OPEN_PASS, mobile, content);
		String respstatus = HttpUtil.handleGet(str, 20);
		String[] strs = respstatus.split("\n");
		String status = strs[0].split(",")[1];
		if ("0".equals(status)) {
			//发送成功
			return null;
		}else if(errorMap.containsKey(status)){
			return errorMap.get(status);
		}else {
			return "发送失败";
		}
	}
	
	/**
	 * 查询余额
	 * @return
	 * @throws Exception 
	 * @throws  
	 */
	public static Double getStock() throws Exception{
		String url = String.format(MT_SERVER + "/msg/QueryBalance?account=%s&pswd=%s", MT_OPEN_ID, MT_OPEN_PASS);
		String respstatus = HttpUtil.handleGet(url, 20);
		String[] strs = respstatus.split("\n");
		String status = strs[0].split(",")[1];
		if ("0".equals(status)) {
			//发送成功
			return Double.valueOf(strs[1].split(",")[1]);
		}else if(errorMap.containsKey(status)){
			throw new RuntimeException(errorMap.get(status));
		}else {
			throw new RuntimeException("发送失败");
		}
	}

	public static void main(String[] args) {
		try {
//			System.out.println(sendMt("您好，您的验证码是123456", "15869117147"));
			System.out.println(getStock());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
