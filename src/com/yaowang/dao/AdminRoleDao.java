package com.yaowang.dao;

import java.util.List;

import com.yaowang.common.dao.PageDto;
import com.yaowang.entity.AdminRole;

/**
 * 后台角色 
 * @author 
 * 
 */
public interface AdminRoleDao{
	/**
	 * 新增后台角色
	 * @param adminRole
	 * @return
	 */
	public AdminRole save(AdminRole adminRole);
	
	/**
	 * 根据ids数组删除后台角色
	 * @param ids
	 * @return
	 */
	public Integer delete(String[] ids);
	
	/**
	 * 更新后台角色
	 * @param adminRole
	 * @return
	 */
	public Integer update(AdminRole adminRole);
	
	/**
	 * 根据id获取后台角色
	 * @param id
	 * @return
	 */
	public AdminRole getAdminRoleById(String id);
		
	/**
	 * 根据ids数组查询后台角色map
	 * @param ids
	 * @return
	 */
//	public Map<String, AdminRole> getAdminRoleMapByIds(String[] ids);
	
	/**
	 * 获取后台角色列表
	 * @return
	 */
	public List<AdminRole> getAdminRoleList();
		
	/**
	 * 分页获取后台角色列表
	 * @param page
	 * @return
	 */
	public List<AdminRole> getAdminRolePage(PageDto page);
	/**
	 * 根据状态获取角色
	 * @param string
	 * @return
	 */
	public List<String> getAdminRole(String state);
	/**
	 * 根据角色名获取后台角色
	 * @param roleName
	 * @return
	 */
	public List<AdminRole> getAdminRoleByName(String roleName);
}
