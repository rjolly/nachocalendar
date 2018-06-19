// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(1000) fieldsfirst nonlb space 
// Source File Name:   CalendarUtils.java

package net.sf.nachocalendar.components;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.ResourceBundle;

public final class CalendarUtils {

	private static DateFormat dateformat = DateFormat.getDateInstance();
	private static Calendar calendar1 = new GregorianCalendar();
	private static Calendar calendar2 = new GregorianCalendar();
	private static ResourceBundle bundle = ResourceBundle.getBundle("net.sf.nachocalendar.language");

	private CalendarUtils() {
	}

	public static Date convertToDate(Object o) throws ParseException {
		if (o == null)
			return null;
		if (o instanceof Date)
			return (Date)o;
		if (o instanceof Timestamp)
			return new Date((new java.sql.Date(((Timestamp)o).getTime())).getTime());
		if (o instanceof java.sql.Date)
			return new Date(((java.sql.Date)o).getTime());
		else
			return dateformat.parse(o.toString());
	}

	private static synchronized boolean isSameDay() {
		if (calendar1.get(6) != calendar2.get(6))
			return false;
		return calendar1.get(1) == calendar2.get(1);
	}

	public static synchronized boolean isSameDay(Date d1, Date d2) {
		calendar1.setTime(d1);
		calendar2.setTime(d2);
		return isSameDay();
	}

	public static synchronized boolean isToday(Date date) {
		calendar1.setTimeInMillis(System.currentTimeMillis());
		calendar2.setTime(date);
		return isSameDay();
	}

	public static String getMessage(String key) {
		return bundle.getString(key);
	}

}
