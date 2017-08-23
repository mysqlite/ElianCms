package com.elian.cms.reg.action;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import com.elian.cms.admin.data.DataContent1;
import com.elian.cms.admin.model.Content;
import com.elian.cms.admin.service.ContentService;
import com.elian.cms.syst.action.BaseAction;
import com.elian.cms.syst.util.ApplicationUtils;
import com.elian.cms.syst.util.ElianCodes;
import com.elian.cms.syst.util.FreemarkerCodes;

@Component
@Scope("prototype")
public class RegHotNewsAction extends BaseAction {
	private static final long serialVersionUID = 5744129744348783204L;
	private int size=8;
	private int siteId=1;
	private ContentService contentService=null;
	
	public void listJson() {
		List<Content> contentList=contentService.findNewestSaticList(siteId,size);
		ArrayList<DataContent1> frontList= getDataContentFromcontList(contentList);
		ApplicationUtils.sendJsonpList(frontList);
	}

	public int getSize() {
		return size;
	}

	public void setSize(int size) {
		this.size = size<=0?10:size;
	}
	
	public int getSiteId() {
		return siteId;
	}

	public void setSiteId(int siteId) {
		this.siteId =(siteId<=0)?1:siteId;
	}

	/*
	 * 将Content List转换为DataContent1 List
	 */
	private ArrayList<DataContent1> getDataContentFromcontList(List<Content> contentList) {		
		if (CollectionUtils.isEmpty(contentList)) {
			return null;
		}			
		ArrayList<DataContent1> dataContentList = new ArrayList<DataContent1>();
		for (Content c : contentList) {	
			String path=null;
			path="http://www"+FreemarkerCodes.DOMAIN_SUFFIX+c.getChannel().getPath()+
				ElianCodes.SPRIT+c.getId()+ElianCodes.SUFFIX_SHTML;
			dataContentList.add(new DataContent1(c.getTitle(),null, path, "#",c.getCreateTime()));			
		}
		return dataContentList;
	}

	@Resource
	public void setContentService(ContentService contentService) {
		this.contentService = contentService;
	}	
}