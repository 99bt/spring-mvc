package com.yaowang.lansha.action.web;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;

import com.yaowang.common.dao.PageDto;
import com.yaowang.lansha.common.action.LanshaBaseAction;
import com.yaowang.lansha.common.constant.LanshaConstant;
import com.yaowang.lansha.entity.YwUserRoom;
import com.yaowang.lansha.service.YwUserRoomService;
import com.yaowang.util.freemark.AppSetting;

public class LanshaLiveListAction extends LanshaBaseAction {

	/**
	 * [2015-12-10上午11:06:37]zlb
	 * @Fields serialVersionUID : TODO
	 */
	
	private static final long serialVersionUID = 1L;
	
	@Resource
	private YwUserRoomService ywUserRoomService;
	
	private Integer count;//直播房间总数
	
	private String show;  //显示类型 0=推荐,1=最热,2=最新
	
	private static final int SHOW_LIMIT = 64;//一次显示64个
	
	public String liveList() throws Exception{
		if (getIsWap()) {
			//wap
			getRequest().getRequestDispatcher("/wap/liveList.html").forward(getRequest(), getResponse());
			return null;
		}
		if(StringUtils.isBlank(show)){
			show  = "1";
		}
		PageDto page = getPageDto();
		if("1".equals(show)){
			YwUserRoom entity = new YwUserRoom();
			entity.setOnline(LanshaConstant.ROOM_STATUS_ONLINE);
			entity.setOrderSql("number*(CASE WHEN multiple_number = '0' THEN 1 ELSE multiple_number END)+base_number desc");
			list = ywUserRoomService.getAllLiveListByRoome(entity, page, null);
		}else if("2".equals(show)){
			YwUserRoom entity = new YwUserRoom();
			entity.setOnline(LanshaConstant.ROOM_STATUS_ONLINE);
			entity.setOrderSql("create_time desc");
			list = ywUserRoomService.getAllLiveListByRoome(entity, page, null);
		}else{
			list = ywUserRoomService.getAllOnlineLiveSortByHot(LanshaConstant.LANSHA_HOT_ANCHOR,page);
		}
		ywUserRoomService.setData((List<YwUserRoom>)list,true);
		count = ywUserRoomService.getRoomCount();
		//设置头像
		for (YwUserRoom room : (List<YwUserRoom>)list) {
			room.setUserIcon(getUploadFilePath(room.getUserIcon()));
		}
		return SUCCESS;
	}
	
	
	/**
	 * 加载更多
	 * @creationDate. 2015-12-11 下午8:36:43
	 * @throws IOException 
	 */
	public void getLiveList() throws IOException{
		PageDto page = getPageDto();
		List<YwUserRoom> data = new ArrayList<YwUserRoom>();
		if(StringUtils.isBlank(show)){
			show  = "1";
		}
		if("1".equals(show)){
			YwUserRoom entity = new YwUserRoom();
			entity.setOnline(LanshaConstant.ROOM_STATUS_ONLINE);
			entity.setOrderSql("number*multiple_number+base_number desc, create_time");
			data = ywUserRoomService.getAllLiveListByRoome(entity, page, null);
		}else if("2".equals(show)){
			YwUserRoom entity = new YwUserRoom();
			entity.setOnline(LanshaConstant.ROOM_STATUS_ONLINE);
			entity.setOrderSql("create_time desc");
			data = ywUserRoomService.getAllLiveListByRoome(entity, page, null);
		}else{
			data = ywUserRoomService.getAllOnlineLiveSortByHot(LanshaConstant.LANSHA_HOT_ANCHOR,page);
		}
		ywUserRoomService.setData(data,true);
		List<Object> list = new ArrayList<Object>();
		for(YwUserRoom room : data){
			Map<String, Object> m = new HashMap<String, Object>();
			m.put("roomURL", this.getContextPath() + AppSetting.getLivePathStatic(room.getIdInt()));
			m.put("roomImg", getUploadFilePath(room.getLiveImg()));
			m.put("avatar", getUploadFilePath(room.getUserIcon()));
			m.put("bogerName", room.getNickname());
			m.put("roomName", room.getName());
			m.put("nickName", room.getNickname());
			m.put("viewNum", room.getOnLineNumber());
			m.put("gameName", room.getGameName());
			m.put("gameURL", "gameLive.html?id=" + room.getGameId());
			list.add(m);
		}
		writeSuccessWithData(list);
	}
	

