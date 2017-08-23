package com.elian.cms.admin.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.persistence.Version;

import com.elian.cms.syst.model.PersistentLogInterface;

/***
 * 图片
 * 
 * @author Gechuanyi
 * 
 */
@javax.persistence.Entity
@Table(name = "T_IMAGES")
public class Images implements PersistentLogInterface {
	private static final long serialVersionUID = 8752295716758706134L;
	/** 图片id */
	private Integer id;
	/** 图片名称 */
	private String imagesName;
	/** 组织id */
	private Integer siteId;
	/** 实体名称 */
	private String entityName;
	/** 主键ID */
	private Integer entityId;
	/** 创建时间 */
	private Date createTime;
	/** 是否编辑器 */
	private boolean editor;
	/** 图片路径 */
	private String imagesPath;
	/** 版本号*/
	private Integer version;
	
	private String importImgpath;
	
	private long fileSize;
	@Id
	@Column(name = "IMAGES_ID")
	// @SequenceGenerator(name = "imgGenerator", sequenceName = "S_T_IMAGES",
	// allocationSize = 1)
	// @GeneratedValue(generator = "imgGenerator", strategy =
	// GenerationType.SEQUENCE)
	@GeneratedValue( strategy = GenerationType.AUTO)
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	@Column(name = "IMAGES_NAME")
	public String getImagesName() {
		return imagesName;
	}

	public void setImagesName(String imagesName) {
		this.imagesName = imagesName;
	}

	@Column(name = "SITE_ID")
	public Integer getSiteId() {
		return siteId;
	}

	public void setSiteId(Integer siteId) {
		this.siteId = siteId;
	}

	@Column(name = "ACTION_NAME")
	public String getEntityName() {
		return entityName;
	}

	public void setEntityName(String entityName) {
		this.entityName = entityName;
	}

	@Column(name = "TABLE_KEY_ID")
	public Integer getEntityId() {
		return entityId;
	}

	public void setEntityId(Integer entityId) {
		this.entityId = entityId;
	}

	@Column(name = "CREATE_TIME")
	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	@Column(name = "IS_EDITOR")
	public boolean isEditor() {
		return editor;
	}

	public void setEditor(boolean editor) {
		this.editor = editor;
	}

	@Column(name = "IMAGES_PATH")
	public String getImagesPath() {
		return imagesPath;
	}

	public void setImagesPath(String imagesPath) {
		this.imagesPath = imagesPath;
	}
	
	@Column(name = "import_img_path")
	public String getImportImgpath() {
		return importImgpath;
	}

	public void setImportImgpath(String importImgpath) {
		this.importImgpath = importImgpath;
	}
	
	
	@Version
	public Integer getVersion() {
		return version;
	}

	public void setVersion(Integer version) {
		this.version = version;
	}

	@Column(name = "file_size")
	public long getFileSize() {
		return fileSize;
	}

	public void setFileSize(long fileSize) {
		this.fileSize = fileSize;
	}

	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null || !(o instanceof User)) {
			return false;
		}
		return id == ((User) o).getId();
	}

	public int hashCode() {
		return id;
	}

	public String toString() {
		return this.getClass().getName() + "[id=" + id + "]" + ",[version="
				+ version + "]";
	}
	
	@Transient
	public String[] getSysLog() {
		return new String[] { "图片", "imagesName=" + imagesName };
	}
}
