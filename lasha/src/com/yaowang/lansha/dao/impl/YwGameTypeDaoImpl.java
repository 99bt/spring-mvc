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
import com.yaowang.lansha.dao.YwGameTypeDao;
import com.yaowang.lansha.entity.YwGameType;

/**
 * yw_game_type 
 * @author 
 * 
 */
@SuppressWarnings("unchecked")
@Repository("ywGameTypeDao")
public class YwGameTypeDaoImpl extends BaseDaoImpl<YwGameType> implements YwGameTypeDao{
	@Override
	public YwGameType setField(ResultSet rs) throws SQLException{
		YwGameType entity = new YwGameType();
		entity.setId(rs.getString("id"));
		entity.setName(rs.getString("name"));
		entity.setOrderId(rs.getInt("order_id"));
		entity.setStatus(rs.getString("status"));
		entity.setCreateTime(rs.getTimestamp("create_time"));
		return entity;
	}
	
	@Override
	public YwGameType save(YwGameType entity){
		String sql = "insert into yw_game_type(id, name, order_id, status, create_time) values(?,?,?,?,?)"; 
		if(entity.getCreateTime() == null){
			entity.setCreateTime(new Date());
		}
		if (StringUtils.isEmpty(entity.getId())) {
			entity.setId(createId());
		}
		Object[] args = new Object[]{
			entity.getId(), entity.getName(), entity.getOrderId(), 
			entity.getStatus(), entity.getCreateTime()
		};
		update(sql, args);
		return entity;
	}
	
	@Override
	public Integer delete(String[] ids){
		String sql = "delete from yw_game_type where id in";
		return executeUpdateForInSQL(sql, null, ids);
	}
	
	@Override
	public YwGameType getYwGameTypeById(String id){
		String sql = "select * from yw_game_type where id = ?";
		return (YwGameType) findForObject(sql, new Object[] { id }, new MultiRow<YwGameType>());
	}

	@Override
	public Map<String, YwGameType> getYwGameTypeMapByIds(String[] ids){
		String sql = "select * from yw_game_type where id in";
		return queryForMap(sql, null, ids, new MapIdRow<Integer, YwGameType>("id", String.class));
	}

	@Override
	public List<YwGameType> getYwGameTypeList(){
		String sql = "select * from yw_game_type";
		return find(sql, null, new MultiRow<YwGameType>());
	}

	@Override
	public List<YwGameType> getYwGameTypeList(YwGameType entity, PageDto page){
		String sql = "select * from yw_game_type where 1=1 and status <> 0";
		List<Object> args = new ArrayList<Object>();
		
		if(entity != null){
			if(StringUtils.isNotBlank(entity.getName())){
				sql += " and name = ?";
				args.add(entity.getName());
			}
			if(entity.getStatus() != null){
				sql += " and status = ?";
				args.add(entity.getStatus());
			}
		}
		sql += " order by order_id, create_time desc";
		if(page == null){
			return find(sql, args.toArray(), new MultiRow<YwGameType>());
		}else{
			return findForPage(sql, args.toArray(), new MultiRow<YwGameType>(), page);
		}
	}

	@Override
	public Integer updateStatus(String[] ids, Integer status) {
		String sql = "update yw_game_type set status = ? where id in";
		return executeUpdateForInSQL(sql, new Integer[]{status}, ids);
	}

	@Override
	public Integer update(YwGameType entity) {
		String sql = "update yw_game_type set name = ?, order_id = ?, status = ? where id = ?";
		Object[] args = new Object[]{entity.getName(), entity.getOrderId(), entity.getStatus(), entity.getId()};
		return update(sql, args);
	}
	
}
