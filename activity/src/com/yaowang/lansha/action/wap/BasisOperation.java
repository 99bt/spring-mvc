package com.yaowang.lansha.action.wap;

import java.io.IOException;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;

import com.alibaba.fastjson.JSONObject;
import com.yaowang.lansha.common.action.LanshaBaseAction;
import com.yaowang.lansha.common.constant.LanshaConstant;
import com.yaowang.lansha.entity.LogUserLogin;
import com.yaowang.lansha.entity.YwUser;
import com.yaowang.lansha.service.LanshaAddActivityStockService;
import com.yaowang.lansha.service.LogUserLoginService;
import com.yaowang.lansha.service.YwUserService;
import com.yaowang.util.MD5;
import com.yaowang.util.UUIDUtils;
import com.yaowang.util.asynchronous.AsynchronousService;
import com.yaowang.util.asynchronous.ObjectCallable;
import com.yaowang.util.img.ValidateCodeUtil;
/**
 * 基础操作类
 * 功能：注册、登录、退出、短信发送
 * @author Administrator
 * zengxi
 */
public class BasisOperation extends LanshaBaseAction {
	private static final long serialVersionUID = 1L;
	@Resource
	private YwUserService ywUserService;
	@Resource
	private LogUserLoginService logUserLoginService;
	@Resource
	private LanshaAddActivityStockService lanshaAddActivityStockService;

	
	private YwUser ywUser;
	
	private String telphone;// 手机号
	private String rcode;// 短信验证码
	private String captchaCode;// 发送短信验证码
	private int type;
	
	protected String url = "/wap/activity/index.html";
	protected String itemId = LanshaConstant.ITEM_ID_TWO;
//	protected String defaultGiftId = LanshaConstant.Q_GIFT_ID;

	/**
	 * wap用户注册
	 * @throws IOException
	 */
	public void register() throws IOException{
		if(ywUser == null){
			return;
		}
		YwUser user =  ywUserService.getYwusersByUsername(ywUser.getUsername(), false);
		if(user != null){
			if(LanshaConstant.USER_STATUS_NORMAL!=user.getUserStatus()){
				write(getFailed("用户已存在"));
				return;
			}
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
			
			//密码MD5加密
			ywUser.setPassword(MD5.encrypt(ywUser.getPassword()));
			
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
			//用户类型
			ywUser.setUserType(0);
			//时长
			ywUser.setTimeLength(0);
			ywUser.setCreateTime(getNow());
			ywUser.setMobile(ywUser.getUsername());//手机号
			//设置推广员ID
    		ywUser.setParentId(getPN());
			try {
				ywUserService.save(ywUser);
			} catch (Exception e) {
				write(getFailed("注册失败"));
				return;
			}
			
			if(StringUtils.isEmpty(ywUser.getMark())){//判断抽奖标志
				write(getFailed("页面数据有误"));
				return;
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

					return null;
				}
			});

			//添加一次抽奖机会
			lanshaAddActivityStockService.addActivityStockInterface(ywUser, 1, itemId);
			
			//注册后登录跳转到首页
			setUserLogin(ywUser);
			JSONObject object = new JSONObject();
			object.put("url", getContextPath()+url+"?pn="+ywUser.getId());
			object.put("id", ywUser.getId());
			object.put("ftype", "reg");
			writeSuccess(object);
			return;
		}
	}
	
	/**
	 * wap登录
	 * @return
	 * @throws IOException 
	 */
	public void login() throws IOException{
		if(ywUser!=null){
			if (StringUtils.isNotEmpty(ywUser.getUsername())) {//用户名不为空
				final YwUser user =  ywUserService.getYwusersByUsername(ywUser.getUsername(), false);
				if(user == null){
					write(getFailed("账户或密码不正确"));
					return;
				}
				if (!user.equalsPwd(ywUser.getPassword())) {
					write(getFailed("账户或密码不正确"));
					return;
				}
				if (LanshaConstant.USER_STATUS_FREEZE==user.getUserStatus() || LanshaConstant.USER_STATUS_CLOSE==user.getUserStatus()) {
					//用户状态-冻结或封号
					write(getFailed("您的账户当前状态异常"));
					return;
				}
				if (LanshaConstant.USER_STATUS_NORMAL == user.getUserStatus()) {
					setUserLogin(user);
					
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
							
							return null;
						}
					});
					
					JSONObject object = new JSONObject();
					object.put("url", getContextPath()+url+"?pn="+user.getId());
					object.put("id", user.getId());
					
					writeSuccess(object);
					
					return;
				}
			}
		}else{
			write(getFailed("数据有误"));
			return;
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
	
	/**
	 * 退出
	 * @return
	 * @throws IOException 
	 */
	public void logout() throws IOException{
		setUserLogin(null);
		getResponse().sendRedirect(getContextPath()+url);
	}

	public YwUser getYwUser() {
		return ywUser;
	}

	public void setYwUser(YwUser ywUser) {
		this.ywUser = ywUser;
	}

	public String getTelphone() {
		return telphone;
	}

	public void setTelphone(String telphone) {
		this.telphone = telphone;
	}

	public String getRcode() {
		return rcode;
	}

	public void setRcode(String rcode) {
		this.rcode = rcode;
	}

	public String getCaptchaCode() {
		return captchaCode;
	}

	public void setCaptchaCode(String captchaCode) {
		this.captchaCode = captchaCode;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

}
