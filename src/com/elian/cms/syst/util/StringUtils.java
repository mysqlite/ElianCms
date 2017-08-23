package com.elian.cms.syst.util;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.io.FilenameUtils;

/**
 * 字符串公用类
 * 
 * @author Joe
 * 
 */
public final class StringUtils {
	/**
	 * 判断字符串是否为空(包括：长度为零，空串，NULL)
	 */
	public static boolean isBlank(String str) {
		if (str == null || str.length() == 0) {
			return true;
		}
		return false;
	}

	public static int binarySearch(int[] srcArray, int n,int value)
	{
		int low = 0;
		while (low <= n) {
			int middle = (low + n) / 2;
			if (value == srcArray[middle]) {
				return middle;
			}
			else if (value < srcArray[middle]) {
				n = middle - 1;
			}
			else {
				low = middle + 1;
			}
		}
		return -1;
	}
	//static int is=0;
	/*public static void main(String[] args)
	{
		
		List<DummyChannel> list=format();
		List<DummyChannel> lists=new ArrayList<DummyChannel>(list.size());
		for (int i = 0; i < list.size(); i++) {
			lists.add(getChird(list,list.get(i)));
		}
		list.clear();
		for (int i = 0; i < lists.size(); i++) {
			if(lists.get(i).getpId().equals("0")) {
				list.add(lists.get(i));
			}
		}
		
		for (DummyChannel dummyChannel : list) {
			digui(dummyChannel);
		}
		System.out.println("over");
	}
	
	private static void digui(DummyChannel channel) {
		System.out.println(channel.getChannelName());
		if(channel.getChirds()!=null&&channel.getChirds().size()>0) {
			for (DummyChannel c :channel.getChirds()) {
				digui(c);
			}
		}
	}
	
	public static DummyChannel getChird(List<DummyChannel> list, DummyChannel duc) {
		List<DummyChannel> ducc=new ArrayList<DummyChannel>();
		for (int i = 0; i < list.size(); i++) {
			if(duc.getId().equals( list.get(i).getpId())) 
				ducc.add(list.get(i));
		}
		duc.setChirds(ducc);
		return duc;
	}
	
	
	public static List<DummyChannel> format(){
		List<DummyChannel> list=new ArrayList<DummyChannel>();
		DummyChannel dumm=new DummyChannel();
		dumm.setChannelName("第1个栏目");dumm.setId("1");dumm.setpId("0");
		DummyChannel dumm2=new DummyChannel();
		dumm2.setChannelName("第2个栏目");dumm2.setId("2");dumm2.setpId("0");
		DummyChannel dumm3=new DummyChannel();
		dumm3.setChannelName("第3个栏目");dumm3.setId("3");dumm3.setpId("0");
		DummyChannel dumm4=new DummyChannel();
		dumm4.setChannelName("第4个栏目");dumm4.setId("4");dumm4.setpId("0");
		DummyChannel dumm5=new DummyChannel();
		dumm5.setChannelName("第5个栏目");dumm5.setId("5");dumm5.setpId("0");
		DummyChannel dumm6=new DummyChannel();
		dumm6.setChannelName("第6个栏目");dumm6.setId("6");dumm6.setpId("0");
		DummyChannel dumm7=new DummyChannel();
		dumm7.setChannelName("第7个栏目");dumm7.setId("7");dumm7.setpId("0");
		
		DummyChannel dumm31=new DummyChannel();
		dumm31.setChannelName("第3_1个栏目");dumm31.setId("31");dumm31.setpId("3");
		DummyChannel dumm32=new DummyChannel();
		dumm32.setChannelName("第3_2个栏目");dumm32.setId("32");dumm32.setpId("3");
		DummyChannel dumm33=new DummyChannel();
		dumm33.setChannelName("第3_3个栏目");dumm33.setId("33");dumm33.setpId("3");
		DummyChannel dumm34=new DummyChannel();
		dumm34.setChannelName("第3_4个栏目");dumm34.setId("34");dumm34.setpId("3");
		
		DummyChannel dumm21=new DummyChannel();
		dumm21.setChannelName("第2_1个栏目");dumm21.setId("21");dumm21.setpId("2");
		DummyChannel dumm22=new DummyChannel();
		dumm22.setChannelName("第2_2个栏目");dumm22.setId("22");dumm22.setpId("2");
		DummyChannel dumm23=new DummyChannel();
		dumm23.setChannelName("第2_3个栏目");dumm23.setId("23");dumm23.setpId("2");
		DummyChannel dumm24=new DummyChannel();
		dumm24.setChannelName("第2_4个栏目");dumm24.setId("24");dumm24.setpId("2");
		
		
		DummyChannel dumm211=new DummyChannel();
		dumm211.setChannelName("第2_1_1个栏目");dumm211.setId("211");dumm211.setpId("21");
		DummyChannel dumm212=new DummyChannel();
		dumm212.setChannelName("第2_1_2个栏目");dumm212.setId("212");dumm212.setpId("21");
		DummyChannel dumm213=new DummyChannel();
		dumm213.setChannelName("第2_1_3个栏目");dumm213.setId("213");dumm213.setpId("21");
		DummyChannel dumm214=new DummyChannel();
		dumm214.setChannelName("第2_1_4个栏目");dumm214.setId("214");dumm214.setpId("21");
		
		DummyChannel dumm221=new DummyChannel();
		dumm221.setChannelName("第2_2_1个栏目");dumm221.setId("221");dumm221.setpId("22");
		DummyChannel dumm222=new DummyChannel();
		dumm222.setChannelName("第2_2_2个栏目");dumm222.setId("222");dumm222.setpId("22");
		DummyChannel dumm223=new DummyChannel();
		dumm223.setChannelName("第2_2_3个栏目");dumm223.setId("223");dumm223.setpId("22");
		DummyChannel dumm224=new DummyChannel();
		dumm224.setChannelName("第2_2_4个栏目");dumm224.setId("224");dumm224.setpId("22");
		
		DummyChannel dumm231=new DummyChannel();
		dumm231.setChannelName("第2_3_1个栏目");dumm231.setId("231");dumm231.setpId("23");
		DummyChannel dumm232=new DummyChannel();
		dumm232.setChannelName("第2_3_2栏目");dumm232.setId("232");dumm232.setpId("23");
		DummyChannel dumm233=new DummyChannel();
		dumm233.setChannelName("第2_3_3栏目");dumm233.setId("233");dumm233.setpId("23");
		DummyChannel dumm234=new DummyChannel();
		dumm234.setChannelName("第2_3_4栏目");dumm234.setId("234");dumm234.setpId("23");
		
		DummyChannel dumm241=new DummyChannel();
		dumm241.setChannelName("第2_4_1栏目");dumm241.setId("241");dumm241.setpId("24");
		DummyChannel dumm242=new DummyChannel();
		dumm242.setChannelName("第2_4_2栏目");dumm242.setId("242");dumm242.setpId("24");
		DummyChannel dumm243=new DummyChannel();
		dumm243.setChannelName("第2_4_3栏目");dumm243.setId("243");dumm243.setpId("24");
		DummyChannel dumm244=new DummyChannel();
		dumm244.setChannelName("第2_4_4栏目");dumm244.setId("244");dumm244.setpId("24");
		
		
		DummyChannel dumm2411=new DummyChannel();
		dumm2411.setChannelName("第2_4_1_1栏目");dumm2411.setId("2411");dumm2411.setpId("241");
		DummyChannel dumm2412=new DummyChannel();
		dumm2412.setChannelName("第2_4_1_2栏目");dumm2412.setId("2412");dumm2412.setpId("241");
		DummyChannel dumm2413=new DummyChannel();
		dumm2413.setChannelName("第2_4_1_3栏目");dumm2413.setId("2413");dumm2413.setpId("241");
		DummyChannel dumm2414=new DummyChannel();
		dumm2414.setChannelName("第2_4_1_4栏目");dumm2414.setId("2414");dumm2414.setpId("241");
		
		
		list.add(dumm);
		
		list.add(dumm24);
		list.add(dumm23);
		list.add(dumm22);
		list.add(dumm21);
		
		list.add(dumm4);
		
		list.add(dumm3);
		list.add(dumm31);
		list.add(dumm32);
		list.add(dumm33);
		list.add(dumm34);
		
		list.add(dumm244);
		list.add(dumm243);
		list.add(dumm242);
		list.add(dumm241);
		
		list.add(dumm222);
		list.add(dumm221);
		list.add(dumm234);
		list.add(dumm233);
		
		list.add(dumm224);
		list.add(dumm223);
		
		list.add(dumm232);
		list.add(dumm231);
		
		list.add(dumm214);
		list.add(dumm213);
		list.add(dumm212);
		list.add(dumm211);
		
		list.add(dumm2);
		list.add(dumm5);
		list.add(dumm6);
		list.add(dumm7);
		
		list.add(dumm2411);
		list.add(dumm2412);
		list.add(dumm2413);
		list.add(dumm2414);
		
		
		return list;
	}
	
	*/
	
