package com.yaowang.lansha.dao;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.yaowang.common.dao.PageDto;
import com.yaowang.lansha.entity.LanshaRoomRanking;

/**
 * 房间排名 
 * @author 
 * 
 */
public interface LanshaRoomRankingDao{
	/**
	 * 新增房间排名
	 * @param lanshaRoomRanking
	 * @return
	 */
	public LanshaRoomRanking save(LanshaRoomRanking entity);
	
	/**
	 * 根据ids数组删除房间排名
	 * @param ids
	 * @return
	 */
	public Integer delete(String[] ids);
	
	/**
	 * 更新房间排名
	 * @param lanshaRoomRanking
	 * @return
	 */
	public Integer update(LanshaRoomRanking entity);
	
	/**
	 * 根据id获取房间排名
	 * @param id
	 * @return
	 */
	public LanshaRoomRanking getLanshaRoomRankingById(String id);
		
	/**
	 * 根据ids数组查询房间排名map
	 * @param ids
	 * @return
	 */
	public Map<String, LanshaRoomRanking> getLanshaRoomRankingMapByIds(String[] ids);
	
	/**
	 * 获取房间排名列表
	 * @return
	 */
	public List<LanshaRoomRanking> getLanshaRoomRankingList(LanshaRoomRanking entity);
		
	/**
	 * 分页获取房间排名列表
	 * @param page
	 * @return
	 */
	public List<LanshaRoomRanking> getLanshaRoomRankingPage(LanshaRoomRanking entity, PageDto page);
	/**
	 * 分页获取房间排名列表
	 * @param entity 查询对象
	 * @param startTime 开始时间
	 * @param endTime 结束时间
	 * @return
	 */
	public List<LanshaRoomRanking> getLanshaRoomRankingPage(LanshaRoomRanking entity,Date startTime, Date endTime, PageDto page);
	/**
	 * 根据房间汇总
	 * @param object
	 * @return
	 */
	public List<LanshaRoomRanking> getLanshaRoomRankingListGroupUser(LanshaRoomRanking entity, Date startTime, Date endTime, PageDto page);
	/**
	 * 根据后台查询信息汇总
	 * @param object
	 * @return
	 */
	public List<LanshaRoomRanking> getLanshaRoomRankingListGroupUser(LanshaRoomRanking entity,String[] uids, Date startTime, Date endTime, PageDto page);

    public List<LanshaRoomRanking> getLanshaRoomRankingList(LanshaRoomRanking entity, Date startTime, Date endTime,PageDto page);

     public Map<String, Integer> getCountValidDays(int time);


    /**
     * 获取房间日票排名（top10）
     * @return
     */
    public List<LanshaRoomRanking> getLanshaRoomRankingListSort(Date date);
}
