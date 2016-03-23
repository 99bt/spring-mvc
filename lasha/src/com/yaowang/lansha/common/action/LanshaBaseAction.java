package com.yaowang.lansha.common.action;

import java.io.IOException;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.Cookie;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.yaowang.common.action.BasePageAction;
import com.yaowang.common.constant.BaseConstant;
import com.yaowang.common.constant.MtConstant;
import com.yaowang.entity.LogMt;
import com.yaowang.entity.SysOption;
import com.yaowang.lansha.common.constant.LanshaConstant;
import com.yaowang.lansha.entity.LanshaUserGiftStock;
import com.yaowang.lansha.entity.YwUser;
import com.yaowang.lansha.entity.YwUserRoom;
import com.yaowang.lansha.service.LanshaUserGiftStockService;
import com.yaowang.lansha.service.YwUserRoomService;
import com.yaowang.lansha.service.YwUserService;
import com.yaowang.lansha.util.UserChannelUtils;
import com.yaowang.service.LogMtService;
import com.yaowang.service.impl.SysOptionServiceImpl;
import com.yaowang.util.DateUtils;
import com.yaowang.util.RegularUtil;
import com.yaowang.util.freemark.AppSetting;
import com.yaowang.util.mt.MTUtil;
import com.yaowang.util.mt.RandCodeUtil;

public class LanshaBaseAction extends BasePageAction {
	private static final long serialVersionUID = -3595641666024058887L;
	@Resource
	private LanshaUserGiftStockService lanshaUserGiftStockService;
	@Resource
	private LogMtService logMtService;
	@Resource
	private YwUserService ywUserService;
    @Resource
    private YwUserRoomService ywUserRoomService;
    
    private String randIds;

	/**
	 * 设置登录
	 * @param
	 */
	public static void setUserLogin(YwUser user){
		getSession().put("login_lansha_user", user);
	}

	/**
	 * 获取登录用户
	 * @return
	 */
	public YwUser getUserLogin(){
		return getUserLoginStatic();
	}
	public static YwUser getUserLoginStatic(){
		return (YwUser)getSession().get("login_lansha_user");
	}
	
	/**
	 * 判断是否登录
	 * @return
	 */
	public Boolean getUserIsLogin(){
		return getUserLoginStatic() != null;
	}
	/**
	 * 是否是主播
	 * @return
	 */
	public Boolean getUserIsAnchor(){
		YwUser user = getUserLoginStatic();
		return user != null && user.getUserType() == 1;
	}
	/**
	 * 获取蓝鲨币
	 * @return
	 */
	public Integer getStock(){
		if (getUserIsLogin()) {
			//虾米库存
			LanshaUserGiftStock stock = lanshaUserGiftStockService.getByGiftIdAndUserId(LanshaConstant.GIFT_ID, getUserLogin().getId());
			if(stock != null){
				return stock.getStock();
			}else{
				return 0;
			}
		}else {
			return 0;
		}
//		return getUserLogin().getBi();
	}
	
	/**
	 * @Title: writeSuccess
	 * @Description: 返回客户端成功
	 * @param msg
	 * @throws IOException
	 */
	public void writeSuccess(JSONObject object) throws IOException{
		if(object  == null){
			object = new JSONObject();
		}
		object.put("status", 1);
		
		writeNoLog(JSON.toJSONString(object, SerializerFeature.WriteDateUseDateFormat));
	}
	/**
	 * @Title: writeSuccessWithData
	 * @Description: 返回客户端成功
	 * @param msg
	 * @throws IOException
	 */
	public void writeSuccessWithData(Object data) throws IOException{
		JSONObject object = new JSONObject();
		object.put("status", 1);
		object.put("data", data);
		
		write(JSON.toJSONString(object, SerializerFeature.WriteDateUseDateFormat));
	}
	/**
	 * 获取当前地址
	 * @return
	 */
	public String getHttpUrl(){
		return getHttpUrlStatic();
	}
	public static String getHttpUrlStatic(){
		String query = getRequest().getQueryString();
		return getRequest().getRequestURI() + (query == null?"":"?" + query);
	}
	/**
	 * 播放缓冲时间
	 * @return
	 */
	public String getPlayCache(){
		return SysOptionServiceImpl.getValue("LANSHA.PLAY.CACHE");
	}
	/**
	 * 获取蓝鲨标示
	 * @return
	 */
	public String getLanshaLogo(){
		return YwUserRoom.LIVE;
	}
	/**
	 * 伪静态id
	 * @return
	 */
	public String getObjectId(){
		//伪静态
		String uri = getRequest().getRequestURI();
		String id = "";
		if (uri.matches(getContextPath() + ".*/\\d+.html")) {
			id = uri.substring(uri.lastIndexOf("/") + 1, uri.length() - ".html".length());
		}
		return id;
	}
	
