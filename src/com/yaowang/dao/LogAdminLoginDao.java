package com.yaowang.dao;

import java.util.Date;
import java.util.List;

import com.yaowang.common.dao.PageDto;
import com.yaowang.entity.LogAdminLogin;

/**
 * 管理员登录日志表 
 * @author 
 * 
 */
public interface LogAdminLoginDao{
	/**
	 * 新增管理员登录日志表
	 * @param logAdminLogin
	 * @return
	 */
	public LogAdminLogin save(LogAdminLogin logAdminLogin);
	
	/**
	 * 根据ids数组删除管理员登录日志表
	 * @param ids
	 * @return
	 */
	public Integer delete(String[] ids);
	
	/**
	 * 更新管理员登录日志表
	 * @param logAdminLogin
	 * @return
	 */
	public Integer update(LogAdminLogin logAdminLogin);
	
	/**
	 * 根据id获取管理员登录日志表
	 * @param id
	 * @return
	 */
	public LogAdminLogin getLogAdminLoginById(String id);
		
	/**
	 * 根据ids数组查询管理员登录日志表map
	 * @param ids
	 * @return
	 */
//	public Map<String, LogAdminLogin> getLogAdminLoginMapByIds(String[] ids);
	
	/**
	 * 获取管理员登录日志表列表
	 * @return
	 */
	public List<LogAdminLogin> getLogAdminLoginList();
		
	/**
	 * 分页获取管理员登录日志表列表
	 * @param page
	 * @return
	 */
	public List<LogAdminLogin> getLogAdminLoginPage(String userId, Date startTime, Date endTime, PageDto page);
	/**
	 * 查询登录数量
	 * @param userId
	 * @param startTime
	 * @param endTime
	 * @param page
	 * @return
	 */
	public Integer getLogAdminLoginNumb(String userId, Date startTime, Date endTime);
	
}
