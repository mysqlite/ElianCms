package com.elian.cms.syst.model;

public class QyfyImgs {
	private String newUrl;
	private String sourUrl;
	private boolean editer;
	
	public boolean isEditer() {
		return editer;
	}

	public void setEditer(boolean editer) {
		this.editer = editer;
	}

	public QyfyImgs(String newsUrl,
			String sourUrl) {
		this.newUrl = newsUrl;
		this.sourUrl = sourUrl;
	}
	
	public QyfyImgs(String newsUrl,
			String sourUrl,boolean edit) {
		this.newUrl = newsUrl;
		this.sourUrl = sourUrl;
		this.editer=edit;
	}

	public QyfyImgs() {
	}

	public String getNewUrl() {
		return newUrl;
	}

	public void setNewUrl(String newUrl) {
		this.newUrl = newUrl;
	}

	public String getSourUrl() {
		return sourUrl;
	}

	public void setSourUrl(String sourUrl) {
		this.sourUrl = sourUrl;
	}
}
