package com.yaowang.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.yaowang.common.dao.BaseDaoImpl;
import com.yaowang.common.dao.PageDto;
import com.yaowang.dao.LogEmailDao;
import com.yaowang.entity.LogEmail;

/**
 * log_email 
 * @author 
 * 
 */
@SuppressWarnings("unchecked")
@Repository("logEmailDao")
public class LogEmailDaoImpl extends BaseDaoImpl<LogEmail> implements LogEmailDao{
	@Override
	public LogEmail setField(ResultSet rs) throws SQLException{
		LogEmail entity = new LogEmail();
		entity.setId(rs.getInt("id"));
		entity.setEmail(rs.getString("email"));
		entity.setTime(rs.getTimestamp("time"));
		entity.setTitle(rs.getString("title"));
		entity.setContent(rs.getString("content"));
		entity.setError(rs.getString("error"));
		entity.setStatus(rs.getString("status"));
		return entity;
	}
	
	@Override
	public LogEmail save(LogEmail entity){
		String sql = "insert into log_email(id, email, time, title, content, error, status) values(?,?,?,?,?,?,?)"; 
		Object[] args = new Object[]{
			entity.getId(), entity.getEmail(), 
			entity.getTime(), entity.getTitle(), 
			entity.getContent(), entity.getError(), 
			entity.getStatus()
		};
		update(sql, args);
		return entity;
	}
	
	@Override
	public Integer delete(Integer[] ids){
		String sql = "delete from log_email where id in";
		return executeUpdateForInSQL(sql, null, ids);
	}
	
	@Override
	public Integer update(LogEmail entity){
		String sql = "update log_email set email = ?, time = ?, title = ?, content = ?, error = ?, status = ? where id = ?";
		Object[] args = new Object[]{
			entity.getEmail(), entity.getTime(), 
			entity.getTitle(), entity.getContent(), 
			entity.getError(), entity.getStatus(), 
			entity.getId()
		};
		return update(sql, args);
	}

	@Override
	public LogEmail getLogEmailById(Integer id){
		String sql = "select * from log_email where id = ?";
		return (LogEmail) findForObject(sql, new Object[] { id }, new MultiRow());
	}

	@Override
	public Map<Integer, LogEmail> getLogEmailMapByIds(Integer[] ids){
		String sql = "select * from log_email where id in";
		return queryForMap(sql, null, ids, new MapIdRow<String, LogEmail>("id", Integer.class));
	}

	@Override
	public List<LogEmail> getLogEmailList(){
		String sql = "select * from log_email";
		return find(sql, null, new MultiRow());
	}

	@Override
	public List<LogEmail> getLogEmailPage(Date startTime, Date endTime, PageDto page){
		String sql = "select * from log_email where 1=1";
		List<Object> args = new ArrayList<Object>();
		if (startTime != null) {
			sql += " and time >= ?";
			args.add(startTime);
		}
		if (endTime != null) {
			sql += " and time <= ?";
			args.add(endTime);
		}
		sql += " order by time desc";
		return findForPage(sql, args.toArray(), new MultiRow(), page);
	}

	@Override
	public void updateLogEmailByCondition(String email, String title) {
		String sql = "update log_email set status = 1 where status = 0 and email = ? and title = ? ";
		Object[] args = new Object[] { email, title };
		update(sql, args);
	}
	
}
