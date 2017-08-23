package com.elian.cms.admin.dao.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import com.elian.cms.admin.dao.MedicineDao;
import com.elian.cms.admin.model.Medicine;
import com.elian.cms.syst.dao.impl.DaoImpl;
import com.elian.cms.syst.model.Pagination;
import com.elian.cms.syst.util.ElianUtils;

@Component("medicineDao")
public class MedicineDaoImpl extends DaoImpl<Medicine, Integer> implements
		MedicineDao {
	public List<Medicine> findByAll(Integer companyId,Integer typeId, Pagination<Medicine> p) {
		StringBuffer hql = new StringBuffer();
		List<Object> params = new ArrayList<Object>();
		hql.append(" from Medicine med where 1=1");
		if (companyId != null && companyId > 0) {
			hql.append(" and med.company.id = ? ");
			params.add(companyId);
		}
		if(typeId!=null&&typeId!=0) {
			hql.append(" and med.type.id=? ");
			params.add(typeId);
		}
		hql.append(" order by med.sort,med.createTime,med.id desc");
		if (p != null) {
			p.setAlias("med");
			return pageByHql(hql.toString(), false, p,params.toArray());
		}
		else {
			return findByHql(hql.toString(), false,params.toArray());
		}
	}
	
	public Medicine findStaticSpageData(Integer siteId,Integer channelId,Boolean hasImg) {
		StringBuffer sql=new StringBuffer();
		List<Object> params=new ArrayList<Object>();
		sql.append(" SELECT TOP 1 * FROM MEDICINE M WHERE EXISTS ");
		sql.append(" (SELECT T_CONTROL C WHERE C.TABLE_KEY_ID=M.MEDICINE_ID ");
		addStaticSql(sql, params);
		if(siteId!=null) {
			sql.append(" AND C.SITE_ID=? ");
			params.add(siteId);
		}
		if(channelId!=null) {
			sql.append(" AND C.CHANNEL_ID=? ");
			params.add(channelId);
		}
		sql.append(")");
		if(null!=hasImg) {
			if(hasImg) {
				sql.append(" AND (M.MEDICINE_IMG IS NOT NULL  AND M.MEDICINE_IMG !='') ");
			}
			else {
				sql.append(" AND (M.MEDICINE_IMG IS NULL OR M.MEDICINE_IMG ='')");
			}
		}
		return findBySqlFirst(sql.toString(), false, params.toArray());
		}
	
	public List<Medicine>  findStaticImgList(Integer siteId, Integer channelId,int size){
		StringBuffer sql=new StringBuffer();
		List<Object> params=new ArrayList<Object>();
		sql.append(" SELECT TOP ");
		sql.append(size);
		sql.append("  * FROM MEDICINE M LEFT JOIN T_CONTROL C ON ");
	    sql.append(" M.MEDICINE_ID=C.TABLE_KEY_ID WHERE ");
	    sql.append(" MEDICINE_IMG IS NOT NULL  AND M.MEDICINE_IMG !='' " );
	    sql.append(" AND  EXISTS(SELECT 1 FROM T_CONTROL C1 WHERE C1.TABLE_KEY_ID=M.MEDICINE_ID  ");
	    if(null!=siteId) {
	    	sql.append(" AND C1.SITE_ID=?");
	    	params.add(siteId);
	    }
	    if(null!=channelId) {
	    	sql.append(" AND C1.CHANNEL_ID=?");
	    	params.add(channelId);
	    }
	    addStaticSql(sql, params);
	    sql.append(")");
	    if(null!=channelId) {
	    	sql.append("  AND C.CHANNEL_ID=? ");
	    	params.add(channelId);
	    }
	    addOrderSql(sql, "C");
		return  findBySql(sql.toString(), false, params.toArray());
	}
	
	
	
	private void addStaticSql(StringBuffer strBuffer,List<Object> params ){
		strBuffer.append("  AND( (C.STATUS = ? AND C.STATIC_STATUS =?) " +
				"OR (C.STATUS=? AND C.STATIC_STATUS=?) )");
		params.add(ElianUtils.CONTENT_STATUS_3);
		params.add(ElianUtils.STATIC_STATUS_1);
		params.add(ElianUtils.CONTENT_STATUS_2);
		params.add(ElianUtils.STATIC_STATUS_2);	
	}
	
	private void addOrderSql(StringBuffer strBuffer,String tableName){
		strBuffer.append(" order by "+tableName+".top_most desc, "+tableName+".ctrl_sort, "+tableName+".ctrl_id ");
	}
}
