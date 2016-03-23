package com.yaowang.lansha.entity;

/**
 * @ClassName: WechatJsJdkParams
 * @Description: 微信JSJDK参数类
 * @author ZHY
 * @date 2016-1-25 中午午12:20:25
 * 
 */
public class WechatJsJdkParams {

	/**
	 * 微信公众号应用ID
	 */
	private String appId;
	/**
	 * 微信公众号应用密钥
	 */
	private String appSecret;
	/**
	 * 访问微信接口的token
	 */
	private String accessToken;
	/**
	 * 微信js接口的验证票据
	 */
	private String jsapiTicket;
	/**
	 * 生成签名的随机串
	 */
	private String noncestr;
	/**
	 * 生成签名的时间戳
	 */
	private String timestamp;
	/**
	 * 签名
	 */
	private String signature;
	/**
	 * 分享的图标地址
	 */
	private String imgUrl;
	/**
	 * 分享链接
	 */
	private String shareLink;
	
	/**
	 * 动态链接(需要参与到签名算法)
	 */
	private String signURL;

	/**
	 * 分享的标题
	 */
	private String shareTitle;
	/**
	 * 分享的内容
	 */
	private String shareDesc;
	
	/**
	 * 分享功能开启/或关闭控制
	 */
	private String shareControl;

	/**
	 * 获取微信公众号应用ID
	 */
	public String getAppId() {
		return appId;
	}

	public void setAppId(String appId) {
		this.appId = appId;
	}

	/**
	 * 获取微信公众号应用密钥
	 */
	public String getAppSecret() {
		return appSecret;
	}

	public void setAppSecret(String appSecret) {
		this.appSecret = appSecret;
	}
	
	/**
	 * 获取访问微信接口的token
	 */
	public String getAccessToken() {
		return accessToken;
	}

	public void setAccessToken(String accessToken) {
		this.accessToken = accessToken;
	}
	
	/**
	 * 获取微信js接口的验证票据
	 */
	public String getJsapiTicket() {
		return jsapiTicket;
	}

	public void setJsapiTicket(String jsapiTicket) {
		this.jsapiTicket = jsapiTicket;
	}

	/**
	 * 获取签名的随机串
	 */
	public String getNoncestr() {
		return noncestr;
	}

	public void setNoncestr(String noncestr) {
		this.noncestr = noncestr;
	}

	/**
	 * 获取签名的时间戳
	 */
	public String getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(String timestamp) {
		this.timestamp = timestamp;
	}

	/**
	 * 获取签名
	 */
	public String getSignature() {
		return signature;
	}

	public void setSignature(String signature) {
		this.signature = signature;
	}
	
	/**
	 * 获取分享图标
	 */
	public String getImgUrl() {
		return imgUrl;
	}

	public void setImgUrl(String imgUrl) {
		this.imgUrl = imgUrl;
	}

	/**
	 * 获取分享的链接
	 */
	public String getShareLink() {
		return shareLink;
	}

	public void setShareLink(String shareLink) {
		this.shareLink = shareLink;
	}

	/**
	 * 获取分享标题
	 */
	public String getShareTitle() {
		return shareTitle;
	}

	public void setShareTitle(String shareTitle) {
		this.shareTitle = shareTitle;
	}

	/**
	 * 获取分享描述
	 */
	public String getShareDesc() {
		return shareDesc;
	}

	public void setShareDesc(String shareDesc) {
		this.shareDesc = shareDesc;
	}
	
	/**
	 * 获取动态链接(需要参与到签名算法)
	 */
	public String getSignURL() {
		return signURL;
	}

	public void setSignURL(String signURL) {
		this.signURL = signURL;
	}

	/**
	 * 获取分享控制参数
	 */
	public String getShareControl() {
		return shareControl;
	}

	public void setShareControl(String shareControl) {
		this.shareControl = shareControl;
	}
}
