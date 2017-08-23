package com.elian.cms.reg.dto;

public class HospitalNoScore {
	private Integer hospId;
	private String hospName;
	private String storeName;
	private Integer noScoreCount;

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

	public Integer getNoScoreCount() {
		return noScoreCount;
	}

	public void setNoScoreCount(Integer noScoreCount) {
		this.noScoreCount = noScoreCount;
	}

	public String getStoreName() {
		return storeName;
	}

	public void setStoreName(String storeName) {
		this.storeName = storeName;
	}
}
