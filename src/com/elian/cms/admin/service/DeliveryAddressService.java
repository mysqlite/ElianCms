package com.elian.cms.admin.service;

import java.util.List;

import com.elian.cms.admin.dao.DeliveryAddressDao;
import com.elian.cms.admin.model.DeliveryAddress;
import com.elian.cms.syst.model.Pagination;
import com.elian.cms.syst.service.Service;

public interface DeliveryAddressService extends
		Service<DeliveryAddressDao, DeliveryAddress, Integer> {
	public List<DeliveryAddress> findByAll(Integer userId,
			Pagination<DeliveryAddress> p);
}
