package com.yaowang.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Repository;

import com.yaowang.common.dao.BaseDaoImpl;
import com.yaowang.common.dao.PageDto;
import com.yaowang.dao.SysOptionTypeDao;
import com.yaowang.entity.SysOptionType;

/**
 * sys_option_type 
 * @author 
 * 
 */
@SuppressWarnings("unchecked")
@Repository("sysOptionTypeDao")
public class SysOptionTypeDaoImpl extends BaseDaoImpl<SysOptionType> implements SysOptionTypeDao{
	@Override
	public SysOptionType setField(ResultSet rs) throws SQLException{
		SysOptionType entity = new SysOptionType();
		entity.setId(rs.getString("id"));
		entity.setName(rs.getString("name"));
		entity.setOrderid(rs.getInt("orderid"));
		return entity;
	}
	
	@Override
	public SysOptionType save(SysOptionType entity){
		String sql = "insert into sys_option_type(id, name, orderid) values(?,?,?)"; 
		if (StringUtils.isBlank(entity.getId())){
			entity.setId(createId());
		}
		Object[] args = new Object[]{
			entity.getId(), entity.getName(), 
			entity.getOrderid()
		};
		update(sql, args);
		return entity;
	}
	
	@Override
	public Integer delete(String[] ids){
		String sql = "delete from sys_option_type where id in";
		return executeUpdateForInSQL(sql, null, ids);
	}
	
	@Override
	public Integer update(SysOptionType entity){
		String sql = "update sys_option_type set name = ?, orderid = ? where id = ?";
		Object[] args = new Object[]{
			entity.getName(), entity.getOrderid(), 
			entity.getId()
		};
		return update(sql, args);
	}

	@Override
	public SysOptionType getSysOptionTypeById(String id){
		String sql = "select * from sys_option_type where id = ?";
		return (SysOptionType) findForObject(sql, new Object[] { id }, new MultiRow());
	}

//	@Override
//	public Map<String, SysOptionType> getSysOptionTypeMapByIds(String[] ids){
//		String sql = "select * from sys_option_type where id in";
//		return findForInSQL(sql, null, ids, new MapRow());
//	}

	@Override
	public List<SysOptionType> getSysOptionTypeList(){
		String sql = "select * from sys_option_type";
		return find(sql, null, new MultiRow());
	}

	@Override
	public List<SysOptionType> getSysOptionTypePage(PageDto page){
		String sql = "select * from sys_option_type";
		return findForPage(sql, null, new MultiRow(), page);
	}
	
}
