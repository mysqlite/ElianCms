package com.elian.cms.admin.action;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.elian.cms.admin.model.Grade;
import com.elian.cms.admin.model.Site;
import com.elian.cms.admin.model.Template;
import com.elian.cms.admin.service.AreaService;
import com.elian.cms.admin.service.ChannelService;
import com.elian.cms.admin.service.FtpService;
import com.elian.cms.admin.service.GradeService;
import com.elian.cms.admin.service.SiteFileService;
import com.elian.cms.admin.service.SiteService;
import com.elian.cms.admin.service.TemplateService;
import com.elian.cms.syst.action.BaseAction;
import com.elian.cms.syst.model.Pagination;
import com.elian.cms.syst.model.SelectItem;
import com.elian.cms.syst.util.ApplicationUtils;
import com.elian.cms.syst.util.ElianCodes;
import com.elian.cms.syst.util.ElianUtils;
import com.elian.cms.syst.util.FreemarkerCodes;
import com.elian.cms.syst.util.SearchParamUtils;
import com.elian.cms.syst.util.SysXmlUtils;
import com.elian.cms.syst.util.ValidateUtils;

/**
 * 站点action
 * 
 * @author CZH
 * 
 */
@Component
@Scope("prototype")
public class SiteAction extends BaseAction {	
	private static final long serialVersionUID = 1223680287635357787L;
	public static final String TEMP = "temp";

	private Site site;
	private Integer id = Integer.valueOf(0);
	private boolean isEdit = false;
	private Pagination<Site> pagination = new Pagination<Site>(SearchParamUtils.getSiteActionConditionMap());
	private String compType;
	private boolean siteInfo;
	private SiteService siteService;
	private FtpService ftpService;
	private GradeService gradeService;
	private TemplateService templateService;
	private AreaService areaService;
	private ChannelService channelService=null;
	public String list() {
		if (ApplicationUtils.isMainStation())
			siteService.findAllSiteByCompType(pagination, compType);
		else if (ApplicationUtils.isSubStation()) {
			if (compType == null)
				compType = ElianUtils.COMP_TYPE_HOSP;
			if (compType.equals(ElianUtils.COMP_TYPE_HOSP))
				siteService.findHospSiteByArea(pagination, areaService.findChirdByParent(ApplicationUtils.getSubstation().getAreaId()));
			if (compType.startsWith(ElianUtils.COMP_TYPE_COMP))
				siteService.findCompSiteByArea(pagination, ApplicationUtils.getSubstation().getAreaId());
		}
		ApplicationUtils.getSite();
		if(!siteInfo)
		    return LIST;
		else {
			id=ApplicationUtils.getSite().getId();
			return edit();
		}
	}

	public String edit() {
		if (siteInfo) {
			id = ApplicationUtils.getSite().getId();
		}
		if (null == id | id <= 0)
			id = ApplicationUtils.getSite().getId();
		if (isEdit && id > 0) {
			site = siteService.get(id);
		}
		else {
			site = createsite();
		}
		return EDIT;
	}
	
	public String checkSiteSize() {
		List<Site> sites=siteService.findByAll(null);
		for (Site s : sites) {
			s.setSiteUsedSize(siteService.findImgSize(s.getId()));
			siteService.save(s);
		}
		return LIST;
	}

	public String editTempUrl(){
		if (siteInfo) {
			id = ApplicationUtils.getSite().getId();
		}
		if (null == id | id <= 0)
			id = ApplicationUtils.getSite().getId();
		if (isEdit && id > 0) {
			site = siteService.get(id);
		}
		else {
			site = createsite();
		}
		return TEMP;
	}
	
	public FtpService getFtpService() {
		return ftpService;
	}

	@Resource
	public void setFtpService(FtpService ftpService) {
		this.ftpService = ftpService;
	}

	public String show() {
		if (id > 0) {
			site = siteService.get(id);
		}
		return SHOW;
	}

	public Site createsite() {
		Site site = new Site();
		site.setCreateTime(new Date());
		site.setSiteSort(99);
		return site;
	}

	private SiteFileService siteFileService;

	@Resource
	public void setSiteFileService(SiteFileService siteFileService) {
		this.siteFileService = siteFileService;
	}

	@Resource
	public void setChannelService(ChannelService channelService) {
		this.channelService = channelService;
	}

