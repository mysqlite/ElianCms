package com.elian.cms.admin.data;

public class HospitalContentData {
	/** 内容Id */
	private String contentId;
	/** 内容Id */
	private String entityId;
	/** 标题 */
	private String title;
	/** 创建人 */
	private String creater;
	/** 创建日期 */
	private String createTime;
	/** 内容 */
	private String content;
	/**实体内容*/
	private Object entity;
	/**实体类型*/
	private String entityType;
	/**tabUrl*/
	private String tableUrl;
	
	public String getContentId() {
		return contentId;
	}

	public void setContentId(String contentId) {
		this.contentId = contentId;
	}

	public String getEntityId() {
		return entityId;
	}

	public void setEntityId(String entityId) {
		this.entityId = entityId;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getCreater() {
		return creater;
	}

	public void setCreater(String creater) {
		this.creater = creater;
	}

	public String getCreateTime() {
		return createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Object getEntity() {
		return entity;
	}

	public void setEntity(Object entity) {
		this.entity = entity;
	}

	public String getEntityType() {
		return entityType;
	}

	public void setEntityType(String entityType) {
		this.entityType = entityType;
	}

	public String getTableUrl() {
		return tableUrl;
	}

	public void setTableUrl(String tableUrl) {
		this.tableUrl = tableUrl;
	}
}