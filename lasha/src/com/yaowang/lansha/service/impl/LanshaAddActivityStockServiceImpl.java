package com.yaowang.lansha.service.impl;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import com.yaowang.lansha.common.constant.LanshaConstant;
import com.yaowang.lansha.entity.ActivityItem;
import com.yaowang.lansha.entity.ActivityStockAdd;
import com.yaowang.lansha.entity.ActivityUser;
import com.yaowang.lansha.entity.ActivityUserStock;
import com.yaowang.lansha.entity.YwUser;
import com.yaowang.lansha.entity.YwUserRoomHistory;
import com.yaowang.lansha.service.ActivityItemService;
import com.yaowang.lansha.service.ActivityUserService;
import com.yaowang.lansha.service.ActivityUserStockService;
import com.yaowang.lansha.service.LanshaAddActivityStockService;
import com.yaowang.lansha.service.LogUserLoginService;
import com.yaowang.lansha.service.YwUserRoomHistoryService;
import com.yaowang.lansha.service.YwUserService;
import com.yaowang.util.DateUtils;
/**
 * 抽奖机会公共方法
 * ClassName: LanshaAddActivityStockServiceImpl 
 * @Description: TODO
 * @author zlb
 * @date 2016-1-11
 */
@Service("lanshaAddActivityStockService")
public class LanshaAddActivityStockServiceImpl implements LanshaAddActivityStockService {
	@Resource
	private YwUserService ywUserService;
	@Resource
	private ActivityUserService activityUserService;
	@Resource
	private ActivityUserStockService activityUserStockService;
	@Resource
	private LogUserLoginService logUserLoginService;
	@Resource
	private YwUserRoomHistoryService ywUserRoomHistoryService;
	@Resource
	private ActivityItemService activityItemService;
//	@Resource
//	private ActivityGiftStockService activityGiftStockService;
//	@Resource
//	private LogUserLoginDao logUserLoginDao;
	
	/**
	 * 注册时增加抽奖机会
	 * @param ywUser
	 * @creationDate. 2016-1-11 下午2:18:42
	 */
	public void addRegActivityStock(YwUser ywUser){
		//获得上线的活动
		List<ActivityItem> activityItems = getOnline();
		if(CollectionUtils.isEmpty(activityItems)){
			return;
		}
		List<ActivityStockAdd> list = new ArrayList<ActivityStockAdd>();
		for(ActivityItem ai : activityItems){
			ActivityStockAdd asa = new ActivityStockAdd();
			asa.setItemId(ai.getId());
			//注册增加机会数
			String[] ss = ai.getChances();
			asa.setRegAdd(Integer.parseInt(ss[0]));
			//被推广注册获得机会数
			asa.setRegSpreadAdd(Integer.parseInt(ss[1]));
			//推广人获得机会数
			asa.setSpreadAdd(Integer.parseInt(ss[1]));
			list.add(asa);
		}
		
		for(ActivityStockAdd activityStockAdd : list){
			int num = activityStockAdd.getRegAdd();
			if (StringUtils.isNotBlank(ywUser.getParentId())) {
				//根据分享的链接注册推广人和注册人各增加抽奖机会
				num += activityStockAdd.getRegSpreadAdd();
			}
			if(num > 0){
				int count = activityUserStockService.updateUserStock(num, ywUser.getId(), activityStockAdd.getItemId());
				if(count <= 0){
					//抽奖用户无数据则新增
					addCheckData(ywUser, num, activityStockAdd.getItemId());
				}
			}
			
			if(activityStockAdd.getSpreadAdd() > 0){
				//根据分享的链接注册推广人和注册人各增加抽奖机会
				if (StringUtils.isNotBlank(ywUser.getParentId())) {
					List<ActivityUserStock> data = new ArrayList<ActivityUserStock>();

					//总共3层推广
					int count = 2;
					YwUser u1 = ywUser;
					for (int i = 0; i < count; i++) {
						if (StringUtils.isBlank(u1.getParentId())) {
							break;
						}
						//增加推广人抽奖机会
						ActivityUserStock a2 = new ActivityUserStock();
						a2.setUserId(u1.getParentId());
						a2.setItemId(activityStockAdd.getItemId());
						a2.setAdd(activityStockAdd.getSpreadAdd());
						data.add(a2);
						
						if ((i + 1) < count) {
							u1 = ywUserService.getYwUserById(u1.getParentId());
						}
					}

					activityUserStockService.updateManyUserStock(data);
				}
			}
		}
	}
	
