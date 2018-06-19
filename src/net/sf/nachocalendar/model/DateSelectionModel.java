// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(1000) fieldsfirst nonlb space 
// Source File Name:   DateSelectionModel.java

package net.sf.nachocalendar.model;

import java.util.Date;
import net.sf.nachocalendar.event.DateSelectionListener;

public interface DateSelectionModel {

	public static final int SINGLE_SELECTION = 0;
	public static final int SINGLE_INTERVAL_SELECTION = 1;
	public static final int MULTIPLE_INTERVAL_SELECTION = 2;

	public abstract void addSelectionInterval(Date date, Date date1);

	public abstract void clearSelection();

	public abstract boolean isSelectedDate(Date date);

	public abstract boolean isSelectionEmpty();

	public abstract int getSelectionMode();

	public abstract void setSelectionMode(int i);

	public abstract void removeSelectionInterval(Date date, Date date1);

	public abstract void addDateSelectionListener(DateSelectionListener dateselectionlistener);

	public abstract void removeDateSelectionListener(DateSelectionListener dateselectionlistener);

	public abstract Date getLeadSelectionDate();

	public abstract void setLeadSelectionDate(Date date);

	public abstract Object getSelectedDate();

	public abstract Object[] getSelectedDates();

	public abstract void setSelectedDate(Object obj);

	public abstract void setSelectedDates(Object aobj[]);

	public abstract void setValueIsAdjusting(boolean flag);

	public abstract boolean getValueIsAdjusting();
}
