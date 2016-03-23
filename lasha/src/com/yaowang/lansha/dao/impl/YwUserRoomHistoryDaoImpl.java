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
import com.yaowang.lansha.dao.YwUserRoomHistoryDao;
import com.yaowang.lansha.entity.YwUserRoomHistory;
import com.yaowang.util.DateUtils;

/**
 * 房间浏览记录
 * @author 
 * 
 */
@SuppressWarnings("unchecked")
@Repository("ywUserRoomHistoryDao")
public class YwUserRoomHistoryDaoImpl extends BaseDaoImpl<YwUserRoomHistory> implements YwUserRoomHistoryDao{
	@Override
	public YwUserRoomHistory setField(ResultSet rs) throws SQLException{
		YwUserRoomHistory entity = new YwUserRoomHistory();
		entity.setId(rs.getString("id"));
		entity.setUid(rs.getString("uid"));
		entity.setRoomId(rs.getString("room_id"));
		entity.setStatus(rs.getInt("status"));
		entity.setCreateTime(rs.getTimestamp("create_time"));
		entity.setTimeLength(rs.getInt("time_length"));
		entity.setBi(rs.getInt("bi"));
		entity.setTypeId(rs.getString("type_id"));
		return entity;
	}
	
	@Override
	public YwUserRoomHistory save(YwUserRoomHistory entity){
		String sql = "insert into yw_user_room_history(id, uid, room_id, status, create_time, time_length, bi, type_id) values(?,?,?,?,?,?,?,?)"; 
		if (StringUtils.isBlank(entity.getId())){
			entity.setId(createId());
		}
		Object[] args = new Object[]{
			entity.getId(), entity.getUid(), 
            entity.getRoomId(), entity.getStatus(), 
            entity.getCreateTime(), entity.getTimeLength(), 
            entity.getBi(), entity.getTypeId()
		};
		update(sql, args);
		return entity;
	}
	
	@Override
	public Integer delete(String[] ids){
		String sql = "delete from yw_user_room_history where id in";
		return executeUpdateForInSQL(sql, null, ids);
	}
	
	@Override
	public Integer update(YwUserRoomHistory entity){
		String sql = "update yw_user_room_history set uid = ?, room_id = ?, status = ?, create_time = ?, time_length = ?, bi = ?, type_id = ? where id = ?";
		Object[] args = new Object[]{
			entity.getUid(), entity.getRoomId(), 
            entity.getStatus(), entity.getCreateTime(), 
            entity.getTimeLength(), entity.getBi(), 
            entity.getTypeId(), entity.getId()
		};
		return update(sql, args);
	}

	@Override
	public YwUserRoomHistory getYwUserRoomHistoryById(String id){
		String sql = "select * from yw_user_room_history where id = ?";
		return (YwUserRoomHistory) findForObject(sql, new Object[] { id }, new MultiRow<YwUserRoomHistory>());
	}

	@Override
	public Map<String, YwUserRoomHistory> getYwUserRoomHistoryMapByIds(String[] ids){
		String sql = "select * from yw_user_room_history where id in";
		return queryForMap(sql, null, ids, new MapIdRow<String, YwUserRoomHistory>("id", String.class));
	}

	@Override
	public List<YwUserRoomHistory> getYwUserRoomHistoryList(YwUserRoomHistory entity){
		String sql = "select * from yw_user_room_history where 1=1";
		List<Object> args = new ArrayList<Object>();
		if(entity != null){
			
		}
		sql += " order by create_time desc";
		return find(sql, args.toArray(), new MultiRow<YwUserRoomHistory>());
	}

	@Override
	public Integer getYwUserRoomHistoryPageCount(YwUserRoomHistory entity, Integer[] status, Date startTime, Date endTime) {
		StringBuilder sql = new StringBuilder("select count(*) from yw_user_room_history where 1=1");
		List<Object> args = buildUserRoomHistory(entity, sql, startTime, endTime);
		return queryForInt(sql.toString(), args.toArray());
	}

	@Override
	public List<YwUserRoomHistory> getYwUserRoomHistoryPage(YwUserRoomHistory entity, Integer[] status,	PageDto page, Date startTime, Date endTime) {
		StringBuilder sql = new StringBuilder("select * from yw_user_room_history where 1=1");
		List<Object> args = buildUserRoomHistory(entity, sql, startTime, endTime);
		if (page == null) {
			return find(sql.toString(), args.toArray(), new MultiRow<YwUserRoomHistory>());
		} else {
			return findForPage(sql.toString(), args.toArray(), new MultiRow<YwUserRoomHistory>(), page);
		}
	}
	
