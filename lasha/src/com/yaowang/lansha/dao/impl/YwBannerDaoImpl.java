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
import com.yaowang.common.dao.SQLUtils;
import com.yaowang.lansha.dao.YwBannerDao;
import com.yaowang.lansha.entity.YwBanner;

/**
 * 广告 
 * @author 
 * 
 */
@SuppressWarnings("unchecked")
@Repository("ywBannerDao")
public class YwBannerDaoImpl extends BaseDaoImpl<YwBanner> implements YwBannerDao{
	@Override
	public YwBanner setField(ResultSet rs) throws SQLException{
		YwBanner entity = new YwBanner();
		entity.setId(rs.getString("id"));
		entity.setName(rs.getString("name"));
		entity.setContent(rs.getString("content"));
		entity.setType(rs.getString("type"));
		entity.setLinkUrl(rs.getString("link_url"));
		entity.setImg(rs.getString("img"));
		entity.setBigImg(rs.getString("big_img"));
		entity.setOrderId(rs.getInt("order_id"));
		entity.setCreateTime(rs.getTimestamp("create_time"));
		entity.setRoomId(rs.getString("room_id"));
		entity.setClientType(rs.getString("client_type"));
		return entity;
	}
	
	@Override
	public YwBanner save(YwBanner entity){
		String sql = "insert into yw_banner(id, name, content, type, link_url, img, big_img, order_id, create_time, room_id, client_type) values(?,?,?,?,?,?,?,?,?,?,?)"; 
		if (StringUtils.isBlank(entity.getId())){
			entity.setId(createId());
		}
		Object[] args = new Object[]{
			entity.getId(), entity.getName(), 
            entity.getContent(), entity.getType(), 
            entity.getLinkUrl(), entity.getImg(),
            entity.getBigImg(), entity.getOrderId(),
            entity.getCreateTime(), entity.getRoomId(),
            entity.getClientType()
		};
		update(sql, args);
		return entity;
	}
	
	@Override
	public Integer delete(String[] ids){
		String sql = "delete from yw_banner where id in";
		return executeUpdateForInSQL(sql, null, ids);
	}
	
	@Override
	public Integer update(YwBanner entity){
		String sql = "update yw_banner set name = ?, content = ?, type = ?, link_url = ?, img = ?, big_img = ?, order_id = ?, room_id = ?, client_type= ? where id = ?";
		Object[] args = new Object[]{
			entity.getName(), entity.getContent(), 
            entity.getType(), entity.getLinkUrl(), 
            entity.getImg(), entity.getBigImg(),
            entity.getOrderId(), entity.getRoomId(), 
            entity.getClientType(), entity.getId()
		};
		return update(sql, args);
	}

	@Override
	public YwBanner getYwBannerById(String id){
		String sql = "select * from yw_banner where id = ?";
		return (YwBanner) findForObject(sql, new Object[] { id }, new MultiRow<YwBanner>());
	}

	@Override
	public Map<String, YwBanner> getYwBannerMapByIds(String[] ids){
		String sql = "select * from yw_banner where id in";
		return queryForMap(sql, null, ids, new MapIdRow<String, YwBanner>("id", String.class));
	}

	@Override
	public List<YwBanner> getYwBannerList(YwBanner entity){
		String sql = "select * from yw_banner where 1=1";
		List<Object> args = new ArrayList<Object>();
		if(entity != null){
			
		}
		return find(sql, args.toArray(), new MultiRow<YwBanner>());
	}

	@Override
	public List<YwBanner> getYwBannerPage(YwBanner entity, PageDto page){
		String sql = "select * from yw_banner where 1=1";
		List<Object> args = new ArrayList<Object>();
		if(entity != null){
			if(StringUtils.isNotBlank(entity.getType())){
				sql += " and type = ?";
				args.add(entity.getType());
			}
			if(StringUtils.isNotBlank(entity.getClientType())){
				sql += " and client_type = ?";
				args.add(entity.getClientType());
			}
			if(StringUtils.isNotBlank(entity.getName())){
				sql += " and name like ?";
				args.add("%" + entity.getName() + "%");
			}
		}
		sql += " order by order_id, create_time desc";
		if(page == null){
			return find(sql, args.toArray(), new MultiRow<YwBanner>());
		}else{
			return findForPage(sql, args.toArray(), new MultiRow<YwBanner>(), page);
		}
	}

	@Override
	public List<YwBanner> getYwBannerListByIds(String[] ids) {
		String sql = "select * from yw_banner where id in "+SQLUtils.toSQLInString(ids)+" order by order_id";
		return find(sql, null, new MultiRow<YwBanner>());
	}
	
}
