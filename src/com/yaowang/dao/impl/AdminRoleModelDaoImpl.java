package com.yaowang.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Repository;

import com.yaowang.common.dao.BaseDaoImpl;
import com.yaowang.common.dao.PageDto;
import com.yaowang.dao.AdminRoleModelDao;
import com.yaowang.entity.AdminRoleModel;

/**
 * 角色对应权限表 
 * @author 
 * 
 */
@SuppressWarnings("unchecked")
@Repository("adminRoleModelDao")
public class AdminRoleModelDaoImpl extends BaseDaoImpl<AdminRoleModel> implements AdminRoleModelDao{
	@Override
	public AdminRoleModel setField(ResultSet rs) throws SQLException{
		AdminRoleModel entity = new AdminRoleModel();
		entity.setId(rs.getString("id"));
		entity.setRoleId(rs.getString("role_id"));
		entity.setModelId(rs.getString("model_id"));
		return entity;
	}
	
	@Override
	public AdminRoleModel save(AdminRoleModel entity){
		String sql = "insert into admin_role_model(id, role_id, model_id) values(?,?,?)"; 
		if (StringUtils.isBlank(entity.getId())){
			entity.setId(createId());
		}
		Object[] args = new Object[]{
			entity.getId(), entity.getRoleId(), 
			entity.getModelId()
		};
		update(sql, args);
		return entity;
	}
	
	@Override
	public Integer delete(String[] ids){
		String sql = "delete from admin_role_model where id in";
		return executeUpdateForInSQL(sql, null, ids);
	}
	
	@Override
	public Integer deleteByRoleId(String id) {
		String sql = "delete from admin_role_model where role_id = ?";
		return update(sql, new Object[]{id });
	}
	
	@Override
	public Integer update(AdminRoleModel entity){
		String sql = "update admin_role_model set role_id = ?, model_id = ? where id = ?";
		Object[] args = new Object[]{
			entity.getRoleId(), entity.getModelId(), 
			entity.getId()
		};
		return update(sql, args);
	}

	@Override
	public AdminRoleModel getAdminRoleModelById(String id){
		String sql = "select * from admin_role_model where id = ?";
		return (AdminRoleModel) findForObject(sql, new Object[] { id }, new MultiRow());
	}

//	@Override
//	public Map<String, AdminRoleModel> getAdminRoleModelMapByIds(String[] ids){
//		String sql = "select * from admin_role_model where id in";
//		return findForInSQL(sql, null, ids, new MapRow());
//	}

	@Override
	public List<String> getAdminRoleModelList(String[] roleIds){
		String sql = "select model_id from admin_role_model where role_id in";
		
		Object[] o = getInSQLObj(null, roleIds);
		return query(sql+(String)o[0], (Object[])o[1], new MultiRow("model_id", String.class));
	}

	@Override
	public List<AdminRoleModel> getAdminRoleModelPage(PageDto page){
		String sql = "select * from admin_role_model";
		return findForPage(sql, null, new MultiRow(), page);
	}
	
}
