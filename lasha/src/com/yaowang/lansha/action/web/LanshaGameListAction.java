package com.yaowang.lansha.action.web;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.Cookie;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;

import com.yaowang.common.dao.PageDto;
import com.yaowang.lansha.common.action.LanshaBaseAction;
import com.yaowang.lansha.common.constant.LanshaConstant;
import com.yaowang.lansha.entity.ActivityGiftStock;
import com.yaowang.lansha.entity.YwGame;
import com.yaowang.lansha.entity.YwGameHot;
import com.yaowang.lansha.entity.YwUser;
import com.yaowang.lansha.entity.YwUserRoom;
import com.yaowang.lansha.service.ActivityGiftStockService;
import com.yaowang.lansha.service.YwGameHotService;
import com.yaowang.lansha.service.YwGameService;
import com.yaowang.lansha.service.YwUserRoomService;
import com.yaowang.service.impl.SysOptionServiceImpl;
import com.yaowang.util.DateUtils;
import com.yaowang.util.NumberUtil;
import com.yaowang.util.UUIDUtils;
import com.yaowang.util.freemark.AppSetting;

public class LanshaGameListAction extends LanshaBaseAction {

	/**
	 * [2015-12-10下午3:11:23]zlb
	 * @Fields serialVersionUID : TODO
	 */
	
	private static final long serialVersionUID = 1L;
	
	@Resource
	private YwUserRoomService ywUserRoomService;
	
	@Resource
	private YwGameService ywGameService;
	
	@Resource
	private YwGameHotService ywGameHotService;

	@Resource
	private ActivityGiftStockService activityGiftStockService;
	
	
	
	//游戏截图
	private List<String> screens;
	
	//推荐游戏
	private List<YwGameHot> ywGameHots;
	
	//游戏详情
	private YwGame entity;
	
	//下载地址
	private String url;
	
	private String gameName;
	
	private String gameIco;
	
	private Integer count;//游戏总数
	
	private String luck;//是否可以抽奖图标 1是0否
	private String tolive;//表示是从详情过去
	
	/**
	 * 游戏专区-热门游戏
	 * @return
	 * @creationDate. 2015-12-11 下午1:59:24
	 */
	public String gameList(){
		//推荐游戏(默认显示三款推荐游戏)
		PageDto p = new PageDto();
		p.setRowNum(3);
		p.setCurrentPage(1);
		ywGameHots = ywGameHotService.getYwGameHotPage(null, p);
		ywGameHotService.setGame(ywGameHots);
		//倒序
		Collections.reverse(ywGameHots);
		YwGame game = new YwGame();
		game.setStatus(LanshaConstant.STATUS_ONLINE);
		PageDto page = getPageDto();
		setDate(page, game, true);
		count = ywGameService.getYwGameCount();
		return SUCCESS;
	}
	
	/**
	 * 加载更多
	 * @creationDate. 2015-12-11 下午2:00:34
	 * @throws IOException 
	 */
	public void getGameList() throws IOException{
		YwGame game = new YwGame();
		game.setStatus(LanshaConstant.STATUS_ONLINE);
		PageDto page = getPageDto();
		List<YwGame> data = setDate(page, game, true);
		List<Object> d = new ArrayList<Object>();
		
		for(YwGame g : data){
			Map<String, Object> map = new HashMap<String, Object>();
			List<Object> list = new ArrayList<Object>();
			map.put("addressId", "id=" + g.getId());
			map.put("gamedetailsImg", getUploadFilePath(g.getAdvert()));
			map.put("liveNum", g.getYwUserRooms().size());
			map.put("nameGame", g.getName());
			map.put("downloadQR", getUploadFilePath(g.getQrcode()));
			int i = 0;
			for(YwUserRoom room : g.getYwUserRooms()){
				Map<String, Object> m = new HashMap<String, Object>();
				i++;
				if(i%4 == 0){
					m.put("className", "game-one fl last");
				}else{
					m.put("className", "game-one fl");
				}
				m.put("liveAddress", AppSetting.getLivePathStatic(room.getIdInt()));
				m.put("gameImg", getUploadFilePath(room.getLiveImg()));
				m.put("livePic", getUploadFilePath(room.getUserIcon()));
				m.put("liveName", room.getNickname());
				m.put("liveTitle", room.getName());
				m.put("viewNum", room.getOnLineNumber());
				m.put("gameNume", room.getGameName());
				list.add(m);
			}
			map.put("gameList", list);
			d.add(map);
		}
		writeJson(d);
	}
	
