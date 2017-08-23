package com.elian.cms.admin.service;

import java.util.Collection;
import java.util.List;

import com.elian.cms.admin.dao.TemplateSetDao;
import com.elian.cms.admin.model.TemplateSet;
import com.elian.cms.syst.model.Pagination;
import com.elian.cms.syst.service.Service;

public interface TemplateSetService extends
		Service<TemplateSetDao, TemplateSet, Integer> {

	public List<TemplateSet> findByAll(Integer tempId,
			Pagination<TemplateSet> pagination);	

	public List<Integer> getAreaParentIdList(Integer tempId);
	
	public TemplateSet getByTempIdAndAreaId(Integer tempId,Integer areaId);

	public List<Integer> getAreaIdList(Integer tempId,Integer maxareaid);
	
	public List<TemplateSet> getTempParentAreas(Integer tempId);

//	public List<Integer> getInUseIds(List<Integer> idList);

	public List<TemplateSet> deleteById(Collection<Integer> pks);
}
