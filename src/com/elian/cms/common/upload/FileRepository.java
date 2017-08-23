package com.elian.cms.common.upload;

import java.io.File;
import java.io.IOException;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.io.FileUtils;
import org.springframework.stereotype.Component;

import com.elian.cms.syst.util.ApplicationUtils;

/**
 * 本地文件存储
 * 
 * @author Gechuanyi
 */
@Component("fileRepository")
public class FileRepository {
	public String storeByExt(String path, String ext, File file) {
		String filename = UploadUtils.generateFilename(path, ext);// 存储文件名称及路径
		File dest = new File(filename);
		dest = UploadUtils.getUniqueFile(dest);
		store(file, dest);// 保存文件
		return filename;
	}

	public String storeByFilename(String filename, File file) {
		File dest = new File(filename);
		store(file, dest);
		return filename;
	}

	private void store(File file, File dest) {
		UploadUtils.checkDirAndCreate(dest.getParentFile());
		try {
			FileUtils.copyFile(file, dest);
		}
		catch (IOException e) {
			e.printStackTrace();
		}
	}

	public File retrieve(String name, HttpServletRequest request) {
		return new File(ApplicationUtils.getSiteRoot(request) + name);
	}
}
