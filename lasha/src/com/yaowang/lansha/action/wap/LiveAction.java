package com.yaowang.lansha.action.wap;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;

import com.yaowang.common.dao.PageDto;
import com.yaowang.lansha.common.action.LanshaBaseAction;
import com.yaowang.lansha.common.constant.LanshaConstant;
import com.yaowang.lansha.entity.LanshaChatInfo;
import com.yaowang.lansha.entity.YwUser;
import com.yaowang.lansha.entity.YwUserRoom;
import com.yaowang.lansha.entity.YwUserRoomRelation;
import com.yaowang.lansha.service.YwUserRoomService;
import com.yaowang.util.DateUtils;
import com.yaowang.util.openfire.http.MessageTool;

/**
 * 
 * @Description: 直播
 * @author wangjs
 * @date 2016-3-15
 * @version V1.0
 */
public class LiveAction extends LanshaBaseAction {
	private static final long serialVersionUID = 1L;
	

	@Resource
	private YwUserRoomService ywUserRoomService;
	
	private YwUserRoom room;
	private YwUser loginUser;
	private YwUser user;
	private YwUserRoomRelation relation;
	private String error ;
	
	private List<YwUserRoom> rooms;
	private List<LanshaChatInfo> chatInfoList;
	
	/**
	 * @Description: TODO
	 * @return String
	 * @throws
	 */
	public String live(){
		error = "";
        //伪静态
        String objectId = getObjectId();
        if (StringUtils.isNotBlank(objectId)) {
            id = objectId;
        }
		if (StringUtils.isEmpty(id) || !id.matches("\\d+")) {
			addActionError("房间不存在");
			error = "房间不存在";
			return SUCCESS;
		}
		
		// 查询房间
		room = ywUserRoomService.getYwUserRoomById(Integer.valueOf(id));
		if (room == null) {
			room = new YwUserRoom();
			error = "房间不存在";
			return SUCCESS;
		}
		if (room.getOnline() == 2) {
			error = "房间已被禁播";
			return SUCCESS;
		}
		
		//主播信息
		ywUserRoomService.setData(Arrays.asList(room),true);		
		getChat();
		hotRoom();
		
		return SUCCESS;
	}
	
	/**
	 * 获取聊天记录
	 * @Description: TODO
	 * @return String
	 * @throws
	 */
	public String getChat(){
		List<Object[]> list = MessageTool.getMessage(room.getOpenfirePath(), room.getOpenfireRoom(), "14");
		
		chatInfoList =new ArrayList<LanshaChatInfo>();
		LanshaChatInfo lanshaChatInfo=null;
		if(list!=null){
			String[] names=null;
			for(Object[] obj:list){
				lanshaChatInfo=new LanshaChatInfo();
				lanshaChatInfo.setUserType(String.valueOf(obj[0]));
				if(obj[1]!=null && !"".equals(obj[1].toString())){
					names=obj[1].toString().split("\\|");
					lanshaChatInfo.setUserName(names[0]);
				}else{
					lanshaChatInfo.setUserName("");
				}
				lanshaChatInfo.setContent(String.valueOf(obj[2]));
				lanshaChatInfo.setCreatetime(DateUtils.pasetime(String.valueOf(obj[3])));
				chatInfoList.add(lanshaChatInfo);
			}
		}
		return SUCCESS;
	}
	
	/**
	 * 
	 * @Description: 获取后台推荐的直播间
	 * @return String
	 * @throws
	 */
	public String hotRoom(){
		
		PageDto page = getPageDto();
		page.setCount(false);
		page.setCurrentPage(1);
/*		// 热门（热门，大神，女神）
		page.setCurrentPage(1);
		page.setRowNum(6);
		YwUserRoom entity = new YwUserRoom();
		entity = new YwUserRoom();
		entity.setOnline(LanshaConstant.ROOM_STATUS_ONLINE);
		rooms = ywUserRoomService.getYwUserRoomIsHot(entity, page);
		ywUserRoomService.setData((List<YwUserRoom>)rooms,true);*/
		
		YwUserRoom yu = new YwUserRoom();
		page.setRowNum(12);
		yu.setOnline(LanshaConstant.ROOM_STATUS_ONLINE);
		yu.setOrderSql("number*multiple_number+base_number desc, create_time");
		rooms = ywUserRoomService.getAllLiveListByRoome(yu, page,null);
		ywUserRoomService.setData(rooms,true);
		if(rooms.size()>6){
			rooms=rooms.subList(0, 6);
		}
		
		return SUCCESS;

	}
	public YwUserRoom getRoom() {
		return room;
	}
	public void setRoom(YwUserRoom room) {
		this.room = room;
	}
	public YwUser getLoginUser() {
		return loginUser;
	}
	public void setLoginUser(YwUser loginUser) {
		this.loginUser = loginUser;
	}
	public YwUser getUser() {
		return user;
	}
	public void setUser(YwUser user) {
		this.user = user;
	}
	public YwUserRoomRelation getRelation() {
		return relation;
	}
	public void setRelation(YwUserRoomRelation relation) {
		this.relation = relation;
	}
	public String getError() {
		return error;
	}

	public List<LanshaChatInfo> getChatInfoList() {
		return chatInfoList;
	}

	public void setChatInfoList(List<LanshaChatInfo> chatInfoList) {
		this.chatInfoList = chatInfoList;
	}
	public List<YwUserRoom> getRooms() {
		return rooms;
	}

	public void setRooms(List<YwUserRoom> rooms) {
		this.rooms = rooms;
	}

	
}
