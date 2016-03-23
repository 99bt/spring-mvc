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
import com.yaowang.lansha.dao.LanshaGiftDao;
import com.yaowang.lansha.entity.LanshaGift;

/**
 * 礼物 
 * @author 
 * 
 */
@SuppressWarnings("unchecked")
@Repository("lanshaGiftDao")
public class LanshaGiftDaoImpl extends BaseDaoImpl<LanshaGift> implements LanshaGiftDao{
	@Override
	public LanshaGift setField(ResultSet rs) throws SQLException{
		LanshaGift entity = new LanshaGift();
		entity.setId(rs.getString("id"));
		entity.setName(rs.getString("name"));
		entity.setStatus(rs.getString("status"));
		entity.setBi(rs.getInt("bi"));
		entity.setImg(rs.getString("img"));
		entity.setOrderid(rs.getInt("orderid"));
		entity.setCreateTime(rs.getTimestamp("create_time"));
		return entity;
	}
	
	@Override
	public LanshaGift save(LanshaGift entity){
		String sql = "insert into lansha_gift(id, name, status, bi, img, orderid, create_time) values(?,?,?,?,?,?,?)"; 
		if (StringUtils.isBlank(entity.getId())){
			entity.setId(createId());
		}
		Object[] args = new Object[]{
			entity.getId(), entity.getName(), 
            entity.getStatus(), entity.getBi(), 
            entity.getImg(), entity.getOrderid(), 
            entity.getCreateTime()
		};
		update(sql, args);
		return entity;
	}
	
	@Override
	public Integer delete(String[] ids){
		String sql = "delete from lansha_gift where id in";
		return executeUpdateForInSQL(sql, null, ids);
	}
	
	@Override
	public Integer update(LanshaGift entity){
		String sql = "update lansha_gift set name = ?, status = ?, bi = ?, img = ?, orderid = ?, create_time = ? where id = ?";
		Object[] args = new Object[]{
			entity.getName(), entity.getStatus(), 
            entity.getBi(), entity.getImg(), 
            entity.getOrderid(), entity.getCreateTime(), 
            entity.getId()
		};
		return update(sql, args);
	}

	@Override
	public LanshaGift getLanshaGiftById(String id){
		String sql = "select * from lansha_gift where id = ?";
		return (LanshaGift) findForObject(sql, new Object[] { id }, new MultiRow<LanshaGift>());
	}

	@Override
	public Map<String, LanshaGift> getLanshaGiftMapByIds(String[] ids){
		String sql = "select * from lansha_gift where id in";
		return queryForMap(sql, null, ids, new MapIdRow<String, LanshaGift>("id", String.class));
	}

	@Override
	public List<LanshaGift> getLanshaGiftList(LanshaGift entity){
		String sql = "select * from lansha_gift where 1=1";
		List<Object> args = new ArrayList<Object>();
		if(entity != null){
			
		}
		return find(sql, args.toArray(), new MultiRow<LanshaGift>());
	}

	@Override
	public List<LanshaGift> getLanshaGiftPage(LanshaGift entity, PageDto page){
		String sql = "select * from lansha_gift where 1=1";
		List<Object> args = new ArrayList<Object>();
		if(entity != null){
			if(StringUtils.isNotBlank(entity.getName())){
				sql += " and name = ? ";
				args.add(entity.getName());
			}
			if(StringUtils.isNotBlank(entity.getStatus())){
				sql += " and status = ? ";
				args.add(entity.getStatus());
			}
		}
		sql +=" ORDER BY create_time DESC";
		if(page == null){
			return find(sql, args.toArray(), new MultiRow<LanshaGift>());
		}else{
			return findForPage(sql, args.toArray(), new MultiRow<LanshaGift>(), page);
		}
	}
	
}