	public String save() {
		if (site.getTemplate() != null&&site.getTemplate().getId()!=null)
			site.setTemplate(templateService.get(site.getTemplate().getId()));
		site.setGrade(gradeService.get(site.getGrade().getId()));
		if(!ApplicationUtils.isMainStation()) {
			Site newSite=siteService.get(site.getId());
			site.setSiteSize(newSite.getSiteSize());
			site.setSiteUsedSize(newSite.getSiteUsedSize());
		}
		siteService.save(site);
		siteFileService.saveFileToFtp(site,getPrevFile(), site.getLogoImg());
		siteService.findByAll(pagination);
		if (site.getId().equals(ApplicationUtils.getSite().getId()))
			ApplicationUtils.setSite(site);
		return LIST;
	}
	/**
	 * 本段代码由于功能尚不明确     只能是仅仅实现了功能而已      以后待功能完善后 需重写
	 * @return
	 */
	public String saveTempurl() {
		if(site.getId().equals(ApplicationUtils.getSite().getId())&&site.getTemplate().getId()!=null) {
			Site temp=siteService.get(site.getId());
			temp.setTemplate(templateService.get(site.getTemplate().getId()));
			siteService.save(temp);
		}
		siteService.findByAll(pagination);
		if (site.getId().equals(ApplicationUtils.getSite().getId()))
			ApplicationUtils.setSite(site);
		
		if (ApplicationUtils.isMainStation())
			siteService.findAllSiteByCompType(pagination, compType);
		else if (ApplicationUtils.isSubStation()) {
			if (compType == null)
				compType = ElianUtils.COMP_TYPE_HOSP;
			if (compType.equals(ElianUtils.COMP_TYPE_HOSP))
				siteService.findHospSiteByArea(pagination, areaService.findChirdByParent(ApplicationUtils.getSubstation().getAreaId()));
			if (compType.startsWith(ElianUtils.COMP_TYPE_COMP))
				siteService.findCompSiteByArea(pagination, ApplicationUtils.getSubstation().getAreaId());
		}
		ApplicationUtils.getSite();
		if(!siteInfo)
		    return TEMP;
		else {
			id=ApplicationUtils.getSite().getId();
			return editTempUrl();
		}
	}

	public String delete() {
		List<Integer> idList = ApplicationUtils.getAjaxIds();
		if (idList != null)
			for (Integer id : idList) {
				site = siteService.get(id);
				site.setStatus(3);
				siteService.save(site);
				// siteService.delete(site);
			}
		return NONE;
	}

	public String sort() {
		Integer id = ApplicationUtils.getAjaxId();
		Integer sort = ApplicationUtils.getAjaxSort();
		if (id != null && sort != null) {
			site = siteService.get(id);
			if (site == null)
				return NONE;
			site.setSiteSort(sort);
			siteService.save(site);
		}
		return NONE;
	}

	private List<SelectItem> comList = null;

	public List<SelectItem> getComList() {
		if (ApplicationUtils.getSite().getComType().equals(
				ElianUtils.COMP_TYPE_MAIN)) {
			if (comList == null) {
				comList = new ArrayList<SelectItem>(4);
				comList.add(new SelectItem(ElianUtils.COMP_TYPE_SUBS,ElianUtils.COMP_TYPE_SUBS_CN));
				comList.add(new SelectItem(ElianUtils.COMP_TYPE_HOSP,ElianUtils.COMP_TYPE_HOSP_CN));
				comList.add(new SelectItem(ElianUtils.COMP_TYPE_MEDICINE_COMP,ElianUtils.COMP_TYPE_MEDICINE_COMP_CN));
				comList.add(new SelectItem(ElianUtils.COMP_TYPE_INSTRUMENT_COMP,ElianUtils.COMP_TYPE_INSTRUMENT_COMP_CN));
			}
		}
		else if (ApplicationUtils.getSite().getComType().equals(
				ElianUtils.COMP_TYPE_SUBS)) {
			if (comList == null) {
				comList = new ArrayList<SelectItem>(4);
				comList.add(new SelectItem(ElianUtils.COMP_TYPE_HOSP,ElianUtils.COMP_TYPE_HOSP_CN));
				comList.add(new SelectItem(ElianUtils.COMP_TYPE_MEDICINE_COMP,ElianUtils.COMP_TYPE_MEDICINE_COMP_CN));
				comList.add(new SelectItem(ElianUtils.COMP_TYPE_INSTRUMENT_COMP,ElianUtils.COMP_TYPE_INSTRUMENT_COMP_CN));
			}
		}
		return comList;
	}

