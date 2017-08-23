package com.elian.cms.admin.dao.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import com.elian.cms.admin.dao.InstrumentDao;
import com.elian.cms.admin.model.Instrument;
import com.elian.cms.syst.dao.impl.DaoImpl;
import com.elian.cms.syst.model.Pagination;
import com.elian.cms.syst.util.ElianUtils;

@Component("instrumentDao")
public class InstrumentDaoImpl extends DaoImpl<Instrument, Integer> implements
		InstrumentDao {

	public List<Instrument> findByAll(Integer companyId,Integer typeId,
			Pagination<Instrument> p) {
		StringBuffer hql = new StringBuffer();
		List<Object> params = new ArrayList<Object>();
		hql.append(" from Instrument ins where 1=1");
		if (companyId != null && companyId > 0) {
			hql.append(" and ins.company.id = ? ");
			params.add(companyId);
		}
		if(typeId!=null) {
			hql.append(" and ins.type.id=? ");
			params.add(typeId);
		}
		
		hql.append(" order by ins.sort,ins.createTime,ins.id desc");
		if (p != null) {
			p.setAlias("ins");
			return pageByHql(hql.toString(), false, p,params.toArray());
		}
		else {
			return findByHql(hql.toString(), false,params.toArray());
		}
	}
	
	public Instrument findStaticSpageData(Integer siteId,Integer channelId,Boolean hasImg) {
		StringBuffer sql=new StringBuffer();
		List<Object> params=new ArrayList<Object>();
		sql.append(" SELECT TOP 1 * FROM INSTRUMENT I WHERE EXISTS ");
		sql.append(" (SELECT T_CONTROL C WHERE C.TABLE_KEY_ID=I.INSTR_ID ");
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
				sql.append(" AND (I.INSTR_IMG IS NOT NULL  AND I.INSTR_IMG !='') ");
			}
			else {
				sql.append(" AND (I.INSTR_IMG IS NULL OR I.INSTR_IMG ='')");
			}
		}
		return findBySqlFirst(sql.toString(), false, params.toArray());
		}
	
	public List<Instrument>  findStaticImgList(Integer siteId, Integer channelId,int size){
		StringBuffer sql=new StringBuffer();
		List<Object> params=new ArrayList<Object>();
		sql.append(" SELECT TOP ");
		sql.append(size);
		sql.append("  * FROM INSTRUMENT I LEFT JOIN T_CONTROL C ON ");
	    sql.append(" I.INSTR_ID=C.TABLE_KEY_ID WHERE ");
	    sql.append(" I.INSTR_IMG IS NOT NULL  AND I.INSTR_IMG !='' " );
	    sql.append(" AND  EXISTS(SELECT 1 FROM T_CONTROL C1 WHERE C1.TABLE_KEY_ID=I.INSTR_ID  ");
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
		strBuffer.append("  AND( (C.STATUS = ? AND C.STATIC_STATUS =?) OR (C.STATUS=? AND C.STATIC_STATUS=?) )");
		params.add(ElianUtils.CONTENT_STATUS_3);
		params.add(ElianUtils.STATIC_STATUS_1);
		params.add(ElianUtils.CONTENT_STATUS_2);
		params.add(ElianUtils.STATIC_STATUS_2);	
	}
	
	private void addOrderSql(StringBuffer strBuffer,String tableName){
		strBuffer.append(" order by "+tableName+".top_most desc, "+tableName+".ctrl_sort, "+tableName+".ctrl_id ");
	}
}
