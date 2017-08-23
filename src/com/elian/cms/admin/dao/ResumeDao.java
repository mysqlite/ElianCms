package com.elian.cms.admin.dao;

import java.util.List;

import com.elian.cms.admin.model.Resume;
import com.elian.cms.syst.dao.Dao;
import com.elian.cms.syst.model.Pagination;

public interface ResumeDao extends Dao<Resume, Integer> {
	public List<Resume> findByAll(Pagination<Resume> pagination);
}
