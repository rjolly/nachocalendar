// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(1000) fieldsfirst nonlb space 
// Source File Name:   CalendarPanel.java

package net.sf.nachocalendar.components;

import java.awt.BorderLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.AdjustmentEvent;
import java.awt.event.AdjustmentListener;
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
import javax.swing.BoundedRangeModel;
import javax.swing.DefaultBoundedRangeModel;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollBar;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.EventListenerList;
import net.sf.nachocalendar.event.DateSelectionEvent;
import net.sf.nachocalendar.event.DateSelectionListener;
import net.sf.nachocalendar.model.DataModel;
import net.sf.nachocalendar.model.DateSelectionModel;
import net.sf.nachocalendar.model.DefaultDateSelectionModel;

// Referenced classes of package net.sf.nachocalendar.components:
//			YearScroller, DefaultDayRenderer, DefaultHeaderRenderer, MonthPanel, 
//			CalendarUtils, DayPanel, HeaderRenderer, DayRenderer

public class CalendarPanel extends JPanel
	implements ChangeListener {

	private boolean antiAliased;
	private KeyListener klistener;
	private MouseListener mlistener;
	private DateSelectionListener listlistener;
	private DateSelectionModel dateSelectionModel;
	private DataModel datamodel;
	private HeaderRenderer headerrenderer;
	private DayRenderer dayrenderer;
	private Calendar navigation;
	private Calendar calendar;
	private Date minDate;
	private Date maxDate;
	private int minimalDaysInFirstWeek;
	private boolean printMoon;
	protected boolean eternalScroll;
	private boolean showToday;
	private int middle;
	private MonthPanel months[];
	private int orientation;
	private int scrollPosition;
	public static final int HORIZONTAL = 0;
	public static final int VERTICAL = 1;
	public static final int LEFT = 0;
	public static final int RIGHT = 1;
	public static final int UP = 0;
	public static final int DOWN = 1;
	private JScrollBar scroll;
	private YearScroller ys;
	private JButton today;
	private boolean workingdays[] = {
		false, true, true, true, true, true, true
	};
	private Calendar cal;
	private int showingyear;
	private int quantity;
	private Date date;
	private JPanel abajo;
	private boolean showWeekNumber;
	private transient ArrayList changeListenerList;
	private int yearPosition;

	public CalendarPanel() {
		this(3, 1);
	}

	public CalendarPanel(int quantity) {
		this(quantity, 1);
	}

	public CalendarPanel(boolean showWeekNumbers) {
		this(3, 1, showWeekNumbers);
	}

	public CalendarPanel(int quantity, int orientation) {
		this(quantity, orientation, true);
	}

	public CalendarPanel(int quantity, int orientation, boolean showWeekNumber) {
		this(quantity, orientation, showWeekNumber, true);
	}

	public CalendarPanel(int quantity, int orientation, boolean showWeekNumber, boolean eternalScroll) {
		if (quantity < 1)
			quantity = 1;
		if (quantity > 12)
			quantity = 12;
		this.quantity = quantity;
		this.showWeekNumber = showWeekNumber;
		this.orientation = orientation;
		navigation = new GregorianCalendar();
		calendar = new GregorianCalendar();
		dateSelectionModel = new DefaultDateSelectionModel();
		ys = new YearScroller();
		middle = quantity / 2;
		this.eternalScroll = eternalScroll;
		initScroll();
		today = new JButton(CalendarUtils.getMessage("today"));
		today.setVisible(false);
		cal = new GregorianCalendar();
		setLayout(new BorderLayout());
		createListeners();
		dateSelectionModel.addDateSelectionListener(listlistener);
		setQuantity(quantity);
		if (orientation == 1)
			layoutVertical();
		else
			layoutHorizontal();
		initDisplayPanel();
		setValue(new Date());
		setRenderer(new DefaultDayRenderer());
		setHeaderRenderer(new DefaultHeaderRenderer());
	}

	private void initScroll() {
		int or = orientation != 0 ? 1 : 0;
		scroll = new JScrollBar(or);
		scroll.setMaximum(17 - quantity);
		scroll.setBlockIncrement(3);
		scroll.setUnitIncrement(1);
		BoundedRangeModel model = new DefaultBoundedRangeModel() {

			public void setValue(int value) {
				super.setValue(value);
				if (eternalScroll) {
					if (value < getMinimum())
						goPreviousYear();
					if (value > getMaximum() - getExtent())
						goNextYear();
				}
			}

		};
		model.setMaximum(17 - quantity);
		model.setMinimum(0);
		model.setExtent(5);
		model.setValue(0);
		scroll.setModel(model);
	}

	private void goNextYear() {
		ys.setYear(ys.getYear() + 1);
		setShowingYear(ys.getYear());
		scroll.setValue(0);
	}

	private void goPreviousYear() {
		ys.setYear(ys.getYear() - 1);
		setShowingYear(ys.getYear());
		scroll.setValue(scroll.getMaximum() - scroll.getModel().getExtent());
	}

	private void initDisplayPanel() {
		int displayRange = cal.get(2);
		setShowingYear(cal.get(1));
		if (0 < displayRange && displayRange - middle < 10)
			scroll.setValue(displayRange - middle);
		else
		if (displayRange < quantity)
			scroll.setValue(displayRange);
		else
			scroll.setValue(displayRange - quantity - middle);
	}

	private void createListeners() {
		scroll.addAdjustmentListener(new AdjustmentListener() {

			public void adjustmentValueChanged(AdjustmentEvent e) {
				setShowingMonth(e.getValue());
			}

		});
		ys.addChangeListener(new ChangeListener() {

			public void stateChanged(ChangeEvent e) {
				setShowingYear(ys.getYear());
			}

		});
		today.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				cal.setTime(new Date());
				initDisplayPanel();
			}

		});
		addMouseWheelListener(new MouseWheelListener() {

			public void mouseWheelMoved(MouseWheelEvent e) {
				if (!isEnabled())
					return;
				int q = e.getWheelRotation();
				int value = scroll.getValue();
				value += q;
				if (value < 0) {
					value = 0;
					if (eternalScroll) {
						goPreviousYear();
						return;
					}
				}
				if (value > 11) {
					value = 11;
					if (eternalScroll) {
						goNextYear();
						return;
					}
				}
				scroll.setValue(value);
			}

		});
		klistener = new KeyListener() {

			public void keyPressed(KeyEvent e) {
				boolean changed = false;
				int keycode = e.getKeyCode();
				navigation.setTime(calendar.getTime());
				if (keycode == 37 || keycode == 226) {
					navigation.add(6, -1);
					changed = true;
				}
				if (keycode == 39 || keycode == 227) {
					navigation.add(6, 1);
					changed = true;
				}
				if (keycode == 38 || keycode == 224) {
					navigation.add(6, -7);
					changed = true;
				}
				if (keycode == 40 || keycode == 225) {
					navigation.add(6, 7);
					changed = true;
				}
				if (keycode == 33) {
					navigation.add(2, -1);
					scroll.setValue(navigation.get(2));
					changed = true;
				}
				if (keycode == 34) {
					navigation.add(2, 1);
					scroll.setValue(navigation.get(2));
					changed = true;
				}
				if (changed) {
					if (!isShowing(navigation.getTime()))
						showMonth(navigation.getTime());
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
					refreshSelection();
					repaint();
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
				refreshSelection();
				repaint();
			}

		};
		listlistener = new DateSelectionListener() {

			public void valueChanged(DateSelectionEvent e) {
				for (int i = 0; i < months.length; i++) {
					DayPanel daypanels[] = months[i].getDaypanels();
					for (int j = 0; j < daypanels.length; j++)
						if (dateSelectionModel.isSelectedDate(daypanels[j].getDate()))
							daypanels[j].setSelected(true);
						else
							daypanels[j].setSelected(false);

				}

				repaint();
				fireChangeListenerStateChanged(new ChangeEvent(CalendarPanel.this));
			}

		};
	}

	public void setOrientation(int orientation) {
		if (orientation == this.orientation)
			return;
		int old = this.orientation;
		this.orientation = orientation;
		removeAll();
		if (orientation == 1)
			layoutVertical();
		else
			layoutHorizontal();
		firePropertyChange("orientation", old, orientation);
	}

	private void layoutVertical() {
		scroll.setOrientation(1);
		JPanel centro = new JPanel(new GridLayout(months.length, 1));
		for (int i = 0; i < months.length; i++)
			centro.add(months[i]);

		add(centro, "Center");
		layoutScrollAndYear();
	}

	private void layoutHorizontal() {
		scroll.setOrientation(0);
		JPanel centro = new JPanel(new GridLayout(1, months.length));
		for (int i = 0; i < months.length; i++)
			centro.add(months[i]);

		add(centro, "Center");
		layoutScrollAndYear();
	}

	public synchronized void addChangeListener(ChangeListener listener) {
		if (listener == null)
			return;
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

	public void stateChanged(ChangeEvent e) {
		fireChangeListenerStateChanged(e);
	}

	private void setShowingMonth(int month) {
		Calendar cal = new GregorianCalendar(showingyear, month, 1);
		for (int i = 0; i < months.length; i++) {
			months[i].setMonth(cal.getTime());
			cal.add(2, 1);
		}

		minDate = months[0].getMinDate();
		maxDate = months[months.length - 1].getMaxDate();
		refreshSelection();
	}

	private void setShowingYear(int year) {
		showingyear = year;
		for (int i = 0; i < months.length; i++) {
			cal.setTime(months[i].getMonth());
			cal.set(1, year);
			months[i].setMonth(cal.getTime());
		}

		refreshSelection();
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		if (date == null)
			return;
		this.date = date;
		cal.setTime(date);
		if (!isShowing(date))
			showMonth(date);
		dateSelectionModel.setSelectedDate(date);
		refreshSelection();
		repaint();
	}

	public boolean[] getWorkingdays() {
		return workingdays;
	}

	public void setWorkingdays(boolean workingdays[]) {
		boolean old[] = this.workingdays;
		this.workingdays = workingdays;
		for (int i = 0; i < months.length; i++)
			months[i].setWorkingdays(workingdays);

		firePropertyChange("workingDays", old, workingdays);
	}

	public DayRenderer getRenderer() {
		return months[0].getRenderer();
	}

	public void setRenderer(DayRenderer renderer) {
		dayrenderer = renderer;
		for (int i = 0; i < months.length; i++)
			months[i].setRenderer(renderer);

	}

	public DataModel getModel() {
		return months[0].getModel();
	}

	public void setModel(DataModel model) {
		datamodel = model;
		for (int i = 0; i < months.length; i++)
			months[i].setModel(model);

	}

	public int getFirstDayOfWeek() {
		return months[0].getFirstDayOfWeek();
	}

	public void setFirstDayOfWeek(int firstDayOfWeek) {
		if (firstDayOfWeek == 1 || firstDayOfWeek == 2) {
			int old = months[0].getFirstDayOfWeek();
			if (firstDayOfWeek == months[0].getFirstDayOfWeek())
				return;
			for (int i = 0; i < months.length; i++)
				months[i].setFirstDayOfWeek(firstDayOfWeek);

			refreshSelection();
			repaint();
			firePropertyChange("firstDayOfWeek", old, firstDayOfWeek);
		}
	}

	public void refresh() {
		for (int i = 0; i < months.length; i++)
			months[i].refresh();

	}

	public HeaderRenderer getHeaderRenderer() {
		return months[0].getHeaderRenderer();
	}

	public void setHeaderRenderer(HeaderRenderer headerRenderer) {
		headerrenderer = headerRenderer;
		for (int i = 0; i < months.length; i++)
			months[i].setHeaderRenderer(headerRenderer);

	}

	public int getOrientation() {
		return orientation;
	}

	public int getScrollPosition() {
		return scrollPosition;
	}

	public void setScrollPosition(int scrollPosition) {
		if (scrollPosition == this.scrollPosition)
			return;
		int old = this.scrollPosition;
		this.scrollPosition = scrollPosition;
		if (orientation == 0) {
			remove(abajo);
		} else {
			remove(ys);
			remove(scroll);
		}
		layoutScrollAndYear();
		firePropertyChange("scrollPosition", old, scrollPosition);
	}

	public int getYearPosition() {
		return yearPosition;
	}

	public void setYearPosition(int yearPosition) {
		if (yearPosition == this.yearPosition)
			return;
		int old = this.yearPosition;
		this.yearPosition = yearPosition;
		if (orientation == 0) {
			remove(abajo);
		} else {
			remove(ys);
			remove(scroll);
		}
		layoutScrollAndYear();
		firePropertyChange("yearPosition", old, yearPosition);
	}

	private void layoutScrollAndYear() {
		if (orientation == 1) {
			if (yearPosition == 0) {
				add(ys, "North");
				add(today, "South");
			} else {
				add(ys, "South");
				add(today, "North");
			}
			if (scrollPosition == 0)
				add(scroll, "West");
			else
				add(scroll, "East");
		} else {
			abajo = new JPanel(new GridBagLayout());
			GridBagConstraints gbc = new GridBagConstraints();
			gbc.weighty = 1.0D;
			gbc.gridheight = 1;
			gbc.gridwidth = 1;
			gbc.fill = 1;
			if (yearPosition == 0) {
				gbc.weightx = 0.0D;
				abajo.add(ys, gbc);
				gbc.weightx = 1.0D;
				abajo.add(scroll, gbc);
				gbc.weightx = 0.0D;
				abajo.add(today, gbc);
			} else {
				gbc.weightx = 0.0D;
				abajo.add(today, gbc);
				gbc.weightx = 1.0D;
				abajo.add(scroll, gbc);
				gbc.weightx = 0.0D;
				abajo.add(ys, gbc);
			}
			if (scrollPosition == 0)
				add(abajo, "North");
			else
				add(abajo, "South");
		}
	}

	public int getQuantity() {
		return months.length;
	}

	public void setQuantity(int quantity) {
		if (quantity < 1)
			quantity = 1;
		if (quantity > 12)
			quantity = 12;
		if (months != null && months.length == quantity)
			return;
		int old = this.quantity;
		months = new MonthPanel[quantity];
		for (int i = 0; i < months.length; i++) {
			months[i] = new MonthPanel(showWeekNumber);
			months[i].showTitle(true);
			months[i].setModel(datamodel);
			months[i].setRenderer(dayrenderer);
			months[i].setHeaderRenderer(headerrenderer);
			months[i].setMinimalDaysInFirstWeek(minimalDaysInFirstWeek);
			months[i].setDay(new Date());
			DayPanel daypanels[] = months[i].getDaypanels();
			for (int j = 0; j < daypanels.length; j++) {
				daypanels[j].addKeyListener(klistener);
				daypanels[j].addMouseListener(mlistener);
			}

		}

		int oldor = orientation;
		orientation = -1;
		setOrientation(oldor);
		this.quantity = quantity;
		scroll.setMaximum(17 - quantity);
		if (old != 0)
			firePropertyChange("quantity", old, quantity);
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
		for (int i = 0; i < months.length; i++)
			months[i].setEnabled(enabled);

		scroll.setEnabled(enabled);
		ys.setEnabled(enabled);
		repaint();
		super.setEnabled(enabled);
	}

	public boolean isEnabled() {
		return scroll.isEnabled();
	}

	public boolean isAntiAliased() {
		return antiAliased;
	}

	public void setAntiAliased(boolean antiAliased) {
		boolean old = this.antiAliased;
		this.antiAliased = antiAliased;
		for (int i = 0; i < months.length; i++)
			months[i].setAntiAliased(antiAliased);

		firePropertyChange("antiAliased", old, antiAliased);
	}

	public int getSelectionMode() {
		return dateSelectionModel.getSelectionMode();
	}

	public void setSelectionMode(int selectionMode) {
		int old = dateSelectionModel.getSelectionMode();
		dateSelectionModel.setSelectionMode(selectionMode);
		refreshSelection();
		firePropertyChange("selectionMode", old, selectionMode);
	}

	private void refreshSelection() {
		for (int i = 0; i < months.length; i++) {
			DayPanel daypanels[] = months[i].getDaypanels();
			for (int j = 0; j < daypanels.length; j++)
				if (!daypanels[j].isEnabled())
					daypanels[j].setSelected(false);
				else
					daypanels[j].setSelected(dateSelectionModel.isSelectedDate(daypanels[j].getDate()));

		}

	}

	private boolean isShowing(Date date) {
		if (date.before(minDate))
			return false;
		return !date.after(maxDate);
	}

	private void showMonth(Date d) {
		Calendar cal = new GregorianCalendar();
		cal.setTime(d);
		if (ys.getYear() != cal.get(1)) {
			ys.setYear(cal.get(1));
			setShowingYear(ys.getYear());
		}
		if (isShowing(d))
			return;
		int month = cal.get(2);
		int middle = quantity / 2;
		int show = 0;
		if (month < scroll.getValue())
			show = month;
		else
			show = (month - quantity) + 1;
		if (show < 0)
			show = 0;
		if (show > (11 - quantity) + middle)
			show = (11 - quantity) + middle;
		scroll.setValue(show);
	}

	public Object getValue() {
		return dateSelectionModel.getSelectedDate();
	}

	public Object[] getValues() {
		return dateSelectionModel.getSelectedDates();
	}

	public void setValue(Object date) {
		try {
			setDate(CalendarUtils.convertToDate(date));
		}
		catch (ParseException e) {
			e.printStackTrace();
		}
	}

	public void setValues(Object dates[]) {
		dateSelectionModel.setSelectedDates(dates);
		refreshSelection();
		repaint();
		showMonth((Date)dateSelectionModel.getSelectedDates()[0]);
	}

	public DateSelectionModel getDateSelectionModel() {
		return dateSelectionModel;
	}

	public void setDateSelectionModel(DateSelectionModel dateSelectionModel) {
		if (dateSelectionModel != null) {
			this.dateSelectionModel.removeDateSelectionListener(listlistener);
			this.dateSelectionModel = dateSelectionModel;
			dateSelectionModel.addDateSelectionListener(listlistener);
		}
	}

	public void setMinimalDaysInFirstWeek(int number) {
		if (months[0].getMinimalDaysInFirstWeek() != number) {
			for (int i = 0; i < months.length; i++)
				months[i].setMinimalDaysInFirstWeek(number);

			refreshSelection();
			repaint();
			firePropertyChange("minimalDaysInFirstWeek", minimalDaysInFirstWeek, number);
			minimalDaysInFirstWeek = number;
		}
	}

	public int getMinimalDaysInFirstWeek() {
		return months[0].getMinimalDaysInFirstWeek();
	}

	public boolean isPrintMoon() {
		return printMoon;
	}

	public void setPrintMoon(boolean printMoon) {
		if (this.printMoon != printMoon) {
			this.printMoon = printMoon;
			for (int i = 0; i < months.length; i++)
				months[i].setPrintMoon(printMoon);

			refreshSelection();
			repaint();
			firePropertyChange("printMoon", printMoon, !printMoon);
		}
	}

	public void setTodayCaption(String caption) {
		if (caption == null)
			today.setText(CalendarUtils.getMessage("today"));
		else
			today.setText(caption);
	}

	public boolean isEternalScroll() {
		return eternalScroll;
	}

	public void setEternalScroll(boolean eternalScroll) {
		this.eternalScroll = eternalScroll;
	}

	public boolean isShowToday() {
		return showToday;
	}

	public void setShowToday(boolean showToday) {
		today.setVisible(showToday);
		repaint();
		this.showToday = showToday;
	}



















}
