package com.elian.cms.admin.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.commons.net.ftp.FTPClient;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import com.elian.cms.admin.data.DataContent1;
import com.elian.cms.admin.data.HospitalChannelData;
import com.elian.cms.admin.data.HospitalContentData;
import com.elian.cms.admin.data.HospitalDataBean;
import com.elian.cms.admin.model.Channel;
import com.elian.cms.admin.model.Content;
import com.elian.cms.admin.model.DummyChannel;
import com.elian.cms.admin.model.Model;
import com.elian.cms.admin.model.Site;
import com.elian.cms.admin.model.SiteInclude;
import com.elian.cms.admin.model.Template;
import com.elian.cms.admin.service.ChannelService;
import com.elian.cms.admin.service.ContentService;
import com.elian.cms.admin.service.StaticService;
import com.elian.cms.admin.service.TemplateService;
import com.elian.cms.syst.listener.StaticPageListener;
import com.elian.cms.syst.model.BaseStaticPageData;
import com.elian.cms.syst.util.ApplicationUtils;
import com.elian.cms.syst.util.EhcacheUtils;
import com.elian.cms.syst.util.ElianCodes;
import com.elian.cms.syst.util.ElianUtils;
import com.elian.cms.syst.util.FilePathUtils;
import com.elian.cms.syst.util.FileUtils;
import com.elian.cms.syst.util.FreemarkerCodes;
import com.elian.cms.syst.util.FreemarkerUtils;
import com.elian.cms.syst.util.FtpToolUtils;
import com.elian.cms.syst.util.StaticPageUtils;
import com.elian.cms.syst.util.StringUtils;
import com.sun.jmx.remote.internal.ArrayQueue;

@Component("staticService")
public class StaticServiceImpl implements StaticService {
	private final static Log logger = LogFactory.getLog(StaticServiceImpl.class);
	private List<StaticPageListener> staticPageListenerList;
	private ChannelService channelService;
	private ContentService contentService;
	private TemplateService templateService;

	/**
	 * 生成导航静态页
	 */
	public void staticNav(boolean isCache) {
		Map<String, Object> dataMap = getNavDataMap(isCache);
		generateNavStaticPage(dataMap);
		clearFolder();
	}

	/**
	 * 获取导航静态化数据DataMap
	 */
	private Map<String, Object> getNavDataMap(boolean isCache) {
		Map<String, Object> dataMap = new HashMap<String, Object>();
		List<DummyChannel> dataList = getNavDataList(isCache);
		dataMap.put(FreemarkerCodes.NAV_LIST, dataList);
		dataMap.put(FreemarkerCodes.P_NAV_LIST, formartNatList(dataList));
		return dataMap;
	}
	
	private List<DummyChannel> formartNatList(List<DummyChannel> list){
		if(CollectionUtils.isEmpty(list)) 
			return list;
		List<DummyChannel> lists=new ArrayList<DummyChannel>(list.size());
		for (int i = 0,len=list.size(); i < len; i++) {
			lists.add(getChird(list, list.get(i)));
		}
		list.clear();
		for (DummyChannel du : lists) {
			if(du.getpId().equals("0"))
				list.add(du);
		}
		return list;
	}
	
	private DummyChannel getChird(List<DummyChannel> list,DummyChannel duChannel) {
		List<DummyChannel> ducc=new ArrayList<DummyChannel>();
		for (int i = 0,len=list.size(); i < len; i++) {
			if(duChannel.getId().equals( list.get(i).getpId())) 
				ducc.add(list.get(i));
		}
		duChannel.setChirds(ducc);
		return duChannel;
	}
	

	/**
	 * 获取导航静态化数据DataList
	 */
	private List<DummyChannel> getNavDataList(boolean isCache) {
		List<Channel> navList = getDbNavList();
		List<DummyChannel> dataList = new ArrayList<DummyChannel>(navList.size());
		String message = "";
		for (Channel channel : navList) {
			DummyChannel dum = new DummyChannel();
			dum.setChannelName(channel.getChannelName());
			dum.setChannelType(channel.getChannelType());
			dum.setId(channel.getId().toString());
			dum.setpId(channel.getParentId().toString());
			try {
				dum.setPath(generateLinkPath(channel, isCache));
			}
			catch (Exception e) {
				logger.warn("", e);
				message += channel.getChannelName() + " ";
				continue;
			}
			dataList.add(dum);
		}
		if (message.length() > 0) {
			ApplicationUtils.sendMsg("栏目：" + message.trim() + ",没有完全配置模板上的区域值!");
		}
		return dataList;
	}

