package com.yaowang.action.util;

import java.io.IOException;

import org.apache.commons.lang.StringUtils;

import com.yaowang.common.action.BaseDataAction;
import com.yaowang.util.img.ValidateCodeUtil;
/**
 * 验证码
 * @author shenl
 *
 */
public class ValidateAction extends BaseDataAction {
	private static final long serialVersionUID = 1L;

	/**
	 * 验证码
	 * @throws IOException 
	 */
	public void verification() throws IOException{
		ValidateCodeUtil.build();
	}
	
	/**
	 * 首页地址
	 * @throws IOException 
	 */
	public void index() throws IOException{
		String index = sysOptionService.getSysOptionByIniid("SYS.INDEX").getNowvalue();
		if (StringUtils.isEmpty(index)) {
			index = "/admin/login.html";
		}
		getResponse().sendRedirect(getContextPath() + index);
	}
}
