package com.elian.cms.admin.dao.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import com.elian.cms.admin.dao.ContentModelDao;
import com.elian.cms.admin.model.ContentModel;
import com.elian.cms.syst.dao.impl.DaoImpl;
import com.elian.cms.syst.model.Pagination;

@Component("contentModelDao")
public class ContentModelDaoImpl extends DaoImpl<ContentModel, Integer>
		implements ContentModelDao {
	public List<ContentModel> findByAll(Pagination<ContentModel> p) {
		StringBuffer hql = new StringBuffer();
		List<Object> params = new ArrayList<Object>();
		hql.append(" from ContentModel cm where 1=1 ");
		if (p != null) {
			p.setAlias("cm");
			return pageByHql(hql.toString(), true, p, params.toArray());
		}
		else
			return findByHql(hql.toString(), true, params.toArray());
	}

	public List<ContentModel> findByChannelId(Integer channelId) {
		if (channelId == null)
			return null;
		StringBuffer hql = new StringBuffer();
		List<Object> params = new ArrayList<Object>();
		hql.append(" SELECT CM.* ");
		hql.append("   FROM T_CONTENT_MODEL CM ");
		hql.append("        INNER JOIN T_MODEL M ON M.CONTENT_MODEL_ID = CM.CONTENT_MODEL_ID ");
		hql.append("        INNER JOIN T_CHANNEL C ON C.MODEL_ID = M.MODEL_ID ");
		hql.append("  WHERE C.CHANNEL_ID = ? ");
		params.add(channelId);
		return findByHql(hql.toString(), true, params.toArray());
	}

	public  List<ContentModel> findByAll(Integer id, boolean b, Pagination<ContentModel> pagination) {
		StringBuffer hql = new StringBuffer();
		List<Object> params = new ArrayList<Object>();
		hql.append(" from ContentModel c where 1=1");
		hql.append(" order by c.id desc");
		if (pagination != null) {
			pagination.setAlias("c");
			return pageByHql(hql.toString(), true, pagination, params.toArray());
		}
		else
			return findByHql(hql.toString(), true, params.toArray());
		
	}
}
