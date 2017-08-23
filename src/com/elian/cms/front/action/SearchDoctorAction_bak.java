package com.elian.cms.front.action;

import java.io.Serializable;

import org.apache.lucene.document.Document;
import org.apache.lucene.index.Term;
import org.apache.lucene.queryParser.MultiFieldQueryParser;
import org.apache.lucene.queryParser.QueryParser;
import org.apache.lucene.search.BooleanClause;
import org.apache.lucene.search.BooleanQuery;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.TermQuery;
import org.apache.lucene.util.Version;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.elian.cms.syst.listener.DoctorIndexInterface;
import com.elian.cms.syst.util.ElianCodes;
import com.elian.cms.syst.util.FreemarkerCodes;
import com.elian.cms.syst.util.IndexUtils;
import com.elian.cms.syst.util.StringUtils;

/**
 * 搜索资讯Action
 * 
 * @author Joe
 * 
 */
@Component
@Scope("prototype")
public class SearchDoctorAction_bak extends BaseSearchAction implements
		DoctorIndexInterface, Serializable {
	private static final long serialVersionUID = -1665395475626644764L;
	/** 搜索条件 */
	private String type;// 搜索选项
	private String keyword;// 关键字

	@Override
	public boolean addQuery(BooleanQuery bq) throws Exception {
		boolean hasCondition = false;
		Query q = null;
		if (StringUtils.isNotBlank(keyword)) {
			if (StringUtils.isNotBlank(type)) {
				QueryParser qp = new QueryParser(Version.LUCENE_35,type, IndexUtils.getAnalyzer());
				q = qp.parse(keyword);
				bq.add(q, BooleanClause.Occur.MUST); 
			}
			else {
				q = MultiFieldQueryParser.parse(Version.LUCENE_35, keyword,
						new String[] { DOCT_NAME, DOCT_SPECIALITY },
						new BooleanClause.Occur[] { BooleanClause.Occur.SHOULD,
								BooleanClause.Occur.SHOULD }, IndexUtils
								.getAnalyzer());
				bq.add(q, BooleanClause.Occur.MUST);
			}
			hasCondition = true;
		}
		Query c = new TermQuery(new Term(ACTION_NAME, "doctor_c"));
		bq.add(c, BooleanClause.Occur.MUST);
		return hasCondition;
	}

	@Override
	public SearchModel createSearchModel(Document doc, Query query) {
		DoctorSearchModel model = new DoctorSearchModel();
		model.setDoctId(doc.get(DOCT_ID));
		model.setIsReg(doc.get(DOCT_IS_REG));
		model.setTitle(doc.get(DOCT_NAME));
		model.setSummary(doc.get(DOCT_SPECIALITY));
		model.setAddress(doc.get(HOSP_NAME));
		model.setDate(doc.get(DEPT_NAME));
		model.setImgPath(doc.get(DOCT_IMG));
		model.setDutyName(doc.get(DOCT_DUTY_NAME));
		model.setSiteId(doc.get(SITE_ID));
		model.setPath(doc.get(DOCT_PATH));
		highlightModel(model, query);
		return model;
	}

	/**
	 * 高亮显示数据
	 */
	public void highlightModel(SearchModel model, Query query) {
		if (StringUtils.isNotBlank(type)) {
			if (DOCT_NAME.equals(type)) {
				model.setTitle(lighterStr(keyword, model.getTitle()));
			}
			else if (DOCT_SPECIALITY.equals(type)) {
				model.setSummary(lighterStr(keyword, model.getSummary()));
			}
		}
		else {
			model.setTitle(lighterStr(keyword, model.getTitle()));
			model.setSummary(lighterStr(keyword, model.getSummary()));
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
	
	public String getBasePath(){
		return "http://hosp"+FreemarkerCodes.DOMAIN_SUFFIX+ElianCodes.SPRIT;
	}
}
