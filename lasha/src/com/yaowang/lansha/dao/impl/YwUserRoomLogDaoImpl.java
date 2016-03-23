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
import com.yaowang.lansha.dao.YwUserRoomLogDao;
import com.yaowang.lansha.entity.YwUserRoomLog;
import com.yaowang.util.DateUtils;

/**
 * 直播间人数日志 
 * @author 
 * 
 */
@SuppressWarnings("unchecked")
@Repository("ywUserRoomLogDao")
public class YwUserRoomLogDaoImpl extends BaseDaoImpl<YwUserRoomLog> implements YwUserRoomLogDao{
	@Override
	public YwUserRoomLog setField(ResultSet rs) throws SQLException{
		YwUserRoomLog entity = new YwUserRoomLog();
		entity.setId(rs.getString("id"));
		entity.setRoomId(rs.getString("room_id"));
		entity.setUserId(rs.getString("user_id"));
		entity.setNumber(rs.getInt("number"));
		entity.setCreateTime(rs.getTimestamp("create_time"));
		return entity;
	}
	
	@Override
	public YwUserRoomLog save(YwUserRoomLog entity){
		String sql = "insert into yw_user_room_log(id, room_id, user_id, number, create_time) values(?,?,?,?,?)"; 
		if (StringUtils.isBlank(entity.getId())){
			entity.setId(createId());
		}
		Object[] args = new Object[]{
			entity.getId(), entity.getRoomId(), 
            entity.getUserId(), entity.getNumber(), 
            entity.getCreateTime()
		};
		update(sql, args);
		return entity;
	}
	
	@Override
	public Integer delete(String[] ids){
		String sql = "delete from yw_user_room_log where id in";
		return executeUpdateForInSQL(sql, null, ids);
	}
	
	@Override
	public Integer update(YwUserRoomLog entity){
		String sql = "update yw_user_room_log set room_id = ?, user_id = ?, number = ?, create_time = ? where id = ?";
		Object[] args = new Object[]{
			entity.getRoomId(), entity.getUserId(), 
            entity.getNumber(), entity.getCreateTime(), 
            entity.getId()
		};
		return update(sql, args);
	}

	@Override
	public YwUserRoomLog getYwUserRoomLogById(String id){
		String sql = "select * from yw_user_room_log where id = ?";
		return (YwUserRoomLog) findForObject(sql, new Object[] { id }, new MultiRow<YwUserRoomLog>());
	}

	@Override
	public Map<String, YwUserRoomLog> getYwUserRoomLogMapByIds(String[] ids){
		String sql = "select * from yw_user_room_log where id in";
		return queryForMap(sql, null, ids, new MapIdRow<String, YwUserRoomLog>("id", String.class));
	}

	@Override
	public List<YwUserRoomLog> getYwUserRoomLogList(YwUserRoomLog entity){
		StringBuffer sql = new StringBuffer("select * from yw_user_room_log where 1=1");
		List<Object> args = new ArrayList<Object>();
		if(entity != null){
			if(entity.getUserId() != null){
				sql.append(" and user_id = ?");
				args.add(entity.getUserId());
			}
		}
		return find(sql.toString(), args.toArray(), new MultiRow<YwUserRoomLog>());
	}

	@Override
	public List<YwUserRoomLog> getYwUserRoomLogPage(YwUserRoomLog entity, PageDto page){
		StringBuffer sql = new StringBuffer("select * from yw_user_room_log where 1=1");
		List<Object> args = new ArrayList<Object>();
		if(entity != null){
			if(entity.getUserId() != null){
				sql.append(" and user_id = ?");
				args.add(entity.getUserId());
			}
			if(entity.getCreateTime() != null){
				sql.append(" and create_time >= ?");
				args.add(DateUtils.getStartDate(entity.getCreateTime()));
				sql.append(" and create_time <= ?");
				args.add(DateUtils.getEndDate(entity.getCreateTime()));
			}
		}
		if(page == null){
			return find(sql.toString(), args.toArray(), new MultiRow<YwUserRoomLog>());
		}else{
			return findForPage(sql.toString(), args.toArray(), new MultiRow<YwUserRoomLog>(), page);
		}
	}

	@Override
	public List<YwUserRoomLog> report(YwUserRoomLog entity) {
		StringBuffer sql = new StringBuffer("select user_id, DATE_FORMAT(create_time, '%Y-%m-%d') createTime from yw_user_room_log where 1=1");
		List<Object> args = new ArrayList<Object>();
		if(entity != null){
			if(entity.getUserId() != null){
				sql.append(" and user_id = ?");
				args.add(entity.getUserId());
			}
			if(entity.getStartTime() != null){
				sql.append(" AND create_time >= ?");
				args.add(entity.getStartTime());
			}
			if(entity.getEndTime() != null){
				sql.append(" AND create_time <= ?");
				args.add(entity.getEndTime());
			}
		}
		sql.append(" group by user_id, createTime");
		return find(sql.toString(), args.toArray(), new MultiRow<YwUserRoomLog>(){

			@Override
			public YwUserRoomLog mapRow(ResultSet rs, int n)
					throws SQLException {
				YwUserRoomLog entity = new YwUserRoomLog();
				entity.setUserId(rs.getString("user_id"));
				entity.setCreateTime(rs.getTimestamp("createTime"));
				return entity;
			}
			
		});
	}
	
}
