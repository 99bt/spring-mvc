package com.yaowang.action.admin;

import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;

import com.yaowang.common.action.BasePageAction;
import com.yaowang.entity.AdminToRole;
import com.yaowang.entity.AdminUser;
import com.yaowang.service.AdminRoleService;
import com.yaowang.service.AdminToRoleService;
import com.yaowang.service.AdminUserService;
import com.yaowang.util.MD5;
/**
 * 管理员用户
 * @author shenl
 *
 */
public class AdminUserAction extends BasePageAction {
	private static final long serialVersionUID = 1L;

	private AdminUser entity;
	private String roleId;
	
	@Resource
	private AdminUserService adminUserService;
	@Resource
	private AdminRoleService adminRoleService;
	@Resource
	private AdminToRoleService adminToRoleService;
	
	/**
	 * 管理员用户
	 * @return
	 */
	public String adminUser(){
		list = adminUserService.getAdminUserListByName(entity);
		return SUCCESS;
	}
	/**
	 * 删除
	 * @return
	 */
	public String delete(){
		adminUserService.delete(ids);
		list = adminUserService.getAdminUserList(null);
		addActionMessage("保存成功");
		return SUCCESS;
	}
	/**
	 * 查看
	 * @return
	 */
	public String view(){
		if (StringUtils.isEmpty(id)) {
			entity = new AdminUser();
		}else {
			entity = adminUserService.getAdminUserById(id);
			//用户角色
			List<String> roles = adminToRoleService.getAdminToRoleList(id);
			if (!roles.isEmpty()) {
				roleId = roles.get(0);
			}
		}
		//角色
		list = adminRoleService.getAdminRoleList();
		
		return SUCCESS;
	}
	public String resetPwd(){
		adminUserService.updatePwd(ids);
		adminUser();
		addActionMessage("密码已重置");
		return SUCCESS;
	}
	/**
	 * 保存
	 * @return
	 */
	public String save(){
		if (StringUtils.isEmpty(entity.getId())) {
			//判断用户名是否重复
			AdminUser temp = adminUserService.getAdminUserByUserName(entity.getUsername());
			if (temp != null) {
				addActionError("此用户名已存在，请使用其他");
				//获取角色
				list = adminRoleService.getAdminRoleList();
				
				return SUCCESS;
			}
			entity.setCreateTime(getNow());
			entity.setPassword(MD5.encrypt("123456"));
			adminUserService.save(entity);
		}else {
			adminUserService.update(entity);
			//管理员角色
			adminToRoleService.deleteByUserId(entity.getId());
		}
		//保存角色
		AdminToRole role = new AdminToRole();
		role.setAdminId(entity.getId());
		role.setCreateTime(getNow());
		role.setRoleId(roleId);
		adminToRoleService.save(role);
		
		//获取角色
		list = adminRoleService.getAdminRoleList();
		
		addActionMessage("保存成功！");
		return SUCCESS;
	}
	
	public AdminUser getEntity() {
		return entity;
	}
	public void setEntity(AdminUser entity) {
		this.entity = entity;
	}
	public String getRoleId() {
		return roleId;
	}
	public void setRoleId(String roleId) {
		this.roleId = roleId;
	}
}
