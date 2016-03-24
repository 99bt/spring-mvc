package com.yaowang.dao;

import java.util.List;

import com.yaowang.common.dao.PageDto;
import com.yaowang.entity.SysOptionType;

/**
 * sys_option_type 
 * @author 
 * 
 */
public interface SysOptionTypeDao{
	/**
	 * 新增sys_option_type
	 * @param sysOptionType
	 * @return
	 */
	public SysOptionType save(SysOptionType sysOptionType);
	
	/**
	 * 根据ids数组删除sys_option_type
	 * @param ids
	 * @return
	 */
	public Integer delete(String[] ids);
	
	/**
	 * 更新sys_option_type
	 * @param sysOptionType
	 * @return
	 */
	public Integer update(SysOptionType sysOptionType);
	
	/**
	 * 根据id获取sys_option_type
	 * @param id
	 * @return
	 */
	public SysOptionType getSysOptionTypeById(String id);
		
	/**
	 * 根据ids数组查询sys_option_typemap
	 * @param ids
	 * @return
	 */
//	public Map<String, SysOptionType> getSysOptionTypeMapByIds(String[] ids);
	
	/**
	 * 获取sys_option_type列表
	 * @return
	 */
	public List<SysOptionType> getSysOptionTypeList();
		
	/**
	 * 分页获取sys_option_type列表
	 * @param page
	 * @return
	 */
	public List<SysOptionType> getSysOptionTypePage(PageDto page);
	
}
