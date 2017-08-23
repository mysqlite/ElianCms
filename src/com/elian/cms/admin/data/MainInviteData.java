package com.elian.cms.admin.data;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.util.CollectionUtils;

import com.elian.cms.admin.model.Bidding;
import com.elian.cms.admin.model.Content;
import com.elian.cms.admin.model.Information;
import com.elian.cms.admin.model.Invitation;
import com.elian.cms.admin.service.BiddingService;
import com.elian.cms.admin.service.InvitationService;
import com.elian.cms.syst.model.BaseStaticPageData;
import com.elian.cms.syst.util.ApplicationUtils;
import com.elian.cms.syst.util.SpringUtils;
import com.elian.cms.syst.util.SysXmlUtils;

public class MainInviteData extends BaseStaticPageData {
	private InvitationService inviteService;
	private BiddingService biddingService;

	
	public MainInviteData(){
		inviteService=(InvitationService) SpringUtils.getEntityService(Invitation.class);
		biddingService=(BiddingService) SpringUtils.getEntityService(Bidding.class);
	}
	
	@Override
	public Map<String, Object> getAllDataMap() {
		Map<String, Object> dataMap = new HashMap<String, Object>();	
		
		Map<String, Object> mainMap=null;
		
		mainMap=new LinkedHashMap<String, Object>();
		mainMap.put("invite", getDatas(238,7,0,false,false));		//招标
		mainMap.put("bidding",getDatas(239,7,0,false,false));		//中标
		dataMap.put("right1", mainMap);    //政府招标
		
		mainMap=new LinkedHashMap<String, Object>();	
		mainMap.put("invite", getDatas(234,7,0,false,false));		//招标
		mainMap.put("bidding",getDatas(235,7,0,false,false));		//中标		
		dataMap.put("right2", mainMap);    //医院招标
		
		mainMap=new LinkedHashMap<String, Object>();		
		mainMap.put("invite", getDatas(236,7,0,false,false));		//招标
		mainMap.put("bidding",getDatas(237,7,0,false,false));		//中标	
		dataMap.put("right3", mainMap);    //企业招标
		
		mainMap=new LinkedHashMap<String, Object>();	
		mainMap.put("invite", getDatas(240,7,0,false,false));		//招标
		mainMap.put("bidding",getDatas(241,7,0,false,false));		//中标	
		dataMap.put("right4", mainMap);   //科研机构招标
		
		return dataMap;
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
		List<Content> contentList=getContentList(null,contentListSize,imgListSize);			
		data.setPath(getListPath(channel));		
		//填充data
		data.setChannelName(channel.getChannelName());
		data.setContentList(getDataContentFromcontList(contentList,null,isContDetial));
		return data;
	}	
	/*
	 * 获取内容列表 大小由size决定
	 */
	protected List<Content> getContentList(List<Information> imgInforList, int contentListSize,int imgContentListSize) {
		if(0==contentListSize) return null;
		
		// 从控制表中取出前10条记录（可能不包含有图片的）
		List<Content> contentList = contentService.findStaticPages(channel
				.getId(), ApplicationUtils.getSite().getId(), contentListSize);

		// 从内容列表中去掉在图片列表中的数据
		if (!CollectionUtils.isEmpty(imgInforList)) {
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
	 * 将ContentList转换为DataContent1 List
	 */
	protected ArrayList<DataContent1> getDataContentFromcontList(
			List<Content> contentList,String navPath,boolean isDetial) {		
		if (CollectionUtils.isEmpty(contentList)) {
			return null;
		}		
		ArrayList<DataContent1> dataContentList = new ArrayList<DataContent1>();
		for (Content c : contentList) {		
			String path = getContentPath(channel, c.getId().toString());
			String detial=null;					
			dataContentList.add(new DataContent1(c.getTitle(),detial, path, "#"));			
		}
		return dataContentList;
	}
	
	/**
	 * 
	 */
	protected Data1 getInvitList(Integer channelId, int size) {		
		if(size==0) return null;
		
		Data1 data=new Data1();		
		initParams(channelId);
		// 从information表中取出前几条有图片的记录
		List<Invitation> inviList = inviteService.getTopList(channel,size);		
		
		if (CollectionUtils.isEmpty(inviList))
			return null;	
		
//		channel=new Channel();
		List<DataContent1> imgContentList=getDataContentFromInviList(inviList,null,false);		
		data.setContentList(imgContentList);
		return data;
	}
	/**
	 * 
	 */
	protected Data1 getBiddList(Integer channelId, int size) {		
		if(size==0) return null;
		
		Data1 data=new Data1();		
		initParams(channelId);
		// 从information表中取出前几条有图片的记录
		List<Bidding> BiddList = biddingService.getTopList(channel,size);		
		
		if (CollectionUtils.isEmpty(BiddList))
			return null;	
		
		//channel=new Channel();
		List<DataContent1> imgContentList=getDataContentFromBiddList(BiddList,null,false);		
		data.setContentList(imgContentList);
		return data;
	}
	
	/*
	 * 将invi List转换为DataContent1 List
	 */
	protected ArrayList<DataContent1> getDataContentFromInviList(
			List<Invitation> InviList, String navPath,boolean isDetial) {
		if (CollectionUtils.isEmpty(InviList)) {
			return null;
		}
		ArrayList<DataContent1> dataContentList = new ArrayList<DataContent1>();			
		for (Invitation i : InviList) {
			List<Content> contentList = contentService.findByIdAndAction(i
					.getId(), SysXmlUtils.getContentActionName("contentType",
					"invitation").getValue());
			if (CollectionUtils.isEmpty(contentList))
				break;
			String path = getContentPath(contentList.get(0).getChannel(),
					contentList.get(0).getId() + "");

			dataContentList.add(new DataContent1(i.getTitle(), i
					.getDescription(), path, "#"));
		}
		return dataContentList;
	}

	/*
	 * 将bidding List转换为DataContent1 List
	 */
	protected ArrayList<DataContent1> getDataContentFromBiddList(
			List<Bidding> biddList, String navPath,boolean isDetial) {
		if (CollectionUtils.isEmpty(biddList)) {
			return null;
		}
		ArrayList<DataContent1> dataContentList = new ArrayList<DataContent1>();			
		for (Bidding b : biddList) {			
			List<Content> contentList = contentService.findByIdAndAction(b
					.getId(), SysXmlUtils.getContentActionName("contentType",
					"bidding").getValue());
			if (CollectionUtils.isEmpty(contentList))
				break;
			String path = getContentPath(contentList.get(0).getChannel(),
					contentList.get(0).getId() + "");
			
			dataContentList.add(new DataContent1(b.getTitle(),b.getDescription(),path,"#"));
		}
		return dataContentList;
	}
}
