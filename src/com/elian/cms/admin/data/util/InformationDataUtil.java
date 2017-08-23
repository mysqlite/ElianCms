package com.elian.cms.admin.data.util;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.util.CollectionUtils;

import com.elian.cms.admin.data.Data1;
import com.elian.cms.admin.data.DataContent1;
import com.elian.cms.admin.model.Content;
import com.elian.cms.admin.model.Information;
import com.elian.cms.admin.service.InformationService;
import com.elian.cms.syst.util.ApplicationUtils;
import com.elian.cms.syst.util.ElianCodes;
import com.elian.cms.syst.util.FilePathUtils;
import com.elian.cms.syst.util.FreemarkerCodes;
import com.elian.cms.syst.util.SpringUtils;
import com.elian.cms.syst.util.SysXmlUtils;

public class InformationDataUtil extends BaseDataUtil{
	protected InformationService infoService=null;
	private String actionName=null;	
	
	public InformationDataUtil() {
		super();
		infoService = (InformationService) SpringUtils.getEntityService(Information.class);
	}	
	
	/**用于获取列表页数据*/
	@Override
	public Object getListDatas(int contentSize, int imgSize,
			boolean isContentDetial) {
		Data1 data = createData(contentSize,imgSize,isContentDetial,false);
		return data;
	}
	
	
	/** 用于获取单页的数据*/
	@Override
	public Data1 getSpageDatas(Boolean hasImg) {
		Data1 data=new Data1();	
		Information info = infoService.findStaticSpageData(site.getId(),
				channel.getId(),hasImg);
		if(info==null) return null;
		data.setChannelName(channel.getChannelName());
		data.setPath(getPath(info.getId(),false));
		data.setContentList(getSpageDataContentFromInforList(info));
		return data;
	}	

	/** 获取幻灯片显示图片的list 大小由size决定*/
	@Override
	public Data1 getPowerPointList(int size) {		
		if(size==0) return null;		
		Data1 data=new Data1();		
		// 从information表中取出前几条有图片的记录
		List<Information> imgList = infoService.getPowerPointList(site,channel,size);		
		
		if (CollectionUtils.isEmpty(imgList))
			return null;	
		
		List<DataContent1> imgContentList=getDataContentFromInforList(imgList,true);		
		data.setImgContentList(imgContentList);
		return data;
	}
	
	@Override
	public Data1 getVideoList(int size) {
		if(size==0) return null;
		Data1 data=new Data1();
		//从information表中抽取是视频的记录
		List<Information> videoList=infoService.getVideoList(site, channel, size);
		if(CollectionUtils.isEmpty(videoList))
			return null;
		List<DataContent1> videoContentList=getDataContentFromInforList(videoList,true);
		data.setVideoList(videoContentList);
		data.setParentChannelName(channel.getChannelName());
		data.setParentPath(ElianCodes.SPRIT+ApplicationUtils.getSite().getId()+channel.getPath()+ElianCodes.SPRIT+FreemarkerCodes.LIST_OUTPUT_NAME);
		return data;
	}
	
	/** 用于获取指定栏目下点击率最高的列表 */
	@Override
	public Data1 getTopHitsDatas(Integer size){
		if(size==0) return null;
		Data1 data = new Data1();
		List<Content> contentList=contentService.findTopHitsList(channel.getId(), site.getId(), size);
		ArrayList<DataContent1>  list=getDataContentFromcontList(contentList,true,true);
		data.setContentList(list);
		return data;
	}
	
	private Data1 createData(int contentListSize, int imgListSize,
			boolean isContDetial, boolean isImgDetial) {
		Data1 data = new Data1();
		if (channel == null)
			return data;		
		//获取内容列表和图片列表的值
		List<Information> imgContentList=getImgContentList(imgListSize);
		List<Content> contentList=getContentList(imgContentList,contentListSize,imgListSize);		
		if(imgContentList==null && contentList==null) return null;

		//获取栏目路径
		String channelPath=null;
		if(!CollectionUtils.isEmpty(contentList)){
			channelPath=getPath(contentList.get(0).getEntityId(),true);
		}else if(!CollectionUtils.isEmpty(imgContentList)){
			channelPath=getPath(imgContentList.get(0).getId(),true);
		}else{
			channelPath=getPath(null,true);
		}

		//填充data
		data.setPath(channelPath);
		data.setChannelName(channel.getChannelName());
		data.setImgContentList(getDataContentFromInforList(imgContentList,isImgDetial));
		data.setContentList(getDataContentFromcontList(contentList,isContDetial,false));
		return data;
	}
	
	/*
	 * 获取显示图片的list 大小由size决定
	 */
	private List<Information> getImgContentList(int size) {	
		if(size==0) return null;
		// 从information表中取出前几条有图片的记录
		List<Information> imgInforList = infoService.findImgList(channel
				.getId(),site.getId(), size);
		if (CollectionUtils.isEmpty(imgInforList))
			return null;
		return imgInforList;
	}
	
