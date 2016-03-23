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
import com.yaowang.lansha.dao.ActivityGiftDao;
import com.yaowang.lansha.entity.ActivityGift;
import com.yaowang.lansha.entity.ActivityGiftStock;

/**
 * 活动礼品 
 * @author 
 * 
 */
@SuppressWarnings("unchecked")
@Repository("activityGiftDao")
public class ActivityGiftDaoImpl extends BaseDaoImpl<ActivityGift> implements ActivityGiftDao{
	@Override
	public ActivityGift setField(ResultSet rs) throws SQLException{
		ActivityGift entity = new ActivityGift();
		entity.setId(rs.getString("id"));
		entity.setItemId(rs.getString("item_id"));
		entity.setName(rs.getString("name"));
		entity.setStatus(rs.getString("status"));
		entity.setImg(rs.getString("img"));
		entity.setOrderId(rs.getInt("order_id"));
		entity.setRemark(rs.getString("remark"));
		entity.setCreateTime(rs.getTimestamp("create_time"));
		entity.setMoney(rs.getFloat("money"));
		entity.setType(rs.getString("type"));
		entity.setBi(rs.getInt("bi"));
		entity.setObjectId(rs.getString("object_id"));
		entity.setNumber(rs.getInt("number"));
		entity.setStock(rs.getInt("stock"));
		return entity;
	}
	
	@Override
	public ActivityGift setFields(ResultSet rs) throws SQLException{
		ActivityGift entity = new ActivityGift();
		entity.setSentGift(rs.getInt("sent_gift"));
		entity.setRemainGift(rs.getInt("remain_gift"));
		entity.setCreateTime(rs.getTimestamp("create_time"));
		
		return entity;
	}
	
	@Override
	public ActivityGift save(ActivityGift entity){
		String sql = "insert into activity_gift(id, item_id, name, status, img, order_id, remark, create_time, money, type, bi, object_id, number, stock) values(?,?,?,?,?,?,?,?,?,?,?,?,?,?)"; 
		if (StringUtils.isBlank(entity.getId())){
			entity.setId(createId());
		}
		if(entity.getCreateTime() == null){
			entity.setCreateTime(new Date());
		}
		Object[] args = new Object[]{
			entity.getId(), entity.getItemId(), 
            entity.getName(), entity.getStatus(), 
            entity.getImg(), entity.getOrderId(), 
            entity.getRemark(), entity.getCreateTime(), 
            entity.getMoney(), entity.getType(), 
            entity.getBi(), entity.getObjectId(), 
            entity.getNumber(), entity.getStock()
		};
		update(sql, args);
		return entity;
	}
	
	@Override
	public Integer delete(String[] ids){
		String sql = "update activity_gift set status = 2 where id in";
		return executeUpdateForInSQL(sql, null, ids);
	}
	
	@Override
	public Integer update(ActivityGift entity){
		String sql = "update activity_gift set name = ?, status = ?, img = ?, order_id = ?, remark = ?, money = ?, type = ?, bi = ? where id = ?";
		Object[] args = new Object[]{
			entity.getName(), 
            entity.getStatus(), entity.getImg(), 
            entity.getOrderId(), entity.getRemark(), 
            entity.getMoney(), 
            entity.getType(), entity.getBi(),
            entity.getId()
		};
		return update(sql, args);
	}
	
	@Override
	public Integer updateGiftNumberAndStock(ActivityGiftStock entity){
		String sql = "update activity_gift set number = number+?, stock = stock+? where id = ? and (stock+?)>=0";
		Object[] args = new Object[]{
			entity.getNumber(),
			entity.getStock(),
			entity.getGiftId(),
			entity.getStock()
		};
		return update(sql, args);
	}
	
	@Override
	public Integer updateLotteryGiftNumberAndStock(ActivityGiftStock entity){
		String sql = "update activity_gift set number = number+?, stock = stock+? where id = ? and status='1' and (stock+?)>=0";
		Object[] args = new Object[]{
			entity.getNumber(),
			entity.getStock(),
			entity.getGiftId(),
			entity.getStock()
		};
		return update(sql, args);
	}

	@Override
	public ActivityGift getActivityGiftById(String id){
		String sql = "select * from activity_gift where id = ?";
		return (ActivityGift) findForObject(sql, new Object[] { id }, new MultiRow<ActivityGift>());
	}
	@Override
	public ActivityGift getActivityGiftByName(String name) {
		String sql = "select * from activity_gift where name = ?";
		return (ActivityGift) findForObject(sql, new Object[] { name }, new MultiRow<ActivityGift>());
	}

