package com.elian.cms.admin.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.elian.cms.admin.dao.SubstationDao;
import com.elian.cms.admin.model.Substation;
import com.elian.cms.admin.service.SubstationService;
import com.elian.cms.syst.model.Pagination;
import com.elian.cms.syst.service.impl.ServiceImpl;
@Component("substationService")
public class SubstationServiceImpl extends
		ServiceImpl<SubstationDao, Substation, Integer> implements
		SubstationService {
	public List<Substation> findByPaging(Pagination<Substation> p){
		return dao.findByPaging(p);
	}

	public Substation findSubstationByAreaCode(Integer areaCode) {
		return dao.findSubstationByAreaCode(areaCode);
	}
	@Resource
	public void setDao(SubstationDao dao) {
		this.dao = dao;
	}
}
