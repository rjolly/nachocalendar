// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(1000) fieldsfirst nonlb space 
// Source File Name:   MonthPanel.java

package net.sf.nachocalendar.components;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.text.DateFormatSymbols;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Iterator;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.UIDefaults;
import javax.swing.UIManager;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import net.sf.nachocalendar.model.DataModel;

// Referenced classes of package net.sf.nachocalendar.components:
//			DayPanel, HeaderPanel, DefaultDayRenderer, DefaultHeaderRenderer, 
//			DayRenderer, HeaderRenderer

public class MonthPanel extends JComponent {

	private DayPanel paneles[][];
	private final int rows = 6;
	private final int cols = 7;
	private int tcols;
	private int firstday;
	private int showingmonth;
	private int showingyear;
	private Calendar calendar;
	private Calendar check;
	private Calendar navigation;
	private String months[];
	private String days[];
	private HeaderPanel weeks[];
	private Date date;
	private Collection changelisteners;
	private boolean workingdays[];
	private DayRenderer renderer;
	private HeaderPanel headers[];
	private boolean showWeekNumber;
	private boolean showtitle;
	private JPanel centro;
	private JLabel title;
	private static final int MONDAYFIRST[] = {
		0, 2, 3, 4, 5, 6, 7, 1
	};
	private static final int SUNDAYFIRST[] = {
		0, 1, 2, 3, 4, 5, 6, 7
	};
	private static final int RESTAMONDAY[] = {
		0, 7, 1, 2, 3, 4, 5, 6
	};
	private int minimalDaysInFirstWeek;
	private int dayorder[];
	private DayPanel daypanels[];
	private boolean antiAliased;
	private boolean printMoon;
	private static final boolean DEFAULTWORKING[] = {
		false, true, true, true, true, true, true
	};
	private DataModel model;
	private HeaderRenderer headerRenderer;

	public MonthPanel() {
		this(false);
	}

	public MonthPanel(boolean showWeekNumber) {
		this.showWeekNumber = showWeekNumber;
		daypanels = new DayPanel[42];
		calendar = new GregorianCalendar();
		calendar.set(10, 0);
		calendar.set(12, 0);
		calendar.set(14, 0);
		check = new GregorianCalendar();
		check.set(10, 0);
		check.set(12, 0);
		check.set(14, 0);
		navigation = new GregorianCalendar();
		navigation.set(10, 0);
		navigation.set(12, 0);
		navigation.set(14, 0);
		firstday = calendar.getFirstDayOfWeek();
		setFocusable(true);
		if (firstday == 2)
			dayorder = MONDAYFIRST;
		else
			dayorder = SUNDAYFIRST;
		centro = new JPanel(new BorderLayout());
		setLayout(new BorderLayout());
		add(centro, "Center");
		if (showWeekNumber)
			tcols = 8;
		else
			tcols = 7;
		headers = new HeaderPanel[tcols];
		centro.setLayout(new GridLayout(7, tcols));
		title = new JLabel();
		title.setHorizontalAlignment(0);
		title.setVerticalAlignment(0);
		add(title, "North");
		title.setVisible(false);
		changelisteners = new ArrayList();
		paneles = new DayPanel[6][7];
		weeks = new HeaderPanel[6];
		Font f = UIManager.getDefaults().getFont("Label.font");
		if (f == null)
			f = new Font("Times", 0, 12);
		DateFormatSymbols symbols = new DateFormatSymbols();
		days = symbols.getShortWeekdays();
		for (int i = 1; i < days.length; i++)
			days[i] = days[i].substring(0, 1).toUpperCase() + days[i].substring(1).toLowerCase();

		months = symbols.getMonths();
		for (int i = 0; i < months.length - 1; i++)
			months[i] = months[i].substring(0, 1).toUpperCase() + months[i].substring(1).toLowerCase();

		for (int i = 0; i < tcols; i++) {
			headers[i] = new HeaderPanel(headerRenderer);
			centro.add(headers[i]);
		}

		setHeaders();
		int index = 0;
		for (int i = 0; i < 6; i++) {
			for (int j = 0; j < tcols; j++) {
				if (j == 0 && showWeekNumber) {
					weeks[i] = new HeaderPanel(headerRenderer);
					centro.add(weeks[i]);
					weeks[i].setValue(days[i]);
					continue;
				}
				int jj = j;
				if (showWeekNumber)
					jj = j - 1;
				paneles[i][jj] = new DayPanel(renderer, index);
				daypanels[index] = paneles[i][jj];
				index++;
				centro.add(paneles[i][jj]);
			}

		}

		setRenderer(new DefaultDayRenderer());
		setHeaderRenderer(new DefaultHeaderRenderer());
		setWorkingdays(getDefaultWorking());
	}

