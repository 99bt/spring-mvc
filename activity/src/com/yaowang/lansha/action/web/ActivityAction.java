package com.yaowang.lansha.action.web;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

import javax.annotation.Resource;
import org.apache.commons.lang3.StringUtils;

import com.yaowang.lansha.action.api.WechatShareAction;
import com.yaowang.lansha.common.constant.LanshaConstant;
import com.yaowang.lansha.entity.ActivityGift;
import com.yaowang.lansha.entity.ActivityGiftStock;
import com.yaowang.lansha.entity.ActivityItem;
import com.yaowang.lansha.entity.ActivityUser;
import com.yaowang.lansha.entity.ActivityUserStock;
import com.yaowang.lansha.entity.YwUser;
import com.yaowang.lansha.service.ActivityGiftService;
import com.yaowang.lansha.service.ActivityGiftStockService;
import com.yaowang.lansha.service.ActivityItemService;
import com.yaowang.lansha.service.ActivityUserService;
import com.yaowang.lansha.service.ActivityUserStockService;
import com.yaowang.service.impl.SysOptionServiceImpl;

public class ActivityAction extends WechatShareAction {
	private static final long serialVersionUID = 1L;
	@Resource
	private ActivityUserService activityUserService;
	@Resource
	private ActivityGiftService activityGiftService;
	@Resource
	protected ActivityGiftStockService activityGiftStockService;
	@Resource
	private ActivityItemService activityItemService;
	@Resource
	protected ActivityUserStockService activityUserStockService;

	private Integer userStock;	//用户抽奖机会	
	private String userActivityLink; //推广链接	
	protected String itemId = LanshaConstant.ITEM_ID;
	private List<ActivityGift> giftList;
	protected String defaultGiftId = null;
	private ActivityUser receiver; //奖品领取人信息

	/**
	 * 抽奖
	 * @throws IOException 
	 */
	public void action() throws IOException{
		YwUser ywUser = getUserLogin();
		if (ywUser == null) {
			write(getFailedLogin());
			return;
		}
		if(StringUtils.isEmpty(itemId)){
			write(getFailed("当前活动不存在"));
			return;
		}
		ActivityItem item = activityItemService.getActivityItemById(itemId);
		if(item==null){
			write(getFailed("当前活动不存在"));
			return;
		}
		//判断活动有效时间
		if(!"1".equalsIgnoreCase(item.getStatus())){
			write(getFailed("活动已经下线"));
			return;
		}		
		if(item.getStartTime().getTime()>(getNow()).getTime()){
			write(getFailed("活动还没开始"));
			return;
		}
		//判断抽奖次数-1，时间间隔是否合法
		//随机奖品
		//奖品数-1
		//添加中奖纪录				
		Map<String, Object> map = new HashMap<String, Object>();
		try{
			ActivityGiftStock itemLottery = new ActivityGiftStock();
			itemLottery.setItemId(itemId);
			itemLottery.setUserId(ywUser.getId());
			itemLottery.setIp(getClientIP());
			itemLottery.setDefaultGiftId(defaultGiftId);
			activityGiftStockService.updateForLottery(itemLottery);
			
			map.put("id", itemLottery.getId());
			map.put("states", itemLottery.getPrizeIndex());
			map.put("name", itemLottery.getGiftName());
			map.put("isentity", itemLottery.getGiftProperty());
			map.put("money", "");
			if("1".equalsIgnoreCase(itemLottery.getGiftProperty())){
				map.put("realname", itemLottery.getRealName());
				map.put("address", itemLottery.getAddress());
				map.put("telphone",itemLottery.getMobile());
			}
			writerEntity(map);
		}catch(Exception e){
			write(getFailed("抽奖失败"));
		}
	}
	
	/**
	 * 活动首页
	 * @return
	 */
	public String index(){
		
		if(!checkCurrentActivity()){
			addActionError("当前活动不存在");
			return "msg";
		}
		YwUser ywUser = getUserLogin();
		if(ywUser==null){
			userActivityLink = getHostPath() + getContextPath() + "/activity/index.html";
		}else{			
			userActivityLink = getHostPath()+getContextPath() + "/activity/index.html?pn="+ywUser.getId();
		}
		return SUCCESS;
	}
	
	public String receiver(){
		if(receiver==null){
			addActionError("请填写领取人信息");
			return "msg";
		}
		YwUser ywUser = getUserLogin();
		if(ywUser==null){
			addActionError(getFailedLogin());
			return "msg";
		}
		receiver.setId(ywUser.getId());
		activityUserService.updateUserInfo(receiver);
		index();
		return SUCCESS;
	}
	
	protected boolean checkCurrentActivity(){
		if(StringUtils.isEmpty(itemId)){
			return false;
		}
		ActivityItem item = activityItemService.getActivityItemById(itemId);
		if(item==null){
			return false;
		}
		return true;
	}
	
	/**
	 * 退出
	 * @return
	 * @throws IOException 
	 */
	public void logout() throws IOException{
		setUserLogin(null);
		getResponse().sendRedirect(getContextPath()+"/activity/index.html");
	}
	
