package com.elian.cms.tenpay.model;

/***
 * 财付通在线支付
 * 
 * @author Gechuanyi
 * 
 */
public class Tenpay {
	/**财付通支付首页*/
	private String tenpayUrl="";
	/** 商品价格，以分为单位 */
	private double totalFee;
	/** 商品价格 */
	private String fee="";
	/** 商品名称 */
	private String prouductName="";
	/** 备注信息 */
	private String remarkexplain="";
	/** 说明="商品：" + product_name+",备注："+remarkexplain */
	//private String desc="";
	/**通知ID*/
	private String notifyId;
	/**通知签名认证*/
	private boolean tenpaySign;
	/**财付通订单号*/
	private String transactionId;
	/**财付通折扣券*/
	private String discount;
	/**支付结果 0为成功，其他不成功*/
	private Integer tradeState;
	/** 商家订单号，如广东医联网订单号 */
	private String outTradeNo;
	/** 商户号 */
	private String partner;
	/** 交易完成后跳转的页面 */
	private String retrunUrl="";
	/** 接收财付通通知的URL */
	private String notifyUrl="";
	/** 用户IP */
	private String spbillCreateIp;
	/** 交易模式，1即时到账(默认)，2中介担保，3后台选择（买家进支付中心列表选择） */
	private String tradeMode="1";
	/*** 以下为不必需条件 */
	/** 币种 1为人民币 */
	private String feeType="1";
	/** 签名类型，默认：MD5 */
	private String signType = "MD5";
	/** 版本号 */
	private String serviceVersion = "1.0";
	/** 字符编码 */
	private String inputCharset = "UTF-8";
	/** 密钥序号 */
	private String signKeyIndex = "1";
	/**密钥*/
	private String  key;
	/** 附加数据，原样返回 */
	private String attach="";
	/** 商品费用 */
	private String productFee="";
	/** 物流费用 */
	private String transportFee="";
	/** 订单生成时间 */
	private String timeStart="";
	/** 订单失效时间 */
	private String timeExpire="";
	/** 买方财付通账号 */
	private String buyerId="";
	/** 商品标记 */
	private String goodsTag="";
	/** 物流说明 */
	private String transportDesc="";
	/** 交易类型，1实物交易，2虚拟交易 */
	private String transType="";
	/** 平台ID */
	private String agentid="";
	/** 代理模式 0无代理(默认)，1表示卡易售模式，2表示网店模式 */
	private String agentType="";
	/** 卖家商户号 为空则等同于partner */
	private String sellerId="";
	/**处理结果*/

	public double getTotalFee() {
		return totalFee;
	}

	public void setTotalFee(double totalFee) {
		this.totalFee = totalFee;
	}

	public String getFee() {
		return fee;
	}

	public void setFee(String fee) {
		this.fee = fee;
	}

	public String getProuductName() {
		return prouductName;
	}

	public void setProuductName(String prouductName) {
		this.prouductName = prouductName;
	}

	public String getRemarkexplain() {
		return remarkexplain;
	}

	public void setRemarkexplain(String remarkexplain) {
		this.remarkexplain = remarkexplain;
	}

	public String getDesc() {
		return "商品:"+prouductName+"备注"+remarkexplain;
	}

	/*public void setDesc(String desc) {
		this.desc = desc;
	}*/

	public String getOutTradeNo() {
		return outTradeNo;
	}

	public void setOutTradeNo(String outTradeNo) {
		this.outTradeNo = outTradeNo;
	}

	public String getPartner() {
		return partner;
	}

	public void setPartner(String partner) {
		this.partner = partner;
	}

	public String getRetrunUrl() {
		return retrunUrl;
	}

	public void setRetrunUrl(String retrunUrl) {
		this.retrunUrl = retrunUrl;
	}

	public String getNotifyUrl() {
		return notifyUrl;
	}

	public void setNotifyUrl(String notifyUrl) {
		this.notifyUrl = notifyUrl;
	}

	public String getSpbillCreateIp() {
		return spbillCreateIp;
	}

	public void setSpbillCreateIp(String spbillCreateIp) {
		this.spbillCreateIp = spbillCreateIp;
	}

	public String getTradeMode() {
		return tradeMode;
	}

	public void setTradeMode(String tradeMode) {
		this.tradeMode = tradeMode;
	}

	public String getFeeType() {
		return feeType;
	}

	public void setFeeType(String feeType) {
		this.feeType = feeType;
	}

	public String getSignType() {
		return signType;
	}

	public void setSignType(String signType) {
		this.signType = signType;
	}

	public String getServiceVersion() {
		return serviceVersion;
	}

	public void setServiceVersion(String serviceVersion) {
		this.serviceVersion = serviceVersion;
	}

	public String getInputCharset() {
		return inputCharset;
	}

	public void setInputCharset(String inputCharset) {
		this.inputCharset = inputCharset;
	}

	public String getSignKeyIndex() {
		return signKeyIndex;
	}

	public void setSignKeyIndex(String signKeyIndex) {
		this.signKeyIndex = signKeyIndex;
	}

	public String getAttach() {
		return attach;
	}

	public void setAttach(String attach) {
		this.attach = attach;
	}

	public String getProductFee() {
		return productFee;
	}

	public void setProductFee(String productFee) {
		this.productFee = productFee;
	}

	public String getTransportFee() {
		return transportFee;
	}

	public void setTransportFee(String transportFee) {
		this.transportFee = transportFee;
	}

	public String getTimeStart() {
		return timeStart;
	}

	public void setTimeStart(String timeStart) {
		this.timeStart = timeStart;
	}

	public String getTimeExpire() {
		return timeExpire;
	}

	public void setTimeExpire(String timeExpire) {
		this.timeExpire = timeExpire;
	}

	public String getBuyerId() {
		return buyerId;
	}

	public void setBuyerId(String buyerId) {
		this.buyerId = buyerId;
	}

	public String getGoodsTag() {
		return goodsTag;
	}

	public void setGoodsTag(String goodsTag) {
		this.goodsTag = goodsTag;
	}

	public String getTransportDesc() {
		return transportDesc;
	}

	public void setTransportDesc(String transportDesc) {
		this.transportDesc = transportDesc;
	}

	public String getTransType() {
		return transType;
	}

	public void setTransType(String transType) {
		this.transType = transType;
	}

	public String getAgentid() {
		return agentid;
	}

	public void setAgentid(String agentid) {
		this.agentid = agentid;
	}

	public String getAgentType() {
		return agentType;
	}

	public void setAgentType(String agentType) {
		this.agentType = agentType;
	}

	public String getSellerId() {
		return sellerId;
	}

	public void setSellerId(String sellerId) {
		this.sellerId = sellerId;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public String getTenpayUrl() {
		return tenpayUrl;
	}

	public void setTenpayUrl(String tenpayUrl) {
		this.tenpayUrl = tenpayUrl;
	}

	public String getNotifyId() {
		return notifyId;
	}

	public void setNotifyId(String notifyId) {
		this.notifyId = notifyId;
	}

	public String getTransactionId() {
		return transactionId;
	}

	public void setTransactionId(String transactionId) {
		this.transactionId = transactionId;
	}

	public String getDiscount() {
		return discount;
	}

	public void setDiscount(String discount) {
		this.discount = discount;
	}

	public Integer getTradeState() {
		return tradeState;
	}

	public void setTradeState(Integer tradeState) {
		this.tradeState = tradeState;
	}

	public boolean isTenpaySign() {
		return tenpaySign;
	}

	public void setTenpaySign(boolean tenpaySign) {
		this.tenpaySign = tenpaySign;
	}
}
