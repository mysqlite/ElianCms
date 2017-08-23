package com.elian.cms.admin.service;

import java.util.List;

import com.elian.cms.admin.dao.OrderDetailDao;
import com.elian.cms.admin.model.OrderDetail;
import com.elian.cms.syst.model.Pagination;
import com.elian.cms.syst.service.Service;

public interface OrderDetailService extends
		Service<OrderDetailDao, OrderDetail, Integer> {
	public List<OrderDetail> findByAll(Integer orderId,
			Pagination<OrderDetail> p);
}
