package com.elian.cms.syst.dao.impl;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.ParameterMetaData;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.sql.Types;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.sql.DataSource;

import org.hibernate.SQLQuery;
import org.hibernate.SessionFactory;
import org.hibernate.Session;
import org.hibernate.transform.ResultTransformer;
import org.hibernate.transform.Transformers;
import org.springframework.jdbc.datasource.DataSourceUtils;
import org.springframework.orm.hibernate3.SessionFactoryUtils;
import org.springframework.stereotype.Component;

import com.elian.cms.syst.dao.JdbcDao;
import com.elian.cms.syst.util.HibernateUtils;

/**
 * JDBC访问结果集的DAO实现
 * 
 * @author Joe
 * 
 */
@Component("jdbcDao")
@SuppressWarnings("unchecked")
public class JdbcDaoImpl implements JdbcDao, Serializable {
	private static final long serialVersionUID = 3985309538600750401L;
	private DataSource dataSource;
	private SessionFactory sessionFactory;

	public List<Object[]> findSqlQuery(String sql, Object... values) {
		return getSqlquery(sql, null, values).list();
	}

	public List<Map<String, Object>> findSqlMapQuery(String sql,
			Object... values) {
		return getSqlquery(sql, Transformers.ALIAS_TO_ENTITY_MAP, values)
				.list();
	}

	public SQLQuery getSqlquery(String sql, ResultTransformer transformer,
			Object... values) {
		SQLQuery query = getSession().createSQLQuery(sql.toString());
		if (transformer != null)
			query.setResultTransformer(transformer);
		int length;
		if (values != null && (length = values.length) > 0)
			for (int i = 0; i < length; i++) {
				query.setParameter(i, values[i]);
			}
		return query;
	}

	/**
	 * 通过参数值执行SQL语句更新数据
	 * 
	 * @param String
	 *            sql
	 * @param Object
	 *            [] values
	 * @return int
	 */
	public int executeUpdate(String sql, Object... values) {
		Connection conn = null;
		PreparedStatement state = null;
		try {
			conn = getConnection();
			state = conn.prepareStatement(sql);
			if (values != null) {
				for (int i = 0, length = values.length; i < length; i++) {
					if (values[i] instanceof Date) {
						state.setTimestamp(i + 1, new Timestamp(
								((Date) values[i]).getTime()));
					}
					else if (values[i] != null) {
						state.setObject(i + 1, values[i]);
					}
					else {
						state.setNull(i + 1, getParameterType(state, i + 1));
					}
				}
			}
			return state.executeUpdate();
		}
		catch (SQLException ex) {
			throw new IllegalArgumentException("Failed to execute update", ex);
		}
		finally {
			if (state != null) {
				try {
					state.close();
				}
				catch (Exception ex) {
				}
				state = null;
			}
			if (conn != null)
				DataSourceUtils.releaseConnection(conn, dataSource);
		}
	}

	/**
	 * 通过参数值分批执行SQL语句更新数据
	 * 
	 * @param String
	 *            sql
	 * @param List
	 *            <Object[]> values
	 * @return int[]
	 */
	public int[] executeUpdate(String sql, List<Object[]> values) {
		Connection conn = null;
		PreparedStatement state = null;
		try {
			conn = getConnection();
			state = conn.prepareStatement(sql);
			if (values != null) {
				Iterator<Object[]> iterator = values.iterator();
				while (iterator.hasNext()) {
					Object[] value = iterator.next();
					if (value != null) {
						for (int i = 0, length = value.length; i < length; i++) {
							if (value[i] instanceof Date) {
								state.setTimestamp(i + 1, new Timestamp(
										((Date) value[i]).getTime()));
							}
							else if (value[i] != null) {
								state.setObject(i + 1, value[i]);
							}
							else {
								state.setNull(i + 1, getParameterType(state,
										i + 1));
							}
						}
						state.addBatch();
					}
				}
			}
			return state.executeBatch();
		}
		catch (SQLException ex) {
			throw new IllegalArgumentException("Failed to execute update", ex);
		}
		finally {
			if (state != null) {
				try {
					state.close();
				}
				catch (Exception ex) {
				}
				state = null;
			}
			if (conn != null)
				DataSourceUtils.releaseConnection(conn, dataSource);
		}
	}

	/**
	 * 获取参数类型，部分数据库驱动程序不支持。如果驱动程序不支持则返回默认类型Types.VARCHAR
	 * 
	 * @param PreparedStatement
	 *            prstat
	 * @param int param
	 * @return
	 */
	private int getParameterType(PreparedStatement prstat, int param) {
		try {
			ParameterMetaData pmd = prstat.getParameterMetaData();
			return pmd.getParameterType(param);
		}
		catch (Exception e) {
			return Types.VARCHAR;
		}
	}

	/**
	 * 获取Connection，代替原来Hibernate中的session.connnection()方法
	 * 
	 * @return Connection
	 */
	private Connection getConnection() {
		try {
			return SessionFactoryUtils.getDataSource(sessionFactory)
					.getConnection();
		}
		catch (SQLException ex) {
			throw new IllegalArgumentException("Failed to get connection", ex);
		}
	}
	
	@Resource
	public void setDataSource(DataSource dataSource) {
		this.dataSource = dataSource;
	}

	@Resource
	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	private Session getSession() {
		return HibernateUtils.getThreadLocalSession();
	}
}