	/**
	 * 全部游戏
	 * @return
	 * @creationDate. 2016-1-16 上午11:40:30
	 */
	public String gameCenter(){
		//推荐游戏(默认显示三款推荐游戏)
//		PageDto p = new PageDto();
//		p.setRowNum(3);
//		p.setCurrentPage(1);
//		ywGameHots = ywGameHotService.getYwGameHotPage(null, p);
//		ywGameHotService.setGame(ywGameHots);
////		//倒序
//		Collections.reverse(ywGameHots);
		YwGame game = new YwGame();
		game.setStatus(LanshaConstant.STATUS_ONLINE);
		setDate(null, game, false);
		count = list.size();
		return SUCCESS;
	}
	
	/**
	 * 设置数据
	 * @param page 分页对象
	 * @param game 查询条件
	 * @param b 是否过滤没房间的游戏
	 * @return
	 * @creationDate. 2015-12-10 下午9:06:55
	 */
	public List<YwGame> setDate(PageDto page, YwGame game, boolean b){
		list = ywGameService.getYwGameIndexPage(game, null, page, null, null, null, b);
		List<YwGame> data = (List<YwGame>)list;
		if(CollectionUtils.isEmpty(data)){
			return data;
		}
		for(YwGame g : data){
			YwUserRoom entity = new YwUserRoom();
			entity.setGameId(g.getId());
			entity.setOnline(LanshaConstant.ROOM_STATUS_ONLINE);
			entity.setOrderSql("number*multiple_number+base_number desc, create_time");
			List<YwUserRoom> d = ywUserRoomService.getAllLiveListByRoome(entity, null, null);
			ywUserRoomService.setData((List<YwUserRoom>)d,true);
			g.setYwUserRooms(d);
			//设置头像
			for (YwUserRoom room : (List<YwUserRoom>)d) {
				room.setUserIcon(getUploadFilePath(room.getUserIcon()));
			}
		}
		if(b){
			//设置数据：如果游戏下没有房间就不显示该游戏
			for(int i=data.size()-1;i>=0;i--){
				YwGame ywGame = data.get(i);
				if(ywGame.getYwUserRooms().size()<1){
					data.remove(i);
				}
			}
		}
		return data;
	}
	
	/**
	 * 游戏详情
	 * @return
	 * @creationDate. 2015-12-10 下午7:58:56
	 */
	public String gameDetail(){
		if(StringUtils.isBlank(id)){
			addActionError("游戏不存在");
			return "msg";
		}else{
			entity = ywGameService.getYwGameById(id);
			if(entity == null){
				addActionError("游戏不存在");
				return "msg";
			}
			screens = new ArrayList<String>();
			String[] ss = entity.getScreens();
			if(ss != null){
				screens = Arrays.asList(ss);
			}
			return SUCCESS;
		}
	}
	
	/**
	 * 在线直播
	 * @return
	 * @creationDate. 2015-12-10 下午7:59:21
	 */
	public String gameLive(){
		if(StringUtils.isBlank(id)){
			addActionError("游戏不存在");
			return "msg";
		}else{
			YwGame ywGame = ywGameService.getYwGameById(id);
			if(ywGame == null){
				addActionError("游戏不存在");
				return "msg";
			}
			YwGame game = new YwGame();
			game.setStatus(1);
			game.setId(id);
			PageDto page = new PageDto();
			page.setRowNum(24);
			setDate(page, game, false);
			entity = (YwGame) list.get(0);
			return SUCCESS;
		}
	}
	