	/**
	 * telphone 手机号码
	 * type 
	 * 1:手机号码对应的用户必须存在
	 * 0：手机号码对应的用户必须不存在
	 * @throws Exception
	 * @Description: 短信发送, 多处使用，勿随意修改
	 */
	protected String mt(String ip, String telphone, int type) throws Exception {
		String back = SysOptionServiceImpl.getValue("MT_BLACKLIST");
		if (back.indexOf(ip) > -1) {
			return "短信发送频率过高";
		}
		
		// 验证手机号码
		if (!RegularUtil.telphoneReg(telphone)) {
			return "手机号码格式有误";
		}
		if(type != BaseConstant.YES && type != BaseConstant.NO){
			return "type错误";
		}

		// 判断用户存在性
		YwUser existsUser = ywUserService.getYwusersByUsername(telphone, false);
		if (type == BaseConstant.YES && existsUser == null) {
			return "用户不存在";
		}
		if (type == BaseConstant.NO && existsUser != null) {
			return "手机号已经存在";
		}

		// 判断最后一次发送时间是否少于5分钟
		LogMt mt = logMtService.getLogMtByTelphone(telphone);
		if (mt != null && (getNow().getTime() - mt.getTime().getTime()) < 5 * 60 * 1000) {
			return "距离上一次发送短信小于5分钟";
		}
//		// 一天内同一ip发送超过5次，则ip被封禁
//		LogMt entity = new LogMt();
//		entity.setIp(ip);
//		
//		Calendar calendar = Calendar.getInstance();
//		endTime = calendar.getTime();
//		//1分钟内
//		calendar.set(Calendar.MINUTE, calendar.get(Calendar.MINUTE) - 1);
//		startTime = calendar.getTime();
//		int count = logMtService.getLogCount(entity, startTime, endTime);
//		if (count >= 2) {
//			return "短信发送频率过高";
//		}
//		
//		//1小时内
//		calendar.set(Calendar.HOUR_OF_DAY, calendar.get(Calendar.HOUR_OF_DAY) - 1);
//		startTime = calendar.getTime();
//		count = logMtService.getLogCount(entity, startTime, endTime);
//		int maxcount = getMaxCount(endTime);
//		if (count > maxcount) {
//			return "短信发送频率过高";
//		}
//
//		//24小时内
//		calendar.set(Calendar.DAY_OF_MONTH, calendar.get(Calendar.DAY_OF_MONTH) - 1);
//		startTime = calendar.getTime();
//		count = logMtService.getLogCount(entity, startTime, endTime);
//		if (count > 100) {
//			return "短信发送频率过高";
//		}

		mt = new LogMt();
		try {
			mt.setCode(RandCodeUtil.getRandCode(6).toString());
			mt.setContent("【" + getPlatFormName() + "】您的验证码：" + mt.getCode() + "，有效期为" + MtConstant.defaultTime + "分钟。");
			mt.setIp(getClientIP());
			mt.setStatus("0");
			mt.setTime(getNow());
			mt.setTelphone(telphone);

			// 正式环境,发送短信
			String msg = MTUtil.sendMt(mt.getContent(), telphone);
			mt.setError(msg);
			return msg;
//			write(EMPTY_ENTITY);
		} catch (Exception e) {
			e.printStackTrace();
			mt.setError(e.getMessage());
			mt.setStatus("2");
			
			return "短信发送失败";
		} finally {
			// 保存
			logMtService.save(mt);
		}
	}
	/**
	 * 获取短信发送限制
	 * @param endTime
	 * @return
	 */
	private int getMaxCount(Date endTime) {
		int hours = endTime.getHours();
		if (hours >= 0 && hours < 8) {
			//闲时
			return 10;
		}else {
			//忙时
			return 50;
		}
	}
	
