package com.elian.cms.front.action;

public class ListModel {
	private String contentId;
	private String title;
	private String path;
	private String createTime;
	private boolean last;
	private Object entity;

	public ListModel() {
	}

	public ListModel(String contentId, String title, String path, String createTime,Object entity) {
		this.contentId = contentId;
		this.title = title;
		this.path = path;
		this.entity = entity;
		this.createTime=createTime;
	}

	public ListModel(String contentId, String title, String path,String createTime) {
		this.contentId = contentId;
		this.title = title;
		this.path = path;
		this.createTime=createTime;
	}
	
	public ListModel(String contentId, String title, String path) {
		this.contentId = contentId;
		this.title = title;
		this.path = path;
	}
	
	public ListModel(String contentId, String title, String path,boolean last) {
		this.contentId = contentId;
		this.title = title;
		this.path = path;
		this.last=last;
	}

	public String getContentId() {
		return contentId;
	}

	public void setContentId(String contentId) {
		this.contentId = contentId;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public String getCreateTime() {
		return createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}

	public boolean isLast() {
		return last;
	}

	public void setLast(boolean last) {
		this.last = last;
	}

	public Object getEntity() {
		return entity;
	}

	public void setEntity(Object entity) {
		this.entity = entity;
	}
}
