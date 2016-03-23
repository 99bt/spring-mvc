package com.yaowang.lansha.dao;

import java.util.List;
import java.util.Map;

import com.yaowang.common.dao.PageDto;
import com.yaowang.lansha.entity.YwUserRoomLog;

/**
 * 直播间人数日志 
 * @author 
 * 
 */
public interface YwUserRoomLogDao{
	/**
	 * 新增直播间人数日志
	 * @param ywUserRoomLog
	 * @return
	 */
	public YwUserRoomLog save(YwUserRoomLog entity);
	
	/**
	 * 根据ids数组删除直播间人数日志
	 * @param ids
	 * @return
	 */
	public Integer delete(String[] ids);
	
	/**
	 * 更新直播间人数日志
	 * @param ywUserRoomLog
	 * @return
	 */
	public Integer update(YwUserRoomLog entity);
	
	/**
	 * 根据id获取直播间人数日志
	 * @param id
	 * @return
	 */
	public YwUserRoomLog getYwUserRoomLogById(String id);
		
	/**
	 * 根据ids数组查询直播间人数日志map
	 * @param ids
	 * @return
	 */
	public Map<String, YwUserRoomLog> getYwUserRoomLogMapByIds(String[] ids);
	
	/**
	 * 获取直播间人数日志列表
	 * @return
	 */
	public List<YwUserRoomLog> getYwUserRoomLogList(YwUserRoomLog entity);
		
	/**
	 * 分页获取直播间人数日志列表
	 * @param page
	 * @return
	 */
	public List<YwUserRoomLog> getYwUserRoomLogPage(YwUserRoomLog entity, PageDto page);
	/**
	 * @Title: report
	 * @Description: 统计主播时间
	 * @param entity
	 * @return
	 */
	public List<YwUserRoomLog> report(YwUserRoomLog entity);
	
}
