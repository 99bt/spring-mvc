package com.yaowang.lansha.dao.impl;

import com.yaowang.common.dao.BaseDaoImpl;
import com.yaowang.common.dao.PageDto;
import com.yaowang.common.dao.SQLUtils;
import com.yaowang.lansha.dao.LanshaRoomRankingDao;
import com.yaowang.lansha.entity.LanshaRoomRanking;
import com.yaowang.util.DateUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 房间排名
 *
 * @author
 */
@SuppressWarnings("unchecked")
@Repository("lanshaRoomRankingDao")
public class LanshaRoomRankingDaoImpl extends BaseDaoImpl<LanshaRoomRanking> implements LanshaRoomRankingDao {
    @Override
    public LanshaRoomRanking setField(ResultSet rs) throws SQLException {
        LanshaRoomRanking entity = new LanshaRoomRanking();
        entity.setId(rs.getString("id"));
        entity.setUserId(rs.getString("user_id"));
        entity.setRoomIdint(rs.getInt("room_idint"));
        entity.setLiveTime(rs.getInt("live_time"));
        entity.setXiamiNumber(rs.getInt("xiami_number"));
        entity.setRelationNumber(rs.getInt("relation_number"));
        entity.setFlowerNubmer(rs.getInt("flower_nubmer"));
        entity.setTicketNumber(rs.getInt("ticket_number"));
        entity.setCancelNumber(rs.getInt("cancel_number"));
        entity.setAudience(rs.getInt("audience"));
        entity.setScore(rs.getFloat("score"));
        entity.setCreateTime(rs.getTimestamp("create_time"));
        return entity;
    }

    @Override
    public LanshaRoomRanking setFields(ResultSet rs) throws SQLException {
        LanshaRoomRanking entity = new LanshaRoomRanking();
        entity.setRoomIdint(rs.getInt("room_idint"));
        entity.setUserId(rs.getString("user_id"));
        entity.setLiveTime(rs.getInt("live_time"));
        entity.setXiamiNumber(rs.getInt("xiami_number"));
        entity.setRelationNumber(rs.getInt("relation_number"));
        entity.setFlowerNubmer(rs.getInt("flower_nubmer"));
        entity.setTicketNumber(rs.getInt("ticket_number"));
        entity.setCancelNumber(rs.getInt("cancel_number"));
        entity.setAudience(rs.getInt("audience"));
        entity.setScore(rs.getFloat("score"));
        return entity;
    }

    @Override
    public LanshaRoomRanking save(LanshaRoomRanking entity) {
        String sql = "insert into lansha_room_ranking(id, user_id, room_idint, live_time, xiami_number, relation_number, flower_nubmer,ticket_number,cancel_number, audience,score,create_time,up_time) values(?,?,?,?,?,?,?,?,?,?,?,?,?)";
        if (StringUtils.isBlank(entity.getId())) {
            entity.setId(createId());
        }
        Object[] args = new Object[]{
                entity.getId(), entity.getUserId(),
                entity.getRoomIdint(), entity.getLiveTime(),
                entity.getXiamiNumber(), entity.getRelationNumber(),
                entity.getFlowerNubmer(), entity.getTicketNumber(), entity.getCancelNumber(), entity.getAudience(), entity.getScore(),
                entity.getCreateTime(),System.currentTimeMillis()
        };
        update(sql, args);
        return entity;
    }

    @Override
    public Integer delete(String[] ids) {
        String sql = "delete from lansha_room_ranking where id in";
        return executeUpdateForInSQL(sql, null, ids);
    }

    @Override
    public Integer update(LanshaRoomRanking entity) {
        String sql = "update lansha_room_ranking set user_id = ?, room_idint = ?, live_time = ?, xiami_number = ?, relation_number = ?, flower_nubmer = ?, audience = ?,score=?, create_time = ? where id = ?";
        Object[] args = new Object[]{
                entity.getUserId(), entity.getRoomIdint(),
                entity.getLiveTime(), entity.getXiamiNumber(),
                entity.getRelationNumber(), entity.getFlowerNubmer(),
                entity.getAudience(), entity.getScore(), entity.getCreateTime(),
                entity.getId()
        };
        return update(sql, args);
    }

    @Override
    public LanshaRoomRanking getLanshaRoomRankingById(String id) {
        String sql = "select * from lansha_room_ranking where id = ?";
        return (LanshaRoomRanking) findForObject(sql, new Object[]{id}, new MultiRow<LanshaRoomRanking>());
    }

    @Override
    public Map<String, LanshaRoomRanking> getLanshaRoomRankingMapByIds(String[] ids) {
        String sql = "select * from lansha_room_ranking where id in";
        return queryForMap(sql, null, ids, new MapIdRow<String, LanshaRoomRanking>("id", String.class));
    }

