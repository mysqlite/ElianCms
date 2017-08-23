package com.elian.cms.admin.model;

import java.util.Date;

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

/**
 * 内容
 * 
 * @author Joe
 */
@Entity
@Table(name = "T_CONTROL")
public class Content implements PersistentLogInterface, Cloneable {
	private static final long serialVersionUID = -4194615703970313863L;

	/** id */
	private Integer id;
	/** 标题 */
	private String title;
	/** 栏目 */
	private Channel channel;
	/** 站点 */
	private Site site;
	/** 状态 */
	private Integer status;
	/** 是否推荐 */
	private boolean isRecommend;
	/** 静态化状态 */
	private int staticStatus;
	/** 置顶 */
	private int topmost;
	/** 实体Id */
	private Integer entityId;
	/** Action名称 */
	private String actionName;
	/** 静态文件路径 */
	private String path;
	/** 创建人 */
	private String creater;
	/** 创建日期 */
	private Date createTime;
	/** 排序 */
	private Integer sort;
	/**流量*/
	private Integer hits=0;
	/** 版本号 */
	private Integer version;	

	@Id
	@Column(name = "ctrl_id")
	// @SequenceGenerator(name = "contentGenerator", sequenceName =
	// "S_T_CONTROL", allocationSize = 1)
	// @GeneratedValue(generator = "contentGenerator", strategy =
	// GenerationType.SEQUENCE)
	@GeneratedValue( strategy = GenerationType.AUTO)
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "SITE_ID")
	public Site getSite() {
		return site;
	}

	public void setSite(Site site) {
		this.site = site;
	}
	
	@Column(name = "content_title")
	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "CHANNEL_ID")
	public Channel getChannel() {
		return channel;
	}

	public void setChannel(Channel channel) {
		this.channel = channel;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	@Column(name = "is_recom")
	public boolean isRecommend() {
		return isRecommend;
	}

	public void setRecommend(boolean isRecommend) {
		this.isRecommend = isRecommend;
	}
	
	@Column(name = "static_status")
	public int getStaticStatus() {
		return staticStatus;
	}

	public void setStaticStatus(int staticStatus) {
		this.staticStatus = staticStatus;
	}
	
	@Column(name = "top_most")
	public int getTopmost() {
		return topmost;
	}

	public void setTopmost(int topmost) {
		this.topmost = topmost;
	}

	@Column(name = "table_key_id")
	public Integer getEntityId() {
		return entityId;
	}

	public void setEntityId(Integer entityId) {
		this.entityId = entityId;
	}
	
	@Column(name = "action_name")
	public String getActionName() {
		return actionName;
	}

	public void setActionName(String actionName) {
		this.actionName = actionName;
	}
	
	@Column(name = "static_path")
	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}
	
	@Column(name = "creater")
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

	@Column(name = "ctrl_sort")
	public Integer getSort() {
		return sort;
	}

	public void setSort(Integer sort) {
		this.sort = sort;
	}	
	
	@Column(name = "hits")
	public Integer getHits() {
		return hits;
	}

	public void setHits(Integer hits) {
		this.hits = hits;
	}

	@Version
	public Integer getVersion() {
		return version;
	}

	public void setVersion(Integer version) {
		this.version = version;
	}

	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null || !(o instanceof Content)) {
			return false;
		}
		return id.equals(((Content) o).getId());
	}

	public int hashCode() {
		return id.hashCode();
	}
	
	public Content clone() {
		Content c = null;
		try {
			c = (Content) super.clone();
		}
		catch (CloneNotSupportedException e) {
			e.printStackTrace();
		}
		return c;
	}

	public String toString() {
		return this.getClass().getName() + "[id=" + id + "]" + ",[version="
				+ version + "]";
	}
	@Transient
	public String[] getSysLog() {
		return new String[] { "内容", "title=" + title };
	}
}
