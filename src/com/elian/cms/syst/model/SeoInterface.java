package com.elian.cms.syst.model;

/**
 * Seo接口
 * 
 * @author Joe
 * 
 */
public interface SeoInterface {
	/**
	 * SEO标题
	 */
	public String getSeoTitle();

	/**
	 * SEO关键字
	 */
	public String getSeoKeywords();

	/**
	 * SEO描述
	 */
	public String getSeoDescription();
}
