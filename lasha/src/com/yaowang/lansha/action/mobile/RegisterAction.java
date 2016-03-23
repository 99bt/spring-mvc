package com.yaowang.lansha.action.mobile;

import java.io.IOException;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;

import com.alibaba.fastjson.JSONObject;
import com.yaowang.lansha.common.action.LanshaMobileAction;
import com.yaowang.lansha.common.constant.LanshaConstant;
import com.yaowang.lansha.entity.ActivityGiftStock;
import com.yaowang.lansha.entity.LanshaUserGiftStock;
import com.yaowang.lansha.entity.LogUserLogin;
import com.yaowang.lansha.entity.YwUser;
import com.yaowang.lansha.service.ActivityGiftStockService;
import com.yaowang.lansha.service.LanshaAddActivityStockService;
import com.yaowang.lansha.service.LanshaUserGiftStockService;
import com.yaowang.lansha.service.LogUserLoginService;
import com.yaowang.lansha.service.YwUserService;
import com.yaowang.util.MD5;
import com.yaowang.util.UUIDUtils;
import com.yaowang.util.asynchronous.AsynchronousService;
import com.yaowang.util.asynchronous.ObjectCallable;

/**
 * 用户注册及忘记密码
 * @author Administrator
 */
public class RegisterAction extends LanshaMobileAction{

	private static final long serialVersionUID = 1L;
	@Resource
	private YwUserService ywUserService;
	@Resource
	private LogUserLoginService logUserLoginService;
	@Resource
	private LanshaUserGiftStockService lanshaUserGiftStockService;
	@Resource
	private LanshaAddActivityStockService lanshaAddActivityStockService;
	@Resource
	private ActivityGiftStockService activityGiftStockService;
	
	private YwUser ywUser = new YwUser();
	
