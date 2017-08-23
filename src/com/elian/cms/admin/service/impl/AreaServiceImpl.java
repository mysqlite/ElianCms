package com.elian.cms.admin.service.impl;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.elian.cms.admin.dao.AreaDao;
import com.elian.cms.admin.model.Area;
import com.elian.cms.admin.service.AreaService;
import com.elian.cms.syst.model.Pagination;
import com.elian.cms.syst.service.impl.ServiceImpl;
@Component("areaService")
public class AreaServiceImpl extends ServiceImpl<AreaDao, Area, Integer>
		implements AreaService {
	public List<Area> findAreaByAll() {
		return dao.findAreaByAll();
	}
	
	public List<Area> findByParentCode(Integer parentCode){
		return dao.findByParentCode(parentCode);
	}

	public List<Area> findAreaByPage(Pagination<Area> p) {
		return dao.findAreaByPage(p);
	}

	public List<Area> initializationArea() {
		return dao.initializationArea();
	}

	public List<Area> findAreaNameByAreaCode(Integer areaCode) {
		return dao.findAreaNameByAreaCode(areaCode);
	}
	
	public List<Integer> findChirdByParent(Integer parentCode){
		return dao.findChirdByParent(parentCode);
	}
	
	public List<Map<String, Object>> findAjaxAreaByAll() {
		List<Area> a = dao.findAreaByAll();
		List<Map<String, Object>> treeModels = new ArrayList<Map<String, Object>>();
		Map<String, Object> map =null;
		for (Area area : a) {
			map = new LinkedHashMap<String, Object>();
			map.put("id", area.getAreaCode());
			map.put("pId", area.getParentCode());
			map.put("name", area.getAreaName());
			treeModels.add(map);
		}
		return treeModels;
	}
	
	public List<Map<String, Object>> findAjaxParentAreal() {
		List<Area> a = dao.getParentAreas();
		List<Map<String, Object>> treeModels = new ArrayList<Map<String, Object>>();
		Map<String, Object> map =null;
		for (Area area : a) {
			map = new LinkedHashMap<String, Object>();
			map.put("id", area.getAreaCode());
			map.put("pId", area.getParentCode());
			map.put("name", area.getAreaName());
			treeModels.add(map);
		}
		return treeModels;
	}
	
	public List <Area> getParentAreas(){
		return dao.getParentAreas();
	}
	
	public Area findByLikeName(String areaName) {
		return dao.findByLikeName(areaName);
	}
	
	public Area findByName(String areaName) {
		return dao.findByName(areaName);
	}
	
	public List<Area> findByParentCodeNameAsc(Integer parentCode){
		return dao.findByParentCodeNameAsc(parentCode);
	}

	@Resource
	public void setDao(AreaDao dao) {
		this.dao = dao;
	}
}
