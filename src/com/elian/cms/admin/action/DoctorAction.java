package com.elian.cms.admin.action;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.beans.BeanUtils;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import com.elian.cms.admin.model.Department;
import com.elian.cms.admin.model.Doctor;
import com.elian.cms.admin.service.DepartmentService;
import com.elian.cms.admin.service.DoctorService;
import com.elian.cms.admin.service.SiteFileService;
import com.elian.cms.syst.action.BaseAction;
import com.elian.cms.syst.model.Pagination;
import com.elian.cms.syst.model.SelectItem;
import com.elian.cms.syst.util.ApplicationUtils;
import com.elian.cms.syst.util.ElianUtils;
import com.elian.cms.syst.util.FilePathUtils;
import com.elian.cms.syst.util.SearchParamUtils;
import com.elian.cms.syst.util.StringUtils;
import com.elian.cms.syst.util.SysXmlUtils;
import com.elian.cms.syst.util.ValidateUtils;

/**
 * 资讯
 * 
 * @author Joe
 * 
 */
@Component
@Scope("prototype")
public class DoctorAction extends BaseAction {
	private static final long serialVersionUID = -5559295908997051318L;
	private Pagination<Doctor> pagination = new Pagination<Doctor>(SearchParamUtils.getDoctorConditionMap());
	private Doctor doctor;
	private Integer id = Integer.valueOf(0);
	private boolean isEdit = false;

	/** 树节点传递过来的是否叶子节点 */
	private boolean isLeaf = false;
	/** 树节点传递过来的栏目ID */
	private Integer channelId ;
	/** 树节点传递过来的action名称 */
	private String action;
	/** 内容状态 */
	private Integer status = Integer.valueOf(1);
	/**是否ztree展示*/
	private boolean ztree;

	// private ChannelService channelService;
	private DoctorService doctorService;
	private DepartmentService departmentService;
	private List<SelectItem> educationList;
	private Integer hospId;
	private Integer departId;
	private List<Department> deptList;
	
	public String trees() {
		if(ApplicationUtils.isHosp()) {
			return "hospDoctorTree";
		}
		return "tree";
	}
	
	
	public String edit() {
		if(hospId==null&&id==null)
		   deptList= departmentService.findByAll(ApplicationUtils.getHospital().getId(), null);
		else if(hospId!=null)
		  deptList= departmentService.findByAll(hospId, null);
		if (isEdit && id > 0) {
			doctor =new Doctor();
			BeanUtils.copyProperties(doctorService.get(id), doctor);
			doctor.setIntroduction(FilePathUtils.setEditorOutPath(doctor.getIntroduction()));

			deptList= departmentService.findByAll(doctor.getDept().getHospital().getId(), null);
		}
		else {
			doctor = createDoctor();
		}
		return EDIT;
	}

	public String show() {
		if (id > 0) {
			doctor =new Doctor();
			BeanUtils.copyProperties(doctorService.get(id), doctor);
			doctor.setIntroduction(FilePathUtils.setEditorOutPath(doctor.getIntroduction()));
		}
		return SHOW;
	}

	public Doctor createDoctor() {
		doctor= new Doctor();
		doctor.setDisable(true);
		doctor.setCreateTime(new Date());
		doctor.setDoctSort(99);
		doctor.setGender(ElianUtils.MALE);
		doctor.setDept(departId!=null?departmentService.get(departId):null);
		return doctor;
	}

	public String list() {
		if(departId!=null) {
			doctorService.findByPage(pagination, null, departId);
		}else {
			Integer hosId=ApplicationUtils.isHosp()?ApplicationUtils.getHospital().getId():(hospId!=null?hospId:null);
			doctorService.findPageByHosp(pagination, hosId);
		}
		return LIST;
	}
	
	public void validateSave() {
		if (ValidateUtils.isBlank(doctor.getDoctName()))
			this.addFieldError("doctor.doctName", "姓名不能为空");
		else if (ValidateUtils.isLengOut(doctor.getDoctName(), 50))
			this.addFieldError("doctor.doctName", "姓名必须在50字以内");
		 if (doctor.getDept().getId() == null)
		 this.addFieldError("doctor.dept.id", "科室不能为空");
		if (ValidateUtils.isLengOut(doctor.getJobTitle(), 50))
			this.addFieldError("doctor.jobTitle", "职称必须在50字以内");
		if (ValidateUtils.isLengOut(doctor.getDutyName(), 255))
			this.addFieldError("doctor.dutyName", "职务必须在255字以内");
		if (ValidateUtils.isLengOut(doctor.getGraduateSchool(), 20))
			this.addFieldError("doctor.graduateSchool", "毕业院校必须在20字以内");
		if (ValidateUtils.isLengOut(doctor.getSpeciality(), 255))
			this.addFieldError("doctor.speciality", "专长必须在255字以内");
		if (!StringUtils.isBlank(doctor.getEmail())) {
			if (!ValidateUtils.isEmail(doctor.getEmail(), 6, 40))
				this.addFieldError("doctor.email", "邮箱格式不正确");
			else if (ValidateUtils.isLengOut(doctor.getEmail(), 40))
				this.addFieldError("doctor.email", "邮箱在6-40字以内");
		}
		if (!StringUtils.isBlank(doctor.getMoblie())) {
			if (!ValidateUtils.isMobile(doctor.getMoblie()))
				this.addFieldError("doctor.moblie", "请输入正确的手机号");
		}
		if (ValidateUtils.isLengOut(doctor.getIntroduction(), 5000))
			this.addFieldError("doctor.introduction", "详细介绍必须在5000字以内");
		
		if(this.hasErrors()) {
			if(hospId==null&&id==null)
				   deptList= departmentService.findByAll(ApplicationUtils.getHospital().getId(), null);
			else if(hospId!=null)
			  deptList= departmentService.findByAll(hospId, null);
		}
	}

