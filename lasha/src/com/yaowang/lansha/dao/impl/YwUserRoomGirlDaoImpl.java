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
import com.yaowang.lansha.dao.YwUserRoomGirlDao;
import com.yaowang.lansha.entity.YwUserRoomGirl;

/**
 * 热门美女房间 
 * @author 
 * 
 */
@SuppressWarnings("unchecked")
@Repository("ywUserRoomGirlDao")
public class YwUserRoomGirlDaoImpl extends BaseDaoImpl<YwUserRoomGirl> implements YwUserRoomGirlDao{
	@Override
	public YwUserRoomGirl setField(ResultSet rs) throws SQLException{
		YwUserRoomGirl entity = new YwUserRoomGirl();
		entity.setId(rs.getString("id"));
		entity.setRoomId(rs.getString("room_id"));
		entity.setOrderId(rs.getInt("order_id"));
		entity.setCreateTime(rs.getTimestamp("create_time"));
		return entity;
	}
	
	@Override
	public YwUserRoomGirl save(YwUserRoomGirl entity){
		String sql = "insert into yw_user_room_girl(id, room_id, order_id, create_time) values(?,?,?,?)"; 
		if (StringUtils.isBlank(entity.getId())){
			entity.setId(createId());
		}
		Object[] args = new Object[]{
			entity.getId(), entity.getRoomId(), 
            entity.getOrderId(), entity.getCreateTime()
		};
		update(sql, args);
		return entity;
	}
	
	@Override
	public Integer delete(String[] ids){
		String sql = "delete from yw_user_room_girl where id in";
		return executeUpdateForInSQL(sql, null, ids);
	}
	
	@Override
	public Integer update(YwUserRoomGirl entity){
		String sql = "update yw_user_room_girl set room_id = ?, order_id = ?, create_time = ? where id = ?";
		Object[] args = new Object[]{
			entity.getRoomId(), entity.getOrderId(), 
            entity.getCreateTime(), entity.getId()
		};
		return update(sql, args);
	}

	@Override
	public YwUserRoomGirl getYwUserRoomGirlById(String id){
		String sql = "select * from yw_user_room_girl where id = ?";
		return (YwUserRoomGirl) findForObject(sql, new Object[] { id }, new MultiRow<YwUserRoomGirl>());
	}

	@Override
	public Map<String, YwUserRoomGirl> getYwUserRoomGirlMapByIds(String[] ids){
		String sql = "select * from yw_user_room_girl where id in";
		return queryForMap(sql, null, ids, new MapIdRow<String, YwUserRoomGirl>("id", String.class));
	}

	@Override
	public List<YwUserRoomGirl> getYwUserRoomGirlList(YwUserRoomGirl entity){
		String sql = "select * from yw_user_room_girl where 1=1";
		List<Object> args = new ArrayList<Object>();
		if(entity != null){
			
		}
		return find(sql, args.toArray(), new MultiRow<YwUserRoomGirl>());
	}

	@Override
	public List<YwUserRoomGirl> getYwUserRoomGirlPage(YwUserRoomGirl entity, PageDto page){
		String sql = "select * from yw_user_room_girl where 1=1";
		List<Object> args = new ArrayList<Object>();
		if(entity != null){
			
		}
		return findForPage(sql, args.toArray(), new MultiRow<YwUserRoomGirl>(), page);
	}
}
