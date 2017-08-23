package com.elian.cms.syst.model;

/**
 * 持久化对象日志
 * 
 * @author Joe
 */
public interface ImgInterface extends PersistentInterface {

	/**
	 * 返回一个length为2的数组，记录日志title和content 例如：new String{"角色", "roleName=医院超级管理员"}
	 */
	public String[] imgs();
	
	/**
	 * 返回一个length为2的数组，记录日志title和content 例如：new String{"角色", "roleName=医院超级管理员"}
	 */
	public String[] descImgs();
}