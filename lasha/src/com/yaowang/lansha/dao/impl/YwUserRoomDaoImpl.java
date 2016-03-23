package com.yaowang.lansha.dao.impl;

import com.yaowang.common.dao.BaseDaoImpl;
import com.yaowang.common.dao.PageDto;
import com.yaowang.common.dao.SQLUtils;
import com.yaowang.lansha.dao.YwUserRoomDao;
import com.yaowang.lansha.entity.YwBanner;
import com.yaowang.lansha.entity.YwGame;
import com.yaowang.lansha.entity.YwUserRoom;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * yw_user_room
 *
 * @author
 */
@SuppressWarnings("unchecked")
@Repository("ywUserRoomDao")
public class YwUserRoomDaoImpl extends BaseDaoImpl<YwUserRoom> implements YwUserRoomDao {
    @Override
    public YwUserRoom setField(ResultSet rs) throws SQLException {
        YwUserRoom entity = new YwUserRoom();
        entity.setId(rs.getString("id"));
        entity.setIdInt(rs.getInt("id_int"));
        entity.setUid(rs.getString("uid"));
        entity.setName(rs.getString("name"));
        entity.setRtmp(rs.getString("rtmp"));
        entity.setRoomId(rs.getString("room_id"));
        entity.setLiveHost(rs.getString("live_host"));
        entity.setWsHost(rs.getString("ws_host"));
        entity.setOnline(rs.getInt("online"));
        entity.setNumber(rs.getInt("number"));
        entity.setOpenfirePath(rs.getString("openfire_path"));
        entity.setOpenfirePort(rs.getString("openfire_port"));
        entity.setOpenfireRoom(rs.getString("openfire_room"));
        entity.setOpenfireConference(rs.getString("openfire_conference"));
        entity.setCreateTime(rs.getTimestamp("create_time"));
        entity.setUpdateTime(rs.getTimestamp("update_time"));
        entity.setOrderId(rs.getInt("order_id"));
        entity.setFans(rs.getInt("fans"));
        entity.setIntro(rs.getString("intro"));
        entity.setNotice(rs.getString("notice"));
        entity.setGameId(rs.getString("game_id"));
        entity.setReportNum(rs.getInt("report_num"));
        entity.setTimeLength(rs.getInt("time_length"));
        entity.setStartTime(rs.getString("start_time"));
        entity.setBaseNumber(rs.getInt("base_number"));
        entity.setMultipleNumber(rs.getInt("multiple_number"));
        entity.setSign(rs.getString("sign"));
        entity.setLiveImg(rs.getString("live_img"));
        entity.setRemarks(rs.getString("remarks"));
        return entity;
    }

    @Override
    public YwUserRoom save(YwUserRoom entity) {
        String sql = "insert into yw_user_room(id, id_int, uid, rtmp, room_id, live_host, ws_host, online, number, openfire_path, openfire_port, openfire_room, openfire_conference, " +
                "create_time, update_time, order_id, fans, intro, notice, game_id, report_num, time_length, start_time, name, base_number, multiple_number, sign) " +
                "values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
        if (entity.getCreateTime() == null) {
            entity.setCreateTime(new Date());
        }
        if (StringUtils.isEmpty(entity.getId())) {
            entity.setId(createId());
        }
        Object[] args = new Object[]{
                entity.getId(), entity.getIdInt(),
                entity.getUid(), entity.getRtmp(),
                entity.getRoomId(), entity.getLiveHost(),
                entity.getWsHost(), entity.getOnline(),
                entity.getNumber(), entity.getOpenfirePath(),
                entity.getOpenfirePort(), entity.getOpenfireRoom(),
                entity.getOpenfireConference(), entity.getCreateTime(),
                entity.getUpdateTime(), entity.getOrderId(),
                entity.getFans(), entity.getIntro(),
                entity.getNotice(), entity.getGameId(),
                entity.getReportNum(), entity.getTimeLength(),
                entity.getStartTime(), entity.getName(),
                entity.getBaseNumber(), entity.getMultipleNumber(), entity.getSign()
        };
        entity.setIdInt(Integer.valueOf(saveEntityForKey(sql, args).toString()));
        return entity;
    }

