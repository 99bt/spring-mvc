package com.yaowang.lansha.dao;

import java.util.List;
import java.util.Map;

import com.yaowang.common.dao.PageDto;
import com.yaowang.lansha.entity.YwUserRoomContract;

/**
 * 签约主播管理 
 * @author 
 * 
 */
public interface YwUserRoomContractDao{
	/**
	 * 新增签约主播管理
	 * @param ywUserRoomContract
	 * @return
	 */
	public YwUserRoomContract save(YwUserRoomContract entity);
	
	/**
	 * 根据userIds数组删除签约主播管理
	 * @param userIds
	 * @return
	 */
	public Integer delete(String[] userIds);
	
	/**
	 * 更新签约主播管理
	 * @param ywUserRoomContract
	 * @return
	 */
	public Integer update(YwUserRoomContract entity);
	
	/**
	 * 根据userId获取签约主播管理
	 * @param userId
	 * @return
	 */
	public YwUserRoomContract getYwUserRoomContractById(String userId);
		
	/**
	 * 根据userIds数组查询签约主播管理map
	 * @param userIds
	 * @return
	 */
	public Map<String, YwUserRoomContract> getYwUserRoomContractMapByIds(String[] userIds);
	
	/**
	 * 获取签约主播管理列表
	 * @return
	 */
	public List<YwUserRoomContract> getYwUserRoomContractList(YwUserRoomContract entity);
		
	/**
	 * 分页获取签约主播管理列表
	 * @param page
	 * @return
	 */
	public List<YwUserRoomContract> getYwUserRoomContractPage(YwUserRoomContract entity, PageDto page);

    public Integer updateStatus(String[] ids);
	
}
