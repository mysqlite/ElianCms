package com.elian.cms.tenpay.action;

import javax.annotation.Resource;

import net.sf.json.JSONObject;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.elian.cms.admin.model.Order;
import com.elian.cms.admin.model.OrderDetail;
import com.elian.cms.admin.model.UserRegister;
import com.elian.cms.admin.service.OrderService;
import com.elian.cms.admin.service.UserRegisterService;
import com.elian.cms.syst.action.BaseAction;
import com.elian.cms.syst.util.ApplicationUtils;
import com.elian.cms.syst.util.StringUtils;
import com.elian.cms.tenpay.model.Tenpay;
import com.elian.cms.tenpay.service.TenpayService;
@Component
@Scope("prototype")
public class TenpayAction  extends BaseAction{
	private static final long serialVersionUID = 5736187473814005428L;
	/**财付通支付页*/
	private static final String tenpayUrl="https://gw.tenpay.com/gateway/pay.htm";
	/**收款方*/
	//private static final String spName="广东医联网有限公司";
	/**商户号*/
	private static final String partner="1215913701";
	/**商户密钥*/
	private static final String key="aa671e16c5e073ee5e655fa97102c5c8";
	/**交易完成后跳转的url*/
	private static final String  returnUrl= "/tenpay/tenpay!retrunTenpay.action";
	/**接收财付通通知的URL*/
	private static final String notifyUrl =  "/tenpay/tenpay!notifys.action";
	/**返回数据连接*/
	private String requestUrl;
	/**挂号订单*/
	private UserRegister ur;
	/**产品订单*/
	private Order order;
	/**订单ID*/
	private Integer orderId;
	/**订单类型 如：挂号、产品*/
	private String orderType;
	
	private Tenpay ten;
	private UserRegisterService userRegisterService;
	private TenpayService tenpayService;
	private OrderService orderService;
	
	public String tenpay() {
		ur=new UserRegister();
		order=new Order();
		  if(StringUtils.getENL(ur).equals(orderType)) {
			  ur=userRegisterService.get(orderId);
			  requestUrl=tenpayService.tenpay(ApplicationUtils.getRequest(),ApplicationUtils.getResponse(), createTenpay(ur));
		  }else if(StringUtils.getENL(order).equals(orderType)) {
			  order=orderService.get(orderId);
			  for (OrderDetail orderdel : order.getOrderDetailSet()) {
				orderdel.getCommodityName();
			}
			  requestUrl=tenpayService.tenpay(ApplicationUtils.getRequest(),ApplicationUtils.getResponse(), createTenpay(order));
		  }
		return LIST;
	}
	
	public void tenPayJson() {
		JSONObject obj = new JSONObject();
		ur=new UserRegister();
		order=new Order();
		if(StringUtils.getENL(ur).equals(orderType)) {
			  ur=userRegisterService.get(orderId);
			 if(ur!=null)
			  requestUrl=tenpayService.tenpay(ApplicationUtils.getRequest(),ApplicationUtils.getResponse(), createTenpay(ur));
		  }else if(StringUtils.getENL(order).equals(orderType)) {
			  order=orderService.get(orderId);
			  if(order!=null)
			  requestUrl=tenpayService.tenpay(ApplicationUtils.getRequest(),ApplicationUtils.getResponse(), createTenpay(order));
		  }
		obj.put("tenpayUrl", requestUrl);
		ApplicationUtils.sendJsonpObj(obj);
	}
	
	public String retrunTenpay() {
		ten=new Tenpay();
		ten.setKey(key);
		ten=tenpayService.returnTenpay(ApplicationUtils.getRequest(), ApplicationUtils.getResponse(), ten);
		return "return";
	}
	
	public String notifys() {
		ten=new Tenpay();
		ten.setKey(key);
		ten.setPartner(partner);
		ten=tenpayService.notifys(ApplicationUtils.getRequest(), ApplicationUtils.getResponse(), ten);
		return "notify";
	}
	private Tenpay createTenpay(UserRegister ur) {
		Tenpay ten=new Tenpay();
		ten.setPartner(partner);
		ten.setRetrunUrl("http://teny.elian.cc:8081"+ApplicationUtils.getRequest().getContextPath()+returnUrl);
		ten.setNotifyUrl("http://teny.elian.cc:8081"+ApplicationUtils.getRequest().getContextPath()+notifyUrl);
		ten.setSpbillCreateIp(ApplicationUtils.getIpAddr());
		ten.setTradeMode("1");//即时到账
		ten.setFeeType("1");//人民币
		ten.setKey(key);//密钥
		ten.setOutTradeNo(ur.getRegisterCode()+"4");//订单号((int)(Double.valueOf(ur.getAmount())*100))+""
		ten.setFee(ur.getAmount()+"");//订单金额
		ten.setTenpayUrl(tenpayUrl);
		return ten;
	} 
	private Tenpay createTenpay(Order order) {
		Tenpay ten=new Tenpay();
		ten.setPartner(partner);
		ten.setRetrunUrl("http://teny.elian.cc:8081"+ApplicationUtils.getRequest().getContextPath()+returnUrl);
		ten.setNotifyUrl("http://teny.elian.cc:8081"+ApplicationUtils.getRequest().getContextPath()+notifyUrl);
		ten.setSpbillCreateIp(ApplicationUtils.getIpAddr());
		ten.setTradeMode("1");//即时到账
		ten.setFeeType("1");//人民币
		ten.setKey(key);//密钥
		ten.setOutTradeNo(order.getOrderCode());//订单号((int)(Double.valueOf(ur.getAmount())*100))+""
		ten.setFee(order.getTotalAmount().toString());//订单金额
		ten.setTenpayUrl(tenpayUrl);
		return ten;
	} 
	
	
	
	public Tenpay getTen() {
		return ten;
	}

	public void setTen(Tenpay ten) {
		this.ten = ten;
	}
	
	public String getRequestUrl() {
		return requestUrl;
	}
	
	public void setRequestUrl(String requestUrl) {
		this.requestUrl = requestUrl;
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
	public void setTenpayService(TenpayService tenpayService) {
		this.tenpayService = tenpayService;
	}

}