	/**
	 * 王者荣耀推广
	 * @Description: TODO
	 * @return String
	 * @throws
	 */
	public String gameLiveWZRYTG(){
		//根据名称获取id		
		String gameName="王者荣耀";
		YwGame ywGame=ywGameService.getYwGameByName(gameName);
		if(ywGame==null){
			addActionError("游戏不存在");
			return "msg";
		}
		id=ywGame.getId();		
		gameLive();
		judgeActivityShow(ywGame);
		addCookie(LanshaConstant.LANSHA_ACTIVITY_TO_DETAIL, "");
		String hdlive=getCookie(LanshaConstant.LANSHA_ACTIVITY_TO_LIVE);
		if(!LanshaConstant.LANSHA_ACTIVITY_TO_LIVE_END.equals(hdlive)){
			addCookie(LanshaConstant.LANSHA_ACTIVITY_TO_LIVE, UUIDUtils.newId());
		}
		return SUCCESS;
	}
	public String gameDetailWZRYTG(){
		//根据名称获取id		
		String gameName="王者荣耀";
		YwGame ywGame=ywGameService.getYwGameByName(gameName);
		if(ywGame==null){
			addActionError("游戏不存在");
			return "msg";
		}
		id=ywGame.getId();
		
		gameDetail();

		addCookie(LanshaConstant.LANSHA_ACTIVITY_TO_DETAIL, UUIDUtils.newId());


		//judgeActivityShow(ywGame);  
		//addCookie(LanshaConstant.LANSHA_ACTIVITY_SHOW, "");//测试使用，暂时删除  
		//addCookie(LanshaConstant.LANSHA_ACTIVITY_TO_LIVE, "");//测试使用，暂时删除  
		
		return SUCCESS;
	}
	/**
	 * 穿越火线推广
	 * @Description: TODO
	 * @return String
	 * @throws
	 */
	public String gameLiveCFTG(){
		//根据名称获取id	
		String gameName="穿越火线";
		YwGame ywGame=ywGameService.getYwGameByName(gameName);
		if(ywGame==null){
			addActionError("游戏不存在");
			return "msg";
		}
		id=ywGame.getId();
		
		gameLive();
		
		judgeActivityShow(ywGame);
		addCookie(LanshaConstant.LANSHA_ACTIVITY_TO_DETAIL, "");

		String hdlive=getCookie(LanshaConstant.LANSHA_ACTIVITY_TO_LIVE);
		if(!LanshaConstant.LANSHA_ACTIVITY_TO_LIVE_END.equals(hdlive)){
			addCookie(LanshaConstant.LANSHA_ACTIVITY_TO_LIVE, UUIDUtils.newId());
		}
		return SUCCESS;
	}
	public String gameDetailCFTG(){
		//根据名称获取id
		String gameName="穿越火线";
		YwGame ywGame=ywGameService.getYwGameByName(gameName);
		if(ywGame==null){
			addActionError("游戏不存在");
			return "msg";
		}
		id=ywGame.getId();
		gameDetail();

		addCookie(LanshaConstant.LANSHA_ACTIVITY_TO_DETAIL, UUIDUtils.newId());

		//judgeActivityShow(ywGame);      
		return SUCCESS;
	}
	
	
	
	
	
	
	/**
	 * 
	 * @Description: 判断活动显示
	 * @return void 游戏名称
	 * @throws
	 */
	public void judgeActivityShow(YwGame ywGame){
		luck="0";
		String value = SysOptionServiceImpl.getValue("LANSHA.ACTIVITY.GAME");
		String TO_LD=getCookie(LanshaConstant.LANSHA_ACTIVITY_TO_LD);//表示已经从活动进入到直播间

        if (StringUtils.isNotEmpty(value) && StringUtils.isBlank(TO_LD)) {
			YwUser ywuser=getUserLogin();
            if(value.indexOf(ywGame.getName())>-1){//表示存在该游戏活动
        		//判断是否显示抽奖
        		if(StringUtils.isNotBlank(luck) && ywuser==null){//推广未登录的      			
        			luck="1";
        		}
        		//判断是否已经进来过
        		if("1".equals(luck)){//有抽奖活动图标才能判断是否隐藏抽奖图
        			//判断是否存在Cookie
        			String cjhd=getCookie(LanshaConstant.LANSHA_ACTIVITY_SHOW);
        			if(StringUtils.isBlank(cjhd)){//第一次打开
        				addCookie(LanshaConstant.LANSHA_ACTIVITY_SHOW, UUIDUtils.newId());//设置cookes
        			}else{
            			String todetail=getCookie(LanshaConstant.LANSHA_ACTIVITY_TO_DETAIL);
        				if(!"1".equals(tolive) || StringUtils.isBlank(todetail)){//第二次判断
        					luck="0";
        				}
        			}
        		}      		
            }
        }
	}
	
