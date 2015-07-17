package com.cherry.spring.plugin.db;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Arrays;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class JDBCUtils {
	private static Logger logger = LoggerFactory.getLogger(JDBCUtils.class);

	private JDBCUtils() {

	}
	/**
	 * 根据driver-url获得mysql的schema，该方法仅限于MySQL数据库
	 * @param driverUrl 数据库路径
	 * @return MySQL的Schema
	 */
	public static String getMySQLSchema(String driverUrl) {
		int index = driverUrl.indexOf("?");
		String schema = null;
		if (index == -1) {
			schema = driverUrl.substring(driverUrl.lastIndexOf("/") + 1,driverUrl.length());
		}
		// url 后面带有参数
		else {
			schema = driverUrl.substring(driverUrl.lastIndexOf("/") + 1, index);
		}
		return schema;
	}
	/**
	 * 输出带参数的sql语句
	 * @param sql 带参数的sql语句
	 * @param params 参数数组
	 */
	public static void printSQL(String sql, Object... params) {
		/*StringBuilder info = new StringBuilder("\n*** SQL: ");
		info.append(sql);
		info.append(", Parameters: ");
		info.append(params != null && params.length > 0 ? Arrays.deepToString(params) : "");
		if( logger.isInfoEnabled() ){
			logger.info(info.toString());
		}*/
	}

	/**
	 * 填充SQL语句
	 * 
	 * @param sql
	 * @param params
	 * @return 填充后的SQL语句
	 */
	public static String fillSQL(String sql, Object... params) {
		if (params != null && params.length > 0) {
			int len = sql.length();
			int i = 0;
			int limit = 0;
			int base = 0;
			Object val = null;

			StringBuffer t = new StringBuffer();

			while ((limit = sql.indexOf('?', limit)) != -1) {
				val = params[i];
				if (val instanceof String) {
					t.append(sql.substring(base, limit));
					t.append("'").append(val).append("'");
				} else {
					t.append(sql.substring(base, limit));
					t.append(val);
				}

				i++;
				limit++;
				base = limit;
				val = null;
			}

			if (base < len) {
				t.append(sql.substring(base));
			}
			return t.toString();
		}
		return sql;
	}

	/**
	 * 拼接存储过程的调用SQL
	 * 
	 * @param procedureName
	 *            存储过程名称
	 * @param sqlTypes
	 *            out参数值
	 * @param paramValues
	 *            in参数值
	 * @return 最终调用存储过程的SQL
	 */
	public static String joinCallSQL(String procedureName, int[] sqlTypes,Object... paramValues) {
		StringBuilder call = new StringBuilder();
		call.append("{ ");
		if (sqlTypes != null && sqlTypes.length == 0) {
			call.append(" ? = ");
		}
		call.append(" call ").append(procedureName).append("(");
		int pc = (sqlTypes == null ? 0 : sqlTypes.length)
				+ (paramValues == null ? 0 : paramValues.length);
		for (int i = 0; i < pc; i++) {
			call.append("?");
			if (i < pc - 1) {
				call.append(",");
			}
		}
		call.append(")}");

		return call.toString();
	}

	/**
	 * 处理返回字段类型为BigInteger和BigDecimal的列的值
	 * 
	 * @param obj
	 *            原始值
	 * @param rs
	 *            当前ResultSet
	 * @param columIndex
	 *            当前列索引
	 * @return 处理后的值
	 * @throws SQLException
	 */
	public static Object processBigDecimal(Object obj, ResultSet rs,
			int columIndex) throws SQLException {
		if (obj instanceof BigInteger) {
			obj = ((BigInteger) obj).longValue();
		} else if (obj instanceof BigDecimal) {
			ResultSetMetaData rsmd = rs.getMetaData();
			int scale = rsmd.getScale(columIndex);// 刻度
			int precision = rsmd.getPrecision(columIndex);// 精度

			// 字段类型为整型，scale一定为0
			boolean isLong = (scale == 0);
			// 该处理用于处理oracle的sequence.nextval的返回值（sequence.nextval值规定必须是整型，但是其scale为-127，与float类型一样）
			boolean isSequence = (scale == -127 && precision == 0);

			if (isLong || isSequence) {
				obj = ((BigDecimal) obj).longValue();
			} else {
				obj = ((BigDecimal) obj).floatValue();
			}
			rsmd = null;
		}

		return obj;
	}
	public static void closePreparedStatement(PreparedStatement pstmt) {
		try {
			if (pstmt != null) {
				pstmt.close();
			}
		} catch (SQLException ex) {
			ex.printStackTrace();
		}
		
	}

	public static void closeConnection(Connection conn) {
		
			try {
				if (conn != null)
					conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
	}

	public static void closeStatement(Statement stmt) {
		try {
			if (stmt != null) {
				stmt.close();
			}
		} catch (SQLException ex) {
			ex.printStackTrace();
		}

	}

	public static void closeResultSet(ResultSet rs) {
		try {
			if (rs != null) {
				rs.close();
			}
		} catch (SQLException ex) {
			ex.printStackTrace();
		}

	}

	public static void colseAll(Statement stmt, ResultSet rs) {
		if (rs != null)
			try {
				rs.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		if (stmt != null)
			try {
				stmt.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}

	}

	public static void colseAll(PreparedStatement pstmt, ResultSet rs) {
		if (rs != null)
			try {
				rs.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		if (pstmt != null)
			try {
				pstmt.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}

	}

	public static void colse(Object obj) {
		if (obj instanceof Connection) {
			Connection conn = (Connection) obj;
			if (conn != null)
				try {
					conn.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
		}
		if (obj instanceof PreparedStatement) {
			PreparedStatement pstmt = (PreparedStatement) obj;
			if (pstmt != null)
				try {
					pstmt.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
		}
		if (obj instanceof Statement) {
			Statement stmt = (Statement) obj;
			if (stmt != null)
				try {
					stmt.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
		}
		if (obj instanceof ResultSet) {
			ResultSet rs = (ResultSet) obj;
			if (rs != null)
				try {
					rs.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
		}

	}
}
