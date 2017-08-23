package com.elian.cms.admin.model;

import javax.persistence.Column;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.elian.cms.reg.model.DoctorComment;
import com.elian.cms.syst.model.PersistentLogInterface;

/**
 * 医生评论顶踩表
 * 
 * @author Gechuanyi
 * @version [0.1,2013-04-17]
 */
@javax.persistence.Entity
@Table(name = "RE_DOCT_COMMENT_USER")
public class DoctCommentUser implements PersistentLogInterface {
	private static final long serialVersionUID = 3941316269434689467L;
	/** 主键*/
	private Integer reId;
	/** 用户ID*/
	private User user;
	/** 医生评论ID*/
	private DoctorComment comment;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "re_id")
	public Integer getReId() {
		return this.reId;
	}

	public void setReId(Integer reId) {
		this.reId = reId;
	}

	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name = "user_id")
	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name = "comm_id")
	public DoctorComment getComment() {
		return comment;
	}

	public void setComment(DoctorComment comment) {
		this.comment = comment;
	}

	@Transient
	public Integer getId() {
		return reId;
	}
	
	@Transient
	public String[] getSysLog() {
		return new String[] { "医生评论顶踩", "reId=" + reId };
	}
}