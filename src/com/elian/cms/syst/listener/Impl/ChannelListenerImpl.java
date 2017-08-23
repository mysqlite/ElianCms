package com.elian.cms.syst.listener.Impl;

import java.io.Serializable;
import java.util.List;

import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import com.elian.cms.admin.model.Channel;
import com.elian.cms.admin.model.Content;
import com.elian.cms.admin.model.TemplateConfig;
import com.elian.cms.admin.service.ContentService;
import com.elian.cms.admin.service.DoctorService;
import com.elian.cms.admin.service.HospitalService;
import com.elian.cms.admin.service.TemplateConfigService;
import com.elian.cms.syst.listener.ChannelListener;
import com.elian.cms.syst.model.BaseContent;
import com.elian.cms.syst.service.Service;
import com.elian.cms.syst.util.ApplicationUtils;
import com.elian.cms.syst.util.EhcacheUtils;
import com.elian.cms.syst.util.ElianCodes;
import com.elian.cms.syst.util.FtpToolUtils;
import com.elian.cms.syst.util.SpringUtils;
import com.elian.cms.syst.util.StringUtils;
import com.sun.jmx.remote.internal.ArrayQueue;

/**
 * 栏目监听接口实现类，用于删除栏目已生成的静态化文件
 * @author Joe
 * 
 */
@Component("channelListener")
public class ChannelListenerImpl implements ChannelListener {
	/**
	 * 删除已生成的静态页
	 */
	public void delete(Channel channel) {
		deleteStaticFile(channel);
		deleteChannelTemplateConfig(channel);
		deleteContent(channel);
	}

	/**
	 * 删除栏目生成的静态文件
	 */
	private void deleteStaticFile(Channel channel) {
		if (StringUtils.isBlank(channel.getPath())
				|| ElianCodes.SPRIT.equals(channel.getPath()))
			return;
		FtpToolUtils.delAllFlie(EhcacheUtils.getCacheFtp(EhcacheUtils.STATIC_FTP),
				ApplicationUtils.getSite().getComType().substring(0, 4) + "/"
						+ ApplicationUtils.getSite().getId()
						+ channel.getPath());
	}

	/**
	 * 删除栏目对应的t_temp_config表数据
	 */
	private void deleteChannelTemplateConfig(Channel channel) {
		TemplateConfigService tcService = (TemplateConfigService) SpringUtils
				.getBean("templateConfigService");
		List<TemplateConfig> tcList = tcService.getAllConfig(channel);
		if (!CollectionUtils.isEmpty(tcList))
			tcService.delete(tcList);
	}

	/**
	 * 删除栏目对应的内容表数据
	 */
	private void deleteContent(Channel channel) {
		if (channel.getModel() == null
				|| channel.getModel().getContentModel() == null)
			return;
		ContentService contentService = (ContentService) SpringUtils
				.getBean("contentService");
		List<Content> contentList = contentService.findByIdsAndAction(channel
				.getId(), null, null);
		if (CollectionUtils.isEmpty(contentList))
			return;
		String actionName = channel.getModel().getContentModel()
				.getActionName();
		String objectName = actionName.substring(0, actionName.length() - 2);
		Service<?, BaseContent, Serializable> service = SpringUtils
				.getBean(objectName.concat("Service"));
		for (Content c : contentList) {
			if (!deleteSpecialContent(c, objectName, service)) {
				BaseContent obj = (BaseContent) service.get(c.getEntityId());
				if (obj != null) {
					service.delete(obj);
				}
			}
		}
	}

	/**
	 * 如果模型是医生、科室、医院类型，删除特殊类型数据
	 */
	private boolean deleteSpecialContent(Content c, String objectName,
			Service<?, ?, ?> service) {
		if ("doctor".equals(objectName)) {
			DoctorService s = (DoctorService) service;
			ArrayQueue<Integer> queue = new ArrayQueue<Integer>(1);
			queue.add(c.getId());
			s.delete(null, queue);
			return true;
		}
		else if ("deparetment".equals(objectName)) {
			HospitalService doctorService = (HospitalService) service;
			ArrayQueue<Integer> queue = new ArrayQueue<Integer>(1);
			queue.add(c.getId());
			doctorService.delete(null, queue);
			return true;
		}
		else if ("hospital".equals(objectName)) {
			DoctorService doctorService = (DoctorService) service;
			ArrayQueue<Integer> queue = new ArrayQueue<Integer>(1);
			queue.add(c.getId());
			doctorService.delete(null, queue);
			return true;
		}
		return false;
	}
}
