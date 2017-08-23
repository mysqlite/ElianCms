package com.elian.cms.alipay.service.Impl;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.net.URLDecoder;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Component;

import com.elian.cms.alipay.model.Alipay;
import com.elian.cms.alipay.service.AlipayService;
import com.elian.cms.alipay.util.ErrorCode;
import com.elian.cms.alipay.util.MD5Signature;
import com.elian.cms.alipay.util.ParameterUtil;
import com.elian.cms.alipay.util.ResponseResult;
import com.elian.cms.alipay.util.XMapUtil;
import com.elian.cms.syst.util.ApplicationUtils;
@Component("alipayService")
public class AlipayServiceImpl implements AlipayService{
	private final static Log logger = LogFactory.getLog(AlipayServiceImpl.class);
	 /**
     * 获得验签名的数据
     * @param map
     * @return
     * @throws Exception 
     */
	@SuppressWarnings("unchecked")
	public String getVerifyData(Map map) {
        String service = (String) ((Object[]) map.get("service"))[0];
        String v = (String) ((Object[]) map.get("v"))[0];
        String sec_id = (String) ((Object[]) map.get("sec_id"))[0];
        String notify_data = (String) ((Object[]) map.get("notify_data"))[0];
        logger.warn("通知参数为："+"service=" + service + "&v=" + v + "&sec_id=" + sec_id + "&notify_data="+ notify_data);
        return "service=" + service + "&v=" + v + "&sec_id=" + sec_id + "&notify_data="+ notify_data;
    }	
    
    /**
	 * 准备alipay.wap.trade.create.direct服务的参数
	 * 
	 * @param request
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	public Map<String, String> prepareTradeRequestParamsMap(Alipay ali){
		Map<String, String> requestParams = new HashMap<String, String>();
		try {
			ApplicationUtils.getRequest().setCharacterEncoding("utf-8");
		}
		catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		requestParams.put("req_data", ali.getReqData());
		requestParams.put("req_id", ali.getOutTradeNo());
		requestParams.putAll(prepareCommonParams(ali));
		return requestParams;
	}
	
	/**
	 * 准备通用参数
	 * 
	 * @param request
	 * @return
	 */
	private Map<String, String> prepareCommonParams(Alipay alipay) {
		Map<String, String> commonParams = new HashMap<String, String>();
		commonParams.put("service", "alipay.wap.trade.create.direct");
		commonParams.put("sec_id", alipay.getSignAlgo());
		commonParams.put("partner", alipay.getPartentr());
		String callBackUrl = alipay.getPath()+"alipay/alipay!callBack.action";
		commonParams.put("call_back_url", callBackUrl);
		commonParams.put("format", alipay.getFormat());
		commonParams.put("v", alipay.getVersion());
		return commonParams;
	}
	