	/**
	 * 房间搜索
	 * @throws IOException
	 * @Description: 全文搜索结果
	 */
	public String dosearch() throws IOException {
		PageDto page = getPageDto();
		if (StringUtils.isBlank(name)) {
			YwUserRoom entity = new YwUserRoom();
			entity.setOnline(LanshaConstant.ROOM_STATUS_ONLINE);
			entity.setOrderSql("number*multiple_number+base_number desc, create_time");
			list = ywUserRoomService.getAllLiveListByRoome(entity, page, null);
			ywUserRoomService.setData((List<YwUserRoom>)list,true);
			count = ywUserRoomService.getRoomCount();
			//设置头像
			for (YwUserRoom room : (List<YwUserRoom>)list) {
				room.setUserIcon(getUploadFilePath(room.getUserIcon()));
			}
			return SUCCESS;
		}

		List<YwUserRoom> setUserRoom = ywUserRoomService.doSearch(name,page);

		if (setUserRoom.size() > 0) {
			List<YwUserRoom> listUserRoom = new ArrayList<YwUserRoom>(setUserRoom);

			// 按权重排序，同权重 按观众数倒序排序
			List<YwUserRoom> roomTemp = new ArrayList<YwUserRoom>();
			//精确匹配
			for (YwUserRoom ywUserRoom : listUserRoom) {
				if (StringUtils.isNumeric(name) && StringUtils.isNotBlank(name)) {
					int pIdInt = Integer.parseInt(name);
					if (pIdInt == ywUserRoom.getIdInt()) {
						//通过房间id精确查找到的
						roomTemp.add(ywUserRoom);
					}else if (name.equals(ywUserRoom.getNickname())) {
						//主播名精确匹配
						roomTemp.add(ywUserRoom);
					}else if (name.equals(ywUserRoom.getName())) {
						//主播房间名精确匹配
						roomTemp.add(ywUserRoom);
					}
				}
			}
			//按人气排序
			YwUserRoom.sort(roomTemp);
			
			//在线房间
			LinkedList<YwUserRoom> roomTemp2 = new LinkedList<YwUserRoom>();
			for (YwUserRoom ywUserRoom : listUserRoom) {
				if (roomTemp.indexOf(ywUserRoom) > -1) {
					continue;
				}
				
				if (ywUserRoom.getOnline() == 1) {
					roomTemp2.add(ywUserRoom);
				}
			}
			//按人气排序
			YwUserRoom.sort(roomTemp2);
			roomTemp.addAll(roomTemp2);
			
			//离线房间
			for (YwUserRoom ywUserRoom : listUserRoom) {
				if (roomTemp.indexOf(ywUserRoom) > -1) {
					continue;
				}
				
				roomTemp.add(ywUserRoom);
			}
			list = roomTemp;
			// 设置名称信息
			ywUserRoomService.setUserName((List<YwUserRoom>) list);
			ywUserRoomService.setGameName((List<YwUserRoom>) list);
			ywUserRoomService.setData((List<YwUserRoom>)list,true);
			count = ywUserRoomService.getDoSearchCount(name);
			
			for (YwUserRoom room : (List<YwUserRoom>)list) {
				room.setUserIcon(getUploadFilePath(room.getUserIcon()));
			}
		}
		return SUCCESS;

	}	
	
	
	/**
	 * 加载更多
	 * @creationDate. 2016-03-16
	 * @throws IOException 
	 */
	public void loadSearchMore() throws IOException{
		List<YwUserRoom> data = new ArrayList<YwUserRoom>();
		PageDto page = getPageDto();
		if(StringUtils.isBlank(name)){
			YwUserRoom entity = new YwUserRoom();
			entity.setOnline(LanshaConstant.ROOM_STATUS_ONLINE);
			entity.setOrderSql("number*multiple_number+base_number desc, create_time");
			data = ywUserRoomService.getAllLiveListByRoome(entity, page, null);
		}else{
			data = ywUserRoomService.doSearch(name,page);
		}
		if (data.size() > 0) {
			List<YwUserRoom> listUserRoom = new ArrayList<YwUserRoom>(data);

			// 按权重排序，同权重 按观众数倒序排序
			List<YwUserRoom> roomTemp = new ArrayList<YwUserRoom>();
			//精确匹配
			for (YwUserRoom ywUserRoom : listUserRoom) {
				if (StringUtils.isNumeric(name) && StringUtils.isNotBlank(name)) {
					int pIdInt = Integer.parseInt(name);
					if (pIdInt == ywUserRoom.getIdInt()) {
						//通过房间id精确查找到的
						roomTemp.add(ywUserRoom);
					}else if (name.equals(ywUserRoom.getNickname())) {
						//主播名精确匹配
						roomTemp.add(ywUserRoom);
					}else if (name.equals(ywUserRoom.getName())) {
						//主播房间名精确匹配
						roomTemp.add(ywUserRoom);
					}
				}
			}
			//按人气排序
			YwUserRoom.sort(roomTemp);
			
			//在线房间
			LinkedList<YwUserRoom> roomTemp2 = new LinkedList<YwUserRoom>();
			for (YwUserRoom ywUserRoom : listUserRoom) {
				if (roomTemp.indexOf(ywUserRoom) > -1) {
					continue;
				}
				
				if (ywUserRoom.getOnline() == 1) {
					roomTemp2.add(ywUserRoom);
				}
			}
			//按人气排序
			YwUserRoom.sort(roomTemp2);
			roomTemp.addAll(roomTemp2);
			
			//离线房间
			for (YwUserRoom ywUserRoom : listUserRoom) {
				if (roomTemp.indexOf(ywUserRoom) > -1) {
					continue;
				}
				
				roomTemp.add(ywUserRoom);
			}
			data = roomTemp;
			
			// 设置名称信息
			ywUserRoomService.setUserName((List<YwUserRoom>) data);
			ywUserRoomService.setGameName((List<YwUserRoom>) data);
			ywUserRoomService.setData((List<YwUserRoom>)data,true);
			count = ywUserRoomService.getDoSearchCount(name);
			
			for (YwUserRoom room : (List<YwUserRoom>)data) {
				room.setUserIcon(getUploadFilePath(room.getUserIcon()));
			}
		}
		List<Object> list = new ArrayList<Object>();
		for(int i=0;i<data.size();i++){
			YwUserRoom room = data.get(i);
			Map<String, Object> m = new HashMap<String, Object>();
			m.put("roomURL", this.getContextPath() + AppSetting.getLivePathStatic(room.getIdInt()));
			m.put("roomImg", getUploadFilePath(room.getLiveImg()));
			m.put("avatar", getUploadFilePath(room.getUserIcon()));
			m.put("bogerName", room.getNickname());
			m.put("roomName", room.getName());
			m.put("nickName", room.getNickname());
			m.put("viewNum", room.getOnLineNumber());
			m.put("gameName", room.getGameName());
			m.put("gameURL", "gameLive.html?id=" + room.getGameId());
			list.add(m);
		}
		writeSuccessWithData(list);
	}
	
	public PageDto getPageDto(){
		PageDto page = super.getPageDto();
		page.setCount(false);
		page.setRowNum(SHOW_LIMIT);
		return page;
	}

	public Integer getCount() {
		return count;
	}

	public void setCount(Integer count) {
		this.count = count;
	}


	public String getShow() {
		return show;
	}


	public void setShow(String show) {
		this.show = show;
	}
	
}
