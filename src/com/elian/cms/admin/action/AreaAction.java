package com.elian.cms.admin.action;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import net.sf.json.JSONObject;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import com.elian.cms.admin.model.Area;
import com.elian.cms.admin.service.AreaService;
import com.elian.cms.syst.action.BaseAction;
import com.elian.cms.syst.model.Pagination;
import com.elian.cms.syst.util.ApplicationUtils;
import com.elian.cms.syst.util.ElianUtils;
import com.elian.cms.syst.util.SearchParamUtils;
import com.elian.cms.syst.util.ValidateUtils;

@Component
@Scope("prototype")
public class AreaAction extends BaseAction {
	private static final long serialVersionUID = -5570009052114373482L;
	private Pagination<Area> pagination = new Pagination<Area>(SearchParamUtils.getAreaActionConditionMap());
	private int id;
	private Area area;
	private boolean edit;
	private boolean disable;
	private AreaService areaService;
	private Integer depth;
	private Integer areaCode;
	
	public void check() {
		List<Integer> idList = ApplicationUtils.getAjaxIds();
		if (idList != null)
			for (Integer id : idList) {
				area = areaService.get(id);
				area.setDisable(!area.isDisable());
				areaService.save(area);
			}
	}
	
	public List<Area> findAreaByAll() {
		return areaService.findAreaByAll();
	}
	
	public void areaList() {
		List<Area> aList=new ArrayList<Area>();
		JSONObject obj=new JSONObject();
		if(areaCode!=null&&areaCode>0) {
			aList.add(areaService.get(areaCode));
			obj.put("area", aList);
		  	ApplicationUtils.sendJsonpObj(obj);
		  	return;
		}
	  	if(ApplicationUtils.isMainStation()) {
	  		aList=areaService.initializationArea();
	  	}
	  	else if(ApplicationUtils.isSubStation()) 
	  		aList.add(areaService.get(ApplicationUtils.getSubstation().getAreaId()));
	  	else {
	  		aList.add(areaService.get(ApplicationUtils.getHospital().getAreaId()));
	  	}
	  	obj.put("area", aList);
	  	ApplicationUtils.sendJsonpObj(obj);
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
		ApplicationUtils.sendJsonArray(list);
	}
	public String list() {
		areaService.findAreaByPage(pagination);
		return LIST;
	}

	public String edit() {
		if (edit && id > 0) {
			area = areaService.get(id);
		}
		return EDIT;
	}

	public String show() {
		if (id > 0) {
			area = areaService.get(id);
			return SHOW;
		}
		else {
			return SAVE;
		}
	}

	public String save() {
		areaService.save(area);
		return SAVE;
	}
	
	public String disable() {
		Integer id = ApplicationUtils.getAjaxId();
		Boolean disable = ApplicationUtils.getAjaxDisable();
		if (id != null & disable != null) {
			area = areaService.get(id);
			if (area == null)
				return NONE;
			area.setDisable(!disable);
			areaService.save(area);
		}
		return NONE;
	}

	public String sort() {
		Integer id = ApplicationUtils.getAjaxId();
		Integer sort = ApplicationUtils.getAjaxSort();
		if (id != null && sort != null) {
			area = areaService.get(id);
			if (area == null)
				return NONE;
			area.setAreaSort(sort);
			areaService.save(area);
		}
		return NONE;
	}

	public String delete() {
		List<Integer> idList = ApplicationUtils.getAjaxIds();
		if (idList != null)
			for (Integer id : idList) {
				area = areaService.get(id);
				areaService.delete(area);
			}
		return NONE;
	}

	public void validateSave() {
		if(null!=area.getAreaCode()) {
    		if (area.getAreaCode() < 110000)
    			this.addFieldError("area.areaCode", "区划编码不符合规则");
    		Area areaCode = areaService.get(area.getAreaCode());
    		if (!edit) {
    			if (areaCode != null)
    				this.addFieldError("area.areaCode", "区划编码已存在,请重新输入");
    		}
		}else {
			this.addFieldError("area.areaCode","请输入区划编码");
		}
		if (!ValidateUtils.strLenth(area.getAreaName(), 1, 20))
			this.addFieldError("area.areaName", "区划名称长度为1-20字之间");
		if (area.getParentCode() == null)
			this.addFieldError("area.parentId", "请选择区划上级");
		if (ValidateUtils.isLengOut(area.getNote(), 255))
			this.addFieldError("area.note", "区划描述最大长度为255字");
		if (area.getAreaSort() == null|| ValidateUtils.isNumber(area.getNote().toString())|| area.getAreaSort() > 9999) {
			this.addFieldError("area.areaSort", "请填写区划排序,且为1-9999的正整数");
		}
	}

	public void dwrDelete(Integer id) {
		area = areaService.get(id);
		areaService.delete(area);
	}

	public String init() {
		List<Area> areas = areaService.initializationArea();
		for (int i = 0, size = areas.size(); i < size; i++) {
			Area area = areas.get(i);
			area.setAreaSort(i);
			areaService.save(area);
		}
		return SAVE;
	}

	public void updateName(Integer id, String name) {
		area = areaService.get(id);
		area.setAreaName(name);
		areaService.save(area);
	}

	public Area getArea(Integer areaCode) {
		return areaService.get(areaCode);
	}

	public Pagination<Area> getPagination() {
		return pagination;
	}

	public void setPagination(Pagination<Area> pagination) {
		this.pagination = pagination;
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

	public boolean isEdit() {
		return edit;
	}

	public void setEdit(boolean edit) {
		this.edit = edit;
	}

	public boolean isDisable() {
		return disable;
	}

	public void setDisable(boolean disable) {
		this.disable = disable;
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
	
	public void	topAreaTree() {
		List<Map<String, Object>> list=new ArrayList<Map<String,Object>>();
		
		Map<String, Object> parentMap= new LinkedHashMap<String, Object>();
		parentMap.put("ID", 0);
		parentMap.put("NAME", "全国");
		parentMap.put("PID", -1);
		parentMap.put("OPEN", ElianUtils.FALSE_STR);
		list.add(parentMap);
		
		List<Area> areaList=areaService.findByParentCode(0);
		if (!CollectionUtils.isEmpty(areaList)) {
			Map<String, Object> map = null;
			for (Area area : areaList) {
				map = new LinkedHashMap<String, Object>();
				map.put("ID", area.getAreaCode());
				map.put("NAME", area.getAreaName());
				map.put("PID", 0);
				map.put("OPEN", ElianUtils.FALSE_STR);
				list.add(map);
			}
		}

		ApplicationUtils.sendJsonArray(list);
	}

	public Integer getAreaCode() {
		return areaCode;
	}

	public void setAreaCode(Integer areaCode) {
		this.areaCode = areaCode;
	}
	
}
