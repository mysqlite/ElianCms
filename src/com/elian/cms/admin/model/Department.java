package com.elian.cms.admin.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.persistence.Version;

import org.apache.commons.beanutils.BeanUtils;

import com.elian.cms.syst.model.BaseContent;
import com.elian.cms.syst.model.EagerLoading;
import com.elian.cms.syst.model.PersistentLogInterface;
import com.elian.cms.syst.model.SeoInterface;
import com.elian.cms.syst.util.ElianCodes;
import com.elian.cms.syst.util.GetPinyin;

/**
 * 科室
 * 
 * @author Joe
 */
@Entity
@Table(name = "T_HOSP_DEPARTMENT")
public class Department extends BaseContent implements SeoInterface,
PersistentLogInterface,EagerLoading{
	
	private static final long serialVersionUID = -6134751365155071926L;
	/** ID */
	private Integer id;
	/** 版本 */
	private Integer version;
	/** 医院 */
	private Hospital hospital;
	/** 科室性质 */
	private Integer natureId;
	/** 科室类型 */
	private Integer typeId;
	/** 科室全称 */
	private String deptName;
	/** 电话 */
	private String phone;
	/** 传真 */
	private String fax;
	/** 电子邮箱 */
	private String email;
	/** 科室地址 */
	private String address;
	/** 创建时间 */
	private Date createTime;
	/** 科室图片 */
	private String deptImg;
	/** 科室简要介绍 */
	private String shortDesc;
	/** 科室详细介绍 */
	private String description;
	/** 排序 */
	private int deptSort;
	/** 是否可用 */
	private boolean isDisable;
	/** 是否开通挂号 */
	private boolean isReg;
	/** 开通挂号时间 */
	private Date regTime;
	/** 是否开通随访 */
	private boolean followUp;
	/** 随访开通时间 */
	private Date followUpTime;
	
	@Id
	@Column(name = "depa_id")
	// @SequenceGenerator(name = "deptGenerator", sequenceName =
	// "S_T_HOSP_DEPA", allocationSize = 1)
	// @GeneratedValue(generator = "deptGenerator", strategy =
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

	@Column(name = "depa_nature")
	public Integer getNatureId() {
		return natureId;
	}

	public void setNatureId(Integer natureId) {
		this.natureId = natureId;
	}

	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name = "hosp_ID")
	public Hospital getHospital() {
		return hospital;
	}

	public void setHospital(Hospital hospital) {
		this.hospital = hospital;
	}

	@Column(name = "depa_type")
	public Integer getTypeId() {
		return typeId;
	}

	public void setTypeId(Integer typeId) {
		this.typeId = typeId;
	}

	@Column(name = "depa_name")
	public String getDeptName() {
		return deptName;
	}

	public void setDeptName(String deptName) {
		this.deptName = deptName;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getFax() {
		return fax;
	}

	public void setFax(String fax) {
		this.fax = fax;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	@Column(name = "depa_img")
	public String getDeptImg() {
		return deptImg;
	}

	public void setDeptImg(String deptImg) {
		this.deptImg = deptImg;
	}

	@Column(name = "Short_Desc")
	public String getShortDesc() {
		return shortDesc;
	}

	public void setShortDesc(String shortDesc) {
		this.shortDesc = shortDesc;
	}

	@Column(name = "descriptor")
	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@Column(name = "Depa_Sort")
	public int getDeptSort() {
		return deptSort;
	}

	public void setDeptSort(int deptSort) {
		this.deptSort = deptSort;
	}

	@Column(name = "is_reg")
	public boolean isReg() {
		return isReg;
	}

	public void setReg(boolean isReg) {
		this.isReg = isReg;
	}

	@Column(name = "reg_time")
	public Date getRegTime() {
		return regTime;
	}

	public void setRegTime(Date regTime) {
		this.regTime = regTime;
	}

	@Column(name = "is_follow_up")
	public boolean isFollowUp() {
		return followUp;
	}

	public void setFollowUp(boolean followUp) {
		this.followUp = followUp;
	}

	@Column(name = "follow_Up_Time")
	public Date getFollowUpTime() {
		return followUpTime;
	}

	public void setFollowUpTime(Date followUpTime) {
		this.followUpTime = followUpTime;
	}

	public boolean equals(Object o) {
		if (o == this)
			return true;
		if (!(o instanceof Department))
			return false;
		return this.id.equals(((Department) o).id);
	}

	public String toString() {
		return this.getClass().getName() + "[id=" + id + "]" + ",[version="
				+ version + "]";
	}

	@Transient
	public String getSeoDescription() {
		return shortDesc;
	}

	@Transient
	public String getSeoKeywords() {
		return deptName;
	}

	@Transient
	public String getSeoTitle() {
		return deptName;
	}

	@Transient
	public String getTitle() {
		return deptName;
	}

	/**扩展字段 区域拼音首字母*/
	@Transient
	public String getFirstPinying() {
		return GetPinyin.getPinYinHeadChar(getDeptName());
	}

	/**扩展字段 区域全拼*/
	@Transient
	public String getAllPinying() {
		return GetPinyin.getPinYin(getDeptName());
	}

	@Transient
	public void getLazyObject() {
		this.getHospital().getLazyObject();
		Hospital h=new Hospital();
		try {
			BeanUtils.copyProperties(h, getHospital());
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		this.setHospital(h);
	}
	
	public void createDefaultData(Hospital hospital) {
		this.setDisable(true);
		this.setDeptName("默认科室");
		this.setDeptImg(ElianCodes.DEPARTMENT_DEFAULT_IMG);
		this.setCreateTime(new Date());
		this.setDeptSort(99);
		this.setReg(false);
		this.setFollowUp(false);
		this.setHospital(hospital);
	}
	@Transient
	public String[] getSysLog() {
		return new String[] { "科室", "deptName=" + deptName };
	}
}
