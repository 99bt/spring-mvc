package com.yaowang.lansha.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.yaowang.common.dao.BaseDaoImpl;
import com.yaowang.common.dao.PageDto;
import com.yaowang.common.dao.SQLUtils;
import com.yaowang.lansha.dao.LanshaGiftUserDao;
import com.yaowang.lansha.entity.LanshaGiftUser;
import com.yaowang.util.DateUtils;

/**
 * 礼物记录 
 * @author 
 * 
 */
@SuppressWarnings("unchecked")
@Repository("lanshaGiftUserDao")
public class LanshaGiftUserDaoImpl extends BaseDaoImpl<LanshaGiftUser> implements LanshaGiftUserDao{
	@Override
	public LanshaGiftUser setField(ResultSet rs) throws SQLException{
		LanshaGiftUser entity = new LanshaGiftUser();
		entity.setId(rs.getString("id"));
		entity.setGiftId(rs.getString("gift_id"));
		entity.setUserId(rs.getString("user_id"));
		entity.setAnchorId(rs.getString("anchor_id"));
		entity.setNumber(rs.getInt("number"));
		entity.setBi(rs.getInt("bi"));
		entity.setCreateTime(rs.getTimestamp("create_time"));
		return entity;
	}
	
	@Override
	public LanshaGiftUser save(LanshaGiftUser entity){
		String sql = "insert into lansha_gift_user(id, gift_id, user_id, anchor_id, number, bi, create_time) values(?,?,?,?,?,?,?)"; 
		if (StringUtils.isBlank(entity.getId())){
			entity.setId(createId());
		}
		Object[] args = new Object[]{
			entity.getId(), entity.getGiftId(), 
            entity.getUserId(), entity.getAnchorId(), 
            entity.getNumber(), entity.getBi(), 
            entity.getCreateTime()
		};
		update(sql, args);
		return entity;
	}
	
	@Override
	public Integer delete(String[] ids){
		String sql = "delete from lansha_gift_user where id in";
		return executeUpdateForInSQL(sql, null, ids);
	}
	
	@Override
	public Integer update(LanshaGiftUser entity){
		String sql = "update lansha_gift_user set gift_id = ?, user_id = ?, anchor_id = ?, number = ?, bi = ?, create_time = ? where id = ?";
		Object[] args = new Object[]{
				entity.getGiftId(), entity.getUserId(), 
            entity.getAnchorId(), entity.getNumber(), 
            entity.getBi(), entity.getCreateTime(), 
            entity.getId()
		};
		return update(sql, args);
	}

	@Override
	public LanshaGiftUser getLanshaGiftUserById(String id){
		String sql = "select * from lansha_gift_user where id = ?";
		return (LanshaGiftUser) findForObject(sql, new Object[] { id }, new MultiRow<LanshaGiftUser>());
	}

	@Override
	public Map<String, LanshaGiftUser> getLanshaGiftUserMapByIds(String[] ids){
		String sql = "select * from lansha_gift_user where id in";
		return queryForMap(sql, null, ids, new MapIdRow<String, LanshaGiftUser>("id", String.class));
	}

	@Override
	public List<LanshaGiftUser> getLanshaGiftUserList(LanshaGiftUser entity){
		StringBuilder sql = new StringBuilder("select * from lansha_gift_user where 1=1");
		List<Object> args = new ArrayList<Object>();
		if(entity != null){
			if (entity.getCreateTime() != null) {
				sql.append(" and create_time >= ? and create_time <= ?");
				args.add(DateUtils.getStartDate(entity.getCreateTime()));
				args.add(DateUtils.getEndDate(entity.getCreateTime()));
			}
		}
		return find(sql.toString(), args.toArray(), new MultiRow<LanshaGiftUser>());
	}

	@Override
	public List<LanshaGiftUser> getLanshaGiftUserPage(LanshaGiftUser entity, PageDto page){
		String sql = "select * from lansha_gift_user where 1=1";
		List<Object> args = new ArrayList<Object>();
		if(entity != null){
			
		}
		return findForPage(sql, args.toArray(), new MultiRow<LanshaGiftUser>(), page);
	}
	
	@Override
	public Integer getSumGiftNumber(LanshaGiftUser entity) {
		StringBuilder sql = new StringBuilder("select sum(number) from lansha_gift_user where 1=1");
		List<Object> args = new ArrayList<Object>();
		if(entity != null){
			if (entity.getCreateTime() != null) {
				sql.append(" and create_time >= ? and create_time <= ?");
				args.add(DateUtils.getStartDate(entity.getCreateTime()));
				args.add(DateUtils.getEndDate(entity.getCreateTime()));
			}
			if (StringUtils.isNotBlank(entity.getAnchorId())) {
				sql.append(" and anchor_id = ?");
				args.add(entity.getAnchorId());
			}
			if (StringUtils.isNotBlank(entity.getGiftId())) {
				sql.append(" and gift_id = ?");
				args.add(entity.getGiftId());
			}
		}
		
		return queryForInt(sql.toString(), args.toArray());
	}

    @Override
    public List<LanshaGiftUser> getSumGiftNum(LanshaGiftUser entity) {
        List<Object> args = new ArrayList<Object>();

        String sql = "select sum(number) as number,anchor_id from lansha_gift_user where gift_id=? and create_time>= ? and create_time <= ? and number>0 group by anchor_id";
        args.add(entity.getGiftId());
        args.add(DateUtils.getStartDate(entity.getCreateTime()));
        args.add(DateUtils.getEndDate(entity.getCreateTime()));
        return find(sql.toString(), args.toArray(), new RowMapper<LanshaGiftUser>() {
            @Override
            public LanshaGiftUser mapRow(ResultSet rs, int arg1) throws SQLException {
                LanshaGiftUser entity = new LanshaGiftUser();
                entity.setNumber(rs.getInt("number"));
                entity.setAnchorId(rs.getString("anchor_id"));

                return entity;
            }
        });
    }
    @Override
    public Map<String, Integer> getSumGiftNumber(String giftId, String[] userIds, Date startTime, Date endTime) {
        StringBuilder sql = new StringBuilder("select anchor_id, sum(number) as number from lansha_gift_user where 1=1");
        List<Object> args = new ArrayList<Object>();
        if(startTime != null) {
            sql.append(" and create_time >= ?");
            args.add(DateUtils.getStartDate(startTime));
        }
        if(endTime != null) {
            sql.append(" and create_time <= ?");
            args.add(DateUtils.getEndDate(endTime));
        }
        if (StringUtils.isNotBlank(giftId)) {
            sql.append(" and gift_id = ?");
            args.add(giftId);
        }
        
        sql.append(" and anchor_id in").append(SQLUtils.toSQLInString(userIds));
        sql.append(" group by anchor_id");

        return queryForMap(sql.toString(), args.toArray(), null, new MapIdRow<String, Integer>("anchor_id", String.class, "number", Integer.class));
    }
}