	private String username;//用户名
	private String pwd;//密码
	private String code;//验证码
	private String deviceId;//设备号
	private String telphone;//手机号码
	private int type;
    private String tokenId;//推送设备号

	
	//用户注册
	public void register() throws IOException {
		if(StringUtils.isEmpty(username)){//用户名不能为空
			write(getFailed("用户名不能为空"));
			return;
		}
		if(StringUtils.isEmpty(pwd)){//密码不能为空
			write(getFailed("密码不能为空"));
			return;
		}
		if(StringUtils.isEmpty(code)){//验证码不能为空
			write(getFailed("验证码不能为空"));
			return;
		}
		//判断手机验证码
		String errormsg = testMt(username, code);
		if(StringUtils.isNotBlank(errormsg)){
			write(getFailed(errormsg));
			return ;
		}
		
		YwUser ywUsers =  ywUserService.getYwusersByUsername(username, false);
		if(ywUsers != null){
			write(getFailed("用户已存在"));
			return;
		}
		final String ip = getClientIP();
		//密码MD5加密
		ywUser.setPassword(MD5.encrypt(pwd));
		//手机号
		ywUser.setMobile(username);
		//用户名
		ywUser.setUsername(username);
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
		ywUser.setLastLoginIp(ip);
		//登录时间
		ywUser.setLastLoginTime(getNow());
		//用户类型
		ywUser.setUserType(0);
		//时长
		ywUser.setTimeLength(0);
		//设置令牌
		token= UUIDUtils.newId();
		ywUser.setToken(token);
		ywUser.setCreateTime(getNow());
		ywUser.setOsType(ostype);
		ywUser.setDeviceId(deviceId);
		ywUser.setRegiestDeviceId(deviceId);
        ywUser.setTokenId(tokenId);
		
		YwUser t = ywUserService.save(ywUser);
		if(t==null){
			write(getFailed("注册失败"));
		}else{
			//记录登录日志
			AsynchronousService.submit(new ObjectCallable() {
				@Override
				public Object run() throws Exception {
					//保存登录日志
					LogUserLogin logUser = new LogUserLogin();
					logUser.setUserId(ywUser.getId());
					logUser.setLoginTime(getNow());
					logUser.setLoginIp(ip);
					logUser.setLoginMethod("0");
					logUserLoginService.save(logUser, ywUser, "register");
					//增加抽奖机会
					lanshaAddActivityStockService.addRegActivityStock(ywUser);
					//判断测设备id是否已注册过(送Q币)
					if (StringUtils.isEmpty(deviceId) || StringUtils.isEmpty(ostype)
							||(LanshaConstant.OSTYPE_ANDROID.equals(ostype)&&(StringUtils.isEmpty(version)||"1.05".compareTo(version) > 0))) {//如果注册的是安卓手机并且版本号小于1.05或者为空的时候，那么不送他Q币
						return null;
					}
					YwUser entity = new YwUser();
					entity.setOsType(ostype);
					entity.setRegiestDeviceId(deviceId);
					List<String> user = ywUserService.getYwUserAllId(entity);
					if (user.isEmpty() || (user.size() == 1 && user.get(0).equals(ywUser.getId()))) {
						//判断是否送2Q币
						ActivityGiftStock stock = new ActivityGiftStock();
						//设置活动ID
						stock.setItemId(LanshaConstant.ITEM_ID_TWO);
						stock.setGiftId(LanshaConstant.Q_GIFT_ID); //礼物ID
						stock.setUserId(ywUser.getId());
						stock.setStatus("0");//设置状态(0:等待发货,1:已发货)
						stock.setType("3"); //获取类型(0:谢谢惠顾,1:蓝鲨币,2:礼包,3:充值卡,4:实物)
						stock.setCreateTime(getNow());//创建时间
						stock.setRemark("首次中1Q币"); //备注
						stock.setIp(ip);//设置Ip
						activityGiftStockService.updateLotteryGiftNumberAndStock(stock);
					}
		            
					return null;
				}
			});
			//返回数据
			JSONObject object = new JSONObject();
			object.put("status", 1);
			
			JSONObject data = new JSONObject();
			data.put("nickName", ywUser.getNickname());
			data.put("token", token);
			data.put("icon", StringUtils.isBlank(ywUser.getHeadpic()) ? getStaticFilePath("/static/lansha/upload/default.png") : getUploadFilePath(ywUser.getHeadpic()));
			data.put("uid", ywUser.getId());
			data.put("bi", "0");
			object.put("data", data);
			write(object);
		}
	}

	//短信发送
	public void sendSMS() throws Exception{
		String errormsg = mt(getClientIP(), telphone, type);
		if(StringUtils.isNotBlank(errormsg)){
			write(getFailed(errormsg));
			return ;
		}
		
		write(EMPTY_ENTITY);
	}
	

	//用户密码修改
	public void updatePassword() throws IOException{
		if(StringUtils.isEmpty(telphone)){//手机号码不能为空
			write(getFailed("手机号码不能为空"));
			return;
		}
		if(StringUtils.isEmpty(token)){//令牌不能为空
			write(getFailed("令牌不能为空"));
			return;
		}
		
		if(StringUtils.isEmpty(code)){//验证码不能为空
			write(getFailed("验证码不能为空"));
			return;
		}
		//判断手机验证码
		String errormsg = testMt(ywUser.getUsername(), code);
		if(StringUtils.isNotBlank(errormsg)){
			write(getFailed(errormsg));
			return ;
		}
		
		if(StringUtils.isEmpty(pwd)){//密码不能为空
			write(getFailed("密码不能为空"));
			return;
		}else if(StringUtils.isNotBlank(pwd)){
			//根据手机号码获取用户
			YwUser ywUsers =  ywUserService.getYwusersByUsername(telphone, false);
			if(ywUsers == null){
				write(getFailed("账户不存在"));
			}else if (LanshaConstant.USER_STATUS_NORMAL!=ywUsers.getUserStatus()) {
				write(getFailed("您的账户当前状态异常"));
			}
			//密码MD5加密
			ywUsers.setPassword(MD5.encrypt(pwd));
			if(ywUserService.updatePassword(ywUsers)<0){
				write(getFailed("请填写和手机号对应的用户"));
			}else{
				//返回数据
				JSONObject object = new JSONObject();
				object.put("status", 1);
				
				JSONObject data = new JSONObject();
				data.put("username", ywUsers.getUsername());
				data.put("token", token);
				//虾米库存
				LanshaUserGiftStock stock = lanshaUserGiftStockService.getByGiftIdAndUserId(LanshaConstant.GIFT_ID, ywUsers.getId());
				data.put("bi", stock == null ? "0" : stock.getStock().toString());
				object.put("data", data);
				
				write(object);
			}	
		}
	}

