package com.elian.cms.front.action;

import java.io.IOException;
import java.io.Serializable;
import java.io.StringReader;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.tokenattributes.CharTermAttribute;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.Term;
import org.apache.lucene.search.BooleanClause;
import org.apache.lucene.search.BooleanQuery;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TermQuery;
import org.apache.lucene.search.TermRangeQuery;
import org.apache.lucene.search.TopDocs;

import com.elian.cms.admin.service.impl.StaticServiceImpl;
import com.elian.cms.syst.listener.BaseIndexInterface;
import com.elian.cms.syst.model.Pagination;
import com.elian.cms.syst.util.ElianUtils;
import com.elian.cms.syst.util.IndexUtils;
import com.elian.cms.syst.util.StringUtils;

public abstract class BaseSearchAction implements BaseIndexInterface,
		Serializable {
	private static final long serialVersionUID = -4481652831512779929L;
	protected final static Log logger = LogFactory
			.getLog(StaticServiceImpl.class);
	/** 执行查询返回列表 */
	protected static final String LIST = "list";
	/** 执行查询返回列表 */
	protected SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");

	/** 站点ID */
	protected Integer siteId;
	/** 分页对象 */
	protected Pagination<SearchModel> pagination = new Pagination<SearchModel>();

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
		try {
			IndexSearcher searcher = IndexUtils.getSearcher();
			BooleanQuery bq = createInitQuery();
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

	/**
	 * 创建初始化查询
	 */
	private BooleanQuery createInitQuery() {
		BooleanQuery booleanQuery = new BooleanQuery();
		Query q = null;
		if (siteId != null) {
			q = new TermQuery(new Term(SITE_ID, siteId.toString()));
			booleanQuery.add(q, BooleanClause.Occur.MUST);
		}
		// 添加内容静态化条件
		q = new TermRangeQuery(STATIC, String
				.valueOf(ElianUtils.STATIC_STATUS_1), String
				.valueOf(ElianUtils.STATIC_STATUS_2), true, true);
		booleanQuery.add(q, BooleanClause.Occur.MUST);

		// 添加内容审核条件
		q = new TermRangeQuery(STATUS, String
				.valueOf(ElianUtils.CONTENT_STATUS_2), String
				.valueOf(ElianUtils.CONTENT_STATUS_3), true, true);
		booleanQuery.add(q, BooleanClause.Occur.MUST);
		return booleanQuery;
	}

	/**
	 * 添加各个模型特有的搜索条件
	 */
	public abstract boolean addQuery(BooleanQuery bq) throws Exception;

	/**
	 * 添加Lucene查询的结果放置到分页对象中
	 * 
	 * @throws IOException
	 */
	protected void addResultInPagination(IndexSearcher searcher,
			BooleanQuery booleanQuery) throws IOException {
		// 先获取上一页的最后一个元素
		ScoreDoc lastSd = getLastScoreDoc(pagination.getPageNo(), pagination
				.getRowSize(), booleanQuery, searcher);
		// 通过最后一个元素搜索下页的pageSize个元素
		TopDocs tds = searcher.searchAfter(lastSd, booleanQuery, pagination
				.getRowSize());
		pagination.setRowCount(tds.totalHits);
		List<SearchModel> modeList = new ArrayList<SearchModel>(pagination
				.getRowSize());
		for (ScoreDoc sd : tds.scoreDocs) {
			Document doc = searcher.doc(sd.doc);
			modeList.add(createSearchModel(doc, booleanQuery));
		}
		pagination.setList(modeList);
	}

	/**
	 * 根据页码和分页大小获取上一次的最后一个ScoreDoc
	 */
	private ScoreDoc getLastScoreDoc(int pageIndex, int pageSize, Query query,
			IndexSearcher searcher) throws IOException {
		if (pageIndex == 1)
			return null;// 如果是第一页就返回空
		int num = pageSize * (pageIndex - 1);// 获取上一页的数量
		TopDocs tds = searcher.search(query, num);
		int lastNum = 0;
		if (tds.totalHits == 0) {
			pagination.setPageNo(1);
			return null;
		}
		else if (tds.totalHits > num) {
			lastNum = num - 1;
		}
		return tds.scoreDocs[lastNum];
	}

	/**
	 * 创建前台搜索列表显示的模型对象
	 */
	public abstract SearchModel createSearchModel(Document doc, Query query);

	/**
	 * 截取摘要内容
	 */
	protected String cutSummaryContent(String content) {
		if (StringUtils.isNotBlank(content) && content.length() > 255) {
			return content.substring(0, 255) + "...";
		}
		return content;
	}

	protected static String lighterStr(String keyword, String txt) {
		if (StringUtils.isBlank(keyword) || StringUtils.isBlank(txt))
			return txt;
		try {
			TokenStream tokenStream = IndexUtils.getAnalyzer().tokenStream(
					keyword, new StringReader(keyword));
			String[] formatter = new String[] { "<em class=\"em_key\">",
					"</em>" };
			boolean hasnext = tokenStream.incrementToken();
			Set<String> keySet = new HashSet<String>();
			while (hasnext) {
				CharTermAttribute ta = tokenStream
						.getAttribute(CharTermAttribute.class);
				keySet.add(ta.toString());
				hasnext = tokenStream.incrementToken();
			}
			return lightFilter(formatter, keySet, txt);
		}
		catch (Exception e) {
			logger.error("高亮处理出错!", e);
		}
		return txt;
	}

	private static String lightFilter(String[] formatter, Set<String> keySet,
			String txt) {
		for (String key : keySet) {
			txt = txt.replaceAll(key, formatter[0] + key + formatter[1]);
		}
		return txt;
	}

	public Pagination<SearchModel> getPagination() {
		return pagination;
	}

	public Integer getSiteId() {
		return siteId;
	}

	public void setSiteId(Integer siteId) {
		this.siteId = siteId;
	}
}
