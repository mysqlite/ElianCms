package com.elian.cms.admin.action;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.beans.BeanUtils;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import com.elian.cms.admin.model.Company;
import com.elian.cms.admin.service.AreaService;
import com.elian.cms.admin.service.CompanyService;
import com.elian.cms.admin.service.SiteFileService;
import com.elian.cms.syst.action.BaseAction;
import com.elian.cms.syst.model.Pagination;
import com.elian.cms.syst.model.SelectItem;
import com.elian.cms.syst.util.ApplicationUtils;
import com.elian.cms.syst.util.ElianUtils;
import com.elian.cms.syst.util.FilePathUtils;
import com.elian.cms.syst.util.SearchParamUtils;
import com.elian.cms.syst.util.StringUtils;
import com.elian.cms.syst.util.ValidateUtils;

@Component
@Scope("prototype")
public class CompanyAction extends BaseAction {
	
	private static final long serialVersionUID = 4724747521477456700L;
	
	private Integer id=null;
	private Integer areaId=null;
	private Company company=null;
	private boolean isEdit=false;
	private String paramId;
	private Integer submitId;
	private String compName;
	
	private Pagination<Company> pagination=new Pagination<Company>(
			SearchParamUtils.getCompanyConditionMap());
	private List<SelectItem> statusList = null;
	private List<SelectItem> availableList=null;

	private CompanyService companyService=null;
	private AreaService areaService=null;
	private SiteFileService siteFileService;
	
	public String saveCompany(){
		company.setBusLine(FilePathUtils.getConContext(company.getBusLine()));
		company.setIntroduce(FilePathUtils.getConContext(company.getIntroduce()));
		
		company.setArea(areaService.get(company.getArea().getId()));
		companyService.save(company,isEdit);
		
		siteFileService.saveFileToFtp(company,getPrevFile(),company.getPermitImg(),company.getLogoImg(),company.getCompanyImg(),company.getMapImg());
		siteFileService.saveConContext(company, getEditorPrevImg(), company.getBusLine(),company.getIntroduce());
		String result=companyTree();
		return result==null?list():result;
	}
	
	public String edit(){
		if(id==null) return list();
		company=new Company();
		BeanUtils.copyProperties(companyService.get(id),company);
		company.setBusLine(FilePathUtils.setEditorOutPath(company.getBusLine()));
		company.setIntroduce(FilePathUtils.setEditorOutPath(company.getIntroduce()));
		return EDIT;
	}
	
	public String list(){
		companyService.getByAreaId(areaId,pagination,null);
		return LIST;
	}
	
	public void delete() {
		List<Integer> idList = ApplicationUtils.getAjaxIds();
		if (CollectionUtils.isEmpty(idList))
			return;
		companyService.deleteById(idList);
	}
	
	public void status() {
		Integer companyId=ApplicationUtils.getAjaxId();
		Integer status=ApplicationUtils.getAjaxStatus();
		if(companyId==null || status==null) return;
		company=companyService.get(companyId);
		if(company==null) return;
		company.setStatus(status);
		companyService.save(company);
	}
	
	public String tree() {
		String result=companyTree();
		return result==null?"tree":result;
	}

	private String companyTree() {
		if(ApplicationUtils.getSite().getComType().startsWith(ElianUtils.COMP_TYPE_COMP)){
			id=ApplicationUtils.getCompany().getId();
			isEdit=true;
			return edit();
		}
		return null;
	}

