package com.elian.cms.admin.dao.impl;

import java.util.List;

import org.springframework.stereotype.Component;

import com.elian.cms.admin.dao.ResumeDao;
import com.elian.cms.admin.model.Resume;
import com.elian.cms.syst.dao.impl.DaoImpl;
import com.elian.cms.syst.model.Pagination;
@Component("resumeDao")
public class ResumeDaoImpl extends DaoImpl<Resume, Integer> implements ResumeDao {
	public List<Resume> findByAll(Pagination<Resume> p) {
		String hql = "from Resume g order by g.ftpSort asc,g.id desc";
		if (p != null) {
			p.setAlias("g");
			return pageByHql(hql, false, p);
		}
		else {
			return findByHql(hql, false);
		}
	}
}
