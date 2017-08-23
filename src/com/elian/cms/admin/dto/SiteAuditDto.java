package com.elian.cms.admin.dto;

import java.util.Date;

import com.elian.cms.admin.model.SiteUser;

public class SiteAuditDto {
	private SiteUser siteUser;
	private String compName;
	private int status;
	private int compId;
	private Date auditTime;
	private String auditor;
	private String remarks;
	
	public int getCompId() {
		return compId;
	}
	public void setCompId(int compId) {
		this.compId = compId;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public String getCompName() {
		return compName;
	}
	public void setCompName(String compName) {
		this.compName = compName;
	}
	public SiteUser getSiteUser() {
		return siteUser;
	}
	public void setSiteUser(SiteUser siteUser) {
		this.siteUser = siteUser;
	}
	public Date getAuditTime() {
		return auditTime;
	}
	public void setAuditTime(Date auditTime) {
		this.auditTime = auditTime;
	}
	public String getAuditor() {
		return auditor;
	}
	public void setAuditor(String auditor) {
		this.auditor = auditor;
	}
	public String getRemarks() {
		return remarks;
	}
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
	
}
