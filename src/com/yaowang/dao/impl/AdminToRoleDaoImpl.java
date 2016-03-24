package com.yaowang.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Repository;

import com.yaowang.common.dao.BaseDaoImpl;
import com.yaowang.common.dao.PageDto;
import com.yaowang.dao.AdminToRoleDao;
import com.yaowang.entity.AdminToRole;

/**
 * 管理员对应角色表 
 * @author 
 * 
 */
@Repository("adminToRoleDao")
public class AdminToRoleDaoImpl extends BaseDaoImpl<AdminToRole> implements AdminToRoleDao{
	@Override
	public AdminToRole setField(ResultSet rs) throws SQLException{
		AdminToRole entity = new AdminToRole();
		entity.setId(rs.getString("id"));
		entity.setAdminId(rs.getString("admin_id"));
		entity.setRoleId(rs.getString("role_id"));
		entity.setCreateTime(rs.getTimestamp("create_time"));
		return entity;
	}
	
	@Override
	public AdminToRole save(AdminToRole entity){
		String sql = "insert into admin_to_role(id, admin_id, role_id, create_time) values(?,?,?,?)"; 
		if (StringUtils.isBlank(entity.getId())){
			entity.setId(createId());
		}
		Object[] args = new Object[]{
			entity.getId(), entity.getAdminId(), 
			entity.getRoleId(), entity.getCreateTime()
		};
		update(sql, args);
		return entity;
	}
	
	@Override
	public Integer delete(String[] ids){
		String sql = "delete from admin_to_role where id in";
		return executeUpdateForInSQL(sql, null, ids);
	}
	
	@Override
	public Integer deleteByUserId(String userId) {
		String sql = "delete from admin_to_role where admin_id = ?";
		return update(sql, new Object[]{userId });
	}
	
	@Override
	public Integer update(AdminToRole entity){
		String sql = "update admin_to_role set admin_id = ?, role_id = ?, create_time = ? where id = ?";
		Object[] args = new Object[]{
			entity.getAdminId(), entity.getRoleId(), 
			entity.getCreateTime(), entity.getId()
		};
		return update(sql, args);
	}

	@Override
	public AdminToRole getAdminToRoleById(String id){
		String sql = "select * from admin_to_role where id = ?";
		return (AdminToRole) findForObject(sql, new Object[] { id }, new MultiRow<AdminToRole>());
	}

//	@Override
//	public Map<String, AdminToRole> getAdminToRoleMapByIds(String[] ids){
//		String sql = "select * from admin_to_role where id in";
//		return findForInSQL(sql, null, ids, new MapRow());
//	}

	@Override
	public List<String> getAdminToRoleListByRoleId(String roleId){
		String sql = "select admin_id from admin_to_role where role_id = ?";
		return query(sql, new Object[]{roleId }, new MultiRow<String>("admin_id", String.class));
	}

	@Override
	public List<AdminToRole> getAdminToRolePage(PageDto page){
		String sql = "select * from admin_to_role";
		return findForPage(sql, null, new MultiRow<AdminToRole>(), page);
	}
	
	@Override
	public List<String> getAdminToRoleList(String userId) {
		String sql = "select role_id from admin_to_role where admin_id = ?";
		return query(sql, new Object[]{userId }, new MultiRow<String>("role_id", String.class));
	}
}
