package com.elian.cms.admin.model;

import java.util.Date;
import java.util.Random;

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
import com.elian.cms.syst.util.ElianCodes;

/**
 * 资讯
 * 
 * @author Joe
 */
@Entity
@Table(name = "T_INFORMATION")
public class Information extends BaseContent implements SeoInterface, PersistentLogInterface {
	private static final long serialVersionUID = 8050000531195827507L;

	/** ID */
	private Integer id;
	/** 版本 */
	private Integer version;
	/** 标题 */
	private String title;
	/** 标题颜色 */
	private String titleColor;
	/** 标题是否加粗 */
	private boolean isTitleBold;
	/** 副标题 */
	private String subTitle;
	/** 摘要 */
	private String description;
	/** 关键字 */
	private String keywords;
	/** 来源名称 */
	private String sourceName;
	/** 来源URL */
	private String sourceUrl;
	/** 创建人 */
	private User creater;
	/** 创建日期 */
	private Date createTime;
	/** 作者 */
	private String author;
	/** 发布时间 */
	private Date publishTime;
	/** 标题图片 */
	private String infoImg;
	/** 是否有标题图  (0,普通;) (1,图文;) (2,切换;)*/
	private Integer hasImg;
	/** 内容 */
	private String content;
	/** 是否启用 */
	private boolean isDisable;
	/**附件路径*/
	private String filePath;
	/**预览器[视频动画类、阅读类]*/
	private boolean previewMode;
	@Id
	@Column(name = "info_id")
	// @SequenceGenerator(name = "informationGenerator", sequenceName =
	// "S_T_INFORMATION", allocationSize = 1)
	// @GeneratedValue(generator = "informationGenerator", strategy =
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

	@Column(name = "title_color")
	public String getTitleColor() {
		return titleColor;
	}

	public void setTitleColor(String titleColor) {
		this.titleColor = titleColor;
	}

	@Column(name = "is_title_bold")
	public boolean isTitleBold() {
		return isTitleBold;
	}

	public void setTitleBold(boolean isTitleBold) {
		this.isTitleBold = isTitleBold;
	}

	@Column(name = "sub_title")
	public String getSubTitle() {
		return subTitle;
	}

	public void setSubTitle(String subTitle) {
		this.subTitle = subTitle;
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
	@JoinColumn(name = "creater_ID")
	public User getCreater() {
		return creater;
	}

	public void setCreater(User creater) {
		this.creater = creater;
	}

	@Column(name = "create_time")
	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	@Column(name = "publish_time")
	public Date getPublishTime() {
		return publishTime;
	}

	public void setPublishTime(Date publishTime) {
		this.publishTime = publishTime;
	}

	@Column(name = "info_img")
	public String getInfoImg() {
		return infoImg;
	}

	public void setInfoImg(String infoImg) {
		this.infoImg = infoImg;
	}

	@Column(name = "has_img")
	public Integer getHasImg() {
		return hasImg;
	}

	public void setHasImg(Integer hasImg) {
		this.hasImg =hasImg;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	@Column(name = "is_disable")
	public boolean isDisable() {
		return isDisable;
	}

	public void setDisable(boolean isDisable) {
		this.isDisable = isDisable;
	}
	

	@Column(name = "file_path")
	public String getFilePath() {
		return filePath;
	}

	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}

	@Column(name = "preview_mode")
	public boolean isPreviewMode() {
		return previewMode;
	}

	public void setPreviewMode(boolean previewMode) {
		this.previewMode = previewMode;
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
		return this.id.equals(((Information) o).id);
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
	
	@Override
	public void initWithDefaultData(User user,Channel channel) {
		Random random=new Random();
		this.setDisable(true);
		this.setTitle(channel.getChannelName()+"***资讯默认数据的标题");
		this.setTitleColor("#000000");
		this.setHasImg(random.nextInt(3));
		this.setInfoImg(ElianCodes.INFORMATION_DEFAULT_IMG);
		this.setTitleBold(false);
		this.setCreateTime(new Date());
		this.setPublishTime(new Date());
		this.setCreater(user);
		this.setContent(channel.getChannelName()+"***资讯默认数据");
		this.setDescription(channel.getChannelName()+"***资讯默认数据的描述");
	}
	
	@Transient
	public String[] getSysLog() {
		return new String[] { "资讯", "title=" + title };
	}
}
