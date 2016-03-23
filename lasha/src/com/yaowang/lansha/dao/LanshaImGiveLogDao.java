package com.yaowang.lansha.dao;

import java.util.List;
import java.util.Map;

import com.yaowang.common.dao.PageDto;
import com.yaowang.lansha.entity.LanshaImGiveLog;

/**
 * 发言得虾米记录 
 * @author 
 * 
 */
public interface LanshaImGiveLogDao{
	/**
	 * 新增发言得虾米记录
	 * @param lanshaImGiveLog
	 * @return
	 */
	public LanshaImGiveLog save(LanshaImGiveLog entity);
	
	/**
	 * 根据ids数组删除发言得虾米记录
	 * @param ids
	 * @return
	 */
	public Integer delete(String[] ids);
	
	/**
	 * 更新发言得虾米记录
	 * @param lanshaImGiveLog
	 * @return
	 */
	public Integer update(LanshaImGiveLog entity);
	
	/**
	 * 根据id获取发言得虾米记录
	 * @param id
	 * @return
	 */
	public LanshaImGiveLog getLanshaImGiveLogById(String id);
		
	/**
	 * 根据ids数组查询发言得虾米记录map
	 * @param ids
	 * @return
	 */
	public Map<String, LanshaImGiveLog> getLanshaImGiveLogMapByIds(String[] ids);
	
	/**
	 * 获取发言得虾米记录列表
	 * @return
	 */
	public List<LanshaImGiveLog> getLanshaImGiveLogList(LanshaImGiveLog entity);
		
	/**
	 * 分页获取发言得虾米记录列表
	 * @param page
	 * @return
	 */
	public List<LanshaImGiveLog> getLanshaImGiveLogPage(LanshaImGiveLog entity, PageDto page);

	/**
	 * 获取赠送记录
	 * @param userId
	 * @param now
	 * @return
	 */
	public Integer getGiveNumber(LanshaImGiveLog entity);
}
