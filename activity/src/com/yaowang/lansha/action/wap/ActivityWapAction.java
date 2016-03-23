package com.yaowang.lansha.action.wap;

import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.collections.CollectionUtils;

import com.yaowang.lansha.action.web.ActivityAction;
import com.yaowang.lansha.common.constant.LanshaConstant;
import com.yaowang.lansha.entity.ActivityGiftStock;
import com.yaowang.lansha.entity.ActivityUserStock;
import com.yaowang.lansha.entity.YwUser;
import com.yaowang.lansha.entity.YwUserRoom;
import com.yaowang.lansha.service.LogUserLoginService;
import com.yaowang.service.impl.SysOptionServiceImpl;

public class ActivityWapAction extends ActivityAction {
	private static final long serialVersionUID = 1L;
//	@Resource
//	private YwUserRoomService ywUserRoomService;
//	@Resource
//	private YwGameService ywGameService;
	@Resource
	private LogUserLoginService logUserLoginService;
	
	private List<ActivityGiftStock> giftStockList;
	List<ActivityGiftStock> myGiftStockList;
	private List<YwUserRoom> hotRooms;
	private String loginMethod = "";
//	private String[] mobilePrefix = new String[] { "130", "131", "132", "133",
//			"134", "135", "136", "137", "138", "139", "145", "147", "150",
//			"151", "152", "153", "155", "156", "157", "158", "159", "176",
//			"177", "180", "181", "182", "183", "185", "186", "187", "188",
//			"189" };// 初始化移动，联通，电信号前三位号码段，用于随机生成手机号码
	private Integer myGiftNumber = 0;
	private String gameName="";

	public ActivityWapAction() {
		this.setGameName("王者荣耀");
		super.itemId = LanshaConstant.ITEM_ID_TWO;// 设置当前活动
		super.defaultGiftId = LanshaConstant.Q_GIFT_ID;// 设置当前活动默认奖品		
		super.setShareLink("/wap/activity/index.html");//设置分享的链接
		super.setShareContent(SysOptionServiceImpl.getValue("ACTIVITY.WZRY.SHARE"));//设置分享的内容
	}

	@Override
	public String index() {
		if (!checkCurrentActivity()) {
			addActionError("当前活动不存在");
			return "msg";
		}
		YwUser ywUser = getUserLogin();
		if (ywUser == null) {
			setUserStock(1); // 需求定义：用户未登录默认是1，引导新用户去注册			
		} else {
			ActivityUserStock queryItem = new ActivityUserStock();
			queryItem.setItemId(itemId);
			queryItem.setUserId(ywUser.getId());
			List<ActivityUserStock> result = activityUserStockService.getActivityUserStockList(queryItem);
			if (CollectionUtils.isEmpty(result)) {
				setUserStock(0);
			} else {
				setUserStock(result.get(0).getStock());
			}
			// 获取我的中奖纪录列表
			getMyGifts(ywUser.getId());
		}
		// 获取中奖纪录列表
		//getVirtualGifts();//2016-01-27 需求变更，wap端暂时不显示虚拟中奖信息。 产品：MJ
		
		// 获取前4条最热房间根据游戏名和在线状态-王者荣耀
		//getHotRoomList(); //2016-01-27 需求变更，wap端暂时不显示视频 产品：MJ
		//获取微信分享内容
		super.getWechatSharedContent(ywUser == null?"":ywUser.getId());
		return SUCCESS;
	}
	
