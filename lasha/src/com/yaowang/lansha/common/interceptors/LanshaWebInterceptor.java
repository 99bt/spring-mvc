package com.yaowang.lansha.common.interceptors;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionInvocation;
import com.yaowang.common.interceptors.CommonInterceptor;
import com.yaowang.lansha.common.action.LanshaBaseAction;


/**
 * 登录拦截器
 * @author Administrator
 *
 */
public class LanshaWebInterceptor extends CommonInterceptor {

	/**
	 * [2015-9-3上午11:24:35]zlb
	 * @Fields serialVersionUID : TODO
	 */
	
	private static final long serialVersionUID = 1L;

	@Override
	public String intercept(ActionInvocation invocation) throws Exception {
		if (LanshaBaseAction.getUserLoginStatic() == null) {
			ActionContext ctx = invocation.getInvocationContext();
			HttpServletRequest request = (HttpServletRequest)ctx.get(ServletActionContext.HTTP_REQUEST);
			HttpServletResponse response = (HttpServletResponse)ctx.get(ServletActionContext.HTTP_RESPONSE);
			response.sendRedirect(request.getContextPath()+"/login.html?backUrl=" + LanshaBaseAction.getHttpUrlStatic());
			return null;
		}
		return invocation.invoke();
	}
}
