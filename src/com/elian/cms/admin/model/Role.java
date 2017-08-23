package com.elian.cms.admin.model;

import java.util.Date;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.persistence.Version;

import org.hibernate.annotations.BatchSize;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;

import com.elian.cms.syst.model.PersistentLogInterface;
import com.elian.cms.syst.model.SelectModel;

/**
 * 角色
 * 
 * @author Joe
 */
@javax.persistence.Entity
// @org.hibernate.annotations.Entity(dynamicUpdate = true, dynamicInsert = true)
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
// 根据分页界面显示的行数,配置级联查询时批量操作
@BatchSize(size = 10)
@Table(name = "T_ROLE")
public class Role extends SelectModel implements PersistentLogInterface{
	private static final long serialVersionUID = -1505105331926760931L;
	private Integer id;
	/** 版本 */
	private Integer version;
	/** 角色名称 */
	private String roleName;
	/** 组织类型 */
	private String compType;
	/** 角色描述 */
	private String roleDesc;
	/** 角色排序 */
	private int roleSort;
	/** 是否可用 */
	private boolean isDisable;
	/** 创建时间 */
	private Date createTime;
	/** 是否默认 */
	private boolean isDefault;
	/** 站点ID */
	private Site site;

	private Set<RoleAction> roleActionSet;// 角色权限映射

	@Id
	@Column(name = "role_id")
	// @SequenceGenerator(name = "roleGenerator", sequenceName = "S_T_ROLE",
	// allocationSize = 1)
	// @GeneratedValue(generator = "roleGenerator", strategy = GenerationType.SEQUENCE)
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

	@Column(name = "role_name")
	public String getRoleName() {
		return roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

	@Column(name = "com_type")
	public String getCompType() {
		return compType;
	}

	public void setCompType(String compType) {
		this.compType = compType;
	}

	@Column(name = "role_desc")
	public String getRoleDesc() {
		return roleDesc;
	}

	public void setRoleDesc(String roleDesc) {
		this.roleDesc = roleDesc;
	}

	@Column(name = "role_sort")
	public int getRoleSort() {
		return roleSort;
	}

	public void setRoleSort(int roleSort) {
		this.roleSort = roleSort;
	}

	@Column(name = "is_disable")
	public boolean isDisable() {
		return isDisable;
	}

	public void setDisable(boolean isDisable) {
		this.isDisable = isDisable;
	}

	@Column(name = "create_time")
	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	@Column(name = "is_default")
	public boolean isDefault() {
		return isDefault;
	}

	public void setDefault(boolean isDefault) {
		this.isDefault = isDefault;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "SITE_ID")
	public Site getSite() {
		return site;
	}

	public void setSite(Site site) {
		this.site = site;
	}
	
	@OneToMany(mappedBy = "role", fetch = FetchType.LAZY)
	@Cascade(value = { CascadeType.DELETE })
	public Set<RoleAction> getRoleActionSet() {
		return roleActionSet;
	}

	public void setRoleActionSet(Set<RoleAction> roleActionSet) {
		this.roleActionSet = roleActionSet;
	}

	public boolean equals(Object o) {
		if (o == this)
			return true;
		if (!(o instanceof Role))
			return false;
		return this.id.equals(((Role) o).id);
	}

	public String toString() {
		return this.getClass().getName() + "[id=" + id + "]" + ",[version="
				+ version + "]";
	}

	@Transient
	public String[] getSysLog() {
		return new String[] { "角色", "roleName=" + roleName };
	}
}
