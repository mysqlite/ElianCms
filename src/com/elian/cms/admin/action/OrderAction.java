package com.elian.cms.admin.action;

import java.util.List;

import javax.annotation.Resource;

import net.sf.json.JSONObject;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.elian.cms.admin.model.Order;
import com.elian.cms.admin.service.OrderService;
import com.elian.cms.syst.action.BaseAction;
import com.elian.cms.syst.model.Pagination;
import com.elian.cms.syst.model.SelectItem;
import com.elian.cms.syst.util.ApplicationUtils;
import com.elian.cms.syst.util.ElianUtils;
import com.elian.cms.syst.util.HibernateEagerLoadingUtil;
import com.elian.cms.syst.util.SearchParamUtils;

/**
 * 订单功能
 * 
 * @author Joe
 * 
 */
@Component
@Scope("prototype")
public class OrderAction extends BaseAction {
	/**
	 * 序列化
	 */
	private static final long serialVersionUID = 3744498219689067010L;

	private Integer compId;
	private Pagination<Order> pagination = new Pagination<Order>(
			SearchParamUtils.getOrderConditionMap());

	private OrderService orderService;

	public String list() {
		orderService.findByAll(
				ApplicationUtils.getCompany() != null ? ApplicationUtils
						.getCompany().getId() : compId, pagination);
		return LIST;
	}
	
	public void ajaxList() {
		list();
		HibernateEagerLoadingUtil.eagerLoadFiled(pagination.getList());
		JSONObject obj = new JSONObject();
		obj.put("pagi", pagination.getList());
		ApplicationUtils.sendJsonpObj(obj);
	}

	public void delete() {
		List<Integer> idList = ApplicationUtils.getAjaxIds();
		if (idList != null) {
			Order order = null;
			for (Integer id : idList) {
				order = orderService.get(id);
				if (order == null)
					continue;
				orderService.delete(order);
			}
		}
	}

	public String trees() {
		if (ApplicationUtils.isCompany()) {
			return list();
		}
		return "tree";
	}

	public List<SelectItem> getOrderStatusList() {
		return ElianUtils.getOrderStatusList();
	}

	public List<SelectItem> getPaymentStatusList() {
		return ElianUtils.getPaymentStatusList();
	}

	public Integer getCompId() {
		return compId;
	}

	public void setCompId(Integer compId) {
		this.compId = compId;
	}

	public Pagination<Order> getPagination() {
		return pagination;
	}

	@Resource
	public void setOrderService(OrderService orderService) {
		this.orderService = orderService;
	}
}
