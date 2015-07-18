package com.cherry.spring.plugin.db.jdbc;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.RowCallbackHandler;
import org.springframework.jdbc.support.rowset.SqlRowSet;

/**
 * 
 * @author Administrator
 *JDBC 操作接口 定义并封装了一些常用的 JDBC 操作接口
 */
public interface JdbcHandler {

	 /**
	   * 执行 INSERT, UPDATE, DELETE, CREATE, DROP 等语句。
	   * 在此 PreparedStatement 对象中执行 SQL 语句，该语句必须是一个 SQL 数据操作语言（Data Manipulation Language，DML）语句，比如 INSERT、UPDATE 或 DELETE 语句；或者是无返回内容的 SQL 语句，比如 DDL 语句
	   * @param sql 要执行的SQL语句
	   * @return 执行是否成功
	   */
	  public  boolean execute(String sql);

	  /**
	   * 执行带预处理的(?)的 INSERT, UPDATE, DELETE, CREATE, DROP 等语句
	   * 在此 PreparedStatement 对象中执行 SQL 语句，该语句必须是一个 SQL 数据操作语言（Data Manipulation Language，DML）语句，比如 INSERT、UPDATE 或 DELETE 语句；或者是无返回内容的 SQL 语句，比如 DDL 语句
	   * @param sql 要执行的sql语句 要执行的sql语句
	   * @param paramVlues 参数
	   * @return 执行是否成功
	   */
	  public  boolean execute(String sql, Object... paramVlues);
	  /**
		 * 执行批量的 INSERT, UPDATE, DELETE 等DDL语句。
		 * <p>
		 * 该批量操作默认不分批执行，不回滚操作。
		 * <p>
		 * 如果想要设置分批执行，则进行如下设置即可：
		 * <p>
		 * <hr>
		 * <blockquote>
		 * 
		 * <pre>
		 *  // 设置分批大小 JdbcHandler.setBatchSize( int batchSize )，例如：每100条SQL命令执行一次
		 *  jdbc.setBatchSize(100);
		 *  // 调用批量操作接口
		 *  jdbc.batchUpdate( ..... );
		 * </pre>
		 * 
		 * </blockquote>
		 * <hr>
		 * <p>
		 * @param sqls 批量静态SQL语句数组
		 * @return 操作成功的条数
		 */
	  public int[] batchUpdate(List<String> sqlList);
	  /**
		 * 执行批量的 INSERT, UPDATE, DELETE 等DDL语句。
		 * <p>
		 * 该批量操作默认不分批执行，不回滚操作。
		 * <p>
		 * 如果想要设置分批执行，则进行如下设置即可：
		 * <p>
		 * <hr>
		 * <blockquote>
		 * 
		 * <pre>
		 *  // 设置分批大小 JdbcHandler.setBatchSize( int batchSize )，例如：每100条SQL命令执行一次
		 *  jdbc.setBatchSize(100);
		 *  // 调用批量操作接口
		 *  jdbc.batchUpdate( ..... );
		 * </pre>
		 * 
		 * </blockquote>
		 * <hr>
		 * <p>
		 * @param sqls 批量静态SQL语句数组
		 * @param params sql语句中参数值
		 * @return 操作成功的条数
		 */
	  public int[] batchUpdate(String sql,List<Object[]> params);
	  
