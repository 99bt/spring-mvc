package com.yaowang.lansha.action.web;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.ServletException;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;

import com.yaowang.common.dao.PageDto;
import com.yaowang.lansha.common.action.LanshaBaseAction;
import com.yaowang.lansha.common.constant.LanshaConstant;
import com.yaowang.lansha.entity.LanshaActivityPush;
import com.yaowang.lansha.entity.YwBanner;
import com.yaowang.lansha.entity.YwGame;
import com.yaowang.lansha.entity.YwGameHot;
import com.yaowang.lansha.entity.YwUserRoom;
import com.yaowang.lansha.service.LanshaActivityPushService;
import com.yaowang.lansha.service.YwBannerService;
import com.yaowang.lansha.service.YwGameHotService;
import com.yaowang.lansha.service.YwGameService;
import com.yaowang.lansha.service.YwUserRoomService;
import com.yaowang.service.impl.SysOptionServiceImpl;
import com.yaowang.util.freemark.AppSetting;

/**
 * 首页action
 * @author shenl
 *
 */
public class IndexAction extends LanshaBaseAction {
	private static final long serialVersionUID = 1L;

	@Resource
	private YwBannerService ywBannerService;
	@Resource
	private YwUserRoomService ywUserRoomService;
	@Resource
	private YwGameHotService ywGameHotService;
	@Resource
	private YwGameService ywGameService;
	@Resource
	private LanshaActivityPushService lanshaActivityPushService;
	
	private List<YwBanner> banners;
	private List<YwUserRoom> rooms;
	private List<YwGame> hotGames;
	private List<YwUserRoom> hotRooms;
	private List<YwUserRoom> bestRooms;
	private List<YwUserRoom> girlRooms;
	private List<Map<String,Object>> hotGameRooms;
	private Integer count;//直播房间总数
	private List<LanshaActivityPush> activityPushList;
//	private List<YwUserRoom> newRooms;
	
