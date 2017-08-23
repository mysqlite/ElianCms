package com.elian.cms.front.action;

import java.io.Serializable;

import javax.annotation.Resource;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.elian.cms.admin.model.Site;
import com.elian.cms.admin.service.SiteService;
import com.elian.cms.syst.util.ElianUtils;

/**
 * 搜索适配器Action
 * 
 * @author Joe
 * 
 */
@Component
@Scope("prototype")
public class SearchAdapterAction implements Serializable {

	private static final long serialVersionUID = -3947171457877934847L;

	private Integer siteId;// 站点ID
	private String keyword;// 关键字
	private SiteService siteService;

	public String execute() {
		Site site = null;
		if (siteId != null && (site = siteService.get(siteId)) != null) {
			if (ElianUtils.COMP_TYPE_MEDICINE_COMP.equals(site.getComType())) {
				return "medicine";
			}
			else if (ElianUtils.COMP_TYPE_INSTRUMENT_COMP.equals(site
					.getComType())) {
				return "instrument";
			}
		}
		return null;
	}

	public Integer getSiteId() {
		return siteId;
	}

	public void setSiteId(Integer siteId) {
		this.siteId = siteId;
	}
	
	public String getKeyword() {
		return keyword;
	}

	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}

	@Resource
	public void setSiteService(SiteService siteService) {
		this.siteService = siteService;
	}
}
