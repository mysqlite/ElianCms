package com.elian.cms.admin.data.util;

import java.util.ArrayList;
import java.util.List;

import org.springframework.util.CollectionUtils;

import com.elian.cms.admin.data.Data1;
import com.elian.cms.admin.data.DataContent1;
import com.elian.cms.admin.model.Content;
import com.elian.cms.admin.model.Information;
import com.elian.cms.admin.model.Job;
import com.elian.cms.admin.service.JobService;
import com.elian.cms.syst.util.ApplicationUtils;
import com.elian.cms.syst.util.SpringUtils;
import com.elian.cms.syst.util.SysXmlUtils;

public class JobDataUtil extends BaseDataUtil {
	protected JobService jobService = null;
	private String actionName=null;

	public JobDataUtil() {
		super();
		jobService = (JobService) SpringUtils
				.getEntityService(Job.class);
	}

	@Override
	public Object getListDatas(int contentSize, int imgSize,
			boolean isContentDetial) {
		Data1 data = createData(contentSize, 0, isContentDetial);
		return data;
	}
	
	@Override
	public Data1 getTopHitsDatas(Integer size) {
		if (size == 0)
			return null;
		Data1 data = new Data1();
		List<Content> contentList = contentService.findTopHitsList(channel
				.getId(), site.getId(), size);
		ArrayList<DataContent1> list = getDataContentFromcontList(contentList,
				true,true);
		data.setContentList(list);
		return data;
	}

	private Data1 createData(int contentListSize, int imgListSize,
			boolean isContDetial) {
		Data1 data = new Data1();
		if (channel == null)
			return data;
		List<Content> contentList = getContentList(null, contentListSize, 0);
		if(contentList==null) return null;
		// 获取栏目路径
		String channelPath = null;
		if (!CollectionUtils.isEmpty(contentList)) {
			channelPath = getPath(contentList.get(0).getEntityId(), true);
		}else {
			channelPath = getPath(null, true);
		}

		// 填充data
		data.setPath(channelPath);
		data.setChannelName(channel.getChannelName());
		data.setContentList(getDataContentFromcontList(contentList,
				isContDetial,false));
		return data;
	}

	/*
	 * 获取内容列表 大小由size决定
	 */
	private List<Content> getContentList(List<Information> imgInforList,
			int contentListSize, int imgContentListSize) {
		if (0 == contentListSize || contentListSize == imgContentListSize)
			return null;
		// 从控制表中取出前n条记录
		List<Content> contentList = contentService.findStaticPages(channel
				.getId(), ApplicationUtils.getSite().getId(), contentListSize);
		if (CollectionUtils.isEmpty(contentList))
			return null;
		return contentList;
	}

	/*
	 * 将Content List转换为DataContent1 List
	 */
	private ArrayList<DataContent1> getDataContentFromcontList(
			List<Content> contentList, boolean isDetial,boolean isDynamicChannel) {
		if (CollectionUtils.isEmpty(contentList)) {
			return null;
		}
		ArrayList<DataContent1> dataContentList = new ArrayList<DataContent1>();
		for (Content c : contentList) {
			String path = null;
			if(isDynamicChannel) path=getDynamicPath(c.getEntityId());
			else	path=getPath(c.getEntityId(), false);	
			Job job=null;
			if(isDetial){
				job=jobService.get(c.getEntityId());
			}
			DataContent1 dataContent1=new DataContent1(c.getTitle(),job==null?null:job.getHireNum(), path,null,c.getCreateTime());
			dataContent1.setEntity(job);
			dataContentList.add(dataContent1);
		}
		return dataContentList;
	}
	
	private String getDynamicPath(int entityId){
		if(actionName==null)
			actionName=SysXmlUtils.getContentActionName("contentType", Job.class.getSimpleName()).getValue();
		channel=channelService.findByEntityId(site.getId(), actionName, entityId);
		setChannel(channel);		
		String path=getPath(entityId,false);
		return path;
	}
}
