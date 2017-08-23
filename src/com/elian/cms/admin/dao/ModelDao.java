package com.elian.cms.admin.dao;

import java.util.List;

import com.elian.cms.admin.model.Model;
import com.elian.cms.syst.dao.Dao;
import com.elian.cms.syst.model.Pagination;

public interface ModelDao extends Dao<Model, Integer> {
	public List<Model> findByAll(Pagination<Model> p);

	public List<Model> findByComType(String comType, Pagination<Model> p);
}
