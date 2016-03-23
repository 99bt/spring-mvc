package com.yaowang.lansha.dao;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.yaowang.common.dao.PageDto;
import com.yaowang.lansha.entity.LanshaUserRecord;

/**
 * 用户收支记录 
 * @author 
 * 
 */
public interface LanshaUserRecordDao{
	/**
	 * 新增用户收支记录
	 * @param lanshaUserRecord
	 * @return
	 */
	public LanshaUserRecord save(LanshaUserRecord entity);
	
	/**
	 * 根据ids数组删除用户收支记录
	 * @param ids
	 * @return
	 */
	public Integer delete(String[] ids);
	
	/**
	 * 更新用户收支记录
	 * @param lanshaUserRecord
	 * @return
	 */
	public Integer update(LanshaUserRecord entity);
	
	/**
	 * 根据id获取用户收支记录
	 * @param id
	 * @return
	 */
	public LanshaUserRecord getLanshaUserRecordById(String id);
		
	/**
	 * 根据ids数组查询用户收支记录map
	 * @param ids
	 * @return
	 */
	public Map<String, LanshaUserRecord> getLanshaUserRecordMapByIds(String[] ids);
	
	/**
	 * 获取用户收支记录列表
	 * @return
	 */
	public List<LanshaUserRecord> getLanshaUserRecordList(LanshaUserRecord entity);
		
	/**
	 * 分页获取用户收支记录列表
	 * @param page
	 * @param startTime TODO
	 * @param endTime TODO
	 * @return
	 */
	public List<LanshaUserRecord> getLanshaUserRecordPage(LanshaUserRecord entity, PageDto page, Date startTime, Date endTime);
	/**
	 * @Title: saveBatch
	 * @Description: 批量保存
	 * @param list
	 * @return
	 */
	public Integer saveBatch(List<LanshaUserRecord> list);
	
}