		/**
		 * 执行一个带参数的SQL语句，并通过自定义实现RowCallbackHandler接口返回对象
		 * <p>
		 * Example:
		 * </p>
		 * <p>
		 * <hr>
		 * <blockquote>
		 * 
		 * <pre>
		 * JdbcHandler jdbc = DBFactory.create(...);
		 * 
		 * // 查询单一对象，自定义返回HashMap
		 * String sql = &quot; SELECT * FROM tableName WHERE id = ? &quot;;
		 * Map&lt;String, String&gt; map = jdbc.query( sql, new RowCallbackHandler&lt;Map&lt;String,String&gt;&gt;(){
		 *    // 实现 handle 接口
		 *    public Object handle(ResultSet rs) throws SQLException {
		 *        Map&lt;String, String&gt; map = new HashMap&lt;String, String&gt;();
		 *        if (rs.next()) {
		 *             ResultSetMetaData rsmd = rs.getMetaData();
		 *             int columnCount = rsmd.getColumnCount();
		 *             for (int i = 1; i &lt;= columnCount; i++) {
		 *                 map.put(rsmd.getColumnLabel(i) ,rs.getString(i));
		 *             }
		 *        }
		 *        if (rs != null){
		 *            rs.close();
		 *            rs = null;
		 *        }
		 *        return map;
		 *    }
		 *  }, 1);
		 * </pre>
		 * 
		 * </blockquote>
		 * <p>
		 * <hr>
		 * <blockquote>
		 * 
		 * <pre>
		 * // 查询结果集，并自定义返回List&lt;Map&lt;String, String&gt;&gt;
		 * String	sql2	= &quot;SELECT * FROM table1&quot;;
		 * List&lt;Map&lt;String, String&gt;&gt; list = dbm.query(sql2, new RowCallbackHandler&lt;List&lt;Map&lt;String, String&gt;&gt;&gt;()
		 * {
		 * 		// 实现 handle 接口
		 * 		public Object handle(ResultSet rs) throws SQLException {
		 * 			List&lt;Map&lt;String, String&gt;&gt; _list = new ArrayList&lt;Map&lt;String, String&gt;&gt;();
		 * 			Map&lt;String, String&gt; map = null;
		 * 			while (rs.next()){
		 * 				map = new HashMap&lt;String, String&gt;();
		 * 				ResultSetMetaData rsmd = rs.getMetaData();
		 * 				int columnCount = rsmd.getColumnCount();
		 * 				for (int i = 1; i &lt;= columnCount; i++) {
		 * 					map.put(rsmd.getColumnLabel(i), rs.getString(i));
		 * 				}
		 * 				_list.add(map);
		 * 			}
		 * 
		 * 			if (rs != null) {
		 * 				rs.close();
		 * 				rs = null;
		 * 			}
		 * 
		 * 			return _list;
		 * 		}
		 * 	}, new Object[]{});
		 * </pre>
		 * 
		 * </blockquote>
		 * <hr>
		 * 
		 * @param <T> RowCallbackHandler转换返回的对象类型
		 * @param sql 要执行的SQL语句
		 * @param rch RowCallbackHandler要转换ResultSet结果集接口的实现类
		 * @param paramValues SQL语句中要替换的参数值
		 */
		public void  query(String sql, RowCallbackHandler rch, Object... paramValues);
		
		/**
		 * 查询单一数据对象，以超类Object返回
		 * 
		 * @param sql 要执行的查询语句
		 * @return 单一Object数据对象
		 */
		public Object queryForSingleColumnObject(String sql);

		/**
		 * 查询单一数据对象，以超类Object返回
		 * 
		 * @param sql 要执行的查询语句
		 * @param paramValues 查询语句中的变量值
		 * @return 单一Object数据对象
		 */
		public Object queryForSingleColumnObject(String sql, Object... paramValues);
		
		
		
	  /**
	   * 查询单一对象，并转换成自定义JavaBean对象
	   * @param <T> JavaBean对象
	   * @param beanClass JavaBean对应的Class类
	   * @param sql 要执行的sql语句
	   * @return JavaBean对象
	   */
	  public  <T> T queryForBean(Class<T> beanClass, String sql);

	  /**
	   * 查询单一对象，并转换成自定义JavaBean对象
	   * @param <T>   JavaBean对象
	   * @param beanClass JavaBean对应的Class类
	   * @param sql 要执行的sql语句
	   * @param paramVlues 参数数组对象
	   * @return
	   */
	  public  <T> T queryForBean(Class<T> beanClass, String sql, Object ... paramVlues);

	  /**
	   * 查询单一数据对象，并封装成Map 返回
	   * @param sql 要执行的sql语句
	   * @return 单一数据对象
	   */
	  public  Map<String, Object> queryForMap(String sql);

	  /**
	   *  查询带参数的单一数据对象，并封装成Map 返回
	   * @param sql 要执行的sql语句
	   * @param paramVlues  参数数组
	   * @return  单一数据对象
	   */
	  public  Map<String, Object> queryForMap(String sql, Object ... paramVlues);

	  /**
	   * 查询单一数据类型 String
	   * @param sql 要执行的sql语句
	   * @return 单一String数据
	   */
	  public  String queryForString(String sql);

	  /**
	   * 查询带参数的单一数据类型 String
	   * @param sql 要执行的sql语句
	   * @param paramValues 参数数组
	   * @return 单一String数据
	   */
	  public  String queryForString(String sql, Object ... paramValues);
	  /**
	   *   查询单一数据类型 Integer
	   * @param sql 要执行的sql语句
	   * @return 单一Integer数据
	   */
	  public  Integer queryForInteger(String sql);

	  /**
	   * 查询带参数的单一数据类型 Integer
	   * @param sql 要执行的sql语句
	   * @param paramValues 参数数组
	   * @return 单一Integer数据
	   */
	  public  Integer queryForInteger(String sql, Object ... paramValues);

	  /**
	   * 查询单一数据类型 Long
	   * @param sql 要执行的sql语句
	   * @return 单一Long数据
	   */
	  public  Long queryForLong(String sql);

	  /**
	   * 查询带参数的单一数据类型 Long
	   * @param sql 要执行的sql语句
	   * @param paramVlues 参数数组
	   * @return  单一Long数据
	   */
	  public  Long queryForLong(String sql, Object ... paramVlues);

