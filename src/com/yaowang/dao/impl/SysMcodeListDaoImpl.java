package com.yaowang.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Repository;

import com.yaowang.common.dao.BaseDaoImpl;
import com.yaowang.common.dao.PageDto;
import com.yaowang.dao.SysMcodeListDao;
import com.yaowang.entity.SysMcodeList;

/**
 * sys_mcode_list 
 * @author 
 * 
 */
@Repository("sysMcodeListDao")
public class SysMcodeListDaoImpl extends BaseDaoImpl<SysMcodeList> implements SysMcodeListDao{
	@Override
	public SysMcodeList setField(ResultSet rs) throws SQLException{
		SysMcodeList entity = new SysMcodeList();
		entity.setId(rs.getString("id"));
		entity.setName(rs.getString("name"));
		entity.setLength(rs.getInt("length"));
		entity.setType(rs.getString("type"));
		entity.setRemark(rs.getString("remark"));
		entity.setIsUsing(rs.getBoolean("is_using"));
		entity.setThisId(rs.getString("this_id"));
		return entity;
	}
	
	@Override
	public SysMcodeList save(SysMcodeList entity){
		String sql = "insert into sys_mcode_list(id, name, length, type, remark, is_using, this_id) values(?,?,?,?,?,?,?)"; 
		if (StringUtils.isBlank(entity.getId())){
			entity.setId(createId());
		}
		Object[] args = new Object[]{
			entity.getId(), entity.getName(), 
			entity.getLength(), entity.getType(), 
			entity.getRemark(), entity.getIsUsing(),
			entity.getThisId()
		};
		update(sql, args);
		return entity;
	}
	
	@Override
	public Integer delete(String[] ids){
		String sql = "delete from sys_mcode_list where id in";
		return executeUpdateForInSQL(sql, null, ids);
	}
	
	@Override
	public Integer update(SysMcodeList entity){
		String sql = "update sys_mcode_list set name = ?, length = ?, type = ?, remark = ?, is_using = ?, this_id = ? where id = ?";
		Object[] args = new Object[]{
			entity.getName(), entity.getLength(), 
			entity.getType(), entity.getRemark(), 
			entity.getIsUsing(), entity.getThisId(),
			entity.getId()
		};
		return update(sql, args);
	}

	@Override
	public SysMcodeList getSysMcodeListById(String id){
		String sql = "select * from sys_mcode_list where id = ?";
		return (SysMcodeList) findForObject(sql, new Object[] { id }, new MultiRow<SysMcodeList>());
	}
	@Override
	public SysMcodeList getSysMcodeListByName(String name){
		String sql = "select * from sys_mcode_list where name = ?";
		return (SysMcodeList) findForObject(sql, new Object[] { name }, new MultiRow<SysMcodeList>());
	}

//	@Override
//	public Map<String, SysMcodeList> getSysMcodeListMapByIds(String[] ids){
//		String sql = "select * from sys_mcode_list where id in";
//		return findForInSQL(sql, null, ids, new MapRow());
//	}

	@Override
	public List<SysMcodeList> getSysMcodeListList(){
		String sql = "select * from sys_mcode_list";
		return find(sql, null, new MultiRow<SysMcodeList>());
	}

	@Override
	public List<SysMcodeList> getSysMcodeListPage(PageDto page){
		String sql = "select * from sys_mcode_list";
		return findForPage(sql, null, new MultiRow<SysMcodeList>(), page);
	}
	
}
