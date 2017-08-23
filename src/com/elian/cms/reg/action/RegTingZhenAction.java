package com.elian.cms.reg.action;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.elian.cms.admin.model.DoctorWork;
import com.elian.cms.admin.service.DoctorWorkService;
import com.elian.cms.syst.action.BaseAction;
import com.elian.cms.syst.util.ApplicationUtils;

@Component
@Scope("prototype")
public class RegTingZhenAction extends BaseAction {
	private static final long serialVersionUID = 5744129744348783204L;
	private Integer areaId= (Integer) ApplicationUtils.getSession().get("areaId");

	private RegSideBarData regSideBarData=null;
	private List<DoctorWork> doctorWorkList=null;
	private List<DoctorWork> tingZhenList=null;
	
	private DoctorWorkService doctorWorkService=null; 
	
	public String show(){
		return SHOW;
	}
	
	public List<DoctorWork> getDoctorWorkList() {
		if(doctorWorkList==null){
			doctorWorkList=regSideBarData.getTingZhenList(areaId);
		}
		return doctorWorkList;
	}
	
	public List<DoctorWork> getTingZhenList() {
		if(tingZhenList==null){
			tingZhenList= doctorWorkService.getTingZhenList(50, areaId, null, null);
		}
		return tingZhenList;
	}

	@Resource
	public void setDoctorWorkService(DoctorWorkService doctorWorkService) {
		this.doctorWorkService = doctorWorkService;
	}

	@Resource
	public void setRegSideBarData(RegSideBarData regSideBarData) {
		this.regSideBarData = regSideBarData;
	}
}