package com.yaowang.lansha.dao;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.yaowang.common.dao.PageDto;
import com.yaowang.lansha.entity.LanshaGiftUser;

/**
 * 礼物记录 
 * @author 
 * 
 */
public interface LanshaGiftUserDao{
	/**
	 * 新增礼物记录
	 * @param lanshaGiftUser
	 * @return
	 */
	public LanshaGiftUser save(LanshaGiftUser entity);
	
	/**
	 * 根据ids数组删除礼物记录
	 * @param ids
	 * @return
	 */
	public Integer delete(String[] ids);
	
	/**
	 * 更新礼物记录
	 * @param lanshaGiftUser
	 * @return
	 */
	public Integer update(LanshaGiftUser entity);
	
	/**
	 * 根据id获取礼物记录
	 * @param id
	 * @return
	 */
	public LanshaGiftUser getLanshaGiftUserById(String id);
		
	/**
	 * 根据ids数组查询礼物记录map
	 * @param ids
	 * @return
	 */
	public Map<String, LanshaGiftUser> getLanshaGiftUserMapByIds(String[] ids);
	
	/**
	 * 获取礼物记录列表
	 * @return
	 */
	public List<LanshaGiftUser> getLanshaGiftUserList(LanshaGiftUser entity);
		
	/**
	 * 分页获取礼物记录列表
	 * @param page
	 * @return
	 */
	public List<LanshaGiftUser> getLanshaGiftUserPage(LanshaGiftUser entity, PageDto page);
	/**
	 * 获取礼物总数
	 * @param entity
	 * @return
	 */
	public Integer getSumGiftNumber(LanshaGiftUser entity);

    public List<LanshaGiftUser> getSumGiftNum(LanshaGiftUser entity);
    /**
     * 统计用户礼物数
     * @param giftId
     * @param userId
     * @param startTime
     * @param endTime
     * @return
     */
    public Map<String, Integer> getSumGiftNumber(String giftId, String[] userId, Date startTime, Date endTime);
}
