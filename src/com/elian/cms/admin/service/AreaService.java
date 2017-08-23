package com.elian.cms.admin.service;

import java.util.List;
import java.util.Map;

import com.elian.cms.admin.dao.AreaDao;
import com.elian.cms.admin.model.Area;
import com.elian.cms.syst.model.Pagination;
import com.elian.cms.syst.service.Service;

public interface AreaService extends Service<AreaDao, Area, Integer> {
	
	public List<Area> findAreaByAll();

	public List<Area> findByParentCode(Integer parentCode);
	
	public List<Area> findAreaByPage(Pagination<Area> p);

	public List<Area> initializationArea();

	public List<Area> findAreaNameByAreaCode(Integer areaCode);

	public List<Map<String, Object>> findAjaxAreaByAll();
	
	public List <Area> getParentAreas();
	
	public List<Map<String, Object>> findAjaxParentAreal();
	
	public List<Integer> findChirdByParent(Integer parentCode);
	
	public Area findByLikeName(String areaName);
	
	public Area findByName(String areaName) ;
	
	public List<Area> findByParentCodeNameAsc(Integer parentCode);
	
}
