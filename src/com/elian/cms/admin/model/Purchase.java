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
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.persistence.Version;

import com.elian.cms.syst.model.BaseContent;
import com.elian.cms.syst.model.PersistentInterface;
import com.elian.cms.syst.model.SeoInterface;

/**
 * 求购
 * 
 * @author thy
 */
@Entity
@Table(name = "T_PURCHASE")
public class Purchase extends BaseContent implements PersistentInterface,SeoInterface {
	private static final long serialVersionUID = 8050000531195827407L;

	/** ID */
	private Integer id;
	/**联系人*/
	private Contacter contacter;
	/**产品名称*/
	private String name;
	/**产品详情*/
	private String desc;
	/**产品规格*/
	private String specification;
	/**产品单价*/
	private String price;
	/**求购数量*/
	private int number;
	/**发布时间*/
	private Date publishTime;
	/**有效期*/
	private Date expireTime;
	/**求购须知*/
	private String notice;
	/**求购区域*/
	private Integer areaId;
	/**标题*/
	private String title;
	/**摘要*/
	private String description;
	/**关键字*/
	private String	keywords;
	/**来源名称*/
	private String	sourceName;
	/**来源地址*/
	private String	sourceUrl;
	/**创建人*/
	private User createrUser;
	/**创建时间*/
	private Date createTime;
	/**是否启用（0未启用,1启用）*/
	private boolean Disable;
	/**版本号*/
	private Integer version;
	
	
	@Id
	@Column(name = "pur_id")
	//@SequenceGenerator(name = "purchaseGenerator", sequenceName = "S_T_PURCHASE", allocationSize = 1)
	//@GeneratedValue(generator = "purchaseGenerator", strategy = GenerationType.SEQUENCE)
	@GeneratedValue(strategy = GenerationType.AUTO)
	public Integer getId() {
		return id;
	}
	
	public void setId(Integer id) {
		this.id = id;
	}	
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="contact_id")
	public Contacter getContacter() {
		return contacter;
	}
	
	public void setContacter(Contacter contacter) {
		this.contacter = contacter;
	}	
	
	@Column(name="prod_name")
	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}
	
	@Column(name="prod_desc")
	public String getDesc() {
		return desc;
	}
	
	public void setDesc(String desc) {
		this.desc = desc;
	}
	
	@Column(name="specification")
	public String getSpecification() {
		return specification;
	}
	
	public void setSpecification(String specification) {
		this.specification = specification;
	}
	
	@Column(name="price")
	public String getPrice() {
		return price;
	}
	
	public void setPrice(String price) {
		this.price = price;
	}
	
	@Column(name="prod_num")
	public int getNumber() {
		return number;
	}

	public void setNumber(int number) {
		this.number = number;
	}
	
	@Column(name="publish_time")
	public Date getPublishTime() {
		return publishTime;
	}
	
	public void setPublishTime(Date publishTime) {
		this.publishTime = publishTime;
	}
	
	@Column(name="expire_time")
	public Date getExpireTime() {
		return expireTime;
	}

	public void setExpireTime(Date expireTime) {
		this.expireTime = expireTime;
	}

	@Column(name="pur_notice")
	public String getNotice() {
		return notice;
	}
	
	public void setNotice(String notice) {
		this.notice = notice;
	}

	@Column(name="area_id")
	public Integer getAreaId() {
		return areaId;
	}

	public void setAreaId(Integer areaId) {
		this.areaId = areaId;
	}

	@Column(name="description")
	public String getDescription() {
		return description;
	}


	public void setDescription(String description) {
		this.description = description;
	}
	
	@Column(name="keywords")
	public String getKeywords() {
		return keywords;
	}

	public void setKeywords(String keywords) {
		this.keywords = keywords;
	}
	
	@Column(name="source_name")
	public String getSourceName() {
		return sourceName;
	}

	public void setSourceName(String sourceName) {
		this.sourceName = sourceName;
	}
	
	@Column(name="source_url")
	public String getSourceUrl() {
		return sourceUrl;
	}

	public void setSourceUrl(String sourceUrl) {
		this.sourceUrl = sourceUrl;
	}
	
	@OneToOne
	@JoinColumn(name="creater_id")
	public User getCreaterUser() {
		return createrUser;
	}
	
	public void setCreaterUser(User createrUser) {
		this.createrUser = createrUser;
	}
	
	@Column(name="create_time")
	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	
	@Column(name="is_disable")
	public boolean isDisable() {
		return Disable;
	}

	public void setDisable(boolean disable) {
		Disable = disable;
	}
	
	@Version
	public Integer getVersion() {
		return version;
	}

	public void setVersion(Integer version) {
		this.version = version;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	@Column(name="title")
	public String getTitle() {		
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	@Transient
	public User getCreater() {
		return createrUser;
	}

	@Transient
	public String getSeoDescription() {
		return desc;
	}

	@Transient
	public String getSeoKeywords() {
		return keywords;
	}

	@Transient
	public String getSeoTitle() {
		return title;
	}
	
	@Override
	public void initWithDefaultData(User user,Channel channel) {
		this.setTitle("求购默认的名称");
		this.setDisable(true);
		this.setTitle(channel.getChannelName()+"***求购默认数据的标题");
		this.setPrice("100");
		this.setNumber(100);
		this.setAreaId(0);
		this.setCreateTime(new Date());
		this.setPublishTime(new Date());
		this.setExpireTime(new Date());
		this.setCreaterUser(user);
		this.setNotice(channel.getChannelName()+"***求购须知");
		this.setDescription(channel.getChannelName()+"***求购默认数据的描述");
	}
	
	@Transient
	public String[] getSysLog() {
		return new String[] { "求购", "id=" + id };
	}
}
