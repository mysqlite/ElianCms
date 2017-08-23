package com.elian.cms.admin.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.elian.cms.admin.dao.OrderDetailDao;
import com.elian.cms.admin.model.OrderDetail;
import com.elian.cms.admin.service.OrderDetailService;
import com.elian.cms.syst.model.Pagination;
import com.elian.cms.syst.service.impl.ServiceImpl;

@Component("orderDetailService")
public class OrderDetailServiceImpl extends
		ServiceImpl<OrderDetailDao, OrderDetail, Integer> implements OrderDetailService {
	public List<OrderDetail> findByAll(Integer orderId, Pagination<OrderDetail> p) {
		return dao.findByAll(orderId, p);
	}

	@Resource
	public void setDao(OrderDetailDao dao) {
		this.dao = dao;
	}
}
