package com.elian.cms.admin.service;

import java.util.List;

import com.elian.cms.admin.dao.ResumeDao;
import com.elian.cms.admin.model.Resume;
import com.elian.cms.syst.model.Pagination;
import com.elian.cms.syst.service.Service;

public interface ResumeService extends Service<ResumeDao, Resume, Integer> {
	public List<Resume> findByAll(Pagination<Resume> pagination);
}
