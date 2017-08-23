package com.elian.cms.admin.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.elian.cms.syst.model.PersistentLogInterface;

@javax.persistence.Entity
@Table(name = "T_LOG")
public class Log implements PersistentLogInterface{
	private static final long serialVersionUID = -2015907353915095941L;
	
	private Integer id;
	private User user;
	private int category;
	private Date createDate;
	private String ip;
	private String url;
	private String title;
	private String content;
	private Integer siteId;

	@Id
	@Column(name = "log_id")
	// @SequenceGenerator(name = "logGenerator", sequenceName = "S_T_LOG",
	// allocationSize = 1)
	// @GeneratedValue(generator = "logGenerator", strategy =
	// GenerationType.SEQUENCE)
	@GeneratedValue( strategy = GenerationType.AUTO)
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	@OneToOne
	@JoinColumn(name = "user_id")
	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public int getCategory() {
		return category;
	}

	public void setCategory(int category) {
		this.category = category;
	}

	@Column(name = "log_time")
	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}
	
	@Column(name = "site_id")
	public Integer getSiteId() {
		return siteId;
	}

	public void setSiteId(Integer siteId) {
		this.siteId = siteId;
	}
	
	@Transient
	public String[] getSysLog() {
		return new String[] { "日志", "title=" + title };
	}
}
