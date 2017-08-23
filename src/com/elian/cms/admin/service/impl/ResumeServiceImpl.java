package com.elian.cms.admin.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.elian.cms.admin.dao.ResumeDao;
import com.elian.cms.admin.model.Resume;
import com.elian.cms.admin.service.ResumeService;
import com.elian.cms.syst.model.Pagination;
import com.elian.cms.syst.service.impl.ServiceImpl;
@Component("resumeService")
public class ResumeServiceImpl extends ServiceImpl<ResumeDao, Resume, Integer>
		implements ResumeService {
	public List<Resume> findByAll(Pagination<Resume> pagination) {
		return dao.findByAll(pagination);
	}

	@Resource
	public void setDao(ResumeDao dao) {
		this.dao = dao;
	}
}
