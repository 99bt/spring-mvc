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
import com.yaowang.lansha.dao.YwUserApplyDao;
import com.yaowang.lansha.entity.YwUserApply;

/**
 * 热门推荐房间 
 * @author 
 * 
 */
@SuppressWarnings("unchecked")
@Repository("ywUserApplyDao")
public class YwUserApplyDaoImpl extends BaseDaoImpl<YwUserApply> implements YwUserApplyDao{
	@Override
	public YwUserApply setField(ResultSet rs) throws SQLException{
		YwUserApply entity = new YwUserApply();
		entity.setId(rs.getString("id"));
		entity.setUserId(rs.getString("user_id"));
		entity.setRealname(rs.getString("realname"));
		entity.setIdentitycard(rs.getString("identitycard"));
		entity.setPic1(rs.getString("pic1"));
		entity.setPic2(rs.getString("pic2"));
		entity.setPic3(rs.getString("pic3"));
		entity.setExpirationTime(rs.getTimestamp("expiration_time"));
		entity.setStatus(rs.getString("status"));
		entity.setCreateTime(rs.getTimestamp("create_time"));
		entity.setRemark(rs.getString("remark"));
		entity.setAduitUid(rs.getString("aduit_uid"));
		entity.setAduitTime(rs.getTimestamp("aduit_time"));
		return entity;
	}
	
	@Override
	public YwUserApply save(YwUserApply entity){
		String sql = "insert into yw_user_apply(id, user_id, realname, identitycard, pic1, pic2, pic3, expiration_time, status, create_time, remark, aduit_uid, aduit_time) values(?,?,?,?,?,?,?,?,?,?,?,?,?)"; 
		if (StringUtils.isBlank(entity.getId())){
			entity.setId(createId());
		}
		Object[] args = new Object[]{
			entity.getId(), entity.getUserId(), 
            entity.getRealname(), entity.getIdentitycard(), 
            entity.getPic1(), entity.getPic2(), 
            entity.getPic3(), entity.getExpirationTime(), 
            entity.getStatus(), entity.getCreateTime(), 
            entity.getRemark(), entity.getAduitUid(), 
            entity.getAduitTime()
		};
		update(sql, args);
		return entity;
	}
	
	@Override
	public Integer delete(String[] ids){
		String sql = "delete from yw_user_apply where id in";
		return executeUpdateForInSQL(sql, null, ids);
	}
	
	@Override
	public Integer update(YwUserApply entity){
		String sql = "update yw_user_apply set user_id = ?, realname = ?, identitycard = ?, pic1 = ?, pic2 = ?, pic3 = ?, expiration_time = ?, status = ?, create_time = ?, remark = ?, aduit_uid = ?, aduit_time = ? where id = ?";
		Object[] args = new Object[]{
			entity.getUserId(), entity.getRealname(), 
            entity.getIdentitycard(), entity.getPic1(), 
            entity.getPic2(), entity.getPic3(), 
            entity.getExpirationTime(), entity.getStatus(), 
            entity.getCreateTime(), entity.getRemark(), 
            entity.getAduitUid(), entity.getAduitTime(), 
            entity.getId()
		};
		return update(sql, args);
	}

	@Override
	public YwUserApply getYwUserApplyById(String id){
		String sql = "select * from yw_user_apply where id = ?";
		return (YwUserApply) findForObject(sql, new Object[] { id }, new MultiRow<YwUserApply>());
	}

	@Override
	public Map<String, YwUserApply> getYwUserApplyMapByIds(String[] ids){
		String sql = "select * from yw_user_apply where id in";
		return queryForMap(sql, null, ids, new MapIdRow<String, YwUserApply>("id", String.class));
	}

	@Override
	public List<YwUserApply> getYwUserApplyList(YwUserApply entity){
		String sql = "select * from yw_user_apply where 1=1";
		List<Object> args = new ArrayList<Object>();
		if(entity != null){
			
		}
		return find(sql, args.toArray(), new MultiRow<YwUserApply>());
	}

	@Override
	public List<YwUserApply> getYwUserApplyPage(YwUserApply entity, PageDto page){
		String sql = "select * from yw_user_apply where 1=1";
		List<Object> args = new ArrayList<Object>();
		if(entity != null){
			
		}
		return findForPage(sql, args.toArray(), new MultiRow<YwUserApply>(), page);
	}
	
}
