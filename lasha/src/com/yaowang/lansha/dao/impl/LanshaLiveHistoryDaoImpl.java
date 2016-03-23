package com.yaowang.lansha.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.yaowang.lansha.entity.LanshaRoomRanking;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.yaowang.common.dao.BaseDaoImpl;
import com.yaowang.common.dao.PageDto;
import com.yaowang.lansha.dao.LanshaLiveHistoryDao;
import com.yaowang.lansha.entity.LanshaLiveHistory;
import com.yaowang.util.DateUtils;

/**
 * 主播开播记录
 * @author 
 * 
 */
@SuppressWarnings("unchecked")
@Repository("lanshaLiveHistoryDao")
public class LanshaLiveHistoryDaoImpl extends BaseDaoImpl<LanshaLiveHistory> implements LanshaLiveHistoryDao{
	@Override
	public LanshaLiveHistory setField(ResultSet rs) throws SQLException{
		LanshaLiveHistory entity = new LanshaLiveHistory();
		entity.setId(rs.getString("id"));
		entity.setRoomId(rs.getString("room_id"));
		entity.setStartTime(rs.getTimestamp("start_time"));
		entity.setEndTime(rs.getTimestamp("end_time"));
		entity.setLength(rs.getInt("length"));
		entity.setCreateTime(rs.getTimestamp("create_time"));
		return entity;
	}
	
	@Override
	public LanshaLiveHistory save(LanshaLiveHistory entity){
		String sql = "insert into lansha_live_history(id, room_id, start_time, end_time, length, create_time) values(?,?,?,?,?,?)"; 
		if (StringUtils.isBlank(entity.getId())){
			entity.setId(createId());
		}
		Object[] args = new Object[]{
			entity.getId(), entity.getRoomId(), 
            entity.getStartTime(), entity.getEndTime(), 
            entity.getLength(), entity.getCreateTime()
		};
		update(sql, args);
		return entity;
	}
	
	@Override
	public Integer delete(String[] ids){
		String sql = "delete from lansha_live_history where id in";
		return executeUpdateForInSQL(sql, null, ids);
	}
	
	@Override
	public Integer update(LanshaLiveHistory entity){
		String sql = "update lansha_live_history set room_id = ?, start_time = ?, end_time = ?, length = ?, create_time = ? where id = ?";
		Object[] args = new Object[]{
			entity.getRoomId(), entity.getStartTime(), 
            entity.getEndTime(), entity.getLength(), 
            entity.getCreateTime(), entity.getId()
		};
		return update(sql, args);
	}

	@Override
	public LanshaLiveHistory getLanshaLiveHistoryById(String id){
		String sql = "select * from lansha_live_history where id = ?";
		return (LanshaLiveHistory) findForObject(sql, new Object[] { id }, new MultiRow<LanshaLiveHistory>());
	}

	@Override
	public Map<String, LanshaLiveHistory> getLanshaLiveHistoryMapByIds(String[] ids){
		String sql = "select * from lansha_live_history where id in";
		return queryForMap(sql, null, ids, new MapIdRow<String, LanshaLiveHistory>("id", String.class));
	}

	@Override
	public List<LanshaLiveHistory> getLanshaLiveHistoryList(LanshaLiveHistory entity){
		String sql = "select * from lansha_live_history where 1=1";
		List<Object> args = new ArrayList<Object>();
		if(entity != null){
			
		}
		return find(sql, args.toArray(), new MultiRow<LanshaLiveHistory>());
	}

	@Override
	public List<LanshaLiveHistory> getLanshaLiveHistoryPage(LanshaLiveHistory entity, Date startTime, Date endTime, PageDto page) {
		StringBuilder sql = new StringBuilder("select * from lansha_live_history where 1=1");
		List<Object> args = new ArrayList<Object>();
		if (entity != null) {
			if(StringUtils.isNotBlank(entity.getRoomId())){
				sql.append(" and room_id = ?");
				args.add(entity.getRoomId());
			}
		}
		if (startTime != null) {
			sql.append(" and start_time >= ?");
			args.add(DateUtils.getStartDate(startTime));
		}
		if(endTime != null){
			sql.append(" and end_time <= ?");
			args.add(DateUtils.getEndDate(endTime));
		}
		sql.append(" order by room_id");
		
		if (page == null) {
			return find(sql.toString(), args.toArray(), new MultiRow<LanshaLiveHistory>());
		} else {
			return findForPage(sql.toString(), args.toArray(), new MultiRow<LanshaLiveHistory>(), page);
		}
	}

	@Override
	public LanshaLiveHistory getLanshaLiveHistoryByRoomId(String roomId) {
		String sql = "select * from lansha_live_history where room_id = ? order by create_time desc";
		List<LanshaLiveHistory> list = find(sql, new Object[] { roomId }, new MultiRow<LanshaLiveHistory>());
		if(CollectionUtils.isEmpty(list)){
			return null;
		}
		return list.get(0);
	}
	
