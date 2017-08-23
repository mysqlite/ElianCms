package com.elian.cms.syst.listener.Impl;

import javax.annotation.Resource;

import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.springframework.stereotype.Component;

import com.elian.cms.admin.model.Content;
import com.elian.cms.admin.model.Hospital;
import com.elian.cms.admin.model.Site;
import com.elian.cms.admin.service.SiteService;
import com.elian.cms.syst.listener.HospitalIndexInterface;
import com.elian.cms.syst.listener.IndexListener;
import com.elian.cms.syst.util.ElianUtils;
import com.elian.cms.syst.util.FilePathUtils;
import com.elian.cms.syst.util.StringUtils;

/**
 * IndexListener的默认实现，方便调用删除索引公用方法
 * 
 * @author Joe
 * 
 */
@Component("hospitalIndexListener")
public class HospitalIndexListener extends IndexListener<Hospital> implements
		HospitalIndexInterface {
	private SiteService siteService=null;
	
	/**
	 * 创建索引文档
	 */
	@Override
	public Document createDocument(Content content, Hospital hosp) {
		Document doc = new Document();
		Site s=siteService.findByByComp(ElianUtils.COMP_TYPE_HOSP, hosp.getId());
		doc.add(new Field(SITE_ID, s.getId().toString(),Field.Store.YES, Field.Index.NOT_ANALYZED));
		doc.add(new Field(ACTION_NAME, content.getActionName(),Field.Store.YES, Field.Index.NOT_ANALYZED));
		createIndexDetail(hosp, content, doc);
		return doc;
	}
	
	
	@Override
	public void createIndexDetail(Hospital hosp, Content content, Document doc) {
		doc.add(new Field(Id, hosp.getId().toString(), Field.Store.YES, Field.Index.NOT_ANALYZED));
		doc.add(new Field(STATUS, hosp.getStatus()+"", Field.Store.YES, Field.Index.NOT_ANALYZED));
		doc.add(new Field(NAME, hosp.getHospName(), Field.Store.YES,Field.Index.ANALYZED));
		if (StringUtils.isNotBlank(hosp.getHospImg())) {
			doc.add(new Field(LOGO, FilePathUtils.setOutFilePath(hosp
					.getHospImg()), Field.Store.YES, Field.Index.NOT_ANALYZED));
		}
		if (StringUtils.isNotBlank(hosp.getAddress())) {
			doc.add(new Field(ADDRESS, hosp.getAddress(), Field.Store.YES,
					Field.Index.ANALYZED));
		}
		if (StringUtils.isNotBlank(hosp.getShortDesc())) {
			doc.add(new Field(SUMMARY, hosp.getShortDesc(), Field.Store.YES,
					Field.Index.ANALYZED));
		}
		doc.add(new Field(AREAID, hosp.getAreaId().toString(), Field.Store.YES, Field.Index.NOT_ANALYZED));
		doc.add(new Field(TYPE, hosp.getHospType().toString(), Field.Store.YES, Field.Index.NOT_ANALYZED));
		doc.add(new Field(RANK, hosp.getRank().toString(), Field.Store.YES, Field.Index.NOT_ANALYZED));
		doc.add(new Field(PHONE,hosp.getPhone(), Field.Store.YES, Field.Index.NOT_ANALYZED));
		doc.add(new Field(ISREG,hosp.isReg()==true?"1":"0", Field.Store.YES, Field.Index.NOT_ANALYZED));
		doc.add(new Field(HITS, hosp.getHits().toString(), Field.Store.YES, Field.Index.NOT_ANALYZED));
	}


	@Resource
	public void setSiteService(SiteService siteService) {
		this.siteService = siteService;
	}
}
