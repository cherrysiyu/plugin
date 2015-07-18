package com.cherry.spring.plugin.db.jdbc;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.sql.DataSource;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.RowCallbackHandler;
import org.springframework.jdbc.core.SingleColumnRowMapper;
import org.springframework.jdbc.datasource.DataSourceUtils;
import org.springframework.jdbc.support.rowset.SqlRowSet;

import com.cherry.spring.plugin.db.DBException;
import com.cherry.spring.plugin.db.JDBCUtils;
import com.cherry.spring.plugin.db.dialect.Dialect;
import com.cherry.spring.plugin.db.dialect.OracleDialect;


/**
 * 操作接口 定义并封装了一些常用的 JDBC 操作接口实现类
 * @author Administrator
 *
 */
public class JdbcHandlerImpl implements JdbcHandler{
	private static Logger logger = LoggerFactory.getLogger(JdbcHandlerImpl.class);
	private JdbcTemplate jdbcTemplate;
	
	private String driverUrl="jdbc:oracle";

	public JdbcHandlerImpl(String driverUrl,JdbcTemplate jdbcTemplate) {
		super();
		this.jdbcTemplate = jdbcTemplate;
		setDriverUrl(driverUrl);
	}

	public JdbcHandlerImpl(String driverUrl,DataSource dataSource) {
		this(driverUrl,dataSource,true);
	}
	public JdbcHandlerImpl(String driverUrl,DataSource dataSource, boolean lazyInit) {
		super();
		jdbcTemplate = new JdbcTemplate(dataSource,lazyInit);
		setDriverUrl(driverUrl);
	}


	/**
	 * 数据库类型，默认oracle
	 */
	private  Dialect dialect = new OracleDialect();
																	
	private static  ThreadLocal<Integer> BATCH_SIZE_LOCAL = new ThreadLocal<Integer>() {
		protected Integer initialValue() {
			return Integer.valueOf(-1);
		}

	};

	public boolean execute(String sql) {
		JDBCUtils.printSQL(sql);
		return jdbcTemplate.update(sql)>0;
	}

	public boolean execute(String sql, Object... paramVlues) {
		JDBCUtils.printSQL(sql,paramVlues);
		return jdbcTemplate.update(sql, paramVlues)>0;
	}
	
	public int[] batchUpdate(List<String> sqlList) {
		return jdbcTemplate.batchUpdate(sqlList.toArray(new String[]{}));
	}

	public int[] batchUpdate(String sql, List<Object[]> params) {
		return jdbcTemplate.batchUpdate(sql, params);
	}

	public void  query(String sql, RowCallbackHandler rch,Object... paramValues) {
		JDBCUtils.printSQL(sql,paramValues);
		jdbcTemplate.query(sql,rch, paramValues);
	}

	public <T> T queryForBean(Class<T> beanClass, String sql) {
		JDBCUtils.printSQL(sql);
		return queryForBean(beanClass,sql,new Object[]{});
	}

	public <T> T queryForBean(Class<T> beanClass, String sql,Object... paramVlues) {
		JDBCUtils.printSQL(sql,paramVlues);
		List<T> list = queryForBeanList(beanClass, sql, paramVlues);
		if(list != null && !list.isEmpty())
			return list.get(0);
		return null;
	}
	
	public Object queryForSingleColumnObject(String sql) {
		JDBCUtils.printSQL(sql);
		return jdbcTemplate.queryForObject(sql, new SingleColumnRowMapper<Object>());
	}

	public Object queryForSingleColumnObject(String sql, Object... paramValues) {
		JDBCUtils.printSQL(sql,paramValues);
		return jdbcTemplate.queryForObject(sql, paramValues, new SingleColumnRowMapper<Object>());
	}

	public Map<String, Object> queryForMap(String sql) {
		JDBCUtils.printSQL(sql,sql);
		return	jdbcTemplate.queryForMap(sql);
	}

	public Map<String, Object> queryForMap(String sql, Object... paramVlues) {
		JDBCUtils.printSQL(sql,paramVlues);
		return jdbcTemplate.queryForMap(sql, paramVlues);
	}

	public String queryForString(String sql) {
		JDBCUtils.printSQL(sql);
		return jdbcTemplate.queryForObject(sql, String.class);
	}

	public String queryForString(String sql, Object... paramValues) {
		JDBCUtils.printSQL(sql,paramValues);
		return jdbcTemplate.queryForObject(sql, String.class, paramValues);
	}

	public Integer queryForInteger(String sql) {
		JDBCUtils.printSQL(sql);
		return jdbcTemplate.queryForObject(sql, Integer.class);
	}

