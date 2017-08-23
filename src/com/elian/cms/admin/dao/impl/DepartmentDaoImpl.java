package com.elian.cms.admin.dao.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import com.elian.cms.admin.dao.DepartmentDao;
import com.elian.cms.admin.model.Channel;
import com.elian.cms.admin.model.Department;
import com.elian.cms.admin.model.Hospital;
import com.elian.cms.admin.model.Site;
import com.elian.cms.syst.dao.impl.DaoImpl;
import com.elian.cms.syst.model.Pagination;
import com.elian.cms.syst.util.ElianUtils;
import com.elian.cms.syst.util.SysXmlUtils;

@Component("departmentDao")
public class DepartmentDaoImpl extends DaoImpl<Department, Integer> implements
		DepartmentDao {
	public List<Department> findByAll(Integer hospId, String typeName) {
		StringBuffer hql = new StringBuffer("from Department d where 1=1 ");
		List<Object> params = new ArrayList<Object>();
		if (hospId != null) {
			hql.append("  and d.hospital.id=?");
			params.add(hospId);
		}
		if (typeName != null) {
			hql.append("  and d.typeId in ( select dt.id from DeptType dt where dt.typeName = ?)");
			params.add(typeName);
		}
		hql.append("  and d.disable=true");
		// hql.append("  and exists ( select 1 from Content c where c.entityId = d.id and c.actionName = '")
		// .append(SysXmlUtils.getContentActionName("contentType",
		// "department").getValue())
		// .append("' and c.status = 3 )");
		return findByHql(hql.toString(), false, params.toArray());
	}

	public List<Department> findByContentId(List<Integer> contentIdList,
			Integer siteId) {
		StringBuffer sql = new StringBuffer();
		List<Object> params = new ArrayList<Object>();
		sql.append(" SELECT H.* ");
		sql.append("   FROM T_HOSP_DEPARTMENT H ");
		sql.append("  WHERE EXISTS ");
		sql.append("           (SELECT 1 ");
		sql.append("              FROM T_CONTROL C ");
		sql.append("             WHERE     C.TABLE_KEY_ID = H.DEPA_ID ");
		sql.append("               AND     C.ACTION_NAME = '"
				+ SysXmlUtils.getContentActionName("contentType", "department")
						.getValue() + "' ");
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
	public List<Department> findPageByHosp(Pagination<Department> p,Integer hospId){
		String hql=" from Department d where 1=1 ";
		List<Object> param=new ArrayList<Object>();
		if(hospId!=null) {
			hql+="  and d.hospital.id=?  ";
			param.add(hospId);
		}
		hql+="  order by   d.typeId , d.deptSort asc,d.deptName  asc";
		if(null!=p){
			p.setAlias("d");
			return pageByHql(hql, false, p, param.toArray());
		}
		return findByHql(hql, false, param.toArray());
	}

	public List<Department> getData(Site site, Hospital hosp, Channel channel,
			Integer size,Integer status, Boolean isStatic,Boolean hasImg) {
		if(null==size) size=1;		
		if(size==0)	return null;		
		StringBuffer sql=new StringBuffer();
		List<Object> param=new ArrayList<Object>();
		sql.append("select top("+size+") * from T_HOSP_DEPARTMENT where depa_id in");
		sql.append("	(");
		sql.append("		select table_key_id from t_control c where 1=1 ");
		if(null != site){
			sql.append("		and site_id=? ");
			param.add(site.getId());
		}
		if(null != isStatic){
			addStaticSql(sql,param);
		}
		if(null != channel){
			sql.append("		and channel_Id=? ");
			param.add(channel.getId());
		}
		sql.append("	)");	
		if(null != hosp){
			sql.append("		and hosp_id=? ");
			param.add(hosp.getId());
		}
		if(null != hasImg){
			if(hasImg)
				sql.append("		and depa_img is not null and  depa_img!=''");
			else
				sql.append("		and (depa_img is null or  depa_img='')");
		}
		return findBySql(sql.toString(), false, param.toArray());	
	}

	public Department findStaticSpageData(Integer siteId, Integer channelId,
			Boolean hasImg) {
		StringBuffer sql=new StringBuffer();
		List<Object> param=new ArrayList<Object>();
		sql.append("select top 1 * from T_HOSP_DEPARTMENT where depa_id in");
		sql.append("	(");
		sql.append("		select table_key_id from t_control c where 1=1 ");
		addStaticSql(sql,param);
		if(null != siteId){
			sql.append("		and site_id=? ");
			param.add(siteId);
		}
		if(null != channelId){
			sql.append("		and channel_id=? ");
			param.add(channelId);
		}
		sql.append("	)");
		if(null != hasImg){
			if(hasImg)
				sql.append("		and (depa_img is not null and  depa_img!='')");
			else
				sql.append("		and (depa_img is null or  depa_img='')");
		}
		return findBySqlFirst(sql.toString(), false, param.toArray());
	}
	
	private void addStaticSql(StringBuffer strBuffer,List<Object> params ){
		strBuffer.append("  and( (c.status = ? and c.static_status =?) or (c.status=? and c.static_status=?) )");
		params.add(ElianUtils.CONTENT_STATUS_3);
		params.add(ElianUtils.STATIC_STATUS_1);
		params.add(ElianUtils.CONTENT_STATUS_2);
		params.add(ElianUtils.STATIC_STATUS_2);	
	}
	
//	private void addOrderSql(StringBuffer strBuffer,String tableName){
//		strBuffer.append(" order by "+tableName+".top_most desc, "+tableName+".ctrl_sort, "+tableName+".ctrl_id ");
//	}
}