	  /**
	   *  查询数据结果集，以List集合存储自定义泛型类型返回
	   * @param <T>
	   * @param beanClass
	   * @param sql 要执行的sql语句
	   * @return List集合存储自定义泛型类型
	   */
	  public  <T> List<T> queryForSingleColumnList(Class<T> beanClass, String sql);

	  /**
	   * 查询带参数的数据结果集，以List集合存储自定义泛型类型返回
	   * @param <T>
	   * @param beanClass
	   * @param sql 要执行的sql语句
	   * @param paramVlues  参数数组
	   * @return List集合存储自定义泛型类型
	   */
	  public  <T> List<T> queryForSingleColumnList(Class<T> beanClass, String sql, Object ... paramVlues);

	  /**
	   * 查询支持分页数据结果集，以List集合存储自定义泛型类型返回
	   * @param <T>
	   * @param beanClass
	   * @param sql 要执行的sql语句
	   * @param pageNum 第几页 
	   * @param pageSize  每一页显示的条数
	   * @param paramValues
	   * @return
	   */
	  public  <T> List<T> queryForSingleColumnList(Class<T> beanClass, String sql,int pageNum,int pageSize, Object ... paramValues);

	  
	  /**
	   *  查询数据结果集，以List集合存储自定义泛型类型返回
	   * @param <T>
	   * @param beanClass
	   * @param sql 要执行的sql语句
	   * @return List集合存储自定义泛型类型
	   */
	  public  <T> List<T> queryForBeanList(Class<T> beanClass, String sql);

	  /**
	   * 查询带参数的数据结果集，以List集合存储自定义泛型类型返回
	   * @param <T>
	   * @param beanClass
	   * @param sql 要执行的sql语句
	   * @param paramVlues  参数数组
	   * @return List集合存储自定义泛型类型
	   */
	  public  <T> List<T> queryForBeanList(Class<T> beanClass, String sql, Object ... paramVlues);

	  /**
	   * 查询支持分页数据结果集，以List集合存储自定义泛型类型返回
	   * @param <T>
	   * @param beanClass
	   * @param sql 要执行的sql语句
	   * @param pageNum 第几页 
	   * @param pageSize  每一页显示的条数
	   * @param paramValues
	   * @return
	   */
	  public  <T> List<T> queryForBeanList(Class<T> beanClass, String sql,int pageNum,int pageSize, Object ... paramValues);

	  
	  /**
	   * 查询数据结果集，以List集合存储Map类型返回
	   * @param sql 要执行的sql语句
	   * @return   List集合存储Map类型数据
	   */
	  public  List<Map<String, Object>> queryForListMap(String sql);

	  /**
	   *  查询带预处理参数的数据结果集，以List集合存储Map类型返回
	   * @param sql 要执行的sql语句
	   * @param paramVlues 参数数组
	   * @return List集合存储Map类型数据
	   */
	  public  List<Map<String, Object>> queryForListMap(String sql, Object ... paramVlues);

	  /**
	   * 查询支持分页数据结果集，以List集合存储Map类型返回
	   * @param sql 要执行的sql语句
	   * @param pageNum 起始条数
	   * @param pageSize  每一页显示的条数
	   * @param paramVlues  参数数组
	   * @return List集合存储Map类型数据
	   */
	  public  List<Map<String, Object>> queryForListMap(String sql, int pageNum,int pageSize, Object ... paramVlues);

	  
	  public SqlRowSet queryForRowSet(String sql) throws DataAccessException;
	  
	  public SqlRowSet queryForRowSet(String sql, Object... args) throws DataAccessException;
	  /**
	   * 获取数据表所有字段名称值
	   * @param tableName 数据库表名称
	   * @return 表所有字段名称值集合
	   */
	  public  List<String> getAllTableFields(String tableName);

	  /**
		 * 判断数据库中是否存在表名为<code>tableName</code>的表
		 * 
		 * @param tableName 表名
		 * @return true--表存在，false--表不存在
		 */
		public boolean isTableExist(String tableName);

		/**
		 * 调用无参数存储过程
		 * 
		 * @param procedureName 存储过程名称
		 * @return true -- 调用成功， false -- 调用失败
		 */
		public boolean callProcedure(String procedureName);

		/**
		 * 调用存储过程
		 * 
		 * @param paramValues 存储过程参数值
		 * @return true -- 调用成功， false -- 调用失败
		 */
		public boolean callProcedure(String procedureName, Object... paramValues);

		/**
		 * 调用无参数存储过程，返回结果集
		 * 
		 * @param procedureName 存储过程名称
		 * @return 执行存储返回结果字符串
		 */
		public String callProcedureForResult(String procedureName);

