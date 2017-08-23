package com.elian.cms.admin.action;

import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.beans.BeanUtils;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import com.elian.cms.admin.model.DeptType;
import com.elian.cms.admin.model.Instrument;
import com.elian.cms.admin.service.CompanyService;
import com.elian.cms.admin.service.DeptTypeService;
import com.elian.cms.admin.service.InstrumentService;
import com.elian.cms.admin.service.SiteFileService;
import com.elian.cms.syst.action.BaseAction;
import com.elian.cms.syst.model.Pagination;
import com.elian.cms.syst.util.ApplicationUtils;
import com.elian.cms.syst.util.ElianUtils;
import com.elian.cms.syst.util.FilePathUtils;
import com.elian.cms.syst.util.SearchParamUtils;
import com.elian.cms.syst.util.ValidateUtils;

/**
 * 企业器械Action
 * 
 * @author Gechuanyi
 * @version {0.1,2013-04-17} 企业药品基本CURD
 */
@Component
@Scope("prototype")
public class InstrumentAction extends BaseAction {
	private static final long serialVersionUID = 88883138708575720L;
	private Pagination<Instrument> pagination = new Pagination<Instrument>(
			SearchParamUtils.getInstrumentConditionMap());
	private Integer id = Integer.valueOf(0);
	private boolean isEdit = false;
	/** 树节点传递过来的是否叶子节点 */
	private boolean isLeaf = false;
	/** 树节点传递过来的栏目ID */
	private Integer channelId;
	/** 树节点传递过来的action名称 */
	private String action;
	/** 内容状态 */
	private Integer status = Integer.valueOf(1);
	/** 是否ztree展示 */
	private boolean ztree;
	private String[] instrImg;

	private Instrument instrument;
	private InstrumentService instrumentService;
	private DeptTypeService deptTypeService;
	private SiteFileService siteFileService;
	private CompanyService companyService;

	private List<DeptType> typeList;//器械类型
	private List<DeptType> departmentType;//器械科种
	private Integer compId;

	public String trees() {
		if (ApplicationUtils.isCompany()) {
			return list();
		}
		return "tree";
	}

	public String edit() {
		getAllType();
		if (id != null && id > 0) {
			instrument= new Instrument();
			BeanUtils.copyProperties(instrumentService.get(id), instrument);
			instrument.setDescription(FilePathUtils.setEditorOutPath(instrument.getDescription()));
		}
		else {
			createInstrument();
		}
		return EDIT;
	}

	public String show() {
		if (id > 0) {
			instrument= new Instrument();
			BeanUtils.copyProperties(instrumentService.get(id), instrument);
			instrument.setDescription(FilePathUtils.setEditorOutPath(instrument.getDescription()));
			typeList = deptTypeService.findByAll(action, isEdit, null);
		}
		return SHOW;
	}

	/*
	 * 获取可用的科室类型结构
	 */
	public void tree() {
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		List<DeptType> dtList = deptTypeService.findByAll(ElianUtils.INSTRUMENT_TYPE,true, null);
		if (dtList != null && dtList.size() > 0) {
			Map<String, Object> map = null;
			for (DeptType t : dtList) {
				map = new LinkedHashMap<String, Object>();
				map.put("id", t.getId());
				map.put("name", t.getTypeName());
				map.put("pId", t.getParentId());
				list.add(map);
			}
		}
		ApplicationUtils.sendJsonArray(list);
	}
	
	public void department() {
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		List<DeptType> dtList = deptTypeService.findByAll(ElianUtils.DEPT_TYPE,true, null);
		if (dtList != null && dtList.size() > 0) {
			Map<String, Object> map = null;
			for (DeptType t : dtList) {
				map = new LinkedHashMap<String, Object>();
				map.put("id", t.getId());
				map.put("name", t.getTypeName());
				map.put("pId", t.getParentId());
				list.add(map);
			}
		}
		ApplicationUtils.sendJsonArray(list);
	}
	
