package com.cherry.spring.plugin.db.mybatis;

import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.xml.bind.PropertyException;

import org.apache.ibatis.executor.ErrorContext;
import org.apache.ibatis.executor.ExecutorException;
import org.apache.ibatis.executor.statement.BaseStatementHandler;
import org.apache.ibatis.executor.statement.RoutingStatementHandler;
import org.apache.ibatis.executor.statement.StatementHandler;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.ParameterMapping;
import org.apache.ibatis.mapping.ParameterMode;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.plugin.Intercepts;
import org.apache.ibatis.plugin.Invocation;
import org.apache.ibatis.plugin.Plugin;
import org.apache.ibatis.plugin.Signature;
import org.apache.ibatis.reflection.MetaObject;
import org.apache.ibatis.reflection.property.PropertyTokenizer;
import org.apache.ibatis.scripting.xmltags.ForEachSqlNode;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.type.TypeHandler;
import org.apache.ibatis.type.TypeHandlerRegistry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.cherry.spring.plugin.CommonMethod;
import com.cherry.spring.plugin.db.dialect.Dialect;
import com.cherry.spring.plugin.db.dialect.MySQLDialect;
import com.cherry.spring.plugin.db.dialect.OracleDialect;
import com.cherry.spring.plugin.page.OrderCondition;
import com.cherry.spring.plugin.page.Page;

/**
 * MyBatis分页集成插件
 * @description:
 * @version:0.1
 * @author:Cherry
 * @date:2013-6-28
 */
@Intercepts( { @Signature(type = StatementHandler.class, method = "prepare", args = { Connection.class }) })
public class PaginationInterceptor implements Interceptor {
	protected static Logger log = LoggerFactory.getLogger(PaginationInterceptor.class);

	private  String dialect = "";
	private  String pageSqlId = "";

