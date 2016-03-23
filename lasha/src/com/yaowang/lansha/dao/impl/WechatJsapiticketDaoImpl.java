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
import com.yaowang.lansha.dao.WechatJsapiticketDao;
import com.yaowang.lansha.entity.WechatJsapiticket;

/**
 * 微信JS接口访问的票据 
 * @author 
 * 
 */
@SuppressWarnings("unchecked")
@Repository("wechatJsapiticketDao")
public class WechatJsapiticketDaoImpl extends BaseDaoImpl<WechatJsapiticket> implements WechatJsapiticketDao{
	@Override
	public WechatJsapiticket setField(ResultSet rs) throws SQLException{
		WechatJsapiticket entity = new WechatJsapiticket();
		entity.setId(rs.getString("id"));
		entity.setAppId(rs.getString("app_id"));
		entity.setTicketName(rs.getString("ticket_name"));
		entity.setPubDate(rs.getTimestamp("pub_date"));
		entity.setExpiresIn(rs.getInt("expires_in"));
		entity.setCreateTime(rs.getTimestamp("create_time"));
		return entity;
	}
	
	@Override
	public WechatJsapiticket save(WechatJsapiticket entity){
		String sql = "insert into wechat_jsapiticket(id, app_id, ticket_name, pub_date, expires_in, create_time) values(?,?,?,?,?,?)"; 
		if (StringUtils.isBlank(entity.getId())){
			entity.setId(createId());
		}
		Object[] args = new Object[]{
			entity.getId(), entity.getAppId(), 
            entity.getTicketName(), entity.getPubDate(), 
            entity.getExpiresIn(), entity.getCreateTime()
		};
		update(sql, args);
		return entity;
	}
	
	@Override
	public Integer delete(String[] ids){
		String sql = "delete from wechat_jsapiticket where id in";
		return executeUpdateForInSQL(sql, null, ids);
	}
	
	@Override
	public Integer update(WechatJsapiticket entity){
		String sql = "update wechat_jsapiticket set ticket_name = ?, pub_date = ?, expires_in = ?, create_time = ? where app_id = ?";
		Object[] args = new Object[]{
			entity.getTicketName(), 
            entity.getPubDate(), entity.getExpiresIn(), 
            entity.getCreateTime(), entity.getAppId()
		};
		return update(sql, args);
	}

	@Override
	public WechatJsapiticket getWechatJsapiticketById(String id){
		String sql = "select * from wechat_jsapiticket where id = ?";
		return (WechatJsapiticket) findForObject(sql, new Object[] { id }, new MultiRow<WechatJsapiticket>());
	}
	
	@Override
	public WechatJsapiticket getWechatJsapiticketByAppId(String appId){
		String sql = "select * from wechat_jsapiticket where app_id = ?";
		return (WechatJsapiticket) findForObject(sql, new Object[] { appId }, new MultiRow<WechatJsapiticket>());
	}

	@Override
	public Map<String, WechatJsapiticket> getWechatJsapiticketMapByIds(String[] ids){
		String sql = "select * from wechat_jsapiticket where id in";
		return queryForMap(sql, null, ids, new MapIdRow<String, WechatJsapiticket>("id", String.class));
	}

	@Override
	public List<WechatJsapiticket> getWechatJsapiticketList(WechatJsapiticket entity){
		String sql = "select * from wechat_jsapiticket where 1=1";
		List<Object> args = new ArrayList<Object>();
		if(entity != null){
			
		}
		return find(sql, args.toArray(), new MultiRow<WechatJsapiticket>());
	}

	@Override
	public List<WechatJsapiticket> getWechatJsapiticketPage(WechatJsapiticket entity, PageDto page){
		String sql = "select * from wechat_jsapiticket where 1=1";
		List<Object> args = new ArrayList<Object>();
		if(entity != null){
			
		}
		return findForPage(sql, args.toArray(), new MultiRow<WechatJsapiticket>(), page);
	}
	
}