	private void setHeaders() {
		for (int i = 0; i < tcols; i++)
			if (showWeekNumber)
				headers[i].setValue(days[dayorder[i]]);
			else
				headers[i].setValue(days[dayorder[i + 1]]);

	}

	public void setDay(Date d) {
		setDay(d, true);
	}

	public void setMonth(Date d) {
		setDay(d, false);
	}

	public Date getMonth() {
		return date;
	}

	public void showTitle(boolean show) {
		showtitle = show;
		title.setVisible(show);
		doLayout();
	}

	private void setDay(Date d, boolean select) {
		if (d == null)
			return;
		date = d;
		boolean update = showingyear != calendar.get(1) || showingmonth != calendar.get(2);
		calendar.setTime(d);
		showingmonth = calendar.get(2);
		showingyear = calendar.get(1);
		calendar.add(6, -1 * (calendar.get(5) - 1));
		if (calendar.getFirstDayOfWeek() == 1)
			calendar.add(6, -1 * (calendar.get(7) - 1));
		else
			calendar.add(6, -1 * (RESTAMONDAY[calendar.get(7)] - 1));
		for (int i = 0; i < daypanels.length; i++) {
			if (update) {
				Date temp = calendar.getTime();
				daypanels[i].setDate(temp);
				if (model != null)
					daypanels[i].setData(model.getData(temp));
				if (calendar.get(2) == showingmonth)
					daypanels[i].setEnabled(true);
				else
					daypanels[i].setEnabled(false);
			}
			calendar.add(6, 1);
		}

		if (showtitle)
			title.setText(months[showingmonth] + " " + showingyear);
		if (showWeekNumber) {
			if (minimalDaysInFirstWeek != 0)
				calendar.setMinimalDaysInFirstWeek(minimalDaysInFirstWeek);
			calendar.setTime(d);
			int backToWeekStart = 0;
			int value = calendar.get(7);
			if (value == 1)
				backToWeekStart = -6;
			else
			if (value == 2)
				backToWeekStart = 0;
			else
			if (value == 3)
				backToWeekStart = -1;
			else
			if (value == 4)
				backToWeekStart = -2;
			else
			if (value == 5)
				backToWeekStart = -3;
			else
			if (value == 6)
				backToWeekStart = -4;
			else
			if (value == 7)
				backToWeekStart = -5;
			if (calendar.getFirstDayOfWeek() == 2)
				calendar.add(5, backToWeekStart);
			else
				calendar.add(5, backToWeekStart - 1);
			for (int i = 0; i < weeks.length; i++) {
				weeks[i].setValue(Integer.toString(calendar.get(3)));
				calendar.add(5, 7);
			}

		}
		repaint();
		fireChangeEvent(new ChangeEvent(this));
	}

	public void refresh() {
		for (int i = 0; i < paneles.length; i++) {
			for (int j = 0; j < paneles[i].length; j++)
				if (model != null) {
					paneles[i][j].setData(model.getData(paneles[i][j].getDate()));
					paneles[i][j].setPrintMoon(printMoon);
				}

		}

		repaint();
	}

	public Date getDay() {
		return date;
	}

	protected void fireChangeEvent(ChangeEvent e) {
		for (Iterator it = changelisteners.iterator(); it.hasNext(); ((ChangeListener)it.next()).stateChanged(e));
	}

	public DataModel getModel() {
		return model;
	}

	public void setModel(DataModel model) {
		this.model = model;
	}

