package com.yaowang.dao;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.yaowang.common.dao.PageDto;
import com.yaowang.entity.LogMt;

/**
 * log_mt 
 * @author 
 * 
 */
public interface LogMtDao{
	/**
	 * 新增log_mt
	 * @param logMt
	 * @return
	 */
	public LogMt save(LogMt logMt);
	
	/**
	 * 根据ids数组删除log_mt
	 * @param ids
	 * @return
	 */
	public Integer delete(Integer[] ids);
	
	/**
	 * 更新log_mt
	 * @param logMt
	 * @return
	 */
	public Integer update(LogMt logMt);

	/**
	 * 更新状态
	 * @param logMt
	 * @return
	 */
	public Integer updateStatus(LogMt logMt);
	
	/**
	 * 根据id获取log_mt
	 * @param id
	 * @return
	 */
	public LogMt getLogMtById(Integer id);
		
	/**
	 * 根据ids数组查询log_mtmap
	 * @param ids
	 * @return
	 */
	public Map<Integer, LogMt> getLogMtMapByIds(Integer[] ids);
	
	/**
	 * 获取log_mt列表
	 * @return
	 */
	public List<LogMt> getLogMtList();
		
	/**
	 * 分页获取log_mt列表
	 * @param page
	 * @return
	 */
	public List<LogMt> getLogMtPage(LogMt mt, Date startTime, Date endTime, PageDto page);
	/**
	 * 取向该手机号发送的最新一条有效验证码
	 * @param telphone
	 * @return
	 */
	public LogMt getLogMtByTelphone(String telphone);

	/**
	 * 获取发送短信量
	 * @param entity
	 * @param startTime
	 * @param endTime
	 * @return
	 */
	public Integer getLogCount(LogMt entity, Date startTime, Date endTime);
}
