package com.elian.cms.syst.util;

public class ElianCodes {
	/** 黑色 */
	public static final String COLOR_BLACK = "#000000";
	/** 红色 */
	public static final String COLOR_RED = "red";
	/** 绿色 */
	public static final String COLOR_GREEN = "green";
	/** 灰色 */
	public static final String COLOR_GRAY = "gray";
	/** 编码 */
	public static final String UNICODE_UTF8 = "UTF-8";

	/** rar */
	public static final String FILE_RAR = "rar";
	/** zip */
	public static final String FILE_ZIP = "zip";

	/** 中划线 */
	public static final String UNDERLINE = "-";
	/** 斜杠 */
	public static final String SPRIT = "/";
	/**  */
	public static final String SHAFT = "#";

	/** 模板后缀.html */
	public static final String SUFFIX_HTML = ".html";
	/** 静态文件后缀.shtml */
	public static final String SUFFIX_SHTML = ".shtml";

	public static final long MAX_SIZE = 5242880;//3145728;// 设置上传文件最大为5MB
	
	public static final long FILE_MAX_SIZE = 31457280;// 设置上传文件最大为30MB

	public static double getMaxSize() {
		
		
		return MAX_SIZE / (1024 * 1024);
	}

	public static void main(String[] args) {
		String aa="10737418240";
		long ab=Long.parseLong(aa);
		long abc=ab-1024*2;
		System.out.println(((ab)/(1024 * 1024))+"MB");
		System.out.println(((abc)/(1024 * 1024))+"MB");
		System.out.println(DoubleUtlis.divLong(abc, 1024*1024, 3)+"MB");
		
		long add=(long) (1024*1024*1.5);
		System.out.println(add);
		
		
		
	}
	public static final String UPLOADPATH = "http://www.eliao.cc/ftpFile";


	public static final String TEMP_FILE="temp/";
	
	public static final String FTP_TEMP="/temp/";
	
	public static final String TEMP_FILE_HTTP="temp\\";
	
	public static final String DOCTOR_DEFAULT_IMG="http://m.191580.com/ftpFile/defdoc.jpg.png";
	
	public static final String DEPARTMENT_DEFAULT_IMG="http://m.191580.com/ftpFile/defhos.png";
	
	public static final String INFORMATION_DEFAULT_IMG="http://images.elian.cc/design/hosp/comm/no_pic.jpg";
}
