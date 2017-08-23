package com.elian.cms.admin.data.util;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.springframework.util.CollectionUtils;

import com.elian.cms.admin.data.Data1;
import com.elian.cms.admin.model.Channel;
import com.elian.cms.admin.model.Content;
import com.elian.cms.admin.model.Site;
import com.elian.cms.admin.model.TemplateConfig;
import com.elian.cms.admin.model.TemplateSet;
import com.elian.cms.admin.service.ChannelService;
import com.elian.cms.admin.service.ContentService;
import com.elian.cms.syst.model.BaseContent;
import com.elian.cms.syst.util.ApplicationUtils;
import com.elian.cms.syst.util.ElianCodes;
import com.elian.cms.syst.util.ElianUtils;
import com.elian.cms.syst.util.FilePathUtils;
import com.elian.cms.syst.util.FreemarkerCodes;
import com.elian.cms.syst.util.SpringUtils;
import com.elian.cms.syst.util.StaticPageUtils;

/**
 * 数据源的基类 
 * 
 * @author thy
 * 
 */
public class BaseDataUtil {	
	protected ChannelService channelService=null;
	protected ContentService contentService=null;
	
	protected Site site;
	/**数据所在的栏目      要获取路径必须对栏目给值*/
	protected Channel channel=null;
	
	public BaseDataUtil() {
		super();
		channelService=(ChannelService) SpringUtils.getEntityService(Channel.class);
		contentService = (ContentService) SpringUtils.getEntityService(Content.class);
		site=ApplicationUtils.getSite();		
	}
		
	/**
	 * 该函数目的在于统一获取数据源的方法       一下四个参数只在获取栏目类型为内容栏目模型为列表页的时候有效
	 * @param contentSize 仅列表页有效
	 * @param imgSize 仅列表页有效
	 * @param isContentDetial 列表页与单页有效 
	 * @param isImgDetial 该参数暂时无用 false
	 * @return
	 */
	public Object getDatas(int contentSize,int imgSize,boolean isContentDetial,int channelSize){
		try {
			if(null==channel) return null;
			if(channel.getChannelType().equals(ElianUtils.CHANNEL_CONTENT)){
				if(channel.getContentType().equals(ElianUtils.CONTENT_LIST))
					return addParentChannelInfo(getListDatas(contentSize,imgSize,isContentDetial));
				if(channel.getContentType().equals(ElianUtils.CONTENT_SINGLE)){				
					return addParentChannelInfo(getSpageDatas(imgSize>0?true:null));
				}
			}
			if(channel.getChannelType().equals(ElianUtils.CHANNEL_PARENT)){
				return getParentList(contentSize, imgSize, isContentDetial,channelSize);
			}
			
			//TODO 添加外链生成数据 2015年11月19日11:35:41 葛传艺
			if(channel.getChannelType().equals(ElianUtils.CHANNEL_OUT)) {
				Data1 d=new Data1();
				d.setChannelName(channel.getChannelName());
				d.setPath(channel.getOutLinkUrl());
				return d;
			}
		}
		catch (Exception e) {
			if(null==channel) return null;
			if(channel.getChannelType().equals(ElianUtils.CHANNEL_CONTENT)){
				if(channel.getContentType().equals(ElianUtils.CONTENT_LIST))
					return addParentChannelInfo(getListDatas(contentSize,imgSize,isContentDetial));
				if(channel.getContentType().equals(ElianUtils.CONTENT_SINGLE)){				
					return addParentChannelInfo(getSpageDatas(imgSize>0?true:null));
				}
			}
			if(channel.getChannelType().equals(ElianUtils.CHANNEL_PARENT)){
				return getParentList(contentSize, imgSize, isContentDetial,channelSize);
			}
			
			//TODO 添加外链生成数据 2015年11月19日11:35:41 葛传艺
			if(channel.getChannelType().equals(ElianUtils.CHANNEL_OUT)) {
				Data1 d=new Data1();
				d.setChannelName(channel.getChannelName());
				d.setPath(channel.getOutLinkUrl());
				return d;
			}
			return null;
		}
		return null;
	}

