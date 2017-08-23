package com.elian.cms.front.action;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import net.sf.json.JSONObject;

import org.springframework.beans.BeanUtils;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import com.elian.cms.admin.model.Area;
import com.elian.cms.admin.model.Company;
import com.elian.cms.admin.model.Site;
import com.elian.cms.admin.service.AreaService;
import com.elian.cms.admin.service.CompanyService;
import com.elian.cms.admin.service.SiteService;
import com.elian.cms.admin.service.SubstationService;
import com.elian.cms.syst.action.BaseAction;
import com.elian.cms.syst.model.Pagination;
import com.elian.cms.syst.util.ApplicationUtils;
import com.elian.cms.syst.util.ElianCodes;
import com.elian.cms.syst.util.ElianUtils;
import com.elian.cms.syst.util.FilePathUtils;
import com.elian.cms.syst.util.FreemarkerCodes;
import com.elian.cms.syst.util.StringUtils;

/**
 * 搜索企业Action
 * 
 * @author thy
 * 
 */
@Component
@Scope("prototype")
public class SearchCompanyAction extends BaseAction implements Serializable {
	private static final long serialVersionUID = -1762884220378192162L;

	/** 搜索条件 */
	private String type;// 搜索选项
	private String keyword;// 关键字
	private Integer siteId=null;
	private Integer parentAreaCode=null;
	private Integer subAreaCode=null;
	private Pagination<Company> pagination=new Pagination<Company>();
	public List<Integer> siteIdList=new ArrayList<Integer>(10);
	private Integer rowSize=10;
	private Integer pageNo=1;
	
	private AreaService areaService=null;
	private SiteService siteService=null;
	private CompanyService companyService=null;
	private SubstationService substationService=null;
	
	public List<Area> parentAreaList=null;
	
	public String list(){
		return LIST;
	}
	/**
	 * 搜索页面方法
	 */
	public String search() {
		String criteria=createCriteria();
		companyService.search(criteria,null,pagination);
		if(!CollectionUtils.isEmpty(pagination.getList())){
			for(Company comp:pagination.getList()){
				Site site=siteService.findByByComp(ElianUtils.COMP_TYPE_COMP, comp.getId());
				if(site==null)		
					site=siteService.findByByComp(ElianUtils.COMP_TYPE_MEDICINE_COMP, comp.getId());
				if(site==null)	
					site=siteService.findByByComp(ElianUtils.COMP_TYPE_INSTRUMENT_COMP, comp.getId());
				siteIdList.add(site.getId());
			}
		}
		return LIST;
	}
	
	public void searchJson() {
		pagination.setRowSize(rowSize);
		pagination.setPageNo(pageNo);
		search();
		List<Company> compList=new ArrayList<Company>(rowSize);
		Company newCompany=null;
		for (Company comp : pagination.getList()) {
			newCompany=new Company();
			BeanUtils.copyProperties(comp, newCompany);
			newCompany.setCompanyImg(FilePathUtils.setOutFilePath(comp.getCompanyImg()));
			compList.add(newCompany);
		}
		JSONObject obj = new JSONObject();
		obj.put("compList", compList);
		obj.put("page", pagination.getPageInfo());
		ApplicationUtils.sendJsonpObj(obj);
	}
	
	
	
	public void areaList() {
		List<Area> area=areaService.findByParentCode(0);
		ApplicationUtils.sendJsonpList(area);
	}
	
	private String createCriteria() {
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
		if(StringUtils.isNotBlank(keyword))
			criteria.append(" and H.full_name like('%").append(keyword).append("%')");
		criteria.append(" and exists (select 1 from t_site s where s.status=1 and s.com_type like 'company%' and s.com_id=H.company_id)");
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
	
	public void setRowSize(Integer rowSize) {
		this.rowSize = rowSize;
	}
	
	public Integer getPageNo() {
		return pageNo;
	}
	public void setPageNo(Integer pageNo) {
		this.pageNo = pageNo;
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

	public Pagination<Company> getPagination() {
		return pagination;
	}


	public void setPagination(Pagination<Company> pagination) {
		this.pagination = pagination;
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
	public void setCompanyService(CompanyService companyService) {
		this.companyService = companyService;
	}

	@Resource
	public void setSubstationService(SubstationService substationService) {
		this.substationService = substationService;
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
		return "http://comp"+FreemarkerCodes.DOMAIN_SUFFIX+ElianCodes.SPRIT;
	}
	
}