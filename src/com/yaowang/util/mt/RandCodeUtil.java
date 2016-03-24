package com.yaowang.util.mt;

import java.util.Date;
import java.util.Random;

/**
 * 随机数生成
 * @author shenl
 *
 */
public class RandCodeUtil {
	/**
	 * 生成随机数
	 * @param size
	 * @return
	 */
	public static final Integer getRandCode(int n){
		Random random = new Random();
		int numb = (int)Math.pow(10, n - 1);
		int rand = (int)Math.pow(10, n) - numb - 1;
		
		return random.nextInt(rand) + numb;
	}
	
	public static void main(String[] args) {
		for (int i = 0; i < 10; i++) {
			System.out.println(getStringRandom(8));
		}
		for (int j = 0; j < 10; j++) {
			System.out.println(getRandCode(8));
		}
	}
	
	/**
	 * 生成随机数字和字母
	 * @param length
	 * @return
	 */
	public static final String getStringRandom(int length) {  
        
        String val = "";  
        Random random = new Random();  
          
        //参数length，表示生成几位随机数  
        for(int i = 0; i < length; i++) {  
              
            String charOrNum = random.nextInt(2) % 2 == 0 ? "char" : "num";  
            //输出字母还是数字  
            if( "char".equalsIgnoreCase(charOrNum) ) {  
                //输出是大写字母还是小写字母  
               // int temp = random.nextInt(2) % 2 == 0 ? 65 : 97;  
                val += (char)(random.nextInt(26) + 97);  
            } else if( "num".equalsIgnoreCase(charOrNum) ) {  
                val += String.valueOf(random.nextInt(10));  
            }  
        }  
        return val;  
    }

    /*
   * 获得在a到b之间的n个随机数
  */
    public static int[] getRandom(int a, int b, int n) {
        if (a > b) {
            int temp = a;
            a = b;
            b = temp;
        }

        Date dt = new Date();
        Random random = new Random(dt.getSeconds());
        int res[] = new int[n];
        for (int i = 0; i < n; i++) {
            res[i] = (int) (random.nextDouble() * (Math.abs(b - a) + 1)) + a;
        }
        return res;
    }
}
