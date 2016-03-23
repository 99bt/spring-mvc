package com.yaowang.lansha.action.mobile;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;

import com.alibaba.fastjson.JSONObject;
import com.yaowang.lansha.common.action.LanshaMobileAction;
import com.yaowang.lansha.common.constant.LanshaConstant;
import com.yaowang.lansha.entity.LanshaUserBand;
import com.yaowang.lansha.entity.LanshaUserGiftStock;
import com.yaowang.lansha.entity.LogUserLogin;
import com.yaowang.lansha.entity.YwUser;
import com.yaowang.lansha.service.LanshaUserBandService;
import com.yaowang.lansha.service.LanshaUserGiftStockService;
import com.yaowang.lansha.service.LogUserLoginService;
import com.yaowang.lansha.service.YwUserService;
import com.yaowang.util.DateUtils;
import com.yaowang.util.UUIDUtils;
import com.yaowang.util.asynchronous.AsynchronousService;
import com.yaowang.util.asynchronous.ObjectCallable;

/**
 * APP登录 退出
 * @author Administrator
 */
public class LoginAction extends LanshaMobileAction{
	private static final long serialVersionUID = 1L;
	
	@Resource
	private YwUserService ywUserService;
	@Resource
	private LanshaUserBandService lanshaUserBandService;
	@Resource
	private LogUserLoginService logUserLoginService;
	@Resource
	private LanshaUserGiftStockService lanshaUserGiftStockService;
	
	private String token;//令牌
	private String username;//用户名
	private String pwd;//密码
	private String ostype;//平台
	private String deviceId;//设备号
	
	// 第三方登录信息
	public String type;// 类型(1: 微信，2:sina weibo，3: mail,4: qq)
	public String nickname;// 昵称
	public String sex;// 性别(0:未知1:男2:女)
	public String birthday;// 出生年月日
	public String icon;// 头像地址
	public String sign;// 签名
	public String regChannel;// 注册渠道(0：网页注册，1：手机注册)
    private String tokenId;//推送设备号
	
	/**
	 * 登录
	 * @return
	 * @throws IOException 
	 */
	public void login() throws IOException{
		if(StringUtils.isEmpty(username)){//用户名不能为空
			write(getFailed("用户名不能为空"));
			return;
		}
		if(StringUtils.isEmpty(ostype)){//平台不能为空
			write(getFailed("平台不能为空"));
			return;
		}
		if(StringUtils.isEmpty(pwd)){//密码不能为空
			write(getFailed("密码不能为空"));
			return;
		}
		final YwUser ywUsers =  ywUserService.getYwusersByUsername(username, false);
		if(ywUsers == null){
			write(getFailed("账户或密码不正确"));
			return;
		}
		if (!ywUsers.equalsPwd(pwd)) {
			write(getFailed("账户或密码不正确"));
			return;
		}
		if (LanshaConstant.USER_STATUS_DELETE.equals(ywUsers.getUserStatus())) {//用户状态-删除
			write(getFailed("账户或密码不正确"));
			return;
		}
		if (LanshaConstant.USER_STATUS_FREEZE.equals(ywUsers.getUserStatus()+"") || LanshaConstant.USER_STATUS_CLOSE.equals(ywUsers.getUserStatus()+"")) {//用户状态-冻结或封号
			write(getFailed("您的账户当前状态异常"));
			return;
		}
		
		//设置token
		ywUsers.setToken(UUIDUtils.newId());
        if(StringUtils.isNotEmpty(tokenId))
        {
            ywUsers.setTokenId(tokenId);
        }
        if(StringUtils.isNotEmpty(deviceId))
        {
            ywUsers.setDeviceId(deviceId);
        }
        if(StringUtils.isNotEmpty(ostype))
        {
            ywUsers.setOsType(ostype);
        }

        ywUsers.setLastLoginTime(new Date());

		int judgeUpdate =ywUserService.update(ywUsers);
		if (judgeUpdate<0){
			write(getFailed("登录失败请重试"));
			return ;
		}
		
		//返回数据
		JSONObject data = new JSONObject();
		
		
		if(StringUtils.isEmpty(ywUsers.getNickname())){
			data.put("nickName", null);
		}else if(StringUtils.isNotEmpty(ywUsers.getNickname())){
			data.put("nickName", ywUsers.getNickname());
		}
		data.put("uid", ywUsers.getId());
		data.put("icon", StringUtils.isBlank(ywUsers.getHeadpic()) ? getStaticFilePath("/static/lansha/upload/default.png") : getUploadFilePath(ywUsers.getHeadpic()));
		data.put("token", ywUsers.getToken());
		//蓝鲨币取虾米库存
		LanshaUserGiftStock stock = lanshaUserGiftStockService.getByGiftIdAndUserId(LanshaConstant.GIFT_ID, ywUsers.getId());
		if(stock == null){
			data.put("bi", "0");
		}else{
			data.put("bi", stock.getStock().toString());
		}
		setUserLogin(ywUsers);
		
		writeSuccessWithData(data); 
		
		//记录登录日志
		final String ip = getClientIP();
		AsynchronousService.submit(new ObjectCallable() {
			@Override
			public Object run() throws Exception {
				//保存登录日志
				LogUserLogin logUser = new LogUserLogin();
				logUser.setUserId(ywUsers.getId());
				logUser.setLoginTime(getNow());
				logUser.setLoginIp(ip);
				logUser.setLoginMethod("0");
				//标示是登录还是注册调用
				logUserLoginService.save(logUser, ywUsers, "login");
				return null;
			}
		});
	}
	
