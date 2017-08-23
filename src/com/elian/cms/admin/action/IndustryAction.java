package com.elian.cms.admin.action;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.elian.cms.admin.model.Industry;
import com.elian.cms.admin.service.IndustryService;
import com.elian.cms.syst.action.BaseAction;
import com.elian.cms.syst.model.Pagination;
import com.elian.cms.syst.model.TrueFalseItem;
import com.elian.cms.syst.util.ApplicationUtils;
import com.elian.cms.syst.util.ElianUtils;
import com.elian.cms.syst.util.SearchParamUtils;
import com.elian.cms.syst.util.ValidateUtils;

/**
 * 行业功能
 * 
 * @author keo
 * 
 */
@Component
@Scope("prototype")
public class IndustryAction extends BaseAction {
	private static final long serialVersionUID = 8470309918943039866L;

	private Industry industry;
	private Integer id = Integer.valueOf(0);
	private boolean isEdit = false;
	private Pagination<Industry> pagination = new Pagination<Industry>(
			SearchParamUtils.getIndustryConditionMap());

	private IndustryService industryService;

	public Industry getIndustry() {
		return industry;
	}

	public void setIndustry(Industry industry) {
		this.industry = industry;
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

	public Pagination<Industry> getPagination() {
		return pagination;
	}

	public void setPagination(Pagination<Industry> pagination) {
		this.pagination = pagination;
	}

	public IndustryService getIndustryService() {
		return industryService;
	}

	@Resource
	public void setIndustryService(IndustryService industryService) {
		this.industryService = industryService;
	}

	public String save() {
	    //industry.setId(ApplicationUtils.getAjaxId());
		industryService.save(industry);
		
		return SAVE;
	}

	public void delete() {
		List<Integer> idList = ApplicationUtils.getAjaxIds();
		if (idList != null) {
			for (Integer id : idList) {
				industry = industryService.get(id);
				if (industry == null)
					continue;
				industryService.delete(industry);
			}
		}
	}

	public String edit() {
		industry = industryService.get(id);
		return EDIT;
	}

	public String list() {
		industryService.findByAll(ApplicationUtils.getSite().getId(), false,
				pagination);
		return LIST;
	}

	public TrueFalseItem getDefaultItem() {
		return ElianUtils.getTrueFalseItem();
	}
	
	public TrueFalseItem getDisableItem() {
		return ElianUtils.getDisableItem();
	}


	public void defaultData() {
		Integer id = ApplicationUtils.getAjaxId();
		Boolean def = ApplicationUtils.getAjaxDisable();
		if (id != null && def != null) {
			industry = industryService.get(id);
			if (industry == null)
				return;
			industry.setDisable(!def);
			industryService.save(industry);
		}
	}
	
	
	public void errorMsg(){
	    ApplicationUtils.sendMsg("行业名称不能为空");

	}
	
	public void validateSave() {
		if (ValidateUtils.isBlank(industry.getIndustryName()))
			this.addFieldError("industry.industryName", "行业名称不能为空");
		else if (ValidateUtils.isLengOut(industry.getIndustryName(), 20))
			this.addFieldError("industry.industryName", "行业名称必须在20字以内");
		if (ValidateUtils.isLengOut(industry.getIndustryDesc(), 255))
			this.addFieldError("industry.industryDesc", "行业描述必须在255字以内");
	}
}
