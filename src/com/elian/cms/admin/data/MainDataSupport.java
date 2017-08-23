package com.elian.cms.admin.data;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.springframework.util.CollectionUtils;

import com.elian.cms.admin.model.Channel;
import com.elian.cms.admin.model.Content;
import com.elian.cms.admin.model.Information;
import com.elian.cms.admin.service.InformationService;
import com.elian.cms.syst.model.BaseStaticPageData;
import com.elian.cms.syst.util.ApplicationUtils;
import com.elian.cms.syst.util.SpringUtils;

public abstract class MainDataSupport extends BaseStaticPageData {
	protected InformationService infoService=null;	
	
	public MainDataSupport(){
		infoService = (InformationService) SpringUtils.getEntityService(Information.class);
	}
	
	/**
	 * @param channelId 栏目的id
	 * @param contentListSize 内容列表的大小
	 * @param imgListSize  图片列表的大小
	 * @param isContDetial  是否获取内容详细信息
	 * @param isImgDetial  是否获取图片详细信息
	 * @return
	 */
	protected Data1 getDatas(int channelId, int contentListSize, int imgListSize
			, boolean isContDetial,boolean isImgDetial) {
		initParams(channelId);
		Data1 data = createData(contentListSize,imgListSize,isContDetial,isImgDetial);
		return data;
	}
	
	protected Data1 createData(int contentListSize,int imgListSize
			,boolean isContDetial,boolean isImgDetial) {
		Data1 data = new Data1();
		if (channel == null)
			return data;		
		//获取内容列表和图片列表的值
		List<Information> imgContentList=getImgContentList(imgListSize);
		List<Content> contentList=getContentList(imgContentList,contentListSize,imgListSize);		
		
		data.setPath(getListPath(channel));
		
		//填充data
		data.setChannelName(channel.getChannelName());
		data.setImgContentList(getDataContentFromInforList(imgContentList,isImgDetial));
		data.setContentList(getDataContentFromcontList(contentList,isContDetial));
		return data;
	}

	/*
	 * 获取内容列表 大小由size决定
	 */
	protected List<Content> getContentList(List<Information> imgInforList, int contentListSize,int imgContentListSize) {
		if(0==contentListSize) return null;
		
		// 从控制表中取出前10条记录（可能不包含有图片的）
		List<Content> contentList = contentService.findStaticPages(channel
				.getId(), ApplicationUtils
				.getSite().getId(), contentListSize);

		// 从内容列表中去掉在图片列表中的数据
		if (!CollectionUtils.isEmpty(imgInforList)&&!CollectionUtils.isEmpty(contentList)) {
			for (Information info : imgInforList) {
				Iterator<Content> itor = contentList.iterator();
				while (itor.hasNext()) {
					Content cont =itor.next();
					if (cont.getEntityId().equals(info.getId())) {
						imgContentListSize--;
						itor.remove();
					}
				}
			}
			if (imgContentListSize>0) {
				for (int i = 0; i < imgContentListSize; i++) {
					contentList.remove(contentList.size()-1);
				}
			}			
		}
		if (CollectionUtils.isEmpty(contentList))
			return null;
		return contentList;
	}
	/*
	 * 将Content List转换为DataContent1 List
	 */
	protected ArrayList<DataContent1> getDataContentFromcontList(
			List<Content> contentList,boolean isDetial) {		
		if (CollectionUtils.isEmpty(contentList)) {
			return null;
		}			
		ArrayList<DataContent1> dataContentList = new ArrayList<DataContent1>();
		for (Content c : contentList) {		
			String path = getContentPath(channel, c.getId().toString());
			String detial=null;
			if(isDetial){
				Information info=infoService.get(c.getEntityId());
				if(null != info)
					detial=info.getDescription();					
			}				
			dataContentList.add(new DataContent1(c.getTitle(),detial, path, "#",c.getCreateTime()));			
		}
		return dataContentList;
	}

	/*
	 * 将information List转换为DataContent1 List
	 */
	protected ArrayList<DataContent1> getDataContentFromInforList(
			List<Information> InforList,boolean isDetial) {
		if (CollectionUtils.isEmpty(InforList)) {
			return null;
		}
		ArrayList<DataContent1> dataContentList = new ArrayList<DataContent1>();			
		for (Information i : InforList) {			
			int contentId=contentService.getChannelIdByEntityId(ApplicationUtils.getSite().getId(),
					i.getId(),channel.getId());
			String path=getContentPath(channel, contentId+"");
			
			dataContentList.add(new DataContent1(i.getTitle(),i.getDescription(),path,
					getImgPath(i.getInfoImg())));
		}
		return dataContentList;
	}

	/*
	 * 获取显示图片的list 大小由size决定
	 */
	protected List<Information> getImgContentList(int size) {	
		if(size==0) return null;
		// 从information表中取出前几条有图片的记录
		List<Information> imgInforList = infoService.findImgList(channel
				.getId(), channel.getSite().getId(), size);
		if (CollectionUtils.isEmpty(imgInforList))
			return null;
		return imgInforList;
	}
	/**
	 * 获取幻灯片显示图片的list 大小由size决定
	 */
	protected Data1 getPowerPointList(Integer channelId, int size) {		
		if(size==0) return null;
		
		Data1 data=new Data1();		
		initParams(channelId);
		// 从information表中取出前几条有图片的记录
		List<Information> imgInforList = infoService.getPowerPointList(ApplicationUtils.getSite(),channel,size);		
		
		if (CollectionUtils.isEmpty(imgInforList))
			return null;	
		
		channel=new Channel();
		List<DataContent1> imgContentList=getDataContentFromInforList(imgInforList,false);		
		data.setImgContentList(imgContentList);
		return data;
	}
}
