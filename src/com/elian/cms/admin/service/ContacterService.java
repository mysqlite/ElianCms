package com.elian.cms.admin.service;

import java.util.List;

import com.elian.cms.admin.dao.ContacterDao;
import com.elian.cms.admin.model.Contacter;
import com.elian.cms.admin.model.Site;
import com.elian.cms.syst.model.Pagination;
import com.elian.cms.syst.service.Service;

public interface ContacterService extends Service<ContacterDao,Contacter, Integer> {
	
	public List<Contacter> findByPage(Pagination<Contacter> p,Site site);

}
