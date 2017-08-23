package com.elian.cms.admin.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.elian.cms.admin.dao.DrugCommonDao;
import com.elian.cms.admin.model.DrugCommon;
import com.elian.cms.admin.service.DrugCommonService;
import com.elian.cms.syst.model.Pagination;
import com.elian.cms.syst.service.impl.ServiceImpl;

@Component("drugCommonService")
public class DrugCommonServiceImpl extends ServiceImpl<DrugCommonDao, DrugCommon, Integer>
		implements DrugCommonService {
	public List<DrugCommon> findByAll(String name, String marjorFunction,
			String drugType, Pagination<DrugCommon> p) {
		return dao.findByAll(name, marjorFunction, drugType, p);
	}

	@Autowired
	public void setDao(DrugCommonDao dao) {
		this.dao = dao;
	}
}
