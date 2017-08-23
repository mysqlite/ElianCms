package com.elian.cms.syst.listener.Impl;

import java.util.Collection;

/**
 * 特殊内容的处理方式
 * 
 * @author Joe
 * 
 */
public interface SpecialContentListener<T> {

	/**
	 * 修改之后执行
	 * 
	 * @param T
	 */
	public void afterUpdate(T t);
	public void afterUpdate(T t,boolean publish);

	/**
	 * 删除之后执行
	 * 
	 * @param T
	 */
	public void afterDelete(T t, Collection<Integer> contentIdList);
}
