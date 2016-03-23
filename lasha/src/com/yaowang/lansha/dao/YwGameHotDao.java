package com.yaowang.lansha.dao;

import java.util.List;
import java.util.Map;

import com.yaowang.common.dao.PageDto;
import com.yaowang.lansha.entity.YwGameHot;

/**
 * 热门推荐游戏 
 * @author 
 * 
 */
public interface YwGameHotDao{
	/**
	 * 新增热门推荐游戏
	 * @param ywGameHot
	 * @return
	 */
	public YwGameHot save(YwGameHot entity);
	
	/**
	 * 根据ids数组删除热门推荐游戏
	 * @param ids
	 * @return
	 */
	public Integer delete(String[] ids);
	
	/**
	 * 更新热门推荐游戏
	 * @param ywGameHot
	 * @return
	 */
	public Integer update(YwGameHot entity);
	
	/**
	 * 根据id获取热门推荐游戏
	 * @param id
	 * @return
	 */
	public YwGameHot getYwGameHotById(String id);
		
	/**
	 * 根据ids数组查询热门推荐游戏map
	 * @param ids
	 * @return
	 */
	public Map<String, YwGameHot> getYwGameHotMapByIds(String[] ids);
	
	/**
	 * 获取热门推荐游戏列表
	 * @return
	 */
	public List<YwGameHot> getYwGameHotList(YwGameHot entity);
		
	/**
	 * 分页获取热门推荐游戏列表
	 * @param page
	 * @return
	 */
	public List<YwGameHot> getYwGameHotPage(YwGameHot entity, PageDto page);
	/**
	 * 根据gameids数组删除热门推荐游戏数据
	 * @param ids
	 * @return
	 */
	public Integer deleteByGameId(String... gameId);
	/**
	 * 根据gameId获取热门推荐游戏
	 * @param gameId
	 * @return
	 */
	public YwGameHot getYwGameHotByGameId(String gameId);
	/**
	 * 根据gameIds数组查询热门推荐游戏map
	 * @param gameIds
	 * @return
	 */
	public Map<String, YwGameHot> getYwGameHotMapByGameIds(String[] gameIds);
	/**
	 * 更新排序号
	 * @param ywGameHot
	 * @return
	 */
	public Integer updateOrderId(String id, Integer orderId);

	/**
	 * 获取推荐游戏id列表,按order_id排序
	 * @param limit
	 * @return
	 */
	public List<String> getHotGameIdPage(int limit);

    List<Map<String, Object>> getHotGame(int limit);
}
