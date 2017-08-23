package com.elian.cms.admin.dao.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import com.elian.cms.admin.dao.BiddingDao;
import com.elian.cms.admin.model.Bidding;
import com.elian.cms.syst.dao.impl.DaoImpl;
import com.elian.cms.syst.util.ElianUtils;

@Component("biddingDao")
public class BiddingDaoImpl extends DaoImpl<Bidding, Integer> implements
		BiddingDao {

	public List<Bidding> findByContentId(List<Integer> contentIdList,
			Integer siteId) {
		StringBuffer sql = new StringBuffer();
		List<Object> params = new ArrayList<Object>();
		sql.append(" SELECT I.* ");
		sql.append("   FROM T_BIDDING I ");
		sql.append("  WHERE EXISTS ");
		sql.append("           (SELECT 1 ");
		sql.append("              FROM T_CONTROL C ");
		sql.append("             WHERE    C.TABLE_KEY_ID = I.BID_ID ");
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

	public List<Bidding> getTopList(Integer siteId, Integer channnelParentId,
			int size) {
		StringBuffer sql = new StringBuffer();
		List<Object> params = new ArrayList<Object>();
		sql.append("select TOP "+size+" * from T_BIDDING B LEFT JOIN T_CONTROL C on B.BID_ID=c.table_key_id  WHERE 1=1  ");
		sql.append(" AND C.site_id=? and C.action_name='bidding_c' ");
		addStaticSql(sql, params);
		sql.append(" and C.channel_id in");
		sql.append("	(select channel_id from t_channel where site_id=? and parent_id=?)");
		addOrderSql(sql, "c");
		if (siteId != null) {
			params.add(siteId);
			params.add(siteId);
		}

		if (channnelParentId != null) {
			params.add(channnelParentId);
		}

		return findBySql(sql.toString(), false, params.toArray());
	}

	public Bidding findStaticSpageData(Integer siteId, Integer channelId) {
		StringBuffer sql = new StringBuffer();
		List<Object> params = new ArrayList<Object>();

		sql.append("select * from T_BIDDING B LEFT JOIN T_CONTROL C on B.BID_ID=c.table_key_id  WHERE 1=1 ");
		addStaticSql(sql, params);
		if (siteId != null) {
			sql.append(" and C.site_id=? ");
			params.add(siteId);
		}
		if (channelId != null) {
			sql.append(" and C.channel_id=? ");
			params.add(channelId);
		}
		addOrderSql(sql, "c");
		return findBySqlFirst(sql.toString(), false, params.toArray());
	}

	private void addStaticSql(StringBuffer strBuffer, List<Object> params) {
		strBuffer.append("  and( (c.status = ? and c.static_status =?) or (c.status=? and c.static_status=?) )");
		params.add(ElianUtils.CONTENT_STATUS_3);
		params.add(ElianUtils.STATIC_STATUS_1);
		params.add(ElianUtils.CONTENT_STATUS_2);
		params.add(ElianUtils.STATIC_STATUS_2);
	}

	private void addOrderSql(StringBuffer strBuffer, String tableName) {
		strBuffer.append(" order by " + tableName + ".top_most desc, "
				+ tableName + ".ctrl_sort, " + tableName + ".ctrl_id desc");
	}
}
