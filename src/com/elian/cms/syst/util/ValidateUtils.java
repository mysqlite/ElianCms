package com.elian.cms.syst.util;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.jdom.Document;
import org.jdom.Element;
import org.jdom.input.SAXBuilder;

/**
 * 字符验证
 * 
 * @author gcy
 */
@SuppressWarnings({"unchecked"})
public class ValidateUtils {
	/** 有道验证地址 */
	public static final String YOUDAO = "http://www.youdao.com/smartresult-xml/search.s";
	/** Email正则表达式 */
	public static final String EMAIL = "^//w+([-+.]//w+)*@//w+([-.]//w+)*//.//w+([-.]//w+)*$";
	public static final String ISNOTCHINA = "[^\u4e00-\u9fa5]+$";
	/** 电话号码正则表达式 修正[不支持0757-8765432] */
	// public static final String PHONE = "^0(10|2[0-5789]|\\d{3})\\d{7,8}$";//
	// "(^(//d{2,4}[-_－—]?)?//d{3,8}([-_－—]?//d{3,8})?([-_－—]?//d{1,7})?$)|(^0?1[35]//d{9}$)";
	public static final String PHONE = "(\\(\\d{3,4}\\)|\\d{3,4}-|\\s)?\\d{7,14}";
	/**
	 * 手机号码正则表达式 修正@gcy
	 */
	public static final String MOBILE = "^((13[0-9])|(15[^4,\\D])|(18[0,0-9]))\\d{8}$";// "^(13[0-9]|15[0|3|6|7|8|9]|18[8|9])//d{8}$";

	/**
	 * IP地址正则表达式
	 */
	public static final String IPADDRESS = "((?:(?:25[0-5]|2[0-4]//d|[01]?//d?//d)//.){3}(?:25[0-5]|2[0-4]//d|[01]?//d?//d))";

