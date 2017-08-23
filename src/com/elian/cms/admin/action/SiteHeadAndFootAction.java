package com.elian.cms.admin.action;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import com.elian.cms.admin.model.Site;
import com.elian.cms.admin.model.SiteInclude;
import com.elian.cms.admin.model.Template;
import com.elian.cms.admin.service.SiteIncludeService;
import com.elian.cms.admin.service.TemplateService;
import com.elian.cms.syst.action.BaseAction;
import com.elian.cms.syst.model.Pagination;
import com.elian.cms.syst.util.ApplicationUtils;
import com.elian.cms.syst.util.ElianCodes;
import com.elian.cms.syst.util.FreemarkerCodes;
import com.elian.cms.syst.util.StringUtils;
import com.elian.cms.syst.util.ValidateUtils;

/**
 * 网站的头部和尾部
 * 
 * @author thy
 * 
 */
@Component
@Scope("prototype")
public class SiteHeadAndFootAction extends BaseAction {
	private static final long serialVersionUID = 4502579967911956011L;
	private boolean isEdit = false;
	private SiteIncludeService siteIncludeService;
	private TemplateService templateService;
	private Pagination<SiteInclude> pagination = new Pagination<SiteInclude>();
	private SiteInclude siteInclude = new SiteInclude();
	private Site site = ApplicationUtils.getSite();
	private Map<String, String> templateName = null;
	private final String STATIC="static";
	public String list() {
		if (checkExist()) { // 如果存在
			if (checkTempChange()) { // 如果更改了模板
				siteIncludeService.deleteBySiteId(site.getId());
				init();
			}
		}
		else { // 如果不存在
			init(); // 初始化操作
		}
		siteIncludeService.getPageBySiteId(pagination, site.getId());
		return LIST;
	}

	private void init() {
		File[] subFiles = getSubFiles();
		if (subFiles == null)
			return;
		List<SiteInclude> list = new ArrayList<SiteInclude>();
		for (int i = 0; i < subFiles.length; i++) {
			File f = subFiles[i];
			SiteInclude siteInclude = new SiteInclude();
			siteInclude.setSiteId(site.getId());
			siteInclude.setTempId(site.getTemplate().getId());
			siteInclude.setFileName(f.getName());
			siteInclude.setContent("");
			list.add(siteInclude);
		}
		siteIncludeService.save(list);
	}

	private String getFileContent(File file) {
		if (!file.isFile())
			return null;
		StringBuffer buf = new StringBuffer();
		try {
			BufferedReader br = new BufferedReader(new InputStreamReader(
					new FileInputStream(file), "UTF-8"));
			String line = null;
			while ((line = br.readLine()) != null) {
				buf.append(line);
			}
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		return buf.toString();
	}

	private File[] getSubFiles() {
		if(site.getTemplate()==null) return null;
		String parentPath = ApplicationUtils.getRealPath(null,
				FreemarkerCodes.TEMPLATE_URL);
		String fileName = site.getTemplate().getFileName()+ FreemarkerCodes.INCLUDE_TEMPLATE_URL;
		File parentFile = new File(parentPath + ElianCodes.SPRIT + fileName);
		if (parentFile == null)return null;
		return parentFile.listFiles();
	}

	private boolean checkTempChange() {
		return siteInclude.getTempId().equals(site.getTemplate().getId()) ? false
				: true;
	}

	private boolean checkExist() {
		siteInclude = siteIncludeService.getBySiteId(site.getId());
		return siteInclude == null ? false : true;
	}

	public String tempFileInit() {
		siteInclude = siteIncludeService.get(siteInclude.getId());
		File[] subFiles = getSubFiles();
		if (subFiles == null)
			return list();
		for (int i = 0; i < subFiles.length; i++) {
			if (subFiles[i].getName().equals(siteInclude.getFileName())) {
				siteInclude.setContent(getFileContent(subFiles[i]));
			}
		}
		return list();
	}

	public String edit() {
		if (null != siteInclude.getId())
			siteInclude = siteIncludeService.get(siteInclude.getId());
		return EDIT;
	}

	public String save() {
		if (null != siteInclude.getId()) {
			siteInclude.setSiteId(site.getId());
			siteInclude.setTempId(site.getTemplate().getId());
			siteIncludeService.save(siteInclude);
		}
		return SAVE;
	}

	public String staticInCludeFile(){
		if (null != siteInclude.getId()) {
			siteInclude.setSiteId(site.getId());
			siteInclude.setTempId(site.getTemplate().getId());
			siteIncludeService.save(siteInclude);
		}
		return STATIC;
	}
	
	public void validateSave() {
		if (!StringUtils.isBlank(siteInclude.getContent())) {
			if (ValidateUtils.isLengOut(siteInclude.getContent(), 7500)) {
				this.addFieldError("siteInclude.content", "内容长度超出范围！");
			}
		}
	}

	public Map<String, String> getTemplateName() {
		if (CollectionUtils.isEmpty(templateName)) {
			List<Template> templateList = templateService
					.findByTempIDAndParentFileName(site.getTemplate().getId(),
							FreemarkerCodes.INCLUDE_TEMPLATE_URL.replace(
									ElianCodes.SPRIT.charAt(0), ' '), false);
			if (CollectionUtils.isEmpty(templateList))
				return null;
			templateName = new HashMap<String, String>();
			for (Template t : templateList)
				templateName.put(t.getFileName(), t.getTempName());
		}
		return templateName;
	}

	public boolean isEdit() {
		return isEdit;
	}

	public void setEdit(boolean isEdit) {
		this.isEdit = isEdit;
	}

	public SiteInclude getSiteInclude() {
		return siteInclude;
	}

	public void setSiteInclude(SiteInclude siteInclude) {
		this.siteInclude = siteInclude;
	}

	public Pagination<SiteInclude> getPagination() {
		return pagination;
	}

	public void setPagination(Pagination<SiteInclude> pagination) {
		this.pagination = pagination;
	}

	@Resource
	public void setSiteIncludeService(SiteIncludeService siteIncludeService) {
		this.siteIncludeService = siteIncludeService;
	}

	@Resource
	public void setTemplateService(TemplateService templateService) {
		this.templateService = templateService;
	}
	
}
