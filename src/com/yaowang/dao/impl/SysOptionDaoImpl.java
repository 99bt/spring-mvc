package com.yaowang.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Repository;

import com.yaowang.common.dao.BaseDaoImpl;
import com.yaowang.common.dao.PageDto;
import com.yaowang.common.dao.SQLUtils;
import com.yaowang.dao.SysOptionDao;
import com.yaowang.entity.SysOption;

/**
 * sys_option 
 * @author 
 * 
 */
@SuppressWarnings("unchecked")
@Repository("sysOptionDao")
public class SysOptionDaoImpl extends BaseDaoImpl<SysOption> implements SysOptionDao{
	@Override
	public SysOption setField(ResultSet rs) throws SQLException{
		SysOption entity = new SysOption();
		entity.setId(rs.getString("id"));
		entity.setIniid(rs.getString("iniid"));
		entity.setName(rs.getString("name"));
		entity.setDvalue(rs.getString("dvalue"));
		entity.setNowvalue(rs.getString("nowvalue"));
		entity.setDescription(rs.getString("description"));
		entity.setViewable(rs.getInt("viewable"));
		entity.setOrderid(rs.getInt("orderid"));
		entity.setValidatejs(rs.getString("validatejs"));
		entity.setTypeId(rs.getString("type_id"));
		return entity;
	}
	
	@Override
	public SysOption save(SysOption entity){
		String sql = "insert into sys_option(id, iniid, name, dvalue, nowvalue, description, viewable, orderid, validatejs, type_id) values(?,?,?,?,?,?,?,?,?,?)"; 
		if (StringUtils.isBlank(entity.getId())){
			entity.setId(createId());
		}
		Object[] args = new Object[]{
			entity.getId(), entity.getIniid(), 
			entity.getName(), entity.getDvalue(), 
			entity.getNowvalue(), entity.getDescription(), 
			entity.getViewable(), entity.getOrderid(), 
			entity.getValidatejs(), entity.getTypeId()
		};
		update(sql, args);
		return entity;
	}
	
	@Override
	public Integer delete(String[] ids){
		String sql = "delete from sys_option where id in";
		return executeUpdateForInSQL(sql, null, ids);
	}
	
	@Override
	public Integer update(SysOption entity){
		String sql = "update sys_option set nowvalue = ?, description = ? where id = ?";
		Object[] args = new Object[]{
			entity.getNowvalue(), entity.getDescription(), entity.getId()
		};
		return update(sql, args);
	}
	
	@Override
	public SysOption getSysOptionById(String id) {
		String sql = "select * from sys_option where id = ?";
		return (SysOption) findForObject(sql, new Object[] { id }, new MultiRow());
	}

	@Override
	public SysOption getSysOptionByIniid(String iniid){
		String sql = "select * from sys_option where iniid = ?";
		return (SysOption) findForObject(sql, new Object[] { iniid }, new MultiRow());
	}

//	@Override
//	public Map<String, SysOption> getSysOptionMapByIds(String[] ids){
//		String sql = "select * from sys_option where id in";
//		return findForInSQL(sql, null, ids, new MapRow());
//	}

	@Override
	public List<SysOption> getSysOptionList(){
		String sql = "select * from sys_option order by orderid";
		return find(sql, null, new MultiRow());
	}

	@Override
	public List<SysOption> getSysOptionPage(String typeId, PageDto page, String[] iniids){
		String sql = "select * from sys_option where type_id = ?";
		if(ArrayUtils.isNotEmpty(iniids)){
			sql += " and iniid in" + SQLUtils.toSQLInString(iniids);
		}
		sql += " order by type_id desc, orderid";
		return findForPage(sql, new Object[]{typeId }, new MultiRow(), page);
	}
	
	@Override
	public Integer getSysOptionNumb() {
		String sql = "select count(*)  from sys_option";
		return queryForInt(sql);
	}
}
