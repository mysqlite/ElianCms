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

import com.elian.cms.syst.model.PersistentLogInterface;

/**
 * 用户 - 角色 映射表
 * 
 *@author Gechuanyi
 */
@javax.persistence.Entity
@Table(name = "RE_USER_ROLE")
public class UserRole implements PersistentLogInterface{
	private static final long serialVersionUID = 1916965362860472590L;
	
	private Integer id;
	private Role role;
	private User user;

	@Id
	@Column(name = "user_role_id")
	// @SequenceGenerator(name = "userRoleGenerator", sequenceName =
	// "S_RE_USER_ROLE", allocationSize = 1)
	// @GeneratedValue(generator = "userRoleGenerator", strategy =
	// GenerationType.SEQUENCE)
	@GeneratedValue( strategy = GenerationType.AUTO)
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	@ManyToOne
	@JoinColumn(name = "role_id")
	public Role getRole() {
		return role;
	}

	public void setRole(Role role) {
		this.role = role;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "user_id")
	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	@Transient
	public String[] getSysLog() {
		return new String[] { "用户角色映射", "id=" + id };
	}
}