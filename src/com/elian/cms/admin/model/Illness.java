package com.elian.cms.admin.model;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.elian.cms.syst.model.PersistentLogInterface;

/**
 * 疾病
 * 
 * @author thy
 * 
 */
@javax.persistence.Entity
@Table(name = "Illness")
public class Illness implements PersistentLogInterface {
	private static final long serialVersionUID = -3855671238253931704L;
	
	private Integer id;	
	/*** 疾病简体中文名*/
	private String illnessName;
	/*** 疾病繁体中文名*/
	private String illnessName2;
	/*** 简体内容*/
	private String contentXMLBak;
	/*** 繁体内容*/
	private String contentXML2;
	/*** 科室名*/
	private String department;
	/***病症ID集*/
	private String symLink;
	/***病症ID集数量*/
	private String symNum;
	/***是否常见病*/
	private boolean isOften=false;
	/***是否非常见病*/
	private boolean isNotOften=false;
	/***html内容*/
	private String html;
	/***疾病描述内容包含html代码*/
	private String contentXML;
	
    public Illness() {
		super();
	}

	public Illness(Integer id, String illnessName, String department,
			boolean isOften, boolean isNotOften) {
		super();
		this.id = id;
		this.illnessName = illnessName;
		this.department = department;
		this.isOften = isOften;
		this.isNotOften = isNotOften;
	}


	@Id
    @Column(name = "IllID")	   
	public Integer getId() {
		return id;
	}
    
	public void setId(Integer id) {
		this.id = id;
	}

	@Column(name="IllnessName")
	public String getIllnessName() {
		return illnessName;
	}

	public void setIllnessName(String illnessName) {
		this.illnessName = illnessName;
	}

	@Column(name="IllnessName2")
	public String getIllnessName2() {
		return illnessName2;
	}

	public void setIllnessName2(String illnessName2) {
		this.illnessName2 = illnessName2;
	}

	@Column(name="ContentXML_bak")
	public String getContentXMLBak() {
		return contentXMLBak;
	}

	public void setContentXMLBak(String contentXMLBak) {
		this.contentXMLBak = contentXMLBak;
	}

	@Column(name="contentXML2")
	public String getContentXML2() {
		return contentXML2;
	}

	public void setContentXML2(String contentXML2) {
		this.contentXML2 = contentXML2;
	}

	@Column(name="Department")
	public String getDepartment() {
		return department;
	}

	public void setDepartment(String department) {
		this.department = department;
	}

	@Column(name="SymLink")
	public String getSymLink() {
		return symLink;
	}

	public void setSymLink(String symLink) {
		this.symLink = symLink;
	}

	@Column(name="SYMNum")
	public String getSymNum() {
		return symNum;
	}

	public void setSymNum(String symNum) {
		this.symNum = symNum;
	}

	@Column(name="IsOften")
	public boolean isOften() {
		return isOften;
	}

	public void setOften(boolean isOften) {
		this.isOften = isOften;
	}

	@Column(name="IsnotOften")
	public boolean isNotOften() {
		return isNotOften;
	}

	public void setNotOften(boolean isNotOften) {
		this.isNotOften = isNotOften;
	}

	@Transient
	public String getHtml() {
		return html;
	}

	public void setHtml(String html) {
		this.html = html;
	}

	@Column(name="ContentXML")
	public String getContentXML() {
		return contentXML;
	}

	public void setContentXML(String contentXML) {
		this.contentXML = contentXML;
	}
	
	@Transient
	public String[] getSysLog() {
		return new String[] { "疾病", "illnessName=" + illnessName };
	}
}
