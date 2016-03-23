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
import com.yaowang.lansha.dao.YwUserRoomAdminDao;
import com.yaowang.lansha.entity.YwUserRoomAdmin;

/**
 * 热门推荐房间 
 * @author 
 * 
 */
@SuppressWarnings("unchecked")
@Repository("ywUserRoomAdminDao")
public class YwUserRoomAdminDaoImpl extends BaseDaoImpl<YwUserRoomAdmin> implements YwUserRoomAdminDao{
	@Override
	public YwUserRoomAdmin setField(ResultSet rs) throws SQLException{
		YwUserRoomAdmin entity = new YwUserRoomAdmin();
		entity.setId(rs.getString("id"));
		entity.setRoomId(rs.getString("room_id"));
		entity.setUserId(rs.getString("user_id"));
		entity.setBi(rs.getInt("bi"));
		entity.setTimeLength(rs.getInt("time_length"));
		entity.setNickname(rs.getString("nickname"));
		entity.setHeadpic(rs.getString("headpic"));
		entity.setCreateTime(rs.getTimestamp("create_time"));
		return entity;
	}
	
	@Override
	public YwUserRoomAdmin save(YwUserRoomAdmin entity){
		String sql = "insert into yw_user_room_admin(id, room_id, user_id, bi, time_length, nickname, headpic, create_time) values(?,?,?,?,?,?,?,?)"; 
		if (StringUtils.isBlank(entity.getId())){
			entity.setId(createId());
		}
		Object[] args = new Object[]{
			entity.getId(), entity.getRoomId(), 
            entity.getUserId(), entity.getBi(), 
            entity.getTimeLength(), entity.getNickname(), 
            entity.getHeadpic(), entity.getCreateTime()
		};
		update(sql, args);
		return entity;
	}
	
	@Override
	public Integer delete(String[] ids){
		String sql = "delete from yw_user_room_admin where id in";
		return executeUpdateForInSQL(sql, null, ids);
	}
	
	@Override
	public Integer update(YwUserRoomAdmin entity){
		String sql = "update yw_user_room_admin set room_id = ?, user_id = ?, bi = ?, time_length = ?, nickname = ?, headpic = ?, create_time = ? where id = ?";
		Object[] args = new Object[]{
			entity.getRoomId(), entity.getUserId(), 
            entity.getBi(), entity.getTimeLength(), 
            entity.getNickname(), entity.getHeadpic(), 
            entity.getCreateTime(), entity.getId()
		};
		return update(sql, args);
	}

	@Override
	public YwUserRoomAdmin getYwUserRoomAdminById(String id){
		String sql = "select * from yw_user_room_admin where id = ?";
		return (YwUserRoomAdmin) findForObject(sql, new Object[] { id }, new MultiRow<YwUserRoomAdmin>());
	}

	@Override
	public Map<String, YwUserRoomAdmin> getYwUserRoomAdminMapByIds(String[] ids){
		String sql = "select * from yw_user_room_admin where id in";
		return queryForMap(sql, null, ids, new MapIdRow<String, YwUserRoomAdmin>("id", String.class));
	}

	@Override
	public List<YwUserRoomAdmin> getYwUserRoomAdminList(YwUserRoomAdmin entity){
		StringBuilder sql = new StringBuilder("select * from yw_user_room_admin where 1=1");
		List<Object> args = new ArrayList<Object>();
		if(entity != null){
			if(StringUtils.isNotBlank(entity.getRoomId())){
				sql.append(" and room_id = ?");
				args.add(entity.getRoomId());
			}
			if(StringUtils.isNotBlank(entity.getUserId())){
				sql.append(" and user_id = ?");
				args.add(entity.getUserId());
			}
		}
		return find(sql.toString(), args.toArray(), new MultiRow<YwUserRoomAdmin>());
	}

	@Override
	public List<YwUserRoomAdmin> getYwUserRoomAdminPage(YwUserRoomAdmin entity, PageDto page){
		String sql = "select * from yw_user_room_admin where 1=1";
		List<Object> args = new ArrayList<Object>();
		if(entity != null){
			
		}
		return findForPage(sql, args.toArray(), new MultiRow<YwUserRoomAdmin>(), page);
	}

	@Override
	public void deleteRoomAdminById(String roomId, String manageId) {
		String sql = "delete from yw_user_room_admin where room_id = ? and id = ? ";
		update(sql, new Object[] { roomId, manageId });
	}

	@Override
	public YwUserRoomAdmin getYwUserRoomAdminByRoomIdAndUserId(String roomId, String uid) {
		if(StringUtils.isBlank(roomId) || StringUtils.isBlank(uid)){
			return null;
		}
		String sql = "select * from yw_user_room_admin where room_id = ? and user_id = ?";
		return findForObject(sql, new Object[] { roomId, uid },new MultiRow<YwUserRoomAdmin>());
	}
	
}
