package com.yaowang.lansha.action.web;

import java.io.IOException;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;

import com.alibaba.fastjson.JSONObject;
import com.yaowang.common.dao.PageDto;
import com.yaowang.lansha.common.action.LanshaBaseAction;
import com.yaowang.lansha.common.constant.LanshaConstant;
import com.yaowang.lansha.entity.LanshaUserGiftStock;
import com.yaowang.lansha.entity.LogUserLogin;
import com.yaowang.lansha.entity.YwUser;
import com.yaowang.lansha.entity.YwUserRoom;
import com.yaowang.lansha.entity.YwUserRoomHistory;
import com.yaowang.lansha.entity.YwUserRoomRelation;
import com.yaowang.lansha.service.LanshaRoomBlacklistService;
import com.yaowang.lansha.service.LanshaUserGiftStockService;
import com.yaowang.lansha.service.LogUserLoginService;
import com.yaowang.lansha.service.YwUserRoomAdminService;
import com.yaowang.lansha.service.YwUserRoomHistoryService;
import com.yaowang.lansha.service.YwUserRoomRelationService;
import com.yaowang.lansha.service.YwUserRoomService;
import com.yaowang.lansha.service.YwUserService;
import com.yaowang.util.DateUtils;
import com.yaowang.util.asynchronous.AsynchronousService;
import com.yaowang.util.asynchronous.ObjectCallable;

/**
 * web前台登录
 * @return
 */
public class LoginAction extends LanshaBaseAction{
	private static final long serialVersionUID = 1L;
	
	private YwUser ywUser;
	private String backUrl;
	private String liveType;
	
	@Resource
	private YwUserService ywUserService;
	@Resource
	private YwUserRoomHistoryService ywUserRoomHistoryService;
	@Resource
	private YwUserRoomService ywUserRoomService;
	@Resource
	private YwUserRoomAdminService ywUserRoomAdminService;
	@Resource
	private YwUserRoomRelationService ywUserRoomRelationService;
	@Resource
	private LogUserLoginService logUserLoginService;
	@Resource
	private LanshaUserGiftStockService lanshaUserGiftStockService;
	@Resource
	private LanshaRoomBlacklistService lanshaRoomBlacklistService;
	private String roomId;
//	@Resource
//	private LanshaUserRecordService lanshaUserRecordService;
	
	/**
	 * 登录
	 * @return
	 * @throws IOException 
	 */
	public void dologin() throws IOException{
		if(ywUser!=null){			
			if (StringUtils.isNotEmpty(ywUser.getUsername())) {//用户名不为空
				final YwUser user =  ywUserService.getYwusersByUsername(ywUser.getUsername(), false);
				if(user == null){
					write(getFailed("账户或密码不正确"));
					return;
				}
				if (StringUtils.isEmpty(ywUser.getPassword()) || !user.equalsPwd(ywUser.getPassword())) {
					write(getFailed("账户或密码不正确"));
					return;
				}
				if (LanshaConstant.USER_STATUS_FREEZE==user.getUserStatus() || LanshaConstant.USER_STATUS_CLOSE==user.getUserStatus()) {//用户状态-冻结或封号
					write(getFailed("您的账户当前状态异常"));
					return;
				}
				if (LanshaConstant.USER_STATUS_NORMAL==user.getUserStatus()) {
					setUserLogin(user);
					
					final JSONObject object = new JSONObject();
					if (StringUtils.isEmpty(backUrl)) {
						backUrl = getContextPath()+"/index.html";
					}
					object.put("url", backUrl);
					object.put("id", user.getId());
					object.put("nickname", user.getNickname());
					object.put("imNickName", LiveAction.getMenberNameStatic());
					List<String> blackRoomlist = lanshaRoomBlacklistService.getLanshaRoomBlacklistByUserId(user.getId());
					if( CollectionUtils.isNotEmpty(blackRoomlist)){
						String blackStr = "|";
						for(String blackOne : blackRoomlist){
							blackStr += blackOne + "|";
						}
						object.put("blackRoomlist", blackStr);
					}
					//虾米库存
					LanshaUserGiftStock gift = lanshaUserGiftStockService.getByGiftIdAndUserId(LanshaConstant.GIFT_ID, user.getId());
					if(gift == null){
						object.put("bi", 0);
					}else{
						object.put("bi", gift.getStock());
					}
					LanshaUserGiftStock flowerStock =  lanshaUserGiftStockService.getByGiftIdAndUserId(LanshaConstant.GIFT_ID_TWO, user.getId());
					//鲜花库存
					if(flowerStock == null){
						object.put("flower", 0);
					}else{
						object.put("flower", flowerStock.getStock());
					}
					
					LanshaUserGiftStock ticketStock =  lanshaUserGiftStockService.getByGiftIdAndUserId(LanshaConstant.GIFT_ID_FOUR, user.getId());
					if(null == ticketStock){
						ticketStock = new LanshaUserGiftStock();
						ticketStock.setCreateTime(getNow());
						ticketStock.setGiftId(LanshaConstant.GIFT_ID_FOUR);
						ticketStock.setStock(1);
						ticketStock.setUserId(user.getId());
						lanshaUserGiftStockService.save(ticketStock);
					}
					//日票库存
					object.put("ticket", ticketStock.getStock());
					//如果用户是房间内登陆
					if(StringUtils.isNotBlank(roomId)){
				        YwUserRoom ywUserRoom = ywUserRoomService.getYwUserRoomById(roomId);
				        if (null == ywUserRoom) {
				            write(getFailed("房间id不存在!"));
				            return;
				        }
						String role = "other";

				        boolean blackRecord = lanshaRoomBlacklistService.getIsBlackByRoomAndUser(roomId, user.getId());
				        role = blackRecord ? "black" : role;
				        if(blackRecord){
				        	role = "black";
				        }else{
							boolean permission = ywUserRoomAdminService.containsAdmin(roomId, user.getId());
					        if (permission) {
					            role = "admin";
					        }else if(ywUserRoom.getUid().equals(user.getId())){
					        	role = "master";
					        }else if(StringUtils.isNotBlank(user.getOfficialType())){
				        		if("1".equals(user.getOfficialType())){
				        			role = "official";
				        		}else if("2".equals(user.getOfficialType())){
				        			role = "superManager";
				        		}
				        	}
				        }

						object.put("role", role);
					}
					
					if ("live".equals(liveType)) {
						//房间
						if (StringUtils.isNotBlank(id) && id.matches("\\d+")) {
							YwUserRoom room = ywUserRoomService.getYwUserRoomById(Integer.valueOf(id));
							if (room != null) {
								boolean isboke = room.getUid().equals(user.getId());
								object.put("isboke", isboke ? 1 : 0);
								//是否关注
								YwUserRoomRelation relation = new YwUserRoomRelation();
								relation.setUid(user.getId());
								relation.setRoomId(room.getId());
								List<YwUserRoomRelation> relations = ywUserRoomRelationService.getYwUserRoomRelationList(relation);
								object.put("isrelation", relations.isEmpty() ? 0 : 1);
								//当天是否领取过虾米
								object.put("getGiftCount", getFirstGetGift() ? 1 : 0);
								object.put("roomId", room.getId());
							}
						}
					}
					
					//记录登录日志
					final String ip = getClientIP();
					AsynchronousService.submit(new ObjectCallable() {
						@Override
						public Object run() throws Exception {
							//保存登录日志
							LogUserLogin logUser = new LogUserLogin();
							logUser.setUserId(user.getId());
							logUser.setLoginTime(getNow());
							logUser.setLoginIp(ip);
							logUserLoginService.save(logUser, user, "login");
							
							//登陆成功，加入观看历史记录
							if (object.containsKey("isboke") && object.getInteger("isboke") == 0) {
								//添加观看记录
								ywUserRoomHistoryService.saveHistory(object.getString("roomId"), user.getId());
							}
							
							return null;
						}
					});
					
					//判断用户是否新用户
					Date newuser=getActivityNewUser();
					if(DateUtils.compare(user.getCreateTime(),newuser)>-1){
						object.put("isnew", 1);
					}else{
						object.put("isnew", 0);
					}
					
					writeSuccess(object);
					return;
				}
			}
		}else{
			write(getFailed("数据有误"));
			return;
		}
	}
	
