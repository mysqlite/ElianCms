package com.elian.cms.syst.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import org.apache.commons.net.ftp.FTPClient;
import org.springframework.stereotype.Component;

import com.elian.cms.admin.model.Channel;

import freemarker.template.Configuration;
import freemarker.template.Template;

/**
 * @author Administrator
 * 
 */
@Component("freemarker")
public class FreemarkerUtils {
	/** 创建freemarker配置实例 */
	private static Configuration config = new Configuration();

	/** 缓存freemarker数据模型 */
	private static Map<Integer, Map<String, Object>> cache = new HashMap<Integer, Map<String, Object>>();
	
	private static Map<String,String> includeFileOutPutMap=new HashMap<String, String>();

	static{
		includeFileOutPutMap.put("header.html", FreemarkerCodes.HEAD_OUTPUT_NAME);
		includeFileOutPutMap.put("head.html", FreemarkerCodes.HEAD_OUTPUT_NAME);
		includeFileOutPutMap.put("footer.html", FreemarkerCodes.FOOT_OUTPUT_NAME);
		includeFileOutPutMap.put("foot.html", FreemarkerCodes.FOOT_OUTPUT_NAME);
		includeFileOutPutMap.put("province.html","province.shtml");
	}
	
	public static void main(String[] args) throws Exception {
		// String tempName = "red/alone/test.html";// 定义模板名
		// String outputName = "test1.html";// 定义模板输出文件名
		// Map<String, String> map = new HashMap<String, String>();
		// map.put("title", "hello");
		// map.put("author", "beautyJoe");
		// map.put("content", "hello,world!");
		// generateStaticFile(tempName, outputName, map);
	}
	
	// TODO
	public static void generateStaticFile(Channel channel, String outputFolder,
			String templateName, String outputFileName,
			Map<String, Object> dataMap, FTPClient ftp) {
		generateDetailStaticFile(channel, outputFolder, templateName,
				outputFileName, dataMap, ftp);
	}

	public static void generateDetailStaticFile(Channel channel,
			String outputFolder, String templateName, String outputFileName,
			Map<String, Object> dataMap, FTPClient ftp) {

		dataMap.put(FreemarkerCodes.DETAIL_FOLDER, outputFolder);
		//生成总模板
		generateStaticFile(templateName, outputFileName, dataMap, ftp);

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
		
		boolean isDetailStatic = false;
		if (!channel.isDetailStatic()) {
			// 用FTP字段是否传值来判断是否是生成内容的明细，如果是内容的明细，只生成一次明细文件夹中的文件
			if (ftp != null)
				isDetailStatic = true;
			File folder = new File(fileUrl + nameSuffix);
			if (folder.isDirectory()) {
				//添加所有的子模板
				Set<String> templateSet = new HashSet<String>();
				for (File file : folder.listFiles()) {
					templateSet.add(file.getName());
				}
				
				//生成模板对应的明细静态文件
				for (String temp : templateSet) {
					String url1 = templateName.substring(0, templateName
							.lastIndexOf(ElianCodes.SPRIT))
							+ ElianCodes.SPRIT;

					String fileUrl1 = outputFileName.substring(0,
							outputFileName.lastIndexOf(ElianCodes.SPRIT))
							+ ElianCodes.SPRIT;

					generateStaticFile(url1 + nameSuffix + ElianCodes.SPRIT
							+ temp, fileUrl1.concat(outputFolder).concat(
							temp.replaceAll(ElianCodes.SUFFIX_HTML, ""))
							.concat(ElianCodes.SUFFIX_SHTML), dataMap, ftp);
				}
			}
		}
		//为了防止在生成内容的时候，重复的去生成侧边栏数据
		if (isDetailStatic) {
			channel.setDetailStatic(true);
		}
	}
	
	/**
	 * 生成静态文件
	 * 
	 * @param templateName
	 * @param outputFileName
	 * @param dataMap
	 */
	public static void generateStaticFile(String templateName,String outputFileName, Map<?, ?> dataMap, FTPClient ftp) {
		Writer out = null;
		try {
			config.setDirectoryForTemplateLoading(new File(ApplicationUtils.getRealPath(null, FreemarkerCodes.TEMPLATE_URL)));
			Template temp = config.getTemplate(templateName,ElianCodes.UNICODE_UTF8);
			File file = createNestFile(ApplicationUtils.getRealPath(null,FreemarkerCodes.STATIC_FILE_OUTPUT_URL), outputFileName);
			// FileWriter不能指定编码确实是个问题，只能用这个代替了。
			System.out.println(templateName);
			out = new OutputStreamWriter(new FileOutputStream(file),ElianCodes.UNICODE_UTF8);
			/*Map<Object, Object> objs=(Map<Object, Object>) dataMap.get("dataMap");
			Map<Object, Object> objs2=(Map<Object, Object>)objs.get("6");
			objs2.get("1");*/
			temp.process(dataMap, out);
			
			out.flush();
			out.close();
			
			String outName = new StringBuilder(ApplicationUtils.getSite().getComType().substring(0, 4)).append(ElianCodes.SPRIT).append(outputFileName).toString();
			if (ftp == null)
				FtpToolUtils.storeByFilename(outName,new FileInputStream(file), EhcacheUtils.getCacheFtp(EhcacheUtils.STATIC_FTP));
			else
				FtpToolUtils.storeByFilename(outName,new FileInputStream(file), ftp);
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 递归创建文件夹及文件
	 * 
	 * @param realPath
	 * @param nestFile
	 * @return
	 */
	public static File createNestFile(String realPath, String nestFile) {
		String[] urls = nestFile.split(ElianCodes.SPRIT);
		String path = realPath;
		File lastFile = null;
		for (String url : urls) {
			if (StringUtils.isBlank(url))
				continue;
			path += ElianCodes.SPRIT + url;
			File file = new File(path);
			if (path.endsWith(ElianCodes.SUFFIX_SHTML)) {
				if (file.exists()) {
					file.delete();
				}
				lastFile = file;
				break;
			}
			else if (!file.exists()) {
				file.mkdirs();
			}
		}
		return lastFile;
	}

	public static void addCache(String name, Object obj) {
		Map<String, Object> dataMap = cache.get(ApplicationUtils.getSite()
				.getId());
		if (dataMap == null) {
			dataMap = new HashMap<String, Object>();
			cache.put(ApplicationUtils.getSite().getId(), dataMap);
		}
		dataMap.put(name, obj);
	}

	public static Object getCache(String name) {
		Map<String, Object> dataMap = cache.get(ApplicationUtils.getSite()
				.getId());
		if (dataMap == null)
			return null;
		return dataMap.get(name);
	}
	
	public static String getIncludeOutFileName(String fileName){
		Iterator<String> keySet= includeFileOutPutMap.keySet().iterator();
		while (keySet.hasNext()) {
			String mapKey = keySet.next();
			if(fileName.equalsIgnoreCase(mapKey))
				return includeFileOutPutMap.get(mapKey);
		}
		return fileName.replaceFirst(".html", ".shtml");
	}
}
