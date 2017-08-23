package com.elian.cms.admin.dao;

import java.util.List;

import com.elian.cms.admin.model.Template;
import com.elian.cms.syst.dao.Dao;
import com.elian.cms.syst.model.Pagination;

public interface TemplateDao extends Dao<Template, Integer> {
	public List<Template> findByAll(Integer parentId, boolean isDisable,
			Pagination<Template> p);
	
	public List<Template> findSubByParentId(List<Integer> parentIdList);
	
	public List<Template> findByGradeId(Integer gradeId);
	
	public List<Template> findByParentAndFolder(Integer parentId,
			String folderName, boolean isDisable);

	public List<Template> findByTempIDAndParentFileName(Integer parentId,
			String folderName, boolean isDisable);
}