	private void getAllType() {
		typeList=deptTypeService.findByAll(action, isEdit, null);
		departmentType=deptTypeService.findByAll(action, isEdit, null);
	}
	private Instrument createInstrument() {
		instrument = new Instrument();
		instrument.setSort(99);
		instrument.setDiscountedPrice(false);
		instrument.setDisable(false);
		instrument.setCreateTime(new Date());
		instrument.setUser(ApplicationUtils.getUser());
		instrument.setCompany(ApplicationUtils.getCompany() != null ? ApplicationUtils.getCompany(): companyService.get(compId));
		instrument.setPricePany(0);
		instrument.setDiscountedPricePany(0);
		return instrument;
	}

	public String list() {
		instrumentService.findByAll(
				ApplicationUtils.getCompany() != null ? ApplicationUtils
						.getCompany().getId() : compId, pagination);
		return LIST;
	}

	public void validateSave() {
		if (!ValidateUtils.strLenthAll(instrument.getCnName(), 1, 100)) {
			this.addFieldError("cnName", "中文名称长度为1-100字");
		}
		if (ValidateUtils.isLengOut(instrument.getEnName(), 100)) {
			this.addFieldError("enName", "英文名称长度为100字以内");
		}
		if (!ValidateUtils.strLenthAll(instrument.getApprovalNumber(), 21, 24)) {
			this.addFieldError("approvalNumber", "批准文号不符合规范");
		}
		if(ValidateUtils.isLengOut(instrument.getSpecifications(),255)) {
			this.addFieldError("specifications", "规格内容长度在255字以内");
		}
		if(ValidateUtils.isLengOut(instrument.getUsage(),255)) {
			this.addFieldError("usage", "用法内容长度在255字以内");
		}
		if(ValidateUtils.isLengOut(instrument.getInstrUse(),255)) {
			this.addFieldError("instrUse", "用途内容长度在255字以内");
		}
		//到期时间、批准时间
		if(ValidateUtils.isLengOut(instrument.getPackaging(),150)) {
			this.addFieldError("packaging", "包装内容长度在150字以内");
		}
		if (ValidateUtils.isLengOut(instrument.getInstrImg(), 255)) {
			this.addFieldError("instrumentImg", "图片路径在255字以内");
		}
		if (!ValidateUtils.isINTEGER_NEGATIVE(instrument.getPrice())) {
			this.addFieldError("pricePany", "请输入有效正数");
		}
		if (instrument.isDiscountedPrice()
				|| instrument.getDiscountedPrices() != null) {
			if (!ValidateUtils.isINTEGER_NEGATIVE(instrument
					.getDiscountedPrices())) {
				this.addFieldError("discountedPricePany", "请输入有效正数");
			}
		}
		if (!ValidateUtils.isINTEGER_NEGATIVE(instrument.getSort())) {
			this.addFieldError("sort", "请输入正整数");
		}
		if (instrument.getType() == null || instrument.getType().getId() == null) {
			this.addFieldError("type", "请选择器械类型");
		}
		if (instrument.getDepartmentType() == null || instrument.getDepartmentType().getId() == null) {
			this.addFieldError("departmentType", "请选择所属科种");
		}
		if (ValidateUtils.isLengOut(instrument.getSummary(), 255)) {
			this.addFieldError("summary", "摘要内容在255字以内");
		}
		if (ValidateUtils.isLengOut(instrument.getDescription(), 7800)) {
			this.addFieldError("discription", "描述内容在7800字以内");
		}
		if (this.hasErrors()) {
			getAllType();
			instrument.setUser(ApplicationUtils.getUser());
		}

	}

	public String save() {
		instrument.setUser(ApplicationUtils.getUser());
		instrument.setType(deptTypeService.get(instrument.getType().getId()));
		instrument.setDepartmentType(deptTypeService.get(instrument.getDepartmentType().getId()));
		instrument.setDescription(FilePathUtils.getConContext(instrument.getDescription()));
		if (compId != null) {
			instrument.setCompany(companyService.get(compId));
		}
		else if (instrument.getCompany().getId() != null) {
			instrument.setCompany(companyService.get(instrument.getCompany().getId()));
		}
		if(isPublish()) {
			instrumentService.save(instrument,isEdit,isPublish());
		}
		else {
			instrumentService.save(instrument,isEdit);
		}
		siteFileService.saveConContext(instrument,getEditorPrevImg(), instrument.getDescription());
		siteFileService.saveFileToFtp(instrument,getPrevFile(), instrument.getInstrImg());
		return SAVE;
	}

