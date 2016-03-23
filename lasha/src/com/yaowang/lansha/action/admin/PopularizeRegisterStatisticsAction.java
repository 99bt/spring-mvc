package com.yaowang.lansha.action.admin;

import java.util.List;

import javax.annotation.Resource;

import com.yaowang.common.action.BasePageAction;
import com.yaowang.lansha.entity.YwUser;
import com.yaowang.lansha.entity.YwUserRoom;
import com.yaowang.lansha.service.YwUserRoomService;
import com.yaowang.lansha.service.YwUserService;

/**
 * 推广注册统一
 * @return
 * zengxi
 */
public class PopularizeRegisterStatisticsAction extends BasePageAction {
	private static final long serialVersionUID = 1L;
	
	@Resource
	private YwUserService ywUserService;
	@Resource
	private YwUserRoomService ywUserRoomService;
	private Integer roomId;//邀请者房间ID
	private Integer idInt;//邀请者ID
	private Integer userIdInt;//被邀请者ID
	
	
	public String list(){
		YwUser user = new YwUser();
		if(idInt!=null){//邀请者ID
			YwUser temp = ywUserService.getYwUserByIdInt(idInt);
			if(temp ==null){
				return SUCCESS;
			}
			user.setQueryParentId(temp.getId());
		}
		if(userIdInt!=null){//被邀请者ID
			user.setIdInt(userIdInt);
		}
		if(roomId!=null){//邀请者房间ID
			YwUserRoom ywUserRoom = ywUserRoomService.getYwUserRoomById(roomId);
			if(ywUserRoom ==null){
				return SUCCESS;
			}
			user.setParentId(ywUserRoom.getUid());
		}
		user.setAdditional(1);
		list = ywUserService.getYwUserList(user, null, null, getPageDto(), null, null);
		ywUserService.setYwUserRoomId((List<YwUser>)list);
		return SUCCESS;
	}

	public Integer getRoomId() {
		return roomId;
	}

	public void setRoomId(Integer roomId) {
		this.roomId = roomId;
	}

	public Integer getIdInt() {
		return idInt;
	}

	public void setIdInt(Integer idInt) {
		this.idInt = idInt;
	}

	public Integer getUserIdInt() {
		return userIdInt;
	}

	public void setUserIdInt(Integer userIdInt) {
		this.userIdInt = userIdInt;
	}
	
	

	
}
