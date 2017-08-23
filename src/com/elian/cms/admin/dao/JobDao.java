package com.elian.cms.admin.dao;

import java.util.List;

import com.elian.cms.admin.model.Job;
import com.elian.cms.syst.dao.Dao;

public interface JobDao extends Dao<Job, Integer> {

	public List<Job> findByContentId(List<Integer> contentIdList,
			Integer siteId);
	public List<Job> getByContentId(List<Integer> contentIdList);
	
}
