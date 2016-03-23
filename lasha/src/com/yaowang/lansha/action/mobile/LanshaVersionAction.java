package com.yaowang.lansha.action.mobile;

import java.io.IOException;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;

import com.alibaba.fastjson.JSONObject;
import com.yaowang.lansha.common.action.LanshaMobileAction;
import com.yaowang.lansha.entity.LanshaVersion;
import com.yaowang.lansha.service.LanshaVersionService;

/**
 * 版本号
 * @author Administrator
 *
 */
public class LanshaVersionAction extends LanshaMobileAction {
	private static final long serialVersionUID = 1L;
	
	@Resource
	private LanshaVersionService lanshaVersionService;
	
	private String ostype;//平台 1:android,2:ios
	private String version;//版本号
	private String appType;//客户端类型(空或者0:普通，1:蓝鲨录)
	
	public void checkVersion() throws IOException{
		if(StringUtils.isEmpty(version)){//版本号不能为空
			write(getFailed("版本号不能为空"));
			return;
		}
		if(StringUtils.isEmpty(ostype)){///平台 
			write(getFailed("平台不能为空"));
			return;
		}
		if(StringUtils.isEmpty(appType)){// 客户端类型,空默认普通客户端
			appType = "0";
		}
		
		LanshaVersion lanshaVersion = lanshaVersionService.getLastYwVersionList(ostype, appType);
		
		if (lanshaVersion == null) {
			// 没有新版本
			write(getFailed("没有新版本"));
			return;
		}
		if (StringUtils.isEmpty(lanshaVersion.getVersion())) {
			write(getFailed("数据异常"));
			return;
		}
		
		if(lanshaVersion.getVersion().compareTo(version) <= 0){
			write(getFailed("没有新版本"));
			return;
		}
		
		if(!lanshaVersion.getOsType().equals(ostype)){
			write(getFailed("请输入正确的平台号"));
		}else{
			JSONObject object = new JSONObject();
			object.put("status", 1);
			
			JSONObject data = new JSONObject();
			data.put("version", lanshaVersion.getVersion());
			data.put("url", lanshaVersion.getAddress());
			data.put("intro",  lanshaVersion.getUpdateLog());
			data.put("must",  lanshaVersion.getIsForce());
			data.put("size",  lanshaVersion.getSize());
			object.put("data",  data);
			write(object);
		}
	}

	public void setOstype(String ostype) {
		this.ostype = ostype;
	}
	public void setVersion(String version) {
		this.version = version;
	}

	public void setAppType(String appType) {
		this.appType = appType;
	}
}
