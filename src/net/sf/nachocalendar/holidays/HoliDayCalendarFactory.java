// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(1000) fieldsfirst nonlb space 
// Source File Name:   HoliDayCalendarFactory.java

package net.sf.nachocalendar.holidays;

import java.text.SimpleDateFormat;
import java.util.Locale;
import javax.swing.text.DateFormatter;
import net.sf.nachocalendar.components.CalendarPanel;
import net.sf.nachocalendar.components.DateField;
import net.sf.nachocalendar.components.DatePanel;
import net.sf.nachocalendar.components.DefaultDayRenderer;
import net.sf.nachocalendar.components.DefaultHeaderRenderer;
import net.sf.nachocalendar.components.FormatSymbols;

// Referenced classes of package net.sf.nachocalendar.holidays:
//			HolidayDecorator, HoliDayModel

public final class HoliDayCalendarFactory {

	private static DateFormatter requiredFormatter = new DateFormatter();
	private static Locale requiredLocale = new Locale("");

	private HoliDayCalendarFactory() {
	}

	public static DateField createDateField(String formatter, Locale userLocale) {
		requiredLocale = userLocale;
		requiredFormatter = new DateFormatter(new SimpleDateFormat(formatter, userLocale));
		new FormatSymbols(requiredFormatter, requiredLocale);
		DateField datefield = new DateField();
		datefield.setRenderer(new HolidayDecorator(new DefaultDayRenderer()));
		datefield.setHeaderRenderer(new DefaultHeaderRenderer());
		datefield.setModel(new HoliDayModel());
		return datefield;
	}

	public static DateField createDateField() {
		DateField datefield = new DateField();
		datefield.setRenderer(new HolidayDecorator(new DefaultDayRenderer()));
		datefield.setHeaderRenderer(new DefaultHeaderRenderer());
		datefield.setModel(new HoliDayModel());
		return datefield;
	}

	public static CalendarPanel createCalendarPanel(int quantity, int orientation) {
		CalendarPanel cp = new CalendarPanel(quantity, orientation);
		cp.setRenderer(new HolidayDecorator(new DefaultDayRenderer()));
		cp.setHeaderRenderer(new DefaultHeaderRenderer());
		cp.setModel(new HoliDayModel());
		return cp;
	}

	public static CalendarPanel createCalendarPanel() {
		CalendarPanel cp = new CalendarPanel();
		cp.setRenderer(new HolidayDecorator(new DefaultDayRenderer()));
		cp.setHeaderRenderer(new DefaultHeaderRenderer());
		cp.setModel(new HoliDayModel());
		return cp;
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
		dp.setRenderer(new DefaultDayRenderer());
		dp.setHeaderRenderer(new DefaultHeaderRenderer());
		dp.setModel(new HoliDayModel());
	}

}
