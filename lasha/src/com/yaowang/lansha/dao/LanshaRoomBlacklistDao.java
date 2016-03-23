package com.yaowang.lansha.dao;

import java.util.List;
import java.util.Map;

import com.yaowang.common.dao.PageDto;
import com.yaowang.lansha.entity.LanshaRoomBlacklist;

/**
 * 房间禁言用户 
 * @author 
 * 
 */
public interface LanshaRoomBlacklistDao{
	/**
	 * 新增房间禁言用户
	 * @param lanshaRoomBlacklist
	 * @return
	 */
	public LanshaRoomBlacklist save(LanshaRoomBlacklist entity);
	
	/**
	 * 根据ids数组删除房间禁言用户
	 * @param ids
	 * @return
	 */
	public Integer delete(String[] ids);
	
	/**
	 * 更新房间禁言用户
	 * @param lanshaRoomBlacklist
	 * @return
	 */
	public Integer update(LanshaRoomBlacklist entity);
	
	/**
	 * 根据id获取房间禁言用户
	 * @param id
	 * @return
	 */
	public LanshaRoomBlacklist getLanshaRoomBlacklistById(String id);
		
	/**
	 * 根据ids数组查询房间禁言用户map
	 * @param ids
	 * @return
	 */
	public Map<String, LanshaRoomBlacklist> getLanshaRoomBlacklistMapByIds(String[] ids);
	
	/**
	 * 获取房间禁言用户列表
	 * @return
	 */
	public List<LanshaRoomBlacklist> getLanshaRoomBlacklistList(LanshaRoomBlacklist entity);
		
	/**
	 * 分页获取房间禁言用户列表
	 * @param page
	 * @return
	 */
	public List<LanshaRoomBlacklist> getLanshaRoomBlacklistPage(LanshaRoomBlacklist entity, PageDto page);

	/**
	 * 根据用户id和房间id删除记录
	 * @param roomId
	 * @param userId
	 * @return
	 */
	public Integer deleteByRoomIdAndUserId(String roomId, String userId);
	
}