	/**
	 * 前台首页
	 * @return
	 * @throws Exception 
	 */
	public String index() throws Exception{
		if (getIsWap()) {
			//wap
			getRequest().getRequestDispatcher("/wap/index.html").forward(getRequest(), getResponse());
			return null;
		}
		PageDto page = getPageDto();
		page.setCount(false);
		//banner
		YwBanner searcBanner = new YwBanner();
		searcBanner.setClientType("0");
		banners = ywBannerService.getYwBannerPage(searcBanner, null);
		ywBannerService.setRoom(banners, true);
		
		// 热门推荐
		page.setCurrentPage(1);
//		page.setRowNum(8);
//		rooms = ywUserRoomService.getBestUserRooms(LanshaConstant.LANSHA_HOT_ANCHOR,page);
//		ywUserRoomService.setData((List<YwUserRoom>)rooms,true);
//		//设置头像
//		for (YwUserRoom room : (List<YwUserRoom>)rooms) {
//			room.setUserIcon(getUploadFilePath(room.getUserIcon()));
//		}
		//最热房间
		page.setRowNum(8);
		page.setCount(false);
		YwUserRoom entity = new YwUserRoom();
		entity.setOnline(LanshaConstant.ROOM_STATUS_ONLINE);
		entity.setOrderSql("(CASE WHEN multiple_number = '0' THEN 1 ELSE multiple_number END) * number +base_number desc");
		rooms = ywUserRoomService.getAllLiveListByRoome(entity, page, null);
		ywUserRoomService.setData((List<YwUserRoom>)rooms,true);
		for (YwUserRoom room : (List<YwUserRoom>)rooms) {
			room.setUserIcon(getUploadFilePath(room.getUserIcon()));
		}
		// 热门推荐游戏
//		page.setRowNum(8);//before 1.0.6
		page.setRowNum(12);
		List<YwGameHot> gameHots = ywGameHotService.getYwGameHotPage(null, page);
		hotGames = ywGameHotService.setGame((List<YwGameHot>)gameHots);
		
        //大神主播
		page.setRowNum(3);
		bestRooms = ywUserRoomService.getBestUserRooms(LanshaConstant.LANSHA_BEST_ANCHOR,page);
		ywUserRoomService.setData((List<YwUserRoom>)bestRooms,false);
		//设置头像
		for (YwUserRoom room : (List<YwUserRoom>)bestRooms) {
			room.setUserIcon(getUploadFilePath(room.getUserIcon()));
		}
		
        //美女主播
		page.setRowNum(3);
		girlRooms = ywUserRoomService.getBestUserRooms(LanshaConstant.LANSHA_GIRL_ANCHOR,page);
		ywUserRoomService.setData((List<YwUserRoom>)girlRooms,false);
		//设置头像
		for (YwUserRoom room : (List<YwUserRoom>)girlRooms) {
			room.setUserIcon(getUploadFilePath(room.getUserIcon()));
		}

		count = ywUserRoomService.getRoomCount();
		//查询推荐游戏列表
		String limit = SysOptionServiceImpl.getValue("LANSHA.INDEX.SHOWNUM.PC");
		List<String> gameIds = ywGameHotService.getHotGameIdPage(Integer.parseInt(limit));
		//获取推荐游戏房间列表
		if(CollectionUtils.isNotEmpty(gameIds)){
			page.setRowNum(8);
			hotGameRooms = new ArrayList<Map<String,Object>>();
			for(int i=0;i<gameIds.size();i++){
				String gameId = gameIds.get(i);
				Map<String,Object> gameEntity = new HashMap<String,Object>();
				YwGame game = ywGameService.getYwGameById(gameId);
				if(null == game){
					continue;
				}
				gameEntity.put("gameId",gameId);
				gameEntity.put("gameName",game.getName());
				YwUserRoom query = new YwUserRoom();
				query.setGameId(gameId);
				query.setOnline(LanshaConstant.ROOM_STATUS_ONLINE);
				query.setOrderSql("(CASE WHEN multiple_number = '0' THEN 1 ELSE multiple_number END) * number +base_number desc");
				List<YwUserRoom> hots = ywUserRoomService.getAllLiveListByRoome(query, page, null);
	    		ywUserRoomService.setData(hots,false);
				gameEntity.put("roomList", hots);
				Integer gameCount = ywUserRoomService.getRoomCountByGameId(gameId);
				gameEntity.put("count", gameCount);
				hotGameRooms.add(gameEntity);
			}
		}
		page.setRowNum(4);
		activityPushList = lanshaActivityPushService.getLanshaActivityPushPage(null, page);
		return SUCCESS;
	}
	
	/**
	 * 最热房间
	 * @throws IOException 
	 */
	public void getBestHotRooms() throws IOException{
		PageDto page = new PageDto();
		//最热房间
		page.setRowNum(8);
		page.setCount(false);
		YwUserRoom entity = new YwUserRoom();
		if(StringUtils.isNotBlank(id)){
			entity.setGameId(id);
		}
		entity.setOnline(LanshaConstant.ROOM_STATUS_ONLINE);
		entity.setOrderSql("(CASE WHEN multiple_number = '0' THEN 1 ELSE multiple_number END) * number +base_number desc");
		List<YwUserRoom> hots = ywUserRoomService.getAllLiveListByRoome(entity, page, null);
		writeToRooms(hots);
		
	}
	
	/**
	 * 最新房间
	 * @throws IOException 
	 */
	public void getNewLiveRooms() throws IOException{
		PageDto page = new PageDto();
		//新开房间
		page.setRowNum(8);
		page.setCount(false);
		YwUserRoom entity = new YwUserRoom();
		if(StringUtils.isNotBlank(id)){
			entity.setGameId(id);
		}
		entity.setOnline(LanshaConstant.ROOM_STATUS_ONLINE);
		entity.setOrderSql("create_time desc");
		List<YwUserRoom> newRooms = ywUserRoomService.getAllLiveListByRoome(entity, page, null);
		writeToRooms(newRooms);
	}
	
	/**
	 * 获取推荐房间
	 * @throws IOException 
	 */
	public void getPushRooms() throws IOException{
		PageDto page = new PageDto();
		page.setRowNum(8);
		page.setCount(false);
		rooms = ywUserRoomService.getBestUserRooms(LanshaConstant.LANSHA_HOT_ANCHOR,page,id);
		writeToRooms(rooms);
	}
	
