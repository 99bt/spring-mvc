package com.yaowang.lansha.dao;

import java.util.List;
import java.util.Map;

import com.yaowang.common.dao.PageDto;
import com.yaowang.lansha.entity.LanshaAdvertisement;

/**
 * 蓝鲨广告 
 * @author 
 * 
 */
public interface LanshaAdvertisementDao{
	/**
	 * 新增蓝鲨广告
	 * @param lanshaAdvertisement
	 * @return
	 */
	public LanshaAdvertisement save(LanshaAdvertisement entity);
	
	/**
	 * 根据ids数组删除蓝鲨广告
	 * @param ids
	 * @return
	 */
	public Integer delete(String[] ids);
	
	/**
	 * 更新蓝鲨广告
	 * @param lanshaAdvertisement
	 * @return
	 */
	public Integer update(LanshaAdvertisement entity);
	
	/**
	 * 根据id获取蓝鲨广告
	 * @param id
	 * @return
	 */
	public LanshaAdvertisement getLanshaAdvertisementById(String id);
	
	/**
	 * 根据rate获取蓝鲨广告
	 * @param rate
	 * @return
	 */
	public LanshaAdvertisement getLanshaAdvertisementByRate(Float rate);
		
	/**
	 * 根据ids数组查询蓝鲨广告map
	 * @param ids
	 * @return
	 */
	public Map<String, LanshaAdvertisement> getLanshaAdvertisementMapByIds(String[] ids);
	
	/**
	 * 获取蓝鲨广告列表
	 * @return
	 */
	public List<LanshaAdvertisement> getLanshaAdvertisementList(LanshaAdvertisement entity);
		
	/**
	 * 分页获取蓝鲨广告列表
	 * @param page
	 * @return
	 */
	public List<LanshaAdvertisement> getLanshaAdvertisementPage(LanshaAdvertisement entity, PageDto page);
	
}
