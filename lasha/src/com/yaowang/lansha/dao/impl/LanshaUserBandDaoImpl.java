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
import com.yaowang.lansha.dao.LanshaUserBandDao;
import com.yaowang.lansha.entity.LanshaUserBand;

/**
 * yw_user_band
 * 
 * @author
 * 
 */
@SuppressWarnings("unchecked")
@Repository("lanshaUserBandDao")
public class LanshaUserBandDaoImpl extends BaseDaoImpl<LanshaUserBand> implements LanshaUserBandDao {
	@Override
	public LanshaUserBand setField(ResultSet rs) throws SQLException {
		LanshaUserBand entity = new LanshaUserBand();
		entity.setId(rs.getString("id"));
		entity.setType(rs.getString("type"));
		entity.setOpenId(rs.getString("open_id"));
		entity.setUid(rs.getString("uid"));
		entity.setCreateTime(rs.getTimestamp("create_time"));
		return entity;
	}

	@Override
	public LanshaUserBand save(LanshaUserBand entity) {
		String sql = "insert into yw_user_band(id, type, open_id, uid, create_time) values(?,?,?,?,?)";
		if (StringUtils.isBlank(entity.getId())) {
			entity.setId(createId());
		}
		Object[] args = new Object[] { entity.getId(), entity.getType(), entity.getOpenId(), entity.getUid(), entity.getCreateTime() };
		update(sql, args);
		return entity;
	}

	@Override
	public Integer delete(String[] ids) {
		String sql = "delete from yw_user_band where id in";
		return executeUpdateForInSQL(sql, null, ids);
	}

	@Override
	public Integer update(LanshaUserBand entity) {
		String sql = "update yw_user_band set type = ?, open_id = ?, uid = ?, create_time = ? where id = ?";
		Object[] args = new Object[] { entity.getType(), entity.getOpenId(), entity.getUid(), entity.getCreateTime(), entity.getId() };
		return update(sql, args);
	}

	@Override
	public LanshaUserBand getLanshaUserBandById(String id) {
		String sql = "select * from yw_user_band where id = ?";
		return (LanshaUserBand) findForObject(sql, new Object[] { id }, new MultiRow<LanshaUserBand>());
	}

	@Override
	public Map<String, LanshaUserBand> getLanshaUserBandMapByIds(String[] ids) {
		String sql = "select * from yw_user_band where id in";
		return queryForMap(sql, null, ids, new MapIdRow<String, LanshaUserBand>("id", String.class));
	}

	@Override
	public List<LanshaUserBand> getLanshaUserBandList(LanshaUserBand entity) {
		String sql = "select * from yw_user_band where 1=1";
		List<Object> args = new ArrayList<Object>();
		if (entity != null) {

		}
		return find(sql, args.toArray(), new MultiRow<LanshaUserBand>());
	}

	@Override
	public List<LanshaUserBand> getLanshaUserBandPage(LanshaUserBand entity, PageDto page) {
		String sql = "select * from yw_user_band where 1=1";
		List<Object> args = new ArrayList<Object>();
		if (entity != null) {

		}
		return findForPage(sql, args.toArray(), new MultiRow<LanshaUserBand>(), page);
	}

	@Override
	public LanshaUserBand getUserBandByOpenId(String openId) {
		String sql = "select * from yw_user_band where open_id = ?";
		return (LanshaUserBand) findForObject(sql, new Object[] { openId }, new MultiRow<LanshaUserBand>());
	}

}
