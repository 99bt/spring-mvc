package com.yaowang.util.mt;

import java.util.HashMap;
import java.util.Map;
import net.sf.json.JSONObject;
import com.yaowang.service.impl.SysOptionServiceImpl;
import com.yaowang.util.AESencryptionUtil;
import com.yaowang.util.http.HttpUtil;

public class MTMaychooUtil {
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
//		errorMap.put(0, "提交成功");
		errorMap.put("-4", "系统错误");
		errorMap.put("-1", "用户或密码错误");
		errorMap.put("-7", "验证码错误");
		errorMap.put("-2", "余额不足");
		errorMap.put("-3", "超日流量或欠费");
		errorMap.put("-6", "超出单次最大提交数（5000）");
		errorMap.put("-10", "消息内容不能为空字");
		errorMap.put("-11", "消息内容不能超过300");
		errorMap.put("-12", "短信提交批次不能为空");
	}
	
	/**
	 * 刷新配置
	 */
	public static void refresh(){
		MT_SERVER = SysOptionServiceImpl.getValue("SYS.MAYCHOO.SERVER");
		MT_OPEN_ID = SysOptionServiceImpl.getValue("SYS.MAYCHOO.OPEN_ID");
		MT_OPEN_PASS = SysOptionServiceImpl.getValue("SYS.MAYCHOO.OPEN_PASS");
	}
	
	/**
	 * 发送消息
	 * @param content
	 * @param mobile (发送的手机号码，多号码用半角逗号分隔，最多不超过500个)
	 * @return
	 * @throws Exception 
	 */
	public static String sendMt(String content, String mobile) throws Exception{
//		String MT_SERVER = "http://sms.maychoo.com";
//		String MT_OPEN_ID = "lansha";
//		String MT_OPEN_PASS = "lanshatv123456";
		JSONObject object = new JSONObject();
		object.put("username", MT_OPEN_ID);
		object.put("password", MT_OPEN_PASS);
		String pk = AESencryptionUtil.encrypt(object.toString());
		String str = String.format(MT_SERVER + "/home/sms/send?username=%s&pk=%s&mobile=%s&content=%s", MT_OPEN_ID, pk, mobile, content);
		String respstatus = HttpUtil.handleGet(str, 20);
		JSONObject obj = JSONObject.fromObject(respstatus);
		String status = (String) obj.get("code");
		if ("0".equals(status.trim())) {
			//发送成功
			return null;
		}else if(errorMap.containsKey(status)){
			return errorMap.get(status);
		}else {
			return "发送失败";
		}
	}
	
	/**
	 * 查询短信余量
	 * @return
	 * @throws Exception 
	 * @throws  
	 */
	public static Integer getStock() throws Exception{
//		String MT_SERVER = "http://sms.maychoo.com";
//		String MT_OPEN_ID = "lansha";
//		String MT_OPEN_PASS = "lanshatv123456";
		JSONObject object = new JSONObject();
		object.put("username", MT_OPEN_ID);
		object.put("password", MT_OPEN_PASS);
		String pk = AESencryptionUtil.encrypt(object.toString());
		String str = String.format(MT_SERVER + "/index.php?s=/Home/Sms/getBalance/username/%s/pk/%s", MT_OPEN_ID, pk);
		String respstatus = HttpUtil.handleGet(str, 20);
		JSONObject obj = JSONObject.fromObject(respstatus);
		String status = (String) obj.get("code");
		if ("0".equals(status.trim())) {
			//发送成功
			return Integer.parseInt((String)obj.get("msg"));
		}
		return 0;
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
