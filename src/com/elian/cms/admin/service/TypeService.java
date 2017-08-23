package com.elian.cms.admin.service;

import java.util.List;

import com.elian.cms.admin.dao.TypeDao;
import com.elian.cms.admin.model.Type;
import com.elian.cms.syst.model.Pagination;
import com.elian.cms.syst.model.SelectItem;
import com.elian.cms.syst.service.Service;

public interface TypeService extends Service<TypeDao, Type, Integer> {
	public List<Type> findByPage(Pagination<Type> p);

	public List<Type> findByTypeListPage(Pagination<Type> p,
			List<SelectItem> typeList);

	public List<Type> findByTypePage(Pagination<Type> p, String type);

	public List<Type> findByType(boolean isDisable, String type);
	
	public Type findByTypeName(String typeName);
	
	public List<Type> findMedicineType(String typeClass,Integer compId);
}
