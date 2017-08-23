package com.elian.cms.admin.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
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
import com.elian.cms.syst.util.ApplicationUtils;
import com.elian.cms.syst.util.DoubleUtlis;
import com.elian.cms.syst.util.FilePathUtils;

/**
 * 企业器械表
 * 
 * @author Gechuanyi
 * @version [0.1,2013-04-17]
 */
@SuppressWarnings("unused")
@javax.persistence.Entity
@Table(name = "INSTRUMENT")
public class Instrument extends BaseContent implements Serializable,
		PersistentLogInterface,EagerLoading,SeoInterface {
	private static final long serialVersionUID = 4517834282449005675L;
	/** 器械ID */
	private Integer id;
	/** 企业 */
	private Company company;
	/** 中文名 */
	private String cnName;
	/** 英文名 */
	private String enName;
	/**别名*/
	private String alias;
	/** 批准文号 */
	private String approvalNumber;
	/** 描述 */
	private String description;
	/**详细描述 前端显示*/
	private String frontDesc;
	/* 规格* */
	private String specifications;
	/** 用法 */
	private String usage;
	/** 用途 */
	private String instrUse;
	/** 批准时间 */
	private Date approvalTime;
	/** 到期时间 */
	private Date effectiveTime;
	/** 包装 */
	private String packaging;
	/** 产品价格 */
	private Integer price;
	private double pricePany;
	/** 折扣价格 */
	private Integer discountedPrices;
	private double discountedPricePany;
	/** 是否使用折扣价 */
	private boolean isDiscountedPrice;
	/** 产品图片 */
	private String instrImg;
	/**产品图片 前端显示*/
	private String frontImg;
	/** 是否启用 */
	private boolean isDisable;
	/** 创建时间 */
	private Date createTime;
	/** 创建人 */
	private User user;
	/** 摘要 */
	private String summary;
	/** 版本号 */
	private Integer version;
	/** 排序 */
	private Integer sort;
	/** 产品类型 */
	private DeptType type;
	/** 所属科种 */
	private DeptType departmentType;
	/**请求路径*/
	private String staticPath;
	/***/
	private String frontsImg;
	/***/
	private String[] frontImgs;
	@Id
	@Column(name = "instr_id")
	@GeneratedValue(strategy = GenerationType.AUTO)
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "comp_id")
	public Company getCompany() {
		return company;
	}

	public void setCompany(Company company) {
		this.company = company;
	}

	@Column(name = "cn_Name")
	public String getCnName() {
		return cnName;
	}

	public void setCnName(String cnName) {
		this.cnName = cnName;
	}

	@Column(name = "en_Name")
	public String getEnName() {
		return enName;
	}

	public void setEnName(String enName) {
		this.enName = enName;
	}

	
	@Column(name = "alias")
	public String getAlias() {
		return alias;
	}

	public void setAlias(String alias) {
		this.alias = alias;
	}

	@Column(name = "approval_number")
	public String getApprovalNumber() {
		return approvalNumber;
	}

	public void setApprovalNumber(String approvalNumber) {
		this.approvalNumber = approvalNumber;
	}

	@Column(name = "description")
	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@Column(name = "specifications")
	public String getSpecifications() {
		return specifications;
	}

	public void setSpecifications(String specifications) {
		this.specifications = specifications;
	}

	@Column(name = "usage")
	public String getUsage() {
		return usage;
	}

	public void setUsage(String usage) {
		this.usage = usage;
	}

	@Column(name = "instr_use")
	public String getInstrUse() {
		return instrUse;
	}

	public void setInstrUse(String instrUse) {
		this.instrUse = instrUse;
	}

	@Column(name = "approval_time")
	public Date getApprovalTime() {
		return approvalTime;
	}

	public void setApprovalTime(Date approvalTime) {
		this.approvalTime = approvalTime;
	}

	@Column(name = "effective_time")
	public Date getEffectiveTime() {
		return effectiveTime;
	}

	public void setEffectiveTime(Date effectiveTime) {
		this.effectiveTime = effectiveTime;
	}

	@Column(name = "packaging")
	public String getPackaging() {
		return packaging;
	}

	public void setPackaging(String packaging) {
		this.packaging = packaging;
	}

	@Column(name = "price")
	public Integer getPrice() {
		return price;
	}

	public void setPrice(Integer price) {
		this.price = price;
		this.pricePany=DoubleUtlis.divInteger(price, 100, 2);
	}

	@Column(name = "discounted_price")
	public Integer getDiscountedPrices() {
		return discountedPrices;
	}

	public void setDiscountedPrices(Integer discountedPrices) {
		this.discountedPrices =discountedPrices;
		this.discountedPricePany=DoubleUtlis.divInteger(discountedPrices, 100, 2);
	}

	@Column(name = "is_discounted_price")