	/**
	 * 登录时增加抽奖机会(连续登录)
	 * @param ywUser
	 * @creationDate. 2016-1-11 下午2:19:04
	 */
	public void addLoginActivityStock(YwUser ywUser){
		Calendar cal = Calendar.getInstance();
		//+1天（第二天）
		cal.setTime(ywUser.getCreateTime());
		cal.add(Calendar.DAY_OF_MONTH, 1);
		Date beginDate = DateUtils.getStartDate(cal.getTime());
		//+1天（第四天）
		cal.add(Calendar.DAY_OF_MONTH, 2);
		Date endDate = DateUtils.getEndDate(cal.getTime());
		//当前时间
		Date now = DateUtils.getStartDate(new Date());
		/**
		 * 注册第二天到第四天增加抽奖机会
		 */
		if(now.getTime() < beginDate.getTime() || now.getTime() > endDate.getTime()){
			return;
		}

		//每天第一次登录增加抽奖机会
		Integer logins = logUserLoginService.getLogUserLoginCountByUserId(ywUser.getId(), DateUtils.getStartDate(now), DateUtils.getEndDate(now));
		if (logins > 0) {
			return;
		}
		
		List<Date> datas = new ArrayList<Date>();
		//需要判断连续登录的天数
		int day = (int)(now.getTime() - beginDate.getTime()) / (24 * 60 * 60 * 1000);
		for (int i = 0; i < day; i++) {
			datas.add(new Date(beginDate.getTime() + i * (24 * 60 * 60 * 1000)));
		}
		
		//循环判断是否连续登录
		for (Date time : datas) {
			Integer temps = logUserLoginService.getLogUserLoginCountByUserId(ywUser.getId(), DateUtils.getStartDate(time), DateUtils.getEndDate(time));
			if (temps == 0) {
				return;
			}
		}
		
		//获得上线的活动
		List<ActivityItem> activityItems = getOnline();
		if(CollectionUtils.isEmpty(activityItems)){
			return;
		}
		List<ActivityStockAdd> list = new ArrayList<ActivityStockAdd>();
		for(ActivityItem ai : activityItems){
			ActivityStockAdd asa = new ActivityStockAdd();
			asa.setItemId(ai.getId());
			//连续登录获得推广机会数
			String[] ss = ai.getChances();
			asa.setLoginAdd(Integer.parseInt(ss[2]));
			if(asa.getLoginAdd() > 0){
				list.add(asa);
			}
		}
		
		for(ActivityStockAdd stock : list) {
			int count = activityUserStockService.updateUserStock(stock.getLoginAdd(), ywUser.getId(), stock.getItemId());
			if (count <= 0) {
				//抽奖用户无数据则新增
				addCheckData(ywUser, stock.getLoginAdd(), stock.getItemId());
			}
		}
	}
	
	
	/**
	 * 领取虾米获得抽奖机会
	 * @param ywUser
	 * @creationDate. 2016-1-11 下午3:17:13
	 */
	public void addActivityStock(YwUser ywUser){
		Calendar cal = Calendar.getInstance();
		//+1天（第二天）
		cal.setTime(ywUser.getCreateTime());
		cal.add(Calendar.DAY_OF_MONTH, 1);
		Date beginDate = DateUtils.getStartDate(cal.getTime());
		//+1天（第四天）
		cal.add(Calendar.DAY_OF_MONTH, 2);
		Date endDate = DateUtils.getEndDate(cal.getTime());
		//当前时间
		Date now = DateUtils.getStartDate(new Date());
		/**
		 * 注册第二天到第四天增加抽奖机会
		 */
		if(now.getTime() >= beginDate.getTime() && now.getTime() <= endDate.getTime()){
			List<Date> datas = new ArrayList<Date>();
			//需要判断连续登录的天数
			int day = (int)(now.getTime() - beginDate.getTime()) / (24 * 60 * 60 * 1000);
			for (int i = 0; i < day; i++) {
				datas.add(new Date(beginDate.getTime() + i * (24 * 60 * 60 * 1000)));
			}
			
			//循环判断是否连续登录
			boolean b = true;
			for (Date time : datas) {
				Integer temps = logUserLoginService.getLogUserLoginCountByUserId(ywUser.getId(), DateUtils.getStartDate(time), DateUtils.getEndDate(time));
				if (temps == 0) {
					b = false;
					break;
				}
			}
			if(!b){
				return;
			}
		}
		
		//获得上线的活动
		List<ActivityItem> activityItems = getOnline();
		if(CollectionUtils.isEmpty(activityItems)){
			return;
		}
		List<ActivityStockAdd> list = new ArrayList<ActivityStockAdd>();
		for(ActivityItem ai : activityItems){
			ActivityStockAdd asa = new ActivityStockAdd();
			asa.setItemId(ai.getId());
			//获得抽奖次数
			String[] ss = ai.getChances();
			asa.setXiamiAdd(Integer.parseInt(ss[3]));
			if(asa.getXiamiAdd() > 0){
				list.add(asa);
			}
		}
		
		for(ActivityStockAdd activityStockAdd : list){
			/**
			 * 注册第二天到第四天连续登陆领取蓝鲨币增加抽奖机会
			 */
			YwUserRoomHistory entity = new YwUserRoomHistory();
			entity.setUid(ywUser.getId());
			entity.setTypeId(LanshaConstant.GIFT_ID);//礼物为领虾米
			//没有领取过虾米时获得抽奖机会
			Integer YwUserRoomHistorys = ywUserRoomHistoryService.getYwUserRoomHistoryCount(entity, now, DateUtils.getEndDate(now));
			if(YwUserRoomHistorys == 0){
				int count = activityUserStockService.updateUserStock(activityStockAdd.getXiamiAdd(), ywUser.getId(), activityStockAdd.getItemId());
				if(count <= 0){
					//抽奖用户无数据则新增
					addCheckData(ywUser, activityStockAdd.getXiamiAdd(), activityStockAdd.getItemId());
				}
			}	
		}
	}
	
