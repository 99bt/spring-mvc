package com.yaowang.lansha.dao;

import java.util.List;
import java.util.Map;

import com.yaowang.common.dao.PageDto;
import com.yaowang.lansha.entity.ActivityItem;

/**
 * 活动 
 * @author 
 * 
 */
public interface ActivityItemDao{
	/**
	 * 新增活动
	 * @param activityItem
	 * @return
	 */
	public ActivityItem save(ActivityItem entity);
	
	/**
	 * 根据ids数组删除活动
	 * @param ids
	 * @return
	 */
	public Integer delete(String[] ids);
	
	/**
	 * 更新活动
	 * @param activityItem
	 * @return
	 */
	public Integer update(ActivityItem entity);
	
	/**
	 * 根据id获取活动
	 * @param id
	 * @return
	 */
	public ActivityItem getActivityItemById(String id);
		
	/**
	 * 根据ids数组查询活动map
	 * @param ids
	 * @return
	 */
	public Map<String, ActivityItem> getActivityItemMapByIds(String[] ids);
	
	/**
	 * 获取活动列表
	 * @return
	 */
	public List<ActivityItem> getActivityItemList(ActivityItem entity);
		
	/**
	 * 分页获取活动列表
	 * @param page
	 * @return
	 */
	public List<ActivityItem> getActivityItemPage(ActivityItem entity, PageDto page);

	/**
	 * @Description: 根据id修改状态
	 * @param id
	 * @param status
	 */
	public void updateStatusById(String id, String status);
	
	/**
	 * 根据name获取活动
	 * @param name
	 * @return
	 */
	public ActivityItem getActivityItemByName(String name);
	
}
