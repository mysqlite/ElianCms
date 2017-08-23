package com.elian.cms.admin.dao.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import com.elian.cms.admin.dao.DeliveryAddressDao;
import com.elian.cms.admin.model.DeliveryAddress;
import com.elian.cms.syst.dao.impl.DaoImpl;
import com.elian.cms.syst.model.Pagination;

@Component("deliveryAddressDao")
public class DeliveryAddressDaoImpl extends DaoImpl<DeliveryAddress, Integer>
		implements DeliveryAddressDao {
	public List<DeliveryAddress> findByAll(Integer userId,
			Pagination<DeliveryAddress> p) {
		StringBuffer hql = new StringBuffer();
		List<Object> params = new ArrayList<Object>();
		hql.append(" from DeliveryAddress o where 1=1");
		if (userId != null && userId > 0) {
			hql.append(" and o.user.id = ? ");
			params.add(userId);
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
