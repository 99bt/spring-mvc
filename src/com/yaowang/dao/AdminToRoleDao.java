package com.yaowang.dao;

import java.util.List;

import com.yaowang.common.dao.PageDto;
import com.yaowang.entity.AdminToRole;

/**
 * 管理员对应角色表 
 * @author 
 * 
 */
public interface AdminToRoleDao{
	/**
	 * 新增管理员对应角色表
	 * @param adminToRole
	 * @return
	 */
	public AdminToRole save(AdminToRole adminToRole);
	
	/**
	 * 根据ids数组删除管理员对应角色表
	 * @param ids
	 * @return
	 */
	public Integer delete(String[] ids);

	/**
	 * 根据用户id删除角色
	 * @param userId
	 * @return
	 */
	public Integer deleteByUserId(String userId);
	
	/**
	 * 更新管理员对应角色表
	 * @param adminToRole
	 * @return
	 */
	public Integer update(AdminToRole adminToRole);
	
	/**
	 * 根据id获取管理员对应角色表
	 * @param id
	 * @return
	 */
	public AdminToRole getAdminToRoleById(String id);
		
	/**
	 * 根据ids数组查询管理员对应角色表map
	 * @param ids
	 * @return
	 */
//	public Map<String, AdminToRole> getAdminToRoleMapByIds(String[] ids);
	
	/**
	 * 获取管理员对应角色表列表
	 * @return
	 */
	public List<String> getAdminToRoleListByRoleId(String roleId);
		
	/**
	 * 分页获取管理员对应角色表列表
	 * @param page
	 * @return
	 */
	public List<AdminToRole> getAdminToRolePage(PageDto page);
	
	/**
	 * 根据用户id查询角色
	 * @param userId
	 * @return
	 */
	public List<String> getAdminToRoleList(String userId);
	
}
