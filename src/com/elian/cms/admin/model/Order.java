package com.elian.cms.admin.model;

import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.persistence.Version;

import org.apache.commons.beanutils.BeanUtils;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;

import com.elian.cms.syst.model.EagerLoading;
import com.elian.cms.syst.model.PersistentLogInterface;

/**
 * 订单
 * 
 * @author Joe
 */
@Entity
@Table(name = "ORDERS")
public class Order implements PersistentLogInterface,EagerLoading {
	/**
	 * 序列化
	 */
	private static final long serialVersionUID = 5291194643138256599L;
	
	private Integer id;
	// 版本号
	private Integer version;
	// 区域编号
	private Area area;
	// 订单编号
	private String orderCode;
	// 总金额
	private Integer totalAmount;
	// 付费金额[折扣]
	private Integer payAmount;
	// 运费
	private Integer shipping;
	// 付款状态（未支付、部分支付、已支付、部分退款、全额退款）
	private int paymentStatus;
	// 用户
	private User user;
	// 货到付款手续费
	private Integer deliveryFee;
	// 支付类型
	private String deliveryType;
	// 送货区域
	private Area shipArea;
	// 送货邮编
	private String shipZipCode;
	// 送货街道地址
	private String shipStreetAddress;
	// 收货人姓名
	private String consignee;
	// 收货人电话
	private String consigneePhone;
	// 收货人手机号码
	private String consigneeMobile;
	// 订单时间
	private Date createTime;
	// 订单状态（未处理、已处理、已完成、已作废）
	private Integer orderStatus;
	// 备注
	private String remark;
	// 运输状况
	private int shippingStatus;
	// 订单明细
	private Set<OrderDetail> orderDetailSet;

	@Id
	@Column(name = "orders_id")
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
	@JoinColumn(name = "AREA_CODE")
	public Area getArea() {
		return area;
	}

	public void setArea(Area area) {
		this.area = area;
	}

	@Column(name = "order_code", unique = true)
	public String getOrderCode() {
		return orderCode;
	}

	public void setOrderCode(String orderCode) {
		this.orderCode = orderCode;
	}

	@Column(name = "total_amount")
	public Integer getTotalAmount() {
		return totalAmount;
	}

	public void setTotalAmount(Integer totalAmount) {
		this.totalAmount = totalAmount;
	}

	@Column(name = "pay_amount")
	public Integer getPayAmount() {
		return payAmount;
	}

	public void setPayAmount(Integer payAmount) {
		this.payAmount = payAmount;
	}

	public Integer getShipping() {
		return shipping;
	}

	public void setShipping(Integer shipping) {
		this.shipping = shipping;
	}

	@Column(name = "payment_status")
	public int getPaymentStatus() {
		return paymentStatus;
	}

	public void setPaymentStatus(int paymentStatus) {
		this.paymentStatus = paymentStatus;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "user_id")
	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	@Column(name = "delivery_fee")
	public Integer getDeliveryFee() {
		return deliveryFee;
	}

	public void setDeliveryFee(Integer deliveryFee) {
		this.deliveryFee = deliveryFee;
	}

	@Column(name = "delivery_type")
	public String getDeliveryType() {
		return deliveryType;
	}

	public void setDeliveryType(String deliveryType) {
		this.deliveryType = deliveryType;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "area_id")
	public Area getShipArea() {
		return shipArea;
	}

	public void setShipArea(Area shipArea) {
		this.shipArea = shipArea;
	}

	@Column(name = "ship_zip_code")
	public String getShipZipCode() {
		return shipZipCode;
	}

	public void setShipZipCode(String shipZipCode) {
		this.shipZipCode = shipZipCode;
	}

	@Column(name = "ship_street_address")
	public String getShipStreetAddress() {
		return shipStreetAddress;
	}

	public void setShipStreetAddress(String shipStreetAddress) {
		this.shipStreetAddress = shipStreetAddress;
	}

	public String getConsignee() {
		return consignee;
	}

	public void setConsignee(String consignee) {
		this.consignee = consignee;
	}

	@Column(name = "consignee_phone")
	public String getConsigneePhone() {
		return consigneePhone;
	}

	public void setConsigneePhone(String consigneePhone) {
		this.consigneePhone = consigneePhone;
	}

	@Column(name = "consignee_mobile")
	public String getConsigneeMobile() {
		return consigneeMobile;
	}

	public void setConsigneeMobile(String consigneeMobile) {
		this.consigneeMobile = consigneeMobile;
	}

	@Column(name = "create_time", nullable = false)
	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	@Column(name = "order_status")
	public Integer getOrderStatus() {
		return orderStatus;
	}

	public void setOrderStatus(Integer orderStatus) {
		this.orderStatus = orderStatus;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	@Column(name = "shipping_status")
	public int getShippingStatus() {
		return shippingStatus;
	}

	public void setShippingStatus(int shippingStatus) {
		this.shippingStatus = shippingStatus;
	}

	@OneToMany(mappedBy = "order", fetch = FetchType.LAZY)
	@Cascade(value = { CascadeType.DELETE })
	public Set<OrderDetail> getOrderDetailSet() {
		return orderDetailSet;
	}

	public void setOrderDetailSet(Set<OrderDetail> orderDetailSet) {
		this.orderDetailSet = orderDetailSet;
	}

	@Transient
	public String[] getSysLog() {
		return new String[] { "订单", "orderCode=" + orderCode };
	}
	
	@Transient
	public void getLazyObject() {
		Set<OrderDetail> set=new HashSet<OrderDetail>();
		Iterator<OrderDetail> itor=orderDetailSet.iterator();
		while (itor.hasNext()) {
			OrderDetail orderDetail =  itor.next();
			orderDetail.getLazyObject();
			OrderDetail od=new OrderDetail();
			try {
				BeanUtils.copyProperties(od, orderDetail);
			}catch (Exception e) {
				e.printStackTrace();
			}
			set.add(od);
		}
		this.setArea(null);
		this.setShipArea(null);
		this.setUser(null);
		this.setOrderDetailSet(set);
	}
}