	/**
	 * 退出
	 * @return
	 * @throws IOException 
	 */
	public void logout() throws IOException{
		setUserLogin(null);
		getResponse().sendRedirect(getContextPath()+"/index.html");
	}

	/**
	 * 当天是否领取过虾米(如果没有领取过，第一次默认不需要倒计时)
	 */
	public boolean getFirstGetGift(){
		YwUser user = getUserLogin();
		if(user == null){
			return true;
		}
//		///查询,因为收入记录还包含别人送的虾米记录，不能区分是送还是自领
//		PageDto page = getPageDto();
//		page.setCount(false);
//		page.setRowNum(1);
//		page.setCurrentPage(1);
//		//当天用户领取虾米的记录
//		LanshaUserRecord record = new LanshaUserRecord();
//		record.setObjectId(LanshaConstant.GIFT_ID);
//		record.setObjectType(LanshaConstant.RECORD_OBJECT_TYPE_2);
//		record.setType(LanshaConstant.INCOME);
//		record.setUserId(user.getId());
//		setStartTime(getNow());
//		setEndTime(getNow());
//		List<LanshaUserRecord> list = lanshaUserRecordService.getLanshaUserRecordPage(record, page, startTime, endTime);
//		if(CollectionUtils.isEmpty(list)){
//			return false;
//		}
		//查询
		YwUserRoomHistory history = new YwUserRoomHistory();
		history.setUid(user.getId());
		history.setStatus(2);
		history.setTypeId(LanshaConstant.GIFT_ID);
		setStartTime(getNow());
		setEndTime(getNow());
		PageDto page = getPageDto();
		page.setCount(false);
		page.setRowNum(1);
		page.setCurrentPage(1);
		List<YwUserRoomHistory> histories = ywUserRoomHistoryService.getYwUserRoomHistoryPage(history, null, page, startTime, endTime);
		if(CollectionUtils.isEmpty(histories)){
			return false;
		}
		return true;
	}
	
	public YwUser getYwUser() {
		return ywUser;
	}

	public void setYwUser(YwUser ywUser) {
		this.ywUser = ywUser;
	}

	public String getBackUrl() {
		return backUrl;
	}

	public void setBackUrl(String backUrl) {
		this.backUrl = backUrl;
	}

	public void setLiveType(String liveType) {
		this.liveType = liveType;
	}

	public String getRoomId() {
		return roomId;
	}

	public void setRoomId(String roomId) {
		this.roomId = roomId;
	}
	
}
