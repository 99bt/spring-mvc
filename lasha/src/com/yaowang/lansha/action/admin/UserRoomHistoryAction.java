package com.yaowang.lansha.action.admin;


import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;

import com.yaowang.common.action.BasePageAction;
import com.yaowang.lansha.entity.YwUser;
import com.yaowang.lansha.entity.YwUserRoom;
import com.yaowang.lansha.entity.YwUserRoomHistory;
import com.yaowang.lansha.service.YwUserRoomHistoryService;
import com.yaowang.lansha.service.YwUserRoomService;
import com.yaowang.lansha.service.YwUserService;

/**
 * @author Administrator
 * @author zengxi
 * @date 2015-12-8
 * 
 */
public class UserRoomHistoryAction extends BasePageAction {
	private static final long serialVersionUID = 1L;
	
	@Resource
	private YwUserRoomHistoryService ywUserRoomHistoryService;
	@Resource
	private YwUserService ywUserService;
	@Resource
	private YwUserRoomService ywUserRoomService;
	
	private YwUserRoomHistory entity;

	public String list() {
		if (startTime == null) {
			setStartTime(getNow());
		}
		if (endTime == null) {
			setEndTime(getNow());
		}
		
		if(entity == null){
			entity = new YwUserRoomHistory();
		}else{
			if(StringUtils.isNotBlank(entity.getRoomIdInts())){
				if(isNumeric(entity.getRoomIdInts())){
					entity.setRoomIdInt(Integer.parseInt(entity.getRoomIdInts()));
				}else{
					entity.setRoomIdInt(-1);
				}
			}
			if ( StringUtils.isNotBlank(entity.getHouseOwner())) {
				YwUser user = ywUserService.getYwusersByUsername(entity.getHouseOwner(), true);
				if (user == null) {
					return SUCCESS;
				}
				entity.setUid(user.getId());
			}
			
			//房间搜索
			if (entity.getRoomIdInt()!=null && entity.getRoomIdInt()!=0) {
				YwUserRoom ywUserRoom = ywUserRoomService.getYwUserRoomById(entity.getRoomIdInt());
				if (ywUserRoom == null) {
					return SUCCESS;
				}
				entity.setRoomId(ywUserRoom.getId());
				entity.setRoomIdInt(ywUserRoom.getIdInt());
			}
		}
		
		//只显示已领取
		entity.setStatus(2);
		list = ywUserRoomHistoryService.getYwUserRoomHistoryPage(entity, null, getPageDto(), startTime, endTime);
		ywUserRoomHistoryService.setUserName((List<YwUserRoomHistory>)list);
		ywUserRoomHistoryService.setRoomId((List<YwUserRoomHistory>)list);
		return SUCCESS;
	}
	public static boolean isNumeric(String str) {
		for (int i = str.length(); --i >= 0;) {
			if (!Character.isDigit(str.charAt(i))) {
				return false;
			}
		}
		return true;
	}

	public YwUserRoomHistory getEntity() {
		return entity;
	}

	public void setEntity(YwUserRoomHistory entity) {
		this.entity = entity;
	}
}
