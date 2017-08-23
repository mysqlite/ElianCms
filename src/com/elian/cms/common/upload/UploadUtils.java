package com.elian.cms.common.upload;

import java.io.File;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Pattern;

import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.RandomStringUtils;

import com.elian.cms.admin.model.Site;
import com.elian.cms.syst.util.ApplicationUtils;
import com.elian.cms.syst.util.Num62Utils;
import com.elian.cms.syst.util.StringUtils;

public class UploadUtils {
	/**
	 * 日期格式化对象
	 */
	public static final DateFormat MONTH_FORMAT = new SimpleDateFormat("/yyyyMMdd/HHmmss");
	public static final DateFormat MONTH_FORMAT_YMD = new SimpleDateFormat("/yyyyMMdd_HHmmss");
	public static final DateFormat MONTH_FORMAT_NAME = new SimpleDateFormat("HHmmss");
	public static final DateFormat MONTH_FORMAT_Y_D = new SimpleDateFormat("/yyyyMMdd");

	public static String generateFilename(String path, String ext) {
			Site site=ApplicationUtils.getSite();
			if(site!=null) {
				return path + "/" +site.getComType().substring(0, 4)+ "/" + site.getId()+ MONTH_FORMAT.format(new Date())+ RandomStringUtils.random(10, Num62Utils.N62_CHARS) + "." + ext;
			}else {
				return path+MONTH_FORMAT.format(new Date())+RandomStringUtils.random(10, Num62Utils.N62_CHARS) + "." + ext;
			}
	}
	
	public static String generateImportFilename(String path, String ext) {
			return path+MONTH_FORMAT.format(new Date())+RandomStringUtils.random(10, Num62Utils.N62_CHARS) + "." + ext;
    }
	
	public static String generateFilename(String path, String ext,String contentType) {
		if(StringUtils.isNotBlank(contentType)) {
			return	MONTH_FORMAT_YMD.format(new Date())+ RandomStringUtils.random(10, Num62Utils.N62_CHARS) + "." + ext;
		}
		return  MONTH_FORMAT.format(new Date())+ RandomStringUtils.random(10, Num62Utils.N62_CHARS) + "." + ext;
}
	public static String generateFilenameQyfy(String path, String ext) {
		return  path+MONTH_FORMAT.format(new Date())+ RandomStringUtils.random(10, Num62Utils.N62_CHARS) + "." + ext;
	}

	public static void main(String[] args) {
		System.out.println( generateFilenameQyfy("/hosp/27","jpg"));
	}
	public static String genertFilename() {
		Site site=ApplicationUtils.getSite();
		if(site!=null) {
		return  "/"+site.getComType().substring(0, 4)+ "/"+ site.getId()+ MONTH_FORMAT_Y_D.format(new Date());
		}else {
			return  "/registerImg"+MONTH_FORMAT_Y_D.format(new Date());
		}
	}
	
	public static String genertFilename(String contentType,String fileName) {
		Site site=ApplicationUtils.getSite();
		if(site!=null) {
			StringBuffer retUrl=new StringBuffer();
                			retUrl.append("/").append(site.getComType().substring(0,4)).append("/").append(site.getId());
                		   	retUrl.append("/").append(contentType).append("/");
                			retUrl.append(fileName);
			return  retUrl.toString();
		}else {
			return  "/registerImg"+"/"+contentType+"/"+fileName;
		}
	}
	
	public static String genertFilename(boolean isTemp) {
		return MONTH_FORMAT_NAME.format(new Date())+RandomStringUtils.random(10, Num62Utils.N62_CHARS);
	}

	protected static final Pattern ILLEGAL_CURRENT_FOLDER_PATTERN = Pattern.compile("^[^/]|[^/]$|/\\.{1,2}|\\\\|\\||:|\\?|\\*|\"|<|>|\\p{Cntrl}");

