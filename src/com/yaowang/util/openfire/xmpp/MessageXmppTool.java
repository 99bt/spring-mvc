package com.yaowang.util.openfire.xmpp;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.apache.commons.collections.MapUtils;
import org.jivesoftware.smack.XMPPConnection;
import org.jivesoftware.smack.packet.Message;
import org.jivesoftware.smack.packet.PacketExtension;
import org.jivesoftware.smack.packet.Presence;
import org.jivesoftware.smack.util.StringUtils;
import org.jivesoftware.smackx.muc.MultiUserChat;

import com.yaowang.common.util.LogUtil;
import com.yaowang.util.openfire.OpenFireConstant;

/**
 * 指令发送
 * 
 * @author shenl
 *
 */
public class MessageXmppTool {
	/**
	 * 发送消息
	 * 
	 * @param username用户名
	 * @param msg消息内容
	 * @param type消息类型(点赞，评论，回复，关注)详细查看OpenFireConstant
	 * @param data消息类型内容(资讯id，用户id等信息)
	 * @return
	 */
	public static Boolean sendUserMessage(String username, String msg, Integer type, String data) {
		// 记录日志
		// String str = msg;
		// LogUtil.log("OPENFIRE.MESSAGE", str);

		XMPPConnection conn = null;
		try {
			// 发送消息
			Message message = new Message();
			message.setType(Message.Type.chat);
			message.setPacketID(UUID.randomUUID().toString());
			message.setTo(username + "@" + OpenFireConstant.DOMAIN);

			// 封装property数据
			Map<String, Object> map = new HashMap<String, Object>();
			map.put(OpenFireConstant.MSG_TYPE, type);
			map.put(OpenFireConstant.MSG_DATA, data);
			map.put(OpenFireConstant.MSG_FORMNAME, "系统消息");
			// 构建消息
			message.addExtension(getPacketExtension(map));
			message.setBody(msg);
			conn = ConnectionTool.getConnection();
			// 登陆
			ConnectionTool.logion(conn);
			Presence presence = new Presence(Presence.Type.available);
			conn.sendPacket(presence);
			// 发送消息
			conn.sendPacket(message);
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
	 * @Description: 构建XML自定义消息
	 * @param map参数集
	 * @return
	 */
	private static PacketExtension getPacketExtension(final Map<String, Object> map) {
		return new PacketExtension() {
			@Override
			public CharSequence toXML() {
				StringBuffer var1 = new StringBuffer();
				if (MapUtils.isNotEmpty(map)) {
					var1.append("<properties xmlns=\"").append(getNamespace()).append("\">");
					for (Map.Entry<String, Object> entry : map.entrySet()) {
						String key = entry.getKey();
						Object value = entry.getValue();
						var1.append("<property>");
						var1.append("<name>").append(StringUtils.escapeForXML(key)).append("</name>");
						var1.append("<value type=\"");
						if (value instanceof Integer) {
							var1.append("integer\">").append(value).append("</value>");
						} else if (value instanceof String) {
							var1.append("string\">");
							var1.append(StringUtils.escapeForXML((String) value));
							var1.append("</value>");
						}
						var1.append("</property>");
					}
					var1.append("</properties>");
				}
				return var1.toString();
			}

			@Override
			public String getNamespace() {
				return "http://www.jivesoftware.com/xmlns/xmpp/properties";
			}

			@Override
			public String getElementName() {
				return "properties";
			}
		};
	}

	/**
	 * 发送房间指令
	 * 
	 * @param room
	 * @param type
	 * @return
	 */
	public static Boolean sendRoomMessage(String roomId, String type) {
		String message = type + "|" + roomId + "|" + System.currentTimeMillis();
		LogUtil.log("OPENFIRE.MESSAGE", message);
		return sendMessage(roomId, message, 0);
	}

	/**
	 * 发送房间消息
	 * 
	 * @param roomName
	 * @param msg
	 * @return
	 */
	public static Boolean sendMessage(String roomName, String msg, int time) {
		XMPPConnection conn = null;
		try {
			conn = ConnectionTool.getConnection();
			// 登陆
			ConnectionTool.logion(conn);
			Presence presence = new Presence(Presence.Type.available);
			conn.sendPacket(presence);
			// 群聊
			MultiUserChat muc = new MultiUserChat(conn, roomName + "@" + OpenFireConstant.CONFERENCE);
			muc.join(OpenFireConstant.ADMIN_NAME + "|0|");
			muc.sendMessage(msg);
			if (time > 0) {
				Thread.sleep(time);
			}
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

	public static void main(String[] args) {
		// sendMessage("tg001", "stop");
		// sendMessage("tg001", "start");
		// sendMessage("tg001", "error");
		sendUserMessage("10000", "哈哈哈", null, null);
	}
}
