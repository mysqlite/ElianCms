package com.elian.cms.admin.model;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.persistence.Version;

import com.elian.cms.syst.model.EagerLoading;
import com.elian.cms.syst.model.PersistentLogInterface;

/**
 * 科室类型
 * 
 * author Joe
 */
@javax.persistence.Entity
@Table(name = "T_TYPE_PARENT")
public class DeptType implements PersistentLogInterface,EagerLoading {
	private static final long serialVersionUID = -843991572417776776L;
	/** 科室类型ID */
	private Integer id;
	/** 科室类型名称 */
	private String typeName;
	/** 类型描述 */
	private String typeDesc;
	/** 父级ID */
	private Integer parentId;
	/** 类型排序 */
	private Integer typeSort;
	/** 树级类型 */
	private String type;
	/** 是否启用 */
	private boolean disable;
	/** 版本 */
	private Integer version;

	@Id
	@Column(name = "TYPE_PARENT_ID")
	// @SequenceGenerator(name = "deptTypeGenerator", sequenceName =
	// "S_T_TYPE_PARENT", allocationSize = 1)
	// @GeneratedValue(generator = "deptTypeGenerator", strategy =
	// GenerationType.SEQUENCE)
	@GeneratedValue( strategy = GenerationType.AUTO)
	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	@Column(name = "parent_Id")
	public Integer getParentId() {
		return parentId;
	}

	public void setParentId(Integer parentId) {
		this.parentId = parentId;
	}

	@Column(name = "TYPE_PARENT_NAME")
	public String getTypeName() {
		return this.typeName;
	}

	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}

	@Column(name = "TYPE_PARENT_DESC")
	public String getTypeDesc() {
		return this.typeDesc;
	}

	public void setTypeDesc(String typeDesc) {
		this.typeDesc = typeDesc;
	}

	@Column(name = "TYPE_PARENT_SORT")
	public Integer getTypeSort() {
		return this.typeSort;
	}

	public void setTypeSort(Integer typeSort) {
		this.typeSort = typeSort;
	}
	
	@Column(name = "type_class")
	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	@Column(name = "IS_DISABLE")
	public Boolean getDisable() {
		return this.disable;
	}

	public void setDisable(Boolean disable) {
		this.disable = disable;
	}
	
	@Version
	public Integer getVersion() {
		return this.version;
	}

	public void setVersion(Integer version) {
		this.version = version;
	}
	
	@Transient
	public void getLazyObject() {
	}

	@Transient
	public String[] getSysLog() {
		return new String[] { "科室类型", "typeName=" + typeName };
	}
}