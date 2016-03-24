package com.yaowang.dao;

import java.util.Date;
import java.util.List;

import com.yaowang.common.dao.PageDto;
import com.yaowang.entity.LogSystem;

/**
 * log_system 
 * @author 
 * 
 */
public interface LogSystemDao{
	/**
	 * 新增log_system
	 * @param logSystem
	 * @return
	 */
	public LogSystem save(LogSystem logSystem);
	
	/**
	 * 根据ids数组删除log_system
	 * @param ids
	 * @return
	 */
	public Integer delete(String[] ids);
	
	/**
	 * 更新log_system
	 * @param logSystem
	 * @return
	 */
	public Integer update(LogSystem logSystem);
	
	/**
	 * 根据id获取log_system
	 * @param id
	 * @return
	 */
	public LogSystem getLogSystemById(String id);
		
	/**
	 * 根据ids数组查询log_systemmap
	 * @param ids
	 * @return
	 */
//	public Map<String, LogSystem> getLogSystemMapByIds(String[] ids);
	
	/**
	 * 获取log_system列表
	 * @return
	 */
	public List<LogSystem> getLogSystemList();
		
	/**
	 * 分页获取log_system列表
	 * @param page
	 * @return
	 */
	public List<LogSystem> getLogSystemPage(LogSystem entity, Date startTime, Date entTime, PageDto page);
	/**
	 * 查询日志数量
	 * @param entity
	 * @param startTime
	 * @param endTime
	 * @return
	 */
	public Integer getLogSystemNumb(LogSystem entity, Date startTime, Date endTime);
	
}