	public Integer queryForInteger(String sql, Object... paramValues) {
		JDBCUtils.printSQL(sql,paramValues);
		return jdbcTemplate.queryForObject(sql, Integer.class,paramValues);
	}

	public Long queryForLong(String sql) {
		JDBCUtils.printSQL(sql);
		return jdbcTemplate.queryForObject(sql, Long.class);
	}

	public Long queryForLong(String sql, Object... paramVlues) {
		JDBCUtils.printSQL(sql,paramVlues);
		return jdbcTemplate.queryForObject(sql, Long.class,paramVlues);
	}

	public <T> List<T> queryForSingleColumnList(Class<T> beanClass, String sql) {
		JDBCUtils.printSQL(sql);
		return jdbcTemplate.queryForList(sql, beanClass);
	}

	public <T> List<T> queryForSingleColumnList(Class<T> beanClass, String sql,Object... paramVlues) {
		JDBCUtils.printSQL(sql,paramVlues);
		return jdbcTemplate.queryForList(sql, beanClass, paramVlues);
	}

	public <T> List<T> queryForSingleColumnList(Class<T> beanClass, String sql,int pageNum, int pageSize, Object... paramValues) {
		String pageSql = Dialect.createPageSQL(dialect,sql, pageNum, pageSize);
		JDBCUtils.printSQL(pageSql,paramValues);
		return jdbcTemplate.queryForList(pageSql, beanClass, paramValues);
	}

	
	public <T> List<T> queryForBeanList(Class<T> beanClass, String sql) {
		JDBCUtils.printSQL(sql);
		return queryForBeanList(beanClass,sql,new Object[]{});
	}

	public <T> List<T> queryForBeanList(Class<T> beanClass, String sql,Object... paramVlues) {
		JDBCUtils.printSQL(sql,paramVlues);
		return jdbcTemplate.query(sql, paramVlues, new  BeanPropertyRowMapper<T>(beanClass));
	}

	public <T> List<T> queryForBeanList(Class<T> beanClass, String sql,int pageNum, int pageSize, Object... paramValues) {
		String pageSql = Dialect.createPageSQL(dialect,sql, pageNum, pageSize);
		JDBCUtils.printSQL(pageSql,paramValues);
		return queryForBeanList(beanClass,pageSql,paramValues);
	}
	
	public List<Map<String, Object>> queryForListMap(String sql) {
		JDBCUtils.printSQL(sql);
		return jdbcTemplate.queryForList(sql);
	}

	public List<Map<String, Object>> queryForListMap(String sql,Object... paramVlues) {
		JDBCUtils.printSQL(sql,paramVlues);
		return jdbcTemplate.queryForList(sql, paramVlues);
	}

	public List<Map<String, Object>> queryForListMap(String sql, int pageNum,int pageSize, Object... paramVlues) {
		String pageSql = Dialect.createPageSQL(dialect,sql, pageNum, pageSize);
		JDBCUtils.printSQL(pageSql,paramVlues);
		return jdbcTemplate.queryForList(pageSql, paramVlues);
	}

	 public SqlRowSet queryForRowSet(String sql) throws DataAccessException{
		 JDBCUtils.printSQL(sql);
		 return jdbcTemplate.queryForRowSet(sql);
	 }
	  
	  public SqlRowSet queryForRowSet(String sql, Object... paramVlues) throws DataAccessException{
		  JDBCUtils.printSQL(sql,paramVlues);
		  return jdbcTemplate.queryForRowSet(sql,paramVlues);
	  }
	  
	  
	

	public boolean callProcedure(String procedureName) {
		return callProcedure(procedureName, new Object[]{});
	}

	public boolean callProcedure(String procedureName, Object... paramValues) {
		boolean result = false;
		CallableStatement cstmt = null;
		Connection conn = null;
		try {
			// 语法：{ call procedure_name[(?, ?, ...)] }
			// 组织调用参数
			String sql = JDBCUtils.joinCallSQL(procedureName, null, paramValues);
			JDBCUtils.printSQL(sql, paramValues);
			conn = getConnection();
			cstmt = conn.prepareCall(sql);
		} catch (SQLException e) {
			logger.error("Failed to call procedure["+ procedureName+ "]"+ (paramValues != null ? (" by params ["+ Arrays.deepToString(paramValues) + "]"): ""), e);
			// 如果出于事务处理，则抛出异常用于捕获
			if (DataSourceUtils.isConnectionTransactional(conn,jdbcTemplate.getDataSource())) {
				throw new DBException("Failed to call procedure["+ procedureName+ "]"+ (paramValues != null ? (" by params ["+ Arrays.deepToString(paramValues) + "]"): ""), e);
			}
		} finally {
			JDBCUtils.closePreparedStatement(cstmt);
			// 存储过程关闭Connection不归入事务管理，直接调用连接池关闭
			DataSourceUtils.releaseConnection(conn,jdbcTemplate.getDataSource());
		}
		return result;
	}

