package com.elian.cms.admin.action;

import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.beans.BeanUtils;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import com.elian.cms.admin.model.Area;
import com.elian.cms.admin.model.Department;
import com.elian.cms.admin.model.Doctor;
import com.elian.cms.admin.model.Hospital;
import com.elian.cms.admin.model.Type;
import com.elian.cms.admin.service.AreaService;
import com.elian.cms.admin.service.DepartmentService;
import com.elian.cms.admin.service.DoctorService;
import com.elian.cms.admin.service.HospitalService;
import com.elian.cms.admin.service.SiteFileService;
import com.elian.cms.admin.service.TypeService;
import com.elian.cms.syst.action.BaseAction;
import com.elian.cms.syst.model.Pagination;
import com.elian.cms.syst.model.SelectItem;
import com.elian.cms.syst.util.ApplicationUtils;
import com.elian.cms.syst.util.ElianCodes;
import com.elian.cms.syst.util.ElianUtils;
import com.elian.cms.syst.util.FilePathUtils;
import com.elian.cms.syst.util.SearchParamUtils;
import com.elian.cms.syst.util.StringUtils;
import com.elian.cms.syst.util.ValidateUtils;

@Component
@Scope("prototype")
public class HospitalAction extends BaseAction{
	private static final long serialVersionUID = -3954434154037074483L;
	
	private boolean leaf = false;   /** 树节点传递过来的栏目ID */	
	private String action;  /** 树节点传递过来的action名称 */	
	private Integer channelId;  
	/** 内容状态 */
	private Integer status = Integer.valueOf(1);
	private boolean myHosp=false;
	
	private Pagination<Hospital> pagination=new Pagination<Hospital>(
			SearchParamUtils.getHospitalConditionMap());
	private int id;
	private HospitalService hospitalService;
	private boolean edit;
	private Hospital hospital;
	private TypeService typeService;
	private DepartmentService departmentService;
	private DoctorService doctorService;
	
	private List<Type> natureList ;
	private List<Type> hospTypeList;
	private List<Type> rankList;
	private String areaName = "";
	private AreaService areaService;
	private List<Hospital> hospList;
	private Integer areaCode;
	private Integer submitId;
	private String paramId;
	private String requType;
	private boolean ztree;
	private String hospName;
	
	
	private static final String AREA="area",HOSP="hosp",DEPART="depart",DOCTOR="doctor";
	
	public String tree() {
		if(ApplicationUtils.getHospital()!=null) {
			areaCode=ApplicationUtils.getHospital().getAreaId();
			ztree=true;
			return "inputMyHosp";
		}
		return "tree";
	}
	public void areaHospitalTree() {
		String paramType=paramId.substring(0, paramId.indexOf(":"));
		submitId=Integer.valueOf(paramId.substring(paramId.indexOf(":")+1, paramId.length()));
		if(paramType.equals(AREA)) {
			putHospJson(submitId,hospName);
		}
		else if(paramType.equals(HOSP)) {
			putDepartJson(submitId);
		}
		else if(paramType.equals(DEPART)) {
			//putDoctorJson(submitId);
		}
	}
	
	public void putThisSessionHosp() {
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		Map<String, Object> map = new LinkedHashMap<String, Object>();
		Hospital h=ApplicationUtils.isHosp()?ApplicationUtils.getHospital():null;
		if(h!=null) {
			map.put("id", h.getId());
			map.put("name",StringUtils.isNotBlank(hospName)?(h.getShortName().contains(hospName)?h.getShortName():h.getHospName()):
					(StringUtils.isNotBlank(h.getShortName())?h.getShortName():h.getHospName()));
			map.put("type", HOSP);
			map.put("open", true);
			map.put("isParent", true);
		}
		list.add(map);
		ApplicationUtils.sendJsonArray(list);
	}
	
	public void putDoctorJson(Integer departId) {
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		List<Doctor> doctorList=doctorService.findByPage(null, null, departId);
		if(doctorList!=null) {
			Map<String, Object> map = null;
			for (Doctor d : doctorList) {
				map = new LinkedHashMap<String, Object>();
				map.put("id", d.getId());
				map.put("name", d.getDoctName());
				map.put("type", DOCTOR);
				map.put("open", false);
				map.put("isParent", false);
				list.add(map);
			}
		}
		ApplicationUtils.sendJsonArray(list);
	}
	
