package com.yaowang.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Repository;

import com.yaowang.common.dao.BaseDaoImpl;
import com.yaowang.common.dao.PageDto;
import com.yaowang.common.dao.SQLUtils;
import com.yaowang.dao.SysModelDao;
import com.yaowang.entity.SysModel;

/**
 * 后台模块 
 * @author 
 * 
 */
@SuppressWarnings("unchecked")
@Repository("sysModelDao")
public class SysModelDaoImpl extends BaseDaoImpl<SysModel> implements SysModelDao{
	@Override
	public SysModel setField(ResultSet rs) throws SQLException{
		SysModel entity = new SysModel();
		entity.setId(rs.getString("id"));
		entity.setParentId(rs.getString("parent_id"));
		entity.setName(rs.getString("name"));
		entity.setImg(rs.getString("img"));
		entity.setIsUse(rs.getBoolean("is_use"));
		entity.setOrderId(rs.getString("order_id"));
		entity.setCreateTime(rs.getTimestamp("create_time"));
		entity.setUrl(rs.getString("url"));
		return entity;
	}
	
	@Override
	public SysModel save(SysModel entity){
		String sql = "insert into sys_model(id, parent_id, name, img, is_use, order_id, create_time) values(?,?,?,?,?,?,?)"; 
		if (StringUtils.isBlank(entity.getId())){
			entity.setId(createId());
		}
		Object[] args = new Object[]{
			entity.getId(), entity.getParentId(), 
			entity.getName(), entity.getImg(), 
			entity.getIsUse(), entity.getOrderId(), 
			entity.getCreateTime()
		};
		update(sql, args);
		return entity;
	}
	
	@Override
	public Integer delete(String[] ids){
		String sql = "delete from sys_model where id in";
		return executeUpdateForInSQL(sql, null, ids);
	}
	
	@Override
	public Integer update(SysModel entity){
		String sql = "update sys_model set parent_id = ?, name = ?, img = ?, is_use = ?, order_id = ?, create_time = ? where id = ?";
		Object[] args = new Object[]{
			entity.getParentId(), entity.getName(), 
			entity.getImg(), entity.getIsUse(), 
			entity.getOrderId(), entity.getCreateTime(), 
			entity.getId()
		};
		return update(sql, args);
	}

	@Override
	public SysModel getSysModelById(String id){
		String sql = "select * from sys_model where id = ?";
		return (SysModel) findForObject(sql, new Object[] { id }, new MultiRow());
	}

//	@Override
//	public Map<String, SysModel> getSysModelMapByIds(String[] ids){
//		String sql = "select * from sys_model where id in";
//		return findForInSQL(sql, null, ids, new MapRow());
//	}

	@Override
	public List<SysModel> getSysModelList(List<String> ids){
		String sql = "select * from sys_model where is_use = '1'";
		if (ids != null) {
			sql += " and id in " + SQLUtils.toSQLInString(ids.toArray(new String[]{}));
		}
		sql += " order by order_id";
		return find(sql, null, new MultiRow());
	}

	@Override
	public List<SysModel> getSysModelPage(PageDto page){
		String sql = "select * from sys_model";
		return findForPage(sql, null, new MultiRow(), page);
	}

	@Override
	public Map<String, SysModel> getSysModelMapByIds(String[] ids) {
		String sql = "select * from sys_model where id in";
		return queryForMap(sql, null, ids, new MapIdRow<String, SysModel>("id", String.class));
	}
	
}
