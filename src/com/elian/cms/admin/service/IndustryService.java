package com.elian.cms.admin.service;

import java.util.List;

import com.elian.cms.admin.dao.IndustryDao;
import com.elian.cms.admin.model.Industry;
import com.elian.cms.syst.model.Pagination;
import com.elian.cms.syst.service.Service;

public interface IndustryService extends Service<IndustryDao, Industry, Integer> {

	public List<Industry> findByName();
	public List<Industry> findByName(String name);
	public List<Industry> findByAll(Integer id, boolean b, Pagination<Industry> pagination);
	public List<Industry> findByIndustry();

}