		/**
		 * 调用无参数存储过程，返回结果对象 <br>
		 * 存储过程中请将返回参数写在最前面
		 * 
		 * @param procedureName 存储过程名称
		 * @param sqlType java.sql.Types.xxx指定
		 * @return 执行存储返回结果对象
		 */
		public Object callProcedureForResult(String procedureName, int sqlType);

		/**
		 * 调用存储过程，返回结果字符串 <br>
		 * 存储过程中请将返回参数写在最前面
		 * 
		 * @param procedureName 存储过程名称
		 * @param paramValues 存储过程参数值
		 * @return 执行存储返回return结果字符串
		 */
		public String callProcedureForResult(String procedureName, Object... paramValues);

		/**
		 * 调用存储过程，返回结果对象 <br>
		 * 存储过程中请将返回参数写在最前面
		 * 
		 * @param procedureName 存储过程名称
		 * @param sqlType java.sql.Types.xxx指定
		 * @param paramValues 存储过程参数值
		 * @return 执行存储返回结果对象
		 */
		public Object callProcedureForResult(String procedureName, int sqlType, Object... paramValues);

		/**
		 * 调用存储过程返回结果集<br>
		 * <code>
		 * PROCEDURE TEST_P (param1 in varchar2,param2 out varchar2)<br>
		 * 
		 * callProcedureForResult("TEST_P", java.sql.Types.VARCHAR, 1, "param1");
		 * 
		 * </code>
		 * 
		 * @param procedureName 存储过程名称
		 * @param sqlType out参数的参数类型，使用 java.sql.Types.xxx 指定
		 * @param sqlTypeIndex out参数在所有参数集中的位置，位置计数从0开始
		 * @param paramValues in类型参数的参数值
		 * @return 执行存储过程返回的out参数结果集，顺序按照存储过程中out参数的先后顺序
		 */
		public Object callProcedureForResult(String procedureName, int sqlType, int sqlTypeIndex, Object... paramValues);

		/**
		 * 调用存储过程，返回结果对象集合List <br>
		 * 存储过程中请将返回参数写在最前面
		 * 
		 * @param procedureName 存储过程名称
		 * @param sqlTypes java.sql.Types.xxx指定
		 * @param paramValues 存储过程参数值
		 * @return 执行存储返回结果对象集合List
		 */
		public List<Object> callProcedureForResult(String procedureName, int[] sqlTypes, Object... paramValues);

		/**
		 * 调用存储过程返回结果集<br>
		 * <code>
		 * PROCEDURE TEST_P (param1 in varchar2,param2 out varchar2)<br>
		 * 
		 * callProcedureForResult("TEST_P", new int[]{java.sql.Types.VARCHAR}, new int[]{1},"param1");
		 * 
		 * </code>
		 * 
		 * @param procedureName 存储过程名称
		 * @param sqlTypes out参数的参数类型，使用 java.sql.Types.xxx 指定
		 * @param sqlTypesIndex out参数在所有参数集中的位置，位置计数从0开始，该参数的长度应该与sqlTypes相同，即sqlTypes与sqlTypesIndex应该一一对应
		 * @param paramValues in类型参数的参数值
		 * @return 执行存储过程返回的out参数结果集，顺序按照存储过程中out参数的先后顺序
		 */
		public List<Object> callProcedureForResult(String procedureName, int[] sqlTypes, int[] sqlTypesIndex, Object... paramValues);
	  /**
	   * 设置批量操作提交的分段大小，默认每100条记录提交一次
	   * @param batchSize
	   */
	  public  void setBatchSize(int batchSize);
	  
	  /**
		 * 判断索引是否存在，该方法可用于MySQL和Oracle数据库
		 * 
		 * @param tableName 表名
		 * @param indexName 索引名
		 * @return true -- 存在，false -- 不存在
		 */
		public boolean isIndexExist(String tableName, String indexName);
		
		/**
		 * 判断函数是否存在，仅用于Oracle数据库
		 * 
		 * @param functionName 函数名
		 * @return true -- 存在，false -- 不存在
		 */
		public boolean isFunctionExist(String functionName);

		/**
		 * 判断存储过程是否存在，仅用于Oracle数据库
		 * 
		 * @param procedureName 存储过程名
		 * @return true -- 存在，false -- 不存在
		 */
		public boolean isProcedureExist(String procedureName);
		
		/**
		 * 判断序列是否存在，仅用于Oracle数据库
		 * 
		 * @param sequenceName 序列名
		 * @return true -- 存在，false -- 不存在
		 */
		public boolean isSequenceExist(String sequenceName);
		
	  /**
	   *  从连接池获得一个数据库连接
	   * @return Connection对象
	   */
	  public  Connection getConnection() throws SQLException;
	  
	  /**
		 * 判断是否可以获得当前数据源的连接
		 * 
		 * @return true -- 可获得，false -- 不可获得
		 */
	public boolean hasConnection();
	  
}
