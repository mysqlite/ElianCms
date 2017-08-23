package com.elian.cms.admin.dao.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import com.elian.cms.admin.dao.ImgUploadDao;
import com.elian.cms.admin.model.Ftp;
import com.elian.cms.admin.model.Images;
import com.elian.cms.syst.dao.impl.DaoImpl;
import com.elian.cms.syst.util.ElianCodes;
import com.elian.cms.syst.util.FtpToolUtils;
import com.elian.cms.syst.util.StringUtils;
@Component("imgUploadDao")
public class ImgUploadDaoImpl extends DaoImpl<Images, Integer> implements ImgUploadDao {
	private String[] delPath;
	
	public void saveToFtp(Ftp ftp,Images img) {
		if(img!=null&&ftp!=null) {
    		FtpToolUtils.moveFile(ElianCodes.FTP_TEMP+img.getImagesName(), img.getImagesPath(),ftp);
    		save(img);
		}
	}
	
	public void saveToFtp(Ftp ftp,List<Images> img) {
		if(img!=null&&ftp!=null) {
    		for (int i = 0; i < img.size(); i++) {
    			FtpToolUtils.moveFile(ElianCodes.FTP_TEMP+img.get(i).getImagesName(), img.get(i).getImagesPath(),ftp);
    		}
			for (Images i : img)
				save(i);
		}
	}
	
	public void delFTPImg(Ftp ftp,Images img) {
		if(img!=null&&ftp!=null) {
    		FtpToolUtils.deleteFile(ftp,img.getImagesPath());
    		delete(img);
		}
	}
	
	public void delFTPImg(Ftp ftp,List<Images> img) {
		if(img.size()>0&&ftp!=null) {
    		delPath=new String[img.size()];
    		for (int i = 0; i < img.size(); i++) {
    			delPath[i]=img.get(i).getImagesPath();
    		}
    		 FtpToolUtils.deleteFile(ftp,delPath);
			for (Images i : img)
				delete(i);
    	}
	}
	public  List<Images> getDelImgsByEitityAndId(String entityName,Integer entityId) {
		StringBuffer hql=new StringBuffer("from Images where  entityName=? and entityId=?  "); 
		List<Object> param=new ArrayList<Object> ();
		if(StringUtils.isNotBlank(entityName)&&entityId!=null) {
			param.add(entityName);
			param.add(entityId);
			return findByHql(hql.toString(),true, param.toArray());
		}
		return null;
	}
	
	public  List<Images> getDelImgsBySiteId(Integer siteId) {
		StringBuffer hql=new StringBuffer("from Images where  siteId=?  "); 
		List<Object> param=new ArrayList<Object> ();
		if(siteId!=null) {
			param.add(siteId);
			return findByHql(hql.toString(),true, param.toArray());
		}
		return null;
	}
} 