	/*
	 * (non-Javadoc)
	 * @see
	 * org.apache.ibatis.plugin.Interceptor#intercept(org.apache.ibatis.plugin.Invocation)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public Object intercept(Invocation invocation) throws Throwable {
		if (invocation.getTarget() instanceof RoutingStatementHandler) {
			RoutingStatementHandler statementHandler = (RoutingStatementHandler) invocation.getTarget();
			BaseStatementHandler delegate = (BaseStatementHandler) ReflectHelper.getValueByFieldName(statementHandler, "delegate");
			MappedStatement mappedStatement = (MappedStatement) ReflectHelper.getValueByFieldName(delegate, "mappedStatement");
			if (mappedStatement.getId().matches(pageSqlId)) {
				BoundSql boundSql = delegate.getBoundSql();
				if (boundSql == null || boundSql.getSql() == null|| "".equals(boundSql.getSql()))
					return null;
				Object parameterObject = boundSql.getParameterObject();// 分页SQL<select>中parameterType属性对应的实体参数，即Mapper接口中执行分页方法的参数,该参数不得为空
				if (parameterObject == null) {
					throw new NullPointerException("parameterObject尚未实例化！");
				} else {
					boolean isFlag = false;
					Page page = null;
					if (parameterObject instanceof Page) // 参数就是Page实体
						page = (Page) parameterObject;
					else if (parameterObject instanceof Map<?, ?>) {
						Map<String, Object> map = (Map<String, Object>) parameterObject;
						page = (Page) map.get(Dialect.pageNameField);
						if (page == null)
							page = new Page();
						if(map.containsKey(Dialect.orderColumn) && page.getOrderCondition() == null){
							if((map.get(Dialect.orderColumn).getClass()== String.class)&& CommonMethod.isNotEmpty(map.get(Dialect.orderColumn).toString())){
								String order = "ASC";
								if(map.get(Dialect.order) != null && map.get(Dialect.order).getClass()== String.class)
									order = map.get(Dialect.order).toString();
								page.setOrderCondition(new OrderCondition(order,map.get(Dialect.orderColumn).toString()));
							}
						}
					} else {// 参数为某个实体，该实体拥有Page属性
						Field pageField = ReflectHelper.getFieldByFieldName(parameterObject,Dialect.pageNameField);
						if (pageField != null) {
							isFlag = true;
							page = (Page) ReflectHelper.getValueByFieldName(parameterObject,Dialect.pageNameField);
							if (page == null)
								page = new Page();
						} else
							throw new NoSuchFieldException(parameterObject+ "不存在 page 属性！");
					}
					if (page != null) {
						// 如果传过来的page对象已经设置过总条数了，那么就不需要再查询了
						int rowCount = page.getRowCount();
						// 原始sql语句
						StringBuilder sql = new StringBuilder(boundSql.getSql());
						// 得到总记录数,如果总条数已经已知了则不需要再查询了
						if (rowCount <= 0) {
							Connection connection = (Connection) invocation.getArgs()[0];
							StringBuilder countSql = new StringBuilder();
							countSql.append("SELECT COUNT(8) FROM (").append(sql).append(") TMP_COUNT");// 记录一共有多少条
							PreparedStatement countStmt = connection.prepareStatement(countSql.toString());
							setParameters(countStmt, mappedStatement, boundSql,parameterObject);
							ResultSet rs = countStmt.executeQuery();
							if (rs.next()) {
								rowCount = rs.getInt(1);
							}
							rs.close();
							countStmt.close();
							log.debug("总条数 : " + rowCount);
						}
						// 设置总条数
						page.setRowCount(rowCount);
						if (isFlag)
							ReflectHelper.setValueByFieldName(parameterObject,Dialect.pageNameField, page); // 通过反射，对实体对象设置分页对象

						String pageSql = generatePageSql(sql, page);
						log.debug("生成分页SQL : " + pageSql);
						ReflectHelper.setValueByFieldName(boundSql, "sql",pageSql); // 将分页sql语句反射回BoundSql.
					}
				}
			}
		}
		return invocation.proceed();
	}

	private String generatePageSql(StringBuilder sql, Page page) {
		Dialect.Type databaseType = null;
		try {
			databaseType = Dialect.Type.valueOf(dialect.toUpperCase());
		} catch (Exception e) {
			// ignore
		}
		if (databaseType == null) {
			throw new RuntimeException("the value of the dialect property  is not defined : "+ dialect);
		}
		Dialect dialect = null;
		switch (databaseType) {
		case ORACLE:
			dialect = new OracleDialect();
			break;
		case MYSQL:
			// 需要实现MySQL的分页逻辑 break;
			dialect = new MySQLDialect();
			break;
		}
		// 计算查询开始位置 当page<1 时统一设置为 0
		int offset = page.getPageIndex() < 1 ? 0: ((page.getPageIndex() - 1) * page.getPageSize());
		int limit = page.getPageSize() <= 0 ? 10 : page.getPageSize();
		
		//加入排序字段
		if (page.getOrderCondition() != null) {
			OrderCondition orderCondition = page.getOrderCondition();
			sql.append(" ORDER BY ").append(orderCondition.getCloumName()).append(" ").append(orderCondition.getOrder()).append(" ");
		}
		return dialect.getLimitSQL(sql.toString(), offset, limit);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.apache.ibatis.plugin.Interceptor#plugin(java.lang.Object)
	 */
	@Override
	public Object plugin(Object target) {
		return Plugin.wrap(target, this);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.apache.ibatis.plugin.Interceptor#setProperties(java.util.Properties)
	 */
	@Override
	public void setProperties(Properties p) {
		/*dialect = p.getProperty("dialect");
		if (dialect == null || "".equals(dialect)) {
			try {
				throw new PropertyException("dialect property is not found!");
			} catch (PropertyException e) {
				LogUtils.error(e);
			}
		}
		pageSqlId = p.getProperty("pageSqlId");
		if (pageSqlId == null || "".equals(pageSqlId)) {
			try {
				throw new PropertyException("pageSqlId property is not found!");
			} catch (PropertyException e) {
				LogUtils.error(e);
			}
		}*/

	}

	@SuppressWarnings("unchecked")
	private void setParameters(PreparedStatement ps,MappedStatement mappedStatement, BoundSql boundSql,
			Object parameterObject) throws SQLException {
		ErrorContext.instance().activity("setting parameters").object(mappedStatement.getParameterMap().getId());
		List<ParameterMapping> parameterMappings = boundSql.getParameterMappings();
		if (parameterMappings != null) {
			Configuration configuration = mappedStatement.getConfiguration();
			TypeHandlerRegistry typeHandlerRegistry = configuration.getTypeHandlerRegistry();
			MetaObject metaObject = parameterObject == null ? null: configuration.newMetaObject(parameterObject);
			for (int i = 0; i < parameterMappings.size(); i++) {
				ParameterMapping parameterMapping = parameterMappings.get(i);
				if (parameterMapping.getMode() != ParameterMode.OUT) {
					Object value;
					String propertyName = parameterMapping.getProperty();
					PropertyTokenizer prop = new PropertyTokenizer(propertyName);
					if (parameterObject == null) {
						value = null;
					} else if (typeHandlerRegistry.hasTypeHandler(parameterObject.getClass())) {
						value = parameterObject;
					} else if (boundSql.hasAdditionalParameter(propertyName)) {
						value = boundSql.getAdditionalParameter(propertyName);
					} else if (propertyName.startsWith(ForEachSqlNode.ITEM_PREFIX)&& boundSql.hasAdditionalParameter(prop.getName())) {
						value = boundSql.getAdditionalParameter(prop.getName());
						if (value != null)
							value = configuration.newMetaObject(value).getValue(propertyName.substring(prop.getName().length()));
					} else {
						value = metaObject == null ? null : metaObject.getValue(propertyName);
					}
					@SuppressWarnings("rawtypes")
					TypeHandler typeHandler = parameterMapping.getTypeHandler();
					if (typeHandler == null) {
						throw new ExecutorException("There was no TypeHandler found for parameter "+ propertyName + " of statement "+ mappedStatement.getId());
					}
					typeHandler.setParameter(ps, i + 1, value, parameterMapping.getJdbcType());
				}
			}
		}
	}

	public void setDialect(String dialect) {
		if (dialect == null || "".equals(dialect)) {
			try {
				throw new PropertyException("dialect property is not found!");
			} catch (PropertyException e) {
				log.error("",e);
			}
		}
		this.dialect = dialect;
	}

	public void setPageSqlId(String pageSqlId) {
		if (pageSqlId == null || "".equals(pageSqlId)) {
			try {
				throw new PropertyException("pageSqlId property is not found!");
			} catch (PropertyException e) {
				log.error("",e);
			}
		}
		this.pageSqlId = pageSqlId;
	}
	
	

}
