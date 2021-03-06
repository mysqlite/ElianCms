package com.elian.cms.admin.data.util;

import java.util.ArrayList;
import java.util.List;

import org.springframework.util.CollectionUtils;

import com.elian.cms.admin.data.Data1;
import com.elian.cms.admin.data.DataContent1;
import com.elian.cms.admin.model.Content;
import com.elian.cms.admin.model.Information;
import com.elian.cms.admin.model.Invitation;
import com.elian.cms.admin.service.InvitationService;
import com.elian.cms.syst.util.ApplicationUtils;
import com.elian.cms.syst.util.SpringUtils;
import com.elian.cms.syst.util.SysXmlUtils;

public class InvitationDataUtil extends BaseDataUtil {
	protected InvitationService invitationService = null;
	private String actionName=null;

	public InvitationDataUtil() {
		super();
		invitationService = (InvitationService) SpringUtils
				.getEntityService(Invitation.class);
	}

	@Override
	public Object getListDatas(int contentSize, int imgSize,
			boolean isContentDetial) {
		Data1 data = createData(contentSize, 0, isContentDetial);
		return data;
	}

	/**
	 * @param hasImg 该参数没有使用到
	 */
	@Override
	public Data1 getSpageDatas(Boolean hasImg) {
		Data1 data = new Data1();

		Invitation invitation = invitationService.findStaticSpageData(site
				.getId(), channel.getId());
		if (null == invitation)
			return null;
		data.setChannelName(channel.getChannelName());
		data.setPath(getPath(invitation.getId(), true));
		data.setContentList(getSpageDataContent(invitation));
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
		}
		else {
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
			String path=null;
			if(isDynamicChannel) path=getDynamicPath(c.getEntityId());
			else	path=getPath(c.getEntityId(), false);	
			Invitation invi=null;
			if(isDetial) 
				invi=invitationService.get(c.getEntityId());
			String detial = null;
			DataContent1 dataContent1=new DataContent1(c.getTitle(), detial, path,
					"#",c.getCreateTime());
			dataContent1.setEntity(invi);
			dataContentList.add(dataContent1);
		}
		return dataContentList;
	}

	private List<DataContent1> getSpageDataContent(Invitation invitation) {
		ArrayList<DataContent1> dataContentList = new ArrayList<DataContent1>();
		String path = getPath(invitation.getId(), false);
		DataContent1 dataContent1 = new DataContent1(invitation.getTitle(),
				null, path, "#",invitation.getCreateTime());
		dataContentList.add(dataContent1);
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
