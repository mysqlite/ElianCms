package com.elian.cms.syst.service;

import java.util.Collection;
import java.util.List;

/**
 * Service接口
 * 
 * @author Joe
 * 
 * @param <DAO>
 * @param <T>
 * @param <ID>
 */
public interface Service<DAO, T, ID> {

	/**
	 * 设置数据访问对象
	 * 
	 * @param DAO
	 *            dao
	 */
	public void setDao(DAO dao);

	/**
	 * 如果实体未持久化过则新建实体，否则更新实体
	 * 
	 * @param T
	 *            t
	 */
	public void save(T t);

	/**
	 * 如果实体未持久化过则新建实体，否则更新实体
	 * 
	 * @param List
	 *            <T> ts
	 */
	public void save(Collection<T> ts);

	/**
	 * 更新实体
	 * 
	 * @param T
	 *            t
	 */
	public void update(T t);

	/**
	 * 更新实体
	 * 
	 * @param List
	 *            <T> ts
	 */
	public void update(Collection<T> ts);

	/**
	 * 删除实体
	 * 
	 * @param T
	 *            t
	 */
	public void delete(T t);

	/**
	 * 删除实体
	 * 
	 * @param List
	 *            <T> ts
	 */
	public void delete(Collection<T> ts);

	/**
	 * 通过主键加载实体
	 * 
	 * @param PK
	 *            pk
	 * @return T
	 */
	public T get(ID pk);

	/**
	 * 通过主键加载实体
	 * 
	 * @param List
	 *            <PK> pks
	 * @return List<T>
	 */
	public List<T> get(Collection<ID> pks);

	/**
	 * 通过主键删除实体
	 * 
	 * @param PK
	 *            pk
	 * @return T
	 */
	public T deleteById(ID pk);

	/**
	 * 通过主键删除实体
	 * 
	 * @param List
	 *            <PK> pks
	 * @return List<T>
	 */
	public List<T> deleteById(Collection<ID> pks);
}