	/**
	 * 活动进入直播间
	 * @Description: TODO
	 * @return String
	 * @throws
	 */
	public void activityToLive(){
		   addCookie(LanshaConstant.LANSHA_ACTIVITY_TO_LD, UUIDUtils.newId());//添加已经进入的标志

			//根据游戏获随机取一个在线直播间
			PageDto page = getPageDto();
			page.setCount(false);
			page.setCurrentPage(1);
			page.setRowNum(8);
			YwUserRoom entity = new YwUserRoom();
			entity.setGameId(id);
			entity.setOnline(LanshaConstant.ROOM_STATUS_ONLINE);
			entity.setOrderSql("number*multiple_number+base_number desc, create_time");
			List<YwUserRoom> roomlist = ywUserRoomService.getAllLiveListByRoome(entity, page,null);
			ywUserRoomService.setData(roomlist,true);
			int size=roomlist.size();
			if(size>0){
				int index=NumberUtil.getRandom(size);
				YwUserRoom ywUserRoom=roomlist.get(index);
				try {
					getResponse().sendRedirect(getContextPath() +"/"+ywUserRoom.getIdInt());
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			
			
	}
	
	/**
	 * @throws IOException 
	 * 判断活动是否显示QQ
	 * @Description: TODO
	 * @return void
	 * @throws
	 */
	public void isShowQq() throws IOException{
	
		YwUser yu=getUserLogin();		
		Date newuser=getActivityNewUser();
		if(yu!=null && DateUtils.compare(yu.getCreateTime(),newuser)>-1){
			write("{\"status\": 1}");
		}else{
			write("{\"status\": 0}");
		}
		
	}
	/**
	 * 游戏下载
	 * @return
	 * @creationDate. 2015-12-11 下午5:59:03
	 */
	public String download(){
		YwGame ywGame = ywGameService.getYwGameById(id);
		gameName = ywGame.getName();
		gameIco = getUploadFilePath(ywGame.getIcon());
		//判断系统类型
		String header = getRequest().getHeader("User-agent").toLowerCase();
		if(header.contains("android")){
			url = ywGame.getAndroidUrl();
		}else if(header.contains("iphone") || header.contains("ipad")){
			url = ywGame.getIosUrl();
		}
		return SUCCESS;
	}
	
	public PageDto getPageDto(){
		PageDto page = super.getPageDto();
		page.setCount(false);
		page.setRowNum(3);
		return page;
	}
	
	public List<YwGameHot> getYwGameHots() {
		return ywGameHots;
	}

	public void setYwGameHots(List<YwGameHot> ywGameHots) {
		this.ywGameHots = ywGameHots;
	}

	public List<String> getScreens() {
		return screens;
	}

	public void setScreens(List<String> screens) {
		this.screens = screens;
	}

	public YwGame getEntity() {
		return entity;
	}

	public void setEntity(YwGame entity) {
		this.entity = entity;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public Integer getCount() {
		return count;
	}

	public void setCount(Integer count) {
		this.count = count;
	}

	public String getGameName() {
		return gameName;
	}

	public void setGameName(String gameName) {
		this.gameName = gameName;
	}

	public String getGameIco() {
		return gameIco;
	}

	public void setGameIco(String gameIco) {
		this.gameIco = gameIco;
	}


	

	public String getLuck() {
		return luck;
	}

	public void setLuck(String luck) {
		this.luck = luck;
	}

	public String getTolive() {
		return tolive;
	}

	public void setTolive(String tolive) {
		this.tolive = tolive;
	}


	
}
