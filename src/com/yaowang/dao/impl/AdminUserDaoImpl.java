package com.yaowang.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Repository;

import com.yaowang.common.dao.BaseDaoImpl;
import com.yaowang.common.dao.PageDto;
import com.yaowang.common.dao.SQLUtils;
import com.yaowang.dao.AdminUserDao;
import com.yaowang.entity.AdminUser;

/**
 * 管理员用户表 
 * @author 
 * 
 */
@SuppressWarnings("unchecked")
@Repository("adminUserDao")
public class AdminUserDaoImpl extends BaseDaoImpl<AdminUser> implements AdminUserDao{
	@Override
	public AdminUser setField(ResultSet rs) throws SQLException{
		AdminUser entity = new AdminUser();
		entity.setId(rs.getString("id"));
		entity.setUsername(rs.getString("username"));
		entity.setPassword(rs.getString("password"));
		entity.setPhoto(rs.getString("photo"));
		entity.setState(rs.getString("state"));
		entity.setCreateTime(rs.getTimestamp("create_time"));
		entity.setLoginTime(rs.getTimestamp("login_time"));
		entity.setLoginIp(rs.getString("login_ip"));
		entity.setIsSys(rs.getBoolean("is_sys"));
		entity.setEmail(rs.getString("email"));
		entity.setTelphone(rs.getString("telphone"));
		entity.setRealname(rs.getString("realname"));
		return entity;
	}
	
	@Override
	public AdminUser save(AdminUser entity){
		String sql = "insert into admin_user(id, username, password, photo, state, create_time, login_time, login_ip, is_sys, email, telphone, realname) values(?,?,?,?,?,?,?,?,?,?,?,?)"; 
		if (StringUtils.isBlank(entity.getId())){
			entity.setId(createId());
		}
		Object[] args = new Object[]{
			entity.getId(), entity.getUsername(), 
			entity.getPassword(), entity.getPhoto(), 
			entity.getState(), entity.getCreateTime(), 
			entity.getLoginTime(), entity.getLoginIp(),
			entity.getIsSys(), entity.getEmail(),
			entity.getTelphone(), entity.getRealname()
		};
		update(sql, args);
		return entity;
	}
	
	@Override
	public Integer delete(String[] ids){
		String sql = "update admin_user set state = '0' where (is_sys = '0' or is_sys is null) and id in";
		return executeUpdateForInSQL(sql, null, ids);
	}
	
	@Override
	public Integer update(AdminUser entity){
		String sql = "update admin_user set photo = ?, state = ?, email = ?, telphone = ?, login_time = ?, login_ip = ?, realname = ? where id = ?";
		Object[] args = new Object[]{
			entity.getPhoto(), entity.getState(), 
			entity.getEmail(), entity.getTelphone(),
			entity.getLoginTime(), entity.getLoginIp(),
			entity.getRealname(), entity.getId()
		};
		return update(sql, args);
	}
	
	@Override
	public Integer updatePwd(AdminUser entity) {
		String sql = "update admin_user set password = ? where id = ?";
		Object[] args = new Object[]{
			entity.getPassword(), entity.getId()
		};
		return update(sql, args);
	}

	@Override
	public AdminUser getAdminUserById(String id){
		String sql = "select * from admin_user where state <> 0 and id = ?";
		return (AdminUser) findForObject(sql, new Object[] { id }, new MultiRow<AdminUser>());
	}
	
	@Override
	public AdminUser getAdminUserByUserName(String userName) {
		String sql = "select * from admin_user where username = ? and state <> 0";
		return (AdminUser) findForObject(sql, new Object[] { userName }, new MultiRow<AdminUser>());
	}

	@Override
	public Map<String, AdminUser> getAdminUserMapByIds(String[] ids) {
		String sql = "select * from admin_user where state <> 0 ";
		if(ids==null||ids.length==0){
			return null;
		}
		sql += " and id in";
		return queryForMap(sql, null, ids, new MapIdRow<String, AdminUser>("id", String.class));
	}
	
	@Override
	public Map<String, String> getAdminUserNameMapByIds(String[] ids){
		String sql = "select id, username from admin_user where  state <> 0 and id in";
		return queryForMap(sql, null, ids, new MapIdRow<String, String>("id", String.class, "username", String.class));
	}

	@Override
	public List<AdminUser> getAdminUserList(String id){
		String sql = "select * from admin_user where state <> 0";
		if(StringUtils.isNotBlank(id)){
			sql += " and id = ?  order by create_time";
			return find(sql, new Object[]{ id }, new MultiRow<AdminUser>());
		}else{
			sql += " order by create_time";
			return find(sql, null, new MultiRow<AdminUser>());
		}
	}

	@Override
	public List<AdminUser> getAdminUserPage(PageDto page){
		String sql = "select * from admin_user where state <> 0 order by create_time";
		return findForPage(sql, null, new MultiRow<AdminUser>(), page);
	}

	@Override
	public List<AdminUser> getAdminUserList() {
		String sql = "select * from admin_user where state <> 0 and is_work = '1' order by create_time";
		return find(sql, null, new MultiRow<AdminUser>());
	}

	@Override
	public void deleteByChannelId(String[] ids) {
		String sql = "update  admin_user set state = 0 where state = 0 ";
		if(ids.length > 0){
			sql += " or id in (select user_id from b2c_user_channel where id in "+SQLUtils.toSQLInString(ids)+")";
		}
		update(sql, new Object[]{});
	}

	@Override
	public Integer updatePwd(String[] ids, String defaultPwd) {
		String sql = "update admin_user set password = ? where id in";
		Object[] args = new Object[]{ defaultPwd };
		return executeUpdateForInSQL(sql, args, ids);
	}

	@Override
	public List<AdminUser> getAdminUserListByName(AdminUser entity) {
		String sql = "select * from admin_user where state <> 0";
		if(null != entity){
			if(StringUtils.isNotBlank(entity.getUsername())){
				sql += " and username like ?  order by create_time";
				return find(sql, new Object[]{ entity.getUsername() + "%"}, new MultiRow<AdminUser>());
			}else{
				sql += " order by create_time";
				return find(sql, null, new MultiRow<AdminUser>());
			}
		}else{
			sql += " order by create_time";
			return find(sql, null, new MultiRow<AdminUser>());
		}
	}
}
