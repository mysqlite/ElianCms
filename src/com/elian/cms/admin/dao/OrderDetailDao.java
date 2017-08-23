package com.elian.cms.admin.dao;

import java.util.List;

import com.elian.cms.admin.model.OrderDetail;
import com.elian.cms.syst.dao.Dao;
import com.elian.cms.syst.model.Pagination;

public interface OrderDetailDao extends Dao<OrderDetail, Integer> {

	public List<OrderDetail> findByAll(Integer orderId,
			Pagination<OrderDetail> p);

}
