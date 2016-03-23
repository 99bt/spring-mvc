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
import com.yaowang.lansha.dao.LanshaSuggestionDao;
import com.yaowang.lansha.entity.LanshaSuggestion;
import com.yaowang.util.DateUtils;

/**
 * 意见反馈
 * 
 * @author
 * 
 */
@SuppressWarnings("unchecked")
@Repository("lanshaSuggestionDao")
public class LanshaSuggestionDaoImpl extends BaseDaoImpl<LanshaSuggestion> implements LanshaSuggestionDao {
	@Override
	public LanshaSuggestion setField(ResultSet rs) throws SQLException {
		LanshaSuggestion entity = new LanshaSuggestion();
		entity.setId(rs.getString("id"));
		entity.setUserId(rs.getString("user_id"));
		entity.setIp(rs.getString("ip"));
		entity.setTitle(rs.getString("title"));
		entity.setContent(rs.getString("content"));
		entity.setType(rs.getString("type"));
		entity.setCreateTime(rs.getTimestamp("create_time"));
		return entity;
	}

	@Override
	public LanshaSuggestion save(LanshaSuggestion entity) {
		String sql = "insert into lansha_suggestion(id, user_id, ip, title, content, type, create_time) values(?,?,?,?,?,?,?)";
		if (StringUtils.isBlank(entity.getId())) {
			entity.setId(createId());
		}
		Object[] args = new Object[] { entity.getId(), entity.getUserId(), entity.getIp(), entity.getTitle(), entity.getContent(), entity.getType(), entity.getCreateTime() };
		update(sql, args);
		return entity;
	}

	@Override
	public Integer delete(String[] ids) {
		String sql = "delete from lansha_suggestion where id in";
		return executeUpdateForInSQL(sql, null, ids);
	}

	@Override
	public Integer update(LanshaSuggestion entity) {
		String sql = "update lansha_suggestion set user_id = ?, ip = ?, content = ?, create_time = ? where id = ?";
		Object[] args = new Object[] { entity.getUserId(), entity.getIp(), entity.getContent(), entity.getCreateTime(), entity.getId() };
		return update(sql, args);
	}

	@Override
	public LanshaSuggestion getMgameSuggestionById(String id) {
		String sql = "select * from lansha_suggestion where id = ?";
		return (LanshaSuggestion) findForObject(sql, new Object[] { id }, new MultiRow<LanshaSuggestion>());
	}

	@Override
	public Map<String, LanshaSuggestion> getMgameSuggestionMapByIds(String[] ids) {
		String sql = "select * from lansha_suggestion where id in";
		return queryForMap(sql, null, ids, new MapIdRow<String, LanshaSuggestion>("id", String.class));
	}

	@Override
	public List<LanshaSuggestion> getMgameSuggestionList(LanshaSuggestion entity) {
		String sql = "select * from lansha_suggestion where 1=1";
		List<Object> args = new ArrayList<Object>();
		if (entity != null) {

		}
		return find(sql, args.toArray(), new MultiRow<LanshaSuggestion>());
	}

	@Override
	public List<LanshaSuggestion> getMgameSuggestionPage(LanshaSuggestion entity, String name, PageDto page, Date startTime, Date endTime) {
		StringBuilder sql = new StringBuilder("select * from lansha_suggestion where 1=1");
		List<Object> args = new ArrayList<Object>();
		if (entity != null) {
			if (StringUtils.isNotBlank(entity.getTitle())) {
				sql.append(" and title like '%" + entity.getTitle() + "%'");
			}
			if (StringUtils.isNotBlank(entity.getType())) {
				sql.append(" and type = ?");
				args.add(entity.getType());
			}
		}
		if (StringUtils.isNotBlank(name)) {
			sql.append(" and content like '%" + name + "%'");
		}
		if (startTime != null) {
			sql.append(" and create_time > ?");
			args.add(DateUtils.getStartDate(startTime));
		}
		if (endTime != null) {
			sql.append(" and create_time < ?");
			args.add(DateUtils.getEndDate(endTime));
		}

		sql.append(" order by create_time desc");
		if (page == null) {
			return find(sql.toString(), args.toArray(), new MultiRow<LanshaSuggestion>());
		} else {
			return findForPage(sql.toString(), args.toArray(), new MultiRow<LanshaSuggestion>(), page);
		}
	}

}
