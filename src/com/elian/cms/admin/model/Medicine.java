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
 * 企业药品表
 * 
 * @author Gechuanyi
 * @version [0.1,2013-04-17]
 */
@SuppressWarnings("unused")
@javax.persistence.Entity
@Table(name = "MEDICINE")
public class Medicine extends BaseContent implements Serializable,
		PersistentLogInterface,EagerLoading,SeoInterface {
	private static final long serialVersionUID = 8091845137789482778L;
	/** 主键 */
	private Integer id;
	/** 企业 */
	private Company company;
	/** 药品中文名 */
	private String cnName;
	/** 药品英文名 */
	private String enName;
	/** 批准文号 */
	private String approvalNumber;
	/** 规格 */
	private String specification;
	/** 主要成分 */
	private String composition;
	/** 功能主治 */
	private String attendingFunctions;
	/** 不良反应 */
	private String adverseReaction;
	/** 禁忌 */
	private String contraindication;
	/** 用法用量 */
	private String usageDosage;
	/** 贮藏方法 */
	private String storageMethod;
	/** 注意事项 */
	private String attentions;
	/** 作用类别 */
	private String effectCategory;
	/** 药物相互作用 */
	private String drugInteractions;
	/** 药理作用 */
	private String pharmacologicalEffects;
	/** 药品图片 */
	private String medicineImg;
	/** 别名 */
	private String alias;
	/** 价格 */
	private Integer price;
	private double pricePany;
	/** 折扣价格 */
	private Integer discountedPrices;
	private double discountedPricePany;
	/** 是否使用折扣价 */
	private boolean isDiscountedPrice;
	/** 是否启用 */
	private boolean isDisable;
	/** 创建时间 */
	private Date createTime;
	/** 创建人 */
	private User user;
	/** 排序 */
	private Integer sort;
	/** 药品类型 */
	private Type type;
	/**剂型*/
	private String dosage;
	/**药品单位*/
	private String medicineUnit;
	/**摘要*/
	private String summary;
	/**描述*/
	private String description;
	/**描述 前端显示*/
	private String frontDesc;
	/** 版本号 */
	private Integer version;
	/**请求路径*/
	private String staticPath;
	/***/
	private String frontsImg;
	/***/
	private String[] frontImgs;
	@Id
	@Column(name = "medicine_id")
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

	@Column(name = "approval_number")
	public String getApprovalNumber() {
		return approvalNumber;
	}

	public void setApprovalNumber(String approvalNumber) {
		this.approvalNumber = approvalNumber;
	}

	@Column(name = "specification")
	public String getSpecification() {
		return specification;
	}

	public void setSpecification(String specification) {
		this.specification = specification;
	}

	@Column(name = "composition")
	public String getComposition() {
		return composition;
	}

	public void setComposition(String composition) {
		this.composition = composition;
	}

	@Column(name = "attending_functions")
	public String getAttendingFunctions() {
		return attendingFunctions;
	}

	public void setAttendingFunctions(String attendingFunctions) {
		this.attendingFunctions = attendingFunctions;
	}

	@Column(name = "adverse_reaction")
	public String getAdverseReaction() {
		return adverseReaction;
	}

	public void setAdverseReaction(String adverseReaction) {
		this.adverseReaction = adverseReaction;
	}

	@Column(name = "contraindication")
	public String getContraindication() {
		return contraindication;
	}

	public void setContraindication(String contraindication) {
		this.contraindication = contraindication;
	}

	@Column(name = "usage_dosage")
	public String getUsageDosage() {
		return usageDosage;
	}

	public void setUsageDosage(String usageDosage) {
		this.usageDosage = usageDosage;
	}

	@Column(name = "storage_method")
	public String getStorageMethod() {
		return storageMethod;
	}

	public void setStorageMethod(String storageMethod) {
		this.storageMethod = storageMethod;
	}

	@Column(name = "attentions")
	public String getAttentions() {
		return attentions;
	}

	public void setAttentions(String attentions) {
		this.attentions = attentions;
	}

	@Column(name = "effect_category")
	public String getEffectCategory() {
		return effectCategory;
	}

	public void setEffectCategory(String effectCategory) {
		this.effectCategory = effectCategory;
	}

	@Column(name = "drug_interactions")
	public String getDrugInteractions() {
		return drugInteractions;
	}

	public void setDrugInteractions(String drugInteractions) {
		this.drugInteractions = drugInteractions;
	}

	@Column(name = "pharmacological_effects")
	public String getPharmacologicalEffects() {
		return pharmacologicalEffects;
	}

	public void setPharmacologicalEffects(String pharmacologicalEffects) {
		this.pharmacologicalEffects = pharmacologicalEffects;
	}

	@Column(name = "medicine_img")
	public String getMedicineImg() {
		return medicineImg;
	}

	public void setMedicineImg(String medicineImg) {
		this.medicineImg = medicineImg;
	}

	@Column(name = "alias")
	public String getAlias() {
		return alias;
	}

	public void setAlias(String alias) {
		this.alias = alias;
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

	@Column(name = "sort")
	public Integer getSort() {
		return sort;
	}

	public void setSort(Integer sort) {
		this.sort = sort;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "medicine_type")
	public Type getType() {
		return type;
	}

	public void setType(Type type) {
		this.type = type;
	}
	
	@Column(name = "dosage")
	public String getDosage() {
		return dosage;
	}

	public void setDosage(String dosage) {
		this.dosage = dosage;
	}

	@Column(name = "medicine_unit")
	public String getMedicineUnit() {
		return medicineUnit;
	}

	public void setMedicineUnit(String medicineUnit) {
		this.medicineUnit = medicineUnit;
	}

	@Column(name = "summary")
	public String getSummary() {
		return summary;
	}

	public void setSummary(String summary) {
		this.summary = summary;
	}

	@Column(name = "description")
	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@Version
	public Integer getVersion() {
		return version;
	}

	public void setVersion(Integer version) {
		this.version = version;
	}

	@Transient
	public String getFrontImg() {
		if(FilePathUtils.getJsonPath(medicineImg)!=null)
			return FilePathUtils.getJsonPath(medicineImg)[0];
		else 
			return null;
	}

	@Transient
	public String getFrontDesc() {
		return FilePathUtils.setEditorOutPath(description);
	}

	public void setFrontDesc(String frontDesc) {
		this.frontDesc = frontDesc;
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

	@Transient
	public boolean equals(Object o) {
		if (o == this)
			return true;
		if (!(o instanceof Role))
			return false;
		return this.id.equals(((Medicine) o).id);
	}

	@Transient
	public String toString() {
		return this.getClass().getName() + "[id=" + id + "]" + ",[version="
				+ version + "]";
	}

	@Transient
	public String getTitle() {
		return cnName;
	}
	
	@Transient
	public void getLazyObject() {
		this.getType().getLazyObject();
		Type d=new Type();
		User u=new User();
		Company com=new Company();
		try {
			BeanUtils.copyProperties(d, getType());
			BeanUtils.copyProperties(u, getUser());
			BeanUtils.copyProperties(com, getCompany());
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		this.setType(d);
		this.setUser(u);
		this.setCompany(com);
	}

	@Transient
	public String[] getSysLog() {
		return new String[] { "企业药品", "Medicine.cnName=" + cnName };
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
	public String getStaticPath() {
		return staticPath;
	}
	
	@Transient
	public String getFrontsImg() {
		if(FilePathUtils.getJsonPath(medicineImg)!=null)
			return FilePathUtils.getJsonPath(medicineImg)[0];
		else 
			return null;
	}

	public void setFrontsImg(String frontsImg) {
		this.frontsImg = frontsImg;
	}

	public void setStaticPath(String staticPath) {
		this.staticPath = staticPath;
	}

	@Transient
	public String[] getFrontImgs() {
		if(FilePathUtils.getJsonPath(medicineImg)!=null)
			return FilePathUtils.getJsonPath(medicineImg);
		else 
			return null;
	}

	public void setFrontImgs(String[] frontImgs) {
		this.frontImgs = frontImgs;
	}
	
	public void createDefaultData(Company company,Type type) {
		this.setCnName("默认药品数据");
		this.setCompany(company);
		this.setType(type);
		this.setDisable(true);
		this.setPrice(0);
		this.setDiscountedPrice(false);
		this.setDescription("默认药品介绍内容");
		this.setDosage("默认药品剂型");
		this.setEffectCategory("默认药品作用类别");
		this.setMedicineUnit("默认单位");
		this.setSort(9999);
		this.setCreateTime(new Date());
		this.setUser(ApplicationUtils.getUser());
		this.setMedicineImg("[{\"path\":\"/design/hosp/comm/no_pic.jpg/\"}]");
	}
}