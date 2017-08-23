package com.elian.cms.syst.listener;

import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;

import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Ehcache;
import net.sf.ehcache.Element;

import org.springframework.util.CollectionUtils;
import org.springframework.web.context.ContextLoaderListener;

import com.elian.cms.admin.model.Ftp;
import com.elian.cms.admin.service.FtpService;
import com.elian.cms.syst.util.SpringUtils;
public class CacheListener extends ContextLoaderListener {
    private  Ehcache initCache;  
    private FtpService ftpService;
   private CacheManager cacheManager;
    /** 
     * @description 重写ContextLoaderListener的contextInitialized方法 
     */  
     @Override  
    public void contextInitialized(ServletContextEvent event) {  
        super.contextInitialized(event);  
        //获取bean  
        cacheManager=SpringUtils.getBean("cacheManager");
        initCache=cacheManager.getEhcache("com.elian.cms.Ftp");
        ftpService=SpringUtils.getBean("ftpService");
        List<Ftp> fileFtp= ftpService.findByTypeIdOrTypeName(null, "附件");
        List<Ftp> imgFtp= ftpService.findByTypeIdOrTypeName(null, "图片");
        List<Ftp> swfFtp= ftpService.findByTypeIdOrTypeName(null, "SWF文件");
        List<Ftp> staticFtp= ftpService.findByTypeIdOrTypeName(null, "静态");
        List<Ftp> videoFtp=ftpService.findByTypeIdOrTypeName(null, "音视频");
        initCache.put(new Element("_file_ftp", fileFtp.get(0)));
        initCache.put(new Element("_img_ftp", imgFtp.get(0)));
        initCache.put(new Element("_swf_ftp", swfFtp.get(0)));
        initCache.put(new Element("_static_ftp", staticFtp.get(0)));
        initCache.put(new Element("_video_ftp", (!CollectionUtils.isEmpty(videoFtp))?videoFtp.get(0):null));
        
        ServletContext servletContext=(ServletContext) event.getServletContext();
        servletContext.setAttribute("path", servletContext.getContextPath());
    }
}
