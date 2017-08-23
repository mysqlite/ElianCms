package com.elian.cms.admin.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;

import javax.annotation.Resource;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import com.elian.cms.admin.action.SiteHeadAndFootAction;
import com.elian.cms.admin.data.exception.TemplateConfigException;
import com.elian.cms.admin.model.Channel;
import com.elian.cms.admin.model.Company;
import com.elian.cms.admin.model.Content;
import com.elian.cms.admin.model.Department;
import com.elian.cms.admin.model.Doctor;
import com.elian.cms.admin.model.Hospital;
import com.elian.cms.admin.model.InitChannel;
import com.elian.cms.admin.model.InitTemplateConfig;
import com.elian.cms.admin.model.Instrument;
import com.elian.cms.admin.model.Medicine;
import com.elian.cms.admin.model.Site;
import com.elian.cms.admin.model.SiteInclude;
import com.elian.cms.admin.model.TemplateConfig;
import com.elian.cms.admin.service.BasecontentService;
import com.elian.cms.admin.service.ChannelService;
import com.elian.cms.admin.service.ContentService;
import com.elian.cms.admin.service.DepartmentService;
import com.elian.cms.admin.service.DeptTypeService;
import com.elian.cms.admin.service.DoctorService;
import com.elian.cms.admin.service.HospitalService;
import com.elian.cms.admin.service.InitChannelService;
import com.elian.cms.admin.service.InitSiteService;
import com.elian.cms.admin.service.InitTemplateConfigService;
import com.elian.cms.admin.service.InstrumentService;
import com.elian.cms.admin.service.MedicineService;
import com.elian.cms.admin.service.SiteIncludeService;
import com.elian.cms.admin.service.StaticService;
import com.elian.cms.admin.service.TemplateConfigService;
import com.elian.cms.admin.service.TypeService;
import com.elian.cms.syst.model.BaseContent;
import com.elian.cms.syst.util.ApplicationUtils;
import com.elian.cms.syst.util.ElianUtils;
import com.elian.cms.syst.util.SpringUtils;
import com.elian.cms.syst.util.StringUtils;

/**
 * 初始化站点
 * @author thy
 */

@Scope("prototype")
@Component("initSiteService")
public class InitSiteServiceImpl implements InitSiteService {

	private InitChannelService initChannelService = null;
	private ChannelService channelService = null;
	private HospitalService hospitalService = null;
	//private DepartmentService departmentService = null;
	private DoctorService doctorService = null;
	private InitTemplateConfigService initTemplateConfigService=null;
	private TemplateConfigService templateConfigService=null;
	private StaticService staticService=null; 
	private SiteIncludeService siteIncludeService=null;
	private DeptTypeService deptTypeService=null;
	private MedicineService medicineService=null;
	private InstrumentService instrumentService=null;
	private TypeService typeService=null;
	
	private Hospital hospital=null;
	private Department department=null;
	private Doctor doctor=null;
	private Company company=null;
	private Instrument instrument=null;
	private Medicine medicine=null;
	
	@SuppressWarnings({ "unchecked"})
	public void addDefaultData(Map<InitChannel, Channel> map) {
		init();
		
		Set<Entry<InitChannel, Channel>> entrySet=map.entrySet();	
		Iterator<Entry<InitChannel, Channel>> it=entrySet.iterator();
		while (it.hasNext()) {
			Map.Entry<InitChannel,Channel> entry = it.next();
			Channel channel=entry.getValue();
			if(channel.getModel()!=null){
				String actionName= channel.getModel().getContentModel().getActionName();
				if(actionName.startsWith("hospital") 
						|| actionName.startsWith("department") 
						|| actionName.startsWith("doctor") 
						|| actionName.startsWith("company")
						||actionName.startsWith(StringUtils.getENL(instrument))
						||actionName.startsWith(StringUtils.getENL(medicine))){
					Content content=createStaticContent(channel, actionName);
					if(actionName.startsWith("hospital")){
						content.setEntityId(hospital.getId());
						content.setTitle(hospital.getHospName());
					}else if(actionName.startsWith("company")){
						content.setEntityId(company.getId());
						content.setTitle(company.getName());
					}else if(actionName.startsWith("department")){
						content.setEntityId(department.getId());
						content.setTitle(department.getDeptName());
					}else if(actionName.startsWith("doctor")){
						content.setEntityId(doctor.getId());
						content.setTitle(channel.getChannelName()+"***"+doctor.getDoctName());
					}else if(actionName.startsWith(StringUtils.getENL(medicine))) {
						content.setEntityId(medicine.getId());
						content.setTitle(channel.getChannelName()+"***"+medicine.getCnName());
					}
					else if(actionName.startsWith(StringUtils.getENL(instrument))) {
						content.setEntityId(instrument.getId());
						content.setTitle(channel.getChannelName()+"***"+instrument.getCnName());
					}
					ContentService contentService=(ContentService) SpringUtils.getEntityService(Content.class);
					contentService.save(content);
				}else{
					BaseContent content=getentityByActionName(actionName);
					
					content.initWithDefaultData(ApplicationUtils.getUser(),channel);
					content.setChannelId(channel.getId());
					content.setActionName(actionName);
					content.setContentStatus(ElianUtils.CONTENT_STATUS_3);
					content.setSource(true);
					
					BasecontentService service= (BasecontentService) SpringUtils.getEntityService(content.getClass());
					service.save(content,false);
				}
			}
		}
	}

