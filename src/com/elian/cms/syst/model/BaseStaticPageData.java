package com.elian.cms.syst.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.util.CollectionUtils;

import com.elian.cms.admin.data.exception.TemplateConfigException;
import com.elian.cms.admin.data.util.BaseDataUtil;
import com.elian.cms.admin.model.Channel;
import com.elian.cms.admin.model.Content;
import com.elian.cms.admin.model.Model;
import com.elian.cms.admin.model.Template;
import com.elian.cms.admin.model.TemplateConfig;
import com.elian.cms.admin.model.TemplateSet;
import com.elian.cms.admin.service.ChannelService;
import com.elian.cms.admin.service.ContentService;
import com.elian.cms.admin.service.TemplateConfigService;
import com.elian.cms.admin.service.TemplateSetService;
import com.elian.cms.syst.util.ApplicationUtils;
import com.elian.cms.syst.util.EhcacheUtils;
import com.elian.cms.syst.util.ElianCodes;
import com.elian.cms.syst.util.ElianUtils;
import com.elian.cms.syst.util.FreemarkerCodes;
import com.elian.cms.syst.util.SpringUtils;
import com.elian.cms.syst.util.StaticPageUtils;

/**
 * 静态化特殊页基类
 * 
 * @author Joe
 * 
 */
public class BaseStaticPageData {
	protected ChannelService channelService = null;
	protected ContentService contentService = null;
	protected TemplateSetService templateSetService = null;
	protected TemplateConfigService templateConfigService = null;

	protected Channel channel = null;

	public BaseStaticPageData() {
		channelService = (ChannelService) SpringUtils
				.getEntityService(Channel.class);
		contentService = (ContentService) SpringUtils
				.getEntityService(Content.class);
		templateSetService = (TemplateSetService) SpringUtils
				.getEntityService(TemplateSet.class);
		templateConfigService = (TemplateConfigService) SpringUtils
				.getEntityService(TemplateConfig.class);
	}

	protected void initParams(int channelId) {
		channel = channelService.get(channelId);
	}

	protected void initParams(Channel channel) {
		this.channel = channel;
	}

	/**
	 * 上层调用该方法取数据
	 * @param channel
	 * @param template
	 * @return
	 */
	public Map<String, Object> getDataMap(Channel channel, Template template) {
		Map<String, Object> dataMap = new HashMap<String, Object>();
		StaticPageUtils.putResInMap(dataMap);
		StaticPageUtils.putSysInMap(dataMap);
		if (channel != null) {
			StaticPageUtils.putSeoInMap(dataMap, channel);
		}
		dataMap.put("dataMap", getAllDataMap(channel, template));
		//getSyncDataMap(channel);
		return dataMap;
	}

	/*
	 * 获取栏目的路径
	 */
	public String getListPath(Channel channel) {
		if (ElianUtils.CONTENT_LIST.equals(channel.getContentType())
				&& channel.isStatic()) {
			return StaticPageUtils.getSiteUrl() + channel.getPath()
					+ ElianCodes.SPRIT + FreemarkerCodes.LIST_OUTPUT_NAME;
		}
		return ElianCodes.SHAFT;
	}

	/*
	 * 获取上传控件的img的绝对路径
	 */
	public String getImgPath(String imgUrl) {
		String ftpUrl = EhcacheUtils.getCacheFtp(EhcacheUtils.IMG_FTP).getFtpUrl();
		return ftpUrl + imgUrl;
	}

	/*
	 * 获取内容的绝对路径
	 */
	public String getContentPath(Channel channel, String contentId) {
		return StaticPageUtils.getSiteUrl() + channel.getPath()
				+ ElianCodes.SPRIT + contentId + ElianCodes.SUFFIX_SHTML;
	}

	/*
	 * 获取栏目标题路径
	 */
	public String getNavPath(Channel channel, ChannelService channelService) {
		List<Channel> channelList = channelService.findNavParentForStaticPage(
				channel.getId(), ApplicationUtils.getSite().getId());
		if (CollectionUtils.isEmpty(channelList))
			return "";
		for (Channel c : channelList) {
			if (c.isNavigetor())
				return c.getPath();
		}
		return "";
	}

	// 无用 待删
	public Map<String, Object> getAllDataMap() {
		return null;
	}

	public Map<String, Object> getAllDataMap(Channel channel, Template tempalte) {
		Map<String, Object> dataMap = new HashMap<String, Object>();
		List<TemplateSet> tempSetList = templateSetService.findByAll(tempalte
				.getId(), null);
		List<TemplateConfig> tempConfigList = templateConfigService
				.findByTempIdAndChannelId(tempalte.getId(), channel.getId());
		boolean isOk = checkTempConfig(tempSetList, tempConfigList);
		if (!isOk)
			throw new TemplateConfigException(channel);
		if (CollectionUtils.isEmpty(tempSetList)
				|| CollectionUtils.isEmpty(tempConfigList))
			return dataMap;
		for (TemplateConfig tempConfig : tempConfigList) {
			TemplateSet tempSet = getByTempConfig(tempSetList, tempConfig);
			getAreaData(tempConfig, tempSet, dataMap);
//			getSyncDataMap(tempConfig.getChannelSet());
		}
		return dataMap;
	}