    @Override
    public Integer delete(String[] ids) {
        String sql = "delete from yw_user_room where id in";
        return executeUpdateForInSQL(sql, null, ids);
    }


    @Override
    public Integer update(YwUserRoom entity) {
        //设置编码问题
        setUtf8mb4(entity.getName(), entity.getNotice());

        String sql = "update yw_user_room set start_time = ?, name = ?, intro = ?, notice = ?, order_id = ?, online = ?, base_number = ?, multiple_number = ?, rtmp = ?, room_id = ?, live_host = ?, ws_host = ?, openfire_path = ?, openfire_port = ?, openfire_room = ?, openfire_conference = ?, game_id = ?, update_time = ?, sign = ? where id = ?";
        Object[] args = new Object[]{
                entity.getStartTime(), entity.getName(),
                entity.getIntro(), entity.getNotice(),
                entity.getOrderId(), entity.getOnline(),
                entity.getBaseNumber(), entity.getMultipleNumber(),
                entity.getRtmp(), entity.getRoomId(),
                entity.getLiveHost(), entity.getWsHost(),
                entity.getOpenfirePath(), entity.getOpenfirePort(),
                entity.getOpenfireRoom(), entity.getOpenfireConference(),
                entity.getGameId(), entity.getUpdateTime(),
                entity.getSign(), entity.getId()
        };
        return update(sql, args);
    }

    @Override
    public int updateState(YwUserRoom room) {
        String sql = "update yw_user_room set online = ?, update_time = ?, time_length = time_length + ? where id = ? and update_time < ?";
        return update(sql, new Object[]{room.getOnline(), room.getUpdateTime(), room.getTimeLength(), room.getId(), room.getUpdateTime()});
    }

    @Override
    public Integer updateFans(String id, Integer numb) {
        String sql = "update yw_user_room set fans = fans + ? where id = ? and (fans + ?) >= 0";
        return update(sql, new Object[]{numb, id, numb});
    }

    @Override
    public YwUserRoom getYwUserRoomById(String id) {
        String sql = "select * from yw_user_room where id = ? and online <> 4";
        return (YwUserRoom) findForObject(sql, new Object[]{id}, new MultiRow<YwUserRoom>());
    }

    @Override
    public YwUserRoom getYwUserRoomById(int idInt) {
        String sql = "select * from yw_user_room where id_int = ? and online <> 4";
        return (YwUserRoom) findForObject(sql, new Object[]{idInt}, new MultiRow<YwUserRoom>());
    }

    @Override
    public YwUserRoom getYwUserRoomByUid(String uid) {
        String sql = "select * from yw_user_room where uid = ? and online <> 4";
        return (YwUserRoom) findForObject(sql, new Object[]{uid}, new MultiRow<YwUserRoom>());
    }

    @Override
    public YwUserRoom getYwUserRoomByRoomId(String roomId) {
        String sql = "select * from yw_user_room where room_id = ? and online <> 4";
        return (YwUserRoom) findForObject(sql, new Object[]{roomId}, new MultiRow<YwUserRoom>());
    }

    @Override
    public Map<String, YwUserRoom> getYwUserRoomMapByIds(String[] ids) {
        String sql = "select * from yw_user_room where online <> 4 and id in";
        return queryForMap(sql, null, ids, new MapIdRow<Integer, YwUserRoom>("id", String.class));
    }

    @Override
    public Map<String, YwUserRoom> getYwUserRoomMapByUserIds(String[] userIds) {
        String sql = "select * from yw_user_room where online <> 4 and uid in";
        return queryForMap(sql, null, userIds, new MapIdRow<Integer, YwUserRoom>("uid", String.class));
    }

    @Override
    public Map<Integer, YwUserRoom> getYwUserRoomMapByIds(Integer[] ids) {
        String sql = "select * from yw_user_room where online <> 4 and id_int in";
        return queryForMap(sql, null, ids, new MapIdRow<Integer, YwUserRoom>("id_int", Integer.class));
    }