	private Content createStaticContent(Channel channel, String actionName) {
		Content content=new Content();
		content.setSite(ApplicationUtils.getSite());
		content.setStatus(1);
		content.setSort(99);
		content.setCreater(ApplicationUtils.getUser().getRealName());
		content.setCreateTime(new Date());
		content.setActionName(actionName);
		content.setChannel(channel);
		content.setStatus(ElianUtils.CONTENT_STATUS_3);
		content.setStaticStatus(ElianUtils.STATIC_STATUS_1);
		return content;
	}

	//给全局变量赋值
	private void init() {
		if(ApplicationUtils.isSubStation()){
			initSubStationData();
		}else if(ApplicationUtils.isHosp()){
			initHospData();
		}else if(ApplicationUtils.isCompany()){
			initCompany();
		}
	}

	private void initCompany() {
		company=ApplicationUtils.getCompany();
		medicine=new Medicine();
		medicine.createDefaultData(company, typeService.get(57));
		medicineService.save(medicine);
		instrument=new Instrument();
		instrument.createDefaultData(company, deptTypeService.get(82), deptTypeService.get(41));
		instrumentService.save(instrument);
	}

	private void initSubStationData() {
		Integer areaId= ApplicationUtils.getSubstation().getAreaId();
		List<Hospital> hospitalList=hospitalService.findByAreaCode(5, areaId);
		if(!CollectionUtils.isEmpty(hospitalList)){
			hospital=hospitalList.get(0);
			List<Doctor> doctList=doctorService.findList(hospitalList, 1);
			if(!CollectionUtils.isEmpty(doctList))					
				doctor=doctList.get(0);
		}		
	}
	
	private void initHospData(){
		hospital= ApplicationUtils.getHospital();
		
    	department=new Department();
    	department.createDefaultData(ApplicationUtils.getHospital());
    	DepartmentService ds=(DepartmentService) SpringUtils.getEntityService(department.getClass());
    	ds.save(department);
    	
    	doctor=new Doctor();
    	doctor.createDefaultData(department);
    	DoctorService docS=(DoctorService) SpringUtils.getEntityService(Doctor.class);
    	docS.save(doctor);
	}

	public Map<InitChannel, Channel> addInitChannelToSite(Site site) {
		Map<InitChannel, Channel> map = new HashMap<InitChannel, Channel>();
		List<InitChannel> initList = initChannelService.findByAll(site
				.getTemplate().getId(), null, null);
		List<InitChannel> parentList = new ArrayList<InitChannel>();
		ListIterator<InitChannel> it = initList.listIterator();
		while (it.hasNext()) {
			InitChannel ic = it.next();
			if (ic.getChannelType().equals(ElianUtils.CHANNEL_PARENT)) {
				parentList.add(ic);
				it.remove();
			}
		}
		// 首页保存所有的父节点
		for (int i = 0; i < parentList.size(); i++) {
			InitChannel current = parentList.get(i);
			if (current.getParentId() == 0) {
				Channel c = current.toChannel(site, null);
				channelService.save(c);
				map.put(current, c);
			}
		}
		// 保存其他节点
		for (int i = 0; i < initList.size(); i++) {
			InitChannel current = initList.get(i);
			Channel parent = null;
			if (!current.getParentId().equals(0)) {
				InitChannel initParent = getParent(parentList, current
						.getParentId());
				parent = map.get(initParent);
			}
			Channel c = current.toChannel(site, parent);
			channelService.save(c);
			map.put(current, c);
		}
		return map;
	}

	public void initTempConfig(Map<InitChannel, Channel> map) {
		Set<Entry<InitChannel, Channel>> set=map.entrySet();
		Iterator<Entry<InitChannel, Channel>> it=set.iterator();
		while (it.hasNext()) {
			Entry<InitChannel, Channel> entry = it.next();
			InitChannel ic= entry.getKey();
			Channel c=entry.getValue();
			configChannel(c,ic,map);
		}
	}

