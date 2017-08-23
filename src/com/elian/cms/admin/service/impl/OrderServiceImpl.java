package com.elian.cms.admin.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.elian.cms.admin.dao.OrderDao;
import com.elian.cms.admin.model.Order;
import com.elian.cms.admin.service.OrderService;
import com.elian.cms.syst.model.Pagination;
import com.elian.cms.syst.service.impl.ServiceImpl;

@Component("orderService")
public class OrderServiceImpl extends
		ServiceImpl<OrderDao, Order, Integer> implements OrderService {
	public List<Order> findByAll(Integer orderId, Pagination<Order> p) {
		return dao.findByAll(orderId, p);
	}

	@Resource
	public void setDao(OrderDao dao) {
		this.dao = dao;
	}
}