	/*
	 * 获取内容列表 大小由size决定
	 */
	private List<Content> getContentList(List<Information> imgInforList, int contentListSize,int imgContentListSize) {
		if(0==contentListSize || contentListSize==imgContentListSize) return null;
		
		// 从控制表中取出前n条记录（可能不包含有图片的）
		List<Content> contentList = contentService.findStaticPages(channel.getId(), ApplicationUtils
				.getSite().getId(), contentListSize);

		// 从内容列表中去掉在图片列表中的数据
		checkRepeat(imgInforList, contentList, contentListSize, imgContentListSize);
//		if (!CollectionUtils.isEmpty(imgInforList)&&!CollectionUtils.isEmpty(contentList)) {
//			for (Information info : imgInforList) {
//				Iterator<Content> itor = contentList.iterator();
//				while (itor.hasNext()) {
//					Content cont =itor.next();
//					if (cont.getEntityId().equals(info.getId())) {
//						imgContentListSize--;
//						itor.remove();
//					}
//				}
//			}
//			if (imgContentListSize>0) {
//				for (int i = 0; i < imgContentListSize; i++) {
//					contentList.remove(contentList.size()-1);
//				}
//			}			
//		}
		if (CollectionUtils.isEmpty(contentList))
			return null;
		return contentList;
	}
	
	/*
	 * 将information List转换为DataContent1 List
	 */
	private ArrayList<DataContent1> getDataContentFromInforList(
			List<Information> InforList,boolean isDynamicChannel) {
		if (CollectionUtils.isEmpty(InforList)) {
			return null;
		}
		ArrayList<DataContent1> dataContentList = new ArrayList<DataContent1>();			
		for (Information info : InforList) {
			String path=null;
			if(isDynamicChannel) path=getDynamicPath(info.getId());
			else	path=getPath(info.getId(), false);
			DataContent1 dataContent1=new DataContent1(info.getTitle(),info.getDescription(),path,getImgPath(FilePathUtils.getImgPath(info.getInfoImg())));
			dataContent1.setEntity(info);
			dataContentList.add(dataContent1);
		}
		return dataContentList;
	}
	/*
	 * 将information List转换为DataContent1 List
	 */
	private ArrayList<DataContent1> getSpageDataContentFromInforList(Information info) {
		if(null==info) return null;
		Information newInfo=new Information();
		BeanUtils.copyProperties(info, newInfo);
		newInfo.setContent(FilePathUtils.setEditorOutPath(info.getContent()));
		ArrayList<DataContent1> dataContentList = new ArrayList<DataContent1>();			
		String path=getPath(info.getId(),false);
		
		dataContentList.add(new DataContent1(channel.getChannelName(),info.getDescription(),path,
				getImgPath(FilePathUtils.getImgPath(info.getInfoImg())),newInfo));

		return dataContentList;
	}
	
	/*
	 * 将Content List转换为DataContent1 List
	 */
	private ArrayList<DataContent1> getDataContentFromcontList(
			List<Content> contentList,boolean isDetial,boolean isDynamicChannel) {		
		if (CollectionUtils.isEmpty(contentList)) {
			return null;
		}			
		ArrayList<DataContent1> dataContentList = new ArrayList<DataContent1>();
		String imgPath="#";
		Information info=null;
		Information infoPut=null;
		String path=null;
		DataContent1 dataContent1=null;
		String detial=null;
		for (Content c : contentList) {	
			info=null;
			detial=null;
			imgPath="#";
			if(isDynamicChannel) path=getDynamicPath(c.getEntityId());
			else	path=getPath(c.getEntityId(), false);	
			if(isDetial){
				 info=infoService.get(c.getEntityId());
				if(null != info)
					detial=info.getDescription();				
				    imgPath=FilePathUtils.getImgPath(info.getInfoImg());
				    infoPut=new Information();
				    BeanUtils.copyProperties(info, infoPut);
			}			
		    dataContent1=new DataContent1(c.getTitle(),detial, path, imgPath,c.getCreateTime());
		    if(info!=null) {
    		    infoPut.setContent(FilePathUtils.setEditorOutPath(info.getContent()));
    			dataContent1.setEntity(infoPut);
		    }
			dataContentList.add(dataContent1);			
		}
		return dataContentList;
	}	
	
	private String getDynamicPath(int entityId){
		if(actionName==null)
			actionName=SysXmlUtils.getContentActionName("contentType", Information.class.getSimpleName()).getValue();
		channel=channelService.findByEntityId(site.getId(), actionName, entityId);
		setChannel(channel);		
		String path=getPath(entityId,false);
		return path;
	}
}
