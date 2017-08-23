package com.elian.cms.admin.model;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.persistence.Version;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import com.elian.cms.syst.model.PersistentLogInterface;

/**
 * 联系人
 * 
 * @author Gechuanyi
 *@201201029
 */

@javax.persistence.Entity
@org.hibernate.annotations.Entity(dynamicUpdate = true, dynamicInsert = true)
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@Table(name = "t_contact_detail")
public class Contacter implements PersistentLogInterface {
	private static final long serialVersionUID = 5027422506733066983L;
	/** 主键ID */
	private Integer id;
	/** 站点 */
	private Site site;
	/** 联系名称 */
	private String contactName;
	/** 应聘职位 */
	private String applyPost;
	/** 性别 */
	private Integer gender;
	/** 出生日期 */
	private String birthday;
	/** 身高 */
	private String height;
	/** 体重 */
	private String weight;
	/** 籍贯 */
	private String hometown;
	/** 毕业学校 */
	private String schools;
	/** 专业 */
	private String specialty;
	/** 最高学历 */
	private String educationalBackground;
	/** 学位 */
	private String degree;
	/** 政治面貌 */
	private String politicalLandscape;
	/** 婚姻情况 */
	private Integer maritalStatus;
	/** 生育情况 */
	private Integer fertility;
	/** 户口所在地 */
	private String hukou;
	/** 相关工作经验 */
	private String xWorkExperience;
	/** 教育经历 */
	private String education;
	/** 工作经历 */
	private String workExperience;
	/** 移动电话 */
	private String mobile;
	/** E-mail */
	private String email;
	/** 有无计算机等级证书*/
	private String computerCertificates;
	/** 有无外语等级证书 */
	private String foreignLanguageCertificates;
	/** 有无相关资格证书 */
	private String certificates;
	/** 有无相关执业证书 */
	private String practicingCertificate;
	/** 其它证书 */
	private String otherCrtificates;
	/** 联系人 */
	private String contacter;
	/** 联系电话 */
	private String phone;
	/** QQ */
	private String qq;
	/** MSN */
	private String msn;
	/** 传真 */
	private String fax;
	/** 邮编 */
	private String postcode;
	/** 地址 */
	private String address;
	/** 部门 */
	private String department;
	/** 是否启用 */
	private boolean disable;
	/** 版本号 */
	private Integer version;

	@Id
	@Column(name = "CONTACT_ID")
	// @SequenceGenerator(name = "contacterGenerator", sequenceName =
	// "S_T_CONTACT_DETAIL", allocationSize = 1)
	// @GeneratedValue(generator = "contacterGenerator", strategy = GenerationType.SEQUENCE)
	@GeneratedValue( strategy = GenerationType.AUTO)
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	@ManyToOne
	@JoinColumn(name = "site_id")
	public Site getSite() {
		return site;
	}

	public void setSite(Site site) {
		this.site = site;
	}

	@Column(name = "CONTACT_NAME")
	public String getContactName() {
		return contactName;
	}

	public void setContactName(String contactName) {
		this.contactName = contactName;
	}

	public String getContacter() {
		return contacter;
	}

	public void setContacter(String contacter) {
		this.contacter = contacter;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getQq() {
		return qq;
	}

	public void setQq(String qq) {
		this.qq = qq;
	}

	public String getMsn() {
		return msn;
	}

	public void setMsn(String msn) {
		this.msn = msn;
	}

	public String getFax() {
		return fax;
	}

	public void setFax(String fax) {
		this.fax = fax;
	}

	public String getPostcode() {
		return postcode;
	}

	public void setPostcode(String postcode) {
		this.postcode = postcode;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getDepartment() {
		return department;
	}

	public void setDepartment(String department) {
		this.department = department;
	}

	public String getApplyPost() {
		return applyPost;
	}

	public void setApplyPost(String applyPost) {
		this.applyPost = applyPost;
	}

	public Integer getGender() {
		return gender;
	}

	public void setGender(Integer gender) {
		this.gender = gender;
	}

	public String getBirthday() {
		return birthday;
	}

	public void setBirthday(String birthday) {
		this.birthday = birthday;
	}

	public String getHeight() {
		return height;
	}

	public void setHeight(String height) {
		this.height = height;
	}

	public String getWeight() {
		return weight;
	}

	public void setWeight(String weight) {
		this.weight = weight;
	}

	public String getHometown() {
		return hometown;
	}

	public void setHometown(String hometown) {
		this.hometown = hometown;
	}

	public String getSchools() {
		return schools;
	}

	public void setSchools(String schools) {
		this.schools = schools;
	}

	public String getSpecialty() {
		return specialty;
	}

	public void setSpecialty(String specialty) {
		this.specialty = specialty;
	}

	public String getEducationalBackground() {
		return educationalBackground;
	}

	public void setEducationalBackground(String educationalBackground) {
		this.educationalBackground = educationalBackground;
	}

	public String getDegree() {
		return degree;
	}

	public void setDegree(String degree) {
		this.degree = degree;
	}

	public String getPoliticalLandscape() {
		return politicalLandscape;
	}

	public void setPoliticalLandscape(String politicalLandscape) {
		this.politicalLandscape = politicalLandscape;
	}

	public Integer getMaritalStatus() {
		return maritalStatus;
	}

	public void setMaritalStatus(Integer maritalStatus) {
		this.maritalStatus = maritalStatus;
	}

	public Integer getFertility() {
		return fertility;
	}

	public void setFertility(Integer fertility) {
		this.fertility = fertility;
	}

	public String getHukou() {
		return hukou;
	}

	public void setHukou(String hukou) {
		this.hukou = hukou;
	}

	public String getxWorkExperience() {
		return xWorkExperience;
	}

	public void setxWorkExperience(String xWorkExperience) {
		this.xWorkExperience = xWorkExperience;
	}

	public String getEducation() {
		return education;
	}

	public void setEducation(String education) {
		this.education = education;
	}

	public String getWorkExperience() {
		return workExperience;
	}

	public void setWorkExperience(String workExperience) {
		this.workExperience = workExperience;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
	
	public String getComputerCertificates() {
		return computerCertificates;
	}

	public void setComputerCertificates(String computerCertificates) {
		this.computerCertificates = computerCertificates;
	}

	public String getForeignLanguageCertificates() {
		return foreignLanguageCertificates;
	}

	public void setForeignLanguageCertificates(String foreignLanguageCertificates) {
		this.foreignLanguageCertificates = foreignLanguageCertificates;
	}

	public String getCertificates() {
		return certificates;
	}

	public void setCertificates(String certificates) {
		this.certificates = certificates;
	}

	public String getPracticingCertificate() {
		return practicingCertificate;
	}

	public void setPracticingCertificate(String practicingCertificate) {
		this.practicingCertificate = practicingCertificate;
	}

	public String getOtherCrtificates() {
		return otherCrtificates;
	}

	public void setOtherCrtificates(String otherCrtificates) {
		this.otherCrtificates = otherCrtificates;
	}

	@Column(name = "is_disable")
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
	@Transient
	public String[] getSysLog() {
		return new String[] { "联系人", "contactName=" + contactName };
	}
}
