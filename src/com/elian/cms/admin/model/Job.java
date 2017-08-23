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

import com.elian.cms.syst.model.BaseContent;
import com.elian.cms.syst.model.PersistentLogInterface;
import com.elian.cms.syst.model.SeoInterface;
import com.elian.cms.syst.util.ElianUtils;

/**
 * 招聘
 * 
 * @author Gechuany
 * @createTime 2012-10-24
 */
@Entity
@Table(name = "T_JOB")
public class Job extends BaseContent implements SeoInterface, PersistentLogInterface {
	private static final long serialVersionUID = 1L;
	/** 招聘ID */
	private Integer id;
	/** 标题 */
	private String title;
	/** 摘要 */
	private String description;
	/** 关键字 */
	private String keywords;
	/** 来源名称 */
	private String sourceName;
	/** 来源url */
	private String sourceUrl;
	/** 创建人 */
	private User createrId;
	/** 创建日期 */
	private Date createTime;
	/** 用人部门 */
	private String servantDepa;
	/** 职位名称 */
	private String jobName;
	/** 工作性质（全职、兼职） */
	private String jobNature;
	/** 招聘人数 */
	private String hireNum;
	/** 专业要求 */
	private String majorRequ;
	/** 提供月薪 */
	private String salary;
	/** 住宿情况 */
	private String housing;
	/** 工作地点 */
	private Integer areaId;
	/** 语言要求 */
	private String language;
	/** 学历要求 */
	private String education;
	/** 年龄要求 */
	private String ageRange;
	/** 性别要求 */
	private String gender;
	/** 工作经验要求 */
	private String workExpe;
	/** 岗位要求 */
	private String jobRequ;
	/** 发布时间 */
	private Date publishTime;
	/** 有效日期 */
	private Date expireTime;
	/** 联系人 */
	private String contacter;
	/** 联系电话 */
	private String contactPhone;
	/** 是否可用（0不可用，1可用） */
	private boolean disable;
	/** 版本号 */
	private Integer version;
	
	/** 行业类型 */
	private Industry industry;


	@Id
	@Column(name = "job_id")
	// @SequenceGenerator(name = "jobGenerator", sequenceName = "S_T_JOB",
	// allocationSize = 1)
	// @GeneratedValue(generator = "jobGenerator", strategy =
	// GenerationType.SEQUENCE)
	@GeneratedValue( strategy = GenerationType.AUTO)
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	
	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getKeywords() {
		return keywords;
	}

	public void setKeywords(String keywords) {
		this.keywords = keywords;
	}

	@Column(name = "source_name")
	public String getSourceName() {
		return sourceName;
	}

	public void setSourceName(String sourceName) {
		this.sourceName = sourceName;
	}

	@Column(name = "source_url")
	public String getSourceUrl() {
		return sourceUrl;
	}

	public void setSourceUrl(String sourceUrl) {
		this.sourceUrl = sourceUrl;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "creater_id")
	public User getCreaterId() {
		return createrId;
	}

	public void setCreaterId(User createrId) {
		this.createrId = createrId;
	}

	@Column(name = "create_time")
	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	@Column(name = "servant_depa")
	public String getServantDepa() {
		return servantDepa;
	}

	public void setServantDepa(String servantDepa) {
		this.servantDepa = servantDepa;
	}

	@Column(name = "Job_Name")
	public String getJobName() {
		return jobName;
	}

	public void setJobName(String jobName) {
		this.jobName = jobName;
	}

	@Column(name = "Job_Nature")
	public String getJobNature() {
		return jobNature;
	}

	public void setJobNature(String jobNature) {
		this.jobNature = jobNature;
	}

	@Column(name = "Hire_Num")
	public String getHireNum() {
		return hireNum;
	}

	public void setHireNum(String hireNum) {
		this.hireNum = hireNum;
	}

	@Column(name = "Major_Requ")
	public String getMajorRequ() {
		return majorRequ;
	}

	public void setMajorRequ(String majorRequ) {
		this.majorRequ = majorRequ;
	}

	public String getSalary() {
		return salary;
	}

	public void setSalary(String salary) {
		this.salary = salary;
	}

	public String getHousing() {
		return housing;
	}

	public void setHousing(String housing) {
		this.housing = housing;
	}

	@Column(name = "Area_Id")
	public Integer getAreaId() {
		return areaId;
	}

	public void setAreaId(Integer areaId) {
		this.areaId = areaId;
	}

	public String getLanguage() {
		return language;
	}

	public void setLanguage(String language) {
		this.language = language;
	}

	public String getEducation() {
		return education;
	}

	public void setEducation(String education) {
		this.education = education;
	}

	@Column(name = "Age_Range")
	public String getAgeRange() {
		return ageRange;
	}

	public void setAgeRange(String ageRange) {
		this.ageRange = ageRange;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	@Column(name = "Work_Expe")
	public String getWorkExpe() {
		return workExpe;
	}

	public void setWorkExpe(String workExpe) {
		this.workExpe = workExpe;
	}

	@Column(name = "Job_Requ")
	public String getJobRequ() {
		return jobRequ;
	}

	public void setJobRequ(String jobRequ) {
		this.jobRequ = jobRequ;
	}

	@Column(name = "Publish_Time")
	public Date getPublishTime() {
		return publishTime;
	}

	public void setPublishTime(Date publishTime) {
		this.publishTime = publishTime;
	}

	@Column(name = "Expire_Time")
	public Date getExpireTime() {
		return expireTime;
	}

	public void setExpireTime(Date expireTime) {
		this.expireTime = expireTime;
	}

	@Column(name = "contacter")
	public String getContacter() {
		return contacter;
	}

	public void setContacter(String contacter) {
		this.contacter = contacter;
	}

	@Column(name = "contact_Phone")
	public String getContactPhone() {
		return contactPhone;
	}

	public void setContactPhone(String contactPhone) {
		this.contactPhone = contactPhone;
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
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "industry_id")
	public Industry getIndustry() {
		return industry;
	}

	public void setIndustry(Industry industry) {
		this.industry = industry;
	}
	
	public boolean equals(Object o) {
		if (o == this)
			return true;
		if (!(o instanceof Information))
			return false;
		return this.id.equals(((Job) o).id);
	}

	public String toString() {
		return this.getClass().getName() + "[id=" + id + "]" + ",[version="
				+ version + "]";
	}
	
	@Transient
	public String getSeoDescription() {
		return description;
	}

	@Transient
	public String getSeoKeywords() {
		return keywords;
	}

	@Transient
	public String getSeoTitle() {
		return title;
	}

	@Transient
	public User getCreater() {
		return createrId;
	}
	
	@Override
	public void initWithDefaultData(User user,Channel channel) {
		this.setTitle(channel.getChannelName()+"***人才招聘的标题");
		this.setDisable(true);
		this.setGender(ElianUtils.UNLIMITED);
		this.setAreaId(0);
		this.setCreaterId(user);
		this.setCreateTime(new Date());
		this.setExpireTime(new Date());
		this.setJobRequ(channel.getChannelName()+"***招聘的岗位要求");
	}
	
	@Transient
	public String[] getSysLog() {
		return new String[] { "招聘", "title=" + title };
	}
}