	private void putDepartJson(Integer hospId) {
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		List<Department> departmentList=departmentService.findPageByHosp(null, hospId);
		if(departmentList!=null) {
			Map<String, Object> map = null;
			for (Department d : departmentList) {
				map = new LinkedHashMap<String, Object>();
				map.put("id", d.getId());
				map.put("name", d.getDeptName());
				map.put("type", DEPART);
				
				if(requType.equals(DEPART)) {
					map.put("open", false);
					map.put("isParent", false);
				}
				else if(requType.equals(DOCTOR)) {
					map.put("open", false);
					map.put("isParent", false);
				}
				list.add(map);
			}
		}
		ApplicationUtils.sendJsonArray(list);
	}
	
	private void putHospJson(Integer areaCode,String hospName) {
		if(areaCode!=null) {
			List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
			List<Integer> areaCodes=areaService.findChirdByParent(areaCode);
			hospList=hospitalService.findByAreaCode(areaCodes,hospName);
			if(hospList!=null) {
				Map<String, Object> map = null;
				for (Hospital h : hospList) {
					map = new LinkedHashMap<String, Object>();
					map.put("id", h.getId());
					map.put("name", StringUtils.isNotBlank(h.getShortName())?h.getShortName():h.getHospName());
					map.put("type", HOSP);
					if(requType.equals(DEPART)) {
						map.put("open", false);
						map.put("isParent", false);
					}
					else if(requType.equals(DOCTOR)) {
						map.put("open", true);
						map.put("isParent", true);
					}
					
					list.add(map);
				}
			}
			ApplicationUtils.sendJsonArray(list);
		}
	}
	
	public void areaHospital() {
		putHospJson(areaCode,null);
	}
	
	public String list() {
		if(ApplicationUtils.getHospital()!=null)
			return INPUT;
		getList();
		List<Integer> areaCodes=null;
		if(ApplicationUtils.isMainStation()&&areaCode!=null&&areaCode==0) {
			hospitalService.findBypage(pagination,null);
		}
		else if(ApplicationUtils.isSubStation()) {
			areaCodes=areaService.findChirdByParent(ApplicationUtils.getSubstation().getAreaId());
			hospitalService.findBypage(pagination,areaCodes);
		}
		else if(areaCode!=null) {
			areaCodes=areaService.findChirdByParent(areaCode);
			hospitalService.findBypage(pagination,areaCodes);
		}
		return LIST;
	}
	
	public String edit() {
		if(ApplicationUtils.isHosp()) {
			id=ApplicationUtils.getHospital().getId();
			edit=true;
		}
		if (edit && id > 0) {
			hospital = new Hospital();
		    BeanUtils.copyProperties(hospitalService.get(id), hospital);
			areaName=null!=hospital.getAreaId()?getAreaNames(hospital.getAreaId()):null;
			hospital.setBusLine(FilePathUtils.setEditorOutPath(hospital.getBusLine()));
			hospital.setHospDesc(FilePathUtils.setEditorOutPath(hospital.getHospDesc()));
		}else {
			createHospital();
		}
		getList();
		return EDIT;
	}
	
	private void createHospital() {
		hospital=new Hospital();
		hospital.setStatus(ElianUtils.STATUS_0);
		if(areaCode!=null) {
			hospital.setAreaId(areaCode);
			areaName=getAreaNames(areaCode);
		}
	}
	
	public void check() {
		List<Integer> idList = ApplicationUtils.getAjaxIds();
		if (idList != null)
			for (Integer id : idList) {
				hospital =hospitalService.get(id);
				if(hospital.getStatus()==ElianUtils.STATUS_0) {
				hospital.setStatus(ElianUtils.STATUS_1);
				hospitalService.save(hospital);
				}
			}
	}
	
