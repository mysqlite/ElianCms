package com.elian.cms.mall.dto;

import com.elian.cms.admin.model.ShoppingCart;

public class OrderItem {
	// 商品图片
	private String image;
	// 商品名称
	private String name;
	// 规格
	private String specification;
	// 数量
	private int quantity;
	// 单价
	private Integer unitPrice;
	// 小计
	private Integer subTotalPrice;

	// 企业ID
	private Integer companyId;
	// 商品类型
	private String commodityType;
	// 商品ID
	private Integer commodityId;

	public OrderItem() {
	}

	public OrderItem(ShoppingCart cart) {
		commodityId = cart.getCommodityId();
		commodityType = cart.getCommodityType();
		quantity = cart.getCommodityCount();
	}

	public Integer getCompanyId() {
		return companyId;
	}

	public void setCompanyId(Integer companyId) {
		this.companyId = companyId;
	}

	public String getCommodityType() {
		return commodityType;
	}

	public void setCommodityType(String commodityType) {
		this.commodityType = commodityType;
	}

	public Integer getCommodityId() {
		return commodityId;
	}

	public void setCommodityId(Integer commodityId) {
		this.commodityId = commodityId;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSpecification() {
		return specification;
	}

	public void setSpecification(String specification) {
		this.specification = specification;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public Integer getUnitPrice() {
		return unitPrice;
	}

	public void setUnitPrice(Integer unitPrice) {
		this.unitPrice = unitPrice;
	}

	public Integer getSubTotalPrice() {
		return subTotalPrice;
	}

	public void setSubTotalPrice(Integer subTotalPrice) {
		this.subTotalPrice = subTotalPrice;
	}
}