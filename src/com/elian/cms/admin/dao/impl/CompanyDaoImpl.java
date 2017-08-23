package com.elian.cms.admin.dao.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import com.elian.cms.admin.dao.CompanyDao;
import com.elian.cms.admin.model.Company;
import com.elian.cms.syst.dao.impl.DaoImpl;
import com.elian.cms.syst.model.Pagination;
import com.elian.cms.syst.util.ElianUtils;
import com.elian.cms.syst.util.StringUtils;
import com.elian.cms.syst.util.ValidateUtils;

@Component("companyDao")
public class CompanyDaoImpl extends DaoImpl<Company, Integer> implements
		CompanyDao {

	public List<Company> getByAreaId(String function,
			Pagination<Company> pagination,String compName) {
		StringBuffer sql=new StringBuffer();
		sql.append("SELECT * FROM COMPANY C WHERE EXISTS(");
		sql.append("	 SELECT 1 FROM FINDSUBBYPARENTID F WHERE C.AREA_ID=F.AREA_CODE");
		sql.append("	 ) ");
		if(StringUtils.isNotBlank(compName)) {
			compName=ValidateUtils.isSql(compName);
			sql.append("AND (C.FULL_NAME LIKE '%").append(compName);
			sql.append("%' OR SHORT_NAME LIKE '%").append(compName).append("%')");
		}
		if(pagination!=null)
			pageByRecursionSql(sql.toString(), false, pagination, function);
		return findByRecursionSql(sql.toString(), false, function);
	}

	public Company findStaticSpageData(Integer siteId, Integer channelId,
			Boolean hasImg) {
		StringBuffer sql=new StringBuffer();
		List<Object> params = new ArrayList<Object>();

		sql.append("select TOP 1 * from COMPANY H where H.COMPANY_ID in( select table_key_id from t_control c where 1=1 ");
		addStaticSql(sql, params);
		if (siteId!=null) {
			sql.append(" and c.site_id=? ");	
			params.add(siteId);
		}		
		if(channelId!=null){
			sql.append(" and c.channel_id=? ");
			params.add(channelId);
		}		
		sql.append(")");
		if(hasImg!=null){
			if(hasImg)
				sql.append(" and ( (H.enterp_img IS NOT NULL AND H.enterp_img !='') or (H.map_img is not null and H.map_img<>'') ) ");
			else
				sql.append(" and (H.enterp_img IS NULL OR H.enterp_img =''  ) ");
		}
		return findBySqlFirst(sql.toString(), false, params.toArray());
	}

	private void addStaticSql(StringBuffer strBuffer,List<Object> params){
		strBuffer.append("  and( (c.status = ? and c.static_status =?) or (c.status=? and c.static_status=?) )");
		params.add(ElianUtils.CONTENT_STATUS_3);
		params.add(ElianUtils.STATIC_STATUS_1);
		params.add(ElianUtils.CONTENT_STATUS_2);
		params.add(ElianUtils.STATIC_STATUS_2);	
	}

	public List<Company> findStaticImgList(Integer siteId, Integer channelId,
			int size) {
		StringBuffer sql = new StringBuffer();
		List<Object> params = new ArrayList<Object>();
		sql.append(" SELECT TOP "+ size);
		sql.append(		" H.* ");
		sql.append("   FROM company H left join T_CONTROL C  on H.company_id=c.table_key_id ");
		sql.append("  WHERE H.enterp_img IS NOT NULL AND H.enterp_img !='' AND H.company_id IN");
		sql.append("           (SELECT TABLE_KEY_ID ");
		sql.append("              FROM T_CONTROL C ");
		sql.append("              WHERE 1=1 ");
		if (channelId!=null) {
			sql.append("               AND C.CHANNEL_ID=?");	
			params.add(channelId);
		}
		if (siteId != null) {
			sql.append(" 				AND C.SITE_ID = ?");
			params.add(siteId);
		}	
		addStaticSql(sql, params);
		sql.append(")");
		if (channelId!=null) {
			sql.append("               AND C.CHANNEL_ID=? ");	
			params.add(channelId);
		}
		addOrderSql(sql, "c");
		return findBySql(sql.toString(), false, params.toArray());
	}
	
	private void addOrderSql(StringBuffer strBuffer,String tableName){
		strBuffer.append(" order by "+tableName+".top_most desc, "+tableName+".ctrl_sort, "+tableName+".ctrl_id ");
	}

	public List<Company> findByAraeName(String areaName, int status,
			Pagination<Company> pagination) {
				return null;
		
	}

	public List<Company> search(String criteria, Integer areaId,
			Pagination<Company> pagination) {
		String function=null;
		StringBuffer sql=new StringBuffer();
		sql.append("select * from company H where 1=1 ");
		if(areaId !=null){
			 function=new AreaDaoImpl().findChirdByParentFunction(areaId);
			 sql.append("AND H.AREA_ID IN (SELECT AREA_CODE FROM FINDSUBBYPARENTID)");
		}
		if(StringUtils.isNotBlank(criteria))
			sql.append(criteria);
		//sql.append(" order by H.IS_REG desc,H.hosp_rank,H.HOSP_TYPE desc");
		if(pagination!=null)
			return pageByRecursionSql(sql.toString(), false, pagination,function);
		return	findByRecursionSql(sql.toString(),false, function);
	}
}
