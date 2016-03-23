package com.yaowang.lansha.action.web;

import java.io.IOException;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;

import com.alibaba.fastjson.JSONObject;
import com.yaowang.lansha.common.action.LanshaBaseAction;
import com.yaowang.lansha.common.constant.LanshaConstant;
import com.yaowang.lansha.entity.LogUserLogin;
import com.yaowang.lansha.entity.YwUser;
import com.yaowang.lansha.entity.YwUserRoom;
import com.yaowang.lansha.entity.YwUserRoomRelation;
import com.yaowang.lansha.service.LanshaAddActivityStockService;
import com.yaowang.lansha.service.LogUserLoginService;
import com.yaowang.lansha.service.YwUserRoomHistoryService;
import com.yaowang.lansha.service.YwUserRoomRelationService;
import com.yaowang.lansha.service.YwUserRoomService;
import com.yaowang.lansha.service.YwUserService;
import com.yaowang.lansha.util.LanshaCommonFunctions;
import com.yaowang.util.DateUtils;
import com.yaowang.util.MD5;
import com.yaowang.util.UUIDUtils;
import com.yaowang.util.asynchronous.AsynchronousService;
import com.yaowang.util.asynchronous.ObjectCallable;
import com.yaowang.util.img.ValidateCodeUtil;

/**
 * 用户注册
 * @author Administrator
 */
public class RegisterAction extends LanshaBaseAction{
	private static final long serialVersionUID = 1L;
	@Resource
	private YwUserService ywUserService;
	@Resource
	private YwUserRoomHistoryService ywUserRoomHistoryService;
	@Resource
	private YwUserRoomService ywUserRoomService;
	@Resource
	private YwUserRoomRelationService ywUserRoomRelationService;
	@Resource
	private LogUserLoginService logUserLoginService;
	@Resource
	private LanshaAddActivityStockService lanshaAddActivityStockService;
	
	private YwUser ywUser;
	
	private String telphone;// 手机号
	private String rcode;// 短信验证码
	private String captchaCode;// 发送短信验证码

	private int type;
	private String liveType;
	//private String password2;
	
	//web用户注册
	public void doregister() throws IOException{
		if(ywUser == null){
			return;
		}
		YwUser user =  ywUserService.getYwusersByUsername(ywUser.getUsername(), false);
		if(user != null){
			write(getFailed("用户已存在"));
			return;
		}
		
		//判断昵称
		if(StringUtils.isEmpty(ywUser.getNickname())){
			write(getFailed("昵称不能为空"));
			return;
		}
		
		if((!ywUser.getNickname().matches("^[\u4E00-\u9FA50-9A-Za-z_]+$"))|| LanshaCommonFunctions.judgeCharsLength(ywUser.getNickname())>16){
			write(getFailed("请输入您的昵称(8位汉字或16位字母数字下划线的组合"));
			return;
		}
		
		if(LanshaCommonFunctions.matchNickKeywords(ywUser.getNickname())){
			write(getFailed("昵称请勿包含非法字符"));
			return;
		}
		
		YwUser u = new YwUser();
		u.setNickname(ywUser.getNickname());
		List<String> userList = ywUserService.getYwUserAllId(u);
		if(userList.size()>=1){
			write(getFailed("昵称已存在，请勿重复使用"));
			return;
		}
		
		String errormsg = testMt(ywUser.getUsername(), rcode);
		if(StringUtils.isNotBlank(errormsg)){
			write(getFailed(errormsg));
			return ;
		}
		
		if(StringUtils.isBlank(ywUser.getPassword())){
			write(getFailed("密码有误"));
			return;
		}else{
			ywUser.setId(UUIDUtils.newId());
			//复制头像
			try {
				ywUserService.saveHeadpicFile(ywUser, getUploadPath());
			} catch (Exception e) {
				e.printStackTrace();
			}
			//密码MD5加密
			ywUser.setPassword(MD5.encrypt(ywUser.getPassword()));
			ywUser.setMobile(ywUser.getUsername());
			
			//用户状态
			ywUser.setUserStatus(LanshaConstant.USER_STATUS_NORMAL);
			//帐号类型
			ywUser.setAccountType(null);
			//注册渠道
			ywUser.setRegChannel("0");
			//豆币
			ywUser.setBi(0);
			//经验
			ywUser.setJingyan(0);
			//是否是vip
			ywUser.setIsVip(false);
			//登录ip
			ywUser.setLastLoginIp(getClientIP());
			//登录时间
			ywUser.setLastLoginTime(getNow());
			//用户类型
			ywUser.setUserType(0);
			//时长
			ywUser.setTimeLength(0);
			ywUser.setCreateTime(getNow());
			//设置推广员ID
			ywUser.setParentId(getPN());
			try {
				ywUserService.save(ywUser);
			} catch (Exception e) {
				write(getFailed("注册失败"));
				return;
			}
			
			//注册后登录跳转到首页
			setUserLogin(ywUser);
			final JSONObject object = new JSONObject();
			object.put("url", getContextPath()+"/index.html");
			object.put("id", ywUser.getId());
			object.put("nickname", ywUser.getNickname());
			object.put("imNickName", LiveAction.getMenberNameStatic());
			
			if ("live".equals(liveType)) {
				//房间
				YwUserRoom room = ywUserRoomService.getYwUserRoomById(Integer.valueOf(id));
				boolean isboke = room.getUid().equals(ywUser.getId());
				object.put("isboke",  isboke ? 1 : 0);
				//是否关注
				YwUserRoomRelation relation = new YwUserRoomRelation();
				relation.setUid(ywUser.getId());
				relation.setRoomId(room.getId());
				List<YwUserRoomRelation> relations = ywUserRoomRelationService.getYwUserRoomRelationList(relation);
				object.put("isrelation",  relations.isEmpty() ? 0 : 1);
				//注册默认未领取过虾米
				object.put("getGiftCount",  0);
			}
			
			//记录登录日志
			final String ip = getClientIP();
			AsynchronousService.submit(new ObjectCallable() {
				@Override
				public Object run() throws Exception {
					//保存登录日志
					LogUserLogin logUser = new LogUserLogin();
					logUser.setUserId(ywUser.getId());
					logUser.setLoginTime(getNow());
					logUser.setLoginIp(ip);
					logUserLoginService.save(logUser, ywUser, "register");
					//增加抽奖机会
					lanshaAddActivityStockService.addRegActivityStock(ywUser);
					
					//登陆成功，加入观看历史记录
					if (object.containsKey("isboke") && object.getInteger("isboke") == 0) {
						//添加观看记录
						ywUserRoomHistoryService.saveHistory(id, ywUser.getId());
					}
					return null;
				}
			});
			
			//判断用户是否新用户
			Date newuser=getActivityNewUser();
			if(DateUtils.compare(ywUser.getCreateTime(),newuser)>-1){
				object.put("isnew", 1);
			}else{
				object.put("isnew", 0);
			}
			
			writeSuccess(object);
			return;
		}
	}
	