public boolean isDiscountedPrice() {
		return isDiscountedPrice;
	}

	public void setDiscountedPrice(boolean isDiscountedPrice) {
		this.isDiscountedPrice = isDiscountedPrice;
	}
	
	
	@Column(name = "instr_img")
	public String getInstrImg() {
		return instrImg;
	}

	

	public void setInstrImg(String instrImg) {
		this.instrImg = instrImg;
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

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "create_user")
	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	@Column(name = "Summary")
	public String getSummary() {
		return summary;
	}

	public void setSummary(String summary) {
		this.summary = summary;
	}

	@Version
	public Integer getVersion() {
		return version;
	}

	public void setVersion(Integer version) {
		this.version = version;
	}

	@Column(name = "sort")
	public Integer getSort() {
		return sort;
	}

	public void setSort(Integer sort) {
		this.sort = sort;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "instr_type")
	public DeptType getType() {
		return type;
	}

	public void setType(DeptType type) {
		this.type = type;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "department_type")
	public DeptType getDepartmentType() {
		return departmentType;
	}

	public void setDepartmentType(DeptType departmentType) {
		this.departmentType = departmentType;
	}
	
	
	@Transient
	public String getFrontDesc() {
		return FilePathUtils.setEditorOutPath(description);
	}

	public void setFrontDesc(String frontDesc) {
		this.frontDesc = frontDesc;
	}

	@Transient
	public String getFrontImg() {
		if(FilePathUtils.getJsonPath(instrImg)!=null)
			return FilePathUtils.getJsonPath(instrImg)[0];
		else 
			return null;
		//return instrImg;
	}

	public void setFrontImg(String frontImg) {
		this.frontImg = frontImg;
	}

	@Transient
	public double getPricePany() {
		return DoubleUtlis.div(price, 100, 2);
	}

	public void setPricePany(double pricePany) {
		this.pricePany = pricePany;
		if(!(price!=null&&price>0&&(int)(pricePany*100)!=price))
		   this.price=(int)(pricePany*100);
	}

	@Transient
	public double getDiscountedPricePany() {
		return DoubleUtlis.div(discountedPrices, 100, 2);
	}

	public void setDiscountedPricePany(double discountedPricePany) {
		this.discountedPricePany = discountedPricePany;
		if(!(discountedPrices!=null&&discountedPrices>0&&(int)(discountedPricePany*100)!=discountedPrices))
		   this.discountedPrices=(int)(discountedPricePany*100);
	}

	public boolean equals(Object o) {
		if (o == this)
			return true;
		if (!(o instanceof Role))
			return false;
		return this.id.equals(((Instrument) o).id);
	}

	public String toString() {
		return this.getClass().getName() + "[id=" + id + "]" + ",[version="
				+ version + "]";
	}
	
	@Transient
	public void getLazyObject() {
		this.getType().getLazyObject();
		DeptType types=new DeptType();
		DeptType deparTypes=new DeptType();
		User user=new User();
		Company com=new Company();
		try {
			BeanUtils.copyProperties(types, getType());
			BeanUtils.copyProperties(deparTypes, getDepartmentType());
			BeanUtils.copyProperties(user, getUser());
			BeanUtils.copyProperties(com, getCompany());
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		this.setType(types);
		this.setDepartmentType(deparTypes);
		this.setUser(user);
		this.setCompany(com);
	}

	@Transient
	public String[] getSysLog() {
		return new String[] { "企业器械", "Instrument.cnName=" + cnName };
	}

	@Transient
	public String getTitle() {
		return cnName;
	}

	@Transient
	public String getSeoDescription() {
		
		return summary;
	}

	@Transient
	public String getSeoKeywords() {
		return cnName+enName;
	}

	@Transient
	public String getSeoTitle() {
		return cnName;
	}
	
	@Transient
	public String getFrontsImg() {
		if(FilePathUtils.getJsonPath(instrImg)!=null)
			return FilePathUtils.getJsonPath(instrImg)[0];
		else 
			return null;
	}

	public void setFrontsImg(String frontsImg) {
		this.frontsImg = frontsImg;
	}
	
	@Transient
	public String[] getFrontImgs() {
		if(FilePathUtils.getJsonPath(instrImg)!=null)
			return FilePathUtils.getJsonPath(instrImg);
		else 
			return null;
	}

	public void setFrontImgs(String[] frontImgs) {
		this.frontImgs = frontImgs;
	}

	public void createDefaultData(Company company,DeptType type,DeptType departmentType) {
		this.setCnName("默认器械数据");
		this.setEnName("default");
		this.setCompany(company);
		this.setType(type);
		this.setDepartmentType(departmentType);
		this.setDisable(true);
		this.setPrice(0);
		this.setDiscountedPrice(false);
		this.setDescription("默认器械介绍内容");
		this.setCreateTime(new Date());
		this.setApprovalTime(new Date());
		this.setEffectiveTime(new Date());
		this.setSort(9999);
		this.setUser(ApplicationUtils.getUser());
		this.setInstrImg("[{\"path\":\"/design/hosp/comm/no_pic.jpg/\"}]");
	}

	@Transient
	public String getStaticPath() {
		return staticPath;
	}

	public void setStaticPath(String staticPath) {
		this.staticPath = staticPath;
	}
}