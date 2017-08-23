package com.elian.cms.admin.action;

import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.net.ftp.FTPClient;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.elian.cms.admin.model.Images;
import com.elian.cms.admin.service.SiteFileService;
import com.elian.cms.syst.action.BaseAction;
import com.elian.cms.syst.model.Pagination;
import com.elian.cms.syst.util.ApplicationUtils;
import com.elian.cms.syst.util.EhcacheUtils;
import com.elian.cms.syst.util.FtpToolUtils;
import com.elian.cms.syst.util.SearchParamUtils;

@Component
@Scope("prototype")
public class ImagesAction extends BaseAction {
	private static final long serialVersionUID = 1980591184853040557L;
	private Pagination<Images> pagination = new Pagination<Images>(SearchParamUtils.getImagesConditionMap());
	private SiteFileService siteFileService;
	
	public String list() {
		if(ApplicationUtils.isMainStation())
			siteFileService.findByPage(pagination,null,null,null);
		else 
			siteFileService.findByPage(pagination, ApplicationUtils.getSite().getId(),null,null);
		return LIST;
	}
	
	public String checkFileSize() {
		List<Images> images=siteFileService.findByPage(null, null, null, null);
		FTPClient client=FtpToolUtils.getClient(EhcacheUtils.getCacheFtp(EhcacheUtils.IMG_FTP));
		long totalSize=0;
		int i=0;
		System.out.println(images.size());
		for (Images img : images) {
			img.setFileSize(FtpToolUtils.getFileSize(img.getImagesPath(),client,false));
			totalSize=totalSize+img.getFileSize();
			siteFileService.saveImg(img);
			i++;
			System.out.println(i);
		}
		System.out.println("目前共存储文件大小"+(totalSize)+"byte");
		return LIST;
	}
	public String delete() {
		List<Integer> idList = ApplicationUtils.getAjaxIds();
		Images images=null;
		if (idList != null)
			for (Integer id : idList) {
				images=siteFileService.getImg(id);
				FtpToolUtils.deleteFile(images.getImagesPath(),EhcacheUtils.getCacheFtp(EhcacheUtils.IMG_FTP));
				siteFileService.deleteImg(images);
			}
		return NONE;
	}

	public Pagination<Images> getPagination() {
		return pagination;
	}

	public void setPagination(Pagination<Images> pagination) {
		this.pagination = pagination;
	}

	@Resource
	public void setSiteFileService(SiteFileService siteFileService) {
		this.siteFileService = siteFileService;
	}
}
