package com.elian.cms.admin.dao;

import java.util.List;

import com.elian.cms.admin.model.Type;
import com.elian.cms.syst.dao.Dao;
import com.elian.cms.syst.model.Pagination;
import com.elian.cms.syst.model.SelectItem;

public interface TypeDao extends Dao<Type, Integer> {
	public List<Type> findByPage(Pagination<Type> p);
	public List<Type> findByTypeListPage(Pagination<Type> p,List<SelectItem> typeList);
	public List<Type> findByTypePage(Pagination<Type> p,String type);
	
	public List<Type> findByType(boolean isDisable, String type);
	
	public Type findByTypeName(String typeName);
	
	public List<Type> findMedicineType(String typeClass,Integer compId);
}
