package com.elian.cms.syst.util;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.elian.cms.admin.model.Ftp;

public class FTPImgClearFactoryUtils{
	private final static Log logger = LogFactory.getLog(FTPImgClearFactoryUtils.class);
	public void deleteFTPFile(Ftp ftp) {
			List<String> delFile = FtpToolUtils.getDelTempFile(ftp,ElianCodes.FTP_TEMP);
			if (delFile.size() > 0) {
				logger.info("⊙⊙⊙FTPTemp文件夹清理，共"+delFile.size()+"个文件☉☉☉☉☉");
				FtpToolUtils.deleteFile(ftp, delFile);
				logger.info("⊙⊙⊙FTPTemp文件夹清理完毕☉☉☉");
				for (int i = 0; i < delFile.size(); i++) {
					logger.info("路径：" +delFile.get(i));
				}
			}
			else
				logger.info("⊙⊙⊙FTPTemp文件夹无文件☉☉☉");
	}
}