	public String save() {
		doctor.setIntroduction(FilePathUtils.getConContext(doctor.getIntroduction()));
		doctor.setDept(departmentService.get(doctor.getDept().getId()));
		if(isPublish())
			doctorService.save(doctor, isEdit,isPublish());
		else
		   doctorService.save(doctor, isEdit);
		siteFileService.saveConContext(doctor,getEditorPrevImg(),doctor.getIntroduction());
		siteFileService.saveFileToFtp(doctor,getPrevFile(),doctor.getDoctImg());
		return SAVE;
	}

	/**
	 * 内容界面delete方法，删除数据的时候只删除内容表的数据
	 */
	public void delete() {
		List<Integer> idList = ApplicationUtils.getAjaxIds();
		if (CollectionUtils.isEmpty(idList))
			return;
		doctorService.delete(null, idList);
	}
	
	public String disable() {
		Integer id = ApplicationUtils.getAjaxId();
		Boolean disable = ApplicationUtils.getAjaxDisable();
		if (id != null && disable != null) {
			doctor = doctorService.get(id);
			if (doctor == null)
				return NONE;
			doctor.setDisable(!disable);
			doctorService.save(doctor);
		}
		return NONE;
	}

	public String sort() {
		Integer id = ApplicationUtils.getAjaxId();
		Integer sort = ApplicationUtils.getAjaxSort();
		if (id != null && sort != null) {
			doctor = doctorService.get(id);
			if (doctor == null)
				return NONE;
			doctor.setDoctSort(sort);
			doctorService.save(doctor);
		}
		return NONE;
	}

	public void check() {
		List<Integer> idList = ApplicationUtils.getAjaxIds();
		if (idList != null)
			for (Integer id : idList) {
				doctor = doctorService.get(id);
				doctor.setDisable(!doctor.isDisable());
				doctorService.save(doctor);
			}
	}
	
	public void deleteDoctor() {
		List<Integer> idList = ApplicationUtils.getAjaxIds();
	      Integer   ids = ApplicationUtils.getAjaxId();
		if(!CollectionUtils.isEmpty(idList)) {
			for (Integer id : idList) {
				doctor=doctorService.get(id);
				doctorService.delete(doctor, null);
			}
		}else if(ids!=null&&ids>0) {
			doctor=doctorService.get(id);
			doctorService.delete(doctor, null);
		}
//		if(!(ApplicationUtils.isMainStation()||ApplicationUtils.isSubStation())) {
//			hospid=ApplicationUtils.getHospital().getId();
//		}
//		List<Department> deptList = doctorService.findByContentId(idList,ApplicationUtils.getSite().getId());
//		if (CollectionUtils.isEmpty(deptList))
//			return;
//		for (Department doctor : deptList) {
//			doctorService.delete(doctor);
//			logService.save(LogParamUtils.OPERATION_DELETE,LogParamUtils.DEPARTMENT_DELETE_CN,LogParamUtils.DEPARTMENT_TITLE, doctor.getTitle());
//		}
	}
	
	public List<Department> getDeptList() {
		return deptList;
	}


	public void setDeptList(List<Department> deptList) {
		this.deptList = deptList;
	}


	public List<SelectItem> getSexList() {
		return ElianUtils.getSexList();
	}
	
	public List<SelectItem> getEducationList() {
		if (educationList == null) {
			educationList = SysXmlUtils.getXMLSelect("education");
		}
		return educationList;
	}

	public Pagination<Doctor> getPagination() {
		return pagination;
	}

	public void setPagination(Pagination<Doctor> pagination) {
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

	public Doctor getDoctor() {
		return doctor;
	}

	public void setDoctor(Doctor doctor) {
		this.doctor = doctor;
	}
	
	public Integer getHospId() {
		return hospId;
	}

	public void setHospId(Integer hospId) {
		this.hospId = hospId;
	}

	public Integer getDepartId() {
		return departId;
	}

	public void setDepartId(Integer departId) {
		this.departId = departId;
	}

	public boolean isZtree() {
		return ztree;
	}


	public void setZtree(boolean ztree) {
		this.ztree = ztree;
	}




	private SiteFileService siteFileService;
	@Resource
	public void setSiteFileService(SiteFileService siteFileService) {
		this.siteFileService = siteFileService;
	}

	@Resource
	public void setDoctorService(DoctorService doctorService) {
		this.doctorService = doctorService;
	}

	@Resource
	public void setDepartmentService(DepartmentService departmentService) {
		this.departmentService = departmentService;
	}

}
