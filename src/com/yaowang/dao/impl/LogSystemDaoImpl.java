package com.yaowang.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Repository;

import com.yaowang.common.dao.BaseDaoImpl;
import com.yaowang.common.dao.PageDto;
import com.yaowang.dao.LogSystemDao;
import com.yaowang.entity.LogSystem;

/**
 * log_system 
 * @author 
 * 
 */
@Repository("logSystemDao")
public class LogSystemDaoImpl extends BaseDaoImpl<LogSystem> implements LogSystemDao{
	@Override
	public LogSystem setField(ResultSet rs) throws SQLException{
		LogSystem entity = new LogSystem();
		entity.setId(rs.getString("id"));
		entity.setType(rs.getString("type"));
		entity.setValue(rs.getString("value"));
		entity.setCreateTime(rs.getTimestamp("create_time"));
		return entity;
	}
	
	@Override
	public LogSystem save(LogSystem entity){
		String sql = "insert into log_system(id, type, value, create_time) values(?,?,?,?)"; 
		if (StringUtils.isBlank(entity.getId())){
			entity.setId(createId());
		}
		Object[] args = new Object[]{
			entity.getId(), entity.getType(), 
			entity.getValue(), entity.getCreateTime()
		};
		update(sql, args);
		return entity;
	}
	
	@Override
	public Integer delete(String[] ids){
		String sql = "delete from log_system where id in";
		return executeUpdateForInSQL(sql, null, ids);
	}
	
	@Override
	public Integer update(LogSystem entity){
		String sql = "update log_system set type = ?, value = ? where id = ?";
		Object[] args = new Object[]{
			entity.getType(), entity.getValue(), 
			entity.getId()
		};
		return update(sql, args);
	}

	@Override
	public LogSystem getLogSystemById(String id){
		String sql = "select * from log_system where id = ?";
		return (LogSystem) findForObject(sql, new Object[] { id }, new MultiRow<LogSystem>());
	}

//	@Override
//	public Map<String, LogSystem> getLogSystemMapByIds(String[] ids){
//		String sql = "select * from log_system where id in";
//		return findForInSQL(sql, null, ids, new MapRow());
//	}

	@Override
	public List<LogSystem> getLogSystemList(){
		String sql = "select * from log_system";
		return find(sql, null, new MultiRow<LogSystem>());
	}

	@Override
	public List<LogSystem> getLogSystemPage(LogSystem entity, Date startTime, Date endTime, PageDto page){
		String sql = "select * from log_system where 1 = 1";
		List<Object> args = new ArrayList<Object>();
		if (startTime != null) {
			sql += " and create_time >= ?";
			args.add(startTime);
		}
		if (endTime != null) {
			sql += " and create_time <= ?";
			args.add(endTime);
		}
		if (entity != null) {
			if (StringUtils.isNotBlank(entity.getType())) {
				sql += " and type = ?";
				args.add(entity.getType());
			}
			if (StringUtils.isNotBlank(entity.getValue())) {
				sql += " and value like ?";
				args.add("%"+entity.getValue()+"%");
			}
		}
		sql += " order by create_time desc";
		return findForPage(sql, args.toArray(), new MultiRow<LogSystem>(), page);
	}
	
	@Override
	public Integer getLogSystemNumb(LogSystem entity, Date startTime, Date endTime) {
		String sql = "select count(*) from log_system where 1 = 1";
		List<Object> args = new ArrayList<Object>();
		if (startTime != null) {
			sql += " and create_time >= ?";
			args.add(startTime);
		}
		if (endTime != null) {
			sql += " and create_time <= ?";
			args.add(endTime);
		}
		if (entity != null) {
			if (StringUtils.isNotBlank(entity.getType())) {
				sql += " and type = ?";
				args.add(entity.getType());
			}
			if (StringUtils.isNotBlank(entity.getValue())) {
				sql += " and value like ?";
				args.add("%"+entity.getValue()+"%");
			}
		}
		return queryForInt(sql, args.toArray());
	}
	
}
