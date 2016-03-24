package com.yaowang.util.email;

import java.util.Date;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.apache.commons.lang.StringUtils;

import com.yaowang.common.action.BaseDataAction;
import com.yaowang.entity.LogEmail;
import com.yaowang.service.LogEmailService;
import com.yaowang.service.impl.SysOptionServiceImpl;
import com.yaowang.util.spring.ContainerManager;

public class EmailUtil {
	/**
	 * smtp
	 */
	private static String SMTP = SysOptionServiceImpl.getValue("SYS.EMAIL.SMTP");
	/**
	 * 游戏账户
	 */
	private static String USER = SysOptionServiceImpl.getValue("SYS.EMAIL.USER");
	/**
	 * 邮箱密码
	 */
	private static String PASSWORD = SysOptionServiceImpl.getValue("SYS.EMAIL.PASSWORD");
	/**
	 * 网站名称
	 */
	private static String WEB_TEXT = BaseDataAction.getPlatFormNameStatic();
//	/**
//	 * smtp
//	 */
//	private static String SMTP = "smtp.qq.com";
//	/**
//	 * 游戏账户
//	 */
//	private static String USER = "2f@ywwl.com";
//	/**
//	 * 邮箱密码
//	 */
//	private static String PASSWORD = "dianqu123";
//	/**
//	 * 网站名称
//	 */
//	private static String WEB_TEXT = "test";
	
	/**
	/**
	 * 刷新配置
	 */
	public static void refresh(){
		SMTP = SysOptionServiceImpl.getValue("SYS.EMAIL.SMTP");
		USER = SysOptionServiceImpl.getValue("SYS.EMAIL.USER");
		PASSWORD = SysOptionServiceImpl.getValue("SYS.EMAIL.PASSWORD");
		WEB_TEXT = BaseDataAction.getPlatFormNameStatic();
	}

	/**
	 * 发送邮件
	 * @param email
	 * @param title
	 * @param content
	 * @throws Exception 
	 */
	public static void sendEmail(String title, String content, String...mails) throws Exception {
		LogEmail logEmail = new LogEmail();
		logEmail.setEmail(toString(mails));
		logEmail.setTime(new Date());
		logEmail.setTitle(title);
		logEmail.setContent(content);
		try {
			//发送
			sendEmail2(title, content, mails);
			logEmail.setStatus("0");
			//保存日志
			LogEmailService service = (LogEmailService)ContainerManager.getComponent("logEmailService");
			service.save(logEmail);
		} catch (Exception e) {
			//出错
			logEmail.setStatus("2");
			logEmail.setError(e.getMessage());
			//保存日志
			LogEmailService service = (LogEmailService)ContainerManager.getComponent("logEmailService");
			service.save(logEmail);
			
			throw e;
		}
	}
	
	public static void sendEmail2(String title, String content, String...mails) throws Exception{
		String host = SMTP; // smtp服务器
		String from = USER; // 发件人地址
		String username = USER; // 用户名
		String password = PASSWORD; // 密码
		Boolean isdebug = false; // 是否输出调试信息
		String webText = WEB_TEXT;
		
		Properties props = new Properties();
		// 设置发送邮件的邮件服务器的属性
		props.put("mail.smtp.host", host);
		props.put("mail.smtp.port", "25");// 发送邮件的端口
		// 需要经过授权，也就是有户名和密码的校验，这样才能通过验证（一定要有这一条）
		props.put("mail.smtp.auth", "true");
		// 用刚刚设置好的props对象构建一个session
		Session session = Session.getDefaultInstance(props);
		// 有了这句便可以在发送邮件的过程中在console处显示过程信息，供调试使
		// 用（你可以在控制台（console)上看到发送邮件的过程）
		session.setDebug(isdebug);
		// 用session为参数定义消息对象
		MimeMessage message = new MimeMessage(session);
		// 加载发件人地址
		message.setFrom(new InternetAddress(from, webText));
		// 加载标题
		message.setSubject(title);
		message.setContent(content, "text/html;charset=utf-8");
		// 保存邮件
		message.saveChanges();
		// 加载收件人地址
		for (int i = 0; i < mails.length; i++) {
			if (StringUtils.isEmpty(mails[i])) {
				continue;
			}
			message.addRecipient(Message.RecipientType.TO, new InternetAddress(mails[i]));
		}
		// 发送邮件
		Transport transport = session.getTransport("smtp");

		try {
			// 连接服务器的邮箱
			transport.connect(host, username, password);
			// 把邮件发送出去
			transport.sendMessage(message, message.getAllRecipients());
		}finally {
			transport.close();
		}
	}
	/**
	 * 根据邮箱地址获取邮箱域名
	 * @param email
	 * @return
	 */
	public static String getEmailHost(String email){
		if (StringUtils.isEmpty(email) || email.indexOf('@') < 0) {
			return email;
		}
		
		String host = "http://mail." + email.substring(email.indexOf("@") + 1, email.length());
		return host;
	}
	
	private static String toString(String[] mails){
		StringBuilder builder = new StringBuilder();
		if (mails.length > 0) {
			for (String string : mails) {
				builder.append(string).append(",");
			}
			builder.setLength(builder.length() - 1);
		}
		return builder.toString();
	}
	
	public static void main(String[] args) throws Exception {
		sendEmail2("test", "test", "snle@qq.com");
	}
}
