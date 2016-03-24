package com.yaowang.common.interceptors;

import com.opensymphony.xwork2.ActionInvocation;
/**
 * 接口拦截器
 * @author shenl
 *
 */
public class InterfaceInterceptor extends CommonInterceptor {
	private static final long serialVersionUID = 1L;

	@Override
	public String intercept(ActionInvocation invocation) throws Exception {
//		System.out.println("InterfaceInterceptor");
		return invocation.invoke();
	}
}
