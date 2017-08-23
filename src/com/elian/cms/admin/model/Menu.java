package com.elian.cms.admin.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.persistence.Version;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import com.elian.cms.syst.model.PersistentLogInterface;

/**
 * 菜单
 * 
 * @author Gechuanyi
 *@20120725
 */

@javax.persistence.Entity
@org.hibernate.annotations.Entity(dynamicUpdate = true, dynamicInsert = true)
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@Table(name = "T_MENU")
public class Menu implements PersistentLogInterface {
	private static final long serialVersionUID = 5250152159531698837L;
	private Integer id;
	/** 菜单id */
	private String menuName;
	/** 菜单名称 */
	private String menuDesc;
	/** 菜单说明 */
	private Integer parentId;
	/** 菜单父ID */
	private Integer depth;
	/** 深度 */
	private String menuUrl;
	/** 链接地址 */
	private Date createTime;
	/** 创建时间 */
	private Integer menuSort;
	/** 菜单排序 */
	private boolean disable;
	// private boolean isDisable;

	/** 是否显示 */
	private Integer version;

	/** 版本号 */

	@Id
	@Column(name = "MENU_ID")
	// @SequenceGenerator(name = "menuGenerator", sequenceName = "S_T_MENU",
	// allocationSize = 1)
	// @GeneratedValue(generator = "menuGenerator", strategy =
	// GenerationType.SEQUENCE)
	@GeneratedValue( strategy = GenerationType.AUTO)
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	@Column(name = "MENU_NAME")
	public String getMenuName() {
		return menuName;
	}

	public void setMenuName(String menuName) {
		this.menuName = menuName;
	}

	@Column(name = "MENU_DESC")
	public String getMenuDesc() {
		return menuDesc;
	}

	public void setMenuDesc(String menuDesc) {
		this.menuDesc = menuDesc;
	}

	@Column(name = "PARENT_ID")
	public Integer getParentId() {
		return parentId;
	}

	public void setParentId(Integer parentId) {
		this.parentId = parentId;
	}

	public Integer getDepth() {
		return depth;
	}

	public void setDepth(Integer depth) {
		this.depth = depth;
	}

	@Column(name = "MENU_URL")
	public String getMenuUrl() {
		return menuUrl;
	}

	public void setMenuUrl(String menuUrl) {
		this.menuUrl = menuUrl;
	}

	@Column(name = "CREATE_TIME")
	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	@Column(name = "MENU_SORT")
	public Integer getMenuSort() {
		return menuSort;
	}

	public void setMenuSort(Integer menuSort) {
		this.menuSort = menuSort;
	}

	@Column(name = "IS_DISABLE")
	public boolean isDisable() {
		return disable;
	}

	public void setDisable(boolean disable) {
		this.disable = disable;
	}

	@Version
	public Integer getVersion() {
		return version;
	}

	public void setVersion(Integer version) {
		this.version = version;
	}

	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null || !(o instanceof User)) {
			return false;
		}
		return id == ((User) o).getId();
	}

	public int hashCode() {
		return id;
	}

	public String toString() {
		return this.getClass().getName() + "[id=" + id + "]" + ",[version="
				+ version + "]";
	}
	
	@Transient
	public String[] getSysLog() {
		return new String[] { "菜单", "menuName=" + menuName };
	}
}
