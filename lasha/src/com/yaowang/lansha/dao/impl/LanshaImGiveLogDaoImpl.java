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
import com.yaowang.lansha.dao.LanshaImGiveLogDao;
import com.yaowang.lansha.entity.LanshaImGiveLog;
import com.yaowang.util.DateUtils;

/**
 * 发言得虾米记录 
 * @author 
 * 
 */
@SuppressWarnings("unchecked")
@Repository("lanshaImGiveLogDao")
public class LanshaImGiveLogDaoImpl extends BaseDaoImpl<LanshaImGiveLog> implements LanshaImGiveLogDao{
	@Override
	public LanshaImGiveLog setField(ResultSet rs) throws SQLException{
		LanshaImGiveLog entity = new LanshaImGiveLog();
		entity.setId(rs.getString("id"));
		entity.setRoomId(rs.getString("room_id"));
		entity.setUserId(rs.getString("user_id"));
		entity.setXiami(rs.getInt("xiami"));
		entity.setCreateTime(rs.getTimestamp("create_time"));
		return entity;
	}
	
	@Override
	public LanshaImGiveLog save(LanshaImGiveLog entity){
		String sql = "insert into lansha_im_give_log(id, room_id, user_id, xiami, create_time) values(?,?,?,?,?)"; 
		if (StringUtils.isBlank(entity.getId())){
			entity.setId(createId());
		}
		Object[] args = new Object[]{
			entity.getId(), entity.getRoomId(), 
            entity.getUserId(), entity.getXiami(), 
            entity.getCreateTime()
		};
		update(sql, args);
		return entity;
	}
	
	@Override
	public Integer delete(String[] ids){
		String sql = "delete from lansha_im_give_log where id in";
		return executeUpdateForInSQL(sql, null, ids);
	}
	
	@Override
	public Integer update(LanshaImGiveLog entity){
		String sql = "update lansha_im_give_log set room_id = ?, user_id = ?, xiami = ?, create_time = ? where id = ?";
		Object[] args = new Object[]{
			entity.getRoomId(), entity.getUserId(), 
            entity.getXiami(), entity.getCreateTime(), 
            entity.getId()
		};
		return update(sql, args);
	}

	@Override
	public LanshaImGiveLog getLanshaImGiveLogById(String id){
		String sql = "select * from lansha_im_give_log where id = ?";
		return (LanshaImGiveLog) findForObject(sql, new Object[] { id }, new MultiRow<LanshaImGiveLog>());
	}

	@Override
	public Map<String, LanshaImGiveLog> getLanshaImGiveLogMapByIds(String[] ids){
		String sql = "select * from lansha_im_give_log where id in";
		return queryForMap(sql, null, ids, new MapIdRow<String, LanshaImGiveLog>("id", String.class));
	}

	@Override
	public List<LanshaImGiveLog> getLanshaImGiveLogList(LanshaImGiveLog entity){
		String sql = "select * from lansha_im_give_log where 1=1";
		List<Object> args = new ArrayList<Object>();
		if(entity != null){
			
		}
		return find(sql, args.toArray(), new MultiRow<LanshaImGiveLog>());
	}

	@Override
	public List<LanshaImGiveLog> getLanshaImGiveLogPage(LanshaImGiveLog entity, PageDto page){
		String sql = "select * from lansha_im_give_log where 1=1";
		List<Object> args = new ArrayList<Object>();
		if(entity != null){
			
		}
		return findForPage(sql, args.toArray(), new MultiRow<LanshaImGiveLog>(), page);
	}
	
	@Override
	public Integer getGiveNumber(LanshaImGiveLog entity) {
		String sql = "select count(*) from lansha_im_give_log where 1=1";
		List<Object> args = new ArrayList<Object>();
		if(entity != null){
			if (StringUtils.isNotBlank(entity.getUserId())) {
				sql += " and user_id = ?";
				args.add(entity.getUserId());
			}
			if (entity.getCreateTime() != null) {
				sql += " and create_time >= ? and create_time <= ?";
				args.add(DateUtils.getStartDate(entity.getCreateTime()));
				args.add(DateUtils.getEndDate(entity.getCreateTime()));
			}
		}
		return queryForInt(sql, args.toArray());
	}
}
