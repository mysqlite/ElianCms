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

/**
 * 
 * 
 * @author thy
 */
@javax.persistence.Entity
@Table(name = "T_TEMP_CONFIG")
public class TemplateConfig implements PersistentLogInterface, Cloneable {

	private static final long serialVersionUID = -5064531345022258204L;
	
	private Integer id;
	
	private Template template;
	
	private Channel channel;
	
//	private Integer channelSetId;
	private Channel channelSet;
	
	private Integer areaId;
	
	private Date createTime;
	
	private String creater;
	
	private Integer version;		

	
	@Column(name="temp_config_id")
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	public Integer getId() {
		return id;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="temp_id")
	public Template getTemplate() {
		return template;
	}
	
	@ManyToOne(fetch = FetchType.LAZY)	
	@JoinColumn(name="channel_id")
	public Channel getChannel() {
		return channel;
	}
	
//	@Column(name="set_channel_id")
//	public Integer getChannelSetId() {
//		return channelSetId;
//	}
	
	@ManyToOne(fetch = FetchType.LAZY)	
	@JoinColumn(name="set_channel_id")
	public Channel getChannelSet() {
		return channelSet;
	}

	@Column(name="area_id")
	public Integer getAreaId() {
		return areaId;
	}

	@Column(name="create_time")
	public Date getCreateTime() {
		return createTime;
	}

	@Column(name="creater")
	public String getCreater() {
		return creater;
	}

	@Version
	public Integer getVersion() {
		return version;
	}

	public void setId(Integer id) {
		this.id = id;
	}
	
	public void setTemplate(Template template) {
		this.template = template;
	}

	public void setChannel(Channel channel) {
		this.channel = channel;
	}
	
//	public void setChannelSetId(Integer channelSetId) {
//		this.channelSetId = channelSetId;
//	}

	public void setChannelSet(Channel channelSet) {
		this.channelSet = channelSet;
	}

	public void setAreaId(Integer areaId) {
		this.areaId = areaId;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	
	public void setCreater(String creater) {
		this.creater = creater;
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
		return new String[] { "模板配置", "id=" + id };
	}

}
