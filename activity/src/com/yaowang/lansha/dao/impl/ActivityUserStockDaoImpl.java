package com.yaowang.lansha.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Repository;

import com.yaowang.common.dao.BaseDaoImpl;
import com.yaowang.common.dao.PageDto;
import com.yaowang.lansha.dao.ActivityUserStockDao;
import com.yaowang.lansha.entity.ActivityUserStock;

/**
 * 用户抽奖机会 
 * @author 
 * 
 */
@SuppressWarnings("unchecked")
@Repository("activityUserStockDao")
public class ActivityUserStockDaoImpl extends BaseDaoImpl<ActivityUserStock> implements ActivityUserStockDao{
	@Override
	public ActivityUserStock setField(ResultSet rs) throws SQLException{
		ActivityUserStock entity = new ActivityUserStock();
		entity.setId(rs.getString("id"));
		entity.setUserId(rs.getString("user_id"));
		entity.setItemId(rs.getString("item_id"));
		entity.setLastActivity(rs.getTimestamp("last_activity"));
		entity.setStock(rs.getInt("stock"));
		entity.setCreateTime(rs.getTimestamp("create_time"));
		return entity;
	}
	
	@Override
	public ActivityUserStock save(ActivityUserStock entity){
		String sql = "insert into activity_user_stock(id, user_id, item_id, last_activity, stock, create_time) values(?,?,?,?,?,?)"; 
		if (StringUtils.isBlank(entity.getId())){
			entity.setId(createId());
		}
		Object[] args = new Object[]{
			entity.getId(), entity.getUserId(), 
            entity.getItemId(), entity.getLastActivity(), 
            entity.getStock(), entity.getCreateTime()
		};
		update(sql, args);
		return entity;
	}
	
	@Override
	public Integer delete(String[] ids){
		String sql = "delete from activity_user_stock where id in";
		return executeUpdateForInSQL(sql, null, ids);
	}
	
	@Override
	public Integer update(ActivityUserStock entity){
		String sql = "update activity_user_stock set user_id = ?, item_id = ?, last_activity = ?, stock = ?, create_time = ? where id = ?";
		Object[] args = new Object[]{
			entity.getUserId(), entity.getItemId(), 
            entity.getLastActivity(), entity.getStock(), 
            entity.getCreateTime(), entity.getId()
		};
		return update(sql, args);
	}
	
	@Override
	public Integer updateUserLottery(ActivityUserStock entity){
		String sql = "update activity_user_stock set last_activity = ?, stock = stock+? where user_id = ? and item_id=? and (stock+?)>=0 and (isnull(last_activity) or date_add(last_activity, interval ? second)<=?)";
		Date now = new Date();
		Object[] args = new Object[]{
			now, entity.getStock(),
            entity.getUserId(), 
            entity.getItemId(),
            entity.getStock(),
            entity.getLimitTime(), now
		};
		return update(sql, args);
	}

	@Override
	public ActivityUserStock getActivityUserStockById(String id){
		String sql = "select * from activity_user_stock where id = ?";
		return (ActivityUserStock) findForObject(sql, new Object[] { id }, new MultiRow<ActivityUserStock>());
	}

	@Override
	public Map<String, ActivityUserStock> getActivityUserStockMapByIds(String[] ids){
		String sql = "select * from activity_user_stock where id in";
		return queryForMap(sql, null, ids, new MapIdRow<String, ActivityUserStock>("id", String.class));
	}

	@Override
	public List<ActivityUserStock> getActivityUserStockList(ActivityUserStock entity){
		StringBuilder sql = new StringBuilder("select * from activity_user_stock where 1=1");
		List<Object> args = new ArrayList<Object>();
		if(entity != null){
			if(StringUtils.isNotBlank(entity.getUserId())){
				sql.append(" and user_id = ?");
				args.add(entity.getUserId());
			}
			if(StringUtils.isNotBlank(entity.getItemId())){
				sql.append(" and item_id = ?");
				args.add(entity.getItemId());
			}
		}
		return find(sql.toString(), args.toArray(), new MultiRow<ActivityUserStock>());
	}

	@Override
	public List<ActivityUserStock> getActivityUserStockPage(ActivityUserStock entity, PageDto page){
		String sql = "select * from activity_user_stock where 1=1";
		List<Object> args = new ArrayList<Object>();
		if(entity != null){
			
		}
		return findForPage(sql, args.toArray(), new MultiRow<ActivityUserStock>(), page);
	}
	
	@Override
	public Integer updateUserStock(Integer add, String userId,  String itemId) {
		String sql = "update activity_user_stock set stock = stock+? where user_id = ? and item_id = ?";
		Object[] args = new Object[]{add, userId, itemId};
		return update(sql, args);
	}
}
