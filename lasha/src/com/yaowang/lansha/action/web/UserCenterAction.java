package com.yaowang.lansha.action.web;

import java.io.IOException;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.springframework.util.CollectionUtils;

import com.alibaba.fastjson.JSONObject;
import com.yaowang.common.constant.BaseConstant;
import com.yaowang.common.util.encryption.MD5;
import com.yaowang.entity.SysOption;
import com.yaowang.lansha.common.action.LanshaBaseAction;
import com.yaowang.lansha.common.constant.LanshaConstant;
import com.yaowang.lansha.entity.YwUser;
import com.yaowang.lansha.entity.YwUserPay;
import com.yaowang.lansha.entity.YwUserRoom;
import com.yaowang.lansha.service.YwUserPayService;
import com.yaowang.lansha.service.YwUserRoomService;
import com.yaowang.lansha.service.YwUserService;
import com.yaowang.lansha.util.LanshaCommonFunctions;
import com.yaowang.util.img.ValidateCodeUtil;
/**
 * @ClassName: UserCenterAction
 * @Description: 我的信息
 * @author wanglp
 * @date 2015-12-10 上午10:55:22
 *
 */
public class UserCenterAction extends LanshaBaseAction {
	private static final long serialVersionUID = 1L;
	@Resource
	private YwUserService ywUserService;
	@Resource
	private YwUserRoomService ywUserRoomService;
	@Resource
	private YwUserPayService ywUserPayService;
	
	private YwUser user;
	private String telphone;
	private String code;//验证码
	private int countRegister;//注册人数
	private int countHost;//主播人数
	private String url;
	private String captchaCode;// 发送短信验证码
	private YwUserPay userPay;
	
	/**
	 * 推广
	 * @return
	 */
	public String invite(){
		String userid = getUserLogin().getId();
		YwUser entity = new  YwUser();
		entity.setParentId(userid);
		list = ywUserService.getYwUserList(entity, null, null, null, null, null);
		countRegister = list.size();
		//获取域名
		SysOption sysOption = sysOptionService.getSysOptionByIniid("SYS.HOST.PATH");//app下载 iOS下载地址
		url = sysOption.getNowvalue();
		user = ywUserService.getYwUserById(userid);
		//设置用户头像
		user.setHeadpic(getUploadFilePath(user.getHeadpic()));
        if(!CollectionUtils.isEmpty(list)){
        	for(Object users:list){
    			YwUser u = (YwUser) users;
    			YwUserRoom ywUserRoom = ywUserRoomService.getYwUserRoomByUid(u.getId());
    			if(ywUserRoom!=null){
    				countHost++;
    			}
    		}
        }
		return SUCCESS;
	}
	
	/**
	 * 银行信息保存
	 * @throws  IOException 
	 */
	public void bankInfoSave() throws IOException {
		if(null == userPay){
			write(getFailed("保存失败"));
			return;
		}
		if (StringUtils.isBlank(userPay.getName())) {
			write(getFailed("开户人姓名不能为空"));
			return;
		}
		if (StringUtils.isBlank(userPay.getCardNo())) {
			write(getFailed("银行卡号不能为空"));
			return;
		}
		if(!StringUtils.isNumeric(userPay.getCardNo()) 
				|| userPay.getCardNo().length() < 10 || userPay.getCardNo().length() > 20){
			write(getFailed("银行卡号格式不正确"));
			return;
		}
		if (StringUtils.isBlank(userPay.getBank())) {
			write(getFailed("开户行不能为空"));
			return;
		}
		YwUser ywUser = getUserLogin();
		if(ywUser == null){
			write(getFailed("请重新登录"));
			return;
		}
		userPay.setUserId(ywUser.getId());
		List<YwUserPay> userPayInfo = ywUserPayService.getYwUserPayByUserId(ywUser.getId());
		if(CollectionUtils.isEmpty(userPayInfo)){
			ywUserPayService.save(userPay);
		}else{
			ywUserPayService.delete(new String[]{userPayInfo.get(0).getCardNo()});
			ywUserPayService.save(userPay);
		}
		writeSuccess(null);
	}
	
	/**
	 * 银行信息
	 * @return
	 */
	public String bankInfo(){
		String userid = getUserLogin().getId();
		List<YwUserPay> userPayInfo = ywUserPayService.getYwUserPayByUserId(userid);
		if(CollectionUtils.isEmpty(userPayInfo)){
			userPay = new YwUserPay();
		}else{
			userPay = userPayInfo.get(0);
		}
		return SUCCESS;
	}
	
	/**
	 * @Title: center
	 * @Description: 基本资料
	 * @return
	 * @throws IOException 
	 */
	public String info() throws IOException{
		String userid = getUserLogin().getId();
		
		user = ywUserService.getYwUserById(userid);
		if(null == user){
			 getResponse().sendRedirect(getContextPath() + "/index.html");
			return null;
		}
		//设置用户头像
		user.setHeadpic(getUploadFilePath(user.getHeadpic()));
		
		//判断登录进来的用户跟session用户对比，是否主播
		if(LanshaConstant.USER_TYPE_ANCHOR.equals(user.getUserType()) &&  !LanshaConstant.USER_TYPE_ANCHOR.equals(getUserLogin().getUserType())   ){
			setUserLogin(user);
		}
		
		
		return SUCCESS;
	}
	
