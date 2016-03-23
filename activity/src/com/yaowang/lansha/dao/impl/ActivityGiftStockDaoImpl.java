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
import com.yaowang.lansha.dao.ActivityGiftStockDao;
import com.yaowang.lansha.entity.ActivityGiftStock;

/**
 * 活动礼品库存 
 * @author 
 * 
 */
@SuppressWarnings("unchecked")
@Repository("activityGiftStockDao")
public class ActivityGiftStockDaoImpl extends BaseDaoImpl<ActivityGiftStock> implements ActivityGiftStockDao{
	@Override
	public ActivityGiftStock setField(ResultSet rs) throws SQLException{
		ActivityGiftStock entity = new ActivityGiftStock();
		entity.setId(rs.getString("id"));
		entity.setItemId(rs.getString("item_id"));
		entity.setGiftId(rs.getString("gift_id"));
		entity.setUserId(rs.getString("user_id"));
		entity.setStatus(rs.getString("status"));
		entity.setType(rs.getString("type"));
		entity.setAdminId(rs.getString("admin_id"));
		entity.setCreateTime(rs.getTimestamp("create_time"));
		entity.setRemark(rs.getString("remark"));
		entity.setIp(rs.getString("ip"));
		return entity;
	}
	
	@Override
	public ActivityGiftStock setFields(ResultSet rs) throws SQLException{
		ActivityGiftStock entity = new ActivityGiftStock();
		entity.setId(rs.getString("id"));
		entity.setItemId(rs.getString("item_id"));
		entity.setItemName(rs.getString("item_name"));
		entity.setGiftId(rs.getString("gift_id"));
		entity.setGiftName(rs.getString("gift_name"));
		entity.setUserId(rs.getString("user_id"));
		entity.setUserName(rs.getString("username"));
		entity.setRealName(rs.getString("realname"));
		entity.setNickName(rs.getString("nickname"));
		entity.setStatus(rs.getString("status"));
		entity.setType(rs.getString("type"));
		entity.setAdminId(rs.getString("admin_id"));
		entity.setCreateTime(rs.getTimestamp("create_time"));
		entity.setRemark(rs.getString("remark"));
		entity.setIp(rs.getString("ip"));
		entity.setAddress(rs.getString("address"));
		entity.setMobile(rs.getString("telphone"));
		entity.setAdminName(rs.getString("admin_name"));
		entity.setQq(rs.getString("qq"));
		return entity;
	}
	
	@Override
	public ActivityGiftStock save(ActivityGiftStock entity){
		String sql = "insert into activity_gift_stock(id, item_id, gift_id, user_id, status, type, admin_id, create_time, remark, ip) values(?,?,?,?,?,?,?,?,?,?)"; 
		if (StringUtils.isBlank(entity.getId())){
			entity.setId(createId());
		}
		Object[] args = new Object[]{
			entity.getId(), entity.getItemId(), 
            entity.getGiftId(), entity.getUserId(), 
            entity.getStatus(), entity.getType(), 
            entity.getAdminId(), entity.getCreateTime(), 
            entity.getRemark(), entity.getIp()
		};
		update(sql, args);
		return entity;
	}
	
	@Override
	public Integer delete(String[] ids){
		String sql = "delete from activity_gift_stock where id in";
		return executeUpdateForInSQL(sql, null, ids);
	}
	
	@Override
	public Integer update(ActivityGiftStock entity){
		String sql = "update activity_gift_stock set item_id = ?, gift_id = ?, user_id = ?, status = ?, type = ?, admin_id = ?, create_time = ?, remark = ?, ip = ? where id = ?";
		Object[] args = new Object[]{
			entity.getItemId(), entity.getGiftId(), 
            entity.getUserId(), entity.getStatus(), 
            entity.getType(), entity.getAdminId(), 
            entity.getCreateTime(), entity.getRemark(), 
            entity.getIp(), entity.getId()
		};
		return update(sql, args);
	}
	
	@Override
	public Integer updateForDoGift(ActivityGiftStock entity){
		String sql = "update activity_gift_stock set status = ?,admin_id = ?,remark = ? where status='0' and id = ?";
		Object[] args = new Object[]{ 
			entity.getStatus(), 
			entity.getAdminId(), 
			entity.getRemark(), 
			entity.getId()
		};
		return update(sql, args);
	}
	
	public Integer updateForDoBatchGifts(String[] ids,ActivityGiftStock entity){
		String sql = "update activity_gift_stock set status = ?,admin_id = ?,remark = ? where status='0' and id in";
		Object[] args = new Object[]{ 
				entity.getStatus(), 
				entity.getAdminId(), 
				entity.getRemark()
		};
		return executeUpdateForInSQL(sql, args, ids);
	}

	@Override
	public ActivityGiftStock getActivityGiftStockById(String id){
		String sql = "select * from activity_gift_stock where id = ?";
		return (ActivityGiftStock) findForObject(sql, new Object[] { id }, new MultiRow<ActivityGiftStock>());
	}

	@Override
	public Map<String, ActivityGiftStock> getActivityGiftStockMapByIds(String[] ids){
		String sql = "select * from activity_gift_stock where id in";
		return queryForMap(sql, null, ids, new MapIdRow<String, ActivityGiftStock>("id", String.class));
	}
	
