package com.elian.cms.admin.dao;

import java.util.List;

import com.elian.cms.admin.model.Contacter;
import com.elian.cms.admin.model.Site;
import com.elian.cms.syst.dao.Dao;
import com.elian.cms.syst.model.Pagination;

public interface ContacterDao extends Dao<Contacter, Integer> {
	public List<Contacter> findByPage(Pagination<Contacter> p,Site site);

}
