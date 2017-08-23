package com.elian.cms.admin.dao.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import com.elian.cms.admin.dao.OrderDetailDao;
import com.elian.cms.admin.model.OrderDetail;
import com.elian.cms.syst.dao.impl.DaoImpl;
import com.elian.cms.syst.model.Pagination;

@Component("orderDetailDao")
public class OrderDetailDaoImpl extends DaoImpl<OrderDetail, Integer> implements
		OrderDetailDao {
	public List<OrderDetail> findByAll(Integer orderId,
			Pagination<OrderDetail> p) {
		StringBuffer hql = new StringBuffer();
		List<Object> params = new ArrayList<Object>();
		hql.append(" from OrderDetail o where 1=1");
		if (orderId != null && orderId > 0) {
			hql.append(" and o.order.id = ? ");
			params.add(orderId);
		}
		hql.append(" order by o.createTime,o.id desc");
		if (p != null) {
			p.setAlias("o");
			return pageByHql(hql.toString(), false, p, params.toArray());
		}
		else {
			return findByHql(hql.toString(), false, params.toArray());
		}
	}
}
