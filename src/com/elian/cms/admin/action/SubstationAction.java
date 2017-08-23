package com.elian.cms.admin.action;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.elian.cms.admin.model.Area;
import com.elian.cms.admin.model.Substation;
import com.elian.cms.admin.service.AreaService;
import com.elian.cms.admin.service.SubstationService;
import com.elian.cms.syst.action.BaseAction;
import com.elian.cms.syst.model.Pagination;
import com.elian.cms.syst.model.SelectItem;
import com.elian.cms.syst.util.ApplicationUtils;
import com.elian.cms.syst.util.ElianCodes;
import com.elian.cms.syst.util.ElianUtils;
import com.elian.cms.syst.util.SearchParamUtils;
import com.elian.cms.syst.util.StringUtils;
import com.elian.cms.syst.util.ValidateUtils;

@Component
@Scope("prototype")
public class SubstationAction extends BaseAction {
	private static final long serialVersionUID = 6022825350206296167L;

	private Pagination<Substation> pagination = new Pagination<Substation>(
			SearchParamUtils.getSubstationConditionMap());

	private Substation substation;
	private Integer id = Integer.valueOf(0);
	private boolean isEdit = false;
	private String areaName;

	private SubstationService substationService;
	private AreaService areaService;

	public String list() {
		substationService.findByPaging(pagination);
		return LIST;
	}

	public String edit() {
		if (isEdit && id > 0) {
			substation = substationService.get(id);
		}
		else {
			substation = createSubstation();
		}
		return EDIT;
	}

	public List<SelectItem> getSexList(){
		return ElianUtils.getSexList();
	}
	
	private Substation createSubstation() {
		Substation sub = new Substation();
		sub.setStatus(ElianUtils.STATUS_1);
		sub.setCreateTime(new Date());
		return sub;
	}
	
	public void check() {
		List<Integer> idList = ApplicationUtils.getAjaxIds();
		if (idList != null)
			for (Integer id : idList) {
				substation =substationService.get(id);
				if(substation.getStatus()==ElianUtils.STATUS_0) {
					substation.setStatus(ElianUtils.STATUS_1);
					substationService.save(substation);
				}
			}
	}

	public void validateSave() {
		if (substation.getAreaId() == null || substation.getAreaId() <= 0)
			this.addFieldError("substation.areaId", "区域名称不能为空");
		else if (StringUtils.isBlank(substation.getSubName()))
			this.addFieldError("substation.subName", "分站名称不能为空");
		else if (ValidateUtils.isLengOut(substation.getSubName(), 100))
			this.addFieldError("substation.subName", "分站名称长度必须在100字以内");
		else if (StringUtils.isBlank(substation.getShortName()))
			this.addFieldError("substation.shortName", "分站简称不能为空");
		else if (ValidateUtils.isLengOut(substation.getShortName(), 50))
			this.addFieldError("substation.shortName", "分站简称长度必须在50字以内");
		else if (StringUtils.isBlank(substation.getShortDesc()))
			this.addFieldError("substation.shortDesc", "简介不能为空");
		else if (ValidateUtils.isLengOut(substation.getShortDesc(), 255))
			this.addFieldError("substation.shortDesc", "简介长度必须在255字以内");
		else if (StringUtils.isBlank(substation.getPhone()))
			this.addFieldError("substation.phone", "联系电话不能为空");
		else if (StringUtils.isBlank(substation.getAddress()))
			this.addFieldError("substation.address", "地址不能为空");
		else if (StringUtils.isBlank(substation.getPostcode()))
			this.addFieldError("substation.postcode", "邮政编码不能为空");
		else if (!ValidateUtils.isPostCode(substation.getPostcode()))
			this.addFieldError("substation.postcode", "请输入正确的邮政编码");
		else if (!StringUtils.isBlank(substation.getEmail())) {
			if (!ValidateUtils.isEmail(substation.getEmail(), 6, 35))
				this.addFieldError("substation.email", "邮箱格式不正确");
			else if (ValidateUtils.isLengOut(substation.getEmail(), 40))
				this.addFieldError("substation.email", "邮箱最大长度为40位");
		}
	}