	public void staticContent(Map<InitChannel, Channel> map) {
		Set<Entry<InitChannel, Channel>> entrtySet= map.entrySet();
		Iterator<Entry<InitChannel, Channel>> it= entrtySet.iterator();
		while (it.hasNext()) {
			Map.Entry<InitChannel, Channel> entry = it.next();
			if(entry.getValue().getParentId()==0){
				try {
					staticService.staticContent(entry.getValue().getId(), false);
				}catch (TemplateConfigException e) {
					e.printStackTrace();
				}
			}
		}
	}

	public void staticIncludeFile() {
		SiteHeadAndFootAction sa=SpringUtils.getBean("siteHeadAndFootAction");
		sa.list();
		List<SiteInclude> includeList = siteIncludeService.getPageBySiteId(null, ApplicationUtils.getSite().getId());
		if(CollectionUtils.isEmpty(includeList)) return ;
		for(SiteInclude si:includeList){
			try {	
				SiteInclude siteInclude=siteIncludeService.get(si.getId());
				staticService.staticIncludeFile(siteInclude);
			}
			catch (TemplateConfigException e) {
				e.printStackTrace();
			}
		}
	}

	public void staticNav(Map<InitChannel, Channel> map) {
		try {
			staticService.staticNav(false);
		}
		catch (TemplateConfigException e) {
			e.printStackTrace();
		}
	}

	@Resource
	public void setInitChannelService(InitChannelService initChannelService) {
		this.initChannelService = initChannelService;
	}
	
	@Resource
	public void setChannelService(ChannelService channelService) {
		this.channelService = channelService;
	}

	@Resource
	public void setHospitalService(HospitalService hospitalService) {
		this.hospitalService = hospitalService;
	}

//	@Resource
//	public void setDepartmentService(DepartmentService departmentService) {
//		this.departmentService = departmentService;
//	}

	@Resource
	public void setDoctorService(DoctorService doctorService) {
		this.doctorService = doctorService;
	}
	
	@Resource
	public void setInitTemplateConfigService(
			InitTemplateConfigService initTemplateConfigService) {
		this.initTemplateConfigService = initTemplateConfigService;
	}
	
	@Resource
	public void setTemplateConfigService(TemplateConfigService templateConfigService) {
		this.templateConfigService = templateConfigService;
	}
	
	@Resource
	public void setStaticService(StaticService staticService) {
		this.staticService = staticService;
	}
	
	@Resource
	public void setDeptTypeService(DeptTypeService deptTypeService) {
		this.deptTypeService = deptTypeService;
	}

	@Resource
	public void setMedicineService(MedicineService medicineService) {
		this.medicineService = medicineService;
	}

	@Resource
	public void setInstrumentService(InstrumentService instrumentService) {
		this.instrumentService = instrumentService;
	}

	@Resource
	public void setTypeService(TypeService typeService) {
		this.typeService = typeService;
	}

	private InitChannel getParent(List<InitChannel> parentList,
			Integer parentId) {
		for(InitChannel ic:parentList){
			if(ic.getId().equals(parentId))
				return ic;
		}
		return null;
	}
	
	private void configChannel(Channel c, InitChannel ic,
			Map<InitChannel, Channel> map) {
		List<InitTemplateConfig> initConfigList=new ArrayList<InitTemplateConfig>();
		getInitConfigList(ic, initConfigList);
		List<TemplateConfig> configList=new ArrayList<TemplateConfig>();
		for(int i=0;i<initConfigList.size();i++){
			TemplateConfig config=initConfigList.get(i).toTemplateConfig(c,map.get(initConfigList.get(i).getInitChannelSet()));
			configList.add(config);
		}
		if(!CollectionUtils.isEmpty(configList))
			templateConfigService.save(configList);
	}
	
	private void getInitConfigList(InitChannel ic,
			List<InitTemplateConfig> initConfigList) {
		if(ic.getChannelTemp()!=null){
			List<InitTemplateConfig> channelTempComfigList=initTemplateConfigService.
				findByTempIdAndChannelId(ic.getChannelTemp().getId(), ic.getId());
			if(!CollectionUtils.isEmpty(channelTempComfigList))
				initConfigList.addAll(channelTempComfigList);
		}
		if(ic.getContentTemp()!=null){
			List<InitTemplateConfig> channelTempComfigList=initTemplateConfigService.
				findByTempIdAndChannelId(ic.getContentTemp().getId(), ic.getId());
			if(!CollectionUtils.isEmpty(channelTempComfigList))
				initConfigList.addAll(channelTempComfigList);
		}
	}
	
	private BaseContent getentityByActionName(String actionName) {
		String modelName = actionName.substring(0, actionName.length() - 2);
		String className = "com.elian.cms.admin.model."
				+ modelName.substring(0, 1).toUpperCase()
				+ modelName.substring(1, modelName.length());
		try {
			Class<?> clazz = Class.forName(className);
			BaseContent data = (BaseContent) clazz.newInstance();
			return data;
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
}
