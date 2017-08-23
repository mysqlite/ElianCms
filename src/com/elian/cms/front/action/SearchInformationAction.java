package com.elian.cms.front.action;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;

import org.apache.lucene.document.Document;
import org.apache.lucene.index.Term;
import org.apache.lucene.queryParser.MultiFieldQueryParser;
import org.apache.lucene.queryParser.QueryParser;
import org.apache.lucene.search.BooleanClause;
import org.apache.lucene.search.BooleanQuery;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.TermQuery;
import org.apache.lucene.search.TermRangeQuery;
import org.apache.lucene.util.Version;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.elian.cms.syst.listener.InformationIndexInterface;
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
public class SearchInformationAction extends BaseSearchAction implements
		InformationIndexInterface, Serializable {
	private static final long serialVersionUID = -1665395475626644764L;

	/** 搜索条件 */
	private String type;// 搜索选项
	private int intDate;// 创建时间
	private String keyword;// 关键字

	@Override
	public boolean addQuery(BooleanQuery bq) throws Exception {
		boolean hasCondition = false;
		Query q = null;
		if (StringUtils.isNotBlank(keyword)) {
			// PaodingDicUtil.addNewWord(keyword);
			if (StringUtils.isNotBlank(type)) {
				// q = new TermQuery(new Term(type, keyword));
				// bq.add(q, BooleanClause.Occur.MUST);
				QueryParser qp = new QueryParser(Version.LUCENE_35, type,
						IndexUtils.getAnalyzer());
				q = qp.parse(keyword);
				bq.add(q, BooleanClause.Occur.MUST);
			}
			else {
				q = MultiFieldQueryParser.parse(Version.LUCENE_35, keyword,
						new String[] { TITLE, CONTENT, AUTHOR },
						new BooleanClause.Occur[] { BooleanClause.Occur.SHOULD,
								BooleanClause.Occur.SHOULD,
								BooleanClause.Occur.SHOULD }, IndexUtils
								.getAnalyzer());
				bq.add(q, BooleanClause.Occur.MUST);
			}
			hasCondition = true;
		}

		Date[] dates = convertDate();
		if (dates != null) {
			q = new TermRangeQuery(CREATE_TIME, format.format(dates[0]), format
					.format(dates[1]), true, true);
			bq.add(q, BooleanClause.Occur.MUST);
		}
		Query c = new TermQuery(new Term(ACTION_NAME, "information_c"));
		bq.add(c, BooleanClause.Occur.MUST);
		return hasCondition;
	}

	private Date[] convertDate() {
		if (intDate == 0)
			return null;
		else
			return new Date[] { getDate(-intDate), new Date() };
	}

	/** 按当前时间向前推迟beforeMonth个月 */
	private static Date getDate(int beforeMonth) {
		Calendar calendar = Calendar.getInstance();
		calendar.add(Calendar.MONTH, beforeMonth); // 得到前一个月
		return calendar.getTime();
	}

	@Override
	public SearchModel createSearchModel(Document doc, Query query) {
		SearchModel model = new SearchModel();
		model.setTitle(doc.get(TITLE));
		model.setSummary(cutSummaryContent(doc.get(CONTENT)));
		model.setPath(doc.get(PATH));
		model.setDate(doc.get(CREATE_TIME));
		highlightModel(model, query);
		return model;
	}

	/**
	 * 高亮显示数据
	 */
	public void highlightModel(SearchModel model, Query query) {
		if (StringUtils.isNotBlank(type)) {
			if (TITLE.equals(type)) {
				model.setTitle(lighterStr(keyword, model.getTitle()));
			}
			else if (CONTENT.equals(type)) {
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

	public int getIntDate() {
		return intDate;
	}

	public void setIntDate(int intDate) {
		this.intDate = intDate;
	}
}
