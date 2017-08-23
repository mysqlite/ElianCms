package com.elian.cms.admin.dao.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import com.elian.cms.admin.dao.TemplateSetDao;
import com.elian.cms.admin.model.TemplateSet;
import com.elian.cms.syst.dao.impl.DaoImpl;
import com.elian.cms.syst.model.Pagination;

@Component("templateSetDao")
public class TemplateSetDaoImpl extends DaoImpl<TemplateSet, Integer> implements
	TemplateSetDao {

	public List<TemplateSet> findByAll(Integer tempId,
			Pagination<TemplateSet> pagination) {
		StringBuffer sql = new StringBuffer("select * from T_TEMPLATE_set t where 1=1 ");
		List<Object> params=new ArrayList<Object>();
		if(null!=tempId){
			sql.append("	and t.temp_id =?");
			params.add(tempId);
		}
		sql.append(" order by area_id ");
		if(null != pagination)
			return pageBySql(sql.toString(), false, pagination, params.toArray());
		return findBySql(sql.toString(), false, params.toArray());
	}	
	
//	private String getSqlFunction(Integer tempId){
//		StringBuffer sql=new StringBuffer();
//		sql.append("with findSubByParentId(temp_id,parent_id)");
//		sql.append("as(");
//		sql.append("	select temp_id,parent_id from T_TEMPLATE where temp_id =").append(tempId);
//		sql.append("		union all select t.temp_id,t.parent_id from T_TEMPLATE as t");
//		sql.append("			inner join findSubByParentId as c on t.parent_id = c.temp_id");
//		sql.append(")");
//		return sql.toString();
//	}

	public TemplateSet getByTempIdAndAreaId(Integer tempId, Integer areaId) {
		if(null==tempId || null==areaId) return null;
		String sql="select * from T_TEMPLATE_set where temp_id=? and area_id=?";
		return findBySqlFirst(sql, false, tempId,areaId);
	}

	public List<TemplateSet> getTempParentAreas(Integer tempId) {
		return findByHql("from TemplateSet t where t.hasSubArea=true and t.tempId=?", false,tempId);
	}
}
