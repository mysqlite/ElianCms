package com.elian.cms.admin.dao.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import com.elian.cms.admin.dao.LinksDao;
import com.elian.cms.admin.model.Links;
import com.elian.cms.syst.dao.impl.DaoImpl;
import com.elian.cms.syst.util.ElianUtils;

@Component("linksDao")
public class LinksDaoImpl extends DaoImpl<Links, Integer> implements LinksDao {

	public List<Links> findByContentId(List<Integer> contentIdList,
			Integer siteId) {
		StringBuffer sql = new StringBuffer();
		List<Object> params = new ArrayList<Object>();
		sql.append(" SELECT I.* ");
		sql.append("   FROM T_FRIENDLINK I ");
		sql.append("  WHERE EXISTS ");
		sql.append("           (SELECT 1 ");
		sql.append("              FROM T_CONTROL C ");
		sql.append("             WHERE    C.TABLE_KEY_ID = I.LINK_ID ");
		if (!CollectionUtils.isEmpty(contentIdList)) {
			sql.append("                   AND C.CTRL_ID IN ( ");
			int i = 0;
			for (Integer id : contentIdList) {
				if (i != 0) {
					sql.append(" , ");
				}
				sql.append(" ? ");
				params.add(id);
				i++;
			}
			sql.append(" )");
		}
		if (siteId != null) {
			sql.append(" AND C.SITE_ID = ?");
			params.add(siteId);
		}
		sql.append(" )");
		return findBySql(sql.toString(), false, params.toArray());
	}
	
	public List<Links> findByParentChannelId(Integer siteId,Integer parentChannelId,Integer state, Boolean isSatatic) {
		StringBuffer hql=new StringBuffer();
		List<Object> params = new ArrayList<Object>();
		hql.append(" from Links l where exists(select 1 from  Content  c where l.id=c.entityId ") ;
		if(siteId!=null) {
			hql.append(" and  c.site.id=? ");
			params.add(siteId);
		}
		if(state!=null) {
			hql.append(" and c.status=?");
			params.add(state);
		}
		if(isSatatic!=null) {
			hql.append(" and ((c.status=? and c.staticStatus=?) or (c.status=? and c.staticStatus=?)) ");
			params.add(ElianUtils.CONTENT_STATUS_3);
			params.add(ElianUtils.STATIC_STATUS_1);
			params.add(ElianUtils.CONTENT_STATUS_2);
			params.add(ElianUtils.STATIC_STATUS_2);	
		}
		if(parentChannelId!=null){
			hql.append("   and exists(select 1 from Channel ch where c.channel.id=ch.id and (ch.id=? or ch.parentId=? )");
			params.add(parentChannelId);
			params.add(parentChannelId);
		}
		hql.append(" ) and l.disable=true ");
		return findByHql(hql.toString(), false, params.toArray());
	}
}
