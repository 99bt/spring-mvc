package com.yaowang.lansha.dao;

import java.util.List;
import java.util.Map;

import com.yaowang.common.dao.PageDto;
import com.yaowang.lansha.entity.YwUserRoomAdmin;

/**
 * 热门推荐房间 
 * @author 
 * 
 */
public interface YwUserRoomAdminDao{
	/**
	 * 新增热门推荐房间
	 * @param ywUserRoomAdmin
	 * @return
	 */
	public YwUserRoomAdmin save(YwUserRoomAdmin entity);
	
	/**
	 * 根据ids数组删除热门推荐房间
	 * @param ids
	 * @return
	 */
	public Integer delete(String[] ids);
	
	/**
	 * 更新热门推荐房间
	 * @param ywUserRoomAdmin
	 * @return
	 */
	public Integer update(YwUserRoomAdmin entity);
	
	/**
	 * 根据id获取热门推荐房间
	 * @param id
	 * @return
	 */
	public YwUserRoomAdmin getYwUserRoomAdminById(String id);
		
	/**
	 * 根据ids数组查询热门推荐房间map
	 * @param ids
	 * @return
	 */
	public Map<String, YwUserRoomAdmin> getYwUserRoomAdminMapByIds(String[] ids);
	
	/**
	 * 获取热门推荐房间列表
	 * @return
	 */
	public List<YwUserRoomAdmin> getYwUserRoomAdminList(YwUserRoomAdmin entity);
		
	/**
	 * 分页获取热门推荐房间列表
	 * @param page
	 * @return
	 */
	public List<YwUserRoomAdmin> getYwUserRoomAdminPage(YwUserRoomAdmin entity, PageDto page);

	/**
	 * @Description: 根据id删除房间管理员信息
	 * @param roomId
	 * @param manageId
	 */
	public void deleteRoomAdminById(String roomId, String manageId);

	/**
	 * 根据roomid和userid查找管理员记录
	 * @param roomId
	 * @param uid
	 * @return
	 */
	public YwUserRoomAdmin getYwUserRoomAdminByRoomIdAndUserId(String roomId, String uid);
	
}
