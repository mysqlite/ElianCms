package com.elian.cms.admin.action;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import com.elian.cms.admin.model.Template;
import com.elian.cms.admin.service.TemplateService;
import com.elian.cms.syst.action.BaseAction;
import com.elian.cms.syst.model.TrueFalseItem;
import com.elian.cms.syst.util.ApplicationUtils;
import com.elian.cms.syst.util.ElianCodes;
import com.elian.cms.syst.util.ElianUtils;
import com.elian.cms.syst.util.FileUtils;
import com.elian.cms.syst.util.FreemarkerCodes;
import com.elian.cms.syst.util.StringUtils;
import com.elian.cms.syst.util.TemplateUtils;
import com.elian.cms.syst.util.ValidateUtils;

/**
 * 模板功能
 * 
 * @author Joe
 * 
 */
@Component
@Scope("prototype")
public class TemplateAction extends BaseAction {
	private static final long serialVersionUID = 8533687776537657796L;

	private Integer tempId = Integer.valueOf(0);
	
	private Integer id = Integer.valueOf(0);
	private boolean isEdit = false;
	private Template template;
	private String url;
	
	private List<Template> templateList;

	private TemplateService templateService;
	
	public String list() {
		templateList = templateService.findByAll(tempId, false, null);
		return LIST;
	}

	public String edit() throws IOException {
		if (isEdit && id > 0) {
			template = templateService.get(id);
			if (template.isFile()) {
				readFileContent(template);
			}
		}
		else {
			template = createTemplate();
		}
		template.setNewFileName(template.getFileName());
		return EDIT;
	}
	
	/**
	 * 读取文件内容
	 */
	private void readFileContent(Template template) {
		String realFileName = ApplicationUtils.getRealPath(null,
				FreemarkerCodes.TEMPLATE_URL)
				+ url + template.getFileName();
		File file = new File(realFileName);
		if (file.exists())
			try {
				template.setFileContent(FileUtils.readFileToString(file,
						ElianCodes.UNICODE_UTF8));
			}
			catch (IOException e) {
				e.printStackTrace();
			}
	}
	
	private Template createTemplate() {
		Template t = new Template();
		t.setParentId(tempId);
		t.setCreater(ApplicationUtils.getUser().getRealName());
		t.setCreateTime(new Date());
		t.setDisable(true);
		t.setTempSort(99);
		return t;
	}
	
	public void validateSave() {
		if (ValidateUtils.isBlank(template.getTempName()))
			this.addFieldError("template.tempName", "模板名称不能为空");
		else if (ValidateUtils.isBlank(template.getNewFileName()))
			this.addFieldError("template.fileName", "文件名称不能为空");
		else if(template.isFile() && ValidateUtils.isBlank(template.getFileContent())){
			this.addFieldError("template.fileContent", "文件内容不能为空");
		}
	}

	public String save() {
		prepareFile(template);
		template.setFileName(template.getNewFileName());
		templateService.save(template);
		return SAVE;
	}
	
	private void prepareFile(Template template) {
		File file = null;
		if (isEdit) {
			file = replaceFile(template);
		}
		else {
			file = createFile(template);
		}
		if (!file.isDirectory()) {
			try {
				FileUtils.writeStringToFile(file, template.getFileContent(),
						ElianCodes.UNICODE_UTF8);
			}
			catch (IOException e) {
			}
		}
	}
	