	public static String callback(String str) {
		try {
			return str;
		}
		catch (Exception e) {
			return str;
			
		}finally {
			System.out.println("执行完毕之后执行");
		}
	}
	
	public static int binary_search(int[] array, int n, int value)
    {
        //write your code here
        int low = 0;
        while (low <= n) {
            int middle = (low + n) / 2;
            if (value == array[middle]) {
                return middle;
            }
            else if (value < array[middle]) {
                n = middle - 1;
            }
            else {
                low = middle + 1;
            }
        }
        return -1;
      
    }

	/**
	 * 判断字符串是否为空(包括：长度为零，空串，NULL)
	 */
	public static boolean isNotBlank(String str) {
		return !StringUtils.isBlank(str);
	}

	/***
	 * 获取请求连接中的关键字
	 * @param map
	 * @return
	 */
	public static String keywork(String url) {
		if(isBlank(url))
			return null;
		 String keyword=null;
		 Map<String,String> map=URLRequest(url);
		 String encode=isNotBlank(map.get("ie"))?map.get("ie"):"UTF-8";
       try {
			keyword=URLDecoder.decode(map.get("wd"), encode);
		}
		catch (Exception e) {}
		if(StringUtils.isEmpty(keyword)) {
			try {
				keyword=URLDecoder.decode(map.get("q"), encode);
			}
			catch (Exception e) {}
		}
		if(StringUtils.isEmpty(keyword)) {
			try {
				keyword=URLDecoder.decode(map.get("w"), "gbk");
			}
			catch (Exception e) {}
		}
		return keyword;
	}
	
