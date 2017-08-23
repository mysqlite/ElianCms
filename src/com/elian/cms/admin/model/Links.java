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

import com.elian.cms.syst.model.BaseContent;
import com.elian.cms.syst.model.PersistentLogInterface;

/**
 * @author Gechuanyi
 * @creat Time 20121212
 */
@javax.persistence.Entity
@Table(name = "T_FRIENDLINK")
public class Links extends BaseContent implements PersistentLogInterface {
	private static final long serialVersionUID = -8756492145094171360L;
	private Integer id;
	/**连接名称*/
	private String title;
	/**连接地址*/
	private String linkUrl;
	/**logo图片地址*/
	private String logoImg;
    /**邮箱*/
	private String email;
	/**描述*/
	private String description;
	/**创建时间*/
	private Date createTime;
    /**是否显示*/
	private boolean disable;
	/**创建人*/
	private User creater;
	/**版本号*/
	private Integer version;
	
	
	@Id
	@Column(name = "link_id")
	@GeneratedValue(strategy = GenerationType.AUTO)
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	@Column(name = "link_name")
	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	@Column(name = "link_url")
	public String getLinkUrl() {
		return linkUrl;
	}

	public void setLinkUrl(String linkUrl) {
		this.linkUrl = linkUrl;
	}

	@Column(name = "logo_img")
	public String getLogoImg() {
		return logoImg;
	}

	public void setLogoImg(String logoImg) {
		this.logoImg = logoImg;
	}

	@Column(name = "email")
	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	@Column(name = "description")
	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@Column(name = "create_time")
	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	@Column(name = "is_disable")
	public boolean isDisable() {
		return disable;
	}

	public void setDisable(boolean disable) {
		this.disable = disable;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "creater")
	public User getCreater() {
		return creater;
	}

	public void setCreater(User creater) {
		this.creater = creater;
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
		if (!(o instanceof Links))
			return false;
		return this.id.equals(((Links) o).id);
	}
	
	@Override
	public void initWithDefaultData(User user,Channel channel) {
		this.setTitle(channel.getChannelName()+"***医联网");
		this.setLinkUrl("http://www.elian.cc/");
		this.setCreateTime(new Date());
		this.setCreater(user);
	}
	
	@Transient
	public String[] getSysLog() {
		return new String[] { "连接", "title=" + title };
	}
}