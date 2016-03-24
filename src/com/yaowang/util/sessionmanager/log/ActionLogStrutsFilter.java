package com.yaowang.util.sessionmanager.log;

import java.io.IOException;
import java.util.Date;
import java.util.Map;

import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;
import org.apache.struts2.dispatcher.Dispatcher;
import org.apache.struts2.dispatcher.ServletDispatcherResult;
import org.apache.struts2.dispatcher.mapper.ActionMapping;
import org.apache.struts2.dispatcher.ng.ExecuteOperations;
import org.apache.struts2.dispatcher.ng.InitOperations;
import org.apache.struts2.dispatcher.ng.PrepareOperations;
import org.apache.struts2.dispatcher.ng.filter.FilterHostConfig;
import org.apache.struts2.dispatcher.ng.filter.StrutsPrepareAndExecuteFilter;
import org.apache.struts2.views.freemarker.FreemarkerResult;

import com.yaowang.util.ReflectionUtil;
import com.yaowang.util.sessionmanager.socket.SocketClient;
import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.ActionProxy;
import com.opensymphony.xwork2.DefaultActionInvocation;
import com.opensymphony.xwork2.Result;
import com.opensymphony.xwork2.config.entities.ActionConfig;
import com.opensymphony.xwork2.config.entities.PackageConfig;
import com.opensymphony.xwork2.config.impl.DefaultConfiguration;
import com.opensymphony.xwork2.util.location.Location;

/**
 * struts2 拦截打印action请求信息
 * @author shenl
 *
 */
public class ActionLogStrutsFilter extends StrutsPrepareAndExecuteFilter {
//	private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	
	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
	    InitOperations init = new InitOperations();
        try {
            FilterHostConfig config = new FilterHostConfig(filterConfig);
            init.initLogging(config);
            Dispatcher dispatcher = init.initDispatcher(config);
            init.initStaticContentLoader(config, dispatcher);

            prepare = new PrepareOperations(filterConfig.getServletContext(), dispatcher);
            execute = new ExecuteOperations(filterConfig.getServletContext(), dispatcher);
            this.excludedPatterns = init.buildExcludedPatternsList(dispatcher);

            postInit(dispatcher, filterConfig);
        } finally {
            init.cleanup();
        }
	}
	
	@Override
	public void doFilter(ServletRequest req, ServletResponse res,
			FilterChain chain) throws IOException, ServletException {
		
        HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) res;

        try {
            prepare.setEncodingAndLocale(request, response);
            prepare.createActionContext(request, response);
            prepare.assignDispatcherToThread();
			if (excludedPatterns != null && prepare.isUrlExcluded(request, excludedPatterns)) {
				chain.doFilter(request, response);
			} else {
				request = prepare.wrapRequest(request);
				ActionMapping mapping = prepare.findActionMapping(request, response, true);
				if (mapping == null) {
					boolean handled = execute.executeStaticResourceRequest(request, response);
					if (!handled) {
						chain.doFilter(request, response);
					}
				} else {
					Date startTime = new Date();
					execute.executeAction(request, response, mapping);
					Date endTime = new Date();
					intercept(request, endTime.getTime() - startTime.getTime());
				}
			}
        } finally {
            prepare.cleanupRequest(request);
        }
    }

	public static void intercept(HttpServletRequest request, double time) {
		ActionInvocation invocation = ServletActionContext.getContext().getActionInvocation();
		if (invocation == null) {
			return;
		}
		String msg = "<=================开始===================================================>\n";

//		msg += ">\t时间:\t" + DATE_FORMAT.format(new Date()) + "\n";
		//耗时
		msg += ">\t总耗时:\t" + (time/1000) + "秒\n";
		//地址
		msg += ">\t地址:\t" + request.getRequestURI() + "\n";
		//struts配置文件路径
		msg += ">\t配置文件路径: at " + getStrutsXml(invocation) + "\n";

		ActionProxy proxy = invocation.getProxy();
		String className = invocation.getAction().getClass().getName();
		String method = proxy.getMethod();
		String fileName = className.substring(className.lastIndexOf('.') + 1) + ".java";
		String action = ">\tAction:\tat " + className	+ "." + method + "(" + fileName + ":" + ClassUtils.getMethodLineNumb(className, method) + ")";
		msg += action + "\n";

		// 在Action和Result运行之后，得到Result对象
		// 并且可以强制转换成ServletDispatcherResult，打印其下一个JSP的位置
		Result rresult = null;
		try {
			rresult = invocation.getResult();
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (rresult instanceof ServletDispatcherResult) {
			ServletDispatcherResult result = (ServletDispatcherResult) rresult;
//			System.out.println("└-->JSP: " + result.getLastFinalLocation());
			msg += ">\t└-->JSP: at(" + getResultFile(request.getRequestURI(), result.getLastFinalLocation())+")\n";
		}else if (rresult instanceof FreemarkerResult) {
			FreemarkerResult result = (FreemarkerResult) rresult;
//			System.out.println("└-->FTL: " + result.getLastFinalLocation());
			msg += ">\t└-->FTL: at(" + getResultFile(request.getRequestURI(), result.getLastFinalLocation())+")\n";
		}
		// 找到这次请求的request中的parameter参数，并打印
		Map<String, Object> params = invocation.getInvocationContext().getParameters();
		for (String key : params.keySet()) {
			Object obj = params.get(key);

			if (obj instanceof String[]) {
				String[] arr = (String[]) obj;
				msg += ">\t└-->参数:" + key + "\n";
				for (String value : arr) {
					msg += ">\t   └--->:" + value + "\n";
				}
			}
		}

		msg += "<=================结束===================================================>\n";
		msg += "\n";
		SocketClient.print(msg);
	}
	/**
	 * struts配置文件路径
	 * @param invocation
	 * @return
	 */
	private static String getStrutsXml(ActionInvocation invocation) {
		//查询配置文件路径
		if (invocation instanceof DefaultActionInvocation) {
			DefaultActionInvocation actionInvocation = (DefaultActionInvocation)invocation;
			ActionProxy proxy = invocation.getProxy();
			DefaultConfiguration config = (DefaultConfiguration)ReflectionUtil.getFieldValue(actionInvocation, "configuration");
			if (config !=null) {
				for (PackageConfig packageConfig : config.getPackageConfigs().values()) {
					if (!packageConfig.isAbstract() && packageConfig.getNamespace().equals(proxy.getNamespace())) {
						Map<String, ActionConfig> actionConfigs = packageConfig.getAllActionConfigs();
						ActionConfig baseConfig = actionConfigs.get(proxy.getActionName());
						if (baseConfig != null) {
							//class的根路径
							String root = proxy.getClass().getClassLoader().getResource("").toString();
							Location location = baseConfig.getLocation();
							String file = location.getURI();
							//截取相对路径
							String xmlPath = file.replaceAll(".*" + root + "(.*?)", "$1");
							return "(" + xmlPath + ":" + location.getLineNumber() + ")";
						}
					}
				}
			}
		}
		return null;
	}
	

	/**
	 * 获取视图文件全路径
	 * @param action
	 * @param file
	 * @return
	 */
	public static String getResultFile(String action, String file){
		int i = action.lastIndexOf("/");
		String result = "";
		if(file.startsWith("/")){
			result = file;
		}else if (i>-1) {
			result += action.substring(0, i);
			result += "/" + file;
		}else {
			result += "/" + file;
		}
		return result;
	}
}