    @Override
    public Map<String, YwUserRoom> getYwUserRoomMapByIdInts(String[] idInts, Integer status) {
        String sql = "select * from yw_user_room where 1=1";
        List<Object> args = new ArrayList<Object>();
        if (status != null) {
            sql += " and online = ?";
            args.add(status);
        }
        sql += " and id_int in";
        return queryForMap(sql, args.toArray(), idInts, new MapIdRow<Integer, YwUserRoom>("id_int", String.class));
    }

    @Override
    public List<YwUserRoom> getYwUserRoomList(YwUserRoom room) {
        String sql = "select * from yw_user_room where 1 = 1 and online <> 4";
        List<Object> args = new ArrayList<Object>();
        if (room != null) {
            if (StringUtils.isNotBlank(room.getName())) {
                //编码问题
                setUtf8mb4(room.getName());
                sql += " and name like ?";
                args.add("%" + room.getName() + "%");
            }
            if (StringUtils.isNotBlank(room.getOpenfireRoom())) {
                sql += " and openfire_room = ?";
                args.add(room.getOpenfireRoom());
            }
            if (room.getOnline() != null) {
                sql += " and online = ?";
                args.add(room.getOnline());
            }
            if (room.getSign() != null) {
                sql += " and sign = ?";
                args.add(room.getSign());
            }
        }
        return find(sql, args.toArray(), new MultiRow<YwUserRoom>());
    }

    @Override
    public List<YwUserRoom> getYwUserRoomList(YwUserRoom room, String[] uids, PageDto page, Boolean isBan, Boolean isShow) {
        //设置编码问题
        if (room != null) {
            setUtf8mb4(room.getName());
        }

        String sql = "select * from yw_user_room where 1 = 1 and online <> 4";
        List<Object> args = new ArrayList<Object>();
        if (room != null) {
            if (room.getUid() != null) {
                sql += " and uid = ?";
                args.add(room.getUid());
            }
            if (StringUtils.isNotBlank(room.getRoomId())) {
                sql += " and room_id = ?";
                args.add(room.getRoomId());
            }
            if (StringUtils.isNotBlank(room.getName())) {
                sql += " and name = ?";
                args.add(room.getName());
            }
            if (room.getOnline() != null) {
                sql += " and online = ?";
                args.add(room.getOnline());
            }
            if (room.getGameIds() != null) {
                sql += " and game_id in" + SQLUtils.toSQLInString(room.getGameIds().toArray());
            }
            if (room.getIdInt() != null) {
                sql += " and id_int = ?";
                args.add(room.getIdInt());
            }
            if (StringUtils.isNotBlank(room.getSign())) {
                sql += " and sign = ?";
                args.add(room.getSign());
            }
        }
        if (uids != null) {
            sql += " and uid in" + SQLUtils.toSQLInString(uids);
        }
        if (isBan != null) {
            if (isBan) {
                sql += " and online <> 2";
            } else {
                sql += " and online = 2";
            }
        }

        if (isShow != null) {
            if (isShow) {
                sql += " and online <> 4";
            } else {
                sql += " and online = 4";
            }
        }


        if (room != null && StringUtils.isNotBlank(room.getOrderSql())) {
            sql += " order by " + room.getOrderSql();
        } else {
            sql += " order by order_id, online desc, fans desc, create_time desc";
        }

        if (page == null) {
            return find(sql, args.toArray(), new MultiRow<YwUserRoom>());
        } else {
            return findForPage(sql, args.toArray(), new MultiRow<YwUserRoom>(), page);
        }
    }

    @Override
    public void updateBySql(String[] ids, Integer status, String remarks) {
        String sql = "update yw_user_room set online = ? where online <> 4 and id in";
        if (remarks != null) {
            sql = "update yw_user_room set online = ?,remarks=? where online <> 4 and id in";
            executeUpdateForInSQL(sql, new Object[]{status, remarks}, ids);
        } else {
            executeUpdateForInSQL(sql, new Object[]{status}, ids);
        }
    }

    @Override
    public Map<Integer, YwUserRoom> getYwUserRoomMapByUIds(String[] uids) {
        String sql = "select * from yw_user_room where online <> 4 and uid in";
        return queryForMap(sql, null, uids, new MapIdRow<Integer, YwUserRoom>("uid", Integer.class));
    }

