package com.yaowang.util.cache.advice;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.springframework.stereotype.Service;

import com.yaowang.common.dao.PageDto;
import com.yaowang.util.MD5;
import com.yaowang.util.cache.CacheUtil;
import com.yaowang.util.cache.DefaultCacheManager;

@Service("cacheAdvice")
public class CacheAdvice implements MethodInterceptor{
	@Override
	public Object invoke(final MethodInvocation invocation) throws Throwable {
		if (invocation.getThis() instanceof DefaultCacheManager) {
			//反射,获取子类注解
			Method method = invocation.getThis().getClass().getMethod(invocation.getMethod().getName(), invocation.getMethod().getParameterTypes());
			//判断是否缓存注解
			if(method.isAnnotationPresent(CacheClearAnnotation.class)){
				Method clear = invocation.getThis().getClass().getMethod("clear");
				Object retVal = invocation.proceed();
				//执行清理方法
				clear.invoke(invocation.getThis());
				return retVal;
			}else if(method.isAnnotationPresent(CacheAnnotation.class)){
				//生成key
				final StringBuilder key =  new StringBuilder();
				Object[] objs = invocation.getArguments();
				
				final List<PageDto> page = new ArrayList<PageDto>();
				for (Object obj : objs) {
					key.append(MD5.encrypt(obj));
					//是否需要分页
					if (obj instanceof PageDto && ((PageDto)obj).isCount()) {
						page.add((PageDto)obj);
					}
				}
				
				final Method getFormCache = invocation.getThis().getClass().getMethod("getFormCache", CacheUtil.class);
				CacheUtil<Object> util = new CacheUtil<Object>() {
					@Override
					public Object get() {
						try {
							Object retVal = invocation.proceed();
							if (!page.isEmpty()) {
								//放入总数数量
								DefaultCacheManager.getCacheService().put(key.toString(), page.get(0).getTotalNum());
							}
							return retVal;
						} catch (Throwable e) {
							e.printStackTrace();
						}
						return null;
					}
					
					@Override
					public String key() {
						if (!page.isEmpty() && ((PageDto)page.get(0)).isCount()) {
							return invocation.getMethod().getName() + "@" + MD5.encrypt(key.toString() + page.get(0).getCurrentPage());
						}else {
							return invocation.getMethod().getName() + "@" + MD5.encrypt(key.toString());
						}
					}
				};
				if (!page.isEmpty() && ((PageDto)page.get(0)).isCount()) {
					// 分页总数
					Object count = DefaultCacheManager.getCacheService().get(key.toString());
					if (count != null) {
						page.get(0).setTotalNum((Integer)count);
					}
				}
				return getFormCache.invoke(invocation.getThis(), util);
			}
		}
		//类方法没有自定义注解，直接执行该方法  
        return invocation.proceed();  
	}
}
