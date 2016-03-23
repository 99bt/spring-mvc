package com.yaowang.lansha.action.mobile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;

import com.yaowang.common.dao.PageDto;
import com.yaowang.lansha.common.action.LanshaMobileAction;
import com.yaowang.lansha.common.constant.LanshaConstant;
import com.yaowang.lansha.entity.YwBanner;
import com.yaowang.lansha.entity.YwUserRoom;
import com.yaowang.lansha.service.YwBannerService;
import com.yaowang.lansha.service.YwUserRoomService;
import com.yaowang.util.NumberUtil;

/**
 * @ClassName: IndexAction
 * @Description: TODO(首页)
 * @author tandingbo
 * @date 2015年12月21日 上午10:20:03
 * 
 */
public class  IndexAction extends LanshaMobileAction {
	private static final long serialVersionUID = -6518012612352493890L;

	@Resource
	private YwBannerService ywBannerService;
	@Resource
	private YwUserRoomService ywUserRoomService;



	/**
	 * @throws IOException
	 * @Description: 首页数据
	 */
	public void index() throws IOException {
		Map<String, Object> result = new HashMap<String, Object>();
		List<Map<String, Object>> listMapRooms = new ArrayList<Map<String, Object>>();
		List<Map<String, Object>> listMapBanner = new ArrayList<Map<String, Object>>();

		// 分页数据
		PageDto page = getPageDto();
		page.setRowNum(5);
		page.setCount(false);

		//获取banner,由原来的只给房间banner转换成给所有移动端类型(clientType=1)的banner
		YwBanner ywBanner = new YwBanner();
		ywBanner.setClientType("1");
		List<YwBanner> listBanner = ywBannerService.getYwBannerPage(ywBanner, null);
		ywBannerService.setRoom(listBanner, true);
		
		if(CollectionUtils.isNotEmpty(listBanner)){
			// 房间ids
			Set<String> idInts = new HashSet<String>();
			for (YwBanner banner : listBanner) {
				idInts.add(banner.getRoomId());
			}
			// 根据房间id获取房间信息
			Map<String, YwUserRoom> mapUserRoom = ywUserRoomService.getYwUserRoomMapByIdInts(idInts.toArray(new String[] {}), LanshaConstant.ROOM_STATUS_ONLINE);
			int i = 0;
			for (YwBanner banner : listBanner) {
				//取6个广告并且房间在线
				if(i < 6){
					Map<String, Object> mapBanner = new HashMap<String, Object>();
					String roomId = banner.getRoomId();
					// 广告图片
					String img = "";
					if ("0".equals(banner.getType())) {
						if(!mapUserRoom.containsKey(roomId)){
							// 房间信息,过滤掉被删除的房间
							continue;
						}else{
							YwUserRoom userRoom = mapUserRoom.get(roomId);
							mapBanner.put("roomId", userRoom.getId());//房间guid
							img = userRoom.getLiveImg();
						}
					}
					if(StringUtils.isNotBlank(banner.getImg())){
						img = banner.getImg();
					}
					mapBanner.put("img", getUploadFilePath(img));// 图片
					mapBanner.put("type", banner.getType());// (0：房间，1：广告)
					mapBanner.put("name", banner.getName());// 名称
					mapBanner.put("link", banner.getLinkUrl());// 链接地址
					listMapBanner.add(mapBanner);
					i++;
				}
			}
		}
		
		// 热门推荐
		page.setRowNum(6);
		page.setCurrentPage(1);
		YwUserRoom entity = new YwUserRoom();
		entity.setOnline(LanshaConstant.ROOM_STATUS_ONLINE);
        entity.setRoomHotType("1");
		List<YwUserRoom> listUserRoom = ywUserRoomService.getYwUserRoomIsHot(entity, page);
		if (CollectionUtils.isNotEmpty(listUserRoom)) {
			// 设置名称信息
			ywUserRoomService.setUserName(listUserRoom);
			ywUserRoomService.setGameName(listUserRoom);
			// 根据返回数据结构
			for (YwUserRoom ywUserRoom : listUserRoom) {
				Map<String, Object> mapRooms = new HashMap<String, Object>();
				mapRooms.put("id", ywUserRoom.getId());
				mapRooms.put("img", getUploadFilePath(ywUserRoom.getLiveImg()));// (http地址)
				mapRooms.put("name", ywUserRoom.getName());
				mapRooms.put("nickName", ywUserRoom.getNickname());
				mapRooms.put("gameName", ywUserRoom.getGameName());
				mapRooms.put("number", ywUserRoom.getOnLineNumber() == null ? "0" : NumberUtil.changeNumberToWan(ywUserRoom.getOnLineNumber(),1));
				mapRooms.put("online", ywUserRoom.getOnline() == null ? "0" : ywUserRoom.getOnline().toString());// (0：离线，1：在线，2：停播)
                mapRooms.put("headImg", getUploadFilePath(ywUserRoom.getUserIcon()));
				listMapRooms.add(mapRooms);
			}
		}

		result.put("rooms", listMapRooms);
		result.put("banner", listMapBanner);

		writerEntity(result);
	}

	/**
	 * @throws IOException
	 * @Description: 分页加载热门推荐房间
	 */
	public void hotRoom() throws IOException {
		// 默认1
		List<Map<String, Object>> listMapRooms = new ArrayList<Map<String, Object>>();

		// 热门推荐
		PageDto page = getPageDto();
		page.setRowNum(6);
		YwUserRoom entity = new YwUserRoom();
        entity.setRoomHotType("1");
		entity.setOnline(LanshaConstant.ROOM_STATUS_ONLINE);
		List<YwUserRoom> listUserRoom = ywUserRoomService.getYwUserRoomIsHot(entity, page);
		if (CollectionUtils.isNotEmpty(listUserRoom)) {
			// 设置名称信息
			ywUserRoomService.setUserName(listUserRoom);
			ywUserRoomService.setGameName(listUserRoom);
			// 根据返回数据结构
			for (YwUserRoom ywUserRoom : listUserRoom) {
				Map<String, Object> mapRooms = new HashMap<String, Object>();
				mapRooms.put("id", ywUserRoom.getId());
				mapRooms.put("img", getUploadFilePath(ywUserRoom.getLiveImg()));// (http地址)
				mapRooms.put("name", ywUserRoom.getName());
				mapRooms.put("nickName", ywUserRoom.getNickname());
				mapRooms.put("gameName", ywUserRoom.getGameName());
				mapRooms.put("number", ywUserRoom.getOnLineNumber() == null ? "0" : NumberUtil.changeNumberToWan(ywUserRoom.getOnLineNumber(),1));
				mapRooms.put("online", ywUserRoom.getOnline() == null ? "0" : ywUserRoom.getOnline().toString());// (0：离线，1：在线，2：停播)
                mapRooms.put("headImg", getUploadFilePath(ywUserRoom.getUserIcon()));
				listMapRooms.add(mapRooms);
			}
		}

		writeSuccessWithData(listMapRooms);
	}


}
