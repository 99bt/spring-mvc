package com.yaowang.util;


import java.lang.reflect.Field;
import java.lang.reflect.Method;

public final class ReflectionUtil {
	
	public static Object callStaticMethod2(Object aObject, String aFieldName) {
		Class<?> cObject = getClass(aObject);
		try {
			Method method = cObject.getMethod(aFieldName);
			return method.invoke(aObject);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	/**
	 * 反射调用静态方法
	 * @param aObject
	 * @param aFieldName
	 * @return
	 */
	public static Object callStaticMethod(Object aObject, String aFieldName){
		Class<?> cObject = getClass(aObject);
		Method method = getClassMethod(cObject, aFieldName);// get the field in this object
		if (method != null) {
			method.setAccessible(true);
			try {
				return method.invoke(aObject);
			} catch (Exception e) {
				e.printStackTrace();
				return null;
			}
		}
		return null;
	}
	/**
	 * 反射获取方法
	 * 
	 * @param aClazz
	 * @param aFieldName
	 * @return
	 */
	public static Method getClassMethod(Class<?> aClazz, String aMethodName){
		Method[] declaredMethods = aClazz.getDeclaredMethods();
		for (Method method : declaredMethods) {
			// 注意：这里判断的方式，是用字符串的比较。很傻瓜，但能跑。要直接返回Field。我试验中，尝试返回Class，然后用getDeclaredField(String
			// fieldName)，但是，失败了
			if (method.getName().equals(aMethodName)) {
				return method;// define in this class
			}
		}

		Class<?> superclass = aClazz.getSuperclass();
		if (superclass != null) {// 简单的递归一下
			return getClassMethod(superclass, aMethodName);
		}
		return null;
	}

	/**
	 * 反射获取对象属性值
	 * 
	 * @param aObject
	 * @param aFieldName
	 * @return
	 */
	public static Object getFieldValue(Object aObject, String aFieldName) {
		Class<?> cObject = aObject.getClass();
		Field field = getClassField(cObject, aFieldName);// get the field in this object
		if (field != null) {
			field.setAccessible(true);
			try {
				return field.get(aObject);
			} catch (Exception e) {
				e.printStackTrace();
				return null;
			}
		}
		return null;
	}
	/**
	 * 反射获取静态属性值
	 * @param aObject
	 * @param aFieldName
	 * @return
	 */
	public static Object getFieldStataicValue(Object aObject, String aFieldName) {
		Class<?> cObject = getClass(aObject);
		Field field = getClassField(cObject, aFieldName);// get the field in this object
		if (field != null) {
			field.setAccessible(true);
			try {
				//静态属性
				return field.get(null);
			} catch (Exception e) {
				e.printStackTrace();
				return null;
			}
		}
		return null;
	}
	/**
	 * 反射获取属性值
	 * 
	 * @param aClazz
	 * @param aFieldName
	 * @return
	 */
	private static Field getClassField(Class<?> aClazz, String aFieldName) {
		Field[] declaredFields = aClazz.getDeclaredFields();
		for (Field field : declaredFields) {
			// 注意：这里判断的方式，是用字符串的比较。很傻瓜，但能跑。要直接返回Field。我试验中，尝试返回Class，然后用getDeclaredField(String
			// fieldName)，但是，失败了
			if (field.getName().equals(aFieldName)) {
				return field;// define in this class
			}
		}

		Class<?> superclass = aClazz.getSuperclass();
		if (superclass != null) {// 简单的递归一下
			return getClassField(superclass, aFieldName);
		}
		return null;
	}
	/**
	 * 获取class对象
	 * @param aObject
	 * @return
	 */
	private static Class<?> getClass(Object aObject){
		Class<?> cObject = null;
		if (aObject instanceof Class<?>) {
			cObject = (Class<?>)aObject;
		}else if (aObject instanceof String) {
			try {
				cObject = Class.forName(aObject.toString());
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}
		}else {
			cObject = aObject.getClass();
		}
		return cObject;
	}
}
