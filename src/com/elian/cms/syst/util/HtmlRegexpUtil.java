package com.elian.cms.syst.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class HtmlRegexpUtil {
	public static void main(String[] args) {
		String str = "＜P＞＆nbsp;＆nbsp;　经过整体搬迁前期认真筹备、周密部署，医院整体搬迁工作顺利进行。8月4日曙光路院区妇产科门诊由行政楼顺利搬迁至门诊楼二楼；8月16日，连江路院区的职能科室顺利搬迁至曙光路院区行政大楼。8月25日，连江路院区的妇科、产科、儿科顺利搬迁至曙光路院区的门诊大楼，病人入住后，医院领导对病人进行慰问，并送上鲜花，充分展现我院人性化服务。＜/P＞  ＜P＞＆nbsp;＜/P＞  ＜P＞＆nbsp;＜IMG　style=＂WIDTH:　429px;　HEIGHT:　340px＂　height=665　src=＂/weifei_uploadfile/20110825111742607.jpg＂　width=854　border=0＞＜/P＞  ＜P＞＆nbsp;＜/P＞  ＜P＞＜IMG　style=＂WIDTH:　436px;　HEIGHT:　317px＂　height=662　src=＂/weifei_uploadfile/20110825111835317.jpg＂　width=861　border=0＞＜/P＞  ＜P＞＆nbsp;＜/P＞  ＜P＞＜IMG　style=＂WIDTH:　440px;　HEIGHT:　304px＂　height=665　src=＂/weifei_uploadfile/20110825111934733.jpg＂　width=744　border=0＞＜/P＞  ＜P＞＆nbsp;＜/P＞  ＜P＞＜IMG　style=＂WIDTH:　443px;　HEIGHT:　316px＂　height=658　src=＂/weifei_uploadfile/20110825112027940.jpg＂　width=866　border=0＞＜/P＞";
		//str = StringUtils.replaceStr(str, "<", "＜");
		//str = StringUtils.replaceStr(str, ">", "＞");
		//str=StringUtils.replaceStr(str, "\"", "＂");
		str=qj2bj(str);
		System.out.println(str.substring(0,1000));
	}

	private final static String regxpForHtml = "<([^>]*)>"; // 过滤所有以<开头以>结尾的标签
	//private final static String regxpForImgTag = "<[url=file://s*img//s+([%5E%3E]*)//s]\\s*img\\s+([^>]*)\\s[/url]*>"; // 找出IMG标签
	//private final static String regxpForImaTagSrcAttrib = "src=\"([^\"]+)\""; // 找出IMG标签的SRC属性

	/**
	 * 
	 * 基本功能：替换标记以正常显示
	 * <p>
	 * 
	 * @param input
	 * @return String
	 */
	public String replaceTag(String input) {
		if (!hasSpecialChars(input)) {
			return input;
		}
		StringBuffer filtered = new StringBuffer(input.length());
		char c;
		for (int i = 0; i <= input.length() - 1; i++) {
			c = input.charAt(i);
			switch (c) {
				case '<':
					filtered.append("&lt;");
					break;
				case '>':
					filtered.append("&gt;");
					break;
				case '"':
					filtered.append("&quot;");
					break;
				case '&':
					filtered.append("&amp;");
					break;
				default:
					filtered.append(c);
			}
		}
		return (filtered.toString());
	}

	/**
	 * 
	 * 基本功能：判断标记是否存在
	 * <p>
	 * 
	 * @param input
	 * @return boolean
	 */
	public boolean hasSpecialChars(String input) {
		boolean flag = false;
		if ((input != null) && (input.length() > 0)) {
			char c;
			for (int i = 0; i <= input.length() - 1; i++) {
				c = input.charAt(i);
				switch (c) {
					case '>':
						flag = true;
						break;
					case '<':
						flag = true;
						break;
					case '"':
						flag = true;
						break;
					case '&':
						flag = true;
						break;
				}
			}
		}
		return flag;
	}

	/**
	 * 
	 * 基本功能：过滤所有以"<"开头以">"结尾的标签
	 * <p>
	 * 
	 * @param str
	 * @return String
	 */
	public static String filterHtml(String str) {
		Pattern pattern = Pattern.compile(regxpForHtml);
		Matcher matcher = pattern.matcher(str);
		StringBuffer sb = new StringBuffer();
		boolean result1 = matcher.find();
		while (result1) {
			matcher.appendReplacement(sb, "");
			result1 = matcher.find();
		}
		matcher.appendTail(sb);
		return sb.toString();
	}

	/**
	 * 
	 * 基本功能：过滤指定标签
	 * <p>
	 * 
	 * @param str
	 * @param tag
	 *            指定标签
	 * @return String
	 */
	public static String fiterHtmlTag(String str, String tag) {
		String regxp = "<[url=file://s/]\\s[/url]*" + tag
				+ "[url=file://s+([%5e%3e]*)//s]\\s+([^>]*)\\s[/url]*>";
		Pattern pattern = Pattern.compile(regxp);
		Matcher matcher = pattern.matcher(str);
		StringBuffer sb = new StringBuffer();
		boolean result1 = matcher.find();
		while (result1) {
			matcher.appendReplacement(sb, "");
			result1 = matcher.find();
		}
		matcher.appendTail(sb);
		return sb.toString();
	}

	/**
	 * 
	 * 基本功能：替换指定的标签
	 * <p>
	 * 
	 * @param str
	 * @param beforeTag
	 *            要替换的标签
	 * @param tagAttrib
	 *            要替换的标签属性值
	 * @param startTag
	 *            新标签开始标记
	 * @param endTag
	 *            新标签结束标记
	 * @return String
	 * @如：替换img标签的src属性值为[img]属性值[/img]
	 */
	public static String replaceHtmlTag(String str, String beforeTag,
			String tagAttrib, String startTag, String endTag) {
		String regxpForTag = "<[url=file://s/]\\s[/url]*" + beforeTag
				+ "[url=file://s+([%5e%3e]*)//s]\\s+([^>]*)\\s[/url]*>";
		String regxpForTagAttrib = tagAttrib + "=\"([^\"]+)\"";
		Pattern patternForTag = Pattern.compile(regxpForTag);
		Pattern patternForAttrib = Pattern.compile(regxpForTagAttrib);
		Matcher matcherForTag = patternForTag.matcher(str);
		StringBuffer sb = new StringBuffer();
		boolean result = matcherForTag.find();
		while (result) {
			StringBuffer sbreplace = new StringBuffer();
			Matcher matcherForAttrib = patternForAttrib.matcher(matcherForTag
					.group(1));
			if (matcherForAttrib.find()) {
				matcherForAttrib.appendReplacement(sbreplace, startTag
						+ matcherForAttrib.group(1) + endTag);
			}
			matcherForTag.appendReplacement(sb, sbreplace.toString());
			result = matcherForTag.find();
		}
		matcherForTag.appendTail(sb);
		return sb.toString();
	}
	
	static final char SBC_CHAR_START = 65281; // 全角！
	static final char SBC_CHAR_END = 65374; // 全角～ 
	static final int CONVERT_STEP = 65248; // 全角半角转换间隔 
	static final char SBC_SPACE = 12288; // 全角空格 12288 
	static final char DBC_SPACE = ' '; 
	public static String qj2bj(String src) { 
		if (src == null) { 
		return src; 
		} 
		StringBuilder buf = new StringBuilder(src.length()); 
		char[] ca = src.toCharArray(); 
		for (int i = 0; i < src.length(); i++) { 
		if (ca[i] >= SBC_CHAR_START && ca[i] <= SBC_CHAR_END) { // 如果位于全角！到全角～区间内 
		buf.append((char) (ca[i] - CONVERT_STEP)); 
		} else if (ca[i] == SBC_SPACE) { // 如果是全角空格 
		buf.append(DBC_SPACE); 
		} else { // 不处理全角空格，全角！到全角～区间外的字符 
		buf.append(ca[i]); 
		} 
		} 
		return buf.toString(); 
		} 
}
