package com.yaowang.dao;

import java.util.List;

import com.yaowang.common.dao.PageDto;
import com.yaowang.entity.AdminRoleModel;

/**
 * 角色对应权限表 
 * @author 
 * 
 */
public interface AdminRoleModelDao{
	/**
	 * 新增角色对应权限表
	 * @param adminRoleModel
	 * @return
	 */
	public AdminRoleModel save(AdminRoleModel adminRoleModel);
	
	/**
	 * 根据ids数组删除角色对应权限表
	 * @param ids
	 * @return
	 */
	public Integer delete(String[] ids);
	/**
	 * 删除对象模块
	 * @param id
	 * @return
	 */
	public Integer deleteByRoleId(String id);
	
	/**
	 * 更新角色对应权限表
	 * @param adminRoleModel
	 * @return
	 */
	public Integer update(AdminRoleModel adminRoleModel);
	
	/**
	 * 根据id获取角色对应权限表
	 * @param id
	 * @return
	 */
	public AdminRoleModel getAdminRoleModelById(String id);
		
	/**
	 * 根据ids数组查询角色对应权限表map
	 * @param ids
	 * @return
	 */
//	public Map<String, AdminRoleModel> getAdminRoleModelMapByIds(String[] ids);
	
	/**
	 * 获取角色对应权限表列表
	 * @return
	 */
	public List<String> getAdminRoleModelList(String[] roleId);
		
	/**
	 * 分页获取角色对应权限表列表
	 * @param page
	 * @return
	 */
	public List<AdminRoleModel> getAdminRoleModelPage(PageDto page);
	
}