	/**
	 * 获取当前站点数据库启用的导航栏目
	 */
	private List<Channel> getDbNavList() {
		List<Channel> navList = channelService.findByAll(ApplicationUtils.getSite().getId(), null, true, true, null, true);
		if (navList == null)
			navList = new ArrayList<Channel>(0);
		return navList;
	}

	/**
	 * 生成栏目内容，并返回栏目链接
	 */
	private String generateLinkPath(Channel channel, boolean isCache) {
		String path = ElianCodes.SHAFT;
		if (isCache)
			path = getCacheLinkPath(channel);
		else
			path = getDumLinkPath(channel);
		return path;
	}

	/**
	 * 根据导航栏目的Id获取，当前当行存在缓存中的链接路径,如果缓存中没有，依然重新去找
	 */
	private String getCacheLinkPath(Channel channel) {
		String path = (String) FreemarkerUtils.getCache(String.valueOf(channel
				.getId()));
		if (StringUtils.isBlank(path)) {
			path = getDumLinkPath(channel);
		}
		return path;
	}

	/**
	 * 根据栏目类型，生成相应的静态化文件,并返回路径
	 */
	private String getDumLinkPath(Channel channel) {
		String path = ElianCodes.SHAFT;
		if (ElianUtils.CHANNEL_INDEX.equals(channel.getChannelType())) {
			path = generateIndexStaticPage(channel);
		}
		else if (ElianUtils.CHANNEL_OUT.equals(channel.getChannelType())) {
			if (!StringUtils.isBlank(channel.getOutLinkUrl()))
				path = channel.getOutLinkUrl();
		}
		else if (ElianUtils.CHANNEL_PARENT.equals(channel.getChannelType())) {
			path = generateParentStaticPage(channel);
		}
		else if (ElianUtils.CHANNEL_CONTENT.equals(channel.getChannelType())) {
			path = generateContentStaticPage(channel, false, true);
		}
		// 添加导航路径到缓存中
		FreemarkerUtils.addCache(String.valueOf(channel.getId()), path);
		// 更新栏目静态化字段
		updateChannelStatic(channel);
		return path;
	}
	
	private void updateChannelStatic(Channel channel) {
		if (!channel.isStatic()) {
			channel.setStatic(true);
			channelService.save(channel);
		}
	}

	/**
	 * 生成导航静态页
	 */
	private void generateNavStaticPage(Map<String, Object> dataMap) {
		generateStaticPage(null, null, FreemarkerCodes.NAV_TEMPLATE,FreemarkerCodes.INCLUDE_OUT_URL + ElianCodes.SPRIT+ FreemarkerCodes.NAV_OUTPUT_NAME, dataMap, null);
	}
	
	/**
	 * 生成静态页公用方法
	 */
	public static String generateStaticPage(Channel channel,
			String outputFolder, String tempPath, String outputPath,
			Map<String, Object> dataMap, FTPClient ftp) {
		String templateName = ApplicationUtils.getSite().getTemplate().getFileName()+ tempPath;
		String outputFileName = ApplicationUtils.getSite().getId() + outputPath;

//		Map<String, Object> m=(Map<String, Object>) dataMap.get("dataMap");
//		m.get("11");
		//Data1 k=(Data1) m.get("11");
		//k.getImgContentList().get(0);
		
		if (channel == null)
			FreemarkerUtils.generateStaticFile(templateName, outputFileName,dataMap, ftp);
		else
			FreemarkerUtils.generateStaticFile(channel, outputFolder,templateName, outputFileName, dataMap, ftp);
		
		
		
		for (String key : dataMap.keySet()) {
			if("subChannelList".equals(key)) {
				List<HospitalChannelData> o=(List<HospitalChannelData>) dataMap.get(key);
				for (HospitalChannelData h : o) {
					System.out.println(h.getChannelName()+"_"+h.getPathUrl()+"_"+h.getType());
				}
			}
		}
		
		return StaticPageUtils.getSiteUrl() + outputPath;
	}
	
