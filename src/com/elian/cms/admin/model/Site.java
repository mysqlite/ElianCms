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

import org.hibernate.annotations.BatchSize;

import com.elian.cms.syst.model.PersistentLogInterface;

/**
 * 站点
 * 
 * @author CZH
 */
@javax.persistence.Entity
@Table(name = "T_SITE")
@BatchSize(size = 10)
public class Site implements PersistentLogInterface {
	private static final long serialVersionUID = 8409898151231967267L;
	private Integer id;
	/** 版本 */
	private Integer version;
	/** 等级ID */
	private Grade grade;
	// /** 模板路径 */
	// private String tempUrl;
	/** 模板 */
	private Template template;
	/*	*//** FTPID */
	/*
	 * private Ftp ftp;
	 */
	/** 组织ID */
	private Integer comId;
	/** 组织类型 */
	private String comType;
	/** 站点名称 */
	private String siteName;
	/** 站点简称 */
	private String shortName;
	/** 站点域名 */
	private String domain;
	/** 域名是否启用 */
	private boolean isDomainPass;
	/** 站点其他域名 */
	private String alias;
	/** 站点重定向 */
	private String redirect;
	/** 站点LOGO */
	private String logoImg;
	/** 排序 */
	private Integer siteSort;
	/** 创建时间 */
	private Date createTime;
	/** 站点路径 */
	private String sitePath;
	/** 创建人 */
	private String creater;
	/** 站点状态 */
	private Integer status;
	/**站点空间*/
	private Long siteSize;
	/**站点已使用空间*/
	private Long siteUsedSize;  
	@Id
	@Column(name = "site_id")
	// @SequenceGenerator(name = "siteGenerator", sequenceName = "S_T_SITE",
	// allocationSize = 1)
	// @GeneratedValue(generator = "siteGenerator", strategy =
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

	@ManyToOne()
	@JoinColumn(name = "grade_id")
	public Grade getGrade() {
		return grade;
	}

	public void setGrade(Grade grade) {
		this.grade = grade;
	}

	// @Column(name = "temp_url")
	// public String getTempUrl() {
	// return tempUrl;
	// }
	//
	// public void setTempUrl(String tempUrl) {
	// this.tempUrl = tempUrl;
	// }
	@ManyToOne()
	@JoinColumn(name = "temp_id")
	public Template getTemplate() {
		return template;
	}

	public void setTemplate(Template template) {
		this.template = template;
	}

	/*
	 * @ManyToOne(fetch = FetchType.LAZY)
	 * 
	 * @JoinColumn(name = "ftp_id") public Ftp getFtp() { return ftp; }
	 * 
	 * public void setFtp(Ftp ftp) { this.ftp = ftp; }
	 */

	@Column(name = "com_id", updatable = false)
	public Integer getComId() {
		return comId;
	}

	public void setComId(Integer comId) {
		this.comId = comId;
	}

	@Column(name = "com_type", updatable = false)
	public String getComType() {
		return comType;
	}

	public void setComType(String comType) {
		this.comType = comType;
	}

	@Column(name = "site_name")
	public String getSiteName() {
		return siteName;
	}

	public void setSiteName(String siteName) {
		this.siteName = siteName;
	}

	@Column(name = "short_name")
	public String getShortName() {
		return shortName;
	}

	public void setShortName(String shortName) {
		this.shortName = shortName;
	}

	public String getDomain() {
		return domain;
	}

	public void setDomain(String domain) {
		this.domain = domain;
	}

	@Column(name = "is_domain_pass")
	public boolean isDomainPass() {
		return isDomainPass;
	}

	public void setDomainPass(boolean isDomainPass) {
		this.isDomainPass = isDomainPass;
	}

	@Column(name = "domain_alias")
	public String getAlias() {
		return alias;
	}

	public void setAlias(String alias) {
		this.alias = alias;
	}

	@Column(name = "domain_redirect")
	public String getRedirect() {
		return redirect;
	}

	public void setRedirect(String redirect) {
		this.redirect = redirect;
	}

	@Column(name = "logo_img")
	public String getLogoImg() {
		return logoImg;
	}

	public void setLogoImg(String logoImg) {
		this.logoImg = logoImg;
	}

	@Column(name = "site_sort")
	public Integer getSiteSort() {
		return siteSort;
	}

	public void setSiteSort(Integer siteSort) {
		this.siteSort = siteSort;
	}

	@Column(name = "create_time")
	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	@Column(name = "site_path")
	public String getSitePath() {
		return sitePath;
	}

	public void setSitePath(String sitePath) {
		this.sitePath = sitePath;
	}

	@Column(name = "creater")
	public String getCreater() {
		return creater;
	}

	public void setCreater(String creater) {
		this.creater = creater;
	}
	
	
	
	@Column(name = "site_size")
	public Long getSiteSize() {
		return siteSize;
	}

	public void setSiteSize(Long siteSize) {
		this.siteSize = siteSize;
	}

	@Column(name = "site_used_size")
	public Long getSiteUsedSize() {
		return siteUsedSize;
	}

	public void setSiteUsedSize(Long siteUsedSize) {
		this.siteUsedSize = siteUsedSize;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null || !(o instanceof Site)) {
			return false;
		}
		return id == ((Site) o).getId();
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
		return new String[] { "站点", "siteName=" + siteName };
	}
}