	/**
	 * Integer正则表达式
	 */
	public static final String INTEGER = "^-?(([1-9]//d*$)|0)";
	/**
	 * 负整数正则表达式
	 */
	public static final String INTEGER_POSITIVE = "^-[1-9]//d*|0$";
	/**
	 * Double正则表达式
	 */
	public static final String DOUBLE = "^-?([1-9]//d*//.//d*|0//.//d*[1-9]//d*|0?//.0+|0)$";
	/**
	 * 正Double正则表达式　
	 */
	public static final String DOUBLE_NEGATIVE = "^[1-9]//d*//.//d*|0//.//d*[1-9]//d*|0?//.0+|0$";
	/**
	 * 负Double正则表达式
	 */
	public static final String DOUBLE_POSITIVE = "^(-([1-9]//d*//.//d*|0//.//d*[1-9]//d*))|0?//.0+|0$";
	/**
	 * 年龄正则表达式(匹配0-120岁)
	 */
	public static final String AGE = "^(?:[1-9][0-9]?|1[01][0-9]|120)$";
	/**
	 * 邮编正则表达式(国内6位邮编)
	 */
	public static final String CODE = "[1-9]//d{5}(?!//d)";
	/**
	 * 匹配由数字、26个英文字母或者下划线组成的字符串
	 */
	public static final String STR_ENG_NUM_ = "^w+$";
	/**
	 * 匹配由数字和26个英文字母组成的字符串
	 */
	public static final String STR_ENG_NUM = "^//w+$";
	/**
	 * 匹配简体中文、英文、数字
	 */
	public static final String STR_CHINA_ENG_NUM = "^[\u4E00-\u9FA5A-Za-z0-9_]+$";
	/**
	 * 匹配由26个英文字母组成的字符串
	 */
	public static final String STR_ENG = "^[A-Za-z]+$";
	/**
	 * 匹配中文字符
	 */
	public static final String STR_CHINA = "^[\u4E00-\u9FA5]+$";
	/**
	 * 过滤特殊字符串正则 regEx=
	 * "[`~!@#$%^&*()+=|{}':;',//[//].<>/?~！@#￥%……&*（）——+|{}【】‘；：”“’。，、？]";
	 */
	public static final String STR_SPECIAL = "[`~!@#$%^&*()+=|{}':;',//[//].<>/?~！@#￥%……&*（）——+|{}【】‘；：”“’。，、？]";
	/**
	 *只能输英文 数字 中文
	 */
	public static final String STR_ENG_CHA_NUM = "^[a-zA-Z0-9/u4e00-/u9fa5]+$";
	/***
	 * 日期正则 支持： YYYY-MM-DD YYYY/MM/DD YYYY_MM_DD YYYYMMDD YYYY.MM.DD的形式
	 */
	public static final String DATE_ALL = "((^((1[8-9]//d{2})|([2-9]//d{3}))([-/////._]?)(10|12|0?[13578])([-/////._]?)(3[01]|[12][0-9]|0?[1-9])$)"
			+ "|(^((1[8-9]//d{2})|([2-9]//d{3}))([-/////._]?)(11|0?[469])([-/////._]?)(30|[12][0-9]|0?[1-9])$)"
			+ "|(^((1[8-9]//d{2})|([2-9]//d{3}))([-/////._]?)(0?2)([-/////._]?)(2[0-8]|1[0-9]|0?[1-9])$)|(^([2468][048]00)([-/////._]?)(0?2)([-/////._]?)(29)$)|(^([3579][26]00)"
			+ "([-/._]?)(0?2)([-/._]?)(29)$)"
			+ "|(^([1][89][0][48])([-/._]?)(0?2)([-/////._]?)(29)$)|(^([2-9][0-9][0][48])([-/._]?)"
			+ "(0?2)([-/._]?)(29)$)"
			+ "|(^([1][89][2468][048])([-/._]?)(0?2)([-/._]?)(29)$)|(^([2-9][0-9][2468][048])([-/._]?)(0?2)"
			+ "([-/._]?)(29)$)|(^([1][89][13579][26])([-/._]?)(0?2)([-/._]?)(29)$)|"
			+ "(^([2-9][0-9][13579][26])([-/._]?)(0?2)([-/._]?)(29)$))";
	/**
	 * 年-月-日 日期正则
	 */
	public static final String DATE = "^((\\d{2}(([02468][048])|([13579][26]))[\\-\\/\\s]?((((0?[13578])|(1[02]))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(3[01])))|(((0?[469])|(11))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(30)))|(0?2[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])))))|(\\d{2}(([02468][1235679])|([13579][01345789]))[\\-\\/\\s]?((((0?[13578])|(1[02]))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(3[01])))|(((0?[469])|(11))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(30)))|(0?2[\\-\\/\\s]?((0?[1-9])|(1[0-9])|(2[0-8]))))))";
	/**
	 * URL正则表达式 匹配 http www ftp
	 */
	public static final String URL = "^(http|www|ftp|)?(:/)?(//w+(-//w+)*)(//.(//w+(-//w+)*))*((://d+)?)(/(//w+(-//w+)*))*(//.?(//w)*)(//?)?"
			+ "(((//w*%)*(//w*//?)*(//w*:)*(//w*//+)*(//w*//.)*(//w*&)*(//w*-)*(//w*=)*(//w*%)*(//w*//?)*"
			+ "(//w*:)*(//w*//+)*(//w*//.)*"
			+ "(//w*&)*(//w*-)*(//w*=)*)*(//w*)*)$";

	public static final String HTTPURL = "http(s)?:\\/\\/([\\w-]+\\.)+[\\w-]+(\\/[\\w- .\\/?%&=]*)?";
	/**
	 * 身份证正则表达式
	 */
	public static final String IDCARD = "((11|12|13|14|15|21|22|23|31|32|33|34|35|36|37|41|42|43|44|45|46|50|51|52|53|54|61|62|63|64|65)[0-9]{4})"
			+ "(([1|2][0-9]{3}[0|1][0-9][0-3][0-9][0-9]{3}"
			+ "[Xx0-9])|([0-9]{2}[0|1][0-9][0-3][0-9][0-9]{3}))";

	/** QQ号正则 */
	public static final String QQ = "[1-9][0-9]{4,14}";
	/** 药品批准文号 */
	private static int[] MED_AREAS = { 10, 11, 12, 13, 14, 15, 19, 20, 21, 22,
			23, 31, 32, 33, 34, 35, 36, 37, 41, 42, 43, 44, 45, 46, 50, 51, 52,
			53, 54, 61, 62, 63, 64, 65 };

