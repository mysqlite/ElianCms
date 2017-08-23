package com.elian.cms.admin.dao;

import java.util.List;

import com.elian.cms.admin.model.DeliveryAddress;
import com.elian.cms.syst.dao.Dao;
import com.elian.cms.syst.model.Pagination;

public interface DeliveryAddressDao extends Dao<DeliveryAddress, Integer> {
	public List<DeliveryAddress> findByAll(Integer userId,
			Pagination<DeliveryAddress> p);
}