	/**
	 * 解析出url请求的路径，包括页面
	 * 
	 * @param strURL
	 *            url地址
	 * @return url路径
	 */
	public static String UrlPage(String strURL) {
		String strPage = null;
		String[] arrSplit = null;
		strURL = strURL.trim().toLowerCase();
		arrSplit = strURL.split("[?]");
		if (strURL.length() > 0) {
			if (arrSplit.length > 1) {
				if (arrSplit[0] != null) {
					strPage = arrSplit[0];
				}
			}
		}
		return strPage;
	}
	/**
	 * 解析出url参数中的键值对 如 "index.jsp?Action=del&id=123"，解析出Action:del,id:123存入map中
	 * 
	 * @param URL
	 *            url地址
	 * @return url请求参数部分
	 */
	public static Map<String, String> URLRequest(String URL) {
		Map<String, String> mapRequest = new HashMap<String, String>();
		String[] arrSplit = null;
		String strUrlParam = TruncateUrlPage(URL);
		if (strUrlParam == null) {
			return mapRequest;
		}
		// 每个键值为一组 www.2cto.com
		arrSplit = strUrlParam.split("[&]");
		for (String strSplit : arrSplit) {
			String[] arrSplitEqual = null;
			arrSplitEqual = strSplit.split("[=]");
			// 解析出键值
			if (arrSplitEqual.length > 1) {
				// 正确解析
				mapRequest.put(arrSplitEqual[0], arrSplitEqual[1]);
			}
			else {
				if (arrSplitEqual[0] != "") {
					// 只有参数没有值，不加入
					mapRequest.put(arrSplitEqual[0], "");
				}
			}
		}
		return mapRequest;
	}
	