	/**
	 * 字符串是否为空验证
	 * 
	 * @param str
	 * @return true为通过，false为未通过；
	 */
	public static boolean isBlank(String str) {
		return StringUtils.isBlank(str);
	}

	/**
	 * 判断字段是否为Email 符合返回ture 修正[支持QQ邮箱:如_876292931@vip.qq.com]
	 * 
	 * @param str
	 * @param begin
	 *            最小值
	 * @param end
	 *            最大值
	 * @return boolean
	 */
	public static boolean isEmail(String str, int begin, int end) {
		String EMAIL = "(?=^[\\w.@]{" + begin + "," + end
				+ "}$)\\w+@\\w+(?:\\.[\\w]{2,3}){1,2}";
		return command(EMAIL, str);
	}

	/**
	 * 判断是否为电话号码 符合返回ture
	 * 
	 * @param str
	 * @return boolean
	 */
	public static boolean isPhone(String str) {
		return command(PHONE, str);
	}

	/**
	 * @author Gechuanyi 判断是否为电话或手机号码
	 * @param str
	 * @return 如果非电话或手机号码，返回true
	 */
	public static boolean isNotPhoneAndMobile(String str) {
		return !isPhone(str) & !isMobile(str);
	}

	/**
	 * 判断是否为手机号码 符合返回ture
	 * 
	 * @param str
	 * @return boolean
	 */
	public static boolean isMobile(String str) {
		return command(MOBILE, str);
	}

	/**
	 * 判断药品批准文号是否符合规则
	 * 
	 * @param str
	 * @return {符合条件，返回true}
	 */
	public static boolean isMedicineNo(String str) {
		String no = str;
		try {
			no = str.substring(str.length() - 8, str.length());
			int area = Integer.parseInt(no.substring(0, 2));
			for (int i = 0; i < MED_AREAS.length; i++) {
				if (area == MED_AREAS[i]) {
					return true;
				}
			}
		}
		catch (RuntimeException e) {
			return false;
		}
		return false;
	}
	/**
	 * 判断是否为Url 符合返回ture
	 * 
	 * @param str
	 * @return boolean
	 */
	public static boolean isUrl(String str) {
		return command(URL, str);
	}

	public static boolean isHttpUrl(String str) {
		return command(HTTPURL, str);
	}

	public static boolean isPostCode(String str) {
		return command("^[1-9][0-9]{5}$", str);
	}

	/**
	 * 用户名及密码判断
	 * 
	 * @param userOrPwd
	 * @param minLength字符最小长度
	 * @param maxLength字符最大长度
	 * @param（限制范围大小写字母及数字组成，长度限制6-16）
	 * @return true为通过，false为未通过；
	 */
	public static boolean accountFilter(String account, Integer minLength,
			Integer maxLength) {
		String regEx = "[a-z0-9]{" + minLength + "," + maxLength + "}";
		return command(regEx, account);
	}

	public static boolean pwdFilter(String userOrPwd, Integer minLength,
			Integer maxLength) {
		String regEx = "[^\u4e00-\u9fa5]{" + minLength + "," + maxLength + "}";
		return command(regEx, userOrPwd);
	}
	

	/**
	 * 判断是否为IP地址 符合返回ture
	 * 
	 * @param str
	 * @return boolean
	 */
	public static boolean isIpAddress(String str) {
		return command(IPADDRESS, str);
	}

	/**
	 * 判断字段是否为数字 正负整数 正负浮点数 符合返回ture
	 * 
	 * @param str
	 * @return boolean
	 */
	public static boolean isNumber(String str) {
		return command(DOUBLE, str);
	}

	/**
	 * 判断字段是否为INTEGER 符合返回ture
	 * 
	 * @param str
	 * @return boolean
	 */
	public static boolean isInteger(String str) {
		try {
			Integer.parseInt(str);
			return true;
		}
		catch (NumberFormatException e) {
			return false;
		}
	}

	/**
	 * 判断字段是否为正整数正则表达式 >=0 符合返回ture
	 * 
	 * @param str
	 * @return boolean
	 */
	public static boolean isINTEGER_NEGATIVE(String str) {
		Integer a = 0;
		try {
			a = Integer.parseInt(str);
			return true;
		}
		catch (NumberFormatException e) {
			a = -1;
			return false;
		}
		finally {
			if (a <= -1) {
				return false;
			}
		}
	}
	
