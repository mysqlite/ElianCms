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

import com.elian.cms.syst.model.BaseContent;
import com.elian.cms.syst.model.EagerLoading;
import com.elian.cms.syst.model.PersistentLogInterface;

/**
 * 医院实体表
 * 
 * @author CZH
 */
@javax.persistence.Entity
@org.hibernate.annotations.Entity(dynamicUpdate = true, dynamicInsert = true)
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@Table(name = "T_HOSPITAL")
// @BatchSize(size=20)//根据分页界面显示的行数,配置批量操作
public class Hospital  extends BaseContent implements PersistentLogInterface ,EagerLoading{
	private static final long serialVersionUID = 1100924295806107805L;
	
	private Integer id;
	/** 版本 NOTNULL*/
	private Integer version;
	/** 医院名称  NOTNULL*/
	private String hospName;
	/** 医院简称 */
	private String shortName;
	/** 医院职业许可证 */
	private String permitImg;
	/** 医院性质  NOTNULL*/
	private Integer nature;
	/** 医院类型  NOTNULL*/
	private Integer hospType;
	/** 医院等级  NOTNULL*/
	private Integer rank;
	/** 医院简介 */
	private String shortDesc;
	/** 医院详细介绍 */
	private String hospDesc;
	/** 医院电话 */
	private String phone;
	/** 医院急救电话 */
	private String emergencyPhone;
	/** 医院地址  NOTNULL*/
	private String address;
	/** 医院网站 */
	private String siteUrl;
	/** 医院电子邮件 */
	private String email;
	/** 医院传真 */
	private String fax;
	/** 医院邮政编码 */
	private String postcode;
	/** 医院区域  NOTNULL*/
	private Integer areaId;
	/** 医院LOGO */
	private String logoImg;
	/** 医院形象图片 */
	private String hospImg;
	/** 医院BUS线路 */
	private String busLine;
	/** 医院地图 */
	private String mapImg;
	/** 医院点击量  NOTNULL*/
	private Integer hits;
	/** 医院创建时间  NOTNULL*/
	private Date createTime;
	/** 是否医保医院  NOTNULL*/
	private boolean health;
	/** 是否启用  NOTNULL*/
	private int status;
	/** 是否挂号  NOTNULL*/
	private boolean reg;
	/** 开启挂号时间 */
	private Date regTime;
	/** 是否随访  NOTNULL*/
	private boolean followup;
	/** 开启随访时间  NOTNULL*/
	private Date followupTime;
	/**审核时间*/
	private Date auditTime;
	/**审核人*/
	private String auditor;
	/**退回说明（用于站点的审核）*/
	private String remarks;

	@Id
	@Column(name = "hosp_id")
	// @SequenceGenerator(name = "hospitalGenerator", sequenceName =
	// "S_T_HOSPITAL", allocationSize = 1)
	// @GeneratedValue(generator = "hospitalGenerator", strategy =
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

	@Column(name = "HOSP_NAME")
	public String getHospName() {
		return hospName;
	}

	public void setHospName(String hospName) {
		this.hospName = hospName;
	}

	@Column(name = "SHORT_NAME")
	public String getShortName() {
		return shortName;
	}

	public void setShortName(String shortName) {
		this.shortName = shortName;
	}
	@Column(name = "PERMIT_IMG")
	public String getPermitImg() {
		return permitImg;
	}

	public void setPermitImg(String permitImg) {
		this.permitImg = permitImg;
	}

	@Column(name = "hosp_nature")
	public Integer getNature() {
		return nature;
	}

	public void setNature(Integer nature) {
		this.nature = nature;
	}

	@Column(name = "HOSP_TYPE")
	public Integer getHospType() {
		return hospType;
	}

	public void setHospType(Integer hospType) {
		this.hospType = hospType;
	}

	@Column(name = "hosp_rank")
	public Integer getRank() {
		return rank;
	}

	public void setRank(Integer rank) {
		this.rank = rank;
	}

	@Column(name = "SHORT_DESC")
	public String getShortDesc() {
		return shortDesc;
	}

	public void setShortDesc(String shortDesc) {
		this.shortDesc = shortDesc;
	}