	public String callProcedureForResult(String procedureName) {
		return callProcedureForResult(procedureName, (Object[]) null);
	}

	public Object callProcedureForResult(String procedureName, int sqlType) {
		return callProcedureForResult(procedureName, sqlType, (Object[]) null);
	}

	public String callProcedureForResult(String procedureName,Object... paramValues) {
		return (String) callProcedureForResult(procedureName, Types.VARCHAR, paramValues);
	}

	public Object callProcedureForResult(String procedureName, int sqlType,Object... paramValues) {
		return callProcedureForResult(procedureName, new int[]{ sqlType }, paramValues).get(0);
	}

	public Object callProcedureForResult(String procedureName, int sqlType,int sqlTypeIndex, Object... paramValues) {
		return callProcedureForResult(procedureName, new int[]{ sqlType }, new int[]{ sqlTypeIndex }, paramValues).get(0);
	}

	public List<Object> callProcedureForResult(String procedureName,int[] sqlTypes, Object... paramValues) {
		int[] sql_index = new int[sqlTypes.length];
		for (int i = 0; i < sql_index.length; i++) {
			sql_index[i] = i;
		}
		return callProcedureForResult(procedureName, sqlTypes, sql_index,paramValues);
	}

	public List<Object> callProcedureForResult(String procedureName,int[] sqlTypes, int[] sqlTypesIndex, Object... paramValues) {
		
		if (!hasConnection()){
			throw new DBException("Get connection fail!");
		}
		// sqlTypes 与 sqlTypesIndex 不一致
		if ((sqlTypes != null && sqlTypesIndex == null)
				|| (sqlTypes == null && sqlTypesIndex != null)
				|| (sqlTypes != null && sqlTypesIndex != null && sqlTypes.length != sqlTypesIndex.length)) {
			throw new DBException("[sqlTypes] and [sqlTypesIndex] is not corresponding!");
		}
		List<Object> list = new ArrayList<Object>();
		CallableStatement cstmt = null;
		Connection conn = null;
		try{
			// 组织调用语句
			String sql = JDBCUtils.joinCallSQL(procedureName, sqlTypes == null ? new int[]{} : sqlTypes, paramValues);
			conn = getConnection();
			cstmt = conn.prepareCall(sql);
			// 参数个数
			int paramCount = (sqlTypes == null ? 0 : sqlTypes.length) + (paramValues == null ? 0 : paramValues.length);
			// 最终参数集
			Object[] final_params = new Object[paramCount];
			Set<Integer> i_set = new HashSet<Integer>();
			// 设置out参数在所有参数的位置
			if (sqlTypes != null && sqlTypesIndex != null) {
				for (int i = 0; i < sqlTypesIndex.length; i++) {
					i_set.add(sqlTypesIndex[i]);
					final_params[sqlTypesIndex[i]] = sqlTypes[i];
				}
			}
			// 设置in参数在所有参数的位置
			if (paramValues != null) {
				int p_i = 0;
				for (int i = 0; i < paramCount; i++) {
					if (final_params[i] == null) {
						final_params[i] = paramValues[p_i++];
					}
				}
			}
			JDBCUtils.printSQL(sql, final_params);
			if ((sqlTypes != null && sqlTypesIndex != null)|| paramValues != null) {
				for (int i = 0; i < final_params.length; i++) {
					// 注册out参数
					if (i_set.contains(i)) {
						cstmt.registerOutParameter(i + 1,(Integer) final_params[i]);
					}
					// 设置 in 参数
					else {
						cstmt.setObject(i + 1, final_params[i]);
					}
				}
			}

			cstmt.execute();

			if (sqlTypes != null && sqlTypesIndex != null) {
				for (int i = 0; i < sqlTypes.length; i++) {
					list.add(cstmt.getObject(sqlTypesIndex[i] + 1));
				}
			} else {
				list.add(cstmt.getObject(1));
			}
			sql = null;
		}catch (SQLException e){
			logger.error("Failed to call procedure [ " + procedureName + " ]");
			throw new DBException("Failed to call procedure [ " + procedureName + " ]", e);
		}finally{
			JDBCUtils.closePreparedStatement(cstmt);
			// 存储过程关闭Connection不归入事务管理，直接调用连接池关闭
			DataSourceUtils.releaseConnection(conn,jdbcTemplate.getDataSource());
		}
		return list;
	}

