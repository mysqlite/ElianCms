package com.elian.cms.admin.dao;

import java.util.List;

import com.elian.cms.admin.model.TemplateSet;
import com.elian.cms.syst.dao.Dao;
import com.elian.cms.syst.model.Pagination;

public interface TemplateSetDao extends Dao<TemplateSet, Integer> {
	
	//public List<TemplateSet> findByAll(String templeteName);

	public List<TemplateSet> findByAll(Integer tempId, Pagination<TemplateSet> pagination);

	public TemplateSet getByTempIdAndAreaId(Integer tempId, Integer areaId);
	
	public List<TemplateSet> getTempParentAreas(Integer tempId);
}