	private List<Object> buildUserRoomHistory(YwUserRoomHistory entity, StringBuilder sql, Date startTime, Date endTime){
		List<Object> args = new ArrayList<Object>();
		if (entity != null) {
			if (StringUtils.isNotBlank(entity.getUid())) {
				sql.append(" and uid = ?");
				args.add(entity.getUid());
			}
			if (StringUtils.isNotBlank(entity.getRoomId())) {
				sql.append(" and room_id = ?");
				args.add(entity.getRoomId());
			}
			if (entity.getStatus() != null) {
				sql.append(" and status = ?");
				args.add(entity.getStatus());
			}
			if(StringUtils.isNotBlank(entity.getTypeId())){
				sql.append(" and type_id = ?");
				args.add(entity.getTypeId());
			}
		}

		if (startTime != null) {
			sql.append(" and create_time >= ?");
			args.add(startTime);
		}
		if (endTime != null) {
			sql.append(" and create_time <= ?");
			args.add(endTime);
		}
		
		if (entity == null || StringUtils.isEmpty(entity.getOrder())) {
			sql.append(" order by create_time desc");
		}else {
			sql.append(" order by ").append(entity.getOrder());
		}
		return args;
	}
	
	@Override
	public Integer updateTimeLength(YwUserRoomHistory entity) {
		String sql = "update yw_user_room_history set status = 3, time_length = 0, bi = 0 where uid = ? and id <> ? and status = 1 and type_id = ?";
		return update(sql, new Object[]{entity.getUid(), entity.getId(), entity.getTypeId()});
	}

	@Override
	public List<String> getRoomIdPage(YwUserRoomHistory entity, PageDto page) {
		StringBuilder sql = new StringBuilder("select room_id, max(create_time) as create_time from yw_user_room_history where 1= 1 and status <> 0");
		List<Object> args = new ArrayList<Object>();
		if (entity != null) {
			if (StringUtils.isNotBlank(entity.getUid())) {
				sql.append(" and uid = ?");
				args.add(entity.getUid());
			}
			if (StringUtils.isNotBlank(entity.getRoomId())) {
				sql.append(" and room_id = ?");
				args.add(entity.getRoomId());
			}
		}
		
		sql.append(" group by room_id order by create_time desc ");
		
		if (page == null) {
			return query(sql.toString(), args.toArray(),
					new MultiRow<String>("room_id", String.class));
		} else {
			return findStringForPage(sql.toString(), args.toArray(), new MultiRow<String>("room_id", String.class), page);
		}
	}

	@Override
	public Integer getYwUserRoomHistoryCount(YwUserRoomHistory entity, Date startTime, Date endTime) {
		StringBuilder sql = new StringBuilder("select count(1) from yw_user_room_history where 1=1");
		List<Object> args = new ArrayList<Object>();
		if (entity != null) {
			if (StringUtils.isNotBlank(entity.getUid())) {
				sql.append(" and uid = ?");
				args.add(entity.getUid());
			}
			if(StringUtils.isNotBlank(entity.getTypeId())){
				sql.append(" and type_id = ?");
				args.add(entity.getTypeId());
			}
		}

		if (startTime != null) {
			sql.append(" and create_time >= ?");
			args.add(startTime);
		}
		if (endTime != null) {
			sql.append(" and create_time <= ?");
			args.add(endTime);
		}
		return queryForInt(sql.toString(), args.toArray());
	}
	
	@Override
	public Integer getSumUserNumber(YwUserRoomHistory entity) {
		StringBuilder sql = new StringBuilder("select count(distinct uid) from yw_user_room_history where 1=1");
		List<Object> args = new ArrayList<Object>();
		if (entity != null) {
			if(StringUtils.isNotBlank(entity.getRoomId())){
				sql.append(" and room_id = ?");
				args.add(entity.getRoomId());
			}
			if(StringUtils.isNotBlank(entity.getTypeId())){
				sql.append(" and type_id = ?");
				args.add(entity.getTypeId());
			}
			if (entity.getCreateTime() != null) {
				sql.append(" and create_time >= ? and create_time <= ?");
				args.add(DateUtils.getStartDate(entity.getCreateTime()));
				args.add(DateUtils.getEndDate(entity.getCreateTime()));
			}
		}

		return queryForInt(sql.toString(), args.toArray());
	}
}