	@Override
	public List<ActivityGiftStock> getIndexActivityGiftStockList(ActivityGiftStock entity){
		StringBuilder sql = new StringBuilder("select * from activity_gift_stock where (status=0 or status=1)");
		List<Object> args = new ArrayList<Object>();
		if(entity != null){
			if(StringUtils.isNotBlank(entity.getItemId())){
				sql.append(" and item_id = ?");
				args.add(entity.getItemId());
			}
			if(StringUtils.isNotBlank(entity.getUserId())){//领取人
				sql.append(" and user_id = ?");
				args.add(entity.getUserId());
			}
			sql.append(" order by create_time desc");
			if(entity.getLimitRows()!=null)
			{
				sql.append(" limit ?");
				args.add(entity.getLimitRows());
			}
		}
		
		return find(sql.toString(), args.toArray(), new MultiRow<ActivityGiftStock>());
	}

	@Override
	public List<ActivityGiftStock> getActivityGiftStockList(ActivityGiftStock entity){
		StringBuilder sql = new StringBuilder("select * from activity_gift_stock where 1=1");
		List<Object> args = new ArrayList<Object>();
		if(entity != null){
			if(StringUtils.isNotBlank(entity.getItemId())){
				sql.append(" and item_id = ?");
				args.add(entity.getItemId());
			}
			if(StringUtils.isNotBlank(entity.getUserId())){//领取人
				sql.append(" and user_id = ?");
				args.add(entity.getUserId());
			}
			if(StringUtils.isNotBlank(entity.getStatus()))
			{
				if(entity.getStatus().indexOf(",")>0)
				{
					sql.append(" and status in ?");
				}else{
					sql.append(" and status = ?");
				}				
				args.add(entity.getStatus());
			}
		}
		sql.append(" order by create_time desc");
		return find(sql.toString(), args.toArray(), new MultiRow<ActivityGiftStock>());
	}

	@Override
	public List<ActivityGiftStock> getActivityGiftStockPage(ActivityGiftStock entity, PageDto page){
		String sql = "select * from activity_gift_stock where 1=1";
		List<Object> args = new ArrayList<Object>();
		if(entity != null){
			sql += " and user_id = ?";
			args.add(entity.getUserId());
		}
		sql += " order by create_time desc ";
		if(page == null){
			return find(sql.toString(), args.toArray(), new MultiRow<ActivityGiftStock>());
		}else{
			return findForPage(sql, args.toArray(), new MultiRow<ActivityGiftStock>(), page);
		}
	}
	
	@Override
	public List<ActivityGiftStock> getActivityGiftStockPage(ActivityGiftStock entity,Date startTime, Date endTime, PageDto page){
		StringBuilder sql = new StringBuilder("select * from activity_gift_stock where (type='3' or type='4')");
		List<Object> args = new ArrayList<Object>();
		if(entity != null){
			if(StringUtils.isNotBlank(entity.getItemId())){
				sql.append(" and item_id = ?");
				args.add(entity.getItemId());
			}
			if(StringUtils.isNotBlank(entity.getGiftId())){
				sql.append(" and gift_id= ?");
				args.add(entity.getGiftId());
			}
			if(StringUtils.isNotBlank(entity.getStatus())){
				sql.append(" and status = ?");
				args.add(entity.getStatus());
			}
			if(StringUtils.isNotBlank(entity.getUserId())){
				sql.append(" and user_id= ?");
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
		sql.append(" order by create_time desc");
		if(page==null){
			return find(sql.toString(), args.toArray(), new MultiRow<ActivityGiftStock>());
		}else{
			return findForPage(sql.toString(), args.toArray(), new MultiRow<ActivityGiftStock>(), page);
		}
	}
	
	@Override
	public List<ActivityGiftStock> getActivityGiftStockPages(ActivityGiftStock entity,Date startTime, Date endTime, PageDto page) {
		StringBuilder sql = new StringBuilder("select * from activity_gift_stock where 1=1 ");
		List<Object> args = new ArrayList<Object>();
		if(entity != null){
			if(StringUtils.isNotBlank(entity.getStatus())){
				sql.append(" and status = ?");
				args.add(entity.getStatus());
			}
			if(StringUtils.isNotBlank(entity.getItemId())){//活动
				sql.append(" and item_id = ?");
				args.add(entity.getItemId());
			}
			if(StringUtils.isNotBlank(entity.getGiftId())){//礼品
				sql.append(" and gift_id = ?");
				args.add(entity.getGiftId());
			}
			if(StringUtils.isNotBlank(entity.getUserId())){//领取人
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
		sql.append(" order by create_time desc");
		return findForPage(sql.toString(), args.toArray(), new MultiRow<ActivityGiftStock>(), page);
	}
	
	@Override
	public Integer getActivityGiftStockNumber(ActivityGiftStock entity, Date startTime, Date endTime){
		StringBuilder sql = new StringBuilder("select count(1) from activity_gift_stock where (type='3' or type='4') ");
		List<Object> args = new ArrayList<Object>();
		if(entity != null){
			if(StringUtils.isNotBlank(entity.getStatus())){
				sql.append(" and status = ?");
				args.add(entity.getStatus());
			}
			if(StringUtils.isNotBlank(entity.getItemId())){//活动
				sql.append(" and item_id = ?");
				args.add(entity.getItemId());
			}
			if(StringUtils.isNotBlank(entity.getGiftId())){//礼品
				sql.append(" and gift_id = ?");
				args.add(entity.getGiftId());
			}
			if(StringUtils.isNotBlank(entity.getUserId())){//领取人
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
		return queryForInt(sql.toString(), args.toArray());
	}
	
    public Integer updateForUserIds(String[] ids,ActivityGiftStock entity){
        String sql = "update activity_gift_stock set status = ?,admin_id = ?,remark = ? where status='0' and user_id in";
        Object[] args = new Object[]{
                entity.getStatus(),
                entity.getAdminId(),
                entity.getRemark()
        };
        return executeUpdateForInSQL(sql, args, ids);
    }
}


