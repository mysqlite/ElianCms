package com.elian.cms.admin.action;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.elian.cms.admin.model.Contacter;
import com.elian.cms.admin.service.ContacterService;
import com.elian.cms.syst.action.BaseAction;
import com.elian.cms.syst.model.Pagination;
import com.elian.cms.syst.util.ApplicationUtils;
import com.elian.cms.syst.util.SearchParamUtils;
import com.elian.cms.syst.util.StringUtils;
import com.elian.cms.syst.util.ValidateUtils;

@Component
@Scope("prototype")
public class ContacterAction extends BaseAction {
	private static final long serialVersionUID = -5568760628858305953L;

	private Integer id;
	private boolean edit;
	private Contacter contacter;
	private Pagination<Contacter> pagination = new Pagination<Contacter>(SearchParamUtils.getContacterConditionMap());
	
	private ContacterService contacterService;

	public String list() {
		contacterService.findByPage(pagination,ApplicationUtils.getSite());
		return LIST;
	}
	
	public String edit() {
		if(id!=null&&id>0)
		contacter=contacterService.get(id);
		return EDIT;
	}
	
	public String save() {
		if(contacter.getSite()==null) {
		contacter.setSite(ApplicationUtils.getSite());
		}
		contacterService.save(contacter);
		return SAVE;
	}
	
	public String disable() {
		Integer id = ApplicationUtils.getAjaxId();
		Boolean disable = ApplicationUtils.getAjaxDisable();
		if (id != null && disable != null) {
			contacter = contacterService.get(id);
			if (contacter == null)
				return NONE;
			contacter.setDisable(!disable);
			contacterService.save(contacter);
		}
		return NONE;
	}
	
	public String delete() {
		List<Integer> idList = ApplicationUtils.getAjaxIds();
		if (idList != null)
			for (Integer id : idList) {
				contacter = contacterService.get(id);
				contacterService.delete(contacter);
			}
		return NONE;
	}
	
	public void validateSave() {
		if(StringUtils.isBlank(contacter.getContactName()))
			this.addFieldError("contacter.contactName", "请输入联系名称");
		else if(ValidateUtils.isLengOut(contacter.getContactName(), 50))
			this.addFieldError("contacter.contactName", "联系名称在50字以内");
		if(StringUtils.isBlank(contacter.getContacter()))
			this.addFieldError("contacter.contacter", "请输入联系人");
		else if(ValidateUtils.isLengOut(contacter.getContacter(),50))
			this.addFieldError("contacter.contacter", "联系人长度在50字以内");
		if(StringUtils.isBlank(contacter.getPhone()))
			this.addFieldError("contacter.phone", "请输入联系电话");
		else if(ValidateUtils.isNotPhoneAndMobile(contacter.getPhone()))
			this.addFieldError("contacter.phone", "请输入正确的联系电话");
		if(StringUtils.isNotBlank(contacter.getQq())) {
			if(!ValidateUtils.isQQ(contacter.getQq()))
				this.addFieldError("contacter.qq", "请输入正确的QQ号");
		}
		if(StringUtils.isNotBlank(contacter.getMsn())) {
			if(!ValidateUtils.isEmail(contacter.getMsn(),5,50))
				this.addFieldError("contacter.msn", "请输入正确的MSN号码");
		}
		if(StringUtils.isNotBlank(contacter.getFax())) {
			if(!ValidateUtils.isPhone(contacter.getFax()))
				this.addFieldError("contacter.fax", "请输入正确的fax号码");
		}
		if(StringUtils.isNotBlank(contacter.getPostcode())) {
			if(!ValidateUtils.isPostCode(contacter.getPostcode()))
				this.addFieldError("contacter.postcode", "请输入正确的邮政编码");
		}
		
		if(ValidateUtils.isLengOut(contacter.getDepartment(), 50))
			this.addFieldError("contacter.department", "部门长度在50字以内");
		if(ValidateUtils.isLengOut(contacter.getAddress(),255))
			this.addFieldError("contacter.address", "地址长度在252字以内");
	}
	
	@Resource
	public void setContacterService(ContacterService contacterService) {
		this.contacterService = contacterService;
	}
	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public boolean isEdit() {
		return edit;
	}

	public void setEdit(boolean edit) {
		this.edit = edit;
	}

	public Contacter getContacter() {
		return contacter;
	}

	public void setContacter(Contacter contacter) {
		this.contacter = contacter;
	}

	public Pagination<Contacter> getPagination() {
		return pagination;
	}

	public void setPagination(Pagination<Contacter> pagination) {
		this.pagination = pagination;
	}

}
