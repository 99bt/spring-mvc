package com.yaowang.lansha.dao;

import java.util.List;
import java.util.Map;

import com.yaowang.common.dao.PageDto;
import com.yaowang.lansha.entity.YwUserPay;

/**
 * 用户财物信息表 
 * @author 
 * 
 */
public interface YwUserPayDao{
	/**
	 * 新增用户财物信息表
	 * @param ywUserPay
	 * @return
	 */
	public YwUserPay save(YwUserPay entity);
	
	/**
	 * 根据cardNos数组删除用户财物信息表
	 * @param cardNos
	 * @return
	 */
	public Integer delete(String[] cardNos);
	
	/**
	 * 更新用户财物信息表
	 * @param ywUserPay
	 * @return
	 */
	public Integer update(YwUserPay entity);
	
	/**
	 * 根据cardNo获取用户财物信息表
	 * @param cardNo
	 * @return
	 */
	public YwUserPay getYwUserPayById(String cardNo);
		
	/**
	 * 根据cardNos数组查询用户财物信息表map
	 * @param cardNos
	 * @return
	 */
	public Map<String, YwUserPay> getYwUserPayMapByIds(String[] cardNos);
	
	/**
	 * 获取用户财物信息表列表
	 * @return
	 */
	public List<YwUserPay> getYwUserPayList(YwUserPay entity);
		
	/**
	 * 分页获取用户财物信息表列表
	 * @param page
	 * @return
	 */
	public List<YwUserPay> getYwUserPayPage(YwUserPay entity, PageDto page);
	
}
