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
 * 订单
 * 
 * @author Joe
 */
@Entity
@Table(name = "DELIVERY_ADDRESS")
public class DeliveryAddress implements PersistentLogInterface {
	/**
	 * 序列化
	 */
	private static final long serialVersionUID = 5291194643138256599L;

	private Integer id;
	// 版本号
	private Integer version;
	// 区域编号
	private Area area;
	// 用户
	private User user;
	// 街道地址
	private String streetAddress;
	// 收货人姓名
	private String consignee;
	// 收货人电话
	private String consigneePhone;
	// 收货人手机
	private String consigneeMobile;
	// 是否默认
	private boolean isDefault;
	// 备注
	private String remark;
	// 创建时间
	private Date createTime;

	@Id
	@Column(name = "ADDRESS_id")
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
	@JoinColumn(name = "area_id")
	public Area getArea() {
		return area;
	}

	public void setArea(Area area) {
		this.area = area;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "user_id")
	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	@Column(name = "street_Address")
	public String getStreetAddress() {
		return streetAddress;
	}

	public void setStreetAddress(String streetAddress) {
		this.streetAddress = streetAddress;
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

	@Column(name = "consignee_Mobile")
	public String getConsigneeMobile() {
		return consigneeMobile;
	}

	public void setConsigneeMobile(String consigneeMobile) {
		this.consigneeMobile = consigneeMobile;
	}

	@Column(name = "is_default")
	public boolean isDefault() {
		return isDefault;
	}

	public void setDefault(boolean isDefault) {
		this.isDefault = isDefault;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}
	
	@Column(name = "create_time", nullable = false)
	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	@Transient
	public String[] getSysLog() {
		return new String[] { "收货地址", "id=" + id };
	}
}