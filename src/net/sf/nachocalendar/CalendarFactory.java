// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(1000) fieldsfirst nonlb space 
// Source File Name:   CalendarFactory.java

package net.sf.nachocalendar;

import net.sf.nachocalendar.components.CalendarPanel;
import net.sf.nachocalendar.components.DateField;
import net.sf.nachocalendar.components.DatePanel;
import net.sf.nachocalendar.components.DefaultDayRenderer;
import net.sf.nachocalendar.components.DefaultHeaderRenderer;
import net.sf.nachocalendar.customizer.DirectSetter;
import net.sf.nachocalendar.customizer.PropertiesCustomizer;
import net.sf.nachocalendar.customizer.PropertiesSetter;
import net.sf.nachocalendar.customizer.XMLCustomizer;

public final class CalendarFactory {

	private static PropertiesSetter setter;

	public CalendarFactory() {
	}

	public static DateField createDateField() {
		DateField retorno = new DateField();
		if (setter != null)
			setter.customize(retorno);
		else
			configureDateField(retorno);
		return retorno;
	}

	private static void configureDateField(DateField df) {
		df.setRenderer(new DefaultDayRenderer());
		df.setHeaderRenderer(new DefaultHeaderRenderer());
	}

	public static CalendarPanel createCalendarPanel() {
		CalendarPanel retorno = new CalendarPanel();
		if (setter != null)
			setter.customize(retorno);
		else
			configureCalendarPanel(retorno);
		return retorno;
	}

	public static CalendarPanel createCalendarPanel(int quantity, int orientation) {
		CalendarPanel retorno = new CalendarPanel(quantity, orientation);
		if (setter != null)
			setter.customize(retorno);
		else
			configureCalendarPanel(retorno);
		return retorno;
	}

	public static CalendarPanel createCalendarPanel(int quantity) {
		CalendarPanel retorno = new CalendarPanel(quantity, 1);
		if (setter != null)
			setter.customize(retorno);
		else
			configureCalendarPanel(retorno);
		return retorno;
	}

	private static void configureCalendarPanel(CalendarPanel cp) {
		cp.setRenderer(new DefaultDayRenderer());
		cp.setHeaderRenderer(new DefaultHeaderRenderer());
	}

	private static void configureDatePanel(DatePanel dp) {
		dp.setHeaderRenderer(new DefaultHeaderRenderer());
		dp.setRenderer(new DefaultDayRenderer());
	}

	public static DatePanel createDatePanel() {
		DatePanel retorno = new DatePanel();
		if (setter != null)
			setter.customize(retorno);
		else
			configureDatePanel(retorno);
		return retorno;
	}

	public static DatePanel createDatePanel(boolean showWeekNumbers) {
		DatePanel retorno = new DatePanel(showWeekNumbers);
		if (setter != null)
			setter.customize(retorno);
		else
			configureDatePanel(retorno);
		return retorno;
	}

	static  {
		java.io.InputStream is = ClassLoader.getSystemResourceAsStream("nachocalendar.properties");
		if (is != null)
			try {
				setter = new DirectSetter(new PropertiesCustomizer(is));
			}
			catch (Exception e) { }
		if (setter == null) {
			is = ClassLoader.getSystemResourceAsStream("nachocalendar.xml");
			if (is != null)
				try {
					setter = new DirectSetter(new XMLCustomizer(is));
				}
				catch (Exception e) { }
		}
	}
}