	private List<SelectItem> substationStatusList = null;

	public List<SelectItem> getSubstationStatusList() {
		if (substationStatusList == null) {
			substationStatusList = new ArrayList<SelectItem>(4);
			substationStatusList.add(new SelectItem(ElianUtils.STATUS_0,ElianUtils.STATUS_0_CN, ElianCodes.COLOR_RED));
			substationStatusList.add(new SelectItem(ElianUtils.STATUS_1,ElianUtils.STATUS_1_CN, ElianCodes.COLOR_GREEN));
			substationStatusList.add(new SelectItem(ElianUtils.STATUS_2,ElianUtils.STATUS_2_CN, ElianCodes.COLOR_RED));
			substationStatusList.add(new SelectItem(ElianUtils.STATUS_3,ElianUtils.STATUS_3_CN));
			substationStatusList.add(new SelectItem(ElianUtils.STATUS_4,ElianUtils.STATUS_4_CN,ElianCodes.COLOR_GRAY));
			substationStatusList.add(new SelectItem(ElianUtils.STATUS_5,ElianUtils.STATUS_5_CN,ElianCodes.COLOR_RED));
		}
		return substationStatusList;
	}
	
	private List<SelectItem> mainSubstationStatusList = null;
	public List<SelectItem> getMainSubstationStatusList() {
		if (mainSubstationStatusList == null) {
			mainSubstationStatusList = new ArrayList<SelectItem>(4);
			mainSubstationStatusList.add(new SelectItem(ElianUtils.STATUS_0,ElianUtils.STATUS_0_CN, ElianCodes.COLOR_RED));
			mainSubstationStatusList.add(new SelectItem(ElianUtils.STATUS_1,ElianUtils.STATUS_1_CN, ElianCodes.COLOR_GREEN));
			mainSubstationStatusList.add(new SelectItem(ElianUtils.STATUS_2,ElianUtils.STATUS_2_CN, ElianCodes.COLOR_RED));
			mainSubstationStatusList.add(new SelectItem(ElianUtils.STATUS_3,ElianUtils.STATUS_3_CN));
		}
		return mainSubstationStatusList;
	}
	
	public String save() {
		substationService.save(substation);
		return SAVE;
	}

	public String status() {
		Integer id = ApplicationUtils.getAjaxId();
		int status = ApplicationUtils.getAjaxStatus();
		if (id != null && status > -1) {
			substation = substationService.get(id);
			if (substation == null)
				return NONE;
			if(null!=ApplicationUtils.getSubstation()) {
    			if(!ApplicationUtils.getSubstation().getId().equals(id)) {
    				substation.setStatus(status);
    				substationService.save(substation);
    			}
			}else {
				substation.setStatus(status);
				substationService.save(substation);
			}
		}
		return NONE;
	}
	
	public void delete() {
		List<Integer> idList = ApplicationUtils.getAjaxIds();
		if (idList != null) {
			for (Integer id : idList) {
				substation = substationService.get(id);
				if (substation == null)
					continue;
				substationService.delete(substation);
			}
		}
	}

	@Resource
	public void setSubstationService(SubstationService substationService) {
		this.substationService = substationService;
	}

	@Resource
	public void setAreaService(AreaService areaService) {
		this.areaService = areaService;
	}

	public Substation getSubstation() {
		return substation;
	}

	public void setSubstation(Substation substation) {
		this.substation = substation;
	}

	public Pagination<Substation> getPagination() {
		return pagination;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public boolean isEdit() {
		return isEdit;
	}

	public void setEdit(boolean isEdit) {
		this.isEdit = isEdit;
	}

	public String getAreaName() {
		if (StringUtils.isBlank(areaName) && substation != null
				&& substation.getAreaId() > 0) {
			Area area = areaService.get(substation.getAreaId());
			if (area != null)
				areaName = area.getAreaName();
		}
		return areaName;
	}

	public void setAreaName(String areaName) {
		this.areaName = areaName;
	}
}
