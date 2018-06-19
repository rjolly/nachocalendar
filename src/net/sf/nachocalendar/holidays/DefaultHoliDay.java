// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(1000) fieldsfirst nonlb space 
// Source File Name:   DefaultHoliDay.java

package net.sf.nachocalendar.holidays;

import java.io.Serializable;
import java.util.Date;

// Referenced classes of package net.sf.nachocalendar.holidays:
//			HoliDay

public class DefaultHoliDay
	implements Serializable, HoliDay {

	private String name;
	private Date date;
	private String description;
	private boolean recurrent;

	public DefaultHoliDay() {
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Date getDate() {
		if (date != null)
			return (Date)date.clone();
		else
			return null;
	}

	public void setDate(Date date) {
		if (date == null) {
			throw new IllegalArgumentException("Illegal Date");
		} else {
			this.date = (Date)date.clone();
			return;
		}
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public boolean isRecurrent() {
		return recurrent;
	}

	public void setRecurrent(boolean recurrent) {
		this.recurrent = recurrent;
	}

	public String toString() {
		return name;
	}
}
