package com.elian.cms.syst.model;

import java.util.List;
import java.util.Map;

/**
 * 分页包含列表list属性基类。
 * 
 * @author Joe
 * 
 */
public class Pagination<T> extends PaginationSupport implements
		java.io.Serializable {
	private static final long serialVersionUID = 7002501955628078021L;
	/** 当前页的数据 */
	private List<T> list;

	public Pagination() {
	}

	public Pagination(String name) {
		super(name);
	}
	
	public Pagination(Map<String, String> conditionMap) {
		super(conditionMap);
	}

	public Pagination(String name, Map<String, String> conditionMap) {
		super(name, conditionMap);
	}

	

	/**
	 * 获得分页内容
	 * 
	 * @return
	 */
	public List<T> getList() {
		return list;
	}

	/**
	 * 设置分页内容
	 * 
	 * @param list
	 */
	public void setList(List<T> list) {
		this.list = list;
	}
}
