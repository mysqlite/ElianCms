package com.elian.cms.reg.action;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import com.elian.cms.admin.model.Department;
import com.elian.cms.admin.model.DeptType;
import com.elian.cms.admin.model.Hospital;
import com.elian.cms.admin.model.Type;
import com.elian.cms.admin.service.DepartmentService;
import com.elian.cms.admin.service.DeptTypeService;
import com.elian.cms.admin.service.DoctorService;
import com.elian.cms.admin.service.HospitalService;
import com.elian.cms.admin.service.TypeService;
import com.elian.cms.syst.action.BaseAction;

@Component
@Scope("prototype")
public class RegHospitalAction extends BaseAction{
	private static final long serialVersionUID = -9059495469917566571L;
	
	private Integer hospId=null;
	private Hospital hospital=null;
	
	private HospitalService hospitalService=null;
	private DepartmentService departmentService=null;
	private DoctorService doctorService=null;
	private TypeService typeService=null;
	private DeptTypeService deptTypeService=null;
//	private FtpService ftpService=null;
	
	private Map<DeptType, Map<Department,Integer>> depaMap=new LinkedHashMap<DeptType, Map<Department,Integer>>();
//	private List<Ftp> imgFtp=null;

	public String detial(){
		List<Department> depaList= departmentService.findByAll(hospId, null);
		if(!CollectionUtils.isEmpty(depaList)){
			int docNum=0;
			for(Department d:depaList){
				DeptType deptType=null;
				docNum=doctorService.findByPage(null, hospId, d.getId()).size();
				if(docNum==0) continue;
				for(DeptType t:depaMap.keySet()){
					if(t.getId().equals(d.getTypeId())){
						deptType=t;
						depaMap.get(t).put(d, docNum);
						break;
					}
				}
				if(deptType==null){					
					deptType=deptTypeService.get(d.getTypeId());
					Map<Department,Integer> map=new LinkedHashMap<Department, Integer>();
					map.put(d, docNum);
					depaMap.put(deptType,map);
				}
			}
		}
		return LIST;
	}
	
	public Hospital getHospital(){
		if(hospital!=null) return hospital;
		if(hospId!=null)	
			return hospitalService.get(hospId);
		return null;
	}
	
	public Type getHospRank(){
		if(hospital!=null)
			return typeService.get(hospital.getRank());
		if(hospId!=null)	
			return typeService.get(hospitalService.get(hospId).getRank());
		return null;
	}

	public Type getHospType(){
		if(hospital!=null)
			return typeService.get(hospital.getHospType());
		if(hospId!=null)	
			return typeService.get(hospitalService.get(hospId).getHospType());
		return null;
	}

	public Integer getHospId() {
		return hospId;
	}

	public void setHospId(Integer hospId) {
		this.hospId = hospId;
	}
	
	public Map<DeptType, Map<Department, Integer>> getDepaMap() {
		return depaMap;
	}

	public void setDepaMap(Map<DeptType, Map<Department, Integer>> depaMap) {
		this.depaMap = depaMap;
	}

	public void setHospital(Hospital hospital) {
		this.hospital = hospital;
	}

	@Resource
	public void setHospitalService(HospitalService hospitalService) {
		this.hospitalService = hospitalService;
	}

	@Resource
	public void setDepartmentService(DepartmentService departmentService) {
		this.departmentService = departmentService;
	}

	@Resource
	public void setDoctorService(DoctorService doctorService) {
		this.doctorService = doctorService;
	}

	@Resource
	public void setTypeService(TypeService typeService) {
		this.typeService = typeService;
	}
	
	@Resource
	public void setDeptTypeService(DeptTypeService deptTypeService) {
		this.deptTypeService = deptTypeService;
	}

//	@Resource
//	public void setFtpService(FtpService ftpService) {
//		this.ftpService = ftpService;
//	}
//		
}
