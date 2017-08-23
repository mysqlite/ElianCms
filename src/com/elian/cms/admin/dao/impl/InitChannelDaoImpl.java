package com.elian.cms.admin.dao.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import com.elian.cms.admin.dao.InitChannelDao;
import com.elian.cms.admin.model.InitChannel;
import com.elian.cms.syst.dao.impl.DaoImpl;
import com.elian.cms.syst.model.Pagination;
import com.elian.cms.syst.util.ElianUtils;

@Component("initChannelDao")
public class InitChannelDaoImpl extends DaoImpl<InitChannel, Integer> implements
		InitChannelDao {

	public List<InitChannel> findByAll(Integer tempId, Integer parentId,
			Pagination<InitChannel> p) {
		StringBuffer hql=new StringBuffer("from InitChannel ic where 1=1 ");
		List<Object> params=new ArrayList<Object>(3);
		
		if(tempId!=null){
			hql.append(" and ic.templateId=? ");
			params.add(tempId);
		}
		if(parentId!=null){
			hql.append(" and ic.parentId=? ");
			params.add(parentId);
		}
		hql.append(" order by ic.sort,ic.createTime");		
		if(p!=null){
			return pageByHql(hql.toString(), false, p, params.toArray());
		}
		return findByHql(hql.toString(), false, params.toArray());
	}

	public List<InitChannel> findAllParent(Integer initTempId, Boolean isDisable) {
		String hql="from InitChannel ic where ic.templateId=? and ic.channelType=? and ic.disable=?";
		return findByHql(hql, false, initTempId,ElianUtils.CHANNEL_PARENT,isDisable);
	}

	public List<InitChannel> find(Integer initTempId, String channelType,
			Integer modelId, String contentType, boolean isDisable) {
		String hql="from InitChannel ic where ic.templateId=? and ic.channelType=? and ic.model.id=? and ic.contentType=? and ic.disable=?";
		return findByHql(hql, false, initTempId,channelType,modelId,contentType,isDisable);
	}

	public List<InitChannel> findSubByParentId(List<Integer> idList,
			Integer tempId) {
		if (tempId == null || idList.isEmpty())
			return null;
		StringBuffer sql = new StringBuffer();
		List<Object> params = new ArrayList<Object>();
		sql.append(" WITH FINDSUBBYPARENTID(CHANNEL_ID, PARENT_ID)  ");
		sql.append(" AS ( ");
		sql.append("     SELECT C.CHANNEL_ID, C.PARENT_ID  ");
		sql.append("       FROM T_INITIAL_CHANNEL C  ");
		sql.append("      WHERE C.CHANNEL_ID IN(  ");

		int i = 0;
		for (Integer id : idList) {
			if (i != 0) {
				sql.append(" , ");
			}
			sql.append(" ? ");
			params.add(id);
			i++;
		}
		sql.append(" )");

		sql.append("        AND C.TEMP_ID = ?   ");
		params.add(tempId);
		sql.append("     UNION ALL  ");
		sql.append("     SELECT T.CHANNEL_ID, T.PARENT_ID  ");
		sql.append("       FROM T_INITIAL_CHANNEL T  ");
		sql.append(" INNER JOIN FINDSUBBYPARENTID F ON T.PARENT_ID = F.CHANNEL_ID  ");
		sql.append(" ) ");
		sql.append(" SELECT C.* FROM T_INITIAL_CHANNEL C  ");
		sql.append(" 		   WHERE C.CHANNEL_ID IN (SELECT CHANNEL_ID FROM FINDSUBBYPARENTID)  ");
		return findBySql(sql.toString(), false, params.toArray());
	}
}
