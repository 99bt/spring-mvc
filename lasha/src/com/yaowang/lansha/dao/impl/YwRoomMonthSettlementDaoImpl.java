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
import com.yaowang.lansha.dao.YwRoomMonthSettlementDao;
import com.yaowang.lansha.entity.YwRoomMonthSettlement;

/**
 * 房间月结 
 * @author 
 * 
 */
@SuppressWarnings("unchecked")
@Repository("ywRoomMonthSettlementDao")
public class YwRoomMonthSettlementDaoImpl extends BaseDaoImpl<YwRoomMonthSettlement> implements YwRoomMonthSettlementDao{
	@Override
	public YwRoomMonthSettlement setField(ResultSet rs) throws SQLException{
		YwRoomMonthSettlement entity = new YwRoomMonthSettlement();
		entity.setMonth(rs.getString("month"));
		entity.setRoomId(rs.getString("room_id"));
		entity.setDayNum(rs.getInt("day_num"));
		entity.setBonus(rs.getFloat("bonus"));
		entity.setForfeit(rs.getFloat("forfeit"));
		entity.setDue(rs.getFloat("due"));
		entity.setReal(rs.getFloat("real"));
        entity.setUserId(rs.getString("user_id"));
        entity.setLiveTime(rs.getInt("live_time"));
		entity.setStatus(rs.getString("status"));
		return entity;
	}
	
	@Override
	public YwRoomMonthSettlement save(YwRoomMonthSettlement entity){
		String sql = "insert into yw_room_month_settlement(month, room_id,user_id, day_num, bonus, forfeit, due, real,live_time, status) values(?,?,?,?,?,?,?,?,?,?)";
		if (StringUtils.isBlank(entity.getRoomId())){
			entity.setRoomId(createId());
		}
		Object[] args = new Object[]{
			entity.getMonth(), entity.getRoomId(),entity.getUserId(),
            entity.getDayNum(), entity.getBonus(), 
            entity.getForfeit(), entity.getDue(), 
            entity.getReal(),entity.getLiveTime(), entity.getStatus()
		};
		update(sql, args);
		return entity;
	}
	
	@Override
	public Integer delete(String[] ids){
		String sql = "delete from yw_room_month_settlement where room_id in";
		return executeUpdateForInSQL(sql, null, ids);
	}
	
	@Override
	public Integer update(YwRoomMonthSettlement entity){
		String sql = "update yw_room_month_settlement set room_id = ?,user_id=?, day_num = ?, bonus = ?, forfeit = ?, due = ?, real = ?,live_time=?, status = ? where room_id = ?";
		Object[] args = new Object[]{
			entity.getRoomId(),entity.getUserId(), entity.getDayNum(),
            entity.getBonus(), entity.getForfeit(), 
            entity.getDue(), entity.getReal(),entity.getLiveTime(),
            entity.getStatus(), entity.getRoomId()
		};
		return update(sql, args);
	}

	@Override
	public YwRoomMonthSettlement getYwRoomMonthSettlementById(String roomId){
		String sql = "select * from yw_room_month_settlement where room_id = ?";
		return (YwRoomMonthSettlement) findForObject(sql, new Object[] { roomId }, new MultiRow<YwRoomMonthSettlement>());
	}

	@Override
	public Map<String, YwRoomMonthSettlement> getYwRoomMonthSettlementMapByIds(String[] roomIds){
		String sql = "select * from yw_room_month_settlement where room_id in";
		return queryForMap(sql, null, roomIds, new MapIdRow<String, YwRoomMonthSettlement>("roomId", String.class));
	}

	@Override
	public List<YwRoomMonthSettlement> getYwRoomMonthSettlementList(YwRoomMonthSettlement entity){
		StringBuilder sql =new StringBuilder("select * from yw_room_month_settlement where 1=1");
		List<Object> args = new ArrayList<Object>();
		if(entity != null){
		    if(StringUtils.isNotEmpty(entity.getMonth()))
            {
                sql.append(" and month=?");
                args.add(entity.getMonth());
            }
            if(StringUtils.isNotEmpty(entity.getRoomId()))
            {
                sql.append(" and room_id=?");
                args.add(entity.getRoomId());
            }
            if(StringUtils.isNotEmpty(entity.getUserId()))
            {
                sql.append(" and user_id=?");
                args.add(entity.getUserId());
            }
		}
        sql.append(" order by due desc");
		return find(sql.toString(), args.toArray(), new MultiRow<YwRoomMonthSettlement>());
	}

	@Override
	public List<YwRoomMonthSettlement> getYwRoomMonthSettlementPage(YwRoomMonthSettlement entity, PageDto page){
        StringBuilder sql =new StringBuilder("select * from yw_room_month_settlement where 1=1");
		List<Object> args = new ArrayList<Object>();
		if(entity != null){
            if(entity != null){
                if(StringUtils.isNotEmpty(entity.getMonth()))
                {
                    sql.append(" and month=?");
                    args.add(entity.getMonth());
                }
                if(StringUtils.isNotEmpty(entity.getRoomId()))
                {
                    sql.append(" and room_id=?");
                    args.add(entity.getRoomId());
                }
                if(StringUtils.isNotEmpty(entity.getUserId()))
                {
                    sql.append(" and user_id=?");
                    args.add(entity.getUserId());
                }
            }
            sql.append(" order by due desc");
		}
		return findForPage(sql.toString(), args.toArray(), new MultiRow<YwRoomMonthSettlement>(), page);
	}

}