	public void update() throws IOException{
		if(user==null){
			write(getFailed("数据为空"));
			return;
		}
		
		if(StringUtils.isNotBlank(user.getHeadpic())){
			try {
				//设置头像
				Boolean b = ywUserService.saveHeadpicFile(user, getUploadPath());
				if (!b) {
					user.setHeadpic("");
				}
			} catch (Exception e) {
				write(getFailed("头像上传失败"));
				e.printStackTrace();
			}
		}
		
		if(StringUtils.isNotEmpty(user.getNickname())){
			if(user.getNickname().equals("") || user.getNickname().length()<1){
				write(getFailed("昵称不能为空"));
				return;
			}
			
			if((!user.getNickname().matches("^[\u4E00-\u9FA50-9A-Za-z_]+$")) || LanshaCommonFunctions.judgeCharsLength(user.getNickname())>16){
				write(getFailed("请输入您的昵称(8位汉字或16位字母数字下划线的组合"));
				return;
			}
			
			if(LanshaCommonFunctions.matchNickKeywords(user.getNickname())){
				write(getFailed("昵称请勿包含非法字符"));
				return;
			}
			
			
			//判断昵称
			YwUser u = new YwUser();
			u.setNickname(user.getNickname());
			
			// 当前登录用户ID
			/*YwUser us = getUserLogin();
			if(us.getNickname().equals(u.getNickname())){
				write(getFailed("您已使用此昵称"));
				return;
			}*/
			
			List<String> userList = ywUserService.getYwUserAllId(u);
			for(String id:userList){
				if(!id.equals(user.getId())){
					write(getFailed("昵称已存在，请勿重复使用"));
					return;
				}
			}
		}
		
		//保存
		ywUserService.updateForBase(user);
		YwUser userSession = ywUserService.getYwUserById(user.getId());
		setUserLogin(userSession);
		
		JSONObject object = new JSONObject();
		object.put("url", getContextPath()+"/user/center.html");
		writeSuccess(object);
	}
	/**
	 * @Title: updatePassword
	 * @Description: 修改密码
	 * @throws IOException
	 */
	public void updatePassword() throws IOException{
		//判断密码是否正确
		YwUser oldUser = ywUserService.getYwUserById(user.getId());
		if(!oldUser.equalsPwd(user.getOldpassword())){
			write(getFailed("原密码错误"));
			return ;
		}
		if(user.getPassword().equals(user.getOldpassword())){
			write(getFailed("新密码不能跟原密码相同"));
			return ;
		}
		//更新密码
		user.setPassword(MD5.encrypt(user.getPassword()));
		ywUserService.updatePassword(user);
		
		JSONObject object = new JSONObject();
		object.put("url", getContextPath()+"/user/password.html");
		writeSuccess(object);
	}
	
	public void updateTelphone() throws IOException{
		if (StringUtils.isEmpty(telphone)) {
			write(getError("手机号不能为空"));
			return ;
		}
		if (StringUtils.isBlank(code)) {
			write(getError("验证码不能为空"));
			return ;
		}
		String errormsg = testMt(telphone, code);
		if (StringUtils.isNotBlank(errormsg)) {
			write(getFailed(errormsg));
			return ;
		}
		YwUser existsUser = ywUserService.getYwusersByUsername(telphone, false);
		if (existsUser != null) {
			write(getFailed("手机号已被绑定！"));
			return ;
		}
		
		try {
			YwUser user = getUserLogin();
			user.setUsername(telphone);
			user.setMobile(telphone);
			ywUserService.update(user);
			//ywUserService.updateMobile(getUserLogin().getId(), telphone);
			
			JSONObject object = new JSONObject();
			object.put("url", getContextPath()+"/user/telphoneAuthe.html");
			writeSuccess(object);
		} catch (Exception e) {
			write(getFailed("绑定手机失败！"));
		}
		return ;
	}
	
	//短信发送
	public void sendSMS() throws Exception{
		if(!ValidateCodeUtil.verificationCode(captchaCode)){
			write(getFailed("验证码有误"));
			return ;
		}
		
		String errormsg = mt(getClientIP(), telphone, BaseConstant.NO);
		if(StringUtils.isNotBlank(errormsg)){
			write(getFailed(errormsg));
			return ;
		}
		
		write(EMPTY_ENTITY);
	}

	public YwUser getUser() {
		return user;
	}

	public void setUser(YwUser user) {
		this.user = user;
	}
	
	public String getTelphone() {
		return telphone;
	}

	public void setTelphone(String telphone) {
		this.telphone = telphone;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public int getCountRegister() {
		return countRegister;
	}

	public int getCountHost() {
		return countHost;
	}

	public String getUrl() {
		return url;
	}

	public String getCaptchaCode() {
		return captchaCode;
	}

	public void setCaptchaCode(String captchaCode) {
		this.captchaCode = captchaCode;
	}

	public YwUserPay getUserPay() {
		return userPay;
	}

	public void setUserPay(YwUserPay userPay) {
		this.userPay = userPay;
	}
	
}
