package com.elian.cms.front.action;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.apache.lucene.document.Document;
import org.apache.lucene.index.Term;
import org.apache.lucene.queryParser.MultiFieldQueryParser;
import org.apache.lucene.queryParser.QueryParser;
import org.apache.lucene.search.BooleanClause;
import org.apache.lucene.search.BooleanQuery;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.TermQuery;
import org.apache.lucene.search.BooleanClause.Occur;
import org.apache.lucene.util.Version;
import org.springframework.stereotype.Component;

import com.elian.cms.admin.model.Area;
import com.elian.cms.admin.model.Site;
import com.elian.cms.admin.model.Type;
import com.elian.cms.admin.service.AreaService;
import com.elian.cms.admin.service.SiteService;
import com.elian.cms.admin.service.SubstationService;
import com.elian.cms.admin.service.TypeService;
import com.elian.cms.syst.listener.HospitalIndexInterface;
import com.elian.cms.syst.util.ElianCodes;
import com.elian.cms.syst.util.ElianUtils;
import com.elian.cms.syst.util.FreemarkerCodes;
import com.elian.cms.syst.util.IndexUtils;
import com.elian.cms.syst.util.StringUtils;

/**
 * 搜索医院Action
 * 
 * @author Joe
 * 
 */
@Component
public class SearchHospitalAction_bak extends BaseSearchAction implements
		HospitalIndexInterface, Serializable {
	private static final long serialVersionUID = -1762884220378192162L;

	/** 搜索条件 */
	private String type;// 搜索选项
	private String keyword;// 关键字
	private Integer hospRank=null;
	private Integer hospType=null;
	private Integer parentAreaCode=null;
	private Integer subAreaCode=null;
	
	private TypeService typeService=null;
	private AreaService areaService=null;
	private SiteService siteService=null;
	private SubstationService substationService=null;
	
	private List<Type> rankList=null;
	private List<Type> typeList=null;
	public List<Area> parentAreaList=null;

	/**
	 * 搜索页面方法
	 */
	@Override
	public String search() {
		try {
			IndexSearcher searcher = IndexUtils.getSearcher();
			BooleanQuery bq =createInitQuery();
			if (addQuery(bq))
				addResultInPagination(searcher, bq);
			else
				pagination.setPageNo(1);
			searcher.close();
		}
		catch (Exception e) {
			logger.error("搜索出错!", e);
		}
		return LIST;
	}
	
	@Override
	public boolean addQuery(BooleanQuery bq) throws Exception {
		boolean hasCondition = false;
		Query q = null;
		if (StringUtils.isNotBlank(keyword)) {
			q = MultiFieldQueryParser.parse(Version.LUCENE_35, keyword,
					new String[] { NAME,SUMMARY, ADDRESS},
					new BooleanClause.Occur[] { BooleanClause.Occur.SHOULD,
							BooleanClause.Occur.SHOULD,BooleanClause.Occur.SHOULD }, IndexUtils
							.getAnalyzer());
			bq.add(q, BooleanClause.Occur.MUST);
			hasCondition = true;
		}
		if(hospRank!=null && hospRank!=0){
			q=new TermQuery(new Term(RANK, hospRank.toString()));
			bq.add(q, BooleanClause.Occur.MUST);
			hasCondition = true;
		}
		if(hospType!=null && hospType!=0){
			q=new TermQuery(new Term(TYPE, hospType.toString()));
			bq.add(q, BooleanClause.Occur.MUST);
			hasCondition = true;
		}
		if(subAreaCode!=null || parentAreaCode!=null ){
			hasCondition = true;
			int areaId=(subAreaCode==null || subAreaCode==0)?parentAreaCode:subAreaCode;
			List<Integer> list= areaService.findChirdByParent(areaId);
			StringBuffer buf=new StringBuffer();
			for(int i=0;i<list.size();i++){
				buf.append(list.get(i)+" ");
			}
			QueryParser qp = new QueryParser(Version.LUCENE_35,AREAID, IndexUtils.getAnalyzer());
			q=qp.parse(buf.toString());
			bq.add(q, BooleanClause.Occur.MUST);
		}
		return hasCondition;
	}

	@Override
	public SearchModel createSearchModel(Document doc, Query query) {
		SearchModel model = new SearchModel();
		model.setTitle(doc.get(NAME));
		model.setSummary(doc.get(SUMMARY));
		model.setPath("http://hosp"+FreemarkerCodes.DOMAIN_SUFFIX+ElianCodes.SPRIT+doc.get(SITE_ID));
		model.setAddress(doc.get(ADDRESS));
		model.setImgPath(doc.get(LOGO));
		highlightModel(model, query);
		return model;
	}

	/**
	 * 创建初始化查询
	 */
	private BooleanQuery createInitQuery() {
		BooleanQuery booleanQuery = new BooleanQuery();
		Query q = null;
		// 添加内容静态化条件
		q = new TermQuery(new Term(ACTION_NAME, "hospital_c"));
		booleanQuery.add(q,Occur.MUST);
		q = new TermQuery(new Term(STATUS, ElianUtils.STATUS_1+""));
		booleanQuery.add(q,Occur.MUST);
		return booleanQuery;
	}
	
	/**
	 * 高亮显示数据
	 */
	public void highlightModel(SearchModel model, Query query) {
		if (StringUtils.isNotBlank(type)) {
			if (NAME.equals(type)) {
				model.setTitle(lighterStr(keyword, model.getTitle()));
			}
			else if (ADDRESS.equals(type)) {
				model.setAddress(lighterStr(keyword, model.getAddress()));
			}
		}
		else {
			model.setTitle(lighterStr(keyword, model.getTitle()));
			model.setAddress(lighterStr(keyword, model.getAddress()));
		}
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
		getParentAreaList();
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
	
	public List<Area> getSubAreaList() {
		if(parentAreaCode != null && parentAreaCode!=0)
			return areaService.findByParentCode(parentAreaCode);
		return null;
	}
	
	@Override
	public void setSiteId(Integer siteId) {
		if(siteId==null)	siteId=1;
		super.setSiteId(siteId);
	}
	
	public String getBasePath(){
		return "http://hosp"+FreemarkerCodes.DOMAIN_SUFFIX+ElianCodes.SPRIT;
	}
}
