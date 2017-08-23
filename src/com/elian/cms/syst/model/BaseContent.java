package com.elian.cms.syst.model;

import java.io.Serializable;
import java.util.Date;

import com.elian.cms.admin.model.Channel;
import com.elian.cms.admin.model.User;

/**
 * 内容基类
 * 
 * @author Joe
 * 
 */
public abstract class BaseContent implements Serializable {
	private static final long serialVersionUID = 8160886381609793025L;

	private String actionName;
	private Integer channelId;
	private Integer contentStatus;
	
	private Date createDate;
	private Integer hitss;
	/**
	 * 内容是否来源于当前站点
	 */
	private boolean isSource;
	
	public boolean isSource() {
		return isSource;
	}

	public void setSource(boolean isSource) {
		this.isSource = isSource;
	}

	public String getActionName() {
		return actionName;
	}

	public void setActionName(String actionName) {
		this.actionName = actionName;
	}

	public Integer getChannelId() {
		return channelId;
	}

	public void setChannelId(Integer channelId) {
		this.channelId = channelId;
	}

	public Integer getContentStatus() {
		return contentStatus;
	}

	public void setContentStatus(Integer contentStatus) {
		this.contentStatus = contentStatus;
	}
	
	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
	
	public Integer getHitss() {
		return hitss;
	}

	public void setHitss(Integer hitss) {
		this.hitss = hitss;
	}

	/** 对象ID */
	public abstract Integer getId();

	/** 标题 */
	public abstract String getTitle();
	
	/** 创建人 */
	public User getCreater() {
		return null;
	}
	
	public void initWithDefaultData(User user, Channel channel){};
}
