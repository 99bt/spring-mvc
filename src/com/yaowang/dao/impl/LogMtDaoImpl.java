package com.yaowang.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Repository;

import com.yaowang.common.dao.BaseDaoImpl;
import com.yaowang.common.dao.PageDto;
import com.yaowang.common.dao.BaseDaoImpl.MapIdRow;
import com.yaowang.common.dao.BaseDaoImpl.MultiRow;
import com.yaowang.dao.LogMtDao;
import com.yaowang.entity.LogMt;

/**
 * log_mt 
 * @author 
 * 
 */
@SuppressWarnings("unchecked")
@Repository("logMtDao")
public class LogMtDaoImpl extends BaseDaoImpl<LogMt> implements LogMtDao{
	@Override
	public LogMt setField(ResultSet rs) throws SQLException{
		LogMt entity = new LogMt();
		entity.setId(rs.getInt("id"));
		entity.setTelphone(rs.getString("telphone"));
		entity.setTime(rs.getTimestamp("time"));
		entity.setIp(rs.getString("ip"));
		entity.setContent(rs.getString("content"));
		entity.setCode(rs.getString("code"));
		entity.setStatus(rs.getString("status"));
		entity.setError(rs.getString("error"));
		return entity;
	}
	
	@Override
	public LogMt save(LogMt entity){
		String sql = "insert into log_mt(telphone, time, ip, content, code, status, error) values(?,?,?,?,?,?,?)"; 
		Object[] args = new Object[]{
			entity.getTelphone(),  entity.getTime(),
			entity.getIp(), entity.getContent(),
			entity.getCode(), entity.getStatus(),
			entity.getError()
		};
		update(sql, args);
		return entity;
	}
	
	@Override
	public Integer delete(Integer[] ids){
		String sql = "delete from log_mt where id in";
		return executeUpdateForInSQL(sql, null, ids);
	}
	
	@Override
	public Integer update(LogMt entity){
		String sql = "update log_mt set telphone = ?, time = ?, ip = ?, content = ?, code = ?, status = ? where id = ?";
		Object[] args = new Object[]{
			entity.getTelphone(), entity.getTime(), 
			entity.getIp(), entity.getContent(), 
			entity.getCode(), entity.getStatus(), 
			entity.getId()
		};
		return update(sql, args);
	}
	
	@Override
	public Integer updateStatus(LogMt entity) {
		String sql = "update log_mt set status = ? where id = ?";
		Object[] args = new Object[]{
			entity.getStatus(), entity.getId()
		};
		return update(sql, args);
	}

	@Override
	public LogMt getLogMtById(Integer id){
		String sql = "select * from log_mt where id = ?";
		return (LogMt) findForObject(sql, new Object[] { id }, new MultiRow());
	}

	@Override
	public Map<Integer, LogMt> getLogMtMapByIds(Integer[] ids){
		String sql = "select * from log_mt where id in";
		return queryForMap(sql, null, ids, new MapIdRow<String, LogMt>("id", Integer.class));
	}

	@Override
	public List<LogMt> getLogMtList(){
		String sql = "select * from log_mt";
		return find(sql, null, new MultiRow());
	}

	@Override
	public List<LogMt> getLogMtPage(LogMt mt, Date startTime,Date endTime,PageDto page){
		String sql = "select * from log_mt where 1=1";
		List<Object> args = new ArrayList<Object>();
		if (mt != null) {
			if(StringUtils.isNotBlank(mt.getIp())){
				sql += " and ip=?";
				args.add(mt.getIp());
			}
			if(StringUtils.isNotBlank(mt.getTelphone())){
				sql += " and telphone=?";
				args.add(mt.getTelphone());
			}
		}
		if(startTime != null){
			sql += " and time >=?";
			args.add(startTime);
		}
		if(endTime != null){
			sql += " and time <=?";
			args.add(endTime);
		}
		sql += " order by time desc";
		
		return findForPage(sql, args.toArray(), new MultiRow(), page);
	}

	@Override
	public LogMt getLogMtByTelphone(String telphone) {
		//取第一条
		PageDto page = new PageDto();
		page.setCount(false);
		page.setRowNum(1);
		
		List<LogMt> list = findForPage("select * from log_mt where telphone=? order by time desc", new Object[]{telphone}, new MultiRow(), page);
		if(CollectionUtils.isEmpty(list)){
			return null;
		}
		return list.get(0);
	}
	
	@Override
	public Integer getLogCount(LogMt entity, Date startTime, Date endTime) {
		String sql = "select count(1) from log_mt where status <> ?";
		List<Object> args = new ArrayList<Object>();
		args.add("2");
		if(entity != null){
			if (StringUtils.isNotBlank(entity.getIp())) {
				sql += " and ip=?";
				args.add(entity.getIp());
			}
		}
		if(startTime != null){
			sql += " and time >=?";
			args.add(startTime);
		}
		if(endTime != null){
			sql += " and time <=?";
			args.add(endTime);
		}
		return queryForInt(sql, args.toArray());
	}
}
