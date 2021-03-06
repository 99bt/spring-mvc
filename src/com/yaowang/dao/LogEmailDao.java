package com.yaowang.dao;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.yaowang.common.dao.PageDto;
import com.yaowang.entity.LogEmail;

/**
 * log_email 
 * @author 
 * 
 */
public interface LogEmailDao{
	/**
	 * 新增log_email
	 * @param logEmail
	 * @return
	 */
	public LogEmail save(LogEmail logEmail);
	
	/**
	 * 根据ids数组删除log_email
	 * @param ids
	 * @return
	 */
	public Integer delete(Integer[] ids);
	
	/**
	 * 更新log_email
	 * @param logEmail
	 * @return
	 */
	public Integer update(LogEmail logEmail);
	
	/**
	 * 根据id获取log_email
	 * @param id
	 * @return
	 */
	public LogEmail getLogEmailById(Integer id);
		
	/**
	 * 根据ids数组查询log_emailmap
	 * @param ids
	 * @return
	 */
	public Map<Integer, LogEmail> getLogEmailMapByIds(Integer[] ids);
	
	/**
	 * 获取log_email列表
	 * @return
	 */
	public List<LogEmail> getLogEmailList();
		
	/**
	 * 分页获取log_email列表
	 * @param page
	 * @return
	 */
	public List<LogEmail> getLogEmailPage(Date startTime, Date endTime, PageDto page);

	/**
	 * 根据email、title修改状态
	 * @param email
	 * @param title
	 */
	public void updateLogEmailByCondition(String email, String title);
	
}
