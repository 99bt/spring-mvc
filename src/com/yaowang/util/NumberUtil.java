package com.yaowang.util;

import java.math.BigDecimal;
import java.util.Random;


public class NumberUtil {
	/**
	 * @Title: changeNumberToWan
	 * @Description: 转换成万,如果数据小于万保持不变，如果大于计算后+“w”单位
	 * @param number
	 * @param scale  需要保留的小数位
	 * @return
	 */
	public static String changeNumberToWan(int number, int scale){
		if(number >= 10000){
			double wan = number/10000d;
			BigDecimal b = new BigDecimal(wan);
			return b.setScale(scale, BigDecimal.ROUND_HALF_UP).doubleValue()+"w";
		}else{
			return String.valueOf(number);
		}
	}
	
	/**
	 * 
	 * @Description: 获取一个随机数并返回
	 * @return int
	 * @throws
	 */
	public static int getRandom(int size){
		Random random=new Random();
		int result=random.nextInt(size);
		return result;
	}

}
