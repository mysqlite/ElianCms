package com.elian.cms.admin.model;

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

import com.elian.cms.syst.model.EagerLoading;
import com.elian.cms.syst.model.PersistentLogInterface;

/**
 * 用户
 * 
 * @author Joe
 */
@javax.persistence.Entity
@Table(name = "T_USER")
public class User implements PersistentLogInterface,EagerLoading {
	private static final long serialVersionUID = -2451136213913587496L;
	private Integer id;
	/** 版本 */
	private Integer version;
	/** 用户账号 */
	private String account;
	/** 密码 */
	private String password;
	/** 用户类型 */
	private UserType userType;
	/** 手机号码 */
	private String mobile;
	/** 用户的性别(男士:male，女士:female，保密:secret) */
	private String gender="male";
	/** 用户生日 */
	private Date birthday;
	/** 身份证号码 */
	private String identityCard;
	/** 身份证照片 */
	private String idCardImg;
	/** 自我介绍 */
	private String intro;
	/** QQ号码 */
	private String qq;
	/** MSN */
	private String msn;
	/** 来自 */
	private Integer comefrom;
	/** 用户头像 */
	private String userImg;
	/** 手机号码是否通过验证 */
	private boolean isMiblePass;
	/** 电子邮件 */
	private String email;
	/** 电子邮件是否通过验证 */
	private boolean isEmailPass;
	/** 医疗卡号 */
	private String medicalCard;
	/** 注册IP */
	private String registerIp;
	/** 注册时间 */
	private Date registerTime;
	/** 登录次数 */
	private Integer loginCount;
	/** 最后登录IP */
	private String lastLoginIp;

	private String loginIp;

	/** 最后登录时间 */
	private Date lastLoginTime;

	private Date LoginTime;

	/** 用户状态 */
	private Integer status;
	/** 用户的真实名字 */
	private String realName;
	/** 是否申请人 */
	private boolean proposer;
	/** 是否超级管理员 */
	private boolean admin;

	@Id
	@Column(name = "user_id")
	// @SequenceGenerator(name = "userGenerator", sequenceName = "S_T_USER",
	// allocationSize = 1)
	// @GeneratedValue(generator = "userGenerator", strategy =
	// GenerationType.SEQUENCE)
	@GeneratedValue(strategy = GenerationType.AUTO)
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

	@Column(name = "account", updatable = false)
	public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		this.account = account;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name = "type_id")
	public UserType getUserType() {
		return userType;
	}

	public void setUserType(UserType userType) {
		this.userType = userType;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	@Column(name = "GENDER")
	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	@Column(name = "BIRTHDAY")
	public Date getBirthday() {
		return birthday;
	}

	public void setBirthday(Date birthday) {
		this.birthday = birthday;
	}

	@Column(name = "IDENTITY_CARD")
	public String getIdentityCard() {
		return identityCard;
	}

	public void setIdentityCard(String identityCard) {
		this.identityCard = identityCard;
	}

	@Column(name = "ID_CARD_IMG")
	public String getIdCardImg() {
		return idCardImg;
	}

	public void setIdCardImg(String idCardImg) {
		this.idCardImg = idCardImg;
	}

	@Column(name = "INTRO")
	public String getIntro() {
		return intro;
	}

	public void setIntro(String intro) {
		this.intro = intro;
	}

	@Column(name = "MSN")
	public String getMsn() {
		return msn;
	}

	public void setMsn(String msn) {
		this.msn = msn;
	}

	@Column(name = "QQ")
	public String getQq() {
		return qq;
	}

	public void setQq(String qq) {
		this.qq = qq;
	}

	@Column(name = "COMEFROM")
	public Integer getComefrom() {
		return comefrom;
	}

	public void setComefrom(Integer comefrom) {
		this.comefrom = comefrom;
	}

	@Column(name = "USER_IMG")
	public String getUserImg() {
		return userImg;
	}

	public void setUserImg(String userImg) {
		this.userImg = userImg;
	}

	@Column(name = "IS_MOBILE_PASS")
	public boolean isMiblePass() {
		return isMiblePass;
	}

	public void setMiblePass(boolean isMiblePass) {
		this.isMiblePass = isMiblePass;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	@Column(name = "is_email_pass")
	public boolean isEmailPass() {
		return isEmailPass;
	}

	public void setEmailPass(boolean isEmailPass) {
		this.isEmailPass = isEmailPass;
	}

	@Column(name = "medical_card")
	public String getMedicalCard() {
		return medicalCard;
	}

	public void setMedicalCard(String medicalCard) {
		this.medicalCard = medicalCard;
	}

	@Column(name = "register_ip")
	public String getRegisterIp() {
		return registerIp;
	}

	public void setRegisterIp(String registerIp) {
		this.registerIp = registerIp;
	}

	@Column(name = "register_time")
	public Date getRegisterTime() {
		return registerTime;
	}

	public void setRegisterTime(Date registerTime) {
		this.registerTime = registerTime;
	}

	@Column(name = "login_count")
	public Integer getLoginCount() {
		return loginCount;
	}

	public void setLoginCount(Integer loginCount) {
		this.loginCount = loginCount;
	}

	@Column(name = "last_login_ip")
	public String getLastLoginIp() {
		return lastLoginIp;
	}

	public void setLastLoginIp(String lastLoginIp) {
		this.lastLoginIp = lastLoginIp;
	}

	@Column(name = "last_login_time")
	public Date getLastLoginTime() {
		return lastLoginTime;
	}

	public void setLastLoginTime(Date lastLoginTime) {
		this.lastLoginTime = lastLoginTime;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	@Column(name = "REAL_NAME")
	public String getRealName() {
		return realName;
	}

	public void setRealName(String realName) {
		this.realName = realName;
	}

	@Column(name = "IS_ADMIN")
	public boolean isAdmin() {
		return admin;
	}

	public void setAdmin(boolean admin) {
		this.admin = admin;
	}

	@Column(name = "IS_PROPOSER")
	public boolean isProposer() {
		return proposer;
	}

	public void setProposer(boolean proposer) {
		this.proposer = proposer;
	}

	@Transient
	// 非持久化属性注解
	public String getLoginIp() {
		return loginIp;
	}

	public void setLoginIp(String loginIp) {
		this.loginIp = loginIp;
	}

	@Transient
	public Date getLoginTime() {
		return LoginTime;
	}

	public void setLoginTime(Date loginTime) {
		LoginTime = loginTime;
	}

	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null || !(o instanceof User)) {
			return false;
		}
		return id == ((User) o).getId();
	}

	public int hashCode() {
		return id;
	}

	public String toString() {
		return this.getClass().getName() + "[id=" + id + "]" + ",[version="
				+ version + "]";
	}

	public void getLazyObject() {
//		getUserType().getLazyObject();
//		UserType ut=new UserType();
//		try {
//			BeanUtils.copyProperties(ut, getUserType());
//		}catch (Exception e) {
//			e.printStackTrace();
//		}
//		this.setUserType(ut);
	}
	
	@Transient
	public String[] getSysLog() {
		return new String[] { "用户", "realName=" + realName };
	}

}
