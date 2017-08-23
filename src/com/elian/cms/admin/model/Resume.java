package com.elian.cms.admin.model;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.elian.cms.syst.model.PersistentInterface;

/**
 * <b>Title:</b>Resume.java <br>
 * <b>Description:</b>应聘投递简历 数据模型<br>
 * <b>Copyright:</b>Copyright (c) 2013 <br>
 * <b>Company:</b>广东医联网科技有限公司<br>
 * @author 葛传艺
 * @version 0.1 2013年12月22日14:52:09
 */
@Entity
@Table(name = "t_resume", schema = "dbo", catalog = "91580_cms")
public class Resume implements PersistentInterface{
	private static final long serialVersionUID = 3903864096409976620L;
	public static Map<String, String> getSearChConditionMap() {
		Map<String, String> conditionMap = new HashMap<String, String>(3);
		conditionMap.put("应聘职位", "appliedPosition");
		conditionMap.put("姓名", "name");
		conditionMap.put("性别", "gender");
		return conditionMap;
	}
	/**主键*/
	private Integer id;
	/**站点ID*/
	private Site site;
	/**应聘职位*/
	private String appliedPosition;
	/**姓名*/
	private String name;
	/**性别*/
	private String gender;
	/**生日*/
	private Date birthday;
	/**身高cm*/
	private Double height;
	/**体重kg*/
	private Double weight;
	/**毕业院校*/
	private String schools;
	/**专业*/
	private String specialty;
	/**最高学历*/
	private String highestDegree;
	/**学位*/
	private String degree;
	/**政治面貌*/
	private String politicalAffiliation;
	/**婚育情况*/
	private String MACRSituation;
	/**户籍*/
	private String census;
	/**有无相关工作经验*/
	private boolean hasWorkExperience;
	/**教育经历*/
	private String education;
	/**工作经历*/
	private String workHistory;
	/**联系电话[座机\手机]*/
	private String mobile;
	/**E-mail*/
	private String email;
	/**地址*/
	private String address;
	/**计算机证书*/
	private String computerCertificate;
	/**外语证书*/
	private String languageCertificate;
	/**资格证书*/
	private String qualificationCertificate;
	/**执业证书*/
	private String practicingCertificate;
	/**其他证书*/
	private String otherCertificate;
	/**状态*/
	private Short status;
	/**version*/
	private Integer version;

	@Id
	@Column(name = "id")
    @GeneratedValue( strategy = GenerationType.AUTO)
	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "site_id")
	public Site getSite() {
		return site;
	}

	public void setSite(Site site) {
		this.site = site;
	}

	@Column(name = "applied_position", length = 200)
	public String getAppliedPosition() {
		return this.appliedPosition;
	}


	public void setAppliedPosition(String appliedPosition) {
		this.appliedPosition = appliedPosition;
	}

	@Column(name = "name", length = 200)
	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Column(name = "gender", length = 20)
	public String getGender() {
		return this.gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	@Column(name = "birthday", length = 23)
	public Date getBirthday() {
		return this.birthday;
	}

	public void setBirthday(Date birthday) {
		this.birthday = birthday;
	}

	@Column(name = "height", precision = 53, scale = 0)
	public Double getHeight() {
		return this.height;
	}

	public void setHeight(Double height) {
		this.height = height;
	}

	@Column(name = "weight", precision = 53, scale = 0)
	public Double getWeight() {
		return this.weight;
	}

	public void setWeight(Double weight) {
		this.weight = weight;
	}

	@Column(name = "schools", length = 200)
	public String getSchools() {
		return this.schools;
	}

	public void setSchools(String schools) {
		this.schools = schools;
	}

	@Column(name = "specialty", length = 200)
	public String getSpecialty() {
		return this.specialty;
	}

	public void setSpecialty(String specialty) {
		this.specialty = specialty;
	}

	@Column(name = "highest_degree", length = 200)
	public String getHighestDegree() {
		return this.highestDegree;
	}

	public void setHighestDegree(String highestDegree) {
		this.highestDegree = highestDegree;
	}

	@Column(name = "degree", length = 200)
	public String getDegree() {
		return this.degree;
	}

	public void setDegree(String degree) {
		this.degree = degree;
	}

	@Column(name = "political_affiliation", length = 200)
	public String getPoliticalAffiliation() {
		return this.politicalAffiliation;
	}

	public void setPoliticalAffiliation(String politicalAffiliation) {
		this.politicalAffiliation = politicalAffiliation;
	}

	@Column(name = "m_a_c_r_situation", length = 200)
	public String getMACRSituation() {
		return this.MACRSituation;
	}

	public void setMACRSituation(String MACRSituation) {
		this.MACRSituation = MACRSituation;
	}

	@Column(name = "census", length = 500)
	public String getCensus() {
		return this.census;
	}

	public void setCensus(String census) {
		this.census = census;
	}

	@Column(name = "has_work_experience")
	public boolean isHasWorkExperience() {
		return hasWorkExperience;
	}

	public void setHasWorkExperience(boolean hasWorkExperience) {
		this.hasWorkExperience = hasWorkExperience;
	}

	@Column(name = "education", length = 1000)
	public String getEducation() {
		return this.education;
	}


	public void setEducation(String education) {
		this.education = education;
	}

	@Column(name = "work_history", length = 1000)
	public String getWorkHistory() {
		return this.workHistory;
	}

	public void setWorkHistory(String workHistory) {
		this.workHistory = workHistory;
	}

	@Column(name = "mobile", length = 15)
	public String getMobile() {
		return this.mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	

	@Column(name = "e_mail", length = 200)
	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	@Column(name = "address", length = 500)
	public String getAddress() {
		return this.address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	@Column(name = "computer_certificate", length = 500)
	public String getComputerCertificate() {
		return this.computerCertificate;
	}

	public void setComputerCertificate(String computerCertificate) {
		this.computerCertificate = computerCertificate;
	}

	@Column(name = "language_certificate", length = 500)
	public String getLanguageCertificate() {
		return this.languageCertificate;
	}

	public void setLanguageCertificate(String languageCertificate) {
		this.languageCertificate = languageCertificate;
	}

	@Column(name = "qualification_certificate", length = 500)
	public String getQualificationCertificate() {
		return this.qualificationCertificate;
	}

	public void setQualificationCertificate(String qualificationCertificate) {
		this.qualificationCertificate = qualificationCertificate;
	}

	@Column(name = "practicing_certificate", length = 500)
	public String getPracticingCertificate() {
		return this.practicingCertificate;
	}

	public void setPracticingCertificate(String practicingCertificate) {
		this.practicingCertificate = practicingCertificate;
	}

	@Column(name = "other_certificate", length = 500)
	public String getOtherCertificate() {
		return this.otherCertificate;
	}

	public void setOtherCertificate(String otherCertificate) {
		this.otherCertificate = otherCertificate;
	}

	@Column(name = "status")
	public Short getStatus() {
		return this.status;
	}

	public void setStatus(Short status) {
		this.status = status;
	}

	@Column(name = "version")
	public Integer getVersion() {
		return this.version;
	}

	public void setVersion(Integer version) {
		this.version = version;
	}

}