	/**
	 * 验证手机验证码
	 * telphone 手机号码
	 * code 用户输入的验证码
	 * @return
	 * @throws IOException
	 */
	protected String testMt(String telphone, String code) throws IOException {
		SysOption permanentCode = sysOptionService.getSysOptionByIniid("SYS.COMMON.CODE");
		if(permanentCode != null && StringUtils.isNotBlank(code) && code.equals(permanentCode.getNowvalue())){
			// 万能验证码
			return null;
		}
		// 根据手机号去发送的验证码
		LogMt logMt = logMtService.getLogMtByTelphone(telphone);
		if (logMt == null) {
			return "请先获取验证码";
		}
		if (!(logMt.getCode().equals(code))) {
			return "请输入正确的验证码";
		}
		// 验证码有效期
		if ((getNow().getTime() - logMt.getTime().getTime()) > MtConstant.defaultTime * 60 * 1000) {
			// 有效期超过设定的时间
			return "验证码超过了有效期";
		}
		// 更新验证码状态
		logMt.setStatus("1");
		logMtService.updateStatus(logMt);

		return null;
	}
	/**
	 * 获取推广人
	 * @return
	 */
	public String getPN(){
		String pn = UserChannelUtils.getChannelFormCookie(getRequest(), "PN");
    	if (StringUtils.isNotBlank(pn)){
    		YwUser yu = ywUserService.getYwUserById(pn);
        	if(yu != null){
        		return pn;
        	}
		}
    	return "";
	}
	
	/**
	 * 获取游戏是否可以下载 1是0否
	 * @Description: TODO
	 * @return boolean
	 * @throws
	 */
	private Boolean gameDownLoadSwitch;
	public boolean getGameDownLoadSwitch(){
		if (gameDownLoadSwitch == null) {
			String isswith = SysOptionServiceImpl.getValue("GAME.DOWNLOAD.SWITCH");
			gameDownLoadSwitch = "1".equals(isswith);
		}
		return gameDownLoadSwitch;
	}
	
	/**
	 * 新增Cookie
	 * @Description: TODO
	 * @return void
	 * @throws
	 */
	 public void addCookie(String name,String value){
	        Cookie cookie = new Cookie(name, value);
	        cookie.setMaxAge(60*60*24*365);//1天
	        getResponse().addCookie(cookie);
	 }

	 /**
	  * 随机4个推荐房间
	  * @return
	  */
	public List<YwUserRoom> getRandRooms() {
		String[] roomIds = null;
		if(StringUtils.isNotBlank(randIds)){
			roomIds = randIds.split(",");
		}
		List<String> idList = ywUserRoomService.getRandLiveFHot(4,roomIds);
		if(CollectionUtils.isNotEmpty(idList)){
			randIds = "";
			for(String rid : idList){
				randIds += "," + rid;
			}
			randIds = randIds.substring(1);
		}
		return ywUserRoomService.getYwUserRoomByIds(idList.toArray(new String[4]), null);
	}

	public String getRandIds() {
		return randIds;
	}
	
	
	/**
	 * 获取pc端活动新用户判断标准
	 * @return
	 */
	public Date getActivityNewUser(){
		Date date=null;
		String ndate=SysOptionServiceImpl.getValue("LANSHA.ACTIVITY.NEWUSER");
		if(!StringUtils.isBlank(ndate)){
			date=DateUtils.pasetime(ndate);
		}
		if(date==null)date=new Date();
		return date;
	}
	
	/**
	 * 获取行为分析代码
	 */
	public String getActionAnalysisCode() {
		String uri = getRequest().getRequestURI();//获取请求的URI
		if(StringUtils.isNotBlank(uri)){
			if(uri.matches("/live/\\d+.html")){//PC端直播间关注事件
				uri = "/live/*.html";
			}
			StringBuilder sbCode = new StringBuilder(AppSetting.getInstance().getMcodeContent(LanshaConstant.SYSTEM_MCODE_LANSHA_CNZZ_CODE,LanshaConstant.SYSTEM_MCODE_LANSHA_CNZZ_CODE_ALL_PAGES));//默认所有页面都加上浏览事件
			sbCode.append(AppSetting.getInstance().getMcodeContent(LanshaConstant.SYSTEM_MCODE_LANSHA_CNZZ_CODE,uri));//根据请求的URI获取对应的行为代码
			return sbCode.toString();
		}
		return "";
	}
	
}
