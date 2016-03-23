package com.yaowang.lansha.common.interceptors;

import java.util.Enumeration;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.apache.struts2.ServletActionContext;

import com.opensymphony.xwork2.ActionInvocation;
import com.yaowang.common.action.BaseDataAction;
import com.yaowang.common.interceptors.CommonInterceptor;
import com.yaowang.common.util.LogUtil;

/**
 * json 拦截处理
 * @author shenl
 *
 */
public class JsonSystemInterceptor extends CommonInterceptor {
	private static final long serialVersionUID = 1L;

	@Override
	public String intercept(ActionInvocation invocation) throws Exception {
		try {
			HttpServletRequest request = ServletActionContext.getRequest();
			LogUtil.log("MOBILE_REQUEST", getRequestData(request));
			return invocation.invoke();
		} catch (Exception e) {
			if (StringUtils.isNotBlank(e.getMessage())) {
				String res = e.getMessage().replaceAll("\"", "\'");
				BaseDataAction.write(BaseDataAction.getError(res));
			}else {
				BaseDataAction.write(BaseDataAction.getError("对不起,系统出错了"));
			}
			e.printStackTrace();
			return null;
		}
	}
	/**
	 * 获取参数
	 * @return
	 */
	private StringBuilder getParameter(ServletRequest request){
		Enumeration<String> e = request.getParameterNames();
		StringBuilder builder = new StringBuilder();
		while (e.hasMoreElements()) {
			String name = e.nextElement();
			String[] strs = request.getParameterValues(name);
			for (String str : strs) {
				builder.append(name);
				builder.append("=");
				builder.append(str);
				builder.append("&");
			}
		}
		if (builder.length() > 0) {
			builder.setLength(builder.length() - 1);
		}
		return builder;
	}
	private String getRequestData(ServletRequest request){
		String url = ((HttpServletRequest)request).getRequestURI();
		return "url="+url+",data={"+getParameter(request)+"}";
	}
}
