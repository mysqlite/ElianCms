package com.elian.cms.admin.action;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.beans.BeanUtils;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.elian.cms.admin.model.Ftp;
import com.elian.cms.admin.model.Type;
import com.elian.cms.admin.service.FtpService;
import com.elian.cms.admin.service.TypeService;
import com.elian.cms.syst.action.BaseAction;
import com.elian.cms.syst.model.Pagination;
import com.elian.cms.syst.model.TrueFalseItem;
import com.elian.cms.syst.util.ApplicationUtils;
import com.elian.cms.syst.util.EhcacheUtils;
import com.elian.cms.syst.util.ElianUtils;
import com.elian.cms.syst.util.SearchParamUtils;
import com.elian.cms.syst.util.StringUtils;
import com.elian.cms.syst.util.ValidateUtils;

/**
 * FTP action
 * 
 * @author CZH
 * 
 */
@Component
@Scope("prototype")
public class FtpAction extends BaseAction {
	private static final long serialVersionUID = -7821357955861861416L;

	private Ftp ftp;
	private Integer id = Integer.valueOf(0);
	private boolean isEdit = false;
	private Pagination<Ftp> pagination = new Pagination<Ftp>(SearchParamUtils
			.getFtpActionConditionMap());

	private FtpService ftpService;
	private TypeService typeService;

	private List<Type> ftpTypeList;

	public String list() {
		ftpService.findByAll(pagination);
		return LIST;
	}

	public String edit() {
		ftpTypeList = typeService.findByType(true, ElianUtils.FTP_TYPE);
		if (isEdit && id > 0) {
			ftp = ftpService.get(id);
		}
		else {
			ftp = createftp();
		}
		return EDIT;
	}

	public String show() {
		if (id > 0) {
			ftp = ftpService.get(id);
		}
		return SHOW;
	}

	public Ftp createftp() {
		Ftp ftp = new Ftp();
		ftp.setDisable(true);
		ftp.setFtpSort(99);
		return ftp;
	}
	
    private void updateSessionImgFtp() {
    	if(null!=ftp.getId()) {
    		if(ftp.getType().getId().equals (EhcacheUtils.getCacheFtp(EhcacheUtils.IMG_FTP).getType().getId())) {
    			EhcacheUtils.setCacheFtp(ftp, EhcacheUtils.IMG_FTP);
    		}
    		else if(ftp.getType().getId().equals(EhcacheUtils.getCacheFtp(EhcacheUtils.STATIC_FTP).getType().getId())) {
    			EhcacheUtils.setCacheFtp(ftp, EhcacheUtils.STATIC_FTP);
    		}
    		else if(ftp.getType().getId().equals(EhcacheUtils.getCacheFtp(EhcacheUtils.SWF_FTP).getType().getId())) {
    			EhcacheUtils.setCacheFtp(ftp, EhcacheUtils.SWF_FTP);
    		}
    		else if(ftp.getType().getId().equals(EhcacheUtils.getCacheFtp(EhcacheUtils.FILE_FTP).getType().getId())) {
    			EhcacheUtils.setCacheFtp(ftp, EhcacheUtils.FILE_FTP);
    		}
    	}
    }
    
	public String save() {
		ftp.setType(typeService.get(ftp.getType().getId()));
		Ftp dataFtp=ftpService.get(ftp.getId());
		if(dataFtp!=null) {
		if(StringUtils.isBlank(ftp.getPassword())) 
			ftp.setPassword(dataFtp.getPassword());
		BeanUtils.copyProperties(ftp, dataFtp);
		ftpService.save(dataFtp);
		 updateSessionImgFtp();
			if(dataFtp.isDefault()) {
				updateDefaule(dataFtp.getType().getId(),dataFtp);
			}
		}
		else {
			ftpService.save(ftp);
			 updateSessionImgFtp();
			if(ftp.isDefault()) {
				updateDefaule(ftp.getType().getId(),ftp);
			}
		}
		return SAVE;
	}

