package com.elian.cms.admin.action;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import com.elian.cms.admin.model.Area;
import com.elian.cms.admin.model.Channel;
import com.elian.cms.admin.model.Content;
import com.elian.cms.admin.model.Department;
import com.elian.cms.admin.model.Doctor;
import com.elian.cms.admin.model.Hospital;
import com.elian.cms.admin.model.Substation;
import com.elian.cms.admin.service.AreaService;
import com.elian.cms.admin.service.ChannelService;
import com.elian.cms.admin.service.ContentService;
import com.elian.cms.admin.service.DepartmentService;
import com.elian.cms.admin.service.DoctorService;
import com.elian.cms.admin.service.HospitalService;
import com.elian.cms.admin.service.SubstationService;
import com.elian.cms.syst.action.BaseAction;
import com.elian.cms.syst.util.ApplicationUtils;
import com.elian.cms.syst.util.ElianUtils;

/**
 * 资讯
 * 
 * @author Joe
 * 
 */
@Component
@Scope("prototype")
public class DoctorImportAction extends BaseAction {
	private static final long serialVersionUID = -350673921164039598L;

	/** 树节点传递过来的是否叶子节点(内容类型节点) */
	private boolean isLeaf = false;
	/** 树节点传递过来的栏目ID */
	private Integer channelId = Integer.valueOf(0);
	/** 树节点传递过来的action名称 */
	private String action;
	/** 内容状态 */
	private Integer status = Integer.valueOf(1);
	/** 模型的组织类型是否为：公有 */
	private boolean isPublic = true;
	/** 判断如果是单页只能添加一条数据 */
	private Boolean isSingle;

	private DoctorService doctorService;
	private DepartmentService departmentService;
	private ContentService contentService;
	private ChannelService channelService;
	private HospitalService hospitalService;
	private AreaService areaService;
	private SubstationService substationService;

	public String edit() {
		return EDIT;
	}

	public String save() {
		List<Integer> idList = ApplicationUtils.getAjaxIds();
		if (CollectionUtils.isEmpty(idList))
			return SAVE;

		removeRepeatData(idList);
		if (idList.size() < 1)
			return SAVE;

		List<Doctor> doctorList = doctorService.get(idList);
		if (CollectionUtils.isEmpty(doctorList))
			return SAVE;

		Channel channel = channelService.get(channelId);
		// 如果栏目是单页的时候，只为第一条数据引入内容
		if (ElianUtils.CONTENT_SINGLE.equals(channel.getContentType())) {
			doctorList = doctorList.subList(0, 1);
		}

		Content temp = createContent();
		for (Doctor d : doctorList) {
			Content content = temp.clone();
			content.setEntityId(d.getId());
			content.setTitle(d.getDoctName());
			content.setActionName(action);
			content.setChannel(channel);

			contentService.save(content, d, false);
		}
		return SAVE;
	}

	/**
	 * 排除重复的内容信息
	 */
	private void removeRepeatData(List<Integer> idList) {
		List<Content> contentList = contentService.findByIdsAndAction(
				channelId, idList, action);
		if (!CollectionUtils.isEmpty(contentList)) {
			for (Content c : contentList) {
				idList.remove(c.getEntityId());
			}
		}
	}

	private Content createContent() {
		Content content = new Content();
		content.setSite(ApplicationUtils.getSite());
		content.setStatus(1);
		content.setSort(99);
		content.setCreater(ApplicationUtils.getUser().getRealName());
		content.setCreateTime(new Date());
		return content;
	}
	
	/**
	 * 树结构数据类型
	 */
	private static final String AREA = "area";
	private static final String HOSPITAL = "hospital";
	private static final String DEPARTMENT = "department";
	private static final String DOCTOR = "doctor";

	public void tree() {
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		if (AREA.equals(ApplicationUtils.getRequest().getParameter("type"))) {
			List<Hospital> hospitalList = loadHospitalList(ApplicationUtils
					.getAjaxId());
			addHospitalNode(hospitalList, list);
		}
		else if (HOSPITAL.equals(ApplicationUtils.getRequest().getParameter(
				"type"))) {
			List<Department> departmentList = departmentService.findPageByHosp(
					null, ApplicationUtils.getAjaxId());
			addDepartmentNode(departmentList, list);
		}
		else if (DEPARTMENT.equals(ApplicationUtils.getRequest().getParameter(
				"type"))) {
			List<Doctor> doctorList = doctorService.findByPage(null, null,
					ApplicationUtils.getAjaxId());
			addDoctorNode(doctorList, list);
		}
		else {
			List<Area> areaList = loadAreaList();
			addAreaNode(areaList, list);
		}
		ApplicationUtils.sendJsonArray(list);
	}
	
	private void addDepartmentNode(List<Department> departmentList,
			List<Map<String, Object>> list) {
		if (CollectionUtils.isEmpty(departmentList))
			return;
		for (Department d : departmentList) {
			list.add(createMapData(DEPARTMENT, d.getId(), d.getDeptName(),
					true));
		}
	}
	
	private void addDoctorNode(List<Doctor> doctorList,
			List<Map<String, Object>> list) {
		if (CollectionUtils.isEmpty(doctorList))
			return;
		for (Doctor d : doctorList) {
			list.add(createMapData(DOCTOR, d.getId(), d.getDoctName(), false));
		}
	}