	private File replaceFile(Template template) {
		String tempUrl = ApplicationUtils.getRealPath(null,
				FreemarkerCodes.TEMPLATE_URL)
				+ url;
		String newFileName = tempUrl + template.getNewFileName();
		File newFile = new File(newFileName);
		if (!template.getFileName().equals(template.getNewFileName())) {
			String oldFileName = tempUrl + template.getFileName();
			File file = new File(oldFileName);
			if (file.isDirectory()) {
				if (file.exists()) {
					file.delete();
				}
				if (!newFile.exists()) {
					newFile.mkdirs();
				}
			}
			else {
				try {
					FileUtils.replaceFile(file, newFile);
				}
				catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return newFile;
	}
	
	private File createFile(Template template) {
		String realFileName = ApplicationUtils.getRealPath(null,
				FreemarkerCodes.TEMPLATE_URL)
				+ url + template.getNewFileName();
		File file = new File(realFileName);
		// 如果是文件夹
		if (!template.isFile() && !file.exists()) {
			file.mkdirs();
		}
		else if (template.isFile()) {
			try {
				file.createNewFile();
			}
			catch (IOException e) {
				e.printStackTrace();
			}
		}
		return file;
	}

	public void delete() {
		List<Integer> idList = ApplicationUtils.getAjaxIds();
		if (CollectionUtils.isEmpty(idList))
			return;
		List<Template> tempList = templateService.get(idList);
		if (CollectionUtils.isEmpty(tempList))
			return;
		ListIterator<Template> ites = tempList.listIterator(tempList.size());
		String tempUrl = ApplicationUtils.getRealPath(null,
				FreemarkerCodes.TEMPLATE_URL)
				+ url;
		while (ites.hasPrevious()) {
			Template template = ites.previous();
			
			//删除路径下的所有文件及文件夹
			FileUtils.delFolder(tempUrl+template.getFileName());
			
			//删除数据库数据
			deleteSubList(template);
		}
	}
	
	/**
	 * 删除所有的子节点
	 */
	private void deleteSubList(Template t) {
		List<Integer> tempIdList = new ArrayList<Integer>(1);
		tempIdList.add(t.getId());
		List<Template> tempList = templateService.findSubByParentId(tempIdList);
		ListIterator<Template> ites = tempList.listIterator(tempList.size());
		while (ites.hasPrevious()) {
			Template template = ites.previous();
			// 数据库数据
			templateService.delete(template);
		}
	}

	public void disable() {
		Integer id = ApplicationUtils.getAjaxId();
		Boolean disable = ApplicationUtils.getAjaxDisable();
		if (id != null && disable != null) {
			template = templateService.get(id);
			if (template == null)
				return;
			template.setDisable(!disable);
			templateService.save(template);
		}
	}

	public void sort() {
		Integer id = ApplicationUtils.getAjaxId();
		Integer sort = ApplicationUtils.getAjaxSort();
		if (id != null && sort != null) {
			template = templateService.get(id);
			if (template == null)
				return;
			template.setTempSort(sort);
			templateService.save(template);
		}
	}
	
	public void resolve() {
		String templateName = ApplicationUtils.getRequest().getParameter(
				"templateName");
		TemplateUtils.resolveTemplate(ApplicationUtils.getRealPath(null,
				FreemarkerCodes.TEMPLATE_URL)
				+ templateName);
	}

	/*
	 * 获取模板树结构
	 */
	public void tree() {
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		addRootNode(list);

		List<Template> tempList = templateService.findByAll(null, false, null);
		if (tempList != null && tempList.size() > 0) {
			Map<String, Object> map = null;
			for (Template t : tempList) {
				map = new LinkedHashMap<String, Object>();
				map.put("ID", t.getId());
				map.put("NAME", t.getTempName());
				map.put("PID", t.getParentId());
				map.put("OPEN", ElianUtils.FALSE_STR);
				map.put("DIR", !t.isFile());
				list.add(map);
			}
		}

		ApplicationUtils.sendJsonArray(list);
	}
//	/*
//	 * 获取模板树结构
//	 */
//	public void usingTempTree() {
//		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
//		List<Integer> ids=new ArrayList<Integer>();
//		ids.add(986);
//		List<Template> tempList = templateService.findSubByParentId(ids);
//		if (tempList != null && tempList.size() > 0) {
//			Map<String, Object> map = null;
//			for (Template t : tempList) {
//				map = new LinkedHashMap<String, Object>();
//				map.put("ID", t.getId());
//				map.put("NAME", t.getTempName());
//				map.put("PID", t.getParentId());
//				map.put("OPEN", ElianUtils.FALSE_STR);
//				map.put("DIR", !t.isFile());
//				list.add(map);
//			}
//		}		
//		ApplicationUtils.sendJsonArray(list);
//	}

	
	/*
	 * 为模板树结构添加根节点
	 */
	public void addRootNode(List<Map<String, Object>> list) {
		Map<String, Object> map = new LinkedHashMap<String, Object>();
		map.put("ID", 0);
		map.put("NAME", ElianUtils.ROOT_URL);
		map.put("PID", -1);
		map.put("OPEN", ElianUtils.TRUE_STR);
		map.put("DIR", ElianUtils.TRUE_STR);
		list.add(map);
	}

	public TrueFalseItem getDisableItem() {
		return ElianUtils.getDisableItem();
	}

	public Integer getTempId() {
		return tempId;
	}

	public void setTempId(Integer tempId) {
		this.tempId = tempId;
	}
	
	public boolean isEdit() {
		return isEdit;
	}

	public void setEdit(boolean isEdit) {
		this.isEdit = isEdit;
	}
	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getUrl() {
		if (StringUtils.isBlank(url)) {
			return tempId != null ? templateService.getTempUrl(tempId)
					: ElianCodes.SPRIT;
		}
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public Template getTemplate() {
		return template;
	}

	public void setTemplate(Template template) {
		this.template = template;
	}

	public List<Template> getTemplateList() {
		return templateList;
	}

	public int getListSize() {
		return templateList == null ? 0 : templateList.size();
	}

	@Resource
	public void setTemplateService(TemplateService templateService) {
		this.templateService = templateService;
	}

}
