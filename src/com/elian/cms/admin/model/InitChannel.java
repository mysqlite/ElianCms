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

import com.elian.cms.syst.model.PersistentLogInterface;

@Entity
@Table(name="T_INITIAL_CHANNEL")
public class InitChannel implements PersistentLogInterface {	
	private static final long serialVersionUID = -6469277533531678100L;

	/** 栏目id */
	private Integer id;
	/** 模板id */
	private Integer templateId;
	/** 模型 */
	private Model model;
	/** 栏目名称 */
	private String channelName;
	/** 创建人 */
	private Integer creater;
	/** 创建时间 */
	private Date createTime;
	/** 父id */
	private Integer parentId;
	/** 排序 */
	private Integer sort;
	/** SEO关键字 */
	private String keywords;
	/** SEO标题 */
	private String title;
	/** SEO描述 */
	private String description;
	/** 栏目类型 */
	private String channelType;
	/** 内容类型 */
	private String contentType;
	/** 外部链接URL */
	private String outLinkUrl;
	/** 栏目模板 */
	private Template channelTemp;
	/** 内容模板 */
	private Template contentTemp;
	/** 是否静态化 */
	private boolean isStatic;
	/** 是否可用 */
	private boolean isDisable;
	/** 是否导航 */
	private boolean isNavigetor;
	/** 静态文件路径 */
	private String path;
	/** 版本号 */
	private Integer version;
	/** 前端排序 */
	private Integer frontSort=99;
	/** 细部文件静态化 */
	private boolean isDetailStatic;

	@Id
	@Column(name = "channel_id")
	@GeneratedValue( strategy = GenerationType.AUTO)
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	@Column(name="temp_id")
	public Integer getTemplateId() {
		return templateId;
	}

	public void setTemplateId(Integer templateId) {
		this.templateId = templateId;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "MODEL_ID")
	public Model getModel() {
		return model;
	}

	public void setModel(Model model) {
		this.model = model;
	}

	@Column(name = "CHANNEL_NAME")
	public String getChannelName() {
		return channelName;
	}

	public void setChannelName(String channelName) {
		this.channelName = channelName;
	}
	
	@Column(name = "links_url")
	public String getOutLinkUrl() {
		return outLinkUrl;
	}

	public void setOutLinkUrl(String outLinkUrl) {
		this.outLinkUrl = outLinkUrl;
	}
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "CHANNEL_TEMP_ID")
	public Template getChannelTemp() {
		return channelTemp;
	}

	public void setChannelTemp(Template channelTemp) {
		this.channelTemp = channelTemp;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "CONTENT_TEMP_ID")
	public Template getContentTemp() {
		return contentTemp;
	}

	public void setContentTemp(Template contentTemp) {
		this.contentTemp = contentTemp;
	}
	
	@Column(name = "is_channel_static")
	public boolean isStatic() {
		return isStatic;
	}

	public void setStatic(boolean isStatic) {
		this.isStatic = isStatic;
	}

	@Column(name = "is_navigetor")
	public boolean isNavigetor() {
		return isNavigetor;
	}

	public void setNavigetor(boolean isNavigetor) {
		this.isNavigetor = isNavigetor;
	}

	@Column(name = "CREATER")
	public Integer getCreater() {
		return creater;
	}

	public void setCreater(Integer creater) {
		this.creater = creater;
	}

	@Column(name = "CREATE_TIME")
	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	@Column(name = "PARENT_ID")
	public Integer getParentId() {
		return parentId;
	}

	public void setParentId(Integer parentId) {
		this.parentId = parentId;
	}

	@Column(name = "channel_SORT")
	public Integer getSort() {
		return sort;
	}

	public void setSort(Integer sort) {
		this.sort = sort;
	}

	@Column(name = "KEYWORDS")
	public String getKeywords() {
		return keywords;
	}

	public void setKeywords(String keywords) {
		this.keywords = keywords;
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
	
	@Column(name = "channel_Type")
	public String getChannelType() {
		return channelType;
	}

	public void setChannelType(String channelType) {
		this.channelType = channelType;
	}
	
	@Column(name = "channel_content_Type")
	public String getContentType() {
		return contentType;
	}

	public void setContentType(String contentType) {
		this.contentType = contentType;
	}
	
	@Column(name = "static_path")
	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	@Column(name = "IS_DISABLE")
	public boolean isDisable() {
		return isDisable;
	}
	
	public void setDisable(boolean disable) {
		this.isDisable = disable;
	}
	
	@Column(name = "front_Sort")
	public Integer getFrontSort() {
		return frontSort;
	}
	
	public void setFrontSort(Integer frontSort) {
		this.frontSort = frontSort;
	}
	
	@Transient
	public boolean isDetailStatic() {
		return isDetailStatic;
	}

	public void setDetailStatic(boolean isDetailStatic) {
		this.isDetailStatic = isDetailStatic;
	}

	@Version
	public Integer getVersion() {
		return version;
	}

	public void setVersion(Integer version) {
		this.version = version;
	}

	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null || !(o instanceof Channel)) {
			return false;
		}
		return id.equals(((Channel) o).getId());
	}

	public int hashCode() {
		return id.hashCode();
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
	
	public Channel toChannel(Site site,Channel parent){
		Channel channel=new Channel();
		channel.setChannelName(getChannelName());
		channel.setChannelTemp(getChannelTemp());
		channel.setContentTemp(getContentTemp());
		channel.setChannelType(getChannelType());
		channel.setContentType(getContentType());
		channel.setCreater(getCreater());
		channel.setCreateTime(getCreateTime());
		channel.setDescription(getDescription());
		channel.setDetailStatic(isDetailStatic());
		channel.setDisable(isDisable());
		channel.setFrontSort(getFrontSort());
		channel.setKeywords(getKeywords());
		channel.setModel(getModel());
		channel.setNavigetor(isNavigetor());
		channel.setOutLinkUrl(getOutLinkUrl());
		channel.setParentId(parent==null?0:parent.getId());
		channel.setPath(getPath());
		channel.setSite(site);
		channel.setSort(getSort());
		channel.setStatic(true);
		channel.setTitle(getTitle());
		return channel;
	}
	
	@Transient
	public String[] getSysLog() {
		return new String[] { "栏目初始化", "id=" + id };
	}
}