	private TemplateSet getByTempConfig(List<TemplateSet> tempSetList,
			TemplateConfig tempConfig) {
		for (TemplateSet tempSet : tempSetList) {
			if (tempSet.getAreaId().equals(tempConfig.getAreaId()))
				return tempSet;
		}
		return null;
	}

	private void getAreaData(TemplateConfig tempConfig, TemplateSet tempSet,
			Map<String, Object> dataMap) {
		BaseDataUtil dataUtil = getModelDataUtil(tempSet.getModel());
		dataUtil.setChannel(tempConfig.getChannelSet());
		dataUtil.getAllDatas(tempConfig, tempSet, dataMap);
	}

	private BaseDataUtil getModelDataUtil(Model model) {
		String actionName = model.getContentModel().getActionName();
		String modelName = actionName.substring(0, actionName.length() - 2);
		String className = "com.elian.cms.admin.data.util."
				+ modelName.substring(0, 1).toUpperCase()
				+ modelName.substring(1, modelName.length()) + "DataUtil";
		try {
			Class<?> clazz = Class.forName(className);
			BaseDataUtil dataUtil = (BaseDataUtil) clazz.newInstance();
			return dataUtil;
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 检查将要静态化栏目是否在模板的各个位置上已经配置好栏目的id
	 * 
	 * @return
	 */
	private boolean checkTempConfig(List<TemplateSet> tempSetList,
			List<TemplateConfig> tempConfigList) {
		// if(tempSetList.size()==0) return false;
		Set<Integer> areaIdSet = new HashSet<Integer>();
		for (TemplateConfig c : tempConfigList) {
			areaIdSet.add(c.getAreaId());
		}
		return tempSetList.size() > areaIdSet.size() ? false : true;
	}

	/**
	 * 获取栏目配置的模板的id
	 * 
	 * @param channel
	 * @return
	 */
	public List<Integer> getChannelTempIds(Channel channel) {
		List<Integer> tempIds = new ArrayList<Integer>();
		if (null != channel.getChannelTemp()) {
			tempIds.add(channel.getChannelTemp().getId());
		}
		if (null != channel.getContentTemp()) {
			tempIds.add(channel.getChannelTemp().getId());
		}
		return tempIds;
	}

	/**
	 * 获取同步数据
	 * 
	 * @param channel
	 * @return
	 */
	public Map<String, Object> getSyncDataMap(Channel channel) {
		if (null == channel)
			return null;
		List<TemplateConfig> configList = templateConfigService
				.getAllConfig(channel);
		if (CollectionUtils.isEmpty(configList))
			return null;
		Map<String, Object> dataMap = new HashMap<String, Object>();
		Map<String, Object> subMap =null;
		for (TemplateConfig config : configList) {
			subMap= new HashMap<String, Object>(1);
			TemplateSet set = templateSetService.getByTempIdAndAreaId(config
					.getTemplate().getId(), config.getAreaId());
			String mapKey = getSyncDataMapKey(config.getChannel(), config);
			Map<String, Object> subdataMap = new HashMap<String, Object>(1);
//			if(config.getChannel().getId().equals(223))
//				System.out.println();
			if(set.getHasSubArea()){
				List<TemplateConfig> subConfigList= templateConfigService.getByTempIdAreaId(config.getChannel().getId(),config
					.getTemplate().getId(), config.getAreaId());
				for (TemplateConfig sc : subConfigList) {
					TemplateSet subset = templateSetService.getByTempIdAndAreaId(sc
						.getTemplate().getId(),sc.getAreaId());
					getAreaData(sc, subset, subMap);
				}
			}else
				getAreaData(config, set, subMap);
			subdataMap.put("dataMap", subMap);
			dataMap.put(mapKey, subdataMap);
		}
		return dataMap;
	}

	private String getSyncDataMapKey(Channel channel, TemplateConfig config) {
		String mapKey = channel.getId() + ElianCodes.UNDERLINE
				+ config.getTemplate().getId() + ElianCodes.UNDERLINE
				+ config.getAreaId();
		if(channel.getChannelTemp()!=null){
			if(config.getTemplate().getId().equals(channel.getChannelTemp().getId())){
				if (ElianUtils.CHANNEL_PARENT.equals(channel.getChannelType())) {
					mapKey +=ElianCodes.UNDERLINE+ElianUtils.CHANNEL_PARENT;
				}else if(ElianUtils.CHANNEL_INDEX.equals(channel.getChannelType())){
					mapKey +=ElianCodes.UNDERLINE+ElianUtils.CHANNEL_INDEX;
				}else if (ElianUtils.CONTENT_LIST.equals(channel.getContentType())) {
					mapKey +=ElianCodes.UNDERLINE+ElianUtils.CONTENT_LIST;
				}
			}else{
				mapKey +=ElianCodes.UNDERLINE+ElianUtils.CONTENT_SINGLE;
			}
		}
		return mapKey;
	}

}