	private void getMyGifts(String userId){
		ActivityGiftStock searchItem = new ActivityGiftStock();
		searchItem.setItemId(itemId);
		searchItem.setUserId(userId);
		myGiftStockList = activityGiftStockService.getIndexActivityGiftStockList(searchItem);
		activityGiftStockService.setUserInfo(myGiftStockList);
		activityGiftStockService.setGiftName(myGiftStockList);
		myGiftNumber = myGiftStockList.size();
		// 判断登陆用户是否第一次用APP登陆
		Integer loignNumber = logUserLoginService
				.getLogUserLoginCountByUserId(userId, null, null,
						"0");
		if (loignNumber > 0) {
			loginMethod = "0";
		}
	}
	
//	private void getVirtualGifts(){		
//		ActivityGiftStock searchItem = new ActivityGiftStock();
//		searchItem.setItemId(itemId);
//		searchItem.setLimitRows(1);// 获取一个真实的中奖用户
//		giftStockList = getActivityGiftStockService()
//				.getIndexActivityGiftStockList(searchItem);
//		getActivityGiftStockService().setUserInfo(giftStockList);
//		getActivityGiftStockService().setGiftName(giftStockList);
//		Pattern p = Pattern.compile("(\\w{3})(\\w+)(\\w{3})");
//		for (ActivityGiftStock item : giftStockList) {
//			item.setUserName(p.matcher(item.getUserName())
//					.replaceAll("$1***$3"));// 格式化用户账号
//		}
//		if (giftStockList == null) {
//			giftStockList = new ArrayList<ActivityGiftStock>();
//		}
//		giftStockList.addAll(generateMockupGiftList(19));// 返回19个虚拟中奖用户和1个真实用户
//															// 如果真实用户没有就返回19个虚拟中奖用户
//		Collections.shuffle(giftStockList);// 随机打乱中奖奖品顺序
//	}
//	
//	/**
//	 * @Description:获取前4条最热房间根据游戏名和在线状态-王者荣耀
//	 */
//	private void getHotRoomList() {
//		PageDto page = getPageDto();
//		page.setCount(false);
//		page.setRowNum(4);
//		YwUserRoom entity = new YwUserRoom();
//		YwGame ywGame = ywGameService.getYwGameByName(gameName);
//		entity.setOnline(1);
//		if (ywGame != null) {
//			entity.setGameId(ywGame.getId());
//		}
//		entity.setOrderSql("number*multiple_number+base_number desc, create_time");
//		hotRooms = ywUserRoomService.getAllLiveListByRoome(entity, page, null);
//		if (hotRooms == null) {
//			hotRooms = new ArrayList<YwUserRoom>();
//		}
//		if (hotRooms.size() < 4) {// 如果得到的王者荣耀最热房间数小于4那么拿其它最热房间补充
//			page.setRowNum(4 + hotRooms.size());
//			entity.setOnline(1);
//			entity.setGameId(null);
//			List<YwUserRoom> hotRoomsTwo = ywUserRoomService
//					.getAllLiveListByRoome(entity, page, null);
//			if (!CollectionUtils.isEmpty(hotRoomsTwo)) {
//				for (YwUserRoom firstRoom : hotRooms) {
//					for (int i = hotRoomsTwo.size() - 1; i >= 0; i--) {// 如果有重复的房间就删除重复的
//						if (firstRoom.getId().equalsIgnoreCase(
//								hotRoomsTwo.get(i).getId())) {
//							hotRoomsTwo.remove(i);
//							break;
//						}
//					}
//				}
//				if (hotRoomsTwo.size() >= (4 - hotRooms.size())) {
//					hotRooms.addAll(hotRoomsTwo.subList(0, 4 - hotRooms.size()));
//				} else {
//					hotRooms.addAll(hotRoomsTwo);
//				}
//			}
//			if (hotRooms.size() < 4) {// 如果得到的其它最热房间数小于4那么拿其它下线的房间补充
//				entity.setOnline(0);
//				page.setRowNum(4 - hotRooms.size());
//				hotRooms.addAll(ywUserRoomService.getAllLiveListByRoome(entity,
//						page, null));
//			}
//		}
//		ywUserRoomService.setData((List<YwUserRoom>) hotRooms);
//	}

//	/**
//	 * @Description: 生成虚拟中奖纪录
//	 * @param size
//	 *            生成虚拟中奖纪录的个数
//	 */
//	private List<ActivityGiftStock> generateMockupGiftList(int size) {
//		List<ActivityGiftStock> resultList = new ArrayList<ActivityGiftStock>();
//		ActivityGift searchItem = new ActivityGift();
//		searchItem.setItemId(itemId);
//		searchItem.setStatus("1");
//		List<ActivityGift> gifts = getActivityGiftService()
//				.getActivityGiftList(searchItem);
//		Random rand = new Random();
//		int tmpIndex = 0;
//		long mobileSuffix = 0;
//		for (int i = 0; i < size; i++) {
//			ActivityGiftStock tmpItem = new ActivityGiftStock();
//			tmpIndex = rand.nextInt(mobilePrefix.length);// 随机获取号码段索引
//			mobileSuffix = Math.round(Math.random() * 1000);// 随机后三位号码
//			if (mobileSuffix < 899) {
//				mobileSuffix += 100;// 保证随机数一定是3位数
//			}
//			tmpItem.setUserName(mobilePrefix[tmpIndex] + "***" + mobileSuffix);// 设置虚拟中奖用户
//			tmpIndex = rand.nextInt(gifts.size());// 随机获取虚拟奖品索引
//			tmpItem.setGiftName(gifts.get(tmpIndex).getName());// 设置虚拟奖品
//			tmpItem.setType(gifts.get(tmpIndex).getType());// 设置虚拟奖品类型
//			resultList.add(tmpItem);
//		}
//		return resultList;
//	}

	public List<ActivityGiftStock> getGiftStockList() {
		return giftStockList;
	}

	public void setGiftStockList(List<ActivityGiftStock> giftStockList) {
		this.giftStockList = giftStockList;
	}

	public List<YwUserRoom> getHotRooms() {
		return hotRooms;
	}

	public void setHotRooms(List<YwUserRoom> hotRooms) {
		this.hotRooms = hotRooms;
	}

	public List<ActivityGiftStock> getMyGiftStockList() {
		return myGiftStockList;
	}

	public void setMyGiftStockList(List<ActivityGiftStock> myGiftStockList) {
		this.myGiftStockList = myGiftStockList;
	}

	public String getLoginMethod() {
		return loginMethod;
	}

	public void setLoginMethod(String loginMethod) {
		this.loginMethod = loginMethod;
	}

	public Integer getMyGiftNumber() {
		return myGiftNumber;
	}

	public void setMyGiftNumber(Integer myGiftNumber) {
		this.myGiftNumber = myGiftNumber;
	}
	
	public String getGameName() {
		return gameName;
	}

	public void setGameName(String gameName) {
		this.gameName = gameName;
	}
}
