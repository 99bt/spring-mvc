package com.yaowang.common.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.SqlParameterValue;
import org.springframework.jdbc.core.StatementCreatorUtils;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.JdbcUtils;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import com.yaowang.util.UUIDUtils;

@SuppressWarnings({ "unchecked", "rawtypes" })
@Repository("baseDao")
public class BaseDaoImpl<T> extends JdbcTemplate {
	/**
	 * 组装实体类
	 * @param rs
	 * @return
	 * @throws SQLException
	 */
	public T setField(ResultSet rs) throws SQLException{
		return null;
	}
	
	/**
	 * 组装实体类关联表查询
	 * @param rs
	 * @return
	 * @throws SQLException
	 */
	public T setFields(ResultSet rs) throws SQLException{
		return null;
	}
	/**
     * 返回主键id
     * 
     * @param sql
     * @param args
     * @return
     */
    public Number saveEntityForKey(final String sql, final Object[] args) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        update(new PreparedStatementCreator() {
            @Override
            public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
                PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
                if (args != null) {
                    for (int i = 0; i < args.length; ++i) {
                        Object argValue = args[i];
                        if (argValue instanceof SqlParameterValue) {
                            SqlParameterValue paramValue = (SqlParameterValue) argValue;
                            StatementCreatorUtils.setParameterValue(ps, i + 1, paramValue, paramValue.getValue());
                        } else {
                            StatementCreatorUtils.setParameterValue(ps, i + 1, JdbcUtils.TYPE_UNKNOWN, argValue);
                        }
                    }
                }
                return ps;
            }
        }, keyHolder);
        return keyHolder.getKey();
    }
	/**
	 * 分页
	 * @param sql
	 * @param args
	 * @param page
	 * @param size
	 * @return
	 */
	public List<T> findForPage(String sql, Object[] args, RowMapper multiRow, PageDto page){
		if(page.isCount()){
			//总数
			String countSql = "select count(1) from (" + sql + ") as limit_temp_table";
			int totalNum = queryForInt(countSql,args);
			page.setTotalNum(totalNum);
			if (totalNum <= 0) {
				return new ArrayList<T>();
			}
		}
		// 起始位置
		int firstResult = (page.getCurrentPage() - 1) * page.getRowNum();
		if (page.getPageType() == 1) {
			sql = "select * from (" + sql + ") as limit_temp_table LIMIT " + firstResult +  "," + page.getRowNum();
		}else {
			sql = sql + " LIMIT " + firstResult +  "," + page.getRowNum();
		}
		return query(sql, args, multiRow);
	}
	/**
	 * 分页
	 * @param sql
	 * @param args
	 * @param page
	 * @param size
	 * @return
	 */
	public List<String> findStringForPage(String sql, Object[] args, RowMapper multiRow, PageDto page){
		if(page.isCount()){
			//总数
			String countSql = "select count(1) from (" + sql + ") as limit_temp_table";
			int totalNum = queryForInt(countSql,args);
			page.setTotalNum(totalNum);
			if (totalNum <= 0) {
				return new ArrayList<String>();
			}
		}
		// 起始位置
		int firstResult = (page.getCurrentPage() - 1) * page.getRowNum();
		if (page.getPageType() == 1) {
			sql = "select * from (" + sql + ") as limit_temp_table LIMIT " + firstResult +  "," + page.getRowNum();
		}else {
			sql = sql + " LIMIT " + firstResult +  "," + page.getRowNum();
		}
		return query(sql, args, multiRow);
	}
	
	/**
	 * 分页
	 * @param sql
	 * @param args
	 * @param page
	 * @param size
	 * @return
	 */
	public List<Map<String, Object>> findListMapForPage(String sql, Object[] args, PageDto page){
		if(page.isCount()){
			//总数
			String countSql = "select count(1) from (" + sql + ") as limit_temp_table";
			int totalNum = queryForInt(countSql,args);
			page.setTotalNum(totalNum);
			if (totalNum <= 0) {
				return new ArrayList<Map<String, Object>>();
			}
		}
		// 起始位置
		int firstResult = (page.getCurrentPage() - 1) * page.getRowNum();
		sql = "select * from (" + sql + ") as limit_temp_table LIMIT " + firstResult +  "," + page.getRowNum();
		return queryForList(sql, args);
	}
	
	/**
	 * 查询列表
	 * @param sql
	 * @param args
	 * @param multiRow
	 * @return
	 */
	public List<T> find(String sql, Object[] args, RowMapper<T> multiRow){
		return query(sql, args, multiRow);
	}
	
	/**
	 * 获取map
	 * @param sql
	 * @param args
	 * @param inArgs
	 * @param rowMapper
	 * @return
	 * @throws SQLException
	 */
	public Map queryForMap(String sql, Object[] args, Object[] inArgs, MapIdRow rowMapper){
		findForInSQL(sql, args, inArgs, rowMapper);
		return rowMapper.getMapData();
	}
	
	/**
	 * 获取map
	 * @param sql
	 * @param args
	 * @param inArgs
	 * @param rowMapper
	 * @return
	 * @throws SQLException
	 */
	public Map queryForMap(String sql, Object[] args, Object[] inArgs, MapRowMapper rowMapper){
		findForInSQL(sql, args, inArgs, rowMapper);
		return rowMapper.getMapData();
	}
	
	public List<T> findForInSQL(String sql, Object[] args, Object[] inArgs, RowMapper rowMapper){
		Object[] o = getInSQLObj(args, inArgs);
		
		if (StringUtils.isNotBlank((String)o[0])) {
			return query(sql + o[0], (Object[])o[1], rowMapper);
		}else {
			return query(sql, (Object[])o[1], rowMapper);
		}
	}
	
	public List<?> queryForInSQL(String sql, Object[] args, Object[] inArgs, RowMapper rowMapper){
		Object[] o = getInSQLObj(args, inArgs);
		
		if (StringUtils.isNotBlank((String)o[0])) {
			return query(sql + o[0], (Object[])o[1], rowMapper);
		}else {
			return query(sql, (Object[])o[1], rowMapper);
		}
	}
	/**
	 * 更新
	 * @param sql
	 * @param args
	 * @param inArgs
	 * @return
	 */
	public Integer executeUpdateForInSQL(String sql, Object[] args, Object[] inArgs){
		Object[] o = getInSQLObj(args, inArgs);
		if (StringUtils.isNotBlank((String)o[0])) {
			return update(sql + o[0], (Object[])o[1]);
		}else {
			return update(sql, (Object[])o[1]);
		}
	}
	
	public Object[] getInSQLObj(Object[] args, Object[] inArgs){
		StringBuffer sqlBuffer = new StringBuffer();
		if (inArgs !=null && inArgs.length > 0) {
			sqlBuffer.append("(");
			for (int i = 0; i < inArgs.length; i++) {
				sqlBuffer.append("?,");
			}
			sqlBuffer.setLength(sqlBuffer.length() - 1);
			sqlBuffer.append(")");
			// 数组合并
			if (args != null) {
				Object[] allArgs = new Object[args.length + inArgs.length];
				System.arraycopy(args, 0, allArgs, 0, args.length);
				System.arraycopy(inArgs, 0, allArgs, args.length, inArgs.length);
				args = allArgs;
			}else {
				args = inArgs;
			}
		} else if (inArgs != null && inArgs.length <= 0) {
			sqlBuffer.append("('')");
		}
		return new Object[]{sqlBuffer.toString(), args};
	}
	
	/**
	 * 获取单个数据
	 * @param sql
	 * @param args
	 * @param multiRow
	 * @return
	 */
	public T findForObject(String sql, Object[] args, MultiRow<T> multiRow){
		List<T> list = query(sql, args, multiRow);
		if (list.isEmpty()) {
			return null;
		}else {
			return list.get(0);
		}
	}
	
	/**
	 * 获取单个数据关联表
	 * @param sql
	 * @param args
	 * @param multiRow
	 * @return
	 */
	public T findForObject(String sql, Object[] args, MultiRows<T> multiRows){
		List<T> list = query(sql, args, multiRows);
		if (list.isEmpty()) {
			return null;
		}else {
			return list.get(0);
		}
	}

	/**
	 * Map工具
	 * @author shenl
	 *
	 * @param <K>
	 * @param <V>
	 */
	public class MapIdRow<K, V> implements RowMapper<Map<K, V>>{
		private String id = null;
		private Class<?> idType = null;
		private String value = null;
		private Class<?> valueType = null;
		
		public MapIdRow(){
		}
		
		public MapIdRow(String id, Class<?> idType){
			this.id = id;
			this.idType = idType;
		}
		
		public MapIdRow(String id, Class<?> idType, String value, Class<?> valueType){
			this.id = id;
			this.value = value;
			this.idType = idType;
			this.valueType = valueType;
		}
		
		public K mapRowKey(ResultSet rs) throws SQLException {
			return (K)DaoUtils.setResult(rs, idType, id);
		}

		public V mapRowValue(ResultSet rs) throws SQLException {
			if (value == null) {
				return (V)setField(rs);
			}else {
				return (V)DaoUtils.setResult(rs, valueType, value);
			}
		}

		private Map<K, V> mapData = new HashMap<K, V>();
		
		@Override
		public Map<K, V> mapRow(ResultSet rs, int arg1) throws SQLException {
			mapData.put(mapRowKey(rs), mapRowValue(rs));
			return null;
		}
		
		public Map<K, V> getMapData(){
			return mapData;
		}
	}
	/**
	 * Map工具
	 * @author shenl
	 *
	 * @param <K>
	 * @param <V>
	 */
	public abstract class MapRowMapper<K, V> implements RowMapper<Map<K, V>>{
		
		public MapRowMapper(){
			
		}
		
		public MapRowMapper(Class<?> idType, Class<?> objectType){
			
		}
		
		public abstract K mapRowKey(ResultSet rs) throws SQLException;

		public abstract V mapRowValue(ResultSet rs) throws SQLException;

		private Map<K, V> mapData = new HashMap<K, V>();
		
		@Override
		public Map<K, V> mapRow(ResultSet rs, int arg1) throws SQLException {
			mapData.put(mapRowKey(rs), mapRowValue(rs));
			return null;
		}
		
		public Map<K, V> getMapData(){
			return mapData;
		}
	}

	/**
	 * list工具
	 * @author shenl
	 *
	 * @param <T>
	 */
	public class MultiRow<V> implements RowMapper<V>{
		private String id = null;
		private Class<?> type = null;
		
		public MultiRow(){
		}

		public MultiRow(String id, Class<?> type){
			this.id = id;
			this.type = type;
		}
		
		@Override
		public V mapRow(ResultSet rs, int n) throws SQLException {
			if (id == null) {
				return (V)setField(rs);
			}else {
				return (V)DaoUtils.setResult(rs, type, id);
			}
		}

	}
	
	/**
	 * list工具关联表查询
	 * @author shenl
	 *
	 * @param <T>
	 */
	public class MultiRows<V> implements RowMapper<V>{
		private String id = null;
		private Class<?> type = null;
		
		public MultiRows(){
		}

		public MultiRows(String id, Class<?> type){
			this.id = id;
			this.type = type;
		}
		
		@Override
		public V mapRow(ResultSet rs, int n) throws SQLException {
			if (id == null) {
				return (V)setFields(rs);
			}else {
				return (V)DaoUtils.setResult(rs, type, id);
			}
		}

	}
	
	public String createId(){
		return UUIDUtils.newId();
	}
	
	public String getEmptySql(String column){
		return "(" + column + " is null or length(" + column + ") = 0)";
	}
	public String getNotEmptySql(String column){
		return "(" + column + " is not null or length(" + column + ") > 0)";
	}
	
	/**
     * 当前时间
     */
    private Date now = null;
    public Date getNow(){
//        if (now == null) {
            now = new Date();
//        }
        return now;
    }
    /**
     * 设置编码问题
     * @param value
     */
    protected void setUtf8mb4(String...value){
    	if (ArrayUtils.isNotEmpty(value)) {
			for (String v : value) {
				if (StringUtils.isNotBlank(v) && v.matches(".*\\\\x..+")) {
					//字符集问题
					update("set names utf8mb4;");
					return;
				}
			}
		}
    }
	
	/**
	 * 单个数据
	 * @author shenl
	 *
	 */
//	public class SingleIdRow<V> implements SingleRowMapper<V>{
//		private String id = null;
//		private Class<?> type = null;
//		
//		public SingleIdRow(){
//		}
//		
//		public SingleIdRow(String id, Class<?> type){
//			this.id = id;
//			this.type = type;
//		}
//		
//		@Override
//		public V mapRow(ResultSet rs) throws SQLException {
//			if (id == null) {
//				return (V)setField(rs);
//			}else {
//				return (V)DaoUtils.setResult(rs, type, id);
//			}
//		}
//	}
}
