package com.elian.cms.admin.dao;

import java.util.List;

import com.elian.cms.admin.model.DrugCommon;
import com.elian.cms.syst.dao.Dao;
import com.elian.cms.syst.model.Pagination;

public interface DrugCommonDao extends Dao<DrugCommon, Integer> {
	public List<DrugCommon> findByAll(String name, String marjorFunction,
			String drugType, Pagination<DrugCommon> p);
}
