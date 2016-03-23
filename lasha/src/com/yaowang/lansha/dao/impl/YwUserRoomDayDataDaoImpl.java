package com.yaowang.lansha.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.yaowang.lansha.entity.LanshaRoomRanking;
import org.apache.commons.lang.StringUtils;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.yaowang.common.dao.BaseDaoImpl;
import com.yaowang.common.dao.PageDto;
import com.yaowang.lansha.dao.YwUserRoomDayDataDao;
import com.yaowang.lansha.entity.YwUserRoomDayData;

/**
 * 房间日数据
 *
 * @author
 */
@SuppressWarnings("unchecked")
@Repository("ywUserRoomDayDataDao")
public class YwUserRoomDayDataDaoImpl extends BaseDaoImpl<YwUserRoomDayData> implements YwUserRoomDayDataDao {
    @Override
    public YwUserRoomDayData setField(ResultSet rs) throws SQLException {
        YwUserRoomDayData entity = new YwUserRoomDayData();
        entity.setRoomId(rs.getString("room_id"));
        entity.setRankingId(rs.getString("ranking_id"));
        entity.setScore(rs.getFloat("score"));
        entity.setRanking(rs.getInt("ranking"));
        entity.setPayStandard(rs.getFloat("pay_standard"));
        entity.setFocusOn(rs.getInt("focus_on"));
        entity.setFocusOff(rs.getInt("focus_off"));
        entity.setBonus(rs.getFloat("bonus"));
        entity.setForfeit(rs.getFloat("forfeit"));
        entity.setDay(rs.getTimestamp("day"));
        entity.setSalary(rs.getFloat("salary"));
        entity.setUserId(rs.getString("user_id"));
        entity.setLiveTime(rs.getInt("live_time"));
        entity.setRemark(rs.getString("remark"));
        return entity;
    }

    @Override
    public YwUserRoomDayData save(YwUserRoomDayData entity) {
        String sql = "insert into yw_user_room_day_data(room_id, ranking_id, score, ranking, pay_standard, focus_on, focus_off, bonus, forfeit,salary,user_id,live_time, day) values(?,?,?,?,?,?,?,?,?,?,?,?,?)";
        if (StringUtils.isBlank(entity.getRoomId())) {
            entity.setRoomId(createId());
        }
        Object[] args = new Object[]{
                entity.getRoomId(), entity.getRankingId(),
                entity.getScore(), entity.getRanking(),
                entity.getPayStandard(), entity.getFocusOn(),
                entity.getFocusOff(), entity.getBonus(),
                entity.getForfeit(), entity.getSalary(), entity.getUserId(), entity.getLiveTime(), entity.getDay()
        };
        update(sql, args);
        return entity;
    }

    @Override
    public Integer delete(String[] ids) {
        String sql = "delete from yw_user_room_day_data where ranking_id in";
        return executeUpdateForInSQL(sql, null, ids);
    }

    @Override
    public Integer update(YwUserRoomDayData entity) {
        String sql = "update yw_user_room_day_data set ranking_id = ?, score = ?, ranking = ?, pay_standard = ?, focus_on = ?, focus_off = ?, bonus = ?, forfeit = ?,salary = ?,user_id = ?,live_time = ?,day = ? where room_id = ?";
        Object[] args = new Object[]{
                entity.getRankingId(), entity.getScore(),
                entity.getRanking(), entity.getPayStandard(),
                entity.getFocusOn(), entity.getFocusOff(),
                entity.getBonus(), entity.getForfeit(), entity.getSalary(), entity.getUserId(), entity.getLiveTime(),
                entity.getDay(), entity.getRoomId()
        };
        return update(sql, args);
    }

    @Override
    public YwUserRoomDayData getYwUserRoomDayDataById(String rankingId) {
        String sql = "select * from yw_user_room_day_data where ranking_id = ?";
        return (YwUserRoomDayData) findForObject(sql, new Object[]{rankingId}, new MultiRow<YwUserRoomDayData>());
    }

