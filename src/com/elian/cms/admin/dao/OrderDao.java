package com.elian.cms.admin.dao;

import java.util.List;

import com.elian.cms.admin.model.Order;
import com.elian.cms.syst.dao.Dao;
import com.elian.cms.syst.model.Pagination;

public interface OrderDao extends Dao<Order, Integer> {

	public List<Order> findByAll(Integer companyId, Pagination<Order> p);

}
