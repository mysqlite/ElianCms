package com.elian.cms.admin.model;

import java.io.Serializable;
import java.sql.Timestamp;
public class InstrumentComment  implements Serializable {
	private static final long serialVersionUID = -5994402662751297442L;
	private Integer commId;
     private OrderDetail ordersItem;
     private Integer userId;
     private Integer deliveryScore;
     private Integer serveScore;
     private Integer logisticsSpeedScore;
     private Integer conformCore;
     private Integer compositeCore;
     private Boolean isScore;
     private Timestamp scoreTime;
     private String leaveWord;
     private Timestamp leaveWordTime;
     private Boolean isLeaveWord;
     private Integer good;
     private Integer poor;
     private Timestamp buyTime;
     private Timestamp createTime;
     private Boolean isTop;
     private Boolean isRecom;
     private Integer commSort;
     private Boolean isDisable;
     private Integer version;
	public Integer getCommId() {
		return commId;
	}
	public void setCommId(Integer commId) {
		this.commId = commId;
	}
	public OrderDetail getOrdersItem() {
		return ordersItem;
	}
	public void setOrdersItem(OrderDetail ordersItem) {
		this.ordersItem = ordersItem;
	}
	public Integer getUserId() {
		return userId;
	}
	public void setUserId(Integer userId) {
		this.userId = userId;
	}
	public Integer getDeliveryScore() {
		return deliveryScore;
	}
	public void setDeliveryScore(Integer deliveryScore) {
		this.deliveryScore = deliveryScore;
	}
	public Integer getServeScore() {
		return serveScore;
	}
	public void setServeScore(Integer serveScore) {
		this.serveScore = serveScore;
	}
	public Integer getLogisticsSpeedScore() {
		return logisticsSpeedScore;
	}
	public void setLogisticsSpeedScore(Integer logisticsSpeedScore) {
		this.logisticsSpeedScore = logisticsSpeedScore;
	}
	public Integer getConformCore() {
		return conformCore;
	}
	public void setConformCore(Integer conformCore) {
		this.conformCore = conformCore;
	}
	public Integer getCompositeCore() {
		return compositeCore;
	}
	public void setCompositeCore(Integer compositeCore) {
		this.compositeCore = compositeCore;
	}
	public Boolean getIsScore() {
		return isScore;
	}
	public void setIsScore(Boolean isScore) {
		this.isScore = isScore;
	}
	public Timestamp getScoreTime() {
		return scoreTime;
	}
	public void setScoreTime(Timestamp scoreTime) {
		this.scoreTime = scoreTime;
	}
	public String getLeaveWord() {
		return leaveWord;
	}
	public void setLeaveWord(String leaveWord) {
		this.leaveWord = leaveWord;
	}
	public Timestamp getLeaveWordTime() {
		return leaveWordTime;
	}
	public void setLeaveWordTime(Timestamp leaveWordTime) {
		this.leaveWordTime = leaveWordTime;
	}
	public Boolean getIsLeaveWord() {
		return isLeaveWord;
	}
	public void setIsLeaveWord(Boolean isLeaveWord) {
		this.isLeaveWord = isLeaveWord;
	}
	public Integer getGood() {
		return good;
	}
	public void setGood(Integer good) {
		this.good = good;
	}
	public Integer getPoor() {
		return poor;
	}
	public void setPoor(Integer poor) {
		this.poor = poor;
	}
	public Timestamp getBuyTime() {
		return buyTime;
	}
	public void setBuyTime(Timestamp buyTime) {
		this.buyTime = buyTime;
	}
	public Timestamp getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Timestamp createTime) {
		this.createTime = createTime;
	}
	public Boolean getIsTop() {
		return isTop;
	}
	public void setIsTop(Boolean isTop) {
		this.isTop = isTop;
	}
	public Boolean getIsRecom() {
		return isRecom;
	}
	public void setIsRecom(Boolean isRecom) {
		this.isRecom = isRecom;
	}
	public Integer getCommSort() {
		return commSort;
	}
	public void setCommSort(Integer commSort) {
		this.commSort = commSort;
	}
	public Boolean getIsDisable() {
		return isDisable;
	}
	public void setIsDisable(Boolean isDisable) {
		this.isDisable = isDisable;
	}
	public Integer getVersion() {
		return version;
	}
	public void setVersion(Integer version) {
		this.version = version;
	}
}