	public List<SelectItem> getDisableStatus() {
		return ElianUtils.getDisableStatus();
	}

	public List<SelectItem> getAvailableList() {
		return ElianUtils.getAvailableList();
	}

	@Resource
	public void setTemplateService(TemplateService templateService) {
		this.templateService = templateService;
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

	public Pagination<Site> getPagination() {
		return pagination;
	}

	public Site getSite() {
		return site;
	}

	public void setSite(Site site) {
		this.site = site;
	}

	public SiteService getSiteService() {
		return siteService;
	}

	@Resource
	public void setSiteService(SiteService siteService) {
		this.siteService = siteService;
	}

	/*
	 * public List<Ftp> getFtpList() { return ftpService.findByAll(null); }
	 */

	public List<Grade> getGradeList() {
		List<Grade> list = new ArrayList<Grade>();
		if (ApplicationUtils.isMainStation() || ApplicationUtils.isSubStation()) {
			list = gradeService.findByComType(site.getComType(), null);
		}
		else {
			list.add(site.getGrade());
		}
		return list;
	}

	public GradeService getGradeService() {
		return gradeService;
	}

	@Resource
	public void setGradeService(GradeService gradeService) {
		this.gradeService = gradeService;
	}

	public void setPagination(Pagination<Site> pagination) {
		this.pagination = pagination;
	}

	public List<SelectItem> getCompTypeList() {
		List<SelectItem> list = new ArrayList<SelectItem>();
		for (int i = 0; i < ElianUtils.getCompTypeList().size(); i++) {
			if (ElianUtils.getCompTypeList().get(i).getKey().equals(
					site.getComType())) {
				list.add(ElianUtils.getCompTypeList().get(i));
				break;
			}
		}
		return list;
	}

	public List<SelectItem> getPageCompTypeList() {
		return ElianUtils.getCompTypeList();
	}

	public List<SelectItem> getStatusList() {
		List<SelectItem> list = new ArrayList<SelectItem>();
		if (ApplicationUtils.isMainStation() || ApplicationUtils.isSubStation()) {
			list = ElianUtils.getStatusList();
		}
		else {
			for (int i = 0; i < ElianUtils.getStatusList().size(); i++) {
				if (ElianUtils.getStatusList().get(i).getKey().equals(
						site.getStatus())) {
					list.add(ElianUtils.getStatusList().get(i));
					break;
				}
			}
		}
		return list;
	}

	public void validateSave() {
		if (!ValidateUtils.isInteger(site.getStatus().toString())) {
			this.addFieldError("site.status", "必须选择一种站点状态");
		}
		if (!ValidateUtils.strLenthAll(site.getSiteName(), 2, 50)) {
			this.addFieldError("site.siteName", "站点名称长度，必须大于等于2且小于等于50");
		}
		if (ValidateUtils.isLengOut(site.getShortName(), 10)) {
			this.addFieldError("site.shortName", "简称长度，必须小于等于10");
		}
		if (ValidateUtils.isBlank(site.getComType())) {
			this.addFieldError("site.comType", "必须选择一种站点类型");
		}
		if (site.getGrade().getId() <= 0) {
			this.addFieldError("site.grade.id", "必须选择一种等级");
		}
		/*
		 * if (site.getFtp().getId() <= 0) { this.addFieldError("site.ftp.id",
		 * "必须选择一种FTP"); }
		 */
		if (ValidateUtils.isLengOut(site.getDomain(), 100)) {
			this.addFieldError("site.domain", "域名长度，必须小于等于100");
		}
		if (ValidateUtils.isLengOut(site.getAlias(), 100)) {
			this.addFieldError("site.alias", "其他域名长度，必须小于等于100");
		}
		if (ValidateUtils.isLengOut(site.getRedirect(), 255)) {
			this.addFieldError("site.redirect", "重定向长度，必须小于等于255");
		}
		if (ValidateUtils.isLengOut(site.getSitePath(), 255)) {
			this.addFieldError("site.sitePath", "站点路径长度，必须小于等于255");
		}
		if (ValidateUtils.isLengOut(site.getLogoImg(), 255)) {
			this.addFieldError("site.logoImg", "logo长度，必须小于等于255");
		}
	}

	public String status() {
		Integer id = ApplicationUtils.getAjaxId();
		int status = ApplicationUtils.getAjaxStatus();
		if (id != null && status > -1) {
			site = siteService.get(id);
			if (site == null)
				return NONE;
			site.setStatus(status);
			siteService.save(site);
		}
		return NONE;
	}

	public void check() {
		List<Integer> idList = ApplicationUtils.getAjaxIds();
		if (idList != null)
			for (Integer id : idList) {
				site = siteService.get(id);
				if (site.getStatus().equals(ElianUtils.STATUS_0)) {
					site.setStatus(ElianUtils.STATUS_1);
					siteService.save(site);
				}
			}
	}

	private List<SelectItem> siteStatusList = null;

	public List<SelectItem> getSiteStatusList() {
		if (siteStatusList == null) {
			siteStatusList = new ArrayList<SelectItem>(6);
			siteStatusList.add(new SelectItem(ElianUtils.STATUS_0,
					ElianUtils.STATUS_0_CN, ElianCodes.COLOR_RED));
			siteStatusList.add(new SelectItem(ElianUtils.STATUS_1,
					ElianUtils.STATUS_1_CN, ElianCodes.COLOR_GREEN));
			siteStatusList.add(new SelectItem(ElianUtils.STATUS_2,
					ElianUtils.STATUS_2_CN, ElianCodes.COLOR_RED));
			siteStatusList.add(new SelectItem(ElianUtils.STATUS_3,
					ElianUtils.STATUS_3_CN));
			siteStatusList.add(new SelectItem(ElianUtils.STATUS_4,
					ElianUtils.STATUS_4_CN, ElianCodes.COLOR_GRAY));
			siteStatusList.add(new SelectItem(ElianUtils.STATUS_5,
					ElianUtils.STATUS_5_CN, ElianCodes.COLOR_RED));
		}
		return siteStatusList;
	}

	private List<SelectItem> mainSiteStatusList = null;

	public List<SelectItem> getMainSiteStatusList() {
		if (mainSiteStatusList == null) {
			mainSiteStatusList = new ArrayList<SelectItem>(4);
			mainSiteStatusList.add(new SelectItem(ElianUtils.STATUS_0,
					ElianUtils.STATUS_0_CN, ElianCodes.COLOR_RED));
			mainSiteStatusList.add(new SelectItem(ElianUtils.STATUS_1,
					ElianUtils.STATUS_1_CN, ElianCodes.COLOR_GREEN));
			mainSiteStatusList.add(new SelectItem(ElianUtils.STATUS_2,
					ElianUtils.STATUS_2_CN, ElianCodes.COLOR_RED));
			mainSiteStatusList.add(new SelectItem(ElianUtils.STATUS_3,
					ElianUtils.STATUS_3_CN));
		}
		return mainSiteStatusList;
	}

	private List<Template> templateList = null;

	public List<Template> getTemplateList() {
		if (templateList == null) {
			templateList = templateService.findByGradeId(site.getGrade()
					.getId());
		}
		return templateList;
	}

	public String getCompType() {
		return compType;
	}

	public void setCompType(String compType) {
		this.compType = compType;
	}

	@Resource
	public void setAreaService(AreaService areaService) {
		this.areaService = areaService;
	}

	public boolean isSiteInfo() {
		return siteInfo;
	}

	public void setSiteInfo(boolean siteInfo) {
		this.siteInfo = siteInfo;
	}
	
	public String getSubDomain() {
		String mainDomain=SysXmlUtils.getString("mainDomain");
		if (ApplicationUtils.getSite().getComType().equals(
				ElianUtils.COMP_TYPE_MAIN) || ApplicationUtils.getSite().getComType().equals(
						ElianUtils.COMP_TYPE_SUBS)){
			return mainDomain;
		}	
		return mainDomain+ElianCodes.SPRIT+ApplicationUtils.getSite().getId()+ElianCodes.SPRIT+FreemarkerCodes.INDEX_OUTPUT_NAME;				
	}
	
	public boolean isSubStation(){
		return ApplicationUtils.isSubStation();
	}
	
	public boolean isMainStation(){
		return ApplicationUtils.isMainStation();
	}

	public Boolean getIsInit() {
		if(site.getTemplate()==null)
			return false;
		Boolean hasChannel=channelService.checkSiteHasChannel(site.getId());
		if(hasChannel)
			return false;
		return true;
	}

	/*public void setIsInit(Boolean isInit) {
		this.isInit = isInit;
	}	*/
}
