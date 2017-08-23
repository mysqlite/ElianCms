package com.elian.cms.syst.listener.Impl;

import com.elian.cms.syst.model.ImgInterface;

/**
 * 特殊内容的处理方式
 * 
 * @author Joe
 * 
 */
public interface ImgListener<T extends ImgInterface> {

	/**
	 * 修改之后执行
	 * 
	 * @param T
	 */
	public void get(T t);
	
	public void save(T t);
}
