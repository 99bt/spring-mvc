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
import com.yaowang.lansha.dao.YwUserRoomContractDao;
import com.yaowang.lansha.entity.YwUserRoomContract;

/**
 * 签约主播管理
 *
 * @author
 */
@SuppressWarnings("unchecked")
@Repository("ywUserRoomContractDao")
public class YwUserRoomContractDaoImpl extends BaseDaoImpl<YwUserRoomContract> implements YwUserRoomContractDao {
    @Override
    public YwUserRoomContract setField(ResultSet rs) throws SQLException {
        YwUserRoomContract entity = new YwUserRoomContract();
        entity.setUserId(rs.getString("user_id"));
        entity.setRoomId(rs.getString("room_id"));
        entity.setTimeTarget(rs.getInt("time_target"));
        entity.setVaildDays(rs.getInt("vaild_days"));
        entity.setTicketTarget(rs.getInt("ticket_target"));
        entity.setSalary(rs.getFloat("salary"));
        entity.setManager(rs.getString("manager"));
        entity.setExamine(rs.getInt("examine"));
        entity.setStartTime(rs.getTimestamp("start_time"));
        entity.setEndTime(rs.getTimestamp("end_time"));
        entity.setStatus(rs.getInt("status"));
        entity.setRemark(rs.getString("remark"));
        entity.setCreateTime(rs.getTimestamp("create_time"));
        entity.setUpdateTime(rs.getTimestamp("update_time"));
        return entity;
    }

    @Override
    public YwUserRoomContract save(YwUserRoomContract entity) {
        String sql = "insert into yw_user_room_contract(user_id, room_id, time_target, vaild_days, ticket_target, salary, manager, examine, start_time, end_time, status, remark, create_time, update_time) values(?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
        if (StringUtils.isBlank(entity.getUserId())) {
            entity.setUserId(createId());
        }
        Object[] args = new Object[]{
                entity.getUserId(), entity.getRoomId(),
                entity.getTimeTarget(), entity.getVaildDays(),
                entity.getTicketTarget(), entity.getSalary(),
                entity.getManager(), entity.getExamine(),
                entity.getStartTime(), entity.getEndTime(),
                entity.getStatus(), entity.getRemark(),
                entity.getCreateTime(), entity.getUpdateTime()
        };
        update(sql, args);
        return entity;
    }

    @Override
    public Integer delete(String[] ids) {
        String sql = "delete from yw_user_room_contract where user_id in";
        return executeUpdateForInSQL(sql, null, ids);
    }

    @Override
    public Integer update(YwUserRoomContract entity) {
        String sql = "update yw_user_room_contract set room_id = ?, time_target = ?, vaild_days = ?, ticket_target = ?, salary = ?, manager = ?, examine = ?, start_time = ?, end_time = ?, status = ?, remark = ?, create_time = ?, update_time = ? where user_id = ?";
        Object[] args = new Object[]{
                entity.getRoomId(), entity.getTimeTarget(),
                entity.getVaildDays(), entity.getTicketTarget(),
                entity.getSalary(), entity.getManager(),
                entity.getExamine(), entity.getStartTime(),
                entity.getEndTime(), entity.getStatus(),
                entity.getRemark(), entity.getCreateTime(),
                entity.getUpdateTime(), entity.getUserId()
        };
        return update(sql, args);
    }

    @Override
    public YwUserRoomContract getYwUserRoomContractById(String userId) {
        String sql = "select * from yw_user_room_contract where user_id = ?";
        return (YwUserRoomContract) findForObject(sql, new Object[]{userId}, new MultiRow<YwUserRoomContract>());
    }

    @Override
    public Map<String, YwUserRoomContract> getYwUserRoomContractMapByIds(String[] userIds) {
        String sql = "select * from yw_user_room_contract where user_id in";
        return queryForMap(sql, null, userIds, new MapIdRow<String, YwUserRoomContract>("userId", String.class));
    }

    @Override
    public List<YwUserRoomContract> getYwUserRoomContractList(YwUserRoomContract entity) {
        String sql = "select * from yw_user_room_contract where 1=1";
        List<Object> args = new ArrayList<Object>();
        if (entity != null) {

        }
        return find(sql, args.toArray(), new MultiRow<YwUserRoomContract>());
    }

    @Override
    public List<YwUserRoomContract> getYwUserRoomContractPage(YwUserRoomContract entity, PageDto page) {
        String sql = "select * from yw_user_room_contract where 1=1";
        List<Object> args = new ArrayList<Object>();
        if (entity != null) {
            sql += " and status=?";
            args.add(entity.getStatus());
            if (StringUtils.isNotEmpty(entity.getRoomId())) {
                sql += " and room_id=?";
                args.add(entity.getRoomId());
            }
            if (StringUtils.isNotEmpty(entity.getUserId())) {
                sql += " and user_id=?";
                args.add(entity.getUserId());
            }
            if (entity.getStartTime() != null) {
                sql += " and start_time>=?";
                args.add(entity.getStartTime());
            }
            if (entity.getEndTime() != null) {
                sql += " and end_time<=?";
                args.add(entity.getEndTime());
            }
            if (entity.getExamine() != null) {
                sql += " and examine=?";
                args.add(entity.getExamine());
            }
        }
        if (page == null) {
            return find(sql, args.toArray(), new MultiRow<YwUserRoomContract>());
        } else {
            return findForPage(sql, args.toArray(), new MultiRow<YwUserRoomContract>(), page);
        }
    }

    @Override
    public Integer updateStatus(String[] ids) {
        String sql = "update yw_user_room_contract set status = 0,update_time=now() where room_id  in";
        return executeUpdateForInSQL(sql, null, ids);
    }


}
