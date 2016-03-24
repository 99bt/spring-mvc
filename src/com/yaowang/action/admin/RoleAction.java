package com.yaowang.action.admin;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;

import com.yaowang.common.action.BaseDataAction;
import com.yaowang.entity.AdminRole;
import com.yaowang.entity.AdminRoleModel;
import com.yaowang.service.AdminRoleModelService;
import com.yaowang.service.AdminRoleService;
import com.yaowang.service.SysModelService;
/**
 * 后台角色管理
 * @author Administrator
 *
 */
public class RoleAction extends BaseDataAction {
	private static final long serialVersionUID = 1L;
	
	private AdminRole entity;
	private Map<String, Object[]> data;

	@Resource
	private AdminRoleService adminRoleService;
	@Resource
	private AdminRoleModelService adminRoleModelService;
	@Resource
	private SysModelService sysModelService;
	
	/**
	 * 角色列表
	 * @return
	 */
	public String role(){
		list = adminRoleService.getAdminRoleList();
		return SUCCESS;
	}
	/**
	 * 删除
	 * @return
	 */
	public String delete(){
		adminRoleService.delete(ids);
		list = adminRoleService.getAdminRoleList();
		addActionMessage("删除成功！");
		return SUCCESS;
	}
	/**
	 * 查看角色
	 * @return
	 */
	public String view(){
		if (StringUtils.isEmpty(id)) {
			entity = new AdminRole();
			list = new ArrayList<String>(0);
		}else {
			entity = adminRoleService.getAdminRoleById(id);
			// 查询角色模块
			list = adminRoleModelService.getAdminRoleModelList(id);
		}
		//获取模块
		data = sysModelService.getSysModelMap();
		
		return SUCCESS;
	}
	/**
	 * 修改角色
	 * @return
	 */
	public String update(){
		if (StringUtils.isEmpty(entity.getId())) {
			entity.setCreateTime(getNow());
			entity.setIsSys(false);
			adminRoleService.save(entity);
		}else {
			adminRoleService.update(entity);
		}
		//模块保存
		adminRoleModelService.deleteByRoleId(entity.getId());
		if (ArrayUtils.isNotEmpty(ids)) {
			list = Arrays.asList(ids);
			for (String modeId : ids) {
				AdminRoleModel model = new AdminRoleModel();
				model.setModelId(modeId);
				model.setRoleId(entity.getId());
				adminRoleModelService.save(model);
			}
		}else {
			list = new ArrayList<String>(0);
		}
		
		//查询模块
		data = sysModelService.getSysModelMap();
		addActionMessage("保存成功！");
		return SUCCESS;
	}

	public AdminRole getEntity() {
		return entity;
	}

	public void setEntity(AdminRole entity) {
		this.entity = entity;
	}
	public Map<String, Object[]> getData() {
		return data;
	}
}
