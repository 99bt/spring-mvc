package com.yaowang.dao;

import java.util.List;

import com.yaowang.common.dao.PageDto;
import com.yaowang.entity.SysOption;

/**
 * sys_option 
 * @author 
 * 
 */
public interface SysOptionDao{
	/**
	 * 新增sys_option
	 * @param sysOption
	 * @return
	 */
	public SysOption save(SysOption sysOption);
	
	/**
	 * 根据ids数组删除sys_option
	 * @param ids
	 * @return
	 */
	public Integer delete(String[] ids);
	
	/**
	 * 更新sys_option
	 * @param sysOption
	 * @return
	 */
	public Integer update(SysOption sysOption);
	
	/**
	 * 根据id查询
	 * @param id
	 * @return
	 */
	public SysOption getSysOptionById(String id);
	
	/**
	 * 根据id获取sys_option
	 * @param id
	 * @return
	 */
	public SysOption getSysOptionByIniid(String id);
		
	/**
	 * 根据ids数组查询sys_optionmap
	 * @param ids
	 * @return
	 */
//	public Map<String, SysOption> getSysOptionMapByIds(String[] ids);
	
	/**
	 * 获取sys_option列表
	 * @return
	 */
	public List<SysOption> getSysOptionList();
		
	/**
	 * 分页获取sys_option列表
	 * @param page
	 * @param iniids TODO
	 * @return
	 */
	public List<SysOption> getSysOptionPage(String typeId, PageDto page, String[] iniids);
	
	/**
	 * 获取总数
	 * @return
	 */
	public Integer getSysOptionNumb();
	
}
