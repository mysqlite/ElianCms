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

import com.elian.cms.admin.model.Department;
import com.elian.cms.admin.model.DeptType;
import com.elian.cms.admin.model.Type;
import com.elian.cms.admin.service.DepartmentService;
import com.elian.cms.admin.service.DeptTypeService;
import com.elian.cms.admin.service.HospitalService;
import com.elian.cms.admin.service.SiteFileService;
import com.elian.cms.admin.service.TypeService;
import com.elian.cms.syst.action.BaseAction;
import com.elian.cms.syst.model.Pagination;
import com.elian.cms.syst.util.ApplicationUtils;
import com.elian.cms.syst.util.ElianUtils;
import com.elian.cms.syst.util.FilePathUtils;
import com.elian.cms.syst.util.SearchParamUtils;
import com.elian.cms.syst.util.StringUtils;
import com.elian.cms.syst.util.ValidateUtils;

/**
 * 资讯
 * 
 * @author Joe
 * 
 */
@Component
@Scope("prototype")
public class DepartmentAction extends BaseAction {
	private static final long serialVersionUID = -8655600358253892296L;
	private Pagination<Department> pagination = new Pagination<Department>(SearchParamUtils.getDepartConditionMap());
	private Department department;
	private Integer id = Integer.valueOf(0);
	private boolean isEdit = false;

	/** 科室类型名称 */
	private String deptTypeName;
	/** 树节点传递过来的是否叶子节点 */
	private boolean isLeaf = false;
	/** 树节点传递过来的栏目ID */
	private Integer channelId;
	/** 树节点传递过来的action名称 */
	private String action;
	/** 内容状态 */
	private Integer status = Integer.valueOf(1);
	/**是否ztree展示*/
	private boolean ztree;

	private DepartmentService departmentService;
	private TypeService typeService;
	private DeptTypeService deptTypeService;
	private SiteFileService siteFileService;
	private HospitalService hospitalService;
	
	private List<Type> natureLists ;
	private Integer hospId;
	
	
	
	public String trees() {
		if(ApplicationUtils.isHosp()) {
			return "treeList";
		}
		return "tree";
	}
	
	
	public String edit() {
		if (isEdit && id > 0) {
			department = new  Department();
			BeanUtils.copyProperties(departmentService.get(id), department);
			department.setDescription(FilePathUtils.setEditorOutPath(department.getDescription()));
		}
		else {
			department = createDepartment();
		}
		return EDIT;
	}

	public String show() {
		if (id > 0) {
			department = new  Department();
			BeanUtils.copyProperties(departmentService.get(id), department);
			department.setDescription(FilePathUtils.setEditorOutPath(department.getDescription()));
		}
		return SHOW;
	}
	
	public String list() {
		getDeptNature();
		if(hospId!=null&&hospId>0) {
			departmentService.findPageByHosp(pagination,hospId);
		}else {
    		Integer hosId=ApplicationUtils.getHospital()!=null?ApplicationUtils.getHospital().getId():null;
    		departmentService.findPageByHosp(pagination,hosId);
		}
		return LIST;
	}

	public void getDeptNature(){
		natureLists=typeService.findByType(true, ElianUtils.DEPT_NATURE);
	}
	public Department createDepartment() {
		Department info = new Department();
		info.setDisable(true);
		info.setCreateTime(new Date());
		info.setDeptSort(99);
		return info;
	}

	public void validateSave() {
		if (department.getTypeId() == null)
			this.addFieldError("department.typeId", "科室类型不能为空");
		if (ValidateUtils.isBlank(department.getDeptName()))
			this.addFieldError("department.deptName", "科室全称不能为空");
		else if (ValidateUtils.isLengOut(department.getDeptName(), 50))
			this.addFieldError("department.deptName", "科室全称必须在50字以内");
		if (!StringUtils.isBlank(department.getEmail())) {
			if (!ValidateUtils.isEmail(department.getEmail(), 6, 40))
				this.addFieldError("department.email", "邮箱格式不正确");
			else if (ValidateUtils.isLengOut(department.getEmail(), 40))
				this.addFieldError("department.email", "邮箱在6-40字以内");
		}
		if (ValidateUtils.isLengOut(department.getAddress(), 255))
			this.addFieldError("department.address", "地址必须在255字以内");
		if (ValidateUtils.isLengOut(department.getShortDesc(), 255))
			this.addFieldError("department.shortDesc", "简要介绍必须在255字以内");
		if (ValidateUtils.isLengOut(department.getDescription(), 5000))
			this.addFieldError("department.description", "简要介绍必须在5000字以内");
	}

