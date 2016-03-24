package com.yaowang.util.mt;

import com.yaowang.service.impl.SysOptionServiceImpl;

public class MTUtil {
	public static void refresh(){
		MTQixinUtil.refresh();
		MTLanchuangUtil.refresh();
		MTMaychooUtil.refresh();
	}
	/**
	 * 是否启动短信服务
	 * @return
	 */
	public static Boolean isUse(){
		return "1".equals(SysOptionServiceImpl.getValue("SYS.MT.USE"));
	}
	
	/**
	 * 发送消息
	 * @param content
	 * @param mobile
	 * @return
	 * @throws Exception 
	 */
	public static String sendMt(String content, String mobile) throws Exception{
		if (!MTUtil.isUse()) {
			return null;
		}
		String type = SysOptionServiceImpl.getValue("SYS.MT.TYPE");
		if ("1".equals(type)) {
			return MTQixinUtil.sendMt(content, mobile);
		}else if("2".equals(type)){
			return MTLanchuangUtil.sendMt(content, mobile);
		}else{
			return MTMaychooUtil.sendMt(content, mobile);
		}
	}
}
