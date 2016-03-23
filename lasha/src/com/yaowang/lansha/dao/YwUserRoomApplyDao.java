package com.yaowang.lansha.dao;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.yaowang.common.dao.PageDto;
import com.yaowang.lansha.entity.YwUserRoomApply;

/**
 * 热门推荐房间 
 * @author 
 * 
 */
public interface YwUserRoomApplyDao{
	/**
	 * 新增热门推荐房间
	 * @param ywUserRoomApply
	 * @return
	 */
	public YwUserRoomApply save(YwUserRoomApply entity);
	
	/**
	 * 根据ids数组删除热门推荐房间
	 * @param ids
	 * @return
	 */
	public Integer delete(String[] ids);
	
	/**
	 * 更新热门推荐房间
	 * @param ywUserRoomApply
	 * @return
	 */
	public Integer update(YwUserRoomApply entity);
	
	/**
	 * 根据id获取热门推荐房间
	 * @param id
	 * @return
	 */
	public YwUserRoomApply getYwUserRoomApplyById(String id);
		
	/**
	 * 根据ids数组查询热门推荐房间map
	 * @param ids
	 * @return
	 */
	public Map<String, YwUserRoomApply> getYwUserRoomApplyMapByIds(String[] ids);
	
	/**
	 * 获取热门推荐房间列表
	 * @return
	 */
	public List<YwUserRoomApply> getYwUserRoomApplyList(YwUserRoomApply entity);
		
	/**
	 * 分页获取热门推荐房间列表
	 * @param page
	 * @return
	 */
	public List<YwUserRoomApply> getYwUserRoomApplyPage(YwUserRoomApply entity, Date startTime, Date endTime, PageDto page);
	/**
	 * 更新申请信息
	 * @param ywUserRoomApply
	 * @return
	 */
	public Integer updateApply(YwUserRoomApply entity);
	
}
