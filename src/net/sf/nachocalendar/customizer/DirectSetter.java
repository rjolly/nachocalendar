// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(1000) fieldsfirst nonlb space 
// Source File Name:   DirectSetter.java

package net.sf.nachocalendar.customizer;

import java.text.SimpleDateFormat;
import net.sf.nachocalendar.components.CalendarPanel;
import net.sf.nachocalendar.components.DateField;
import net.sf.nachocalendar.components.DatePanel;
import net.sf.nachocalendar.components.DayRenderer;
import net.sf.nachocalendar.components.HeaderRenderer;
import net.sf.nachocalendar.model.DataModel;

// Referenced classes of package net.sf.nachocalendar.customizer:
//			PropertiesSetter, Customizer

public class DirectSetter
	implements PropertiesSetter {

	private boolean initialized;
	private boolean allowsInvalid;
	private boolean printMoon;
	private boolean showOkCancel;
	private boolean showToday;
	private boolean antiAliased;
	private int firstDayOfWeek;
	private Class headerRenderer;
	private Class model;
	private Class renderer;
	private String todayCaption;
	private boolean workingDays[];
	private int selectionMode;
	private int orientation;
	private int scrollPosition;
	private int yearPosition;
	private String dateFormat;

	public DirectSetter(Customizer customizer) {
		firstDayOfWeek = 1;
		selectionMode = 2;
		orientation = 0;
		scrollPosition = 0;
		yearPosition = 0;
		init(customizer);
	}

	public DirectSetter() {
		firstDayOfWeek = 1;
		selectionMode = 2;
		orientation = 0;
		scrollPosition = 0;
		yearPosition = 0;
	}

	public void setCustomizer(Customizer customizer) {
		init(customizer);
	}

	private void init(Customizer customizer) {
		String first = customizer.getString("firstDayOfWeek");
		if (first != null && first.toLowerCase().equals("monday"))
			firstDayOfWeek = 2;
		allowsInvalid = customizer.getBoolean("allowsInvalid");
		antiAliased = customizer.getBoolean("antiAliased");
		String dateFormat = customizer.getString("dateFormat");
		if (dateFormat != null)
			this.dateFormat = dateFormat;
		String headerRenderer = customizer.getString("headerRenderer");
		if (headerRenderer != null)
			this.headerRenderer = loadClass(headerRenderer);
		String model = customizer.getString("model");
		if (model != null)
			this.model = loadClass(model);
		printMoon = customizer.getBoolean("printMoon");
		String renderer = customizer.getString("renderer");
		if (renderer != null)
			this.renderer = loadClass(renderer);
		showOkCancel = customizer.getBoolean("showOkCancel");
		showToday = customizer.getBoolean("showToday");
		todayCaption = customizer.getString("todayCaption");
		String workingDays = customizer.getString("workingDays");
		if (workingDays != null) {
			String work[] = workingDays.split(",");
			boolean wd[] = new boolean[7];
			for (int i = 0; i < work.length && i != 7; i++)
				wd[i] = Boolean.valueOf(work[i]).booleanValue();

			this.workingDays = wd;
		}
		String selection = customizer.getString("selectionMode");
		if (selection != null) {
			if (selection.toLowerCase().equals("singleinterval"))
				selectionMode = 1;
			if (selection.toLowerCase().equals("singleselection"))
				selectionMode = 0;
		}
		String orientation = customizer.getString("orientation");
		if (orientation != null && orientation.toLowerCase().equals("vertical"))
			this.orientation = 1;
		String scrollposition = customizer.getString("scrollPosition");
		if (scrollposition != null && scrollposition.toLowerCase().equals("right"))
			scrollPosition = 1;
		String yearposition = customizer.getString("yearPosition");
		if (yearposition != null && yearposition.toLowerCase().equals("down"))
			yearPosition = 1;
		initialized = true;
	}

	private Class loadClass(String name) {
		try {
			return Class.forName(name);
		} catch (final ClassNotFoundException e) {
			e.printStackTrace();
		}
		return null;
	}

	public void customize(DateField datefield) {
		if (!initialized)
			throw new IllegalStateException("This setter is not initialized.");
		datefield.setFirstDayOfWeek(firstDayOfWeek);
		datefield.setAllowsInvalid(allowsInvalid);
		datefield.setAntiAliased(antiAliased);
		if (dateFormat != null)
			datefield.setDateFormat(new SimpleDateFormat(dateFormat));
		if (headerRenderer != null)
			try {
				datefield.setHeaderRenderer((HeaderRenderer)headerRenderer.newInstance());
			}
			catch (InstantiationException e) {
				e.printStackTrace();
			}
			catch (IllegalAccessException e) {
				e.printStackTrace();
			}
		if (model != null)
			try {
				datefield.setModel((DataModel)model.newInstance());
			}
			catch (InstantiationException e) {
				e.printStackTrace();
			}
			catch (IllegalAccessException e) {
				e.printStackTrace();
			}
		datefield.setPrintMoon(printMoon);
		if (renderer != null)
			try {
				datefield.setRenderer((DayRenderer)renderer.newInstance());
			}
			catch (InstantiationException e) {
				e.printStackTrace();
			}
			catch (IllegalAccessException e) {
				e.printStackTrace();
			}
		datefield.setShowOkCancel(showOkCancel);
		datefield.setShowToday(showToday);
		if (todayCaption != null)
			datefield.setTodayCaption(todayCaption);
		if (workingDays != null)
			datefield.setWorkingDays(workingDays);
	}

	public void customize(CalendarPanel calendarpanel) {
		if (!initialized)
			throw new IllegalStateException("This setter is not initialized.");
		calendarpanel.setFirstDayOfWeek(firstDayOfWeek);
		calendarpanel.setAntiAliased(antiAliased);
		calendarpanel.setSelectionMode(selectionMode);
		if (headerRenderer != null)
			try {
				calendarpanel.setHeaderRenderer((HeaderRenderer)headerRenderer.newInstance());
			}
			catch (InstantiationException e) {
				e.printStackTrace();
			}
			catch (IllegalAccessException e) {
				e.printStackTrace();
			}
		if (model != null)
			try {
				calendarpanel.setModel((DataModel)model.newInstance());
			}
			catch (InstantiationException e) {
				e.printStackTrace();
			}
			catch (IllegalAccessException e) {
				e.printStackTrace();
			}
		calendarpanel.setPrintMoon(printMoon);
		if (renderer != null)
			try {
				calendarpanel.setRenderer((DayRenderer)renderer.newInstance());
			}
			catch (InstantiationException e) {
				e.printStackTrace();
			}
			catch (IllegalAccessException e) {
				e.printStackTrace();
			}
		calendarpanel.setShowToday(showToday);
		if (todayCaption != null)
			calendarpanel.setTodayCaption(todayCaption);
		if (workingDays != null)
			calendarpanel.setWorkingdays(workingDays);
		calendarpanel.setOrientation(orientation);
		calendarpanel.setScrollPosition(scrollPosition);
		calendarpanel.setYearPosition(yearPosition);
	}

	public void customize(DatePanel datepanel) {
		if (!initialized)
			throw new IllegalStateException("This setter is not initialized.");
		datepanel.setFirstDayOfWeek(firstDayOfWeek);
		datepanel.setAntiAliased(antiAliased);
		datepanel.setSelectionMode(selectionMode);
		if (headerRenderer != null)
			try {
				datepanel.setHeaderRenderer((HeaderRenderer)headerRenderer.newInstance());
			}
			catch (InstantiationException e) {
				e.printStackTrace();
			}
			catch (IllegalAccessException e) {
				e.printStackTrace();
			}
		if (model != null)
			try {
				datepanel.setModel((DataModel)model.newInstance());
			}
			catch (InstantiationException e) {
				e.printStackTrace();
			}
			catch (IllegalAccessException e) {
				e.printStackTrace();
			}
		datepanel.setPrintMoon(printMoon);
		if (renderer != null)
			try {
				datepanel.setRenderer((DayRenderer)renderer.newInstance());
			}
			catch (InstantiationException e) {
				e.printStackTrace();
			}
			catch (IllegalAccessException e) {
				e.printStackTrace();
			}
		datepanel.setShowToday(showToday);
		if (todayCaption != null)
			datepanel.setTodayCaption(todayCaption);
		if (workingDays != null)
			datepanel.setWorkingDays(workingDays);
	}
}
