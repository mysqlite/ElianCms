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
@Table(name = "T_INVITATION")
public class Invitation extends BaseContent implements SeoInterface, PersistentLogInterface {
	private static final long serialVersionUID = 3017012425778885409L;
	/** 招标ID */
	private Integer id;
	/** 招标企业名称 */
	private String publisher;
	/** 项目名称 */
	private String projectName;
	/** 采购方式 */
	private String invitType;
	/** 区域 */
	private Integer areaId;
	/** 简要说明 */
	private String invitDesc;
	/** 招标发布日期 */
	private Date publishTime;
	/** 招标终止日期 */
	private Date expireTime;
	/** 联系人 */
	private String contacter;
	/** 联系人电话 */
	private String contacterPhone;
	/** 标题 */
	private String title;
	/** 摘要 */
	private String description;
	/** 关键字 */
	private String keywords;
	/** 来源名称 */
	private String sourceName;
	/** 来源ＵＲＬ */
	private String sourceUrl;
	/** 创建人     不显示 */   
	private User creater;	
	/** 创建时间 */
	private Date createTime;
	/** 是否启用 */
	private boolean disable;
	/** 版本号 */
	private Integer version;

	
	@Id
	@Column(name = "INVIT_ID")
	@GeneratedValue(strategy=GenerationType.AUTO)
	//@SequenceGenerator(name = "invitGenerator", sequenceName = "S_T_INVITATION", allocationSize = 1)
	//@GeneratedValue(generator = "invitGenerator", strategy = GenerationType.SEQUENCE)
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getPublisher() {
		return publisher;
	}

	public void setPublisher(String publisher) {
		this.publisher = publisher;
	}
	@Column(name = "PROJECT_NAME")
	public String getProjectName() {
		return projectName;
	}

	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}
	@Column(name = "INVIT_TYPE")
	public String getInvitType() {
		return invitType;
	}

	public void setInvitType(String invitType) {
		this.invitType = invitType;
	}

	@Column(name = "AREA_ID")
	public Integer getAreaId() {
		return areaId;
	}

	public void setAreaId(Integer areaId) {
		this.areaId = areaId;
	}

	@Column(name = "INVIT_DESC")
	public String getInvitDesc() {
		return invitDesc;
	}

	public void setInvitDesc(String invitDesc) {
		this.invitDesc = invitDesc;
	}

	@Column(name = "PUBLISH_TIME")
	public Date getPublishTime() {
		return publishTime;
	}

	public void setPublishTime(Date publishTime) {
		this.publishTime = publishTime;
	}

	@Column(name = "EXPIRE_TIME")
	public Date getExpireTime() {
		return expireTime;
	}

	public void setExpireTime(Date expireTime) {
		this.expireTime = expireTime;
	}
	
	@Column(name="contacter")
	public String getContacter() {
		return contacter;
	}

	public void setContacter(String contacter) {
		this.contacter = contacter;
	}

	@Column(name="contact_phone")
	public String getContacterPhone() {
		return contacterPhone;
	}

	public void setContacterPhone(String contacterPhone) {
		this.contacterPhone = contacterPhone;
	}
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "creater")
	public User getCreater() {
		return creater;
	}

	public void setCreater(User creater) {
		this.creater = creater;
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
		return this.id.equals(((Invitation) o).id);
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
		return new String[] { "招标", "id=" + id };
	}
}
