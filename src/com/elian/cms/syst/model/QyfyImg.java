package com.elian.cms.syst.model;

import java.util.List;

public class QyfyImg {
	private Integer entityId;
	private String entityName;
	private List<QyfyImgs> path;

	public QyfyImg(Integer entityId, String entityName, String newsUrl,
			String sourUrl) {
		this.entityId = entityId;
		this.entityName = entityName;
	}

	public QyfyImg() {
	}

	public Integer getEntityId() {
		return entityId;
	}

	public void setEntityId(Integer entityId) {
		this.entityId = entityId;
	}

	public String getEntityName() {
		return entityName;
	}

	public void setEntityName(String entityName) {
		this.entityName = entityName;
	}

	public List<QyfyImgs> getPath() {
		return path;
	}

	public void setPath(List<QyfyImgs> path) {
		this.path = path;
	}
	
}