	public DayRenderer getRenderer() {
		return renderer;
	}

	public void setRenderer(DayRenderer renderer) {
		this.renderer = renderer;
		for (int i = 0; i < paneles.length; i++) {
			for (int j = 0; j < paneles[i].length; j++)
				paneles[i][j].setRenderer(renderer);

		}

	}

	public HeaderRenderer getHeaderRenderer() {
		return headerRenderer;
	}

	public void setHeaderRenderer(HeaderRenderer headerRenderer) {
		this.headerRenderer = headerRenderer;
		for (int i = 0; i < headers.length; i++)
			headers[i].setRenderer(headerRenderer);

		if (showWeekNumber) {
			for (int i = 0; i < weeks.length; i++)
				weeks[i].setRenderer(headerRenderer);

		}
	}

	public void setMinimalDaysInFirstWeek(int number) {
		if (minimalDaysInFirstWeek != number) {
			minimalDaysInFirstWeek = number;
			setHeaders();
			setMonth(getMonth());
		}
	}

	public int getMinimalDaysInFirstWeek() {
		return minimalDaysInFirstWeek;
	}

	public boolean[] getWorkingdays() {
		return workingdays;
	}

	public void setWorkingdays(boolean workingdays[]) {
		if (workingdays == null)
			return;
		this.workingdays = workingdays;
		for (int i = 0; i < paneles.length; i++) {
			for (int j = 0; j < paneles[i].length; j++)
				paneles[i][j].setWorking(workingdays[dayorder[j + 1] - 1]);

		}

	}

	public int getFirstDayOfWeek() {
		return calendar.getFirstDayOfWeek();
	}

	public void setFirstDayOfWeek(int firstDayOfWeek) {
		if (firstDayOfWeek == 2 || firstDayOfWeek == 1) {
			calendar.setFirstDayOfWeek(firstDayOfWeek);
			check.setFirstDayOfWeek(firstDayOfWeek);
			if (firstDayOfWeek == 1)
				dayorder = SUNDAYFIRST;
			else
				dayorder = MONDAYFIRST;
			setHeaders();
			setMonth(getMonth());
		}
		setWorkingdays(getWorkingdays());
	}

	public static boolean[] getDefaultWorking() {
		return DEFAULTWORKING;
	}

	public void setEnabled(boolean b) {
		for (int i = 0; i < daypanels.length; i++)
			daypanels[i].setComponentEnabled(b);

	}

	public boolean isEnabled() {
		return daypanels[0].isComponentEnabled();
	}

	public boolean isAntiAliased() {
		return antiAliased;
	}

	public void setAntiAliased(boolean antiAliased) {
		this.antiAliased = antiAliased;
		for (int i = 0; i < daypanels.length; i++)
			daypanels[i].setAntiAliased(antiAliased);

		for (int i = 0; i < headers.length; i++)
			headers[i].setAntiAliased(antiAliased);

		if (showWeekNumber) {
			for (int i = 0; i < weeks.length; i++)
				weeks[i].setAntiAliased(antiAliased);

		}
		repaint();
	}

	protected DayPanel[] getDaypanels() {
		return daypanels;
	}

	protected void setDaypanels(DayPanel daypanels[]) {
		this.daypanels = daypanels;
	}

	public Date getMinDate() {
		Calendar cal = new GregorianCalendar();
		cal.setTime(date);
		cal.add(5, cal.get(5) * -1 + 1);
		return cal.getTime();
	}

	public Date getMaxDate() {
		Calendar cal = new GregorianCalendar();
		cal.setTime(date);
		cal.add(5, cal.get(5) * -1 + 1);
		cal.add(2, 1);
		cal.add(6, -1);
		return cal.getTime();
	}

	public boolean isPrintMoon() {
		return printMoon;
	}

	public void setPrintMoon(boolean printMoon) {
		if (this.printMoon != printMoon) {
			this.printMoon = printMoon;
			for (int i = 0; i < paneles.length; i++) {
				for (int j = 0; j < paneles[i].length; j++)
					paneles[i][j].setPrintMoon(printMoon);

			}

		}
	}

}
