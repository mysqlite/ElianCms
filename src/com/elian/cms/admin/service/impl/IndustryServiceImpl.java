package com.elian.cms.admin.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.elian.cms.admin.dao.IndustryDao;
import com.elian.cms.admin.model.Industry;
import com.elian.cms.admin.service.IndustryService;
import com.elian.cms.syst.model.Pagination;
import com.elian.cms.syst.service.impl.ServiceImpl;

@Component("industryService")
public class IndustryServiceImpl extends
		ServiceImpl<IndustryDao, Industry, Integer> implements IndustryService {

	public List<Industry> findByName() {
		// TODO Auto-generated method stub
		return null;
	}

	public List<Industry> findByName(String name) {
		// TODO Auto-generated method stub
		return null;
	}
 
	public List<Industry> findByAll(Integer id, boolean b, Pagination<Industry> pagination) {
		  return dao.findByAll(id, b, pagination);
	}
	
	@Autowired
	public void setDao(IndustryDao dao) {
		this.dao = dao;
	}

	public List<Industry> findByIndustry() {
		 return dao.findByIndustry();
	}

	

}
