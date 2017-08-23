package com.elian.cms.syst.dao;

/**
 * Dao接口
 * 
 * @author Joe
 * 
 */
public interface Dao<T, ID> {

	/**
	 * 如果实体未持久化过则新建实体，否则更新实体
	 * 
	 * @param T
	 *            t
	 */
	public void save(T t);

	/**
	 * 更新实体
	 * 
	 * @param T
	 *            t
	 */
	public void update(T t);

	/**
	 * 删除实体
	 * 
	 * @param T
	 *            t
	 */
	public void delete(T t);

	/**
	 * 通过主键加载实体
	 * 
	 * @param PK
	 *            pk
	 * @return T
	 */
	public T get(ID pk);

	/**
	 * 通过HQL语句和参数值查询实体
	 * 
	 * @param String
	 *            hql
	 * @param Object
	 *            ... values
	 * @return List<T>
	 */
	// public List<T> findByHql(String hql, boolean cacheAble, Object...
	// values);

	/**
	 * 通过SQL语句和参数值查询实体
	 * 
	 * @param sql
	 * @param clazz
	 * @param values
	 * @return List<T>
	 */
	// public List<T> findBySql(String sql, boolean cacheAble, Object...
	// values);
}