	public String save() {
		if(!edit) {
			hospital.setCreateTime(new Date());
		}
		if(hospital.isReg()&null==hospital.getRegTime())
			hospital.setRegTime(new Date());
		if(hospital.isFollowup()&null==hospital.getFollowupTime())
			hospital.setFollowupTime(new Date());
		
		 hospital.setActionName(action);
		 hospital.setChannelId(channelId);
		 hospital.setContentStatus(status);
		
		hospital.setBusLine(FilePathUtils.getConContext(hospital.getBusLine()));
		hospital.setHospDesc(FilePathUtils.getConContext(hospital.getHospDesc()));	
		if(isPublish())
			hospitalService.save(hospital, edit,isPublish());	
		else
		    hospitalService.save(hospital, edit);		
		siteFileService.saveFileToFtp(hospital,getPrevFile(),hospital.getPermitImg(),hospital.getLogoImg(),hospital.getHospImg(),hospital.getMapImg());
		siteFileService.saveConContext(hospital, getEditorPrevImg(), hospital.getBusLine(),hospital.getHospDesc());
		if(ApplicationUtils.isHosp()&&(channelId==null||channelId==0)) {
			areaName="";
			return edit();
		}
		return SAVE;
	}
	
	public String show() {
		if (id > 0) {
			getList();
			hospital = new Hospital();
		    BeanUtils.copyProperties(hospitalService.get(id), hospital);
			areaName=areaService.get(hospital.getAreaId()).getAreaName();
			hospital.setBusLine(FilePathUtils.setEditorOutPath(hospital.getBusLine()));
			hospital.setHospDesc(FilePathUtils.setEditorOutPath(hospital.getHospDesc()));
		}
		return SHOW;
	}

	/**
	 * 内容界面delete方法，删除数据的时候只删除内容表的数据
	 */
	public void delete() {
		List<Integer> idList = ApplicationUtils.getAjaxIds();
		if (CollectionUtils.isEmpty(idList))
			return;
		hospitalService.delete(null, idList);
	}
	
	public void deleteHospital() {
		List<Integer> idList = ApplicationUtils.getAjaxIds();
		if (CollectionUtils.isEmpty(idList))
			return ;
		List<Hospital> hospList = hospitalService.findByContentId(idList,ApplicationUtils.getSite().getId());
		if (CollectionUtils.isEmpty(hospList))
			return ;
		for (Hospital hosp : hospList) {
			hospitalService.delete(hosp, null);
		}
	}
	
	private List<SelectItem> hospitalStatusList = null;

	public List<SelectItem> getHospitalStatusList() {
		if (hospitalStatusList == null) {
			hospitalStatusList=ElianUtils.getStatusList();
		}
		return hospitalStatusList;
	}
	
	private List<SelectItem> mainHospitalStatusList = null;

	public List<SelectItem> getMainHospitalStatusList() {
		if (mainHospitalStatusList == null) {
			mainHospitalStatusList = new ArrayList<SelectItem>(4);
			mainHospitalStatusList.add(new SelectItem(ElianUtils.STATUS_0,ElianUtils.STATUS_0_CN, ElianCodes.COLOR_RED));
			mainHospitalStatusList.add(new SelectItem(ElianUtils.STATUS_1,ElianUtils.STATUS_1_CN, ElianCodes.COLOR_GREEN));
			mainHospitalStatusList.add(new SelectItem(ElianUtils.STATUS_2,ElianUtils.STATUS_2_CN, ElianCodes.COLOR_RED));
			mainHospitalStatusList.add(new SelectItem(ElianUtils.STATUS_3,ElianUtils.STATUS_3_CN));
		}
		return mainHospitalStatusList;
	}
	
	public List<SelectItem> getDisableStatus(){
		return ElianUtils.getDisableStatus();
	}
	
	public String status() {
		Integer id = ApplicationUtils.getAjaxId();
		int status = ApplicationUtils.getAjaxStatus();
		if (id != null && status > -1) {
			hospital = hospitalService.get(id);
			if (hospital == null)
				return NONE;
			if(null!=ApplicationUtils.getHospital()) {
				if(!ApplicationUtils.getHospital().getId().equals(id)) {
					hospital.setStatus(status);
					hospitalService.save(hospital);
				}
			}else {
				hospital.setStatus(status);
				hospitalService.save(hospital);
			}
		}
		return NONE;
	}
	
