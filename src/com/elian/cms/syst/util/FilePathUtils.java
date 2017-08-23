package com.elian.cms.syst.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.StringTokenizer;

import net.sf.json.JSONArray;

import org.apache.commons.io.FilenameUtils;

import com.elian.cms.admin.model.Site;
import com.elian.cms.admin.service.SiteService;
import com.elian.cms.common.upload.FileType;
import com.elian.cms.common.upload.UploadUtils;

public class FilePathUtils {
	private static final String[] CONIMG= {"department","doctor","hospital","company","instrument","medicine"};
	private static final String[] COMP= {"main","hosp","comp","subs","inst","medi"};
	static String context="<embed src=\"/comp/383/20130822/0.swf\" />" +
	"<embed src=\"/comp/383/20130822/1.swf\" />" +
			         "<embed src=\"/comp/383/20130822/0.mid\" />" +
			         "<embed src=\"/comp/383/20130822/1.mid\" />" +
			         "<img alt=\"\" src=\"/comp/383/165408GHW6zDu5b0.jpg\" width=\"71\" height=\"100\" />"+
			         "<img alt=\"\" src=\"/comp/383/20130822/1.jpg\" width=\"71\" height=\"100\" />"
			         ;
	
	static String imgFtp="http://images.elian.cc";
	static String videoFtp="http://video.elian.cc";
	static String fileFtp="http://file.elian.cc";
	
	
public static void main(String[] args) {
	System.out.println(setEditorOutPath(context));
}
	
public static String HospImg(String actionName) {
	if(StringUtils.isNotBlank(actionName)) {
		for (int i = 0; i < CONIMG.length; i++) {
			if(actionName.toLowerCase().contains(CONIMG[i].toLowerCase())) {
				return CONIMG[i].substring(0,4)+"Img";
			}
		}
	}
	return null;
}

/**转换上传控件路径为真实路径*/
public static String setOutFilePath(String path) {
	if(StringUtils.isBlank(path)) return null;
	String ext="";
	for (int i = 0; i < COMP.length; i++) {
		if(path.contains("/"+COMP[i]+"/"+getFileSite(path))) {
			ext=FilenameUtils.getExtension(path);
			if(FileType.IMGS.contains(ext))
				return EhcacheUtils.getCacheFtp(EhcacheUtils.IMG_FTP).getFtpUrl()+path;
			else if(FileType.FILES.contains(ext))
				return EhcacheUtils.getCacheFtp(EhcacheUtils.FILE_FTP).getFtpUrl()+path;
			else if(FileType.VIDEO.contains(ext))
				return EhcacheUtils.getCacheFtp(EhcacheUtils.VIDEO_FTP).getFtpUrl()+path;
		} 
	}
	return path;
}

/**文本编辑器输出文件地址*/
public static String setEditorOutPath(String context) {
	if(StringUtils.isBlank(context))return context;
	String[]  sf=getContentFiles(context);
	String ext="";
	for (String s : sf) {
		ext=FilenameUtils.getExtension(s);
		if(FileType.IMGS.contains(ext))
			context=StringUtils.replaceStr(context, EhcacheUtils.getCacheFtp(EhcacheUtils.IMG_FTP).getFtpUrl()+s, s);
		else if(FileType.FILES.contains(ext))
			context=StringUtils.replaceStr(context, EhcacheUtils.getCacheFtp(EhcacheUtils.FILE_FTP).getFtpUrl()+s, s);
		else if(FileType.VIDEO.contains(ext))
			context=StringUtils.replaceStr(context, EhcacheUtils.getCacheFtp(EhcacheUtils.VIDEO_FTP).getFtpUrl()+s, s);
	}
	return context;
}
/***
 * 获取被删除文件的路径，如果参数调换，得到被添加的文件路径
 * @param prvEditorImg 原编辑器数据图片路径
 * @param newEditorImg 	现在编辑器图片路径
 * @return
 */
public static List<String> getDeleteFilePath(String prvFile,String newFile){
	String[] prvEditorImg=getContentFiles(getConContext(prvFile));
	String[] newEditorImg=getContentFiles(getConContext(newFile));
	List<String> delImgPath= new ArrayList<String>();
	Set<String> newImgSet=new HashSet<String>();
	if(newEditorImg!=null) {
		for (int i = 0; i < newEditorImg.length; i++) {
			newImgSet.add(newEditorImg[i]);
			if(prvEditorImg==null)
				delImgPath.add(newEditorImg[i]);
		}
	}
	if(prvEditorImg!=null) {
		for (int i = 0; i < prvEditorImg.length; i++) {
			if(!newImgSet.contains(prvEditorImg[i])) {
				delImgPath.add(prvEditorImg[i]);
			}else if(newEditorImg==null)
				delImgPath.add(prvEditorImg[i]);
		}
	}
	return delImgPath;
}
	
/**
 * 在保存实体的时候，将编辑器中的图片路径更改为实际路径
 * @param context
 * @return
 */
public static String getConContext(String context) {
	String ftpUrl[]=new String[3];
	ftpUrl[0]=EhcacheUtils.getCacheFtp(EhcacheUtils.FILE_FTP).getFtpUrl();
	ftpUrl[1]=EhcacheUtils.getCacheFtp(EhcacheUtils.IMG_FTP).getFtpUrl();
	ftpUrl[2]=EhcacheUtils.getCacheFtp(EhcacheUtils.VIDEO_FTP).getFtpUrl();
	String resultContext=context;
	String temp="",result="";
	for (String url : ftpUrl) {
		temp=url+ElianCodes.FTP_TEMP;
		result=StringUtils.replaceStr(resultContext, StringUtils.replaceStr(UploadUtils.genertFilename(), "/", "\\")+"/",temp);
		resultContext=StringUtils.replaceStr(result, "", url);
	}
	return resultContext;
}

/**获取编辑器中临时上传的图片路径*/
public static String[]	 getContentFiles(String context) {
	if(StringUtils.isBlank(context))return null;
	StringTokenizer st = new StringTokenizer(context, "\"");  
	String[] items = new String[st.countTokens()];  
	int count = 0;  
	while (st.hasMoreTokens()) {  
	items[count++] = st.nextToken();  
	}  
	int j=0;
	for (int i=0; i < items.length; i++) {
		if(items[i].contains(getFileRoot())&&!items[i].contains("http://")&&!items[i].contains("https://"))
		j++;
	}
	String[] result=new  String[j];
	int k=0;
	for (int i=0; i < items.length; i++) {
		if(items[i].contains(getFileRoot())&&!items[i].contains("http://")&&!items[i].contains("https://")) {
			result[k]=items[i];
			k++;
		}
	}
	return result;
}
public static String[]	 getContentFile(String context) {
	if(StringUtils.isBlank(context))return null;
	StringTokenizer st = new StringTokenizer(context, "\"");  
	String[] items = new String[st.countTokens()];  
	int count = 0;  
	while (st.hasMoreTokens()) {  
		items[count++] = st.nextToken();  
	}  
	int j=0;
	for (int i=0; i < items.length; i++) {
		//if(items[i].contains("/weifei_uploadfile")&&!items[i].contains("http://")&&!items[i].contains("https://"))//清远导入
		if(items[i].contains(getFileRoot())&&!items[i].contains("http://")&&!items[i].contains("https://"))//原系统
		//if(items[i].contains("/weblogin/include/eWebEditor/uploadfile/")&&!items[i].contains("http://")&&!items[i].contains("https://"))//西樵医院
			j++;
	}
	String[] result=new  String[j];
	int k=0;
	for (int i=0; i < items.length; i++) {
		//if(items[i].contains("/weifei_uploadfile")&&!items[i].contains("http://")&&!items[i].contains("https://")){//清远导入
		if(items[i].contains(getFileRoot())&&!items[i].contains("http://")&&!items[i].contains("https://")){//原系统
		//if(items[i].contains("/weblogin/include/eWebEditor/uploadfile/")&&!items[i].contains("http://")&&!items[i].contains("https://")) {//西樵医院
			result[k]=items[i];
			k++;
		}
		
		
		
	}
	return result;
}

public static String getFileRoot() {
	if(ApplicationUtils.getSite()==null) {
		Integer siteId=Integer.parseInt(ApplicationUtils.getRequest().getParameter("siteId"));
		if(siteId!=null) {
			Site site=((SiteService)SpringUtils.getBean("siteService")).get(siteId);
			if(site!=null)
			return "/"+site.getComType().substring(0,4)+"/"+site.getId()+"/";
		}
	}else {
		return "/"+ApplicationUtils.getSite().getComType().substring(0,4)+"/"+ApplicationUtils.getSite().getId()+"/";
	}
	return null;
}
	/**
	 * 是否为引用  是则return false
	 * @param s
	 * @return
	 */
	public static boolean isHttp(String s) {
		if(StringUtils.isBlank(s))return false;
		if(!(s.indexOf("https")==0||s.indexOf("http")==0||s.indexOf("ftp")==0||s.indexOf("rtsp")==0||s.indexOf("mms")==0))//如果为引用则不删除//https|http|ftp|rtsp|mms
			return true;
		else return false;
	}
	