	@Override
	public LanshaLiveHistory getLanshaLiveHistoryByRoomId(String roomId,int index) {
		String sql = "select * from lansha_live_history where room_id = ? order by create_time";
		List<LanshaLiveHistory> list = find(sql, new Object[] { roomId }, new MultiRow<LanshaLiveHistory>());
		if(CollectionUtils.isEmpty(list)){
			return null;
		}
		return list.get(index);
	}

	@Override
	public List<Map<String, Object>> listMapLiveHistoryReport(LanshaLiveHistory entity, Date startTime, Date endTime) {
		StringBuilder sql = new StringBuilder("select r.name, r.id_int as idInt, r.uid, r.game_id as gameId, h.start_time as startTime, h.end_time as endTime  from lansha_live_history h left join yw_user_room r on h.room_id = r.id where 1=1");
		List<Object> args = new ArrayList<Object>();
		if (entity != null) {
			if(StringUtils.isNotBlank(entity.getRoomId())){
				sql.append(" and h.room_id = ?");
				args.add(entity.getRoomId());
			}
		}
		
		if (startTime != null) {
			sql.append(" and h.start_time >= ?");
			args.add(DateUtils.getStartDate(startTime));
		}
		if(endTime != null){
			sql.append(" and h.end_time <= ?");
			args.add(DateUtils.getEndDate(endTime));
		}
		
		sql.append(" order by h.start_time asc");
		
		return queryForList(sql.toString(), args.toArray());
	}

    @Override
    public Map<String, Integer> getCount(Date startTime, Date endTime) {
        String sql="select sum(length) as num,room_id from lansha_live_history where create_time>= ? and create_time<= ? group by room_id";
        List<Object> args = new ArrayList<Object>();
        args.add(startTime);
        args.add(endTime);

        return queryForMap(sql, args.toArray(), null, new MapRowMapper<String, Integer>() {

            @Override
            public String mapRowKey(ResultSet rs) throws SQLException {
                return rs.getString("room_id");
            }

            @Override
            public Integer mapRowValue(ResultSet rs) throws SQLException {
                return rs.getInt("num");
            }

        });

    }


    public List<LanshaLiveHistory> getLanshaLiveHistoryForPage(LanshaLiveHistory entity, Date startTime, Date endTime, PageDto page) {
        StringBuilder sql = new StringBuilder("select h.start_time as start_time,h.end_time as end_time,h.room_id as room_id,r.id_int as id_int,r.sign as sign,r.uid as user_id ,r.game_id as game_id from lansha_live_history h,yw_user_room r where h.room_id=r.id");
        List<Object> args = new ArrayList<Object>();
        if (entity != null) {
            if(entity.getIdInt()!=null){
                sql.append(" and r.id_int = ?");
                args.add(entity.getIdInt());
            }
            if(entity.getSign()!=null){
                sql.append(" and r.sign = ?");
                args.add(entity.getSign());
            }
        }
        if (startTime != null) {
            sql.append(" and h.start_time >= ?");
            args.add(DateUtils.getStartDate(startTime));
        }
        if(endTime != null){
            sql.append(" and h.end_time <= ?");
            args.add(DateUtils.getEndDate(endTime));
        }
        sql.append(" order by h.room_id");

        if (page == null) {
            return find(sql.toString(), args.toArray(), new RowMapper<LanshaLiveHistory>() {
                @Override
                public LanshaLiveHistory mapRow(ResultSet rs, int arg1) throws SQLException {
                    LanshaLiveHistory entity = new LanshaLiveHistory();
                    entity.setIdInt(rs.getInt("id_int"));
                    entity.setRoomId(rs.getString("room_id"));
                    entity.setUserId(rs.getString("user_id"));
                    entity.setStartTime(rs.getTimestamp("start_time"));
                    entity.setEndTime(rs.getTimestamp("end_time"));
                    entity.setSign(rs.getInt("sign"));
                    entity.setGameId(rs.getString("game_id"));


                    return entity;
                }
            });
        } else {
            return findForPage(sql.toString(), args.toArray(),  new RowMapper<LanshaLiveHistory>() {
                @Override
                public LanshaLiveHistory mapRow(ResultSet rs, int arg1) throws SQLException {
                    LanshaLiveHistory entity = new LanshaLiveHistory();
                    entity.setIdInt(rs.getInt("id_int"));
                    entity.setRoomId(rs.getString("room_id"));
                    entity.setUserId(rs.getString("user_id"));
                    entity.setStartTime(rs.getTimestamp("start_time"));
                    entity.setEndTime(rs.getTimestamp("end_time"));
                    entity.setSign(rs.getInt("sign"));
                    entity.setGameId(rs.getString("game_id"));

                    return entity;
                }
            }, page);
        }
    }

}