	/**
	 * 判断字段是否为正整数 符合返回ture
	 * @param str
	 * @return boolean
	 */
	public static boolean isINTEGER_NEGATIVE(Integer str) {
		if (str == null) {
			return false;
		}
		else if (str <= -1) {
			return false;
		}
		else {
			return true;
		}
	}

	/**
	 * 判断字段是否为负整数正则表达式 <=0 符合返回ture
	 * 
	 * @param str
	 * @return boolean
	 */
	public static boolean isINTEGER_POSITIVE(String str) {
		Integer a = 0;
		try {
			a = Integer.parseInt(str);
			return true;
		}
		catch (NumberFormatException e) {
			a = 1;
			return false;
		}
		finally {
			if (a >= 1) {
				return false;
			}
		}
	}

	/**
	 * 申明：此方法不可用 判断字段是否为DOUBLE 符合返回ture
	 * 
	 * @param str
	 * @return boolean
	 */
	public static boolean isDouble(String str) {
		try {
			Double.parseDouble(str);
			return true;
		}
		catch (NumberFormatException e) {
			return false;
		}
	}

	/**
	 * 申明：此方法不可用 判断字段是否为正浮点数正则表达式 >=0 符合返回ture
	 * 
	 * @param str
	 * @return boolean
	 */
	public static boolean isDOUBLE_NEGATIVE(String str) {
		return command(DOUBLE_NEGATIVE, str);
	}

	/**
	 * 申明：此方法不可用 判断字段是否为负浮点数正则表达式 <=0 符合返回ture
	 * 
	 * @param str
	 * @return boolean
	 */
	public static boolean isDOUBLE_POSITIVE(String str) {
		try {
			double d = Double.parseDouble(str);
			return d <= 0 ? true : false;
		}
		catch (NumberFormatException e) {
			return false;
		}
	}

	/**
	 * 申明：此方法不可用 判断字段是否为日期 符合返回ture
	 * 
	 * @param str
	 * @return boolean
	 */
	public static boolean isDate(String str) {
		return command(DATE_ALL, str);
	}

	/**
	 * 判断是否为日期(yyyy-MM-dd)
	 * 
	 * @param str
	 * @return
	 */
	public static boolean isDateYYYY_MM_DD(String str) {
		return command(DATE, str);
	}

	/**
	 * 判断是否为QQ号
	 * 
	 * @param str
	 * @return 正确QQ号为true
	 */
	public static boolean isQQ(String str) {
		return command(QQ, str);
	}

	/**
	 * 判断字段是否为年龄 符合返回ture
	 * 
	 * @param str
	 * @return boolean
	 */
	public static boolean isAge(String str) {
		return command(AGE, str);
	}

	/**
	 * 判断字段是否超长 字串为空返回fasle, 超过长度{leng}返回ture 反之返回false
	 * 
	 * @param str
	 * @param leng
	 * @return boolean
	 */
	public static boolean isLengOut(String str, int leng) {
		return isBlank(str) ? false : str.trim().length() > leng;
	}

	/**
	 * 申明：此方法不可用 判断字段是否为身份证 符合返回ture
	 * 
	 * @param str
	 * @return boolean
	 */
	public static boolean isIdCard(String str) {
		if (isBlank(str))
			return false;
		if (str.trim().length() == 15 || str.trim().length() == 18) {
			return command(IDCARD, str);
		}
		else {
			return false;
		}
	}

	/**
	 * @author Gechuanyi
	 * @param str
	 *            被验证的身份证号码
	 * @return 验证通过返回true
	 */
	public static boolean isIdCardFormart(String str) {
		return IdCardUtils.validateCard(str);
	}

	/**
	 * 判断字段是否为邮编 符合返回ture
	 * 
	 * @param str
	 * @return boolean
	 */
	public static boolean isCode(String str) {
		return command(CODE, str);
	}

	/**
	 * 判断字符串是不是全部是汉字
	 * 
	 * @param str
	 * @return boolean
	 */
	public static boolean isChina(String str) {
		return command(STR_CHINA, str);
	}

	/**
	 * 判断字符串是不是全部是英文字母
	 * 
	 * @param str
	 * @return boolean
	 */
	public static boolean isEnglish(String str) {
		return command(STR_ENG, str);
	}