	private List<Area> loadAreaList() {
		List<Area> areaList = null;
		if (ElianUtils.COMP_TYPE_MAIN.equals(ApplicationUtils.getSite()
				.getComType())) {
			areaList = areaService.initializationArea();
		}
		else if (ElianUtils.COMP_TYPE_SUBS.equals(ApplicationUtils.getSite()
				.getComType())) {
			areaList = new ArrayList<Area>(1);
			Substation sb = substationService.get(ApplicationUtils.getSite()
					.getComId());
			if (sb != null)
				areaList.add(areaService.get(sb.getAreaId()));
		}
		else if (ElianUtils.COMP_TYPE_HOSP.equals(ApplicationUtils.getSite()
				.getComType())) {
			List<Area> list = areaService
					.findAreaNameByAreaCode(ApplicationUtils.getHospital()
							.getAreaId());
			if (!CollectionUtils.isEmpty(list)) {
				areaList = list.subList(list.size()-1, list.size());
			}
		}
		return areaList;
	}

	private void addAreaNode(List<Area> areaList, List<Map<String, Object>> list) {
		if (CollectionUtils.isEmpty(areaList))
			return;
		for (Area a : areaList) {
			list.add(createMapData(AREA, a.getAreaCode(), a.getAreaName(),
							true));
		}
	}

	private List<Hospital> loadHospitalList(Integer id) {
		List<Hospital> hospitalList = null;
		if (ElianUtils.COMP_TYPE_SUBS.equals(ApplicationUtils.getSite()
				.getComType())
				|| ElianUtils.COMP_TYPE_MAIN.equals(ApplicationUtils.getSite()
						.getComType())) {
			List<Integer> areaCodes=areaService.findChirdByParent(id);
			hospitalList=hospitalService.findByAreaCode(areaCodes,null);
		}
		else if (ElianUtils.COMP_TYPE_HOSP.equals(ApplicationUtils.getSite()
				.getComType())) {
			hospitalList = new ArrayList<Hospital>(1);
			hospitalList.add(ApplicationUtils.getHospital());
		}
		return hospitalList;
	}

	private void addHospitalNode(List<Hospital> hospitalList,
			List<Map<String, Object>> list) {
		if (CollectionUtils.isEmpty(hospitalList))
			return;
		for (Hospital h : hospitalList) {
			list.add(createMapData(HOSPITAL, h.getId(), h.getHospName(),
							true));
		}
	}

	private Map<String, Object> createMapData(String type, Integer areaCode,
			String name, boolean isParent) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("ID", areaCode);
		map.put("NAME", name);
		map.put("IS_PARENT", isParent);
		map.put("TYPE", type);
		return map;
	}

//	public void tree() {
//		String areaCode = ApplicationUtils.getRequest().getParameter("areaCode");
//		System.err.println(areaCode);
//		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
//		List<Doctor> doctorList = doctorService.findPageByHosp(null,
//				ApplicationUtils.getHospital().getId());
//		Set<Integer> doctorSet = new HashSet<Integer>();
//		addDoctorNode(doctorList, doctorSet, list);
//		addDeptNode(doctorSet, list);
//		ApplicationUtils.sendJsonArray(list);
//	}
//
//	private void addDeptNode(Set<Integer> doctorSet,
//			List<Map<String, Object>> list) {
//		if (CollectionUtils.isEmpty(doctorSet))
//			return;
//		List<Department> deptList = departmentService.get(doctorSet);
//		if (CollectionUtils.isEmpty(deptList))
//			return;
//		for (Department dept : deptList) {
//			list.add(createMapData(dept.getId(), dept.getDeptName(), 0));
//		}
//	}
//
//	private void addDoctorNode(List<Doctor> doctorList, Set<Integer> doctorSet,
//			List<Map<String, Object>> list) {
//		if (CollectionUtils.isEmpty(doctorList))
//			return;
//		for (Doctor d : doctorList) {
//			if (d.getDept() != null) {
//				doctorSet.add(d.getDept().getId());
//				list.add(createMapData(d.getId(), d.getDoctName(), d.getDept()
//						.getId()));
//			}
//		}
//	}
//
//	private Map<String, Object> createMapData(Integer id, String name,
//			Integer pId) {
//		Map<String, Object> map = new HashMap<String, Object>();
//		map.put("ID", id);
//		map.put("NAME", name);
//		map.put("PID", pId);
//		map.put("IS_PARENT", true);
//		map.put("AREA_CODE", 3);
//		return map;
//	}

	public boolean isLeaf() {
		return isLeaf;
	}

	public void setLeaf(boolean isLeaf) {
		this.isLeaf = isLeaf;
	}

	public Integer getChannelId() {
		return channelId;
	}

	public void setChannelId(Integer channelId) {
		this.channelId = channelId;
	}

	public String getAction() {
		return action;
	}

	public void setAction(String action) {
		this.action = action;
	}
	
	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public boolean isPublic() {
		return isPublic;
	}

	public void setPublic(boolean isPublic) {
		this.isPublic = isPublic;
	}

	public boolean isSingle() {
		return isSingle;
	}

	public void setSingle(boolean isSingle) {
		this.isSingle = isSingle;
	}

	@Resource
	public void setDoctorService(DoctorService doctorService) {
		this.doctorService = doctorService;
	}

	@Resource
	public void setDepartmentService(DepartmentService departmentService) {
		this.departmentService = departmentService;
	}

	@Resource
	public void setContentService(ContentService contentService) {
		this.contentService = contentService;
	}

	@Resource
	public void setChannelService(ChannelService channelService) {
		this.channelService = channelService;
	}
	
	@Resource
	public void setHospitalService(HospitalService hospitalService) {
		this.hospitalService = hospitalService;
	}
	
	@Resource
	public void setAreaService(AreaService areaService) {
		this.areaService = areaService;
	}
	
	@Resource
	public void setSubstationService(SubstationService substationService) {
		this.substationService = substationService;
	}

}
