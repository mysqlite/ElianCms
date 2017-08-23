package com.elian.cms.admin.action;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import com.elian.cms.admin.model.DoctorWork;
import com.elian.cms.admin.service.DoctorService;
import com.elian.cms.admin.service.DoctorWorkService;
import com.elian.cms.admin.service.UserService;
import com.elian.cms.syst.action.BaseAction;
import com.elian.cms.syst.model.Pagination;
import com.elian.cms.syst.model.SelectItem;
import com.elian.cms.syst.model.TrueFalseItem;
import com.elian.cms.syst.util.ApplicationUtils;
import com.elian.cms.syst.util.ElianUtils;

/**
 * 医生排班设置功能
 * 
 * @author Joe
 * 
 */
@Component
@Scope("prototype")
public class DoctorWorkAction extends BaseAction {
	private static final long serialVersionUID = 5201972709507622756L;

	private Pagination<DoctorWork> pagination = new Pagination<DoctorWork>();

	private DoctorWork doctorWork;
	private Integer doctorId = Integer.valueOf(0);
	private Integer id = Integer.valueOf(0);

	private boolean isEdit = false;
	private boolean isGenerate = false;

	private DoctorWorkService doctorWorkService;
	private DoctorService doctorService;
	private UserService userService;

	public String list() {
		doctorWorkService.findByAll(doctorId, pagination);
		return LIST;
	}

	public String edit() {
		doctorWork = doctorWorkService.get(id);
		return EDIT;
	}

	public String save() {
		if (doctorId != null && doctorId > 0) {
			doctorWork.setDoctor(doctorService.get(doctorId));
		}
		if (doctorWork.getCreater().getId() != null) {
			doctorWork.setCreater(userService.get(doctorWork.getCreater()
					.getId()));
		}
		else
			doctorWork.setCreater(null);
		doctorWorkService.save(doctorWork);
		return SAVE;
	}

	public void stopWork() {
		Integer id = ApplicationUtils.getAjaxId();
		Boolean disable = ApplicationUtils.getAjaxDisable();
		if (id != null && disable != null) {
			doctorWork = doctorWorkService.get(id);
			if (doctorWork == null)
				return;
			doctorWork.setStopWork(!disable);
			doctorWorkService.save(doctorWork);
		}
	}
	
	public void stopWorks() {
		List<Integer> idList = ApplicationUtils.getAjaxIds();
		if (CollectionUtils.isEmpty(idList))
			return;
		for (Integer id : idList) {
			doctorWork = doctorWorkService.get(id);
			if (doctorWork == null)
				return;
			doctorWork.setStopWork(!doctorWork.isStopWork());
			doctorWorkService.save(doctorWork);
		}
	}

	public List<SelectItem> getRankList() {
		return ElianUtils.getRankList();
	}

	public List<SelectItem> getRegTypeList() {
		return ElianUtils.getRegTypeList();
	}

	public TrueFalseItem getDisableItem() {
		return ElianUtils.getTrueFalseItem();
	}

	public Pagination<DoctorWork> getPagination() {
		return pagination;
	}

	public DoctorWork getDoctorWork() {
		return doctorWork;
	}

	public void setDoctorWork(DoctorWork doctorWork) {
		this.doctorWork = doctorWork;
	}

	public Integer getDoctorId() {
		return doctorId;
	}

	public void setDoctorId(Integer doctorId) {
		this.doctorId = doctorId;
	}

	public String getDoctorName() {
		if (doctorId == null || doctorId < 0)
			return "";
		else
			return doctorService.get(doctorId).getDoctName();
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

	public boolean isGenerate() {
		return isGenerate;
	}

	public void setGenerate(boolean isGenerate) {
		this.isGenerate = isGenerate;
	}

	@Resource
	public void setDoctorWorkService(DoctorWorkService doctorWorkService) {
		this.doctorWorkService = doctorWorkService;
	}

	@Resource
	public void setDoctorService(DoctorService doctorService) {
		this.doctorService = doctorService;
	}

	@Resource
	public void setUserService(UserService userService) {
		this.userService = userService;
	}
}