	/**
	 * 内容界面delete方法，删除数据的时候只删除内容表的数据
	 */
	public void delete() {
		List<Integer> idList = ApplicationUtils.getAjaxIds();
		if (CollectionUtils.isEmpty(idList))
			return;
		instrumentService.delete(null, idList);
	}

	public String disable() {
		Integer id = ApplicationUtils.getAjaxId();
		Boolean disable = ApplicationUtils.getAjaxDisable();
		if (id != null && disable != null) {
			instrument = instrumentService.get(id);
			if (instrument == null)
				return NONE;
			instrument.setDisable(!disable);
			instrumentService.save(instrument);
		}
		return NONE;
	}

	public String sort() {
		Integer id = ApplicationUtils.getAjaxId();
		Integer sort = ApplicationUtils.getAjaxSort();
		if (id != null && sort != null) {
			instrument = instrumentService.get(id);
			if (instrument == null)
				return NONE;
			instrument.setSort(sort);
			instrumentService.save(instrument);
		}
		return NONE;
	}

	public void check() {
		List<Integer> idList = ApplicationUtils.getAjaxIds();
		if (idList != null)
			for (Integer id : idList) {
				instrument = instrumentService.get(id);
				instrument.setDisable(!instrument.isDisable());
				instrumentService.save(instrument);
			}
	}

	public void deleteInstrument() {
		List<Integer> idList = ApplicationUtils.getAjaxIds();
		Integer ids = ApplicationUtils.getAjaxId();
		if (!CollectionUtils.isEmpty(idList)) {
			for (Integer id : idList) {
				instrument = instrumentService.get(id);
				instrumentService.delete(instrument, null);
			}
		}
		else if (ids != null && ids > 0) {
			instrument = instrumentService.get(id);
			instrumentService.delete(instrument, null);
		}
	}

	@Resource
	public void setSiteFileService(SiteFileService siteFileService) {
		this.siteFileService = siteFileService;
	}

	@Resource
	public void setInstrumentService(InstrumentService instrumentService) {
		this.instrumentService = instrumentService;
	}

	@Resource
	public void setDeptTypeService(DeptTypeService deptTypeService) {
		this.deptTypeService = deptTypeService;
	}

	@Resource
	public void setCompanyService(CompanyService companyService) {
		this.companyService = companyService;
	}

	public Pagination<Instrument> getPagination() {
		return pagination;
	}

	public void setPagination(Pagination<Instrument> pagination) {
		this.pagination = pagination;
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

	public boolean isLeaf() {
		return isLeaf;
	}

	public void setLeaf(boolean isLeaf) {
		this.isLeaf = isLeaf;
	}

	public Integer getChannelId() {
		return channelId;
	}

	public void setChannelId(Integer channelId) {
		this.channelId = channelId;
	}

	public String getAction() {
		return action;
	}

	public void setAction(String action) {
		this.action = action;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public boolean isZtree() {
		return ztree;
	}

	public void setZtree(boolean ztree) {
		this.ztree = ztree;
	}

	public Instrument getInstrument() {
		return instrument;
	}

	public void setInstrument(Instrument instrument) {
		this.instrument = instrument;
	}

	public Integer getCompId() {
		return compId;
	}

	public void setCompId(Integer compId) {
		this.compId = compId;
	}

	public List<DeptType> getTypeList() {
		return typeList;
	}

	public void setTypeList(List<DeptType> typeList) {
		this.typeList = typeList;
	}

	public List<DeptType> getDepartmentType() {
		return departmentType;
	}

	public void setDepartmentType(List<DeptType> departmentType) {
		this.departmentType = departmentType;
	}

	public String[] getInstrImg() {
		return instrImg;
	}

	public void setInstrImg(String[] instrImg) {
		this.instrImg = instrImg;
	}
}