	public void validateSaveCompany(){
		if (StringUtils.isBlank(company.getName())) {
			this.addFieldError("company.name", "企业名称不能为空");
		}else if (ValidateUtils.isLengOut(company.getName(), 45)) {
			this.addFieldError("company.name", "企业名称在45字以内");
		}
		
		if (ValidateUtils.isLengOut(company.getShortName(), 30)) {
			this.addFieldError("company.shortName", "企业名称在30字以内");
		}
		
		if (StringUtils.isBlank(company.getAddress())) {
			this.addFieldError("company.address", "地址不能为空");
		}else if (ValidateUtils.isLengOut(company.getAddress(), 250)){
			this.addFieldError("company.address", "地址长度在250字以内");
		}
		
		if(null==company.getArea())
			this.addFieldError("company.area.id", "请选择医院所在省市区");
		
		if(company.getArea().getId().equals(0))
			this.addFieldError("company.area.id", "请选择医院所在省市区");
		
		if (ValidateUtils.isLengOut(company.getLegalRepresentative(), 250))
			this.addFieldError("company.legalRepresentative", "法人代表长度在250字以内");
		
		if (ValidateUtils.isLengOut(company.getRegisteredCapital(), 50))
			this.addFieldError("company.registeredCapital", "注册资金长度在250字以内");
		else if (StringUtils.isNotBlank(company.getRegisteredCapital())&& !ValidateUtils.isDouble(company.getRegisteredCapital()))
			this.addFieldError("company.registeredCapital", "请填写正确的金额");
		
		if(StringUtils.isNotBlank(company.getQq())){
			if(ValidateUtils.isLengOut(company.getQq(), 25))
				this.addFieldError("company.qq", "qq号码长度在25个字以内");
			if (!ValidateUtils.isQQ(company.getQq()))
				this.addFieldError("company.qq", "请填写正确的qq号码");
		}
		
		if(StringUtils.isNotBlank(company.getContactName())){
			if(ValidateUtils.isLengOut(company.getContactName(), 25))
				this.addFieldError("company.contactName", "联系人名称长度在25个字以内");
		}
		
		if(StringUtils.isNotBlank(company.getEmpiricaMode())){
			if(ValidateUtils.isLengOut(company.getEmpiricaMode(), 255))
				this.addFieldError("company.empiricaMode", "经营模式长度在255个字以内");
		}
		
		if (!StringUtils.isBlank(company.getPhone())) {
			if (ValidateUtils.isNotPhoneAndMobile(company.getPhone()))
				this.addFieldError("company.phone", "请填写正确的联系电话");
		}
		
		if(StringUtils.isNotBlank(company.getFax())) {
			if(!ValidateUtils.isPhone(company.getFax()))
				this.addFieldError("company.fax", "请填写正确的传真号");
		}
		
		if(StringUtils.isNotBlank(company.getEmail())) {
    		if(!ValidateUtils.isLengOut(company.getEmail(), 50)) {	
    			if(!ValidateUtils.isEmail(company.getEmail(), 6, 50))
    				this.addFieldError("company.email", "请填写正确的邮箱");
    		}
    		else 
    			this.addFieldError("company.email", "邮箱长度在50字以内");
		}
		
		if(StringUtils.isNotBlank(company.getPostcode())) {
			if(!ValidateUtils.isLengOut(company.getPostcode(), 6)) {
    			if(!ValidateUtils.isPostCode(company.getPostcode()))
    				this.addFieldError("company.postcode", "请输入正确的邮政编码");
			}
			else this.addFieldError("company.postcode", "请输入正确的邮政编码");
		}
		
		if(StringUtils.isNotBlank(company.getSiteUrl())){
    		if(!ValidateUtils.isLengOut(company.getSiteUrl(), 255)) {
        		if(!ValidateUtils.isHttpUrl(company.getSiteUrl()))
        			this.addFieldError("company.siteUrl", "请填写正确的网址");
    		}
    		else 
    			this.addFieldError("company.siteUrl", "医院网址在255");
    	}
		
		if(ValidateUtils.isLengOut(company.getLogoImg(), 255))
			this.addFieldError("company.logoImg","LOGO图片长度在255字以内");
		
		if(ValidateUtils.isLengOut(company.getCompanyImg(),255))
			this.addFieldError("company.companyImg","形象图片长度在255字以内");
		
		if(ValidateUtils.isLengOut(company.getBusLine(), 2000))
			this.addFieldError("company.busLine","BUS线路长度在2000字以内");
		
		if(ValidateUtils.isLengOut(company.getMapImg(),255))
			this.addFieldError("company.mapImg", "医院地图长度在255字以内");
		

		if(ValidateUtils.isLengOut(company.getShortIntroduce(),255))
			this.addFieldError("company.shortIntroduce", "简要介绍长度在255字以内");
		
		if(ValidateUtils.isLengOut(company.getBusLine(), 5000))
			this.addFieldError("company.busLine", "乘车路线长度在5000字以内");
		
		if(ValidateUtils.isLengOut(company.getIntroduce(), 5000))
			this.addFieldError("company.introduce", "详细介绍长度在5000字以内");
	}
	
	public void areaCompanyTree() {
		String paramType=paramId.substring(0, paramId.indexOf(":"));
		submitId=Integer.valueOf(paramId.substring(paramId.indexOf(":")+1, paramId.length()));
		if(paramType.equals("area")) {
			putCompanyJson(submitId,compName);
		}
	}
	
	private void putCompanyJson(Integer areaCode,String compName) {
		if(areaCode==null) {
			return;
		}
		List<Map<String,Object>> list=new ArrayList<Map<String,Object>>();
		List<Company> compList=companyService.getByAreaId(areaCode, null,compName);
		if(compList==null) {
			return;
		}
		Map<String, Object> map=null;
		for (Company company : compList) {
			map=new LinkedHashMap<String, Object>();
			map.put("id", company.getId());
			map.put("name",company.getName());
			map.put("type", "comp");
			map.put("open", true);
			list.add(map);
		}
		ApplicationUtils.sendJsonArray(list);
	}
	
	
	
	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getAreaId() {
		return areaId;
	}

	public void setAreaId(Integer areaId) {
		this.areaId = areaId;
	}

	public Pagination<Company> getPagination() {
		return pagination;
	}

	public void setPagination(Pagination<Company> pagination) {
		this.pagination = pagination;
	}
	
	public Company getCompany() {
		return company;
	}

	public void setCompany(Company company) {
		this.company = company;
	}

	public boolean isEdit() {
		return isEdit;
	}

	public void setEdit(boolean isEdit) {
		this.isEdit = isEdit;
	}

	@Resource
	public void setCompanyService(CompanyService companyService) {
		this.companyService = companyService;
	}
	
	@Resource
	public void setAreaService(AreaService areaService) {
		this.areaService = areaService;
	}
	
	@Resource
	public void setSiteFileService(SiteFileService siteFileService) {
		this.siteFileService = siteFileService;
	}

	public List<SelectItem> getStatusList(){
		if (statusList == null) {
			statusList=ElianUtils.getStatusList();
		}
		return statusList;
	}

	public List<SelectItem> getAvailableList() {
		if(availableList==null){
			availableList=ElianUtils.getAvailableList();
		}
		return availableList;
	}

	public String getParamId() {
		return paramId;
	}

	public void setParamId(String paramId) {
		this.paramId = paramId;
	}

	public Integer getSubmitId() {
		return submitId;
	}

	public void setSubmitId(Integer submitId) {
		this.submitId = submitId;
	}

	public String getCompName() {
		return compName;
	}

	public void setCompName(String compName) {
		this.compName = compName;
	}
	
}
