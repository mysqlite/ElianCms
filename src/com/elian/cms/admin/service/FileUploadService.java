package com.elian.cms.admin.service;

import java.io.File;

public interface FileUploadService {

	public Object[] saveFileToFtpTemp(File file, String realName,
			String prevFile, String ext,String contentAction,boolean controls,boolean mark,boolean video);
}
