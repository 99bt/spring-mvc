package com.yaowang.lansha.dao.impl;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.stereotype.Repository;

import com.yaowang.common.dao.BaseDaoImpl;
import com.yaowang.common.dao.PageDto;
import com.yaowang.lansha.dao.LanshaUserRecordDao;
import com.yaowang.lansha.entity.LanshaUserRecord;

/**
 * 用户收支记录 
 * @author 
 * 
 */
@SuppressWarnings("unchecked")
@Repository("lanshaUserRecordDao")
public class LanshaUserRecordDaoImpl extends BaseDaoImpl<LanshaUserRecord> implements LanshaUserRecordDao{
	@Override
	public LanshaUserRecord setField(ResultSet rs) throws SQLException{
		LanshaUserRecord entity = new LanshaUserRecord();
		entity.setId(rs.getString("id"));
		entity.setUserId(rs.getString("user_id"));
		entity.setType(rs.getString("type"));
		entity.setObjectType(rs.getString("object_type"));
		entity.setObjectId(rs.getString("object_id"));
		entity.setRemark(rs.getString("remark"));
		entity.setCreateTime(rs.getTimestamp("create_time"));
		entity.setStock(rs.getInt("stock"));
		return entity;
	}
	
	@Override
	public LanshaUserRecord save(LanshaUserRecord entity){
		String sql = "insert into lansha_user_record(id, user_id, type, object_type, object_id, remark, create_time, stock) values(?,?,?,?,?,?,?,?)"; 
		if (StringUtils.isBlank(entity.getId())){
			entity.setId(createId());
		}
		Object[] args = new Object[]{
			entity.getId(), entity.getUserId(), 
            entity.getType(), entity.getObjectType(), 
            entity.getObjectId(), entity.getRemark(), 
            entity.getCreateTime(), entity.getStock()
		};
		update(sql, args);
		return entity;
	}
	
	@Override
	public Integer delete(String[] ids){
		String sql = "delete from lansha_user_record where id in";
		return executeUpdateForInSQL(sql, null, ids);
	}
	
	@Override
	public Integer update(LanshaUserRecord entity){
		String sql = "update lansha_user_record set user_id = ?, type = ?, object_type = ?, object_id = ?, remark = ?, create_time = ?, stock = ? where id = ?";
		Object[] args = new Object[]{
			entity.getUserId(), entity.getType(), 
            entity.getObjectType(), entity.getObjectId(), 
            entity.getRemark(), entity.getCreateTime(), 
            entity.getStock(), entity.getId()
		};
		return update(sql, args);
	}

	@Override
	public LanshaUserRecord getLanshaUserRecordById(String id){
		String sql = "select * from lansha_user_record where id = ?";
		return (LanshaUserRecord) findForObject(sql, new Object[] { id }, new MultiRow<LanshaUserRecord>());
	}

	@Override
	public Map<String, LanshaUserRecord> getLanshaUserRecordMapByIds(String[] ids){
		String sql = "select * from lansha_user_record where id in";
		return queryForMap(sql, null, ids, new MapIdRow<String, LanshaUserRecord>("id", String.class));
	}

	@Override
	public List<LanshaUserRecord> getLanshaUserRecordList(LanshaUserRecord entity){
		String sql = "select * from lansha_user_record where 1=1";
		List<Object> args = new ArrayList<Object>();
		if(entity != null){
			
		}
		return find(sql, args.toArray(), new MultiRow<LanshaUserRecord>());
	}

	@Override
	public List<LanshaUserRecord> getLanshaUserRecordPage(LanshaUserRecord entity, PageDto page, Date startTime, Date endTime){
		StringBuffer sql = new StringBuffer("select * from lansha_user_record where 1=1");
		List<Object> args = new ArrayList<Object>();
		if(entity != null){
			if(StringUtils.isNotBlank(entity.getObjectType())){
				sql.append(" and object_type = ?");
				args.add(entity.getObjectType());
			}
			if(StringUtils.isNotBlank(entity.getObjectId())){
				sql.append(" and object_id = ?");
				args.add(entity.getObjectId());
			}
			if(StringUtils.isNotBlank(entity.getType())){
				sql.append(" and type = ?");
				args.add(entity.getType());
			}
			if(StringUtils.isNotBlank(entity.getUserId())){
				sql.append(" and user_id = ?");
				args.add(entity.getUserId());
			}
		}
		if(startTime != null){
			sql.append(" and create_time >= ?");
			args.add(startTime);
		}
		if(endTime != null){
			sql.append(" and create_time <= ?");
			args.add(endTime);
		}
		if(page == null){
			return find(sql.toString(), args.toArray(), new MultiRow<LanshaUserRecord>());
		}else{
			return findForPage(sql.toString(), args.toArray(), new MultiRow<LanshaUserRecord>(), page);
		}
	}

	@Override
	public Integer saveBatch(List<LanshaUserRecord> list) {
		if (CollectionUtils.isNotEmpty(list)) {
			String sql = "insert into lansha_user_record(id, user_id, type, object_type, object_id, remark, create_time, stock) values(?,?,?,?,?,?,?,?)";
			final List<LanshaUserRecord> entitys = list;
			int n = batchUpdate(sql, new BatchPreparedStatementSetter() {
				@Override
				public int getBatchSize() {
					return entitys.size();
				}

				@Override
				public void setValues(PreparedStatement ps, int i) throws SQLException {
					LanshaUserRecord entity = entitys.get(i);
					if(StringUtils.isBlank(entity.getId())){
						entity.setId(createId());
					}
					if(entity.getCreateTime() == null){
						entity.setCreateTime(new Date());
					}
					ps.setString(1, entity.getId());
					ps.setString(2, entity.getUserId());
					ps.setString(3, entity.getType());
					ps.setString(4, entity.getObjectType());
					ps.setString(5, entity.getObjectId());
					ps.setString(6, entity.getRemark());
					ps.setTimestamp(7, new Timestamp(entity.getCreateTime().getTime()));
					ps.setInt(8, entity.getStock());
				}
			}).length;
			return n;
		} else {
			return 0;
		}
	}
	
}
