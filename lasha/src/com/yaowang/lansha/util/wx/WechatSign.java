package com.yaowang.lansha.util.wx;

import java.security.MessageDigest;
import java.util.Formatter;
import java.util.UUID;

import com.yaowang.lansha.entity.WechatJsJdkParams;

public class WechatSign {

	/**
	 * @param wxParams 微信js接口参数
	 * @throws Exception
	 */
	public static void sign(WechatJsJdkParams wxParams) throws Exception {
		if (wxParams == null) {
			wxParams = new WechatJsJdkParams();
		}
		String signature = "";// 生成最终的签名
		StringBuilder tmpStr = new StringBuilder();// 参与签名算法的字符串汇总
		wxParams.setNoncestr(create_nonce_str());
		wxParams.setTimestamp(create_timestamp());
		// 注意这里参数名必须全部小写，且必须有序
		tmpStr.append("jsapi_ticket=");
		tmpStr.append(wxParams.getJsapiTicket());
		tmpStr.append("&noncestr=");
		tmpStr.append(wxParams.getNoncestr());
		tmpStr.append("&timestamp=");
		tmpStr.append(wxParams.getTimestamp());
		tmpStr.append("&url=");// 注意 URL 一定要动态获取，不能 hardcode
		tmpStr.append(wxParams.getSignURL());

		try {
			MessageDigest crypt = MessageDigest.getInstance("SHA-1");
			crypt.reset();
			crypt.update(tmpStr.toString().getBytes("UTF-8"));
			signature = byteToHex(crypt.digest());
		} catch (Exception e) {
			throw e;
		}
		wxParams.setSignature(signature);
	}

	private static String byteToHex(final byte[] hash) {
		Formatter formatter = new Formatter();
		for (byte b : hash) {
			formatter.format("%02x", b);
		}
		String result = formatter.toString();
		formatter.close();
		return result;
	}

	private static String create_nonce_str() {
		return UUID.randomUUID().toString();
	}

	private static String create_timestamp() {
		return Long.toString(System.currentTimeMillis() / 1000);
	}
}
