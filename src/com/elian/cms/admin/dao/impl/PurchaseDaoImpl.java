package com.elian.cms.admin.dao.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import com.elian.cms.admin.dao.PurchaseDao;
import com.elian.cms.admin.model.Purchase;
import com.elian.cms.syst.dao.impl.DaoImpl;
import com.elian.cms.syst.util.ElianUtils;

@Component("purchaseDaoImpl")
public class PurchaseDaoImpl extends DaoImpl<Purchase, Integer> implements PurchaseDao {

	public List<Purchase> findByContentId(List<Integer> contentIdList,
			Integer siteId) {
		StringBuffer sql = new StringBuffer();
		List<Object> params = new ArrayList<Object>();
		sql.append(" SELECT I.* ");
		sql.append("   FROM T_PURCHASE I ");
		sql.append("  WHERE EXISTS ");
		sql.append("           (SELECT 1 ");
		sql.append("              FROM T_CONTROL C ");
		sql.append("             WHERE    C.TABLE_KEY_ID = I.PUR_ID ");
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

	public Purchase findStaticSpageData(Integer siteId, Integer channelId) {
		StringBuffer sql = new StringBuffer();
		List<Object> params = new ArrayList<Object>();
		
		sql.append("select * from T_PURCHASE P LEFT JOIN T_CONTROL C on P.PUR_ID=c.table_key_id  WHERE 1=1 ");
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
