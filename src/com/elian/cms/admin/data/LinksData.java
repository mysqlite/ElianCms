package com.elian.cms.admin.data;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.elian.cms.admin.model.Content;
import com.elian.cms.admin.model.Links;
import com.elian.cms.admin.service.ContentService;
import com.elian.cms.admin.service.LinksService;
import com.elian.cms.syst.util.ApplicationUtils;
import com.elian.cms.syst.util.SpringUtils;
import com.elian.cms.syst.util.SysXmlUtils;

/**
 * 友情链接数据源
 * 
 */
public class LinksData extends MainDataSupport {
	private LinksService linksService;
	private ContentService contentService;
	public Map<String, Object> getAllDataMap() {
		Map<String, Object> dataMap = new HashMap<String, Object>();	
		linksService=(LinksService) SpringUtils.getEntityService(Links.class);
		contentService=(ContentService)SpringUtils.getEntityService(Content.class);
		List<Content> cons=contentService.getByActionName(ApplicationUtils.getSite().getId(),SysXmlUtils.getContentActionName("contentType", "links").getValue(), false, null);
		List<Integer> entityList=new ArrayList<Integer>();
		String channelName=null;
		for (int i = 0,len=cons.size(); i < len; i++) {
			entityList.add(cons.get(i).getEntityId());
		    	if(i==len-1)
			        channelName=cons.get(i).getChannel().getChannelName();
		}
		List<Links> linksList=linksService.get(entityList);
		dataMap.put("links",linksList);
		dataMap.put("channelName",channelName);
		return dataMap;
	}	
}