	@Override
	public Map<String, ActivityGift> getActivityGiftMapByIds(String[] ids){
		String sql = "select * from activity_gift where id in";
		return queryForMap(sql, null, ids, new MapIdRow<String, ActivityGift>("id", String.class));
	}
	
	@Override
	public List<ActivityGift> getLotteryActivityGiftList(ActivityGift entity){
		StringBuilder sql = new StringBuilder("select * from activity_gift where stock>0");
		List<Object> args = new ArrayList<Object>();
		if(entity != null){
			if(StringUtils.isNotBlank(entity.getItemId())){
				sql.append(" and item_id = ?");
				args.add(entity.getItemId());
			}
			if(StringUtils.isNotBlank(entity.getStatus())){
				sql.append(" and status = ?");
				args.add(entity.getStatus());
			}
			if(StringUtils.isNotBlank(entity.getStatus())){
				sql.append(" order by money limit ?");
				args.add(entity.getLimitNumber());
			}
		}
				 
		return find(sql.toString(), args.toArray(), new MultiRow<ActivityGift>());
	}

	@Override
	public List<ActivityGift> getActivityGiftList(ActivityGift entity){
		StringBuilder sql = new StringBuilder("select * from activity_gift where 1=1");
		List<Object> args = new ArrayList<Object>();
		if(entity != null){
			if(StringUtils.isNotBlank(entity.getItemId())){
				sql.append(" and item_id = ?");
				args.add(entity.getItemId());
			}
			if(StringUtils.isNotBlank(entity.getStatus())){
				sql.append(" and status = ?");
				args.add(entity.getStatus());
			}
		}
		return find(sql.toString(), args.toArray(), new MultiRow<ActivityGift>());
	}
	
	@Override
	public List<ActivityGift> getActivityGiftList(){
		String sql = "select * from activity_gift order by order_id";
		List<Object> args = new ArrayList<Object>();
		return find(sql, args.toArray(), new MultiRow<ActivityGift>());
	}
	
	public List<ActivityGift> getActivityGiftList(ActivityGift entity,Date startTime, Date endTime)
	{
		StringBuilder sql = new StringBuilder("select ags.create_time,count(ags.id) as sent_gift,ag.stock as remain_gift from activity_gift_stock ags " +
				" left join activity_gift ag on ags.gift_id = ag.id " +
				" left join activity_item ai on ags.item_id = ai.id" +
				" where (ags.status='0' or ags.status='1')");
		List<Object> args = new ArrayList<Object>();		
		if(entity != null){
			if(StringUtils.isNotBlank(entity.getItemId())){
				sql.append(" and ags.item_id = ?");
				args.add(entity.getItemId());
			}
			if(StringUtils.isNotBlank(entity.getId())){
				sql.append(" and ags.gift_id = ?");
				args.add(entity.getId());
			}
		}
		if(startTime != null){
			sql.append(" and ags.create_time >= ?");
			args.add(startTime);
		}
		if(endTime != null){
			sql.append(" and ags.create_time <= ?");
			args.add(endTime);
		}
		sql.append(" group by ags.item_id,ags.gift_id,date_format(ags.create_time,'%Y-%c-%d') order by ags.create_time");
		return find(sql.toString(), args.toArray(), new MultiRows<ActivityGift>());
	}

	@Override
	public List<ActivityGift> getActivityGiftPage(ActivityGift entity, PageDto page) {
		StringBuilder sql = new StringBuilder("select * from activity_gift where 1=1 and status <> 2");
		List<Object> args = new ArrayList<Object>();
		if (entity != null) {
			if (StringUtils.isNotBlank(entity.getItemId())) {
				sql.append(" and item_id = ?");
				args.add(entity.getItemId());
			}
		}
		sql.append(" order by order_id asc");
		if (page == null) {
			return find(sql.toString(), args.toArray(), new MultiRow<ActivityGift>());
		} else {
			return findForPage(sql.toString(), args.toArray(), new MultiRow<ActivityGift>(), page);
		}
	}

	@Override
	public void updateStatusById(String id, String status) {
		String sql = "update activity_gift set status = ? where id = ?";
		update(sql, new Object[] { status, id });
	}

	
	
}
