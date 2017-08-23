package com.elian.cms.admin.service;

import java.util.List;

import com.elian.cms.admin.dao.JobDao;
import com.elian.cms.admin.model.Content;
import com.elian.cms.admin.model.Job;
import com.elian.cms.syst.service.Service;

public interface JobService extends Service<JobDao, Job, Integer>,BasecontentService<Job> {

	public void save(Job job, boolean isEdit);
	public Integer save(Job job, boolean isEdit,boolean publish);
	
	public void delete(Job job) ;
	
	public List<Job> findByContentId(List<Integer> contentIdList,
			Integer siteId);

	public List<Job> findFormContentList(List<Content> contentList,Integer siteId);
}
	
	
