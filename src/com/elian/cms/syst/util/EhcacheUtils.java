package com.elian.cms.syst.util;

import com.elian.cms.admin.model.Ftp;

import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Ehcache;
import net.sf.ehcache.Element;

public class EhcacheUtils {
	/**启动加载缓存FTP名称*/
	public static final String FTP="com.elian.cms.Ftp";
	/**启动加载缓存Area实体*/
	public static final String AREA="com.elian.cms.Area";
	/** 获取当前图片FTP */
	public static final String IMG_FTP = "_img_ftp";
	/** 获取当前静态文件FTP */
	public static final String STATIC_FTP = "_static_ftp";
	public static final String FILE_FTP="_file_ftp";
	public static final String SWF_FTP="_swf_ftp";
	public static final String VIDEO_FTP="_video_ftp";
	
	private static final CacheManager cacheManager=SpringUtils.getBean("cacheManager");
	
	public static Ftp getCacheFtp(String key) {
		return (Ftp)getEhcache(FTP).get(key).getObjectValue();
	}
	
	public static void setCacheFtp(Ftp ftp,String key) {
		getEhcache(FTP).put(new Element(key, ftp));
	}
	
	private static Ehcache getEhcache(String key) {
		return cacheManager.getEhcache(key);
	}
}
