package com.elian.cms.front.action;

public class DoctorSearchModel extends SearchModel{
	/** 医生id*/
	private String doctId;
	/** 站点id */
	private String siteId;
	/** 科室id */
	private String deptId;
	/** 是否开通挂号 */
	
	private String isReg;
	
	public String getDoctId() {
		return doctId;
	}
	public void setDoctId(String doctId) {
		this.doctId = doctId;
	}
	public String getSiteId() {
		return siteId;
	}
	public void setSiteId(String siteId) {
		this.siteId = siteId;
	}
	public String getDeptId() {
		return deptId;
	}
	public void setDeptId(String deptId) {
		this.deptId = deptId;
	}
	public String getIsReg() {
		return isReg;
	}
	public void setIsReg(String isReg) {
		this.isReg = isReg;
	}
}