	/**
	 * 生成静态化首页
	 */
	private String generateIndexStaticPage(Channel channel) {
		Map<String, Object> dataMap = getParentDataMap(channel);
		if (dataMap == null)
			return ElianCodes.SHAFT;
		dataMap.put(FreemarkerCodes.SITE_ID, ApplicationUtils.getSite().getId());
		dataMap.put(FreemarkerCodes.COMP_ID, ApplicationUtils.getSite().getComId());
		String outputFileName = ElianCodes.SPRIT+ FreemarkerCodes.INDEX_OUTPUT_NAME;
		return generateStaticPage(channel,FreemarkerCodes.CHANNEL_OUTPUT_FOLDER,FreemarkerCodes.INDEX_TEMPLATE_URL + ElianCodes.SPRIT+ channel.getChannelTemp().getFileName(),outputFileName, dataMap, null);
	}

	/**
	 * 生成父栏目类型静态页
	 */
	private String generateParentStaticPage(Channel channel) {
		Map<String, Object> dataMap = getParentDataMap(channel);
		if (dataMap == null)
			return ElianCodes.SHAFT;
		String outputFileName = channel.getPath()+ ElianCodes.SPRIT+ FreemarkerCodes.INDEX_OUTPUT_NAME;
		return generateStaticPage(channel, FreemarkerCodes.CHANNEL_OUTPUT_FOLDER,FreemarkerCodes.CHANNEL_TEMPLATE_URL + ElianCodes.SPRIT+ channel.getChannelTemp().getFileName(),outputFileName, dataMap, null);
	}

	/**
	 * 获取父栏目类型静态化数据DataMap
	 */
	private Map<String, Object> getParentDataMap(Channel channel) {
		// 栏目没有选择父栏目模板
		if (channel.getChannelTemp() == null){
			logger.warn(channel.getChannelName()+"没有配置父栏目模板!");
			return null;
		}
		Map<String, Object> dataMap = getConfigDataMap(channel, channel.getChannelTemp());
		System.out.println();
		if (ElianUtils.COMP_TYPE_HOSP.equals(ApplicationUtils.getSite().getComType())) {
			List<HospitalContentData> contentList = new ArrayList<HospitalContentData>();
			List<List<DataContent1>> specialContent=new ArrayList<List<DataContent1>>();
			//获取父栏目对应子栏目
			dataMap.put(FreemarkerCodes.SUB_CHANNEL_LIST,new HospitalDataBean().getDbAllSubChannel(channel,contentList,specialContent));
			dataMap.put(FreemarkerCodes.SUB_PARENT_CHANNEL, channel);
			dataMap.put(FreemarkerCodes.SUB_CONTENT_LIST, contentList);
			dataMap.put(FreemarkerCodes.SPECIAL_CONTENT_LIST, specialContent);			
			
		}else if(ApplicationUtils.isCompany()){
			List<HospitalContentData> contentList = new ArrayList<HospitalContentData>();
			dataMap.put(FreemarkerCodes.CHANNEL, channel);
			dataMap.put(FreemarkerCodes.SUB_CHANNEL_LIST,new HospitalDataBean().getDbAllSubChannel(channel,contentList,null));
			dataMap.put(FreemarkerCodes.SUB_CONTENT_LIST, contentList);
		}
		return dataMap;
	}

	public static Map<String, Object> getConfigDataMap(Channel channel,Template template) {
		return new BaseStaticPageData().getDataMap(channel, template);
	}
	
	/**
	 * 生成内容静态页
	 */
	private String generateContentStaticPage(Channel channel, boolean isCache, boolean isChannel) {
		String path = ElianCodes.SHAFT;
		// 如果栏目的内容类型为:列表页的时候，需要覆盖第一个内容页，返回列表页地址
		if (ElianUtils.CONTENT_LIST.equals(channel.getContentType())) {
			path = generateListStaticPage(channel);
		}
		if (!isChannel || ElianUtils.CONTENT_SINGLE.equals(channel.getContentType())) {
			path = generateContentInfoStaticPage(channel, isCache);
		}
		return path;
	}
	
	/**
	 * 生成列表静态页
	 */
	private String generateListStaticPage(Channel channel) {
		Map<String, Object> dataMap = getListDataMap(channel);
		if (dataMap == null)
			return ElianCodes.SHAFT;

		String outputFileName = channel.getPath() + ElianCodes.SPRIT+ FreemarkerCodes.LIST_OUTPUT_NAME;
		return generateStaticPage(channel, FreemarkerCodes.LIST_OUTPUT_FOLDER,
				FreemarkerCodes.LIST_TEMPLATE_URL + ElianCodes.SPRIT
						+ channel.getChannelTemp().getFileName(),
				outputFileName, dataMap, null);
	}

