package com.yaowang.lansha.util;

import java.util.regex.Pattern;

import org.apache.commons.lang.StringUtils;

import com.yaowang.service.impl.SysOptionServiceImpl;

public class LanshaCommonFunctions {
	public static boolean matchNickKeywords(String nickName){
		String cacheKeywords = SysOptionServiceImpl.getValue("LANSHA.NICKNAME.KEYWORD");
		if(StringUtils.isNotBlank(cacheKeywords)){
			Pattern patternNickName = Pattern.compile("\\s+");//过滤昵称中的空格
			String newStr = patternNickName.matcher(nickName).replaceAll("");
			String[] keywords = cacheKeywords.split(",");
			for(String keyword : keywords){
				Pattern p = Pattern.compile(keyword,Pattern.CASE_INSENSITIVE);
				if(p.matcher(newStr).matches()){//能正确匹配则返回true
					return true;
				}
			}
		}
		return false;
	}
	
	//判断此字符串大小
	public static int judgeCharsLength(String str) {
		int index = 0;
		
		for(int a =0;a<str.length();a++){
			if(str.substring(a, a+1).matches("[\u4e00-\u9fa5]")){
				index+=2;
			}else{
				index+=1;
			}
		}
		return index;
	}
}
