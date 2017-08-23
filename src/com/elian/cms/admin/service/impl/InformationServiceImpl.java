package com.elian.cms.admin.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.elian.cms.admin.dao.InformationDao;
import com.elian.cms.admin.model.Channel;
import com.elian.cms.admin.model.Information;
import com.elian.cms.admin.model.Site;
import com.elian.cms.admin.service.InformationService;
import com.elian.cms.syst.listener.ContentListener;
import com.elian.cms.syst.listener.Impl.ContentListenerImpl;
import com.elian.cms.syst.model.Pagination;
import com.elian.cms.syst.service.impl.ServiceImpl;

@Component("informationService")
public class InformationServiceImpl extends
		ServiceImpl<InformationDao, Information, Integer> implements
		InformationService {
	
	public List<Information> findByAll(Integer siteId, Integer channelId,
			Pagination<Information> p) {
		return dao.findByAll(siteId, channelId, p);
	}

	public List<Information> findByContentId(List<Integer> contentIdList,
			Integer siteId) {
		return dao.findByContentId(contentIdList, siteId);
	}

	@Autowired
	public void setDao(InformationDao dao) {
		this.dao = dao;
	}

	public List<Information> findImgList(Integer channelId, Integer siteId,Integer size) {		
		return dao. findImgList(channelId, siteId,size);
	}

	public List<Information> getPowerPointList(Site site,Channel channel,int size) {		
//		List<Content> contentList=contentService.getAllEntityIds(channel.getSite().getId(),subChannelList,true);
//		List<Integer> entityIds=new ArrayList<Integer>();
//		for(Content c:contentList)
//			entityIds.add(c.getId());			
		return dao.getPowerPointList(site,channel,size);
	}
	
	public List<Information> getVideoList(Site site,Channel channel,int size) {		
		return dao.getVideoList(site,channel,size);
	}
	
	public List<Information> findByParentChannelId(Integer siteId,Integer parentChannelId,
			Integer state, Boolean isSatatic) {
		return dao.findByParentChannelId(siteId,parentChannelId,state, isSatatic);
	}

	private ContentListener contentListener;

	public void delete(Information info) {
		ContentListenerImpl.checkSource(info);
		if (info.isSource())
			super.delete(info);
		if (null != contentListener)
			contentListener.afterDelete(info);
	}

	public void save(Information info, boolean isEdit) {
		super.save(info);
		if (null != contentListener)
			if (isEdit)
				contentListener.afterUpdate(info);
			else
				contentListener.afterSave(info);
	}
	
	public Integer save(Information info, boolean isEdit,boolean publish) {
		super.save(info);
		Integer controlId=0;
		if (null != contentListener)
			if (isEdit)
				contentListener.afterUpdate(info,publish);
			else
			 controlId=contentListener.afterSave(info,publish);
		return controlId;
	}
	
	@Resource
	public void setContentListener(ContentListener contentListener) {
		this.contentListener = contentListener;
	}

	public Information findStaticSpageData(Integer siteId, Integer channelId,
			Boolean hasImg) {
		return dao.findStaticSpageData(siteId,channelId,hasImg);
	}

}
