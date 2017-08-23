package com.elian.cms.admin.dao.impl;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.springframework.stereotype.Component;

import com.elian.cms.admin.dao.DeptTypeDao;
import com.elian.cms.admin.model.DeptType;
import com.elian.cms.syst.dao.impl.DaoImpl;
import com.elian.cms.syst.model.Pagination;
import com.elian.cms.syst.util.StringUtils;

@Component("deptTypeDao")
public class DeptTypeDaoImpl extends DaoImpl<DeptType, Integer> implements
		DeptTypeDao {

	public List<DeptType> findByAll(String type, boolean disable,
			Pagination<DeptType> p) {
		StringBuffer hql = new StringBuffer();
		List<Object> params = new ArrayList<Object>();
		hql.append(" from DeptType dt where 1=1");
		if(StringUtils.isNotBlank(type)){
			hql.append(" and dt.type = ? ");
			params.add(type);
		}
		if (disable) {
			hql.append(" and dt.disable = true ");
		}
		hql.append(" order by dt.typeSort asc,dt.id desc");
		if (p != null) {
			p.setAlias("dt");
			return pageByHql(hql.toString(), true, p, params.toArray());
		}
		else
			return findByHql(hql.toString(), true, params.toArray());
	}
	
	/* 
	 * 包含本身查父节点
	 */
	public List<DeptType> findParentBySubId(Integer deptTypeId) {
		if (deptTypeId == null)
			return null;
		StringBuffer sql = new StringBuffer();
		List<Object> params = new ArrayList<Object>();
		/*sql.append(" SELECT A.* FROM T_TYPE_PARENT A ");
		sql.append(" START WITH A.TYPE_PARENT_ID = ? AND A.IS_DISABLE = 1 ");
		sql.append(" CONNECT BY PRIOR A.PARENT_ID = A.TYPE_PARENT_ID ");*/
		sql.append(" WITH FINDSUBBYPARENTID(TYPE_PARENT_ID, PARENT_ID)   ");
		sql.append(" AS (  ");
		sql.append("      SELECT C.TYPE_PARENT_ID, C.PARENT_ID   ");
		sql.append("        FROM T_TYPE_PARENT C   ");
		sql.append("       WHERE C.TYPE_PARENT_ID = ? AND C.IS_DISABLE = 1 ");
		sql.append("      UNION ALL   ");
		sql.append("      SELECT T.TYPE_PARENT_ID, T.PARENT_ID   ");
		sql.append("        FROM T_TYPE_PARENT T   ");
		sql.append("  INNER JOIN FINDSUBBYPARENTID F ON F.PARENT_ID = T.TYPE_PARENT_ID   ");
		sql.append("  )  ");
		sql.append("  SELECT C.* FROM T_TYPE_PARENT C   ");
		sql.append("             WHERE C.TYPE_PARENT_ID IN (SELECT TYPE_PARENT_ID FROM FINDSUBBYPARENTID)   ");
		params.add(deptTypeId);
		return findBySql(sql.toString(), false, params.toArray());
	}

	public DeptType getByName(String name) {
		if(StringUtils.isBlank(name))
			return null;
		
		StringBuffer hql = new StringBuffer();
		List<Object> params = new ArrayList<Object>();
		hql.append(" from DeptType dt where 1=1");
		if(StringUtils.isNotBlank(name)){
			hql.append(" and dt.typeName = ? ");
			params.add(name);
		}		
		return (findByHql(hql.toString(), false, params.toArray()).get(0));				
	}

	private String getSqlFunction(Set<Integer> departmentSet){
		StringBuffer sql=new StringBuffer();
		sql.append("with findParentBySubId(type_parent_id,parent_id)");
		sql.append("	as(");
		sql.append("		select type_parent_id,parent_id from T_TYPE_PARENT where type_parent_id in");
		sql.append("		(");
		Iterator<Integer> iterator=departmentSet.iterator();
		while (iterator.hasNext()) {
			Integer integer = (Integer) iterator.next();			
			if(iterator.hasNext())  sql.append(integer+",");
			else sql.append(integer);
		}		
		sql.append("			)");
		sql.append("		union all select t.type_parent_id ,t.parent_id from T_TYPE_PARENT as t");
		sql.append("			inner join findParentBySubId as c on t.type_parent_id in( c.parent_id )");
		sql.append("		)");
		return sql.toString();
	}
	
	public List<DeptType> findParentBySubsId(Set<Integer> departmentSet) {
		String function=getSqlFunction(departmentSet);
		StringBuffer sql=new StringBuffer();
		List<Object> params=new ArrayList<Object>();
		sql.append(" select * from T_TYPE_PARENT where type_parent_id in ");
		sql.append("	(");
		sql.append("		select type_parent_id from findParentBySubId ");
		sql.append("	)");
		return findByRecursionSql(sql.toString(), false,function,params.toArray());
	}
	
	public List<DeptType> findInstrumentType(String classType,Integer compId){
		List<Object> params=new ArrayList<Object>();
		String hql=" from DeptType t where t.disable=? ";
		params.add(true);
		if(StringUtils.isNotBlank(classType)) {
			hql+=" and t.type=? ";
			params.add(classType);
		}
		if(compId!=null) {
			hql+="  and exists( select 1 from Instrument i where t.id=i.type.id and i.company.id=? )";
			params.add(compId);
		}
		return findByHql(hql, false,params.toArray());
	}
	
}
