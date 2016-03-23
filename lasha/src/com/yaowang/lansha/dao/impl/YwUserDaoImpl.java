package com.yaowang.lansha.dao.impl;

import com.yaowang.common.dao.BaseDaoImpl;
import com.yaowang.common.dao.PageDto;
import com.yaowang.common.dao.SQLUtils;
import com.yaowang.lansha.common.constant.LanshaConstant;
import com.yaowang.lansha.dao.YwUserDao;
import com.yaowang.lansha.entity.YwUser;
import com.yaowang.util.DateUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 用户基本信息表
 *
 * @author
 */
@SuppressWarnings("unchecked")
@Repository("ywUserDao")
public class YwUserDaoImpl extends BaseDaoImpl<YwUser> implements YwUserDao {
    @Override
    public YwUser setField(ResultSet rs) throws SQLException {
        YwUser entity = new YwUser();
        entity.setId(rs.getString("id"));
        entity.setUsername(rs.getString("username"));
        entity.setAccount(rs.getString("account"));
        entity.setAccountType(rs.getString("account_type"));
        entity.setNickname(rs.getString("nickname"));
        entity.setPassword(rs.getString("password"));
        entity.setMobile(rs.getString("mobile"));
        entity.setSex(rs.getInt("sex"));
        entity.setHeadpic(rs.getString("headpic"));
        entity.setRegChannel(rs.getString("reg_channel"));
        entity.setBirthday(rs.getTimestamp("birthday"));
        entity.setSign(rs.getString("sign"));
        entity.setBi(rs.getInt("bi"));
        entity.setJingyan(rs.getInt("jingyan"));
        entity.setIsVip(rs.getBoolean("is_vip"));
        entity.setLastLoginIp(rs.getString("last_login_ip"));
        entity.setLastLoginTime(rs.getTimestamp("last_login_time"));
        entity.setUserStatus(rs.getInt("user_status"));
        entity.setUserType(rs.getInt("user_type"));
        entity.setCreateTime(rs.getTimestamp("create_time"));
        entity.setUpdateTime(rs.getTimestamp("update_time"));
        entity.setQq(rs.getString("qq"));
        entity.setEmail(rs.getString("email"));
        entity.setAddress(rs.getString("address"));
        entity.setToken(rs.getString("token"));
        entity.setTimeLength(rs.getInt("time_length"));
        entity.setRemark(rs.getString("remark"));
        entity.setIdInt(rs.getInt("id_int"));
        entity.setAuthe(rs.getInt("authe"));
        entity.setPush(rs.getString("push"));
        entity.setParentId(rs.getString("parent_id"));
        entity.setIsRead(rs.getString("is_read"));
        entity.setDeviceId(rs.getString("device_id"));
        entity.setRegiestDeviceId(rs.getString("regiest_device_id"));
        entity.setOsType(rs.getString("os_type"));
        entity.setIsBlack(rs.getString("is_black"));
        entity.setTokenId(rs.getString("token_id"));
        entity.setOfficialType(rs.getString("official_type"));
        return entity;
    }