	private Object addParentChannelInfo(Object data){
		if(data instanceof Data1){
			if(channel!=null){
				if(channel.getChannelName().startsWith("视频"))
				{
					System.out.println("ddd");
				}
				if(null!=channel.getParentId() && 0!=channel.getParentId()){
					Channel parent=channelService.get(channel.getParentId());
					((Data1)data).setParentChannelName(parent.getChannelName());
					String path=StaticPageUtils.getSiteUrl()+parent.getPath()+
						ElianCodes.SPRIT + FreemarkerCodes.INDEX_OUTPUT_NAME;
					((Data1)data).setParentPath(path);
				}
			}
		}
		return data;
	}
	
	private Map<String, Object> getParentList(int contentSize,int imgSize,boolean isContentDetial,int channelSize){
		Map<String, Object> subDataMap=new HashMap<String, Object>();		
		List<Channel> subChannelList=channelService.findByParentIdSiteId(channel.getId(),site.getId(),true);
		if(!CollectionUtils.isEmpty(subChannelList)){
			int size=(channelSize>subChannelList.size())?subChannelList.size():channelSize;
			for(int i=0;i<size;i++){
				BaseDataUtil baseData = null;
				try {
					baseData = this.getClass().newInstance();
				}
				catch (Exception e) {
					e.printStackTrace();
				}
				baseData.setChannel(subChannelList.get(i));
				try {
					Data1 data1=(Data1) baseData.getDatas(contentSize, imgSize, isContentDetial,channelSize);
					if(null!=data1) subDataMap.put(subDataMap.size()+1+"", data1);
				}
				catch (Exception e) {
					Object obj=baseData.getDatas(contentSize, imgSize, isContentDetial,channelSize);
					if(null!=obj) {
    					if(obj.getClass().equals(HashMap.class)) {
    						if(((Map)obj).size()!=0) {
    							subDataMap.put(subDataMap.size()+1+"", (Map)obj);
    						}
    					}
					}
				}
			}
		}
		return subDataMap;
	}

	/**
	 * 该函数目的在于统一获取链接栏目地址的方法
	 * @param entityId 改参数只有在栏目类型为单页的时候有用  该参数只为提高效率（获取单页栏目链接时）    可为null
	 * @param isChannelPath true 要获取的是栏目的路径  false 要获取的是内容的路径 
	 * @return
	 */
	public String getPath(Integer entityId,boolean isChannelPath){
		if(null==channel) return null;		
		Integer contentId=null;
		if(null !=entityId)
			contentId=contentService.getChannelIdByEntityId(site.getId(),entityId,channel.getId());
		if(isChannelPath)
			return getChannelPath(contentId);
		else
			return getContentPath(contentId);
	}
	
	public String getChannelPath(Integer contentId){
		if(null==channel) return null;		
		if(channel.getChannelType().equals(ElianUtils.CHANNEL_PARENT))
			return getParentParent();
		if(channel.getChannelType().equals(ElianUtils.CHANNEL_CONTENT)){				
			//当栏目是列表页时
			if(channel.getContentType().equals(ElianUtils.CONTENT_LIST)){
				return getListPath();
			}			
			//当栏目是单页时
			if(channel.getContentType().equals(ElianUtils.CONTENT_SINGLE)){
				if(null==contentId){
					List<Content> contentList=contentService.getByChannel(site, channel, ElianUtils.STATUS_3, true);
					if(CollectionUtils.isEmpty(contentList))
						return null;
					contentId=contentList.get(0).getId();
				}
				return getContentPath(contentId);
			}	
		}
		return null;
	}
	
	@SuppressWarnings("unchecked")
	public void getAllDatas(TemplateConfig tempConfig, TemplateSet tempSet,
			Map<String, Object> dataMap) {		
		if(tempSet.getHasSubArea()){
			String nextMapKey="1";
			Map<String, Object> subDataMap=null;
			if(dataMap.containsKey(tempSet.getAreaId().toString())){
				subDataMap=(Map<String, Object>) dataMap.get(tempSet.getAreaId().toString());
				nextMapKey=getNextMapKey(subDataMap);			
			}else{
				subDataMap=new HashMap<String, Object>();
				dataMap.put(tempSet.getAreaId().toString(), subDataMap);
			}
			Object data=getConfigDatas(tempConfig,tempSet);
			if(null!=data)
				subDataMap.put(nextMapKey, data);
		}else{
			Object data=getConfigDatas(tempConfig,tempSet);
			if(null!=data)	dataMap.put(tempSet.getAreaId().toString(),data);
		}
	}	

