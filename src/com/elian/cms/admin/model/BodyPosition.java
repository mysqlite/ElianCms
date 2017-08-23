package com.elian.cms.admin.model;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.elian.cms.syst.model.PersistentLogInterface;

/**
 * 身体部位
 * 
 * @author thy
 * 
 */
@javax.persistence.Entity
@Table(name = "BodyPosition")
public class BodyPosition implements PersistentLogInterface {
	private static final long serialVersionUID = -3855671238253931704L;
	
	private Integer id;
	/*** 身体部位名称 */
	private String bodyName;
	/*** 疾病症状id集 */
	private String symLink;
	/*** 疾病ID集*/
	private String illLink;
	
    @Id
    @Column(name = "BodID")	   
	public Integer getId() {
		return id;
	}
    
	public void setId(Integer id) {
		this.id = id;
	}
	
	@Column(name="bodyName")
	public String getBodyName() {
		return bodyName;
	}
	
	public void setBodyName(String bodyName) {
		this.bodyName = bodyName;
	}
	
	@Column(name="symLink")
	public String getSymLink() {
		return symLink;
	}
	
	public void setSymLink(String symLink) {
		this.symLink = symLink;
	}
	
	@Column(name="illLink")
	public String getIllLink() {
		return illLink;
	}
	
	public void setIllLink(String illLink) {
		this.illLink = illLink;
	}
	@Transient
	public String[] getSysLog() {
		return new String[] { "身体部位", "bodyName=" + bodyName };
	}
}
