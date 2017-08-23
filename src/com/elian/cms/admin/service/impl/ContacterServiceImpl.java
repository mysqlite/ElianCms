package com.elian.cms.admin.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.elian.cms.admin.dao.ContacterDao;
import com.elian.cms.admin.model.Contacter;
import com.elian.cms.admin.model.Site;
import com.elian.cms.admin.service.ContacterService;
import com.elian.cms.syst.model.Pagination;
import com.elian.cms.syst.service.impl.ServiceImpl;

@Component(" contacterService")
public class ContacterServiceImpl extends
		ServiceImpl<ContacterDao, Contacter, Integer> implements
		ContacterService {

	public List<Contacter> findByPage(Pagination<Contacter> p,Site site){
		return dao.findByPage(p,site);
	}
	
	@Resource
	public void setDao(ContacterDao dao) {
		this.dao = dao;
	}
}
