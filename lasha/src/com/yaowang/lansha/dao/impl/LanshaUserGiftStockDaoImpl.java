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
import com.yaowang.lansha.dao.LanshaUserGiftStockDao;
import com.yaowang.lansha.entity.LanshaUserGiftStock;

/**
 * 礼物记录 
 * @author 
 * 
 */
@SuppressWarnings("unchecked")
@Repository("lanshaUserGiftStockDao")
public class LanshaUserGiftStockDaoImpl extends BaseDaoImpl<LanshaUserGiftStock> implements LanshaUserGiftStockDao{
	@Override
	public LanshaUserGiftStock setField(ResultSet rs) throws SQLException{
		LanshaUserGiftStock entity = new LanshaUserGiftStock();
		entity.setId(rs.getString("id"));
		entity.setUserId(rs.getString("user_id"));
		entity.setGiftId(rs.getString("gift_id"));
		entity.setStock(rs.getInt("stock"));
		entity.setCreateTime(rs.getTimestamp("create_time"));
		return entity;
	}
	
	@Override
	public LanshaUserGiftStock save(LanshaUserGiftStock entity){
		String sql = "insert into lansha_user_gift_stock(id, user_id, gift_id, stock, create_time) values(?,?,?,?,?)"; 
		if (StringUtils.isBlank(entity.getId())){
			entity.setId(createId());
		}
		if (entity.getCreateTime() == null){
			entity.setCreateTime(new Date());
		}
		Object[] args = new Object[]{
			entity.getId(), entity.getUserId(), 
            entity.getGiftId(), entity.getStock(), 
            entity.getCreateTime()
		};
		update(sql, args);
		return entity;
	}
	
	@Override
	public Integer delete(String[] ids){
		String sql = "delete from lansha_user_gift_stock where id in";
		return executeUpdateForInSQL(sql, null, ids);
	}
	
	@Override
	public Integer update(LanshaUserGiftStock entity){
		String sql = "update lansha_user_gift_stock set stock = ? where id = ?";
		Object[] args = new Object[]{
			entity.getStock(), entity.getId()
		};
		return update(sql, args);
	}
	
	@Override
	public Integer updateStockByGiftId(LanshaUserGiftStock entity){
		String sql = "update lansha_user_gift_stock set stock = ? where gift_id = ? and stock <> ?";
		Object[] args = new Object[]{
			entity.getStock(), entity.getGiftId(), entity.getStock()
		};
		return update(sql, args);
	}
	
	@Override
	public Integer updateLottery(LanshaUserGiftStock entity){
		String sql = "update lansha_user_gift_stock set stock = stock+? where user_id = ? and gift_id=?";
		Object[] args = new Object[]{
			entity.getStock(),
			entity.getUserId(), 
			entity.getGiftId()
		};
		return update(sql, args);
	}

	@Override
	public LanshaUserGiftStock getLanshaUserGiftStockById(String id){
		String sql = "select * from lansha_user_gift_stock where id = ?";
		return (LanshaUserGiftStock) findForObject(sql, new Object[] { id }, new MultiRow<LanshaUserGiftStock>());
	}

	@Override
	public Map<String, LanshaUserGiftStock> getLanshaUserGiftStockMapByIds(String[] ids){
		String sql = "select * from lansha_user_gift_stock where id in";
		return queryForMap(sql, null, ids, new MapIdRow<String, LanshaUserGiftStock>("id", String.class));
	}

	@Override
	public List<LanshaUserGiftStock> getLanshaUserGiftStockList(LanshaUserGiftStock entity){
		String sql = "select * from lansha_user_gift_stock where 1=1";
		List<Object> args = new ArrayList<Object>();
		if(entity != null){
			
		}
		return find(sql, args.toArray(), new MultiRow<LanshaUserGiftStock>());
	}

	@Override
	public List<LanshaUserGiftStock> getLanshaUserGiftStockPage(LanshaUserGiftStock entity, PageDto page){
		StringBuffer sql = new StringBuffer("select * from lansha_user_gift_stock where 1=1");
		List<Object> args = new ArrayList<Object>();
		if(entity != null){
			if(StringUtils.isNotBlank(entity.getUserId())){
				sql.append(" and user_id = ?");
				args.add(entity.getUserId());
			}
			if(StringUtils.isNotBlank(entity.getGiftId())){
				sql.append(" and gift_id = ?");
				args.add(entity.getGiftId());
			}
		}
		if(page == null){
			return find(sql.toString(), args.toArray(), new MultiRow<LanshaUserGiftStock>());
		}else{
			return findForPage(sql.toString(), args.toArray(), new MultiRow<LanshaUserGiftStock>(), page);
		}
	}

	@Override
	public Integer updateReduceStock(String id, Integer stock) {
		String sql = "update lansha_user_gift_stock set stock = stock - ? where id = ? and stock - ? >= 0";
		return update(sql, new Object[]{ stock, id, stock});
	}

	@Override
	public Map<String, Integer> getStockMapByUserIds(String[] userIds) {
		String sql = "select * from lansha_user_gift_stock where user_id in";
		return queryForMap(sql, null, userIds, new MapRowMapper<String, Integer>() {

			@Override
			public String mapRowKey(ResultSet rs) throws SQLException {
				return rs.getString("user_id") + rs.getString("gift_id");
			}

			@Override
			public Integer mapRowValue(ResultSet rs) throws SQLException {
				return rs.getInt("stock");
			}
			
		});
	}
	
}
