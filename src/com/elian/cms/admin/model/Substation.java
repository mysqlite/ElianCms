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
 * 分站实体类
 * 
 * @author CZH
 */
@javax.persistence.Entity
@Table(name = "T_SUBSTATION")
@org.hibernate.annotations.Entity(dynamicUpdate = true, dynamicInsert = true)
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
// @BatchSize(size=20)//根据分页界面显示的行数,配置批量操作
public class Substation implements PersistentLogInterface {
	private static final long serialVersionUID = 6443122776774463201L;
	private Integer id;
	/** 版本 */
	private Integer version;
	/** 分站区域ID */
	private Integer areaId;
	/** 分站名称 */
	private String subName;
	/** 分站简称 */
	private String shortName;
	/** 分站简介 */
	private String shortDesc;
	/** 分站详细介绍 */
	private String subDesc;
	/** 电子邮箱 */
	private String email;
	/** 分站联系电话 */
	private String phone;
	/** 分站地址 */
	private String address;
	/** 分站邮政编码 */
	private String postcode;
	/** 分站传真号码 */
	private String fax;
	/** 分站点击量 */
	private int hits;
	/** 分站创建时间 */
	private Date createTime;
	/** 是否启用 */
	private int status;
	/** 审核时间 */
	private Date auditTime;
	/** 审核人 */
	private String auditor;
	/**退回说明（用于站点的审核）*/
	private String remarks;

	@Id
	@Column(name = "sub_id")
	// @SequenceGenerator(name = "substationGenerator", sequenceName =
	// "S_T_SUBSTATION", allocationSize = 1)
	// @GeneratedValue(generator = "substationGenerator", strategy =
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

	@Column(name = "area_id",updatable=false)
	public Integer getAreaId() {
		return areaId;
	}

	public void setAreaId(Integer areaId) {
		this.areaId = areaId;
	}

	@Column(name = "sub_name")
	public String getSubName() {
		return subName;
	}

	public void setSubName(String subName) {
		this.subName = subName;
	}

	@Column(name = "short_name")
	public String getShortName() {
		return shortName;
	}

	public void setShortName(String shortName) {
		this.shortName = shortName;
	}

	@Column(name = "short_desc")
	public String getShortDesc() {
		return shortDesc;
	}

	public void setShortDesc(String shortDesc) {
		this.shortDesc = shortDesc;
	}

	@Column(name = "sub_desc")
	public String getSubDesc() {
		return subDesc;
	}

	public void setSubDesc(String subDesc) {
		this.subDesc = subDesc;
	}

	@Column(name = "email")
	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	@Column(name = "phone")
	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	@Column(name = "address")
	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	@Column(name = "postcode")
	public String getPostcode() {
		return postcode;
	}

	public void setPostcode(String postcode) {
		this.postcode = postcode;
	}

	@Column(name = "fax")
	public String getFax() {
		return fax;
	}

	public void setFax(String fax) {
		this.fax = fax;
	}

	@Column(name = "hits")
	public int getHits() {
		return hits;
	}

	public void setHits(int hits) {
		this.hits = hits;
	}

	@Column(name = "create_time")
	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	@Column(name = "status")
	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	@Column(name = "audit_time")
	public Date getAuditTime() {
		return auditTime;
	}

	public void setAuditTime(Date auditTime) {
		this.auditTime = auditTime;
	}

	@Column(name = "auditor")
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
		if (o == null || !(o instanceof Substation)) {
			return false;
		}
		return id.equals(((Substation) o).getId());
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
		return new String[] { "分站", "subName=" + subName };
	}
}
