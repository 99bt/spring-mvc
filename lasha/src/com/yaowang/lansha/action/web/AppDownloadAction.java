package com.yaowang.lansha.action.web;


import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;

import com.yaowang.entity.SysOption;
import com.yaowang.lansha.common.action.LanshaBaseAction;
import com.yaowang.lansha.util.UserChannelUtils;
import com.yaowang.service.SysOptionService;

/**
 * APP下载
 * @author Administrator
 * zengxi
 */
public class AppDownloadAction extends LanshaBaseAction{

	private static final long serialVersionUID = 1L;
	
	@Resource
	private SysOptionService sysOptionService;
	
	private String lanshaAppIos;//app下载 iOS下载地址
	private String lanshaAppAndroid;//app下载 安卓下载地址
	private String lanshaAppLiveIos;//直播插件iOS下载地址
	private String lanshaAppLiveAndroid;//直播插件安卓下载地址
	
	private String url;
	
	private String type;//1、APP  2、直播插件
	
	public String appDownload(){
		SysOption sysOption = sysOptionService.getSysOptionByIniid("LANSHA.APP.IOS");//app下载 iOS下载地址
		lanshaAppIos = sysOption.getNowvalue();
		
		sysOption = sysOptionService.getSysOptionByIniid("LANSHA.APP.ANDROID");//app下载 安卓下载地址
		lanshaAppAndroid = sysOption.getNowvalue();
		
		sysOption = sysOptionService.getSysOptionByIniid("LANSHA.APP.LIVE_IOS");//直播插件iOS下载地址
		lanshaAppLiveIos = sysOption.getNowvalue();
		
		sysOption = sysOptionService.getSysOptionByIniid("LANSHA.APP.LIVE_ANDROID");//直播插件安卓下载地址
		lanshaAppLiveAndroid = sysOption.getNowvalue();
		
		return SUCCESS;
	}
	
	public String doAppDownload(){
		if(StringUtils.isEmpty(type)){
			addActionError("type数据有误");
			return "msg";
		}

		String header = getRequest().getHeader("User-agent");
		if (StringUtils.isEmpty(header)) {
			header = "";
		}else {
			header = header.toLowerCase();
		}
		
		if(type.equals("1")){//APP下载
			if(header.contains("android")){
				if (StringUtils.isNotBlank(name)) {
					url = getUploadFilePath("/app/app" + name);
				}else {
					String tgqd = UserChannelUtils.getChannelFormCookie(getRequest(), "tgqd");
					if (StringUtils.isNotBlank(tgqd)) {
						//cookie推广渠道包地址
						url = getUploadFilePath("/app/app" + tgqd);
					}else {
						SysOption sysOption = sysOptionService.getSysOptionByIniid("LANSHA.APP.ANDROID");//app下载 安卓下载地址
						url = sysOption.getNowvalue();
					}
				}
				name = getPlatFormName() + "-APP(安卓)";
			}else if(header.contains("iphone") || header.contains("ipad")){
				SysOption sysOption = sysOptionService.getSysOptionByIniid("LANSHA.APP.IOS");//app下载 iOS下载地址
				url = sysOption.getNowvalue();
				name = getPlatFormName() + "-APP(iOS)";
			}
		}else if(type.equals("2")){//直播插件下载
			if(header.contains("android")){
				SysOption sysOption = sysOptionService.getSysOptionByIniid("LANSHA.APP.LIVE_ANDROID");//直播插件安卓下载地址
				url = sysOption.getNowvalue();
				name = getPlatFormName() + "-直播插件(安卓)";
			}else if(header.contains("iphone") || header.contains("ipad")){
				SysOption sysOption = sysOptionService.getSysOptionByIniid("LANSHA.APP.LIVE_IOS");//直播插件iOS下载地址
				url = sysOption.getNowvalue();
				name = getPlatFormName() + "-直播插件(iOS)";
			}
		}

		return SUCCESS;
	}

	public String getLanshaAppIos() {
		return lanshaAppIos;
	}

	public String getLanshaAppAndroid() {
		return lanshaAppAndroid;
	}

	public String getLanshaAppLiveIos() {
		return lanshaAppLiveIos;
	}

	public String getLanshaAppLiveAndroid() {
		return lanshaAppLiveAndroid;
	}

	public String getUrl() {
		return url;
	}

	public void setType(String type) {
		this.type = type;
	}

}
