package com.elian.cms.syst.service.impl;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.elian.cms.syst.dao.Dao;
import com.elian.cms.syst.model.PersistentInterface;
import com.elian.cms.syst.service.Service;

/**
 * Service基类，实现了数据的CRUD
 * 
 * @author Joe
 * 
 * @param <DAO>
 * @param <T>
 * @param <ID>
 */
@SuppressWarnings({ "unchecked"})
public class ServiceImpl<DAO extends Dao, T extends PersistentInterface, ID extends Serializable>
		implements Service<DAO, T, ID> {
	/**
	 * 数据访问对象，提供子类使用
	 */
	protected DAO dao;

	public void setDao(DAO dao) {
		this.dao = dao;
	}

	/**
	 * 如果实体未持久化过则新建实体，否则更新实体
	 * 
	 * @param T
	 *            t
	 * 
	 */
	public void save(T t) {
		if (t.getId() == null)
			((Dao) dao).save(t);
		else
			((Dao) dao).update(t);
	}

	/**
	 * 如果实体未持久化过则新建实体，否则更新实体
	 * 
	 * @param List
	 *            <T> ts
	 */
	public void save(Collection<T> ts) {
		for (T t : ts) {
			save(t);
		}
	}

	/**
	 * 更新实体
	 * 
	 * @param T
	 *            t
	 */
	public void update(T t) {
		((Dao) dao).update(t);
	}

	/**
	 * 更新实体
	 * 
	 * @param List
	 *            <T> ts
	 */
	public void update(Collection<T> ts) {
		for (T t : ts) {
			update(t);
		}
	}

	/**
	 * 删除实体
	 * 
	 * @param T
	 *            t
	 */
	public void delete(T t) {
		((Dao) dao).delete(t);
	}

	/**
	 * 删除实体
	 * 
	 * @param List
	 *            <T> ts
	 */
	public void delete(Collection<T> ts) {
		for (T t : ts) {
			delete(t);
		}
	}

	/**
	 * 通过主键加载实体
	 * 
	 * @param PK
	 *            pk
	 * @return T
	 */
	public T get(ID pk) {
		return (T) ((Dao) dao).get(pk);
	}

	/**
	 * 通过主键加载实体
	 * 
	 * @param List
	 *            <PK> pks
	 * @return List<T>
	 */
	public List<T> get(Collection<ID> pks) {
		List<T> list = new ArrayList<T>(pks.size());
		for (ID pk : pks) {
			list.add(get(pk));
		}
		return list;
	}

	/**
	 * 通过主键删除实体
	 * 
	 * @param PK
	 *            pk
	 * @return T
	 */
	public T deleteById(ID pk) {
		T t = get(pk);
		delete(t);
		return t;
	}

	/**
	 * 通过主键删除实体
	 * 
	 * @param List
	 *            <PK> pks
	 * @return List<T>
	 */
	public List<T> deleteById(Collection<ID> pks) {
		List<T> ts = get(pks);
		delete(ts);
		return ts;
	}
}
