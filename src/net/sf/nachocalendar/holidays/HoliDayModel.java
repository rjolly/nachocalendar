// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(1000) fieldsfirst nonlb space 
// Source File Name:   HoliDayModel.java

package net.sf.nachocalendar.holidays;

import java.util.Calendar;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.Iterator;
import java.util.LinkedList;
import net.sf.nachocalendar.model.DataModel;

// Referenced classes of package net.sf.nachocalendar.holidays:
//			HoliDay

public class HoliDayModel
	implements DataModel {

	private LinkedList holidays;
	private Comparator sorter;
	private Calendar cal;
	private Calendar check;
	private int currentmonth;
	private LinkedList minimumlist;

	public HoliDayModel() {
		holidays = new LinkedList();
		minimumlist = new LinkedList();
		cal = Calendar.getInstance();
		check = Calendar.getInstance();
		sorter = new Comparator() {

			public int compare(Object o1, Object o2) {
				HoliDay h1 = (HoliDay)o1;
				HoliDay h2 = (HoliDay)o2;
				return (int)(h1.getDate().getTime() - h2.getDate().getTime());
			}

		};
	}

	private void changeMonth(int month) {
		currentmonth = month;
		minimumlist.clear();
		Iterator it = holidays.iterator();
		do {
			if (!it.hasNext())
				break;
			HoliDay h = (HoliDay)it.next();
			check.setTime(h.getDate());
			if (check.get(2) == month)
				minimumlist.add(h);
		} while (true);
	}

	private boolean compare(HoliDay h, int year, int month, int day) {
		cal.setTime(h.getDate());
		if (!h.isRecurrent() && year != cal.get(1))
			return false;
		if (month != cal.get(2))
			return false;
		return day == cal.get(5);
	}

	public void addHoliDay(HoliDay day) {
		holidays.add(day);
		if (holidays.size() > 1)
			Collections.sort(holidays, sorter);
	}

	public void removeHoliDay(HoliDay day) {
		holidays.remove(day);
	}

	public int getSize() {
		return holidays.size();
	}

	public Collection getAll() {
		return (Collection)holidays.clone();
	}

	public void clear() {
		holidays.clear();
	}

	public Object getData(Date date) {
		return getHoliDay(date);
	}

	public HoliDay getHoliDay(Date date) {
		cal.setTime(date);
		int day = cal.get(5);
		int month = cal.get(2);
		int year = cal.get(1);
		if (month != currentmonth)
			changeMonth(month);
		for (Iterator it = minimumlist.iterator(); it.hasNext();) {
			HoliDay h = (HoliDay)it.next();
			if (compare(h, year, month, day))
				return h;
		}

		return null;
	}
}
