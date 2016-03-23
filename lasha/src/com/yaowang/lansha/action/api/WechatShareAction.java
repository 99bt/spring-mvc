package com.yaowang.lansha.action.api;

import java.util.Date;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;

import com.yaowang.lansha.common.action.LanshaBaseAction;
import com.yaowang.lansha.common.constant.LanshaConstant;
import com.yaowang.lansha.entity.WechatAccessToken;
import com.yaowang.lansha.entity.WechatJsJdkParams;
import com.yaowang.lansha.entity.WechatJsapiticket;
import com.yaowang.lansha.service.WechatAccessTokenService;
import com.yaowang.lansha.service.WechatJsapiticketService;
import com.yaowang.lansha.util.wx.WechatConstantsUtil;
import com.yaowang.lansha.util.wx.WechatSign;
import com.yaowang.lansha.util.wx.WechatTokenUtil;

public class WechatShareAction extends LanshaBaseAction {
	private static final long serialVersionUID = 1L;
	@Resource
	private WechatAccessTokenService wechatAccessTokenService;
	@Resource
	private WechatJsapiticketService wechatJsapiticketService;
	private String shareLink;
	private String shareContent;

	private WechatJsJdkParams wxParams=new WechatJsJdkParams();
	
	public void generateWechatParams() throws Exception{
		
		wxParams.setAppId(WechatConstantsUtil.ACCOUNT_APPID);
		wxParams.setAppSecret(WechatConstantsUtil.ACCOUNT_APPSECRET);
		WechatAccessToken localToken = wechatAccessTokenService.getWechatAccessTokenByAppId(WechatConstantsUtil.ACCOUNT_APPID);//先从缓存里面取token,如果缓存没有则从数据库中取
		String token = getLocalToken(localToken);//判断token是否失效
		String ticket = "";
		if (StringUtils.isNotEmpty(token)){
			wxParams.setAccessToken(token);
			WechatJsapiticket localTicket = wechatJsapiticketService.getWechatJsapiticketByAppId(WechatConstantsUtil.ACCOUNT_APPID);//先从缓存里面取ticket,如果缓存没有则从数据库中取
			ticket = getLocalTicket(localTicket);//判断ticket是否失效
			if (StringUtils.isNotEmpty(ticket)){
				wxParams.setJsapiTicket(localTicket.getTicketName());
			}else{
				ticket = WechatTokenUtil.getRemoteTicket(wxParams.getAppId(), wxParams.getAccessToken(),wechatJsapiticketService);//重新从微信服务器获取ticket并保存数据库
				if(StringUtils.isNotEmpty(ticket)){
					wxParams.setJsapiTicket(ticket);
				}else{
					throw new Exception("获取ticket失败");
				}
			}
			WechatSign.sign(wxParams);//调用签名算法返回签名	
			return;
		}
		token = WechatTokenUtil.getRemoteToken(wxParams.getAppId(), wxParams.getAppSecret(),wechatAccessTokenService);//重新从微信服务器获取token并保存数据库
		if(StringUtils.isNotEmpty(token)){
			wxParams.setAccessToken(token);
			ticket = WechatTokenUtil.getRemoteTicket(wxParams.getAppId(), wxParams.getAccessToken(),wechatJsapiticketService);//重新从微信服务器获取ticket并保存数据库
			if(StringUtils.isNotEmpty(ticket)){
				wxParams.setJsapiTicket(ticket);
			}else{
				throw new Exception("获取ticket失败");
			}
		}else{
			throw new Exception("获取token失败");
		}
		WechatSign.sign(wxParams);
	}
	
	/**
	 * @Description:获取微信分享内容
	 */
	protected void getWechatSharedContent(String userId){
		try {			
			if(shareContent!=null){
				String[]  str = shareContent.split("\\|");
				if(str.length>=4){
					wxParams.setImgUrl(str[0]);
					wxParams.setShareTitle(str[1]);
					wxParams.setShareDesc(str[2]);
					wxParams.setShareControl(str[3]);
					if(!LanshaConstant.WECHAT_SHARE_FUNCTION_ON.equalsIgnoreCase(wxParams.getShareControl())){
						/*微信分享功能功能关闭,注意：如果在有效期内再次获取access_token，那么上一次获取的access_token将失效。当分享功能上线之后，必须关闭测试环境和开发环境的
						 * 微信分享功能，否则会影响产品环境的分享功能，因为这几个环境用的是同一个公众号。
						*/
						return;
					}
				}
				wxParams.setShareLink(getHostPath()+getContextPath() + (StringUtils.isEmpty(userId)?getShareLink():(getShareLink()+"?pn="+userId)));//设置分享链接
				//wxParams.setShareLink("http://lansha.tv"+getContextPath() + (StringUtils.isEmpty(userId)?getShareLink():(getShareLink()+"?pn="+userId)));//测试设置分享链接
				//一定要动态获取请求的URL，微信已经在链接的最后面加了参数，不能修改这个链接，否则会导致签名失败
				//要测试微信分享功能，域名必须是code.ywwl.com 或者www.lansha.tv (且后面没有跟端口号)在公众号中可以配置(进入公众平台->设置->功能设置->JS接口安全域名)
				wxParams.setSignURL(getHostPath()+getHttpUrl());
				//wxParams.setSignURL("http://lansha.tv"+getContextPath()+getHttpUrl());//一定要动态获取请求的URL，微信已经在链接的最后面加了参数，不能修改这个链接，否则会导致签名失败
				generateWechatParams();				
			}			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 获得本地token
	 * 
	 * @author ZHY
	 * @creationDate. 2016-01-25 下午13:51:41
	 * @param localToken
	 *            本地token信息
	 * @return token：如果过期或者不存在token信息则返回空
	 * @throws Exception
	 */
	private String getLocalToken(WechatAccessToken localToken){
		if (localToken != null) { // token信息不为空
			int timeout = WechatConstantsUtil.API_ACCESSTOKEN_TIMEOUT; // 超时时间
			long time = localToken.getPubDate().getTime(); // 取到token记录的时间
			Date now = new Date();
			// 获取时间间隔
			double diff = (now.getTime() - time) / 1000d;
			if (diff < timeout) { // 未超时，直接返回
				return localToken.getTokenName();
			}
		}
		return "";
	}
	
	/**
	 * 获得本地ticket
	 * 
	 * @author ZHY
	 * @creationDate. 2016-01-25 下午13:51:41
	 * @param localToken
	 *            本地token信息
	 * @return token：如果过期或者不存在token信息则返回空
	 * @throws Exception
	 */
	private String getLocalTicket(WechatJsapiticket localTicket){
		if (localTicket != null) { // token信息不为空
			int timeout = WechatConstantsUtil.API_ACCESSTOKEN_TIMEOUT; // 超时时间
			long time = localTicket.getPubDate().getTime(); // 取到token记录的时间
			Date now = new Date();
			// 获取时间间隔
			double diff = (now.getTime() - time) / 1000d;
			if (diff < timeout) { // 未超时，直接返回
				return localTicket.getTicketName();
			}
		}
		return "";
	}
	
	public WechatJsJdkParams getWxParams() {
		return wxParams;
	}

	public void setWxParams(WechatJsJdkParams wxParams) {
		this.wxParams = wxParams;
	}
	
	public String getShareLink() {
		return shareLink;
	}

	public void setShareLink(String shareLink) {
		this.shareLink = shareLink;
	}

	public String getShareContent() {
		return shareContent;
	}

	public void setShareContent(String shareContent) {
		this.shareContent = shareContent;
	}
	
}
