package com.elian.cms.admin.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.persistence.Version;

import org.apache.commons.lang3.StringUtils;

import com.elian.cms.syst.model.BaseContent;
import com.elian.cms.syst.model.PersistentLogInterface;
import com.elian.cms.syst.model.SeoInterface;
import com.elian.cms.syst.util.FilePathUtils;

/**
 * 企业
 * 
 * @author thy
 * 
 */
@javax.persistence.Entity
@Table(name = "COMPANY")
public class Company extends BaseContent implements PersistentLogInterface,SeoInterface {
	private static final long serialVersionUID = -3855671238253931704L;
	
	/**id*/
	private Integer id;
	/**企业名称*/
	private String name;
	/**企业类型*/
	private String type;
	/**企业名称缩写*/
	private String shortName;
	/**营业执照*/
	private String permitImg;
	/**企业简介*/
	private String shortIntroduce;
	/**详细介绍*/
	private String introduce;
	/**电话号码*/
	private String phone;
	/**地址*/
	private String address;
	/**站点URL*/
	private String siteUrl;
	/**邮箱*/
	private String email;
	/**传真*/
	private String fax;
	/**邮政编码*/
	private String postcode;
	/**区域*/
	private Area area;
	/**logo图片*/
	private String logoImg;
	/**企业图片*/
	private String companyImg;
	/**乘车路线*/
	private String busLine;
	/**位置地图*/
	private String mapImg;
	/**点击量*/
	private Integer hits;
	/**注册时间*/
	private Date createTime;
	/**审核人*/
	private String auditor;
	/**审核时间*/
	private Date auditTime;
	/**状态*/
	private int status;
	/**是否开通商城*/
	private boolean isMall=false;
	/**开通商城时间*/
	private Date mallTime;
	/**退回说明*/
	private String remarks;
	/**注册资金**/
	private String registeredCapital;
	/**成立时间**/
	private Date foundedTime;
	/**法人代表**/
	private String legalRepresentative;
	/**qq**/
	private String qq;
	/**联系人名称**/
	private String contactName;
	/**经验模式**/
	private String empiricaMode;
	/**版本号*/
	private Integer version;
	/**展示到前端的公司描述**/
	private String frontIntroduce;
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="company_id")
	public Integer getId() {
		return id;
	}
	
	public void setId(Integer id) {
		this.id = id;
	}
	
	@Column(name="type")
	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	@Column(name="full_name")
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	@Column(name="short_name")
	public String getShortName() {
		return shortName;
	}
	
	public void setShortName(String shortName) {
		this.shortName = shortName;
	}
	
	@Column(name="permit_img")
	public String getPermitImg() {
		return permitImg;
	}
	
	public void setPermitImg(String permitImg) {
		this.permitImg = permitImg;
	}
	
	@Column(name="short_desc")
	public String getShortIntroduce() {
		return shortIntroduce;
	}
	
	public void setShortIntroduce(String shortIntroduce) {
		this.shortIntroduce = shortIntroduce;
	}
	
	@Column(name="enterp_desc")
	public String getIntroduce() {
		return introduce;
	}
	
	public void setIntroduce(String introduce) {
		this.introduce = introduce;
	}
	
	@Column(name="phone")
	public String getPhone() {
		return phone;
	}
	
	public void setPhone(String phone) {
		this.phone = phone;
	}
	
	@Column(name="address")
	public String getAddress() {
		return address;
	}
	
	public void setAddress(String address) {
		this.address = address;
	}
	
	@Column(name="site_url")
	public String getSiteUrl() {
		return siteUrl;
	}
	
	public void setSiteUrl(String siteUrl) {
		this.siteUrl = siteUrl;
	}
	
	@Column(name="email")
	public String getEmail() {
		return email;
	}
	
	public void setEmail(String email) {
		this.email = email;
	}
	
	@Column(name="fax")
	public String getFax() {
		return fax;
	}
	
	public void setFax(String fax) {
		this.fax = fax;
	}
	
	@Column(name="postcode")
	public String getPostcode() {
		return postcode;
	}
	
	public void setPostcode(String postcode) {
		this.postcode = postcode;
	}
	
	@ManyToOne
	@JoinColumn(name="area_id")
	public Area getArea() {
		return area;
	}
	
	public void setArea(Area area) {
		this.area = area;
	}
	
	@Column(name="logo_img")
	public String getLogoImg() {
		return logoImg;
	}
	
	public void setLogoImg(String logoImg) {
		this.logoImg = logoImg;
	}
	
	@Column(name="enterp_img")
	public String getCompanyImg() {
		return companyImg;
	}
	
	public void setCompanyImg(String companyImg) {
		this.companyImg = companyImg;
	}
	
	@Column(name="bus_line")
	public String getBusLine() {
		return busLine;
	}
	
	public void setBusLine(String busLine) {
		this.busLine = busLine;
	}
	
	@Column(name="map_img")
	public String getMapImg() {
		return mapImg;
	}
	
	public void setMapImg(String mapImg) {
		this.mapImg = mapImg;
	}
	
	@Column(name="hits")
	public Integer getHits() {
		return hits;
	}
	
	public void setHits(Integer hits) {
		this.hits = hits;
	}
	
	@Column(name="create_time")
	public Date getCreateTime() {
		return createTime;
	}
	
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	
	@Column(name="auditor")
	public String getAuditor() {
		return auditor;
	}
	
	public void setAuditor(String auditor) {
		this.auditor = auditor;
	}
	
	@Column(name="audit_time")
	public Date getAuditTime() {
		return auditTime;
	}
	
	public void setAuditTime(Date auditTime) {
		this.auditTime = auditTime;
	}
	
	@Column(name="status")
	public int getStatus() {
		return status;
	}
	
	public void setStatus(int status) {
		this.status = status;
	}
	
	@Column(name="is_mall")
	public boolean isMall() {
		return isMall;
	}

	public void setMall(boolean isMall) {
		this.isMall = isMall;
	}
	
	@Column(name="mall_open_time")
	public Date getMallTime() {
		return mallTime;
	}
	
	public void setMallTime(Date mallTime) {
		this.mallTime = mallTime;
	}
	
	@Column(name="remarks")
	public String getRemarks() {
		return remarks;
	}
	
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
	
	@Column(name="registered_capital")
	public String getRegisteredCapital() {
		return registeredCapital;
	}

	public void setRegisteredCapital(String registeredCapital) {
		this.registeredCapital = registeredCapital;
	}

	@Column(name="founded_time")
	public Date getFoundedTime() {
		return foundedTime;
	}

	public void setFoundedTime(Date foundedTime) {
		this.foundedTime = foundedTime;
	}

	@Column(name="legal_representative")
	public String getLegalRepresentative() {
		return legalRepresentative;
	}

	public void setLegalRepresentative(String legalRepresentative) {
		this.legalRepresentative = legalRepresentative;
	}
	
	@Column(name="qq")
	public String getQq() {
		return qq;
	}

	public void setQq(String qq) {
		this.qq = qq;
	}

	@Column(name="contact_name")
	public String getContactName() {
		return contactName;
	}

	public void setContactName(String contactName) {
		this.contactName = contactName;
	}

	@Column(name="empirica_mode")
	public String getEmpiricaMode() {
		return empiricaMode;
	}

	public void setEmpiricaMode(String empiricaMode) {
		this.empiricaMode = empiricaMode;
	}

	@Version
	public Integer getVersion() {
		return version;
	}

	public void setVersion(Integer version) {
		this.version = version;
	}

	@Transient
	public String getTitle() {
		return this.name;
	}
	
	@Transient
	public String getSeoDescription() {
		return name;
	}

	@Transient
	public String getSeoKeywords() {
		return name;
	}

	@Transient
	public String getFrontIntroduce() {
		if (StringUtils.isNotBlank(this.frontIntroduce))
			return this.frontIntroduce;
		return FilePathUtils.setEditorOutPath(introduce);
	}
	
	public void setFrontIntroduce(String frontIntroduce) {
		this.frontIntroduce = frontIntroduce;
	}

	@Transient
	public String getSeoTitle() {
		return name;
	}
	@Transient
	public String[] getSysLog() {
		return new String[] { "企业", "name=" + name };
	}
}
