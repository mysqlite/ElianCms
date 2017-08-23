package com.elian.cms.admin.action;

import java.io.File;
import java.io.IOException;

import javax.annotation.Resource;

import net.sf.json.JSONObject;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.struts2.dispatcher.multipart.MultiPartRequestWrapper;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.elian.cms.admin.model.Ftp;
import com.elian.cms.admin.service.FileUploadService;
import com.elian.cms.common.image.ImageUtils;
import com.elian.cms.common.upload.FileType;
import com.elian.cms.syst.action.BaseAction;
import com.elian.cms.syst.util.ApplicationUtils;
import com.elian.cms.syst.util.DoubleUtlis;
import com.elian.cms.syst.util.EhcacheUtils;
import com.elian.cms.syst.util.ElianCodes;
import com.elian.cms.syst.util.ElianUtils;
import com.elian.cms.syst.util.StringUtils;
/**
 * @author Gechuanyi
 * @version 1.0 文件上传处理类
 */
@Component
@Scope("prototype")
public class FileUploadAction extends BaseAction {
	private static final long serialVersionUID = -9179704696855962510L;
	private String ext="";
	private File file;
	private boolean mark;
	private boolean controls;
	private boolean files;
	private boolean video;
	private boolean flash;
	private String upPrevFile;
	private String contentAction;
	private String divId;
	private String n;
	private FileUploadService fileUploadService;
	
	/**
	 * 将文件上传到临时文件夹
	 */
	public String uploadFileToTemp() {
		MultiPartRequestWrapper wrapper=(MultiPartRequestWrapper)ApplicationUtils.getRequest();
		String fileName=wrapper.getFileNames("site_file")[0];
		file=wrapper.getFiles("site_file")[0];
		
		if((ApplicationUtils.getSite().getSiteSize()-ApplicationUtils.getSite().getSiteUsedSize())<=0) 
			return putError("    对不起，您的站点免费使用空间不足;\\n免费空间:"+(ApplicationUtils.getSite().getSiteSize()/(1024 * 1024))
					+"MB;已用空间为:"+(DoubleUtlis.divLong(ApplicationUtils.getSite().getSiteUsedSize(), 1024 * 1024, 2))
					+"MB;\\n    详情请联系:\\n电话:0757-82137888,\\nQQ:876292931");
		
		if((ApplicationUtils.getSite().getSiteSize()-(ApplicationUtils.getSite().getSiteUsedSize()+file.length()))<=0) {
			return putError("    对不起，您的站点免费使用空间不足;\\n免费空间:"+(ApplicationUtils.getSite().getSiteSize()/(1024 * 1024))
					+"MB;已用空间为:"+(DoubleUtlis.divLong(ApplicationUtils.getSite().getSiteUsedSize(), 1024 * 1024, 2))
					+"MB;\\n本次上传文件大小加上使用量超过使用\\n    详情请联系:\\n电话:0757-82137888,\\nQQ:876292931");
		}
		
		if(StringUtils.isBlank(fileName)||file==null) 
			return putError("请选择文件");
		
		try {ext=FileType.getFileByFile(FileUtils.openInputStream(file));}
		catch (IOException e) {return putError("上传失败！,请稍后再试");}
		Object[] object=null;
		if(FileType.isImage(file)&&!files&&!video) 
			return saveImgToTemp(object, fileName);
		else if ((FileType.WORD.contains(ext)||FileType.PDF.contains(ext)||FileType.SWF.contains(ext))&&files&&!video&&!flash) 
			return saveFiletoTemp(object, fileName);
		else if(FileType.SWF.contains(ext)&&flash)
			return saveFiletoTemp(object, fileName);
		else if(FileType.VIDEO.contains(ext)&&files&&video)
			return saveFiletoTemp(object, fileName);
		else if(controls&&files)//自定义控件文件上传
			return putError("您上传本系统不支持的类型,\\n支持【.doc|.docx|.xls|.xlsx|.ppt|.pptx|.pdf|.swf】上传。\\n若您上传的是以上类型文件，请联系QQ:876292931\\n请重新选择上传文件!");
		else if(controls&&!files)//控件图片上传
			return putError("您上传本系统不支持的类型,\\n支持【.jpg|.png|.gif|.bmp】上传。\\n若您上传的是以上类型文件，请联系QQ:876292931\\n请重新选择上传文件!\\n注:{图片上传不支持颜色非RGB类型的图片}");
		else if (!controls&&files&&!flash)//编辑器文件上传
			return putError("您上传本系统不支持的类型,支持【.acc|.mp3|.mp4|.avi|.mid】文件上传。若您上传的是以上类型文件，请联系QQ:876292931;请重新选择上传文件!");
		else if(!controls&&!files)//编辑器图片上传
			return putError("您上传本系统不支持的类型,支持【.jpg|.png|.gif|.bmp】上传。若您上传的是以上类型文件，请联系QQ:876292931;请重新选择上传文件!注:{图片上传不支持颜色非RGB类型的图片}");
		else	if(flash)
			return putError("您上传本系统不支持的类型,支持【.swf】上传。若您上传的是以上类型文件，请联系QQ:876292931;请重新选择上传文件!");
		else	
			return putError("未知错误！");
	}
	
