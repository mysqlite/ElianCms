package com.elian.cms.alipay.action;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.elian.cms.admin.model.Order;
import com.elian.cms.admin.model.UserRegister;
import com.elian.cms.admin.service.OrderService;
import com.elian.cms.admin.service.UserRegisterService;
import com.elian.cms.alipay.model.Alipay;
import com.elian.cms.alipay.service.AlipayService;
import com.elian.cms.alipay.util.DirectTradeCreateRes;
import com.elian.cms.alipay.util.MD5Signature;
import com.elian.cms.alipay.util.ParameterUtil;
import com.elian.cms.alipay.util.ResponseResult;
import com.elian.cms.alipay.util.XMapUtil;
import com.elian.cms.syst.action.BaseAction;
import com.elian.cms.syst.util.ApplicationUtils;
import com.elian.cms.syst.util.StringUtils;
@Component
@Scope("prototype")
public class AlipayAction  extends BaseAction{
	private static final long serialVersionUID = 5736187473814005428L;
	/**挂号订单*/
	private UserRegister ur;
	/**产品订单*/
	private Order order;
	/**订单ID*/
	private Integer orderId;
	/**订单类型 如：挂号、产品*/
	private String orderType;
	private UserRegisterService userRegisterService;
	private OrderService orderService;
	private AlipayService alipayService; 
	
	public String index() {
		ApplicationUtils.getSession().get("path");
		return "index";
	}
	
	public String alipay() {
		//创建基本订单信息，金额
		Alipay alipay=createAlipay(orderId, orderType);
		//设置通知URL
		alipay.setNotifyUrl(alipay.getPath()+"alipay/alipay!notifys.action");
		//准备alipay.wap.trade.create.direct服务的参数
	    alipay.setReqParams(alipayService.prepareTradeRequestParamsMap(alipay));
	    //对参数进行签名
		alipay.setSign(alipayService.sign(alipay.getReqParams(),alipay));
	    //获取商户MD5 key
		alipay.getReqParams().put("sign", alipay.getSign());
		
		ResponseResult resResult =alipayService.send(alipay);
		String businessResult = "";
		if (resResult.isSuccess()) {
			businessResult = resResult.getBusinessResult();
		} else {
			return "return";
		}
		DirectTradeCreateRes directTradeCreateRes = null;
		XMapUtil.register(DirectTradeCreateRes.class);
		try {
			directTradeCreateRes = (DirectTradeCreateRes) XMapUtil.load(new ByteArrayInputStream(businessResult.getBytes("UTF-8")));
		} catch (Exception e) {
			
		}
		// 开放平台返回的内容中取出request_token
		alipay.setRequestToken(directTradeCreateRes.getRequestToken());
		alipay.setAuthparams(alipayService.prepareAuthParamsMap(alipay));
		//对调用授权请求数据签名
		alipay.getAuthparams().put("sign", alipayService.sign(alipay.getAuthparams(),alipay));
		String redirectURL= alipayService.getRedirectUrl(alipay);
		if (StringUtils.isNotBlank(redirectURL)) {
				try {
					//发送支付请求
					ApplicationUtils.getResponse().sendRedirect(redirectURL);
				}
				catch (IOException e) {
					e.printStackTrace();
				}
			return "return";
		}
		return "return";
	}
	
