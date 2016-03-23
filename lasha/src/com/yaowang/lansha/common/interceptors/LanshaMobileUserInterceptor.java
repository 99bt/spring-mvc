package com.yaowang.lansha.common.interceptors;

import org.apache.commons.lang3.StringUtils;

import com.opensymphony.xwork2.ActionInvocation;
import com.yaowang.common.action.BaseAction;
import com.yaowang.common.interceptors.CommonInterceptor;
import com.yaowang.lansha.common.action.LanshaMobileAction;
import com.yaowang.lansha.entity.YwUser;
import com.yaowang.lansha.service.YwUserService;
import com.yaowang.util.spring.ContainerManager;

/**
 * 用户登录信息拦截器
 * 
 * @author wanglp
 *
 */
public class LanshaMobileUserInterceptor extends CommonInterceptor {
	private static final long serialVersionUID = 1L;

	@Override
	public String intercept(ActionInvocation invocation) throws Exception {
		//登录
		try {
			String token = BaseAction.getRequest().getParameter("token");
			if (StringUtils.isNotBlank(token)) {
				YwUserService ywUserService = (YwUserService) ContainerManager.getComponent("ywUserService");
				YwUser user = ywUserService.getYwusersByToken(token);
				if (user != null) {
					LanshaMobileAction.setUserLogin(user);
				}
			}
			return invocation.invoke();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
}
