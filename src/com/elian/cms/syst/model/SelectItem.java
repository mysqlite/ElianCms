package com.elian.cms.syst.model;

/**
 * @author Gechuanyi
 * 
 * 用于下拉列表List，便于显示键值对数据。
 */
public class SelectItem {
	private Object key;
	private String value;
	private String description;

	public SelectItem() {
	}

	public SelectItem(Object key, String value) {
		this.key = key;
		this.value = value;
	}

	public SelectItem(int status,String value , String description) {
		this.key =status ;
		this.value =value;
		this.description = description;
	}

	public Object getKey() {
		return key;
	}

	public void setKey(Object key) {
		this.key = key;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
}
