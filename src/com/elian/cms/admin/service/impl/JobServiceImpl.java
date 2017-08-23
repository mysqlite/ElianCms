package com.elian.cms.admin.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.elian.cms.admin.dao.JobDao;
import com.elian.cms.admin.model.Content;
import com.elian.cms.admin.model.Job;
import com.elian.cms.admin.service.JobService;
import com.elian.cms.syst.listener.ContentListener;
import com.elian.cms.syst.listener.Impl.ContentListenerImpl;
import com.elian.cms.syst.service.impl.ServiceImpl;

@Component("jobService")
public class JobServiceImpl extends ServiceImpl<JobDao, Job, Integer> implements
		JobService {

	public List<Job> findByContentId(List<Integer> contentIdList,
			Integer siteId){
		
		return dao.findByContentId(contentIdList, siteId);
	}

	@Resource
	public void setDao(JobDao dao) {
		this.dao = dao;
	}

	private ContentListener contentListener;

	public void delete(Job job) {
		ContentListenerImpl.checkSource(job);
		if (job.isSource())
			super.delete(job);
		if (null != contentListener)
			contentListener.afterDelete(job);
	}

	public void save(Job job, boolean isEdit) {
		super.save(job);
		if (null != contentListener)
			if (isEdit)
				contentListener.afterUpdate(job);
			else
				contentListener.afterSave(job);
	}
	
	public Integer save(Job job, boolean isEdit,boolean publish) {
		super.save(job);
		Integer controlId=0;
		if (null != contentListener)
			if (isEdit)
				contentListener.afterUpdate(job,publish);
			else
				controlId=contentListener.afterSave(job,publish);
		return controlId;
	}

	@Resource
	public void setContentListener(ContentListener contentListener) {
		this.contentListener = contentListener;
	}

	public List<Job> findFormContentList(List<Content> contentList,Integer siteId) {		
		List<Integer> contentIdList=new ArrayList<Integer>();
		for(Content c :contentList)
			contentIdList.add(c.getEntityId());
		return dao.getByContentId(contentIdList);		
	}

}
