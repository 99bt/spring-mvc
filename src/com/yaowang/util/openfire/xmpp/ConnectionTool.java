package com.yaowang.util.openfire.xmpp;
import java.io.IOException;

import javax.security.sasl.SaslException;

import org.jivesoftware.smack.ConnectionConfiguration;
import org.jivesoftware.smack.ConnectionConfiguration.SecurityMode;
import org.jivesoftware.smack.SmackException;
import org.jivesoftware.smack.XMPPConnection;
import org.jivesoftware.smack.XMPPException;
import org.jivesoftware.smack.tcp.XMPPTCPConnection;

import com.yaowang.util.openfire.OpenFireConstant;

public class ConnectionTool {
	/**
	 * 打开连接
	 * @return
	 * @throws Exception
	 */
	public static XMPPConnection getConnection() throws Exception {
		// 声明XMPP连接 ConnectionConfiguration类的三个参数分别为ip地址，端口号
		ConnectionConfiguration config = new ConnectionConfiguration(OpenFireConstant.IP, OpenFireConstant.PORT);
		//ssl验证
		config.setSecurityMode(SecurityMode.disabled);
		//压缩
		config.setCompressionEnabled(true);
		//调试
		config.setDebuggerEnabled(false);
		XMPPConnection con = new XMPPTCPConnection(config);
		// 开始连接
		con.connect();
		return con;
	}
	/**
	 * 关闭
	 * @param con
	 * @throws Exception
	 */
	public static void closeConnection(XMPPConnection con) throws Exception {
		if (con != null && con.isConnected()) {
			con.disconnect();
		}
	}
	/**
	 * 登录
	 * @param con
	 * @return
	 * @throws IOException 
	 * @throws SmackException 
	 * @throws XMPPException 
	 * @throws SaslException 
	 */
	public static Boolean logion(XMPPConnection con) throws SaslException, XMPPException, SmackException, IOException {
		con.login(OpenFireConstant.ADMIN_NAME, OpenFireConstant.ADMIN_PWD);
		return true;
	}
}