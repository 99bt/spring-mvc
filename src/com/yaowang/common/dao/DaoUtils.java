package com.yaowang.common.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

public class DaoUtils {

	public static Object setResult(ResultSet rs, Class<?> type, String id)
			throws SQLException {
		if (type.equals(String.class)) {
			return rs.getString(id);
		} else if (type.equals(Integer.class)) {
			return rs.getInt(id);
		} else if (type.equals(Date.class)) {
			return rs.getTimestamp(id);
		} else if (type.equals(Float.class)) {
			return rs.getFloat(id);
		} else if (type.equals(Double.class)) {
			return rs.getDouble(id);
		} else {
			return rs.getObject(id);
		}
	}
}
