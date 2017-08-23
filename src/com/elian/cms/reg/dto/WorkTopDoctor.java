package com.elian.cms.reg.dto;

import com.elian.cms.syst.util.FilePathUtils;

public class WorkTopDoctor {
	private Integer doctorId;
	private Integer depaId;
	private Integer hospId;
	private String doctorName;
	private String doctImg;
	private String speciality;// 主治、专长
	private String jobTitle;// 职位
	private Integer overNo;// 剩余挂号数
	private String hospName;// 医院名称
	private double avgScore;// 综合评分

	public Integer getDoctorId() {
		return doctorId;
	}

	public void setDoctorId(Integer doctorId) {
		this.doctorId = doctorId;
	}

	public Integer getDepaId() {
		return depaId;
	}

	public void setDepaId(Integer depaId) {
		this.depaId = depaId;
	}

	public Integer getHospId() {
		return hospId;
	}

	public void setHospId(Integer hospId) {
		this.hospId = hospId;
	}

	public String getDoctorName() {
		return doctorName;
	}

	public void setDoctorName(String doctorName) {
		this.doctorName = doctorName;
	}

	public String getDoctImg() {
		return FilePathUtils.getImgPath(doctImg);
	}

	public void setDoctImg(String doctImg) {
		this.doctImg = doctImg;
	}

	public String getSpeciality() {
		return speciality;
	}

	public void setSpeciality(String speciality) {
		this.speciality = speciality;
	}

	public Integer getOverNo() {
		return overNo;
	}

	public void setOverNo(Integer overNo) {
		this.overNo = overNo;
	}

	public String getHospName() {
		return hospName;
	}

	public void setHospName(String hospName) {
		this.hospName = hospName;
	}

	public double getAvgScore() {
		return avgScore;
	}

	public void setAvgScore(double avgScore) {
		this.avgScore = avgScore;
	}

	public String getJobTitle() {
		return jobTitle;
	}

	public void setJobTitle(String jobTitle) {
		this.jobTitle = jobTitle;
	}
}