	/**
	 * Sanitizes a filename from certain chars.<br />
	 * 
	 * This method enforces the <code>forceSingleExtension</code> property and
	 * then replaces all occurrences of \, /, |, :, ?, *, &quot;, &lt;, &gt;,
	 * control chars by _ (underscore).
	 * 
	 * @param filename
	 *            a potentially 'malicious' filename
	 * @return sanitized filename
	 */
	public static String sanitizeFileName(final String filename) {

		if (StringUtils.isBlank(filename))
			return filename;

		String name = forceSingleExtension(filename);

		// Remove \ / | : ? * " < > 'Control Chars' with _
		return name.replaceAll("\\\\|/|\\||:|\\?|\\*|\"|<|>|\\p{Cntrl}", "_");
	}

	/**
	 * Sanitizes a folder name from certain chars.<br />
	 * 
	 * This method replaces all occurrences of \, /, |, :, ?, *, &quot;, &lt;,
	 * &gt;, control chars by _ (underscore).
	 * 
	 * @param folderName
	 *            a potentially 'malicious' folder name
	 * @return sanitized folder name
	 */
	public static String sanitizeFolderName(final String folderName) {

		if (StringUtils.isBlank(folderName))
			return folderName;

		// Remove . \ / | : ? * " < > 'Control Chars' with _
		return folderName.replaceAll("\\.|\\\\|/|\\||:|\\?|\\*|\"|<|>|\\p{Cntrl}", "_");
	}

	/**
	 * Checks whether a path complies with the FCKeditor File Browser <a href="http://docs.fckeditor.net/FCKeditor_2.x/Developers_Guide/Server_Side_Integration#File_Browser_Requests"
	 * target="_blank">rules</a>.
	 * 
	 * @param path
	 *            a potentially 'malicious' path
	 * @return <code>true</code> if path complies with the rules, else
	 *         <code>false</code>
	 */
	public static boolean isValidPath(final String path) {
		if (StringUtils.isBlank(path))
			return false;

		if (ILLEGAL_CURRENT_FOLDER_PATTERN.matcher(path).find())
			return false;

		return true;
	}

	/**
	 * Replaces all dots in a filename with underscores except the last one.
	 * 
	 * @param filename
	 *            filename to sanitize
	 * @return string with a single dot only
	 */
	public static String forceSingleExtension(final String filename) {
		return filename.replaceAll("\\.(?![^.]+$)", "_");
	}

	/**
	 * Checks if a filename contains more than one dot.
	 * 
	 * @param filename
	 *            filename to check
	 * @return <code>true</code> if filename contains severals dots, else
	 *         <code>false</code>
	 */
	public static boolean isSingleExtension(final String filename) {
		return filename.matches("[^\\.]+\\.[^\\.]+");
	}

	/**
	 * Checks a directory for existence and creates it if non-existent.
	 * 
	 * @param dir
	 *            directory to check/create
	 */
	public static void checkDirAndCreate(File dir) {
		if (!dir.exists())
			dir.mkdirs();
	}

	/**
	 * Iterates over a base name and returns the first non-existent file.<br />
	 * 遍历一个基地的名称和返回第一个不存在的文件。 This method extracts a file's base name, iterates
	 * over it until the first 这种方法提取文件的基名称，直到第一次遍历 non-existent appearance with
	 * <code>basename(n).ext</code>. Where n is a positive integer starting from
	 * one.
	 * 
	 * @param file
	 *            base file
	 * @return first non-existent file
	 */
	public static File getUniqueFile(final File file) {
		if (!file.exists())// 检查文件是否以绝对路径存储
			return file;

		File tmpFile = new File(file.getAbsolutePath());
		File parentDir = tmpFile.getParentFile();
		int count = 1;
		String extension = FilenameUtils.getExtension(tmpFile.getName());
		String baseName = FilenameUtils.getBaseName(tmpFile.getName());
		do {
			tmpFile = new File(parentDir, baseName + "(" + count++ + ")."
					+ extension);
		}
		while (tmpFile.exists());
		return tmpFile;
	}
}
