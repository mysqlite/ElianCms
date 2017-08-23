package com.elian.cms.admin.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.elian.cms.admin.dao.TypeDao;
import com.elian.cms.admin.model.Type;
import com.elian.cms.admin.service.TypeService;
import com.elian.cms.syst.model.Pagination;
import com.elian.cms.syst.model.SelectItem;
import com.elian.cms.syst.service.impl.ServiceImpl;
@Component("typeService")
public class TypeServiceImpl extends ServiceImpl<TypeDao, Type, Integer> implements TypeService {

	public List<Type> findByPage(Pagination<Type> p){
		return dao.findByPage(p);
	}
	
	
	public List<Type> findByTypeListPage(Pagination<Type> p,List<SelectItem> typeList){
		return dao.findByTypeListPage(p, typeList);
	}
	
	public List<Type> findByTypePage(Pagination<Type> p,String type){
		return dao.findByTypePage(p, type);
	}
	
	public List<Type> findByType(boolean isDisable, String type) {
		return dao.findByType(isDisable, type);
	}
	
	public Type findByTypeName(String typeName) {
		return dao.findByTypeName(typeName);
	}
	
	public List<Type> findMedicineType(String typeClass,Integer compId){
		return dao.findMedicineType(typeClass, compId);
	}

	@Resource
	public void setDao(TypeDao dao) {
		this.dao = dao;
	}
}
