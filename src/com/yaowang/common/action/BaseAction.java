package com.yaowang.common.action;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;

import com.yaowang.common.constant.BaseConstant;
import com.yaowang.entity.AdminUser;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

public class BaseAction extends ActionSupport{
	private static final long serialVersionUID = 1L;

	/**
	 * 获取request
	 * @return
	 */
	public static HttpServletRequest getRequest(){
		return ServletActionContext.getRequest();
	}
	
	/**
	 * 获取response
	 * @return
	 */
	public static HttpServletResponse getResponse(){
		return ServletActionContext.getResponse();
	}
	
	/**
	 * 获取session
	 * @param parm
	 * @return
	 */
	public static Map<String, Object> getSession() {
        return ActionContext.getContext().getSession();
    }
	
	/**
	 * 获取管理员用户信息
	 */
	public static AdminUser getAdminLogin(){
		return (AdminUser)getSession().get(BaseConstant.ADMIN_LOGIN);
	}
	/**
	 * 设置管理员信息
	 * @param user
	 */
	public static void setAdminLogin(AdminUser user){
		getSession().put(BaseConstant.ADMIN_LOGIN, user);
	}
	
	public String getCommonUserId(){
		return getAdminLogin().getId();
	}
}
