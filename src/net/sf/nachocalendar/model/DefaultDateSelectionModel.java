// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(1000) fieldsfirst nonlb space 
// Source File Name:   DefaultDateSelectionModel.java

package net.sf.nachocalendar.model;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Iterator;
import java.util.List;
import javax.swing.event.EventListenerList;
import net.sf.nachocalendar.components.CalendarUtils;
import net.sf.nachocalendar.event.DateSelectionEvent;
import net.sf.nachocalendar.event.DateSelectionListener;

// Referenced classes of package net.sf.nachocalendar.model:
//			DateSelectionModel

public class DefaultDateSelectionModel
	implements DateSelectionModel {
	private static class MultipleInterval
		implements DateSelectionModel {

		private List selection;
		private Calendar calendar;
		private Date leadSelection;

		public void addSelectionInterval(Date from, Date to) {
			if (from == null || to == null)
				return;
			Date d1 = null;
			Date d2 = null;
			if (from.before(to)) {
				d1 = from;
				d2 = to;
			} else {
				d1 = to;
				d2 = from;
			}
			calendar.setTime(d1);
			do {
				selection.add(calendar.getTime());
				calendar.add(6, 1);
			} while (d2.after(calendar.getTime()) || CalendarUtils.isSameDay(d2, calendar.getTime()));
			leadSelection = to;
		}

		public void clearSelection() {
			selection.clear();
		}

		public boolean isSelectedDate(Date date) {
			if (selection.isEmpty())
				return false;
			if (date == null)
				return false;
			for (Iterator it = selection.iterator(); it.hasNext();) {
				Date d = (Date)it.next();
				if (CalendarUtils.isSameDay(d, date))
					return true;
			}

			return false;
		}

		public boolean isSelectionEmpty() {
			return selection.isEmpty();
		}

		public void removeSelectionInterval(Date from, Date to) {
			if (from == null || to == null)
				return;
			if (from == null || to == null)
				return;
			Object dates[] = DefaultDateSelectionModel.getDates(from, to);
label0:
			for (int i = 0; i < dates.length; i++) {
				Iterator it = selection.iterator();
				Date d = (Date)dates[i];
				Date dd;
				do {
					if (!it.hasNext())
						continue label0;
					dd = (Date)it.next();
				} while (!CalendarUtils.isSameDay(d, dd));
				selection.remove(dd);
			}

			leadSelection = to;
		}

		public void addDateSelectionListener(DateSelectionListener dateselectionlistener) {
		}

		public void removeDateSelectionListener(DateSelectionListener dateselectionlistener) {
		}

		public int getSelectionMode() {
			return 2;
		}

		public void setSelectionMode(int i) {
		}

		public Date getLeadSelectionDate() {
			return leadSelection;
		}

		public void setLeadSelectionDate(Date date) {
			leadSelection = date;
		}

		public Object getSelectedDate() {
			if (selection.isEmpty())
				return null;
			else
				return leadSelection;
		}

		public Object[] getSelectedDates() {
			Collections.sort(selection);
			return selection.toArray();
		}

		public void setSelectedDate(Object date) {
			selection.clear();
			if (date != null)
				try {
					selection.add(CalendarUtils.convertToDate(date));
				}
				catch (ParseException e) {
					e.printStackTrace();
				}
		}

		public void setSelectedDates(Object dates[]) {
			selection.clear();
			if (dates == null || dates.length < 1)
				return;
			for (int i = 0; i < dates.length; i++)
				try {
					selection.add(CalendarUtils.convertToDate(dates[i]));
				}
				catch (ParseException e) {
					e.printStackTrace();
				}

		}

		public void setValueIsAdjusting(boolean flag) {
		}

		public boolean getValueIsAdjusting() {
			return false;
		}

		MultipleInterval() {
			selection = new ArrayList();
			calendar = new GregorianCalendar();
		}
	}

	private static class SingleInterval
		implements DateSelectionModel {

		private Date from;
		private Date to;
		private Date lead;

		public void addSelectionInterval(Date from, Date to) {
			if (from == null || to == null) {
				this.from = null;
				this.to = null;
				return;
			}
			if (from.after(to)) {
				this.from = to;
				this.to = from;
			} else {
				this.from = from;
				this.to = to;
			}
			lead = to;
		}

		public void clearSelection() {
			from = null;
			to = null;
		}

		public boolean isSelectedDate(Date date) {
			if (from == null || to == null)
				return false;
			if (date == null)
				return false;
			if (CalendarUtils.isSameDay(date, from) || CalendarUtils.isSameDay(date, to))
				return true;
			return !date.before(from) && !date.after(to);
		}

		public boolean isSelectionEmpty() {
			return from == null || to == null;
		}

		public void removeSelectionInterval(Date date, Date date1) {
		}

		public void addDateSelectionListener(DateSelectionListener dateselectionlistener) {
		}

		public void removeDateSelectionListener(DateSelectionListener dateselectionlistener) {
		}

		public int getSelectionMode() {
			return 1;
		}

		public void setSelectionMode(int i) {
		}

		public Date getLeadSelectionDate() {
			return lead;
		}

		public void setLeadSelectionDate(Date date) {
			lead = date;
		}

		public Object getSelectedDate() {
			return lead;
		}

		public Object[] getSelectedDates() {
			return DefaultDateSelectionModel.getDates(from, to);
		}

		public void setSelectedDate(Object date) {
			try {
				from = CalendarUtils.convertToDate(date);
				to = from;
			}
			catch (ParseException e) {
				e.printStackTrace();
			}
		}

		public void setSelectedDates(Object dates[]) {
			if (dates == null || dates.length < 1) {
				from = null;
				to = null;
				return;
			}
			try {
				from = CalendarUtils.convertToDate(dates[0]);
				to = CalendarUtils.convertToDate(dates[dates.length - 1]);
			}
			catch (ParseException e) {
				e.printStackTrace();
			}
		}

		public void setValueIsAdjusting(boolean flag) {
		}

		public boolean getValueIsAdjusting() {
			return false;
		}

		private SingleInterval() {
		}

	}

	private static class SingleSelection
		implements DateSelectionModel {

		private Date selection;

		public void addSelectionInterval(Date from, Date to) {
			selection = to;
		}

		public void clearSelection() {
			selection = null;
		}

		public boolean isSelectedDate(Date date) {
			if (selection == null)
				return false;
			if (date == null)
				return false;
			else
				return CalendarUtils.isSameDay(date, selection);
		}

		public boolean isSelectionEmpty() {
			return selection == null;
		}

		public void removeSelectionInterval(Date date, Date date1) {
		}

		public void addDateSelectionListener(DateSelectionListener dateselectionlistener) {
		}

		public void removeDateSelectionListener(DateSelectionListener dateselectionlistener) {
		}

		public int getSelectionMode() {
			return 0;
		}

		public void setSelectionMode(int i) {
		}

		public Date getLeadSelectionDate() {
			return selection;
		}

		public void setLeadSelectionDate(Date date) {
			selection = date;
		}

		public Object getSelectedDate() {
			return selection;
		}

		public Object[] getSelectedDates() {
			return (new Object[] {
				selection
			});
		}

		public void setSelectedDate(Object date) {
			if (date == null)
				return;
			try {
				selection = CalendarUtils.convertToDate(date);
			}
			catch (ParseException e) {
				e.printStackTrace();
			}
		}

		public void setSelectedDates(Object dates[]) {
			if (dates == null)
				return;
			if (dates.length == 0) {
				selection = null;
				return;
			}
			try {
				selection = CalendarUtils.convertToDate(dates[0]);
			}
			catch (ParseException e) {
				e.printStackTrace();
			}
		}

		public void setValueIsAdjusting(boolean flag) {
		}

		public boolean getValueIsAdjusting() {
			return false;
		}

		private SingleSelection() {
		}

	}


	private DateSelectionModel model;
	private boolean isAdjusting;
	private boolean pendingEvent;
	private EventListenerList listenerList;

	public DefaultDateSelectionModel() {
		listenerList = null;
		model = new MultipleInterval();
	}

	public void addSelectionInterval(Date from, Date to) {
		model.addSelectionInterval(from, to);
		fireDateSelectionListenerValueChanged(new DateSelectionEvent(this));
	}

	public void clearSelection() {
		model.clearSelection();
		fireDateSelectionListenerValueChanged(new DateSelectionEvent(this));
	}

	public boolean isSelectedDate(Date date) {
		return model.isSelectedDate(date);
	}

	public boolean isSelectionEmpty() {
		return model.isSelectionEmpty();
	}

	public void removeSelectionInterval(Date from, Date to) {
		model.removeSelectionInterval(from, to);
		fireDateSelectionListenerValueChanged(new DateSelectionEvent(this));
	}

	public synchronized void addDateSelectionListener(DateSelectionListener listener) {
		if (listenerList == null)
			listenerList = new EventListenerList();
		listenerList.add(net.sf.nachocalendar.event.DateSelectionListener.class, listener);
	}

	public synchronized void removeDateSelectionListener(DateSelectionListener listener) {
		listenerList.remove(net.sf.nachocalendar.event.DateSelectionListener.class, listener);
	}

	private void fireDateSelectionListenerValueChanged(DateSelectionEvent event) {
		if (isAdjusting) {
			pendingEvent = true;
			return;
		}
		if (listenerList == null)
			return;
		Object listeners[] = listenerList.getListenerList();
		for (int i = listeners.length - 2; i >= 0; i -= 2)
			if (listeners[i] == (net.sf.nachocalendar.event.DateSelectionListener.class))
				((DateSelectionListener)listeners[i + 1]).valueChanged(event);

	}

	public int getSelectionMode() {
		return model.getSelectionMode();
	}

	public void setSelectionMode(int selectionMode) {
		switch (selectionMode) {
		case 0: // '\0'
			model = new SingleSelection();
			break;

		case 1: // '\001'
			model = new SingleInterval();
			break;

		case 2: // '\002'
		default:
			model = new MultipleInterval();
			break;
		}
	}

	public Date getLeadSelectionDate() {
		return model.getLeadSelectionDate();
	}

	public void setLeadSelectionDate(Date date) {
		model.setLeadSelectionDate(date);
	}

	public Object getSelectedDate() {
		return model.getSelectedDate();
	}

	public Object[] getSelectedDates() {
		return model.getSelectedDates();
	}

	public void setSelectedDate(Object date) {
		model.setSelectedDate(date);
		fireDateSelectionListenerValueChanged(new DateSelectionEvent(this));
	}

	public void setSelectedDates(Object dates[]) {
		model.setSelectedDates(dates);
		fireDateSelectionListenerValueChanged(new DateSelectionEvent(this));
	}

	private static Object[] getDates(Date from, Date to) {
		List retorno = new ArrayList();
		Calendar cal = new GregorianCalendar();
		cal.setTime(from);
		for (; to.after(cal.getTime()) || CalendarUtils.isSameDay(to, cal.getTime()); cal.add(6, 1))
			retorno.add(cal.getTime());

		Collections.sort(retorno);
		return retorno.toArray();
	}

	public void setValueIsAdjusting(boolean b) {
		isAdjusting = b;
		if (!b && pendingEvent) {
			fireDateSelectionListenerValueChanged(new DateSelectionEvent(this));
			pendingEvent = false;
		}
	}

	public boolean getValueIsAdjusting() {
		return isAdjusting;
	}

}
