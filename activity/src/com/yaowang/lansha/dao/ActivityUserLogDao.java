package com.yaowang.lansha.dao;

import java.util.List;
import java.util.Map;

import com.yaowang.common.dao.PageDto;
import com.yaowang.lansha.entity.ActivityUserLog;

/**
 * 抽奖机会抽取日志 
 * @author 
 * 
 */
public interface ActivityUserLogDao{
	/**
	 * 新增抽奖机会抽取日志
	 * @param activityUserLog
	 * @return
	 */
	public ActivityUserLog save(ActivityUserLog entity);
	
	/**
	 * 根据ids数组删除抽奖机会抽取日志
	 * @param ids
	 * @return
	 */
	public Integer delete(String[] ids);
	
	/**
	 * 更新抽奖机会抽取日志
	 * @param activityUserLog
	 * @return
	 */
	public Integer update(ActivityUserLog entity);
	
	/**
	 * 根据id获取抽奖机会抽取日志
	 * @param id
	 * @return
	 */
	public ActivityUserLog getActivityUserLogById(String id);
		
	/**
	 * 根据ids数组查询抽奖机会抽取日志map
	 * @param ids
	 * @return
	 */
	public Map<String, ActivityUserLog> getActivityUserLogMapByIds(String[] ids);
	
	/**
	 * 获取抽奖机会抽取日志列表
	 * @return
	 */
	public List<ActivityUserLog> getActivityUserLogList(ActivityUserLog entity);
		
	/**
	 * 分页获取抽奖机会抽取日志列表
	 * @param page
	 * @return
	 */
	public List<ActivityUserLog> getActivityUserLogPage(ActivityUserLog entity, PageDto page);
	
}
