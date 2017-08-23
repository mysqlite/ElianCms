package com.elian.cms.admin.dao.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import com.elian.cms.admin.dao.OrderDao;
import com.elian.cms.admin.model.Order;
import com.elian.cms.syst.dao.impl.DaoImpl;
import com.elian.cms.syst.model.Pagination;

@Component("orderDao")
public class OrderDaoImpl extends DaoImpl<Order, Integer> implements OrderDao {
	public List<Order> findByAll(Integer companyId, Pagination<Order> p) {
		StringBuffer hql = new StringBuffer();
		List<Object> params = new ArrayList<Object>();
		hql.append(" from Order o where 1=1");
		if (companyId != null && companyId > 0) {
			hql.append(" and exists( select 1 from OrderDetail od where od.order.id = o.id and od.company.id = ? )");
			params.add(companyId);
		}
		hql.append(" order by o.createTime desc,o.id desc");
		if (p != null) {
			p.setAlias("o");
			return pageByHql(hql.toString(), false, p, params.toArray());
		}
		else {
			return findByHql(hql.toString(), false, params.toArray());
		}
	}
}
