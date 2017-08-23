package com.elian.cms.admin.service;

import com.elian.cms.admin.model.SiteInclude;
import com.elian.cms.admin.model.Template;

public interface StaticService {

	/**
	 * 生成导航静态页
	 */
	public void staticNav(boolean isCache);
	
	/**
	 * 生成栏目静态页
	 */
	public void staticChannel(Integer channelId, boolean isCache);

	/**
	 * 生成内容静态页
	 */
	public void staticContent(Integer channelId, boolean isCache);
	
	/**
	 * 生成特殊页静态页
	 */
	public void staticSpecial(Template template);

	/**
	 * 生成包含文件
	 * @param siteInclude 
	 */
	public void staticIncludeFile(SiteInclude siteInclude);
	
}