    @Override
    public YwUser save(YwUser entity) {
        String sql = "insert into yw_user(id, username, account, account_type, nickname, password, mobile, sex, headpic, reg_channel, birthday, sign, bi, jingyan, is_vip, last_login_ip, " +
                "last_login_time, user_status, user_type, create_time, update_time, address, qq, email, token, time_length, remark, authe, parent_id, is_read, device_id, regiest_device_id, os_type, is_black,token_id,official_type)" +
                " values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
        if (entity.getCreateTime() == null) {
            entity.setCreateTime(new Date());
        }
        if (entity.getUpdateTime() == null) {
            entity.setUpdateTime(new Date());
        }
        if (StringUtils.isEmpty(entity.getId())) {
            entity.setId(createId());
        }
        Object[] args = new Object[]{
                entity.getId(), entity.getUsername(),
                entity.getAccount(), entity.getAccountType(),
                entity.getNickname(), entity.getPassword(),
                entity.getMobile(), entity.getSex(),
                entity.getHeadpic(), entity.getRegChannel(),
                entity.getBirthday(), entity.getSign(),
                entity.getBi(), entity.getJingyan(),
                entity.getIsVip(), entity.getLastLoginIp(),
                entity.getLastLoginTime(), entity.getUserStatus(),
                entity.getUserType(), entity.getCreateTime(),
                entity.getUpdateTime(), entity.getAddress(),
                entity.getQq(), entity.getEmail(),
                entity.getToken(), entity.getTimeLength(),
                entity.getRemark(), entity.getAuthe(),
                entity.getParentId(), entity.getIsRead(),
                entity.getDeviceId(), entity.getRegiestDeviceId(),
                entity.getOsType(), entity.getIsBlack(), entity.getTokenId(),
                entity.getOfficialType()
        };
        Number idInt = saveEntityForKey(sql, args);
        entity.setIdInt(idInt.intValue());
        return entity;
    }

    @Override
    public Integer delete(String[] ids) {
        String sql = "update yw_user set user_status = 0 where id in";
        return executeUpdateForInSQL(sql, null, ids);
    }

    @Override
    public YwUser getYwUserById(String id) {
        String sql = "select * from yw_user where id = ?";
        return (YwUser) findForObject(sql, new Object[]{id}, new MultiRow<YwUser>());
    }

    @Override
    public YwUser getYwusersByToken(String token) {
        String sql = "select * from yw_user where token = ?";
        return (YwUser) findForObject(sql, new Object[]{token}, new MultiRow<YwUser>());
    }

    @Override
    public Map<String, YwUser> getYwUserMapByIds(String[] ids) {
        String sql = "select * from yw_user where id in";
        return queryForMap(sql, null, ids, new MapIdRow<Integer, YwUser>("id", String.class));
    }

    @Override
    public List<YwUser> getYwUserList(String searchNote, Integer status, PageDto page) {
        String sql = "select * from yw_user where user_status = ?";
        List<Object> args = new ArrayList<Object>();
        args.add(status);
        if (StringUtils.isNotBlank(searchNote)) {
            sql += " and (id = ? or username = ?)";
            args.add(searchNote);
            args.add(searchNote);
        }
        if (page == null) {
            return find(sql, args.toArray(), new MultiRow<YwUser>());
        } else {
            return findForPage(sql, args.toArray(), new MultiRow<YwUser>(), page);
        }
    }

    @Override
    public List<YwUser> getYwUserList(YwUser users, String[] ids, Integer[] status, PageDto page, Date startTime, Date endTime) {
        String sql = "select * from yw_user where 1=1";
        List<Object> args = new ArrayList<Object>();
        if (users != null) {
            if (users.getId() != null) {
                sql += " and id = ?";
                args.add(users.getId());
            }
            if (StringUtils.isNotBlank(users.getUsername())) {
                sql += " and username = ?";
                args.add(users.getUsername());
            }
            if (StringUtils.isNotBlank(users.getNickname())) {
                sql += " and nickname like ?";
                args.add("%" + users.getNickname() + "%");
            }
            if (users.getSex() != null) {
                sql += " and sex = ?";
                args.add(users.getSex());
            }
            if (users.getUserStatus() != null) {
                sql += " and user_status = ?";
                args.add(users.getUserStatus());
            }
            if (users.getIdInt() != null) {
                sql += " and id_int = ?";
                args.add(users.getIdInt());
            }
            if (users.getParentId() != null) {
                sql += " and parent_id = ?";
                args.add(users.getParentId());
            }
            if (users.getQueryParentId() != null) {
                sql += " and parent_id = ?";
                args.add(users.getQueryParentId());
            }
            if (users.getAdditional() == 1) {
                sql += " and parent_id  is not null and parent_id<>'' ";
            }
        }

        if (ids != null) {
            sql += " and id in" + SQLUtils.toSQLInString(ids);
        }
        if (status != null) {
            sql += " and user_status in" + SQLUtils.toSQLInString(status);
        }
        if (startTime != null) {
            sql += " and create_time >= ?";
            args.add(startTime);
        }
        if (endTime != null) {
            sql += " and create_time <= ?";
            args.add(endTime);
        }

        sql += " order by user_type, user_status, create_time desc";
        if (page == null) {
            return find(sql, args.toArray(), new MultiRow<YwUser>());
        } else {
            return findForPage(sql, args.toArray(), new MultiRow<YwUser>(), page);
        }
    }


