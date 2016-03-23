package com.yaowang.lansha.dao;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.yaowang.common.dao.PageDto;
import com.yaowang.lansha.entity.ActivityGiftStock;

/**
 * 活动礼品库存 
 * @author 
 * 
 */
public interface ActivityGiftStockDao{
	/**
	 * 新增活动礼品库存
	 * @param activityGiftStock
	 * @return
	 */
	public ActivityGiftStock save(ActivityGiftStock entity);
	
	/**
	 * 根据ids数组删除活动礼品库存
	 * @param ids
	 * @return
	 */
	public Integer delete(String[] ids);
	
	/**
	 * 更新活动礼品库存
	 * @param activityGiftStock
	 * @return
	 */
	public Integer update(ActivityGiftStock entity);
	
	/**
	 * 礼物发放审核结果更新
	 * @param activityGiftStock
	 * @return
	 */
	public Integer updateForDoGift(ActivityGiftStock entity);
	
	/**
	 * 批量更新奖品状态
	 * @param activityGiftStock
	 * @return
	 */
	public Integer updateForDoBatchGifts(String[] ids,ActivityGiftStock entity);
	
	/**
	 * 根据id获取活动礼品库存
	 * @param id
	 * @return
	 */
	public ActivityGiftStock getActivityGiftStockById(String id);
		
	/**
	 * 根据ids数组查询活动礼品库存map
	 * @param ids
	 * @return
	 */
	public Map<String, ActivityGiftStock> getActivityGiftStockMapByIds(String[] ids);
	/**
	 * 获取活动首页礼品库存列表
	 * @return
	 */
	public List<ActivityGiftStock> getIndexActivityGiftStockList(ActivityGiftStock entity);
	
	/**
	 * 获取活动礼品库存列表
	 * @return
	 */
	public List<ActivityGiftStock> getActivityGiftStockList(ActivityGiftStock entity);
	/**
	 * 获取用户中奖纪录数
	 * @return
	 */
	public Integer getActivityGiftStockNumber(ActivityGiftStock entity ,Date startTime, Date endTime);
		
	/**
	 * 分页获取活动礼品库存列表
	 * @param page
	 * @return
	 */
	public List<ActivityGiftStock> getActivityGiftStockPage(ActivityGiftStock entity, PageDto page);
	
	public List<ActivityGiftStock> getActivityGiftStockPage(ActivityGiftStock entity, Date startTime, Date endTime,PageDto page);
	public List<ActivityGiftStock> getActivityGiftStockPages(ActivityGiftStock entity, Date startTime, Date endTime,PageDto page);

    public Integer updateForUserIds(String[] ids,ActivityGiftStock entity);
	
}
