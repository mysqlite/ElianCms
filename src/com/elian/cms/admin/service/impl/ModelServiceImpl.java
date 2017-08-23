package com.elian.cms.admin.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.elian.cms.admin.dao.ModelDao;
import com.elian.cms.admin.model.Model;
import com.elian.cms.admin.service.ModelService;
import com.elian.cms.syst.model.Pagination;
import com.elian.cms.syst.service.impl.ServiceImpl;
@Component("modelService")
public class ModelServiceImpl extends ServiceImpl<ModelDao, Model, Integer>
		implements ModelService {
	public List<Model> findByAll(Pagination<Model> p) {
		return dao.findByAll(p);
	}
	
	public List<Model> findByComType(String comType, Pagination<Model> p) {
		return dao.findByComType(comType, p);
	}

	@Resource
	public void setDao(ModelDao dao) {
		this.dao = dao;
	}
}
