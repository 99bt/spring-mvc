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
import com.yaowang.lansha.dao.ActivityUserDao;
import com.yaowang.lansha.entity.ActivityUser;

/**
 * 用户抽奖机会 
 * @author 
 * 
 */
@SuppressWarnings("unchecked")
@Repository("activityUserDao")
public class ActivityUserDaoImpl extends BaseDaoImpl<ActivityUser> implements ActivityUserDao{
	@Override
	public ActivityUser setField(ResultSet rs) throws SQLException{
		ActivityUser entity = new ActivityUser();
		entity.setId(rs.getString("id"));
		entity.setRealname(rs.getString("realname"));
		entity.setAddress(rs.getString("address"));
		entity.setTelphone(rs.getString("telphone"));
		entity.setQq(rs.getString("qq"));
		entity.setCreateTime(rs.getTimestamp("create_time"));
		return entity;
	}
	
	@Override
	public ActivityUser save(ActivityUser entity){
		String sql = "insert into activity_user(id, realname, address, telphone, qq, create_time) values(?,?,?,?,?,?)"; 
		if (StringUtils.isBlank(entity.getId())){
			entity.setId(createId());
		}
		Object[] args = new Object[]{
			entity.getId(), entity.getRealname(), 
            entity.getAddress(), entity.getTelphone(), entity.getQq(),
            entity.getCreateTime()
		};
		update(sql, args);
		return entity;
	}
	
	@Override
	public Integer delete(String[] ids){
		String sql = "delete from activity_user where id in";
		return executeUpdateForInSQL(sql, null, ids);
	}
	
	@Override
	public Integer update(ActivityUser entity){
		String sql = "update activity_user set realname = ?, address = ?, telphone = ?, qq = ?, create_time = ? where id = ?";
		Object[] args = new Object[]{
			entity.getRealname(), entity.getAddress(), 
            entity.getTelphone(), entity.getQq(), 
            entity.getCreateTime(), entity.getId()
		};
		return update(sql, args);
	}
	
	@Override
	public Integer updateUserInfo(ActivityUser entity){
		String sql ="update activity_user set realname = ?, address = ?, telphone = ?, qq = ? where id = ?";
		Object[] args = new Object[]{
				entity.getRealname(),
				entity.getAddress(),
	            entity.getTelphone(),
	            entity.getQq(),
	            entity.getId()
			};
		return update(sql, args);
	}
	
	@Override
	public ActivityUser getActivityUserById(String id){
		String sql = "select * from activity_user where id = ?";
		return (ActivityUser) findForObject(sql, new Object[] { id }, new MultiRow<ActivityUser>());
	}

	@Override
	public Map<String, ActivityUser> getActivityUserMapByIds(String[] ids){
		String sql = "select * from activity_user where id in";
		return queryForMap(sql, null, ids, new MapIdRow<String, ActivityUser>("id", String.class));
	}

	@Override
	public List<ActivityUser> getActivityUserList(ActivityUser entity){
		String sql = "select * from activity_user where 1=1";
		List<Object> args = new ArrayList<Object>();
		if(entity != null){
			
		}
		return find(sql, args.toArray(), new MultiRow<ActivityUser>());
	}

	@Override
	public List<ActivityUser> getActivityUserPage(ActivityUser entity, PageDto page){
		String sql = "select * from activity_user where 1=1";
		List<Object> args = new ArrayList<Object>();
		if(entity != null){
			
		}
		return findForPage(sql, args.toArray(), new MultiRow<ActivityUser>(), page);
	}

}
