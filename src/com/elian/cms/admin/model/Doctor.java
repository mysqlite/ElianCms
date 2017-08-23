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
import com.elian.cms.syst.model.ImgInterface;
import com.elian.cms.syst.model.PersistentLogInterface;
import com.elian.cms.syst.model.SeoInterface;
import com.elian.cms.syst.util.ElianCodes;
import com.elian.cms.syst.util.ElianUtils;
import com.elian.cms.syst.util.StringUtils;

/**
 * 医生
 * 
 * @author Joe
 */
@Entity
@Table(name = "T_DOCTOR")
public class Doctor extends BaseContent implements SeoInterface, PersistentLogInterface,EagerLoading,ImgInterface {
	private static final long serialVersionUID = -960640443033303332L;

	/** ID */
	private Integer id;
	/** 版本 */
	private Integer version;
	/** 科室Id */
	private Department dept;
	/** 姓名 */
	private String doctName;
	/** 性别 */
	private String gender;
	/** 生日 */
	private Date birthday;
	/** 职称 */
	private String jobTitle;
	/** 职务 */
	private String dutyName;
	/** 学历 */
	private String education;
	/** 毕业院校 */
	private String graduateSchool;
	/** 个人专长 */
	private String speciality;
	/** 介绍 */
	private String introduction;
	/** 个人图片 */
	private String doctImg;
	/** 手机 */
	private String moblie;
	/** 电子邮箱 */
	private String email;
	/** 从业时间 */
	private Date jobBeginTime;
	/** 创建时间 */
	private Date createTime;
	/** 排序 */
	private int doctSort;
	/** 是否可用 */
	private boolean isDisable;
	/** 是否开通挂号 */
	private boolean isReg;
	/** 开通挂号时间 */
	private Date regTime;
	/** 是否开通博客 */
	private boolean isBlog;
	/** 博客开通时间 */
	private Date blogTime;
	/** 是否开通随访 */
	private boolean followUp;
	/** 随访开通时间 */
	private Date followUpTime;
    /**综合评分*/
	private double avgScore;
	@Id
	@Column(name = "doct_id")
	// @SequenceGenerator(name = "doctorGenerator", sequenceName = "S_T_DOCTOR",
	// allocationSize = 1)
	// @GeneratedValue(generator = "doctorGenerator", strategy =
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

	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name = "depa_id")
	public Department getDept() {
		return dept;
	}

	public void setDept(Department dept) {
		this.dept = dept;
	}

	@Column(name = "doct_name")
	public String getDoctName() {
		return doctName;
	}

	public void setDoctName(String doctName) {
		this.doctName = doctName;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public Date getBirthday() {
		return birthday;
	}

	public void setBirthday(Date birthday) {
		this.birthday = birthday;
	}

	@Column(name = "job_Title")
	public String getJobTitle() {
		return jobTitle;
	}

	public void setJobTitle(String jobTitle) {
		this.jobTitle = jobTitle;
	}

	@Column(name = "duty_Name")
	public String getDutyName() {
		return dutyName;
	}

	public void setDutyName(String dutyName) {
		this.dutyName = dutyName;
	}

	public String getEducation() {
		return education;
	}

	public void setEducation(String education) {
		this.education = education;
	}

	@Column(name = "graduate_School")
	public String getGraduateSchool() {
		return graduateSchool;
	}

	public void setGraduateSchool(String graduateSchool) {
		this.graduateSchool = graduateSchool;
	}

	public String getSpeciality() {
		return speciality;
	}

	public void setSpeciality(String speciality) {
		this.speciality = speciality;
	}

	@Column(name = "doct_intro")
	public String getIntroduction() {
		return introduction;
	}

	public void setIntroduction(String introduction) {
		this.introduction = introduction;
	}

	@Column(name = "doct_img")
	public String getDoctImg() {
		return doctImg;
	}

	public void setDoctImg(String doctImg) {
		this.doctImg = doctImg;
	}

	public String getMoblie() {
		return moblie;
	}

	public void setMoblie(String moblie) {
		this.moblie = moblie;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	@Column(name = "job_Begin_Time")
	public Date getJobBeginTime() {
		return jobBeginTime;
	}

	public void setJobBeginTime(Date jobBeginTime) {
		this.jobBeginTime = jobBeginTime;
	}

	@Column(name = "create_Time")
	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	@Column(name = "doct_Sort")
	public int getDoctSort() {
		return doctSort;
	}

	public void setDoctSort(int doctSort) {
		this.doctSort = doctSort;
	}

	@Column(name = "is_Disable")
	public boolean isDisable() {
		return isDisable;
	}

	public void setDisable(boolean isDisable) {
		this.isDisable = isDisable;
	}

	@Column(name = "is_Reg")
	public boolean isReg() {
		return isReg;
	}

	public void setReg(boolean isReg) {
		this.isReg = isReg;
	}

	@Column(name = "reg_Time")
	public Date getRegTime() {
		return regTime;
	}

	public void setRegTime(Date regTime) {
		this.regTime = regTime;
	}

	@Column(name = "is_Blog")
	public boolean isBlog() {
		return isBlog;
	}

	public void setBlog(boolean isBlog) {
		this.isBlog = isBlog;
	}

	@Column(name = "blog_Time")
	public Date getBlogTime() {
		return blogTime;
	}

	public void setBlogTime(Date blogTime) {
		this.blogTime = blogTime;
	}

	@Column(name = "is_follow_Up")
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
	
	@Transient
	public String getSeoDescription() {
		return speciality;
	}

	@Transient
	public String getSeoKeywords() {
		return doctName;
	}

	@Transient
	public String getSeoTitle() {
		return doctName;
	}

	@Transient
	public String getTitle() {
		return doctName;
	}

	@Transient
	public double getAvgScore() {
		return avgScore;
	}

	public void setAvgScore(double avgScore) {
		this.avgScore = avgScore;
	}

	@Transient
	public String getIntroductionNoHTML() {
		return StringUtils.replaceHtml(StringUtils.isBlank(introduction)?"":introduction);
	}
	
	@Transient
	public void getLazyObject() {
		this.getDept().getLazyObject();
		Department d=new Department();
		try {
			BeanUtils.copyProperties(d, getDept());
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		this.setDept(d);
	}

	public void createDefaultData(Department d) {
		this.setDept(d);
		this.setDisable(true);
		this.setDoctName("默认医生");
		this.setGender(ElianUtils.SECRET);
		this.setCreateTime(new Date());
		this.setDoctSort(99);
		this.setReg(d.isReg());
		this.setFollowUp(d.isFollowUp());
		this.setDoctImg(ElianCodes.DOCTOR_DEFAULT_IMG);
		this.setIntroduction("默认医生介绍");
	}

	public String[] descImgs() {
		return new String[]{doctImg};
	}

	public String[] imgs() {
		return new String[]{doctName};
	}
	
	@Transient
	public String[] getSysLog() {
		return new String[] { "医生", "doctName=" + doctName };
	}
}
