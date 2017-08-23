package com.elian.cms.admin.model;

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

/**
 * 模板设置相关的实体类
 * 
 * @author thy
 */
@javax.persistence.Entity
@Table(name = "T_TEMPLATE_SET")
public class TemplateSet implements PersistentLogInterface, Cloneable {
	private static final long serialVersionUID = 1741834378660425904L;
	
	private Integer id;	
	/**模板id*/
	private Integer tempId;		
	/**模板区域id*/
	private Integer areaId;
	/**是否有子区域*/
	private Boolean hasSubArea;
	/**栏目的类型*/
	private String channelType;	
	/**模型*/
	private Model model;	
	/**内容栏目类型*/
	private String contentType;	
	/**读取栏目的最大限额 */
	private Integer maxChannelSize;
	/**读取信息的总条数*/
	private Integer listSize;
	/**读取有图片或者详细信息的总条数 */
	private Integer imgSize;
	/** 默认是0不取内容，1就是图文,2就是取内容 */
	private Integer specialContentType;
	/** 版本 */
	private Integer version;		

	@Id
	@Column(name="temp_set_id")
	@GeneratedValue(strategy=GenerationType.AUTO)
	public Integer getId() {
		return id;
	}

	@Column(name="temp_id")
	public Integer getTempId() {
		return tempId;
	}

	@Column(name="area_id")
	public Integer getAreaId() {
		return areaId;
	}

	@Column(name="has_child_area")
	public Boolean getHasSubArea() {
		return hasSubArea;
	}

	@Column(name="channel_type")
	public String getChannelType() {
		return channelType;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "model_id")
	public Model getModel() {
		return model;
	}

	@Column(name="channel_content_type")
	public String getContentType() {
		return contentType;
	}

	@Column(name="max_channel_count")
	public Integer getMaxChannelSize() {
		return maxChannelSize;
	}

	@Column(name="list_count")
	public Integer getListSize() {
		return listSize;
	}
	
	@Column(name="sp_list_count")
	public Integer getImgSize() {
		return imgSize;
	}

	@Column(name="sp_content_type")
	public Integer getSpecialContentType() {
		return specialContentType;
	}

	@Version
	public Integer getVersion() {
		return version;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public void setTempId(Integer tempId) {
		this.tempId = tempId;
	}

	public void setAreaId(Integer areaId) {
		this.areaId = areaId;
	}
	
	public void setHasSubArea(Boolean hasSubArea) {
		this.hasSubArea = hasSubArea;
	}

	public void setChannelType(String channelType) {
		this.channelType = channelType;
	}

	public void setModel(Model model) {
		this.model = model;
	}

	public void setContentType(String contentType) {
		this.contentType = contentType;
	}

	public void setMaxChannelSize(Integer maxChannelSize) {
		this.maxChannelSize = maxChannelSize;
	}

	public void setListSize(Integer listSize) {
		this.listSize = listSize;
	}

	public void setImgSize(Integer imgSize) {
		this.imgSize = imgSize;
	}

	public void setSpecialContentType(Integer specialContentType) {
		this.specialContentType = specialContentType;
	}

	public void setVersion(Integer version) {
		this.version = version;
	}

	public String toString() {
		return this.getClass().getName() + "[id=" + id + "]" + ",[version="
				+ version + "]";
	}
	
	public TemplateSet clone() {
		TemplateSet t = null;
		try {
			t = (TemplateSet) super.clone();
		}
		catch (CloneNotSupportedException e) {
			e.printStackTrace();
		}
		return t;
	}

	@Transient
	public String[] getSysLog() {
		return new String[] { "模板设置", "id=" + id };
	}

}
