// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(1000) fieldsfirst nonlb space 
// Source File Name:   TaskCalendarFactory.java

package net.sf.nachocalendar.tasks;

import java.text.SimpleDateFormat;
import java.util.Locale;
import javax.swing.text.DateFormatter;
import net.sf.nachocalendar.components.CalendarPanel;
import net.sf.nachocalendar.components.DateField;
import net.sf.nachocalendar.components.DatePanel;
import net.sf.nachocalendar.components.DefaultDayRenderer;
import net.sf.nachocalendar.components.DefaultHeaderRenderer;
import net.sf.nachocalendar.components.FormatSymbols;

// Referenced classes of package net.sf.nachocalendar.tasks:
//			TaskDataModel, TaskDecorator

public final class TaskCalendarFactory {

	private static DateFormatter requiredFormatter = new DateFormatter();
	private static Locale requiredLocale = new Locale("");

	private TaskCalendarFactory() {
	}

	public static DateField createDateField(String formatter, Locale userLocale) {
		requiredLocale = userLocale;
		requiredFormatter = new DateFormatter(new SimpleDateFormat(formatter, userLocale));
		new FormatSymbols(requiredFormatter, requiredLocale);
		DateField retorno = new DateField(requiredFormatter);
		configureDateField(retorno);
		return retorno;
	}

	public static DateField createDateField() {
		DateField retorno = new DateField();
		configureDateField(retorno);
		return retorno;
	}

	private static void configureDateField(DateField df) {
		df.setModel(new TaskDataModel());
		df.setRenderer(new TaskDecorator(new DefaultDayRenderer()));
		df.setHeaderRenderer(new DefaultHeaderRenderer());
	}

	public static CalendarPanel createCalendarPanel() {
		CalendarPanel retorno = new CalendarPanel();
		configureCalendarPanel(retorno);
		return retorno;
	}

	public static CalendarPanel createCalendarPanel(int quantity, int orientation) {
		CalendarPanel retorno = new CalendarPanel(quantity, orientation);
		configureCalendarPanel(retorno);
		return retorno;
	}

	public static CalendarPanel createCalendarPanel(int quantity) {
		CalendarPanel retorno = new CalendarPanel(quantity, 1);
		configureCalendarPanel(retorno);
		return retorno;
	}

	private static void configureCalendarPanel(CalendarPanel cp) {
		cp.setModel(new TaskDataModel());
		cp.setRenderer(new TaskDecorator(new DefaultDayRenderer()));
		cp.setHeaderRenderer(new DefaultHeaderRenderer());
	}

	public static DatePanel createDatePanel() {
		DatePanel retorno = new DatePanel();
		configureDatePanel(retorno);
		return retorno;
	}

	public static DatePanel createDatePanel(boolean showWeekNumbers) {
		DatePanel retorno = new DatePanel(showWeekNumbers);
		configureDatePanel(retorno);
		return retorno;
	}

	private static void configureDatePanel(DatePanel dp) {
		dp.setModel(new TaskDataModel());
		dp.setRenderer(new TaskDecorator(new DefaultDayRenderer()));
		dp.setHeaderRenderer(new DefaultHeaderRenderer());
	}

}
