package com.yaowang.lansha.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Repository;

import com.yaowang.common.dao.BaseDaoImpl;
import com.yaowang.common.dao.PageDto;
import com.yaowang.lansha.dao.ActivityUserLogDao;
import com.yaowang.lansha.entity.ActivityUserLog;

/**
 * 抽奖机会抽取日志 
 * @author 
 * 
 */
@SuppressWarnings("unchecked")
@Repository("activityUserLogDao")
public class ActivityUserLogDaoImpl extends BaseDaoImpl<ActivityUserLog> implements ActivityUserLogDao{
	@Override
	public ActivityUserLog setField(ResultSet rs) throws SQLException{
		ActivityUserLog entity = new ActivityUserLog();
		entity.setId(rs.getString("id"));
		entity.setUserId(rs.getString("user_id"));
		entity.setStock(rs.getInt("stock"));
		entity.setType(rs.getString("type"));
		entity.setRemark(rs.getString("remark"));
		entity.setCreateTime(rs.getTimestamp("create_time"));
		return entity;
	}
	
	@Override
	public ActivityUserLog save(ActivityUserLog entity){
		String sql = "insert into activity_user_log(id, user_id, stock, type, remark, create_time) values(?,?,?,?,?,?)"; 
		if (StringUtils.isBlank(entity.getId())){
			entity.setId(createId());
		}
		Object[] args = new Object[]{
			entity.getId(), entity.getUserId(), 
            entity.getStock(), entity.getType(), 
            entity.getRemark(), entity.getCreateTime()
		};
		update(sql, args);
		return entity;
	}
	
	@Override
	public Integer delete(String[] ids){
		String sql = "delete from activity_user_log where id in";
		return executeUpdateForInSQL(sql, null, ids);
	}
	
	@Override
	public Integer update(ActivityUserLog entity){
		String sql = "update activity_user_log set user_id = ?, stock = ?, type = ?, remark = ?, create_time = ? where id = ?";
		Object[] args = new Object[]{
			entity.getUserId(), entity.getStock(), 
            entity.getType(), entity.getRemark(), 
            entity.getCreateTime(), entity.getId()
		};
		return update(sql, args);
	}

	@Override
	public ActivityUserLog getActivityUserLogById(String id){
		String sql = "select * from activity_user_log where id = ?";
		return (ActivityUserLog) findForObject(sql, new Object[] { id }, new MultiRow<ActivityUserLog>());
	}

	@Override
	public Map<String, ActivityUserLog> getActivityUserLogMapByIds(String[] ids){
		String sql = "select * from activity_user_log where id in";
		return queryForMap(sql, null, ids, new MapIdRow<String, ActivityUserLog>("id", String.class));
	}

	@Override
	public List<ActivityUserLog> getActivityUserLogList(ActivityUserLog entity){
		String sql = "select * from activity_user_log where 1=1";
		List<Object> args = new ArrayList<Object>();
		if(entity != null){
			
		}
		return find(sql, args.toArray(), new MultiRow<ActivityUserLog>());
	}

	@Override
	public List<ActivityUserLog> getActivityUserLogPage(ActivityUserLog entity, PageDto page){
		String sql = "select * from activity_user_log where 1=1";
		List<Object> args = new ArrayList<Object>();
		if(entity != null){
			
		}
		return findForPage(sql, args.toArray(), new MultiRow<ActivityUserLog>(), page);
	}
	
}
