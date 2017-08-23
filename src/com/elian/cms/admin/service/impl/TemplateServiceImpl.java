package com.elian.cms.admin.service.impl;

import java.util.List;
import java.util.ListIterator;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import com.elian.cms.admin.dao.TemplateDao;
import com.elian.cms.admin.model.Template;
import com.elian.cms.admin.service.TemplateService;
import com.elian.cms.syst.model.Pagination;
import com.elian.cms.syst.service.JdbcService;
import com.elian.cms.syst.service.impl.ServiceImpl;
import com.elian.cms.syst.util.ElianCodes;
import com.elian.cms.syst.util.SpringUtils;

@Component("templateService")
public class TemplateServiceImpl extends
		ServiceImpl<TemplateDao, Template, Integer> implements TemplateService {

	public List<Template> findByAll(Integer parentId, boolean isDisable,
			Pagination<Template> p) {
		return dao.findByAll(parentId, isDisable, p);
	}
	
	public List<Template> findSubByParentId(List<Integer> parentIdList) {
		return dao.findSubByParentId(parentIdList);
	}
	
	public List<Template> findByGradeId(Integer gradeId) {
		return dao.findByGradeId(gradeId);
	}
	
	public List<Template> findByParentAndFolder(Integer parentId,
			String folderName, boolean isDisable) {
		return dao.findByParentAndFolder(parentId, folderName, isDisable);
	}

	/**
	 * 获取模板相对路径
	 */
	public String getTempUrl(Integer tempId) {
		JdbcService jdbcService = SpringUtils.getBean("jdbcService");
		if (tempId != null && tempId > 0) {
			StringBuffer sql = new StringBuffer();
			sql.append(" WITH FINDSUBBYPARENTID (TEMP_ID, PARENT_ID, TEMP_FILE_NAME) ");
			sql.append("      AS (SELECT C.TEMP_ID, C.PARENT_ID, C.TEMP_FILE_NAME ");
			sql.append("            FROM T_TEMPLATE C ");
			sql.append("           WHERE C.TEMP_ID = ? ");
			sql.append("          UNION ALL ");
			sql.append("          SELECT T.TEMP_ID, T.PARENT_ID, T.TEMP_FILE_NAME ");
			sql.append("            FROM    T_TEMPLATE T ");
			sql.append("                 INNER JOIN ");
			sql.append("                    FINDSUBBYPARENTID F ");
			sql.append("                 ON F.PARENT_ID = T.TEMP_ID) ");
			sql.append(" SELECT TEMP_FILE_NAME ");
			sql.append("   FROM FINDSUBBYPARENTID ");
			List<Map<String, Object>> list = jdbcService.findSqlMapQuery(sql
					.toString(), tempId);
			if (!CollectionUtils.isEmpty(list)) {
				ListIterator<Map<String, Object>> ites = list.listIterator(list
						.size());
				StringBuilder sb = new StringBuilder(ElianCodes.SPRIT);
				while (ites.hasPrevious()) {
					Map<String, Object> map = ites.previous();
					sb.append((String) map.get("TEMP_FILE_NAME"));
					sb.append(ElianCodes.SPRIT);
				}
				return sb.toString();
			}
		}
		return ElianCodes.SPRIT;
	}

	@Resource
	public void setDao(TemplateDao dao) {
		this.dao = dao;
	}

	public List<Template> findByTempIDAndParentFileName(Integer parentId,
			String folderName, boolean isDisable) {
		return dao. findByTempIDAndParentFileName(parentId,folderName,isDisable);
	}
}