	/**
	 * 去掉url中的路径，留下请求参数部分
	 * 
	 * @param strURL
	 *            url地址
	 * @return url请求参数部分
	 */
	private static String TruncateUrlPage(String strURL) {
		String strAllParam = null;
		String[] arrSplit = null;
		strURL = strURL.trim().toLowerCase();
		arrSplit = strURL.split("[?]");
		if (strURL.length() > 1) {
			if (arrSplit.length > 1) {
				if (arrSplit[1] != null) {
					strAllParam = arrSplit[1];
				}
			}
		}
		return strAllParam;
	}
	
	/**
	 * 返回指定字符在此字符串中最后一次出现处的索引，从指定的索引处开始进行反向查找。
	 * 
	 * @param subject
	 *            被查找字符串 。
	 * @param search
	 *            要查找的子字符串。
	 * @param fromIndex
	 *            开始查找的索引。fromIndex 的值没有限制。如果它大于等于此字符串的长度，则与它小于此字符串长度减 1
	 *            的效果相同：将查找整个字符串。 如果它为负，则与它为 -1 的效果相同：返回 -1。
	 * @return 在此对象表示的字符序列（小于等于 fromIndex）中最后一次出现该字符的索引； 如果在该点之前未出现该字符，则返回 -1
	 */
	public static int ignoreCaseLastIndexOf(String subject, String search,
			int fromIndex) {
		// 当被查找字符串或查找子字符串为空时，抛出空指针异常。
		if (subject == null || search == null) {
			throw new NullPointerException("输入的参数为空");
		}
		if (search.equals("")) {
			return fromIndex >= subject.length() ? subject.length() : fromIndex;
		}
		fromIndex = fromIndex >= subject.length() ? subject.length() - 1
				: fromIndex;

		int index1 = fromIndex;
		int index2 = 0;
		char c1;
		char c2;

		loop1: while (true) {

			if (index1 >= 0) {
				c1 = subject.charAt(index1);
				c2 = search.charAt(index2);
			}
			else {
				break loop1;
			}
			while (true) {
				// 判断两个字符是否相等
				if (isEqual(c1, c2)) {
					if (index1 < subject.length() - 1
							&& index2 < search.length() - 1) {
						c1 = subject.charAt(++index1);
						c2 = search.charAt(++index2);
					}
					else if (index2 == search.length() - 1) {
						return fromIndex;
					}
					else {
						break loop1;
					}
				}
				else {
					// 在比较时，发现查找子字符串中某个字符不匹配，则重新开始查找子字符串
					index2 = 0;
					break;
				}
			}
			// 重新查找子字符串的位置
			index1 = --fromIndex;
		}
		return -1;
	}

	/**
	 * 判断两个字符是否相等。
	 * 
	 * @param c1
	 *            字符1
	 * @param c2
	 *            字符2
	 * @return 若是英文字母，不区分大小写，相等true，不等返回false； 若不是则区分，相等返回true，不等返回false。
	 */
	private static boolean isEqual(char c1, char c2) {
		if (((97 <= c1 && c1 <= 122) || (65 <= c1 && c1 <= 90))
				&& ((97 <= c2 && c2 <= 122) || (65 <= c2 && c2 <= 90))
				&& ((c1 - c2 == 32) || (c2 - c1 == 32))) {
			return true;
		}
		else if (c1 == c2) {
			return true;
		}
		return false;
	}

	/**
	 * 获取系统当前时间
	 * 
	 * @return Date类型
	 */
	public static Date getSystDate() {
		Calendar c = Calendar.getInstance();
		c.setTimeInMillis(new Date().getTime());
		return c.getTime();
	}

	public static String getSysYyyyMMDD() {
		Date date = new Date();
		SimpleDateFormat sf = new SimpleDateFormat("yyyyMMdd");
		return sf.format(date.getTime());
	}

	public static String replaceStr(String str, String replace,String... target) {
		if (str != null && target != null && replace != null) {
			for (String s : target) {
				str=str.replace(s, replace);
			}
			return str;
		}
		else
			return null;
	}

	/**
	 * 根据拆分字符分隔数组
	 * 
	 * @param str
	 *            传入字符串
	 * @param spl
	 *            分隔字符
	 * @return int[]
	 */
	public static int[] getSplitByString(String str, String spl) {
		String[] strs = str.split(spl);
		int[] inte = new int[strs.length];
		for (int i = 0; i < strs.length; i++) {
			try {
				inte[i] = Integer.valueOf(strs[i]);
			}
			catch (Exception e) {
				return null;
			}
		}
		return inte;
	}

