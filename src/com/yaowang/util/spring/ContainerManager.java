package com.yaowang.util.spring;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.web.context.ContextLoader;
/**
 * 获取spring bean
 * @author shenl
 *
 */
public class ContainerManager implements ApplicationContextAware{
	private ApplicationContext applicationContext;
	
    public static Object getComponent(String name) {
//		ApplicationContext ctx = WebApplicationContextUtils.getRequiredWebApplicationContext(ServletActionContext.getServletContext());
//		return ctx.getBean(name);
    	return ContextLoader.getCurrentWebApplicationContext().getBean(name);
    }
    
    public Object getBean(String name){
    	if (applicationContext == null) {
			return null;
		}
    	return applicationContext.getBean(name);
    }

	@Override
	public void setApplicationContext(ApplicationContext applicationContext)
			throws BeansException {
		this.applicationContext = applicationContext;
	}
}
