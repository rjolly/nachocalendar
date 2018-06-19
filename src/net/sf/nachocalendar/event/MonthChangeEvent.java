// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(1000) fieldsfirst nonlb space 
// Source File Name:   MonthChangeEvent.java

package net.sf.nachocalendar.event;

import java.util.Date;
import java.util.EventObject;

public class MonthChangeEvent extends EventObject {

	private Date date;

	public MonthChangeEvent(Object source, Date newdate) {
		super(source);
		if (newdate != null)
			date = (Date)newdate.clone();
		else
			date = null;
	}

	public Date getDate() {
		if (date != null)
			return (Date)date.clone();
		else
			return null;
	}

	public void setDate(Date date) {
		if (date != null)
			this.date = (Date)date.clone();
		else
			this.date = null;
	}
}