	/**
	 * 判断字符串是不是全部是英文字母+数字
	 * 
	 * @param str
	 * @return boolean
	 */
	public static boolean isENG_NUM(String str) {
		return command(STR_ENG_NUM, str);
	}

	/**
	 * 判断字符串是不是全部是英文字母+数字+下划线
	 * 
	 * @param str
	 * @return boolean
	 */
	public static boolean isENG_NUM_(String str) {
		return command(STR_ENG_NUM_, str);
	}

	/**
	 *是否为除汉字之外的字符
	 * 
	 * @param str
	 * @return 是 返回true else 返回false
	 * 
	 */
	public static boolean isNotChina(String str) {
		return command(ISNOTCHINA, str);
	}

	/**
	 * 判断字符串是不是全部是中文+英文字母+数字+下划线
	 * 
	 * @param str
	 * @return boolean
	 */
	public static boolean isCHINA_ENG_NUM(String str) {
		return command(STR_CHINA_ENG_NUM, str);
	}

	/**
	 * 过滤特殊字符串 返回过滤后的字符串
	 * 
	 * @param str
	 * @return boolean
	 */
	public static String filterStr(String str) {
		Pattern p = Pattern.compile(STR_SPECIAL);
		Matcher m = p.matcher(str);
		return m.replaceAll("").trim();
	}

	/**
	 * 字符串长度验证
	 * 
	 * @param minLength字符最小长度
	 * @param maxLength字符最大长度
	 * @param（英文 数字 中文）
	 */
	public static boolean strLenth(String str, Integer minLength,
			Integer maxLength) {
		int len = 0;
		if (isCHINA_ENG_NUM(str) && (len = str.trim().length()) >= minLength
				&& len <= maxLength)
			return true;
		return false;
	}

	public static boolean strLenthAll(String str, Integer minLen, Integer maxLen) {
		if (str.trim().length() >= minLen & str.trim().length() <= maxLen)
			return true;
		else
			return false;
	}
	public static final String PWD="\"^(?:(?=.*[A-Z])(?=.*[a-z])(?=.*[0-9])|(?=.*[A-Z])(?=.*[a-z])(?=.*[^A-Za-z0-9])|(?=.*[A-Z])(?=.*[0-9])(?=.*[^A-Za-z0-9])|(?=.*[a-z])(?=.*[0-9])(?=.*[^A-Za-z0-9])).{10,20}$";
	public static boolean pwdFilters(String pwd) {
		return pwd.matches(".*?[^a-zA-Z\\d]+.*?") && pwd.matches(".*?[a-z]+.*?") && pwd.matches(".*?[A-Z]+.*?") && pwd.matches(".*?[\\d]+.*?");
	}

	public static void main(String[] args) {
		System.out.println(pwdFilters("0000001WxW/"));
	}
	
	/**
	 * 提取公共正则方法
	 * 
	 * @param regEx
	 *            正则表达式
	 * @param str
	 *            检测字符
	 * @return true为通过，false为未通过；
	 */
	public static boolean command(String regEx, String str) {
		if (null == str || str.trim().length() <= 0)
			return false;
		Pattern pat = Pattern.compile(regEx);
		Matcher mat = pat.matcher(str);
		return mat.matches();
	}

	public static boolean strValBLOut(String str, int minlen, int maxlen) {
		if (minlen != 0) {
			if (isLengOut(str, maxlen) & StringUtils.isBlank(str)
					& strLenth(str, minlen, maxlen))
				return true;
			else
				return false;
		}
		else {
			if (isLengOut(str, maxlen) & StringUtils.isBlank(str))
				return true;
			else
				return false;
		}
	}

	/**
	 * 电话
	 * 
	 * @param tel
	 * @param 前三位为010或020或前四位最高到5789
	 *            ，限制为7到8位纯数字
	 * @return 检测通过，返回true，反之，false
	 */
	public static boolean isTelephone(String tel) {
		String regEx = "^0(10|2[0-5789]|\\d{3})\\d{7,8}$";
		return command(regEx, tel);
	}

