package com.yaowang.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Repository;

import com.yaowang.common.dao.BaseDaoImpl;
import com.yaowang.common.dao.PageDto;
import com.yaowang.dao.SysMcodeDetailDao;
import com.yaowang.entity.SysMcodeDetail;

/**
 * sys_mcode_detail 
 * @author 
 * 
 */
@Repository("sysMcodeDetailDao")
public class SysMcodeDetailDaoImpl extends BaseDaoImpl<SysMcodeDetail> implements SysMcodeDetailDao{
	@Override
	public SysMcodeDetail setField(ResultSet rs) throws SQLException{
		SysMcodeDetail entity = new SysMcodeDetail();
		entity.setId(rs.getString("id"));
		entity.setListId(rs.getString("list_id"));
		entity.setThisId(rs.getString("this_id"));
		entity.setContent(rs.getString("content"));
		entity.setIsUsing(rs.getBoolean("is_using"));
		entity.setMcodeType(rs.getString("mcode_type"));
		entity.setDisplayOrder(rs.getString("display_order"));
		return entity;
	}
	
	@Override
	public SysMcodeDetail save(SysMcodeDetail entity){
		String sql = "insert into sys_mcode_detail(id, list_id, this_id, content, is_using, mcode_type, display_order) values(?,?,?,?,?,?,?)"; 
		if (StringUtils.isBlank(entity.getId())){
			entity.setId(createId());
		}
		Object[] args = new Object[]{
			entity.getId(), entity.getListId(), 
			entity.getThisId(), entity.getContent(), 
			entity.getIsUsing(), entity.getMcodeType(), 
			entity.getDisplayOrder()
		};
		update(sql, args);
		return entity;
	}
	
	@Override
	public Integer delete(String[] ids){
		String sql = "delete from sys_mcode_detail where id in";
		return executeUpdateForInSQL(sql, null, ids);
	}
	
	@Override
	public Integer update(SysMcodeDetail entity){
		String sql = "update sys_mcode_detail set content = ?, is_using = ?, display_order = ? where id = ?";
		Object[] args = new Object[]{
			entity.getContent(), entity.getIsUsing(), 
			entity.getDisplayOrder(), entity.getId()
		};
		return update(sql, args);
	}
	
	@Override
	public SysMcodeDetail getSysMcodeDetail(String listId, String thisId) {
		String sql = "select * from sys_mcode_detail where list_id = ? and this_id = ?";
		return (SysMcodeDetail) findForObject(sql, new Object[] { listId, thisId }, new MultiRow<SysMcodeDetail>());
	}

	@Override
	public SysMcodeDetail getSysMcodeDetailById(String id){
		String sql = "select * from sys_mcode_detail where id = ?";
		return (SysMcodeDetail) findForObject(sql, new Object[] { id }, new MultiRow<SysMcodeDetail>());
	}

//	@Override
//	public Map<String, SysMcodeDetail> getSysMcodeDetailMapByIds(String[] ids){
//		String sql = "select * from sys_mcode_detail where id in";
//		return findForInSQL(sql, null, ids, new MapRow());
//	}

	@Override
	public List<SysMcodeDetail> getSysMcodeDetailList(String listId) {
		String sql = "select * from sys_mcode_detail where list_id = ? and is_using = 1 order by display_order";
		return find(sql, new Object[]{listId }, new MultiRow<SysMcodeDetail>());
	}
	
	@Override
	public List<SysMcodeDetail> getSysMcodeDetailList(String listId, SysMcodeDetail detail){
		String sql = "select * from sys_mcode_detail where list_id = ?";
		List<Object> args = new ArrayList<Object>();
		args.add(listId);
		if (detail != null) {
			if (detail.getIsUsing() != null) {
				sql += " and is_using = ?";
				args.add(detail.getIsUsing());
			}
		}
		sql += " order by display_order";
		return find(sql, args.toArray(), new MultiRow<SysMcodeDetail>());
	}

	@Override
	public List<SysMcodeDetail> getSysMcodeDetailPage(String listId, PageDto page){
		String sql = "select * from sys_mcode_detail where list_id = ? order by display_order";
		return findForPage(sql, new Object[]{listId }, new MultiRow<SysMcodeDetail>(), page);
	}

	@Override
	public Map<String, SysMcodeDetail> getSysMcodeDetailMap(String listId) {
		String sql = "select * from sys_mcode_detail where list_id = ? and is_using = 1";
		return queryForMap(sql, new Object[]{listId }, null, new MapIdRow<String, SysMcodeDetail>("this_id", String.class));
	}
	
	@Override
	public Map<String, String> getMcodeContentToThisId(String listId) {
		String sql = "select content, this_id from sys_mcode_detail where list_id = ? and is_using = 1";
		return queryForMap(sql, new Object[]{listId }, null, new MapIdRow<String, String>("content", String.class, "this_id", String.class));
	}
	
	@Override
	public Map<String, String> getMcodeThisIdToContent(String listId) {
		String sql = "select this_id, content from sys_mcode_detail where list_id = ? and is_using = 1";
		return queryForMap(sql, new Object[]{listId }, null, new MapIdRow<String, String>("this_id", String.class, "content", String.class));
	}

	@Override
	public SysMcodeDetail getSysMcodeDetailByContent(String listId,
			String content) {
		String sql = "select * from sys_mcode_detail where list_id = ? and content = ?";
		return (SysMcodeDetail) findForObject(sql, new Object[] { listId, content }, new MultiRow<SysMcodeDetail>());
	}
}
