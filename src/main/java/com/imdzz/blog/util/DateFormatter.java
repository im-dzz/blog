package com.imdzz.blog.util;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.stereotype.Component;

@Component
public class DateFormatter {

	public static String getFormateBy(Date date, String format) {
		if (null == date) {
			return null;
		}
		SimpleDateFormat simpliDateFormat = new SimpleDateFormat(format);
		return simpliDateFormat.format(date);
	}
}
