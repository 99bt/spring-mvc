package com.yaowang.common.interceptors;

import com.yaowang.common.action.BaseAction;
import com.yaowang.entity.AdminUser;
import com.opensymphony.xwork2.ActionInvocation;

/**
 * 管理员拦截器
 * @author shenl
 *
 */
public class AdminInterceptor extends CommonInterceptor{
	private static final long serialVersionUID = 1L;

	@Override
	public String intercept(ActionInvocation invocation) throws Exception {
		AdminUser user = BaseAction.getAdminLogin();
		if (user == null) {
			return "loginScript";
		}else {
			return invocation.invoke();
		}
	}
}
