package com.elian.cms.syst.util;

import java.io.File;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

/**
 * 用于分解模板
 * 
 * @author Joe
 */
public class TemplateUtils {
	/**
	 * 模板分解
	 */
	public static void resolveTemplate(String templatePath) {
		String[] contents = resolveTemplateMarcoContent(templatePath);
		// 分解后的主内容
		StringBuilder mainContent = new StringBuilder(contents[0])
				.append(contents[1]);
		// 分解后的marco内容
		String marcoContent = contents[1];
		// 分解后的剩余内容
		String residueContent = contents[2];

		// 文件存放位置
		String fileUrl = templatePath.substring(0, templatePath
				.lastIndexOf(ElianCodes.SPRIT))
				+ ElianCodes.SPRIT;

		String[] files = templatePath.split(ElianCodes.SPRIT);
		// //文件名称前缀
		String nameSuffix = files[files.length - 1].replaceAll(
				ElianCodes.SUFFIX_HTML, "");

		createDetailTemplateFile(marcoContent, residueContent, mainContent,
				fileUrl + nameSuffix + ElianCodes.SPRIT);
		createMainTemplateFile(mainContent.toString(), fileUrl, nameSuffix);
	}

	/**
	 * 对模板内容首先进行macro分解
	 */
	private static String[] resolveTemplateMarcoContent(String templatePath) {
		String[] contents = getTemplateContent(templatePath).split(
				"<!--#macro-->");
		if (contents.length == 3) {
			return contents;
		}
		else if (contents.length == 1) {
			contents = new String[] { contents[0], "", "" };
		}
		return contents;
	}

	/**
	 * 获取模板文件中的所有内容
	 */
	private static String getTemplateContent(String outputTempName) {
		String templateContent = "";
		try {
			File file = new File(outputTempName);
			if (file.isFile())
				templateContent = FileUtils.readFileToString(file,
						ElianCodes.UNICODE_UTF8);
		}
		catch (IOException e) {
			e.printStackTrace();
		}
		return templateContent;
	}

	/**
	 * 创建区块模板文件
	 */
	private static void createDetailTemplateFile(String marcoContent,
			String residueContent, StringBuilder mainContent, String fileUrl) {
		createDir(fileUrl);

		// 为子文件夹添加相对路径
		if (StringUtils.isNotBlank(marcoContent)) {
			String[] marcos = marcoContent.split("<#import '");
			if (marcos.length == 2) {
				marcoContent = marcos[0] + "<#import '../" + marcos[1];
			}
		}

		String[] contents = residueContent.split("<!--#-->");
		if (contents != null) {
			for (String con : contents) {
				if (con.startsWith("<!--#")) {
					int i = Integer.valueOf(con.substring("<!--#".length(), con
							.indexOf("-->")));
					try {
						File file = new File(fileUrl + i
								+ ElianCodes.SUFFIX_HTML);
						FileUtils.writeStringToFile(file,
								marcoContent
										+ con.substring(("<!--#" + i + "-->")
												.length()),
								ElianCodes.UNICODE_UTF8);
						file.createNewFile();
					}
					catch (IOException e) {
						e.printStackTrace();
					}
					appendIncludeInformation(mainContent, i);
				}
				else {
					mainContent.append(con);
				}
			}
		}
	}

	/**
	 * 创建主模板文件
	 */
	private static void createMainTemplateFile(String mainContent,
			String fileUrl, String nameSuffix) {
		File file = new File(fileUrl + nameSuffix + ElianCodes.SUFFIX_HTML);
		try {
			FileUtils.writeStringToFile(file, mainContent,
					ElianCodes.UNICODE_UTF8);
		}
		catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 为主模板内容添加服务端包含信息
	 */
	private static void appendIncludeInformation(StringBuilder mainContent,
			int areaNum) {
		mainContent.append("<!--#include file=\"${"
				+ FreemarkerCodes.DETAIL_FOLDER + "}" + areaNum
				+ ElianCodes.SUFFIX_SHTML + "\"-->");
	}

	/**
	 * 获取模板对应的所有的区域模板 例如：news.html return
	 * news/1.html,news/2.html......
	 */
	public static Set<String> getAllTemplateSet(String templateName) {
		String outputTempName = ApplicationUtils.getRealPath(null,
				FreemarkerCodes.TEMPLATE_URL)
				+ ElianCodes.SPRIT + templateName;
		// 文件存放路径
		String fileUrl = outputTempName.substring(0, outputTempName
				.lastIndexOf(ElianCodes.SPRIT))
				+ ElianCodes.SPRIT;

		String[] files = outputTempName.split(ElianCodes.SPRIT);
		// 文件名称前缀
		String nameSuffix = files[files.length - 1].replaceAll(
				ElianCodes.SUFFIX_HTML, "");

		Set<String> templateSet = new HashSet<String>();
		File folder = new File(fileUrl);
		if (folder.isDirectory()) {
			for (File file : folder.listFiles()) {
				if (file.getName().startsWith(nameSuffix)) {
					templateSet.add(file.getName());
				}
			}
		}
		return templateSet;
	}

	/**
	 * 用于模板创建文件夹目录
	 * 
	 * @param realPath
	 * @param nestFile
	 * @return
	 */
	private static void createDir(String pathUrl) {// 创建目录,不关闭连接
		String[] urls = pathUrl.split(ElianCodes.SPRIT);
		String path = urls[0];
		for (int i = 1; i < urls.length; i++) {
			String url = urls[i];
			if (StringUtils.isBlank(url))
				continue;
			path += ElianCodes.SPRIT + url;
			File file = new File(path);
			if (!file.exists()) {
				file.mkdirs();
			}
		}
	}
}
