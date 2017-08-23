package com.elian.cms.admin.model;

import javax.persistence.Column;
import javax.persistence.Entity;
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
import com.elian.cms.syst.model.SelectModel;

/**
 * 模型
 * 
 * @author Joe
 */
@Entity
@Table(name = "T_MODEL")
public class Model extends SelectModel implements PersistentLogInterface {
	private static final long serialVersionUID = 1466835363617293328L;

	private Integer id;
	/** 版本 */
	private Integer version;
	/** 模型名称 */
	private String modelName;
	/** 栏目模板前缀 */
	private String channelTempPrefix;
	/** 内容模板前缀 */
	private String contentTempPrefix;
	/** 列表模板前缀 */
	private String listTempPrefix;
	/** 栏目模板路径 */
	private String channelTempUrl;
	/** 内容模板路径 */
	private String contentTempUrl;
	/** 列表模板路径 */
	private String listTempUrl;
	/** 组织类型 */
	private String compType;
	/** 内容模型 */
	private ContentModel contentModel;
	/** 排序 */
	private int modelSort;
	/** 是否可用 */
	private boolean isDisable;

	@Id
	@Column(name = "model_id")
	// @SequenceGenerator(name = "modelGenerator", sequenceName = "S_T_MODEL",
	// allocationSize = 1)
	// @GeneratedValue(generator = "modelGenerator", strategy = GenerationType.SEQUENCE)
	@GeneratedValue( strategy = GenerationType.AUTO)
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

	@Column(name = "model_name")
	public String getModelName() {
		return modelName;
	}

	public void setModelName(String modelName) {
		this.modelName = modelName;
	}

	@Column(name = "channel_temp_prefix")
	public String getChannelTempPrefix() {
		return channelTempPrefix;
	}

	public void setChannelTempPrefix(String channelTempPrefix) {
		this.channelTempPrefix = channelTempPrefix;
	}

	@Column(name = "content_temp_prefix")
	public String getContentTempPrefix() {
		return contentTempPrefix;
	}

	public void setContentTempPrefix(String contentTempPrefix) {
		this.contentTempPrefix = contentTempPrefix;
	}

	@Column(name = "channel_temp_url")
	public String getChannelTempUrl() {
		return channelTempUrl;
	}

	public void setChannelTempUrl(String channelTempUrl) {
		this.channelTempUrl = channelTempUrl;
	}

	@Column(name = "content_temp_url")
	public String getContentTempUrl() {
		return contentTempUrl;
	}

	public void setContentTempUrl(String contentTempUrl) {
		this.contentTempUrl = contentTempUrl;
	}
	
	@Column(name = "list_temp_Prefix")
	public String getListTempPrefix() {
		return listTempPrefix;
	}

	public void setListTempPrefix(String listTempPrefix) {
		this.listTempPrefix = listTempPrefix;
	}

	@Column(name = "list_temp_url")
	public String getListTempUrl() {
		return listTempUrl;
	}

	public void setListTempUrl(String listTempUrl) {
		this.listTempUrl = listTempUrl;
	}
	
	@Column(name = "com_type")
	public String getCompType() {
		return compType;
	}

	public void setCompType(String compType) {
		this.compType = compType;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "content_model_id")
	public ContentModel getContentModel() {
		return contentModel;
	}

	public void setContentModel(ContentModel contentModel) {
		this.contentModel = contentModel;
	}

	@Column(name = "model_sort")
	public int getModelSort() {
		return modelSort;
	}

	public void setModelSort(int modelSort) {
		this.modelSort = modelSort;
	}

	@Column(name = "is_disable")
	public boolean isDisable() {
		return isDisable;
	}

	public void setDisable(boolean isDisable) {
		this.isDisable = isDisable;
	}
	
	public boolean equals(Object o) {
		if (o == this)
			return true;
		if (!(o instanceof Model))
			return false;
		return this.id.equals(((Model) o).id);
	}

	public String toString() {
		return this.getClass().getName() + "[id=" + id + "]" + ",[version="
				+ version + "]";
	}
	
	@Transient
	public String[] getSysLog() {
		return new String[] { "模型", "modelName=" + modelName };
	}
}
