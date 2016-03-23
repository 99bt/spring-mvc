package com.yaowang.lansha.dao;

import java.util.List;
import java.util.Map;

import com.yaowang.common.dao.PageDto;
import com.yaowang.lansha.entity.ActivityUserStock;

/**
 * 用户抽奖机会 
 * @author 
 * 
 */
public interface ActivityUserStockDao{
	/**
	 * 新增用户抽奖机会
	 * @param activityUserStock
	 * @return
	 */
	public ActivityUserStock save(ActivityUserStock entity);
	
	/**
	 * 根据ids数组删除用户抽奖机会
	 * @param ids
	 * @return
	 */
	public Integer delete(String[] ids);
	
	/**
	 * 更新用户抽奖机会
	 * @param activityUserStock
	 * @return
	 */
	public Integer update(ActivityUserStock entity);
	
	/**
	 * 更新用户抽奖机会
	 * @param activityUser
	 * @return
	 */
	public Integer updateUserLottery(ActivityUserStock entity);
	
	/**
	 * 根据id获取用户抽奖机会
	 * @param id
	 * @return
	 */
	public ActivityUserStock getActivityUserStockById(String id);
		
	/**
	 * 根据ids数组查询用户抽奖机会map
	 * @param ids
	 * @return
	 */
	public Map<String, ActivityUserStock> getActivityUserStockMapByIds(String[] ids);
	
	/**
	 * 获取用户抽奖机会列表
	 * @return
	 */
	public List<ActivityUserStock> getActivityUserStockList(ActivityUserStock entity);
		
	/**
	 * 分页获取用户抽奖机会列表
	 * @param page
	 * @return
	 */
	public List<ActivityUserStock> getActivityUserStockPage(ActivityUserStock entity, PageDto page);
	/**
	 * 增加用户抽奖机会
	 * @param add
	 * @param userId
	 * @param itemId
	 * @return
	 * @creationDate. 2016-1-11 上午11:33:03
	 */
	public Integer updateUserStock(Integer add, String userId, String itemId);
	
}