	public void validateSave() {
		if(StringUtils.isBlank(hospital.getHospName()))
			this.addFieldError("hospital.hospName", "请填写医院名称");
		
		if(ValidateUtils.isLengOut(hospital.getHospName(),50))
			this.addFieldError("hospital.hospName", "名称长度在50字以内");
		
		if(ValidateUtils.isLengOut(hospital.getShortName(),30))
			this.addFieldError("hospital.shortName", "简称长度在30字以内");
		
		if(ValidateUtils.isLengOut(hospital.getPermitImg(), 255))
			this.addFieldError("hospital.permitImg", "许可证长度在255字以内");
		
		if(null==hospital.getNature())
			this.addFieldError("hospital.nature", "请选择医院性质");
		
		if(null==hospital.getHospType())
			this.addFieldError("hospital.hospType", "请选择医院类型");
		
		if(null==hospital.getRank())
			this.addFieldError("hospital.rank", "请选择医院等级");
		
		if(ValidateUtils.isLengOut(hospital.getShortDesc(),255))
			this.addFieldError("hospital.shortDesc", "简要介绍长度在255字以内");
		
		if(ValidateUtils.isLengOut(hospital.getHospDesc(), 5000))
			this.addFieldError("hospital.hospDesc", "详细介绍长度在5000字以内");
		
		if(!StringUtils.isBlank(hospital.getPhone())) {
    		if(ValidateUtils.isNotPhoneAndMobile(hospital.getPhone()))
    			this.addFieldError("hospital.phone", "请填写正确的联系电话");
		}
		if(!StringUtils.isBlank(hospital.getEmergencyPhone())) {
			if(ValidateUtils.isNotPhoneAndMobile(hospital.getEmergencyPhone()))
				this.addFieldError("hospital.emergencyPhone", "请填写正确的联系电话");
		}
		if (StringUtils.isBlank(hospital.getAddress()))
			this.addFieldError("hospital.address", "请填写医院地址");
		if(ValidateUtils.isLengOut(hospital.getAddress(), 255))
			this.addFieldError("hospital.address", "医院地址长度在255字以内");
		
    	if(StringUtils.isNotBlank(hospital.getSiteUrl())){
    		if(!ValidateUtils.isLengOut(hospital.getSiteUrl(), 255)) {
        		if(!ValidateUtils.isHttpUrl(hospital.getSiteUrl()))
        			this.addFieldError("hospital.siteUrl", "请填写正确的网址");
    		}
    		else 
    			this.addFieldError("hospital.siteUrl", "医院网址在255");
    	}
		if(StringUtils.isNotBlank(hospital.getEmail())) {
    		if(!ValidateUtils.isLengOut(hospital.getEmail(), 50)) {	
    			if(!ValidateUtils.isEmail(hospital.getEmail(), 6, 50))
    				this.addFieldError("hospital.email", "请填写正确的邮箱");
    		}
    		else 
    			this.addFieldError("hospital.email", "邮箱长度在50字以内");
		}
		if(StringUtils.isNotBlank(hospital.getFax())) {
			if(!ValidateUtils.isPhone(hospital.getFax()))
				this.addFieldError("hospital.fax", "请填写正确的传真号");
		}
		if(StringUtils.isNotBlank(hospital.getPostcode())) {
			if(!ValidateUtils.isLengOut(hospital.getPostcode(), 6)) {
    			if(!ValidateUtils.isPostCode(hospital.getPostcode()))
    				this.addFieldError("hospital.postcode", "请输入正确的邮政编码");
			}
			else this.addFieldError("hospital.postcode", "请输入正确的邮政编码");
		}
		
		if(null==hospital.getAreaId())
			this.addFieldError("hospital.areaId", "请选择医院所在省市区");
		
		if(hospital.getAreaId().equals(0))
			this.addFieldError("hospital.areaId", "请选择医院所在省市区");
		
		if(ValidateUtils.isLengOut(hospital.getLogoImg(), 255))
			this.addFieldError("hospital.logoImg","LOGO图片长度在255字以内");
		
		if(ValidateUtils.isLengOut(hospital.getHospImg(),255))
			this.addFieldError("hospital.hospImg","形象图片长度在255字以内");
		
		if(ValidateUtils.isLengOut(hospital.getBusLine(), 2000))
			this.addFieldError("hospital.busLine","BUS线路长度在2000字以内");
		
		if(ValidateUtils.isLengOut(hospital.getMapImg(),255))
			this.addFieldError("hospital.mapImg", "医院地图长度在255字以内");
		
		if(this.hasFieldErrors()) 
			getList();
	}
	private void getList() {
		natureList=typeService.findByType(true,ElianUtils.HOSP_NATURE);
		hospTypeList=typeService.findByType(true,ElianUtils.HOSP_TYPE);
		rankList=typeService.findByType(true,ElianUtils.HOSP_RANK);
	}
	
	
	public String getAreaNames(Integer areaCode) {
	    List<String> list = new ArrayList<String>();
		List<Area> area = areaService.findAreaNameByAreaCode(areaCode);
		for (int i = 0, len = area.size(); i < len; i++) {
			list.add(area.get(i).getAreaName());
		}
		for (int i = list.size() - 1; i >= 0; i--) {
			areaName += list.get(i) + " ";
		}
		return areaName;
	}
	
