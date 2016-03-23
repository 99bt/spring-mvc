package com.yaowang.lansha.dao;

import java.util.List;
import java.util.Map;

import com.yaowang.common.dao.PageDto;
import com.yaowang.lansha.entity.YwUserRoomRelation;

/**
 * 房间关注 
 * @author 
 * 
 */
public interface YwUserRoomRelationDao{
	/**
	 * 新增房间关注
	 * @param ywUserRoomRelation
	 * @return
	 */
	public YwUserRoomRelation save(YwUserRoomRelation entity);
	
	/**
	 * 根据ids数组删除房间关注
	 * @param ids
	 * @return
	 */
	public Integer delete(String[] ids);

    /**
     * 根据id删除房间关注(假删除)
     * @param id
     * @return
     */
    public Integer updateStatusById(Integer status,String id);
	
	/**
	 * 更新房间关注
	 * @param ywUserRoomRelation
	 * @return
	 */
	public Integer update(YwUserRoomRelation entity);
	
	/**
	 * 根据id获取房间关注
	 * @param id
	 * @return
	 */
	public YwUserRoomRelation getYwUserRoomRelationById(String id);
		
	/**
	 * 根据ids数组查询房间关注map
	 * @param ids
	 * @return
	 */
	public Map<String, YwUserRoomRelation> getYwUserRoomRelationMapByIds(String[] ids);
	
	/**
	 * 获取房间关注列表
	 * @return
	 */
	public List<YwUserRoomRelation> getYwUserRoomRelationList(YwUserRoomRelation entity);
		
	/**
	 * 分页获取房间关注列表
	 * @param page
	 * @return
	 */
	public List<YwUserRoomRelation> getYwUserRoomRelationPage(YwUserRoomRelation entity, PageDto page);
	/**
	 * 获取关注数
	 * @param relation
	 * @return
	 */
	public Integer getSumRelationNumber(YwUserRoomRelation relation);
}
