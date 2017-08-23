package com.elian.cms.admin.service;


/**
 * 站点流量缓存接口
 */
public interface SiteFlowCacheService {
	public void flow(Integer siteId, String ip, String sessionId, String page, String referrer,String keyword);
}
