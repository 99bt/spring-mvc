package com.yaowang.lansha.action.mobile;

import java.io.IOException;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;

import com.alibaba.fastjson.JSONObject;
import com.yaowang.lansha.common.action.LanshaMobileAction;
import com.yaowang.lansha.common.constant.LanshaConstant;
import com.yaowang.lansha.entity.YwUser;
import com.yaowang.lansha.entity.YwUserRoom;
import com.yaowang.lansha.service.YwUserRoomService;
import com.yaowang.lansha.service.YwUserService;

public class UserSettingAction extends LanshaMobileAction {
	private static final long serialVersionUID = 1941247956505436984L;

	@Resource
	private YwUserService ywUserService;
	@Resource
	private YwUserRoomService ywUserRoomService;

	private String on;
	public String type;// 分享类型(1：app，2：房间)
	public String objectId;// 分享对象（type=2必填）

	/**
	 * 全局消息开关
	 * 
	 * @creationDate. 2015-12-21 下午3:16:06
	 * @throws IOException
	 */
	public void push() throws IOException {
		// 当前登录用户ID
		YwUser u = getUserLogin();
		if (StringUtils.isBlank(on)) {
			write(getFailed("on不能为空"));
		}
		ywUserService.updatePush(u.getId(), on);
		JSONObject object = new JSONObject();
		JSONObject map = new JSONObject();
		map.put("status", 1);
		map.put("data", object);
		write(map);
	}

	/**
	 * @throws IOException
	 * @Description: 分享
	 */
	public void share() throws IOException {
		if (StringUtils.isBlank(type)) {
			write(getError("分享类型不能为空"));
			return;
		}
		if (LanshaConstant.SHARE_TYPE_APP.equals(type)) {
			getResponse().sendRedirect(getHostContextPath("/appdownload.html"));
		} else if (LanshaConstant.SHARE_TYPE_ROOM.equals(type)) {
			if (StringUtils.isBlank(objectId)) {
				write(getError("房间Id不能为空"));
				return;
			}
			YwUserRoom userRoom = ywUserRoomService.getYwUserRoomById(objectId);
			if(userRoom == null){
				write(getError("房间不存在"));
				return;
			}
			getResponse().sendRedirect(getHostContextPath("/live/" + userRoom.getIdInt() + ".html"));
		}
	}

	public void setOn(String on) {
		this.on = on;
	}

	public void setType(String type) {
		this.type = type;
	}

	public void setObjectId(String objectId) {
		this.objectId = objectId;
	}
}
