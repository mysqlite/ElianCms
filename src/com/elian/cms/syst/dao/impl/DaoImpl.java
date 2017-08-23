package com.elian.cms.syst.dao.impl;

import java.io.Serializable;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.annotation.Resource;

import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import com.elian.cms.syst.dao.Dao;
import com.elian.cms.syst.model.Pagination;
import com.elian.cms.syst.util.DaoUtils;
import com.elian.cms.syst.util.HibernateUtils;
import com.elian.cms.syst.util.StringUtils;
import com.elian.cms.syst.util.ValidateUtils;

/**
 * Dao基类，实现了数据的CRUD
 * 
 * @author Joe
 * 
 * @param <T>
 * @param <ID>
 */
@SuppressWarnings({"unchecked" })
public class DaoImpl<T, ID extends Serializable> implements Dao<T, ID> {
	protected Class<?> entityClass;
	protected SessionFactory sessionFactory;

	protected DaoImpl() {
		entityClass = DaoUtils.getSuperClassType(getClass());
	}
	
	/**
	 * 注入Spring管理的SessionFactory
	 */
	@Resource
	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}
	
	/**
	 * 获取当前线程Session
	 * @return
	 */
	protected Session getSession() {
		return HibernateUtils.getThreadLocalSession();
	}

	/**
	 * 如果实体未持久化过则新建实体，否则更新实体
	 * 
	 * @param T
	 *            t
	 */
	public void save(T t) {
		getSession().save(t);
	}

	/**
	 * 更新实体
	 * 
	 * @param T
	 *            t
	 */
	public void update(T t) {
		getSession().update(t);
	}

	/**
	 * 删除实体
	 * 
	 * @param T
	 *            t
	 */
	public void delete(T t) {
		getSession().delete(t);
	}

	/**
	 * 获取实体By主键
	 */
	public T get(ID pk) {
		return (T) getSession().get(entityClass, (Serializable) pk);
	}

	/**
	 * 通过HQL语句和参数值查询实体
	 * 
	 * @param String
	 *            hql
	 * @param Object
	 *            ... values
	 * @return List<T>
	 */
	public List<T> findByHql(String hql, boolean cacheAble, Object... values) {
		return createHqlQuery(hql, cacheAble, values).list();
	}
	
	/**
	 *  删除或者更新
	 * @param hql  语句
	 * @param values  条件参数
	 */
	public void deleteOrUpdate(String hql, Object... values){
		createHqlQuery(hql, false, values).executeUpdate();
	}

	
	/**
	 * 通过SQL语句和参数值查询唯一实体
	 */
	public T findByHqlFirst(String hql, boolean cacheAble, Object... values) {
		return (T) createHqlQuery(hql, cacheAble, values).uniqueResult();
	}

	/**
	 * 创建Hql查询语句
	 */
	private Query createHqlQuery(String hql, boolean cacheAble, Object... values) {
		Query query = getSession().createQuery(hql);
		query.setCacheable(cacheAble);
		if (values != null) {
			for (int i = 0; i < values.length; i++) {
				query.setParameter(i, values[i]);
			}
		}
		return query;
	}

	/**
	 * 通过HQL语句和参数值查询实体(分页)
	 * 
	 * @param String
	 *            hql
	 * @param Object
	 *            ... values
	 * @return List<T>
	 */
	public List<T> pageByHql(String hql, boolean cacheAble, Pagination p,
			Object... values) {
		//添加搜索条件
		hql = addSearchParams(hql, p);
		
		//查询总记录数,hql语句一定要写别名
		long rowCount = (Long) createHqlQuery(queryCountByHql(hql), cacheAble,
				values).uniqueResult();
		if (rowCount <= 0)
			return null;
		p.setRowCount(rowCount);
		
		//查询当前页记录
		Query query = getSession().createQuery(hql);
		query.setCacheable(cacheAble);
		if (values != null) {
			for (int i = 0; i < values.length; i++) {
				query.setParameter(i, values[i]);
			}
		}
		query.setFirstResult(p.getFirstResult());
		query.setMaxResults(p.getRowSize());
		p.setList(query.list());
		return p.getList();
	}

	/**
	 * 添加搜索条件
	 */
	private String addSearchParams(String hql, Pagination p) {
		String conditionContent = StringUtils.isBlank(p.getConverterContent()) ? p
				.getConditionContent()
				: p.getConverterContent();
		if (StringUtils.isNotBlank(conditionContent)) {
			// 验证过滤非法字符
			conditionContent = ValidateUtils.isSql(conditionContent);
			if (StringUtils.isNotBlank(conditionContent)
					&& StringUtils.isNotBlank(p.getConditionName())) {
				String[] sql = hql.split("order\\s*by[\\w|\\W|\\s|\\S]*")[0]
						.split("ORDER\\s*BY[\\w|\\W|\\s|\\S]*");
				StringBuilder sbHql = new StringBuilder();
				sbHql.append(sql[0]);
				if (sql[0].toLowerCase().indexOf("where") > 0)
					sbHql.append(" and ");
				else
					sbHql.append(" where ");
				// 拼接搜索条件，去掉两边空格，中间有空格使用 or
				String[] conditionContents = conditionContent.trim().split(
						"[\\t \\n]+");
				if (conditionContents.length == 1) {
					if (StringUtils.isNotBlank(p.getAlias())) {
						sbHql.append(p.getAlias()).append(".");
					}
					sbHql.append(p.getConditionName()).append(" like '%")
							.append(conditionContents[0]).append("%'");
				}
				else {
					sbHql.append("(");
					for (int i = 0, length = conditionContents.length; i < length; i++) {
						if (i != 0)
							sbHql.append(" or ");
						if (StringUtils.isNotBlank(p.getAlias())) {
							sbHql.append(p.getAlias()).append(".");
						}
						sbHql.append(p.getConditionName()).append(" like '%")
								.append(conditionContents[i]).append("%'");
					}
					sbHql.append(")");
				}
				// 拼接order by语句
				if (sql.length == 2 && StringUtils.isNotBlank(sql[1])) {
					sbHql.append(" order by ").append(sql[1]);
				}
				return sbHql.toString();
			}
		}
		return hql;
	}

	/**
	 * 把HQL语句转换成计数HQL语句，用于分页查询时统计总记录数
	 * 
	 * @param String
	 *            hql
	 * @return String
	 */
	private String queryCountByHql(String hql) {
		hql = Pattern.compile(" fetch ", Pattern.CASE_INSENSITIVE).matcher(hql)
				.replaceAll(" ");
		Pattern p = Pattern.compile("order\\s*by[\\w|\\W|\\s|\\S]*",
				Pattern.CASE_INSENSITIVE);
		Matcher m = p.matcher(hql);
		StringBuffer sb = new StringBuffer();
		while (m.find()) {
			m.appendReplacement(sb, "");
		}
		m.appendTail(sb);

		String lowerHQL = sb.toString().toLowerCase();
		int selectPos = lowerHQL.indexOf("select");
		int fromPos = lowerHQL.indexOf("from");
		if (selectPos < 0 || selectPos > fromPos) {
			Class clazz = DaoUtils.getSuperClassType(getClass());
			int length = clazz.getSimpleName().length() + 1;
			int entityIndex = sb.indexOf(clazz.getSimpleName() + " ");
			if (entityIndex <= 0) {
				length = clazz.getName().length() + 1;
				entityIndex = sb.indexOf(clazz.getName() + " ");
			}
			int index = entityIndex + length;
			String tmp = sb.substring(index).trim();
			String alian = null;
			if (tmp.indexOf(" ") < 0) {
				alian = tmp;
			}
			else {
				alian = tmp.substring(0, tmp.indexOf(" "));
			}
			sb.insert(0, "SELECT COUNT(" + alian + ") ");
		}
		else {
			String tmp = sb.substring(selectPos + 7).trim();
			int distinctPos = lowerHQL.indexOf("distinct");
			String alian = null;
			if (distinctPos < 0 || distinctPos > fromPos) {
				alian = tmp.substring(0, tmp.indexOf(" "));
			}
			else {
				tmp = tmp.substring(9).trim();
				int index = tmp.indexOf(" ");
				alian = "DISTINCT " + tmp.substring(0, index);
			}
			sb.delete(0, fromPos);
			sb.insert(0, "SELECT COUNT(" + alian + ") ");
		}
		return sb.toString();
	}
	
	/**
	 * 通过SQL语句和参数值查询实体
	 * 
	 * @param String
	 *            sql
	 * @param Object
	 *            ... values
	 * @return List<T>
	 */
	public List<T> findByRecursionSql(String sql, boolean cacheAble,
			String function, Object... values) {
		// 为递归查询添加前缀方法
		sql = (function == null ? "" : function).concat(" ").concat(sql);
		return createSqlQuery(sql, cacheAble, values).list();
	}

	/**
	 * 通过SQL语句和参数值查询实体
	 * 
	 * @param String
	 *            sql
	 * @param Object
	 *            ... values
	 * @return List<T>
	 */
	public List<T> findBySql(String sql, boolean cacheAble, Object... values) {
		return findByRecursionSql(sql, cacheAble, null, values);
	}
	
	/**
	 * 通过SQL语句和参数值查询唯一实体
	 */
	public T findBySqlFirst(String sql, boolean cacheAble, Object... values) {
		return (T) createSqlQuery(sql, cacheAble, values).uniqueResult();
	}
	
	/**
	 * 创建Sql查询语句
	 */
	private SQLQuery createSqlQuery(String sql, boolean cacheAble,
			Object... values) {
		SQLQuery query = getSqlQuery(sql, cacheAble, values);
		query.addEntity(entityClass);
		return query;
	}

	private SQLQuery getSqlQuery(String sql, boolean cacheAble, Object... values) {
		SQLQuery query = getSession().createSQLQuery(sql.toString());
		int length;
		if (values != null && (length = values.length) > 0)
			for (int i = 0; i < length; i++) {
				query.setParameter(i, values[i]);
			}
		query.setCacheable(cacheAble);
		return query;
	}

	/**
	 * 通过SQL语句和参数值查询实体(分页)
	 * 
	 * @param String
	 *            sql
	 * @param Object
	 *            ... values
	 * @return List<T>
	 */
	public List<T> pageBySql(String sql, boolean cacheAble, Pagination p,
			Object... values) {
		return pageByRecursionSql(sql, cacheAble, p, null, values);
	}
	
	/**
	 * 通过SQL语句和参数值查询实体(分页)
	 * 
	 * @param String
	 *            sql
	 * @param Object
	 *            ... values
	 * @return List<T>
	 */
	public List<T> pageByRecursionSql(String sql, boolean cacheAble,
			Pagination p, String function, Object... values) {
		//添加搜索条件
		sql = addSearchParams(sql, p);
		
		// 为递归查询添加前缀方法
		String functionStr = (function == null ? "" : function).concat(" ");
		String countSql = (functionStr).concat(queryCountBySql(sql));
		
		// 查询总记录数
		long rowCount = Long.valueOf(getSqlQuery(countSql, false,
				values).uniqueResult().toString());
		if (rowCount <= 0)
			return null;
		p.setRowCount(rowCount);
		
		// 为递归查询添加前缀方法
		String querySql = (functionStr).concat(sql.toString());
		// 查询当前页记录
		SQLQuery query = getSession().createSQLQuery(querySql);
		query.setCacheable(cacheAble);
		int length;
		if (values != null && (length = values.length) > 0)
			for (int i = 0; i < length; i++) {
				query.setParameter(i, values[i]);
			}
		query.setFirstResult(p.getFirstResult());
		query.setMaxResults(p.getRowSize());
		query.addEntity(entityClass);
		p.setList(query.list());
		return p.getList();
	}

	/**
	 * 把SQL语句转换成计数SQL语句，用于分页查询时统计总记录数
	 * 
	 * @param String
	 *            sql
	 * @return String
	 */
	private String queryCountBySql(String sql) {
		Pattern p = Pattern.compile("order\\s*by[\\w|\\W|\\s|\\S]*",
				Pattern.CASE_INSENSITIVE);
		Matcher m = p.matcher(sql);
		StringBuffer sb = new StringBuffer();
		while (m.find()) {
			m.appendReplacement(sb, "");
		}
		m.appendTail(sb);
		String lowerSQL = sb.toString().toLowerCase();
		int fromPos = lowerSQL.indexOf("from");
		int distinctPos = lowerSQL.indexOf("distinct");
		if (distinctPos < 0 || distinctPos > fromPos) {
			sb.delete(0, fromPos);
			sb.insert(0, "SELECT COUNT(1) ");
		}
		return sb.toString();
	}
}
