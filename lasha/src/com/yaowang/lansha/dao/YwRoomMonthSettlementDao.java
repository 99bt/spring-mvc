package com.yaowang.lansha.dao;

import java.util.List;
import java.util.Map;

import com.yaowang.common.dao.PageDto;
import com.yaowang.lansha.entity.YwRoomMonthSettlement;

/**
 * 房间月结 
 * @author 
 * 
 */
public interface YwRoomMonthSettlementDao{
	/**
	 * 新增房间月结
	 * @param ywRoomMonthSettlement
	 * @return
	 */
	public YwRoomMonthSettlement save(YwRoomMonthSettlement entity);
	
	/**
	 * 根据roomIds数组删除房间月结
	 * @param roomIds
	 * @return
	 */
	public Integer delete(String[] roomIds);
	
	/**
	 * 更新房间月结
	 * @param ywRoomMonthSettlement
	 * @return
	 */
	public Integer update(YwRoomMonthSettlement entity);
	
	/**
	 * 根据roomId获取房间月结
	 * @param roomId
	 * @return
	 */
	public YwRoomMonthSettlement getYwRoomMonthSettlementById(String roomId);
		
	/**
	 * 根据roomIds数组查询房间月结map
	 * @param roomIds
	 * @return
	 */
	public Map<String, YwRoomMonthSettlement> getYwRoomMonthSettlementMapByIds(String[] roomIds);
	
	/**
	 * 获取房间月结列表
	 * @return
	 */
	public List<YwRoomMonthSettlement> getYwRoomMonthSettlementList(YwRoomMonthSettlement entity);
		
	/**
	 * 分页获取房间月结列表
	 * @param page
	 * @return
	 */
	public List<YwRoomMonthSettlement> getYwRoomMonthSettlementPage(YwRoomMonthSettlement entity, PageDto page);


	
}
