package com.elian.cms.admin.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.persistence.Version;

import com.elian.cms.syst.model.PersistentLogInterface;

@Entity
@Table(name="T_SITE_INCLUDE")
public class SiteInclude implements PersistentLogInterface{
	private static final long serialVersionUID = -3108993790581608114L;
	
	private Integer id;
	private Integer siteId;
	/**模板（列：总站，分站模板）的id**/
	private Integer tempId;
	/**模板的名字**/
	private String fileName;
	private String content;
	private Integer version;
	
	@Id
	@Column(name="site_include_id")
	@GeneratedValue(strategy=GenerationType.AUTO)
	public Integer getId() {
		return id;
	}
	@Column(name="site_id")
	public Integer getSiteId() {
		return siteId;
	}
	@Column(name="temp_parent_id")
	public Integer getTempId() {
		return tempId;
	}
	@Column(name="file_name")
	public String getFileName() {
		return fileName;
	}
	@Column(name="content")
	public String getContent() {
		return content;
	}
	@Version
	public Integer getVersion() {
		return version;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public void setSiteId(Integer siteId) {
		this.siteId = siteId;
	}
	public void setTempId(Integer tempId) {
		this.tempId = tempId;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public void setVersion(Integer version) {
		this.version = version;
	}
	@Override
	public String toString() {
		return "siteId="+siteId+";tempId="+tempId+";fileName="+fileName+";version="+version;
	}
	
	@Transient
	public String[] getSysLog() {
		return new String[] { "站点包含", "id=" + id };
	}
}