    @Override
    public List<LanshaRoomRanking> getLanshaRoomRankingList(LanshaRoomRanking entity) {
        StringBuilder sql = new StringBuilder("select * from lansha_room_ranking where 1=1");
        List<Object> args = new ArrayList<Object>();
        if (entity != null) {
            if (entity.getRoomIdint() != null) {
                sql.append(" and room_idint = ?");
                args.add(entity.getRoomIdint());
            }
            if (entity.getCreateTime() != null) {
                sql.append(" and create_time >= ? and create_time <= ?");
                args.add(DateUtils.getStartDate(entity.getCreateTime()));
                args.add(DateUtils.getEndDate(entity.getCreateTime()));
            }
        }
        return find(sql.toString(), args.toArray(), new MultiRow<LanshaRoomRanking>());
    }

    @Override
    public List<LanshaRoomRanking> getLanshaRoomRankingPage(LanshaRoomRanking entity, PageDto page) {
        StringBuilder sql = new StringBuilder("select * from lansha_room_ranking where 1=1");
        List<Object> args = new ArrayList<Object>();
        if (entity != null) {
            if (StringUtils.isNotEmpty(entity.getUserId())) {
                sql.append(" and user_id = ?");
                args.add(entity.getUserId());
            }
        }
        if (page != null) {
            return findForPage(sql.toString(), args.toArray(), new MultiRow<LanshaRoomRanking>(), page);
        } else {
            return find(sql.toString(), args.toArray(), new MultiRow<LanshaRoomRanking>());
        }
    }