	/**
	 * 第一位为1，第二位3或5或8，第三位0到9，后8位0到9，纯数字，共11位 年龄
	 * 
	 * @param age
	 *            被检测数值在minage及maxage之间，包含maxage
	 * @param minage
	 * @param maxage
	 * @param 大于0
	 *            ，小于等于140
	 * @return 满足条件，返回true，反之false；
	 */
	public static boolean isAge(String age, Integer minage, Integer maxage) {
		int ages = Integer.valueOf(age.toString()).intValue();
		if (ages < minage | ages >= maxage) {
			return false;
		}
		else {
			return true;
		}
	}

	/**
	 * 数据库敏感注入
	 * 
	 * @param sql
	 * @return
	 */
	public static String isSql(String sql) {
		String filterText = filterName(sql);
		if (sql.equals(filterText)) {
			return sql;
		}
		else {
			return isSql(filterText);
		}
	}

	private static String filterName(String filterText) {
		String[] list = { "and", "exec", "insert", "select", "delete",
				"update", "truncate", "char", "declare", "'", "--", "\"", "<",
				">", "drop", "fetch", "%" };
		Matcher m = null;
		for (int i = 0, len = list.length; i < len; i++) {
			Pattern p = Pattern.compile(list[i], Pattern.CASE_INSENSITIVE);
			StringBuffer sBuffer = new StringBuffer();
			m = p.matcher(filterText);
			while (m.find()) {
				m.appendReplacement(sBuffer, "");
			}
			m.appendTail(sBuffer);
			filterText = sBuffer.toString();
		}
		return filterText;
	}

	
	public static boolean isIdentity(String identity) {
		List lst = queryXML("id", identity);
		if (lst != null && lst.size() > 0) {
			return true;
		}
		return false;
	}

	public static String getIpAddress(String ip) {
		List list = queryXML("ip", ip);
		String address = "";
		if (list != null && list.size() > 0) {
			for (int i = 0; i < list.size(); i++) {
				Element element = (Element) list.get(i);
				address = element.getChildText("location");
			}
		}
		return address;
	}

	private static List queryXML(String type, String q) {
		SAXBuilder saxb = new SAXBuilder();
		Document doc = null;
		List lst = null;
		Element root = null;
		try {
			doc = saxb.build(YOUDAO + "?type=" + type + "&q=" + q);
			root = doc.getRootElement();
			lst = root.getChildren("product");
		}
		catch (Exception e) {
		}
		return lst;
	}

	/**
	 * 
	 * @param filterTexts
	 *            被检测的字符
	 * @param path
	 *            指定文件路径，必须为txt文件
	 * @return 敏感字词被替换后的text
	 * @throws IOException
	 */
	public static String isTxtFile(String filterTexts, String path)
			throws IOException {
		List list = new ArrayList();
		String regExcom = "[、]|[,]|[@]|[#]|[|]|[+]|[~]|[?]|[？]|[!][ ]|[》]|[《]|[￥]|[…]|[！]|[%]|[&]|[（]|[）]|[【]|[】]|[{]|[}]";
		String stchar = "[u4E00-u9FFF]";
		String filterText = filterTexts;

		FileReader reader = new FileReader(path);// H:/MyEclipse8.6M1/Elian/WebRoot/filter/TextFilter.txt/
		BufferedReader br = new BufferedReader(reader);
		String s = null;
		while ((s = br.readLine()) != null) {
			list.add(s.trim());
		}
		br.close();
		reader.close();
		Matcher m = null;
		for (int i = 0; i < list.size(); i++) {
			Pattern p = Pattern.compile(list.get(i).toString(),
					Pattern.CASE_INSENSITIVE);
			StringBuffer sBuffer = new StringBuffer();
			m = p.matcher(filterText.replaceAll(regExcom, "").replaceAll(
					stchar, ""));
			while (m.find()) {
				m.appendReplacement(sBuffer, "*");
			}
			m.appendTail(sBuffer);
			filterText = sBuffer.toString();
		}
		if (filterTexts.replaceAll(regExcom, "").replaceAll(stchar, "").equals(
				filterText)) {
			return filterTexts;
		}
		return filterText;
	}

	public boolean checkCaptcha(String captcha, HttpServletRequest request) {
		HttpSession sessions = request.getSession();
		if (captcha.equals(sessions.getAttribute("captcha").toString())) {
			return true;
		}
		return false;
	}

}
