package com.yaowang.lansha.dao;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.yaowang.common.dao.PageDto;
import com.yaowang.lansha.entity.YwGame;

/**
 * 游戏表 
 * @author 
 * 
 */
public interface YwGameDao{
	/**
	 * 新增游戏表
	 * @param ywGame
	 * @return
	 */
	public YwGame save(YwGame entity);
	
	/**
	 * 根据ids数组删除游戏表
	 * @param ids
	 * @return
	 */
	public Integer delete(String[] ids);
	
	/**
	 * 根据name查询游戏
	 * @param ids
	 * @return
	 */
	public YwGame getYwGameByName(String name);
	
	/**
	 * 根据id获取游戏表
	 * @param id
	 * @return
	 */
	public YwGame getYwGameById(String id);
		
	/**
	 * 根据ids数组查询游戏表map
	 * @param ids
	 * @return
	 */
	public Map<String, YwGame> getYwGameMapByIds(String[] ids);
		
	/**
	 * 后台分页获取游戏表列表
	 * @param game
	 * @param ids
	 * @param page
	 * @param startTime TODO
	 * @param endTime TODO
	 * @param status TODO
	 * @return
	 */
	public List<YwGame> getYwGamePage(YwGame game, String[] ids, PageDto page, Date startTime, Date endTime, Integer[] status);
	
	/**
	 * 游戏专区根据按游戏内房间数排序
	 * @param game
	 * @param ids
	 * @param page
	 * @param startTime
	 * @param endTime
	 * @param status
	 * @param b 是否过滤没房间的游戏
	 * @return
	 * @creationDate. 2016-1-16 下午5:56:56
	 */
	public List<YwGame> getYwGameIndexPage(YwGame game, String[] ids, PageDto page, Date startTime, Date endTime, Integer[] status, boolean b);
	
	/**
	 * 后台分页获取游戏表列表
	 * @param game
	 * @param ids
	 * @param page
	 * @param startTime TODO
	 * @param endTime TODO
	 * @param status TODO
	 * @return
	 */
	public List<YwGame> getYwGamePages(YwGame game, String[] ids, PageDto page, Date startTime, Date endTime, Integer[] status);
	/**
	 * 更新游戏表状态
	 * @param ywGame
	 * @return
	 */
	public Integer updateStatus(String[] ids, Integer status);
	/**
	 * @Title: update
	 * @Description: 更新
	 * @param entity
	 * @return
	 */
	public Integer update(YwGame entity);
	/**
	 * 统计游戏总数
	 * @return
	 * @creationDate. 2015-12-15 下午4:12:06
	 */
	public Integer getYwGameCount();

	/**
	 * @Description: 获取游戏信息（app接口调用）
	 * @param ywGame
	 * @return
	 */
	public List<Map<String, Object>> getYwGameListMap(YwGame ywGame);

    public List<YwGame> getYwGameList(YwGame ywGame,int limit);

	
	
}
