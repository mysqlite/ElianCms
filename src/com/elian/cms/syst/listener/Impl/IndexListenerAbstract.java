package com.elian.cms.syst.listener.Impl;

import org.apache.lucene.document.Document;
import org.springframework.stereotype.Component;

import com.elian.cms.admin.model.Content;
import com.elian.cms.syst.listener.IndexListener;
import com.elian.cms.syst.model.BaseContent;

/**
 * IndexListener的默认实现，方便调用删除索引公用方法
 * 
 * @author Joe
 * 
 */
@Component("indexListener")
public class IndexListenerAbstract extends IndexListener<BaseContent> {


	@Override
	public void updateIndex(Content content, BaseContent t) {
	}

	@Override
	public void createIndexDetail(BaseContent t, Content content, Document doc) {
	}
}
