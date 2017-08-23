package com.elian.cms.syst.converter;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Map;

import org.apache.log4j.Logger;
import org.apache.struts2.util.StrutsTypeConverter;

/**
 * 日期转换器
 */
public class DateTypeConverter extends StrutsTypeConverter {
	private static final Logger log = Logger.getLogger(DateTypeConverter.class);
	public static final String DEFAULT_DATE_FORMAT = "yyyy-MM-dd HH:mm:ss";

	// 暂时只考虑这几种日期格式
	public static final DateFormat[] ACCEPT_DATE_FORMATS = {
			new SimpleDateFormat(DEFAULT_DATE_FORMAT),
			new SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy", new Locale(
					"en")), new SimpleDateFormat("yyyy-MM-dd") };

	@SuppressWarnings("unchecked")
	@Override
	public Date convertFromString(Map context, String[] values, Class toClass) {
		if (values[0] == null || values[0].trim().equals(""))
			return null;
		for (DateFormat format : ACCEPT_DATE_FORMATS) {
			try {
				return format.parse(values[0]);
			}
			catch (ParseException e) {
				continue;
			}
			catch (RuntimeException e) {
				continue;
			}
		}
		log.debug("can not format date string:" + values[0]);
		return null;
	}

	@SuppressWarnings("unchecked")
	@Override
	public String convertToString(Map context, Object o) {
		if (o instanceof Date) {
			SimpleDateFormat format = new SimpleDateFormat(DEFAULT_DATE_FORMAT);
			try {
				return format.format((Date) o);
			}
			catch (RuntimeException e) {
				return "";
			}
		}
		return "";
	}
}