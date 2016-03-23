package com.yaowang.lansha.dao;

import java.util.List;
import java.util.Map;

import com.yaowang.common.dao.PageDto;
import com.yaowang.lansha.entity.WechatJsapiticket;

/**
 * 微信JS接口访问的票据 
 * @author 
 * 
 */
public interface WechatJsapiticketDao{
	/**
	 * 新增微信JS接口访问的票据
	 * @param wechatJsapiticket
	 * @return
	 */
	public WechatJsapiticket save(WechatJsapiticket entity);
	
	/**
	 * 根据ids数组删除微信JS接口访问的票据
	 * @param ids
	 * @return
	 */
	public Integer delete(String[] ids);
	
	/**
	 * 更新微信JS接口访问的票据
	 * @param wechatJsapiticket
	 * @return
	 */
	public Integer update(WechatJsapiticket entity);
	
	/**
	 * 根据id获取微信JS接口访问的票据
	 * @param id
	 * @return
	 */
	public WechatJsapiticket getWechatJsapiticketById(String id);
	
	/**
	 * 根据APP Id获取微信JS接口访问的票据
	 * @param id
	 * @return
	 */
	public WechatJsapiticket getWechatJsapiticketByAppId(String appId);
		
	/**
	 * 根据ids数组查询微信JS接口访问的票据map
	 * @param ids
	 * @return
	 */
	public Map<String, WechatJsapiticket> getWechatJsapiticketMapByIds(String[] ids);
	
	/**
	 * 获取微信JS接口访问的票据列表
	 * @return
	 */
	public List<WechatJsapiticket> getWechatJsapiticketList(WechatJsapiticket entity);
		
	/**
	 * 分页获取微信JS接口访问的票据列表
	 * @param page
	 * @return
	 */
	public List<WechatJsapiticket> getWechatJsapiticketPage(WechatJsapiticket entity, PageDto page);
	
}
