package com.elian.cms.admin.model;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.elian.cms.syst.model.PersistentLogInterface;

/**
 * 站点 - 用户 映射表
 * 
 * @author CZH
 */
@javax.persistence.Entity
@Table(name = "RE_SITE_USER")
public class SiteUser implements PersistentLogInterface{
	private static final long serialVersionUID = 4646337148543507625L;
	
	private Integer id;
	private User user;
	private Site site;

	@Id
	@Column(name = "site_user_id")
	// @SequenceGenerator(name = "siteUserGenerator", sequenceName =
	// "S_RE_SITE_USER", allocationSize = 1)
	// @GeneratedValue(generator = "siteUserGenerator", strategy =
	// GenerationType.SEQUENCE)
	@GeneratedValue( strategy = GenerationType.AUTO)
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	@ManyToOne
	@JoinColumn(name = "user_id")
	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	@ManyToOne
	@JoinColumn(name = "site_id")
	public Site getSite() {
		return site;
	}

	public void setSite(Site site) {
		this.site = site;
	}

	@Transient
	public String[] getSysLog() {
		return new String[] { "站点用户映射", "id=" + id };
	}
}