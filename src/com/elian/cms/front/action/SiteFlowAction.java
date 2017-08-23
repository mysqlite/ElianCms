package com.elian.cms.front.action;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONObject;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.elian.cms.admin.service.SiteFlowCacheService;
import com.elian.cms.admin.service.SiteFlowService;
import com.elian.cms.syst.util.ApplicationUtils;
import com.elian.cms.syst.util.RequestUtils;
import com.elian.cms.syst.util.StringUtils;

@Component
@Scope("prototype")
public class SiteFlowAction {
	private String page=null;
	private String referer=null;
	private Integer siteId=null;
	private String refererUrl=null;
	
	private SiteFlowCacheService siteFlowCacheService=null;
	private SiteFlowService siteFlowService=null;
	
	public void flowStatisticJson() {
		if (!StringUtils.isBlank(page) && siteId!=null && siteId>0) {
			HttpServletRequest request=ApplicationUtils.getRequest();
			String ip = RequestUtils.getIpAddr(request);
			String sessionId = RequestUtils.getRequestedSessionId(request);
			try {
				referer=URLDecoder.decode(referer,"UTF-8");
				System.out.println(referer);
			}
			catch (UnsupportedEncodingException e) {}
			siteFlowCacheService.flow(siteId, ip, sessionId, page, referer,StringUtils.keywork(refererUrl));
			ApplicationUtils.sendJsonpString("true");
		} else {
			ApplicationUtils.sendJsonpString("false");
		}
	}
	
	public void flowCountJson() {
		if (siteId!=null && siteId>0) {
			Map<String, Object> result=new HashMap<String, Object>();
			int count=siteFlowService.getSiteFlowCount(siteId);
			result.put("msg", true);
			result.put("count", count);
			JSONObject jsonObj=JSONObject.fromObject(result);
			ApplicationUtils.sendJsonpObj(jsonObj);
		}
	}
	
	@Resource
	public void setSiteFlowCacheService(SiteFlowCacheService siteFlowCacheService) {
		this.siteFlowCacheService = siteFlowCacheService;
	}
	
	@Resource
	public void setSiteFlowService(SiteFlowService siteFlowService) {
		this.siteFlowService = siteFlowService;
	}

	public String getPage() {
		return page;
	}

	public void setPage(String page) {
		this.page = page;
	}

	public String getReferer() {
		return referer;
	}

	public void setReferer(String referer) {
		this.referer = referer;
	}

	public Integer getSiteId() {
		return siteId;
	}

	public void setSiteId(Integer siteId) {
		this.siteId = siteId;
	}
	
	public String getRefererUrl() {
		return refererUrl;
	}

	public void setRefererUrl(String refererUrl) {
		this.refererUrl = refererUrl;
	}



	public class Msg{
		
	}
}