    @Override
    public Integer updatePassword(YwUser entity) {
        String sql = "update yw_user set password = ? where id = ?";
        Object[] args = new Object[]{
                entity.getPassword(), entity.getId()
        };
        return update(sql, args);
    }


    @Override
    public YwUser getYwusersByUsername(String username, boolean containDel) {
        String sql = "select * from yw_user where username = ?";
        if (!containDel) {
            sql += " and user_status <> '" + LanshaConstant.USER_STATUS_DELETE + "'";
        }
        return (YwUser) findForObject(sql, new Object[]{username}, new MultiRow<YwUser>());
    }

    @Override
    public YwUser getYwusersByTelphone(String telphone) {
        String sql = "select * from yw_user where mobile = ?";
        return (YwUser) findForObject(sql, new Object[]{telphone}, new MultiRow<YwUser>());
    }

    @Override
    public Integer getRecommendUserNumber(YwUser entity) {
        StringBuilder sql = new StringBuilder("select count(1) from yw_user where 1 = 1");
        List<Object> args = new ArrayList<Object>();
        if (entity != null) {
            if (StringUtils.isNotBlank(entity.getId())) {
                sql.append(" and parent_id = ?");
                args.add(entity.getId());
            }
        }
        return queryForInt(sql.toString(), args.toArray());
    }

    @Override
    public List<String> getYwUserAllId(YwUser entity) {
        String sql = "select id from yw_user where 1 = 1";
        List<Object> args = new ArrayList<Object>();
        if (entity != null) {
            if (entity.getUserStatus() != null) {
                sql += " and user_status = ?";
                args.add(entity.getUserStatus());
            }
            if (StringUtils.isNotBlank(entity.getRegiestDeviceId())) {
                sql += " and regiest_device_id = ?";
                args.add(entity.getRegiestDeviceId());
            }
            if (StringUtils.isNotBlank(entity.getDeviceId())) {
                sql += " and device_id = ?";
                args.add(entity.getDeviceId());
            }
            if (StringUtils.isNotBlank(entity.getOsType())) {
                sql += " and os_type = ?";
                args.add(entity.getOsType());
            }
            if (StringUtils.isNotBlank(entity.getNickname())) {
                sql += " and nickname = ?";
                args.add(entity.getNickname());
            }
        }
        return query(sql, args.toArray(), new MultiRow<String>("id", String.class));
    }

    /**
     * 2016-02-17 增加 token_id,os_type 字段
     *
     * @param entity
     * @return
     */
    @Override
    public Integer update(YwUser entity) {
        String sql = "update yw_user set username = ?, account = ?, account_type = ?, nickname = ?, password = ?, mobile = ?, sex = ?, headpic = ?, " +
                "birthday = ?, sign = ?, bi = ?, jingyan = ?, is_vip = ?, last_login_ip = ?, last_login_time = ?, user_status = ?, " +
                "user_type = ?, qq = ?, email = ?, address = ?, update_time = ?, time_length = ?, remark = ?, authe = ? ,token =?,token_id=?,os_type=?,device_id=?,official_type=? where id = ?";
        Object[] args = new Object[]{
                entity.getUsername(),
                entity.getAccount(), entity.getAccountType(),
                entity.getNickname(), entity.getPassword(),
                entity.getMobile(), entity.getSex(),
                entity.getHeadpic(),
                entity.getBirthday(), entity.getSign(),
                entity.getBi(), entity.getJingyan(),
                entity.getIsVip(), entity.getLastLoginIp(),
                entity.getLastLoginTime(), entity.getUserStatus(),
                entity.getUserType(), entity.getQq(), entity.getEmail(),
                entity.getAddress(), entity.getUpdateTime(),
                entity.getTimeLength(), entity.getRemark(),
                entity.getAuthe(), entity.getToken(), entity.getTokenId(), entity.getOsType(), entity.getDeviceId(),entity.getOfficialType(), entity.getId()
        };
        return update(sql, args);
    }

