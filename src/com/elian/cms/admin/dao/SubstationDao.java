package com.elian.cms.admin.dao;

import java.util.List;

import com.elian.cms.admin.model.Substation;
import com.elian.cms.syst.dao.Dao;
import com.elian.cms.syst.model.Pagination;

public interface SubstationDao extends Dao<Substation, Integer> {
	public List<Substation> findByPaging(Pagination<Substation> p);
	
	public Substation findSubstationByAreaCode(Integer areaCode);

}
