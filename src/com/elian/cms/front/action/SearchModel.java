package com.elian.cms.front.action;


public class SearchModel {
	/** 标题 */
	private String title;
	/** 地址 */
	private String address;
	/** 摘要 */
	private String summary;
	/** 路径 */
	private String path;
	/** 时间 */
	private String date;
	/** 图片路径 */
	private String imgPath;
	/** 职务 */
	private String dutyName;
	/** 价格 */
	private String price;
	/**企业名称*/
	private String compName;
	/**企业地址*/
    private String compAddress;	
    /**原title*/
    private String realTitle;
    
	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}
	
	public String getRealTitle() {
		return realTitle;
	}

	public void setRealTitle(String realTitle) {
		this.realTitle = realTitle;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getSummary() {
		return summary;
	}

	public void setSummary(String summary) {
		this.summary = summary;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getImgPath() {
		return imgPath;
	}

	public void setImgPath(String imgPath) {
		this.imgPath = imgPath;
	}
	
	public String getDutyName() {
		return dutyName;
	}

	public void setDutyName(String dutyName) {
		this.dutyName = dutyName;
	}
	
	public String getPrice() {
		return price;
	}

	public void setPrice(String price) {
		this.price = price;
	}

	public String getCompName() {
		return compName;
	}

	public void setCompName(String compName) {
		this.compName = compName;
	}

	public String getCompAddress() {
		return compAddress;
	}

	public void setCompAddress(String compAddress) {
		this.compAddress = compAddress;
	}
}