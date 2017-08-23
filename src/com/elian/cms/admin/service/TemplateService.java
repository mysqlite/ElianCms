package com.elian.cms.admin.service;

import java.util.List;

import com.elian.cms.admin.dao.TemplateDao;
import com.elian.cms.admin.model.Template;
import com.elian.cms.syst.model.Pagination;
import com.elian.cms.syst.service.Service;

public interface TemplateService extends
		Service<TemplateDao, Template, Integer> {
//	public List<Template> findByAll(Integer parentId,
//			Pagination<Template> pagination);

	public List<Template> findByAll(Integer parentId, boolean isDisable,
			Pagination<Template> p);
	
	public List<Template> findSubByParentId(List<Integer> parentIdList);
	
	public List<Template> findByGradeId(Integer gradeId);
	
	public List<Template> findByParentAndFolder(Integer parentId,
			String folderName, boolean isDisable);

	public List<Template> findByTempIDAndParentFileName(Integer parentId,
			String folderName, boolean isDisable);
	/**
	 * 获取模板相对路径
	 */
	public String getTempUrl(Integer parentId);	
}
