package com.elian.cms.admin.service;

import java.util.List;

import com.elian.cms.admin.dao.SubstationDao;
import com.elian.cms.admin.model.Substation;
import com.elian.cms.syst.model.Pagination;
import com.elian.cms.syst.service.Service;

public interface SubstationService extends
		Service<SubstationDao, Substation, Integer> {
	public List<Substation> findByPaging(Pagination<Substation> p);

	public Substation findSubstationByAreaCode(Integer areaCode);
}