	@Column(name = "HOSP_DESC")
	public String getHospDesc() {
		return hospDesc;
	}

	public void setHospDesc(String hospDesc) {
		this.hospDesc = hospDesc;
	}

	@Column(name = "PHONE")
	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	@Column(name = "emergency_num")
	public String getEmergencyPhone() {
		return emergencyPhone;
	}

	public void setEmergencyPhone(String emergencyPhone) {
		this.emergencyPhone = emergencyPhone;
	}

	@Column(name = "ADDRESS")
	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	@Column(name = "SITE_URL")
	public String getSiteUrl() {
		return siteUrl;
	}

	public void setSiteUrl(String siteUrl) {
		this.siteUrl = siteUrl;
	}

	@Column(name = "EMAIL")
	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	@Column(name = "FAX")
	public String getFax() {
		return fax;
	}

	public void setFax(String fax) {
		this.fax = fax;
	}

	@Column(name = "POSTCODE")
	public String getPostcode() {
		return postcode;
	}

	public void setPostcode(String postcode) {
		this.postcode = postcode;
	}

	@Column(name = "AREA_ID")
	public Integer getAreaId() {
		return areaId;
	}

	public void setAreaId(Integer areaId) {
		this.areaId = areaId;
	}

	@Column(name = "LOGO_IMG")
	public String getLogoImg() {
		return logoImg;
	}

	public void setLogoImg(String logoImg) {
		this.logoImg = logoImg;
	}

	@Column(name = "HOSP_IMG")
	public String getHospImg() {
		return hospImg;
	}

	public void setHospImg(String hospImg) {
		this.hospImg = hospImg;
	}

	@Column(name = "BUS_LINE")
	public String getBusLine() {
		return busLine;
	}

	public void setBusLine(String busLine) {
		this.busLine = busLine;
	}

	@Column(name = "MAP_IMG")
	public String getMapImg() {
		return mapImg;
	}

	public void setMapImg(String mapImg) {
		this.mapImg = mapImg;
	}

	@Column(name = "HITS")
	public Integer getHits() {
		return hits;
	}

	public void setHits(Integer hits) {
		this.hits = hits;
	}

	@Column(name = "CREATE_TIME")
	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	@Column(name = "IS_HEALTH")
	public boolean isHealth() {
		return health;
	}

	public void setHealth(boolean health) {
		this.health = health;
	}

	@Column(name = "status")
	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	@Column(name = "IS_REG")
	public boolean isReg() {
		return reg;
	}


	public void setReg(boolean reg) {
		this.reg = reg;
	}

	@Column(name = "REG_TIME")
	public Date getRegTime() {
		return regTime;
	}

	public void setRegTime(Date regTime) {
		this.regTime = regTime;
	}

	@Column(name = "IS_FOLLOW_UP")
	public boolean isFollowup() {
		return followup;
	}

	public void setFollowup(boolean followup) {
		this.followup = followup;
	}

	@Column(name = "FOLLOW_UP_TIME")
	public Date getFollowupTime() {
		return followupTime;
	}

	public void setFollowupTime(Date followupTime) {
		this.followupTime = followupTime;
	}
	
	@Column(name = "AUDIT_TIME")
	public Date getAuditTime() {
		return auditTime;
	}

	public void setAuditTime(Date auditTime) {
		this.auditTime = auditTime;
	}

	@Column(name = "AUDITOR")
	public String getAuditor() {
		return auditor;
	}

	public void setAuditor(String auditor) {
		this.auditor = auditor;
	}

	@Column(name = "remarks")
	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
	
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null || !(o instanceof Hospital)) {
			return false;
		}
		return id == ((Hospital) o).getId();
	}

	public int hashCode() {
		return id;
	}

	public String toString() {
		return this.getClass().getName() + "[id=" + id + "]" + ",[version="
				+ version + "]";
	}	
	
	@Transient
	public String getTitle() {		
		return hospName;
	}

	@Transient
	public void getLazyObject() {
	}
	
	@Transient
	public String[] getSysLog() {
		return new String[] { "医院", "hospName=" + hospName };
	}
}