	public void setBatchSize(int batchSize) {
		if (batchSize < 0) {
			throw new IllegalArgumentException(
					"The batchSize must be greater than  0!");
		}
		BATCH_SIZE_LOCAL.set(Integer.valueOf(batchSize));
		jdbcTemplate.setFetchSize(BATCH_SIZE_LOCAL.get());
	}

	public List<String> getAllTableFields(String tableName) {
		if(StringUtils.isBlank(tableName)){
			return new ArrayList<String>();
		}
		
		if(dialect.getClass().equals(Dialect.ORACLE)){
			String sql = "select * from " + tableName + "";
			String pageSql = Dialect.createPageSQL(dialect,sql, 1, 1);
			return jdbcTemplate.query(pageSql,new  ResultSetExtractor<List<String>>() {
				public List<String> extractData(ResultSet rs) throws SQLException,DataAccessException {
					List<String> list = new ArrayList<String>();
					 ResultSetMetaData ar = rs.getMetaData();
			            int columnnum = ar.getColumnCount();
			            for(int k = 1 ; k <= columnnum ; k++){
			                String columnName = ar.getColumnName(k).toLowerCase();
			                list.add(columnName.toUpperCase());
			            }
					return list;
				}
			});
		}else{
			String sql = "SELECT column_name FROM information_schema.columns WHERE table_name = ? AND table_schema = ?";
			Object[] params = new Object[]{ tableName.toLowerCase(), JDBCUtils.getMySQLSchema(driverUrl) };
			return queryForSingleColumnList(String.class, sql, params);
		
		}
	}

	
	public boolean isTableExist(String tableName) {
		if(StringUtils.isBlank(tableName)){
			return false;
		}
		String sql = null;
		Object[] params = null;
		if(dialect.getClass().equals(Dialect.ORACLE)){
			sql = "SELECT count(1) FROM sys.user_tables WHERE table_name = ?";
			params = new Object[]{ tableName.toUpperCase() };
		}else{
			sql = "SELECT count(1) FROM information_schema.tables WHERE table_name = ? AND table_schema = ?";
			params = new Object[]{ tableName.toLowerCase(), JDBCUtils.getMySQLSchema(driverUrl) };
		}
		return queryForLong(sql, params) > 0;
	}
	public boolean isIndexExist(String tableName, String indexName) {
		if (StringUtils.isEmpty(tableName) || StringUtils.isEmpty(indexName)){
			return false;
		}
		String sql = null;
		if(dialect.getClass().equals(Dialect.ORACLE)){
			sql = "SELECT count(1) FROM sys.user_indexes WHERE TABLE_NAME = ? and INDEX_NAME = ?";
		}else{
			sql = "SELECT count(1) FROM information_schema.statistics WHERE TABLE_NAME = ? and INDEX_NAME = ? and TABLE_SCHEMA = '"
					+ JDBCUtils.getMySQLSchema(driverUrl) + "'";
		}
		return queryForInteger(sql, tableName, indexName) != null;
	}

	public Connection getConnection() throws SQLException {
		return jdbcTemplate.getDataSource().getConnection();
	}

	

	public JdbcTemplate getJdbcTemplate() {
		return jdbcTemplate;
	}

	public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	public void setDriverUrl(String driverUrl) {
		this.driverUrl = driverUrl;
		dialect = Dialect.getDialect(driverUrl);
	}
	public boolean hasConnection(){
		try {
			return getConnection() != null;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}
	
	public boolean isFunctionExist(String functionName) {
		if (StringUtils.isEmpty(functionName)) {
			return false;
		}
		String sql = "SELECT count(1) FROM sys.user_objects WHERE object_type = 'FUNCTION' AND object_name = ?";
		return queryForInteger(sql, functionName.toUpperCase()) != null;
	}

	public boolean isProcedureExist(String procedureName) {
		if (StringUtils.isEmpty(procedureName)) {
			return false;
		}
		String sql = "SELECT count(1) FROM sys.user_objects WHERE object_type = 'PROCEDURE' AND object_name = ?";
		return queryForInteger(sql, procedureName.toUpperCase()) != null;
	}
	
	public boolean isSequenceExist(String sequenceName) {
		if (StringUtils.isEmpty(sequenceName)) {
			return false;
		}
		String sql = "SELECT count(1) FROM sys.all_sequences WHERE sequence_name = ?";
		return queryForInteger(sql, sequenceName) != null;
	}
}
