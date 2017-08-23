package com.elian.cms.admin.dao;

import java.util.List;

import com.elian.cms.admin.model.Industry;
import com.elian.cms.syst.dao.Dao;
import com.elian.cms.syst.model.Pagination;

public interface IndustryDao extends Dao<Industry, Integer> {
	public List<Industry> findByAll(Integer siteId, boolean disable,
			Pagination<Industry> p);

	public List<Industry> findByName();
	public List<Industry> findByName(String name);
	public List<Industry> findByIndustry();


}
