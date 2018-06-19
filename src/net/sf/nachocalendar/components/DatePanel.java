// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(1000) fieldsfirst nonlb space 
// Source File Name:   DatePanel.java

package net.sf.nachocalendar.components;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.EventListenerList;
import net.sf.nachocalendar.event.DateSelectionEvent;
import net.sf.nachocalendar.event.DateSelectionListener;
import net.sf.nachocalendar.event.MonthChangeEvent;
import net.sf.nachocalendar.event.MonthChangeListener;
import net.sf.nachocalendar.event.YearChangeEvent;
import net.sf.nachocalendar.event.YearChangeListener;
import net.sf.nachocalendar.model.DataModel;
import net.sf.nachocalendar.model.DateSelectionModel;
import net.sf.nachocalendar.model.DefaultDateSelectionModel;

// Referenced classes of package net.sf.nachocalendar.components:
//			MonthPanel, DefaultDayRenderer, DefaultHeaderRenderer, MonthScroller, 
//			YearScroller, CalendarUtils, DayPanel, DayRenderer, 
//			HeaderRenderer

public class DatePanel extends JPanel {

	private MonthPanel monthpanel;
	private Calendar calendar;
	private Calendar navigation;
	private MonthScroller monthscroller;
	private YearScroller yearscroller;
	private boolean antiAliased;
	private DateSelectionModel dateSelectionModel;
	private MouseListener mlistener;
	private KeyListener klistener;
	private boolean printMoon;
	private JButton today;
	private transient ArrayList changeListenerList;
	private boolean workingDays[];
	private transient ArrayList actionListenerList;

	public DatePanel() {
		monthpanel = new MonthPanel();
		init();
	}

	public DatePanel(boolean showWeekNumbers) {
		monthpanel = new MonthPanel(showWeekNumbers);
		init();
	}

	private void init() {
		setRenderer(new DefaultDayRenderer());
		setHeaderRenderer(new DefaultHeaderRenderer());
		workingDays = new boolean[7];
		dateSelectionModel = new DefaultDateSelectionModel();
		monthscroller = new MonthScroller();
		yearscroller = new YearScroller();
		calendar = new GregorianCalendar();
		navigation = new GregorianCalendar();
		add(monthpanel);
		setDate(calendar.getTime());
		setFocusable(true);
		today = new JButton(CalendarUtils.getMessage("today"));
		today.setVisible(false);
		JPanel arriba = new JPanel(new GridLayout(1, 2));
		setLayout(new BorderLayout());
		arriba.add(monthscroller);
		arriba.add(yearscroller);
		add(arriba, "North");
		add(monthpanel, "Center");
		add(today, "South");
		addListeners();
		DayPanel daypanels[] = monthpanel.getDaypanels();
		for (int i = 0; i < daypanels.length; i++) {
			daypanels[i].addMouseListener(mlistener);
			daypanels[i].addKeyListener(klistener);
		}

		monthpanel.setMonth(getDate());
	}

