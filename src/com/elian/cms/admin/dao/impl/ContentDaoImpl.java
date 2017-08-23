package com.elian.cms.admin.dao.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import com.elian.cms.admin.dao.ContentDao;
import com.elian.cms.admin.model.Area;
import com.elian.cms.admin.model.Channel;
import com.elian.cms.admin.model.Content;
import com.elian.cms.syst.dao.impl.DaoImpl;
import com.elian.cms.syst.model.BaseContent;
import com.elian.cms.syst.model.Pagination;
import com.elian.cms.syst.util.ElianUtils;
import com.elian.cms.syst.util.StringUtils;

@Component("contentDao")
public class ContentDaoImpl extends DaoImpl<Content, Integer> implements
		ContentDao {
	private String getRecursionFunction(Integer channelId, Integer siteId) {
		StringBuilder function = new StringBuilder();
		if (channelId != null) {
			function.append(" WITH FINDSUBBYPARENTID(CHANNEL_ID, PARENT_ID)  ");
			function.append(" AS ( ");
			function.append("     SELECT C.CHANNEL_ID, C.PARENT_ID  ");
			function.append("       FROM T_CHANNEL C  ");
			function.append("      WHERE C.CHANNEL_ID = " + channelId);
			function.append("        AND C.SITE_ID = " + siteId);
			function.append("     UNION ALL  ");
			function.append("     SELECT T.CHANNEL_ID, T.PARENT_ID  ");
			function.append("       FROM T_CHANNEL T  ");
			function.append(" INNER JOIN FINDSUBBYPARENTID F ON T.PARENT_ID = F.CHANNEL_ID  ");
			function.append(" ) ");
		}
		return function.toString();
	}
	
	public List<Content> findByAll(Integer siteId, Integer channelId,
			Integer status, String userName, Pagination<Content> p) {
		StringBuffer sql = new StringBuffer();
		List<Object> params = new ArrayList<Object>();

		String function = getRecursionFunction(channelId, siteId);
		sql.append(" SELECT C.* ");
		sql.append("   FROM T_CONTROL C ");
		sql.append("  WHERE     1 = 1 ");
		if (siteId != null) {
			sql.append("        AND C.SITE_ID = ? ");
			params.add(siteId);
		}
		if (channelId != null) {
			sql.append("        AND C.CHANNEL_ID IN (   SELECT CHANNEL_ID FROM FINDSUBBYPARENTID) ");
		}
		if (status != null && status > -1) {
			sql.append("        AND C.STATUS = ? ");
			params.add(status);
		}
		if(StringUtils.isNotBlank(userName)){
			sql.append("        AND C.CREATER = ? ");
			params.add(userName);
		}
		sql.append(" ORDER BY C.CHANNEL_ID ASC, C.TOP_MOST DESC, C.CTRL_SORT, C.CREATE_TIME DESC, C.CTRL_ID");
		if (p != null) {
			p.setAlias("C");
			return pageByRecursionSql(sql.toString(), false, p, function,
					params.toArray());
		}
		else
			return findByRecursionSql(sql.toString(), false, function, params
					.toArray());
	}

	public Content findByIdAndAction(Integer channelId, Integer entityId,
			String actionName) {
		StringBuffer hql = new StringBuffer("from Content c where 1=1 ");
		List<Object> params = new ArrayList<Object>();
		if (channelId != null) {
			hql.append("  and c.channel.id=?");
			params.add(channelId);
		}
		if (entityId != null) {
			hql.append("  and c.entityId=?");
			params.add(entityId);
		}
		if (actionName != null) {
			hql.append("  and c.actionName=?");
			params.add(actionName);
		}
		return findByHqlFirst(hql.toString(), false, params.toArray());
	}
	
	public List<Content> findByIdsAndAction(Integer channelId,
			Collection<Integer> entityIds, String actionName) {
		StringBuffer hql = new StringBuffer("from Content c where 1=1 ");
		List<Object> params = new ArrayList<Object>();
		if (channelId != null) {
			hql.append("  and c.channel.id=?");
			params.add(channelId);
		}
		if (!CollectionUtils.isEmpty(entityIds)) {
			hql.append(" and c.entityId in (");
			int i = 0;
			for (Integer id : entityIds) {
				if (i != 0) {
					hql.append(" , ");
				}
				hql.append(" ? ");
				params.add(id);
				i++;
			}
			hql.append(" )");
		}
		if (actionName != null) {
			hql.append("  and c.actionName=?");
			params.add(actionName);
		}
		return findByHql(hql.toString(), false, params.toArray());
	}

	public List<Content> findByIdAndAction(Integer entityId, String actionName) {
		StringBuffer hql = new StringBuffer("from Content c where 1=1 ");
		List<Object> params = new ArrayList<Object>();
		if (entityId != null) {
			hql.append("  and c.entityId=?");
			params.add(entityId);
		}
		if (actionName != null) {
			hql.append("  and c.actionName=?");
			params.add(actionName);
		}
		return findByHql(hql.toString(), false, params.toArray());
	}
	
	public List<Content> findByIdAndAction(Integer entityId, String actionName,Integer siteId) {
		StringBuffer hql = new StringBuffer("from Content c where 1=1 ");
		List<Object> params = new ArrayList<Object>();
		if (entityId != null) {
			hql.append("  and c.entityId=?");
			params.add(entityId);
		}
		if (actionName != null) {
			hql.append("  and c.actionName=?");
			params.add(actionName);
		}
		if(siteId!=null) {
			hql.append(" and c.site.id=? ");
			params.add(siteId);
		}
		return findByHql(hql.toString(), false, params.toArray());
	}

	/*
	 * 为生成静态页获取所有审核通过后的所有叶子节点
	 */
	public List<Content> findLeafByStatus(Integer channelId, Integer status,
			Integer siteId) {
		StringBuffer hql = new StringBuffer(
				"from Content c where c.channel.disable = true");
		List<Object> params = new ArrayList<Object>();
		if (status != null) {
			hql.append("  and c.status=?");
			params.add(status);
		}
		if (siteId != null) {
			hql.append("  and c.site.id=?");
			params.add(siteId);
		}
		if (channelId != null) {
			hql.append("  and c.channel.id=?");
			params.add(channelId);
		}
		return findByHql(hql.toString(), false, params.toArray());
	}
	
	public List<Content> findLeafByStatus(Integer channelId, Integer status,
			Integer siteId,Integer size) {
		if(size==null)
			return findLeafByStatus(channelId,status,siteId);
		else{			
			Pagination<Channel> page=new Pagination<Channel>();
			page.setRowSize(size);
			
			StringBuffer hql = new StringBuffer(
				"from Content c where c.channel.disable = true ");
			List<Object> params = new ArrayList<Object>();
			if (status != null) {
				hql.append("  and c.status=?");
				params.add(status);
			}
			if (siteId != null) {
				hql.append("  and c.site.id=?");
				params.add(siteId);
			}
			if (channelId != null) {
				hql.append("  and c.channel.id=?");
				params.add(channelId);
			}
			hql.append(" order by c.topmost desc, c.sort, c.createTime desc, c.id");
			return pageByHql(hql.toString(), false, page,params.toArray());
		}
	}	
	public List<Content> findForListStaticPage(Integer channelId,
			Integer siteId, Pagination<Content> p) {		
		StringBuffer hql = new StringBuffer("from Content c where 1 = 1");
		List<Object> params = new ArrayList<Object>();
		if (channelId != null) {
			hql.append("  and c.channel.id=?");
			params.add(channelId);
		}
		if (siteId != null) {
			hql.append("  and c.site.id=?");
			params.add(siteId);
		}
		hql.append("  and c.status=?");
		params.add(ElianUtils.CONTENT_STATUS_3);
		hql.append(" order by c.topmost desc, c.sort, c.createTime desc, c.id ");
		if (p != null) {
			return pageByHql(hql.toString(), false, p, params.toArray());
		}
		else
			return findByHql(hql.toString(), false, params.toArray());
	}

	public List<Content> findTopHitsList(Integer channelId, Integer siteId,
			Integer size) {		
		Pagination<Content> pagination=new Pagination<Content>();
		if(size!=null)
			pagination.setRowSize(size);
		String function = getRecursionFunction(channelId, siteId);
		StringBuffer sql = new StringBuffer("SELECT C.* FROM T_CONTROL C WHERE 1 = 1 ");
		List<Object> params = new ArrayList<Object>();
		if (siteId != null) {
			sql.append("        AND C.SITE_ID = ? ");
			params.add(siteId);
		}
		if (channelId != null) {
			sql.append("        AND C.CHANNEL_ID IN (   SELECT CHANNEL_ID FROM FINDSUBBYPARENTID) ");
		}
		addStaticSql(sql, params);
		addOrderSql(sql, "C");	
		return pageByRecursionSql(sql.toString(), false, pagination, function,
				params.toArray());
	}

	public List<Content> findStaticPages(Integer channelId,Integer siteId, Integer size) {
		if(size==null)
			return findLeafByStatus(channelId,ElianUtils.CONTENT_STATUS_3,siteId);
		else{			
			Pagination<Channel> page=new Pagination<Channel>();
			page.setRowSize(size);
			
			StringBuffer hql = new StringBuffer(
				"from Content c where c.channel.disable = true and ((c.status=? and c.staticStatus=?) or (c.status=? and c.staticStatus=?))");
			List<Object> params = new ArrayList<Object>();	
			params.add(ElianUtils.CONTENT_STATUS_3);
			params.add(ElianUtils.STATIC_STATUS_1);
			params.add(ElianUtils.CONTENT_STATUS_2);
			params.add(ElianUtils.STATIC_STATUS_2);			
			if (siteId != null) {
				hql.append("  and c.site.id=?");
				params.add(siteId);
			}
			if (channelId != null) {
				hql.append("  and c.channel.id=?");
				params.add(channelId);
			}
			hql.append(" order by c.topmost desc, c.sort, c.createTime desc, c.id ");
			return pageByHql(hql.toString(), false, page,params.toArray());
		}
	}

	public List<Content> getAllEntityIds(Integer siteId, List<Channel> channelList,
			boolean isStatic) {
		StringBuffer sql = new StringBuffer();
		List<Object> params = new ArrayList<Object>();
		sql.append(" SELECT C.* ");
		sql.append("   FROM T_CONTROL C ");
		sql.append("  WHERE     1 = 1 ");
		if (siteId != null) {
			sql.append("        AND C.SITE_ID = ? ");
			params.add(siteId);
		}
		if (!CollectionUtils.isEmpty(channelList)) {
			sql.append(" AND C.CHANNEL_ID IN (");
			int i = 0;
			for (Channel c : channelList) {
				if (i != 0) {
					sql.append(" , ");
				}
				sql.append(" ? ");
				params.add(c.getId()); 
				i++;
			}
			sql.append(" )");
		}
		if (isStatic) {
			sql.append("        AND ((c.status=? and c.staticStatus=?) or (c.status=? and c.staticStatus=?))");	
			params.add(ElianUtils.CONTENT_STATUS_3);
			params.add(ElianUtils.STATIC_STATUS_1);
			params.add(ElianUtils.CONTENT_STATUS_2);
			params.add(ElianUtils.STATIC_STATUS_2);		
		}
//		sql.append("");		
		return findBySql(sql.toString(), false, params.toArray());
	}

	public List<Content> getByActionName(Integer siteId, String actionName,
			boolean isStatic, Integer size) {			
			Pagination<Channel> page=new Pagination<Channel>();
			if(size!=null) {
			   page.setRowSize(size);
			}		
			StringBuffer sql = new StringBuffer("select * from t_control c where 1=1 ");
			List<Object> params = new ArrayList<Object>();
			addStaticSql(sql, params);
			if (siteId != null) {
				sql.append("  and c.site_id=?");
				params.add(siteId);
			}
			if (actionName != null) {
				sql.append("  and c.action_name=?");
				params.add(actionName);
			}
			addOrderSql(sql, "c");
			if(size==null) {
			    return findBySql(sql.toString(), false, params.toArray());
			}
			return pageBySql(sql.toString(), false, page,params.toArray());		
	}

	public Content getByEntityId(Integer siteId, String actionName,
			Integer entityId) {
		StringBuffer sql = new StringBuffer();
		List<Object> params = new ArrayList<Object>();

		sql.append("select * from t_control where 1=1 ");
		if (null != siteId) {
			sql.append("                          and site_id=?");
			params.add(siteId);
		}
		if (StringUtils.isNotBlank(actionName)) {
			sql.append("                          and action_name=?");
			params.add(actionName);
		}
		if (null != entityId) {
			sql.append("                          and table_key_id=?");
			params.add(entityId);
		}
		List<Content> list= findBySql(sql.toString(), false, params.toArray());
		if(!CollectionUtils.isEmpty(list)) return list.get(0);
		return null;
	}
	
	public List<Content> findIndexContent(Integer siteId, String actionName) {
		StringBuffer sql = new StringBuffer();
		List<Object> params = new ArrayList<Object>();

		sql.append("select * from t_control where 1=1 ");
		if (null != siteId) {
			sql.append("                          and site_id=?");
			params.add(siteId);
		}
		if (StringUtils.isNotBlank(actionName)) {
			sql.append("                          and action_name=?");
			params.add(actionName);
		}
		return findBySql(sql.toString(), false, params.toArray());
	}
	
	private void addStaticSql(StringBuffer strBuffer,List<Object> params ){
		strBuffer.append("  and( (c.status = ? and c.static_status =?) or (c.status=? and c.static_status=?) )");
		params.add(ElianUtils.CONTENT_STATUS_3);
		params.add(ElianUtils.STATIC_STATUS_1);
		params.add(ElianUtils.CONTENT_STATUS_2);
		params.add(ElianUtils.STATIC_STATUS_2);	
	}
	
	private void addOrderSql(StringBuffer strBuffer,String tableName){
		strBuffer.append(" order by "+tableName+".top_most desc, "+tableName+".ctrl_sort, "+tableName+".ctrl_id ");
	}

	public List<Content> findBySite(Integer siteId,
			List<? extends BaseContent> list, String actionName,
			Boolean isStatic) {
		if(CollectionUtils.isEmpty(list)) return null;
		StringBuffer sql=new StringBuffer();
		List<Object> params = new ArrayList<Object>();
		sql.append("select * from t_control c where 1=1 ");
		if (null != siteId) {
			sql.append("                          and c.site_id=?");
			params.add(siteId);
		}
		if(!CollectionUtils.isEmpty(list)){
			sql.append("						 and c.table_key_id in (");
			Iterator<? extends BaseContent> itor=list.iterator();
			while (itor.hasNext()) {
				BaseContent baseContent = (BaseContent) itor.next();
				if(itor.hasNext())
					sql.append(baseContent.getId().toString()).append(",");
				else
					sql.append(baseContent.getId());
			}
			sql.append("												)");
		}
		if (StringUtils.isNotBlank(actionName)) {
			sql.append("                          and c.action_name=?");
			params.add(actionName);
		}
		if(null != isStatic && isStatic)
			addStaticSql(sql, params);
		addOrderSql(sql, "c");			
		return findBySql(sql.toString(), false, params.toArray());
	}

	public List<Content> findNewestSaticList(int siteId, int size) {
		StringBuffer sql=new StringBuffer();
		List<Object> params = new ArrayList<Object>();
		sql.append("select top ("+size+") * from t_control c where c.action_name='information_c' and c.site_id=? ");
		params.add(siteId);
		addStaticSql(sql,params);	
		sql.append(" order by c.create_time desc");
		return findBySql(sql.toString(), false, params.toArray());
	}

	public List<Content> findJobBySite(Integer siteId, String actionName,
			boolean isStatic, List<Area> list, String jobName,
			Pagination<Content> p) {
		StringBuffer sql = new StringBuffer();
		List<Object> params = new ArrayList<Object>();
		sql.append("select * from t_control c");
		if (!CollectionUtils.isEmpty(list) || StringUtils.isNotBlank(jobName)) {
			sql.append("		 inner join t_job j on c.table_key_id = j.job_id  where 1=1");
			if (!CollectionUtils.isEmpty(list)) {
				sql.append("						 and j.area_id in (");
				Iterator<Area> itor = list.iterator();
				while (itor.hasNext()) {
					Area area = itor.next();
					if (itor.hasNext())
						sql.append(area.getId().toString()).append(",");
					else
						sql.append(area.getId());
				}
				sql.append(" )");
			}
			if (StringUtils.isNotBlank(jobName)) {
				sql.append("                          and j.job_Name like '%" + jobName + "%'");
			}
		}
		else {
			sql.append("  where 1=1 ");
		}
		if (null != siteId) {
			sql.append("                          and c.site_id=?");
			params.add(siteId);
		}
		if (StringUtils.isNotBlank(actionName)) {
			sql.append("                          and c.action_name=?");
			params.add(actionName);
		}
		if (isStatic)
			addStaticSql(sql, params);
		addOrderSql(sql, "c");
		if (p != null) {
			p.setAlias("c");
			return pageBySql(sql.toString(), true, p, params.toArray());
		}
		else
			return findBySql(sql.toString(), true, params.toArray());
	}
}
