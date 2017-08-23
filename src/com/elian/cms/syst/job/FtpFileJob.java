package com.elian.cms.syst.job;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import com.elian.cms.syst.util.EhcacheUtils;
import com.elian.cms.syst.util.FTPImgClearFactoryUtils;

public class FtpFileJob implements Job {
	public void execute(JobExecutionContext context)
			throws JobExecutionException {
		new FTPImgClearFactoryUtils().deleteFTPFile(EhcacheUtils.getCacheFtp(EhcacheUtils.IMG_FTP));//清理图片FTP临时文件
		new FTPImgClearFactoryUtils().deleteFTPFile(EhcacheUtils.getCacheFtp(EhcacheUtils.FILE_FTP));//清理文件FTP临时文件
	}
}