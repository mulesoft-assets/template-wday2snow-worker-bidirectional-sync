/**
 * Mule Anypoint Template
 * Copyright (c) MuleSoft, Inc.
 * All rights reserved.  http://www.mulesoft.com
 */

package org.mule.templates.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

public class DateUtil {
	
	public static String applyTimeZone(String dateObject, String format, String timeZoneOffset){
		DateFormat formatter = new SimpleDateFormat(format);
		Date date = null;
		
		try {
		 date = formatter.parse(dateObject);
		} catch (ParseException e){
			return null;
		}
		
		TimeZone timeZone = TimeZone.getTimeZone(timeZoneOffset);
		
		formatter.setTimeZone(timeZone);
		return formatter.format(date);
	}
}
