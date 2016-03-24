package com.yaowang.util.openfire.xmpp;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.jivesoftware.smack.AccountManager;
import org.jivesoftware.smack.PacketCollector;
import org.jivesoftware.smack.XMPPConnection;
import org.jivesoftware.smack.filter.AndFilter;
import org.jivesoftware.smack.filter.PacketFilter;
import org.jivesoftware.smack.filter.PacketIDFilter;
import org.jivesoftware.smack.filter.PacketTypeFilter;
import org.jivesoftware.smack.packet.IQ;
import org.jivesoftware.smack.packet.Registration;

public class UserXmppTool {
	public static void main(String[] args) throws Exception {
//		Object type = register("test1", UUIDUtils.newId(), "测试");
//		Object type = modifyPwd("test", "123456");
		Object type = deleteAccount("snle1", "123456");
		System.out.println(type);
	}
	
//	public static String register(YwUsers users){
//		if (StringUtils.isEmpty(users.getToken())) {
//			users.setToken(BaseConstant.ZERO_GUID);
//		}
//		return register(String.valueOf(users.getId()), users.getToken(), users.getNickname());
//	}
	
	/**
	 * 注册用户
	 * @param username
	 * @param pwd
	 * @param name
	 * @return
	 */
	public static String register(String username, String pwd, String name) {
		if (StringUtils.isEmpty(pwd)) {
			pwd = "";
		}
		//可以直接改登陆用户的信息(如果是username的值必须和该用户的用户名相同)
		Registration r = new Registration();
		Map<String, String> attributes = new HashMap<String, String>();
		attributes.put("username", username);
		attributes.put("password", pwd);
//		attributes.put("email", "new00@126.com");
		attributes.put("name", name);
		//添加用户，要设置type类型为set，原因不明
		r.setType(IQ.Type.SET);
		r.setAttributes(attributes);
		//过滤器，用来过滤由服务器返回的信息（即得到注册信息的内容）
		PacketFilter packetFilter = new AndFilter(new PacketIDFilter(r.getPacketID()), new PacketTypeFilter(IQ.class));
		XMPPConnection conn = null;
		try {
			conn = ConnectionTool.getConnection();
			PacketCollector collector = conn.createPacketCollector(packetFilter); 
			conn.sendPacket(r);
			//返回结果
			IQ result = (IQ) collector.nextResult();
			if(result == null) {
				return "error";
			} else {
				String type = result.getType().toString();
				
				if("result".equalsIgnoreCase(type)) {
					return "success";
				}else if ("error".equalsIgnoreCase(type) && result.getError().toString().equalsIgnoreCase("conflict")) {
					//用户名已存在
					return "conflict";
				}else {
					return result.getError().toString();
				}
			}
		}catch(Exception e){
			e.printStackTrace();
			return "error";
		}finally{
			try {
				ConnectionTool.closeConnection(conn);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	/**
	 * 修改密码
	 * @return
	 */
	public static Boolean modifyPwd(String username, String pwd, String npwd){
		XMPPConnection conn = null;
		try {
			conn = ConnectionTool.getConnection();
			//登录
			conn.login(username, pwd);
			
			AccountManager am = AccountManager.getInstance(conn);
			am.changePassword(npwd);
		}catch(Exception e){
			return false;
		}finally{
			try {
				ConnectionTool.closeConnection(conn);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return true;
	}
	
	/**
	 * 删除当前用户
	 * 
	 * @param connection
	 * @return
	 */
	public static boolean deleteAccount(String username, String pwd) {
		XMPPConnection conn = null;
		try {
			conn = ConnectionTool.getConnection();
			//登录
			conn.login(username, pwd);
			
			AccountManager am = AccountManager.getInstance(conn);
			am.deleteAccount();
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}finally{
			try {
				ConnectionTool.closeConnection(conn);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return true;
	}
}
