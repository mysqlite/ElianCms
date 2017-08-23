package com.elian.cms.reg.action;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import net.sf.json.JSONObject;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import com.elian.cms.admin.model.Area;
import com.elian.cms.admin.model.Department;
import com.elian.cms.admin.model.DoctorWork;
import com.elian.cms.admin.model.Hospital;
import com.elian.cms.admin.service.AreaService;
import com.elian.cms.admin.service.DepartmentService;
import com.elian.cms.admin.service.DoctorCommentService;
import com.elian.cms.admin.service.DoctorWorkService;
import com.elian.cms.admin.service.HospitalService;
import com.elian.cms.reg.dto.HospitalNoScore;
import com.elian.cms.reg.dto.WorkTopDeparment;
import com.elian.cms.reg.dto.WorkTopDoctor;
import com.elian.cms.reg.model.DoctorComment;
import com.elian.cms.syst.action.BaseAction;
import com.elian.cms.syst.model.SelectItem;
import com.elian.cms.syst.util.ApplicationUtils;
import com.elian.cms.syst.util.DoubleUtlis;
import com.elian.cms.syst.util.ElianUtils;
import com.elian.cms.syst.util.HibernateEagerLoadingUtil;

@Component
@Scope("prototype")
public class RegIndexAction extends BaseAction {
	private static final long serialVersionUID = 5744129744348783204L;

	private RegSideBarData regSideBarData=null;
	private AreaService areaService=null;
	private HospitalService hospitalService=null;
	private DepartmentService departmentService=null;
	private DoctorCommentService doctorCommentService;
	private DoctorWorkService doctorWorkService;
	
	
	private List<DoctorWork> doctorWorkList=null;
	private List<Area> subAreaList=null;
	private List<Hospital> hospitalList=null;
	private List<HospitalNoScore> noScoreList;
	private List<WorkTopDeparment> workTopDeparments;
	private SimpleDateFormat sf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	   
	private Integer areaId= (Integer) ApplicationUtils.getSession().get("areaId");
	private Integer regHospId=null;//首页快速挂号的可挂号医院
	private Integer subAreaId=null;
	
	@Override
	public String execute() throws Exception {
		hospitalList=hospitalService.findByAreaCode(24,areaId);
		return super.execute();
	}

	public void hospital(){
		List<Hospital> hospitalList=hospitalService.findByAreaCode(24,subAreaId);
		JSONObject obj = new JSONObject();
		obj.put("hosp", hospitalList);
		ApplicationUtils.sendJsonpObj(obj);		
	}
	
	public void regHospital() {
		List<Hospital> hospitalList=hospitalService.findRegHospital(areaId,null);
		JSONObject obj = new JSONObject();
		obj.put("hosp", hospitalList);
		ApplicationUtils.sendJsonpObj(obj);		
	}
	
	public void regDept() {
		List<Department> deptList=departmentService.findPageByHosp(null, regHospId);
		List<SelectItem> list=new ArrayList<SelectItem>();
		for(Department dept:deptList){
			list.add(new SelectItem(dept.getId(), dept.getDeptName()));
		}
		JSONObject obj = new JSONObject();
		obj.put("dept", list);
		ApplicationUtils.sendJsonpObj(obj);		
	}
	
	public Integer getSubAreaId() {
		return subAreaId;
	}

	public void setSubAreaId(Integer subAreaId) {
		this.subAreaId = subAreaId;
	}

	public List<Hospital> getHospitalList() {
		return hospitalList;
	}

	public Integer getRegHospId() {
		return regHospId;
	}

	public void setRegHospId(Integer regHospId) {
		this.regHospId = regHospId;
	}

	@Resource
	public void setRegSideBarData(RegSideBarData regSideBarData) {
		this.regSideBarData = regSideBarData;
	}
	
	@Resource
	public void setAreaService(AreaService areaService) {
		this.areaService = areaService;
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
	public void setDoctorCommentService(DoctorCommentService doctorCommentService) {
		this.doctorCommentService = doctorCommentService;
	}
	
	@Resource
	public void setDoctorWorkService(DoctorWorkService doctorWorkService) {
		this.doctorWorkService = doctorWorkService;
	}

	public List<DoctorWork> getDoctorWorkList() {
		if(doctorWorkList==null){
			doctorWorkList=regSideBarData.getTingZhenList(areaId);
		}
		return doctorWorkList;
	}
	
	public List<Area> getSubAreaList() {
		if(subAreaList==null){
			subAreaList=areaService.findByParentCode(areaId);
		}
		return subAreaList;
	}
	
	public List<SelectItem> getDayList(){
		Calendar calendar = Calendar.getInstance();
		SimpleDateFormat df = new SimpleDateFormat("dd日(EEEE)");
		List<SelectItem> dayList=new ArrayList<SelectItem>();
		for(int i=1;i<=7;i++){
			if(i!=1)
				calendar.add(Calendar.DAY_OF_WEEK, 1);
			 String s = df.format(calendar.getTime()).replace("星期", "周");
			 dayList.add(new SelectItem(i, s));			 
		}
		return dayList;
	}
	
	public List<SelectItem> getRegTypeList() {
		return ElianUtils.getRegTypeList();
	}
	
	public List<HospitalNoScore> getNoScoreList() {
		if(noScoreList==null)
			noScoreList=regSideBarData.getHospNoScores();
		return noScoreList;
	}
	
	private List<WorkTopDeparment> getWorkTopDeparments(){
		if(workTopDeparments==null) {
			workTopDeparments=regSideBarData.getWorkTopDeparments();
			for (int i = 0,len=workTopDeparments.size(); i < len; i++) {
				if(!CollectionUtils.isEmpty(workTopDeparments.get(i).getDoctors())) {
					List<DoctorComment> docCommenList=null;
					double aveScore=0;
    				for (WorkTopDoctor doc : workTopDeparments.get(i).getDoctors()) {
    					docCommenList=doctorCommentService.findByPageOrAll(null, doc.getDoctorId(),null, null);
    					if(!CollectionUtils.isEmpty(docCommenList)) {
    						for (DoctorComment dc : docCommenList) {
    							aveScore=dc.getAveScore()+aveScore;
    						}
    						aveScore=DoubleUtlis.div((double)aveScore,(double)docCommenList.size(),1) ;
    					}
    					doc.setAvgScore(aveScore);
    					doc.setOverNo(doctorWorkService.findDoctorOverScore(doc.getDoctorId(),sf.format(new Date()) ,""));
    				}
				}
			}
		}
		return workTopDeparments;
	}
	
	public void getWorkTopDeparmentsJson() {
		JSONObject obj = new JSONObject();
		getWorkTopDeparments();
		HibernateEagerLoadingUtil.eagerLoadFiled(workTopDeparments);
		obj.put("top", workTopDeparments);
		ApplicationUtils.sendJsonpObj(obj);
	}
	
	
}