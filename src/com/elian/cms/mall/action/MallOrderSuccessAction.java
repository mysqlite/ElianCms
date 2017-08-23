package com.elian.cms.mall.action;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.elian.cms.admin.model.Order;
import com.elian.cms.admin.service.OrderService;
import com.elian.cms.syst.action.BaseAction;
import com.elian.cms.syst.model.SelectItem;
import com.elian.cms.syst.util.ElianUtils;

/**
 * 订单完成功能
 * 
 * @author Joe
 * 
 */
@Component
@Scope("prototype")
public class MallOrderSuccessAction extends BaseAction {
	/**
	 * 序列化
	 */
	private static final long serialVersionUID = 3744498219689067010L;

	// 站点ID
	private Integer siteId;
	
	private Order order;

	private OrderService orderService;

	public String list() {
		if (order.getId() != null) {
			order = orderService.get(order.getId());
		}
		return LIST;
	}

	public Order getOrder() {
		return order;
	}

	public void setOrder(Order order) {
		this.order = order;
	}
	
	public List<SelectItem> getDeliveryTypeList() {
		return ElianUtils.getDeliveryTypeList();
	}
	
	public Integer getSiteId() {
		return siteId;
	}

	public void setSiteId(Integer siteId) {
		this.siteId = siteId;
	}
	
	@Resource
	public void setOrderService(OrderService orderService) {
		this.orderService = orderService;
	}
}
