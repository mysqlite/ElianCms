package com.elian.cms.front.action;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONObject;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.elian.cms.admin.model.Area;
import com.elian.cms.admin.service.AreaService;
import com.elian.cms.syst.action.BaseAction;
import com.elian.cms.syst.util.ApplicationUtils;
import com.elian.cms.syst.util.GetPinyin;

@Component
@Scope("prototype")
public class FrontAreaAction extends BaseAction {
	private static final long serialVersionUID = 6240784261237794552L;
	private int id;
	private Area area;
	private AreaService areaService;
	private Integer depth;
	
	
	public List<Area> findAreaByAll() {
		return areaService.findAreaByAll();
	}
	
	public List<Area> areaList(HttpServletRequest request,Integer areaCode) {
		List<Area> aList=new ArrayList<Area>();
		if(areaCode!=null&&areaCode>0) {
			aList.add(areaService.get(areaCode));
			return aList;
		}
	  	if(ApplicationUtils.isMainStation(request)) {
	  		return areaService.initializationArea();
	  	}
	  	else if(ApplicationUtils.isSubStation(request)) 
	  		aList.add(areaService.get(ApplicationUtils.getSubstation(request).getAreaId()));
	  	else {
	  		aList.add(areaService.get(ApplicationUtils.getHospital(request).getAreaId()));
	  	}
	 return aList;
	}
	
	public void areapro() {
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		List<Area> areas=areaService.findByParentCode(id);
		if(depth==null)
			depth=1;
		if(areas!=null) {
			Map<String, Object> map = null;
			for (Area a : areas) {
				map = new LinkedHashMap<String, Object>();
				map.put("id", a.getAreaCode());
				map.put("name",a.getAreaName());
				if(depth<2) {
					map.put("open", true);
					map.put("isParent", true);
				}
				else {
    				map.put("open", false);
    				map.put("isParent", false);
				}
				map.put("depth",depth+1);
				list.add(map);
			}
		}
		JSONObject obj=new JSONObject();
		obj.put("area",list);
		ApplicationUtils.sendJsonpObj(obj);
		ApplicationUtils.sendJsonArray(list);
	}
	
	public void areaJson() {
		List<Area> areas=areaService.findByParentCodeNameAsc(id);
		if(areas!=null) {
			for (Area a : areas) {
			 if(a.getAreaCode().equals(500000)) {
				 a.setFirstPinying("cqs");
				 a.setAllPinying("chongqingshi");
			  }
			 else {
				 a.setFirstPinying(GetPinyin.getPinYinHeadChar(a.getAreaName()));
				 a.setAllPinying(GetPinyin.getPinYin(a.getAreaName()));
			 }
			}
		}
		JSONObject obj = new JSONObject();
		obj.put("area", areas);
		ApplicationUtils.sendJsonpObj(obj);
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Area getArea() {
		return area;
	}

	public void setArea(Area area) {
		this.area = area;
	}

	
	public Integer getDepth() {
		return depth;
	}

	public void setDepth(Integer depth) {
		this.depth = depth;
	}

	@Resource
	public void setAreaService(AreaService areaService) {
		this.areaService = areaService;
	}
}
