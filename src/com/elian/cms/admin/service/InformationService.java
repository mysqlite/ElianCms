package com.elian.cms.admin.service;

import java.util.List;

import com.elian.cms.admin.dao.InformationDao;
import com.elian.cms.admin.model.Channel;
import com.elian.cms.admin.model.Information;
import com.elian.cms.admin.model.Site;
import com.elian.cms.syst.model.Pagination;
import com.elian.cms.syst.service.Service;

public interface InformationService extends
		Service<InformationDao, Information, Integer> ,BasecontentService<Information> {
	
	public List<Information> findByAll(Integer siteId, Integer channelId,
			Pagination<Information> p);

	public List<Information> findByContentId(List<Integer> contentIdList,
			Integer siteId);

	public void save(Information info, boolean isEdit);
	public Integer save(Information info, boolean isEdit,boolean publish);
	/**
	 * @param siteId站点的id
	 * @param parentChannelId 父栏目id
	 * @param state  内容表的状态
	 * @param isSatatic 是否静态化
	 * @return 符合条件的information列表（包括他本身的这个栏目）
	 */
	public List<Information> findByParentChannelId(Integer siteId,Integer parentChannelId,Integer state,Boolean isSatatic);
	
	public List<Information> findImgList(Integer channelId,Integer siteId,
			Integer state);

	public List<Information> getPowerPointList(Site site,Channel channel,int size);
	
	public List<Information> getVideoList(Site site,Channel channel,int size);

	public Information findStaticSpageData(Integer siteId, Integer channelId,
			Boolean hasImg);
}
