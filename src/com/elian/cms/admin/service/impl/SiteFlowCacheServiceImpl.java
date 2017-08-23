package com.elian.cms.admin.service.impl;

import java.sql.Timestamp;
import java.util.Date;

import javax.annotation.Resource;

import net.sf.ehcache.Ehcache;
import net.sf.ehcache.Element;

import org.hibernate.HibernateException;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.stereotype.Service;

import com.elian.cms.admin.dto.FlowBeanDto;
import com.elian.cms.admin.model.SiteFlow;
import com.elian.cms.admin.service.SiteFlowCacheService;
import com.elian.cms.admin.service.SiteFlowService;
import com.elian.cms.syst.util.DateFormatUtils;
import com.elian.cms.syst.util.StringUtils;

@Service(value="siteFlowCacheService")
public class SiteFlowCacheServiceImpl implements SiteFlowCacheService, DisposableBean {

	private SiteFlowService SiteFlowService;
	private Ehcache SiteFlowCache;

	// 间隔时间
	private int interval = 1 * 60 * 1000; // 1分钟
	// 最后刷新时间
	private long refreshTime = System.currentTimeMillis();
	
	public void flow(Integer siteId, String ip, String sessionId, String page, String referer,String keyword) {
		SiteFlow cmsSiteFlow = create(siteId, ip, sessionId, page, referer,keyword);
		FlowBeanDto flowBean = new FlowBeanDto(cmsSiteFlow.getAccessDate(), sessionId, page);
		if(SiteFlowCache.get(flowBean) == null){
			SiteFlow bean = null;
			try {
				bean = SiteFlowService.findUniqueByProperties(siteId, cmsSiteFlow.getAccessDate(),sessionId, page);
			} catch (HibernateException e) {
				SiteFlowCache.remove(flowBean);
			}
			if(bean!=null){
				SiteFlowCache.put(new Element(flowBean,bean));
			}else{
				SiteFlowCache.put(new Element(flowBean, cmsSiteFlow));
			}
		}
		refreshToDB();
	}

	private SiteFlow create(Integer siteId, String ip, String sessionId,
			String page, String referer,String keyword) {
		SiteFlow bean = new SiteFlow();
		Date now = new Timestamp(System.currentTimeMillis());
		bean.setSiteId(siteId);
		bean.setAccessIp(ip);
		bean.setAccessPage(page);
		bean.setAccessTime(now);
		bean.setAccessDate(DateFormatUtils.formatDate(now));
		bean.setSessionId(sessionId);
		bean.setRefererPage(referer);
		bean.setRefererSite(getRefererWebSite(referer));
		bean.setArea("");
		bean.setRefererKeyword(keyword);
		return bean;
	}
	
	private void refreshToDB() {
		long time = System.currentTimeMillis();
		if (time > refreshTime + interval) {
			refreshTime = time;
			SiteFlowService.freshCacheToDB(SiteFlowCache);
			// 清除缓存
			SiteFlowCache.removeAll();
		}
	}

	/**
	 * 销毁BEAN时，缓存入库。
	 */
	public void destroy() throws Exception {
		SiteFlowService.freshCacheToDB(SiteFlowCache);
	}

	
	private static String getRefererWebSite(String referer){
		if(StringUtils.isBlank(referer)){
			return "";
		}
		int start = 0, i = 0, count = 3;
		while (i < count && start != -1) {
			start = referer.indexOf('/', start + 1);
			i++;
		}
		if (start <= 0) {
			throw new IllegalStateException(
					"referer website uri not like 'http://.../...' pattern: "
							+ referer);
		}
		return referer.substring(0, start);
	}
	
	
	public void setInterval(int interval) {
		this.interval = interval * 1000;
	}

	@Resource
	public void setSiteFlowService(SiteFlowService siteFlowService) {
		SiteFlowService = siteFlowService;
	}

	@Resource
	public void setSiteFlowCache(Ehcache siteFlowCache) {
		SiteFlowCache = siteFlowCache;
	}
}
