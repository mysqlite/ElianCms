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
import com.elian.cms.admin.model.Company;
import com.elian.cms.admin.model.Content;
import com.elian.cms.admin.model.Instrument;
import com.elian.cms.admin.model.Substation;
import com.elian.cms.admin.service.AreaService;
import com.elian.cms.admin.service.ChannelService;
import com.elian.cms.admin.service.CompanyService;
import com.elian.cms.admin.service.ContentService;
import com.elian.cms.admin.service.InstrumentService;
import com.elian.cms.admin.service.SubstationService;
import com.elian.cms.syst.action.BaseAction;
import com.elian.cms.syst.util.ApplicationUtils;
import com.elian.cms.syst.util.ElianUtils;

/**
 * 器械列表的引入
 * 
 * @author thy
 * 
 */
@Component
@Scope("prototype")
public class InstrumentImportAction extends BaseAction {
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

	private InstrumentService instrumentService;
	private ContentService contentService;
	private ChannelService channelService;
	private CompanyService companyService;
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

		List<Instrument> instrumentList = instrumentService.get(idList);
		if (CollectionUtils.isEmpty(instrumentList))
			return SAVE;

		Channel channel = channelService.get(channelId);
		// 如果栏目是单页的时候，只为第一条数据引入内容
		if (ElianUtils.CONTENT_SINGLE.equals(channel.getContentType())) {
			instrumentList = instrumentList.subList(0, 1);
		}

		Content temp = createContent();
		for (Instrument d : instrumentList) {
			Content content = temp.clone();
			content.setEntityId(d.getId());
			content.setTitle(d.getCnName());
			content.setActionName(action);
			content.setChannel(channel);

			contentService.save(content, d, false);
		}
		return SAVE;
	}
	
	private void removeRepeatData(List<Integer> idList){
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
	private static final String COMPANY = "company";
	private static final String INSTRUMENT = "instrument";

	public void tree() {
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		if (AREA.equals(ApplicationUtils.getRequest().getParameter("type"))) {
			List<Company> companyList = loadCompanyList(ApplicationUtils
					.getAjaxId());
			addCompanyNode(companyList, list);
		}
		else if (COMPANY.equals(ApplicationUtils.getRequest().getParameter(
				"type"))) {
			List<Instrument> instrumentList = instrumentService.findByAll(
					ApplicationUtils.getAjaxId(), null);
			addInstrumentNode(instrumentList, list);
		}
		else {
			List<Area> areaList = loadAreaList();
			addAreaNode(areaList, list);
		}
		ApplicationUtils.sendJsonArray(list);
	}
	
	private void addInstrumentNode(List<Instrument> instrumentList,
			List<Map<String, Object>> list) {
		if (CollectionUtils.isEmpty(instrumentList))
			return;
		for (Instrument d : instrumentList) {
			list.add(createMapData(INSTRUMENT, d.getId(), d.getCnName(),
					false));
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
		else if (ApplicationUtils.getSite()
				.getComType().startsWith(ElianUtils.COMP_TYPE_COMP)) {
			List<Area> list = areaService
					.findAreaNameByAreaCode(ApplicationUtils.getCompany().getArea()
							.getId());
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

	private List<Company> loadCompanyList(Integer id) {
		List<Company> companyList = null;
		if (ElianUtils.COMP_TYPE_SUBS.equals(ApplicationUtils.getSite()
				.getComType())
				|| ElianUtils.COMP_TYPE_MAIN.equals(ApplicationUtils.getSite()
						.getComType())) {
			companyList=companyService.getByAreaId(id, null,null);
		}
		else if (ApplicationUtils.getSite()
				.getComType().startsWith(ElianUtils.COMP_TYPE_COMP)) {
			companyList = new ArrayList<Company>(1);
			companyList.add(ApplicationUtils.getCompany());
		}
		return companyList;
	}

	private void addCompanyNode(List<Company> companyList,
			List<Map<String, Object>> list) {
		if (CollectionUtils.isEmpty(companyList))
			return;
		for (Company h : companyList) {
			list.add(createMapData(COMPANY, h.getId(), h.getName(),
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

//	@Resource
//	public void setDeptTypeService(DeptTypeService deptTypeService) {
//		this.deptTypeService = deptTypeService;
//	}

	@Resource
	public void setInstrumentService(InstrumentService instrumentService) {
		this.instrumentService = instrumentService;
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
	public void setCompanyService(CompanyService companyService) {
		this.companyService = companyService;
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
