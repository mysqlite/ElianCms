package com.elian.cms.admin.data.util;

import java.util.ArrayList;
import java.util.List;

import org.springframework.util.CollectionUtils;

import com.elian.cms.admin.data.Data1;
import com.elian.cms.admin.data.DataContent1;
import com.elian.cms.admin.model.Content;
import com.elian.cms.admin.model.Links;
import com.elian.cms.admin.service.LinksService;
import com.elian.cms.syst.util.ApplicationUtils;
import com.elian.cms.syst.util.ElianUtils;
import com.elian.cms.syst.util.SpringUtils;

public class LinksDataUtil extends BaseDataUtil{
	protected LinksService linksService=null;	
	
	public LinksDataUtil() {
		super();
		linksService = (LinksService) SpringUtils.getEntityService(Links.class);
	}

	
	/**用于获取列表页数据1*/
	@Override
	public Object getListDatas(int contentSize, int imgSize,boolean isContentDetial) {
		Data1 data = createData(contentSize,imgSize,isContentDetial,false);
		return data;
	}
	/**
	 * @param hasImg 该参数没有使用到
	 */
	@Override
	public Data1 getSpageDatas(Boolean hasImg) {
		Data1 data=new Data1();	
		List<Links> linkList=linksService.findByParentChannelId(site.getId(),channel.getId(),ElianUtils.CONTENT_STATUS_3, true);
		if(CollectionUtils.isEmpty(linkList)) return null;
		data.setChannelName(channel.getChannelName());
		//data.setPath(getPath(linkList.get(0).getId(),false));
		data.setContentList(getSpageDataContentFromInforList(linkList.get(0)));
		return data;
	}	

	private Data1 createData(int contentListSize, int imgListSize,boolean isContDetial, boolean isImgDetial) {
		Data1 data = new Data1();
		if (channel == null)
			return data;		
		List<Content> contentList=getContentList(contentListSize,imgListSize);		
		if(contentList==null) return null;
		data.setChannelName(channel.getChannelName());
		data.setContentList(getDataContentFromcontList(contentList,isContDetial));
		return data;
	}
	
	/*
	 * 获取内容列表 大小由size决定3
	 */
	private List<Content> getContentList( int contentListSize,int imgContentListSize) {
		if(0==contentListSize) return null;
		// 从控制表中取出前n条记录（可能不包含有图片的）
		List<Content> contentList = contentService.findStaticPages(channel.getId(), ApplicationUtils.getSite().getId(), contentListSize);
		if (CollectionUtils.isEmpty(contentList))
			return null;
		return contentList;
	}
	
	/*
	 * 将information List转换为DataContent1 List
	 */
	private ArrayList<DataContent1> getSpageDataContentFromInforList(Links links) {
		if(null==links) return null;
		ArrayList<DataContent1> dataContentList = new ArrayList<DataContent1>();			
		String path=getPath(links.getId(),false);
		dataContentList.add(new DataContent1(channel.getChannelName(),links.getDescription(),path,getImgPath(links.getLogoImg())));
		return dataContentList;
	}
	
	/*
	 * 将Content List转换为DataContent1 List
	 */
	private ArrayList<DataContent1> getDataContentFromcontList(
			List<Content> contentList,boolean isDetial) {		
		if (CollectionUtils.isEmpty(contentList)) {
			return null;
		}			
		ArrayList<DataContent1> dataContentList = new ArrayList<DataContent1>();
		for (Content c : contentList) {		
			String path ="" ;
			String detial=null;
			Links links=linksService.get(c.getEntityId());
			if(null != links) {
			 detial=links.getDescription();		
			 path=links.getLinkUrl();
			}
			dataContentList.add(new DataContent1(c.getTitle(),detial, path, "#",c.getCreateTime()));			
		}
		return dataContentList;
	}	
}
