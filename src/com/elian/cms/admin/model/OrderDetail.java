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

import org.hibernate.annotations.BatchSize;

import com.elian.cms.syst.model.EagerLoading;
import com.elian.cms.syst.model.PersistentLogInterface;

/**
 * 订单明细
 * 
 * @author Joe
 */
@Entity
@BatchSize(size = 10)
@Table(name = "ORDERS_ITEM")
public class OrderDetail implements PersistentLogInterface,EagerLoading {
	/**
	 * 序列化
	 */
	private static final long serialVersionUID = -5814629007487298373L;

	private Integer id;
	// 版本号
	private Integer version;
	// 订单
	private Order order;
	// 企业
	private Company company;
	// 商品类型
	private String commodityType;
	// 商品ID
	private Integer commodityId;
	// 商品名称
	private String commodityName;
	// 商品数量
	private int commodityCount;
	// 创建时间
	private Date createTime;
	// 商品单位
	private String commodityUnit;
	// 折扣价[或涨价]
	private Integer discountedPrice;
	// 物流号
	private String logisticNumber;

	@Id
	@Column(name = "orders_item_id")
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

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "orders_id")
	public Order getOrder() {
		return order;
	}

	public void setOrder(Order order) {
		this.order = order;
	}

	@Column(name = "commodity_type")
	public String getCommodityType() {
		return commodityType;
	}

	public void setCommodityType(String commodityType) {
		this.commodityType = commodityType;
	}

	@Column(name = "commodity_id")
	public Integer getCommodityId() {
		return commodityId;
	}

	public void setCommodityId(Integer commodityId) {
		this.commodityId = commodityId;
	}

	@Column(name = "commodity_count")
	public int getCommodityCount() {
		return commodityCount;
	}

	public void setCommodityCount(int commodityCount) {
		this.commodityCount = commodityCount;
	}

	@Column(name = "create_time")
	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	@Column(name = "commodity_unit")
	public String getCommodityUnit() {
		return commodityUnit;
	}

	public void setCommodityUnit(String commodityUnit) {
		this.commodityUnit = commodityUnit;
	}

	@Column(name = "discounted_price")
	public Integer getDiscountedPrice() {
		return discountedPrice;
	}

	public void setDiscountedPrice(Integer discountedPrice) {
		this.discountedPrice = discountedPrice;
	}

	@Column(name = "logistic_number")
	public String getLogisticNumber() {
		return logisticNumber;
	}

	public void setLogisticNumber(String logisticNumber) {
		this.logisticNumber = logisticNumber;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "company_id")
	public Company getCompany() {
		return company;
	}

	public void setCompany(Company company) {
		this.company = company;
	}
	
	@Transient
	public String getCommodityName() {
		return commodityName;
	}

	public void setCommodityName(String commodityName) {
		this.commodityName = commodityName;
	}

	@Transient
	public String[] getSysLog() {
		return new String[] { "订单明细", "id=" + id };
	}

	public void getLazyObject() {
		this.setCompany(null);
		this.setOrder(null);
	}
	
}