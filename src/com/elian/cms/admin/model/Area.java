package com.elian.cms.admin.model;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.persistence.Version;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import com.elian.cms.syst.model.PersistentLogInterface;

/**
 * 区域
 * 
 * @author Gechuanyi
 * 
 */
@javax.persistence.Entity
@org.hibernate.annotations.Entity(dynamicUpdate = true, dynamicInsert = true)
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@Table(name = "T_AREA")
public class Area implements PersistentLogInterface {
	private static final long serialVersionUID = -3855671238253931704L;
	/** 区域编码 */
    private Integer areaCode;
    /** 区域名称 */
    private String areaName;
    /** 父编码 */
    private Integer parentCode;
    /** 注释 */
    private String note;
    /** 排序 */
    private Integer areaSort;
    /** 状态 */
    private boolean disable;
    /** 版本 */
    private Integer version;
    /**扩展字段 区域拼音首字母*/
    private String firstPinying;
    /**扩展字段 区域全拼*/
    private String allPinying;

    @Id
    @Column(name = "AREA_CODE")
    public Integer getAreaCode() {
        return areaCode;
    }

    public void setAreaCode(Integer areaCode) {
        this.areaCode = areaCode;
    }

    @Column(name = "AREA_NAME")
    public String getAreaName() {
        return areaName;
    }

    public void setAreaName(String areaName) {
        this.areaName = areaName;
    }

    @Column(name = "PARENT_CODE")
    public Integer getParentCode() {
        return parentCode;
    }

    public void setParentCode(Integer parentCode) {
        this.parentCode = parentCode;
    }

    @Column(name = "NOTE")
    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    @Column(name = "AREA_SORT")
    public Integer getAreaSort() {
        return areaSort;
    }

    public void setAreaSort(Integer areaSort) {
        this.areaSort = areaSort;
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

    public String toString() {
        return this.getClass().getName() + "[id=" + areaCode + "]" + ",[version=" + version + "]";
    }
    
    @Transient
	public Integer getId() {
		return areaCode;
	}

    @Transient
	public String getFirstPinying() {
		return firstPinying;
	}

	public void setFirstPinying(String firstPinying) {
		this.firstPinying = firstPinying;
	}

	 @Transient
	public String getAllPinying() {
		return allPinying;
	}

	public void setAllPinying(String allPinying) {
		this.allPinying = allPinying;
	}
	@Transient
	public String[] getSysLog() {
		return new String[] { "区域", "areaName=" + areaName };
	}
}
