package com.elian.cms.front.action;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.annotation.Resource;

import net.sf.json.JSONObject;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import com.elian.cms.admin.data.Data1;
import com.elian.cms.admin.model.Channel;
import com.elian.cms.admin.service.ChannelService;
import com.elian.cms.syst.util.ApplicationUtils;
import com.elian.cms.syst.util.ElianCodes;
import com.elian.cms.syst.util.ElianUtils;
import com.elian.cms.syst.util.FreemarkerCodes;
import com.elian.cms.syst.util.StringUtils;

@Component
@Scope("prototype")
public class PageLevelAction {
	private ChannelService channelService;
	private Integer channelId;
	private Integer siteId;
	private boolean getLast=true;
	private String target;
	private String siteUrl;	
	private String companyType;
	
	public void list(){
		if(null==channelId) return;	
		
		List<Data1> datas=getPageLevel();
		String html=appentHtml(datas);
		JSONObject obj = new JSONObject();
		obj.put("list", html);
		obj.put("data", datas);
		ApplicationUtils.sendJsonpObj(obj);	
	}
	
	private String appentHtml(List<Data1> datas) {
		if(CollectionUtils.isEmpty(datas))	return "";
		
		StringBuffer html=new StringBuffer();
		Iterator<Data1> iterator=datas.iterator();
		while (iterator.hasNext()) {
			Data1 data1 = (Data1) iterator.next();			
			if(iterator.hasNext()){
				html.append("<a href='"+data1.getPath()+"'>");
				html.append(data1.getChannelName());
				html.append("</a>");
				html.append(" "+target+" ");
			}else{
				if(!getLast || StringUtils.isBlank(data1.getPath())){
					html.append(data1.getChannelName());
				}else{
					html.append("<a href='"+data1.getPath()+"'>");
					html.append(data1.getChannelName());
					html.append("</a>");
				}
			}
		}
		return html.toString();
	}

	private List<Data1> getPageLevel() {
		List<Data1> datas=new ArrayList<Data1>();
		
		List<Channel> channelList=channelService.getParentChannelList(siteId,channelId);
		if(CollectionUtils.isEmpty(channelList)){
			return null;
		}
		for(Channel c:channelList){			
			String path="javascript:void(0);";
			//System.out.println("channelType："+c.getChannelType());
			if(ElianUtils.CHANNEL_PARENT.equals(c.getChannelType())){
				if(ElianUtils.COMP_TYPE_SUBS.equals(companyType))	
					path="javascript:void(0);";
				else if(c.isStatic())
					path=getSiteUrl()+c.getPath()+ElianCodes.SPRIT+FreemarkerCodes.INDEX_OUTPUT_NAME;
				datas.add(CreateData1(c, path));
			}else if(ElianUtils.CHANNEL_CONTENT.equals(c.getChannelType())){
				if(c.getContentType().equals(ElianUtils.CONTENT_LIST)){
					if(c.isStatic()) path=getListPath(c.getPath(),c.getId());
					datas.add(CreateData1(c,path));
				}if(ElianUtils.CONTENT_SINGLE.equals(c.getContentType())){
					datas.add(CreateData1(c, ""));
				}
			}
		}
		return datas;
	}

	private Data1 CreateData1(Channel c, String path) {
		Data1 data1=new Data1();
		data1.setChannelName(c.getChannelName());
		data1.setPath(path);
		return data1;
	}
	
	/*
	 * 获取栏目的路径
	 */
	public String getListPath(String navPath, Integer channelId) {
		navPath += ElianCodes.SPRIT + FreemarkerCodes.LIST_OUTPUT_NAME;
		return getSiteUrl() + navPath;
	}
	
	public String getSiteUrl() {
		return null == siteUrl ? "" : siteUrl;
	}

	public void setSiteUrl(String siteUrl) {
		this.siteUrl = siteUrl;
	}

	public Integer getChannelId() {
		return channelId;
	}

	public void setChannelId(Integer channelId) {
		this.channelId = channelId;
	}	

	public Integer getSiteId() {
		return siteId;
	}
	
	public void setSiteId(Integer siteId) {
		this.siteId = siteId;
	}
	
	public boolean isGetLast() {
		return getLast;
	}

	public void setGetLast(boolean getLast) {
		this.getLast = getLast;
	}

	public String getTarget() {
		if(StringUtils.isBlank(target))
			return ">";
		return target;
	}

	public void setTarget(String target) {		
			this.target = target;
	}
	
	public String getCompanyType() {
		return companyType;
	}

	public void setCompanyType(String companyType) {
		this.companyType = companyType;
	}

	@Resource
	public void setChannelService(ChannelService channelService) {
		this.channelService = channelService;
	}
}
