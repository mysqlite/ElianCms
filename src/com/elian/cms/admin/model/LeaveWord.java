package com.elian.cms.admin.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.persistence.Version;

import com.elian.cms.syst.model.PersistentInterface;

@Entity
@Table(name="T_GUESTBOOK")
public class LeaveWord implements PersistentInterface{
	private static final long serialVersionUID = 6757613835228604138L;
	
	private Integer id;
	/**站点id*/
	private Integer siteId;
	/**真实姓名*/
	private String realName;
	/** 用户的性别(男士，女士，保密) */
	private String gender;
	/** 电子邮箱*/
	private String email;
	/** 手机号码*/
	private String phoneNumber;
	/** 创建时间*/
	private Date createDate;
	/** 留言标题*/
	private String title;
	/** 留言内容*/
	private String content;
	/** 父id*/
	private Integer parentId;
	/** 是否回复*/
	private boolean isReply=false;
	/**状态*/
	private Integer status;
	/**版本号*/
	private Integer version;
	
	@Id
	@Column(name = "guestbook_id")
	@GeneratedValue( strategy = GenerationType.AUTO)
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	
	@Column(name="site_id")
	public Integer getSiteId() {
		return siteId;
	}
	public void setSiteId(Integer siteId) {
		this.siteId = siteId;
	}
	
	@Column(name="real_name")
	public String getRealName() {
		return realName;
	}
	public void setRealName(String realName) {
		this.realName = realName;
	}
	
	@Column(name="gender")
	public String getGender() {
		return gender;
	}
	public void setGender(String gender) {
		this.gender = gender;
	}
	
	@Column(name="email")
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	
	@Column(name="mobile")
	public String getPhoneNumber() {
		return phoneNumber;
	}
	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}
	
	@Column(name="create_time")
	public Date getCreateDate() {
		return createDate;
	}
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
	
	@Column(name="msg_title")
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	
	@Column(name="msg_content")
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	
	@Column(name="parent_id")
	public Integer getParentId() {
		return parentId;
	}
	public void setParentId(Integer parentId) {
		this.parentId = parentId;
	}
	
	@Column(name="is_reply")
	public boolean isReply() {
		return isReply;
	}
	public void setReply(boolean isReply) {
		this.isReply = isReply;
	}
	@Column(name="status")
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	
	@Version
	public Integer getVersion() {
		return version;
	}
	public void setVersion(Integer version) {
		this.version = version;
	}
	
	@Transient
	public String[] getSysLog() {
		return new String[] { "留言", "title=" + title };
	}
}
