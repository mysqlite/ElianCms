package com.elian.cms.admin.model;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.persistence.Version;

import org.hibernate.annotations.BatchSize;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import com.elian.cms.syst.model.PersistentLogInterface;
import com.elian.cms.syst.model.SelectModel;

/**
 * 行业类型
 * 
 * @author keo
 */

@javax.persistence.Entity
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@BatchSize(size = 10)
// 根据分页界面显示的行数,配置级联查询时批量操作
@Table(name = "INDUSTRY_TYPE")
public class Industry extends SelectModel implements PersistentLogInterface {

	private static final long serialVersionUID = -7664627291628020722L;

	private Integer id;
	/** 行业描述 */
	private String industryDesc;
	/** 行业名称 */
	private String industryName;
	/** 是否可用 */
	private boolean isDisable;
	/** 版本 */
	private Integer version;

	@Id
	@Column(name = "industry_id")
	@GeneratedValue(strategy = GenerationType.AUTO)
	public Integer getId() {
		return id;
	}

	@Column(name = "is_disable")
	public boolean isDisable() {
		return isDisable;
	}

	public void setDisable(boolean isDisable) {
		this.isDisable = isDisable;
	}

	@Column(name = "industry_name")
	public String getIndustryName() {
		return industryName;
	}

	@Column(name = "industry_desc")
	public String getIndustryDesc() {
		return industryDesc;
	}

	@Version
	public Integer getVersion() {
		return version;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public void setIndustryDesc(String industryDesc) {
		this.industryDesc = industryDesc;
	}

	public void setIndustryName(String industryName) {
		this.industryName = industryName;
	}

	public void setVersion(Integer version) {
		this.version = version;
	}

	@Transient
	public String[] getSysLog() {
		// TODO Auto-generated method stub
		return new String[] { "行业", "industryName=" + industryName };
	}

	public boolean equals(Object o) {
		if (o == this)
			return true;
		if (!(o instanceof Industry))
			return false;
		return this.id.equals(((Industry) o).id);
	}

	public String toString() {
		return this.getClass().getName() + "[id=" + id + "]" + ",[version="
				+ version + "]";
	}
}
