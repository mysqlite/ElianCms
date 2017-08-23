package com.elian.cms.syst.listener.Impl;

import org.apache.commons.net.ftp.FTPClient;
import org.springframework.stereotype.Component;

import com.elian.cms.admin.model.Content;
import com.elian.cms.syst.listener.StaticPageListener;

/**
 * StaticPageListener的默认实现，方便调用删除静态方法
 * 
 * @author Joe
 * 
 */
@Component("staticPageListener")
public class StaticPageListenerAbstract extends StaticPageListener {
	public String generateStaticPage(Content c, FTPClient ftp) {
		return null;
	}
}