	public String delete() {
		List<Integer> idList = ApplicationUtils.getAjaxIds();
		if (idList != null)
			for (Integer id : idList) {
				ftp = ftpService.get(id);
				ftpService.delete(ftp);
			}
		return NONE;
	}

	public String sort() {
		Integer id = ApplicationUtils.getAjaxId();
		Integer sort = ApplicationUtils.getAjaxSort();
		if (id != null && sort != null) {
			ftp = ftpService.get(id);
			if (ftp == null)
				return NONE;
			ftp.setFtpSort(sort);
			ftpService.save(ftp);
			 updateSessionImgFtp();
		}
		return NONE;
	}

	public String disable() {
		Integer id = ApplicationUtils.getAjaxId();
		Boolean disable = ApplicationUtils.getAjaxDisable();
		if (id != null && disable != null) {
			ftp = ftpService.get(id);
			if (ftp == null)
				return NONE;
			ftp.setDisable(!disable);
			ftpService.save(ftp);
		}
		return NONE;
	}

	public void check() {
		List<Integer> idList = ApplicationUtils.getAjaxIds();
		if (idList != null)
			for (Integer id : idList) {
				ftp = ftpService.get(id);
				ftp.setDisable(!ftp.isDisable());
				ftpService.save(ftp);
			}
	}
	
	public TrueFalseItem getDefaultItem() {
		return ElianUtils.getTrueFalseItem();
	}
	
	public boolean isMainStation() {
		return ApplicationUtils.isMainStation();
	}
	
	public void defaultData() {
		Integer id = ApplicationUtils.getAjaxId();
		Boolean def = ApplicationUtils.getAjaxDisable();
		if (id != null && def != null) {
			ftp = ftpService.get(id);
			if (ftp == null)
				return;
			if(def) 
				updateDefaule(ftp.getType().getId(),ftp);
		}
	}
	
	private void updateDefaule(Integer typeId,Ftp ftp) {
		List<Ftp> ftps=ftpService.findByTypeIdOrTypeName(typeId, null);
		for (Ftp ftp2 : ftps) {
			if(!ftp.getId().equals(ftp2.getId())) {
			ftp2.setDefault(false);
			ftpService.save(ftp2);
			}
		}
		if(ftp!=null) {
		ftp.setDefault(true);
		ftpService.save(ftp);
		}
		updateSessionImgFtp();
	}


	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public boolean isEdit() {
		return isEdit;
	}

	public void setEdit(boolean isEdit) {
		this.isEdit = isEdit;
	}

	public Pagination<Ftp> getPagination() {
		return pagination;
	}

	public Ftp getFtp() {
		return ftp;
	}

	public void setFtp(Ftp ftp) {
		this.ftp = ftp;
	}

	public FtpService getFtpService() {
		return ftpService;
	}

	@Resource
	public void setFtpService(FtpService ftpService) {
		this.ftpService = ftpService;
	}

	@Resource
	public void setTypeService(TypeService typeService) {
		this.typeService = typeService;
	}

	public void setPagination(Pagination<Ftp> pagination) {
		this.pagination = pagination;
	}

	public void validateSave() {
		if (!ValidateUtils.strLenth(ftp.getFtpName(), 2, 20))
			this.addFieldError("ftp.ftpName", "FTP名称长度，必须大于等于2且小于等于20");
		if (ValidateUtils.isBlank(ftp.getFtpSort().toString().trim()))
			this.addFieldError("ftp.ftpSort", "排序不能为空");
		if (ValidateUtils.isBlank(ftp.getFtpPort().toString().trim()))
			this.addFieldError("ftp.port", "ftp端口不能为空");
		if (this.hasFieldErrors())
			ftpTypeList = typeService.findByType(true, ElianUtils.FTP_TYPE);
	}

	public List<Type> getFtpTypeList() {
		return ftpTypeList;
	}

	public void setFtpTypeList(List<Type> ftpTypeList) {
		this.ftpTypeList = ftpTypeList;
	}
}