    @Override
    public Integer updateByRoomId(YwUserRoom room) {
        String sql = "update yw_user_room set number = ?, update_time = ? where room_id = ?";
        return update(sql, new Object[]{room.getNumber(), room.getUpdateTime(), room.getRoomId()});
    }

    @Override
    public Integer updateLiveImgByRoomId(YwUserRoom room) {
        String sql = "update yw_user_room set live_img = ? where room_id = ?";
        return update(sql, new Object[]{room.getLiveImg(), room.getRoomId()});
    }

    @Override
    public Integer updateReset(Date date) {
        String sql = "update yw_user_room set number = 0, update_time = ? where update_time < ?";
        return update(sql, new Object[]{date, date});
    }

    @Override
    public List<Map<String, Object>> getListMapRoomInfo(String name, List<Integer> listInStatus) {
        StringBuilder sql = new StringBuilder("select id, name from yw_user_room where 1 = 1 and online <> 4");
        List<Object> args = new ArrayList<Object>();
        if (StringUtils.isNotBlank(name)) {
            sql.append(" and name like ?");
            args.add(name + "%");
        }
        if (CollectionUtils.isNotEmpty(listInStatus)) {
            sql.append(" and online in " + SQLUtils.toSQLInString(listInStatus.toArray(new Integer[]{})));
        }
        return queryForList(sql.toString(), args.toArray());
    }

    @Override
    public List<YwUserRoom> listYwUserRoomList(YwUserRoom entity, String[] ids) {
        StringBuilder sql = new StringBuilder("select * from yw_user_room where 1 = 1 and online <> 4");
        List<Object> args = new ArrayList<Object>();
        if (entity != null) {
            if (StringUtils.isNotBlank(entity.getName())) {
                sql.append(" and name = ?");
                args.add(entity.getName());
            }
        }
        if (ids != null) {
            sql.append(" and id in" + SQLUtils.toSQLInString(ids));
        }
        return find(sql.toString(), args.toArray(), new MultiRow<YwUserRoom>());
    }

    @Override
    public List<YwUserRoom> getAllLiveListByRoome(YwUserRoom entity, PageDto page, String[] ids) {
        StringBuilder sql = new StringBuilder("select * from yw_user_room where online <> 4 and " + getNotEmptySql("game_id"));
        List<Object> args = new ArrayList<Object>();
        if (entity != null) {
            if (StringUtils.isNotBlank(entity.getGameId())) {
                sql.append(" and game_id = ?");
                args.add(entity.getGameId());
            }
            if (entity.getOnline() != null) {
                sql.append(" and online = ?");
                args.add(entity.getOnline());
            }
        }
        if (ids != null) {
            sql.append(" and id in" + SQLUtils.toSQLInString(ids));
        }

        if (entity != null) {
            if (StringUtils.isNotBlank(entity.getOrderSql())) {
                sql.append(" order by ").append(entity.getOrderSql());
            }
        }
        if (page == null) {
            return find(sql.toString(), args.toArray(), new MultiRow<YwUserRoom>());
        } else {
            return findForPage(sql.toString(), args.toArray(), new MultiRow<YwUserRoom>(), page);
        }
    }

    @Override
    public List<YwUserRoom> getYwUserRoomByIds(String[] ids, PageDto page) {
        String sql = "select * from yw_user_room where online <> 4 and id in " + SQLUtils.toSQLInString(ids) + " order by online desc, (number * multiple_number) + base_number desc, fans desc";
//		return findForInSQL(sql, null, ids, new MultiRow<YwUserRoom>());
        if (page == null) {
            return find(sql, null, new MultiRow<YwUserRoom>());
        } else {
            return findForPage(sql, null, new MultiRow<YwUserRoom>(), page);
        }
    }

    @Override
    public Integer getRoomCount() {
        return queryForInt("select count(1) from yw_user_room where online = 1");
    }

    @Override
    public int updateRoomId(String id, String roomId) {
        String sql = "update yw_user_room set  room_id = ? where id = ?";
        Object[] args = new Object[]{
                roomId, id
        };
        return update(sql, args);
    }

