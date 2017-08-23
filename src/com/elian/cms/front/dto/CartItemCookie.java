package com.elian.cms.front.dto;

import java.util.Date;


/**
 *购物车项Cookie
 * @author thy
 * @version v2.0
 */

public class CartItemCookie {
	
	public static final String CART_ITEM_LIST_COOKIE_NAME = "cartItemList";// 保存未登录会员购物车项集合的Cookie名称
	public static final int CART_ITEM_LIST_COOKIE_MAX_AGE = 86400;// 保存未登录会员购物车项集合的Cookie最大有效时间（单位：秒）
	
	private String i;// 商品ID
	private String sId;//内容id
	private String t;//商品type
	private Integer q;// 商品购买数量
	private Date d;// 添加日期
	
	public CartItemCookie(){}
	
	public CartItemCookie(String i, String sId, String t, Integer q, Date d) {
		super();
		this.i = i;
		this.sId = sId;
		this.t = t;
		this.q = q;
		this.d = d;
	}

	public String getsId() {
		return sId;
	}

	public void setsId(String sId) {
		this.sId = sId;
	}

	public String getI() {
		return i;
	}
	
	public void setI(String i) {
		this.i = i;
	}
	
	public String getT() {
		return t;
	}

	public void setT(String t) {
		this.t = t;
	}

	public Integer getQ() {
		return q;
	}

	public void setQ(Integer q) {
		this.q = q;
	}

	public Date getD() {
		return d;
	}

	public void setD(Date d) {
		this.d = d;
	}
	
}