	private String saveImgToTemp(Object object[],String fileName) {
		if(file.length()>ElianCodes.MAX_SIZE) 
			return putError("图片大小超过限制"+ElianUtils.getMaxSize()+"MB;\\n当前大小:"+(file.length()/(1024*1024))+"MB;");
		object=fileUploadService.saveFileToFtpTemp(file, fileName, upPrevFile, ext, contentAction, controls, mark,false);
		if(object[3]!=null) 
			return putError(object[3].toString());
		else {
			if(controls) {
				ApplicationUtils.getRequest().setAttribute("uploadImgInfo", object);
				return "todo";
			}
			else {
				JSONObject obj = new JSONObject();
				obj.put("error", 0);
				obj.put("url", EhcacheUtils.getCacheFtp(EhcacheUtils.IMG_FTP).getFtpUrl()+ object[1].toString());
				obj.put("width", ImageUtils.getImgWidth(file));
				obj.put("height", ImageUtils.getImgHeight(file));
				file.delete();
				ApplicationUtils.getRequest().setAttribute("jsonresult", obj);
				return SUCCESS;
			}
		}
	}
	
	private String saveFiletoTemp(Object object[],String fileName) {
		if(file.length()>ElianCodes.FILE_MAX_SIZE) 
			return putError("文件大小超过限制"+ElianUtils.getMaxSize(ElianCodes.FILE_MAX_SIZE)+"MB;\\n当前大小:"+(file.length()/(1024*1024))+"MB;");
		ext=FilenameUtils.getExtension(fileName);
		object=fileUploadService.saveFileToFtpTemp(file, fileName, upPrevFile, ext, contentAction, controls,false,video);
		if(controls) {
			ApplicationUtils.getRequest().setAttribute("uploadImgInfo", object);
			return "todo";
		}
		else {
			Ftp ftp=video?EhcacheUtils.getCacheFtp(EhcacheUtils.VIDEO_FTP):EhcacheUtils.getCacheFtp(EhcacheUtils.FILE_FTP);
			JSONObject obj = new JSONObject();
			obj.put("error", 0);
			obj.put("url", ftp.getFtpUrl()+ object[1].toString());
			ApplicationUtils.getRequest().setAttribute("jsonresult", obj);
			file.delete();
			return SUCCESS;
		}
	}
	
	private String putError(String msg) {
		getError("errMsg", msg,controls);
		if(controls)return "todo";
		else return ERROR;
	}
	
	private void getError(String key,String message,boolean controls) {
		file.delete();
		if(controls) {
			ApplicationUtils.getRequest().setAttribute(key, message);
		}
		else {
		JSONObject obj = new JSONObject();
		obj.put("error", 1);
		obj.put("message", message);
		ApplicationUtils.getRequest().setAttribute("jsonresult", obj);
		ApplicationUtils.getRequest().setAttribute("errMsg", message);
		}
	}

	public void setMark(boolean mark) {
		this.mark = mark;
	}
	
	public void setControls(boolean controls) {
		this.controls = controls;
	}

	public void setUpPrevFile(String upPrevFile) {
		this.upPrevFile = upPrevFile;
	}

	public void setContentAction(String contentAction) {
		this.contentAction = contentAction;
	}

	@Resource
	public void setFileUploadService(FileUploadService fileUploadService) {
		this.fileUploadService = fileUploadService;
	}

	public String getDivId() {
		return divId;
	}

	public void setDivId(String divId) {
		this.divId = divId;
	}

	public String getN() {
		return n;
	}

	public void setN(String n) {
		this.n = n;
	}

	public boolean isFiles() {
		return files;
	}

	public void setFiles(boolean files) {
		this.files = files;
	}

	public boolean isVideo() {
		return video;
	}

	public void setVideo(boolean video) {
		this.video = video;
	}

	public boolean isFlash() {
		return flash;
	}

	public void setFlash(boolean flash) {
		this.flash = flash;
	}	
}