	private String getNextMapKey(Map<String, Object> subDataMap){
		Iterator<String> it= subDataMap.keySet().iterator();
		Integer maxKey=1;
		while (it.hasNext()) {
			Integer key=Integer.parseInt(it.next());				 
			if(key>maxKey)
				maxKey=key;
		}
		return (maxKey+1)+"";
	}
	
	private Object getConfigDatas(TemplateConfig tempConfig, TemplateSet tempSet){
		Integer maxChannel=0;
		if(tempSet.getChannelType().equals(ElianUtils.CHANNEL_PARENT)){
			maxChannel=tempSet.getMaxChannelSize();
		}
		if(tempSet.getSpecialContentType().equals(ElianUtils.STATUS_0))
			return getDatas(tempSet.getListSize(),0,false,maxChannel);
		if(tempSet.getSpecialContentType().equals(ElianUtils.STATUS_1))
			return getDatas(tempSet.getListSize(),tempSet.getImgSize(),false,maxChannel);			
		if(tempSet.getSpecialContentType().equals(ElianUtils.STATUS_2))
			return getDatas(tempSet.getListSize(),0,true,maxChannel);	
		if(tempSet.getSpecialContentType().equals(ElianUtils.STATUS_3))
			return getPowerPointList(tempSet.getListSize());			
		if(tempSet.getSpecialContentType().equals(ElianUtils.STATUS_4))
			return getTopHitsDatas(tempSet.getListSize());		
		if(tempSet.getSpecialContentType().equals(ElianUtils.STATUS_5))
			return getVideoList(tempSet.getListSize());
		return null;
	}	

	public void checkRepeat(List<? extends BaseContent> imgList,
			List<Content> contentList, int contentSize, int imgListSize) {
		// 从内容列表中去掉在图片列表中的数据
		if (!CollectionUtils.isEmpty(imgList)&&!CollectionUtils.isEmpty(contentList)) {
			for (BaseContent base : imgList) {
				Iterator<Content> itor = contentList.iterator();
				while (itor.hasNext()) {
					Content cont =itor.next();
					if (cont.getEntityId().equals(base.getId())) {
						itor.remove();
					}
				}
			}
			if(contentList.size()>contentSize-imgListSize){
				for(int i=contentList.size();i>contentSize-imgListSize;i--)	
					contentList.remove(i-1);
			}		
		}
	}
	
	/**
	 * 获取父栏目路径
	 */
	public String getParentParent(){
		return channel.getPath()+ElianCodes.SPRIT+"index.html";
	}
	
	/*
	 * 获取列表页路径
	 */
	public String getListPath() {
		if (ElianUtils.CONTENT_LIST.equals(channel.getContentType())
				&& channel.isStatic()) {
			return StaticPageUtils.getSiteUrl() + channel.getPath()
					+ ElianCodes.SPRIT + FreemarkerCodes.LIST_OUTPUT_NAME;
		}
		return ElianCodes.SHAFT;
	}

	/*
	 * 获取内容的绝对路径
	 */
	public String getContentPath(int contentId) {
		return StaticPageUtils.getSiteUrl() + channel.getPath()
				+ ElianCodes.SPRIT + contentId + ElianCodes.SUFFIX_SHTML;
	}
	
	/*
	 * 获取上传控件的img的绝对路径
	 */
	public String getImgPath(String imgUrl) {
		return FilePathUtils.setOutFilePath(imgUrl);
	}	

	/**
	 * 获取单页数据
	 * @return
	 */
	public Data1 getSpageDatas(Boolean hasImg) {		
		return null;
	}

	/**
	 * 获取列表页数据	
	 */
	public Object getListDatas(int contentSize, int imgSize,
			boolean isContentDetial) {
		return null;
	}
	
	/**
	 * 获取幻灯片数据	
	 */
	public Data1 getPowerPointList(int size){
		return null;		
	}
	
	/**
	 * 获取视频数据	
	 */
	public Data1 getVideoList(int size){
		return null;		
	}
	
	/**
	 * 用于获取指定栏目下点击率最高的列表
	 */
	public Data1 getTopHitsDatas(Integer size){
		return null;
	}
	
	public void setChannel(Channel channel) {
		this.channel = channel;
	}
}
