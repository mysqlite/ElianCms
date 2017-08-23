package com.elian.cms.syst.util;

public class FreemarkerCodes {
	/** 后台路径 */
	//public static final String CMS_URL = "http://192.168.0.113:8080/ElianCms";
	public static final String CMS_URL ="http://cms.mcoding.cn"; 
	/** 静态文件路径 */
	public static final String DOMAIN_SUFFIX = ".mcoding.cn";
	/** 样式路径 */
	public static final String STYLE_URL = "http://style.mcoding.cn";
	/** 脚本路径 */
	public static final String SCRIPT_URL = "http://script.mcoding.cn";
	/** 图片路径 */
	public static final String IMG_URL = "http://images.mcoding.cn";

	/** 模板存放路径 */
	public static final String TEMPLATE_URL = "/template";
	/** 静态文件输出路径 */
	public static final String STATIC_FILE_OUTPUT_URL = "/temp";

	/** 导航页模板 */
	public static final String NAV_TEMPLATE = "/nav/nav.html";
	/** 列表页模板 */
	public static final String LIST_TEMPLATE_URL = "/list";
	/** 栏目模板路径 */
	public static final String CHANNEL_TEMPLATE_URL = "/channel";
	/** 特殊页模板路径 */
	public static final String OTHER_TEMPLATE_URL = "/other";
	/** 首页模板路径 */
	public static final String INDEX_TEMPLATE_URL = "/index";
	/** 内容模板路径 */
	public static final String CONTENT_TEMPLATE_URL = "/content";
	
	/** 被包涵模板路径 */
	public static final String INCLUDE_TEMPLATE_URL = "/include";
	/** 头部模板前缀 */
	public static final String HEAD_TEMPLATE_PREFIX = "head";
	/** 尾部模板前缀 */
	public static final String FOOT_TEMPLATE_PREFIX = "foot";	
	/** 头部输出文件名称 */
	public static final String HEAD_OUTPUT_NAME = "head"
			.concat(ElianCodes.SUFFIX_SHTML);
	/**商品类型*/
	public static final String PRODUCT_TYPE="product_type";
	/** 尾部输出文件名称 */
	public static final String FOOT_OUTPUT_NAME = "foot"
			.concat(ElianCodes.SUFFIX_SHTML);
	/** 模板头部路径名称 */
	public static final String HEAD_URL = "headUrl";
	/** 模板尾部路径名称 */
	public static final String FOOT_URL = "footUrl";
	/** 站点ID */
	public static final String SITE_ID = "siteId";
	/** 组织ID */
	public static final String COMP_ID = "compId";
	/** 被包涵模板输出文件路径 */
	public static final String INCLUDE_OUT_URL = "/include";
	/** 父栏目输出文件名称 */
	public static final String INDEX_OUTPUT_NAME = "index"
			.concat(ElianCodes.SUFFIX_SHTML);
	/** 导航页输出文件名称 */
	public static final String NAV_OUTPUT_NAME = "nav"
			.concat(ElianCodes.SUFFIX_SHTML);
	/** 列表页输出文件后缀名称 */
	public static final String LIST_OUTPUT_NAME = "list"
			.concat(ElianCodes.SUFFIX_SHTML);
	
	/** 栏目输出文件夹路径 */
	public static final String CHANNEL_OUTPUT_FOLDER = ElianCodes.UNDERLINE
			.concat("channel").concat(ElianCodes.SPRIT);
	/** 列表明细输出文件夹路径 */
	public static final String LIST_OUTPUT_FOLDER = ElianCodes.UNDERLINE
			.concat("list").concat(ElianCodes.SPRIT);
	/** 内容明细输出文件夹路径 */
	public static final String CONTENT_OUTPUT_FOLDER = ElianCodes.UNDERLINE
			.concat("content").concat(ElianCodes.SPRIT);
	
	/** 公用系统样式名称 */
	public static final String RES_STYLE_PUBL = "resStylePubl";
	/** 模板引入系统样式名称 */
	public static final String RES_STYLE = "resStyle";
	/** 公用系统脚本名称 */
	public static final String RES_SCRIPT_PUBL = "resScriptPubl";
	/** 模板引入系统脚本名称 */
	public static final String RES_SCRIPT = "resScript";
	/** 模板引入系统图片名称 */
	public static final String RES_IMG = "resImg";
	/** 模板引入服务器路径 */
	public static final String RES_CMS = "resCms";
	/** SEO标题 */
	public static final String SEO_TITLE = "title";
	/** SEO关键字 */
	public static final String SEO_KEYWORDS = "keywords";
	/** SEO描述 */
	public static final String SEO_DESCRIPTION = "description";

	/** 模板导航路径名称 */
	public static final String NAV_URL = "navUrl";
	/** 模板栏目路径名称 */
	public static final String CHANNEL_URL = "channelUrl";
	/** 模板表格路径名称 */
	public static final String TABLE_URL = "tableUrl";
	/** 模板导航栏名称 */
	public static final String NAV_LIST = "navList";
	/**父子结构导航*/
	public static final String P_NAV_LIST="pNavList";
	/** 模板子栏目名称 */
	public static final String SUB_CHANNEL_LIST = "subChannelList";
	/**父栏目*/
	public static final String SUB_PARENT_CHANNEL="parentChannel";
	/** 模板内容名称 */
	public static final String SUB_CONTENT_LIST = "subContentList";
	/** 特殊内容名称*/
	public static final String SPECIAL_CONTENT_LIST="specialContentList";
	/** 模板位置名称 */
	public static final String PATH_URL = "pathUrl";
	/** 模板明细文件夹名称 */
	public static final String DETAIL_FOLDER = "detailFolder";
	/**栏目*/
	public static final String CHANNEL = "channel";
}
