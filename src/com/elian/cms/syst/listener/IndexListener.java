package com.elian.cms.syst.listener;

import java.io.IOException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.index.CorruptIndexException;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.Term;
import org.apache.lucene.search.BooleanClause;
import org.apache.lucene.search.BooleanQuery;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TermQuery;
import org.apache.lucene.search.TopDocs;

import com.elian.cms.admin.model.Channel;
import com.elian.cms.admin.model.Content;
import com.elian.cms.admin.model.Site;
import com.elian.cms.syst.model.BaseContent;
import com.elian.cms.syst.util.ElianCodes;
import com.elian.cms.syst.util.ElianUtils;
import com.elian.cms.syst.util.FreemarkerCodes;
import com.elian.cms.syst.util.IndexUtils;

/**
 * 内容索引监听
 * 
 * @author Joe
 * 
 */
public abstract class IndexListener<T extends BaseContent> implements
		BaseIndexInterface {

	private final static Log logger = LogFactory.getLog(IndexListener.class);

	/**
	 * 创建索引
	 */
	public void createIndex(Content content, T t) {
		IndexWriter writer = null;
		try {
			writer = IndexUtils.getWriter();
			Document doc = createDocument(content, t);
			writer.addDocument(doc);
		}
		catch (Exception e) {
			logger.error("索引创建失败!", e);
		}
		finally {
			try {
				writer.close();
			}
			catch (Exception e) {
				logger.error("索引创建失败！", e);
			}
		}
	}

	/**
	 * 创建索引文档
	 */
	public Document createDocument(Content content, T t) {
		Document doc = new Document();
		doc.add(new Field(CONTENT_ID, content.getId().toString(),
				Field.Store.YES, Field.Index.NOT_ANALYZED));
		doc.add(new Field(SITE_ID, content.getSite().getId().toString(),
				Field.Store.YES, Field.Index.NOT_ANALYZED));
		doc.add(new Field(CHANNEL_ID, content.getChannel().getId().toString(),
				Field.Store.YES, Field.Index.NOT_ANALYZED));
		doc.add(new Field(STATUS, String.valueOf(content.getStatus()),
				Field.Store.YES, Field.Index.NOT_ANALYZED));
		doc.add(new Field(STATIC, String.valueOf(content.getStaticStatus()),
				Field.Store.YES, Field.Index.NOT_ANALYZED));
		doc.add(new Field(ACTION_NAME, content.getActionName(),
				Field.Store.YES, Field.Index.NOT_ANALYZED));
		createIndexDetail(t, content, doc);
		return doc;
	}

	/**
	 * 获取内容的绝对路径
	 */
	public String getContentPath(Site site, Channel channel, Integer contentId) {
		StringBuilder url = new StringBuilder();
		if (ElianUtils.COMP_TYPE_MAIN.equals(site.getComType())) {
			url.append("http://www").append(FreemarkerCodes.DOMAIN_SUFFIX);
		}
		else {
			url.append("http://").append(site.getDomain()).append(
					FreemarkerCodes.DOMAIN_SUFFIX);
		}
		return url.append(getSiteUrl(site)).append(channel.getPath()).append(
				ElianCodes.SPRIT).append(contentId).append(
				ElianCodes.SUFFIX_SHTML).toString();
	}

	public static String getSiteUrl(Site site) {
		String compType = site.getComType();
		if (!(ElianUtils.COMP_TYPE_MAIN.equals(compType) || ElianUtils.COMP_TYPE_SUBS
				.equals(compType))) {
			return ElianCodes.SPRIT + site.getId();
		}
		return "";
	}

	/**
	 * 钩子函数，用于加载各个模型中的特殊字段
	 */
	public abstract void createIndexDetail(T t, Content content, Document doc);

	/**
	 * 更新索引
	 * 
	 * @param T
	 */
	public void updateIndex(Content content, T t) {
		IndexWriter writer = IndexUtils.getWriter();
		try {
			writer.deleteDocuments(createQuery(content.getSite().getId(),
					content.getChannel().getId(), content.getId()));
			writer.addDocument(createDocument(content, t));
			writer.close();
		}
		catch (Exception e) {
			logger.error("索引更新失败!", e);
		}
	}

	/**
	 * 删除索引
	 * 
	 * @param T
	 */
	public void deleteIndex(Content content) {
		IndexWriter writer = IndexUtils.getWriter();
		try {
			writer.deleteDocuments(createQuery(content.getSite().getId(),
					content.getChannel().getId(), content.getId()));
			writer.close();
		}
		catch (Exception e) {
			logger.error("索引删除失败!", e);
		}
	}

	public static Query createQuery(Integer siteId, Integer channelId,
			Integer contentId) {
		BooleanQuery bq = new BooleanQuery();
		Query q = null;
		if (siteId != null) {
			q = new TermQuery(new Term(SITE_ID, siteId.toString()));
			bq.add(q, BooleanClause.Occur.MUST);
		}
		if (channelId != null) {
			q = new TermQuery(new Term(CHANNEL_ID, channelId.toString()));
			bq.add(q, BooleanClause.Occur.MUST);
		}
		if (contentId != null) {
			q = new TermQuery(new Term(CONTENT_ID, contentId.toString()));
			bq.add(q, BooleanClause.Occur.MUST);
		}
		return bq;
	}

	public static void main(String[] args) {
		 query();
		 search();
	}

	public static void query() {
		IndexReader reader = IndexUtils.getReader();
		// 通过reader可以有效的获取到文档的数量
		System.out.println("numDocs:" + reader.numDocs());
		System.out.println("maxDocs:" + reader.maxDoc());
		System.out.println("deleteDocs:" + reader.numDeletedDocs());
	}

	public static void search() {
		try {
			IndexSearcher searcher = IndexUtils.getSearcher();
			BooleanQuery bq = new BooleanQuery();
			Query q = new TermQuery(new Term(ACTION_NAME, "doctor_c"));
			bq.add(q, BooleanClause.Occur.MUST);
//			TermQuery query = new TermQuery(new Term("title", "生活"));
//			bq.add(query, BooleanClause.Occur.SHOULD);
			
			TopDocs tds = searcher.search(bq, 10);
			for (ScoreDoc sd : tds.scoreDocs) {
				Document doc = searcher.doc(sd.doc);
				System.out.println("name:" + doc.get("name")+ "---->status:"
				+ doc.get("status") + "---->static:"
				+ doc.get("static"));
//				System.out.println("title:" + doc.get("title")
//						+ "---->summary:" + doc.get("summary") + "---->author:"
//						+ doc.get("author") + "---->status:"
//						+ doc.get("status") + "---->static:"
//						+ doc.get("static"));
//				System.out.println("---->siteId:"
//						+ doc.get("siteId")
//						+ "---->channelId:"
//						+ doc.get("channelId")
//						+ "---->contentId:"
//						+ doc.get("contentId")
//						+ "---->createTime:"
//						+ doc.get("createTime"));
			}
			searcher.close();
		}
		catch (CorruptIndexException e) {
			e.printStackTrace();
		}
		catch (IOException e) {
			e.printStackTrace();
		}
	}
}
