package com.yaowang.lansha.dao;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.yaowang.common.dao.PageDto;
import com.yaowang.lansha.entity.LanshaLiveHistory;

/**
 * 主播开播记录
 * @author 
 * 
 */
public interface LanshaLiveHistoryDao{
	/**
	 * 新增主播开播记录
	 * @param lanshaLiveHistory
	 * @return
	 */
	public LanshaLiveHistory save(LanshaLiveHistory entity);
	
	/**
	 * 根据ids数组删除主播开播记录
	 * @param ids
	 * @return
	 */
	public Integer delete(String[] ids);
	
	/**
	 * 更新主播开播记录
	 * @param lanshaLiveHistory
	 * @return
	 */
	public Integer update(LanshaLiveHistory entity);
	
	/**
	 * 根据id获取主播开播记录
	 * @param id
	 * @return
	 */
	public LanshaLiveHistory getLanshaLiveHistoryById(String id);
		
	/**
	 * 根据ids数组查询主播开播记录map
	 * @param ids
	 * @return
	 */
	public Map<String, LanshaLiveHistory> getLanshaLiveHistoryMapByIds(String[] ids);
	
	/**
	 * 获取主播开播记录列表
	 * @return
	 */
	public List<LanshaLiveHistory> getLanshaLiveHistoryList(LanshaLiveHistory entity);
		
	/**
	 * 分页获取主播开播记录列表
	 * @param startTime TODO
	 * @param endTime TODO
	 * @param page
	 * @return
	 */
	public List<LanshaLiveHistory> getLanshaLiveHistoryPage(LanshaLiveHistory entity, Date startTime, Date endTime, PageDto page);

	/**
	 * @Description: 根据房间ID获取开播记录
	 * @param roomId
	 * @return
	 */
	public LanshaLiveHistory getLanshaLiveHistoryByRoomId(String roomId);
	/**
	 * @Description: 根据房间ID获取开播记录
	 * @param room:房间Id  index:取第几条数据
	 * @return
	 */
	public LanshaLiveHistory getLanshaLiveHistoryByRoomId(String roomId,int index);

	/**
	 * @Description: 直播时长统计使用
	 * @param liveHistory
	 * @param startTime TODO
	 * @param endTime TODO
	 * @return
	 */
	public List<Map<String, Object>> listMapLiveHistoryReport(LanshaLiveHistory liveHistory, Date startTime, Date endTime);

    public Map<String, Integer> getCount(Date startTime, Date endTime);

    public List<LanshaLiveHistory> getLanshaLiveHistoryForPage(LanshaLiveHistory entity, Date startTime, Date endTime, PageDto page);
	
}
