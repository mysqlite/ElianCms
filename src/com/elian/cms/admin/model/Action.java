package com.elian.cms.admin.model;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.persistence.Version;

import com.elian.cms.syst.model.PersistentLogInterface;
import com.elian.cms.syst.model.Tree;

/**
 * @author Gechuanyi
 * @creat Time 20120725
 */
@javax.persistence.Entity
@Table(name = "T_ACTION")
public class Action extends Tree implements PersistentLogInterface {
	private static final long serialVersionUID = 1441045345939202527L;
	private Integer id;
	private Integer version;
	/** 权限名称 */
	private String actionName;
	/** 权限描述 */
	private String actionDesc;
	/** 权限Url */
	private String actionUrl;
	/** 排序 */
	private int actionSort;
	/** 是否可用 */
	private boolean isDisable;
	/** 父Id */
	private Integer parentId;
	/** 树的深度,都是从1开始，默认为1 */
	private int depth = Integer.valueOf(1);

	// private Set<RoleAction> roleActionSet;// 角色权限映射

	@Id
	@Column(name = "ACTION_ID")
	// @SequenceGenerator(name = "actionGenerator", sequenceName = "S_T_ACTION",
	// allocationSize = 1)
	// @GeneratedValue(strategy = GenerationType.SEQUENCE, generator =
	// "actionGenerator")
	@GeneratedValue(strategy = GenerationType.AUTO)
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	@Column(name = "ACTION_NAME")
	public String getActionName() {
		return this.actionName;
	}

	public void setActionName(String actionName) {
		this.actionName = actionName;
	}

	@Column(name = "ACTION_DESC")
	public String getActionDesc() {
		return this.actionDesc;
	}

	public void setActionDesc(String actionDesc) {
		this.actionDesc = actionDesc;
	}

	@Column(name = "ACTION_URL")
	public String getActionUrl() {
		return this.actionUrl;
	}

	public void setActionUrl(String actionUrl) {
		this.actionUrl = actionUrl;
	}

	@Column(name = "ACTION_SORT")
	public int getActionSort() {
		return this.actionSort;
	}

	public void setActionSort(int actionSort) {
		this.actionSort = actionSort;
	}

	@Column(name = "IS_DISABLE")
	public boolean isDisable() {
		return this.isDisable;
	}

	public void setDisable(boolean isDisable) {
		this.isDisable = isDisable;
	}

	@Version
	public Integer getVersion() {
		return this.version;
	}

	public void setVersion(Integer version) {
		this.version = version;
	}

	@Column(name = "PARENT_ID")
	public Integer getParentId() {
		return parentId;
	}

	public void setParentId(Integer parentId) {
		this.parentId = parentId;
	}

	public int getDepth() {
		return depth;
	}

	public void setDepth(int depth) {
		this.depth = depth;
	}

	// @OneToMany(mappedBy = "action", fetch = FetchType.EAGER)
	// @Cascade(value = { CascadeType.DELETE })
	// public Set<RoleAction> getRoleActionSet() {
	// return roleActionSet;
	// }

	// public void setRoleActionSet(Set<RoleAction> roleActionSet) {
	// this.roleActionSet = roleActionSet;
	// }

	public boolean equals(Object o) {
		if (o == this)
			return true;
		if (!(o instanceof Action))
			return false;
		return this.id.equals(((Action) o).id);
	}
	@Transient
	public String[] getSysLog() {
		return new String[] { "权限", "actionName=" + actionName };
	}
}