	/**
	 * 退出
	 * @return
	 * @throws IOException 
	 */
	public void logout() throws IOException{
		if(StringUtils.isEmpty(token)){//令牌不能为空
			write(getFailed("令牌不能为空"));
			return;
		}
		setUserLogin(null);
		YwUser user = getUserByToken(token);
		if(user != null){
			ywUserService.updateToken(user.getId(), null);
		}
		
		write(EMPTY_ENTITY);
	}
	
	/**
	 * 检测token有效
	 */
	public void checkToken()throws IOException{
		if(StringUtils.isEmpty(token)){//令牌不能为空
			write(getFailed("令牌不能为空"));
			return;
		}
		final YwUser ywUser = ywUserService.getYwusersByToken(token);

//		String token = BaseAction.getRequest().getParameter("token");
//		if (StringUtils.isBlank(token)) {
//			write(getFailedLogin());
//		}else{
//			write(EMPTY_STRING);
//		}
		if(ywUser==null){
			write(getFailedLogin("令牌失效"));
			return;
		}else{
            if(StringUtils.isNotEmpty(tokenId))
            {
                ywUser.setTokenId(tokenId);
            }
            if(StringUtils.isNotEmpty(deviceId))
            {
                ywUser.setDeviceId(deviceId);
            }
            if(StringUtils.isNotEmpty(ostype))
            {
                ywUser.setOsType(ostype);
            }
            int judgeUpdate =ywUserService.update(ywUser);
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
					logUser.setLoginMethod("0");
					logUserLoginService.save(logUser, ywUser, "login");
					return null;
				}
			});
			write(EMPTY_ENTITY);
		}
	}
	
	/**
	 * @throws IOException 
	 * @Description: 第三方登录
	 */
	public void userband() throws IOException{
		if (StringUtils.isEmpty(id)) {
			write(getError("OpenId不能为空"));
			return;
		}
		if(StringUtils.isEmpty(type)){
			write(getError("类型不能为空"));
			return;
		}
		
		YwUser ywUsers;
		String status = "2";
		Date now = getNow();
		// 生成token
		String token = UUIDUtils.newId();
		// ip
		String clientIp = getClientIP();
		icon = StringUtils.isBlank(icon) ? "" : icon;
		nickname = StringUtils.isBlank(nickname) ? "" : nickname;
		
		// 检查id是否存在
		LanshaUserBand userBand = lanshaUserBandService.getUserBandByOpenId(id);
		if(userBand == null){
			status = "1";
			// 新增用户信息
			ywUsers = new YwUser();
			ywUsers.setId(UUIDUtils.newId());
			ywUsers.setAccount(nickname);// 第三方登录的用户名
			ywUsers.setNickname(nickname);// 昵称
			ywUsers.setAccountType(type);// 帐号类型
			ywUsers.setSex(StringUtils.isBlank(sex) ? 0 : Integer.valueOf(sex));// 性别(0未知1男2女)
			ywUsers.setHeadpic(icon);// 头像地址
			ywUsers.setRegChannel(regChannel);// 注册渠道（0：网页注册，1：手机注册）
			ywUsers.setBirthday(DateUtils.toDate(birthday));// 生日
			ywUsers.setSign(StringUtils.isBlank(sign) ? "主人很懒，什么都没留下！" : sign);// 签名
			ywUsers.setBi(0);// 豆币（冗余用户行为表sum(bi)）
			ywUsers.setJingyan(0);// 经验
			ywUsers.setIsVip(false);// 是否是vip
			ywUsers.setLastLoginIp(clientIp);// 登录ip
			ywUsers.setLastLoginTime(now);// 登录时间
			ywUsers.setUserStatus(LanshaConstant.USER_STATUS_NORMAL);// 状态(0:删除、1:正常、2:冻结、3:封号)
			ywUsers.setUserType(LanshaConstant.USER_TYPE_ORDINARY);// 用户类型(0:前台普通会员,1:主播)
			ywUsers.setCreateTime(now);// 创建时间戳
			ywUsers.setUpdateTime(now);// 更新时间
			ywUsers.setToken(token);// 最后登录的token
			ywUsers.setTimeLength(0);// 时长
			ywUsers.setAuthe(0);// 是否认证（0：否，1：是）
			ywUsers.setPush("1");// 全局推送开关(0：否拒绝 1：是接受)
            ywUsers.setTokenId(tokenId);

			// 第三方信息
			LanshaUserBand lanshaUserBand = new LanshaUserBand();
			lanshaUserBand.setId(UUIDUtils.newId());
			lanshaUserBand.setUid(ywUsers.getId());
			lanshaUserBand.setOpenId(id);
			lanshaUserBand.setType(type.toString());
			lanshaUserBand.setCreateTime(now);
			
			try {
				ywUserService.saveUserBand(ywUsers, lanshaUserBand);
			} catch (Exception e) {
				write(getError("第三方登录失败，请与客服联系！"));
				return;
			}
		}else{
			ywUsers = ywUserService.getYwUserById(userBand.getUid());
			// 修改用户token信息
			ywUserService.updateToken(userBand.getUid(), token);
		}
		
		if (ywUsers == null) {
			write(getError("第三方登录失败，请与客服联系！"));
			return;
		}
		
		// 返回结果信息
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("id", ywUsers.getIdInt());
		result.put("token", token);
		if (StringUtils.isEmpty(ywUsers.getHeadpic())) {
			result.put("icon", "");
		} else {
			result.put("icon", ywUsers.getHeadpic().contains("http://") ? ywUsers.getHeadpic() : getUploadFilePath(ywUsers.getHeadpic()));
		}
		result.put("nickname", nickname);
		result.put("type", status);
		result.put("uid", ywUsers.getId());
		//蓝鲨币取虾米库存
		LanshaUserGiftStock stock = lanshaUserGiftStockService.getByGiftIdAndUserId(LanshaConstant.GIFT_ID, ywUsers.getId());
		if(stock == null){
			result.put("bi", "0");
		}else{
			result.put("bi", stock.getStock().toString());
		}
		writerEntity(result);
	}

	public void setToken(String token) {
		this.token = token;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public void setPwd(String pwd) {
		this.pwd = pwd;
	}

	public void setOstype(String ostype) {
		this.ostype = ostype;
	}

	public void setDeviceId(String deviceId) {
		this.deviceId = deviceId;
	}

	public void setType(String type) {
		this.type = type;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public void setBirthday(String birthday) {
		this.birthday = birthday;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}

	public void setSign(String sign) {
		this.sign = sign;
	}

	public void setRegChannel(String regChannel) {
		this.regChannel = regChannel;
	}

    public String getTokenId() {
        return tokenId;
    }

    public void setTokenId(String tokenId) {
        this.tokenId = tokenId;
    }
}
