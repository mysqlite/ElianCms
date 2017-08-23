package com.elian.cms.front.action;

import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

import net.sf.json.JSONObject;

import org.apache.lucene.document.Document;
import org.apache.lucene.index.Term;
import org.apache.lucene.queryParser.MultiFieldQueryParser;
import org.apache.lucene.search.BooleanClause;
import org.apache.lucene.search.BooleanQuery;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.TermQuery;
import org.apache.lucene.util.Version;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.elian.cms.syst.listener.InstrumentIndexInterface;
import com.elian.cms.syst.util.ApplicationUtils;
import com.elian.cms.syst.util.IndexUtils;
import com.elian.cms.syst.util.StringUtils;

/**
 * 搜索器械Action
 * 
 * @author Joe
 * 
 */
@Component
@Scope("prototype")
public class SearchInstrumentAction extends BaseSearchAction implements
		InstrumentIndexInterface, Serializable {
	private static final long serialVersionUID = -1665395475626644764L;

	private Integer typeId;// 类型ID
	private String keyword;// 关键字
	private Integer rowSize=12;
	private Integer pageNo=1;
	
	public String search() {
		pagination.setRowSize(rowSize);
		return super.search();
	}
	
	public void searchJson() {
		try {
			if(keyword!=null)
			URLDecoder.decode(keyword,"UTF-8");
		}
		catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} 
		pagination.setRowSize(rowSize);
		pagination.setPageNo(pageNo);
		super.search();
		JSONObject obj = new JSONObject();
		obj.put("insList", pagination.getList());
		obj.put("page", pagination.getPageInfo());
		ApplicationUtils.sendJsonpObj(obj);
	}

	@Override
	public boolean addQuery(BooleanQuery bq) throws Exception {
		Query q = null;
		if (StringUtils.isNotBlank(keyword)) {
			q = MultiFieldQueryParser.parse(Version.LUCENE_35, keyword,
					new String[] { TITLE, DESCRIPTION },
					new BooleanClause.Occur[] { BooleanClause.Occur.SHOULD,
							BooleanClause.Occur.SHOULD }, IndexUtils
							.getAnalyzer());
			bq.add(q, BooleanClause.Occur.MUST);
		}
		if (typeId != null && typeId > 0) {
			Query c = new TermQuery(new Term(TYPE_ID, typeId.toString()));
			bq.add(c, BooleanClause.Occur.MUST);
		}
		Query c = new TermQuery(new Term(ACTION_NAME, "instrument_c"));
		bq.add(c, BooleanClause.Occur.MUST);
		return true;
	}

	@Override
	public SearchModel createSearchModel(Document doc, Query query) {
		SearchModel model = new SearchModel();
		model.setTitle(doc.get(TITLE));
		model.setSummary(cutSummaryContent(doc.get(DESCRIPTION)));
		model.setPath(doc.get(PATH));
		model.setImgPath(doc.get(IMAGE));
		model.setPrice(doc.get(PRICE));
		model.setDate(doc.get(CREATE_TIME));
		model.setCompAddress(doc.get(COMP_ADDRESS));
		model.setCompName(doc.get(COMP_NAME));
		highlightModel(model, query);
		return model;
	}

	/**
	 * 高亮显示数据
	 */
	public void highlightModel(SearchModel model, Query query) {
		model.setRealTitle(model.getTitle());
		model.setTitle(lighterStr(keyword, model.getTitle()));
		model.setSummary(lighterStr(keyword, model.getSummary()));
	}

	public String getKeyword() {
		return keyword;
	}

	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}
	
	public Integer getTypeId() {
		return typeId;
	}

	public void setTypeId(Integer typeId) {
		this.typeId = typeId;
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
}