	public static String[] getSplitStringByString(String str, String spl) {
		String[] strs = str.split(spl);
		String[] inte = new String[strs.length];
		for (int i = 0; i < strs.length; i++) {
			if (isNotBlank(strs[i])) {
				inte[i] = strs[i];
			}
		}
		return inte;
	}

	public static String getFileDirectory(String path) {
		return replaceStr(path, "", FilenameUtils.getName(path));
	}

	public static String getEN(Object t) {
		return t.getClass().getSimpleName();
	}
	
	public static void main(String[] args) {
		
		List<String> c=new ArrayList<String>();
		c.add("医院新闻");
		c.add("2013招聘计划");
		c.add("保健知识");
		c.add("护理动态");
		c.add("健康教育");
		c.add("健康宣教");
		c.add("生活浪花");
		c.add("天使情怀");
		c.add("学术动态");
		c.add("医疗常识");
		c.add("医学进展");
		c.add("医院通告");
		c.add("医院新闻");
		c.add("医院资讯");
		c.add("优质护理");
		c.add("招聘信息");
		
		for (String s : c) {
			if(s.equals("2013招聘计划"))continue;
			System.out.println(s);
		}

		
		
	}

	public static String getENL(Object t) {
		if(t==null) return "false";
		return t.getClass().getSimpleName().toLowerCase();
	}

	public static String getEN(String en) {
		return en.substring(en.lastIndexOf(".") + 1, en.length());
	}

	public static String getOperator() {
		String userIp = "";
		String userAccount = "";
		HttpServletRequest request = null;
		try {
			request = ApplicationUtils.getRequest();
		}
		catch (Exception e1) {
			request = null;
		}
		try {
			if (ApplicationUtils.getIpAddr() != null
					&& ApplicationUtils.getUser() != null) {
				userIp = ApplicationUtils.getIpAddr();
				userAccount = ApplicationUtils.getUser().getAccount();
			}
			else if (ApplicationUtils.getUser() == null && request != null) {
				userIp = ApplicationUtils.getIpAddr();
			}
		}
		catch (Exception e) {
			userIp = "异步请求";
			userAccount = "";
		}
		userAccount = isNotBlank(userAccount) ? "账号:" + userAccount : "未登录用户";
		return "操作者[" + "IP:" + userIp + "," + userAccount + "]";
	}

	public static String getOperatorAction() {
		String userIp = "";
		String userAccount = "";
		HttpServletRequest request = null;
		try {
			request = ApplicationUtils.getRequest();
		}
		catch (Exception e1) {
			request = null;
		}
		try {
			if (ApplicationUtils.getIpAddr() != null
					&& ApplicationUtils.getUser() != null) {
				userIp = ApplicationUtils.getIpAddr();
				// userIp+=";映射地址:"+ValidateUtils.getIpAddress(userIp);
				userAccount = ApplicationUtils.getUser().getAccount();
			}
			else if (ApplicationUtils.getUser() == null && request != null) {
				userIp = ApplicationUtils.getIpAddr();
				// userIp+=";映射地址:"+ValidateUtils.getIpAddress(userIp);
			}
		}
		catch (Exception e) {
			userIp = "异步请求";
			userAccount = "";
		}
		userAccount = isNotBlank(userAccount) ? "账号:" + userAccount : "未登录用户";
		return "操作者[" + "IP:" + userIp + ";" + userAccount + "]";
	}

