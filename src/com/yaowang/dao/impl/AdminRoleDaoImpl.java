package com.yaowang.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Repository;

import com.yaowang.common.dao.BaseDaoImpl;
import com.yaowang.common.dao.PageDto;
import com.yaowang.dao.AdminRoleDao;
import com.yaowang.entity.AdminRole;

/**
 * 后台角色 
 * @author 
 * 
 */
@SuppressWarnings("unchecked")
@Repository("adminRoleDao")
public class AdminRoleDaoImpl extends BaseDaoImpl<AdminRole> implements AdminRoleDao{
	@Override
	public AdminRole setField(ResultSet rs) throws SQLException{
		AdminRole entity = new AdminRole();
		entity.setId(rs.getString("id"));
		entity.setName(rs.getString("name"));
		entity.setState(rs.getString("state"));
		entity.setIsSys(rs.getBoolean("is_sys"));
		entity.setDisplayOrder(rs.getString("display_order"));
		entity.setCreateTime(rs.getTimestamp("create_time"));
		entity.setIndexUrl(rs.getString("index_url"));
		return entity;
	}
	
	@Override
	public AdminRole save(AdminRole entity){
		String sql = "insert into admin_role(id, name, state, is_sys, display_order, index_url, create_time) values(?,?,?,?,?,?,?)"; 
		if (StringUtils.isBlank(entity.getId())){
			entity.setId(createId());
		}
		Object[] args = new Object[]{
			entity.getId(), entity.getName(), 
			entity.getState(), entity.getIsSys(), 
			entity.getDisplayOrder(), entity.getIndexUrl(),
			entity.getCreateTime(),
		};
		update(sql, args);
		return entity;
	}
	
	@Override
	public Integer delete(String[] ids){
		String sql = "update admin_role set state = '0' where is_sys = '0' and id in";
		return executeUpdateForInSQL(sql, null, ids);
	}
	
	@Override
	public Integer update(AdminRole entity){
		String sql = "update admin_role set name = ?, state = ?, display_order = ?, index_url = ? where id = ? and is_sys = '0'";
		Object[] args = new Object[]{
			entity.getName(), entity.getState(), 
			entity.getDisplayOrder(), entity.getIndexUrl(),
			entity.getId()
		};
		return update(sql, args);
	}

	@Override
	public AdminRole getAdminRoleById(String id){
		String sql = "select * from admin_role where id = ?";
		return (AdminRole) findForObject(sql, new Object[] { id }, new MultiRow());
	}

//	@Override
//	public Map<String, AdminRole> getAdminRoleMapByIds(String[] ids){
//		String sql = "select * from admin_role where id in";
//		return findForInSQL(sql, null, ids, new MapRow());
//	}

	@Override
	public List<AdminRole> getAdminRoleList(){
		String sql = "select * from admin_role where state <> 0 order by is_sys desc, display_order";
		return find(sql, null, new MultiRow());
	}

	@Override
	public List<AdminRole> getAdminRolePage(PageDto page){
		String sql = "select * from admin_role";
		return findForPage(sql, null, new MultiRow(), page);
	}
	
	@Override
	public List<String> getAdminRole(String state) {
		String sql = "select id from admin_role where state = ?";
		return query(sql, new Object[]{state }, new MultiRow("id", String.class));
	}

	@Override
	public List<AdminRole> getAdminRoleByName(String roleName) {
		String sql = "select * from admin_role where name like ? and state <> 0";
		return find(sql, new Object[] { "%" + roleName + "%" }, new MultiRow());
	}
}
