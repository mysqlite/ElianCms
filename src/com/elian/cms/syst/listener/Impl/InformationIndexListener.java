package com.elian.cms.syst.listener.Impl;

import java.text.SimpleDateFormat;

import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.springframework.stereotype.Component;

import com.elian.cms.admin.model.Content;
import com.elian.cms.admin.model.Information;
import com.elian.cms.syst.listener.IndexListener;
import com.elian.cms.syst.listener.InformationIndexInterface;
import com.elian.cms.syst.util.StringUtils;

/**
 * IndexListener的默认实现，方便调用删除索引公用方法
 * 
 * @author Joe
 * 
 */
@Component("informationIndexListener")
public class InformationIndexListener extends IndexListener<Information>
		implements InformationIndexInterface {
	@Override
	public void createIndexDetail(Information info, Content content,
			Document doc) {
		doc.add(new Field(TITLE, info.getTitle(), Field.Store.YES,
				Field.Index.ANALYZED));
		String txt = StringUtils.replaceHtml(info.getContent());
		if (StringUtils.isNotBlank(txt))
			doc.add(new Field(CONTENT, txt, Field.Store.YES,
					Field.Index.ANALYZED));
		doc.add(new Field(AUTHOR, info.getAuthor(), Field.Store.YES,
				Field.Index.ANALYZED));
		doc.add(new Field(CREATE_TIME, new SimpleDateFormat("yyyy-MM-dd")
				.format(info.getCreateTime()), Field.Store.YES,
				Field.Index.NOT_ANALYZED));
		doc.add(new Field(PATH, getContentPath(content.getSite(), content
				.getChannel(), content.getId()), Field.Store.YES,
				Field.Index.NOT_ANALYZED));
	}
}
