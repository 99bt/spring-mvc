package com.yaowang.lansha.dao;

import java.util.List;
import java.util.Map;

import com.yaowang.common.dao.PageDto;
import com.yaowang.lansha.entity.LanshaUserGiftStock;

/**
 * 礼物记录 
 * @author 
 * 
 */
public interface LanshaUserGiftStockDao{
	/**
	 * 新增礼物记录
	 * @param lanshaUserGiftStock
	 * @return
	 */
	public LanshaUserGiftStock save(LanshaUserGiftStock entity);
	
	/**
	 * 根据ids数组删除礼物记录
	 * @param ids
	 * @return
	 */
	public Integer delete(String[] ids);
	
	/**
	 * 更新礼物记录
	 * @param lanshaUserGiftStock
	 * @return
	 */
	public Integer update(LanshaUserGiftStock entity);
	
	/**
	 * 更新抽奖礼物记录
	 * @param lanshaUserGiftStock
	 * @return
	 */
	public Integer updateLottery(LanshaUserGiftStock entity);
	
	/**
	 * 根据id获取礼物记录
	 * @param id
	 * @return
	 */
	public LanshaUserGiftStock getLanshaUserGiftStockById(String id);
		
	/**
	 * 根据ids数组查询礼物记录map
	 * @param ids
	 * @return
	 */
	public Map<String, LanshaUserGiftStock> getLanshaUserGiftStockMapByIds(String[] ids);
	
	/**
	 * 获取礼物记录列表
	 * @return
	 */
	public List<LanshaUserGiftStock> getLanshaUserGiftStockList(LanshaUserGiftStock entity);
		
	/**
	 * 分页获取礼物记录列表
	 * @param page
	 * @return
	 */
	public List<LanshaUserGiftStock> getLanshaUserGiftStockPage(LanshaUserGiftStock entity, PageDto page);
	/**
	 * @Title: updateReduceStock
	 * @Description: 减少用户礼物库存
	 * @param id
	 * @param stock
	 * @return
	 */
	public Integer updateReduceStock(String id, Integer stock);
	/**
	 * 根据ids数组查询礼物记录map
	 * @param userIds
	 * @return
	 */
	public Map<String, Integer> getStockMapByUserIds(String[] userIds);

	/**
	 * 更新某一礼物的所有库存
	 * @param entity
	 * @return
	 */
	Integer updateStockByGiftId(LanshaUserGiftStock entity);
	
}
