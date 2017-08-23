package com.elian.cms.admin.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.persistence.Version;

import com.elian.cms.syst.model.PersistentLogInterface;

/**
 */
@Entity
@Table(name = "SHOPPING_CART")
public class ShoppingCart implements java.io.Serializable
	,PersistentLogInterface{
	private static final long serialVersionUID = 1286772116882879404L;
	
	/**id**/
	private Integer id;
	/**站点**/
	private Integer siteId;
	/**用户id**/
	private Integer userId;
	/**商品类型**/
	private String commodityType;
	/**商品id**/
	private Integer commodityId;
	/**订购数量**/
	private Integer commodityCount;
	/**备注**/
	private String remark;
	/**创建时间**/
	private Date createTime;
	/**版本**/
	private Integer version;

	public ShoppingCart() {	}
	
	public ShoppingCart(Integer siteId,Integer userId, String commodityType,
			Integer commodityId, Integer commodityCount, Date createTime) {
		super();
		this.siteId=siteId;
		this.userId = userId;
		this.commodityType = commodityType;
		this.commodityId = commodityId;
		this.commodityCount = commodityCount;
		this.createTime = createTime;
	}



	@Id
	@GeneratedValue( strategy = GenerationType.AUTO)
	@Column(name = "cart_id")
	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	@Column(name = "site_id")
	public Integer getSiteId() {
		return siteId;
	}

	public void setSiteId(Integer siteId) {
		this.siteId = siteId;
	}

	@Column(name = "user_id")
	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	@Column(name = "commodity_type", nullable = false, length = 20)
	public String getCommodityType() {
		return this.commodityType;
	}

	public void setCommodityType(String commodityType) {
		this.commodityType = commodityType;
	}

	@Column(name = "commodity_id", nullable = false)
	public Integer getCommodityId() {
		return this.commodityId;
	}

	public void setCommodityId(Integer commodityId) {
		this.commodityId = commodityId;
	}

	@Column(name = "commodity_count")
	public Integer getCommodityCount() {
		return this.commodityCount;
	}

	public void setCommodityCount(Integer commodityCount) {
		this.commodityCount = commodityCount;
	}

	@Column(name = "remark")
	public String getRemark() {
		return this.remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}
	
	@Column(name = "create_time")
	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	
	public void setVersion(Integer version) {
		this.version = version;
	}

	@Version
	public Integer getVersion() {
		return this.version;
	}
	
	@Transient
	public String[] getSysLog() {
		return new String[] { "购物车", "id=" + id };
	}
}