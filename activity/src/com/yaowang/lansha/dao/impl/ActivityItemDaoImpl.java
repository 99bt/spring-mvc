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
import com.yaowang.lansha.dao.ActivityItemDao;
import com.yaowang.lansha.entity.ActivityItem;

/**
 * 活动 
 * @author 
 * 
 */
@SuppressWarnings("unchecked")
@Repository("activityItemDao")
public class ActivityItemDaoImpl extends BaseDaoImpl<ActivityItem> implements ActivityItemDao{
	@Override
	public ActivityItem setField(ResultSet rs) throws SQLException{
		ActivityItem entity = new ActivityItem();
		entity.setId(rs.getString("id"));
		entity.setName(rs.getString("name"));
		entity.setStartTime(rs.getTimestamp("start_time"));
		entity.setStatus(rs.getString("status"));
		entity.setIndexUrl(rs.getString("index_url"));
		entity.setMiddleGift(rs.getFloat("middle_gift"));
		entity.setMaxGift(rs.getFloat("max_gift"));
		entity.setRemark(rs.getString("remark"));
		entity.setCreateTime(rs.getTimestamp("create_time"));
		entity.setChance(rs.getString("chance"));
		return entity;
	}
	
	@Override
	public ActivityItem save(ActivityItem entity){
		String sql = "insert into activity_item(id, name, start_time, status, index_url, middle_gift, max_gift, remark, create_time, chance) values(?,?,?,?,?,?,?,?,?, chance)"; 
		if (StringUtils.isBlank(entity.getId())){
			entity.setId(createId());
		}
		Object[] args = new Object[]{
			entity.getId(), entity.getName(), 
            entity.getStartTime(), entity.getStatus(), 
            entity.getIndexUrl(), entity.getMiddleGift(), 
            entity.getMaxGift(), entity.getRemark(), 
            entity.getCreateTime()
		};
		update(sql, args);
		return entity;
	}
	
	@Override
	public Integer delete(String[] ids){
		String sql = "delete from activity_item where id in";
		return executeUpdateForInSQL(sql, null, ids);
	}
	
	@Override
	public Integer update(ActivityItem entity){
		String sql = "update activity_item set name = ?, start_time = ?, status = ?, index_url = ?, middle_gift = ?, max_gift = ?, remark = ?, chance = ? where id = ?";
		Object[] args = new Object[]{
			entity.getName(), entity.getStartTime(), 
            entity.getStatus(), entity.getIndexUrl(), 
            entity.getMiddleGift(), entity.getMaxGift(),
            entity.getRemark(), entity.getChance(), entity.getId()
		};
		return update(sql, args);
	}

	@Override
	public ActivityItem getActivityItemById(String id){
		String sql = "select * from activity_item where id = ?";
		return (ActivityItem) findForObject(sql, new Object[] { id }, new MultiRow<ActivityItem>());
	}
	@Override
	public ActivityItem getActivityItemByName(String name) {
		String sql = "select * from activity_item where name = ?";
		return (ActivityItem) findForObject(sql, new Object[] { name }, new MultiRow<ActivityItem>());
	}

	@Override
	public Map<String, ActivityItem> getActivityItemMapByIds(String[] ids){
		String sql = "select * from activity_item where id in";
		return queryForMap(sql, null, ids, new MapIdRow<String, ActivityItem>("id", String.class));
	}

	@Override
	public List<ActivityItem> getActivityItemList(ActivityItem entity){
		String sql = "select * from activity_item where 1=1";
		List<Object> args = new ArrayList<Object>();
		if(entity != null){
			if(StringUtils.isNotBlank(entity.getId())){
				sql += " and id = ?";
				args.add(entity.getId());
			}
			if(StringUtils.isNotBlank(entity.getStatus())){
				sql += " and status = ?";
				args.add(entity.getStatus());
			}
			if(entity.getStartTime() != null){
				sql += " and start_time <= ?";
				args.add(entity.getStartTime());
			}
		}
		return find(sql, args.toArray(), new MultiRow<ActivityItem>());
	}

	@Override
	public List<ActivityItem> getActivityItemPage(ActivityItem entity, PageDto page) {
		StringBuilder sql = new StringBuilder("select * from activity_item where 1=1 and status <> 2");
		List<Object> args = new ArrayList<Object>();
		if (entity != null) {
			if(StringUtils.isNotBlank(entity.getName())){
				sql.append(" and name like ?");
				args.add("%"+entity.getName()+"%");
			}
		}
		sql.append(" order by status desc, create_time desc");
		if (page == null) {
			return find(sql.toString(), args.toArray(), new MultiRow<ActivityItem>());
		} else {
			return findForPage(sql.toString(), args.toArray(), new MultiRow<ActivityItem>(), page);
		}
	}

	@Override
	public void updateStatusById(String id, String status) {
		String sql = "update activity_item set status = ? where id = ?";
		update(sql, new Object[] { status, id });
	}


}
