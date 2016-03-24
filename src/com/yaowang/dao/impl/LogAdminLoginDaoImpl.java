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
import com.yaowang.dao.LogAdminLoginDao;
import com.yaowang.entity.LogAdminLogin;

/**
 * 管理员登录日志表 
 * @author 
 * 
 */
@Repository("logAdminLoginDao")
public class LogAdminLoginDaoImpl extends BaseDaoImpl<LogAdminLogin> implements LogAdminLoginDao{
	@Override
	public LogAdminLogin setField(ResultSet rs) throws SQLException{
		LogAdminLogin entity = new LogAdminLogin();
		entity.setId(rs.getString("id"));
		entity.setUserId(rs.getString("user_id"));
		entity.setLoginTime(rs.getTimestamp("login_time"));
		entity.setLoginIp(rs.getString("login_ip"));
		return entity;
	}
	
	@Override
	public LogAdminLogin save(LogAdminLogin entity){
		String sql = "insert into log_admin_login(id, user_id, login_time, login_ip) values(?,?,?,?)"; 
		if (StringUtils.isBlank(entity.getId())){
			entity.setId(createId());
		}
		Object[] args = new Object[]{
			entity.getId(), entity.getUserId(), 
			entity.getLoginTime(), entity.getLoginIp()
		};
		update(sql, args);
		return entity;
	}
	
	

	@Override
	public Integer delete(String[] ids){
		String sql = "delete from log_admin_login where id in";
		return executeUpdateForInSQL(sql, null, ids);
	}
	
	@Override
	public Integer update(LogAdminLogin entity){
		String sql = "update log_admin_login set user_id = ?, login_time = ?, login_ip = ? where id = ?";
		Object[] args = new Object[]{
			entity.getUserId(), entity.getLoginTime(), 
			entity.getLoginIp(), entity.getId()
		};
		return update(sql, args);
	}

	@Override
	public LogAdminLogin getLogAdminLoginById(String id){
		String sql = "select * from log_admin_login where id = ?";
		return (LogAdminLogin) findForObject(sql, new Object[] { id }, new MultiRow<LogAdminLogin>());
	}

//	@Override
//	public Map<String, LogAdminLogin> getLogAdminLoginMapByIds(String[] ids){
//		String sql = "select * from log_admin_login where id in";
//		return findForInSQL(sql, null, ids, new MapRow());
//	}

	@Override
	public List<LogAdminLogin> getLogAdminLoginList(){
		String sql = "select * from log_admin_login";
		return find(sql, null, new MultiRow<LogAdminLogin>());
	}

	@Override
	public List<LogAdminLogin> getLogAdminLoginPage(String userId, Date startTime, Date endTime, PageDto page){
		String sql = "select * from log_admin_login where 1 = 1";
		List<Object> args = new ArrayList<Object>();
		if (startTime != null) {
			sql += " and login_time >= ?";
			args.add(startTime);
		}
		if (endTime != null) {
			sql += " and login_time <= ?";
			args.add(endTime);
		}
		if (StringUtils.isNotBlank(userId)) {
			sql += " and user_id = ?";
			args.add(userId);
		}
		sql += " order by login_time desc";
		return findForPage(sql, args.toArray(), new MultiRow<LogAdminLogin>(), page);
	}
	
	@Override
	public Integer getLogAdminLoginNumb(String userId, Date startTime, Date endTime) {
		String sql = "select count(*) from log_admin_login where 1 = 1";
		List<Object> args = new ArrayList<Object>();
		if (startTime != null) {
			sql += " and login_time >= ?";
			args.add(startTime);
		}
		if (endTime != null) {
			sql += " and login_time <= ?";
			args.add(endTime);
		}
		if (StringUtils.isNotBlank(userId)) {
			sql += " and user_id = ?";
			args.add(userId);
		}
		return queryForInt(sql, args.toArray());
	}
}
