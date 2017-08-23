package com.elian.cms.admin.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import com.elian.cms.admin.dao.InitChannelDao;
import com.elian.cms.admin.model.InitChannel;
import com.elian.cms.admin.service.InitChannelService;
import com.elian.cms.syst.model.Pagination;
import com.elian.cms.syst.service.impl.ServiceImpl;
import com.elian.cms.syst.util.ElianUtils;

@Component("initChannelService")
public class InitChannelServiceImpl extends
		ServiceImpl<InitChannelDao, InitChannel, Integer> implements
		InitChannelService {

	public List<InitChannel> findByAll(Integer tempId, Integer parentId,
			Pagination<InitChannel> p) {
		return dao.findByAll(tempId,parentId,p);
	}

	@Resource
	public void setDao(InitChannelDao dao) {
		this.dao = dao;
	}

	public List<InitChannel> findAllParent(Integer initTempId, Boolean isDisable) {
		return dao.findAllParent(initTempId,isDisable);
	}

	public List<InitChannel> find(Integer initTempId, String channelType,
			Integer modelId, String contentType, boolean isDisable) {
		List<InitChannel> subList=null;
		if(ElianUtils.CHANNEL_CONTENT.equals(channelType))
			subList=dao.find(initTempId,channelType,modelId,contentType,isDisable);
//		if(ElianUtils.CHANNEL_PARENT.equals(channelType))
//			subList=dao.find(siteId,)
		if(CollectionUtils.isEmpty(subList)) return null;
		return subList;
	}

	public List<InitChannel> findSubByParentId(List<Integer> idList,
			Integer tempId) {
		return dao.findSubByParentId(idList,tempId);
	}
}
