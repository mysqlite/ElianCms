package com.elian.cms.admin.service.impl;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.elian.cms.admin.dao.ContentDao;
import com.elian.cms.admin.data.exception.TemplateConfigException;
import com.elian.cms.admin.model.Area;
import com.elian.cms.admin.model.Channel;
import com.elian.cms.admin.model.Content;
import com.elian.cms.admin.model.Site;
import com.elian.cms.admin.service.ContentService;
import com.elian.cms.syst.listener.IndexListener;
import com.elian.cms.syst.listener.StaticPageListener;
import com.elian.cms.syst.model.BaseContent;
import com.elian.cms.syst.model.Pagination;
import com.elian.cms.syst.service.JdbcService;
import com.elian.cms.syst.service.Service;
import com.elian.cms.syst.service.impl.ServiceImpl;
import com.elian.cms.syst.util.ApplicationUtils;
import com.elian.cms.syst.util.ElianUtils;
import com.elian.cms.syst.util.SpringUtils;
import com.elian.cms.syst.util.SysXmlUtils;

@SuppressWarnings("unchecked")
@Component("contentService")
public class ContentServiceImpl extends
		ServiceImpl<ContentDao, Content, Integer> implements ContentService {

	public List<Content> findByAll(Integer siteId, Integer channelId,
			Integer status, Pagination<Content> p) {
		return dao.findByAll(siteId, channelId, status, status
				.equals(ElianUtils.CONTENT_STATUS_0) ? ApplicationUtils
				.getUser().getRealName() : null, p);
	}

	public Content findByIdAndAction(Integer channelId, Integer entityId,
			String actionName) {
		return dao.findByIdAndAction(channelId, entityId, actionName);
	}
	
	public List<Content> findByIdAndAction(Integer entityId, String actionName,Integer siteId) {
		return dao.findByIdAndAction(entityId, actionName, siteId);
	}
	
	public List<Content> findByIdsAndAction(Integer channelId, Collection<Integer> entityIds,
			String actionName){
		return dao.findByIdsAndAction(channelId, entityIds, actionName);
	}

	public List<Content> findByIdAndAction(Integer entityId, String actionName) {
		return dao.findByIdAndAction(entityId, actionName);
	}

	public List<Content> findLeafByStatus(Integer channelId, Integer status,
			Integer siteId) {
		return dao.findLeafByStatus(channelId, status, siteId);
	}

	public List<Content> findForListStaticPage(Integer channelId,
			Integer siteId, Pagination<Content> p) {
		return dao.findForListStaticPage(channelId, siteId, p);
	}

	@Autowired
	public void setDao(ContentDao dao) {
		this.dao = dao;
	}

	public List<Content> findLeafByStatus(Integer channelId, Integer status,
			Integer siteId, Integer size) {
		return dao. findLeafByStatus(channelId, status,siteId, size);		
	}
	
	public Integer getChannelIdByEntityId(Integer siteId,Integer entityId,Integer channelId){
		JdbcService jdbcService=SpringUtils.getBean("jdbcService");
		if(channelId!=null)
			return (Integer) jdbcService.findSqlQuery("select ctrl_id from T_CONTROL where site_id=? and table_key_id=? and channel_id=?", siteId,entityId,channelId).toArray()[0];
		else
			return (Integer) jdbcService.findSqlQuery("select ctrl_id from T_CONTROL where site_id=? and table_key_id=?",siteId, entityId).toArray()[0];
	}
	
	public List<Content> findTopHitsList(Integer channelId,Integer siteId,Integer size){
		return dao.findTopHitsList(channelId,siteId,size);
	}

	public List<Content> findStaticPages(Integer channelId,	Integer siteId, Integer size) {		
		return dao.findStaticPages(channelId,siteId,size);
	}

	public List<Content> getAllEntityIds(Integer siteId, List<Channel> channelList,boolean isStatic) {
		return dao.getAllEntityIds(siteId,channelList,isStatic);		 
	}

	public List<Content> getByActionName(Integer siteId, String actionName,
			boolean isStatic, Integer size) {
		return dao.getByActionName(siteId,actionName,isStatic,size);
	}

	public Content getByEntityId(Site site, Class<? extends BaseContent> contentClass,
			Integer entityId) {
		String className=contentClass.getSimpleName();
		String actionName=SysXmlUtils.getContentActionName("contentType", className).getValue();
		return dao.getByEntityId(site.getId(),actionName,entityId);
		
	}
	
	public Content getByEntityId(Integer siteId, Class<? extends BaseContent> contentClass,
			Integer entityId) {
		String className=contentClass.getSimpleName();
		String actionName=SysXmlUtils.getContentActionName("contentType", className).getValue();
		return dao.getByEntityId(siteId,actionName,entityId);
		
	}
	
	

	public List<Content> getByChannel(Site site, Channel channel,
			Integer status, Boolean isStatic) {
		if(isStatic)
			return dao.findForListStaticPage(channel.getId(), site.getId(), null);
		else
			return dao.findByAll(site.getId(), channel.getId(), status, null, null);
	}
	
	public int getContentListLength(Integer channelId) {
		JdbcService jdbcService = SpringUtils.getBean("jdbcService");
		StringBuffer sql = new StringBuffer();
		sql.append(" SELECT COUNT (1) ");
		sql.append("   FROM T_CONTROL T ");
		sql.append("  WHERE EXISTS ");
		sql.append("           (SELECT 1 ");
		sql.append("              FROM T_CHANNEL C ");
		sql.append("             WHERE     C.CHANNEL_CONTENT_TYPE = 'single' ");
		sql.append("                   AND T.CHANNEL_ID = C.CHANNEL_ID ");
		sql.append("                   AND CHANNEL_ID = ?) ");
		return (Integer) jdbcService.findSqlQuery(sql.toString(), channelId)
				.toArray()[0];
	}
	
	public List<Content> findIndexContent(Integer siteId, String actionName){
		return dao.findIndexContent(siteId, actionName);
	}
	
	public void generate(Content content) {
		StaticPageListener s = (StaticPageListener) getStaticPageListenerImpl(content.getActionName(), "staticPageListenerList");
		if (s != null) {
			try {
				s.generateStaticPage(content, null);
			}
			catch (TemplateConfigException e) {
				e.printStackTrace();
			}
		}
	}
	
	/**
	 * 获取当前内容监听实现类
	 */
	private Object getStaticPageListenerImpl(String actionName, String beanName) {
		List<Object> listenerList = SpringUtils.getBean(beanName);
		if (listenerList != null)
			for (Object s : listenerList) {
				String listenerName = s.getClass().getSimpleName();
				String action = actionName.substring(0, 1).toUpperCase()+ actionName.substring(1, actionName.length() - 2);
				if (listenerName.startsWith(action)) {
					return s;
				}
			}
		return null;
	}
	
	public void save(Collection<Content> ts, boolean isEdit) {
		for (Content c : ts)
			this.save(c, isEdit);
	}
	
	public void save(Content content, boolean isEdit) {
		String actionName = content.getActionName();
		String action = actionName.substring(0, actionName.length() - 2);
		Service<?, BaseContent, Serializable> s = (Service<?, BaseContent, Serializable>) SpringUtils
				.getBean(action + "Service");
		save(content, s.get(content.getEntityId()), isEdit);
	}

	public void save(Content content, BaseContent bc, boolean isEdit) {
		super.save(content);
		IndexListener<BaseContent> s = (IndexListener<BaseContent>) getStaticPageListenerImpl(
				content.getActionName(), "indexListenerList");
		if (s != null) {
			if (isEdit)
				s.updateIndex(content, bc);
			else
				s.createIndex(content, bc);
		}
	}

	@Override
	public void delete(Collection<Content> ts) {
		for (Content c : ts)
			this.delete(c);
	}
	
	@Override
	public void delete(Content content) {
		super.delete(content);
		deleteStaticPage(content);
		deleteIndex(content);
	}
	
	/**
	 * 删除静态化文件
	 */
	public void deleteStaticPage(Content content) {
		StaticPageListener s = null;
		// 如果内容已经静态化了，删除的时候需要删除静态化文件
		if ((content.getStaticStatus() == ElianUtils.STATIC_STATUS_1 || content
				.getStaticStatus() == ElianUtils.STATIC_STATUS_2)
				&& (s = (StaticPageListener) SpringUtils
						.getBean("staticPageListener")) != null) {
			s.deleteStaticPage(content);
		}
	}

	/**
	 * 删除索引文件
	 */
	public void deleteIndex(Content content) {
		IndexListener listener = (IndexListener) SpringUtils.getBean("indexListener");
		if (listener != null)
			listener.deleteIndex(content);
	}

	public List<Content> findBySite(Integer siteId,
			List<? extends BaseContent> list, Class contentClass,Boolean isStatic) {
		String className=contentClass.getSimpleName();
		String actionName=SysXmlUtils.getContentActionName("contentType", className).getValue();
		return dao.findBySite(siteId,list,actionName,isStatic);
	}

	public List<Content> findNewestSaticList(int siteId, int size) {
		return dao.findNewestSaticList(siteId,size);
	}
	
	public List<Content> findJobBySite(Integer siteId, Class<?> clazz,
			boolean isStatic, List<Area> list, String jobName,
			Pagination<Content> p) {
		return dao.findJobBySite(siteId, SysXmlUtils.getContentActionName(
				"contentType", clazz.getSimpleName()).getValue(), isStatic,
				list, jobName, p);
	}
}
