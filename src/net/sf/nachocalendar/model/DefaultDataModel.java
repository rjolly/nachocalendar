// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(1000) fieldsfirst nonlb space 
// Source File Name:   DefaultDataModel.java

package net.sf.nachocalendar.model;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import net.sf.nachocalendar.event.DataChangeEvent;
import net.sf.nachocalendar.event.DataModelListener;

// Referenced classes of package net.sf.nachocalendar.model:
//			DataModel

public class DefaultDataModel
	implements DataModel {

	private HashMap data;
	private HashMap mindata;
	private Calendar cal;
	private Calendar check;
	private int currentmonth;
	private transient ArrayList dataModelListenerList;

	public DefaultDataModel() {
		cal = Calendar.getInstance();
		check = Calendar.getInstance();
		data = new HashMap();
		mindata = new HashMap();
	}

	private void changeMonth(int month) {
		currentmonth = month;
		mindata.clear();
		Iterator it = data.keySet().iterator();
		do {
			if (!it.hasNext())
				break;
			Date d = (Date)it.next();
			check.setTime(d);
			if (check.get(2) == month)
				mindata.put(d, data.get(d));
		} while (true);
	}

	public void addData(Date date, Object o) {
		data.put(date, o);
		fireDataModelListenerDataChanged(new DataChangeEvent(o, date));
		currentmonth = -1;
	}

	public void removeData(Date date) {
		Object o = data.remove(date);
		currentmonth = -1;
		if (o != null)
			fireDataModelListenerDataChanged(new DataChangeEvent(o, date));
	}

	public int getSize() {
		return data.size();
	}

	public Map getAll() {
		return (Map)data.clone();
	}

	public void clear() {
		data.clear();
	}

	public Object getData(Date date) {
		cal.setTime(date);
		int month = cal.get(2);
		if (month != currentmonth)
			changeMonth(month);
		int year = cal.get(1);
		int day = cal.get(5);
		for (Iterator it = mindata.keySet().iterator(); it.hasNext();) {
			Date d = (Date)it.next();
			if (compareDates(year, month, day, d))
				return mindata.get(d);
		}

		return null;
	}

	private boolean compareDates(int year, int month, int day, Date d) {
		check.setTime(d);
		if (day != check.get(5))
			return false;
		if (month != check.get(2))
			return false;
		return year == check.get(1);
	}

	public synchronized void addDataModelListener(DataModelListener listener) {
		if (dataModelListenerList == null)
			dataModelListenerList = new ArrayList();
		dataModelListenerList.add(listener);
	}

	public synchronized void removeDataModelListener(DataModelListener listener) {
		if (dataModelListenerList != null)
			dataModelListenerList.remove(listener);
	}

	private void fireDataModelListenerDataChanged(DataChangeEvent event) {
label0:
		{
			synchronized (this) {
				if (dataModelListenerList != null)
					break label0;
			}
			return;
		}
		ArrayList list = (ArrayList)dataModelListenerList.clone();
		for (int i = 0; i < list.size(); i++)
			((DataModelListener)list.get(i)).dataChanged(event);

		return;
	}
}
