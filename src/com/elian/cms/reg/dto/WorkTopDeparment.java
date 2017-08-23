package com.elian.cms.reg.dto;

import java.util.List;

public class WorkTopDeparment {
	private Integer depaId;
	private Integer hospId;
	private String depaName;
	private String hospName;
	private String depaTypeName;//科种
	private List<WorkTopDoctor> doctors;

	public Integer getDepaId() {
		return depaId;
	}

	public void setDepaId(Integer depaId) {
		this.depaId = depaId;
	}

	public String getDepaName() {
		return depaName;
	}

	public void setDepaName(String depaName) {
		this.depaName = depaName;
	}

	public List<WorkTopDoctor> getDoctors() {
		return doctors;
	}

	public void setDoctors(List<WorkTopDoctor> doctors) {
		this.doctors = doctors;
	}

	public Integer getHospId() {
		return hospId;
	}

	public void setHospId(Integer hospId) {
		this.hospId = hospId;
	}

	public String getHospName() {
		return hospName;
	}

	public void setHospName(String hospName) {
		this.hospName = hospName;
	}

	public String getDepaTypeName() {
		return depaTypeName;
	}

	public void setDepaTypeName(String depaTypeName) {
		this.depaTypeName = depaTypeName;
	}
}
