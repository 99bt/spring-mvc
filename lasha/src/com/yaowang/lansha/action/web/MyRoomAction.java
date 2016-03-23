package com.yaowang.lansha.action.web;

import java.io.IOException;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;

import com.alibaba.fastjson.JSONObject;
import com.yaowang.lansha.common.action.LanshaBaseAction;
import com.yaowang.lansha.common.constant.LanshaConstant;
import com.yaowang.lansha.entity.YwGame;
import com.yaowang.lansha.entity.YwUserRoom;
import com.yaowang.lansha.service.YwGameService;
import com.yaowang.lansha.service.YwUserRoomService;

/**
 * @ClassName: MyRoomAction
 * @Description: TODO(这里用一句话描述这个类的作用)
 * @author tandingbo
 * @date 2015年12月10日 上午10:25:29
 * 
 */
public class MyRoomAction extends LanshaBaseAction {
	private static final long serialVersionUID = -3433310787674075768L;
	
	@Resource
	private YwUserRoomService ywUserRoomService;
	@Resource
	private YwGameService ywGameService;
	
	public String roomNo; // 房间ID号
	public String roomName; // 房间名称
	public String gameId; // 游戏Id
	public String notice; // 直播公告
	
	/**
	 * @Description: 我的房间
	 * @return
	 */
	public String myroom(){
		// 当前登录用户ID
		String uid = getUserLogin().getId();
		YwUserRoom userRoom = ywUserRoomService.getYwUserRoomByUid(uid);
		if (userRoom == null) {
			addActionError("主播房间不存在");
			return "msg";
		}
		roomNo = String.valueOf(userRoom.getIdInt());
		roomName = userRoom.getName();
		notice = userRoom.getNotice();
		gameId = userRoom.getGameId();
		return SUCCESS;
	}

	/**
	 * @throws IOException
	 * @Description: 保存信息
	 */
	public void save() throws IOException {
		if (StringUtils.isBlank(roomNo)) {
			write(getFailed("房间号不能为空"));
			return;
		}
		if (StringUtils.isBlank(roomName)) {
			write(getFailed("房间名称不能为空"));
			return;
		}
		if (StringUtils.isBlank(gameId)) {
			write(getFailed("请选择游戏"));
			return;
		}

		YwUserRoom userRoom = ywUserRoomService.getYwUserRoomById(Integer.valueOf(roomNo));
		if (userRoom == null) {
			write(getFailed("房间号不存在，请检查房间号是否正确"));
			return;
		}
		
		userRoom.setName(roomName);
		userRoom.setGameId(gameId);
		userRoom.setNotice(notice);

		try {
			ywUserRoomService.update(userRoom);
			JSONObject object = new JSONObject();
			object.put("url", getContextPath()+"/user/myroom.html");
			writeSuccess(object);
		} catch (Exception e) {
			write(getFailed("保存失败"));
		}
	}
	
	/**
	 * @Description: 获取游戏列表
	 * @return
	 */
	public List<YwGame> getListGame() {
		YwGame ywGame = new YwGame();
		ywGame.setStatus(LanshaConstant.STATUS_ONLINE);
		return ywGameService.getYwGamePage(ywGame, null, null, null, null, null);
	}
	
	public String getRoomNo() {
		return roomNo;
	}

	public void setRoomNo(String roomNo) {
		this.roomNo = roomNo;
	}

	public String getRoomName() {
		return roomName;
	}

	public void setRoomName(String roomName) {
		this.roomName = roomName;
	}

	public String getGameId() {
		return gameId;
	}

	public void setGameId(String gameId) {
		this.gameId = gameId;
	}

	public String getNotice() {
		return notice;
	}

	public void setNotice(String notice) {
		this.notice = notice;
	}
}