	@SuppressWarnings("unchecked")
	public static String[] getJsonPath(String jsonString) {
		if(StringUtils.isBlank(jsonString))
			return null;
		ImgPath path=new ImgPath();
		List<ImgPath> list=(List<ImgPath>) jsonArrayStrToList(jsonString, path.getClass());
		String[] paths=new String[list.size()];
		int i=0;
		for (ImgPath imgPath: list) {
				if(isHttp(imgPath.getPath()))
					paths[i]=EhcacheUtils.getCacheFtp(EhcacheUtils.IMG_FTP).getFtpUrl()+imgPath.getPath();
				else
					paths[i]=imgPath.getPath();
			i++;
		}
		return paths;
	}
	
	@SuppressWarnings("deprecation")
	public static List<?> jsonArrayStrToList(String jsonArry,Class<?> clazz){
		JSONArray array = JSONArray.fromObject(jsonArry); 
		Map<String, Class<?>> classMap = new HashMap<String, Class<?>>(); 
		classMap.put("list", clazz); 
		List<?> list = JSONArray.toList(array, clazz,classMap);
		return list;
	}
	
	public static String getImgPath(String str) {
		  if(isHttp(str))str=EhcacheUtils.getCacheFtp(EhcacheUtils.IMG_FTP).getFtpUrl()+str;
		return str;
	}
	
