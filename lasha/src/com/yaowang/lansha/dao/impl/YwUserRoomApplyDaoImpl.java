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
import com.yaowang.lansha.dao.YwUserRoomApplyDao;
import com.yaowang.lansha.entity.YwUserRoomApply;

/**
 * 热门推荐房间 
 * @author 
 * 
 */
@SuppressWarnings("unchecked")
@Repository("ywUserRoomApplyDao")
public class YwUserRoomApplyDaoImpl extends BaseDaoImpl<YwUserRoomApply> implements YwUserRoomApplyDao{
	@Override
	public YwUserRoomApply setField(ResultSet rs) throws SQLException{
		YwUserRoomApply entity = new YwUserRoomApply();
		entity.setId(rs.getString("id"));
		entity.setUserId(rs.getString("user_id"));
		entity.setRealname(rs.getString("realname"));
		entity.setIdentitycard(rs.getString("identitycard"));
		entity.setPic1(rs.getString("pic1"));
		entity.setPic2(rs.getString("pic2"));
		entity.setPic3(rs.getString("pic3"));
		entity.setExpirationTime(rs.getTimestamp("expiration_time"));
		entity.setStatus(rs.getString("status"));
		entity.setCreateTime(rs.getTimestamp("create_time"));
		entity.setRemark(rs.getString("remark"));
		entity.setAduitUid(rs.getString("aduit_uid"));
		entity.setAduitTime(rs.getTimestamp("aduit_time"));
		entity.setRoomName(rs.getString("room_name"));
		entity.setGameId(rs.getString("game_id"));
		entity.setNotice(rs.getString("notice"));
		return entity;
	}
	
	@Override
	public YwUserRoomApply save(YwUserRoomApply entity){
		String sql = "insert into yw_user_room_apply(id, user_id, realname, identitycard, pic1, pic2, pic3, expiration_time, status, create_time, remark, aduit_uid, aduit_time, room_name, game_id, notice) values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)"; 
		if (StringUtils.isBlank(entity.getId())){
			entity.setId(createId());
		}
		Object[] args = new Object[]{
			entity.getId(), entity.getUserId(), 
            entity.getRealname(), entity.getIdentitycard(), 
            entity.getPic1(), entity.getPic2(), 
            entity.getPic3(), entity.getExpirationTime(), 
            entity.getStatus(), entity.getCreateTime(), 
            entity.getRemark(), entity.getAduitUid(), 
            entity.getAduitTime(), entity.getRoomName(),
            entity.getGameId(), entity.getNotice()
		};
		update(sql, args);
		return entity;
	}
	
	@Override
	public Integer delete(String[] ids){
		String sql = "delete from yw_user_room_apply where id in";
		return executeUpdateForInSQL(sql, null, ids);
	}
	
	@Override
	public Integer update(YwUserRoomApply entity){
		String sql = "update yw_user_room_apply set user_id = ?, realname = ?, identitycard = ?, pic1 = ?, pic2 = ?, pic3 = ?, expiration_time = ?, status = ?, remark = ?, aduit_uid = ?, aduit_time = ? where id = ?";
		Object[] args = new Object[]{
			entity.getUserId(), entity.getRealname(), 
            entity.getIdentitycard(), entity.getPic1(), 
            entity.getPic2(), entity.getPic3(), 
            entity.getExpirationTime(), entity.getStatus(), 
            entity.getRemark(), 
            entity.getAduitUid(), entity.getAduitTime(), 
            entity.getId()
		};
		return update(sql, args);
	}

	@Override
	public YwUserRoomApply getYwUserRoomApplyById(String id){
		String sql = "select * from yw_user_room_apply where id = ?";
		return (YwUserRoomApply) findForObject(sql, new Object[] { id }, new MultiRow<YwUserRoomApply>());
	}

	@Override
	public Map<String, YwUserRoomApply> getYwUserRoomApplyMapByIds(String[] ids){
		String sql = "select * from yw_user_room_apply where id in";
		return queryForMap(sql, null, ids, new MapIdRow<String, YwUserRoomApply>("id", String.class));
	}

	@Override
	public List<YwUserRoomApply> getYwUserRoomApplyList(YwUserRoomApply entity){
		StringBuilder sql = new StringBuilder("select * from yw_user_room_apply where 1=1");
		List<Object> args = new ArrayList<Object>();
		if(entity != null){
			if(StringUtils.isNotBlank(entity.getUserId())){
				sql.append(" and user_id = ?");
				args.add(entity.getUserId());
			}
		}
		return find(sql.toString(), args.toArray(), new MultiRow<YwUserRoomApply>());
	}

	@Override
	public List<YwUserRoomApply> getYwUserRoomApplyPage(YwUserRoomApply entity, Date startTime, Date endTime, PageDto page){
		StringBuffer sql = new StringBuffer("select * from yw_user_room_apply where 1=1");
		List<Object> args = new ArrayList<Object>();
		if(entity != null){
			if(StringUtils.isNotBlank(entity.getRealname())){
				sql.append(" and realname like ?");
				args.add("%" + entity.getRealname() + "%");
			}
			if(StringUtils.isNotBlank(entity.getUserId())){
				sql.append(" and user_id = ?");
				args.add(entity.getUserId());
			}
			if(StringUtils.isNotBlank(entity.getStatus())){
				sql.append(" and status = ?");
				args.add(entity.getStatus());
			}
		}
		if(startTime != null){
			sql.append(" and create_time >= ?");
			args.add(startTime);
		}
		if(endTime != null){
			sql.append(" and create_time <= ?");
			args.add(endTime);
		}
		sql.append(" order by create_time desc");
		return findForPage(sql.toString(), args.toArray(), new MultiRow<YwUserRoomApply>(), page);
	}

	@Override
	public Integer updateApply(YwUserRoomApply entity) {
		String sql = "update yw_user_room_apply set realname = ?, identitycard = ?, pic1 = ?, pic2 = ?, pic3 = ?, expiration_time = ?, status = ?, room_name = ?, game_id = ?, notice = ? where id = ?";
		Object[] args = new Object[]{
			entity.getRealname(), 
            entity.getIdentitycard(), entity.getPic1(), 
            entity.getPic2(), entity.getPic3(), 
            entity.getExpirationTime(), entity.getStatus(), 
            entity.getRoomName(), entity.getGameId(),
            entity.getNotice(), entity.getId()
		};
		return update(sql, args);
	}
	
}