    @Override
    public List<YwUserRoom> getYwUserRoomIsHot(YwUserRoom entity, PageDto page) {
        StringBuilder sql = new StringBuilder("select r.* FROM yw_user_room r inner join yw_user_room_hot h ON r.id = h.room_id where 1 = 1 and online <> 4");
        List<Object> args = new ArrayList<Object>();
        if (entity != null) {
            if (entity.getOnline() != null) {
                sql.append(" and r.online = ?");
                args.add(entity.getOnline());
            }
            if (StringUtils.isNotBlank(entity.getRoomHotType())) {
                if ("1".equals(entity.getRoomHotType())) {
                    sql.append(" and h.type = ?");
                    args.add(entity.getRoomHotType());
                } else {
                    sql.append(" and h.type != '1'");
                }

            }
        }

        sql.append(" order by h.order_id asc, r.number*r.multiple_number+r.base_number desc, h.create_time desc");

        if (page == null) {
            return find(sql.toString(), args.toArray(), new MultiRow<YwUserRoom>());
        } else {
            return findForPage(sql.toString(), args.toArray(), new MultiRow<YwUserRoom>(), page);
        }
    }

    @Override
    public List<Map<String, Object>> getBannerUserRoom(YwBanner ywBanner, Integer online, PageDto page) {
        StringBuilder sql = new StringBuilder("select b.id as bannerId, r.id_int as idInt from yw_banner b left join yw_user_room r on b.room_id = r.id_int where 1=1 ");
        List<Object> args = new ArrayList<Object>();
        if (ywBanner != null) {
            if (StringUtils.isNotBlank(ywBanner.getType())) {
                sql.append(" and b.type = ?");
                args.add(ywBanner.getType());
            }
            if (StringUtils.isNotBlank(ywBanner.getClientType())) {
                sql.append(" and b.client_type = ?");
                args.add(ywBanner.getClientType());
            }
        }
        if (online != null) {
            sql.append(" and r.online = ?");
            args.add(online);
        }

        sql.append(" order by b.order_id asc");

        if (page == null) {
            return queryForList(sql.toString(), args.toArray());
        } else {
            return findListMapForPage(sql.toString(), args.toArray(), page);
        }
    }

    @Override
    public List<YwUserRoom> getYwUserRoomByNickName(String name, PageDto page) {
        //分页默认1000
        if (null == page) {
            page = new PageDto();
            page.setRowNum(1000);
        }
        String sql = "SELECT yur.* FROM yw_user_room as yur LEFT JOIN yw_user AS yu ON yur.uid = yu.id "
                + "WHERE yu.nickname LIKE '%" + name + "%'  AND yu.user_type =  1";
        return findForPage(sql, null, new MultiRow<YwUserRoom>(), page);
    }

    @Override
    public List<Map<String, Object>> getByRoomIdint(Integer id) {
        String sql = "select r.id_int as id,y.id as userId,y.username as username,y.mobile as mobile,g.name as name from yw_user_room r,yw_user y ,yw_game g where  r.uid=y.id and r.game_id=g.id and r.sign=0 and  r.id_int=? ";
        return queryForList(sql, new Object[]{id});

    }

    @Override
    public Integer updateSignByIDInts(Integer status, String[] ids) {
        String sql = "update yw_user_room set  sign = ? where  id_int in";

        return executeUpdateForInSQL(sql, new Object[]{status}, ids);
    }

    @Override
    public Integer updateRoomNumber(int type, String[] ids, Integer num) {
        String field = (1 == type) ? "multiple_number" : "base_number";
        String sql = "update yw_user_room set " + field + "=? where id in";
        return executeUpdateForInSQL(sql, new Object[]{num}, ids);
    }

