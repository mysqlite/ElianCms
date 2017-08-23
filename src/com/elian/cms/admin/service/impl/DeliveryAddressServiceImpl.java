package com.elian.cms.admin.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.elian.cms.admin.dao.DeliveryAddressDao;
import com.elian.cms.admin.model.DeliveryAddress;
import com.elian.cms.admin.service.DeliveryAddressService;
import com.elian.cms.syst.model.Pagination;
import com.elian.cms.syst.service.impl.ServiceImpl;

@Component("deliveryAddressService")
public class DeliveryAddressServiceImpl extends
		ServiceImpl<DeliveryAddressDao, DeliveryAddress, Integer> implements
		DeliveryAddressService {
	public List<DeliveryAddress> findByAll(Integer userId,
			Pagination<DeliveryAddress> p) {
		return dao.findByAll(userId, p);
	}

	@Resource
	public void setDao(DeliveryAddressDao dao) {
		this.dao = dao;
	}
}
