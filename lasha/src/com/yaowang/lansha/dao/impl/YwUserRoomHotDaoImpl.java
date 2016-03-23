package com.yaowang.lansha.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.yaowang.common.dao.BaseDaoImpl;
import com.yaowang.common.dao.PageDto;
import com.yaowang.lansha.dao.YwUserRoomHotDao;
import com.yaowang.lansha.entity.YwUserRoomHot;

/**
 * 热门推荐房间 
 * @author 
 * 
 */
@SuppressWarnings("unchecked")
@Repository("ywUserRoomHotDao")
public class YwUserRoomHotDaoImpl extends BaseDaoImpl<YwUserRoomHot> implements YwUserRoomHotDao{
	@Override
	public YwUserRoomHot setField(ResultSet rs) throws SQLException{
		YwUserRoomHot entity = new YwUserRoomHot();
		entity.setId(rs.getString("id"));
		entity.setRoomId(rs.getString("room_id"));
		entity.setOrderId(rs.getInt("order_id"));
        entity.setType(rs.getInt("type"));
		entity.setCreateTime(rs.getTimestamp("create_time"));
		return entity;
	}
	
	@Override
	public YwUserRoomHot save(YwUserRoomHot entity){
		String sql = "insert into yw_user_room_hot(id, room_id, order_id, create_time,type) values(?,?,?,?,?)";
		if (StringUtils.isBlank(entity.getId())){
			entity.setId(createId());
		}
		Object[] args = new Object[]{
			entity.getId(), entity.getRoomId(), 
            entity.getOrderId(), entity.getCreateTime(),entity.getType()
		};
		update(sql, args);
		return entity;
	}
	
	@Override
	public Integer delete(String[] ids){
		String sql = "delete from yw_user_room_hot where id in";
		return executeUpdateForInSQL(sql, null, ids);
	}
	
	@Override
	public Integer update(YwUserRoomHot entity){
		String sql = "update yw_user_room_hot set room_id = ?, order_id = ?, create_time = ? where id = ?";
		Object[] args = new Object[]{
			entity.getRoomId(), entity.getOrderId(), 
            entity.getCreateTime(), entity.getId()
		};
		return update(sql, args);
	}

	@Override
	public YwUserRoomHot getYwUserRoomHotById(String id){
		String sql = "select * from yw_user_room_hot where id = ? ";
		return (YwUserRoomHot) findForObject(sql, new Object[] { id}, new MultiRow<YwUserRoomHot>());
	}

	@Override
	public Map<String, YwUserRoomHot> getYwUserRoomHotMapByIds(String[] ids){
		String sql = "select * from yw_user_room_hot where id in";
		return queryForMap(sql, null, ids, new MapIdRow<String, YwUserRoomHot>("id", String.class));
	}

	@Override
	public List<YwUserRoomHot> getYwUserRoomHotList(YwUserRoomHot entity){
		String sql = "select * from yw_user_room_hot where 1=1";
		List<Object> args = new ArrayList<Object>();
		if(entity != null){
			
		}
		return find(sql, args.toArray(), new MultiRow<YwUserRoomHot>());
	}

	@Override
	public List<YwUserRoomHot> getYwUserRoomHotPage(YwUserRoomHot entity, PageDto page){
		StringBuilder sql = new StringBuilder("select * from yw_user_room_hot where 1=1");
		List<Object> args = new ArrayList<Object>();
		if(entity != null){

		}
		
		sql.append(" order by order_id");
		
		if(page == null){
			return find(sql.toString(), args.toArray(), new MultiRow<YwUserRoomHot>());
		}else{
			return findForPage(sql.toString(), args.toArray(), new MultiRow<YwUserRoomHot>(), page);
		}
	}

	@Override
	public void deleteByRoomId(String[] ids) {
		String sql = "delete from yw_user_room_hot where room_id in ";
		executeUpdateForInSQL(sql, null, ids);
	}

	@Override
	public YwUserRoomHot getYwUserRoomHotByRoomId(String roomId,Integer type) {
		String sql = "select * from yw_user_room_hot where room_id = ? and type=?";
		return findForObject(sql, new Object[] { roomId,type}, new MultiRow<YwUserRoomHot>());
	}

	@Override
	public List<Map<String, Object>> listMapRootHot(Integer type,PageDto page) {
		String sql = "SELECT h.id, h.room_id as roomId, h.order_id as orderId, h.create_time as createTime, r.uid, r.`name`,"
				+ " r.id_int as idInt, r.`online`, r.number, r.fans, r.intro, r.notice,"
				+ " r.game_id as gameId, r.report_num as reportNum, r.time_length as timeLength, r.start_time as startTime,"
				+ " r.base_number as baseNumber, r.multiple_number as multipleNumber FROM yw_user_room_hot h"
				+ " left join yw_user_room r on h.room_id = r.id where h.type =? order by h.order_id, r.number*r.multiple_number+r.base_number desc, h.create_time desc";
		return findListMapForPage(sql, new Object[]{type}, page);
	}

	@Override
	public Map<String, YwUserRoomHot> getMapUserRoomHotByRoomIds(String[] ids) {
		String sql = "select * from yw_user_room_hot where type=1 and room_id in";
		return queryForMap(sql, null, ids, new MapIdRow<String, YwUserRoomHot>("room_id", String.class));
	}

    @Override
    public Map<String, YwUserRoomHot> getMapUserRoomDaShenByRoomIds(String[] ids) {
        String sql = "select * from yw_user_room_hot where type=2 and room_id in";
        return queryForMap(sql, null, ids, new MapIdRow<String, YwUserRoomHot>("room_id", String.class));
    }

    @Override
    public Map<String, YwUserRoomHot> getMapUserRoomNvShenByRoomIds(String[] ids) {
        String sql = "select * from yw_user_room_hot where type=3 and room_id in";
        return queryForMap(sql, null, ids, new MapIdRow<String, YwUserRoomHot>("room_id", String.class));
    }


    public List<YwUserRoomHot> listRoomByType(Integer online,Integer type) {
        String sql = "select r.id as id,r.live_img as live_img,r.name as name,h.order_id as order_id FROM yw_user_room r inner join yw_user_room_hot h ON r.id = h.room_id  where r.online=? and h.type=?";
        return find(sql.toString(), new Object[]{online,type}, new RowMapper<YwUserRoomHot>() {
            @Override
            public YwUserRoomHot mapRow(ResultSet rs, int arg1) throws SQLException {
                YwUserRoomHot entity = new YwUserRoomHot();
                entity.setRoomId(rs.getString("id"));
                entity.setLiveImg(rs.getString("live_img"));
                entity.setRoomName(rs.getString("name"));
                entity.setOrderId(rs.getInt("order_id"));
                return entity;
            }
        });
    }

	@Override
	public Integer randRoomInHotWithOnline() {
		String sql = "SELECT  room.id_int FROM (SELECT room_id,RAND() AS rand FROM yw_user_room_hot) AS hot "
				+ "LEFT JOIN yw_user_room AS room ON hot.room_id = room.id "
				+ "WHERE room.`online` = '1' ORDER BY  hot.rand limit 1";
		return this.queryForInt(sql);
	}

}
