package com.elian.cms.admin.service;

import java.util.List;

import com.elian.cms.admin.dao.DrugCommonDao;
import com.elian.cms.admin.model.DrugCommon;
import com.elian.cms.syst.model.Pagination;
import com.elian.cms.syst.service.Service;

public interface DrugCommonService extends
		Service<DrugCommonDao, DrugCommon, Integer> {
	public List<DrugCommon> findByAll(String name, String marjorFunction,
			String drugType, Pagination<DrugCommon> p);
}
