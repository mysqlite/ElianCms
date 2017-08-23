package com.elian.cms.syst.listener;

import org.apache.commons.net.ftp.FTPClient;

import com.elian.cms.admin.model.Channel;
import com.elian.cms.admin.model.Content;
import com.elian.cms.syst.util.ApplicationUtils;
import com.elian.cms.syst.util.EhcacheUtils;
import com.elian.cms.syst.util.ElianCodes;
import com.elian.cms.syst.util.FtpToolUtils;
import com.elian.cms.syst.util.StringUtils;

/**
 * 内容静态化模型抽象监听基类
 * 
 * @author Joe
 * 
 */
public abstract class StaticPageListener {
	public final static String CONTENT_ID = "contentId";

	/**
	 * 生成静态页
	 * 
	 * @param T
	 */
	public abstract String generateStaticPage(Content c, FTPClient ftp);

	/**
	 * 删除静态页
	 * 
	 * @param T
	 */
	public final void deleteStaticPage(Content content) {
		Channel channel = content.getChannel();
		if (channel != null && StringUtils.isBlank(channel.getPath()))
			return;
		FtpToolUtils.delAllFlie(EhcacheUtils.getCacheFtp(EhcacheUtils.STATIC_FTP),
				ApplicationUtils.getSite().getComType().substring(0, 4) + "/"
						+ ApplicationUtils.getSite().getId()
						+ channel.getPath() + ElianCodes.SPRIT
						+ content.getId() + ElianCodes.SUFFIX_SHTML);
	}
}
