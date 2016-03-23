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
import com.yaowang.lansha.dao.LogUserLoginDao;
import com.yaowang.lansha.entity.LogUserLogin;

/**
 * log_user_login 
 * @author 
 * 
 */
@SuppressWarnings("unchecked")
@Repository("logUserLoginDao")
public class LogUserLoginDaoImpl extends BaseDaoImpl<LogUserLogin> implements LogUserLoginDao{
	@Override
	public LogUserLogin setField(ResultSet rs) throws SQLException{
		LogUserLogin entity = new LogUserLogin();
		entity.setId(rs.getString("id"));
		entity.setUserId(rs.getString("user_id"));
		entity.setLoginTime(rs.getTimestamp("login_time"));
		entity.setLoginIp(rs.getString("login_ip"));
		return entity;
	}
	
	@Override
	public LogUserLogin save(LogUserLogin entity){
		String sql = "insert into log_user_login(id, user_id, login_time, login_ip,login_method) values(?,?,?,?,?)"; 
		if (StringUtils.isBlank(entity.getId())){
			entity.setId(createId());
		}
		Object[] args = new Object[]{
			entity.getId(), entity.getUserId(), 
            entity.getLoginTime(), entity.getLoginIp(),
            entity.getLoginMethod()
		};
		update(sql, args);
		return entity;
	}
	
	@Override
	public Integer delete(String[] ids){
		String sql = "delete from log_user_login where id in";
		return executeUpdateForInSQL(sql, null, ids);
	}
	
	@Override
	public Integer update(LogUserLogin entity){
		String sql = "update log_user_login set user_id = ?, login_time = ?, login_ip = ? where id = ?";
		Object[] args = new Object[]{
				entity.getUserId(), entity.getLoginTime(), 
            entity.getLoginIp(), entity.getId()
		};
		return update(sql, args);
	}

	@Override
	public LogUserLogin getLogUserLoginById(String id){
		String sql = "select * from log_user_login where id = ?";
		return (LogUserLogin) findForObject(sql, new Object[] { id }, new MultiRow<LogUserLogin>());
	}

	@Override
	public Map<String, LogUserLogin> getLogUserLoginMapByIds(String[] ids){
		String sql = "select * from log_user_login where id in";
		return queryForMap(sql, null, ids, new MapIdRow<String, LogUserLogin>("id", String.class));
	}

	@Override
	public List<LogUserLogin> getLogUserLoginList(){
		String sql = "select * from log_user_login";
		return find(sql, null, new MultiRow<LogUserLogin>());
	}

	@Override
	public List<LogUserLogin> getLogUserLoginPage(LogUserLogin entity, Date startTime, Date endTime, PageDto page){
		String sql = "select * from log_user_login where 1=1";
		List<Object> args = new ArrayList<Object>();
		if (startTime != null) {
			sql += " and login_time >= ?";
			args.add(startTime);
		}
		if (endTime != null) {
			sql += " and login_time <= ?";
			args.add(endTime);
		}
		if(entity != null){
			if (StringUtils.isNotBlank(entity.getUserId())) {
				sql += " and user_id = ?";
				args.add(entity.getUserId());
			}
		}
		sql += " order by login_time desc";
		return findForPage(sql, args.toArray(), new MultiRow<LogUserLogin>(), page);
	}

	@Override
	public List<LogUserLogin> getLogUserLoginByUserId(String user_id, Date startTime, Date endTime) {
		String sql = "select * from log_user_login where user_id = ?";
		List<Object> args = new ArrayList<Object>();
		args.add(user_id);
		if (startTime != null) {
			sql += " and login_time >= ?";
			args.add(startTime);
		}
		if (endTime != null) {
			sql += " and login_time <= ?";
			args.add(endTime);
		}
		return find(sql, args.toArray(), new MultiRow<LogUserLogin>());
	}

	@Override
	public Integer getLogUserLoginCountByUserId(String user_id, Date startTime,
			Date endTime) {
		String sql = "select count(1) from log_user_login where user_id = ?";
		List<Object> args = new ArrayList<Object>();
		args.add(user_id);
		if (startTime != null) {
			sql += " and login_time >= ?";
			args.add(startTime);
		}
		if (endTime != null) {
			sql += " and login_time <= ?";
			args.add(endTime);
		}
		return queryForInt(sql, args.toArray());
	}
	
	@Override
	public Integer getLogUserLoginCountByUserId(String user_id, Date startTime,
			Date endTime,String loginMethod) {
		StringBuilder sql = new StringBuilder("select count(1) from log_user_login where user_id = ?");
		List<Object> args = new ArrayList<Object>();
		args.add(user_id);
		if (startTime != null) {
			sql.append(" and login_time >= ?");
			args.add(startTime);
		}
		if (endTime != null) {
			sql.append(" and login_time <= ?");
			args.add(endTime);
		}
		if (loginMethod != null) {
			sql.append(" and login_method = ?");
			args.add(loginMethod);
		}
		return queryForInt(sql.toString(), args.toArray());
	}
	
}
