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

@Entity
@Table(name="T_BIDDING")
public class Bidding extends BaseContent implements SeoInterface, PersistentLogInterface {
	private static final long serialVersionUID = -5511484629355434223L;
	/** 中标ID*/
	private Integer id;
	/** 项目名称 */
	private String projectName;
	/**  中标发布时间*/
	private Date bidTime;
	/**  发布时间*/
	private Date publishTime;
	/**  中标内容*/
	private String bidDesc;
	/**  中标单位*/
	private String bidCompany;
	/**  中标金额*/
	private String bidSum;
	/**  区域*/
	private Integer areaId;
	/**  标题*/
	private String title;
	/**  摘要*/
	private String description;
	/**  关键字*/
	private String keywords;
	/**  来源名称*/
	private String sourceName;
	/**  来源URL*/
	private String sourceUrl;
	/**  创建人*/
	private User creater;
	/**  创建时间*/
	private Date createTime;
	/**  是否启用*/
	private boolean disable;
	/**  版本号*/
	private Integer version;
	
	@Id
	@Column(name = "BID_ID")
	@GeneratedValue(strategy=GenerationType.AUTO)
//	@SequenceGenerator(name = "bidGenerator", sequenceName = "S_T_BIDDING", allocationSize = 1)
//	@GeneratedValue(generator = "bidGenerator", strategy = GenerationType.SEQUENCE)
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	@Column(name = "PROJECT_NAME")
	public String getProjectName() {
		return projectName;
	}

	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}
	
	@Column(name = "BID_TIME")
	public Date getBidTime() {
		return bidTime;
	}

	public void setBidTime(Date bidTime) {
		this.bidTime = bidTime;
	}

	@Column(name = "PUBLISH_TIME")
	public Date getPublishTime() {
		return publishTime;
	}

	public void setPublishTime(Date publishTime) {
		this.publishTime = publishTime;
	}

	@Column(name = "BID_DECS")
	public String getBidDesc() {
		return bidDesc;
	}

	public void setBidDesc(String bidDesc) {
		this.bidDesc = bidDesc;
	}

	@Column(name = "BID_COMPANY")
	public String getBidCompany() {
		return bidCompany;
	}

	public void setBidCompany(String bidCompany) {
		this.bidCompany = bidCompany;
	}

	@Column(name = "BID_SUM")	
	public String getBidSum() {
		return bidSum;
	}
	
	public void setBidSum(String bidSum) {
		this.bidSum = bidSum;
	}	

	@Column(name = "AREA_ID")
	public Integer getAreaId() {
		return areaId;
	}

	public void setAreaId(Integer areaId) {
		this.areaId = areaId;
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

	@Column(name = "SOURCE_NAME")
	public String getSourceName() {
		return sourceName;
	}

	public void setSourceName(String sourceName) {
		this.sourceName = sourceName;
	}

	@Column(name = "SOURCE_URL")
	public String getSourceUrl() {
		return sourceUrl;
	}

	public void setSourceUrl(String sourceUrl) {
		this.sourceUrl = sourceUrl;
	}
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "creater")
	public User getCreater() {
		return creater;
	}

	public void setCreater(User creater) {
		this.creater = creater;
	}

	@Column(name = "CREATE_TIME")
	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	@Column(name = "IS_DISABLE")
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

	public boolean equals(Object o) {
		if (o == this)
			return true;
		if (!(o instanceof Information))
			return false;
		return this.id.equals(((Bidding) o).id);
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
	public String[] getSysLog() {
		return new String[] { "中标", "projectName=" + projectName };
	}
}