    /**
     * 找回密码
     */
    public void findpwd() throws IOException {
        if(StringUtils.isEmpty(telphone)){//手机号码不能为空
            write(getFailed("手机号码不能为空"));
            return;
        }
        if(StringUtils.isEmpty(code)){//验证码不能为空
            write(getFailed("验证码不能为空"));
            return;
        }
        //判断手机验证码
        String errormsg = testMt(telphone, code);
        if(StringUtils.isNotBlank(errormsg)){
            write(getFailed(errormsg));
            return ;
        }

        if(StringUtils.isEmpty(pwd)){//密码不能为空
            write(getFailed("密码不能为空"));
            return;
        }else if(StringUtils.isNotBlank(pwd)){
            //根据手机号码获取用户
            YwUser ywUsers =  ywUserService.getYwusersByUsername(telphone, false);
            if(ywUsers == null){
                write(getFailed("账户不存在"));
            }else if (LanshaConstant.USER_STATUS_NORMAL!=ywUsers.getUserStatus()) {
                write(getFailed("您的账户当前状态异常"));
            }
            //密码MD5加密
            ywUsers.setPassword(MD5.encrypt(pwd));
            if(ywUserService.updatePassword(ywUsers)<0){
                write(getFailed("请填写和手机号对应的用户"));
            }else{
                //返回数据
                JSONObject object = new JSONObject();
                object.put("status", 1);

                JSONObject data = new JSONObject();
                object.put("data", data);

                write(object);
            }
        }
    }



	
	/**
	 * 设备号是否已注册过
	 * @throws IOException 
	 */
	public void device() throws IOException{
		JSONObject object = new JSONObject();
		object.put("status", 1);
		
		JSONObject data = new JSONObject();
		if (StringUtils.isEmpty(deviceId) || StringUtils.isEmpty(ostype)) {
			data.put("isRegister", "0");
			data.put("msg", "deviceid=" + deviceId + ",ostype=" + ostype);
			object.put("data", data);
			write(object);
			return;
		}
		
		List<String> user = ywUserService.isExistedDeviceId(deviceId);
		if (user.isEmpty()) {	
			data.put("isRegister", "0");
			data.put("msg", "deviceid=" + deviceId + ",ostype=" + ostype);
		}else {
			if((LanshaConstant.OSTYPE_ANDROID.equals(ostype) && "1.05".compareTo(version) > 0)){
				data.put("isRegister", "0");
			}else{
				data.put("isRegister", "1");
			}
			data.put("msg", "deviceid=" + deviceId + ",ostype=" + ostype);
		}

		object.put("data", data);
		write(object);
	}
	
	public void setUsername(String username) {
		this.username = username;
	}
	
	public void setPwd(String pwd) {
		this.pwd = pwd;
	}

	public void setCode(String code) {
		this.code = code;
	}
	
	public void setDeviceId(String deviceId) {
		this.deviceId = deviceId;
	}

	public void setTelphone(String telphone) {
		this.telphone = telphone;
	}

	public void setType(int type) {
		this.type = type;
	}

    public String getTokenId() {
        return tokenId;
    }

    public void setTokenId(String tokenId) {
        this.tokenId = tokenId;
    }
}