	public String save() {
		department.setHospital(ApplicationUtils.isHosp()?ApplicationUtils.getHospital(): hospitalService.get(hospId));
		department.setDescription(FilePathUtils.getConContext(department.getDescription()));
		if(isPublish())
			departmentService.save(department, isEdit,isPublish());
		else	
		   departmentService.save(department, isEdit);
		siteFileService.saveFileToFtp(department, getPrevFile(), department.getDeptImg());
		siteFileService.saveConContext(department,getEditorPrevImg(),department.getDescription());
		return SAVE;
	}
	public List<Type> getNatureList() {
		return typeService.findByType(true, ElianUtils.DEPT_NATURE);
	}

	/*
	 * 获取可用的科室类型结构
	 */
	public void tree() {
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		List<DeptType> dtList = deptTypeService.findByAll(ElianUtils.DEPT_TYPE,
				true, null);
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
	
	
	
	public String disable() {
		Integer id = ApplicationUtils.getAjaxId();
		Boolean disable = ApplicationUtils.getAjaxDisable();
		if (id != null && disable != null) {
			department = departmentService.get(id);
			if (department == null)
				return NONE;
			department.setDisable(!disable);
			departmentService.save(department);
		}
		return NONE;
	}

	public String sort() {
		Integer id = ApplicationUtils.getAjaxId();
		Integer sort = ApplicationUtils.getAjaxSort();
		if (id != null && sort != null) {
			department = departmentService.get(id);
			if (department == null)
				return NONE;
			department.setDeptSort(sort);
			departmentService.save(department);
		}
		return NONE;
	}

	public void check() {
		List<Integer> idList = ApplicationUtils.getAjaxIds();
		if (idList != null)
			for (Integer id : idList) {
				department = departmentService.get(id);
				department.setDisable(!department.isDisable());
				departmentService.save(department);
			}
	}
	
	/**
	 * 内容界面delete方法，删除数据的时候只删除内容表的数据
	 */
	public void delete() {
		List<Integer> idList = ApplicationUtils.getAjaxIds();
		if (CollectionUtils.isEmpty(idList))
			return;
		departmentService.delete(null, idList);
	}
	
	public void deleteDepat() {
		List<Integer> idList = ApplicationUtils.getAjaxIds();
		Integer ids = ApplicationUtils.getAjaxId();
		if (!CollectionUtils.isEmpty(idList)) {
			for (Integer id : idList) {
				department = departmentService.get(id);
				departmentService.delete(department, null);
			}
		}
		else if (ids != null && ids > 0) {
			department = departmentService.get(id);
			departmentService.delete(department, null);
		}
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

	public Department getDepartment() {
		return department;
	}

	public void setDepartment(Department department) {
		this.department = department;
	}

	public String getDeptTypeName() {
		if (StringUtils.isBlank(deptTypeName) && department != null
				&& department.getTypeId() > 0) {
			DeptType dt = deptTypeService.get(department.getTypeId());
			if (dt != null)
				deptTypeName = dt.getTypeName();
		}
		return deptTypeName;
	}

	public void setDeptTypeName(String deptTypeName) {
		this.deptTypeName = deptTypeName;
	}

	public List<Type> getNatureLists() {
		return natureLists;
	}

	public void setNatureLists(List<Type> natureLists) {
		this.natureLists = natureLists;
	}

	public Pagination<Department> getPagination() {
		return pagination;
	}

	public void setPagination(Pagination<Department> pagination) {
		this.pagination = pagination;
	}

	public Integer getHospId() {
		return hospId;
	}


	public void setHospId(Integer hospId) {
		this.hospId = hospId;
	}

	public boolean isZtree() {
		return ztree;
	}


	public void setZtree(boolean ztree) {
		this.ztree = ztree;
	}
	
	

	@Resource
	public void setHospitalService(HospitalService hospitalService) {
		this.hospitalService = hospitalService;
	}

	@Resource
	public void setSiteFileService(SiteFileService siteFileService) {
		this.siteFileService = siteFileService;
	}

	@Resource
	public void setDepartmentService(DepartmentService departmentService) {
		this.departmentService = departmentService;
	}
	
	@Resource
	public void setTypeService(TypeService typeService) {
		this.typeService = typeService;
	}

	@Resource
	public void setDeptTypeService(DeptTypeService deptTypeService) {
		this.deptTypeService = deptTypeService;
	}
}
