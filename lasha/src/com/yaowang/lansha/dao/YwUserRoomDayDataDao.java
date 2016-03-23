package com.yaowang.lansha.dao;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.yaowang.common.dao.PageDto;
import com.yaowang.lansha.entity.YwUserRoomDayData;

/**
 * 房间日数据 
 * @author 
 * 
 */
public interface YwUserRoomDayDataDao{
	/**
	 * 新增房间日数据
	 * @param ywUserRoomDayData
	 * @return
	 */
	public YwUserRoomDayData save(YwUserRoomDayData entity);
	
	/**
	 * 根据roomIds数组删除房间日数据
	 * @param roomIds
	 * @return
	 */
	public Integer delete(String[] rankingIds);
	
	/**
	 * 更新房间日数据
	 * @param ywUserRoomDayData
	 * @return
	 */
	public Integer update(YwUserRoomDayData entity);
	
	/**
	 * 根据roomId获取房间日数据
	 * @param roomId
	 * @return
	 */
	public YwUserRoomDayData getYwUserRoomDayDataById(String rankingId);
		
	/**
	 * 根据roomIds数组查询房间日数据map
	 * @param roomIds
	 * @return
	 */
	public Map<String, YwUserRoomDayData> getYwUserRoomDayDataMapByIds(String[] rankingIds);
	
	/**
	 * 获取房间日数据列表
	 * @return
	 */
	public List<YwUserRoomDayData> getYwUserRoomDayDataList(YwUserRoomDayData entity,Date startTime,Date endTime);
		
	/**
	 * 分页获取房间日数据列表
	 * @param page
	 * @return
	 */
	public List<YwUserRoomDayData> getYwUserRoomDayDataPage(YwUserRoomDayData entity, PageDto page);

    /**
     * 获取排名数据
     * @param entity
     * @return
     */
    public List<YwUserRoomDayData> getYwUserRoomDayDataSort(YwUserRoomDayData entity);

    YwUserRoomDayData  getYwUserRoomDayData(YwUserRoomDayData entity);

    public List<YwUserRoomDayData> getYwUserRoomDayDataTotalList(YwUserRoomDayData entity,Date startTime,Date endTime, PageDto page);

    /**
     * 根据日期删除
     * @param date
     * @return
     */
    public Integer deleteByDay(Date date);

    /**
     * 更新奖金罚金信息
     * @param forfeit
     * @param bonus
     * @param remark
     * @param id
     * @return
     */
    public Integer updateInfo(Float forfeit,Float bonus,String remark,String id );

    /**
     * 统计奖金，罚金
     * @param entity
     * @param startTime
     * @param endTime
     * @return
     */
    public YwUserRoomDayData getYwUserRoomDataSum(YwUserRoomDayData entity, Date startTime, Date endTime);

}
