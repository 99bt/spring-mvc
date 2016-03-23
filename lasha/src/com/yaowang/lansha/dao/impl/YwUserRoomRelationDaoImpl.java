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
import com.yaowang.lansha.dao.YwUserRoomRelationDao;
import com.yaowang.lansha.entity.YwUserRoomRelation;
import com.yaowang.util.DateUtils;

/**
 * 房间关注 
 * @author 
 * 
 */
@SuppressWarnings("unchecked")
@Repository("ywUserRoomRelationDao")
public class YwUserRoomRelationDaoImpl extends BaseDaoImpl<YwUserRoomRelation> implements YwUserRoomRelationDao{
	@Override
	public YwUserRoomRelation setField(ResultSet rs) throws SQLException{
		YwUserRoomRelation entity = new YwUserRoomRelation();
		entity.setId(rs.getString("id"));
		entity.setUid(rs.getString("uid"));
		entity.setRoomId(rs.getString("room_id"));
		entity.setStatus(rs.getInt("status"));
		entity.setCreateTime(rs.getTimestamp("create_time"));
		return entity;
	}
	
	@Override
	public YwUserRoomRelation save(YwUserRoomRelation entity){
		String sql = "insert into yw_user_room_relation(id, uid, room_id, status, create_time) values(?,?,?,?,?)"; 
		if (StringUtils.isBlank(entity.getId())){
			entity.setId(createId());
		}
		Object[] args = new Object[]{
			entity.getId(), entity.getUid(), 
            entity.getRoomId(), entity.getStatus(), 
            entity.getCreateTime()
		};
		update(sql, args);
		return entity;
	}
	
	@Override
	public Integer delete(String[] ids){
		String sql = "delete from yw_user_room_relation where id in";
		return executeUpdateForInSQL(sql, null, ids);
	}

    @Override
    public Integer updateStatusById(Integer status,String id) {
        String sql = "update yw_user_room_relation set status = ? where id = ?";
        Object[] args = new Object[]{status,id};
        return update(sql, args);
    }

    @Override
	public Integer update(YwUserRoomRelation entity){
		String sql = "update yw_user_room_relation set uid = ?, room_id = ?, status = ?, create_time = ? where id = ?";
		Object[] args = new Object[]{
			entity.getUid(), entity.getRoomId(), 
            entity.getStatus(), entity.getCreateTime(), 
            entity.getId()
		};
		return update(sql, args);
	}

	@Override
	public YwUserRoomRelation getYwUserRoomRelationById(String id){
		String sql = "select * from yw_user_room_relation where id = ?";
		return (YwUserRoomRelation) findForObject(sql, new Object[] { id }, new MultiRow<YwUserRoomRelation>());
	}

	@Override
	public Map<String, YwUserRoomRelation> getYwUserRoomRelationMapByIds(String[] ids){
		String sql = "select * from yw_user_room_relation where id in";
		return queryForMap(sql, null, ids, new MapIdRow<String, YwUserRoomRelation>("id", String.class));
	}

	@Override
	public List<YwUserRoomRelation> getYwUserRoomRelationList(YwUserRoomRelation entity){
		String sql = "select * from yw_user_room_relation where 1=1";
		List<Object> args = new ArrayList<Object>();
		if(entity != null){
			if (StringUtils.isNotBlank(entity.getRoomId())) {
				sql += " and room_id = ?";
				args.add(entity.getRoomId());
			}
			if (StringUtils.isNotBlank(entity.getUid())) {
				sql += " and uid = ?";
				args.add(entity.getUid());
			}
            if (entity.getStatus()!=null) {
                sql += " and status = ?";
                args.add(entity.getStatus());
            }
		}
		return find(sql, args.toArray(), new MultiRow<YwUserRoomRelation>());
	}

	@Override
	public List<YwUserRoomRelation> getYwUserRoomRelationPage(YwUserRoomRelation entity, PageDto page){
		StringBuilder sql = new StringBuilder("select * from yw_user_room_relation where 1=1");
		List<Object> args = new ArrayList<Object>();
		if(entity != null){
			if(StringUtils.isNotBlank(entity.getUid())){
				sql.append(" and uid = ?");
				args.add(entity.getUid());
			}
			if(entity.getStatus() != null){
				sql.append(" and status = ?");
				args.add(entity.getStatus());
			}
		}
		
		sql.append(" order by create_time desc");
		
		if(page == null){
			return find(sql.toString(), args.toArray(), new MultiRow<YwUserRoomRelation>());
		}else{
			return findForPage(sql.toString(), args.toArray(), new MultiRow<YwUserRoomRelation>(), page);
		}
	}
	
	@Override
	public Integer getSumRelationNumber(YwUserRoomRelation relation) {
		StringBuilder sql = new StringBuilder("select count(*) from yw_user_room_relation where 1=1");
		List<Object> args = new ArrayList<Object>();
		if(relation != null){
			if (relation.getCreateTime() != null) {
				sql.append(" and create_time >= ? and create_time <= ?");
				args.add(DateUtils.getStartDate(relation.getCreateTime()));
				args.add(DateUtils.getEndDate(relation.getCreateTime()));
			}
			if (StringUtils.isNotBlank(relation.getUid())) {
				sql.append(" and uid = ?");
				args.add(relation.getUid());
			}
			if (StringUtils.isNotBlank(relation.getRoomId())) {
				sql.append(" and room_id = ?");
				args.add(relation.getRoomId());
			}
            if(relation.getStatus() != null){
                sql.append(" and status = ?");
                args.add(relation.getStatus());
            }

		}
		
		return queryForInt(sql.toString(), args.toArray());
	}
}