	/**
	 * 当主播成为主播增加主播和推广人抽奖机会
	 * @param ywUser
	 * @creationDate. 2016-1-11 下午3:48:21
	 */
	public void addHostStock(YwUser ywUser){
		//获得上线的活动
		List<ActivityItem> activityItems = getOnline();
		if(CollectionUtils.isEmpty(activityItems)){
			return;
		}
		List<ActivityStockAdd> list = new ArrayList<ActivityStockAdd>();
		for(ActivityItem ai : activityItems){
			ActivityStockAdd asa = new ActivityStockAdd();
			asa.setItemId(ai.getId());
			//抽奖机会
			String[] ss = ai.getChances();
			asa.setHostAdd(Integer.parseInt(ss[4]));
			if(asa.getHostAdd() > 0){
				list.add(asa);
			}
		}
		
		for(ActivityStockAdd activityStockAdd : list){
			//增加主播抽奖机会
			int count = activityUserStockService.updateUserStock(activityStockAdd.getHostAdd(), ywUser.getId(), activityStockAdd.getItemId());
			if (count <= 0) {
				//抽奖用户无数据则新增
				addCheckData(ywUser, activityStockAdd.getHostAdd(), activityStockAdd.getItemId());
			}
			
			if(StringUtils.isNotBlank(ywUser.getParentId())){
				//增加推广人抽奖机会
				activityUserStockService.updateUserStock(activityStockAdd.getHostAdd(), ywUser.getParentId(), activityStockAdd.getItemId());
			}
		}
	}
	
	/**
	 * 判断用户领奖信息表和抽奖机会库存表是否有数据
	 * @param ywUser
	 * @param add (需初始化的抽奖机会)
	 * @param itemId (活动id)
	 * @creationDate. 2016-1-19 下午6:04:04
	 */
	private void addCheckData(YwUser ywUser, int add, String itemId){
		//抽奖用户无数据则新增
		ActivityUser activityUser = activityUserService.getActivityUserById(ywUser.getId());
		Date date = new Date();
		ActivityUser au = null;
		if(activityUser == null){
			au = new ActivityUser();
			au.setId(ywUser.getId());
			au.setTelphone(ywUser.getMobile());
			au.setQq(ywUser.getQq());
			au.setCreateTime(date);
		}
		
		//抽奖机会无数据则新增
		ActivityUserStock activityUserStock = new ActivityUserStock();
		activityUserStock.setUserId(ywUser.getId());
		activityUserStock.setItemId(itemId);
		List<ActivityUserStock> activityUserStocks = activityUserStockService.getActivityUserStockList(activityUserStock);
		ActivityUserStock aus = null;
		if(CollectionUtils.isEmpty(activityUserStocks)){
			aus = new ActivityUserStock();
			aus.setUserId(ywUser.getId());
			aus.setItemId(itemId);
			aus.setStock(add);
			aus.setCreateTime(date);
		}
		activityUserService.save(au, aus);
	}
	
