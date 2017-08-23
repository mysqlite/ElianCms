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
import com.elian.cms.syst.model.SelectModel;

/**
 * 模板实体类
 * 
 * @author CZH
 */
@javax.persistence.Entity
@Table(name = "T_TEMPLATE")
public class Template extends SelectModel implements PersistentLogInterface {
	private static final long serialVersionUID = -7095444492283699302L;
	private Integer id;
	/** 版本 */
	private Integer version;
	/** 模板名称 */
	private String tempName;
	/** 父ID */
	private Integer parentId;
	/** 模板排序 */
	private int tempSort;
	/** 模板缩略图 */
	private String tempImg;
	/** 模板文件名称 */
	private String fileName;
	/** 创建人 */
	private String creater;
	/** 创建日期 */
	private Date createTime;
	/** 是否文件(文件或者文件夹) */
	private boolean isFile;
	/** 是否启用 */
	private boolean isDisable;
	
	/** 编辑模板文件名 */
	private String newFileName;
	/** 编辑文件内容 */
	private String fileContent;

	@Id
	@Column(name = "temp_id")
	@GeneratedValue(strategy = GenerationType.AUTO)
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	@Version
	public Integer getVersion() {
		return version;
	}

	public void setVersion(Integer version) {
		this.version = version;
	}

	@Column(name = "temp_name")
	public String getTempName() {
		return tempName;
	}

	public void setTempName(String tempName) {
		this.tempName = tempName;
	}
	
	@Column(name = "parent_Id")
	public Integer getParentId() {
		return parentId;
	}

	public void setParentId(Integer parentId) {
		this.parentId = parentId;
	}

	@Column(name = "temp_sort")
	public int getTempSort() {
		return tempSort;
	}

	public void setTempSort(int tempSort) {
		this.tempSort = tempSort;
	}
	
	@Transient
	public String getFileContent() {
		return fileContent;
	}

	public void setFileContent(String fileContent) {
		this.fileContent = fileContent;
	}
	
	@Transient
	public String getNewFileName() {
		return newFileName;
	}

	public void setNewFileName(String newFileName) {
		this.newFileName = newFileName;
	}

	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null || !(o instanceof Template)) {
			return false;
		}
		return id == ((Template) o).getId();
	}

	@Column(name = "temp_img")
	public String getTempImg() {
		return tempImg;
	}

	public void setTempImg(String tempImg) {
		this.tempImg = tempImg;
	}

	@Column(name = "temp_file_name")
	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public String getCreater() {
		return creater;
	}

	public void setCreater(String creater) {
		this.creater = creater;
	}

	@Column(name = "create_time")
	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	
	@Column(name = "is_file")
	public boolean isFile() {
		return isFile;
	}

	public void setFile(boolean isFile) {
		this.isFile = isFile;
	}

	@Column(name = "is_disable")
	public boolean isDisable() {
		return isDisable;
	}

	public void setDisable(boolean isDisable) {
		this.isDisable = isDisable;
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
		return new String[] { "模板", "tempName=" + tempName };
	}
}
