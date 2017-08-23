package com.elian.cms.admin.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.persistence.Version;

import com.elian.cms.syst.model.PersistentLogInterface;

/**
 * 内容模型
 * 
 * @author Joe
 */
@Entity
@Table(name = "T_CONTENT_MODEL")
public class ContentModel implements PersistentLogInterface {
	private static final long serialVersionUID = 7500648724914594879L;
	
	private Integer id;
	/** 版本 */
	private Integer version;
	/** 对象名称 */
	private String objectName;
	/** ACTION名称 */
	private String actionName;

	@Id
	@Column(name = "content_model_id")
	// @SequenceGenerator(name = "contentModelGenerator", sequenceName =
	// "S_T_CONTENT_MODEL", allocationSize = 1)
	// @GeneratedValue(generator = "contentModelGenerator", strategy =
	// GenerationType.SEQUENCE)
	@GeneratedValue( strategy = GenerationType.AUTO)
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	@Version
	public Integer getVersion() {
		return version;
	}

	public void setVersion(Integer version) {
		this.version = version;
	}

	@Column(name = "object_name")
	public String getObjectName() {
		return objectName;
	}

	public void setObjectName(String objectName) {
		this.objectName = objectName;
	}

	@Column(name = "action_name")
	public String getActionName() {
		return actionName;
	}

	public void setActionName(String actionName) {
		this.actionName = actionName;
	}

	public String toString() {
		return this.getClass().getName() + "[id=" + id + "]" + ",[version="
				+ version + "]";
	}
	@Transient
	public String[] getSysLog() {
		return new String[] { "内容模型", "objectName=" + objectName };
	}
}