    @Override
    public List<YwUserRoom> findByKeyword(String name, List<YwGame> listGame, List<Map<String, Object>> listUser, PageDto page) {
        if (StringUtils.isBlank(name)) {
            return new ArrayList<YwUserRoom>(0);
        }
        String sql = "select * from yw_user_room where online='1' and ("
                //精确查找房间号
                + "id_int = '" + name + "' or ";
        //按主播昵称查询
        if (CollectionUtils.isNotEmpty(listUser)) {
            Object[] uids = new String[listUser.size()];
            for (int i = 0; i < listUser.size(); i++) {
                uids[i] = listUser.get(i).get("id");
            }
            sql += "uid in " + SQLUtils.toSQLInString(uids) + " or ";
        }
        //按游戏名称查询
        if (CollectionUtils.isNotEmpty(listGame)) {
            Object[] gids = new String[listGame.size()];
            for (int j = 0; j < listGame.size(); j++) {
                gids[j] = listGame.get(j).getId();
            }
            sql += "game_id in " + SQLUtils.toSQLInString(gids) + " or ";
        }
        sql += "name LIKE '%" + name + "%')";
        if (page == null) {
            return find(sql.toString(), null, new MultiRow<YwUserRoom>());
        } else {
            return findForPage(sql.toString(), null, new MultiRow<YwUserRoom>(), page);
        }
    }

    @Override
    public Integer countFindByKeyword(String name, List<YwGame> listGame, List<Map<String, Object>> listUser) {
        if (StringUtils.isBlank(name)) {
            return 0;
        }
        String sql = "select count(id) from yw_user_room where online='1' and ( "
                //精确查找房间号
                + "id_int = '" + name + "' or ";
        //按主播昵称查询
        if (CollectionUtils.isNotEmpty(listUser)) {
            Object[] uids = new String[listUser.size()];
            for (int i = 0; i < listUser.size(); i++) {
                uids[i] = listUser.get(i).get("id");
            }
            sql += "uid in " + SQLUtils.toSQLInString(uids) + " or ";
        }
        //按游戏名称查询
        if (CollectionUtils.isNotEmpty(listGame)) {
            Object[] gids = new String[listGame.size()];
            for (int j = 0; j < listGame.size(); j++) {
                gids[j] = listGame.get(j).getId();
            }
            sql += "game_id in " + SQLUtils.toSQLInString(gids) + " or ";
        }
        sql += "name LIKE '%" + name + "%')";
        return queryForInt(sql.toString());
    }

    @Override
    public List<YwUserRoom> getBestUserRooms(Integer type, PageDto page) {
        return getBestUserRooms(type, page, null);
    }

    @Override
    public Map<String, Integer> getRoomNumber() {


        String sql = "select game_id as gameId,count(*) as num from yw_user_room where online = 1  group by game_id ";
        return queryForMap(sql, null, null, new MapIdRow<String, Integer>("gameId", String.class, "num", Integer.class));
    }

    @Override
    public Integer getRoomCount(String gameId) {
        if (StringUtils.isBlank(gameId)) {
            return 0;
        }
        return queryForInt("select count(1) from yw_user_room where online = 1 and game_id='" + gameId + "'");
    }

    @Override
    public List<YwUserRoom> getBestUserRooms(Integer type, PageDto page, String gameId) {
        List<Object> args = new ArrayList<Object>();

        String sql = "SELECT room.* FROM yw_user_room_hot AS hot "
                + "LEFT JOIN yw_user_room AS room "
                + "ON hot.room_id = room.id "
                + "WHERE hot.type = ? AND room.`online` = '1' ";
        args.add(type);
        if (StringUtils.isNotBlank(gameId)) {
            sql += "AND room.game_id = ? ";
            args.add(gameId);
        }
        sql += "ORDER BY "
                + "(CASE WHEN hot.order_id = '0' THEN 99 ELSE hot.order_id END)";
        return findForPage(sql, args.toArray(), new MultiRow<YwUserRoom>(), page);

    }

    @Override
    public List<YwUserRoom> getRoomOnlineFHot(int limit, String[] ids) {
        String sql = "SELECT room.*,RAND() * 1 AS ra_order "
                + "FROM yw_user_room_hot AS hot "
                + "LEFT JOIN yw_user_room  AS room "
                + "ON hot.room_id = room.id WHERE room.`online` = 1 ";
        if (null != ids) {
            sql += "AND room.id NOT IN " + SQLUtils.toSQLInString(ids);
        }
        sql += " ORDER BY ra_order";
        if (limit > 0) {
            sql += " LIMIT " + limit;
        }
        return find(sql, null, new MultiRow<YwUserRoom>());
    }