	public static final String full2HalfChange(String QJstr) {
		StringBuffer outStrBuf = new StringBuffer("");
		String Tstr = "";
		byte[] b = null;
		for (int i = 0; i < QJstr.length(); i++) {
			Tstr = QJstr.substring(i, i + 1);
			// 全角空格转换成半角空格
			if (Tstr.equals("　")) {
				outStrBuf.append(" ");
				continue;
			}
			try {
				b = Tstr.getBytes("unicode");
				// 得到 unicode 字节数据
				if (b[2] == -1) {
					// 表示全角
					b[3] = (byte) (b[3] + 32);
					b[2] = 0;
					outStrBuf.append(new String(b, "unicode"));
				}
				else {
					outStrBuf.append(Tstr);
				}
			}
			catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}

		} // end for.
		return outStrBuf.toString();
	}

	public static Calendar getCNWeekStatr() {
		Calendar calendar = Calendar.getInstance();
		int min = calendar.getActualMinimum(Calendar.DAY_OF_WEEK); // 获取周开始基准
		int current = calendar.get(Calendar.DAY_OF_WEEK); // 获取当天周内天数
		if (current == 1)
			min = min - 6;
		else
			min = min + 1;
		calendar.add(Calendar.DAY_OF_WEEK, min - current); // 当天-基准，获取周开始日期
		return calendar;
	}

	public static Calendar getCNWeekEnd(Calendar weekStatrDate) {// 当前这周的最后一天
		weekStatrDate.add(Calendar.DAY_OF_WEEK, 6); // 开始+6，获取周结束日期
		return weekStatrDate;
	}

	public static void pringSuccessImg(String a) {
		try {
			FileReader reader = new FileReader("E:\\successImg.txt");
			BufferedReader br = new BufferedReader(reader);
			String s = null;
			String realString = "";
			while ((s = br.readLine()) != null) {
				realString += s.toString() + "\r\n";
			}
			br.close();
			reader.close();
			FileWriter fileWriter = new FileWriter("E:\\successImg.txt");
			fileWriter.write(realString + a);
			fileWriter.flush();
			fileWriter.close();
		}
		catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void pringTxt(String a) {
		try {
			FileReader reader = new FileReader("E:\\ErrorImg.txt");
			BufferedReader br = new BufferedReader(reader);
			String s = null;
			String realString = "";
			while ((s = br.readLine()) != null) {
				realString += s.toString() + "\r\n";
			}
			br.close();
			reader.close();
			FileWriter fileWriter = new FileWriter("E:\\ErrorImg.txt");
			fileWriter.write(realString + a);
			fileWriter.flush();
			fileWriter.close();
		}
		catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void pringUserAccount(String a) {
		try {
			FileReader reader = new FileReader("E:\\userAccount.txt");
			BufferedReader br = new BufferedReader(reader);
			String s = null;
			String realString = "";
			while ((s = br.readLine()) != null) {
				realString += s.toString() + "\r\n";
			}
			br.close();
			reader.close();
			FileWriter fileWriter = new FileWriter("E:\\userAccount.txt");
			fileWriter.write(realString + a);
			fileWriter.flush();
			fileWriter.close();
		}
		catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static String replaceHtml(String html) {
		if (isBlank(html))
			return "";
		String str = html.replaceAll("\\&[a-zA-Z]{1,10};", "").replaceAll(
				"<[^>]*>", "");
		str = str.replaceAll("[(/>)<]", "");
		return str;
	}
	
	/**
     * 比较两个字符串（大小写敏感）。
     * <pre>
     * StringUtil.equals(null, null)   = true
     * StringUtil.equals(null, "abc")  = false
     * StringUtil.equals("abc", null)  = false
     * StringUtil.equals("abc", "abc") = true
     * StringUtil.equals("abc", "ABC") = false
     * </pre>
     *
     * @param str1 要比较的字符串1
     * @param str2 要比较的字符串2
     *
     * @return 如果两个字符串相同，或者都是<code>null</code>，则返回<code>true</code>
     */
    public static boolean equals(String str1, String str2) {
        if (str1 == null) {
            return str2 == null;
        }

        return str1.equals(str2);
    }
    
    /**
     * 检查字符串是否为<code>null</code>或空字符串<code>""</code>。
     * <pre>
     * StringUtil.isEmpty(null)      = true
     * StringUtil.isEmpty("")        = true
     * StringUtil.isEmpty(" ")       = false
     * StringUtil.isEmpty("bob")     = false
     * StringUtil.isEmpty("  bob  ") = false
     * </pre>
     *
     * @param str 要检查的字符串
     *
     * @return 如果为空, 则返回<code>true</code>
     */
    public static boolean isEmpty(String str) {
        return ((str == null) || (str.length() == 0));
    }
    /**将string数组转换为string*/
    public static String arrayToStr(String[] strs) {
		String str="";
		if(strs==null)return null;
		for (String s : strs) {
			str+=s;
		}
		return str;
	}
}
