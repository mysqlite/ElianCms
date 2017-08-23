package com.elian.cms.admin.service;

import java.util.List;

import com.elian.cms.admin.dao.InitChannelDao;
import com.elian.cms.admin.model.InitChannel;
import com.elian.cms.syst.model.Pagination;
import com.elian.cms.syst.service.Service;

public interface InitChannelService extends
		Service<InitChannelDao, InitChannel, Integer> {

	public List<InitChannel> findByAll(Integer tempId, Integer parentId,
			Pagination<InitChannel> p);
	
	/**
	 * 找到所有的父栏目
	 * @param initTempId 初始化模板的id
	 * @param isDisable 是否可用
	 * @return
	 */
	public List<InitChannel> findAllParent(Integer initTempId, Boolean isDisable);
	
	/**
	 * 
	 * @param initTempId 初始化模板的id
	 * @param channelType 栏目类型
	 * @param modelId 模型id
	 * @param contentType 内容类型
	 * @param isDisable 是否可用
	 * @return
	 */
	public List<InitChannel> find(Integer initTempId, String channelType, Integer modelId,
			String contentType, boolean isDisable);

	public List<InitChannel> findSubByParentId(List<Integer> idList,
			Integer tempId);
	
	
}
