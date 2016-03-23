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
import com.yaowang.lansha.dao.WechatAccessTokenDao;
import com.yaowang.lansha.entity.WechatAccessToken;

/**
 * 微信接口访问的Token 
 * @author 
 * 
 */
@SuppressWarnings("unchecked")
@Repository("wechatAccessTokenDao")
public class WechatAccessTokenDaoImpl extends BaseDaoImpl<WechatAccessToken> implements WechatAccessTokenDao{
	@Override
	public WechatAccessToken setField(ResultSet rs) throws SQLException{
		WechatAccessToken entity = new WechatAccessToken();
		entity.setId(rs.getString("id"));
		entity.setAppId(rs.getString("app_id"));
		entity.setTokenName(rs.getString("token_name"));
		entity.setPubDate(rs.getTimestamp("pub_date"));
		entity.setExpiresIn(rs.getInt("expires_in"));
		entity.setCreateTime(rs.getTimestamp("create_time"));
		return entity;
	}
	
	@Override
	public WechatAccessToken save(WechatAccessToken entity){
		String sql = "insert into wechat_access_token(id, app_id, token_name, pub_date, expires_in, create_time) values(?,?,?,?,?,?)"; 
		if (StringUtils.isBlank(entity.getId())){
			entity.setId(createId());
		}
		Object[] args = new Object[]{
			entity.getId(), entity.getAppId(), 
            entity.getTokenName(), entity.getPubDate(), 
            entity.getExpiresIn(), entity.getCreateTime()
		};
		update(sql, args);
		return entity;
	}
	
	@Override
	public Integer delete(String[] ids){
		String sql = "delete from wechat_access_token where id in";
		return executeUpdateForInSQL(sql, null, ids);
	}
	
	@Override
	public Integer update(WechatAccessToken entity){
		String sql = "update wechat_access_token set token_name = ?, pub_date = ?, expires_in = ?, create_time = ? where app_id = ?";
		Object[] args = new Object[]{
			entity.getTokenName(), 
            entity.getPubDate(), entity.getExpiresIn(), 
            entity.getCreateTime(),entity.getAppId()
		};
		return update(sql, args);
	}

	@Override
	public WechatAccessToken getWechatAccessTokenById(String id){
		String sql = "select * from wechat_access_token where id = ?";
		return (WechatAccessToken) findForObject(sql, new Object[] { id }, new MultiRow<WechatAccessToken>());
	}
	
	@Override
	public WechatAccessToken getWechatAccessTokenByAppId(String appId){
		String sql = "select * from wechat_access_token where app_id = ?";
		return (WechatAccessToken) findForObject(sql, new Object[] { appId }, new MultiRow<WechatAccessToken>());
	}

	@Override
	public Map<String, WechatAccessToken> getWechatAccessTokenMapByIds(String[] ids){
		String sql = "select * from wechat_access_token where id in";
		return queryForMap(sql, null, ids, new MapIdRow<String, WechatAccessToken>("id", String.class));
	}

	@Override
	public List<WechatAccessToken> getWechatAccessTokenList(WechatAccessToken entity){
		String sql = "select * from wechat_access_token where 1=1";
		List<Object> args = new ArrayList<Object>();
		if(entity != null){
			
		}
		return find(sql, args.toArray(), new MultiRow<WechatAccessToken>());
	}

	@Override
	public List<WechatAccessToken> getWechatAccessTokenPage(WechatAccessToken entity, PageDto page){
		String sql = "select * from wechat_access_token where 1=1";
		List<Object> args = new ArrayList<Object>();
		if(entity != null){
			
		}
		return findForPage(sql, args.toArray(), new MultiRow<WechatAccessToken>(), page);
	}
	
}