	private void addListeners() {
		monthscroller.addChangeListener(new ChangeListener() {

			public void stateChanged(ChangeEvent e) {
				updateMonth();
			}

		});
		yearscroller.addChangeListener(new ChangeListener() {

			public void stateChanged(ChangeEvent e) {
				updateYear();
			}

		});
		monthscroller.addYearChangeListener(new YearChangeListener() {

			public void yearIncreased(YearChangeEvent e) {
				yearscroller.setYear(yearscroller.getYear() + 1);
				updateYear();
			}

			public void yearDecreased(YearChangeEvent e) {
				yearscroller.setYear(yearscroller.getYear() - 1);
				updateYear();
			}

		});
		addMouseWheelListener(new MouseWheelListener() {

			public void mouseWheelMoved(MouseWheelEvent e) {
				if (!isEnabled())
					return;
				int q = e.getWheelRotation();
				for (int i = 0; i < Math.abs(q); i++)
					if (q > 0)
						monthscroller.nextMonth();
					else
						monthscroller.previousMonth();

			}

		});
		dateSelectionModel.addDateSelectionListener(new DateSelectionListener() {

			public void valueChanged(DateSelectionEvent e) {
				refreshSelection();
				fireChangeListenerStateChanged(new ChangeEvent(DatePanel.this));
			}

		});
		mlistener = new MouseAdapter() {

			public void mouseClicked(MouseEvent e) {
				DayPanel dp = (DayPanel)e.getSource();
				if (!dp.isEnabled() || !dp.isComponentEnabled())
					return;
				dateSelectionModel.setValueIsAdjusting(true);
				if (!e.isControlDown()) {
					dateSelectionModel.clearSelection();
					if (e.isShiftDown())
						dateSelectionModel.addSelectionInterval(dateSelectionModel.getLeadSelectionDate(), dp.getDate());
					else
						dateSelectionModel.addSelectionInterval(dp.getDate(), dp.getDate());
				} else
				if (e.isShiftDown())
					dateSelectionModel.addSelectionInterval(dateSelectionModel.getLeadSelectionDate(), dp.getDate());
				else
				if (dateSelectionModel.isSelectedDate(dp.getDate()))
					dateSelectionModel.removeSelectionInterval(dp.getDate(), dp.getDate());
				else
					dateSelectionModel.addSelectionInterval(dp.getDate(), dp.getDate());
				dateSelectionModel.setLeadSelectionDate(dp.getDate());
				repaint();
				dp.requestFocus();
				calendar.setTime(dp.getDate());
				dateSelectionModel.setValueIsAdjusting(false);
				fireActionListenerActionPerformed(new ActionEvent(this, 0, "clicked"));
			}

		};
		klistener = new KeyListener() {

			public void keyPressed(KeyEvent e) {
				boolean changed = false;
				int keycode = e.getKeyCode();
				navigation.setTime(calendar.getTime());
				if (keycode == 37 || keycode == 226) {
					int month = navigation.get(2);
					navigation.add(6, -1);
					if (month != navigation.get(2)) {
						fireMonthChangeListenerMonthDecreased(new MonthChangeEvent(this, navigation.getTime()));
						setDate(navigation.getTime());
					}
					changed = true;
				}
				if (keycode == 39 || keycode == 227) {
					int month = navigation.get(2);
					navigation.add(6, 1);
					if (month != navigation.get(2)) {
						fireMonthChangeListenerMonthIncreased(new MonthChangeEvent(this, navigation.getTime()));
						setDate(navigation.getTime());
					}
					changed = true;
				}
				if (keycode == 38 || keycode == 224) {
					int month = navigation.get(2);
					navigation.add(6, -7);
					if (month != navigation.get(2)) {
						fireMonthChangeListenerMonthDecreased(new MonthChangeEvent(this, navigation.getTime()));
						setDate(navigation.getTime());
					}
					changed = true;
				}
				if (keycode == 40 || keycode == 225) {
					int month = navigation.get(2);
					navigation.add(6, 7);
					if (month != navigation.get(2)) {
						fireMonthChangeListenerMonthIncreased(new MonthChangeEvent(this, navigation.getTime()));
						setDate(navigation.getTime());
					}
					changed = true;
				}
				if (keycode == 33) {
					navigation.add(2, -1);
					fireMonthChangeListenerMonthIncreased(new MonthChangeEvent(this, navigation.getTime()));
					setDate(navigation.getTime());
					changed = true;
				}
				if (keycode == 34) {
					navigation.add(2, 1);
					fireMonthChangeListenerMonthIncreased(new MonthChangeEvent(this, navigation.getTime()));
					setDate(navigation.getTime());
					changed = true;
				}
				if (changed) {
					if (!e.isControlDown() && !e.isShiftDown()) {
						dateSelectionModel.clearSelection();
						if (e.isShiftDown())
							dateSelectionModel.addSelectionInterval(dateSelectionModel.getLeadSelectionDate(), navigation.getTime());
						else
							dateSelectionModel.addSelectionInterval(navigation.getTime(), navigation.getTime());
					} else
					if (e.isShiftDown())
						dateSelectionModel.addSelectionInterval(dateSelectionModel.getLeadSelectionDate(), navigation.getTime());
					else
					if (dateSelectionModel.isSelectedDate(navigation.getTime()))
						dateSelectionModel.removeSelectionInterval(navigation.getTime(), navigation.getTime());
					else
						dateSelectionModel.addSelectionInterval(navigation.getTime(), navigation.getTime());
					dateSelectionModel.setLeadSelectionDate(navigation.getTime());
					calendar.setTime(navigation.getTime());
					monthpanel.repaint();
				}
				fireKeyListenerKeyPressed(e);
			}

			public void keyReleased(KeyEvent e) {
				fireKeyListenerKeyReleased(e);
			}

			public void keyTyped(KeyEvent e) {
				fireKeyListenerKeyTyped(e);
			}

		};
		today.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				setDate(new Date());
			}

		});
	}

	private void updateMonth() {
		int month = calendar.get(2);
		calendar.add(2, monthscroller.getMonth() - month);
		monthpanel.setMonth(calendar.getTime());
		refreshSelection();
	}

	private void updateYear() {
		int year = calendar.get(1);
		calendar.add(1, yearscroller.getYear() - year);
		monthpanel.setMonth(calendar.getTime());
		refreshSelection();
	}

	public void setDate(Date d) {
		calendar.setTime(d);
		int year = calendar.get(1);
		int month = calendar.get(2);
		if (year != yearscroller.getYear())
			yearscroller.setYear(year);
		if (month != monthscroller.getMonth())
			monthscroller.setMonth(month);
		monthpanel.setDay(d);
		dateSelectionModel.setSelectedDate(d);
		refreshSelection();
	}

	public Date getDate() {
		return calendar.getTime();
	}

	public DayRenderer getRenderer() {
		return monthpanel.getRenderer();
	}

	public void setRenderer(DayRenderer renderer) {
		monthpanel.setRenderer(renderer);
	}

	public synchronized void addChangeListener(ChangeListener listener) {
		if (changeListenerList == null)
			changeListenerList = new ArrayList();
		changeListenerList.add(listener);
	}

	public synchronized void removeChangeListener(ChangeListener listener) {
		if (changeListenerList != null)
			changeListenerList.remove(listener);
	}

	private void fireChangeListenerStateChanged(ChangeEvent event) {
label0:
		{
			synchronized (this) {
				if (changeListenerList != null)
					break label0;
			}
			return;
		}
		ArrayList list = (ArrayList)changeListenerList.clone();
		for (int i = 0; i < list.size(); i++)
			((ChangeListener)list.get(i)).stateChanged(event);

		return;
	}

	public DataModel getModel() {
		return monthpanel.getModel();
	}

	public void setModel(DataModel model) {
		monthpanel.setModel(model);
	}

	public int getFirstDayOfWeek() {
		return monthpanel.getFirstDayOfWeek();
	}

	public void setFirstDayOfWeek(int firstDayOfWeek) {
		if (firstDayOfWeek == 2 || firstDayOfWeek == 1) {
			if (monthpanel.getFirstDayOfWeek() == firstDayOfWeek)
				return;
			int old = monthpanel.getFirstDayOfWeek();
			monthpanel.setFirstDayOfWeek(firstDayOfWeek);
			refreshSelection();
			repaint();
			firePropertyChange("firstDayOfWeek", old, firstDayOfWeek);
		}
	}

	public void refresh() {
		monthpanel.refresh();
	}

	public HeaderRenderer getHeaderRenderer() {
		return monthpanel.getHeaderRenderer();
	}

	public void setHeaderRenderer(HeaderRenderer headerRenderer) {
		monthpanel.setHeaderRenderer(headerRenderer);
	}

	public Object getValue() {
		return dateSelectionModel.getSelectedDate();
	}

	public void setValue(Object value) {
		try {
			setDate(CalendarUtils.convertToDate(value));
		}
		catch (ParseException e) {
			e.printStackTrace();
		}
	}

	public void setValues(Object values[]) {
		dateSelectionModel.setSelectedDates(values);
		refreshSelection();
	}

	public boolean[] getWorkingDays() {
		boolean retorno[] = new boolean[workingDays.length];
		for (int i = 0; i < workingDays.length; i++)
			retorno[i] = workingDays[i];

		return retorno;
	}

	public void setWorkingDays(boolean workingDays[]) {
		if (workingDays == null)
			return;
		boolean old[] = monthpanel.getWorkingdays();
		monthpanel.setWorkingdays(workingDays);
		for (int i = 0; i < workingDays.length && i < this.workingDays.length; i++)
			this.workingDays[i] = workingDays[i];

		firePropertyChange("workingDays", old, workingDays);
	}

	public synchronized void addKeyListener(KeyListener listener) {
		if (listenerList == null)
			listenerList = new EventListenerList();
		listenerList.add(java.awt.event.KeyListener.class, listener);
	}

	public synchronized void removeKeyListener(KeyListener listener) {
		listenerList.remove(java.awt.event.KeyListener.class, listener);
	}

	private void fireKeyListenerKeyTyped(KeyEvent event) {
		if (listenerList == null)
			return;
		Object listeners[] = listenerList.getListenerList();
		for (int i = listeners.length - 2; i >= 0; i -= 2)
			if (listeners[i] == (java.awt.event.KeyListener.class))
				((KeyListener)listeners[i + 1]).keyTyped(event);

	}

	private void fireKeyListenerKeyPressed(KeyEvent event) {
		if (listenerList == null)
			return;
		Object listeners[] = listenerList.getListenerList();
		for (int i = listeners.length - 2; i >= 0; i -= 2)
			if (listeners[i] == (java.awt.event.KeyListener.class))
				((KeyListener)listeners[i + 1]).keyPressed(event);

	}

	private void fireKeyListenerKeyReleased(KeyEvent event) {
		if (listenerList == null)
			return;
		Object listeners[] = listenerList.getListenerList();
		for (int i = listeners.length - 2; i >= 0; i -= 2)
			if (listeners[i] == (java.awt.event.KeyListener.class))
				((KeyListener)listeners[i + 1]).keyReleased(event);

	}

	public void setEnabled(boolean enabled) {
		monthpanel.setEnabled(enabled);
		monthscroller.setEnabled(enabled);
		yearscroller.setEnabled(enabled);
		super.setEnabled(enabled);
		repaint();
	}

	public boolean isEnabled() {
		return monthpanel.isEnabled();
	}

	public Object[] getValues() {
		return dateSelectionModel.getSelectedDates();
	}

	public void setSelectionMode(int mode) {
		int old = dateSelectionModel.getSelectionMode();
		dateSelectionModel.setSelectionMode(mode);
		refreshSelection();
		firePropertyChange("selectionMode", old, mode);
	}

	public int getSelectionMode() {
		return dateSelectionModel.getSelectionMode();
	}

	public synchronized void addActionListener(ActionListener listener) {
		if (actionListenerList == null)
			actionListenerList = new ArrayList();
		actionListenerList.add(listener);
	}

	public synchronized void removeActionListener(ActionListener listener) {
		if (actionListenerList != null)
			actionListenerList.remove(listener);
	}

	private void fireActionListenerActionPerformed(ActionEvent event) {
label0:
		{
			synchronized (this) {
				if (actionListenerList != null)
					break label0;
			}
			return;
		}
		ArrayList list = (ArrayList)actionListenerList.clone();
		for (int i = 0; i < list.size(); i++)
			((ActionListener)list.get(i)).actionPerformed(event);

		return;
	}

	public synchronized void addMonthChangeListener(MonthChangeListener listener) {
		if (listenerList == null)
			listenerList = new EventListenerList();
		listenerList.add(net.sf.nachocalendar.event.MonthChangeListener.class, listener);
	}

	public synchronized void removeMonthChangeListener(MonthChangeListener listener) {
		listenerList.remove(net.sf.nachocalendar.event.MonthChangeListener.class, listener);
	}

	private void fireMonthChangeListenerMonthIncreased(MonthChangeEvent event) {
		if (listenerList == null)
			return;
		Object listeners[] = listenerList.getListenerList();
		for (int i = listeners.length - 2; i >= 0; i -= 2)
			if (listeners[i] == (net.sf.nachocalendar.event.MonthChangeListener.class))
				((MonthChangeListener)listeners[i + 1]).monthIncreased(event);

	}

	private void fireMonthChangeListenerMonthDecreased(MonthChangeEvent event) {
		if (listenerList == null)
			return;
		Object listeners[] = listenerList.getListenerList();
		for (int i = listeners.length - 2; i >= 0; i -= 2)
			if (listeners[i] == (net.sf.nachocalendar.event.MonthChangeListener.class))
				((MonthChangeListener)listeners[i + 1]).monthDecreased(event);

	}

	private void refreshSelection() {
		DayPanel daypanels[] = monthpanel.getDaypanels();
		for (int i = 0; i < daypanels.length; i++)
			if (!daypanels[i].isEnabled())
				daypanels[i].setSelected(false);
			else
				daypanels[i].setSelected(dateSelectionModel.isSelectedDate(daypanels[i].getDate()));

	}

	public DateSelectionModel getDateSelectionModel() {
		return dateSelectionModel;
	}

	public void setDateSelectionModel(DateSelectionModel dateSelectionModel) {
		if (dateSelectionModel != null)
			this.dateSelectionModel = dateSelectionModel;
	}

	public boolean isAntiAliased() {
		return antiAliased;
	}

	public void setAntiAliased(boolean antiAliased) {
		boolean old = this.antiAliased;
		this.antiAliased = antiAliased;
		monthpanel.setAntiAliased(antiAliased);
		firePropertyChange("antiAliased", old, antiAliased);
	}

	public boolean isPrintMoon() {
		return printMoon;
	}

	public void setPrintMoon(boolean printMoon) {
		monthpanel.setPrintMoon(printMoon);
		repaint();
		this.printMoon = printMoon;
	}

	public void setShowToday(boolean show) {
		today.setVisible(show);
		repaint();
	}

	public boolean getShowToday() {
		return today.isVisible();
	}

	public void setTodayCaption(String caption) {
		if (caption == null)
			today.setText(CalendarUtils.getMessage("today"));
		else
			today.setText(caption);
	}

	public String getTodayCaption() {
		return today.getText();
	}
















}
