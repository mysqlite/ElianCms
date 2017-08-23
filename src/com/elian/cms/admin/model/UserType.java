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
 * 用户类型
 * 
 * @author Gechuanyi
 * @2012-07-16
 */
@javax.persistence.Entity
@Table(name = "T_USER_TYPE")
public class UserType implements PersistentLogInterface ,EagerLoading{
	private static final long serialVersionUID = 1894941983324476757L;
	/* 主键 */
    private Integer id;
    /* 版本 */
    private Integer version;
    /* 类型名称 */
    private String typeName;
    /* 类型说明 */
    private String typeDesc;
    /* 类型排序 */
    private Integer typeSort;
    /* 父节点 */
    private Integer parentId;
    /* 是否启用 */
    private boolean disable;

	@Id
	@Column(name = "USER_TYPE_ID")
	// @SequenceGenerator(name = "userTypeGenerator", sequenceName =
	// "S_T_USER_TYPE", allocationSize = 1)
	// @GeneratedValue(generator = "userTypeGenerator", strategy =
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

    @Column(name = "TYPE_NAME")
    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    @Column(name = "TYPE_DESC")
    public String getTypeDesc() {
        return typeDesc;
    }

    public void setTypeDesc(String typeDesc) {
        this.typeDesc = typeDesc;
    }

    @Column(name = "TYPE_SORT")
    public Integer getTypeSort() {
        return typeSort;
    }

    public void setTypeSort(Integer typeSort) {
        this.typeSort = typeSort;
    }

    @Column(name = "PARENT_ID")
    public Integer getParentId() {
        return parentId;
    }

    public void setParentId(Integer parentId) {
        this.parentId = parentId;
    }

    @Column(name = "IS_DISABLE")
    public boolean isDisable() {
        return disable;
    }

    public void setDisable(boolean disable) {
        this.disable = disable;
    }

    public String toString() {
        return this.getClass().getName() + "[id=" + id + "]" + ",[version=" + version + "]";
    }

    @Transient
	public void getLazyObject() {
	}
    
    @Transient
    public String[] getSysLog() {
    	return new String[] { "用户类型", "typeName=" + typeName };
    }
}