	//web用户密码修改
	public void doupdatePassword(){
		if(ywUser!=null){
			try {
				String errormsg = testMt(ywUser.getMobile(), rcode);
				if(StringUtils.isNotBlank(errormsg)){
					write(getFailed(errormsg));
					return;
				}
				
				if(StringUtils.isBlank(ywUser.getPassword())){
					write(getFailed("请填写有效的密码"));
					return;
				}else{
					YwUser ywUsers =  ywUserService.getYwusersByUsername(ywUser.getMobile(), false);
					if(ywUsers == null){
						write(getFailed("账户不存在"));
						return;
					}
					if (LanshaConstant.USER_STATUS_FREEZE==ywUsers.getUserStatus() || LanshaConstant.USER_STATUS_CLOSE==ywUsers.getUserStatus()) {//用户状态-冻结或封号
						write(getFailed("您的账户当前状态异常"));
						return;
					}
					//密码MD5加密
					ywUsers.setPassword(MD5.encrypt(ywUser.getPassword()));
					if(ywUserService.updatePassword(ywUsers)<0){
						write(getFailed("请填写和手机号对应的用户"));
						return;
					}else{
						addActionMessage("密码修改成功");
						
						JSONObject object = new JSONObject();
						object.put("url", getContextPath()+"/login.html");
						writeSuccess(object);
						return;
						
					}	
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	//短信发送
	public void sendSMS() throws Exception{
		if(!ValidateCodeUtil.verificationCode(captchaCode)){
			write(getFailed("验证码有误"));
			return ;
		}
		
		String errormsg = mt(getClientIP(), telphone, type);
		if(StringUtils.isNotBlank(errormsg)){
			write(getFailed(errormsg));
			return ;
		}
		
		write(EMPTY_ENTITY);
	}

	public YwUser getYwUser() {
		return ywUser;
	}

	public void setYwUser(YwUser ywUser) {
		this.ywUser = ywUser;
	}

	public String getRcode() {
		return rcode;
	}

	public void setRcode(String rcode) {
		this.rcode = rcode;
	}

	public String getTelphone() {
		return telphone;
	}

	public void setTelphone(String telphone) {
		this.telphone = telphone;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public void setLiveType(String liveType) {
		this.liveType = liveType;
	}

	public String getCaptchaCode() {
		return captchaCode;
	}

	public void setCaptchaCode(String captchaCode) {
		this.captchaCode = captchaCode;
	}
	
}
