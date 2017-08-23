package com.elian.cms.syst.model;

import java.io.Serializable;

/**
 * 持久化对象接口
 * 
 * @author Joe
 */
public interface PersistentInterface extends Serializable {

	/**
	 * 返回主键
	 */
	public Integer getId();
}