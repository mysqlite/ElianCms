package com.elian.cms.mall.action;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.elian.cms.admin.model.DeliveryAddress;
import com.elian.cms.admin.model.Order;
import com.elian.cms.admin.model.OrderDetail;
import com.elian.cms.admin.model.ShoppingCart;
import com.elian.cms.admin.model.User;
import com.elian.cms.admin.service.AreaService;
import com.elian.cms.admin.service.CompanyService;
import com.elian.cms.admin.service.DeliveryAddressService;
import com.elian.cms.admin.service.OrderDetailService;
import com.elian.cms.admin.service.OrderService;
import com.elian.cms.admin.service.ShoppingCartService;
import com.elian.cms.mall.dto.OrderItem;
import com.elian.cms.syst.action.BaseAction;
import com.elian.cms.syst.util.ApplicationUtils;
import com.elian.cms.syst.util.ElianUtils;
import com.elian.cms.syst.util.SpringUtils;

/**
 * 订单确认并保存订单信息功能
 * 
 * @author Joe
 * 
 */
@Component
@Scope("prototype")
public class MallOrderConfirmAction extends BaseAction {
	/**
	 * 序列化
	 */
	private static final long serialVersionUID = 3744498219689067010L;

	// 站点ID
	private Integer siteId;
	// 购物车传递过来的购物车ids
	private String ids;

	// 新添加的收货地址
	private DeliveryAddress address;
	// 用于收货地址单选
	private Integer addressId;
	// 是否保存收货地址
	private boolean isSave = true;
	// 支付方式
	private String deliveryType = ElianUtils.DELIVERY_TYPE_0;
	// 附言
	private String description;

	private CompanyService companyService;
	private OrderService orderService;
	private OrderDetailService orderDetailService;
	private DeliveryAddressService deliveryAddressService;
	private AreaService areaService;
	private ShoppingCartService shoppingCartService;
	
	public String save() {
		User user = ApplicationUtils.getUser();
		if (user == null)
			return null;
		saveAddress();

		MallOrderAction bean = SpringUtils.getBean("mallOrderAction");
		List<ShoppingCart> list = bean.loadShopCart();
		List<OrderItem> orderItemList = bean.getOrderItemList();
		if (orderItemList != null) {
			Order order = createOrder(bean.getTotalPrice());
			orderService.save(order);

			List<OrderDetail> odList = new ArrayList<OrderDetail>(orderItemList
					.size());
			OrderDetail detail = null;
			for (OrderItem item : orderItemList) {
				detail = new OrderDetail();
				detail.setOrder(order);
				detail.setCompany(companyService.get(item.getCompanyId()));
				detail.setCommodityType(item.getCommodityType());
				detail.setCommodityId(item.getCommodityId());
				detail.setCommodityName(item.getName());
				detail.setCommodityCount(item.getQuantity());
				detail.setCreateTime(new Date());
				detail.setDiscountedPrice(item.getUnitPrice());
				odList.add(detail);
			}
			orderDetailService.save(odList);
			// 传递参数ID
			ids = order.getId().toString();
		}
		shoppingCartService.delete(list);
		return SAVE;
	}

	public void saveAddress() {
		if (addressId != null) {
			address = deliveryAddressService.get(addressId);
		}
		else if (isSave) {
			address.setUser(ApplicationUtils.getUser());
			address.setCreateTime(new Date());
			address.setArea(areaService.get(address.getArea().getAreaCode()));
			deliveryAddressService.save(address);
		}
	}

	private Order createOrder(Integer totalPrice) {
		Order order = new Order();
		Date date = new Date();
		order.setOrderCode(new SimpleDateFormat("yyyyMMddHHmmss").format(date));
		order.setCreateTime(date);
		order.setShipping(1000);
		order.setTotalAmount(totalPrice + order.getShipping());
		order.setPayAmount(totalPrice + order.getShipping());
		order.setPaymentStatus(ElianUtils.PAYMENT_STATUS_0);
		order.setDeliveryType(deliveryType);
		order.setUser(ApplicationUtils.getUser());
		order.setConsignee(address.getConsignee());
		order.setConsigneeMobile(address.getConsigneeMobile());
		order.setOrderStatus(ElianUtils.ORDER_STATUS_0);
		return order;
	}
	
	public Integer getSiteId() {
		return siteId;
	}

	public void setSiteId(Integer siteId) {
		this.siteId = siteId;
	}

	public String getIds() {
		return ids;
	}

	public void setIds(String ids) {
		this.ids = ids;
	}

	public Integer getAddressId() {
		return addressId;
	}

	public void setAddressId(Integer addressId) {
		this.addressId = addressId;
	}

	public boolean isSave() {
		return isSave;
	}

	public void setSave(boolean isSave) {
		this.isSave = isSave;
	}

	public DeliveryAddress getAddress() {
		return address;
	}

	public void setAddress(DeliveryAddress address) {
		this.address = address;
	}

	public String getDeliveryType() {
		return deliveryType;
	}

	public void setDeliveryType(String deliveryType) {
		this.deliveryType = deliveryType;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@Resource
	public void setCompanyService(CompanyService companyService) {
		this.companyService = companyService;
	}

	@Resource
	public void setOrderService(OrderService orderService) {
		this.orderService = orderService;
	}

	@Resource
	public void setOrderDetailService(OrderDetailService orderDetailService) {
		this.orderDetailService = orderDetailService;
	}

	@Resource
	public void setDeliveryAddressService(
			DeliveryAddressService deliveryAddressService) {
		this.deliveryAddressService = deliveryAddressService;
	}
	
	@Resource
	public void setAreaService(AreaService areaService) {
		this.areaService = areaService;
	}
	
	@Resource
	public void setShoppingCartService(ShoppingCartService shoppingCartService) {
		this.shoppingCartService = shoppingCartService;
	}
}
