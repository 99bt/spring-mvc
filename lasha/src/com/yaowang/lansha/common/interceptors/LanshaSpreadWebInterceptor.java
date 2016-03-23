package com.yaowang.lansha.common.interceptors;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.struts2.ServletActionContext;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionInvocation;
import com.yaowang.common.interceptors.CommonInterceptor;
import com.yaowang.lansha.util.UserChannelUtils;

public class LanshaSpreadWebInterceptor extends CommonInterceptor {

	/**
	 * [2016-1-6下午1:12:24]zlb
	 * @Fields serialVersionUID : TODO
	 */
	
	private static final long serialVersionUID = 1L;

	@Override
	public String intercept(ActionInvocation invocation) throws Exception {
		ActionContext ctx = invocation.getInvocationContext();
		HttpServletRequest request = (HttpServletRequest)ctx.get(ServletActionContext.HTTP_REQUEST);
		String pn = request.getParameter("pn");
		//把推广人员id存放cookie
		if(StringUtils.isNotEmpty(pn)){
			HttpServletResponse response = (HttpServletResponse)ctx.get(ServletActionContext.HTTP_RESPONSE);
			UserChannelUtils.addChannel2Cookie(response, "PN", pn);
		}
		
		String tgqd = request.getParameter("tgqd");
		//把推广包地址存放cookie
		if(StringUtils.isNotEmpty(tgqd)){
			HttpServletResponse response = (HttpServletResponse)ctx.get(ServletActionContext.HTTP_RESPONSE);
			UserChannelUtils.addChannel2Cookie(response, "tgqd", tgqd);
		}
		return invocation.invoke();
	}

}
