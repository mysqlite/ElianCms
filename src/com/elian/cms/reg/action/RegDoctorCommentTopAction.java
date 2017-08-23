package com.elian.cms.reg.action;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.elian.cms.admin.model.Doctor;
import com.elian.cms.admin.service.DoctorCommentService;
import com.elian.cms.syst.action.BaseAction;
import com.elian.cms.syst.util.ApplicationUtils;
import com.elian.cms.syst.util.HibernateEagerLoadingUtil;

@Component
@Scope("prototype")
public class RegDoctorCommentTopAction extends BaseAction {
	private static final long serialVersionUID = 5744129744348783204L;
	private int size=10;
	private DoctorCommentService doctorCommentService = null;	

	public void listJson() {
		List<Doctor> doctList= doctorCommentService.getCommentTopDoctor(size);
		HibernateEagerLoadingUtil.eagerLoadFiled(doctList);
		ApplicationUtils.sendJsonpList(doctList);
	}

	public int getSize() {
		return size;
	}

	public void setSize(int size) {
		this.size = size<=0?10:size;
	}

	@Resource
	public void setDoctorCommentService(DoctorCommentService doctorCommentService) {
		this.doctorCommentService = doctorCommentService;
	}			
}