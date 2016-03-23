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
import com.yaowang.lansha.dao.LanshaAdvertisementDao;
import com.yaowang.lansha.entity.LanshaAdvertisement;

/**
 * 蓝鲨广告 
 * @author 
 * 
 */
@SuppressWarnings("unchecked")
@Repository("lanshaAdvertisementDao")
public class LanshaAdvertisementDaoImpl extends BaseDaoImpl<LanshaAdvertisement> implements LanshaAdvertisementDao{
	@Override
	public LanshaAdvertisement setField(ResultSet rs) throws SQLException{
		LanshaAdvertisement entity = new LanshaAdvertisement();
		entity.setId(rs.getString("id"));
		entity.setName(rs.getString("name"));
		entity.setLink(rs.getString("link"));
		entity.setImg(rs.getString("img"));
		entity.setType(rs.getString("type"));
		entity.setRate(rs.getFloat("rate"));
		entity.setStatus(rs.getString("status"));
		entity.setRemark(rs.getString("remark"));
		entity.setCreateTime(rs.getTimestamp("create_time"));
		return entity;
	}
	
	@Override
	public LanshaAdvertisement save(LanshaAdvertisement entity){
		String sql = "insert into lansha_advertisement(id, name, link, img, type, rate, status, remark, create_time) values(?,?,?,?,?,?,?,?,?)"; 
		if (StringUtils.isBlank(entity.getId())){
			entity.setId(createId());
		}
		Object[] args = new Object[]{
			entity.getId(), entity.getName(), 
            entity.getLink(), entity.getImg(), 
            entity.getType(), entity.getRate(), 
            entity.getStatus(), entity.getRemark(), 
            entity.getCreateTime()
		};
		update(sql, args);
		return entity;
	}
	
	@Override
	public Integer delete(String[] ids){
		String sql = "delete from lansha_advertisement where id in";
		return executeUpdateForInSQL(sql, null, ids);
	}
	
	@Override
	public Integer update(LanshaAdvertisement entity){
		String sql = "update lansha_advertisement set name = ?, link = ?, img = ?, type = ?, rate = ?, status = ?, remark = ? where id = ?";
		Object[] args = new Object[]{
			entity.getName(), entity.getLink(), 
            entity.getImg(), entity.getType(), 
            entity.getRate(), entity.getStatus(), 
            entity.getRemark(), 
            entity.getId()
		};
		return update(sql, args);
	}

	@Override
	public LanshaAdvertisement getLanshaAdvertisementById(String id){
		String sql = "select * from lansha_advertisement where id = ?";
		return (LanshaAdvertisement) findForObject(sql, new Object[] { id }, new MultiRow<LanshaAdvertisement>());
	}
	
	@Override
	public LanshaAdvertisement getLanshaAdvertisementByRate(Float rate){
		String sql = "select * from lansha_advertisement where rate >= ? and status=1 ORDER BY RAND() LIMIT 1";
		return (LanshaAdvertisement) findForObject(sql, new Object[] { rate}, new MultiRow<LanshaAdvertisement>());
	}

	@Override
	public Map<String, LanshaAdvertisement> getLanshaAdvertisementMapByIds(String[] ids){
		String sql = "select * from lansha_advertisement where id in";
		return queryForMap(sql, null, ids, new MapIdRow<String, LanshaAdvertisement>("id", String.class));
	}

	@Override
	public List<LanshaAdvertisement> getLanshaAdvertisementList(LanshaAdvertisement entity){
		String sql = "select * from lansha_advertisement where 1=1";
		List<Object> args = new ArrayList<Object>();
		if(entity != null){
			
		}
		return find(sql, args.toArray(), new MultiRow<LanshaAdvertisement>());
	}

	@Override
	public List<LanshaAdvertisement> getLanshaAdvertisementPage(LanshaAdvertisement entity, PageDto page){
		StringBuilder sql = new StringBuilder( "select * from lansha_advertisement where 1=1");
		List<Object> args = new ArrayList<Object>();
		if(entity != null){
			if(StringUtils.isNotEmpty(entity.getName())){
				sql.append(" and name = ?");
				args.add(entity.getName());
			}
		}
		sql.append(" order by create_time desc");
		return findForPage(sql.toString(), args.toArray(), new MultiRow<LanshaAdvertisement>(), page);
	}
	
}