    @Override
    public YwUser getYwusersByNickname(String nickname) {
        String sql = "select * from yw_user where nickname = ?";
        return (YwUser) findForObject(sql, new Object[]{nickname}, new MultiRow<YwUser>());
    }

    @Override
    public List<YwUser> getYwusersByMobile(YwUser entity, String[] telphones) {
        String sql = "select * from yw_user where 1=1";
        List<Object> args = new ArrayList<Object>();
        if (entity != null) {
            if (entity.getUserStatus() != null) {
                sql += " and user_status = ?";
                args.add(entity.getUserStatus());
            }
        }
        if (telphones != null) {
            sql += " and mobile in" + SQLUtils.toSQLInString(telphones);
        }
        return find(sql, args.toArray(), new MultiRow<YwUser>());
    }

    @Override
    public void updateMobile(String id, String telphone) {
        String sql = "update yw_user set mobile = ? where id = ?";
        update(sql, new Object[]{telphone, id});
    }

    @Override
    public Integer updateBatchBiAndJingyan(final List<YwUser> list) {
        String sql = "update yw_user set bi = ?, jingyan = ?, update_time = ? where id = ?";
        int[] n = batchUpdate(sql, new BatchPreparedStatementSetter() {
            @Override
            public void setValues(PreparedStatement ps, int i) throws SQLException {
                YwUser entity = list.get(i);

                ps.setInt(1, entity.getBi());
                ps.setInt(2, entity.getJingyan());
                ps.setTimestamp(3, Timestamp.valueOf(DateUtils.formatHms(entity.getUpdateTime())));
                ps.setString(4, entity.getId());
            }

            @Override
            public int getBatchSize() {
                return list.size();
            }
        });
        return n.length;
    }

    @Override
    public List<Map<String, Object>> getListMapAnchor(String name, Integer type, Integer status) {
        StringBuilder sql = new StringBuilder("select id as id, nickname as name from yw_user where 1 = 1");
        List<Object> args = new ArrayList<Object>();
        if (status != null) {
            sql.append(" and user_status = ?");
            args.add(status);
        }
        if (type != null) {
            sql.append(" and user_type = ?");
            args.add(type);
        }
        if (StringUtils.isNotBlank(name)) {
            sql.append(" and nickname like ?");
            args.add("%" + name + "%");
        }
        return queryForList(sql.toString(), args.toArray());
    }

    @Override
    public Integer updateForBase(YwUser entity) {
        String sql = "update yw_user set update_time = ?";
        if (entity.getUpdateTime() == null) {
            entity.setUpdateTime(new Date());
        }

        List<Object> args = new ArrayList<Object>();
        args.add(entity.getUpdateTime());

        if (StringUtils.isNotBlank(entity.getUsername())) {
            sql += ", username = ?";
            args.add(entity.getUsername());
        }
        if (StringUtils.isNotBlank(entity.getNickname())) {
            //设置编码问题
            setUtf8mb4(entity.getNickname());

            sql += ", nickname = ?";
            args.add(entity.getNickname());
        }
        if (entity.getSex() != null) {
            sql += ", sex = ?";
            args.add(entity.getSex());
        }
        if (StringUtils.isNotBlank(entity.getHeadpic())) {
            sql += ", headpic = ?";
            args.add(entity.getHeadpic());
        }
        if (StringUtils.isNotBlank(entity.getRemark())) {
            sql += ", remark = ?";
            args.add(entity.getRemark());
        }
        if (StringUtils.isNotBlank(entity.getPassword())) {
            sql += ", password = ?";
            args.add(entity.getPassword());
        }
        if (StringUtils.isNotBlank(entity.getOfficialType())) {
            sql += ", official_type = ?";
            args.add(entity.getOfficialType());
        }

        sql += " where id = ?";
        args.add(entity.getId());

        return update(sql, args.toArray());
    }

