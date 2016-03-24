package com.yaowang.util.mt;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.yaowang.common.util.LogUtil;
import com.yaowang.service.impl.SysOptionServiceImpl;
import com.yaowang.util.http.HttpUtil;

/**
 * 企信通平台
 * @author shenlie
 *
 */
public class MTQixinUtil {
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
	static{
		refresh();
	}
	
	/**
	 * 刷新配置
	 */
	public static void refresh(){
		MT_SERVER = SysOptionServiceImpl.getValue("SYS.MT.SERVER");
		MT_OPEN_ID = SysOptionServiceImpl.getValue("SYS.MT.OPEN_ID");
		MT_OPEN_PASS = SysOptionServiceImpl.getValue("SYS.MT.OPEN_PASS");
	}

	/**
	 * 发送消息
	 * @param content
	 * @param mobile
	 * @return
	 * @throws Exception 
	 */
	public static String sendMt(String content, String mobile) throws Exception{
		//组装url
		StringBuilder builder = builderUrl(content, mobile);
//		//发送消息
//		String xml = callServer(builder);
		//发送消息,解析返回值
		String code = analysisXml(builder);
		
		StringBuffer log = new StringBuffer();
		if ("11".equals(code)) {
			//余额不足
			log.append("余额不足");
		}else if ("05".equals(code)) {
			//密码错误
			log.append("密码错误！username = ").append(MT_OPEN_ID).append(",passpowrd=").append(MT_OPEN_PASS);
		}else if ("04".equals(code)) {
			//用户名错误
			log.append("用户名错误！username = ").append(MT_OPEN_ID).append(",passpowrd=").append(MT_OPEN_PASS);
		}else if ("03".equals(code) || "00".equals(code)) {
			//发送成功
			
			return null;
		}else {
			//未知错误
			log.append("未知错误，code=").append(code);
		}
		//记录日志
		LogUtil.log("MT_LOG", log.toString(), builder.toString());
		
		return log.toString();
	}
	/**
	 * 组装url
	 * @param content
	 * @param mobile
	 * @return
	 * @throws UnsupportedEncodingException 
	 */
	private static StringBuilder builderUrl(String content, String mobile) throws UnsupportedEncodingException{
		//事例
		//http://221.179.180.158:9007/QxtSms/QxtFirewall?
		//OperID=test&OperPass=test&SendTime=&ValidTime=&AppendID=1234&DesMobile=13900000000&Content=%D6%D0%CE%C4%B6%CC%D0%C5abc&ContentType=8
		
		//内容转码
		content = URLEncoder.encode(content, "GBK");
		StringBuilder builder = new StringBuilder();
		builder.append(MT_SERVER).append("/QxtSms/QxtFirewall?SendTime=&ValidTime=&AppendID=&ContentType=8")
		.append("&OperID=").append(MT_OPEN_ID).append("&OperPass=").append(MT_OPEN_PASS).
		append("&DesMobile=").append(mobile).append("&Content=").append(content);
		
		return builder;
	}
	/**
	 * 查询余额
	 * @return
	 * @throws Exception 
	 * @throws  
	 */
	public static Double getStock() throws Exception{
		String url = String.format("http://221.179.180.158:8081/QxtSms_surplus/surplus?OperID=%s&OperPass=%s", MT_OPEN_ID, MT_OPEN_PASS);
		String stock = HttpUtil.handleGet(url, 20);
		return Double.valueOf(analysisXmlStock(stock));
	}
	/**
	 * 发送消息
	 * @param builder
	 * @return
	 * @throws Exception 
	 */
//	private static String callServer(StringBuilder builder) throws Exception{
//		StringBuilder res = new StringBuilder();
//		URL url = new URL(builder.toString());
//		HttpURLConnection connection = (HttpURLConnection) url.openConnection();
//		connection.setConnectTimeout(5000);
//		connection.setRequestMethod("GET");
//		int code = connection.getResponseCode();
//		if (code == 200) {
//			//发送成功（读取返回值）
//			InputStream io = connection.getInputStream();
//			byte[] bs = new byte[1024];
//			int length = 0;
//			while ((length = io.read(bs)) != -1) {
//				res.append(new String(bs, 0, length));
//			}
//		}
//		return res.toString();
//	}
	private static String analysisXmlStock(String builder) throws Exception{
		//<?xml version="1.0" encoding="GBK"?><resRoot><rcode>19951</rcode></resRoot>
		String key = "<rcode>";
		int index = builder.indexOf(key);
		if (index > -1) {
			builder = builder.substring(index + key.length()).trim();
			Integer end = builder.indexOf("<");
			String stock = builder.substring(0, end).trim();
			return stock;
		}
		return "";
	}
	
	/**
	 * 解析xml
	 * @param xml
	 * @return
	 * @throws Exception 
	 */
	private static String analysisXml(StringBuilder builder) throws Exception{
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance(); 
		DocumentBuilder db = dbf.newDocumentBuilder();
		Document document = db.parse(builder.toString());
		NodeList employees = document.getChildNodes(); 
		for (int i = 0; i < employees.getLength(); i++) { 
			Node employee = employees.item(i); 
			NodeList employeeInfo = employee.getChildNodes(); 
			for (int j = 0; j < employeeInfo.getLength(); j++) { 
				Node node = employeeInfo.item(j); 
				if ("code".equals(node.getNodeName())) {
					NodeList employeeMeta = node.getChildNodes();
					return employeeMeta.item(0).getTextContent();
				}
			} 
		} 
		return "";
	}
	
	public static void main(String[] args) throws Exception {
//		sendMt("【点趣网络】您的验证码：******，有效期为5分钟。", "15869117147");
		sendMt("【点趣网络】您的验证码：******，有效期为5分钟。", "18758198520");
	}
}