    @Override
    public List<YwUserRoom> getBestUserRoomsOnline(Integer type, PageDto page) {
        String sql = "SELECT room.* FROM yw_user_room_hot AS hot "
                + "LEFT JOIN yw_user_room AS room "
                + "ON hot.room_id = room.id "
                + "WHERE hot.type = '" + type + "'  AND room.`online` = '1'  "
                + "ORDER BY "
                + "(CASE WHEN hot.order_id = '0' THEN 99 ELSE hot.order_id END)";
        return findForPage(sql, null, new MultiRow<YwUserRoom>(), page);
    }

    @Override
    public List<YwUserRoom> getYwUserRoomIsDN(YwUserRoom entity, PageDto page) {
        StringBuilder sql = new StringBuilder("select r.* FROM yw_user_room r inner join (select distinct(room_id) from yw_user_room_hot where type in ('2','3')  order by order_id asc,create_time desc) h ON r.id = h.room_id where 1 = 1 and online <> 4");
        List<Object> args = new ArrayList<Object>();
        if (entity != null) {
            if (entity.getOnline() != null) {
                sql.append(" and r.online = ?");
                args.add(entity.getOnline());
            }
        }
        sql.append(" order by r.number*r.multiple_number+r.base_number desc");

        if (page == null) {
            return find(sql.toString(), args.toArray(), new MultiRow<YwUserRoom>());
        } else {
            return findForPage(sql.toString(), args.toArray(), new MultiRow<YwUserRoom>(), page);
        }
    }

    @Override
    public List<String> getRandLiveFHot(int limit, String[] ids) {

        String sql = "SELECT hot.room_id AS roomId FROM (SELECT room_id,RAND() AS rand FROM yw_user_room_hot) AS hot "
                + "LEFT JOIN yw_user_room AS room ON hot.room_id = room.id "
                + "WHERE room.`online` = '1'";
        if (null != ids && ids.length > 0) {
            sql += " hot.room_id NOT IN " + SQLUtils.toSQLInString(ids);
        }
        sql += " ORDER BY  hot.rand";
        if (limit > 0) {
            sql += " LIMIT " + limit;
        }
        return this.query(sql, new MultiRow<String>("roomId", String.class));
    }

    @Override
    public List<YwUserRoom> geUserRoomsByEntity(Integer type, PageDto page) {
        StringBuilder sql = new StringBuilder("select r.uid as uid,r.game_id as game_id,r.online as online, r.id as id,h.order_id as order_id  from yw_user_room r inner join yw_user_room_hot h ON r.id = h.room_id where 1 = 1 and online <2 ");
        List<Object> args = new ArrayList<Object>();
       if(type!=null)
       {
           sql.append(" and h.type=?");
           args.add(type);
       }
       // sql.append(" order by h.order_id ");
        return findForPage(sql.toString(), args.toArray(), new MultiRow<YwUserRoom>() {
            @Override
            public YwUserRoom mapRow(ResultSet rs, int n) throws SQLException {
                YwUserRoom ywUserRoom = new YwUserRoom();
                ywUserRoom.setOrderId(rs.getInt("order_id"));
                ywUserRoom.setGameId(rs.getString("game_id"));
                ywUserRoom.setId(rs.getString("id"));
                ywUserRoom.setUid(rs.getString("uid"));
                ywUserRoom.setOnline(rs.getInt("online"));

                return ywUserRoom;
            }
        }, page);
    }

	@Override
	public List<YwUserRoom> getAllOnlineLiveSortByHot(Integer lanshaHotAnchor, PageDto page) {
		String sql = "SELECT CASE WHEN (hot.order_id IS NULL OR hot.type <> '" + lanshaHotAnchor
				+ "') THEN room.multiple_number * room.number + room.base_number ELSE hot.order_id + 100000000 END AS order_sort ,room.* FROM yw_user_room AS room LEFT JOIN yw_user_room_hot AS hot ON hot.room_id = room.id "
				+ "WHERE room.`online` = 1 ORDER BY order_sort DESC";
        if (page == null) {
            return find(sql, null, new MultiRow<YwUserRoom>());
        } else {
            return findForPage(sql, null, new MultiRow<YwUserRoom>(), page);
        }
	}
}