	/**
	 * 奖品列表接口
	 * @return
	 */
	public void giftList()throws IOException{
		if(StringUtils.isEmpty(itemId)){
			write(getFailed("当前活动不存在"));
			return ;
		}
		ActivityItem item = activityItemService.getActivityItemById(itemId);
		if(item==null){
			write(getFailed("当前活动不存在"));
			return ;
		}
		Map<String, Object> map = new HashMap<String, Object>();
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		ActivityGift searchItem = new ActivityGift();
		searchItem.setItemId(itemId);
		searchItem.setStatus("1");
		List<ActivityGift> gifts = activityGiftService.getActivityGiftList(searchItem);
		for(ActivityGift gift: gifts){
			Map<String, Object> giftmap = new HashMap<String, Object>();
			giftmap.put("id", gift.getId());
			giftmap.put("name", gift.getName());
			giftmap.put("money", "");
			giftmap.put("number", gift.getNumber());
			giftmap.put("stock", gift.getStock());
			giftmap.put("img", "");
			list.add(giftmap);
		}
		map.put("name", item.getName());
		map.put("img", "");
		map.put("gifts", list);
		writerEntity(map);
	}
	
	public void remainTimes() throws IOException{
		YwUser ywUser = getUserLogin();		
		Map<String, Object> map = new HashMap<String, Object>();
		if(ywUser==null){
			map.put("remainTimes", "0");
		}else{			
			ActivityUserStock queryItem = new ActivityUserStock();
			queryItem.setItemId(itemId);
			queryItem.setUserId(ywUser.getId());
			List<ActivityUserStock> result = activityUserStockService.getActivityUserStockList(queryItem);
			if(result==null||result.size()<1){
				map.put("remainTimes", "0");
			}else{
				map.put("remainTimes", String.valueOf(result.get(0).getStock()));
			}
		}
		writerEntity(map);
	}
	
	/**
	 * 中奖纪录接口 
	 * @return
	 * @throws IOException 
	 */
	public void list() throws IOException{
		if(!checkCurrentActivity()){
			write(getFailed("当前活动不存在"));
			return ;
		}		
		ActivityGiftStock searchItem = new ActivityGiftStock();
		searchItem.setItemId(itemId);
		searchItem.setLimitRows(20);
		List<ActivityGiftStock> giftStockList = activityGiftStockService.getIndexActivityGiftStockList(searchItem);
		activityGiftStockService.setUserInfo(giftStockList);
		activityGiftStockService.setGiftName(giftStockList);
		SimpleDateFormat sd = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		List<Map<String, String>> giftStocks = new ArrayList<Map<String,String>>();
		Pattern p = Pattern.compile("(\\w{3})(\\w+)(\\w{3})"); 
		for(ActivityGiftStock item : giftStockList){
			Map<String, String> resultMmap = new HashMap<String, String>();
			resultMmap.put("username", p.matcher(item.getUserName()).replaceAll("$1***$3"));
			resultMmap.put("nickName", item.getNickName());
			resultMmap.put("icon", "");
			resultMmap.put("id", item.getUserId());
			resultMmap.put("datetime", sd.format(item.getCreateTime()));
			resultMmap.put("giftName", item.getGiftName());
			giftStocks.add(resultMmap);
		}
		writeJson(giftStocks);
	}
	
	/**
	 * 我的中奖记录接口
	 * @return
	 * @throws IOException 
	 */
	public void mylist() throws IOException{
		if(!checkCurrentActivity()){
			addActionError("当前活动不存在");
			return ;
		}
		YwUser ywUser = getUserLogin();
		if(ywUser==null){
			write(getFailed("当前用户不存在"));
			return ;
		}
		ActivityGiftStock searchItem = new ActivityGiftStock();
		searchItem.setItemId(itemId);
		searchItem.setUserId(ywUser.getId());
		List<ActivityGiftStock> myGiftStockList = activityGiftStockService.getIndexActivityGiftStockList(searchItem);
		activityGiftStockService.setUserInfo(myGiftStockList);
		activityGiftStockService.setGiftName(myGiftStockList);
		SimpleDateFormat sd = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		List<Map<String, String>> myGiftStocks = new ArrayList<Map<String,String>>();
		for(ActivityGiftStock item : myGiftStockList){
			Map<String, String> resultMmap = new HashMap<String, String>();
			resultMmap.put("username", item.getUserName());
			resultMmap.put("nickName", item.getNickName());
			resultMmap.put("icon", "");
			resultMmap.put("id", item.getUserId());
			resultMmap.put("datetime", sd.format(item.getCreateTime()));
			resultMmap.put("giftName", item.getGiftName());
			myGiftStocks.add(resultMmap);
		}
		writeJson(myGiftStocks);
	}
	
	/**
	 * 新年发红包
	 * @return
	 * @creationDate. 2016-2-4 下午4:16:09
	 */
	public String fahongbao(){
		if (getIsWap()) {
			//wap
			return "wap";
		}else{
			return "web";
		}
	}
	
	/**
	 * 获取抽奖最少时间间隔(秒)
	 * @return
	 */
	public Integer getLimitTime(){
		return Integer.valueOf(SysOptionServiceImpl.getValue("ACTIVITY.LIMIT.TIME"));
	}
	
	public Integer getUserStock() {
		return userStock;
	}

	public void setUserStock(Integer userStock) {
		this.userStock = userStock;
	}

	public String getUserActivityLink() {
		return userActivityLink;
	}

	public void setUserActivityLink(String userActivityLink) {
		this.userActivityLink = userActivityLink;
	}

	public List<ActivityGift> getGiftList() {
		return giftList;
	}

	public void setGiftList(List<ActivityGift> giftList) {
		this.giftList = giftList;
	}	

	public ActivityUser getReceiver() {
		return receiver;
	}

	public void setReceiver(ActivityUser receiver) {
		this.receiver = receiver;
	}	
}
