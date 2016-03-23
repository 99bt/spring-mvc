package com.yaowang.lansha.action.web;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.collections.CollectionUtils;

import com.yaowang.common.dao.PageDto;
import com.yaowang.lansha.common.action.LanshaBaseAction;
import com.yaowang.lansha.entity.YwUserRoom;
import com.yaowang.lansha.entity.YwUserRoomHistory;
import com.yaowang.lansha.service.YwUserRoomHistoryService;
import com.yaowang.lansha.service.YwUserRoomService;

/**
 * @ClassName: HistoryAction
 * @Description: TODO(这里用一句话描述这个类的作用)
 * @author tandingbo
 * @date 2015年12月9日 下午11:01:17
 * 
 */
public class HistoryAction extends LanshaBaseAction {
	private static final long serialVersionUID = -3555891085532462069L;
	
	@Resource
	private YwUserRoomService ywUserRoomService;
	@Resource
	private YwUserRoomHistoryService ywUserRoomHistoryService;
	
	/**
	 * @Description: 历史观看记录
	 * @return
	 */
	public String history() {
		// 当前登录用户ID
		String uid = getUserLogin().getId();
		YwUserRoomHistory roomHistory = new YwUserRoomHistory();
		roomHistory.setUid(uid);
		PageDto page = getPageDto();
		page.setCount(false);
		
		List<String> roomIds = ywUserRoomHistoryService.getRoomIdPage(roomHistory, page);
		List<YwUserRoom> listUserRoom = new ArrayList<YwUserRoom>();
		if(CollectionUtils.isNotEmpty(roomIds)){
			Map<String, YwUserRoom> mapUserRoom = ywUserRoomService.getYwUserRoomMapByIds(roomIds.toArray(new String[]{}));
			for (String roomId : roomIds) {
				if(mapUserRoom.containsKey(roomId)){
					listUserRoom.add(mapUserRoom.get(roomId));
				}
			}
			if(CollectionUtils.isNotEmpty(listUserRoom)){
				ywUserRoomService.setUserName(listUserRoom);
				ywUserRoomService.setGameName(listUserRoom);
			}
		}
		list = listUserRoom;
		//设置头像
		for (YwUserRoom room : listUserRoom) {
			room.setUserIcon(getUploadFilePath(room.getUserIcon()));
		}
		return SUCCESS;
	}
}
