package com.yaowang.util.openfire;

import com.yaowang.service.impl.SysOptionServiceImpl;


/**
 * 常量
 * 
 * @author shenl
 *
 */
public class OpenFireConstant {
	/**
	 * openfire地址
	 */
	public static String IP = "192.168.0.172";
//	public static String IP = "127.0.0.1";
	/**
	 * openfire端口
	 */
	public static Integer PORT = 5222;
	/**
	 * 管理员
	 */
	public static String ADMIN_NAME = "admin";
	/**
	 * 管理员密码
	 */
	public static String ADMIN_PWD = "admin";
	/**
	 * 域名
	 */
	public static String DOMAIN = "0.0.0.0";
//	public static String DOMAIN = "shenl";
	
	/**
	 * 聊天室
	 */
	public static String CONFERENCE = "conference." + DOMAIN;
	public static String CHATGROUP_CONFERENCE = "chatgroup." + DOMAIN;
	
	/**
	 * OpenFire插件调用地址
	 */
	public static String PLUGINS_URL = "http://%s:9090/plugins/yaowangchat/service/chat.html";

	/**
	 * 消息类型
	 */
	public static final String MSG_TYPE = "msg_type";
	public static final String MSG_DATA = "msg_data";
	public static final String MSG_MESSAGE = "msg";
	public static final String MSG_FORMNAME = "msg_formname";

	public static final int MSG_TEXT_TYPE = 1;
	public static final int MSG_PIC_TYPE = 2;
	public static final int MSG_SOUND_TYPE = 3;
	public static final int MSG_SHARE_TYPE = 4;
	public static final int MSG_FILE_TYPE = 5;

	// 系统点赞
	public static final int MSG_SYSTEM_TYPE_1 = 6;
	// 系统评论
	public static final int MSG_SYSTEM_TYPE_2 = 7;
	// 系统关注
	public static final int MSG_SYSTEM_TYPE_3 = 8;

	// OpenFire插件调用地址
	public static final String PLUGINS_TOKEN = "123456";

	static {
		try {
			// 加载properties文件
			IP = SysOptionServiceImpl.getValue("OPENFIRE.URL");
			PORT = Integer.valueOf(SysOptionServiceImpl.getValue("OPENFIRE.PORT"));
			ADMIN_NAME = SysOptionServiceImpl.getValue("OPENFIRE.ADMIN.USER");
			ADMIN_PWD = SysOptionServiceImpl.getValue("OPENFIRE.ADMIN.PASSWORD");
			CONFERENCE = SysOptionServiceImpl.getValue("OPENFIRE.CONFERENCE");
			// 截取域名
			DOMAIN = CONFERENCE.substring(CONFERENCE.indexOf(".") + 1, CONFERENCE.length());
			CHATGROUP_CONFERENCE = "chatgroup." + DOMAIN;
			
//			PLUGINS_URL = "http://" + IP + ":9090/plugins/yaowangchat/service/chat.html";
		} catch (Exception e) {
			
		}
	}
}
