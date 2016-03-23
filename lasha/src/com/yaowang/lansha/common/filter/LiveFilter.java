package com.yaowang.lansha.common.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class LiveFilter implements Filter{

	@Override
	public void init(FilterConfig arg0) throws ServletException {
		
	}

	@Override
	public void destroy() {
		
	}

	@Override
	public void doFilter(ServletRequest request1, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		if (request1 instanceof HttpServletRequest) {
			HttpServletRequest request = (HttpServletRequest)request1;
			String uri = request.getRequestURI();
			String path = request.getContextPath();
			if (uri.matches(path + "/\\d+")) {
				//伪静态
				String url = "/live" + uri + ".html";
				request.getRequestDispatcher(url).forward(request1, response);
				return;
			}
		}
		chain.doFilter(request1, response);
	}
}
