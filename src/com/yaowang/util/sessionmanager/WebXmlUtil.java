package com.yaowang.util.sessionmanager;

import javax.servlet.ServletContext;

import org.apache.catalina.core.StandardContext;
import org.apache.catalina.deploy.FilterDef;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Service;
import org.springframework.web.context.ServletContextAware;

import com.yaowang.util.ReflectionUtil;
import com.yaowang.util.ResourcesLoad;
import com.yaowang.util.sessionmanager.socket.SocketClient;
/**
 * 初始化web.xml
 * @author shenl
 *
 */
@Service("webXmlUtil")
public class WebXmlUtil implements InitializingBean, ServletContextAware {
	private ServletContext servletContext;
	private static StandardContext context;

	@Override
	public void afterPropertiesSet() throws Exception {
        //修改filter的class
        if (ResourcesLoad.getDevMode()) {
            //开发环境下执行
            if (servletContext == null) {
                System.out.println("ServletContext 获取失败！");
            }else {
                initContext();
            }
        }
	}
	
	private void initContext(){
		StandardContext context = (StandardContext)ReflectionUtil.getFieldValue(ReflectionUtil.getFieldValue(servletContext, "context"),"context");
		init(context);
	}
	
	public static void init(StandardContext context){
		WebXmlUtil.context = context;
		//是否保存session
		//更换session管理工具
		SessionManager manager = new SessionManager(context.getDocBase());
		context.setManager(manager);
		
		//是否输出请求日志
		//修改struts2的filter
		FilterDef filterDef = context.findFilterDef("struts2");
		if (filterDef != null) {
			filterDef.setFilterClass("com.yaowang.util.sessionmanager.log.ActionLogStrutsFilter");
			new SocketClient();//与eclipse插件连接
		}
	}
	
	@Override
	public void setServletContext(ServletContext servletContext) {
		this.servletContext = servletContext;
	}

	public static StandardContext getContext() {
		return context;
	}
	
	public static ServletContext getServletContext(){
		return context.getServletContext();
	}
}
