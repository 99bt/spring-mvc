package com.yaowang.lansha.common.interceptors;

import java.io.Writer;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.apache.struts2.ServletActionContext;

import com.opensymphony.xwork2.ActionInvocation;
import com.yaowang.common.action.BaseAction;
import com.yaowang.common.interceptors.CommonInterceptor;
import com.yaowang.lansha.common.action.LanshaMobileAction;
import com.yaowang.lansha.entity.YwUser;
import com.yaowang.lansha.service.YwUserService;
import com.yaowang.util.spring.ContainerManager;

/**
 * 登录拦截器
 * 
 * @author shenlie
 *
 */
public class LanshaMobileInterceptor extends CommonInterceptor {
	private static final long serialVersionUID = 1L;

	@Override
	public String intercept(ActionInvocation invocation) throws Exception {
		Boolean b = setAutoLogin();
		if (!b) {
			// 登录
			HttpServletRequest request = ServletActionContext.getRequest();
			HttpServletResponse response = ServletActionContext.getResponse();
			
			String encoding = request.getCharacterEncoding();
			response.setCharacterEncoding(encoding);
			response.setContentType("text/html;charset=" + encoding);
			Writer writer = response.getWriter();
			try {
				writer.write(LanshaMobileAction.TIMEOUT_STRING);
			} finally {
				writer.flush();
				writer.close();
			}
			return null;
		}
		return invocation.invoke();
	}

	public static Boolean setAutoLogin() throws Exception{
		//自动登录
		YwUser user = LanshaMobileAction.getUserLoginStatic();
		if (user != null) {
			return true;
		}
		return false;
	}
}