	/**
	 * 获取内容类型：列表页静态化数据DataMap
	 */
	private Map<String, Object> getListDataMap(Channel channel) {
		// 栏目没有选择列表模板
		if (channel.getChannelTemp() == null){
			logger.warn(channel.getChannelName()+"没有配置列表模板!");
			return null;
		}

		Map<String, Object> dataMap = getConfigDataMap(channel, channel.getChannelTemp());

		dataMap.put(FreemarkerCodes.TABLE_URL, getTableUrl(channel));
		dataMap.put(FreemarkerCodes.PATH_URL, getTableUrl(channel, "pageLevel",
				"list").concat("&siteUrl=").concat(StaticPageUtils.getSiteUrl()).
				concat("&companyType=").concat(ApplicationUtils.getSite().getComType()));
		return dataMap;
	}
	
	@Deprecated
	public static String getSysPrefix(String path) {
		StringBuilder prefixSb = new StringBuilder("");
		if (!StringUtils.isBlank(path)) {
			String[] paths = path.split("/");
			for (int i = 0; i < paths.length - 1; i++) {
				prefixSb.append("../");
			}
		}
		return prefixSb.toString();
	}

	/**
	 * 获取列表页数据链接
	 */
	public static String getTableUrl(Channel channel) {
		return getTableUrl(channel, getActionName(), getActionMethod(channel));
	}

	/**
	 * 获取列表页数据链接
	 */
	public static String getTableUrl(Channel channel, String actionName,
			String actionMethod) {
		return new StringBuilder().append(FreemarkerCodes.CMS_URL).append(
				ElianCodes.SPRIT).append("front/").append(actionName).append(
				"!").append(actionMethod).append(".action?siteId=").append(
				ApplicationUtils.getSite().getId()).append("&channelId=")
				.append(channel.getId()).append("&path=").append(
						StaticPageUtils.getSiteUrl()).append(channel.getPath())
				.toString();
	}

	/**
	 * 获取ActionName，组织类型的前四位+List，例如：hospList
	 */
	private static String getActionName() {
		return ApplicationUtils.getSite().getComType().substring(0, 4) + "List";
	}

	/**
	 * 获取ActionMethod名称，模型action的名称截取后两位，例如：job_c——>job
	 */
	private static String getActionMethod(Channel sub) {
		Model model = sub.getModel();
		if (model != null && model.getContentModel() != null) {
			String objName = model.getContentModel().getActionName();
			return objName.substring(0, objName.length() - 2);
		}
		return "";
	}

	/**
	 * 生成内容信息静态页,并返回第一个生成的内容地址
	 */
	private String generateContentInfoStaticPage(Channel channel, boolean isCache) {
		// 栏目没有选择内容模板
		if (channel.getContentTemp() == null) {
			logger.warn(channel.getChannelName() + "没有配置内容模板!");
			return ElianCodes.SHAFT;
		}
		
		List<Content> contentList = getDbContentList(channel);
		StaticPageListener listener = getStaticPageListenerImpl(channel
				.getModel().getContentModel().getActionName());
		if (listener != null && !CollectionUtils.isEmpty(contentList)) {
			return InvocationContentInfoStaticPage(contentList, listener,
					channel, isCache);
		}
		return ElianCodes.SHAFT;
	}

	/**
	 * 获取数据库所有状态为：通过的内容数据
	 */
	private List<Content> getDbContentList(Channel channel) {
		List<Content> contentList = contentService.findLeafByStatus(channel
				.getId(), ElianUtils.CONTENT_STATUS_3, ApplicationUtils
				.getSite().getId());
		if (contentList == null)
			contentList = new ArrayList<Content>(0);
		return contentList;
	}

	/**
	 * 获取当前内容栏目的对应的内容模型的静态化实现类
	 */
	private StaticPageListener getStaticPageListenerImpl(String actionName) {
		for (StaticPageListener s : staticPageListenerList) {
			String listenerName = s.getClass().getSimpleName();
			String action = actionName.substring(0, 1).toUpperCase()
					+ actionName.substring(1, actionName.length() - 2);
			if (listenerName.startsWith(action)) {
				return s;
			}
		}
		return null;
	}