	public static String strArrayToStr(String[] str) {
		if(str==null)
			return "";
		int length=0;
		for (int i = 0; i < str.length; i++) {
			if(StringUtils.isNotBlank(str[i])) {
				length++;
			}
		}
		String[] array=new String[length];
		int ilen=0;
		for (int i = 0; i < str.length; i++) {
			if(StringUtils.isNotBlank(str[i])) {
				array[ilen]=str[i];
				ilen++;
			}
		}
		String resultStr="";
		for (int i = 0; i < array.length; i++) {
			if(i==0)
				resultStr+="[";
			if(StringUtils.isNotBlank(array[i])) {
				if((i==0||i<(array.length-1))&&array.length>1) 
					resultStr+="{\"path\":\""+array[i]+"\"},";
				else
					resultStr+="{\"path\":\""+array[i]+"\"}";
			}
			if(i==(array.length-1)) 
				resultStr+="]";
		}
		return resultStr;
	}
	
	private static Integer getFileSite(String paths) {
		String[] path=StringUtils.getSplitStringByString(paths, "/");
		Integer realut=null;
		if(path!=null&&path.length>0) {
    		try {
    			realut=Integer.parseInt(path[2]);
    		}
    		catch (Exception e) {
    			realut=0;
    		}
    		return realut;
		}
		return 0;
	}
	
	public static class ImgPath{
		private String path;

		public String getPath() {
			return path;
		}

		public void setPath(String path) {
			this.path = path;
		}
	}
}
