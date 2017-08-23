package com.elian.cms.admin.data;

public class HospitalChannelData {
	private String channelName;// 栏目名称
	private String type;// 类型 list,parent,single
	private String tableUrl;// 列表请求路径
	private String contentId;// 内容Id
	private Integer parentId;// 父Id
	private String pathUrl;// 位置URL
	private Integer id;

	public String getChannelName() {
		return channelName;
	}

	public void setChannelName(String channelName) {
		this.channelName = channelName;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getTableUrl() {
		return tableUrl;
	}

	public void setTableUrl(String tableUrl) {
		this.tableUrl = tableUrl;
	}

	public String getContentId() {
		return contentId;
	}

	public void setContentId(String contentId) {
		this.contentId = contentId;
	}

	public Integer getParentId() {
		return parentId;
	}

	public void setParentId(Integer parentId) {
		this.parentId = parentId;
	}
	
	public String getPathUrl() {
		return pathUrl;
	}

	public void setPathUrl(String pathUrl) {
		this.pathUrl = pathUrl;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}
}