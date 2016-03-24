package com.yaowang.common.interceptors;

import com.yaowang.common.action.BaseAction;
import com.opensymphony.xwork2.interceptor.AbstractInterceptor;
/**
 * 公共拦截器
 * @author Administrator
 *
 */
public abstract class CommonInterceptor extends AbstractInterceptor{
	private static final long serialVersionUID = 1L;

	/**
	 * 获取session
	 * @param parm
	 * @return
	 */
	public static Object getSession(String parm) {
        return BaseAction.getSession().get(parm);
    }
}