	/**
	 * 调用生成内容静态方法
	 */
	private String InvocationContentInfoStaticPage(List<Content> contentList,
			StaticPageListener listener, Channel channel, boolean isCache) {
		String path = ElianCodes.SHAFT;
		// 用于保存最新静态过的内容对象
		List<Content> needSaveList = new ArrayList<Content>();
		// 用于链接FTP客户端
		FTPClient ftp = FtpToolUtils.getClient(EhcacheUtils.getCacheFtp(EhcacheUtils.STATIC_FTP));
		for (Content content : contentList) {
			path = invocationContentInfoDetailStaticPage(needSaveList,
					listener, ftp, channel, content, isCache);
		}
		FtpToolUtils.close(ftp);
		contentService.save(needSaveList, true);
		return path;
	}

	private String invocationContentInfoDetailStaticPage(
			List<Content> needSaveList, StaticPageListener s, FTPClient ftp,
			Channel channel, Content content, boolean isCache) {
		String path = ElianCodes.SHAFT;
		// 如果需要重新更新内容，或者内容没有静态化,生成静态内容页
		if (!isCache || content.getStaticStatus() != ElianUtils.STATIC_STATUS_1) {
			path = s.generateStaticPage(content, ftp);
			// 为生成后，内容静态化字段为false的字段更新
			if (content.getStaticStatus() != ElianUtils.STATIC_STATUS_1) {
				content.setStaticStatus(ElianUtils.STATIC_STATUS_1);
				needSaveList.add(content);
			}
		}
		else {
			// 如果静态化过了，也需要为第一条内容获取导航栏目和子栏目的链接路径
			path = StaticPageUtils.getSiteUrl() + channel.getPath()
					+ ElianCodes.SPRIT + content.getId()
					+ ElianCodes.SUFFIX_SHTML;
		}
		return path;
	}
	
	/**
	 * 删除站点下所有文件
	 */
	private void clearFolder() {
		String folderName = ApplicationUtils.getRealPath(null,
				FreemarkerCodes.STATIC_FILE_OUTPUT_URL)
				+ ElianCodes.SPRIT + ApplicationUtils.getSite().getId();
		FileUtils.delFolder(folderName);
	}
	
	/**
	 * 生成栏目静态页
	 */
	public void staticChannel(Integer channelId, boolean isCache) {
		if (channelId != null && channelId > 0) {
			// 有选择具体栏目
			Channel channel = channelService.get(channelId);
			generateLinkPath(channel, isCache);
		}
		clearFolder();
	}

	/**
	 * 生成内容静态页
	 */
	public void staticContent(Integer channelId, boolean isCache) {
		// 获取当前节点对应的所有启用子节点，包含本身,并且栏目类型为：内容类型
		List<Channel> channelList = channelService.findAllSubByForStaticPage(channelId, ApplicationUtils.getSite().getId());
		List<Channel> channelQueue = new ArrayQueue<Channel>(channelList.size());
		for (Channel channel : channelList) {
			generateContentStaticPage(channel, isCache, false);
			syncDetailStaticPage(channel);
			// 更新栏目静态化字段
			if (!channel.isStatic()) {
				channel.setStatic(true);
				channelQueue.add(channel);
			}
		}
		channelService.save(channelQueue);
		clearFolder();
	}
	
	/**
	 * 同步栏目存在的引用
	 */
	@SuppressWarnings("unchecked")
	public void syncDetailStaticPage(Channel channel) {
		Map<String, Object> detailDataMap = new BaseStaticPageData()
				.getSyncDataMap(channel);
		if (CollectionUtils.isEmpty(detailDataMap))
			return;
		for (String key : detailDataMap.keySet()) {
			syncDetailTemplate(key, (Map<String, Object>) detailDataMap
					.get(key));
		}
	}

	public void syncDetailTemplate(String key, Map<String, Object> dataMap) {
		if (StringUtils.isBlank(key))
			return;
		String[] ids = key.split(ElianCodes.UNDERLINE);
		if (ids == null || ids.length != 4)
			return;
		generateDetailTemplate(Integer.valueOf(ids[0]),
				Integer.valueOf(ids[1]), ids[2], ids[3], dataMap);
	}

	public void generateDetailTemplate(Integer channelId, Integer tempId,
			String areaId, String contentType, Map<String, Object> dataMap) {
		String outputFileName = getOutputFileName(channelId, areaId,
				contentType);
		String templateName = getTemplateFile(tempId, areaId);
		FreemarkerUtils.generateStaticFile(templateName, outputFileName,
				dataMap, null);
	}

