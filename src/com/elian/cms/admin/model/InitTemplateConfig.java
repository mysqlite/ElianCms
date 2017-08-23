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
@Table(name = "T_INIT_TEMP_CONFIG")
public class InitTemplateConfig implements PersistentLogInterface, Cloneable {

	private static final long serialVersionUID = -5064531345022258204L;
	
	private Integer id;
	
	private Template template;
	
	private InitChannel initChannel;
	
	private InitChannel initChannelSet;
	
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
	@JoinColumn(name="init_channel_id")
	public InitChannel getInitChannel() {
		return initChannel;
	}

	@ManyToOne(fetch = FetchType.LAZY)	
	@JoinColumn(name="init_set_channel_id")
	public InitChannel getInitChannelSet() {
		return initChannelSet;
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

	public void setInitChannel(InitChannel initChannel) {
		this.initChannel = initChannel;
	}

	public void setInitChannelSet(InitChannel initChannelSet) {
		this.initChannelSet = initChannelSet;
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
	
	
	public TemplateConfig toTemplateConfig(Channel channel, Channel channelSet){
		TemplateConfig config=new TemplateConfig();
		config.setAreaId(getAreaId());
		config.setChannel(channel);
		config.setChannelSet(channelSet);
		config.setCreater(getCreater());
		config.setCreateTime(getCreateTime());
		config.setTemplate(getTemplate());
		return config;
	}
	
	@Transient
	public String[] getSysLog() {
		return new String[] { "模板初始化配置", "id=" + id };
	}
}
