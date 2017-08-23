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
 * 角色权限管理
 * 
 * @author Joe
 */
@javax.persistence.Entity
@Table(name = "RE_ROLE_ACTION")
public class RoleAction implements PersistentLogInterface{
	private static final long serialVersionUID = 761689702511779814L;
	
	private Integer id;
	private Role role;
	private Action action;
	private boolean isAuthorize;

	@Id
	@Column(name = "role_action_id")
	// @SequenceGenerator(name = "roleActionGenerator", sequenceName =
	// "S_RE_ROLE_ACTION", allocationSize = 1)
	// @GeneratedValue(generator = "roleActionGenerator", strategy =
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

	@ManyToOne
	@JoinColumn(name = "action_id")
	public Action getAction() {
		return action;
	}

	public void setAction(Action action) {
		this.action = action;
	}

	@Column(name = "is_authorize")
	public boolean isAuthorize() {
		return isAuthorize;
	}

	public void setAuthorize(boolean isAuthorize) {
		this.isAuthorize = isAuthorize;
	}
	
	@Transient
	public String[] getSysLog() {
		return new String[] { "角色权限管理", "id=" + id };
	}
}