    @Override
    public Integer updateStatus(String[] ids, Integer status, String remark) {
        String sql = "update yw_user set user_status = ?, remark = ? where id in";
        return executeUpdateForInSQL(sql, new Object[]{status, remark}, ids);
    }

    @Override
    public Integer updateAddBi(String id, int bi, int time) {
        String sql = "update yw_user set bi = bi + ?, time_length = time_length + ? where id = ?";
        return update(sql, new Object[]{bi, time, id});
    }

    @Override
    public Integer updateReduceBi(String id, int bi) {
        String sql = "update yw_user set bi = bi - ?  where id = ? and bi - ? > 0";
        return update(sql, new Object[]{bi, id, bi});
    }

    @Override
    public Integer updatePush(String id, String push) {
        String sql = "update yw_user set push = ?  where id = ?";
        return update(sql, new Object[]{push, id});
    }

    @Override
    public void updateToken(String id, String token) {
        String sql = "update yw_user set token = ? where id = ?";
        update(sql, new Object[]{token, id});
    }

    @Override
    public List<YwUser> getRegiestCount(Date startTime, Date endTime) {
        StringBuffer hql = new StringBuffer("select DATE_FORMAT(create_time, '%Y-%m-%d') time, count(id) as regiest_count from yw_user where 1=1 ");
        List<Object> args = new ArrayList<Object>();
        if (startTime != null) {
            hql.append(" and create_time >= ?");
            args.add(startTime);
        }
        if (endTime != null) {
            hql.append(" and create_time <= ?");
            args.add(endTime);
        }
        hql.append(" group by DATE_FORMAT(create_time, '%Y-%m-%d') order by create_time");
        return find(hql.toString(), args.toArray(), new MultiRow<YwUser>() {
            @Override
            public YwUser mapRow(ResultSet rs, int n) throws SQLException {
                YwUser mgameUser = new YwUser();
                mgameUser.setRegiestCount(rs.getInt("regiest_count"));
                mgameUser.setCreateTime(rs.getDate("time"));
                return mgameUser;
            }
        });
    }

    @Override
    public YwUser getYwUserByIdInt(Integer idInt) {
        String sql = "select * from yw_user where id_int = ?";
        return (YwUser) findForObject(sql, new Object[]{idInt}, new MultiRow<YwUser>());
    }

    @Override
    public Integer updateIsRead(String id) {
        StringBuilder sql = new StringBuilder("update yw_user set is_read = '1' where id = ?");
        return update(sql.toString(), new Object[]{id});
    }

    @Override
    public Integer updateBlackList(String id, String isBlack) {
        StringBuilder sql = new StringBuilder("update yw_user set is_black = ? where id = ?");
        return update(sql.toString(), new Object[]{isBlack, id});
    }

    @Override
    public List<String> getYwUserAllIds(String[] ids) {
        String sql = "select id from yw_user where qq in";
        Object[] o = getInSQLObj(null, ids);
        return query(sql+(String)o[0], (Object[])o[1], new MultiRow("id", String.class));

    }

	@Override
	public List<String> getUserIdByDeveice(String deviceId) {
		String sql = "SELECT id FROM yw_user WHERE regiest_device_id = ? OR device_Id = ? LIMIT  1";
		return query(sql, new String[]{deviceId,deviceId}, new MultiRow("id", String.class));
	}
}
