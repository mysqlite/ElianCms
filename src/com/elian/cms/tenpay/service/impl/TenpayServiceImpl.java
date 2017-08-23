package com.elian.cms.tenpay.service.impl;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Component;

import com.elian.cms.syst.util.ApplicationUtils;
import com.elian.cms.tenpay.client.ClientResponseHandler;
import com.elian.cms.tenpay.client.TenpayHttpClient;
import com.elian.cms.tenpay.model.Tenpay;
import com.elian.cms.tenpay.service.TenpayService;
import com.elian.cms.tenpay.util.RequestHandler;
import com.elian.cms.tenpay.util.ResponseHandler;
import com.elian.cms.tenpay.util.TenpayUtil;
@Component("tenpayService")
public class TenpayServiceImpl implements TenpayService {
	public String tenpay(HttpServletRequest request,HttpServletResponse response,Tenpay ten) {
		RequestHandler rh=new RequestHandler(request, response);
		rh.init();
		rh.setKey(ten.getKey());//设置密钥
		rh.setGateUrl(ten.getTenpayUrl());//设置支付网关
		//-----------------------------
		//设置支付参数
		//-----------------------------
		rh.setParameter("partner",ten.getPartner()); //商户号
		rh.setParameter("out_trade_no",ten.getOutTradeNo());//商家订单号
		rh.setParameter("total_fee", String.valueOf(ten.getFee()));//商品金额,以分为单位
		rh.setParameter("return_url",ten.getRetrunUrl());//交易完成后跳转的URL
		rh.setParameter("notify_url",ten.getNotifyUrl());	//接收财付通通知的URL
		rh.setParameter("body", ten.getDesc());	//商品描述
		rh.setParameter("bank_type", "DEFAULT");//银行类型(中介担保时此参数无效)
		rh.setParameter("spbill_create_ip",request.getRemoteAddr());   //用户的公网ip，不是商户服务器IP
		rh.setParameter("fee_type", "1");  //币种，1人民币
		rh.setParameter("subject", ten.getDesc());//商品名称(中介交易时必填)
		//系统可选参数
		rh.setParameter("sign_type",ten.getSignType());                //签名类型,默认：MD5
		rh.setParameter("service_version",ten.getServiceVersion());			//版本号，默认为1.0
		rh.setParameter("input_charset", ten.getInputCharset());            //字符编码
		rh.setParameter("sign_key_index", ten.getSignKeyIndex());             //密钥序号
		//业务可选参数
		rh.setParameter("attach", "");                      //附加数据，原样返回
		rh.setParameter("product_fee", "");                 //商品费用，必须保证transport_fee + product_fee=total_fee
		rh.setParameter("transport_fee", "0");               //物流费用，必须保证transport_fee + product_fee=total_fee
		rh.setParameter("time_start", TenpayUtil.getCurrTime());         //订单生成时间，格式为yyyymmddhhmmss
		rh.setParameter("time_expire", "");                 //订单失效时间，格式为yyyymmddhhmmss
		rh.setParameter("buyer_id", "");                    //买方财付通账号
		rh.setParameter("goods_tag", "");                   //商品标记
		rh.setParameter("trade_mode", ten.getTradeMode());//交易模式，1即时到账(默认)，2中介担保，3后台选择（买家进支付中心列表选择）
		rh.setParameter("transport_desc", ten.getTransportDesc()); //物流说明
		rh.setParameter("trans_type",ten.getTransType()); //交易类型，1实物交易，2虚拟交易
		rh.setParameter("agentid",ten.getAgentid());  //平台ID
		rh.setParameter("agent_type",ten.getAgentType()); //代理模式，0无代理(默认)，1表示卡易售模式，2表示网店模式
		rh.setParameter("seller_id", ten.getPartner()); //卖家商户号，为空则等同于partner
		String requestUrl="";
		try {
		 requestUrl = rh.getRequestURL();
		}catch (Exception e) {
			
		}
		//String debuginfo = rh.getDebugInfo();
		return requestUrl;
	}
	
	public Tenpay returnTenpay(HttpServletRequest request,HttpServletResponse response,Tenpay ten) {
		ResponseHandler rh = new ResponseHandler(request, response);
		rh.setKey(ten.getKey());
		ApplicationUtils.getIpAddr();
		if(rh.isTenpaySign()) {//判断签名
			ten.setTenpaySign(true);
			ten.setNotifyId(rh.getParameter("notify_id")); //通知id
			ten.setOutTradeNo(rh.getParameter("out_trade_no"));//商户订单号
			ten.setTransactionId(rh.getParameter("transaction_id"));//财付通订单号
			ten.setTotalFee(Double.parseDouble(rh.getParameter("total_fee")));//金额,以分为单位
			ten.setDiscount(rh.getParameter("discount"));//如果有使用折扣券，discount有值，total_fee+discount=原请求的total_fee
			ten.setTradeState(Integer.parseInt(rh.getParameter("trade_state")));//支付结果
			ten.setTradeMode(rh.getParameter("trade_mode"));//交易模式，1即时到账，2中介担保
			if("1".equals(ten.getTradeMode())){//即时到账 
				if(0==ten.getTradeState()){
					//即时到帐付款成功
				}else{
					//即时到帐付款失败
				}
			}else if("2".equals(ten.getTradeMode())){//中介担保
				if( 0==ten.getTradeState()){
					//中介担保付款成功
				}else{
					//中介担保付款失败
				}
			}
		} else {
			ten.setTenpaySign(false);//签名认证失败
		}
		return ten;
	}
	
