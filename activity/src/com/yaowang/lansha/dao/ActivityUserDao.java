package com.yaowang.lansha.dao;

import java.util.List;
import java.util.Map;

import com.yaowang.common.dao.PageDto;
import com.yaowang.lansha.entity.ActivityUser;
import com.yaowang.lansha.entity.ActivityUserStock;

/**
 * 用户抽奖机会 
 * @author 
 * 
 */
public interface ActivityUserDao{
	/**
	 * 新增用户抽奖机会
	 * @param activityUser
	 * @return
	 */
	public ActivityUser save(ActivityUser entity);
	
	/**
	 * 根据ids数组删除用户抽奖机会
	 * @param ids
	 * @return
	 */
	public Integer delete(String[] ids);
	
	/**
	 * 更新用户抽奖机会
	 * @param activityUser
	 * @return
	 */
	public Integer update(ActivityUser entity);
	
	/**
	 * 更新用户中奖领取信息
	 * @param activityUser
	 * @return
	 */
	public Integer updateUserInfo(ActivityUser entity);
	
	/**
	 * 根据id获取用户抽奖机会
	 * @param id
	 * @return
	 */
	public ActivityUser getActivityUserById(String id);
		
	/**
	 * 根据ids数组查询用户抽奖机会map
	 * @param ids
	 * @return
	 */
	public Map<String, ActivityUser> getActivityUserMapByIds(String[] ids);
	
	/**
	 * 获取用户抽奖机会列表
	 * @return
	 */
	public List<ActivityUser> getActivityUserList(ActivityUser entity);
		
	/**
	 * 分页获取用户抽奖机会列表
	 * @param page
	 * @return
	 */
	public List<ActivityUser> getActivityUserPage(ActivityUser entity, PageDto page);
}
