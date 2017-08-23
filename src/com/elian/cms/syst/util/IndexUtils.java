package com.elian.cms.syst.util;

import java.io.File;
import java.io.IOException;

import net.paoding.analysis.analyzer.PaodingAnalyzer;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.util.Version;

public class IndexUtils {
	private static String INDEX_DIR = "/lucene";// 索引存放目录

	private final static Log logger = LogFactory.getLog(IndexUtils.class);

	private static Directory directory = null;
	private static IndexReader reader = null;

	static {
		try {
			File file = new File(ApplicationUtils.getRealPath(null, INDEX_DIR));
			if (!file.exists())
				file.mkdirs();
			directory = FSDirectory.open(file);
		}
		catch (Exception e) {
			logger.error("索引目录初始化失败", e);
			e.printStackTrace();
		}
	}

	public static IndexWriter getWriter() {
		IndexWriter writer = null;
		try {
			writer = new IndexWriter(directory, new IndexWriterConfig(
					Version.LUCENE_35, getAnalyzer()));
		}
		catch (Exception e) {
			logger.error("获取IndexWriter失败！", e);
		}
		return writer;
	}

	public static IndexReader getReader() {
		try {
			if (reader == null) {
				reader = IndexReader.open(directory);
			}
			else {
				IndexReader tr = IndexReader.openIfChanged(reader);
				if (tr != null) {
					reader.close();
					reader = tr;
				}
			}
			return reader;
		}
		catch (Exception e) {
			logger.error("获取IndexReader失败！", e);
		}
		return null;
	}

	public static IndexSearcher getSearcher() {
		try {
			return new IndexSearcher(getReader());
		}
		catch (Exception e) {
			logger.error("获取IndexSearcher失败！", e);
		}
		return null;
	}

	public static Analyzer getAnalyzer() {
		try {
			return new PaodingAnalyzer();
			//return new StandardAnalyzer(Version.LUCENE_35);
		}
		catch (Exception e) {
			logger.error("获取Analyzer失败！", e);
		}
		return null;
	}

	public static void cleanIndex() {
		IndexWriter writer = getWriter();
		try {
			writer.deleteAll();
		}
		catch (IOException e) {
			logger.error("清空索引失败！", e);
		}
		finally {
			try {
				writer.close();
			}
			catch (Exception e) {
				logger.error("清空索引失败！", e);
			}
		}

	}
}
