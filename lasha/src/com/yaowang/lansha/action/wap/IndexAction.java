package com.yaowang.lansha.action.wap;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.yaowang.common.dao.PageDto;
import com.yaowang.lansha.common.action.LanshaBaseAction;
import com.yaowang.lansha.common.constant.LanshaConstant;
import com.yaowang.lansha.entity.YwBanner;
import com.yaowang.lansha.entity.YwGame;
import com.yaowang.lansha.entity.YwGameHot;
import com.yaowang.lansha.entity.YwUserRoom;
import com.yaowang.lansha.service.YwBannerService;
import com.yaowang.lansha.service.YwGameHotService;
import com.yaowang.lansha.service.YwGameService;
import com.yaowang.lansha.service.YwUserRoomService;
import com.yaowang.service.impl.SysOptionServiceImpl;
import com.yaowang.util.freemark.AppSetting;

/**
 * wap首页action
 * @author zengxi
 *
 */
public class IndexAction extends LanshaBaseAction {
	private static final long serialVersionUID = 1L;

	@Resource
	private YwUserRoomService ywUserRoomService;
	@Resource
	private YwGameService ywGameService;
	@Resource
	private YwBannerService ywBannerService;
	@Resource
	private YwGameHotService ywGameHotService;
	
	
	private List<YwUserRoom> rooms;
	private List<YwGame> hotGamesDH;
	private List<YwUserRoom> hotRooms;//推荐直播
	private List<YwBanner> bannerList;

	
	/**
	 * wap首页
	 * @return
	 * @throws IOException 
	 */
/*	public String index(){
		// 热门推荐
		YwUserRoom entity = new YwUserRoom();
		entity = new YwUserRoom();
		entity.setOnline(LanshaConstant.ROOM_STATUS_ONLINE);
		rooms = ywUserRoomService.getYwUserRoomIsHot(entity, null);
		ywUserRoomService.setData((List<YwUserRoom>)rooms);		
		return SUCCESS;
	}*/
	public String index() throws IOException{
		PageDto page = getPageDto();
		page.setCount(false);
		page.setCurrentPage(1);
		//获取热门游戏（导航）
		YwGame ywGame=new YwGame();
		ywGame.setStatus(1);
		int num = 6;
        // 取个数
        String value = SysOptionServiceImpl.getValue("LANSHA.INDEX.SHOWNUM.APP");
        if (StringUtils.isNotEmpty(value)) {
            num = Integer.valueOf(value);
        }
		page.setRowNum(num);
		//hotGamesDH=ywGameService.getYwGamePage(ywGame, null, page, null, null, null);
		List<YwGameHot> yghlist=ywGameHotService.getYwGameHotPage(null, page);
		hotGamesDH=ywGameHotService.setGame(yghlist);
		
		YwUserRoom yu =null;
		List<YwUserRoom> roomlist=null;
		for(YwGame game:hotGamesDH){			
			//推荐直播
			yu = new YwUserRoom();
			page.setCount(false);
			page.setRowNum(4);
			yu.setGameId(game.getId());
			yu.setOnline(LanshaConstant.ROOM_STATUS_ONLINE);
			yu.setOrderSql("number*multiple_number+base_number desc, create_time");
			roomlist= ywUserRoomService.getAllLiveListByRoome(yu, page, null);
			ywUserRoomService.setData(roomlist,true);
			if(roomlist.size()>=2){
				game.setYwUserRooms(roomlist);			
			}
		}
		
		//获取banner图
		setBanner();

		//获取大神，女神
		recommend();
		
/*		Iterator<YwUserRoom> iter = rooms.iterator();
		YwUserRoom yu =null;
		while(iter.hasNext()){  
			yu = iter.next();  
		    if(StringUtils.isBlank(yu.getUserIcon())){  
		        iter.remove();  
		    }  
		} */
		//推荐直播
		page.setCurrentPage(1);
		page.setRowNum(4);
		YwUserRoom entity = new YwUserRoom();
		entity.setOnline(LanshaConstant.ROOM_STATUS_ONLINE);
		entity.setRoomHotType("1");
		hotRooms = ywUserRoomService.getYwUserRoomIsHot(entity, page);
		ywUserRoomService.setData((List<YwUserRoom>)hotRooms,true);
		return SUCCESS;
	}

