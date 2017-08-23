package com.elian.cms.front.action;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import com.elian.cms.admin.model.DrugCommon;
import com.elian.cms.admin.service.DrugCommonService;
import com.elian.cms.syst.action.BaseAction;
import com.elian.cms.syst.listener.DrugCommonIndexInterface;
import com.elian.cms.syst.model.Pagination;
import com.elian.cms.syst.util.StringUtils;

/**
 * 搜索药品Action
 * 
 * @author Joe
 * 
 */
@Component
@Scope("prototype")
public class SearchDrugCommonAction extends BaseAction implements
		DrugCommonIndexInterface, Serializable {
	private static final long serialVersionUID = -1665395475626644764L;
	
	/** 分页对象 */
	private Pagination<DrugCommon> pagination = new Pagination<DrugCommon>();
	private List<SearchModel> modelList = new ArrayList<SearchModel>();
	
	private Integer id;
	private DrugCommon drugCommon;
	
	/** 搜索条件 */
	private String drugType;//药品分类
	private String type = NAME;// 搜索选项
    private String keyword;//关键字
    private DrugCommonService drugCommonService;
    
    /**
	 * 初始化搜索页面方法
	 */
	public String list() {
		return LIST;
	}
    
    /**
	 * 搜索页面方法
	 */
	public String search() {
		if(pagination.getPageNo()>100){
			pagination.setOffsetSize(2);
		}
//		if (StringUtils.isNotBlank(keyword)) {
			addDataInModelList();
//		}
//		else{
//			pagination.setPageNo(1);
//		}
		return LIST;
	}
	
	public void addDataInModelList() {
		if (NAME.equals(type))
			drugCommonService.findByAll(keyword, null, drugType, pagination);
		else if (MARJOR_FUNCTION.equals(type))
			drugCommonService.findByAll(null, keyword, drugType, pagination);

		if (CollectionUtils.isEmpty(pagination.getList())) 
			return;
		for(DrugCommon drug : pagination.getList()){
			SearchModel model = new SearchModel();
			model.setTitle(drug.getPdtName());
			model.setSummary(drug.getMarjorFunc());
			model.setPath("front/searchDrugCommon!show.action?id="+drug.getId());
			highlightModel(model);
			modelList.add(model);
		}
	}
	
	/**
	 * 高亮显示数据
	 */
	public void highlightModel(SearchModel model) {
		if (StringUtils.isNotBlank(type)) {
			if (NAME.equals(type)) {
				model.setTitle(BaseSearchAction.lighterStr(keyword, model.getTitle()));
			}
			else if (MARJOR_FUNCTION.equals(type)) {
				model.setSummary(BaseSearchAction.lighterStr(keyword, model.getSummary()));
			}
		}
	}
	
	public String show() {
		if (id > 0) {
			drugCommon = drugCommonService.get(id);
		}
		return SHOW;
	}
	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public DrugCommon getDrugCommon() {
		return drugCommon;
	}

	public void setDrugCommon(DrugCommon drugCommon) {
		this.drugCommon = drugCommon;
	}
	
	public String getDrugType() {
		return drugType;
	}

	public void setDrugType(String drugType) {
		this.drugType = drugType;
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
	
	public Pagination<DrugCommon> getPagination() {
		return pagination;
	}
	
	public List<SearchModel> getModelList() {
		return modelList;
	}

	@Resource
	public void setDrugCommonService(DrugCommonService drugCommonService) {
		this.drugCommonService = drugCommonService;
	}
}