    public List<LanshaRoomRanking> getLanshaRoomRankingPage(LanshaRoomRanking entity, Date startTime, Date endTime, PageDto page) {
        StringBuilder sql = new StringBuilder("select * from lansha_room_ranking where 1=1");
        List<Object> args = new ArrayList<Object>();
        if (entity != null) {
            if (StringUtils.isNotEmpty(entity.getUserId())) {
                sql.append(" and user_id = ?");
                args.add(entity.getUserId());
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
        sql.append(" order by create_time desc");
        if (page != null) {
            return findForPage(sql.toString(), args.toArray(), new MultiRow<LanshaRoomRanking>(), page);
        } else {
            return find(sql.toString(), args.toArray(), new MultiRow<LanshaRoomRanking>());
        }
    }

    @Override
    public List<LanshaRoomRanking> getLanshaRoomRankingListGroupUser(LanshaRoomRanking entity, Date startTime, Date endTime, PageDto page) {
//        String sql = "select room_idint, user_id ,sum(live_time) as live_time, sum(xiami_number) as xiami_number, sum(relation_number) as relation_number, sum(flower_nubmer) as flower_nubmer," +
//                " sum(audience) as audience,sum(ticket_number) as ticket_number,sum(cancel_number) as cancel_number from lansha_room_ranking where create_time >= ? and create_time <= ? group by room_idint, user_id ";

//        String sql1 = "select r.room_idint as room_idint, r.user_id as user_id,y.id as id,sum(r.live_time) as live_time, sum(r.xiami_number) as xiami_number, sum(r.relation_number) as relation_number, sum(r.flower_nubmer) as flower_nubmer," +
//                " sum(r.audience) as audience,sum(r.ticket_number) as ticket_number,sum(r.cancel_number) as cancel_number,y.sign as sign from lansha_room_ranking r,yw_user_room y where r.room_idint=y.id_int and y.sign=? and r.create_time >= ? and r.create_time <= ? group by r.room_idint,r.user_id ";
        StringBuilder sql = new StringBuilder("select room_idint,user_id,sum(live_time) as live_time,sum(xiami_number) as xiami_number, sum(relation_number) as relation_number, sum(flower_nubmer) as flower_nubmer,sum(audience) as audience,sum(ticket_number) as ticket_number,sum(cancel_number) as cancel_number,sum(score) as score from lansha_room_ranking  where 1=1");
        List<Object> args = new ArrayList<Object>();
        if (startTime != null) {
            sql.append(" and create_time >= ?");
            args.add(DateUtils.getStartDate(startTime));
        }
        if (endTime != null) {
            sql.append("  and create_time <= ?");
            args.add(DateUtils.getEndDate(endTime));
        }
        if (entity.getRoomIdint() != null) {
            sql.append("  and room_idint = ?");
            args.add(entity.getRoomIdint());
        }
        sql.append(" group by room_idint,user_id order by score desc,up_time asc");
        return find(sql.toString(), args.toArray(), new RowMapper<LanshaRoomRanking>() {
            @Override
            public LanshaRoomRanking mapRow(ResultSet rs, int arg1) throws SQLException {
                LanshaRoomRanking entity = new LanshaRoomRanking();
                entity.setRoomIdint(rs.getInt("room_idint"));
                entity.setUserId(rs.getString("user_id"));
                entity.setLiveTime(rs.getInt("live_time"));
                entity.setXiamiNumber(rs.getInt("xiami_number"));
                entity.setRelationNumber(rs.getInt("relation_number"));
                entity.setFlowerNubmer(rs.getInt("flower_nubmer"));
                entity.setTicketNumber(rs.getInt("ticket_number"));
                entity.setCancelNumber(rs.getInt("cancel_number"));
                entity.setAudience(rs.getInt("audience"));
                entity.setScore(rs.getFloat("score"));
                return entity;
            }
        });
    }

    @Override
    public List<LanshaRoomRanking> getLanshaRoomRankingListGroupUser(LanshaRoomRanking entity, String[] uids, Date startTime, Date endTime, PageDto page) {
        StringBuilder sql = new StringBuilder("select room_idint, user_id, sum(live_time) as live_time, sum(xiami_number) as xiami_number, sum(relation_number) as relation_number, sum(flower_nubmer) as flower_nubmer," +
                " sum(audience) as audience,sum(score) as score from lansha_room_ranking where 1=1 ");
        List<Object> args = new ArrayList<Object>();
        args.add(DateUtils.getStartDate(startTime));
        args.add(DateUtils.getEndDate(endTime));
        if (entity != null) {
            if (entity.getRoomIdint() != null) {
                sql.append(" and room_idint = ?");
                args.add(entity.getRoomIdint());
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
        if (uids != null) {
            sql.append(" and uid in" + SQLUtils.toSQLInString(uids));
        }
        sql.append("group by room_idint, user_id");
        return findForPage(sql.toString(), args.toArray(), new MultiRows<LanshaRoomRanking>(), page);
    }


    @Override
    public List<LanshaRoomRanking> getLanshaRoomRankingList(LanshaRoomRanking entity, Date startTime, Date endTime, PageDto page) {
        List<Object> args = new ArrayList<Object>();
        args.add(DateUtils.getStartDate(startTime));
        args.add(DateUtils.getEndDate(endTime));
        StringBuilder sql = new StringBuilder("select r.room_idint, r.user_id ,sum(r.live_time) as live_time, sum(r.xiami_number) as xiami_number, sum(r.relation_number) as relation_number, sum(r.flower_nubmer) as flower_nubmer," +
                " sum(r.audience) as audience,sum(r.ticket_number) as ticket_number,sum(r.cancel_number) as cancel_number,sum(r.score) as score, c.sign as sign from lansha_room_ranking r, yw_user_room c where r.user_id=c.uid" +
                " where r.create_time >= ? and r.create_time <= ?");

        if (entity.getRoomIdint() != null) {
            sql.append(" and r.room_idint=?");
            args.add(entity.getRoomIdint());

        }
        if (entity.getSign() != null) {
            sql.append(" and c.sign=?");
            args.add(entity.getSign());

        }
        sql.append(" group by r.room_idint, r.user_id ");


        return find(sql.toString(), args.toArray(), new RowMapper<LanshaRoomRanking>() {
            @Override
            public LanshaRoomRanking mapRow(ResultSet rs, int arg1) throws SQLException {
                LanshaRoomRanking entity = new LanshaRoomRanking();
                entity.setRoomIdint(rs.getInt("room_idint"));
                entity.setUserId(rs.getString("user_id"));
                entity.setLiveTime(rs.getInt("live_time"));
                entity.setXiamiNumber(rs.getInt("xiami_number"));
                entity.setRelationNumber(rs.getInt("relation_number"));
                entity.setFlowerNubmer(rs.getInt("flower_nubmer"));
                entity.setTicketNumber(rs.getInt("ticket_number"));
                entity.setCancelNumber(rs.getInt("cancel_number"));
                entity.setAudience(rs.getInt("audience"));
                entity.setSign(rs.getInt("sign"));
                entity.setScore(rs.getFloat("score"));
                return entity;
            }
        });
    }

    @Override
    public Map<String, Integer> getCountValidDays(int time) {
        String sql = "select count(*) as num,room_idInt from lansha_room_ranking where live_time>= ? group by room_idInt, create_time";
        List<Object> args = new ArrayList<Object>();
        args.add(time);

        return queryForMap(sql, args.toArray(), null, new MapRowMapper<String, Integer>() {

            @Override
            public String mapRowKey(ResultSet rs) throws SQLException {
                return rs.getString("room_idInt");
            }

            @Override
            public Integer mapRowValue(ResultSet rs) throws SQLException {
                return rs.getInt("num");
            }

        });

    }

    @Override
    public List<LanshaRoomRanking> getLanshaRoomRankingListSort(Date date) {

        String sql = "select r.ticket_number as ticket_number,y.nickname as name,r.user_id as user_id from lansha_room_ranking r, yw_user y where r.user_id=y.id and r.create_time=? order by r.ticket_number desc limit 10 ";
        List<Object> args = new ArrayList<Object>();
        args.add(date);

        return query(sql, args.toArray(), new MultiRow<LanshaRoomRanking>() {
            @Override
            public LanshaRoomRanking mapRow(ResultSet rs, int arg1) throws SQLException {
                LanshaRoomRanking entity = new LanshaRoomRanking();
                entity.setTicketNumber(rs.getInt("ticket_number"));
                entity.setUserName(rs.getString("name"));
                entity.setUserId(rs.getString("user_id"));
                return entity;
            }
        });
    }

}