	public String callBack() {
        //获得通知签名
		 Alipay alipay=new Alipay();
        alipay.setSign(ApplicationUtils.getRequest().getParameter("sign"));
        alipay.setResult(ApplicationUtils.getRequest().getParameter("result"));
        alipay.setRequestToken(ApplicationUtils.getRequest().getParameter("request_token"));
        alipay.setOutTradeNo(ApplicationUtils.getRequest().getParameter("out_trade_no"));
        alipay.setTradeNo(ApplicationUtils.getRequest().getParameter("trade_no"));
      
        Map<String,String> resMap  = new HashMap<String,String>();
        resMap.put("result", alipay.getResult());
        resMap.put("request_token", alipay.getRequestToken());
        resMap.put("out_trade_no", alipay.getOutTradeNo());
        resMap.put("trade_no", alipay.getTradeNo());
       alipay.setVerifyData(ParameterUtil.getSignData(resMap));
        boolean verified = false;

        //使用MD5验签名
            try {
				verified = MD5Signature.verify(alipay);
			}
			catch (Exception e) {
				e.printStackTrace();
			}
	        //PrintWriter out = ApplicationUtils.getResponse().getWriter();
	        //ApplicationUtils.getResponse().setContentType("text/html");
	       // ApplicationUtils.getResponse().setCharacterEncoding("GBK");
	        if (!verified || !alipay.getResult().equals("success")) {
	        	//out.println("非法的签名!!");
	        } else {
	        	//out.println("签名验证成功!!");
	        	//out.println("记录日志!");
	        }
	        //out.flush();
			//out.close();
        return "return";
    }
	@SuppressWarnings("unchecked")
	public String notifys() {
		Alipay alipay=new Alipay();
    	System.out.println("接收到通知!");
		Map map = ApplicationUtils.getRequest().getParameterMap();//获得通知参数
        alipay.setSign((String) ((Object[]) map.get("sign"))[0]); //获得通知签名
        alipay.setVerifyData(alipayService.getVerifyData(map));//获得待验签名的数据
       System.out.println("verifyData:"+ alipay.getVerifyData());
        boolean verified = false;
        //验签名
        try {
            verified = MD5Signature.verify(alipay);
        } catch (Exception e) {
            e.printStackTrace();
        }
        PrintWriter out;
		try {
			out = ApplicationUtils.getResponse().getWriter();
			//验证签名通过
	        if (verified) {
	        	//根据交易状态处理业务逻辑
	        	//当交易状态成功，处理业务逻辑成功。回写success
	        	out.print("success");
	        } else {
	        	System.out.println("接收支付宝系统通知验证签名失败，请检查！");
	            out.print("fail");
	        }
		}
		catch (IOException e) {
			e.printStackTrace();
		}
		return "";
	}
	
 	private Alipay createAlipay(Integer orderId,String orderType) {
		Alipay alipay=new Alipay();
		ur=new UserRegister();
		order=new Order();
		if(StringUtils.getENL(ur).equals(orderType)) {
			ur=userRegisterService.get(orderId);
			alipay.setTotalFee("0.01");//DoubleUtlis.div(ur.getAmount(), 100, 2)
			alipay.setOutTradeNo(ur.getRegisterCode()+"1");
			String[] names=new String[1];
			names[0]=ur.getSchedulingId().getDoctor().getDoctName()+"医生挂号";
			alipay.setSubjectNames(names);
			alipay.setPath(ApplicationUtils.getSitePath("alipay.elian.cc:8081"));
			alipay.setMerchantUrl(alipay.getPath());
			//设置本站支付用户
			alipay.setOutAccount("gcy");
		}else if(StringUtils.getENL(order).equals(orderType)) {
			order=orderService.get(orderId);
			alipay.setTotalFee(order.getTotalAmount().toString());
			alipay.setOutTradeNo(order.getOrderCode());
			alipay.setPath(ApplicationUtils.getSitePath("alipay.elian.cc:8081"));
			alipay.setMerchantUrl(alipay.getPath());
			//设置本站支付用户
			alipay.setOutAccount("gcy");
		}
		return alipay;
	}
	
	public UserRegister getUr() {
		return ur;
	}
	
	public void setUr(UserRegister ur) {
		this.ur = ur;
	}

	public Integer getOrderId() {
		return orderId;
	}

	public void setOrderId(Integer orderId) {
		this.orderId = orderId;
	}

	public String getOrderType() {
		return orderType;
	}

	public void setOrderType(String orderType) {
		this.orderType = orderType;
	}

	@Resource
	public void setOrderService(OrderService orderService) {
		this.orderService = orderService;
	}

	@Resource
	public void setUserRegisterService(UserRegisterService userRegisterService) {
		this.userRegisterService = userRegisterService;
	}

	@Resource
	public void setAlipayService(AlipayService alipayService) {
		this.alipayService = alipayService;
	}
}
