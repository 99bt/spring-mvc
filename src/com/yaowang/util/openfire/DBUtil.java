package com.yaowang.util.openfire;

import javax.sql.DataSource;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;


@Repository("dbUtil")
public class DBUtil extends JdbcTemplate{
	
	public Integer updateSql(String sql, Object[] args){
		return update(sql, args);
	}
	
	public void setDataSourceOpenfire(DataSource dataSource) {
		super.setDataSource(dataSource);
	}
}
