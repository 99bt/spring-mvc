package com.yaowang.lansha.action.wap;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
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
import com.yaowang.lansha.entity.YwGame;
import com.yaowang.lansha.entity.YwGameHot;
import com.yaowang.lansha.entity.YwUser;
import com.yaowang.lansha.entity.YwUserRoom;
import com.yaowang.lansha.entity.YwUserRoomHot;
import com.yaowang.lansha.entity.YwUserRoomRelation;
import com.yaowang.lansha.service.YwGameHotService;
import com.yaowang.lansha.service.YwGameService;
import com.yaowang.lansha.service.YwUserRoomHotService;
import com.yaowang.lansha.service.YwUserRoomService;
import com.yaowang.lansha.service.YwUserService;
import com.yaowang.service.impl.SysOptionServiceImpl;
import com.yaowang.util.freemark.AppSetting;

/**
 * 
 * @Description: 导航栏点击游戏
 * @author wangjs
 * @date 2016-3-15
 * @version V1.0
 */
public class AllLiveAction extends LanshaBaseAction {
	private static final long serialVersionUID = 1L;

	@Resource
	private YwGameService ywGameService;
	@Resource
	private YwUserRoomService ywUserRoomService;
	@Resource
	private YwGameHotService ywGameHotService;

	private YwGame entity;// 游戏
	private String error;// 错误信息
	private List<YwUserRoom> roomlist;
	private List<YwGame> hotGamesDH;

	private Integer totalNum;//总数
	/**
	 * 
	 * @Description: 导航栏点击游戏
	 * @return String
	 * @throws
	 */
	public String liveList() {
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
		
		YwUserRoom yu = new YwUserRoom();
		if (StringUtils.isNotBlank(id)) {
			entity = ywGameService.getYwGameById(id);
			// 获取该游戏下房间
			page.setCount(true);
			yu.setGameId(id);
		}
		page.setRowNum(8);
		yu.setOnline(LanshaConstant.ROOM_STATUS_ONLINE);
		yu.setOrderSql("number*multiple_number+base_number desc, create_time");
		roomlist = ywUserRoomService.getAllLiveListByRoome(yu, page,null);
		ywUserRoomService.setData(roomlist,true);
		totalNum=page.getTotalNum();
		return SUCCESS;
	}

	/**
	 * 
	 * @Description: 推荐直播获取下一页
	 * @return void
	 * @throws
	 */
	public void nextLiveList(){
		List<Map<String,String>> list=new ArrayList<Map<String,String>>();
		YwUserRoom yu = new YwUserRoom();
		if(StringUtils.isNotBlank(id)){
			entity=ywGameService.getYwGameById(id);
			yu.setGameId(id);
		}
			//推荐直播
		PageDto page = getPageDto();
		page.setCount(false);
		page.setRowNum(8);			
		yu.setOnline(LanshaConstant.ROOM_STATUS_ONLINE);
		yu.setOrderSql("number*multiple_number+base_number desc, create_time");
		roomlist= ywUserRoomService.getAllLiveListByRoome(yu, page, null);
		ywUserRoomService.setData(roomlist,true);
		
		Map<String,String> map=null;
		for(YwUserRoom ywUserRoom:roomlist){			
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
	/**
	 * 设置数据
	 * 
	 * @param page
	 *            分页对象
	 * @param game
	 *            查询条件
	 * @param b
	 *            是否过滤没房间的游戏
	 * @return
	 * @creationDate. 2015-12-10 下午9:06:55
	 */
	public List<YwGame> setDate(PageDto page, YwGame game, boolean b) {
		list = ywGameService.getYwGameIndexPage(game, null, page, null, null,null, b);
		List<YwGame> data = (List<YwGame>) list;
		if (CollectionUtils.isEmpty(data)) {
			return data;
		}
		for (YwGame g : data) {
			YwUserRoom entity = new YwUserRoom();
			entity.setGameId(g.getId());
			entity.setOnline(LanshaConstant.ROOM_STATUS_ONLINE);
			entity.setOrderSql("number*multiple_number+base_number desc, create_time");
			List<YwUserRoom> d = ywUserRoomService.getAllLiveListByRoome(
					entity, null, null);
			ywUserRoomService.setData((List<YwUserRoom>) d,true);
			game.setYwUserRooms(d);
		}
		if (b) {
			// 设置数据：如果游戏下没有房间就不显示该游戏
			for (int i = data.size() - 1; i >= 0; i--) {
				YwGame ywGame = data.get(i);
				if (ywGame.getYwUserRooms().size() < 1) {
					data.remove(i);
				}
			}
		}
		return data;
	}

	public YwGame getEntity() {
		return entity;
	}

	public void setEntity(YwGame entity) {
		this.entity = entity;
	}

	public String getError() {
		return error;
	}

	public void setError(String error) {
		this.error = error;
	}

	public List<YwUserRoom> getRoomlist() {
		return roomlist;
	}

	public void setRoomlist(List<YwUserRoom> roomlist) {
		this.roomlist = roomlist;
	}

	public List<YwGame> getHotGamesDH() {
		return hotGamesDH;
	}

	public void setHotGamesDH(List<YwGame> hotGamesDH) {
		this.hotGamesDH = hotGamesDH;
	}

	public Integer getTotalNum() {
		return totalNum;
	}

	public void setTotalNum(Integer totalNum) {
		this.totalNum = totalNum;
	}



}