	public List<Type> getNatureList() {
		return natureList;
	}

	public void setNatureList(List<Type> natureList) {
		this.natureList = natureList;
	}
	
	public List<Type> getHospTypeList() {
		return hospTypeList;
	}

	public void setHospTypeList(List<Type> hospTypeList) {
		this.hospTypeList = hospTypeList;
	}

	public List<Type> getRankList() {
		return rankList;
	}

	public void setRankList(List<Type> rankList) {
		this.rankList = rankList;
	}
	
	public Pagination<Hospital> getPagination() {
		return pagination;
	}
	
	public void setPagination(Pagination<Hospital> pagination) {
		this.pagination = pagination;
	}
	
	public int getId() {
		return id;
	}
	
	public void setId(int id) {
		this.id = id;
	}
	
	public boolean isEdit() {
		return edit;
	}
	
	public void setEdit(boolean edit) {
		this.edit = edit;
	}
	
	public Hospital getHospital() {
		return hospital;
	}
	
	public void setHospital(Hospital hospital) {
		this.hospital = hospital;
	}
	
	public HospitalService getHospitalService() {
		return hospitalService;
	}

	public String getAreaName() {
		return areaName;
	}

	public void setAreaName(String areaName) {
		this.areaName = areaName;
	}	
	
    public boolean isLeaf() {
		return leaf;
	}

	public void setLeaf(boolean leaf) {
		this.leaf = leaf;
	}

	public String getAction() {
		return action;
	}

	public void setAction(String action) {
		this.action = action;
	}

	public Integer getChannelId() {
		return channelId;
	}

	public void setChannelId(Integer channelId) {
		this.channelId = channelId;
	}
	
	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}
	
	public boolean isMyHosp() {
		return myHosp;
	}

	public void setMyHosp(boolean myHosp) {
		this.myHosp = myHosp;
	}
	
	public Integer getAreaCode() {
		return areaCode;
	}

	public void setAreaCode(Integer areaCode) {
		this.areaCode = areaCode;
	}
	
	public String getParamId() {
		return paramId;
	}

	public void setParamId(String paramId) {
		this.paramId = paramId;
	}

	public String getRequType() {
		return requType;
	}
	public void setRequType(String requType) {
		this.requType = requType;
	}

	public boolean isZtree() {
		return ztree;
	}
	public void setZtree(boolean ztree) {
		this.ztree = ztree;
	}

	public String getHospName() {
		return hospName;
	}
	public void setHospName(String hospName) {
		this.hospName = hospName;
	}

	private SiteFileService siteFileService;
	@Resource
	public void setSiteFileService(SiteFileService siteFileService) {
		this.siteFileService = siteFileService;
	}
	
	@Resource
	public void setHospitalService(HospitalService hospitalService) {
		this.hospitalService = hospitalService;
	}
	
	@Resource
	public void setTypeService(TypeService typeService) {
		this.typeService = typeService;
	}
	
	@Resource
	public void setAreaService(AreaService areaService) {
		this.areaService = areaService;
	}
	
	@Resource
	public void setDepartmentService(DepartmentService departmentService) {
		this.departmentService = departmentService;
	}
	
	@Resource
	public void setDoctorService(DoctorService doctorService) {
		this.doctorService = doctorService;
	}
}