	/**
	 * 增加抽奖机会接口
	 * @param userId
	 * @param add
	 * @param itemId
	 * @return
	 * @creationDate. 2016-1-20 下午5:25:01
	 */
	@Override
	public boolean addActivityStockInterface(YwUser ywUser, int add, String itemId){
		Integer i = activityUserStockService.updateUserStock(add, ywUser.getId(), itemId);
		if(i > 0){
			return true;
		}
		addCheckData(ywUser, 1, itemId);
		return false;
	}
	/**
	 * 获得上线的活动并且是已经开始的活动
	 * @return
	 * @creationDate. 2016-1-19 下午8:40:58
	 */
	public List<ActivityItem> getOnline(){
		ActivityItem activityItem = new ActivityItem();
		activityItem.setStatus(String.valueOf(LanshaConstant.STATUS_ONLINE));
		activityItem.setStartTime(new Date());
		List<ActivityItem> activityItems = activityItemService.getActivityItemList(activityItem);
		return activityItems;
	}
	
	/**
	 * 移动端第一次登录增加机会
	 * @creationDate. 2016-1-21 上午10:05:47
	 */
	@Override
	public void addFirstMobileLogin(YwUser ywUser, String type){
		//获得上线的活动
		List<ActivityItem> activityItems = getOnline();
		if(CollectionUtils.isEmpty(activityItems)){
			return;
		}
		List<ActivityStockAdd> list = new ArrayList<ActivityStockAdd>();
		for(ActivityItem ai : activityItems){
			ActivityStockAdd asa = new ActivityStockAdd();
			asa.setItemId(ai.getId());
			//移动端登录增加机会数
			String[] ss = ai.getChances();
			asa.setMobileLoginAdd(Integer.parseInt(ss[5]));
			list.add(asa);
		}
		for(ActivityStockAdd activityStockAdd : list){
			int count = activityUserStockService.updateUserStock(activityStockAdd.getMobileLoginAdd(), ywUser.getId(), activityStockAdd.getItemId());
			if (count <= 0) {
				//抽奖用户无数据则新增
				addCheckData(ywUser, activityStockAdd.getMobileLoginAdd(), activityStockAdd.getItemId());
			}
		}
	}

//	@Override
//	public void addWapActivityStocks(YwUser ywUser, String ip, String itemId, String defaultGiftId) {
//		if("2".equals(ywUser.getMark()) ){//没有抽过奖
//			if(!addActivityStockInterface(ywUser.getId(), 1, itemId)){
//				addCheckData(ywUser,1, itemId);
//			}
//		}else{
//			ActivityGiftStock entity = new ActivityGiftStock();
//			
//			//设置活动ID
//			entity.setItemId(itemId);
//			entity.setGiftId(defaultGiftId); //礼物ID
//			entity.setUserId(ywUser.getId());
//            entity.setStatus("0");//设置状态(0:等待发货,1:已发货)
//            entity.setType("3"); //获取类型(0:谢谢惠顾,1:蓝鲨币,2:礼包,3:充值卡,4:实物)
//            entity.setCreateTime(new Date());//创建时间
//            entity.setRemark("首次中2Q币"); //备注
//            entity.setIp(ip);//设置Ip
//            activityGiftStockService.save(entity);
//		}
//		
//	}

//	@Override
//	public void addWapLoginActivityStocks(YwUser ywUser, String ip,
//			String itemId, String defaultGiftId) {
//		//获得上线的活动
//		ActivityItem activityItem = new ActivityItem();
//		activityItem.setId(itemId);
//		activityItem.setStatus(String.valueOf(LanshaConstant.STATUS_ONLINE));
//		activityItem.setStartTime(new Date());
//		List<ActivityItem> activityItems = activityItemService.getActivityItemList(activityItem);
//		if(CollectionUtils.isEmpty(activityItems)){
//			return;
//		}
//		activityItem = activityItems.get(0);
//		String[] activityNum = activityItem.getChances();
//		int regNum = Integer.parseInt(activityNum[0]);
//		int apploginNum = Integer.parseInt(activityNum[5]);
//		
//		ActivityGiftStock item = new ActivityGiftStock();
//		int addNumber = 0;
//		item.setItemId(itemId);
//		item.setUserId(ywUser.getId());
//		//查询用户中奖纪录
//		Integer prizeNumber = activityGiftStockService.getActivityGiftStockNumber(item);
//		ActivityUserStock activityUserStock = new ActivityUserStock();
//		activityUserStock.setItemId(itemId);
//		activityUserStock.setUserId(ywUser.getId());
//		//查询用户抽奖机会
//		List<ActivityUserStock> listUserStock = activityUserStockService.getActivityUserStockList(activityUserStock);
//		if("2".equals(ywUser.getMark())){//没有点击WAP界面抽奖
//			if(prizeNumber<1){//无中奖纪录
//				if(CollectionUtils.isEmpty(listUserStock)
//						||(listUserStock.get(0).getStock()<1)){//活动之前的用户
//					addNumber = 1;//需求变更2016-01-28 产品：MJ. 首次登陆并且没有在未登录状态下点击抽奖，那么给一次抽奖机会
//				}else if(regNum == listUserStock.get(0).getStock().intValue()){//如果PC端注册送的抽奖机会与用户抽奖机会相等，那么给一次机会
//					addNumber = 1;
//				}else if(apploginNum == listUserStock.get(0).getStock().intValue()){//如果配置的首次登录app次数与用户首次注册或登录APP端送的抽奖机会相等
//					Integer loignNumber = logUserLoginDao.getLogUserLoginCountByUserId(ywUser.getId(), null, null, "0");//用户登录过APP
//					if(loignNumber>0){
//						addNumber = 1;//给三次抽奖机会
//					}
//				}else if((apploginNum+regNum)== listUserStock.get(0).getStock().intValue()){
//					Integer loignNumber = logUserLoginDao.getLogUserLoginCountByUserId(ywUser.getId(), null, null, "0");//用户登录过APP
//					if(loignNumber>0){
//						addNumber = 1;
//					}
//				}
//			}
//		}else{
//			if(prizeNumber<1){//无中奖纪录
//				if(CollectionUtils.isEmpty(listUserStock)
//						||(listUserStock.get(0).getStock()<1)){//活动之前的用户
//					//addNumber = 2;//需求变更2016-01-28 产品：MJ.首次登陆并且有载未登录状态下点击抽奖，那么给2个Q币
//					ActivityGiftStock entity = new ActivityGiftStock();			
//					//设置活动ID
//					entity.setItemId(itemId);
//					entity.setGiftId(defaultGiftId); //礼物ID
//					entity.setUserId(ywUser.getId());
//		            entity.setStatus("0");//设置状态(0:等待发货,1:已发货)
//		            entity.setType("3"); //获取类型(0:谢谢惠顾,1:蓝鲨币,2:礼包,3:充值卡,4:实物)
//		            entity.setCreateTime(new Date());//创建时间
//		            entity.setRemark("首次登录WAP页中2Q币"); //备注
//		            entity.setIp(ip);//设置Ip
//					activityGiftStockService.save(entity);
//				}else if(regNum == listUserStock.get(0).getStock().intValue()){//如果PC端注册送的抽奖机会与用户抽奖机会相等，那么给一次机会
//					addNumber = 1;
//				}else if(apploginNum == listUserStock.get(0).getStock().intValue()){
//					Integer loignNumber = logUserLoginDao.getLogUserLoginCountByUserId(ywUser.getId(), null, null, "0");
//					if(loignNumber>0){
//						addNumber = 1;
//					}
//				}else if((apploginNum+regNum)== listUserStock.get(0).getStock().intValue()){
//					Integer loignNumber = logUserLoginDao.getLogUserLoginCountByUserId(ywUser.getId(), null, null, "0");
//					if(loignNumber>0){
//						addNumber = 1;
//					}
//				}
//			}
//		}
//		if(addNumber!=0){
//			if(!addActivityStockInterface(ywUser.getId(), addNumber, itemId)){
//				addCheckData(ywUser,addNumber, itemId);
//			}
//		}
//		
//	}
	
}
