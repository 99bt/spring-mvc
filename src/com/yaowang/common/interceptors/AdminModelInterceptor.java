package com.yaowang.common.interceptors;

import java.util.Set;

import org.apache.commons.lang.StringUtils;

import com.yaowang.common.action.BasePageAction;
import com.yaowang.common.constant.BaseConstant;
import com.opensymphony.xwork2.ActionInvocation;

/**
 * 后台管理员权限模块验证
 * @author shenl
 *
 */
@SuppressWarnings("unchecked")
public class AdminModelInterceptor extends CommonInterceptor{
	private static final long serialVersionUID = 1L;

	@Override
	public String intercept(ActionInvocation invocation) throws Exception {
		//权限列表
		Set<String> urlSet = (Set<String>) BasePageAction.getSession().get(BaseConstant.ADMIN_MODEL);
		if (urlSet == null) {
			return "loginScript";
		}
		//请求地址
		String uri = BasePageAction.getRequest().getRequestURI();
		//是否带有contextPath
		String path = BasePageAction.getRequest().getContextPath();
		if(StringUtils.isNotBlank(path)) {
			uri = uri.substring(path.length());
		}
		//是否带jsessionid
		if (uri.indexOf(";jsessionid=") > -1) {
			uri = uri.substring(0, uri.indexOf(";jsessionid="));
		}
		if (urlSet.contains(uri)) {
			return invocation.invoke();
		}
		for (String url : urlSet) {
			if (url.length() < 5) {
				continue;
			}
			url = url.substring(0, url.length() - 5) + "-.*\\.html";
			if (uri.matches(url)) {
				return invocation.invoke();
			}
		}
		return "loginScript";
	}
}
