package com.yaowang.util.sessionmanager.log;

import java.io.IOException;

import javassist.CannotCompileException;
import javassist.ClassClassPath;
import javassist.ClassPool;
import javassist.CtClass;
import javassist.CtMethod;
import javassist.NotFoundException;

public class ClassUtils {
	private static final ClassPool pool = ClassPool.getDefault();
	static {
		//将tomcat应用中WEB-INF/lib目录加入classpath
		pool.insertClassPath(new ClassClassPath(new ClassUtils().getClass()));
	}
	/**
	 * 获取方法行数
	 * @param className
	 * @param methhod
	 * @return
	 */
	public static final int getMethodLineNumb(String className, String methhod){
		try {
			CtMethod method = pool.getMethod(className, methhod);
			return method.getMethodInfo2().getLineNumber(0);
		} catch (NotFoundException e) {
			return -1;
		}
	}
	/**
	 * 修改方法体
	 * @param className
	 * @param method
	 * @param body
	 * @throws NotFoundException
	 * @throws CannotCompileException
	 * @throws IOException 
	 */
	public static final void alertMethos(String className, String method, String body) throws NotFoundException, CannotCompileException, IOException{
		CtClass cc = pool.get(className);
		CtMethod cm = cc.getDeclaredMethod(method);
		cm.insertBefore(body);
		cc.writeFile();
	}
}
