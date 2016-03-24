package com.yaowang.util;

public class ArrayUtil {
	/**
	 * 将字符串数组转成数字数组
	 * 
	 * @param ids
	 * @return
	 */
	public static Integer[] toIntger(String[] ids) {
		Integer[] arrays = new Integer[ids.length];
		for (int i = 0; i < ids.length; i++) {
			arrays[i] = Integer.valueOf(ids[i]);
		}
		return arrays;
	}
}