	/**
	 * 对参数进行签名
	 * 
	 * @param reqParams
	 * @return
	 */
	public String sign(Map<String, String> reqParams,Alipay alipay) {

		String signData = ParameterUtil.getSignData(reqParams);
		String sign = "";
		try {
			sign = MD5Signature.sign(signData, alipay.getKey());
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		return sign;
	}
	
	
	/**
	 * 调用alipay.wap.auth.authAndExecute服务的时候需要跳转到支付宝的页面，组装跳转url
	 * 
	 * @param reqParams
	 * @return
	 * @throws Exception
	 */
	public String getRedirectUrl(Alipay alipay) {
		String redirectUrl = alipay.getReqUrl() + "?";
		try {
			redirectUrl = redirectUrl + ParameterUtil.mapToUrl(alipay.getAuthparams());
		}
		catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return redirectUrl;
	}
	
	/**
	 * 准备alipay.wap.auth.authAndExecute服务的参数
	 * 
	 * @param request
	 * @param requestToken
	 * @return
	 */
	public Map<String, String> prepareAuthParamsMap(Alipay ali) {
		Map<String, String> requestParams = new HashMap<String, String>();
		String reqData = "<auth_and_execute_req><request_token>" + ali.getRequestToken()+ "</request_token></auth_and_execute_req>";
		requestParams.put("req_data", reqData);
		requestParams.putAll(prepareCommonParams(ali));
        //支付成功跳转链接
		String callbackUrl = ali.getPath()+"alipay/alipay!callBack.action";
		requestParams.put("call_back_url", callbackUrl);
		requestParams.put("service", "alipay.wap.auth.authAndExecute");
		return requestParams;
	}
	
	/**
	 * 调用支付宝开放平台的服务
	 * 
	 * @param reqParams
	 *            请求参数
	 * @return
	 * @throws Exception
	 */
	public ResponseResult send(Alipay alipay) {
		String response = "";
		HttpURLConnection conn=null;
		try {
			String invokeUrl = alipay.getReqUrl()  + "?";
			URL serverUrl = new URL(invokeUrl);
			conn = (HttpURLConnection) serverUrl.openConnection();
			conn.setRequestMethod("POST");
			conn.setDoOutput(true);
			conn.connect();
			String params = ParameterUtil.mapToUrl(alipay.getReqParams());
			conn.getOutputStream().write(params.getBytes());
			InputStream is = conn.getInputStream();
			BufferedReader in = new BufferedReader(new InputStreamReader(is));
			StringBuffer buffer = new StringBuffer();
			String line = "";
			while ((line = in.readLine()) != null) {
				buffer.append(line);
			}
			response = URLDecoder.decode(buffer.toString(), "utf-8");
		}
		catch (MalformedURLException e) {
			logger.error("支付宝发送请求URL错误"+response);
		}
		catch (ProtocolException e) {
			logger.error("支付宝发送请求URL协议异常"+response);
		}
		catch (UnsupportedEncodingException e) {
			logger.error("支付宝发送请求URL编码异常"+response);
		}
		catch (IOException e) {
			logger.error("支付宝发送请求URL流异常"+response);
		}
		conn.disconnect();
		return praseResult(response,alipay);
	}
	
	/**
	 * 解析支付宝返回的结果
	 * 
	 * @param response
	 * @return
	 * @throws Exception
	 */
	private ResponseResult praseResult(String response,Alipay alipay){
		// 调用成功
		HashMap<String, String> resMap = new HashMap<String, String>();
		alipay.setVersion(ParameterUtil.getParameter(response, "v"));
		alipay.setService(ParameterUtil.getParameter(response, "service"));
		alipay.setPartentr(ParameterUtil.getParameter(response, "partner"));
		alipay.setSign(ParameterUtil.getParameter(response, "sign"));
		alipay.setReqId(ParameterUtil.getParameter(response, "req_id"));
		
		resMap.put("v", alipay.getVersion());
		resMap.put("service", alipay.getService());
		resMap.put("partner", alipay.getPartentr());
		resMap.put("sec_id", alipay.getSignAlgo());
		resMap.put("req_id", alipay.getReqId());
		String businessResult = "";
		ResponseResult result = new ResponseResult();
		System.out.println("Token Result:"+response);
		if (response.contains("<err>")) {
			result.setSuccess(false);
			businessResult = ParameterUtil.getParameter(response, "res_error");
			// 转换错误信息
			XMapUtil.register(ErrorCode.class);
			ErrorCode errorCode=null;
			try {
				errorCode = (ErrorCode) XMapUtil.load(new ByteArrayInputStream(businessResult.getBytes("UTF-8")));
			}
			catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
			catch (Exception e) {
				e.printStackTrace();
			}
			result.setErrorMessage(errorCode);
			resMap.put("res_error", ParameterUtil.getParameter(response,
					"res_error"));
		} else {
		    businessResult = ParameterUtil.getParameter(response, "res_data");
            result.setSuccess(true);
            result.setBusinessResult(businessResult);
            resMap.put("res_data", businessResult);
		}
		//获取待签名数据
		alipay.setVerifyData(ParameterUtil.getSignData(resMap));
		//对待签名数据使用支付宝公钥验签名
		try {
			boolean verified = MD5Signature.verify(alipay);//PartnerConfig.KEY);
			if (!verified) {
				throw new Exception("验证签名失败");
			}
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
	
}
