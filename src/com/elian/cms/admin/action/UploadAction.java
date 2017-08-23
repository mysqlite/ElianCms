package com.elian.cms.admin.action;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Date;

import javax.annotation.Resource;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.elian.cms.admin.model.Template;
import com.elian.cms.admin.service.TemplateService;
import com.elian.cms.syst.action.BaseAction;
import com.elian.cms.syst.util.ApplicationUtils;
import com.elian.cms.syst.util.ElianCodes;
import com.elian.cms.syst.util.FileUtils;
import com.elian.cms.syst.util.FreemarkerCodes;
import com.elian.cms.syst.util.StringUtils;

@Component
@Scope("prototype")
public class UploadAction extends BaseAction {
	private static final long serialVersionUID = 3263461459135266949L;

	/** 文件 */
	private File upload;
	/** 文件名称 */
	private String fileName;
	/** 文件类型 */
	private String uploadContentType;
	/** 文件上传去处路径 */
	private String uploadToUrl;
	/** 文件上传成功跳转Action */
	private String url;
	
	private Integer tempId = Integer.valueOf(0);

	private TemplateService templateService;

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getUploadToUrl() {
		return uploadToUrl;
	}

	public void setUploadToUrl(String uploadToUrl) {
		this.uploadToUrl = uploadToUrl;
	}

	public String list() {
		return LIST;
	}

	public String getUploadFileName() {
		return fileName;
	}

	public void setUploadFileName(String fileName) {
		this.fileName = fileName;
	}

	public File getUpload() {
		return upload;
	}

	public void setUpload(File upload) {
		this.upload = upload;
	}
	
	public Integer getTempId() {
		return tempId;
	}

	public void setTempId(Integer tempId) {
		this.tempId = tempId;
	}

	public void setUploadContentType(String contentType) {
		this.uploadContentType = contentType;
	}

	public String getUploadContentType() {
		return this.uploadContentType;
	}

	public String upload() {
		InputStream in;
		try {
			in = new FileInputStream(upload);
			uploadToUrl = ApplicationUtils.getRealPath(null,
					FreemarkerCodes.TEMPLATE_URL)+ uploadToUrl;
			File file = new File(uploadToUrl);
			if (!file.exists()) {
				file.mkdir();
			}
			OutputStream out = new FileOutputStream(uploadToUrl
					+ fileName);
			byte size[] = new byte[1024 * 1024];
			int count = 0;
			while ((count = in.read(size)) > 0) {
				out.write(size, 0, count);
			}
			out.close();
			in.close();
			if (StringUtils.isNotBlank(fileName)) {
				String realPath = uploadToUrl + fileName;
				if (fileName.endsWith(ElianCodes.FILE_RAR)) {
					FileUtils.rarToFile(realPath, uploadToUrl);
					FileUtils.delFolder(realPath);
				}
				if (fileName.endsWith(ElianCodes.FILE_ZIP)) {
					FileUtils.zipToFile(realPath, uploadToUrl);
					FileUtils.delFolder(realPath);
				}
				if(realPath.endsWith(ElianCodes.SUFFIX_HTML))
					createTemplateData(realPath);
				else
					createTemplateData(realPath.substring(0, realPath.length() - 4));
			}
		}
		catch (Exception e) {
			e.printStackTrace();
			return FAIL;
		}
		return SUCCESS;
	}

	public void createTemplateData(String realPath) {
		File file = new File(realPath);
		if (!file.exists()) {
			return;
		}
		Integer parentId = createAndSaveTemplate(file, file.isDirectory(), tempId,
				false);
		createSubsTemplate(file, parentId);
	}

	private void createSubsTemplate(File file, Integer parentId) {
		if (file.isDirectory())
			for (File f : file.listFiles()) {
				createAndSaveTemplate(f, f.isDirectory(), parentId, true);
			}
	}

	private Integer createAndSaveTemplate(File file, boolean isDir,
			Integer parentId, boolean isGenerateSub) {
		Template t = new Template();
		t.setParentId(parentId);
		t.setTempName(file.getName());
		t.setCreater(ApplicationUtils.getUser().getRealName());
		t.setCreateTime(new Date());
		t.setDisable(true);
		t.setFileName(file.getName());
		t.setTempSort(99);
		t.setFile(!isDir);
		templateService.save(t);
		if (isGenerateSub)
			createSubsTemplate(file, t.getId());
		return t.getId();
	}

	@Resource
	public void setTemplateService(TemplateService templateService) {
		this.templateService = templateService;
	}
}
