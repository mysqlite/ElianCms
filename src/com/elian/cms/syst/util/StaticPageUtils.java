package com.elian.cms.syst.util;

import java.util.HashMap;
import java.util.Map;

import com.elian.cms.syst.model.BaseStaticPageData;
import com.elian.cms.syst.model.SeoInterface;

/**
 * 缓存静态模板对应的数据源
 * 
 * @author Joe
 * 
 */
public class StaticPageUtils {

	/** 缓存静态模板文件对应的数据源文件 */
	private final static Map<String, Map<String, String>> cache;

	static {
		cache = new HashMap<String, Map<String, String>>(4);

		Map<String, String> mainMap = new HashMap<String, String>();
		cache.put(ElianUtils.COMP_TYPE_MAIN, mainMap);
		mainMap.put("index.html", "com.elian.cms.admin.data.MainIndexData");
		mainMap.put("friend_link.html", "com.elian.cms.admin.data.DefaultData");
		mainMap.put("health_info.html", "com.elian.cms.admin.data.MainInformationData");
		mainMap.put("news.html", "com.elian.cms.admin.data.MainNewsData");
		mainMap.put("invite.html", "com.elian.cms.admin.data.MainInviteData");
		mainMap.put("hospital.html", "com.elian.cms.admin.data.MainHospitalData");
		mainMap.put("shangjia.html", "com.elian.cms.admin.data.MainShangJiaData");
		mainMap.put("job.html", "com.elian.cms.admin.data.MainJobData");
		mainMap.put("blog.html", "com.elian.cms.admin.data.MainBlogData");
		mainMap.put("drugbase.html", "com.elian.cms.admin.data.MainDrugBaseData");
		mainMap.put("news_list_side.html", "com.elian.cms.admin.data.MainNewsListSideData");
		mainMap.put("news_content_side.html", "com.elian.cms.admin.data.MainNewsContentSideData");
		mainMap.put("illness.html", "com.elian.cms.admin.data.DefaultData");
		
		Map<String, String> hospMap = new HashMap<String, String>();
		cache.put(ElianUtils.COMP_TYPE_HOSP, hospMap);
		hospMap.put("index.html", "com.elian.cms.admin.data.HospPinkIndexData");
		hospMap.put("hosp_content_side.html", "com.elian.cms.admin.data.HospContentSideData");
		hospMap.put("news_content_side.html", "com.elian.cms.admin.data.HospNewsContentSideData");
		hospMap.put("links_list.html", "com.elian.cms.admin.data.LinksData");
	}

	private static String navUrl=FreemarkerCodes.INCLUDE_OUT_URL+ ElianCodes.SPRIT + FreemarkerCodes.NAV_OUTPUT_NAME;
	private static String headUrl=FreemarkerCodes.INCLUDE_OUT_URL+ ElianCodes.SPRIT + FreemarkerCodes.HEAD_OUTPUT_NAME;
	private static String foodUrl=FreemarkerCodes.INCLUDE_OUT_URL+ ElianCodes.SPRIT + FreemarkerCodes.FOOT_OUTPUT_NAME;
	
	public static BaseStaticPageData getTemplateDatas(String tempName) {
		Map<String, String> mainMap = cache.get(ApplicationUtils.getSite()
				.getComType());
		try {
			Class<?> clazz = Class.forName(mainMap.get(tempName));
			BaseStaticPageData data = (BaseStaticPageData) clazz.newInstance();
			return data;
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * 添加样式、脚本、图片、后台服务器
	 */
	public static void putResInMap(Map<String, Object> dataMap) {
		String compType = ElianCodes.SPRIT+ ApplicationUtils.getSite().getComType().substring(0, 4);
//		String compType = ElianCodes.SPRIT+ "main";
		dataMap.put(FreemarkerCodes.SITE_ID, ApplicationUtils.getSite().getId());
		dataMap.put(FreemarkerCodes.COMP_ID, ApplicationUtils.getSite().getComId());
		dataMap.put(FreemarkerCodes.RES_SCRIPT_PUBL, FreemarkerCodes.SCRIPT_URL+ ElianCodes.SPRIT+ "public");
		dataMap.put(FreemarkerCodes.RES_SCRIPT, FreemarkerCodes.SCRIPT_URL+ compType);
		dataMap.put(FreemarkerCodes.RES_STYLE_PUBL, FreemarkerCodes.STYLE_URL+ ElianCodes.SPRIT+ "public");
		dataMap.put(FreemarkerCodes.RES_STYLE, FreemarkerCodes.STYLE_URL+ compType);
		dataMap.put(FreemarkerCodes.RES_IMG, FreemarkerCodes.IMG_URL);
		dataMap.put(FreemarkerCodes.RES_CMS, FreemarkerCodes.CMS_URL);
	}
	
	/**
	 * 添加栏目SEO标题、SEO关键字、SEO描述
	 */
	public static void putSeoInMap(Map<String, Object> dataMap, SeoInterface obj) {
		if (obj != null) {
			dataMap.put(FreemarkerCodes.SEO_TITLE, obj.getSeoTitle());
			dataMap.put(FreemarkerCodes.SEO_KEYWORDS, obj.getSeoKeywords());
			dataMap.put(FreemarkerCodes.SEO_DESCRIPTION, obj.getSeoDescription());
		}
	}
	
	/**
	 * 添加导航页,站点头部，尾部
	 */
	public static void putSysInMap(Map<String, Object> dataMap) {
		String hospSiteRoot="";
		if(ApplicationUtils.isHosp() || ApplicationUtils.isCompany()) {
			hospSiteRoot= ElianCodes.SPRIT+ApplicationUtils.getSite().getId();
		}
		dataMap.put(FreemarkerCodes.NAV_URL, hospSiteRoot+navUrl);
		dataMap.put(FreemarkerCodes.HEAD_URL, hospSiteRoot+headUrl);
		dataMap.put(FreemarkerCodes.FOOT_URL,hospSiteRoot+foodUrl);
	}
	
	public static String getSiteUrl() {
		String compType = ApplicationUtils.getSite().getComType();
		if (!(ElianUtils.COMP_TYPE_MAIN.equals(compType) || ElianUtils.COMP_TYPE_SUBS
				.equals(compType))) {
			return ElianCodes.SPRIT + ApplicationUtils.getSite().getId();
		}
		return "";
	}

	public static void main(String[] args) {
		try {
			Class<?> c = Class
					.forName("com.elian.cms.admin.data.MainIndexData");
			System.out.print(c.getName());
		}
		catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}
}
