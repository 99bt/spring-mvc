package com.yaowang.dao;

import java.util.List;
import java.util.Map;

import com.yaowang.common.dao.PageDto;
import com.yaowang.entity.AdminUser;

/**
 * 管理员用户表 
 * @author 
 * 
 */
public interface AdminUserDao{
	/**
	 * 新增管理员用户表
	 * @param adminUser
	 * @return
	 */
	public AdminUser save(AdminUser adminUser);
	
	/**
	 * 根据ids数组删除管理员用户表
	 * @param ids
	 * @return
	 */
	public Integer delete(String[] ids);
	
	/**
	 * 更新管理员用户表
	 * @param adminUser
	 * @return
	 */
	public Integer update(AdminUser adminUser);

	/**
	 * 修改密码
	 * @param user
	 * @return
	 */
	public Integer updatePwd(AdminUser user);
	
	/**
	 * 根据id获取管理员用户表
	 * @param id
	 * @return
	 */
	public AdminUser getAdminUserById(String id);
	
	/**
	 * 根据用户名查找
	 * @param userName
	 * @return
	 */
	public AdminUser getAdminUserByUserName(String userName);
	
	/**
	 * 根据ids数组查询管理员用户表map
	 * @param ids
	 * @return
	 */
	public Map<String, AdminUser> getAdminUserMapByIds(String[] ids);
		
	/**
	 * 根据ids数组查询管理员用户表map
	 * @param ids
	 * @return
	 */
	public Map<String, String> getAdminUserNameMapByIds(String[] ids);
	
	/**
	 * 获取管理员用户表列表
	 * @param id TODO
	 * @return
	 */
	public List<AdminUser> getAdminUserList(String id);
		
	/**
	 * 分页获取管理员用户表列表
	 * @param page
	 * @return
	 */
	public List<AdminUser> getAdminUserPage(PageDto page);

	public List<AdminUser> getAdminUserList();
	/**
	 * 根据ChannelID删除管理员用户
	 * @author zhanL
	 * @creationDate. 2015-8-3 下午5:57:56 
	 * @param ids
	 */
	public void deleteByChannelId(String[] ids);
	
	public Integer updatePwd(String[] ids, String defaultPwd);

	public List<AdminUser> getAdminUserListByName(AdminUser entity);
}
