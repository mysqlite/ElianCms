package com.elian.cms.admin.dao;

import java.util.List;

import com.elian.cms.admin.model.Area;
import com.elian.cms.syst.dao.Dao;
import com.elian.cms.syst.model.Pagination;

public interface AreaDao extends Dao<Area, Integer> {
	public List<Area> findAreaByAll();
	
	public List<Area> findByParentCode(Integer parentCode);

	public List<Area> findAreaByPage(Pagination<Area> p);

	public List<Area> initializationArea();

	public List<Area> findAreaNameByAreaCode(Integer areaCode);
	
	public List <Area> getParentAreas();
	
	public List<Integer> findChirdByParent(Integer parentCode);
	
	public Area findByLikeName(String areaName);
	
	public Area findByName(String areaName) ;
	
	public List<Area> findByParentCodeNameAsc(Integer parentCode);//根据名称排序
	
	public String findParentByChirdFunction(Integer chirdCode);
	
	public String findChirdByParentFunction(Integer parentCode);
}
