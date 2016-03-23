package com.yaowang.lansha.dao;

import java.util.List;
import java.util.Map;

import com.yaowang.common.dao.PageDto;
import com.yaowang.lansha.entity.YwBanner;

/**
 * 广告 
 * @author 
 * 
 */
public interface YwBannerDao{
	/**
	 * 新增广告
	 * @param ywBanner
	 * @return
	 */
	public YwBanner save(YwBanner entity);
	
	/**
	 * 根据ids数组删除广告
	 * @param ids
	 * @return
	 */
	public Integer delete(String[] ids);
	
	/**
	 * 更新广告
	 * @param ywBanner
	 * @return
	 */
	public Integer update(YwBanner entity);
	
	/**
	 * 根据id获取广告
	 * @param id
	 * @return
	 */
	public YwBanner getYwBannerById(String id);
		
	/**
	 * 根据ids数组查询广告map
	 * @param ids
	 * @return
	 */
	public Map<String, YwBanner> getYwBannerMapByIds(String[] ids);
	
	/**
	 * 获取广告列表
	 * @return
	 */
	public List<YwBanner> getYwBannerList(YwBanner entity);
		
	/**
	 * 分页获取广告列表
	 * @param page
	 * @return
	 */
	public List<YwBanner> getYwBannerPage(YwBanner entity, PageDto page);

	/**
	 * @Description: 根据ids数组查询广告list
	 * @param ids
	 * @return
	 */
	public List<YwBanner> getYwBannerListByIds(String[] ids);
	
}
