package com.elian.cms.alipay.model;

import java.util.Map;

public class Alipay {

	
	private String path;//应用服务器地址[本机]
	
	private String signAlgo="MD5";//签名类型
	
	private String totalFee;//商品总价格
	
	private String reqUrl="https://wappaygw.alipay.com/service/rest.htm";//交易请求URL
	
	private String businessResult;
	
	private String[] subjectNames;//商品名称
	
	private String outTradeNo;//订单号
	
	private String tradeNo;
	
	private String outAccount;
	
	private String sellerAccountName="zhengyh@elian.cc";//账户名 
	
	private String partentr="2088901682482484";//合作商户ID
	
	private String key="c242ozdyqwarnatrl86lim0ao0tt22s4";// 商户（MD5）KEY
	
	private String notifyUrl;//接收支付宝发送通知的url
	
	private String merchantUrl;//未完成支付用户返回的url
	
	private String requestToken;//返回的token
	
	private Map<String, String> reqParams;//alipay.wap.trade.create.direct服务的参数
	
	private Map<String, String> authparams;
	
	private Map<String, String> resMap;
	
	private String sign;//
	
	private String result;
	
	private String verifyData;//待签名数据
	
	private String service;
	
	private String version="2.0";
	
	private String reqId;

	private String format="xml";
	
	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public String getSignAlgo() {
		return signAlgo;
	}

	public void setSignAlgo(String signAlgo) {
		this.signAlgo = signAlgo;
	}

	public String getTotalFee() {
		return totalFee;
	}

	public void setTotalFee(String totalFee) {
		this.totalFee = totalFee;
	}

	public String getReqUrl() {
		return reqUrl;
	}

	public String getBusinessResult() {
		return businessResult;
	}

	public void setBusinessResult(String businessResult) {
		this.businessResult = businessResult;
	}

	public String[] getSubjectNames() {
		return subjectNames;
	}

	public void setSubjectNames(String[] subjectNames) {
		this.subjectNames = subjectNames;
	}

	public String getOutTradeNo() {
		return outTradeNo;
	}

	public void setOutTradeNo(String outTradeNo) {
		this.outTradeNo = outTradeNo;
	}

	public String getSellerAccountName() {
		return sellerAccountName;
	}

	public void setSellerAccountName(String sellerAccountName) {
		this.sellerAccountName = sellerAccountName;
	}

	public String getNotifyUrl() {
		return notifyUrl;
	}

	public void setNotifyUrl(String notifyUrl) {
		this.notifyUrl = notifyUrl;
	}

	public String getMerchantUrl() {
		return merchantUrl;
	}

	public void setMerchantUrl(String merchantUrl) {
		this.merchantUrl = merchantUrl;
	}

	
	/**
	 * <subject>商品名</subject>
	 * <out_trade_no>本站订单号</out_trade_no>
	 * <total_fee>交易金额</total_fee>元为单位
	 * <seller_account_name>卖家支付宝账号</seller_account_name>
	 * <call_back_url>支付成功跳转路径</call_back_url>
	 * <notify_url>服务器异步通知页面路径</notify_url>
	 * <out_user>本站用户名</out_user>
	 * <merchant_url>商品展示的超链接</merchant_url>[文档表示为支付失败返回的连接]
	 * @return
	 */
	public String getReqData() {
		StringBuffer data=new StringBuffer();
		data.append("<direct_trade_create_req>");
		data.append("<subject>");
		if(subjectNames!=null) {
    		for (int i = 0; i <subjectNames.length; i++) {
    			data.append(subjectNames[i]);
    		}
		}
		data.append("</subject>");
		data.append("<out_trade_no>").append(outTradeNo).append("</out_trade_no>");
		data.append("<total_fee>").append(totalFee).append("</total_fee>");
		data.append("<seller_account_name>").append(sellerAccountName).append("</seller_account_name>");
		data.append("<notify_url>").append(notifyUrl).append("</notify_url>");
		data.append("<out_user>").append(outAccount).append("</out_user>");
		data.append("<call_back_url>").append(merchantUrl).append("</call_back_url>");
		data.append("<merchant_url>").append(merchantUrl).append("</merchant_url>");
		data.append("</direct_trade_create_req>");	
		return data.toString();
	}

	public String getPartentr() {
		return partentr;
	}

	public String getKey() {
		return key;
	}

	public String getRequestToken() {
		return requestToken;
	}

	public void setRequestToken(String requestToken) {
		this.requestToken = requestToken;
	}

	public Map<String, String> getReqParams() {
		return reqParams;
	}

	public void setReqParams(Map<String, String> reqParams) {
		this.reqParams = reqParams;
	}

	public Map<String, String> getAuthparams() {
		return authparams;
	}

	public void setAuthparams(Map<String, String> authparams) {
		this.authparams = authparams;
	}

	public Map<String, String> getResMap() {
		return resMap;
	}

	public void setResMap(Map<String, String> resMap) {
		this.resMap = resMap;
	}

	public String getSign() {
		return sign;
	}

	public void setSign(String sign) {
		this.sign = sign;
	}

	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}

	public String getTradeNo() {
		return tradeNo;
	}

	public void setTradeNo(String tradeNo) {
		this.tradeNo = tradeNo;
	}

	public String getVerifyData() {
		return verifyData;
	}

	public void setVerifyData(String verifyData) {
		this.verifyData = verifyData;
	}

	public void setReqUrl(String reqUrl) {
		this.reqUrl = reqUrl;
	}

	public void setPartentr(String partentr) {
		this.partentr = partentr;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public String getService() {
		return service;
	}

	public void setService(String service) {
		this.service = service;
	}

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public String getReqId() {
		return reqId;
	}

	public void setReqId(String reqId) {
		this.reqId = reqId;
	}

	public String getFormat() {
		return format;
	}

	public void setFormat(String format) {
		this.format = format;
	}

	public String getOutAccount() {
		return outAccount;
	}

	public void setOutAccount(String outAccount) {
		this.outAccount = outAccount;
	}
	
}
