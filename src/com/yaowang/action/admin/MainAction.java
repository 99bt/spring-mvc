package com.yaowang.action.admin;

import java.io.IOException;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;

import com.yaowang.common.action.BaseDataAction;
import com.yaowang.service.AdminRoleService;
/**
 * 后台框架
 * @author shenl
 *
 */
public class MainAction extends BaseDataAction{
	private static final long serialVersionUID = 1L;
	
	private Map<String, Object[]> models;
	
	@Resource
	private AdminRoleService adminRoleService;
	
	/**
	 * 左侧
	 * @return
	 */
	public String left(){
		models = adminRoleService.getAdminRoleMapById(getAdminLogin().getId());
		return SUCCESS;
	}
	
	/**
	 * 右侧
	 * @return
	 * @throws IOException 
	 */
	public String nav() throws IOException{
		String index = adminRoleService.getIndexUrl(getAdminLogin().getId());
		if (StringUtils.isEmpty(index)) {
			return SUCCESS;
		}else {
			if (index.startsWith("/")) {
				index  = getContextPath() + index;
			}
			getResponse().sendRedirect(index);
			return null;
		}
	}

	public Map<String, Object[]> getModels() {
		return models;
	}
}
