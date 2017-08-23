package com.elian.cms.syst.dao;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * JDBC访问结果集DAO层
 * 
 * @author Joe
 * 
 */
public interface JdbcDao extends Serializable {

	/**
	 * 比如查询SELECT USER_NAME, USER_CODE FROM USER，那么查询返回结果 Object[] value =
	 * list.get(0)取第一条数据; value[0]为user_name的值, value[1]为user_code的值
	 * 
	 * @param String
	 *            hql
	 * @param Object
	 *            ... values
	 * @return List<Object[]>
	 */
	public List<Object[]> findSqlQuery(String sql, Object... values);

	/**
	 * 比如查询SELECT USER_NAME, USER_CODE FROM USER，那么查询返回结果 Map<String, Object>
	 * value = list.get(0)取第一条数据; value.get("user_name")为user_name的值,
	 * value.get("user_code")为user_code的值
	 * 
	 * @param String
	 *            hql
	 * @param Object
	 *            ... values
	 * @return List<Map<String, Object>>
	 */
	public List<Map<String, Object>> findSqlMapQuery(String sql,
			Object... values);

	/**
	 * 通过参数值执行SQL语句更新数据
	 * 
	 * @param String
	 *            sql
	 * @param Object
	 *            [] values
	 * @return int
	 */
	public int executeUpdate(String sql, Object... values);

	/**
	 * 通过参数值分批执行SQL语句更新数据
	 * 
	 * @param String
	 *            sql
	 * @param List
	 *            <Object[]> values
	 * @return int[]
	 */
	public int[] executeUpdate(String sql, List<Object[]> values);
}