	   /**
     * 大神美女
     *
     * @throws IOException
     */
    public void recommend() throws IOException {
        int num = 10;
        // 取个数
        String value = SysOptionServiceImpl.getValue("LANSHA.INDEX.SHOWNUM.APP.RECOMMEND");
        if (StringUtils.isNotEmpty(value)) {
            num = Integer.valueOf(value);
        }
        PageDto page = getPageDto();
        page.setCount(false);
        page.setRowNum(num);
        List<YwUserRoom> listD = ywUserRoomService.getBestUserRooms(LanshaConstant.LANSHA_BEST_ANCHOR, page);

        //美女主播
        page.setRowNum(num);
        List<YwUserRoom> listN = ywUserRoomService.getBestUserRooms(LanshaConstant.LANSHA_GIRL_ANCHOR, page);

        listD.removeAll(listN);
        listD.addAll(listN);

        if (!CollectionUtils.isEmpty(listD)) {
            Collections.sort(listD, new Comparator<YwUserRoom>() {
                @Override
                public int compare(YwUserRoom o1, YwUserRoom o2) {
                    return o1.getOrderId().compareTo(o2.getOrderId());
                }

            });
        }

        ywUserRoomService.setData((List<YwUserRoom>) listD,false);
        rooms=listD;
        
    }
	
	
	
	/**
	 * 
	 * @Description: 推荐直播获取下一页
	 * @return void
	 * @throws
	 */
	public void nextHotRooms(){
		List<Map<String,String>> list=new ArrayList<Map<String,String>>();
		//推荐直播
		PageDto page = getPageDto();
		page.setCount(false);
		page.setRowNum(4);
		YwUserRoom entity = new YwUserRoom();
		entity.setOnline(LanshaConstant.ROOM_STATUS_ONLINE);
		entity.setRoomHotType("1");
		hotRooms = ywUserRoomService.getYwUserRoomIsHot(entity, page);
		ywUserRoomService.setData((List<YwUserRoom>)hotRooms,true);
		Map<String,String> map=null;
		for(YwUserRoom ywUserRoom:hotRooms){			
			map=new HashMap<String,String>();
			map.put("url", getContextPath() + AppSetting.getLivePathStatic(ywUserRoom.getIdInt()));
			map.put("gameImg", getUploadPath()+ywUserRoom.getLiveImg());
			map.put("liver", ywUserRoom.getNickname());
			map.put("watchNum", String.valueOf(ywUserRoom.getOnLineNumber()));
			map.put("roomName", ywUserRoom.getName());
			map.put("avatar", getUploadPath()+ywUserRoom.getUserIcon());
			list.add(map);
		}
		try {
			write(JSON.toJSONString(list, SerializerFeature.WriteDateUseDateFormat));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	//获取banner图
	public void setBanner(){
		bannerList=new ArrayList<YwBanner>();
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
					String roomId = banner.getRoomId();
					// 广告图片
					//String img = "";
					if ("0".equals(banner.getType())) {
						if(!mapUserRoom.containsKey(roomId)){
							// 房间信息,过滤掉被删除的房间
							continue;
						}else{
							YwUserRoom userRoom = mapUserRoom.get(roomId);
							banner.setImg(userRoom.getLiveImg());
						}
					}
					bannerList.add(banner);
					i++;
				}
			}
		}
	}
	
	/**
	 * 美女主播专题
	 * @return
	 */
	public String meinvzhubo(){
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
		name = SysOptionServiceImpl.getValue("LANSHA.ANCHOR.H5");
		return SUCCESS;
	}
	
	public List<YwUserRoom> getRooms() {
		return rooms;
	}
	public List<YwGame> getHotGamesDH() {
		return hotGamesDH;
	}

	public void setHotGamesDH(List<YwGame> hotGamesDH) {
		this.hotGamesDH = hotGamesDH;
	}

	public List<YwBanner> getBannerList() {
		return bannerList;
	}

	public void setBannerList(List<YwBanner> bannerList) {
		this.bannerList = bannerList;
	}

	public List<YwUserRoom> getHotRooms() {
		return hotRooms;
	}

	public void setHotRooms(List<YwUserRoom> hotRooms) {
		this.hotRooms = hotRooms;
	}

	public void setRooms(List<YwUserRoom> rooms) {
		this.rooms = rooms;
	}

}
