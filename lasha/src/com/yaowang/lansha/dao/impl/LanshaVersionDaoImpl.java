package com.yaowang.lansha.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Repository;

import com.yaowang.common.dao.BaseDaoImpl;
import com.yaowang.common.dao.PageDto;
import com.yaowang.lansha.dao.LanshaVersionDao;
import com.yaowang.lansha.entity.LanshaVersion;

/**
 * app版本表 
 * @author 
 * 
 */
@SuppressWarnings("unchecked")
@Repository("lanshaVersionDao")
public class LanshaVersionDaoImpl extends BaseDaoImpl<LanshaVersion> implements LanshaVersionDao{
	@Override
	public LanshaVersion setField(ResultSet rs) throws SQLException{
		LanshaVersion entity = new LanshaVersion();
		entity.setId(rs.getString("id"));
		entity.setVersion(rs.getString("version"));
		entity.setName(rs.getString("name"));
		entity.setSize(rs.getString("size"));
		entity.setUpdateLog(rs.getString("update_log"));
		entity.setIsForce(rs.getString("is_force"));
		entity.setOnlineTime(rs.getTimestamp("online_time"));
		entity.setStatus(rs.getString("status"));
		entity.setAddress(rs.getString("address"));
		entity.setOsType(rs.getString("os_type"));
		entity.setAppType(rs.getString("app_type"));
		return entity;
	}
	
	@Override
	public LanshaVersion save(LanshaVersion entity){
		String sql = "insert into lansha_version(id, version, name, size, update_log, is_force, online_time, status, address,os_type, app_type) values(?,?,?,?,?,?,?,?,?,?,?)"; 
		Object[] args = new Object[]{
			entity.getId(), entity.getVersion(), 
            entity.getName(), entity.getSize(), 
            entity.getUpdateLog(), entity.getIsForce(), 
            entity.getOnlineTime(), entity.getStatus(), 
            entity.getAddress(),entity.getOsType(),
            entity.getAppType()
		};
		update(sql, args);
		return entity;
	}
	
	@Override
	public Integer delete(String[] ids){
		String sql = "delete from lansha_version where id in";
		return executeUpdateForInSQL(sql, null, ids);
	}
	
	@Override
	public Integer update(LanshaVersion entity){
		String sql = "update lansha_version set version = ?, name = ?, size = ?, update_log = ?, is_force = ?, online_time = ?, status = ?, address = ? ,os_type =?, app_type = ? where id = ?";
		Object[] args = new Object[]{
				entity.getVersion(), entity.getName(), 
            entity.getSize(), entity.getUpdateLog(), 
            entity.getIsForce(), entity.getOnlineTime(), 
            entity.getStatus(), entity.getAddress() ,
            entity.getOsType(), entity.getAppType(),
            entity.getId()
		};
		return update(sql, args);
	}

	@Override
	public LanshaVersion getVersionById(String id){
		String sql = "select * from lansha_version where id = ?";
		return (LanshaVersion) findForObject(sql, new Object[] { id }, new MultiRow<LanshaVersion>());
	}

	@Override
	public Map<Integer, LanshaVersion> getVersionMapByIds(Integer[] ids){
		String sql = "select * from lansha_version where id in";
		return queryForMap(sql, null, ids, new MapIdRow<Integer, LanshaVersion>("id", Integer.class));
	}

	@Override
	public List<LanshaVersion> getVersionList(LanshaVersion entity){
		String sql = "select * from lansha_version where 1=1";
		List<Object> args = new ArrayList<Object>();
		if(entity != null){
			
		}
		return find(sql, args.toArray(), new MultiRow<LanshaVersion>());
	}

	@Override
	public List<LanshaVersion> getVersionPage(LanshaVersion entity,  Integer[] status, PageDto page, Date startTime, Date endTime) {
		StringBuilder sql = new StringBuilder("select * from lansha_version where 1=1");
		List<Object> args = new ArrayList<Object>();
		if (entity != null) {
			if (StringUtils.isNotBlank(entity.getStatus())) {
				sql.append(" and status = ?");
				args.add(entity.getStatus());
			}
			if (StringUtils.isNotBlank(entity.getName())) {
				sql.append(" and name = ?");
				args.add(entity.getName());
			}
			if (StringUtils.isNotBlank(entity.getOsType())) {
				sql.append(" and os_type = ?");
				args.add(entity.getOsType());
			}
			if (StringUtils.isNotBlank(entity.getVersion())) {
				sql.append(" and version = ?");
				args.add(entity.getVersion());
			}
			if (StringUtils.isNotBlank(entity.getAppType())) {
				sql.append(" and app_type = ?");
				args.add(entity.getAppType());
			}
		}
		if (startTime != null) {
			sql.append(" and online_time  >= ?");
			args.add(startTime);
		}
		if (endTime != null) {
			sql.append(" and online_time  <= ?");
			args.add(endTime);
		}
		
		sql.append(" order by online_time desc");
		if (page == null) {
			return find(sql.toString(), args.toArray(), new MultiRow<LanshaVersion>());
		} else {
			return findForPage(sql.toString(), args.toArray(), new MultiRow<LanshaVersion>(), page);
		}
	}

	@Override
	public void updateStatus(String[] ids, String status) {
		String sql = "update lansha_version set status = ? where id in";
		executeUpdateForInSQL(sql, new Object[] { status }, ids);
	}
	
}