	public String getOutputFileName(Integer channelId, String areaId,
			String contentType) {
		Channel channel = channelService.get(channelId);
		String outputFileName = ApplicationUtils.getSite().getId()
				+ (StringUtils.isBlank(channel.getPath()) ? ElianCodes.SPRIT
						: (channel.getPath() + ElianCodes.SPRIT));
		if (ElianUtils.CHANNEL_PARENT.equals(contentType)
				|| ElianUtils.CHANNEL_INDEX.equals(contentType)) {
			outputFileName += FreemarkerCodes.CHANNEL_OUTPUT_FOLDER;
		}
		else if (ElianUtils.CONTENT_LIST.equals(contentType)) {
			outputFileName += FreemarkerCodes.LIST_OUTPUT_FOLDER;
		}
		else if (ElianUtils.CONTENT_SINGLE.equals(contentType)) {
			outputFileName += FreemarkerCodes.CONTENT_OUTPUT_FOLDER;
		}
		outputFileName += areaId + ElianCodes.SUFFIX_SHTML;
		return outputFileName;
	}

	public String getTemplateFile(Integer tempId, String areaId) {
		Template template = templateService.get(tempId);
		String templateFile = ElianCodes.SPRIT
				+ template.getFileName().replace(ElianCodes.SUFFIX_HTML, "")
				+ ElianCodes.SPRIT;
		if (template.getParentId() != null) {
			Template parent = templateService.get(template.getParentId());
			templateFile = ElianCodes.SPRIT + parent.getFileName()
					+ templateFile;
		}
		templateFile = ApplicationUtils.getSite().getTemplate().getFileName()
				+ templateFile;
		templateFile += (areaId + ElianCodes.SUFFIX_HTML);
		return templateFile;
	}
	
	/**
	 * 静态化特殊页
	 */
	public void staticSpecial(Template template) {
		// Map<String, Object> dataMap = getSpecialDataMap(null, template
		// .getFileName());
		// if (dataMap == null)
		// return;
		//
		// String outputFileName = ElianCodes.SPRIT
		// + template.getFileName().substring(0,
		// template.getFileName().length() - 5).concat(
		// ElianCodes.SUFFIX_SHTML);
		// generateStaticPage(FreemarkerCodes.OTHER_TEMPLATE_URL
		// + ElianCodes.SPRIT + template.getFileName(), outputFileName,
		// dataMap, null);
		// clearFolder();
	}

	/**
	 * 获取特殊页静态化数据DataMap
	 */
	// private Map<String, Object> getSpecialDataMap(Channel channel,
	// String tempName) {
	// // 模板没有配置对应的数据源
	// BaseStaticPageData baseData = StaticPageUtils
	// .getTemplateDatas(tempName);
	// if (baseData == null)
	// return null;
	// Map<String, Object> dataMap = baseData.getDataMap(channel,
	// channel.getChannelTemp());
	// return dataMap;
	// }

	
	public void staticIncludeFile(SiteInclude siteInclude) {
		Site site = ApplicationUtils.getSite();

		Map<String, Object> dataMap = new HashMap<String, Object>();
		// 公共的数据
		StaticPageUtils.putResInMap(dataMap);
		StaticPageUtils.putSysInMap(dataMap); 

		dataMap.put("siteName", site.getSiteName());
		dataMap.put("siteDomain", site.getDomain());
		dataMap.put("siteAlias", site.getAlias());
		dataMap.put("domainSuffix", FreemarkerCodes.DOMAIN_SUFFIX);
		dataMap.put("content", FilePathUtils.setEditorOutPath(siteInclude.getContent()));

		generateIncludePage(dataMap,siteInclude.getFileName(),
				FreemarkerUtils.getIncludeOutFileName(siteInclude.getFileName()));		
		clearFolder();
	}
	
	private void generateIncludePage(Map<String, Object> dataMap,
			String tempName, String outFileName) {
		String outputFileName = FreemarkerCodes.INCLUDE_OUT_URL
				+ ElianCodes.SPRIT + outFileName;
		generateStaticPage(null, null, FreemarkerCodes.INCLUDE_TEMPLATE_URL
				+ ElianCodes.SPRIT + tempName, outputFileName, dataMap, null);
	}
	
	@Resource
	public void setChannelService(ChannelService channelService) {
		this.channelService = channelService;
	}

	@Resource
	public void setContentService(ContentService contentService) {
		this.contentService = contentService;
	}
	
	@Resource
	public void setTemplateService(TemplateService templateService) {
		this.templateService = templateService;
	}
		
	@Resource
	public void setStaticPageListenerList(
			List<StaticPageListener> staticPageListenerList) {
		this.staticPageListenerList = staticPageListenerList;
	}
	
}
