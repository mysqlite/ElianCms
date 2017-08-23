package com.elian.cms.syst.listener.Impl;

import java.text.SimpleDateFormat;

import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.springframework.stereotype.Component;

import com.elian.cms.admin.model.Content;
import com.elian.cms.admin.model.Instrument;
import com.elian.cms.syst.listener.IndexListener;
import com.elian.cms.syst.listener.InstrumentIndexInterface;
import com.elian.cms.syst.util.StringUtils;

/**
 * IndexListener的默认实现，方便调用删除索引公用方法
 * 
 * @author Joe
 * 
 */
@Component("instrumentIndexListener")
public class InstrumentIndexListener extends IndexListener<Instrument> implements
		InstrumentIndexInterface {
	public static final SimpleDateFormat sf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	@Override
	public void createIndexDetail(Instrument info, Content content, Document doc) {
		doc.add(new Field(TITLE, info.getTitle(), Field.Store.YES,Field.Index.ANALYZED));
		String txt = StringUtils.replaceHtml(info.getDescription());
		if (StringUtils.isNotBlank(txt))
			doc.add(new Field(DESCRIPTION, txt, Field.Store.YES,Field.Index.ANALYZED));
		doc.add(new Field(PATH, getContentPath(content.getSite(), content.getChannel(), content.getId()), Field.Store.YES,Field.Index.NOT_ANALYZED));
		if (StringUtils.isNotBlank(info.getFrontImg()))
			doc.add(new Field(IMAGE, info.getFrontImg(), Field.Store.YES,Field.Index.NOT_ANALYZED));
		doc.add(new Field(PRICE, info.getPrice() / 100 + "", Field.Store.YES,Field.Index.NOT_ANALYZED));
		if (info.getType() != null) {
			if (StringUtils.isNotBlank(info.getType().getTypeName())) {
				doc.add(new Field(TYPE_NAME, info.getType().getTypeName(),Field.Store.YES, Field.Index.NOT_ANALYZED));
			}
			doc.add(new Field(TYPE_ID, info.getType().getId().toString(),Field.Store.YES, Field.Index.NOT_ANALYZED));
		}
		doc.add(new Field(CREATE_TIME, sf.format(info.getCreateTime()),Field.Store.YES,Field.Index.NOT_ANALYZED));
		doc.add(new Field(COMP_ADDRESS, info.getCompany().getAddress(),Field.Store.YES,Field.Index.NOT_ANALYZED));
		doc.add(new Field(COMP_NAME, info.getCompany().getName(),Field.Store.YES,Field.Index.NOT_ANALYZED));
	}
}
