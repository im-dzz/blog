package com.imdzz.blog.util;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.stereotype.Component;

@Component
public class DateFormatter {

	private static final String DEFAULT_FORMAT = "YYYY-mm-DD HH:MM:SS";

	public static String getFormateBy(Date date, String format) {
		if (null == date) {
			return null;
		}
		SimpleDateFormat simpliDateFormat = new SimpleDateFormat(format);
		return simpliDateFormat.format(date);
	}

	public static String getNowDateStr(String format) {
		SimpleDateFormat simpliDateFormat = new SimpleDateFormat(format);
		return simpliDateFormat.format(new Date());
	}

	public static String getNowDateStr() {
		SimpleDateFormat simpliDateFormat = new SimpleDateFormat(DEFAULT_FORMAT);
		return simpliDateFormat.format(new Date());
	}
}
