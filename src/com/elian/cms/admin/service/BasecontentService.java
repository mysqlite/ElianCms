package com.elian.cms.admin.service;

import com.elian.cms.syst.model.BaseContent;

public interface BasecontentService<T extends BaseContent> {
	public void save(T basecontent, boolean isEdit);
}
