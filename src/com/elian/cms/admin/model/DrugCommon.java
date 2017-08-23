package com.elian.cms.admin.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.elian.cms.syst.model.BaseContent;
import com.elian.cms.syst.model.PersistentLogInterface;
import com.elian.cms.syst.model.SeoInterface;

/**
 * 药品库
 * 
 * @createTime 2013-02-06 09:00
 * @author Gechuanyi
 */
@Entity
@Table(name = "DrugCommon")
public class DrugCommon extends BaseContent implements SeoInterface,
PersistentLogInterface {
	private static final long serialVersionUID = 7438293335309815158L;
	/** ID */
	private Integer id;
	/**组织id*/
	private Integer companyId;
	/**药品名称*/
	private String pdtName;
	/**药品图片*/
	private String pdtImg;
	/**别名*/
	private String alias;
	/**药品中文名称*/
	private String merchandiseName;
	/**药品英文名称*/
	private String merchandiseEN;
	/**药物种类*/
	private String drugType;
	/**详细信息类型*/
	private String detailType;
	/**生产厂家*/
	private String manufacturer;
	/**生产地址*/
	private String manufacturerAddr;
	/**规格*/
	private String specification;
	/**单位*/
	private String unit;
	/**功能主治*/
	private String marjorFunc;
	/**药房*/
	private String pharmacy;
	/**形态*/
	private String shape;
	/**到期时间*/
	private String expireDate;
	/**成分*/
	private String packing;
	/**备注*/
	private String remark;
	/**批准文号*/
	private String approvalNumber;
	/**访问次数*/
	private Integer visits;

	@Id
	@Column(name = "dcId")
	@GeneratedValue(strategy = GenerationType.AUTO)
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getCompanyId() {
		return companyId;
	}

	public void setCompanyId(Integer companyId) {
		this.companyId = companyId;
	}

	public String getPdtName() {
		return pdtName;
	}

	public void setPdtName(String pdtName) {
		this.pdtName = pdtName;
	}

	public String getPdtImg() {
		return pdtImg;
	}

	public void setPdtImg(String pdtImg) {
		this.pdtImg = pdtImg;
	}

	public String getAlias() {
		return alias;
	}

	public void setAlias(String alias) {
		this.alias = alias;
	}

	public String getMerchandiseName() {
		return merchandiseName;
	}

	public void setMerchandiseName(String merchandiseName) {
		this.merchandiseName = merchandiseName;
	}

	public String getMerchandiseEN() {
		return merchandiseEN;
	}

	public void setMerchandiseEN(String merchandiseEN) {
		this.merchandiseEN = merchandiseEN;
	}

	public String getDrugType() {
		return drugType;
	}

	public void setDrugType(String drugType) {
		this.drugType = drugType;
	}

	public String getDetailType() {
		return detailType;
	}

	public void setDetailType(String detailType) {
		this.detailType = detailType;
	}

	public String getManufacturer() {
		return manufacturer;
	}

	public void setManufacturer(String manufacturer) {
		this.manufacturer = manufacturer;
	}

	public String getManufacturerAddr() {
		return manufacturerAddr;
	}

	public void setManufacturerAddr(String manufacturerAddr) {
		this.manufacturerAddr = manufacturerAddr;
	}

	public String getSpecification() {
		return specification;
	}

	public void setSpecification(String specification) {
		this.specification = specification;
	}

	public String getUnit() {
		return unit;
	}

	public void setUnit(String unit) {
		this.unit = unit;
	}

	public String getMarjorFunc() {
		return marjorFunc;
	}

	public void setMarjorFunc(String marjorFunc) {
		this.marjorFunc = marjorFunc;
	}

	public String getPharmacy() {
		return pharmacy;
	}

	public void setPharmacy(String pharmacy) {
		this.pharmacy = pharmacy;
	}

	public String getShape() {
		return shape;
	}

	public void setShape(String shape) {
		this.shape = shape;
	}

	public String getExpireDate() {
		return expireDate;
	}

	public void setExpireDate(String expireDate) {
		this.expireDate = expireDate;
	}

	public String getPacking() {
		return packing;
	}

	public void setPacking(String packing) {
		this.packing = packing;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getApprovalNumber() {
		return approvalNumber;
	}

	public void setApprovalNumber(String approvalNumber) {
		this.approvalNumber = approvalNumber;
	}

	public Integer getVisits() {
		return visits;
	}

	public void setVisits(Integer visits) {
		this.visits = visits;
	}

	@Transient
	public String getTitle() {

		return null;
	}

	@Transient
	public String getSeoDescription() {
		return null;
	}

	@Transient
	public String getSeoKeywords() {
		return null;
	}

	@Transient
	public String getSeoTitle() {
		return null;
	}

	@Transient
	public String[] getSysLog() {
		return new String[] { "药品库", "pdtName=" + pdtName };
	}	
}
