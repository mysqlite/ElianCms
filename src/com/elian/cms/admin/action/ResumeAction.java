package com.elian.cms.admin.action;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.elian.cms.admin.model.Resume;
import com.elian.cms.admin.service.ResumeService;
import com.elian.cms.admin.service.SiteService;
import com.elian.cms.syst.action.BaseAction;
import com.elian.cms.syst.model.Pagination;
import com.elian.cms.syst.util.ApplicationUtils;

/**
 * <b>Title:</b>Resume.java <br>
 * <b>Description:</b>应聘投递简历 数据交互层<br>
 * <b>Copyright:</b>Copyright (c) 2013 <br>
 * <b>Company:</b>广东医联网科技有限公司<br>
 * @author 葛传艺
 * @version 0.1 2013年12月22日14:52:09
 */
@Component
@Scope("prototype")
public class ResumeAction extends BaseAction {
	private static final long serialVersionUID = -7821357955861861416L;

	private Resume resume;
	private Integer id = Integer.valueOf(0);
	private boolean edit = false;
	private Pagination<Resume> pagination = new Pagination<Resume>(Resume.getSearChConditionMap());
	@Autowired
	private ResumeService resumeService;
	@Autowired
	private SiteService siteService;
	public String list() {
		resumeService.findByAll(pagination);
		return LIST;
	}

	public String edit() {
		if (edit && id > 0) {
			resume = resumeService.get(id);
		}
		else {
		    resume = new Resume();
		}
		return EDIT;
	}

	public String show() {
		if (id!=null&&id > 0) {
			resume = resumeService.get(id);
		}
		return SHOW;
	}
	
	public void save() {
		resume.setSite(siteService.get(resume.getSite().getId()));
	    resumeService.save(resume);
	}
	
/*	private void valSave() {
		boolean flag=true;
		
		
	}
*/
	public String delete() {
		List<Integer> idList = ApplicationUtils.getAjaxIds();
		if (idList != null)
			for (Integer id : idList) {
				resume = resumeService.get(id);
				resumeService.delete(resume);
			}
		return NONE;
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

	public Pagination<Resume> getPagination() {
		return pagination;
	}

	public Resume getResume() {
		return resume;
	}

	public void setResume(Resume resume) {
		this.resume = resume;
	}
}
