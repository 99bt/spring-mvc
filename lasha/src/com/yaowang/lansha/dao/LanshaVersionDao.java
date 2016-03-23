package com.yaowang.lansha.dao;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.yaowang.common.dao.PageDto;
import com.yaowang.lansha.entity.LanshaVersion;

/**
 * app版本表 
 * @author 
 * 
 */
public interface LanshaVersionDao{
	/**
	 * 新增app版本表
	 * @param mgameVersion
	 * @return
	 */
	public LanshaVersion save(LanshaVersion entity);
	
	/**
	 * 根据ids数组删除app版本表
	 * @param ids
	 * @return
	 */
	public Integer delete(String[] ids);
	
	/**
	 * 更新app版本表
	 * @param mgameVersion
	 * @return
	 */
	public Integer update(LanshaVersion entity);
	
	/**
	 * 根据id获取app版本表
	 * @param id
	 * @return
	 */
	public LanshaVersion getVersionById(String id);
		
	/**
	 * 根据ids数组查询app版本表map
	 * @param ids
	 * @return
	 */
	public Map<Integer, LanshaVersion> getVersionMapByIds(Integer[] ids);
	
	/**
	 * 获取app版本表列表
	 * @return
	 */
	public List<LanshaVersion> getVersionList(LanshaVersion entity);
		
	/**
	 * 分页获取app版本表列表
	 * @param page
	 * @return
	 */
	public List<LanshaVersion> getVersionPage(LanshaVersion entity,  Integer[] status, PageDto page, Date startTime, Date endTime);;

	/**
	 * @Description: 修改状态
	 * @param ids
	 * @param status
	 */
	public void updateStatus(String[] ids, String status);
	
}