	public Tenpay notifys(HttpServletRequest request,HttpServletResponse response,Tenpay ten) {
		//---------------------------------------------------------
		//财付通支付通知（后台通知）示例，商户按照此文档进行开发即可
		//---------------------------------------------------------
		//创建支付应答对象
		ResponseHandler resHandler = new ResponseHandler(request, response);
		resHandler.setKey(ten.getKey());
		System.out.println("后台回调返回参数:"+resHandler.getAllParameters());
		if(resHandler.isTenpaySign()) {//判断签名
			ten.setNotifyId(resHandler.getParameter("notify_id"));//通知id
			RequestHandler queryReq = new RequestHandler(null, null);//创建请求对象
			TenpayHttpClient httpClient = new TenpayHttpClient();//通信对象
			ClientResponseHandler queryRes = new ClientResponseHandler();//应答对象
			queryReq.init();//通过通知ID查询，确保通知来至财付通
			queryReq.setKey(ten.getKey());
			queryReq.setGateUrl("https://gw.tenpay.com/gateway/simpleverifynotifyid.xml");
			queryReq.setParameter("partner",ten.getPartner());
			queryReq.setParameter("notify_id", ten.getNotifyId());
			httpClient.setTimeOut(5);//通信对象
			
			try {
				httpClient.setReqContent(queryReq.getRequestURL());
				System.out.println("验证ID请求字符串:" + queryReq.getRequestURL());
			}
			catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
			//设置请求内容
			
			//后台调用
			if(httpClient.call()) {
				//设置结果参数
				try {
					queryRes.setContent(httpClient.getResContent());
				}
				catch (Exception e) {
					
				}
				System.out.println("验证ID返回字符串:" + httpClient.getResContent());
				queryRes.setKey(ten.getKey());//获取id验证返回状态码，0表示此通知id是财付通发起
				String retcode = queryRes.getParameter("retcode");
				ten.setOutTradeNo(resHandler.getParameter("out_trade_no"));
				ten.setTransactionId(resHandler.getParameter("transaction_id"));//财付通订单号
				ten.setTotalFee(Double.parseDouble(resHandler.getParameter("total_fee")));//金额,以分为单位
				ten.setDiscount(resHandler.getParameter("discount"));//如果有使用折扣券，discount有值，total_fee+discount=原请求的total_fee
				ten.setTradeState(Integer.parseInt(resHandler.getParameter("trade_state")));//支付结果
				ten.setTradeMode(resHandler.getParameter("trade_mode"));//交易模式，1即时到账，2中介担保
				//判断签名及结果
				if(queryRes.isTenpaySign()&& "0".equals(retcode)){ 
					System.out.println("id验证成功");
					if("1".equals(ten.getTradeMode())){//即时到账 
						if( "0".equals(ten.getTradeState())){
					        //------------------------------
							//即时到账处理业务开始
							//------------------------------
							//处理数据库逻辑
							//注意交易单不要重复处理
							//注意判断返回金额
							//------------------------------
							//即时到账处理业务完毕
							//------------------------------
							System.out.println("即时到账支付成功");
							//给财付通系统发送成功信息，财付通系统收到此结果后不再进行后续通知
							try {
								resHandler.sendToCFT("success");
							}
							catch (IOException e) {
								e.printStackTrace();
							}
						}else{
							System.out.println("即时到账支付失败");
							try {
								resHandler.sendToCFT("fail");
							}
							catch (IOException e) {
								e.printStackTrace();
							}
						}
					}else if("2".equals(ten.getTradeMode())){    //中介担保
						//------------------------------
						//中介担保处理业务开始
						//------------------------------
						//处理数据库逻辑
						//注意交易单不要重复处理
						//注意判断返回金额
						switch(ten.getTradeState()) {
							case 0:		//付款成功
								break;
							case 1:		//交易创建
								break;
							case 2:		//收获地址填写完毕
								break;
							case 4:		//卖家发货成功
								break;
							case 5:		//买家收货确认，交易成功
								break;
							case 6:		//交易关闭，未完成超时关闭
								break;
							case 7:		//修改交易价格成功
								break;
							case 8:		//买家发起退款
								break;
							case 9:		//退款成功
								break;
							case 10:	//退款关闭
								break;
							default:
						}
						//------------------------------
						//中介担保处理业务完毕
						//------------------------------
						System.out.println("trade_state = " +ten.getTradeState());
						//给财付通系统发送成功信息，财付通系统收到此结果后不再进行后续通知
						try {
							resHandler.sendToCFT("success");
						}
						catch (IOException e) {
							e.printStackTrace();
						}
					}
				}else{
					//错误时，返回结果未签名，记录retcode、retmsg看失败详情。
					System.out.println("查询验证签名失败或id验证失败"+",retcode:" + queryRes.getParameter("retcode"));
				}
			} else {
				System.out.println("后台调用通信失败");
				System.out.println(httpClient.getResponseCode());
				System.out.println(httpClient.getErrInfo());
				//有可能因为网络原因，请求已经处理，但未收到应答。
			}
		}else{
			System.out.println("通知签名验证失败");
		}
		return ten;
	}
}
