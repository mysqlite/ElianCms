package com.elian.cms.admin.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.persistence.Version;

import com.elian.cms.syst.model.PersistentLogInterface;
@javax.persistence.Entity
@Table(name = "T_FILE")
public class SiteFile implements PersistentLogInterface {
	private static final long serialVersionUID = -7070338537416099594L;
	/**主键*/
	private Integer id;
	/**存储文件名称*/
	private String fileName;
	/**显示文件名称*/
	private String showName;
	/**文件大小*/
	private Long fileSize;
	/**上传时间*/
	private Date uploadTime;
	/**文件类型*/
	private String fileType;
	/**实体名称*/
	private String entityName;
	/**实体主键*/
	private Integer entityId;
	/**文件路径*/
	private String filePath;
	/**站点*/
	private Site site;
	/**用户*/
	private User user;
	/***/
	private boolean editor;
	/**版本*/
	private Integer	version;
	
	@Id
	@Column(name = "file_id")
	@GeneratedValue(strategy = GenerationType.AUTO)
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	@Column(name = "file_name")
	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	@Column(name = "file_show_name")
	public String getShowName() {
		return showName;
	}

	public void setShowName(String showName) {
		this.showName = showName;
	}

	@Column(name = "file_size")
	public Long getFileSize() {
		return fileSize;
	}

	public void setFileSize(Long fileSize) {
		this.fileSize = fileSize;
	}

	@Column(name = "upload_time")
	public Date getUploadTime() {
		return uploadTime;
	}

	public void setUploadTime(Date uploadTime) {
		this.uploadTime = uploadTime;
	}

	@Column(name = "file_type")
	public String getFileType() {
		return fileType;
	}

	public void setFileType(String fileType) {
		this.fileType = fileType;
	}

	@Column(name = "entity_name")
	public String getEntityName() {
		return entityName;
	}

	public void setEntityName(String entityName) {
		this.entityName = entityName;
	}

	@Column(name = "entity_id")
	public Integer getEntityId() {
		return entityId;
	}

	public void setEntityId(Integer entityId) {
		this.entityId = entityId;
	}

	@Column(name = "file_path")
	public String getFilePath() {
		return filePath;
	}

	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "site_id")
	public Site getSite() {
		return site;
	}

	public void setSite(Site site) {
		this.site = site;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "author")
	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}
	
	@Column(name = "is_editor")
	public boolean isEditor() {
		return editor;
	}

	public void setEditor(boolean editor) {
		this.editor = editor;
	}

	@Version
	public Integer getVersion() {
		return version;
	}

	public void setVersion(Integer version) {
		this.version = version;
	}

	public String toString() {
		return this.getClass().getName() + "[id=" + id + "]" + ",[version="
				+ version + "]";
	}
	
	@Transient
	public String[] getSysLog() {
		return new String[] { "站点文件", "fileName=" + fileName };
	}
}
