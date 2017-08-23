package com.elian.cms.admin.data;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.util.CollectionUtils;

import com.elian.cms.admin.model.Content;
import com.elian.cms.admin.model.Job;
import com.elian.cms.admin.service.JobService;
import com.elian.cms.syst.model.BaseStaticPageData;
import com.elian.cms.syst.util.ApplicationUtils;
import com.elian.cms.syst.util.SpringUtils;

public class MainJobData extends BaseStaticPageData {
	private JobService jobService=null;
	
	public MainJobData(){	
		jobService=(JobService) SpringUtils.getEntityService(Job.class);
	}
	
	@Override
	public Map<String, Object> getAllDataMap() {
		Map<String, Object> dataMap = new HashMap<String, Object>();	
		
		dataMap.put("1", getTopHitsDatas(244,ApplicationUtils.getSite().getId(),20));	//点击排行榜		
		
		dataMap.put("2", getDatas(243, 20, 0, false, false,false));	//医院招聘
		
		dataMap.put("3", getDatas(244, 15, 0, true, false,false));	//企业招聘		
		
		return dataMap;
	}
	

	/**
	 * @param channelId 栏目的id
	 * @param contentListSize 内容列表的大小
	 * @param imgListSize  图片列表的大小
	 * @param isContDetial  是否获取内容详细信息
	 * @param isImgDetial  是否获取图片详细信息
	 * @param isGetSitepName  是否获取医院名称    如果获取 该名称存在imgpath中
	 * @return
	 */
	protected Data1 getDatas(int channelId, int contentListSize, int imgListSize
			, boolean isContDetial,boolean isImgDetial,boolean isGetSitepName) {
		initParams(channelId);
		Data1 data = createData(contentListSize,imgListSize,isContDetial,isImgDetial,isGetSitepName);
		return data;
	}	
	
	protected Data1 createData(int contentListSize,int imgListSize
			,boolean isContDetial,boolean isImgDetial,boolean isGetSitepName) {
		Data1 data = new Data1();
		if (channel == null)
			return data;		
		//获取内容列表和图片列表的值
	//	List<Information> imgContentList=getImgList(imgListSize);
		List<Content> contentList=getContentList(contentListSize);		
		
		data.setPath(getListPath(channel));
		
		//填充data
		data.setChannelName(channel.getChannelName());
	//	data.setImgContentList(getDataContentFromInforList(imgContentList,navPath,isImgDetial));
		data.setContentList(getDataContentFromcontList(contentList,isContDetial,isGetSitepName));
		return data;
	}
	
	/*
	 * 获取内容列表 大小由size决定
	 */
	protected List<Content> getContentList(int contentListSize) {
		if(0==contentListSize) return null;
		
		// 从控制表中取出前10条记录（可能不包含有图片的）
		List<Content> contentList = contentService.findStaticPages(channel
				.getId(),ApplicationUtils.getSite().getId(), contentListSize);
		
		if (CollectionUtils.isEmpty(contentList))
			return null;
		return contentList;
	}	
	/*
	 * 将Content List转换为DataContent1 List
	 */
	protected ArrayList<DataContent1> getDataContentFromcontList(List<Content> contentList,boolean isDetial,boolean isGetSitepName) {		
		if (CollectionUtils.isEmpty(contentList)) {
			return null;
		}			
		ArrayList<DataContent1> dataContentList = new ArrayList<DataContent1>();
		for (Content c : contentList) {		
			String path = getContentPath(channel, c.getId().toString());
			String detial=null;
			String siteName=null;
			if(isDetial){
				Job job=jobService.get(c.getEntityId());
				if(null != job)
					detial=job.getHireNum();					
			}
			if(isGetSitepName){
				   siteName=c.getSite().getSiteName();
			}
			dataContentList.add(new DataContent1(c.getTitle(),detial, path, siteName,c.getCreateTime()));			
		}
		return dataContentList;
	}
	
	Data1 getTopHitsDatas(Integer channelId ,Integer siteId,Integer size){
		Data1 data = new Data1();
		initParams(channelId);
		List<Content> contentList=contentService.findTopHitsList(channelId, siteId, size);
		ArrayList<DataContent1>  list=getDataContentFromcontList(contentList,false,false);
		data.setContentList(list);
		return data;
	}
}
