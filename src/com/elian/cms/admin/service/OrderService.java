package com.elian.cms.admin.service;

import java.util.List;

import com.elian.cms.admin.dao.OrderDao;
import com.elian.cms.admin.model.Order;
import com.elian.cms.syst.model.Pagination;
import com.elian.cms.syst.service.Service;

public interface OrderService extends Service<OrderDao, Order, Integer> {
	public List<Order> findByAll(Integer companyId, Pagination<Order> p);
}
