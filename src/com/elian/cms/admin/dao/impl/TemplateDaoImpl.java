package com.elian.cms.admin.dao.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import com.elian.cms.admin.dao.TemplateDao;
import com.elian.cms.admin.model.Template;
import com.elian.cms.syst.dao.impl.DaoImpl;
import com.elian.cms.syst.model.Pagination;
import com.elian.cms.syst.util.StringUtils;

@Component("templateDao")
public class TemplateDaoImpl extends DaoImpl<Template, Integer> implements
		TemplateDao {
	public List<Template> findByAll(Integer parentId, boolean isDisable,
			Pagination<Template> p) {
		StringBuffer hql = new StringBuffer("from Template t where 1=1 ");
		List<Object> params = new ArrayList<Object>();
		if (parentId != null) {
			hql.append("  and t.parentId=?");
			params.add(parentId);
		}
		if (isDisable) {
			hql.append("  and t.disable=true");
		}
		hql.append(" order by t.tempSort,t.createTime");
		if (p != null) {
			p.setAlias("t");
			return pageByHql(hql.toString(), false, p, params.toArray());
		}
		else
			return findByHql(hql.toString(), false, params.toArray());
	}
	
	public List<Template> findSubByParentId(List<Integer> parentIdList){
		if (parentIdList.isEmpty())
			return null;
		StringBuffer sql = new StringBuffer();
		List<Object> params = new ArrayList<Object>();
		sql.append(" WITH FINDSUBBYPARENTID(TEMP_ID, PARENT_ID)  ");
		sql.append(" AS ( ");
		sql.append("     SELECT C.TEMP_ID, C.PARENT_ID  ");
		sql.append("       FROM T_TEMPLATE C  ");
		sql.append("      WHERE C.TEMP_ID IN(  ");

		int i = 0;
		for (Integer id : parentIdList) {
			if (i != 0) {
				sql.append(" , ");
			}
			sql.append(" ? ");
			params.add(id);
			i++;
		}
		sql.append(" )");

		sql.append("     UNION ALL  ");
		sql.append("     SELECT T.TEMP_ID, T.PARENT_ID  ");
		sql.append("       FROM T_TEMPLATE T  ");
		sql.append(" INNER JOIN FINDSUBBYPARENTID F ON T.PARENT_ID = F.TEMP_ID  ");
		sql.append(" ) ");
		sql.append(" SELECT C.* FROM T_TEMPLATE C  ");
		sql.append(" 		   WHERE C.TEMP_ID IN (SELECT TEMP_ID FROM FINDSUBBYPARENTID)  ");
		return findBySql(sql.toString(), false, params.toArray());
	}
	
	public List<Template> findByGradeId(Integer gradeId) {
		StringBuffer hql = new StringBuffer("from Template t where 1=1 and t.disable=true");
		List<Object> params = new ArrayList<Object>();
		if (gradeId != null) {
			hql.append("  and exists(select 1 from GradeTemplate gt where gt.grade.id = ? and t.id = gt.template.id)");
			params.add(gradeId);
		}
		return findByHql(hql.toString(), false, params.toArray());
	}
	
	public List<Template> findByParentAndFolder(Integer parentId,
			String folderName, boolean isDisable) {
		if (parentId == null)
			return null;
		StringBuffer sql = new StringBuffer();
		List<Object> params = new ArrayList<Object>();
		sql.append(" SELECT TT.* ");
		sql.append("   FROM T_TEMPLATE TT ");
		sql.append("  WHERE EXISTS ");
		sql.append("           (SELECT 1 ");
		sql.append("              FROM T_TEMPLATE T ");
		sql.append("             WHERE     1=1 ");
		if (parentId != null) {
			sql.append("             	   AND T.PARENT_ID = ? ");
			params.add(parentId);
		}
		if (StringUtils.isNotBlank(folderName)) {
			sql.append("                   AND TEMP_FILE_NAME = ? ");
			params.add(folderName);
		}
		sql.append("                   AND TT.PARENT_ID = T.TEMP_ID) ");
		if (isDisable) {
			sql.append("  AND TT.IS_DISABLE=1");
		}
		return findBySql(sql.toString(), false, params.toArray());
	}

	public List<Template> findByTempIDAndParentFileName(Integer parentId,
			String folderName, boolean isDisable) {
		if (parentId == null)
			return null;
		StringBuffer sql = new StringBuffer();
		List<Object> params = new ArrayList<Object>();
		sql.append(" SELECT TT.* ");
		sql.append("   FROM T_TEMPLATE TT ");
		sql.append("  WHERE TT.PARENT_ID IN(");
		sql.append("		 SELECT TT1.TEMP_ID  FROM T_TEMPLATE TT1 WHERE 1=1 ");
		if(parentId!=null){
			sql.append("	AND TT1.PARENT_ID=? ");
			params.add(parentId);
		}
		if(folderName!=null){
			sql.append("	AND TT1.TEMP_FILE_NAME=? ");
			params.add(folderName.trim());
		}		
		sql.append("	)");
		if (isDisable) {
			sql.append("  AND TT.IS_DISABLE=1");
		}
		return findBySql(sql.toString(), false, params.toArray());
	}
}
