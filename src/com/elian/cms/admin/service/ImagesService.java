package com.elian.cms.admin.service;

import java.util.Date;
import java.util.List;

import com.elian.cms.admin.dao.ImagesDao;
import com.elian.cms.admin.model.Images;
import com.elian.cms.syst.model.Pagination;
import com.elian.cms.syst.service.Service;

public interface ImagesService extends Service<ImagesDao, Images, Integer> {

	public List<Images> findByPage(Pagination<Images> p,Integer siteId,String entityName,Integer entityId,Date createDate);
	
	public Images findByImagePath(String path);
	
	public Images findByrealImgPath(String realPath);
}
