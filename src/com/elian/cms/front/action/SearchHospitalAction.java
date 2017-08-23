package com.elian.cms.front.action;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import com.elian.cms.admin.model.Area;
import com.elian.cms.admin.model.Hospital;
import com.elian.cms.admin.model.Site;
import com.elian.cms.admin.model.Type;
import com.elian.cms.admin.service.AreaService;
import com.elian.cms.admin.service.HospitalService;
import com.elian.cms.admin.service.SiteService;
import com.elian.cms.admin.service.SubstationService;
import com.elian.cms.admin.service.TypeService;
import com.elian.cms.syst.action.BaseAction;
import com.elian.cms.syst.model.Pagination;
import com.elian.cms.syst.util.ElianCodes;
import com.elian.cms.syst.util.ElianUtils;
import com.elian.cms.syst.util.FreemarkerCodes;
import com.elian.cms.syst.util.StringUtils;

/**
 * 搜索医院Action
 * 
 * @author Joe
 * 
 */
@Component
@Scope("prototype")
public class SearchHospitalAction extends BaseAction implements Serializable {
	private static final long serialVersionUID = -1762884220378192162L;

	/** 搜索条件 */
	private String type;// 搜索选项
	private String keyword;// 关键字
	private Integer hospRank=null;
	private Integer hospType=null;
	private Integer siteId=null;
	private Integer parentAreaCode=null;
	private Integer subAreaCode=null;
	private Pagination<Hospital> pagination=new Pagination<Hospital>();
	
	private TypeService typeService=null;
	private AreaService areaService=null;
	private SiteService siteService=null;
	private SubstationService substationService=null;
	private HospitalService hospitalService=null;

	
	private List<Type> rankList=null;
	private List<Type> typeList=null;
	public List<Area> parentAreaList=null;
	public List<Site> siteList=new ArrayList<Site>(10);
	
	
	public String list() {
		return LIST;
	}
	
	/**
	 * 搜索页面方法
	 */
	public String search() {
		String criteria=createCriteria();
		hospitalService.search(criteria,null,pagination);
		if(!CollectionUtils.isEmpty(pagination.getList())){
			for(Hospital hosp:pagination.getList()){
				Site s= siteService.findByByComp(ElianUtils.COMP_TYPE_HOSP, hosp.getId());
				siteList.add(s);
			}
		}
		return LIST;
	}
	
	private String createCriteria(){
		StringBuilder criteria=new StringBuilder();
		if(subAreaCode!=null || parentAreaCode!=null ){
			int areaId=(subAreaCode==null || subAreaCode==0)?parentAreaCode:subAreaCode;
			List<Integer> list= areaService.findChirdByParent(areaId);
			StringBuffer buf=new StringBuffer();
			for(int i=0;i<list.size();i++){
				buf.append(list.get(i));
				if(i!=list.size()-1) buf.append(",");
			}
			criteria.append(" and H.AREA_ID in(");		
			criteria.append(buf.toString());
			criteria.append("	)");
		}
		if(hospRank!=null && 0!=hospRank)
			criteria.append(" and H.hosp_rank=").append(hospRank);
		if(hospType!=null && 0!=hospType)
			criteria.append(" and H.HOSP_TYPE=").append(hospType);
		if(StringUtils.isNotBlank(keyword))
			criteria.append(" and H.HOSP_NAME like('%").append(keyword).append("%')");
		criteria.append(" and exists (select 1 from t_site s where s.status=1 and s.com_type='hospital' and s.com_id=H.hosp_id)");
		return criteria.toString();
	}
	
	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getKeyword() {
		return keyword;
	}

	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}

	public Integer getHospRank() {
		return hospRank;
	}

	public void setHospRank(Integer hospRank) {
		this.hospRank = hospRank;
	}

	public Integer getHospType() {
		return hospType;
	}

	public void setHospType(Integer hospType) {
		this.hospType = hospType;
	}
	
	public Integer getParentAreaCode() {
		return parentAreaCode;
	}

	public void setParentAreaCode(Integer parentAreaCode) {
		this.parentAreaCode = parentAreaCode;
	}

	public Integer getSubAreaCode() {
		return subAreaCode;
	}

	public void setSubAreaCode(Integer subAreaCode) {
		this.subAreaCode = subAreaCode;
	}
	
	public Pagination<Hospital> getPagination() {
		return pagination;
	}


	public void setPagination(Pagination<Hospital> pagination) {
		this.pagination = pagination;
	}
	
	public Integer getSiteId() {
		return siteId;
	}

	public void setSiteId(Integer siteId) {
		this.siteId = siteId;
	}

	public List<Site> getSiteList() {
		return siteList;
	}

	public void setSiteList(List<Site> siteList) {
		this.siteList = siteList;
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
	public void setSiteService(SiteService siteService) {
		this.siteService = siteService;
	}
	
	@Resource
	public void setSubstationService(SubstationService substationService) {
		this.substationService = substationService;
	}

	@Resource
	public void setHospitalService(HospitalService hospitalService) {
		this.hospitalService = hospitalService;
	}

	public List<Type> getHospRankList(){
		if(rankList==null)
			rankList=typeService.findByType(true,ElianUtils.HOSP_RANK);
		return rankList;
	}
	
	public List<Type> getHospTypeList(){
		if(typeList==null)
			typeList=typeService.findByType(true,ElianUtils.HOSP_TYPE);
		return typeList;
	}
	
	public List<Area> getSubAreaList() {
		if(parentAreaCode != null && parentAreaCode!=0)
			return areaService.findByParentCode(parentAreaCode);
		return null;
	}
	
	public List<Area> getParentAreaList() {
		if(parentAreaList==null){
			if(siteId==null) siteId=1;
			Site site= siteService.get(siteId);
			if(site.getComType().equals(ElianUtils.COMP_TYPE_MAIN))
				parentAreaList= areaService.findByParentCode(0);
			if(site.getComType().equals(ElianUtils.COMP_TYPE_SUBS)){
				parentAreaCode=substationService.get(site.getComId()).getAreaId();
				Area area= areaService.get(parentAreaCode);
				parentAreaList=new ArrayList<Area>(1);
				parentAreaList.add(area);
			}
		}
		return parentAreaList;
	}
	
	public String getBasePath(){
		return "http://hosp"+FreemarkerCodes.DOMAIN_SUFFIX+ElianCodes.SPRIT;
	}
}