    @Override
    public Map<String, YwUserRoomDayData> getYwUserRoomDayDataMapByIds(String[] roomIds) {
        String sql = "select * from yw_user_room_day_data where ranking_id in";
        return queryForMap(sql, null, roomIds, new MapIdRow<String, YwUserRoomDayData>("ranking_id", String.class));
    }

    @Override
    public List<YwUserRoomDayData> getYwUserRoomDayDataList(YwUserRoomDayData entity, Date startTime, Date endTime) {
        StringBuilder sql = new StringBuilder("select *  from yw_user_room_day_data where 1=1");
        List<Object> args = new ArrayList<Object>();
        if (startTime != null) {
            sql.append(" and day>=?");
            args.add(startTime);
        }
        if (endTime != null) {
            sql.append(" and day<=?");
            args.add(endTime);
        }
        if (entity != null) {
            if (StringUtils.isNotEmpty(entity.getRoomId())) {
                sql.append(" and room_id=?");
                args.add(entity.getRoomId());
            }

        }
        return find(sql.toString(), args.toArray(), new RowMapper<YwUserRoomDayData>() {
            @Override
            public YwUserRoomDayData mapRow(ResultSet rs, int arg1) throws SQLException {
                YwUserRoomDayData entity = new YwUserRoomDayData();
                entity.setRankingId(rs.getString("ranking_id"));
                entity.setDay(rs.getDate("day"));
                entity.setRoomId(rs.getString("room_id"));
                entity.setUserId(rs.getString("user_id"));
                entity.setLiveTime(rs.getInt("live_time"));
                entity.setSalary(rs.getFloat("salary"));
                entity.setBonus(rs.getFloat("bonus"));
                entity.setScore(rs.getFloat("score"));
                entity.setForfeit(rs.getFloat("forfeit"));
                entity.setRanking(rs.getInt("ranking"));
                entity.setPayStandard(rs.getFloat("pay_standard"));
                entity.setRemark(rs.getString("remark"));
                return entity;
            }
        });
    }

    @Override
    public List<YwUserRoomDayData> getYwUserRoomDayDataPage(YwUserRoomDayData entity, PageDto page) {
        String sql = "select * from yw_user_room_day_data where 1=1";
        List<Object> args = new ArrayList<Object>();
        if (entity != null) {

        }
        return findForPage(sql, args.toArray(), new MultiRow<YwUserRoomDayData>(), page);
    }


    @Override
    public List<YwUserRoomDayData> getYwUserRoomDayDataSort(YwUserRoomDayData entity) {
        String sql = "select d.ranking as ranking,y.nickname as name from yw_user_room_day_data d, yw_user y where d.user_id=y.id and d.day=? order by d.ranking asc limit 10 ";
        List<Object> args = new ArrayList<Object>();
        args.add(entity.getDay());

        return query(sql, args.toArray(), new MultiRow<YwUserRoomDayData>() {
            @Override
            public YwUserRoomDayData mapRow(ResultSet rs, int arg1) throws SQLException {
                YwUserRoomDayData entity = new YwUserRoomDayData();
                entity.setRanking(rs.getInt("ranking"));
                entity.setUserName(rs.getString("name"));

                return entity;
            }
        });

    }

    @Override
    public YwUserRoomDayData getYwUserRoomDayData(YwUserRoomDayData entity) {
        StringBuilder sql = new StringBuilder("select * from yw_user_room_day_data where 1=1");
        List<Object> args = new ArrayList<Object>();
        if (entity != null) {
            if (entity.getDay() != null) {
                sql.append(" and day =?");
                args.add(entity.getDay());
            }
            if (StringUtils.isNotEmpty(entity.getUserId())) {
                sql.append(" and user_id =?");
                args.add(entity.getUserId());
            }
        }
        return findForObject(sql.toString(), args.toArray(), new MultiRow<YwUserRoomDayData>());
    }

