package com.yaowang.lansha.dao;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.yaowang.common.dao.PageDto;
import com.yaowang.lansha.entity.YwUserRoomHistory;

/**
 * 房间浏览记录
 * @author 
 * 
 */
public interface YwUserRoomHistoryDao{
	/**
	 * 新增房间收藏表
	 * @param ywUserRoomHistory
	 * @return
	 */
	public YwUserRoomHistory save(YwUserRoomHistory entity);
	
	/**
	 * 根据ids数组删除房间收藏表
	 * @param ids
	 * @return
	 */
	public Integer delete(String[] ids);
	
	/**
	 * 更新房间收藏表
	 * @param ywUserRoomHistory
	 * @return
	 */
	public Integer update(YwUserRoomHistory entity);
	
	/**
	 * 根据id获取房间收藏表
	 * @param id
	 * @return
	 */
	public YwUserRoomHistory getYwUserRoomHistoryById(String id);
		
	/**
	 * 根据ids数组查询房间收藏表map
	 * @param ids
	 * @return
	 */
	public Map<String, YwUserRoomHistory> getYwUserRoomHistoryMapByIds(String[] ids);
	
	/**
	 * 获取房间收藏表列表
	 * @return
	 */
	public List<YwUserRoomHistory> getYwUserRoomHistoryList(YwUserRoomHistory entity);
		
	/**
	 * 分页获取房间收藏表列表
	 * @param page
	 * @return
	 */
	public List<YwUserRoomHistory> getYwUserRoomHistoryPage(YwUserRoomHistory entity, Integer[] status, PageDto page, Date startTime, Date endTime);
	/**
	 * 获取总数
	 * @param entity
	 * @param status
	 * @param page
	 * @param startTime
	 * @param endTime
	 * @return
	 */
	public Integer getYwUserRoomHistoryPageCount(YwUserRoomHistory entity, Integer[] status, Date startTime, Date endTime);

	/**
	 * 更新领取蓝鲨币
	 * @param entity
	 * @return
	 */
	public Integer updateTimeLength(YwUserRoomHistory entity);
	/**
	 * 获取历史记录中的房间id
	 * @param room
	 * @return
	 * @creationDate. 2015-12-30 下午8:51:52
	 */
	public List<String> getRoomIdPage(YwUserRoomHistory room, PageDto page);
	/**
	 * 获取房间浏览记录总数
	 * @param entity
	 * @param startTime
	 * @param endTime
	 * @return
	 * @creationDate. 2016-1-13 下午4:20:05
	 */
	public Integer getYwUserRoomHistoryCount(YwUserRoomHistory entity, Date startTime, Date endTime);
	/**
	 * 获取房间浏览总人数
	 * @param history
	 * @return
	 */
	public Integer getSumUserNumber(YwUserRoomHistory history);
}
