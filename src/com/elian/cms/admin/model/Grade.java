package com.elian.cms.admin.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.persistence.Version;

import com.elian.cms.syst.model.PersistentLogInterface;

/**
 * 站点等级实体类
 * 
 * @author CZH
 */
@javax.persistence.Entity
@Table(name = "T_SITE_GRADE")
// @BatchSize(size=20)//根据分页界面显示的行数,配置批量操作
public class Grade implements PersistentLogInterface {
	private static final long serialVersionUID = -8695494933584840433L;
	private Integer id;
	/** 版本 */
	private Integer version;
	/** 等级名称 */
	private String gradeName;
	/** 等级描述 */
	private String gradeDesc;
	/** 排序 */
	private Integer gradeSort;
	/** 创建时间 */
	private Date createTime;
	/** 是否启用 */
	private boolean isDisable;
	/**等级类型*/
	private String comType;
	/**是否默认*/
	private boolean isDefault;
	@Id
	@Column(name = "grade_id")
	// @SequenceGenerator(name = "gradeGenerator", sequenceName =
	// "S_T_SITE_GRADE", allocationSize = 1)
	// @GeneratedValue(generator = "gradeGenerator", strategy =
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

	@Column(name = "grade_name")
	public String getGradeName() {
		return gradeName;
	}

	public void setGradeName(String gradeName) {
		this.gradeName = gradeName;
	}

	@Column(name = "grade_desc")
	public String getGradeDesc() {
		return gradeDesc;
	}

	public void setGradeDesc(String gradeDesc) {
		this.gradeDesc = gradeDesc;
	}

	@Column(name = "grade_sort")
	public Integer getGradeSort() {
		return gradeSort;
	}

	public void setGradeSort(Integer gradeSort) {
		this.gradeSort = gradeSort;
	}

	@Column(name = "create_time")
	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	@Column(name = "is_disable")
	public boolean isDisable() {
		return isDisable;
	}

	public void setDisable(boolean isDisable) {
		this.isDisable = isDisable;
	}
	
	@Column(name = "com_type")
	public String getComType() {
		return comType;
	}

	public void setComType(String comType) {
		this.comType = comType;
	}

	@Column(name = "is_default")
	public boolean isDefault() {
		return isDefault;
	}

	public void setDefault(boolean isDefault) {
		this.isDefault = isDefault;
	}

	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null || !(o instanceof Grade)) {
			return false;
		}
		return id == ((Grade) o).getId();
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
		return new String[] { "站点等级", "gradeName=" + gradeName };
	}

}
