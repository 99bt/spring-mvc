package com.yaowang.lansha.common.action;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import com.yaowang.lansha.entity.YwUser;
import com.yaowang.lansha.service.YwUserService;
import com.yaowang.util.spring.ContainerManager;

/**
 * 蓝鲨移动端接口类
 * @author shenl
 *
 */
public class LanshaMobileAction extends LanshaBaseAction{
	private static final long serialVersionUID = 1L;
	protected String token;
	protected String appver;//android版本号，将值设置到version中，统一调用version(可查看setVersion方法)
	protected String version;//ios版本号
	protected String ostype;//系统类型（1：android，2：ios）
	/**
	 * 超时
	 */
	public static final String TIMEOUT_STRING = "{\"status\": -2, \"failed\": \"登录超时\"}";
	/**
	 * 空数据
	 */
	protected static final List EMPTY_DATA_ARRAY = new ArrayList();
	protected static final Object EMPTY_DATA_ENTITY = new Object();
	protected static final String EMPTY_DATA_STRING = "";
	
	/**
	 * 设置登录
	 * @param
	 */
	public static void setUserLogin(YwUser user){
		getRequest().setAttribute("login_lansha_user", user);
	}

	/**
	 * 获取登录用户
	 * @return
	 */
	public YwUser getUserLogin(){
		return getUserLoginStatic();
	}
	public static YwUser getUserLoginStatic(){
		return (YwUser)getRequest().getAttribute("login_lansha_user");
	}
	
	/**
	 * 根据token获取用户id
	 * @param token
	 * @return
	 */
	public YwUser getUserByToken(String token){
		//测试
		//token = "3A433F079C6F4B06938D86ADFEDE12F9";
		//测试
		if (StringUtils.isBlank(token) || token.length() != 32) {
			return null;
		}
		YwUserService service = (YwUserService)ContainerManager.getComponent("ywUserService");
		return service.getYwusersByToken(token);
	}
	
	/**
	 * 判断是否登录
	 * @return
	 */
	public Boolean getUserIsLogin(String token){
		return getUserLogin() != null;
	}
	/**
	 * 是否是主播
	 * @return
	 */
	public Boolean getUserIsAnchor(String token){
		YwUser user = getUserLogin();
		return user != null && user.getUserType() == 1;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public void setAppver(String appver) {
		this.appver = appver;
		if(StringUtils.isBlank(this.version)){
			this.version = this.appver;
		}
	}

	public void setOstype(String ostype) {
		this.ostype = ostype;
	}

	public void setVersion(String version) {
		//前期因为android和ios参数名称不统一，这里统一到同一个参数中
		this.version = version;
	}
}
