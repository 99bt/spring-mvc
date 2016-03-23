package com.yaowang.lansha.dao;

import java.util.List;
import java.util.Map;

import com.yaowang.common.dao.PageDto;
import com.yaowang.lansha.entity.LanshaGift;

/**
 * 礼物 
 * @author 
 * 
 */
public interface LanshaGiftDao{
	/**
	 * 新增礼物
	 * @param lanshaGift
	 * @return
	 */
	public LanshaGift save(LanshaGift entity);
	
	/**
	 * 根据ids数组删除礼物
	 * @param ids
	 * @return
	 */
	public Integer delete(String[] ids);
	
	/**
	 * 更新礼物
	 * @param lanshaGift
	 * @return
	 */
	public Integer update(LanshaGift entity);
	
	/**
	 * 根据id获取礼物
	 * @param id
	 * @return
	 */
	public LanshaGift getLanshaGiftById(String id);
		
	/**
	 * 根据ids数组查询礼物map
	 * @param ids
	 * @return
	 */
	public Map<String, LanshaGift> getLanshaGiftMapByIds(String[] ids);
	
	/**
	 * 获取礼物列表
	 * @return
	 */
	public List<LanshaGift> getLanshaGiftList(LanshaGift entity);
		
	/**
	 * 分页获取礼物列表
	 * @param page
	 * @return
	 */
	public List<LanshaGift> getLanshaGiftPage(LanshaGift entity, PageDto page);
	
}
