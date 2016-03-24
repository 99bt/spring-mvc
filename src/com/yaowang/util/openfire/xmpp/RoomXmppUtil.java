package com.yaowang.util.openfire.xmpp;

import java.util.ArrayList;
import java.util.List;

import org.jivesoftware.smack.XMPPConnection;
import org.jivesoftware.smackx.muc.MultiUserChat;
import org.jivesoftware.smackx.xdata.Form;

import com.yaowang.util.openfire.OpenFireConstant;

public class RoomXmppUtil {
	/**
	 * 创建房间
	 * 
	 * @return
	 */
	public static Boolean createRoom(String user, String roomName, String conference) {
		XMPPConnection conn = null;
		try {
			conn = ConnectionTool.getConnection();
			// 登陆
			ConnectionTool.logion(conn);

			// 群聊
			MultiUserChat muc = new MultiUserChat(conn, roomName + "@" + conference);
			muc.create(roomName);
			// 参数设置
			Form form = muc.getConfigurationForm();
			Form submitForm = form.createAnswerForm();
			// 向要提交的表单添加默认答复
			// for (Iterator<FormField> fields = form.getFields();
			// fields.hasNext();) {
			// FormField field = (FormField) fields.next();
			// if (!FormField.TYPE_HIDDEN.equals(field.getType())
			// && field.getVariable() != null) {
			// // 设置默认值作为答复
			// submitForm.setDefaultAnswer(field.getVariable());
			// }
			// }

			// 设置聊天室的新拥有者
			List<String> owners = new ArrayList<String>();
			// 用户JID
			owners.add(conn.getUser());
			submitForm.setAnswer("muc#roomconfig_roomowners", owners);

			// 设置聊天室的管理员
			List<String> admins = new ArrayList<String>();
			// 用户JID
			admins.add(user + "@" + OpenFireConstant.DOMAIN);
			submitForm.setAnswer("muc#roomconfig_roomadmins", admins);

			// 设置聊天室是持久聊天室，即将要被保存下来
			submitForm.setAnswer("muc#roomconfig_persistentroom", true);
			// 最大人数
			List<String> maxusers = new ArrayList<String>();
			maxusers.add("0");
			submitForm.setAnswer("muc#roomconfig_maxusers", maxusers);
			// 进入是否需要密码
			submitForm.setAnswer("muc#roomconfig_passwordprotectedroom", false);
			// 私有的房间
			submitForm.setAnswer("muc#roomconfig_publicroom", true);

			muc.sendConfigurationForm(submitForm);
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		} finally {
			try {
				ConnectionTool.closeConnection(conn);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return true;
	}

	/**
	 * 销毁房间
	 * 
	 * @return
	 */
	public static Boolean destroyRoom(String roomName, String conference) {
		XMPPConnection conn = null;
		try {
			conn = ConnectionTool.getConnection();
			// 登陆
			ConnectionTool.logion(conn);
			MultiUserChat muc = new MultiUserChat(conn, roomName + "@" + conference);
			muc.destroy("销毁房间", conn.getUser());
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		} finally {
			try {
				ConnectionTool.closeConnection(conn);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return true;
	}

}
