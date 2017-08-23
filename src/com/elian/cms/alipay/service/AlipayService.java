package com.elian.cms.alipay.service;

import java.util.Map;

import com.elian.cms.alipay.model.Alipay;
import com.elian.cms.alipay.util.ResponseResult;

public interface AlipayService {
	
	@SuppressWarnings("unchecked")
	public String getVerifyData(Map map);
	
	public Map<String, String> prepareTradeRequestParamsMap(Alipay ali);

	public String sign(Map<String, String> reqParams,Alipay alipay);

	public ResponseResult send(Alipay alipay) ;
	
	public Map<String, String> prepareAuthParamsMap(Alipay ali);
	
	public String getRedirectUrl(Alipay alipay);

}
