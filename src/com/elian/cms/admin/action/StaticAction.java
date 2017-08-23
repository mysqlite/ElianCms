package com.elian.cms.admin.action;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import com.elian.cms.admin.model.Channel;
import com.elian.cms.admin.model.DummyChannel;
import com.elian.cms.admin.model.SiteInclude;
import com.elian.cms.admin.model.Template;
import com.elian.cms.admin.service.ChannelService;
import com.elian.cms.admin.service.SiteIncludeService;
import com.elian.cms.admin.service.StaticService;
import com.elian.cms.admin.service.TemplateService;
import com.elian.cms.syst.action.BaseAction;
import com.elian.cms.syst.util.ApplicationUtils;
import com.elian.cms.syst.util.ElianCodes;
import com.elian.cms.syst.util.FreemarkerCodes;

/**
 * 静态化功能
 * 
 * @author Joe
 * 
 */
@Component
@Scope("prototype")
public class StaticAction extends BaseAction {
	private static final long serialVersionUID = 8916452923159136955L;

	private StaticService staticService;
	private ChannelService channelService;
	private TemplateService templateService;
	private SiteIncludeService siteIncludeService;

	/** 访问目录 */
	private String url;
	/** 栏目ID */
	private Integer channelId;
	/** 特殊页模板名称 */
	private Integer tempId;
	/** 判断生成是否获取缓存 */
	private boolean isCache;
	/** 特殊页模板List */
	private List<Template> otherTempList;
	/** 站点包含文件的id */
	private Integer siteIncludeId;

	public void staticIndex() {
		List<Channel> navList = channelService.findByAll(ApplicationUtils
				.getSite().getId(), null, true, true, null, true);
		List<DummyChannel> dataList = new ArrayList<DummyChannel>(navList
				.size());
		for (Channel channel : navList) {
			DummyChannel dum = new DummyChannel();
			dum.setChannelName(channel.getChannelName());
			dum.setChannelType(channel.getChannelType());
			dum.setId(channel.getId().toString());
			dum.setpId(channel.getParentId().toString());
			dum.setPath("#");
			dataList.add(dum);
		}
		List<DummyChannel> duChannels = formartNatList(dataList);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		dataMap.put(FreemarkerCodes.P_NAV_LIST, duChannels);
		// FreemarkerUtils.generateStaticFile("hosp_huangqi/nav/nav.html",
		// "270/nav.shtml", dataMap,
		// FtpToolUtils.getClient(EhcacheUtils.getCacheFtp(EhcacheUtils.STATIC_FTP)));
		for (DummyChannel dummyChannel : duChannels) {
			digui(dummyChannel);
		}
	}

	private void digui(DummyChannel channel) {
		if (channel.getChirds() != null && channel.getChirds().size() > 0) {
			for (DummyChannel c : channel.getChirds()) {
				digui(c);
			}
		}
	}

	private List<DummyChannel> formartNatList(List<DummyChannel> list) {
		if (CollectionUtils.isEmpty(list))
			return list;
		List<DummyChannel> lists = new ArrayList<DummyChannel>(list.size());
		for (int i = 0, len = list.size(); i < len; i++) {
			lists.add(getChird(list, list.get(i)));
		}
		list.clear();
		for (DummyChannel du : lists) {
			if (du.getpId().equals("0"))
				list.add(du);
		}
		return list;
	}

	private DummyChannel getChird(List<DummyChannel> list,
			DummyChannel duChannel) {
		List<DummyChannel> ducc = new ArrayList<DummyChannel>();
		for (int i = 0, len = list.size(); i < len; i++) {
			if (duChannel.getId().equals(list.get(i).getpId()))
				ducc.add(list.get(i));
		}
		duChannel.setChirds(ducc);
		return duChannel;
	}

	public void staticNav() {
		try {
			staticService.staticNav(isCache);
		}
		catch (Exception e) {
			e.printStackTrace();
			ApplicationUtils.sendMsg(e.getMessage());
			return;
		}
		ApplicationUtils.sendMsg("发布成功!");
	}

	public void staticChannel() {
		try {
			staticService.staticChannel(channelId, isCache);
		}
		catch (Exception e) {
			e.printStackTrace();
			ApplicationUtils.sendMsg(e.getMessage());
			return;
		}
		ApplicationUtils.sendMsg("发布成功!");
	}

	public void staticContent() {
		try {
			staticService.staticContent(channelId, isCache);
		}
		catch (Exception e) {
			e.printStackTrace();
			ApplicationUtils.sendMsg(e.getMessage());
			return;
		}
		ApplicationUtils.sendMsg("发布成功!");
	}

	public void staticSpecial() {
		Template template = null;
		if (tempId == null || (template = templateService.get(tempId)) == null) {
			return;
		}
		try {
			staticService.staticSpecial(template);
		}
		catch (Exception e) {
			e.printStackTrace();
			ApplicationUtils.sendMsg(e.getMessage());
			return;
		}
		ApplicationUtils.sendMsg("发布成功!");
	}

	public void staticIncludeFile() {
		try {
			SiteInclude siteInclude = siteIncludeService.get(siteIncludeId);
			staticService.staticIncludeFile(siteInclude);
		}
		catch (Exception e) {
			e.printStackTrace();
			ApplicationUtils.sendMsg(e.getMessage());
			return;
		}
		ApplicationUtils.sendMsg("发布成功!");
	}

	public List<Template> getOtherTempList() {
		if (otherTempList == null) {
			otherTempList = InvocationTemp(otherTempList,
					FreemarkerCodes.OTHER_TEMPLATE_URL, "");
		}
		return otherTempList;
	}

	private List<Template> InvocationTemp(List<Template> tempList,
			String folderName, String tempPrefix) {
		List<Template> list = templateService.findByParentAndFolder(
				ApplicationUtils.getSite().getTemplate().getId(), folderName
						.replace(ElianCodes.SPRIT, ""), true);
		if (list == null)
			list = new ArrayList<Template>(0);
		return list;
	}

	/*
	 * 获取栏目树结构
	 */
	public void tree() {
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		List<Channel> channelList = channelService.findByAll(ApplicationUtils
				.getSite().getId(), null, true, false, null, false);
		if (channelList != null && channelList.size() > 0) {
			Map<String, Object> map = null;
			for (Channel c : channelList) {
				map = new LinkedHashMap<String, Object>();
				map.put("id", c.getId());
				map.put("name", c.getChannelName());
				map.put("pId", c.getParentId());
				list.add(map);
			}
		}

		ApplicationUtils.sendJsonArray(list);
	}

	public Integer getChannelId() {
		return channelId;
	}

	public void setChannelId(Integer channelId) {
		this.channelId = channelId;
	}

	public boolean isCache() {
		return isCache;
	}

	public void setCache(boolean isCache) {
		this.isCache = isCache;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public Integer getTempId() {
		return tempId;
	}

	public void setTempId(Integer tempId) {
		this.tempId = tempId;
	}

	public Integer getSiteIncludeId() {
		return siteIncludeId;
	}

	public void setSiteIncludeId(Integer siteIncludeId) {
		this.siteIncludeId = siteIncludeId;
	}

	@Resource
	public void setChannelService(ChannelService channelService) {
		this.channelService = channelService;
	}

	@Resource
	public void setStaticService(StaticService staticService) {
		this.staticService = staticService;
	}

	@Resource
	public void setTemplateService(TemplateService templateService) {
		this.templateService = templateService;
	}

	@Resource
	public void setSiteIncludeService(SiteIncludeService siteIncludeService) {
		this.siteIncludeService = siteIncludeService;
	}
}
