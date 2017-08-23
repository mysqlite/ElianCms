package com.elian.cms.admin.dao.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import com.elian.cms.admin.dao.InvitationDao;
import com.elian.cms.admin.model.Invitation;
import com.elian.cms.syst.dao.impl.DaoImpl;
import com.elian.cms.syst.util.ElianUtils;

@Component("invitationDao")
public class InvitationDaoImpl extends DaoImpl<Invitation, Integer> implements
		InvitationDao {

	public List<Invitation> findByContentId(List<Integer> contentIdList,
			Integer siteId) {
		StringBuffer sql = new StringBuffer();
		List<Object> params = new ArrayList<Object>();
		sql.append(" SELECT I.* ");
		sql.append("   FROM T_INVITATION I ");
		sql.append("  WHERE EXISTS ");
		sql.append("           (SELECT 1 ");
		sql.append("              FROM T_CONTROL C ");
		sql.append("             WHERE    C.TABLE_KEY_ID = I.INVIT_ID");
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

	public Invitation getByrid(Integer rid) {
		String hql = " from Invitation where rid=?";
		return findByHql(hql, false, rid).get(0);
	}

	public List<Invitation> getTopList(Integer siteId,
			Integer channnelParentId, int size) {
		StringBuffer sql = new StringBuffer();
		List<Object> params = new ArrayList<Object>();
		sql.append("select * from T_INVITATION where invit_id in");
		sql
				.append("(select top "
						+ size
						+ " table_key_id from T_CONTROL c where site_id=? and action_name='invitation_c' ");
		addStaticSql(sql, params);
		sql.append(" 							and channel_id in");
		sql
				.append("(select channel_id from t_channel where site_id=? and parent_id=?)");
		sql.append(" order by top_most desc,ctrl_sort,ctrl_id");
		sql.append(")");
		if (siteId != null) {
			params.add(siteId);
		}

		if (channnelParentId != null) {
			params.add(channnelParentId);
		}

		return findBySql(sql.toString(), false, params.toArray());
	}

	public Invitation findStaticSpageData(Integer siteId, Integer channelId) {
		StringBuffer sql = new StringBuffer();
		List<Object> params = new ArrayList<Object>();

		sql
				.append("select * from T_INVITATION where INVIT_ID in( select table_key_id from t_control c where ");
		addStaticSql(sql, params);
		if (siteId != null) {
			sql.append(" and site_id=? ");
			params.add(siteId);
		}
		if (channelId != null) {
			sql.append(" and channel_id=? ");
			params.add(channelId);
		}
		sql.append(")");
		return findBySqlFirst(sql.toString(), false, params.toArray());
	}

	private void addStaticSql(StringBuffer strBuffer, List<Object> params) {
		strBuffer
				.append("  and( (c.status = ? and c.static_status =?) or (c.status=? and c.static_status=?) )");
		params.add(ElianUtils.CONTENT_STATUS_3);
		params.add(ElianUtils.STATIC_STATUS_1);
		params.add(ElianUtils.CONTENT_STATUS_2);
		params.add(ElianUtils.STATIC_STATUS_2);
	}

//	private void addOrderSql(StringBuffer strBuffer, String tableName) {
//		strBuffer.append(" order by " + tableName + ".top_most desc, "
//				+ tableName + ".ctrl_sort, " + tableName + ".ctrl_id ");
//	}
}
