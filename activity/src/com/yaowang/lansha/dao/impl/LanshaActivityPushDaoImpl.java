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
import com.yaowang.lansha.dao.LanshaActivityPushDao;
import com.yaowang.lansha.entity.LanshaActivityPush;

/**
 * 精彩活动表 
 * @author 
 * 
 */
@SuppressWarnings("unchecked")
@Repository("lanshaActivityPushDao")
public class LanshaActivityPushDaoImpl extends BaseDaoImpl<LanshaActivityPush> implements LanshaActivityPushDao{
	@Override
	public LanshaActivityPush setField(ResultSet rs) throws SQLException{
		LanshaActivityPush entity = new LanshaActivityPush();
		entity.setId(rs.getString("id"));
		entity.setTitle(rs.getString("title"));
		entity.setIndexImg(rs.getString("index_img"));
		entity.setActivityUrl(rs.getString("activity_url"));
		entity.setRemark(rs.getString("remark"));
        entity.setOrderId(rs.getInt("order_id"));
		entity.setCreateTime(rs.getTimestamp("create_time"));
		return entity;
	}
	
	@Override
	public LanshaActivityPush save(LanshaActivityPush entity){
		String sql = "insert into lansha_activity_push(id, title, index_img, activity_url, remark,order_id,create_time) values(?,?,?,?,?,?,?)";
		if (StringUtils.isBlank(entity.getId())){
			entity.setId(createId());
		}
		Object[] args = new Object[]{
			entity.getId(), entity.getTitle(), 
            entity.getIndexImg(), entity.getActivityUrl(), 
            entity.getRemark(),entity.getOrderId(), entity.getCreateTime()
		};
		update(sql, args);
		return entity;
	}
	
	@Override
	public Integer delete(String[] ids){
		String sql = "delete from lansha_activity_push where id in";
		return executeUpdateForInSQL(sql, null, ids);
	}
	
	@Override
	public Integer update(LanshaActivityPush entity){
		String sql = "update lansha_activity_push set title = ?, index_img = ?, activity_url = ?, remark = ?, order_id = ?,create_time = ? where id = ?";
		Object[] args = new Object[]{
			entity.getTitle(), entity.getIndexImg(), 
            entity.getActivityUrl(), entity.getRemark(),entity.getOrderId(),
            entity.getCreateTime(), entity.getId()
		};
		return update(sql, args);
	}

	@Override
	public LanshaActivityPush getLanshaActivityPushById(String id){
		String sql = "select * from lansha_activity_push where id = ?";
		return (LanshaActivityPush) findForObject(sql, new Object[] { id }, new MultiRow<LanshaActivityPush>());
	}

	@Override
	public Map<String, LanshaActivityPush> getLanshaActivityPushMapByIds(String[] ids){
		String sql = "select * from lansha_activity_push where id in";
		return queryForMap(sql, null, ids, new MapIdRow<String, LanshaActivityPush>("id", String.class));
	}

	@Override
	public List<LanshaActivityPush> getLanshaActivityPushList(LanshaActivityPush entity){
		String sql = "select * from lansha_activity_push where 1=1";
		List<Object> args = new ArrayList<Object>();
		if(entity != null){
			
		}
		return find(sql, args.toArray(), new MultiRow<LanshaActivityPush>());
	}

	@Override
	public List<LanshaActivityPush> getLanshaActivityPushPage(LanshaActivityPush entity, PageDto page){
		StringBuffer sql = new StringBuffer("select * from lansha_activity_push where 1=1");
		List<Object> args = new ArrayList<Object>();
		if(entity != null){
            if (StringUtils.isNotBlank(entity.getTitle())) {
                sql.append(" and title like ?");
                args.add("%"+entity.getTitle()+"%");
            }
		}
		sql.append(" order by order_id asc");
		return findForPage(sql.toString(), args.toArray(), new MultiRow<LanshaActivityPush>(), page);
	}
	
}
