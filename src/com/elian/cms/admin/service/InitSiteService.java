package com.elian.cms.admin.service;

import java.util.Map;

import com.elian.cms.admin.model.Channel;
import com.elian.cms.admin.model.InitChannel;
import com.elian.cms.admin.model.Site;

public interface InitSiteService {
	
	/**
	 * 添加初始化栏目到新建立的站点
	 * @param site
	 * @return
	 */
	public Map<InitChannel, Channel> addInitChannelToSite(Site site);
	
	/**
	 * 初始化配置
	 * @param map
	 */
	public void initTempConfig(Map<InitChannel, Channel> map);
	
	/**
	 * 添加默认数据
	 * @param map
	 */
	public void addDefaultData(Map<InitChannel, Channel> map);
	
	/**
	 * 静态化内容数据
	 * @param map
	 */
	public void staticContent(Map<InitChannel, Channel> map);
	
	/**
	 * 静态化导航数据
	 * @param map
	 */
	public void staticNav(Map<InitChannel, Channel> map);
	
	/**
	 * 静态化包含数据
	 */
	public void staticIncludeFile();
}
