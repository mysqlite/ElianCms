package com.elian.cms.admin.service;

import java.util.List;

import com.elian.cms.admin.dao.ModelDao;
import com.elian.cms.admin.model.Model;
import com.elian.cms.syst.model.Pagination;
import com.elian.cms.syst.service.Service;

public interface ModelService extends Service<ModelDao, Model, Integer> {
	public List<Model> findByAll(Pagination<Model> p);
	
	public List<Model> findByComType(String comType, Pagination<Model> p);
}