	/**
	 * 美女主播
	 * @return
	 * @throws IOException 
	 * @throws ServletException 
	 */
	public String meinvzhubo() throws Exception{
		if (getIsWap()) {
			//wap
			getRequest().getRequestDispatcher("/wap/meinvzhubo.html").forward(getRequest(), getResponse());
			return null;
		}
		PageDto page = new PageDto();
		//新开房间
		page.setRowNum(6);
		page.setCount(false);
		YwUserRoom entity = new YwUserRoom();
		if(StringUtils.isNotBlank(id)){
			entity.setGameId(id);
		}
		entity.setOrderSql("(CASE WHEN online = '1' THEN -1 ELSE online END),create_time desc");
		List<YwUserRoom> newRooms = ywUserRoomService.getAllLiveListByRoome(entity, page, null);
		list = newRooms;
		
		//参数
		name = SysOptionServiceImpl.getValue("LANSHA.ANCHOR.PC");
		
		return SUCCESS;
	}
	
	/**
	 * 水友赛
	 * @return
	 * @throws IOException 
	 */
	public String shuiyousai() throws Exception{
		if (getIsWap()) {
			//wap
			getRequest().getRequestDispatcher("/wap/shuiyousai.html").forward(getRequest(), getResponse());
			return null;
		}
		return SUCCESS;
	}
	
	/**
	 * ajax返回room list数据
	 * @param rooms
	 * @throws IOException 
	 */
	private void writeToRooms(List<YwUserRoom> rooms) throws IOException{
    	if(CollectionUtils.isNotEmpty(rooms)){
    		ywUserRoomService.setData((List<YwUserRoom>)rooms,false);
    		List<Map<String,String>> roomsInfo = new ArrayList<Map<String,String>>();
    		for(YwUserRoom room: rooms){
    			Map<String,String> roomMap = new HashMap<String,String>();
    			roomMap.put("roomURL", getContextPath() + AppSetting.getLivePathStatic(room.getIdInt()));
    			roomMap.put("roomImg", getUploadPath() + room.getLiveImg());
    			roomMap.put("bogerName", room.getNickname());
    			roomMap.put("avatar", getUploadFilePath(room.getUserIcon()));
    			roomMap.put("roomName", room.getName());
    			roomMap.put("viewNum", room.getOnLineNumber() + "");
    			roomMap.put("gameName", room.getGameName());
    			roomMap.put("gameURL", getContextPath() + "/gameLive.html?id=" + room.getGameId());
    			roomsInfo.add(roomMap);
    		}
    		writeSuccessWithData(roomsInfo);
    	}else{
            write(getErrorMsg("没有相应的直播间!"));
    	}
	}
	
    /**
     * @throws java.io.IOException
     * @Title: list
     * @Description:
     */
    public String integralrule() throws IOException {
        return SUCCESS;
    }
    
	/**
	 * 获取banner背景图
	 * @return
	 */
	public String getBannerBackground(){
		return SysOptionServiceImpl.getValue("LANSHA.INDEX.BANNER");
	}
	
	public List<LanshaActivityPush> getActivityPushList() {
		if(CollectionUtils.isNotEmpty(activityPushList)){
			int len = activityPushList.size();
			if(len > 4){
				activityPushList = activityPushList.subList(0, 3);
				len = 4;
			}
			for(int i=0;i<len;i++){
				activityPushList.get(i).setIndexImg(getUploadFilePath(activityPushList.get(i).getIndexImg()));
			}
		}
		return activityPushList;
	}

	public List<YwBanner> getBanners() {
		return banners;
	}

	public List<YwUserRoom> getRooms() {
		return rooms;
	}

	public List<YwGame> getHotGames() {
		return hotGames;
	}

	public List<YwUserRoom> getHotRooms() {
		return hotRooms;
	}
	
	public List<YwUserRoom> getBestRooms() {
		return bestRooms;
	}
	public List<YwUserRoom> getGirlRooms() {
		return girlRooms;
	}
	public Integer getCount() {
		return count;
	}
	public List<Map<String, Object>> getHotGameRooms() {
		return hotGameRooms;
	}
	
	
//	public List<YwUserRoom> getNewRooms() {
//		return newRooms;
//	}
	
}
