package com.yaowang.lansha.dao;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.yaowang.common.dao.PageDto;
import com.yaowang.lansha.entity.LanshaSuggestion;

/**
 * 意见反馈 
 * @author 
 * 
 */
public interface LanshaSuggestionDao{
	/**
	 * 新增意见反馈
	 * @param mgameSuggestion
	 * @return
	 */
	public LanshaSuggestion save(LanshaSuggestion entity);
	
	/**
	 * 根据ids数组删除意见反馈
	 * @param ids
	 * @return
	 */
	public Integer delete(String[] ids);
	
	/**
	 * 更新意见反馈
	 * @param mgameSuggestion
	 * @return
	 */
	public Integer update(LanshaSuggestion entity);
	
	/**
	 * 根据id获取意见反馈
	 * @param id
	 * @return
	 */
	public LanshaSuggestion getMgameSuggestionById(String id);
		
	/**
	 * 根据ids数组查询意见反馈map
	 * @param ids
	 * @return
	 */
	public Map<String, LanshaSuggestion> getMgameSuggestionMapByIds(String[] ids);
	
	/**
	 * 获取意见反馈列表
	 * @return
	 */
	public List<LanshaSuggestion> getMgameSuggestionList(LanshaSuggestion entity);
		
	/**
	 * 分页获取意见反馈列表
	 * @param name TODO
	 * @param page
	 * @param startTime TODO
	 * @param endTime TODO
	 * @return
	 */
	public List<LanshaSuggestion> getMgameSuggestionPage(LanshaSuggestion entity, String name, PageDto page, Date startTime, Date endTime);
	
}