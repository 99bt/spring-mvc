package com.yaowang.lansha.action.api;

import java.io.IOException;
import java.util.Date;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.qq.connect.QQConnectException;
import com.qq.connect.api.OpenID;
import com.qq.connect.javabeans.AccessToken;
import com.qq.connect.oauth.Oauth;
import com.yaowang.lansha.common.action.LanshaBaseAction;
import com.yaowang.lansha.common.constant.LanshaConstant;
import com.yaowang.lansha.entity.LanshaUserBand;
import com.yaowang.lansha.entity.LogUserLogin;
import com.yaowang.lansha.entity.YwUser;
import com.yaowang.lansha.service.LanshaUserBandService;
import com.yaowang.lansha.service.LogUserLoginService;
import com.yaowang.lansha.service.YwUserService;
import com.yaowang.util.UUIDUtils;
import com.yaowang.util.asynchronous.AsynchronousService;
import com.yaowang.util.asynchronous.ObjectCallable;
import com.yaowang.util.http.HttpUtil;
/**
 * oauth2 登录
 * @author Administrator
 *
 */
public class IndexOAuth2LoginAction extends LanshaBaseAction{
	private static final long serialVersionUID = 1L;
	@Resource
	private YwUserService ywUserService;
	@Resource
	private LanshaUserBandService lanshaUserBandService;
	@Resource
	private LogUserLoginService logUserLoginService;

	/**
	 * qq登陆
	 * @return
	 */
	public void qqlogin() throws IOException, QQConnectException{
        getResponse().sendRedirect(new Oauth().getAuthorizeURL(getRequest()));
    }
	/**
	 * qq回调
	 * @return
	 * @throws QQConnectException 
	 * @throws IOException 
	 */
	public String afterloginqq() throws QQConnectException, IOException {
		HttpServletRequest request = getRequest();
        AccessToken accessTokenObj = (new Oauth()).getAccessTokenByRequest(request);

        if (accessTokenObj.getAccessToken().equals("")) {
//            我们的网站被CSRF攻击了或者用户取消了授权
//            做一些数据统计工作
            addActionError("没有获取到响应参数，<a href='" + getContextPath() + "/index.html'>返回首页</a>");
            return "msg";
        } else {
        	String accessToken = accessTokenObj.getAccessToken();
//            tokenExpireIn = accessTokenObj.getExpireIn();

            // 利用获取到的accessToken 去获取当前用的openid -------- start
            OpenID openIDObj =  new OpenID(accessToken);
            String openId = openIDObj.getUserOpenID();
            // 利用获取到的accessToken 去获取当前用户的openid --------- end

            //查询openId对应当前用户
            // 检查id是否存在
    		LanshaUserBand userBand = lanshaUserBandService.getUserBandByOpenId(openId);
            YwUser ywUser = null;
            //登录注册类型
            final StringBuilder loginType = new StringBuilder();
    		if(userBand == null){
    			// 新增用户信息
    			ywUser = new YwUser();
    			ywUser.setId(UUIDUtils.newId());
    			try {
            		// 获取用户信息
            		String string = HttpUtil.handleGet("https://graph.qq.com/user/get_user_info?access_token=" + accessToken + "&oauth_consumer_key=101281605&openid=" + openId);
            		JSONObject object = JSON.parseObject(string);
            		String nickname = object.getString("nickname");
            		String icon = object.getString("figureurl");
            		String gender = object.getString("gender");
        			ywUser.setAccount(nickname);// 第三方登录的用户名
        			ywUser.setNickname(nickname);// 昵称
        			
        			ywUser.setSex(StringUtils.isBlank(gender) ? 0 : ("男".equals(gender) ? 1 : 2));// 性别(0未知1男2女)
        			ywUser.setHeadpic(icon);// 头像地址
//        			ywUsers.setBirthday(DateUtils.toDate(birthday));// 生日
//        			ywUsers.setSign(StringUtils.isBlank(sign) ? "主人很懒，什么都没留下！" : sign);// 签名
				} catch (Exception e) {
					e.printStackTrace();
				}
    			String clientIp = getClientIP();
    			Date now = getNow();
    			String type = "1";
    			
    			ywUser.setAccountType(type);// 帐号类型
    			ywUser.setRegChannel("0");// 注册渠道（0：网页注册，1：手机注册）
    			ywUser.setBi(0);// 豆币（冗余用户行为表sum(bi)）
    			ywUser.setJingyan(0);// 经验
    			ywUser.setIsVip(false);// 是否是vip
    			ywUser.setLastLoginIp(clientIp);// 登录ip
    			ywUser.setLastLoginTime(now);// 登录时间
    			ywUser.setUserStatus(LanshaConstant.USER_STATUS_NORMAL);// 状态(0:删除、1:正常、2:冻结、3:封号)
    			ywUser.setUserType(LanshaConstant.USER_TYPE_ORDINARY);// 用户类型(0:前台普通会员,1:主播)
    			ywUser.setCreateTime(now);// 创建时间戳
    			ywUser.setUpdateTime(now);// 更新时间
    			ywUser.setToken("");// 最后登录的token
    			ywUser.setTimeLength(0);// 时长
    			ywUser.setAuthe(0);// 是否认证（0：否，1：是）
    			ywUser.setPush("1");// 全局推送开关(0：否拒绝 1：是接受)

    			// 第三方信息
    			LanshaUserBand lanshaUserBand = new LanshaUserBand();
    			lanshaUserBand.setId(UUIDUtils.newId());
    			lanshaUserBand.setUid(ywUser.getId());
    			lanshaUserBand.setOpenId(openId);
    			lanshaUserBand.setType(type);
    			lanshaUserBand.setCreateTime(now);
    			
    			try {
    				ywUserService.saveUserBand(ywUser, lanshaUserBand);
    			} catch (Exception e) {
    				addActionError("第三方登录失败，请与客服联系！");
    				return "msg";
    			}
    			loginType.append("regiest");
    		}else{
    			ywUser = ywUserService.getYwUserById(userBand.getUid());
    			loginType.append("login");
    		}
    		
    		//记录登录日志
    		final YwUser user = ywUser;
			final String ip = getClientIP();
			AsynchronousService.submit(new ObjectCallable() {
				@Override
				public Object run() throws Exception {
					//保存登录日志
					LogUserLogin logUser = new LogUserLogin();
					logUser.setUserId(user.getId());
					logUser.setLoginTime(getNow());
					logUser.setLoginIp(ip);
					logUserLoginService.save(logUser, user, loginType.toString());
					return null;
				}
			});
    		
            setUserLogin(ywUser);
            
    		HttpServletResponse response = getResponse();
            response.sendRedirect(getContextPath() + "/index.html");
            return null;
        }
	}
}
