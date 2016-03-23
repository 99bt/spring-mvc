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
import com.yaowang.lansha.dao.LanshaRoomBlacklistDao;
import com.yaowang.lansha.entity.LanshaRoomBlacklist;

/**
 * 房间禁言用户 
 * @author 
 * 
 */
@SuppressWarnings("unchecked")
@Repository("lanshaRoomBlacklistDao")
public class LanshaRoomBlacklistDaoImpl extends BaseDaoImpl<LanshaRoomBlacklist> implements LanshaRoomBlacklistDao{
	@Override
	public LanshaRoomBlacklist setField(ResultSet rs) throws SQLException{
		LanshaRoomBlacklist entity = new LanshaRoomBlacklist();
		entity.setId(rs.getString("id"));
		entity.setRoomId(rs.getString("room_id"));
		entity.setUserId(rs.getString("user_id"));
		entity.setImRoom(rs.getString("im_room"));
		entity.setAdminId(rs.getString("admin_id"));
		entity.setType(rs.getString("type"));
		entity.setValidTime(rs.getTimestamp("valid_time"));
		entity.setCreateTime(rs.getTimestamp("create_time"));
		return entity;
	}
	
	@Override
	public LanshaRoomBlacklist save(LanshaRoomBlacklist entity){
		String sql = "insert into lansha_room_blacklist(id, room_id, user_id, im_room, admin_id, type, valid_time, create_time) values(?,?,?,?,?,?,?,?)"; 
		if (StringUtils.isBlank(entity.getId())){
			entity.setId(createId());
		}
		Object[] args = new Object[]{
			entity.getId(), entity.getRoomId(), 
            entity.getUserId(), entity.getImRoom(), 
            entity.getAdminId(), entity.getType(), 
            entity.getValidTime(), entity.getCreateTime()
		};
		update(sql, args);
		return entity;
	}
	
	@Override
	public Integer delete(String[] ids){
		String sql = "delete from lansha_room_blacklist where id in";
		return executeUpdateForInSQL(sql, null, ids);
	}
	
	@Override
	public Integer update(LanshaRoomBlacklist entity){
		String sql = "update lansha_room_blacklist set room_id = ?, user_id = ?, im_room = ?, admin_id = ?, type = ?, valid_time = ?, create_time = ? where id = ?";
		Object[] args = new Object[]{
			entity.getRoomId(), entity.getUserId(), 
            entity.getImRoom(), entity.getAdminId(), 
            entity.getType(), entity.getValidTime(), 
            entity.getCreateTime(), entity.getId()
		};
		return update(sql, args);
	}

	@Override
	public LanshaRoomBlacklist getLanshaRoomBlacklistById(String id){
		String sql = "select * from lansha_room_blacklist where id = ?";
		return (LanshaRoomBlacklist) findForObject(sql, new Object[] { id }, new MultiRow<LanshaRoomBlacklist>());
	}

	@Override
	public Map<String, LanshaRoomBlacklist> getLanshaRoomBlacklistMapByIds(String[] ids){
		String sql = "select * from lansha_room_blacklist where id in";
		return queryForMap(sql, null, ids, new MapIdRow<String, LanshaRoomBlacklist>("id", String.class));
	}

	@Override
	public List<LanshaRoomBlacklist> getLanshaRoomBlacklistList(LanshaRoomBlacklist entity){
		String sql = "select * from lansha_room_blacklist where 1=1";
		List<Object> args = new ArrayList<Object>();
		if(entity != null){
			if(StringUtils.isNotBlank(entity.getAdminId())){
				sql += " and admin_id = ?";
				args.add(entity.getAdminId());
			}
			if(StringUtils.isNotBlank(entity.getImRoom())){
				sql += " and im_room = ?";
				args.add(entity.getImRoom());
			}
			if(StringUtils.isNotBlank(entity.getRoomId())){
				sql += " and room_id = ?";
				args.add(entity.getRoomId());
			}
			if(StringUtils.isNotBlank(entity.getType())){
				sql += " and type = ?";
				args.add(entity.getType());
			}
			if(StringUtils.isNotBlank(entity.getUserId())){
				sql += " and user_id = ?";
				args.add(entity.getUserId());
			}
		}
		return find(sql, args.toArray(), new MultiRow<LanshaRoomBlacklist>());
	}

	@Override
	public List<LanshaRoomBlacklist> getLanshaRoomBlacklistPage(LanshaRoomBlacklist entity, PageDto page){
		String sql = "select * from lansha_room_blacklist where 1=1";
		List<Object> args = new ArrayList<Object>();
		if(entity != null){
			if(StringUtils.isNotBlank(entity.getAdminId())){
				sql += " and admin_id = ?";
				args.add(entity.getAdminId());
			}
			if(StringUtils.isNotBlank(entity.getImRoom())){
				sql += " and im_room = ?";
				args.add(entity.getImRoom());
			}
			if(StringUtils.isNotBlank(entity.getRoomId())){
				sql += " and room_id = ?";
				args.add(entity.getRoomId());
			}
			if(StringUtils.isNotBlank(entity.getType())){
				sql += " and type = ?";
				args.add(entity.getType());
			}
			if(StringUtils.isNotBlank(entity.getUserId())){
				sql += " and user_id = ?";
				args.add(entity.getUserId());
			}
			if(null != entity.getCreateTime()){
				sql += " and create_time = ?";
				args.add(entity.getCreateTime());
			}
			if(null != entity.getValidTime()){
				sql += " and valid_time = ?";
				args.add(entity.getValidTime());
			}
		}
		return findForPage(sql, args.toArray(), new MultiRow<LanshaRoomBlacklist>(), page);
	}

	@Override
	public Integer deleteByRoomIdAndUserId(String roomId, String userId) {
		if(StringUtils.isBlank(roomId) || StringUtils.isBlank(userId)){
			return 0;
		} 
		String sql = "delete from lansha_room_blacklist where room_id=? and user_id=?";
		return update(sql, new Object[]{roomId,userId});
	}
	
}