    @Override
    public List<YwUserRoomDayData> getYwUserRoomDayDataTotalList(YwUserRoomDayData entity, Date startTime, Date endTime, PageDto page) {
        List<Object> args = new ArrayList<Object>();


        StringBuilder sql = new StringBuilder("select d.room_id as room_id,sum(d.salary) as salary,sum(d.forfeit) as forfeit,sum(d.bonus) as bonus,d.user_id as user_id,sum(d.live_time) as live_time from yw_user_room_day_data d,lansha_room_ranking r where d.ranking_id=r.id ");

        if (startTime != null) {
            sql.append(" and d.day>=?");
            args.add(startTime);
        }
        if (endTime != null) {
            sql.append(" and d.day<=?");
            args.add(endTime);
        }
        if (entity != null) {
            if (StringUtils.isNotEmpty(entity.getUserId())) {
                sql.append(" and d.user_id=?");
                args.add(entity.getUserId());
            }
            if (StringUtils.isNotEmpty(entity.getRoomId())) {
                sql.append(" and d.room_id=?");
                args.add(entity.getRoomId());
            }
        }
        sql.append(" group by d.user_id order by d.salary desc");
        if (page== null) {
            return find(sql.toString(), args.toArray(), new RowMapper<YwUserRoomDayData>() {
                @Override
                public YwUserRoomDayData mapRow(ResultSet rs, int arg1) throws SQLException {
                    YwUserRoomDayData entity = new YwUserRoomDayData();
                    entity.setRoomId(rs.getString("room_id"));
                    entity.setUserId(rs.getString("user_id"));
                    entity.setLiveTime(rs.getInt("live_time"));
                    entity.setSalary(rs.getFloat("salary"));
                    entity.setBonus(rs.getFloat("bonus"));
                    entity.setForfeit(rs.getFloat("forfeit"));
                    return entity;
                }
            });
        } else {


            return findForPage(sql.toString(), args.toArray(), new RowMapper<YwUserRoomDayData>() {
                @Override
                public YwUserRoomDayData mapRow(ResultSet rs, int arg1) throws SQLException {
                    YwUserRoomDayData entity = new YwUserRoomDayData();
                    entity.setRoomId(rs.getString("room_id"));
                    entity.setUserId(rs.getString("user_id"));
                    entity.setLiveTime(rs.getInt("live_time"));
                    entity.setSalary(rs.getFloat("salary"));
                    entity.setBonus(rs.getFloat("bonus"));
                    entity.setForfeit(rs.getFloat("forfeit"));
                    return entity;
                }
            }, page);
        }

    }

    @Override
    public Integer deleteByDay(Date date) {
        String sql = "delete from yw_user_room_day_data where day=?";
        return executeUpdateForInSQL(sql, new Date[]{date}, null);
    }

    @Override
    public Integer updateInfo(Float forfeit, Float bonus, String remark, String id) {
        String sql = "update yw_user_room_day_data set forfeit=?,bonus=?, remark=? where ranking_id=?";
        return executeUpdateForInSQL(sql, new Object[]{forfeit, bonus, remark, id}, null);
    }

    @Override
    public YwUserRoomDayData getYwUserRoomDataSum(YwUserRoomDayData entity, Date startTime, Date endTime) {
        StringBuilder sql = new StringBuilder("select sum(salary) as salary,sum(forfeit) as forfeit,sum(bonus) as bonus from yw_user_room_day_data where 1=1");
        List<Object> args = new ArrayList<Object>();
        if (startTime != null) {
            sql.append(" and day>=?");
            args.add(startTime);
        }
        if (endTime != null) {
            sql.append(" and day<=?");
            args.add(endTime);
        }
        if (entity != null) {
            if (StringUtils.isNotEmpty(entity.getUserId())) {
                sql.append(" and user_id=?");
                args.add(entity.getUserId());
            }
            if (StringUtils.isNotEmpty(entity.getRoomId())) {
                sql.append(" and room_id=?");
                args.add(entity.getRoomId());
            }
        }
        return findForObject(sql.toString(), args.toArray(), new MultiRow<YwUserRoomDayData>() {
            @Override
            public YwUserRoomDayData mapRow(ResultSet rs, int arg1) throws SQLException {
                YwUserRoomDayData entity = new YwUserRoomDayData();
                entity.setSalary(rs.getFloat("salary"));
                entity.setBonus(rs.getFloat("bonus"));
                entity.setForfeit(rs.getFloat("forfeit"));

                return entity;
            }
        });
    }

}
